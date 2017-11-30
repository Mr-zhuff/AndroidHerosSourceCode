package com.imooc.systemwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleProgressView extends View {

    private static final String TAG = "CircleProgressVIew";

    /**
     * 调用顺序 Second Constructor -> onMeasure() -> onDraw()
     */
    // 屏幕宽高
    private int mMeasureHeigth;
    private int mMeasureWidth;

    // 中间圆形
    private Paint mCirclePaint;
    private float mCircleXY;
    private float mRadius;

    // 外圈弧线
    private Paint mArcPaint;
    private RectF mArcRectF;
    private float mSweepAngle;
    private float mSweepValue = 120;

    // 中间文字
    private Paint mTextPaint;
    private String mShowText;
    private float mShowTextSize;

    public CircleProgressView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "Third Constructor");
    }

    // xml布局文件中引用时，在系统初始化该View时，调用的是第二个构造函数
    /**
     * E/CircleProgressVIew: Second Constructor
     * E/CircleProgressVIew: id   :   @2131230720
     * E/CircleProgressVIew: layout_width   :   -1
     * E/CircleProgressVIew: layout_height   :   -1
     */
    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG, "Second Constructor");

        //打印attrs参数，可以看出其中的参数attrs是我们在xml中配置的参数。
        for(int i=0;i<attrs.getAttributeCount();i++){
            Log.e(TAG , attrs.getAttributeName(i)+"   :   "+attrs.getAttributeValue(i));
        }
    }

    public CircleProgressView(Context context) {
        super(context);
        Log.e(TAG, "First Constructor");

    }



    @Override
    protected void onMeasure(int widthMeasureSpec,
                             int heightMeasureSpec) {
        Log.e(TAG ,"-------------onMeasure");
        mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMeasureHeigth = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mMeasureWidth, mMeasureHeigth);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG ,"-------------onDraw");
        super.onDraw(canvas);
        // 绘制圆
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);

        // 绘制弧线，
        /**
         * public void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
         * oval :指定圆弧的外轮廓矩形区域。
         * startAngle: 圆弧起始角度，单位为度。   0°的时候水平向右，顺时针为正
         * sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度,从右中间开始为零度。
         * useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。False用来绘制圆弧
         * paint: 绘制圆弧的画板属性，如颜色，是否填充等。
         */
        canvas.drawArc(mArcRectF, 0, mSweepAngle, false, mArcPaint);
        Log.e(TAG ,"mSweepAngle = " + mSweepAngle);

        // 绘制文字
        canvas.drawText(mShowText, 0, mShowText.length(),
                mCircleXY, mCircleXY + (mShowTextSize / 4), mTextPaint);
    }

    private void initView() {
        float length = 0;
        if (mMeasureHeigth >= mMeasureWidth) {
            length = mMeasureWidth; // 取短边
        } else {
            length = mMeasureHeigth;
        }

        // 画圆
        mCircleXY = length / 2;     // 圆心
        mRadius = (float) (length * 0.5 / 2);   // 内部小圆半径
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);    // 抗锯齿  更为柔和
        mCirclePaint.setColor(getResources().getColor(
                android.R.color.holo_blue_bright));

        // 绘制弧线需要指定椭圆的外接矩形
        mArcRectF = new RectF(
                (float) (length * 0.1),
                (float) (length * 0.1),
                (float) (length * 0.9),
                (float) (length * 0.9));
        mSweepAngle = (mSweepValue / 100f) * 360f;  //扫过角度  Float mSweepValue = 66
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(getResources().getColor(
                android.R.color.holo_blue_bright));
        mArcPaint.setStrokeWidth((float) (length * 0.1));   //设置画出的线的 粗细程度
        mArcPaint.setStyle(Style.STROKE);   // 画出的图形为空心

        mShowText = setShowText();
        mShowTextSize = setShowTextSize();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mShowTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);    //text的偏移量
    }

    private float setShowTextSize() {
        this.invalidate();
        return 50;
    }

    //设置text内容
    private String setShowText() {
        this.invalidate();
        return "Android Skill";
    }

    public void forceInvalidate() {
        this.invalidate();
    }

    //提供外部接口设置圆弧弧度
    public void setSweepValue(float sweepValue) {
        if (sweepValue != 0) {
            mSweepValue = sweepValue;
        } else {
            mSweepValue = 25;
        }
        this.invalidate();
    }
}
