package com.imooc.customanim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CustomTV extends Animation {

    private int mCenterWidth;
    private int mCenterHeight;
//    private Camera mCamera = new Camera();  // Camera类
    private float mRotateY = 0.0f;

    @Override
    public void initialize(int width,
                           int height,
                           int parentWidth,
                           int parentHeight) {

        super.initialize(width, height, parentWidth, parentHeight);
        // 设置默认时长
        setDuration(1000);
        // 动画结束后保留状态
        setFillAfter(true);
        // 设置默认插值器
//        setInterpolator(new AccelerateInterpolator());
        mCenterWidth = width / 2;
        mCenterHeight = height / 2;
    }

    // 暴露接口-设置旋转角度
    public void setRotateY(float rorateY) {
        mRotateY = rorateY;
    }

    /**
     * 创建自定义动画，必须实现Animation的applyTransformation方法
     * @param interpolatedTime
     *         插值器的时间因子，由动画当前完成的百分比和当前时间所对应的插值所计算而来，取值范围为0到1.0
     * @param transformation
     *          矩阵的封装类，一般使用来获得当前的矩阵对象
     */
    @Override
    protected void applyTransformation(
            float interpolatedTime,
            Transformation t) {
        final Matrix matrix = t.getMatrix();
        matrix.preScale(1,
                1 - interpolatedTime,   // 纵向比例不断缩小
                mCenterWidth,
                mCenterHeight); // 缩放中心
    }
}
