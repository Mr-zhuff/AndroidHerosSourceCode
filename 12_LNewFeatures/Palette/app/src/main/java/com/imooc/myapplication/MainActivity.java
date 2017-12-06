package com.imooc.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

/**
 * 使用Palette首先需要在AndroidStudio中引用相关的依赖
 * 在Module Setting的Dependencies选项卡中添加com.android.support:palette-v7:21.0.2引用
 */

public class MainActivity extends Activity {

    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test);

//        //创建Palette对象
//        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                // 通过Palette来获取对应的色调
//                Palette.Swatch vibrant = palette.getVibrantSwatch();
//                // 将颜色设置给相应的组件
//                getActionBar().setBackgroundDrawable(
//                        new ColorDrawable(vibrant.getRgb()));
//                Window window = getWindow();
//                window.setStatusBarColor(vibrant.getRgb());
//            }
//        });
    }

    public void onClick(View view) {
        // 创建Palette对象
        Palette palette = Palette.generate(bitmap);
        Palette.Swatch vibrant = null;
        switch (view.getId()) {
            case R.id.bt1:
                // 通过Palette来获取对应的色调
                vibrant = palette.getVibrantSwatch();
                Toast.makeText(MainActivity.this, "充满活力的", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt2:
                vibrant= palette.getDarkVibrantSwatch();
                Toast.makeText(MainActivity.this, "充满活力的黑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt3:
                vibrant= palette.getLightVibrantSwatch();
                Toast.makeText(MainActivity.this, "充满活力的亮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt4:
                vibrant= palette.getMutedSwatch();
                Toast.makeText(MainActivity.this, "柔和的", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt5:
                vibrant= palette.getDarkMutedSwatch();
                Toast.makeText(MainActivity.this, "柔和的黑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt6:
                vibrant= palette.getDarkMutedSwatch();
                Toast.makeText(MainActivity.this, "柔和的亮", Toast.LENGTH_SHORT).show();
                break;
        }
        // 将颜色设置给相应的组件
        getActionBar().setBackgroundDrawable( new ColorDrawable(vibrant.getRgb()));
        Window window = getWindow();
        window.setStatusBarColor(vibrant.getRgb());
    }
}
