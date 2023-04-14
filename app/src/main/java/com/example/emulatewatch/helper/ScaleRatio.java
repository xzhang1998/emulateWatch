package com.example.emulatewatch.helper;

public class ScaleRatio {
    private static final float MAX = 1f;
    private static final float SECOND = 0.6f;
    private static final float MIN = 0.2f;

    private static final int VISIBLE_WIDTH = 380;
    private static final int VISIBLE_HEIGHT = 440;


    public static float getPositionScale(float x, float y){
        float xDist = Math.abs(x-VISIBLE_WIDTH/2);
        float yDist = Math.abs(x-VISIBLE_HEIGHT/2);

        if(xDist<= 150 || yDist <=155){
            return MAX;
        } else if(xDist<= 250 || yDist <=250){
            return SECOND;
        } else {
            return MIN;
        }
    }
}
