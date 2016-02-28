package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;

/**
 * Created by Derek on 2/27/2016.
 */
public class DiningHall extends Building {

    public static final String description = "A place to get food on campus! A staple at any university." +
            "\nMax Occupancy: 10 \nPrice: 50000 \n*Prices and foods are customizable.";

    public DiningHall(Context c, String name, Coordinate coord) {
        super(c, 50000, name, coord, Tile.DINING_HALL);

    }
}
