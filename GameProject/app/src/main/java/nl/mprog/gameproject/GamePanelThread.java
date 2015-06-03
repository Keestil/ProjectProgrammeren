package nl.mprog.gameproject;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by k on 3-6-2015.
 */
public class GamePanelThread extends Thread {

    private boolean running;
    private SurfaceHolder surfaceholder;
    private GamePanel game;
    Canvas canvas;

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
        while(running){
            startTime = System.nanoTime();
            canvas = null;
        try{

        }
    }


    }
}
