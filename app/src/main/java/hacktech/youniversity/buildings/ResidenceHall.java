package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;
import hacktech.youniversity.Profile;
import hacktech.youniversity.platform.YouniversityPlatform;

/**
 * Created by Derek on 2/27/2016.
 */
public class ResidenceHall extends Building {

    public static int cost = 200000;

    public ResidenceHall(Context c, String name, Coordinate coord) {
        super(c, cost, name, coord, Tile.RESIDENCE_HALL, 50);

    }

    public static String description() {
        return "A necessity for students traveling from far away, give 'em a place to stay!" +
                "\nMax Occupancy: 50 \nPrice: $200000 Your balance: $" + YouniversityPlatform.getInstance().getProfile().getBalance() +
                "\n*RA's and furniture customizable";
    }

    public String toString() {
        return "Type: Residence Hall " + super.toString();
    }
}
