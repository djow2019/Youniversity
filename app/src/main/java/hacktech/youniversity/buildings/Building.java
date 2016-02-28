package hacktech.youniversity.buildings;

import android.content.Context;
import android.graphics.drawable.Drawable;

import graphics.Tile;
import hacktech.youniversity.Coordinate;
import hacktech.youniversity.R;

/**
 * Created by Derek on 2/27/2016.
 */
public abstract class Building {

    private int price;
    private String name;
    private int occupancy;
    private Coordinate coord;

    private Drawable background;

    protected Context c;

    public Building(Context c, int price, String name, Coordinate coord, int type) {
        this.price = price;
        this.name = name;
        this.occupancy = 0;
        this.coord = coord;

        switch (type) {
            case Tile.LECTURE_HALL:
                background = c.getResources().getDrawable(R.drawable.lecture_hall, null);
                break;
            case Tile.DINING_HALL:
                background = c.getResources().getDrawable(R.drawable.food_building, null);
                break;
            case Tile.RESIDENCE_HALL:
                background = c.getResources().getDrawable(R.drawable.dorm, null);
                break;
            case Tile.GYM:
                background = c.getResources().getDrawable(R.drawable.gym, null);
                break;
            case Tile.POOL:
                background = c.getResources().getDrawable(R.drawable.pool, null);
                break;
            case Tile.ROAD:
                background = c.getResources().getDrawable(R.drawable.path, null);
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
