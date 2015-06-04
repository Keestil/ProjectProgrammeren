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
        bmp = BitmapFactory.decodeResource(getResources(), resource);
        sprite = new Sprite(this,bmp);
        holder = getHolder();
        holder.addCallback(this);
        thread = new GamePanelThread(holder, this);
        setFocusable(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        background = BitmapFactory.decodeResource(getResources(), R.mipmap.cool_background);
        float scale = (float)background.getHeight()/(float)getHeight();
        int newWidth = Math.round(background.getWidth()/scale);
        int newHeight = Math.round(background.getHeight()/scale);
        scaledbmp = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);

        thread.setRunning(true);
        thread.start();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(scaledbmp,0,0,null);
        sprite.onDraw(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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



