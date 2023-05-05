package com.example.emulatewatch.helper;

import android.graphics.PointF;

import java.util.Objects;

public class Position extends PointF {


    private static float M_RECT_LENGTH; //item直径
    public int index; //Item所对应的索引


    public Position(int index, float x, float y) {
        super(x,y);
        this.index = index;
        this.setmRectLength(100f);
    }

    public void setmRectLength(float mRectLength) {
        this.M_RECT_LENGTH = mRectLength/2;
    }

    @Override
    public boolean equals(Object obj) {
        double distance = getDistance(this,(Position) obj);
        float standard = M_RECT_LENGTH;
        return obj != null && obj instanceof Position ? (distance<standard) : this == obj; //两点间距离只要小于item直径即视为该位置已被占有
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
        return ((Integer) ((int)Math.round(x))).hashCode()
                +((Integer)((int)Math.round(y))).hashCode();
    }

    @Override
    public String toString() {
        return "Position{" +
                "index=" + index +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
