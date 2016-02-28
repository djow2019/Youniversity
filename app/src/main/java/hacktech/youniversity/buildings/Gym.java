package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;

/**
 * Created by Derek on 2/27/2016.
 */
public class Gym extends Building {

    public static final String description = "Nobody likes a chunky student body. Get your kids in shape!" +
            "\nMax Occupancy: 30 \nPrice: 300000 \n*Equipment is customizable";

    public Gym(Context c, String name, Coordinate coord) {
        super(c, 300000, name, coord, Tile.GYM);

    }
}
