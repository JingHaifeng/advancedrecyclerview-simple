package com.alexjing.recyclerviewexample;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String DATA_PROVIDER = "data_provider";
    private static final String RECYCLER_VIEW = "recycler_view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(ProviderFragment.newInstance(), DATA_PROVIDER)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MainFragment.newInstance(), RECYCLER_VIEW)
                    .commit();
        }
    }

    public DataProvider getDataProvider() {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(DATA_PROVIDER);
        return ((ProviderFragment) fragment).getDataProvider();
    }
}
