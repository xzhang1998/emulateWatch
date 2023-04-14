package com.example.emulatewatch.helper;

import android.graphics.PointF;

import java.util.Objects;

public class Position extends PointF {
    //缩放大小
    public float fraction;

    //Item所对应的索引
    public int index;

    //view直径
    private static float M_RECT_LENGTH;

    public Position(int index, float x, float y) {
        this(index,x,y,ScaleRatio.getPositionScale(x,y));
    }

    public Position(int index, float x, float y, float fraction) {
        super(x,y);
        this.index = index;
        this.fraction = fraction;
        this.setmRectLength(100f);
    }

    public void setmRectLength(float mRectLength) {
        this.M_RECT_LENGTH = mRectLength/2;
    }

    @Override
    public boolean equals(Object obj) {
        double distance = getDistance(this,(Position) obj);
        float standard = M_RECT_LENGTH/2;
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
        return ((Integer)((int)Math.floor(this.x))).hashCode()+((Integer)((int)Math.floor(this.y))).hashCode();
    }

    @Override
    public String toString() {
        return "Position{" +
                "fraction=" + fraction +
                ", index=" + index +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
