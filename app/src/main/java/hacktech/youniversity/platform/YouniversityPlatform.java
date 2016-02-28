package hacktech.youniversity.platform;

import android.app.Application;
import android.util.Log;

/**
 * Created by Derek on 2/28/2016.
 */
public class YouniversityPlatform extends Application implements  Runnable{

    public static final String NAME = "Youniversity";

    Thread thread = new Thread(this);

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(NAME, "Background Process Runing");
        thread.start();
    }

    @Override
    public void run() {

    }
}
