package nl.mprog.gameproject2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.MainThread;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1800;
    private Hero player;
    private Bitmap background;
    private Bitmap scaledbmp;
    private GameThread thread;
    private long misslesStarttime;
    private long misslesTimepassed;
    private ArrayList<Missles> missles = new ArrayList<Missles>();
    private boolean newgame = false;
    private Random random = new Random();
    private Paint text;

    public GamePanel(Context context) {
        super(context);

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

        // macking the background
        background = BitmapFactory.decodeResource(getResources(), R.mipmap.cool_background);
        float scale = (float) background.getHeight() / (float) getHeight();
        int newWidth = Math.round(background.getWidth() / scale);
        int newHeight = Math.round(background.getHeight() / scale);
        scaledbmp = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);

        //making the player
        player = new Hero(BitmapFactory.decodeResource(getResources(), R.mipmap.helicopter), 66, 40, 3);

        //starting the gameloop
        thread.setRunning(true);
        thread.start();

    }

    //this methods updates the sprite while going through the gameloop, hardest function of all in my opinion
    public void update() {

        if (player.isPlaying()) {
            //updating the player
            player.update();

            //Check how many time has gone past and making a new missle for
            // each unit of time!

            //System.out.println("Making missles");
            long misslesTimepassed = (System.nanoTime() - misslesStarttime) / 1000000;
            if (misslesTimepassed > 2000) {
                int randomNum = random.nextInt(HEIGHT);
                missles.add(new Missles(BitmapFactory.decodeResource(getResources(), R.mipmap.missile), WIDTH + 10,randomNum, 45, 15, 13));

                misslesStarttime = System.nanoTime();
            }
            //Updating the missles in our list
            for (int i = 0; i < missles.size(); i++) {
                missles.get(i).update();
                //If the missles and the player touch, the player dies and the game is over
                if (touch(missles.get(i), player)) {
                    missles.remove(i);
                    player.setPlaying(false);
                    break;
                }

                //for memory issues we remove the missles going outside of the screen!
                if (missles.get(i).getX() < -100) {
                    missles.remove(i);
                    break;
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //What happens when the player touches the screen!

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

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

    public boolean touch(Object hero, Object missle) {
        if (Rect.intersects(hero.getRectangle(), missle.getRectangle())) {
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(scaledbmp, 0, 0, null);
        player.draw(canvas);
        for (Missles m : missles) {
            m.draw(canvas);
        }
        Textview(canvas);
    }

    public void Textview(Canvas canvas){
        text = new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(30);
        canvas.drawText("BEST: " + 0, WIDTH - 40, HEIGHT - 40, text);
    }

    public void newGame(){
        newgame = true;
    }

}
