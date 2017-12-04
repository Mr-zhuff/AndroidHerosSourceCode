package com.imooc.anim;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TimerTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
    }

    // 点击事件
    public void tvTimer(final View view) {
        // 构造并返回在int值之间动画的ValueAnimator。
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        // 将侦听器添加到在动画生命周期中发送更新事件的侦听器集，在计算动画的值之后，
        // 在动画的每个帧的所有侦听器上调用此方法。
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ((TextView) view).setText("$ " + (Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(10000);
        valueAnimator.start();
    }
}
