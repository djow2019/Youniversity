package hacktech.youniversity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Derek on 2/27/2016.
 */
public class SetupMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_screen);

    }

    public void onCloseClicked(View view) {

        EditText text = (EditText) findViewById(R.id.university_name);
        EditText text2 = (EditText) findViewById(R.id.user_name);

        new Profile(text2.getText().toString(), text.getText().toString());

        startActivity(new Intent(getApplicationContext(), Gameplay.class));

    }

}
