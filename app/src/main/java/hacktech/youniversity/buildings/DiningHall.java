package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;
import hacktech.youniversity.Profile;

/**
 * Created by Derek on 2/27/2016.
 */
public class DiningHall extends Building {

    public static int cost = 50000;

    public DiningHall(Context c, String name, Coordinate coord) {
        super(c, cost, name, coord, Tile.DINING_HALL, 30);

    }

    public static String description() {
        return "A place to get food on campus! A staple at any university." +
                "\nMax Occupancy: 30 \nPrice: $50000 Your balance: $" + profile.getBalance() +
                "\n*Prices and foods are customizable.";
    }

    public String toString() {
        return "Type: Dining Hall " + super.toString();
    }
}
