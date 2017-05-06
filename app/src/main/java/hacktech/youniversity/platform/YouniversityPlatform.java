package hacktech.youniversity.platform;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import hacktech.youniversity.Profile;
import hacktech.youniversity.buildings.Building;

/**
 * Created by Derek on 2/28/2016.
 * Separate from Gameplay because it runs in the background
 * (Generate income when user doesnt have the app open)
 */
public class YouniversityPlatform extends Application implements Runnable {

    // name of the game
    public static final String NAME = "Youniversity";

    // instance of this application
    private static YouniversityPlatform platform;

    // the data of the user
    private Profile profile;

    // whether or not the player is
    private boolean running;

    // the gameloop that generates money
    private Thread gameloop;

    /**
     * Called when the application first starts
     */
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(NAME, "Background Process Running");

        // initialize instance of the class
        platform = this;

        // initialize the game thread
        gameloop = new Thread(this);
    }

    /**
     * Starts the gameloop to generate money on a specific profile
     */
    public void start(Profile profile) {

        this.profile = profile;
        running = true;
        gameloop.start();
    }

    /**
     * End the gameloop
     */
    @Override
    public void onTerminate() {
        super.onTerminate();

        running = false;
    }

    /**
     * @return instance of the application
     */
    public static YouniversityPlatform getInstance() {
        return platform;
    }

    /**
     * Generates money even in the background
     */
    @Override
    public void run() {

        // time between increments
        long lasttime = System.currentTimeMillis();

        while (running) {
            if (System.currentTimeMillis() > lasttime + 30000) {
                profile.generateStudents();
                profile.deposit(profile.getIncome() / 2880.0);
                lasttime = System.currentTimeMillis();
                Log.e(NAME, "GENERATING STUDENTS");
            }
        }

    }

    /**
     * @return instance of the user profile
     */
    public Profile getProfile() {
        return profile;
    }
}
