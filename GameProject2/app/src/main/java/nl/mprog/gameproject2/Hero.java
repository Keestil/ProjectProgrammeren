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

    public Hero(Bitmap bmp, int w, int h, int frames){

        //setting the starting position of the chopper
        x = 100;
        y = game.SCREENHEIGHT / 2;
        y_move = 0;
        score = 0;
        height = h;
        width = w;

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

        //updating the animation
        animation.update();

        //Flying up or down
        if (up){
            y_move = -20;
        }

        if (!up){
            y_move = 20;
        }

        //checking the bounds, I want the helicopter to stay above the text score, thats why I have
        //an extra 63.
        if (y > game.SCREENHEIGHT - spritesheet.getHeight()-y_move){
            y_move = 0;
        }

        if (y + y_move < 0){
            y_move = 0;
        }
        y = y + y_move;

        //we need to reset the movement, because we have an infinite loop in the gamepanel.
        y_move = 0;
    }

    // here we draw the frames
    public void draw(Canvas canvas) { canvas.drawBitmap(animation.getImage(),x,y,null); }

    //all these functions below are pretty obvious, setUp is to check whether the helicopter must
    //fly or not
    public int getScore() { return score; }
    public boolean isPlaying() { return playing; }

    public void setScore(int newScore) { score = newScore; }
    public void setPlaying(boolean b){
        playing = b;
    }
    public void setUp(boolean b){
        up = b;
    }
    public void resetScore(){
        score = 0;
    }
}
