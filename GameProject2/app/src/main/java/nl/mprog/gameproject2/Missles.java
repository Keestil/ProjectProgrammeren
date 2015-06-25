package nl.mprog.gameproject2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

public class Missles extends Object{

    private int score;
    private long speed;
    private Random rand = new Random();
    private Spriteslide animation = new Spriteslide();
    private Bitmap spritesheet;

    //making missles
    public Missles(Bitmap bmp, int x, int y, int w, int h,int frames){
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        speed = 20;

        Bitmap[] cropimage = new Bitmap[frames];
        spritesheet = bmp;

        //note that i crop a spritesheet here with x frames where x is an integer
        for (int i = 0; i<cropimage.length;i++){
            cropimage[i] = Bitmap.createBitmap(spritesheet, 0, i*height, width, height);
        }

        //we set the frames and waittime in the animation class
        animation.setFrames(cropimage);
        animation.setWaitTime(5);
    }

    //here we set the speed of the missles and update it in the animation class
    public void update(){
        animation.update();
        x -= speed;
    }

    //draw the animation
    public void draw(Canvas canvas){
            canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    //get the speed of the missles
    public long getmissleSpeed(){
        return speed;
    }

    //set the speed of the missles
    public void setmissleSpeed(long newSpeed){
        speed = newSpeed;
    }

}
