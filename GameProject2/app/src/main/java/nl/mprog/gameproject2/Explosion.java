package nl.mprog.gameproject2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion extends Object{

    private GamePanel game;
    private Spriteslide animation = new Spriteslide();
    private Bitmap spritesheet;

    public Explosion(Bitmap bmp, int w, int h, int frames){
        width = w;
        height = h;

        // here we are setting up a imagecropper
        Bitmap[] cropimage = new Bitmap[frames];
        spritesheet = bmp;

        // this loop crops the images
        for (int i = 0; i < cropimage.length; i++){
            cropimage[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(cropimage);
        animation.setWaitTime(5);
    }

    public void update(){
            animation.update();
    }
    public void draw(Canvas canvas){
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }
}

