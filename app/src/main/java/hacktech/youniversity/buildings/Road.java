package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;

/**
 * Created by Derek on 2/28/2016.
 */
public class Road extends Building {

    public static final String description = "Helps students get to class faster!" +
            "\nPrice: 500";

    public Road(Context c, String name, Coordinate coord) {
        super(c, 500, name, coord, Tile.ROAD);

    }

}
