package com.bigheart.smithchart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * 自定义绘图控件
 */
public class MyDrawable extends Drawable {

    private final String TAG = "MyDrawable";
    private final int SCALE = 200, OVER_LEN = 50;
    private final float POINT_R = (float) 0.05;

    private float R, X;
    private int high, width;
    private Element.Circle circleR1, circleX1, circleR2, circleX2, circleF, circleP,
            circleE = new Element.Circle(0, 0, 1);
    private Point A, B;
    private float lLamdaRate = -100;


    /**
     * 归一化的原始阻抗点
     *
     * @param r
     * @param x
     */
    public MyDrawable(float r, float x) {
        R = r;
        X = x;
        lLamdaRate = -100;
        calculate();
    }

    /**
     * @param r
     * @param x
     * @param lLamda l/λ
     */
    public MyDrawable(float r, float x, float lLamda) {
        R = r;
        X = x;
        lLamdaRate = lLamda;
        calculate();
    }

    private void calculate() {
        high = width = 2 * SCALE;
        circleR1 = new Element.Circle(SCALE * (R / (1 + R * (float) 1.0)), 0, SCALE / (1 + R * (float) 1.0));
        circleX1 = new Element.Circle(SCALE, (float) SCALE / X, (float) SCALE / Math.abs(X));

        A = circleR1.crossAt(circleX1);
        Log.d(TAG, "location : " + A.toString());

        float AToODist = Element.distance(0, 0, A.x, A.y);
        circleF = new Element.Circle(0, 0, AToODist);//等反射系数圆
        float pR = (SCALE - AToODist) / (float) 2.0;
        circleP = new Element.Circle(AToODist + pR, 0, pR);//等电导圆


        if (!Element.isZero(lLamdaRate + 100)) {
            B = Element.getRotatePoint(A.x, A.y, 0, 0, lLamdaRate);


            float x2 = (B.x * B.x + B.y * B.y - SCALE * SCALE) / (2 * (B.x - SCALE));
            circleR2 = new Element.Circle(x2, 0, Element.distance(x2, 0, SCALE, 0));

            float y2 = ((B.x - SCALE) * (B.x - SCALE) + B.y * B.y) / (2 * B.y);
            circleX2 = new Element.Circle(SCALE, y2, y2);
        } else {
            B = null;
        }

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.translate(width, high);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(Color.BLACK);
        canvas.drawLine(-width / 2 - OVER_LEN, 0, width / 2 + OVER_LEN, 0, paint);
        canvas.drawLine(0, -high / 2 - OVER_LEN, 0, high / 2 + OVER_LEN, paint);


        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, circleE.r * SCALE, paint);


        paint.setColor(Color.BLUE);
        drawCircle(canvas, paint, circleR1);
        drawCircle(canvas, paint, circleX1);


        paint.setStyle(Paint.Style.FILL);
        drawPoint(canvas, paint, A);

        paint.setColor(Color.argb(255, 0, 200, 0));
        paint.setStyle(Paint.Style.STROKE);
        drawCircle(canvas, paint, circleF);
        drawCircle(canvas, paint, circleP);


        if (B != null) {
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            drawPoint(canvas, paint, B);

            paint.setStyle(Paint.Style.STROKE);
            drawCircle(canvas, paint, circleR2);
            drawCircle(canvas, paint, circleX2);
        }
    }

    public String getVSWR() {
        return "VSWR = " + ((1 / (circleP.r / SCALE)) - 1);
    }

    /**
     * 反射系数
     *
     * @return
     */
    public String getReflectionNum() {
        return "反射系数 = " + circleF.r / SCALE;
    }

    public String getInputImpedance() {
        if (B != null)
            return "输入阻抗 = " + ((1 / (circleR2.r / SCALE)) - 1) + " + " + 1 / (circleX2.r / SCALE) + "j";
        else
            return "输入错误";
    }


    /**
     * 画点
     *
     * @param canvas
     * @param paint
     * @param point
     */
    private void drawPoint(Canvas canvas, Paint paint, Point point) {
        canvas.drawCircle(point.x, -1 * point.y, POINT_R * SCALE, paint);
    }

    /**
     * 画圆
     *
     * @param canvas
     * @param paint
     * @param circle
     */
    private void drawCircle(Canvas canvas, Paint paint, Element.Circle circle) {
        canvas.drawCircle(circle.x, -1 * circle.y, circle.r, paint);
    }


    @Override
    public int getIntrinsicWidth() {
        return width * 2;
    }

    @Override
    public int getIntrinsicHeight() {
        return high * 2;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
