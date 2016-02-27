package hacktech.youniversity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainMenu extends Activity {

    private Button button_new;
    private Button button_load;
    private Button button_options;
    private Button button_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        button_new = (Button) findViewById(R.id.button_new);
        button_load = (Button) findViewById(R.id.button_load);
        button_options = (Button) findViewById(R.id.button_options);
        button_quit = (Button) findViewById(R.id.button_quit);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

    }

    public void onNewClicked(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setPositiveButton("Yeah!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), Gameplay.class));
            }
        });
        builder.setNegativeButton("No, thanks", null);
        builder.setMessage(R.string.newConfirmation);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void onLoadClicked(View view) {

    }

    public void onOptionsClicked(View view) {

    }

    public void onQuitClicked(View view) {
        System.exit(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
