package nl.mprog.gameproject;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class GamePanelThread extends Thread {

    private int FPS = 10;
    private boolean running;
    private SurfaceHolder surfaceholder;
    private GamePanel game;

    public GamePanelThread(SurfaceHolder surfaceholder, GamePanel game){

        super();
        this.surfaceholder = surfaceholder;
        this.game = game;

    }
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run(){
        long startTime;
        long sleepTime;
        //tijd van gameloop
        long ticksPS = 1000 / FPS;
        while(running){
            startTime = System.nanoTime();
            Canvas canvas = null;
        try {
            canvas = surfaceholder.lockCanvas();
            synchronized (surfaceholder)
            {
                game.onDraw(canvas);
            }
        } finally {
            if (canvas != null) {
                surfaceholder.unlockCanvasAndPost(canvas);
            }
        sleepTime = ticksPS - (System.nanoTime()- startTime);//laatste deel is hoeveel seconden om 1 loop om te gaan;
        try{
            if(sleepTime>0) {
                this.sleep(sleepTime);
            } else {
                sleep(10);
            }
            } catch (Exception e) {}
        }
    }


    }
}
