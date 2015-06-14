package nl.mprog.gameproject2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

public class Missles extends Object{

    private int score;
    private int speed;
    private Random rand = new Random();
    private Spriteslide animation = new Spriteslide();
    private Bitmap spritesheet;

    public Missles(Bitmap bmp, int x, int y, int w, int h,int frames) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        speed = 20;


        Bitmap[] cropimage = new Bitmap[frames];
        spritesheet = bmp;

        for(int i = 0; i<cropimage.length;i++) {
            cropimage[i] = Bitmap.createBitmap(spritesheet, 0, i*height, width, height);
        }

        animation.setFrames(cropimage);
        animation.setWaitTime(100);

    }
    public void update() {
        x -= speed;
        animation.update();
    }
    public void draw(Canvas canvas) {
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch(Exception e){}
    }

    @Override
    public int getWidth() {
        return width-10;
    }

}
