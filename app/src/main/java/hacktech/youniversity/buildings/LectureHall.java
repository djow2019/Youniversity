package hacktech.youniversity.buildings;

import android.content.Context;

import hacktech.youniversity.Building;
import hacktech.youniversity.Coordinate;

/**
 * Created by Derek on 2/27/2016.
 */
public class LectureHall extends Building {

    public LectureHall(Context c, String name, Coordinate coord) {
        super(c, 1000, name, coord, Building.LECTURE_HALL);

    }
}
