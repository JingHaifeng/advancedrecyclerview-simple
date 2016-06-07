package com.alexjing.recyclerviewexample.draggable;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-07
 * @time: 13:49
 */
public interface DraggableConstants {

    int STATE_FLAG_DRAGGING = (1 << 0);

    int STATE_FLAG_IS_ACTIVE = (1 << 1);

    int STATE_FLAG_IS_IN_RANGE = (1 << 2);

    int STATE_FLAG_IS_UPDATED = (1 << 31);
}
