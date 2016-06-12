package com.alexjing.recyclerviewexample.draggable;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-12
 * @time: 13:56
 */
public class Utils {

    /**
     * 递归找到指定 Adapter
     *
     * @param adapter
     * @param clazz
     * @return
     */
    public static <T> T findWrappedAdapter(RecyclerView.Adapter adapter, Class<T> clazz) {
        if (clazz.isInstance(adapter)) {
            return clazz.cast(adapter);
        } else if (adapter instanceof BaseWrapperAdapter) {
            final RecyclerView.Adapter wrappedAdapter =
                    ((BaseWrapperAdapter) adapter).getWrappedAdapter();
            return findWrappedAdapter(wrappedAdapter, clazz);
        } else {
            return null;
        }
    }

    /**
     * 获取 view margin 值
     *
     * @param v
     * @param outMargins
     * @return
     */
    public static Rect getLayoutMargins(View v, Rect outMargins) {
        final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            final ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) layoutParams;
            outMargins.left = marginLayoutParams.leftMargin;
            outMargins.right = marginLayoutParams.rightMargin;
            outMargins.top = marginLayoutParams.topMargin;
            outMargins.bottom = marginLayoutParams.bottomMargin;
        } else {
            outMargins.left = outMargins.right = outMargins.top = outMargins.bottom = 0;
        }
        return outMargins;
    }


    public static final int INVALID_SPAN_ID = -1;
    public static final int INVALID_SPAN_COUNT = -1;

    /**
     * 获取列数
     * @param holder
     * @return
     */
    public static int getSpanSize(@Nullable RecyclerView.ViewHolder holder) {
        final View itemView = getLaidOutItemView(holder);

        if (itemView == null) {
            return INVALID_SPAN_COUNT;
        }

        ViewGroup.LayoutParams lp = itemView.getLayoutParams();

        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            final boolean isFullSpan = ((StaggeredGridLayoutManager.LayoutParams) lp).isFullSpan();
            if (isFullSpan) {
                final RecyclerView rv = (RecyclerView) itemView.getParent();
                final int spanCount = getSpanCount(rv);
                return spanCount;
            } else {
                return 1;
            }
        } else if (lp instanceof GridLayoutManager.LayoutParams) {
            return ((GridLayoutManager.LayoutParams) lp).getSpanSize();
        } else if (lp instanceof RecyclerView.LayoutParams) {
            return 1;
        } else {
            return INVALID_SPAN_COUNT;
        }
    }

    /**
     * 获取已布局完成的 VH 若无则返回空
     *
     * @param holder
     * @return
     */
    @Nullable
    private static View getLaidOutItemView(@Nullable RecyclerView.ViewHolder holder) {
        if (holder == null) {
            return null;
        }

        final View itemView = holder.itemView;

        if (!ViewCompat.isLaidOut(itemView)) {
            return null;
        }

        return itemView;
    }

    public static int getSpanCount(@NonNull RecyclerView rv) {
        RecyclerView.LayoutManager layoutManager = rv.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        } else {
            return 1;
        }
    }
}
