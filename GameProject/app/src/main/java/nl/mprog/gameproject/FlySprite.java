package nl.mprog.gameproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.animation.Animation;

/**
 * Created by k on 10-6-2015.
 */
public class FlySprite extends Object{
    private GamePanel game;
    private Bitmap sprite;
    private int score;
    private boolean up;
    private boolean playing;
    private Animation animation;
    private long startTime;

    public Player(Bitmap bmp, int w, int h, int frames){
        x = 100;
        y = game.getHeight()/2;
        score = 0;
        height = h;
        width = w;

        Bitmap[] sheet = new Bitmap[frames];

        for(int i = 0; i< sheet.length; i++){
            sheet[i] = Bitmap.createBitmap(sprite,i*width,0,width,height);

        }
        animation = new Animation();
        animation.setFrames(sheet);
        animation.setDelay(100);
        startTime = System.nanoTime();
    }

    public void setUp(boolean b){
        up = b;
    }
    public void update(){
        long elapsed = (System.nanoTime() - startTime)/1000000;
        if(elapsed>100){
            score ++;
            startTime = System.nanoTime();

        }
        animation.update();
    }

    public void onDraw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
    public int getScore(){
        return score;
    }
    public boolean isPlaying(){
        return playing;
    }
    public boolean setPlaying(boolean b){
        return playing = b;
    }
    public void resetScore(){
        score = 0;
    }
}
