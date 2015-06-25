package nl.mprog.gameproject2;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread{
    private int FPS = 30;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running = false;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        long targetTime = 1000/FPS;

        while (running){
            startTime = System.nanoTime();
            canvas = null;

            try{

                // try locking the canvas for exclusive pixel editing
                // in the surface
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){

                    //update the gamestate and draw the canvas on the panel
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }catch (Exception e){

            }
            finally{

                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;
            try{

                // send the thread to sleep for a short period, we don't want the game to run
                // quicker then intended.
                this.sleep(waitTime);
            }catch (Exception e){}
        }
    }

    public void setRunning(boolean b) { running=b; }
}






