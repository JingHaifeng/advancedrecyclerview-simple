package com.alexjing.recyclerviewexample.draggable;

import android.support.v7.widget.RecyclerView;

/**
 * 拖动 RecyclerView 的方法接口
 *
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-07
 * @time: 11:53
 */
public interface DraggableItemAdapter<T extends RecyclerView.ViewHolder> {

    boolean onCheckCanStartDrag(T holder, int position, int x, int y);

    void moveItem(int fromPosition, int toPosition);

    boolean onCheckCanDrop(int draggingPosition, int dropPosition);
}
