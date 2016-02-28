package hacktech.youniversity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

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

    public void onLoadClicked(View view) {

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
