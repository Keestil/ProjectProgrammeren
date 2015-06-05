package nl.mprog.gameproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private int Sprite_Rows = 4;
    private int Sprite_Columns = 3;
    private int xposition = 0;
    private int yposition = 0;
    private int yspeed = 100;
    private GamePanel game;
    private Bitmap sprite;
    private int currentframe = 0;
    private int width;
    private int height;

    public Sprite(GamePanel game, Bitmap bmp) {
        this.game = game;
        this.sprite = bmp;
        this.width = bmp.getWidth() / Sprite_Columns;
        this.height = bmp.getHeight() / Sprite_Rows;
    }

    private void update() {
        //Here we check whether the bitmap touches the bound or not.

        if (yposition > game.getHeight() - height - yspeed) {
            yspeed = 0;
        }
        if (yposition + yspeed < 0) {
            yspeed = 50;
        }
        yposition = yposition + yspeed;
        //xposition = game.getWidth() / 4;
        //yposition = game.getHeight() / 2;
        currentframe = (currentframe + 1) % Sprite_Columns;
    }

    public void onDraw(Canvas canvas) {
        update();
        // Here we cut our bitmap, such that we get the frames from left to right in the columns
        int smallrectX = currentframe * width;

        //choosing the second row of our sprite sheet, quite confusing y-axis though
        int smallrectY = 2 * height;

        // Making two rectangles, the frame of the spritesheet and
        // the position to put it in!
        Rect smallrect = new Rect(smallrectX, smallrectY, smallrectX + width, smallrectY + height);
        Rect position = new Rect(xposition, yposition, xposition + width, yposition + height);

        // making a drawing
        canvas.drawBitmap(sprite, smallrect, position, null);
    }
}

