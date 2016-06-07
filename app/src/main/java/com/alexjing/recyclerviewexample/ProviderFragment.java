package com.alexjing.recyclerviewexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-07
 * @time: 09:48
 */
public class ProviderFragment extends Fragment {
    private DataProvider mDataProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mDataProvider = new ExampleProvider();
    }

    public static ProviderFragment newInstance(){
        return new ProviderFragment();
    }

    public DataProvider getDataProvider() {
        return mDataProvider;
    }
}
