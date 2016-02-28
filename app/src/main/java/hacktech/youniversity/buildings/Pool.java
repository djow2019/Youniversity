package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;

/**
 * Created by Derek on 2/28/2016.
 */
public class Pool extends Building {

    public static final String description = "Let your students cool off on a hot summer day with an luxurious swimming pool!" +
            "\nMax Occupancy: 20 \nPrice: 500000";

    public Pool(Context c, String name, Coordinate coord) {
        super(c, 500000, name, coord, Tile.POOL);

    }
}
