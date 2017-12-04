package com.imooc.customanim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

public class CustomAnim extends Animation {

    private int mCenterWidth;
    private int mCenterHeight;
    private Camera mCamera = new Camera();
    private float mRotateY = 0.0f;

    @Override
    public void initialize(int width,
                           int height,
                           int parentWidth,
                           int parentHeight) {

        super.initialize(width, height, parentWidth, parentHeight);
        // 设置默认时长
        setDuration(2000);
        // 动画结束后保留状态
        setFillAfter(true);
        // 设置默认插值器
        setInterpolator(new BounceInterpolator());
        mCenterWidth = width / 2;
        mCenterHeight = height / 2;
    }

    // 暴露接口-设置旋转角度
    public void setRotateY(float rotateY) {
        mRotateY = rotateY;     // Y轴旋转角度
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
        mCamera.save();
        // 使用Camera设置旋转的角度
        mCamera.rotateY(mRotateY * interpolatedTime);   // mRotateY = 0
        // 将旋转变换作用到matrix上
        mCamera.getMatrix(matrix);
        mCamera.restore();
        //这两行代码就是起到了这样的作用。
        // preTranslate方法的作用是在旋转之间先把图片向上移动图片高度的一半的距离，
        // 这样图片就关于x轴对称了，然后再进行旋转的变换，
        // postTranslate方法是在变换之后再将图片向下移动图片高度的一半的距离也即是回到了原来的位置，
        // 这样图片显示出来的结果就是对称的了。
        // 原理也很简单，旋转中心还是(0,0)，只不过我们移动图片，这样进行旋转变换的时候就会得到对称的结果了。
        matrix.preTranslate(mCenterWidth, mCenterHeight);
        matrix.postTranslate(-mCenterWidth, -mCenterHeight);
    }
}
