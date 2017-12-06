package com.xys.myapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/**
 * getItemCount -> onCreateViewHolder -> RecyclerView.ViewHolder -> onBindViewHolder
 */

public class RecyclerAdapter
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

//    private static final String TAG = "RecyclerAdapter";
    private List<String> mData;

    public RecyclerAdapter(List<String> data) {
        mData = data;
    }

    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public int getItemCount() {
//        Log.e(TAG, "getItemCount");
        return mData.size();
    }

    // 数据与视图关联
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        Log.e(TAG, "onCreateViewHolder");
        // 将List item 的布局转化为View并传递给RecyclerView封装好的ViewHolder
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.rc_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
//        Log.e(TAG, "onBindViewHolder");
        // 建立起ViewHolder中视图与数据的关联
        viewHolder.textView.setText(mData.get(i) + i);
    }


    // Android并没有给RecycleView增进点击事件，需要自己使用接口回调机制
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.e("OnItemClickListener", "ViewHolder");
            textView = (TextView) itemView;
            textView.setOnClickListener(this);
        }

        // 通过接口回调来实现RecyclerView的点击事件
        @Override
        public void onClick(View v) {
            Log.e("OnItemClickListener", "onClick");
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getPosition());
            }
        }
    }


}
