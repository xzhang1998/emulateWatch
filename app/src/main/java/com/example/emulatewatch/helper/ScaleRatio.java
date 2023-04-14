package com.example.emulatewatch.helper;

public class ScaleRatio {
    private static final float MAX = 1f;
    private static final float SECOND = 0.8f;
    private static final float MIN = 0.2f;

    private static final float X_CENTER = 300f;
    private static final float Y_CENTER = 350f;

    private static final int VISIBLE_WIDTH = 600;
    private static final int VISIBLE_WEIGHT = 700;


    public static float getPositionScale(float x, float y){
        float xDist = Math.abs(x-X_CENTER);
        float yDist = Math.abs(x-Y_CENTER);

        if(xDist<= 150 && yDist <=175){
            return MAX;
        } else if(xDist<= 250 && yDist <=300){
            return SECOND;
        } else {
            return MIN;
        }
    }
}
