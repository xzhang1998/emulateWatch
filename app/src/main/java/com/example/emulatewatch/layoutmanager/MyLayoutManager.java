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

    private static final int VISIBLE_WIDTH = 420;
    private static final int VISIBLE_HEIGHT = 440;
    private static final int MAX_ITEM_IN_SCREEN = 27;
    private static final Position centerPoint = new Position(0,VISIBLE_WIDTH/2,VISIBLE_HEIGHT/2,1f);

    private RecyclerView.Recycler mRecycler;
    private static float mRectLength;
    private static Position[] layoutList;

    private int mOffsetX, mOffsetY;


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
        heightSpec = View.MeasureSpec.makeMeasureSpec(VISIBLE_HEIGHT, View.MeasureSpec.EXACTLY);

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

        for (int i=0; i<needLayoutItems.size();i++) {
            Position tmp = needLayoutItems.get(i);
            item = recycler.getViewForPosition(tmp.index);
            addView(item);
            measureChild(item, 0, 0);

            //计算左和上边界
            x = (int) tmp.x - mOffsetX - getDecoratedMeasuredWidth(item) / 2;
            y = (int) tmp.y - mOffsetY - getDecoratedMeasuredHeight(item) / 2;

//            Log.d("Offset","x:"+mOffsetX+"y:"+mOffsetY);
            layoutDecorated(item, x, y, x + getDecoratedMeasuredWidth(item), y + getDecoratedMeasuredHeight(item));

//            Log.d("position","left: "+mOffsetX+"bottom: "+mOffsetY+"right: "+mOffsetX+"bottom: "+mOffsetY);
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
        mRectLength = getDecoratedMeasuredWidth(item); //直径

        float polygonLength = mRectLength/(float) Math.sqrt(3);
        float[][] direction = new float[][]{
                {-polygonLength,mRectLength},//左下
                {-polygonLength,-mRectLength}, //左上
                {polygonLength,-mRectLength}, //右上
                {polygonLength,mRectLength}, //右下
                {-2*polygonLength,0}, //左
                {2*polygonLength,0}, //右
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
        for(Position tmp: layoutList){
            float xNow = tmp.x-mOffsetX;
            float yNow = tmp.y-mOffsetY;
            if(Math.abs(xNow-centerPoint.x)<VISIBLE_WIDTH/2 && Math.abs(yNow-centerPoint.y)<VISIBLE_HEIGHT/2){
                result.add(tmp);
            }
        }
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        mOffsetX+=dx;
        relayoutChildren(recycler, state);
        return dx;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        mOffsetY+=dy;
        relayoutChildren(recycler, state);
        return dy;
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
