package nl.mprog.gameproject2;

import android.graphics.Bitmap;

public class Spriteslide {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long waitTime;
    private boolean playedOnce;

    //setting up the number of frames i want to play and starting a timer
    //for the update method
    public void setFrames(Bitmap[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    //This is the time we wait between playing frames!
    public void setWaittime(long w){
        waitTime = w;
    }

    //Setting the frame we are at
    public void setFrame(int i){
        currentFrame= i;
    }

    public void update() {
        long timePassed = (System.nanoTime()-startTime)/1000000;

        //If the time passed our delaytime we want to go to the next frame and start the timer again
        if(timePassed>waitTime) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        //Here we want to
        if(currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }
    }
    public Bitmap getImage(){
        return frames[currentFrame];
    }
    public int getFrame(){
        return currentFrame;
    }
    public boolean playedOnce(){
        return playedOnce;
    }
}
