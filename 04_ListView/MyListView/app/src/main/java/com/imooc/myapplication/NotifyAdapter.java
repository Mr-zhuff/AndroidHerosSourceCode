package com.imooc.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NotifyAdapter extends BaseAdapter {

    private static final String TAG = "NotifyAdapter";
    private List<String> mData;
    private LayoutInflater mInflater;

    public NotifyAdapter(Context context, List<String> data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
//        Log.e(TAG, "------------NotifyAdapter()");
    }

    // ListView中缓存的Item数目，------------getCount(), mData.size() = 20
    @Override
    public int getCount() {
//        Log.e(TAG, "------------getCount(), mData.size() = " + mData.size());
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
//        Log.e(TAG, "------------getItem(), mData.get(position) = "+ mData.get(position));
        return mData.get(position);
    }

    //Item的position
    @Override
    public long getItemId(int position) {
//        Log.e(TAG, "------------getItemId(), position " + position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Log.e(TAG, "------------getView()");
        ViewHolder holder = null;
        // 判断是否缓存
        if (convertView == null) {
            holder = new ViewHolder();
            // 通过LayoutInflater实例化布局，inflate 加载一个布局文件，findViewById则是从布局文件中查找一个控件
            convertView = mInflater.inflate(R.layout.notify_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.imageView);
            holder.title = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            // 通过tag找到缓存的布局
            holder = (ViewHolder) convertView.getTag();
        }
        // 设置布局中控件要显示的视图
        holder.img.setBackgroundResource(R.drawable.ic_launcher);
        holder.title.setText(mData.get(position));
        return convertView;
    }

    public final class ViewHolder {
        public ImageView img;
        public TextView title;
    }
}
