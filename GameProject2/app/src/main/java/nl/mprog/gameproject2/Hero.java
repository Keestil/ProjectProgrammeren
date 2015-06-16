package nl.mprog.gameproject2;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Hero extends Object{
    private GamePanel game;
    private Spriteslide animation = new Spriteslide();
    private Bitmap spritesheet;
    private int score;
    private boolean up;
    private boolean playing;

    public Hero(Bitmap bmp, int w, int h, int frames) {

        x = 100;
        y = game.HEIGHT / 2;
        y_move = 0;
        score = 0;
        height = h;
        width = w;

        // here we are setting up a imagecropper
        Bitmap[] cropimage = new Bitmap[frames];
        spritesheet = bmp;

        // this loop crops the images
        for (int i = 0; i < cropimage.length; i++) {
            cropimage[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(cropimage);
        animation.setWaitTime(10);

    }

    //boolean check for flying.
    public void setUp(boolean b){
        up = b;
    }

    public void update() {
        //updating the animation
        animation.update();

        //Flying up or down
        if(up){
            y_move = -20;
        }if(!up){
            y_move = 20;
        }

        //Checking the bounds
        if (y > game.HEIGHT - 63 - spritesheet.getHeight()-y_move) {
            y_move = 0;
        }
        if (y + y_move < 0) {
            y_move = 0;
        }
        y = y + y_move;
        y_move = 0;
    }

    // here we draw the anymation
    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    public int getScore(){
        return score;
    }

    public void setScore(int newScore){
        score = newScore;
    }

    public boolean isPlaying(){
        return playing;
    }

    public void setPlaying(boolean b){
        playing = b;
    }

    public void resetScore(){
        score = 0;
    }
}
