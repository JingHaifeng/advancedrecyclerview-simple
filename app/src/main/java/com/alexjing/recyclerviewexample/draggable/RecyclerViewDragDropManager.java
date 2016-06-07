package com.alexjing.recyclerviewexample.draggable;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-07
 * @time: 14:03
 */
public class RecyclerViewDragDropManager implements DraggableConstants {

    public static final Interpolator DEFAULT_ITEM_SETTLE_BACK_INTO_PLACE_ANIMATION_INTERPOLATOR =
            new DecelerateInterpolator();

    public interface OnDragEventListener {

        void onDragStarted(int position);

        void onDragPositionChanged(int fromPosition, int toPosition);

        void onDragFinished(int fromPosition, int toPosition, boolean result);

        void onDragMoveDistanceUpdated(int offsetX, int offsetY);
    }

    private RecyclerView mRecyclerView;
    // 监听 recycler view 的触碰事件
    private RecyclerView.OnItemTouchListener mInternalOnItemTouchListener;
    // 监听 recycler view 的滚动事件
    private RecyclerView.OnScrollListener mInternalOnScrollListener;

    public RecyclerViewDragDropManager() {
        mInternalOnItemTouchListener = new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };

        mInternalOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        };
    }
}
