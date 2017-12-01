package com.imooc.dragviewtest;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DragView4 extends View {

    private int lastX;
    private int lastY;

    public DragView4(Context context) {
        super(context);
        ininView();
    }

    public DragView4(Context context, AttributeSet attrs) {
        super(context, attrs);
        ininView();
    }

    public DragView4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ininView();
    }

    private void ininView() {
        setBackgroundColor(Color.BLUE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                /**
                 * scrollTo、scrollBy移动的是View的content，即让View的内容移动，
                 * 例如：TextView，content就是它的文本；ImageView，content就是它的drawable对象
                 * 如果在ViewGroup中则是移动所有的子View
                 * scrollTo(x, y)表示移动到一个具体的坐标点(x,y)
                 * scrollBy(dx, dy)表示移动的增量为dx、dy
                 */
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;
        }
        return true;
    }
}
