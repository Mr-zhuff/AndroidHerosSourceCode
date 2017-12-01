package com.imooc.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * 旗帜飞扬的效果
 *  图片中每个交织点的横坐标较之前不发生变化没纵坐标较之前坐标呈现一个三角形的周期性变化
 */
public class FlagBitmapMeshView extends View {

    private final int WIDTH = 200;
    private final int HEIGHT = 200;
    private int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    private float[] verts = new float[COUNT * 2];
    private float[] orig = new float[COUNT * 2];
    private Bitmap bitmap;
    private float A;
    private float k = 1;

    public FlagBitmapMeshView(Context context) {
        super(context);
        initView(context);
    }

    public FlagBitmapMeshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FlagBitmapMeshView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setFocusable(true);
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.test);
        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();

        int index = 0;
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = bitmapHeight * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = bitmapWidth * x / WIDTH;
                orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
                //人为将坐标+100 是为了让图像下移，避免扭曲后被屏幕遮挡
                orig[index * 2 + 1] = verts[index * 2 + 1] = fy + 100;
                index += 1;
            }
        }
        A = 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        flagWave();
        k += 0.1F;
        canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT,
                verts, 0, null, 0, null);   //绘制出来
        invalidate();
    }

    private void flagWave() {
        for (int j = 0; j <= HEIGHT; j++) {
            for (int i = 0; i <= WIDTH; i++) {
                verts[(j * (WIDTH + 1) + i) * 2 + 0] += 0;  //横坐标不变
                // 正弦函数sinx 改变交叉点纵坐标的值（利用正弦函数的周期性可以实现动态效果）
                float offsetY = (float) Math.sin((float) i / WIDTH * 2 * Math.PI +
                                Math.PI * k);
                verts[(j * (WIDTH + 1) + i) * 2 + 1] =
                        orig[(j * WIDTH + i) * 2 + 1] + offsetY * A;
            }
        }
    }
}