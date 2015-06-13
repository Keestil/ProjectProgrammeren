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
        speed = 7;


        Bitmap[] image = new Bitmap[frames];
        spritesheet = bmp;

        for(int i = 0; i<image.length;i++) {
            image[i] = Bitmap.createBitmap(spritesheet, 0, i*height, width, height);
        }

        animation.setFrames(image);
        animation.setWaittime(100);

    }
    public void update() {
        x-=speed;
        animation.update();
    }
    public void draw(Canvas canvas) {
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch(Exception e){Log.d(">", "except e" + e);}
    }

    @Override
    public int getWidth() {
        //offset slightly for more realistic collision detection
        return width-10;
    }

}
