package com.alexjing.recyclerviewexample.draggable;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-07
 * @time: 13:53
 */

@IntDef(flag = true,
        value = {DraggableConstants.STATE_FLAG_DRAGGING,
                DraggableConstants.STATE_FLAG_IS_ACTIVE,
                DraggableConstants.STATE_FLAG_IS_IN_RANGE,
                DraggableConstants.STATE_FLAG_IS_UPDATED})
@Retention(RetentionPolicy.SOURCE)
public @interface DraggableStateFlags {

}
