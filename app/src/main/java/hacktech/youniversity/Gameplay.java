package hacktech.youniversity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import graphics.Generation;
import graphics.Tile;
import hacktech.youniversity.buildings.Building;
import hacktech.youniversity.buildings.DiningHall;
import hacktech.youniversity.buildings.Gym;
import hacktech.youniversity.buildings.LectureHall;
import hacktech.youniversity.buildings.Pool;
import hacktech.youniversity.buildings.ResidenceHall;
import hacktech.youniversity.buildings.Road;
import hacktech.youniversity.platform.YouniversityPlatform;

import static graphics.Tile.DINING_HALL;
import static graphics.Tile.GYM;
import static graphics.Tile.LECTURE_HALL;
import static graphics.Tile.POOL;
import static graphics.Tile.RESIDENCE_HALL;
import static graphics.Tile.ROAD;

/**
 * Created by Derek on 2/27/2016.
 * This activity manages the main gameplay and its map mechanics
 */
public class Gameplay extends Activity {

    /* Size of each tile as rendered in pixels */
    public static final int TILE_SIZE = 128;

    /* # of tiles on each side */
    public static final int MAP_SIZE = 32;

    /* Instance of the grid of the map */
    private GridLayout map;

    /* Determines whether or not a building can currently be built */
    public static boolean inBuildMode = false;

    /* Determines whether or not a building can be destroyed */
    public static boolean inDemolishMode = false;

    /* Determines the last build type that was clicked */
    public static int lastTypeClicked;

    // instance of this class
    public static Gameplay gameplay;

    // user data
    private Profile profile;

    /* Used to change the action bar at the top */
    private LinearLayout build_bar, action_bar, settings_bar, demolish_bar;

    /* Layout of the screen */
    private LinearLayout screen;

    /* Last mousex, mousey */
    private float mx, my;

    /* Scroll views that handle movement horizontally and vertically */
    private ScrollView vScroll;
    private HorizontalScrollView hScroll;

    /*
    * Called when activity starts up
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gameplay_screen);

        /* Hide the original action bar */
        getActionBar().hide();

        gameplay = this;

        /* Initialize our action bar */
        action_bar = (LinearLayout) findViewById(R.id.action_bar);

        /* Initialize the map */
        map = (GridLayout) findViewById(R.id.map_id);

        /* Gets the display information of the device */
        DisplayMetrics display = getResources().getDisplayMetrics();
        map.setRowCount(display.heightPixels / MAP_SIZE);
        map.setColumnCount(display.widthPixels / MAP_SIZE);

        /* Initialize reference to screen layout */
        screen = (LinearLayout) findViewById(R.id.gameplay_screen);

        // tile map data
        final Tile[] tilemap;

        // extra data with the intent
        Bundle b = getIntent().getExtras();

        // check if data is present
        if (b != null) {

            // get the data
            int[] data = b.getIntArray("load");

            // initialize the tile map
            tilemap = new Tile[data.length / 4];

            // build the tile data
            for (int i = 0; i < data.length; i += 4) {

                int type = data[i + 0];
                int x = data[i + 1];
                int y = data[i + 2];
                int buildingType = data[i+3];

                // tile locatioin
                Coordinate c = new Coordinate(x, y);

                // intialize the tile
                Tile t = new Tile(this, c, type);

                // initialize the building
                t.setBuilding(loadBuilding(buildingType, c));

                // store it in tile map
                tilemap[i / 4] = t;

            }

        } else {
            // no data was found so generate from scratch
            tilemap = Generation.generate(this, map.getColumnCount(), map.getRowCount());
        }

