package nl.mprog.gameproject2;

import android.graphics.Rect;

//Every class can use methods from this class, that's the reason we make it abstract
public abstract class Object {
    protected int ID = 0;
    protected int x;
    protected int y;
    protected int y_move;
    protected int width;
    protected int height;

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y) {
            this.y = y;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public int getX(){
            return x;
    }
    public int getY() {
            return y;
    }
    public int getID(){ return ID;}

    public int getHeight() {
            return height;
    }
    public int getWidth() {
        return width;
    }

    //all these previous functions are pretty obvious, this one however gives one the recangle
    //that the sprite covers.
    public Rect getRectangle() {
        return new Rect(x, y, x+width, y+height);
    }

}

