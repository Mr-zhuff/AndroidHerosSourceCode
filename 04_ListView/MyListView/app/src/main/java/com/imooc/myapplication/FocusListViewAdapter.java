package com.imooc.myapplication;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class FocusListViewAdapter extends BaseAdapter {

    private List<String> mData;
    private Context mContext;
    private int mCurrentItem = 0;

    public FocusListViewAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    public int getCount() {
        return mData.size();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * 在初始化的时候调用，后面再次点击Item，不会触发getView再次调用，
     *      使用notifyDataSetChanged()方法帮助实现刷新布局的功能
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);  //纵向

        if (mCurrentItem == position) {
            layout.addView(addFocusView(position)); //点击的位置用Focus布局
        } else {
            layout.addView(addNormalView(position));
        }
        return layout;
    }

    public void setCurrentItem(int currentItem) {
        this.mCurrentItem = currentItem;
    }

    // Focus 布局
    private View addFocusView(int i) {
        ImageView iv = new ImageView(mContext);
        iv.setImageResource(R.drawable.ic_launcher);
        return iv;
    }

    // Normal 布局
    private View addNormalView(int i) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.HORIZONTAL); // 水平方向  ImageView + TextView

        ImageView iv = new ImageView(mContext);
        iv.setImageResource(R.drawable.in_icon);
        layout.addView(iv, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView tv = new TextView(mContext);
        tv.setText(mData.get(i));
        layout.addView(tv, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        layout.setGravity(Gravity.CENTER);
        return layout;
    }
}