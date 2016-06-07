package com.alexjing.recyclerviewexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-06-07
 * @time: 10:24
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private DataProvider mDataProvider;

    public MainAdapter(DataProvider dataProvider) {
        mDataProvider = dataProvider;
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DataProvider.Data data = mDataProvider.getItem(position);
        holder.mTitle.setText(data.getText());
    }

    @Override
    public int getItemCount() {
        return mDataProvider.getCount();
    }

    @Override
    public long getItemId(int position) {
        return mDataProvider.getItem(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataProvider.getItem(position).getViewType();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout mContainer;
        private View mHandle;
        private TextView mTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            mContainer = (FrameLayout) itemView.findViewById(R.id.container);
            mHandle = itemView.findViewById(R.id.handle);
            mTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
