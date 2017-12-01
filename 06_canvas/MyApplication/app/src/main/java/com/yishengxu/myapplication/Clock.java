package com.yishengxu.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Clock extends View {

    private int mHeight, mWidth;

    public Clock(Context context) {
        super(context);
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取宽高参数
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        // 画外圆
        Paint paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);   //空心
        paintCircle.setAntiAlias(true);     //锯齿效果，true -- 更加柔和
        paintCircle.setStrokeWidth(5);      // 空心边框宽度
        canvas.drawCircle(mWidth / 2,
                mHeight / 2, mWidth / 2, paintCircle);

        // 画刻度，线段，旋转画布可以避免根据角度计算起始点坐标
        Paint paintDegree = new Paint();
        paintCircle.setStrokeWidth(3);
        for (int i = 0; i < 24; i++) {
            // 区分整点与非整点
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                paintDegree.setStrokeWidth(5);
                paintDegree.setTextSize(30);
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2,
                        mWidth / 2, mHeight / 2 - mWidth / 2 + 60,
                        paintDegree);    //整天线段长度为60像素
                String degree = String.valueOf(i);
                canvas.drawText(degree,
                        mWidth / 2 - paintDegree.measureText(degree) / 2,
                        mHeight / 2 - mWidth / 2 + 90,
                        paintDegree);    //绘制文本
            } else {
                paintDegree.setStrokeWidth(3);
                paintDegree.setTextSize(15);
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2,
                        mWidth / 2, mHeight / 2 - mWidth / 2 + 30,
                        paintDegree);    // 非整点线段长度为30像素
                String degree = String.valueOf(i);
                canvas.drawText(degree,
                        mWidth / 2 - paintDegree.measureText(degree) / 2,
                        mHeight / 2 - mWidth / 2 + 60,
                        paintDegree);
            }
            // 通过旋转画布简化坐标运算
            canvas.rotate(15, mWidth / 2, mHeight / 2);
        }

        // 画圆心
        Paint paintPointer = new Paint();
        paintPointer.setStrokeWidth(30);
        canvas.drawPoint(mWidth / 2, mHeight / 2, paintPointer);

        // 画指针
        Paint paintHour = new Paint();
        paintHour.setStrokeWidth(20);
        Paint paintMinute = new Paint();
        paintMinute.setStrokeWidth(10);
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);  //将坐标中的原点平移到圆心
        canvas.drawLine(0, 0, 100, 100, paintHour);
        canvas.drawLine(0, 0, 100, 200, paintMinute);
        canvas.restore();
    }
}
