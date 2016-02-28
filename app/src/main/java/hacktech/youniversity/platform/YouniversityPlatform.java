package hacktech.youniversity.platform;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import hacktech.youniversity.Profile;
import hacktech.youniversity.buildings.Building;

/**
 * Created by Derek on 2/28/2016.
 */
public class YouniversityPlatform extends Application implements Runnable {

    public static final String NAME = "Youniversity";
    public static Profile profile;

    public static boolean inGame = false;

    Thread thread = new Thread(this);

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(NAME, "Background Process Running");
        thread.start();
    }

    @Override
    public void run() {

        long lasttime = System.currentTimeMillis();

        while (true) {
            if (inGame && System.currentTimeMillis() > lasttime + 30000) {
                profile.generateStudents();
                profile.deposit(profile.getIncome() / 2880.0);
                lasttime = System.currentTimeMillis();
                Log.e(NAME, "GENERATING STUDENTS");
            }
        }

    }
}
