package com.imooc.systemwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

public class MyScrollView extends ViewGroup {

    /**
     * I/Choreographer: Skipped 40 frames!  The application may be doing too much work on its main thread.
     * E/MyScrollView: ----------Second Constructor !!
     * E/MyScrollView: ----------onMeasure() !!
     * E/MyScrollView: ----------onSizeChanged() !!
     * E/MyScrollView: ----------onLayout() !!
     * E/MyScrollView: ----------onMeasure() !!
     * E/MyScrollView: ----------onSizeChanged() !!
     * E/MyScrollView: ----------onLayout() !!
     * E/MyScrollView: ----------computeScroll() !!
     * E/MyScrollView: ----------onTouchEvent() !!
     * E/MyScrollView: ----------computeScroll() !!
     */


    private static final String TAG = "MyScrollView";

    private int mScreenHeight;
    private Scroller mScroller;
    private int mLastY;
    private int mStart;
    private int mEnd;

    public MyScrollView(Context context) {
        super(context);
        initView(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG, "----------Second Constructor !!");
        initView(context);
    }

    public MyScrollView(Context context, AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
        mScroller = new Scroller(context);
    }

    ///2、布局，当View分配所有的子元素的大小和位置时触发left top right bottom
    @Override
    protected void onLayout(boolean changed,
                            int l, int t, int r, int b) {
        Log.e(TAG, "----------onLayout() !!");
        int childCount = getChildCount();

        // 设置ViewGroup的高度
        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        mlp.height = mScreenHeight * childCount;    //整个ViewGroup的高度即子View的个数乘以屏幕的高度
        setLayoutParams(mlp);

        // 通过遍历设定每个子View需要放置的位置
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(l, i * mScreenHeight,
                        r, (i + 1) * mScreenHeight);
            }
        }
    }

    // 1、测量，确定所有子元素的大小（遍历的方式确认）
    @Override
    protected void onMeasure(int widthMeasureSpec,
                             int heightMeasureSpec) {
//        Log.e(TAG, "----------onMeasure() !!");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
//        Log.e(TAG, "getChildCount() = " + count); //getChildCount() = 4 子元素个数为4
        for (int i = 0; i < count; ++i) {
            View childView = getChildAt(i);
            measureChild(childView,
                    widthMeasureSpec, heightMeasureSpec);
        }
    }

    // 当调用 onMeasure 测量后，发现尺寸和原来的不一致就调用该方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "----------onSizeChanged() !!");
    }

    // 3、触屏事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.e(TAG, "----------onTouchEvent() !!");
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y; //鼠标点击坐标Y值
                mStart = getScrollY();      //记录触摸起点 0  800  1600  2400
//                Log.e(TAG , "ACTION_DOWN ,mLastY = " + mLastY + " , mStart = " + mStart);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                if (getScrollY() < 0) {
                    dy = 0;
                }
                if (getScrollY() > getHeight() - mScreenHeight) {
                    dy = 0;
                }
                scrollBy(0, dy); //辅助滑动，手指滑动会使ViewGroup中的所有子View跟着滚动dy
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:     // 手指离开后ViewGroup的黏性效果
                int dScrollY = checkAlignment();
                if (dScrollY > 0) {     // 黏性效果  1/3屏高
                    if (dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -dScrollY);
                    } else {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, mScreenHeight - dScrollY);
                    }
                } else {
                    if (-dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -dScrollY);
                    } else {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -mScreenHeight - dScrollY);
                    }
                }
                break;
        }
        postInvalidate();
        return true;
    }

	private int checkAlignment() {      //向上还是向下滑动
        int mEnd = getScrollY();        // 记录触摸终点
        boolean isUp = ((mEnd - mStart) > 0) ? true : false;
        int lastPrev = mEnd % mScreenHeight;
        int lastNext = mScreenHeight - lastPrev;
        if (isUp) {
            //向上的
            return lastPrev;
        } else {
            return -lastNext;
        }
    }

    /**
     * computeScroll也不是来让ViewGroup滑动的，真正让ViewGroup滑动的是scrollTo,scrollBy。
     * computeScroll的作用是计算ViewGroup如何滑动。而computeScroll是通过draw来调用的。
     */
    // 由父视图调用用来请求子视图根据偏移值 mScrollX,mScrollY重新绘制
    // 通过invalidate操纵，此方法通过draw方法调用
    @Override
    public void computeScroll() {
        Log.e(TAG, "----------computeScroll() !!");
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());  // scrollTo( )表示View相对于其初始位置滚动某段距离
            postInvalidate();       // // 使用postInvalidate可以直接在线程中更新界面
        }
    }
}
