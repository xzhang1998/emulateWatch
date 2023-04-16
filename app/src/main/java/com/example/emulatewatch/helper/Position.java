package com.example.emulatewatch.helper;

import android.graphics.PointF;

import java.util.Objects;

public class Position extends PointF {

    //view直径
    private static float M_RECT_LENGTH;
//    public static float[] distScale;

    //Item所对应的索引
    public int index;

//    //被expand时的层数
//    public int level;
//
//    //没调整距离前的坐标
//    private float xParent;
//    private float yParent;


    public Position(int index, float x, float y) { //float x1, float y1, int level
        super(x,y);
        this.index = index;
//        this.level = level;
//        this.xParent=x1;
//        this.yParent=y1;
        this.setmRectLength(100f);
    }

    public void setmRectLength(float mRectLength) {
        this.M_RECT_LENGTH = mRectLength/2;
    }

    @Override
    public boolean equals(Object obj) {
        double distance = getDistance(this,(Position) obj);
        float standard = M_RECT_LENGTH;
        return obj != null && obj instanceof Position ? (distance<standard) : this == obj;
    }

    private double getDistance(Position p1, Position p2){
        float x1 = p1.x;
        float y1 = p1.y;
        float x2 = p2.x;
        float y2 = p2.y;

        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    @Override
    public int hashCode() {
//        float xCompare = (x-xParent)/distScale[this.level]+xParent;
//        float yCompare = (y-yParent)/distScale[this.level]+yParent;
        return ((Integer) ((int)Math.round(x))).hashCode()
                +((Integer)((int)Math.round(y))).hashCode();
    }

    @Override
    public String toString() {
        return "Position{" +
                "index=" + index +
                ", x=" + x +
                ", y=" + y +
//                ", distScale=" + distScale[level] +
                '}';
    }
}
