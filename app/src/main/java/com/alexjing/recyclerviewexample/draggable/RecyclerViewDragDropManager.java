package com.alexjing.recyclerviewexample.draggable;

import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * 处理拖动的实现
 *
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-07
 * @time: 14:03
 */
public class RecyclerViewDragDropManager implements DraggableConstants {

    public static final Interpolator DEFAULT_ITEM_SETTLE_BACK_INTO_PLACE_ANIMATION_INTERPOLATOR =
            new DecelerateInterpolator();


    //滚动响应与触摸响应的比例系数
    private static final float SCROLL_TOUCH_SLOP_MULTIPLY = 1.5f;

    public interface OnDragEventListener {

        void onDragStarted(int position);

        void onDragPositionChanged(int fromPosition, int toPosition);

        void onDragFinished(int fromPosition, int toPosition, boolean result);

        void onDragMoveDistanceUpdated(int offsetX, int offsetY);
    }

    private RecyclerView mRecyclerView;
    private DraggableWrapperAdapter mAdapter;
    // 监听 recycler view 的触碰事件
    private RecyclerView.OnItemTouchListener mInternalOnItemTouchListener;
    // 监听 recycler view 的滚动事件
    private RecyclerView.OnScrollListener mInternalOnScrollListener;

    ///////////拖动相关
    // 显示密度
    private float mDisplayDensity;
    // 触碰响应距离
    private int mTouchSlop;
    // 滚动响应距离
    private int mScrollTouchSlop;
    // 拖动的 ViewHolder,保存当前拖动的状态
    private DraggableViewHolder mDraggableViewHoleder;
    // 记录当前拖动条目的信息
    private DraggingItemInfo mDraggingItemInfo;
    // 分发事件
    private InternalHandler mHandler;

    private Point mLastTouchPoint;
    private Point mDragStartTouchPoint;
    private Point mDragMinTouchPoint;
    private Point mDragMaxTouchPoint;

    private int mDragScrollDistanceX;
    private int mDragScrollDistanceY;


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

    /**
     * 创建出新的 {@link com.alexjing.recyclerviewexample.draggable.DraggableWrapperAdapter}
     * 并与 {@link #RecyclerViewDragDropManager} 相关联
     *
     * @param adapter
     * @return
     */
    public RecyclerView.Adapter createWrappedAdapter(RecyclerView.Adapter adapter) {
        if (!adapter.hasStableIds()) {
            throw new IllegalArgumentException("The passed adapter does not support stable IDs");
        }
        if (mAdapter != null) {
            throw new IllegalStateException("already have a wrapped adapter");
        }

        mAdapter = new DraggableWrapperAdapter(this, adapter);
        return mAdapter;
    }

    /**
     * 指明 Manager 是否已经被释放
     *
     * @return
     */
    public boolean isRelease() {
        return mInternalOnItemTouchListener == null;
    }

    /**
     * 绑定 RecyclerView 的实例
     * 调用此方法前 recyclerView 的 adapter 必须为 {@link #createWrappedAdapter(RecyclerView.Adapter)}
     * 创建的实例
     *
     * @param recyclerView
     */
    public void attachRecyclerView(@NonNull RecyclerView recyclerView) {
        if (isRelease()) {
            throw new IllegalStateException("Accessing released object");
        }

        if (mRecyclerView != null) {
            throw new IllegalStateException("RecyclerView instance has already been set");
        }

        if (mAdapter == null || getDraggableItemWrapperAdapter(recyclerView) != mAdapter) {
            throw new IllegalStateException("adapter is not set properly");
        }

        mRecyclerView = recyclerView;

        // 监听 RecyclerView 的滚动与触碰事件
        mRecyclerView.addOnScrollListener(mInternalOnScrollListener);
        mRecyclerView.addOnItemTouchListener(mInternalOnItemTouchListener);

        //初始化部分触碰事件参数
        mDisplayDensity = mRecyclerView.getResources().getDisplayMetrics().density;
        mTouchSlop = ViewConfiguration.get(mRecyclerView.getContext()).getScaledTouchSlop();
        mScrollTouchSlop = ((int) (mTouchSlop * SCROLL_TOUCH_SLOP_MULTIPLY + 0.5));

        mHandler = new InternalHandler(this);
        //边缘效果支持
        // TODO: 16-6-12

    }

    public void release() {
        isDragging();

        if (mHandler != null) {
            mHandler.release();
            mHandler = null;
        }

        // 释放边缘效果
        if (mRecyclerView != null && mInternalOnItemTouchListener != null) {
            mRecyclerView.removeOnItemTouchListener(mInternalOnItemTouchListener);
        }
        mInternalOnItemTouchListener = null;

        if (mRecyclerView != null && mInternalOnScrollListener != null) {
            mRecyclerView.removeOnScrollListener(mInternalOnScrollListener);
        }
        mInternalOnScrollListener = null;

        // TODO: 16-6-12 释放滚动任务

        mAdapter = null;
        mRecyclerView = null;
    }

    public boolean isDragging() {
        return (mDraggingItemInfo != null) && mHandler != null && mHandler.isCancelDragRequested();
    }

    ////////触碰事件/////////
    boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        final int action = MotionEventCompat.getActionMasked(e);
        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handleActionUpOrCancel(action, true);
                break;
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:

        }
        return false;
    }

    private void handleActionUpOrCancel(int action, boolean requestFinish) {

    }

    ////////////////////////
    private static DraggableWrapperAdapter getDraggableItemWrapperAdapter(RecyclerView rv) {
        return Utils.findWrappedAdapter(rv.getAdapter(), DraggableWrapperAdapter.class);
    }

    ///////////通过 Handler 分发触碰事件////////////
    private static final class InternalHandler extends Handler {
        private static final int MSG_LONG_PRESS = 1;
        private static final int MSG_DEFERRED_CANCEL_DRAG = 2;

        private RecyclerViewDragDropManager mHolder;
        private MotionEvent mDownMotionEvent;

        public InternalHandler(RecyclerViewDragDropManager holder) {
            mHolder = holder;
        }

        public void release() {
            removeCallbacks(null);
            mHolder = null;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LONG_PRESS: {
                    // 处理长按
                }
                break;
                case MSG_DEFERRED_CANCEL_DRAG: {
                    // 处理取消
                }
                break;
            }
        }

        /**
         * 开始检查是否开始长按
         * timeout == 0 时 即为触碰触发
         *
         * @param e
         * @param timeout
         */
        public void startLongPressDetection(MotionEvent e, int timeout) {
            cancelLongPressDetection();
            mDownMotionEvent = MotionEvent.obtain(e);
            sendEmptyMessageAtTime(MSG_LONG_PRESS, e.getDownTime() + timeout);
        }

        public void cancelLongPressDetection() {
            removeMessages(MSG_LONG_PRESS);
            if (mDownMotionEvent != null) {
                mDownMotionEvent.recycle();
                mDownMotionEvent = null;
            }
        }

        public void removeDeferredCancelDragRequest() {
            removeMessages(MSG_DEFERRED_CANCEL_DRAG);
        }

        public void requestDeferredCancelDrag() {
            if (isCancelDragRequested()) {
                return;
            }
            sendEmptyMessage(MSG_DEFERRED_CANCEL_DRAG);
        }

        public boolean isCancelDragRequested() {
            return hasMessages(MSG_DEFERRED_CANCEL_DRAG);
        }
    }
}
