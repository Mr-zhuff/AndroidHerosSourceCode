package com.imooc.dragviewtest;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class DragViewGroup extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View mMenuView, mMainView;
    private int mWidth;

    public DragViewGroup(Context context) {
        super(context);
        initView();
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragViewGroup(Context context,
                         AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        /**
         * ViewDragHelper定义在一个ViewGroup的内部，通过其静态工厂方法进行初始化
         * @param
         *      第一个参数是要监听的View本身，通常需要的是一个VIewGroup，即parentView
         *      第二个参数是一个Callback回调，这个回调是整个ViewDragHelper的逻辑核心
         */
        mViewDragHelper = ViewDragHelper.create(this, callback);
    }

    private ViewDragHelper.Callback callback =
            new ViewDragHelper.Callback() {

                // tryCaptureView() 指定在创建ViewDragHelper时，参数parentView中的哪一个子
                // View可以被移动，本例中ViewGroup定义了两个子View--MenuView和MainView，其中
                // 只有MainView 是可以被拖动的
                // 何时开始检测触摸事件
                @Override
                public boolean tryCaptureView(View child, int pointerId) {
                    //如果当前触摸的child是mMainView时开始检测
                    return mMainView == child;
                }

                // 处理垂直滑动，默认0 --- 不发生滑动
                @Override
                public int clampViewPositionVertical(View child, int top, int dy) {
                    return 0;
                }

                // 处理水平滑动
                @Override
                public int clampViewPositionHorizontal(View child, int left, int dx) {
                    return left;
                }

                // 拖动结束后调用，是指离开屏幕后实现的操作
                @Override
                public void onViewReleased(View releasedChild, float xvel, float yvel) {
                    super.onViewReleased(releasedChild, xvel, yvel);
                    //手指抬起后缓慢移动到指定位置
                    if (mMainView.getLeft() < 300) {
                        //关闭菜单,mMainView 移动后左边距小于300px，将MainView还原到初始状态，即（0,0）
                        //ViewDragHelper中的smoothSlideViewTo相当于Scroller的startScroll方法
//                        Log.e("mMainView.getLeft()", "" + mMainView.getLeft());
                        mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                        ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
                        // mSroller.startScroll(x, y, dx ,dy);
                        // invalidate();
                    } else {
                        //打开菜单，将MainView移动到坐标（150,0），即显示MenuView
                        mViewDragHelper.smoothSlideViewTo(mMainView, 150, 0);
                        ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
                    }
                }
//
//                // 触摸到View后回调
//                @Override
//                public void onViewCaptured(View capturedChild,
//                                           int activePointerId) {
//                    super.onViewCaptured(capturedChild, activePointerId);
//                }
//
//                // 当拖拽状态改变，比如idle，dragging
//                @Override
//                public void onViewDragStateChanged(int state) {
//                    super.onViewDragStateChanged(state);
//                }
//
//                // 当位置改变的时候调用,常用与滑动时更改scale等
//                @Override
//                public void onViewPositionChanged(View changedView,
//                                                  int left, int top, int dx, int dy) {
//                    super.onViewPositionChanged(changedView, left, top, dx, dy);
//                }
//

            };

    // 处理滑动，按顺序将子View分别定义成MenView和MainView
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    // 获取View的宽度、高度
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mMenuView.getMeasuredWidth();
    }

    /**
     * 拦截事件，将事件传递给ViewDragHelper进行处理
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件传递给ViewDragHelper,此操作必不可少
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    // 平滑移动
    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
