package com.imooc.systemwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

public class ShineTextView extends TextView {

    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private Paint mPaint;
    private int mViewWidth = 0;
    private int mTranslate = 0;

    public ShineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
    LinearGradient(float x0, float y0, float x1, float y1, int colors[], float positions[], TileMode tile)
    注：Android中计算x,y坐标都是以屏幕左上角为原点，向右为x+，向下为y+
    第一个参数为线性起点的x坐标
            第二个参数为线性起点的y坐标
    第三个参数为线性终点的x坐标
            第四个参数为线性终点的y坐标
    第五个参数为实现渐变效果的颜色的组合
    第六个参数为前面的颜色组合中的各颜色在渐变中占据的位置（比重），如果为空，则表示上述颜色的集合在渐变中均匀出现
    第七个参数为渲染器平铺的模式，一共有三种

    -CLAMP
            边缘拉伸
    -REPEAT
    在水平和垂直两个方向上重复，相邻图像没有间隙
    -MIRROR
    以镜像的方式在水平和垂直两个方向上重复，相邻图像有间隙
    */

    //调用顺序onMeasure()→onSizeChanged()→onLayout()→onMeasure()→onLayout()→onDraw()

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();    //获取当前绘制TextView的Paint对象
                mLinearGradient = new LinearGradient(   //实现颜色线性渐变效果
                        0,
                        0,
                        mViewWidth,
                        0,
                        new int[]{
                                Color.RED, Color.GREEN, Color.BLUE},
                        null,
                        Shader.TileMode.MIRROR);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();     //3x3矩阵,可以帮助实现动态效果
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);  // Translate平移变换
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);    //重新绘制延时时间
        }
    }
}
