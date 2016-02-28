package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;

/**
 * Created by Derek on 2/27/2016.
 */
public class ResidenceHall extends Building {

    public static final String description = "A necessity for students traveling from far away, give 'em a place to stay!" +
            "\nMax Occupancy: 50 \nPrice: 200000 \n*RA's and furniture customizable";

    public ResidenceHall(Context c, String name, Coordinate coord) {
        super(c, 200000, name, coord, Tile.RESIDENCE_HALL);

    }
}
