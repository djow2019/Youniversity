package hacktech.youniversity.buildings;

import android.content.Context;

import graphics.Tile;
import hacktech.youniversity.Coordinate;
import hacktech.youniversity.Profile;
import hacktech.youniversity.platform.YouniversityPlatform;

/**
 * Created by Derek on 2/27/2016.
 */
public class LectureHall extends Building {

    public static int cost = 250000;

    public LectureHall(Context c, String name, Coordinate coord) {
        super(c, cost, name, coord, Tile.LECTURE_HALL, 100);

    }

    public static String description() {
        return "You cannot have a university without a lecture hall! Education is key to success." +
                "\nMax Occupancy: 100 \nPrice: $250000 Your balance: $" + YouniversityPlatform.getInstance().getProfile().getBalance() +
                "\n*Classes and Professors are customizable";
    }

    public String toString() {
        return "Type: Lecture Hall " + super.toString();
    }
}
