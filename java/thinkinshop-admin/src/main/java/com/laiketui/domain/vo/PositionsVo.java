package com.laiketui.domain.vo;

import net.coobird.thumbnailator.geometry.Position;

import java.awt.*;


/**
 * 位置坐标
 *
 * @author Trick
 * @date 2020/12/18 9:48
 */
public enum PositionsVo implements Position {

    /**
     * 顶部位置1
     *
     * @date 2020/12/18 9:57
     */
    TOP_CENTER1 {
        /**
         *  参数说明
         * @author Trick
         * @date 2020/12/18 9:57
         * @param var1 - 图片长度
         * @param var2 - 图片宽度
         * @param var3 -
         * @param var4 -
         * @param var5 -
         * @param var6 -
         * @param var7 -
         * @param var8 -
         * @return java.awt.Point
         */
        @Override
        public Point calculate(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
            int var9 = var1 / 2 - var3 / 2;
            return new Point(var9, 50);
        }
    },
    /**
     * 圆形暂未实现
     */
    CIRCULAR {
        @Override
        public Point calculate(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
            return new Point(0, 0);
        }
    },
    BOTTOM_CENTER1 {
        @Override
        public Point calculate(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
            int var9 = var1 / 2 - var3 / 2;
            int var10 = var2 - var4 - 100;
            return new Point(var9, var10);
        }
    };

    private PositionsVo() {
    }
}
