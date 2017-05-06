package hacktech.youniversity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import graphics.Tile;
import hacktech.youniversity.platform.YouniversityPlatform;

/*
* The first main activity that is launched when the game is started
 */
public class MainMenu extends Activity {

    public static MediaPlayer mPlayer;

    /*
    * Called before the activity starts
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        /* Hides the action bar at the top*/
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        /* Plays the music at the start */
        mPlayer = MediaPlayer.create(this, R.raw.cookie);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setLooping(true);
        mPlayer.start();
    }

    /*
    * Called when the activity ends
     */
    @Override
    protected void onStop() {
        super.onStop();
        /* Stops the music playing */
        mPlayer.stop();
    }

    /*
    * Called when the activity resumes from pause
     */
    @Override
    protected void onResume() {
        super.onResume();
        mPlayer.start();
    }

    /*
    * Called when the activity goes into the background
     */
    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.stop();
    }

    /*
    * Called when "NEW" is clicked
     */
    public void onNewClicked(View view) {

        /* Creates a pop up window asking for confirmation */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setPositiveButton("Yeah!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /* Goes to Setup Menu activity */
                startActivity(new Intent(getApplicationContext(), SetupMenu.class));

                /* Ends this activity */
                finish();
            }
        });
        builder.setNegativeButton("No, thanks", null);
        builder.setMessage(R.string.newConfirmation);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * Load a game from a save file
     * @param view - the button that was pressed
     */
    public void onLoadClicked(View view) {

        // Constructs a window asking for the save file name
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // title of window
        builder.setTitle("Load");

        // message that appears on window
        builder.setMessage("Enter the name of the save file: ");

        // set an input text field
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // cancel the dialog
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Try to load the game!
        builder.setPositiveButton("Load", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int x) {

                // all data in the file
                ArrayList<Integer> data = new ArrayList<Integer>();

                // open an output stream
                try (FileInputStream inputStream = openFileInput(input.getText().toString())) {

                    // stores an integer as a byte array
                    byte[] current = new byte[4];

                    // loop through all available integers
                    while (inputStream.read(current, 0, 4) != -1) {

                        // store it in data
                        data.add(ByteBuffer.wrap(current).getInt());

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // check if anything was loaded
                if (data.size() == 0) {
                    dialogInterface.cancel();
                    return;
                }

                // transition from this activity to the next
                Intent intent = new Intent(getApplicationContext(), Gameplay.class);


                // package the data to send
                int[] extra = new int[data.size()];
                for (int i = 0; i < data.size(); i++) {
                    extra[i] = data.get(i);
                }

                // pass along the parsed data
                intent.putExtra("load", extra);

                Log.e(YouniversityPlatform.NAME, "" + intent.getIntArrayExtra("load").length);

                // start gameplay
                startActivity(intent);

                // Ends this activity
                finish();

            }

        });

        // display the window
        builder.show();

    }

    public void onOptionsClicked(View view) {

    }

    /*
    * Ends the game
     */
    public void onQuitClicked(View view) {
        System.exit(0);
    }

}
