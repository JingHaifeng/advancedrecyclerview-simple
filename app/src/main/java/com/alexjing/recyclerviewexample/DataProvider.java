package com.alexjing.recyclerviewexample;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-07
 * @time: 09:38
 */
public abstract class DataProvider {
    public static abstract class Data {
        public abstract int getId();

        public abstract String getText();

        public abstract int getViewType();
    }

    public abstract int getCount();

    public abstract Data getItem(int index);

    public abstract int getViewTypeCount();
}
