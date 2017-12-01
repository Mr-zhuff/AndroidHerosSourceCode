package com.imooc.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class ImageHelper {

    public static Bitmap handleImageEffect(Bitmap bm,
                                           float hue,
                                           float saturation,
                                           float lum) {
        // android中不允许直接修改原图，通过原图创建一个同样大小的Bitmap，并将原图绘制到
        //该Bitmap中，以一个副本的形式修改图像
        Bitmap bmp = Bitmap.createBitmap(
                bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();

        /**
         * setRotate(int axis, float degree)设置颜色的色调
         * @parm  axis
         *          0,1,2分别表示Red，Green，Blue
         * @parm  degree
         *          需要处理的值
         */
        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        /**
         * saturationMatrix(float saturation) 设置颜色的饱和度
         */
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        /**
         * 亮度    三原色以相同的比例混合 会显示出白色  亮度为0  图像为黑
         */
        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        /**
         * postConcat()将矩阵的作用效果混合，从而叠加处理效果
         */
        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        /**
         * 使用Paint类的setColorFilter方法，将通过imageMatrix构造的colorMatrixColorFilter对象传递进去
         *    并使用这个画笔来绘制原来的图像，从而将颜色矩阵作用到原图中。
         */
        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);
        return bmp;
    }


    /**
     * public void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height)
     * 把位图的数据拷贝到pixels[]中。每一个都由一个表示颜色值的int值来表示。
     * 幅度参数表明调用者允许的像素数组行间距。对通常的填充结果，只要传递宽度值给幅度参数。
     * @param
     *      pixels      接收位图颜色值的数组
     *      offset      写入到pixels[]中的第一个像素索引值
     *      stride      pixels[]中的行间距个数值(必须大于等于位图宽度)。可以为负数
     *      x           从位图中读取的第一个像素的x坐标值。
     *      y           从位图中读取的第一个像素的y坐标值
     *      width    　 从每一行中读取的像素宽度
     *      height 　　 读取的行数
     */

    //底片效果
    public static Bitmap handleImageNegative(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color;
        int r, g, b, a;

        Bitmap bmp = Bitmap.createBitmap(width, height
                , Bitmap.Config.ARGB_8888);     //创建原图的副本

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            //底片效果rgb值处理
            r = 255 - r;
            g = 255 - g;
            b = 255 - b;

            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            newPx[i] = Color.argb(a, r, g, b);  //将新的RGBA值合成像素点
        }
        //将处理后的像素点数组重新set给Bitmap
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    //老照片效果
    public static Bitmap handleImagePixelsOldPhoto(Bitmap bm) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0;
        int r, g, b, a, r1, g1, b1;

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];

        bm.getPixels(oldPx, 0, bm.getWidth(), 0, 0, width, height);
        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            a = Color.alpha(color);
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);

            //老照片效果rgb值处理
            r1 = (int) (0.393 * r + 0.769 * g + 0.189 * b);
            g1 = (int) (0.349 * r + 0.686 * g + 0.168 * b);
            b1 = (int) (0.272 * r + 0.534 * g + 0.131 * b);

            if (r1 > 255) {
                r1 = 255;
            }
            if (g1 > 255) {
                g1 = 255;
            }
            if (b1 > 255) {
                b1 = 255;
            }

            newPx[i] = Color.argb(a, r1, g1, b1);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    //浮雕效果
    public static Bitmap handleImagePixelsRelief(Bitmap bm) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0, colorBefore = 0;
        int a, r, g, b;
        int r1, g1, b1;

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];

        bm.getPixels(oldPx, 0, bm.getWidth(), 0, 0, width, height);
        for (int i = 1; i < width * height; i++) {
            colorBefore = oldPx[i - 1];
            a = Color.alpha(colorBefore);
            r = Color.red(colorBefore);
            g = Color.green(colorBefore);
            b = Color.blue(colorBefore);

            color = oldPx[i];
            r1 = Color.red(color);
            g1 = Color.green(color);
            b1 = Color.blue(color);

            //浮雕效果rgb值处理
            r = (r - r1 + 127);
            g = (g - g1 + 127);
            b = (b - b1 + 127);

            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }
}
