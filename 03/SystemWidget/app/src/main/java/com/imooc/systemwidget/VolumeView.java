package com.imooc.systemwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class VolumeView extends View {

    private static final String TAG = "VolumeView";

    private int mWidth;
    private int mRectWidth;
    private int mRectHeight;
    private Paint mPaint;
    private int mRectCount;
    private int offset = 5; //小矩形偏移量
    private double mRandom;
    private LinearGradient mLinearGradient;

    public VolumeView(Context context) {
        super(context);
        initView();
    }

    public VolumeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG, "Second Constructor");
        initView();
    }

    public VolumeView(Context context, AttributeSet attrs,
                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mRectCount = 12;    //长方形个数
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e(TAG, "-----------onSizaChanges");
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mRectHeight = getHeight();
        mRectWidth = (int) (mWidth * 0.6 / mRectCount);     // 长方形宽度
        /**
         * LinearGradient(float x0, float y0, float x1, float y1, int colors[], float positions[], TileMode tile)
         * 注：Android中计算x,y坐标都是以屏幕左上角为原点，向右为x+，向下为y+
         * 第一个参数为线性起点的x坐标
         * 第二个参数为线性起点的y坐标
         * 第三个参数为线性终点的x坐标
         * 第四个参数为线性终点的y坐标
         * 第五个参数为实现渐变效果的颜色的组合
         * 第六个参数为前面的颜色组合中的各颜色在渐变中占据的位置（比重），如果为空，则表示上述颜色的集合在渐变中均匀出现
         * 第七个参数为渲染器平铺的模式，一共有三种
         * -CLAMP
                边缘拉伸
         * -REPEAT
                在水平和垂直两个方向上重复，相邻图像没有间隙
         * -MIRROR
                以镜像的方式在水平和垂直两个方向上重复，相邻图像有间隙
         */
        mLinearGradient = new LinearGradient(
                0,
                0,
                mRectWidth,
                mRectHeight,
                Color.YELLOW,
                Color.BLUE,
                Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "--------------onDraw");

        // 循环里面绘制所有的小矩形
        for (int i = 0; i < mRectCount; i++) {
            mRandom = Math.random();    //令系统随机选取大于等于 0.0 且小于 1.0 的伪随机 double 值
            float currentHeight = (float) (mRectHeight * mRandom);
            canvas.drawRect(
                    (float) (mWidth * 0.4 / 2 + mRectWidth * i + offset),
                    currentHeight,
                    (float) (mWidth * 0.4 / 2 + mRectWidth * (i + 1)),
                    mRectHeight,    // 小矩形高度的终点值即为屏幕高度
                    mPaint);
        }
        postInvalidateDelayed(300); //延迟300毫秒重绘，重新调用onDraw()
    }
}
