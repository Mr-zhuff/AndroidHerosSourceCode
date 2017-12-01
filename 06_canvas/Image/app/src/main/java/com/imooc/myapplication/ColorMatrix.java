package com.imooc.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

public class ColorMatrix extends Activity {

    private ImageView mImageView;
    private GridLayout mGroup;
    private Bitmap bitmap;
    private int mEtWidth, mEtHeight;
    private EditText[] mEts = new EditText[20];
    private float[] mColorMatrix = new float[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_matrix);

        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test1);
        mImageView = (ImageView) findViewById(R.id.imageview);
        mGroup = (GridLayout) findViewById(R.id.group);
        mImageView.setImageBitmap(bitmap);

        //无法在onCreate()方法中获得视图的宽高值，可以通过View的post方法，在视图创建完毕获取
        /**
         * onCreate()方法被调用的时候会通过LayoutInflater将xml文件填充到ContentView。
         * 填充过程中只包括创建视图，不包括设置视图大小。
         */
        mGroup.post(new Runnable() {
            @Override
            public void run() {
                // 获取宽高信息
                mEtWidth = mGroup.getWidth() / 5;
                mEtHeight = mGroup.getHeight() / 4;
                addEts();       // 动态创建EditText
                initMatrix();   // 初始化颜色矩阵为初始状态
            }
        });
    }

    // 动态创建EditText，4x5的矩阵的值全部都为空
    private void addEts() {
        for (int i = 0; i < 20; i++) {
            EditText editText = new EditText(ColorMatrix.this);
            mEts[i] = editText;
            mGroup.addView(editText, mEtWidth, mEtHeight);
        }
    }

    // 初始化颜色矩阵为初始状态10000 10000 10000 10000
    private void initMatrix() {
        for (int i = 0; i < 20; i++) {
            if (i % 6 == 0) {
                mEts[i].setText(String.valueOf(1));
            } else {
                mEts[i].setText(String.valueOf(0));
            }
        }
    }

    // 获取矩阵值
    private void getMatrix() {
        for (int i = 0; i < 20; i++) {
            mColorMatrix[i] = Float.valueOf(mEts[i].getText().toString());
        }
    }

    // 将矩阵值设置到图像
    private void setImageMatrix() {
        Bitmap bmp = Bitmap.createBitmap(   // 不能修改android的原图，创建图片的副本
                bitmap.getWidth(),
                bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        android.graphics.ColorMatrix colorMatrix =
                new android.graphics.ColorMatrix();
        colorMatrix.set(mColorMatrix);  //将一个一维数组转化为ColorMartix

        /**
         * 使用Paint类的setColorFilter方法，将通过imageMatrix构造的colorMatrixColorFilter对象
         * 传递进去,并使用这个画笔来绘制原来的图像，从而将颜色矩阵作用到原图中。
         */
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        mImageView.setImageBitmap(bmp);
    }

    // 作用矩阵效果,将现有矩阵作用到图像
    public void btnChange(View view) {
        getMatrix();
        setImageMatrix();
    }

    // 重置矩阵效果，将现有矩阵恢复到初始状态，再作用到图像，即还原图像
    public void btnReset(View view) {
        initMatrix();
        getMatrix();
        setImageMatrix();
    }


}
