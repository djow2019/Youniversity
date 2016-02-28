package hacktech.youniversity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import hacktech.youniversity.platform.YouniversityPlatform;

/**
 * Created by Derek on 2/27/2016.
 * Initializes the university name and user name, introduces the game
 */
public class SetupMenu extends Activity {

    /*
    * Called when activity first starts up
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_screen);

    }

    /* Called when close is clicked */
    public void onCloseClicked(View view) {

        EditText user = (EditText) findViewById(R.id.user_name);
        EditText university = (EditText) findViewById(R.id.university_name);

        YouniversityPlatform.profile = new Profile(user.getText().toString(), university.getText().toString());

        startActivity(new Intent(getApplicationContext(), Gameplay.class));

        /* Ends this activity */
        finish();

    }

}
