package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;
import hacktech.youniversity.Profile;

/**
 * Created by Derek on 2/27/2016.
 */
public class Gym extends Building {

    public static int cost = 300000;

    public Gym(Context c, String name, Coordinate coord) {
        super(c, cost, name, coord, Tile.GYM, 30);

    }

    public static String description() {
        return "Get your kids in shape!" +
                "\nMax Occupancy: 30 \nPrice: $300000 Your balance: $" + profile.getBalance() + " \n*Equipment is customizable";
    }
}

