package nl.mprog.gameproject2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
//import android.media.MediaPlayer;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    //soundfragments and savestate
    private MediaPlayer coolMusic;
    private MediaPlayer boomSound;
    private SharedPreferences saveState;

    //here we just make the width and height, adjust this if playing on another mobile!
    public static final int WIDTH = 900;
    public static final int HEIGHT = 1780;

    //the timers
    private long misslesStarttime;
    private long deadTime;
    private long deadTimepassed;

    //the Bitmaps of my game
    private Bitmap background;
    private Bitmap scaledbmp;

    // the classes
    private GameThread thread;
    private Hero player;
    private Missles missle;
    private Explosion explosion;

    //the booleans
    private boolean freeze = true;
    private boolean newgame = true;
    private boolean playMusic = false;
    private boolean playSound = false;
    private boolean musicPlayed = false;
    private boolean soundPlayed = false;
    private boolean detonate = false;

    //the texts
    private Paint text;
    private Paint suckText;
    private Paint bestText;

    // the rest
    private int musicCounter = 0;
    private int numMissles = 0;
    private int counter = 0;
    private int best = 0;
    private int score = 0;
    private int difficultyScore = 0;
    private ArrayList<Missles> missles = new ArrayList<Missles>();
    private Random random = new Random();


    public GamePanel(Context context,SharedPreferences data, MediaPlayer explosionSound,MediaPlayer backgroundSound){
        super(context);

        //importing data from mainactivity class
        coolMusic = backgroundSound;
        boomSound = explosionSound;
        saveState = data;

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        //making our thread for the game loop
        thread = new GameThread(getHolder(), this);

        //this is used so the program can handle things better
        setFocusable(true);
    }

    //we don't use this, but if i remove this function the program error's
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
    }

    //this loop just says to try running the thread,
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){
                e.printStackTrace();}
            retry = false;
        }
    }

    //here the action begins, we make the objects and start the thread.
    @Override
    public void surfaceCreated(SurfaceHolder holder){

        //making the scaled background
        background = BitmapFactory.decodeResource(getResources(), R.mipmap.cool_background);
        float scale = (float) background.getHeight() / (float) getHeight();
        int newWidth = Math.round(background.getWidth() / scale);
        int newHeight = Math.round(background.getHeight() / scale);
        scaledbmp = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);

        //making the player and explosion
        player = new Hero(BitmapFactory.decodeResource(getResources(), R.mipmap.helicopter_metalslug), 146, 91, 4);
        explosion = new Explosion(BitmapFactory.decodeResource(getResources(), R.mipmap.explosion), 100, 256, 20);
        explosion.setX(player.getX());

        //starting the thread
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        //what happens when the player touches the screen!
        if (event.getAction() == MotionEvent.ACTION_DOWN){

            //boolean check for when player is playing, if he isn't the chopper does nothing.
            if ((!player.isPlaying()) && (newgame) && (freeze)){
                player.setPlaying(true);

            //here we see what happens when the player is playing.
            } else if(player.isPlaying()){
                player.setUp(true);
                freeze = false;
            }
            return true;
        }

        //what happens when the player is pressing the screen
        if (event.getAction() == MotionEvent.ACTION_UP){
            player.setUp(false);
            playMusic = true;
            freeze = false;
            return true;
        }
        return super.onTouchEvent(event);
    }

    //this methods updates the sprites while going through the game,
    public void update(){

        //first we check whether the music must be played. Note the while loop must be used because
        //of the gamethread.
        playMusic();

        if (player.isPlaying()){

            //updating the player
            player.update();

            // check how many time has gone past and making a new missle for
            // each unit of time!
            long misslesTimepassed = (System.nanoTime() - misslesStarttime) / 1000000;

            //I like this time so far, i tried to let the game make more missles if the score
            //increases. One can adjust the scoreDifficulty() function for a preferred difficulty-
            //setting.
            if (misslesTimepassed > 750 - scoreDifficulty()){
                //making missles
                makeMissles();
                //don't forget to reset your timer
                misslesStarttime = System.nanoTime();
            }

            // Updating the missles in our list, this next algoritm just makes a list of missles and
            // removes them if they fall of our screen!
            for (int i = 0; i < missles.size(); i++){

                //can make a function out of this, Adjust missleSpeed when missles are made.
                if (missles.get(i).getmissleSpeed()<50){
                    missles.get(i).setmissleSpeed((long) (missles.get(i).getmissleSpeed() + (score / (float) 100)));

                } else{
                    missles.get(i).setmissleSpeed((long) 50);
                }

                //draw the missle
                missles.get(i).update();

                //if the missles and the player touch, the player dies and the game is over if the
                //ID is a rocket, but proceeds if the ID is a powerup
                if (touch(missles.get(i), player)){

                    //when the projectile is a missle
                    if (missles.get(i).getID() == 0 || missles.get(i).getID() == 3){
                        missles.remove(i);
                        player.setPlaying(false);
                        break;
                    }

                    //when the projectile is not a missle
                    if (missles.get(i).getID() == 1){
                        player = new Hero(BitmapFactory.decodeResource(getResources(), R.mipmap.saucer), 114, 76, 12);
                        player.setY(missles.get(i).getY());
                        missles.remove(i);
                        player.setPlaying(true);
                    }

                    if (missles.get(i).getID() == 2){
                        player = new Hero(BitmapFactory.decodeResource(getResources(), R.mipmap.helicopter_metalslug), 146, 91, 4);
                        player.setY(missles.get(i).getY());
                        missles.remove(i);
                        player.setPlaying(true);
                    }
                }

                //for memory issues we remove the missles going outside of the screen!
                //missed powerups also count on the score, adjustments can be made!
                if (missles.get(i).getX() < -50 && missles.get(i).getID() != 3){
                    missles.remove(i);
                    score++;
                    player.setScore(score);
                    break;
                }

                if ((missles.get(i).getX() < -300 && missles.get(i).getID() == 3)){
                    missles.remove(i);
                    score = score + 5;
                    player.setScore(score);
                    break;
                }
            }

        }if (!player.isPlaying()){

            //check whether the player starts again. the first time we lose the player
            //will definitely get into this loop
            if (!freeze){

                //freeze game. in this freezed state we want to do a few things.
                //we want to detonate the bomb
                // stop the music
                // and play the explosionsound,
                newgame = false;
                freeze = true;
                deadTime = System.nanoTime();
                explosion.setY(player.getY() - 120);
                detonate = true;
                playSound = true;

                //check wheter de music has been played and if the music is playing.
                if(musicPlayed) {
                    coolMusic.pause();
                    coolMusic.seekTo(0);
                }
            }
            deadTimepassed = (System.nanoTime() - deadTime)/1000000;
            explosion.update();

            if (playSound && saveState.getInt("explosionSound",1) == 1){
                while(counter<1){
                    Log.d(">>>>>>", "boomsound");
                    boomSound.start();
                    soundPlayed = true;
                    counter++;
                }
            }

            if (playSound && saveState.getInt("explosionSound",1) == 0){
                Log.d(">>>>>>", "hi");
                if(soundPlayed){
                    counter = 0;
                    soundPlayed = false;
                }
            }

            //here the game freezes before starting a new game
            if ((deadTimepassed > 2000) && (!newgame)){
                newGame();
            }
        }
    }
    //this function checks if rockets and missles collide!
    public boolean touch(Object missle, Object hero){

        if (Rect.intersects(hero.getRectangle(), missle.getRectangle())){
            return true;
        }
        return false;
    }

    //drawing method
    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(scaledbmp, 0, 0, null);
        player.draw(canvas);

        for (Missles m : missles){
            m.draw(canvas);
        }

        if (detonate){
            explosion.draw(canvas);
        }

        textView(canvas);
    }

    //paint method for texts
    public void textView(Canvas canvas){
        text = new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(30);
        canvas.drawText("SCORE: " + player.getScore(),WIDTH - 50,HEIGHT - 70,text);
        canvas.drawText("BEST: " + saveState.getInt("new best",0), WIDTH - 50, HEIGHT - 20, text);

        //this is written in the beginning state
        if ((!player.isPlaying()) && (freeze) && (newgame)){
            canvas.drawText("TOUCH THE SCREEN TO START THE GAME",WIDTH/2,HEIGHT/2,text);
            canvas.drawText("PRESS TO GO UP, RELEASE TO GO DOWN",WIDTH/2,(HEIGHT/2)+70,text);
            canvas.drawText("SOUND CAN BE ADJUSTED IN THE MENU",WIDTH/2,(HEIGHT/2)+420,text);
        }

        //this is writen when player and missle collide
        if ((!player.isPlaying()) && (freeze) && (!newgame)){
            suckText = new Paint();
            suckText.setColor(Color.RED);
            suckText.setTextSize(30);

            bestText = new Paint();
            bestText.setColor(Color.GREEN);
            bestText.setTextSize(30);
            canvas.drawText("SCORE: " + player.getScore(), WIDTH - 50, HEIGHT - 70,text);

            if (player.getScore() <= saveState.getInt("new best",0)){
                canvas.drawText("YOU SUCK!",WIDTH/2,HEIGHT/2,suckText);
            }

            if (player.getScore() > saveState.getInt("new best",0)){
                canvas.drawText("NEW BEST!",WIDTH/2,HEIGHT/2,bestText);
            }
        }
    }

    //starts a new game
    public void newGame(){

        //check if the music has been played and is playing
        if (saveState.getInt("boomSound", 1) == 1 && musicPlayed){
            boomSound.pause();
            boomSound.seekTo(0);
        }

        if (player.getScore() > saveState.getInt("new best",0)){
            best = player.getScore();
            SharedPreferences.Editor editor = saveState.edit();
            editor.putInt("new best",best);
            editor.commit();
        }
        missles.clear();
        player.resetScore();
        player.setY(HEIGHT / 2);
        player = new Hero(BitmapFactory.decodeResource(getResources(),
                R.mipmap.helicopter_metalslug), 146, 91, 4);

        score = 0;
        counter = 0;
        musicCounter = 0;
        numMissles = 0;

        newgame = true;
        soundPlayed = false;
        musicPlayed = false;
        detonate = false;
        playSound = false;
        playMusic = false;
    }
    
    //----------------------------------------Helper functions--------------------------------------

    //makes missles in a list
    public void makeMissles(){
        numMissles ++;
        int randomNum = random.nextInt(HEIGHT - 70);

        //once in 10 missles, make a big missle
        if (numMissles % 10 == 0 && numMissles != 0){
            missle = new Missles(BitmapFactory.decodeResource(getResources(),
                    R.mipmap.large_missles), WIDTH + 10, randomNum, 270, 68, 12);
            missle.setID(3);
            missles.add(missle);

            //once in 25 missles, make a random powerup for demo purposes, set this on 75 for the
            //better game experience
        }

        if (numMissles % 25 == 0 && numMissles !=0){
            int randompowerup = random.nextInt(2);

            //could use switch case, but i prefer this
            if (randompowerup == 0){
                missle = new Missles(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.chicken_powerup), WIDTH + 10, randomNum, 46, 40, 1);
                missle.setID(1);
                missles.add(missle);
            }

            if (randompowerup == 1){
                missle = new Missles(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.helicopterpowerup), WIDTH + 10, randomNum, 42, 40, 1);
                missle.setID(2);
                missles.add(missle);
            }

            //make small missles in all other cases
        }

        if (numMissles % 10 !=0 && numMissles % 25 != 0){
            missle = new Missles(BitmapFactory.decodeResource(getResources(),
                    R.mipmap.missile), WIDTH + 10,randomNum, 90, 30, 13);
            missle.setID(0);
            missles.add(missle);
        }
    }

    //function that adjusts speed of making missles
    public int scoreDifficulty(){

        if (score < 550){
            difficultyScore = score;

        } else{
            difficultyScore = 550;
        }
        return difficultyScore;
    }

    public void playMusic(){

        //Here we play the music and remember that we played it with musicPlayed boolean
        if (playMusic && saveState.getInt("backgroundSound",1)==1 && player.isPlaying()){
            while (musicCounter < 1){
                coolMusic.setLooping(true);
                coolMusic.start();
                musicPlayed = true;
                musicCounter++;
            }
        }

        //here we say, pause de music if you remembered we played it, else do nothing
        if (playMusic && saveState.getInt("backgroundSound", 1)==0 && player.isPlaying()){
            if (musicPlayed){
                coolMusic.pause();
                coolMusic.seekTo(0);
                musicCounter = 0;
                musicPlayed = false;
            }
        }
    }
}
