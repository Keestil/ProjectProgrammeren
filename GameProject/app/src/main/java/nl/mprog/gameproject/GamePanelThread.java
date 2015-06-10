package nl.mprog.gameproject;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class GamePanelThread extends Thread {

    private int FPS = 30;
    private double averagefps;
    private boolean running;
    private SurfaceHolder surfaceholder;
    private GamePanel game;

    public GamePanelThread(SurfaceHolder surfaceholder, GamePanel game) {

        super();
        this.surfaceholder = surfaceholder;
        this.game = game;

    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long startTime;
        long sleepTime;
        //tijd van gameloop
        long ticksPS = 1000 / FPS;
        long averageFPS;
        long timeinMill;
        long totalTime = 0;
        int counter = 0;

        while (running) {
            startTime = System.nanoTime();
            Canvas canvas = null;
            try {
                canvas = surfaceholder.lockCanvas();
                synchronized (surfaceholder) {
                    this.game.update();
                    this.game.onDraw(canvas);
                }
            } catch (Exception e) {

            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceholder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }

            timeinMill = (System.nanoTime() - startTime) / 1000000;
            sleepTime = ticksPS - timeinMill; //laatste deel is hoeveel seconden om 1 loop om te gaan;

            try{
                this.sleep(sleepTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            counter ++;
            if(counter == FPS)
            {
                averageFPS = 1000/((totalTime/counter)/1000000);
                counter =0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
}
