package com.example.emulatewatch.helper;

public class ScaleRatio { //not used
    private static final float MAX = 1f;
    private static final float SECOND = 0.8f;
    private static final float MIN = 0.2f;

    private static final int VISIBLE_WIDTH = 420;
    private static final int VISIBLE_HEIGHT = 440;


    public static float getPositionScale(float x, float y){
        float xDist = Math.abs(x-VISIBLE_WIDTH/2);
        float yDist = Math.abs(y-VISIBLE_HEIGHT/2);

        if(xDist<= 10 && yDist <=15){
            return MAX;
        } else if(xDist<= 160 && yDist <=170){
            return SECOND;
        } else {
            return MIN;
        }
    }
}
