package com.ysmork.blog.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 精确的浮点数运算
 * 
 * @author ruoyi
 */
public class Arith
{

    /** 默认除法运算精度 */
    private static final int DEF_DIV_SCALE = 10;

    /** 这个类不能实例化 */
    private Arith()
    {
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2)
    {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        if (b1.compareTo(BigDecimal.ZERO) == 0)
        {
            return BigDecimal.ZERO.doubleValue();
        }
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差 类型BigDecimal
     */
    public static BigDecimal subToDecimal(double v1,double v2){
        return new BigDecimal (Double.toString (sub (v1, v2)));
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差 类型BigDecimal
     */
    public static BigDecimal addToDecimal(double v1,double v2){
        return new BigDecimal (Double.toString (add (v1, v2)));
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round2(double v, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_UP);
    }

    /**
     *将Double Float String Integer int float double等
     *转换为BigDecimal，如果为null则为0
     * @param t
     * @return BigDecimal
     */
    public static<T> BigDecimal dicimal(T t)  {
        String format = String.valueOf (t==null?"0":t);
        return new BigDecimal (format);
    }

    /**
     * 任意两种类型的数相加
     * @param t1
     * @param t2
     * @return BigDecimal
     */
    public static<T> BigDecimal adds(T t1,T t2){
        BigDecimal add = dicimal (t1).add (dicimal (t2));
        return add;
    }

    /**
     * 任意两种类型的数相减
     * @param t1 被减数
     * @param t2 减数
     * @return BigDecimal
     */
    public static<T> BigDecimal subs(T t1,T t2){
        return dicimal (t1).subtract (dicimal (t2));
    }

    /**
     * 任意两种类型的数相乘
     * @param t1
     * @param t2
     * @return BigDecimal
     */
    public static<T> BigDecimal muls(T t1,T t2){
        return dicimal (t1).multiply (dicimal (t2));
    }

    /**
     * 任意两种类型的数相除(保留两位小数)
     * @param t1 被除数
     * @param t2 除数
     * @return BigDecimal
     */
    public static<T> BigDecimal divs(T t1,T t2){
        return dicimal (t1).divide (dicimal (t2),2,RoundingMode.HALF_UP);
    }


    /**
     * 任意类型的数相减
     * @param t
     * @param <T>
     * @return
     */
    public static<T> BigDecimal subsMany(T... t){
        BigDecimal total = dicimal (t[0]);
        for (int i = 1; i < t.length; i++) {
            total = subs (total,t[i]);
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println (dicimal (null));
    }
}
