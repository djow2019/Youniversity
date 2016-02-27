package hacktech.youniversity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

import graphics.Tile;

/**
 * Created by Derek on 2/27/2016.
 */
public class Gameplay extends Activity {

    private static final int TILE_SIZE = 64;

    GridLayout map;

    private Random random = new Random();

    public static Gameplay gameplay;

    LinearLayout options, action_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gameplay_screen);
        getActionBar().hide();

        gameplay = this;

        action_bar = (LinearLayout) findViewById(R.id.action_bar);

        map = (GridLayout) findViewById(R.id.map_id);

        DisplayMetrics display = getResources().getDisplayMetrics();

        map.setRowCount(display.heightPixels / TILE_SIZE - 2);
        map.setColumnCount(display.widthPixels / TILE_SIZE);

        int xPadding = display.widthPixels % TILE_SIZE;
        int yPadding = display.heightPixels % TILE_SIZE;

        // broken
        map.setPadding(xPadding / 2, yPadding / 2, xPadding / 2, yPadding / 2);

        // Inflate your custom layout
        ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar, null);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int row = 0; row < map.getRowCount(); row++) {
                    for (int col = 0; col < map.getColumnCount(); col++) {

                        Tile tile = new Tile(Gameplay.this, new Coordinate(col, row), random.nextInt(8) == 0 ? 1 : 0);

                        map.addView(tile);
                    }

                }
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBuildClicked(View view) {
        final LinearLayout screen = (LinearLayout) findViewById(R.id.gameplay_screen);
        Log.e("Youniversity", "Screen: " + screen);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                screen.removeView(action_bar);

                if (options == null)
                    options = (LinearLayout) getLayoutInflater().inflate(R.layout.build_options, null);

                screen.addView(options, 0);
            }
        });
        for (int i = 0; i < map.getChildCount(); i++) {
            Tile t = (Tile) map.getChildAt(i);
            t.drawOutline();
            if (t.getBuilding() != null)
                t.disable();
        }
    }

    public void onCancelBuildClick(View view) {
        Tile.buildClicked = false;
        final LinearLayout screen = (LinearLayout) findViewById(R.id.gameplay_screen);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                screen.removeView(options);

                screen.addView(action_bar, 0);


                for (int i = 0; i < map.getChildCount(); i++) {
                    ((Tile) map.getChildAt(i)).enable();
                }
            }
        });
    }

    public void onLectureHallClick(View view) {
        Tile.buildClicked = true;
        Tile.next_type = Building.LECTURE_HALL;
    }

}
