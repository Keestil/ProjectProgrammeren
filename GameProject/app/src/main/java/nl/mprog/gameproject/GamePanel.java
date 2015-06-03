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

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    SurfaceHolder holder;
    GamePanelThread thread;

    public GamePanel(Context context) {
        super(context);
        //TODO Auto generated constructor stub
        holder = getHolder();
        holder.addCallback(this);
        thread = new GamePanelThread();
        setFocusable(true);
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }
}

