package com.imooc.viewanim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 为视图增加透明度的变换动画
     * fromAlpha：开始时刻的透明度，取值范围0~1。
     * toAlpha：结束时刻的透明度，取值范围0~1。
     */
    public void btnAlpha(View view) {
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(5000);
        view.startAnimation(aa);
    }

    /**
     * 为视图增加旋转的变换动画
     * 旋转的起始角度和旋转中心的坐标
     */
    public void btnRotate(View view) {
        RotateAnimation ra = new RotateAnimation(0, 360, 100, 100); //旋转中心为（100,100）
        ra.setDuration(1000);
        view.startAnimation(ra);
    }

    public void btnRotateSelf(View view) {
        RotateAnimation ra = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F);    // 旋转中心为自身中心点
        ra.setDuration(1000);
        view.startAnimation(ra);
    }

    /**
     * 为视图移动增加位移动画，坐标系是以本身View的左上角为android坐标系原点
     * fromXDelta起始点X轴坐标，可以是数值、百分数、百分数p 三种样式，比如 50、50%、50%p，具体意义已在scale标签中讲述，这里就不再重讲
     * android:toXDelta结束点X轴坐标
     * fromYDelta起始点Y轴从标，可以是数值、百分数、百分数p 三种样式；
     * android:toYDelta结束点Y轴坐标
     */
    public void btnTranslate(View view) {
        TranslateAnimation ta = new TranslateAnimation(0, 200, 0, 300);
        ta.setDuration(1000);
        view.startAnimation(ta);
    }

    /**
     * 为视图的缩放增加动画效果，实现动态调控件尺寸   有正负方向
     * fromX 起始的X方向上相对自身的缩放比例，浮点值，比如1.0代表自身无变化，0.5代表起始时缩小一倍，2.0代表放大一倍；
     * toX 结尾的X方向上相对自身的缩放比例，浮点值；
     * fromY 起始的Y方向上相对自身的缩放比例，浮点值，
     * toY 结尾的Y方向上相对自身的缩放比例，浮点值；
     */
    public void btnScale(View view) {
        ScaleAnimation sa = new ScaleAnimation(0, 2, 0, 2);
        sa.setDuration(1000);
        view.startAnimation(sa);
    }

    // 旋转中心设置为自身
    public void btnScaleSelf(View view) {
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5F);
        sa.setDuration(1000);
        view.startAnimation(sa);
    }

    // 动画集合
    public void btnSet(View view) {
        AnimationSet as = new AnimationSet(true);
        as.setDuration(1000);

        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(5000);
        as.addAnimation(aa);

        TranslateAnimation ta = new TranslateAnimation(0, -100, 0, -200);
        ta.setDuration(1000);
        as.addAnimation(ta);

        view.startAnimation(as);
    }

}
