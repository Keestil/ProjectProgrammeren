package nl.mprog.gameproject2;

import android.graphics.Bitmap;

public class Spriteslide {

    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long waitTime;

    //setting up the number of frames i want to play and starting a timer
    //for the update method
    public void setFrames(Bitmap[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    //This is the time we wait between playing frames!
    public void setWaitTime(long w){
        waitTime = w;
    }

    //Setting the frame we are at
    public void setFrame(int i){
        currentFrame = i;
    }

    public void update() {
        //If the time passed our waitTime we want to go to the next frame and start the timer again
        long timePassed = (System.nanoTime()-startTime)/1000000;
        if(timePassed>waitTime) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        //Here we want to start at the first frame again
        if(currentFrame == frames.length){
            currentFrame = 0;
        }
    }

    //These next two functions are pretty obvious
    public Bitmap getImage(){
        return frames[currentFrame];
    }

//    public int getFrame(){
//        return currentFrame;
//    }
}
