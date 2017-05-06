package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;
import hacktech.youniversity.Profile;
import hacktech.youniversity.platform.YouniversityPlatform;

/**
 * Created by Derek on 2/28/2016.
 */
public class Pool extends Building {

    public static int cost = 500000;

    public Pool(Context c, String name, Coordinate coord) {
        super(c, cost, name, coord, Tile.POOL, 20);

    }

    public static String description() {
        return "Let your students cool off on a hot summer day with an luxurious swimming pool!" +
                "\nMax Occupancy: 20 \nPrice: $500000 Your balance: $" + YouniversityPlatform.getInstance().getProfile().getBalance();
    }

    public String toString() {
        return "Type: Pool " + super.toString();
    }
}
