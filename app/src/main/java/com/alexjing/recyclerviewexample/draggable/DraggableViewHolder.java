package com.alexjing.recyclerviewexample.draggable;

/**
 * 处理 view 当前拖动的状态
 *
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-07
 * @time: 13:52
 */
public interface DraggableViewHolder {

    void setDraggableStateFlags(@DraggableStateFlags int flags);

    @DraggableStateFlags
    int getDraggableStateFlags();
}
