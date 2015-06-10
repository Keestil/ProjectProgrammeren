package nl.mprog.gameproject;

import android.graphics.Rect;

/**
 * Created by k on 10-6-2015.
 */
public abstract class Object {
    protected int x;
    protected int y;
    protected int height;
    protected int width;

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public Rect getRectangle(){
        return new Rect(x,y,x+width,y+width);
    }

}
