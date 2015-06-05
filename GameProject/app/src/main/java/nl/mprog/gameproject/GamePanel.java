package nl.mprog.gameproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by k on 3-6-2015.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private Sprite sprite;
    private SurfaceHolder holder;
    private GamePanelThread thread;
    private Bitmap bmp;
    private Bitmap background;
    private Bitmap scaledbmp;
    private int xposition = 0;
    private int xspeed = 1;

    public GamePanel(Context context,int resource) {
        super(context);
        //TODO Auto generated constructor stub
        // unpacking sprites
        bmp = BitmapFactory.decodeResource(getResources(), resource);
        sprite = new Sprite(this,bmp);

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
        background = BitmapFactory.decodeResource(getResources(), R.mipmap.cool_background);
        float scale = (float)background.getHeight()/(float)getHeight();
        int newWidth = Math.round(background.getWidth()/scale);
        int newHeight = Math.round(background.getHeight()/scale);
        scaledbmp = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);

        // starting the thread
        thread.setRunning(true);
        thread.start();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //Drawing the background and sprites as it is a blackboard.
        canvas.drawBitmap(scaledbmp,0,0,null);
        sprite.onDraw(canvas);
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
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}



