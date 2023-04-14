package com.example.emulatewatch.layoutmanager;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emulatewatch.helper.Position;
import com.example.emulatewatch.helper.ScaleRatio;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MyLayoutManager extends RecyclerView.LayoutManager {

    private static final int VISIBLE_WIDTH = 600;
    private static final int VISIBLE_WEIGHT = 700;
    private static final int MAX_ITEM_IN_SCREEN = 27;
    private static final Position centerPoint = new Position(0,300f,350f,1f);

    private RecyclerView.Recycler mRecycler;
    private static float mRectLength;
    private static Position[] layoutList;

    private float mOffsetX, mOffsetY;


    public MyLayoutManager() {

    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        mRecycler = recycler;
        detachAndScrapAttachedViews(recycler);
        relayoutChildren(recycler,state);

    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        widthSpec = View.MeasureSpec.makeMeasureSpec(VISIBLE_WIDTH, View.MeasureSpec.EXACTLY);
        heightSpec = View.MeasureSpec.makeMeasureSpec(VISIBLE_WEIGHT, View.MeasureSpec.EXACTLY);

        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return false;
    }



    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.MATCH_PARENT);
    }

    //检查状态，布局&回收item
    private void relayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        List<Position> needLayoutItems = getNeedLayoutItems();
        if (needLayoutItems.isEmpty() || state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        onLayout(recycler, needLayoutItems);
        recycleChildren(recycler);
    }

    //确定item位置和尺寸
    private void onLayout(RecyclerView.Recycler recycler, List<Position> needLayoutItems) {
        int x, y;
        View item;
        int i=0;

//        item = recycler.getViewForPosition(needLayoutItems.get(0).index);
//        addView(item);
//        measureChild(item, 0, 0);
//
//        //计算左和上边界
//        x = (int) needLayoutItems.get(0).x - getDecoratedMeasuredWidth(item) / 2;
//        y = (int) needLayoutItems.get(0).y - getDecoratedMeasuredHeight(item) / 2;
//
//        Log.d("X",""+i++);
//        layoutDecorated(item, x, y, x + getDecoratedMeasuredWidth(item), y + getDecoratedMeasuredHeight(item));
        for (Position tmp : needLayoutItems) {
            item = recycler.getViewForPosition(tmp.index);
            addView(item);
            measureChild(item, 0, 0);

            //计算左和上边界
            x = (int) tmp.x - getDecoratedMeasuredWidth(item) / 2;
            y = (int) tmp.y - getDecoratedMeasuredHeight(item) / 2;

            Log.d("X",""+i++);
            layoutDecorated(item, x, y, x + getDecoratedMeasuredWidth(item), y + getDecoratedMeasuredHeight(item));

            float scale = tmp.fraction;
            item.setScaleX(scale);
            item.setScaleY(scale);

        }
    }

    //屏幕上显示哪些
    private List<Position> getNeedLayoutItems() {
        List<Position> result = new ArrayList<>();
        initLayoutList();
        initNeedLayoutItems(result);
        return  result;
    }

    private void initLayoutList(){
        int itemCount = getItemCount();
        this.layoutList = new Position[itemCount];

        View item = mRecycler.getViewForPosition(0);
        measureChild(item, 0, 0);
        mRectLength = getDecoratedMeasuredWidth(item);

        float polygonLength = 2*mRectLength/(float) Math.sqrt(3);
        float[][] direction = new float[][]{
                {-2*polygonLength,0},
                {2*polygonLength,0},
                {-polygonLength,2*mRectLength},
                {polygonLength,2*mRectLength},
                {-polygonLength,-2*mRectLength},
                {polygonLength,-2*mRectLength}
        };
        Queue<Position> queue = new ArrayDeque<>();
        Set<Position> dedup = new HashSet<>();
        queue.offer(centerPoint);
        int size = 1;
        while (!queue.isEmpty()){
            Position now = queue.poll();
            float xNow = now.x;
            float yNow = now.y;
            this.layoutList[now.index] = now;
            dedup.add(now);
            //六个方向expand，同时去重
            for(float[] dir: direction){
                float xNew = xNow+dir[0];
                float yNew = yNow+dir[1];

                if(size==itemCount){
                    break;
                }

                Position candidate = new Position(size,xNew,yNew);
                if(!dedup.contains(candidate)){
                    queue.offer(candidate);
                    dedup.add(candidate);
                    size++;
                }
            }
        }
    }

    //generate屏幕上需要显示的item
    private void initNeedLayoutItems(List<Position> result) {
        //配合scroll offset 遍历position[]，拿到本次会显示在屏幕上的部分
        float[] offset = getScrollOffset();
        for(Position tmp: layoutList){
            float xNow = tmp.x-offset[0];
            float yNow = tmp.y-offset[1];
            if(Math.abs(xNow-centerPoint.x)<300 && Math.abs(yNow-centerPoint.y)<350){
                result.add(tmp);
            }
        }
    }

    private float[] getScrollOffset() {
        float[] res = new float[2];
        res[0] = mOffsetX;
        res[1] = mOffsetY;
        return res;
    }


    //回收屏幕外需回收的ITEM
    private void recycleChildren(RecyclerView.Recycler recycler) {
        List<RecyclerView.ViewHolder> scrapList = recycler.getScrapList();
        for (int i = 0; i < scrapList.size(); i++) {
            RecyclerView.ViewHolder holder = scrapList.get(i);
            removeView(holder.itemView);
            recycler.recycleView(holder.itemView);
        }
    }

}
