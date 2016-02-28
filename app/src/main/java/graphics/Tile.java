package graphics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import hacktech.youniversity.Coordinate;
import hacktech.youniversity.Gameplay;
import hacktech.youniversity.R;
import hacktech.youniversity.buildings.Building;
import hacktech.youniversity.buildings.DiningHall;
import hacktech.youniversity.buildings.Gym;
import hacktech.youniversity.buildings.LectureHall;
import hacktech.youniversity.buildings.ResidenceHall;


/**
 * Created by Derek on 2/27/2016.
 */
public class Tile extends ImageView {

    public static final int GRASS = 0;
    public static final int DIRT = 1;
    public static final int TREE = 2;
    public static final int LECTURE_HALL = 10;
    public static final int DINING_HALL = 11;
    public static final int RESIDENCE_HALL = 12;
    public static final int GYM = 13;

    /* The building on this tile */
    private Building building;

    /* Location in the map array */
    private Coordinate coord;

    /* The image of the first background */
    private Drawable background;

    /* Image of the disabled background that all tiles have */
    private static Drawable disabled;

    /* The type of the tile, changes with background */
    private int type;

    /*
    * Tile
    * @param c - the Activity context to intialize the image view
    * @param coord - the location in the map
    * @param type - the type of tile it is
     */
    public Tile(Context c, Coordinate coord, int type) {
        super(c);

        this.coord = coord;
        this.type = type;

        /* Size of each tile */
        setMinimumHeight(Gameplay.TILE_SIZE);
        setMinimumWidth(Gameplay.TILE_SIZE);
        setMaxHeight(Gameplay.TILE_SIZE);
        setMaxWidth(Gameplay.TILE_SIZE);

        /* Sets the click listener */
        this.setOnClickListener(listener);

        disabled = getResources().getDrawable(R.drawable.disable_bg, null);

        /* Initially sets the first background*/
        updateBackground();
    }

    private void updateBackground() {
        /* Building's background has priority */
        if (building != null) {
            setBackground(building.getBackground());
            return;
        }
        if (background == null) {
            switch (type) {
                case GRASS:
                    background = getResources().getDrawable(R.drawable.grass, null);
                    break;
                case DIRT:
                    background = getResources().getDrawable(R.drawable.dirt, null);
                    break;
                case TREE:
                    background = getResources().getDrawable(R.drawable.tree, null);
                    break;
            }
        }

        setBackground(background);
    }

    public void drawOutline() {
        switch (type) {
            case GRASS:
                setBackground(getResources().getDrawable(R.drawable.grass_o, null));
                break;
            case DIRT:
                setBackground(getResources().getDrawable(R.drawable.dirt_o, null));
                break;
            case TREE:
                setBackground(getResources().getDrawable(R.drawable.tree_o, null));
                break;
            case LECTURE_HALL:
                setBackground(getResources().getDrawable(R.drawable.lecture_hall_o, null));
                break;
            case DINING_HALL:
                setBackground(getResources().getDrawable(R.drawable.food_building_o, null));
                break;
            case RESIDENCE_HALL:
                setBackground(getResources().getDrawable(R.drawable.dorm_o, null));
                break;
            case GYM:
                setBackground(getResources().getDrawable(R.drawable.gym_o, null));
                break;
        }
    }

    /* Fired when a tile is clicked */
    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Youniversity", "You clicked on " + coord);

            if (Gameplay.inBuildMode && isBuildable()) {

                /* Constructs a window asking for the building name and confirmation */
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                /* Called when the build button is pressed */
                builder.setPositiveButton("Build!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (Gameplay.lastTypeClicked) {

                            case LECTURE_HALL:
                                building = new LectureHall(getContext(), input.getText().toString(), coord);
                                type = Tile.LECTURE_HALL;
                                break;
                            case DINING_HALL:
                                building = new DiningHall(getContext(), input.getText().toString(), coord);
                                type = Tile.DINING_HALL;
                                break;
                            case RESIDENCE_HALL:
                                building = new ResidenceHall(getContext(), input.getText().toString(), coord);
                                type = Tile.RESIDENCE_HALL;
                                break;
                            case GYM:
                                building = new Gym(getContext(), input.getText().toString(), coord);
                                type = Tile.GYM;
                                break;

                        }
                        updateBackground();
                    }
                });
                /* Does nothing */
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                /* Customizes the popup window */
                switch (Gameplay.lastTypeClicked) {

                    case LECTURE_HALL:
                        builder.setTitle("Build Lecture Hall");
                        builder.setMessage(LectureHall.description);
                        break;
                    case DINING_HALL:
                        builder.setTitle("Build Dining Hall");
                        builder.setMessage(DiningHall.description);
                        break;

                    case RESIDENCE_HALL:
                        builder.setTitle("Build Residence Hall");
                        builder.setMessage(ResidenceHall.description);
                        break;
                    case GYM:
                        builder.setTitle("Build Gym");
                        builder.setMessage(Gym.description);
                        break;

                }
                builder.show();

                /* Tells the gameplay level to reset the action bar */
                Gameplay.gameplay.onCancelBuildClick(null);
            } else if (Gameplay.inDemolishMode && !isBuildable()) {

                /* Confirms you want to delete this building*/
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete " + building.getName());
                builder.setMessage("Are you sure you want to remove " + building.getName() + "?" +
                        "\n It will cose you $" + building.getPrice() / 2);
                builder.setPositiveButton("Demolish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        building = null;
                        type = Tile.GRASS;
                        updateBackground();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();

                /* Tells the gameplay level to reset the action bar */
                Gameplay.gameplay.onCancelDemolishClick(null);

            } else if (building != null) {
                /* Displays information aboubt the building*/
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(building.getName());
                builder.setMessage("To be implemented....");
                builder.setPositiveButton("Okay", null);
                builder.setNegativeButton("Manage", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* to be implemented */
                    }
                });
                builder.show();
            } else {
                /* Displays information aboubt the tile*/
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Tile");
                builder.setMessage("A building can be constructed here.");
                builder.setPositiveButton("Okay", null);
                builder.show();
            }

        }
    };

    /* Sets the background to disabled */
    public void disable() {
        setBackground(disabled);
    }

    /* Updates the background */
    public void enable() {
        updateBackground();
    }

    /* Buildable if it doesn't have a building */
    public boolean isBuildable() {
        return building == null;
    }

    /* Gets the Tile's building */
    public Building getBuilding() {
        return building;
    }

}
