package com.alexjing.recyclerviewexample.draggable;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-12
 * @time: 11:33
 */
public class DraggableWrapperAdapter<VH extends RecyclerView.ViewHolder>
        extends BaseWrapperAdapter<VH> {

    // 初始化全置为 1
    private static final int STATE_FLAG_INITIAL_VALUE = -1;

    private interface Constants extends DraggableConstants {
    }

    private int mDraggingItemInitialPosition = RecyclerView.NO_POSITION;
    private int mDraggingItemCurrentPosition = RecyclerView.NO_POSITION;

    private RecyclerViewDragDropManager mDragDropManager;
    private DraggableItemAdapter<VH> mDraggableItemAdapter;
    private RecyclerView.ViewHolder mDraggingItemViewHolder;
    private DraggingItemInfo mDraggingItemInfo;

    public DraggableWrapperAdapter(RecyclerViewDragDropManager dragDropManager,
                                   RecyclerView.Adapter<VH> adapter) {
        super(adapter);
        mDraggableItemAdapter = getDraggableAdapter(adapter);
        if (mDraggableItemAdapter == null) {
            throw new IllegalArgumentException("adapter must implement DraggableItemAdapter");
        }

        if (dragDropManager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        }

        mDragDropManager = dragDropManager;
    }

    @Override
    protected void onRelease() {
        super.onRelease();
        mDraggingItemViewHolder = null;
        mDraggableItemAdapter = null;
        mDragDropManager = null;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        final VH holder = super.onCreateViewHolder(parent, viewType);
        if (holder instanceof DraggableViewHolder) {
            // 将VH状态初始化
            ((DraggableViewHolder) holder).setDraggableStateFlags(STATE_FLAG_INITIAL_VALUE);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        if (isDragging()) {
            final long draggingItemId = mDraggingItemInfo.id;
            final long itemId = holder.getItemId();

            final int origPosition = convertToOriginalPosition(position,mDraggingItemInitialPosition,mDraggingItemCurrentPosition);
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    private boolean isDragging() {
        return mDraggingItemInfo != null;
    }

    /**
     * 获取 DraggableItemAdapter
     *
     * @param adapter
     * @param <VH>
     * @return
     */
    private static <VH extends RecyclerView.ViewHolder> DraggableItemAdapter<VH> getDraggableAdapter(
            RecyclerView.Adapter<VH> adapter) {
        return Utils.findWrappedAdapter(adapter, DraggableItemAdapter.class);
    }


    /**
     * 获取原本的位置
     *
     * @param position
     * @param dragInitial
     * @param dragCurrent
     * @return
     */
    protected static int convertToOriginalPosition(int position, int dragInitial, int dragCurrent) {

        if (dragInitial < 0 || dragCurrent < 0) {
            // 没有拖动时，返回正常位置
            return position;
        } else {
            if ((dragInitial == dragCurrent) ||
                ((position < dragInitial) && (position < dragCurrent)) ||
                (position > dragInitial) && (position > dragCurrent)) {
                // 初始位置还未变化
                //
                return position;
            } else if (dragCurrent < dragInitial) {
                if (position == dragCurrent) {
                    return dragInitial;
                } else {
                    return position - 1;
                }
            } else { // if (dragCurrent > dragInitial)
                if (position == dragCurrent) {
                    return dragInitial;
                } else {
                    return position + 1;
                }
            }
        }
    }

}
