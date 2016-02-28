package hacktech.youniversity;

import android.app.Activity;
import android.app.AlertDialog;
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

import java.util.Random;

import graphics.Tile;

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

    /* Used for randomly getting tiles */
    private Random random = new Random();

    public static Gameplay gameplay;

    /* Used to change the action bar at the top */
    private LinearLayout build_bar, action_bar, settings_bar, demolish_bar;

    /* Higher the number less likely it spawns */
    private static final int DIRT_CHANCE = 8;

    /* Higher the number less likely it spawns */
    private static final int TREE_CHANCE = 5;

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

        /* Update the Tiles on the user interface thread */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /* Loop through all possible tiles */
                for (int row = 0; row < map.getRowCount(); row++) {
                    for (int col = 0; col < map.getColumnCount(); col++) {

                        /* Default tile type */
                        int type = Tile.GRASS;

                        if (random.nextInt(DIRT_CHANCE) == 0)
                            type = Tile.DIRT;
                        else if (random.nextInt(TREE_CHANCE) == 0)
                            type = Tile.TREE;

                        Tile tile = new Tile(Gameplay.this, new Coordinate(col, row), type);

                        map.addView(tile);
                    }

                }
            }
        });

        /* Start playing music */
        MainMenu.mPlayer.start();

        vScroll = (ScrollView) findViewById(R.id.vScroll);
        hScroll = (HorizontalScrollView) findViewById(R.id.hScroll);
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

    /* Called when user saves the game */
    public void onSaveClicked(View view) {
        Log.d("Youniversity", "Save Clicked, but it doesn't do anything");
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

    }

    /* Called when profile is clicked */
    public void onProfileClicked(View view) {

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

    }

}
