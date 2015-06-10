package nl.mprog.gameproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


/**
 * Created by k on 3-6-2015.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private long startTime;
    private Sprite sprite;
    private Missles missles;
    private SurfaceHolder holder;
    private GamePanelThread thread;
    private Background background;
    private Bitmap bmp;
    private Bitmap missle;
    private List<Missles> missleslist = new ArrayList<>();
    private Bitmap scaledbmp;
    private int xposition = 0;
    private int xspeed = 1;
    private long missileStartTime;
    private long missileElapsedTime;

    public GamePanel(Context context, int resource) {
        super(context);
        //TODO Auto generated constructor stub
        // unpacking sprites and missles
        background = new Background(BitmapFactory.decodeResource(getResources(),R.mipmap.cool_background));

        // Making the surfaceholder and the thread for the gameloop
        holder = getHolder();
        holder.addCallback(this);
        thread = new GamePanelThread(holder, this);

        // This statement improves the performance
        setFocusable(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //Here i just creat the scaled background
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.cool_background);
        float scale = (float) bmp.getHeight() / (float) getHeight();
        int newWidth = Math.round(bmp.getWidth() / scale);
        int newHeight = Math.round(bmp.getHeight() / scale);
        scaledbmp = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, true);
        createSprites();

        // starting the thread
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // the purpose here is to tell the thread to shut down.
        boolean retry = false;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Drawing the background and sprites as it is a blackboard.
        canvas.drawBitmap(scaledbmp, 0, 0, null);
        //       sprite.onDraw(canvas);
        //       missileStartTime = System.nanoTime();
        //for (int i = missleslist.size() - 1; i >= 0; i--) {
        //    missleslist.get(i).onDraw(canvas);
        //}

        //as long as you have missiles, it'll try to fire
        //       while(missleslist.size() > 0) {
        //           if ((System.nanoTime() - missileStartTime)  >= 1000000){
        //              missleslist.get(0).onDraw(canvas);
        //               missleslist.remove(0);
        //               missileStartTime = System.nanoTime();
        //           }
        //       }
        //   }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            sprite.up = true;
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            sprite.up = false;
            return true;
        }


        return super.onTouchEvent(event);
    }

    public void update(){
        background.update();

    }

    private void createSprites() {
        missleslist.add(createSprite(R.mipmap.angle_sprite));
        missleslist.add(createSprite(R.mipmap.monster_girl_sprite));
        missleslist.add(createSprite(R.mipmap.flyingwoman_sprite));
        missleslist.add(createSprite(R.mipmap.angle_sprite));
        missleslist.add(createSprite(R.mipmap.monster_girl_sprite));
        missleslist.add(createSprite(R.mipmap.flyingwoman_sprite));
        missleslist.add(createSprite(R.mipmap.angle_sprite));
        missleslist.add(createSprite(R.mipmap.monster_girl_sprite));
        missleslist.add(createSprite(R.mipmap.flyingwoman_sprite));
    }

    private Missles createSprite(int resource) {
        Bitmap missle = BitmapFactory.decodeResource(getResources(), resource);
        return new Missles(this,missle);
    }
}



