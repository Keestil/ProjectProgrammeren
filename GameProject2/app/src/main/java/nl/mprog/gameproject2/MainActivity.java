package nl.mprog.gameproject2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;



public class MainActivity extends Activity {


    MediaPlayer backgroundSound;
    MediaPlayer explosionSound;
    SharedPreferences data;
    private String filename = "savedstate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //creating data for saving scores
        data = getSharedPreferences(filename,0);

        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //make sounds
        explosionSound = MediaPlayer.create(this, R.raw.explosion02);
        backgroundSound = MediaPlayer.create(this, R.raw.cool_music2);

        //using  the gamePanel class to draw and play the game
        setContentView(new GamePanel(this, data, explosionSound, backgroundSound));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    //here we turn of the music when the player stops the app.
    @Override
    protected void onPause() {
        super.onPause();
        backgroundSound.stop();
        explosionSound.stop();
    }
}
