package hacktech.youniversity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import graphics.Tile;

/**
 * Created by Derek on 2/27/2016.
 */
public abstract class Building {

    private int price;
    private String name;
    private int occupancy;
    private Coordinate coord;

    private Drawable background;

    public static final int LECTURE_HALL = 10;

    protected Context c;

    public Building(Context c, int price, String name, Coordinate coord, int type) {
        this.price = price;
        this.name = name;
        this.occupancy = 0;
        this.coord = coord;

        switch (type) {
            case LECTURE_HALL:
                background = c.getResources().getDrawable(R.drawable.lecture_hall, null);
                break;
        }

    }

    public void demolish() {
        coord = null;
        // to be implemented later
    }

    public String toString() {
        return "Name: " + name + " Value: " + price + " Occupancy: " + occupancy;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public Coordinate getCoordinate() {
        return coord;
    }

    public Drawable getBackground() {
        return background;
    }

}
