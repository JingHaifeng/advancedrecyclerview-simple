package com.alexjing.recyclerviewexample;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-07
 * @time: 09:49
 */
public class ExampleProvider extends DataProvider {
    private List<ConcreteData> mDataList;

    public ExampleProvider() {
        mDataList = new ArrayList<>();
        for (int i = 0; i <= 57; i++) {
            char c = (char) (i + 65);
            ConcreteData data =
                    new ConcreteData(i, String.valueOf(c), 0);
            mDataList.add(data);
        }
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Data getItem(int index) {
        if (index < 0 && index > getCount()) {
            throw new IndexOutOfBoundsException("index=" + index);
        }
        return mDataList.get(index);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    public static final class ConcreteData extends Data {
        private final int mId;
        private final String mText;
        private final int mViewType;

        public ConcreteData(int id, String text, int viewType) {
            mId = id;
            mText = text;
            mViewType = viewType;
        }

        @Override
        public int getId() {
            return mId;
        }

        @Override
        public String getText() {
            return mText;
        }

        @Override
        public int getViewType() {
            return mViewType;
        }
    }
}
