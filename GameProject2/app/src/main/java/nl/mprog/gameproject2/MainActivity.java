package nl.mprog.gameproject2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;



public class MainActivity extends Activity{


    MediaPlayer backgroundSound;
    MediaPlayer explosionSound;
    SharedPreferences data;
    private String filename = "savedstate";

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        //creating data for saving scores
        data = getSharedPreferences(filename,0);

        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //make sounds
        explosionSound = MediaPlayer.create(this, R.raw.explosion02);
        backgroundSound = MediaPlayer.create(this, R.raw.cool_music);

        //using  the gamePanel class to draw and play the game
        setContentView(new GamePanel(this, data, explosionSound, backgroundSound));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //here I am creating a menu for the sounds and giving them an ID when pressed
        switch (item.getItemId()){
            case R.id.soundOn:
                SharedPreferences.Editor editor = data.edit();
                editor.putInt("backgroundSound",1);
                editor.putInt("explosionSound",1);
                editor.commit();
                return true;

            case R.id.soundOf:
                SharedPreferences.Editor editor1 = data.edit();
                editor1.putInt("backgroundSound",0);
                editor1.putInt("explosionSound",0);
                editor1.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //here we turn of the music when the player stops the app.
    @Override
    protected void onPause(){
        super.onPause();{

            //only stop when music is playing
            if(data.getInt("backgroundSound",1) == 1) {
                backgroundSound.stop();
                explosionSound.stop();
            }
        }
    }
}
