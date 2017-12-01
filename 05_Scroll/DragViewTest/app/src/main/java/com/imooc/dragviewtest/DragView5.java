package com.imooc.dragviewtest;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class DragView5 extends View {

    private static final String TAG = "DragView5";

    private int lastX;
    private int lastY;
    private Scroller mScroller;

    public DragView5(Context context) {
        super(context);
        ininView(context);
    }

    public DragView5(Context context, AttributeSet attrs) {
        super(context, attrs);
        ininView(context);
    }

    public DragView5(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ininView(context);
    }

    private void ininView(Context context) {
        setBackgroundColor(Color.BLUE);
        // 初始化Scroller
        mScroller = new Scroller(context);
    }

    // 实现模拟滑动，系统在绘制View的时候会在draw()方法中调用该方法
    // invalidate() ->draw() -> computeScroll() 循环
    @Override
    public void computeScroll() {
        super.computeScroll();
        // 判断Scroller是否执行完毕
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(
                    mScroller.getCurrX(),   //获取当前滑动坐标
                    mScroller.getCurrY());
            // 通过重绘来不断调用computeScroll
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 每次回调onTouchEvent的时候，都获取一下触摸点的坐标（视图坐标）
        int x = (int) event.getX();
        int y = (int) event.getY();
//        Log.e("DragView5 ", "x = " + x);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
//                Log.e("DragView5 ", "lastX = " + lastX);
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
//                Log.e("DragView5 ", "offsetX = " + offsetX);
                /**
                 * scrollTo、scrollBy移动的是View的content，即让View的内容移动，
                 * 例如：TextView，content就是它的文本；ImageView，content就是它的drawable对象
                 * 如果在ViewGroup中则是移动所有的子View
                 * scrollTo(x, y)表示移动到一个具体的坐标点(x,y)
                 * scrollBy(dx, dy)表示移动的增量为dx、dy
                 */
                // 移动view ，应该在它的父视图中使用scrollTo、scrollBy
                // scrollBy、scrollTo将参数设置为负数，content将会向坐标轴正方向移动
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                // 手指离开时，执行滑动过程
                View viewGroup = ((View) getParent());
                mScroller.startScroll(      //startScroll()开启模拟过程
                        viewGroup.getScrollX(),
                        viewGroup.getScrollY(),
                        -viewGroup.getScrollX(),
                        -viewGroup.getScrollY());
                /**
                 * 请求重绘View树，即draw()过程，假如视图发生大小没有变化就不会调用layout()过程，
                 * 并且只绘制那些“需要重绘的”视图，即谁(View的话，只绘制该View ；
                 * ViewGroup，则绘制整个ViewGroup)请求invalidate()方法，就绘制该视图
                 */
                invalidate();   // 通知View进行重绘
                break;
        }
        return true;
    }
}
