package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;

/**
 * Created by Derek on 2/27/2016.
 */
public class LectureHall extends Building {

    public static final String description = "You cannot have a university without a lecture hall! Education is key to success." +
            "\nMax Occupancy: 100 \nPrice: 250000 \n*Classes and Professors are customizable";

    public LectureHall(Context c, String name, Coordinate coord) {
        super(c, 250000, name, coord, Tile.LECTURE_HALL);

    }
}
