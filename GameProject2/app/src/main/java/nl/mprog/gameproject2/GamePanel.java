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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    //soundfragments and savestate
    private MediaPlayer coolMusic;
    private MediaPlayer boomSound;
    private SharedPreferences saveBest;

    //here we just make the width and height, adjust this if playing on another mobile!
    public static final int WIDTH = 900;
    public static final int HEIGHT = 1800;

    //the timers
    private long misslesStarttime;
    private long misslesTimepassed;
    private long deadTime;
    private long deadTimepassed;
    private long soundTime;
    private long soundTimeElapsed;

    //the Bitmaps of my game
    private Bitmap background;
    private Bitmap scaledbmp;

    // the classes
    private GameThread thread;
    private Hero player;
    private Hero error;
    private Missles missle;
    private Explosion explosion;

    //the booleans
    private boolean playMusic = false;
    private boolean playSound = false;
    private boolean startAgain = true;
    private boolean newgame = true;
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


    public GamePanel(Context context,SharedPreferences data, MediaPlayer explosionSound,MediaPlayer backgroundSound) {
        super(context);

        //importing data from mainactivity class
        coolMusic = backgroundSound;
        boomSound = explosionSound;
        saveBest = data;

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        //making our thread for the game loop
        thread = new GameThread(getHolder(), this);

        //this is used so the program can handle things better
        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    // something i saw people doing on the internet, still need to understand this though.
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //making the scaled background
        background = BitmapFactory.decodeResource(getResources(), R.mipmap.cool_background);
        float scale = (float) background.getHeight() / (float) getHeight();
        int newWidth = Math.round(background.getWidth() / scale);
        int newHeight = Math.round(background.getHeight() / scale);
        scaledbmp = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);

        //making the player and explosion
        player = new Hero(BitmapFactory.decodeResource(getResources(), R.mipmap.helicopter_metalslug), 146, 91, 4);
        error = player;
        error.setX(player.getX()-10);
        explosion = new Explosion(BitmapFactory.decodeResource(getResources(), R.mipmap.explosion), 100, 256, 20);
        explosion.setX(player.getX());

        //starting the gameloop
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //what happens when the player touches the screen!
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            //boolean check for when player is playing, if he isn't the chopper does nothing.
            //we see that the chopper only hangs still if all these booleans are true!
            if ((!player.isPlaying()) && (newgame) && (startAgain)) {
                player.setPlaying(true);

            //here we see what happens when the player is playing. we set the detonation on true
            //but draw the detonation in a later stadium of the game! The startAgain is set false
            //so we can freeze the game in a later stadium
            } else if(player.isPlaying()) {
                //if (detonate) {
                //    detonate = false;
                //}
                player.setUp(true);
                startAgain = false;
            }
            return true;
        }

        //what happens when the player is pressing the screen, note that we want to start
        //playing the music!
        if (event.getAction() == MotionEvent.ACTION_UP) {
            player.setUp(false);
            playMusic = true;
            //if (detonate) {
            //    detonate = false;
            //}
            startAgain = false;
            return true;
        }
        return super.onTouchEvent(event);
    }

    //this methods updates the sprites while going through the game,
    public void update() {
        //first we check wheter the music must be played. This while-loop is a neat way of saying
        //play the music once and not infitinite many times!
        if(playMusic) {
            while (musicCounter < 1) {
                coolMusic.setLooping(true);
                coolMusic.start();
                musicCounter++;
            }
        }

        if (player.isPlaying()) {

            //set the detonation on false again, so we won't get infinite many drawn explosions
            //detonate = false;

            //updating the player
            player.update();

            // check how many time has gone past and making a new missle for
            // each unit of time!
            long misslesTimepassed = (System.nanoTime() - misslesStarttime) / 1000000;

            //I like this time so far, i tried to let the game make more missles if the score
            //increases. One can adjust the scoreDifficulty() function for a preferred difficulty-
            //setting.
            if (misslesTimepassed > 750 - scoreDifficulty()) {
                numMissles ++;
                int randomNum = random.nextInt(HEIGHT - 130);

                //once in 10 missles, make a big missle
                if(numMissles % 10 == 0 && numMissles != 0) {

                    missle = new Missles(BitmapFactory.decodeResource(getResources(), R.mipmap.large_missles), WIDTH + 10, randomNum, 270, 68, 12);
                    missle.setID(3);
                    missles.add(missle);

                //once in 50 missles, make a random powerup
                }if(numMissles % 25 == 0 && numMissles !=0){

                    int randompowerup = random.nextInt(2);
                    if(randompowerup == 0){
                        missle = new Missles(BitmapFactory.decodeResource(getResources(), R.mipmap.chicken_powerup), WIDTH + 10, randomNum, 46, 40, 1);
                        missle.setID(1);
                        missles.add(missle);
                    } if(randompowerup == 1) {
                        missle = new Missles(BitmapFactory.decodeResource(getResources(), R.mipmap.helicopterpowerup), WIDTH + 10, randomNum, 42, 40, 1);
                        missle.setID(2);
                        missles.add(missle);
                    }
                //make small missles in all other cases
                } else{
                    missle = new Missles(BitmapFactory.decodeResource(getResources(), R.mipmap.missile), WIDTH + 10,randomNum, 90, 30, 13);
                    missle.setID(0);
                    missles.add(missle);
                }
                //don't forget to reset your timer
                misslesStarttime = System.nanoTime();
            }

            // Updating the missles in our list, this next algoritm just makes a list of missles and
            // removes them if they fall of our screen!
            for (int i = 0; i < missles.size(); i++) {

                //can make a function out of this, Adjust missleSpeed when missles are made.
                if(missles.get(i).getmissleSpeed()<50) {
                    missles.get(i).setmisslesSpeed((long) (missles.get(i).getmissleSpeed() + (score / (float) 100)));
                }else{
                    missles.get(i).setmisslesSpeed((long) 50);
                }
                //draw the missle
                missles.get(i).update();
                //if the missles and the player touch, the player dies and the game is over if the
                //ID is a rocket, but proceeds if the ID is a powerup
                if (touch(missles.get(i), player)) {
                    if (missles.get(i).getID() == 0 || missles.get(i).getID() == 3) {
                        //we could remove the missle here but i chose not to
                        player.setPlaying(false);
                        break;

                    }
                    if (missles.get(i).getID() == 1) {
                        player = new Hero(BitmapFactory.decodeResource(getResources(), R.mipmap.saucer), 114, 76, 12);
                        player.setY(missles.get(i).getY());
                        missles.remove(i);
                        player.setPlaying(true);

                    }
                    if (missles.get(i).getID() == 2) {
                        player = new Hero(BitmapFactory.decodeResource(getResources(), R.mipmap.helicopter_metalslug), 146, 91, 4);
                        player.setY(missles.get(i).getY());
                        missles.remove(i);
                        player.setPlaying(true);
                    }
                }
                //for memory issues we remove the missles going outside of the screen!
                //missed powerups also count on the score, adjustments can be made!
                if (missles.get(i).getX() < -50 && missles.get(i).getID() != 3) {
                    missles.remove(i);
                    score++;
                    player.setScore(score);
                    break;
                }
                if ((missles.get(i).getX() < -300 && missles.get(i).getID() == 3)) {
                    missles.remove(i);
                    score = score + 5;
                    player.setScore(score);
                    break;
                }
            }

        }if(!player.isPlaying()) {
            //check whether the player starts again. the first time we lose the player
            //will definitely get into this loop, remember the startAgain booleans which
            //we all set to false?
            if(!startAgain){
                //this part is tricky, we set the boolean newgame on false and startagain on true so
                //the gamestate will freeze. in this freezed state we want to do a few things.
                //we want to detonate the bomb,stop the music and play the explosionsound, which all
                //happens under this if-statement.
                newgame = false;
                startAgain = true;
                deadTime = System.nanoTime();
                explosion.setY(player.getY() - 120);
                detonate = true;
                playSound = true;
                coolMusic.pause();
                coolMusic.seekTo(0);
            }
            deadTimepassed = (System.nanoTime() - deadTime)/1000000;
            explosion.update();
            if(playSound){
                while(counter<1){
                    boomSound.start();
                    counter++;
                }
            }
            //here the game freezes
            if((deadTimepassed > 2000) && (!newgame)){
                newGame();
            }
        }
    }

    //this function checks if rockets and missles collide!
    public boolean touch(Object missle, Object hero) {
        if (Rect.intersects(hero.getRectangle(), missle.getRectangle())) {
            return true;
        }
        return false;
    }

    //drawing method
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(scaledbmp, 0, 0, null);
        player.draw(canvas);
        for (Missles m : missles) {
            m.draw(canvas);
        }
        if(detonate) {
            explosion.draw(canvas);
        }
        textView(canvas);
    }

    //paint method for texts
    public void textView(Canvas canvas){
        text = new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(30);
        canvas.drawText("SCORE: " + player.getScore(),100,HEIGHT - 50,text);
        canvas.drawText("BEST: " + saveBest.getInt("new best",0), WIDTH - 50, HEIGHT - 50, text);

        //this is written in the beginning state
        if((!player.isPlaying()) && (startAgain) && (newgame)){
            canvas.drawText("Touch the screen to start the game",WIDTH/2,HEIGHT/2,text);
        }

        //this is writen when player and missle collide
        if((!player.isPlaying()) && (startAgain) && (!newgame)){
            suckText = new Paint();
            suckText.setColor(Color.RED);
            suckText.setTextSize(30);

            bestText = new Paint();
            bestText.setColor(Color.GREEN);
            bestText.setTextSize(30);
            canvas.drawText("SCORE: " + player.getScore(),100,HEIGHT - 50,text);

            if(player.getScore() <= saveBest.getInt("new best",0)){
                canvas.drawText("YOU SUCK!",WIDTH/2,HEIGHT/2,suckText);

            }if(player.getScore() > saveBest.getInt("new best",0)){
                canvas.drawText("NEW BEST!",WIDTH/2,HEIGHT/2,bestText);
            }
        }
    }

    //function that adjusts speed of making missles
    public int scoreDifficulty() {
        if (score < 550) {
            difficultyScore = score;
        } else {
            difficultyScore = 550;
        }
        return difficultyScore;
    }

    //starts a new game
    public void newGame(){

        boomSound.pause();
        boomSound.seekTo(0);

        missles.clear();
        if(player.getScore() > saveBest.getInt("new best",0)) {
            best = player.getScore();
            SharedPreferences.Editor editor = saveBest.edit();
            editor.putInt("new best",best);
            editor.commit();
        }
        player.resetScore();

        score = 0;
        counter = 0;
        musicCounter = 0;

        player.setY(HEIGHT/2);

        newgame = true;
        detonate = false;
        playSound = false;
        playMusic = false;

        player = new Hero(BitmapFactory.decodeResource(getResources(), R.mipmap.helicopter_metalslug), 146, 91, 4);
    }
}
