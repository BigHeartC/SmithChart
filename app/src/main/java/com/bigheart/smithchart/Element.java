package com.bigheart.smithchart;

import android.graphics.Point;

/**
 * 定义圆类 以及一些 计算函数
 */
public class Element {

    public static class Circle {
        float x, y, r;

        Circle(float x, float y, float r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

        /**
         * 两圆的交点，剔除（1，0）点
         *
         * @param c2
         * @return
         */
        public Point crossAt(Circle c2) {
            double r1 = r;
            double r2 = c2.r;
            double L = distance(x, y, c2.x, c2.y);
            if (L > r1 + r2) return null;
            double dx = x - c2.x;
            double dy = y - c2.y;
            double dr = (r2 * r2 - r1 * r1 - dx * dx - dy * dy) / 2;
            double a = dx * dx + dy * dy;
            double b, c, d, x01, x02, y01, y02;
            if (isZero(dx)) {
                b = -2 * dx * dr;
                c = dr * dr - r1 * r1 * dy * dy;
                d = Math.sqrt(b * b - 4 * a * c);
                x01 = (-b + d) / (2 * a);
                x02 = (-b - d) / (2 * a);
                y01 = (dr - x01 * dx) / dy;
                y02 = (dr - x02 * dx) / dy;
            } else {
                b = -2 * dy * dr;
                c = dr * dr - r1 * r1 * dx * dx;
                d = Math.sqrt(b * b - 4 * a * c);
                y01 = (-b + d) / (2 * a);
                y02 = (-b - d) / (2 * a);
                x01 = (dr - y01 * dy) / dx;
                x02 = (dr - y02 * dy) / dx;
            }

            if (!isZero(x01 + x - 1) && !isZero(y01 + y))//剔除（1，0）点
                return new Point((int) (x01 + x), (int) (y01 + y));
            else
                return new Point((int) (x02 + x), (int) (y02 + y));
        }


    }

    public static boolean isZero(double dx) {
        return Math.abs(dx) < 0.001;
    }

    /**
     * 两点间距离
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static float distance(double x1, double y1, double x2, double y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    /**
     * （rotateX，rotateY）沿着（baseX，base）顺时针旋转 rotareAngel角度
     *
     * @param rotateX
     * @param rotateY
     * @param baseX
     * @param baseY
     * @param rotareAngel
     * @return
     */
    public static Point getRotatePoint(int rotateX, int rotateY, int baseX, int baseY, double rotareAngel) {
        double k = (2 * Math.PI - (4 * Math.PI * rotareAngel) % (2 * Math.PI));
        double x2 = (rotateX - baseX) * Math.cos(k) - (rotateY - baseY) * Math.sin(k) + baseX;
        double y2 = (rotateY - baseY) * Math.cos(k) + (rotateX - baseX) * Math.sin(k) + baseY;
        return new Point((int) Math.round(x2), (int) Math.round(y2));
    }
}
