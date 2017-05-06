package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;
import hacktech.youniversity.Profile;
import hacktech.youniversity.platform.YouniversityPlatform;

/**
 * Created by Derek on 2/28/2016.
 */
public class Road extends Building {

    public static int cost = 500;

    public Road(Context c, String name, Coordinate coord) {
        super(c, cost, name, coord, Tile.ROAD, 5);

    }

    public static String description() {
        return "Helps students get to class faster!" +
                "\nPrice: $500 Your balance: $" + YouniversityPlatform.getInstance().getProfile().getBalance();
    }

    public String toString() {
        return "Type: Road " + super.toString();
    }

}
