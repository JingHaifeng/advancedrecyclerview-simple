package com.alexjing.recyclerviewexample.draggable;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-07
 * @time: 13:52
 */
public interface DraggableViewHolder {

    void setDraggableStateFlags(@DraggableStateFlags int flags);

    @DraggableStateFlags
    int getDraggableStateFlags();
}
