package nl.mprog.gameproject2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    //here we just make the width and height, adjust this if playing on another mobile!
    public static final int WIDTH = 900;
    public static final int HEIGHT = 1800;

    //the Timers
    private long misslesStarttime;
    private long misslesTimepassed;

    //the Bitmaps of my game
    private Bitmap background;
    private Bitmap scaledbmp;

    SharedPreferences saveBest;
    // the classes
    private GameThread thread;
    private Hero player;
    private Missles missle;

    // the rest
    private int best;
    private int score = 0;
    private ArrayList<Missles> missles = new ArrayList<Missles>();
    private boolean newgame = false;
    private Random random = new Random();
    private Paint text;

    public GamePanel(Context context,SharedPreferences data) {
        super(context);

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

        // Making the background
        background = BitmapFactory.decodeResource(getResources(), R.mipmap.cool_background);
        float scale = (float) background.getHeight() / (float) getHeight();
        int newWidth = Math.round(background.getWidth() / scale);
        int newHeight = Math.round(background.getHeight() / scale);
        scaledbmp = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);

        //making the player
        player = new Hero(BitmapFactory.decodeResource(getResources(), R.mipmap.helicopter), 70, 40, 3);

        //starting the gameloop
        thread.setRunning(true);
        thread.start();

    }

    //this methods updates the sprite while going through the gameloop,
    // hardest function of all in my opinion.
    public void update() {

        if (player.isPlaying()) {

            //updating the player
            player.update();

            // check how many time has gone past and making a new missle for
            // each unit of time!

            //System.out.println("Making missles");
            long misslesTimepassed = (System.nanoTime() - misslesStarttime) / 1000000;

            //I like this time so far, if one wants the rockets to go slower/faster adjust this time!
            if (misslesTimepassed > 2000) {
                int randomNum = random.nextInt(HEIGHT - 130);
                missles.add(new Missles(BitmapFactory.decodeResource(getResources(), R.mipmap.missile), WIDTH + 10,randomNum, 45, 15, 13));
                misslesStarttime = System.nanoTime();
            }

            // Updating the missles in our list, this next algoritm just makes a list of missles and
            // removes them if they fall of our screen!
            for (int i = 0; i < missles.size(); i++) {
                missles.get(i).update();

                //If the missles and the player touch, the player dies and the game is over
                if (touch(missles.get(i), player)) {
                    missles.remove(i);
                    player.setPlaying(false);
                    break;
                }
                //for memory issues we remove the missles going outside of the screen!
                if (missles.get(i).getX() < -50) {
                    missles.remove(i);
                    score ++;
                    player.setScore(score);
                    break;
                }
                // Does not work yet don't know why
                if(getpoint(missles.get(i), player)){
                    score ++;
                    player.setPlaying(false);
                    break;
                }
            }

        // Here one starts a new game if the player is finished with playing!
        } if(!player.isPlaying()){
            newGame();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //What happens when the player touches the screen!

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            //boolean check for when player is playing, if he isn't the chopper does nothing
            if (!player.isPlaying()) {
                player.setPlaying(true);

            } else {
                player.setUp(true);
            }
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    //This function checks if rockets and missles collide!
    public boolean touch(Object missle, Object hero) {
        if (Rect.intersects(hero.getRectangle(), missle.getRectangle())) {
            return true;
        }
        return false;
    }

    //this function does not work yet, don't know why
    public boolean getpoint(Object hero, Object missle){
        if(hero.getX() == missle.getX()){
            return true;
        }
        return false;
    }

    //Drawing method
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(scaledbmp, 0, 0, null);
        player.draw(canvas);
        for (Missles m : missles) {
            m.draw(canvas);
        }
        textView(canvas);
    }

    // Cool paint method for texts
    public void textView(Canvas canvas){
        text = new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(30);
        canvas.drawText("SCORE: " + player.getScore(),100,HEIGHT - 50,text);
        canvas.drawText("BEST: " + saveBest.getInt("new best",0), WIDTH - 50, HEIGHT - 50, text);
        if(player.isPlaying() == false){
            canvas.drawText("Touch the screen to start the game",WIDTH/2,HEIGHT/2,text);
        }
    }

    //Happens when player starts over again.
    public void newGame(){
        missles.clear();
        newgame = true;
        if(player.getScore()>best) {
            best = player.getScore();
            SharedPreferences.Editor editor = saveBest.edit();
            editor.putInt("new best",best);
            editor.commit();
        }
        player.resetScore();
        player.setY(HEIGHT/2);
    }

}
