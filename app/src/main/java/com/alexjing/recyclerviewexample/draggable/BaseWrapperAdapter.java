package com.alexjing.recyclerviewexample.draggable;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-12
 * @time: 10:25
 */
public class BaseWrapperAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private static final String TAG = "BaseWrapperAdapter";
    private static final boolean LOCAL_LOGD = true;

    private RecyclerView.Adapter<VH> mWrappedAdapter;
    private BridgeObserver<VH> mBridgeObserver;

    /**
     * 有效负载，用与更新数据
     */
    protected static final List<Object> FULL_UPDATE_PAYLOADS = Collections.emptyList();

    public BaseWrapperAdapter(RecyclerView.Adapter<VH> adapter) {
        mWrappedAdapter = adapter;
        mBridgeObserver = new BridgeObserver<>(this);
        mWrappedAdapter.registerAdapterDataObserver(mBridgeObserver);
        super.setHasStableIds(mWrappedAdapter.hasStableIds());
    }

    public boolean isWrappedAdapterAlive() {
        return mWrappedAdapter != null;
    }

    public boolean isBridgeAlive() {
        return mBridgeObserver != null;
    }

    /**
     * 释放
     */
    public void release() {
        onRelease();

        if (isWrappedAdapterAlive() && isBridgeAlive()) {
            mWrappedAdapter.unregisterAdapterDataObserver(mBridgeObserver);
        }

        mWrappedAdapter = null;
        mBridgeObserver = null;
    }

    /**
     * 回调方法
     */
    protected void onRelease() {
        // 预留对应回调方法
    }

    public RecyclerView.Adapter<VH> getWrappedAdapter() {
        return mWrappedAdapter;
    }

    ///////Override///////

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (isWrappedAdapterAlive()) {
            mWrappedAdapter.onAttachedToRecyclerView(recyclerView);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (isWrappedAdapterAlive()) {
            mWrappedAdapter.onDetachedFromRecyclerView(recyclerView);
        }
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        if (isWrappedAdapterAlive()) {
            mWrappedAdapter.onViewAttachedToWindow(holder);
        }
    }

    @Override
    public void onViewDetachedFromWindow(VH holder) {
        if (isWrappedAdapterAlive()) {
            mWrappedAdapter.onViewDetachedFromWindow(holder);
        }
    }

    @Override
    public void onViewRecycled(VH holder) {
        if (isWrappedAdapterAlive()) {
            mWrappedAdapter.onViewRecycled(holder);
        }
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);

        if (isWrappedAdapterAlive()) {
            mWrappedAdapter.setHasStableIds(hasStableIds);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return mWrappedAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, position, FULL_UPDATE_PAYLOADS);
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        if (isWrappedAdapterAlive()) {
            mWrappedAdapter.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return isWrappedAdapterAlive() ? mWrappedAdapter.getItemCount() : 0;
    }

    @Override
    public long getItemId(int position) {
        return mWrappedAdapter.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return mWrappedAdapter.getItemViewType(position);
    }

    ///////////Override///////////

    //////////handle event/////////

    protected void onHandleWrappedAdapterChanged() {
        notifyDataSetChanged();
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount) {
        notifyItemRangeChanged(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount,
                                                          Object payload) {
        notifyItemRangeChanged(positionStart, itemCount, payload);
    }

    protected void onHandleWrappedAdapterItemRangeInserted(int positionStart, int itemCount) {
        notifyItemRangeInserted(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterItemRangeRemoved(int positionStart, int itemCount) {
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    protected void onHandleWrappedAdapterRangeMoved(int fromPosition, int toPosition,
                                                    int itemCount) {
        if (itemCount != 1) {
            throw new IllegalStateException(
                    "itemCount should be always 1  (actual: " + itemCount + ")");
        }

        notifyItemMoved(fromPosition, toPosition);
    }
    ////////handle event///////

    ///////观察者对应方法实现//////

    /*package*/
    final void onWrappedAdapterChanged() {
        if (LOCAL_LOGD) {
            Log.d(TAG, "onWrappedAdapterChanged");
        }

        onHandleWrappedAdapterChanged();
    }

    /*package*/
    final void onWrappedAdapterItemRangeChanged(int positionStart, int itemCount) {
        if (LOCAL_LOGD) {
            Log.d(TAG, "onWrappedAdapterItemRangeChanged(positionStart = " + positionStart +
                       ", itemCount = " + itemCount + ")");
        }

        onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount);
    }

    /*package*/
    final void onWrappedAdapterItemRangeChanged(int positionStart, int itemCount, Object payload) {
        if (LOCAL_LOGD) {
            Log.d(TAG, "onWrappedAdapterItemRangeChanged(positionStart = " + positionStart +
                       ", itemCount = " + itemCount + ", payload = " + payload + ")");
        }

        onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount, payload);
    }

    /*package*/
    final void onWrappedAdapterItemRangeInserted(int positionStart, int itemCount) {
        if (LOCAL_LOGD) {
            Log.d(TAG, "onWrappedAdapterItemRangeInserted(positionStart = " + positionStart +
                       ", itemCount = " + itemCount + ")");
        }

        onHandleWrappedAdapterItemRangeInserted(positionStart, itemCount);
    }

    /*package*/
    final void onWrappedAdapterItemRangeRemoved(int positionStart, int itemCount) {
        if (LOCAL_LOGD) {
            Log.d(TAG, "onWrappedAdapterItemRangeRemoved(positionStart = " + positionStart +
                       ", itemCount = " + itemCount + ")");
        }

        onHandleWrappedAdapterItemRangeRemoved(positionStart, itemCount);
    }

    /*package*/
    final void onWrappedAdapterRangeMoved(int fromPosition, int toPosition, int itemCount) {
        if (LOCAL_LOGD) {
            Log.d(TAG,
                  "onWrappedAdapterRangeMoved(fromPosition = " + fromPosition + ", toPosition = " +
                  toPosition + ", itemCount = " + itemCount + ")");
        }

        onHandleWrappedAdapterRangeMoved(fromPosition, toPosition, itemCount);
    }

    /**
     * 桥接观察者
     *
     * @param <VH>
     */
    private static final class BridgeObserver<VH extends RecyclerView.ViewHolder>
            extends RecyclerView.AdapterDataObserver {
        private final WeakReference<BaseWrapperAdapter<VH>> mRefHolder;

        public BridgeObserver(BaseWrapperAdapter<VH> holder) {
            mRefHolder = new WeakReference<>(holder);
        }

        @Override
        public void onChanged() {
            final BaseWrapperAdapter<VH> adapter = mRefHolder.get();
            if (adapter != null) {
                adapter.onWrappedAdapterChanged();
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            final BaseWrapperAdapter<VH> adapter = mRefHolder.get();
            if (adapter != null) {
                adapter.onWrappedAdapterItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            final BaseWrapperAdapter<VH> adapter = mRefHolder.get();
            if (adapter != null) {
                adapter.onWrappedAdapterItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            final BaseWrapperAdapter<VH> adapter = mRefHolder.get();
            if (adapter != null) {
                adapter.onWrappedAdapterItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            final BaseWrapperAdapter<VH> adapter = mRefHolder.get();
            if (adapter != null) {
                adapter.onWrappedAdapterItemRangeRemoved(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            final BaseWrapperAdapter<VH> adapter = mRefHolder.get();
            if (adapter != null) {
                adapter.onWrappedAdapterRangeMoved(fromPosition, toPosition, itemCount);
            }
        }
    }
}
