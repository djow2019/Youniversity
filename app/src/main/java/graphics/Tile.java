package graphics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import hacktech.youniversity.Building;
import hacktech.youniversity.Coordinate;
import hacktech.youniversity.Gameplay;
import hacktech.youniversity.R;
import hacktech.youniversity.buildings.LectureHall;


/**
 * Created by Derek on 2/27/2016.
 */
public class Tile extends ImageView {

    private Building building;

    private Coordinate coord;

    // Type of scenery
    public static int next_type;

    // if the user owns the land to build
    boolean owned;

    public static int TILE_SIZE = 128;

    public static boolean buildClicked;

    Drawable background;

    Drawable disabled;

    private int type;

    public Tile(Context c, Coordinate coord, int type) {
        super(c);

        this.coord = coord;

        setMinimumHeight(TILE_SIZE);
        setMinimumWidth(TILE_SIZE);
        setMaxHeight(TILE_SIZE);
        setMaxWidth(TILE_SIZE);

        this.setOnClickListener(listener);

        disabled = getResources().getDrawable(R.drawable.disable_bg, null);

        switch (type) {
            case 0:
                background = getResources().getDrawable(R.drawable.grass, null);
                break;
            case 1:
                background = getResources().getDrawable(R.drawable.dirt, null);
                break;
            case 2:
                background = getResources().getDrawable(R.drawable.tree, null);
                break;
            case 10:
                background = getResources().getDrawable(R.drawable.lecture_hall, null);
                break;
        }
        this.type = type;

        setBackground(background);

    }

    public void drawOutline() {
        switch (type) {
            case 0:
                setBackground(getResources().getDrawable(R.drawable.grass_o, null));
                break;
            case 1:
                setBackground(getResources().getDrawable(R.drawable.dirt_o, null));
                break;
            case 2:
                setBackground(getResources().getDrawable(R.drawable.tree_o, null));
                break;
            case 10:
                setBackground(getResources().getDrawable(R.drawable.lecture_hall_o, null));
                break;
        }
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Youniversity", "You clicked on " + coord);

            if (buildClicked && isBuildable()) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Build!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        building = new LectureHall(getContext(), input.getText().toString(), coord);
                        setBackground(building.getBackground());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                switch (next_type) {

                    case Building.LECTURE_HALL:
                        builder.setTitle("Build Lecture Hall");
                        break;

                }
                builder.show();

                Gameplay.gameplay.onCancelBuildClick(null);
            }

        }
    };

    public void disable() {
        setBackground(disabled);
    }

    public void enable() {
        if (building == null)
            setBackground(background);
        else {
            setBackground(building.getBackground());
        }
    }

    public boolean isBuildable() {
        return building == null;
    }

    public Building getBuilding() {
        return building;
    }


}