        /* Update the Tiles on the user interface thread */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /* Loop through all possible tiles */
                for (Tile t : tilemap) {
                    map.addView(t);
                }
            }
        });

        /* Start playing music */
        MainMenu.mPlayer.start();

        vScroll = (ScrollView) findViewById(R.id.vScroll);
        hScroll = (HorizontalScrollView) findViewById(R.id.hScroll);

        profile = YouniversityPlatform.getInstance().getProfile();
    }

    /* Top of the touch event chain, fires first and trickles down after the scroll views fire
     * their touch events. Then, it changes the position to move diagonally.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        /* currentx and current y*/
        float curX = 0, curY = 0;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mx = event.getX();
                my = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                mx = curX;
                my = curY;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                break;
        }

        return true;
    }

    /* Called when build is clicked */
    public void onBuildClicked(View view) {

        /* Update the action bars */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                screen.removeView(action_bar);

                if (build_bar == null)
                    build_bar = (LinearLayout) getLayoutInflater().inflate(R.layout.build_options, null);

                screen.addView(build_bar, 0);
            }
        });

        /* Give all the tiles an outline if they are possible targets */
        for (int i = 0; i < map.getChildCount(); i++) {
            Tile t = (Tile) map.getChildAt(i);
            t.drawOutline();
            if (t.getBuilding() != null)
                t.disable();
        }
    }

    /* Called when cancel build is clicked */
    public void onCancelBuildClick(View view) {

        inBuildMode = false;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                screen.removeView(build_bar);

                screen.addView(action_bar, 0);

                /* Update any tiles that have been disabled */
                for (int i = 0; i < map.getChildCount(); i++) {
                    ((Tile) map.getChildAt(i)).enable();
                }
            }
        });
    }

    /* Called when any building mode is clicked on*/
    public void onBuildingModeClicked(View view) {
        inBuildMode = true;
        lastTypeClicked = Integer.parseInt((String) view.getTag());
    }

    /* Called when settings button is clicked */
    public void onSettingsClicked(View view) {

        /* Update the action bars */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                screen.removeView(action_bar);

                if (settings_bar == null)
                    settings_bar = (LinearLayout) getLayoutInflater().inflate(R.layout.setting_bar, null);

                screen.addView(settings_bar, 0);
            }
        });

    }

    /* Called when exiting from settings */
    public void onBackClicked(View view) {

        /* Update the action bars */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                screen.removeView(settings_bar);
                screen.addView(action_bar, 0);

            }
        });
    }

    /**
     * Get a building based on the type specified
     * @param type - type of the building
     * @param coord - cooridnate of the building
     * @return the building instance
     */
    public Building loadBuilding(int type, Coordinate coord) {
        switch (type) {
            case LECTURE_HALL:
                return new LectureHall(this, "", coord);
            case DINING_HALL:
                return new DiningHall(this, "", coord);
            case RESIDENCE_HALL:
                return new ResidenceHall(this, "", coord);
            case GYM:
                return new Gym(this, "", coord);
            case POOL:
                return new Pool(this, "", coord);
            case ROAD:
                return new Road(this, "", coord);
            default:
                return null;
        }
    }

    /* Called when user saves the game */
    public void onSaveClicked(View view) {
        Log.d(YouniversityPlatform.NAME, "Saving!");

        // open an output stream
        try (FileOutputStream outputStream = openFileOutput(profile.getUniversityName(), Context.MODE_PRIVATE)) {

            // write tile data
            for (int i = 0; i < map.getChildCount(); i++) {

                // tile being saved
                Tile t = (Tile) map.getChildAt(i);

                // store the data for each tile in a byte array
                // 4 ints * 4 bytes each
                byte[] data = ByteBuffer.allocate(4 * 4)
                        .putInt(t.getType())
                        .putInt(t.getXCoord())
                        .putInt(t.getYCoord())
                        .putInt(t.getBuildingType())
                        .array();

                // write the data
                outputStream.write(data);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* Called when clicking quit */
    public void onQuit2Clicked(View view) {

        /* Show the following window to the user before quitting */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit Confirmation");
        builder.setMessage("Are you sure you want to quit? \n**Make sure you save first!");
        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

    }

    /* Called when demolish is clicked */
    public void onDemolishClicked(View view) {

        inDemolishMode = true;

        /* Update the action bars */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                screen.removeView(action_bar);

                if (demolish_bar == null)
                    demolish_bar = (LinearLayout) getLayoutInflater().inflate(R.layout.demolish_bar, null);

                screen.addView(demolish_bar, 0);

                /* Give all the tiles an outline if they are possible targets */
                for (int i = 0; i < map.getChildCount(); i++) {
                    Tile t = (Tile) map.getChildAt(i);
                    t.drawOutline();
                    if (t.getBuilding() == null)
                        t.disable();
                }

            }
        });

    }

    /* Called when finances is clicked */
    public void onFinancesClicked(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Finances");

        String body = "Current balance: " + profile.getBalance() +
                "\nEarning: " + (profile.getIncome() + profile.getExpenses()) +
                "\nLosing: " + profile.getExpenses() +
                "\nNet Profit: " + profile.getIncome();

        builder.setMessage(body);
        builder.setPositiveButton("Okay", null);
        builder.show();
    }

    /* Called when profile is clicked */
    public void onProfileClicked(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(profile.getUserName());
        builder.setMessage(profile.toString());
        builder.setPositiveButton("Okay", null);
        builder.show();

    }

    /* Called when you cancel demolition */
    public void onCancelDemolishClick(View view) {

        inDemolishMode = false;

        /* Update the action bars */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                screen.removeView(demolish_bar);
                screen.addView(action_bar, 0);

                /* Update any tiles that have been disabled */
                for (int i = 0; i < map.getChildCount(); i++) {
                    ((Tile) map.getChildAt(i)).enable();
                }

            }
        });
    }

    /* Called when business is clicked */
    public void onBusinessClicked(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Businesses");
        String format = "Lecture Halls\n";

        for (Building building : profile.getBuildings()) {
            if (building instanceof LectureHall)
                format += building.toString() + "\n";
        }
        format += "\nResidence Halls\n";
        for (Building building : profile.getBuildings()) {
            if (building instanceof ResidenceHall)
                format += building.toString() + "\n";
        }
        format += "\nDining Halls\n";
        for (Building building : profile.getBuildings()) {
            if (building instanceof DiningHall)
                format += building.toString() + "\n";
        }
        format += "\nGyms\n";
        for (Building building : profile.getBuildings()) {
            if (building instanceof Gym)
                format += building.toString() + "\n";
        }
        format += "\nPools\n";
        for (Building building : profile.getBuildings()) {
            if (building instanceof Pool)
                format += building.toString() + "\n";
        }
        format += "\nRoads\n";
        for (Building building : profile.getBuildings()) {
            if (building instanceof Road)
                format += building.toString() + "\n";
        }
        builder.setMessage(format);
        builder.setPositiveButton("Okay", null);
        builder.show();

    }

}
