package com.bbz.filetransform.util;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-5
 * Time: 下午6:24
 */
public class Util{
    /**
     * 将第一个字符转换成大写
     *
     * @param str 源字符串
     * @return 转换后的字符串
     */
    public static String firstToUpperCase(String str) {
        return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
    }

    public static String firstToLowCase(String src) {
        return src.replaceFirst(src.substring(0, 1), src.substring(0, 1).toLowerCase());
    }

    public static boolean isExist(String path) {
        return (new File(path).exists());
    }


    /**
     * 主要用于getCount函数中get的生成，重点在于要考虑boolean值的生成
     * isMax字段相应的get，set函数分别是
     * isMax()
     * setMax()
     */
    public static String genGet(String paraName) {
        if (paraName.startsWith("is") || paraName.startsWith("Is")) {

            return Util.firstToLowCase(paraName);
        } else {
            return "get" + Util.firstToUpperCase(paraName);
        }
    }


    /**
     * 主要用于setCount函数中get的生成，重点在于要考虑boolean值的生成
     * isMax字段相应的get，set函数分别是
     * isMax()
     * setMax()
     */
    public static String genSet(String paraName) {
        if (paraName.toLowerCase().startsWith("is")) {

            return "set" + paraName.substring(2);//去掉is
        } else {
            return "set" + Util.firstToUpperCase(paraName);
        }
    }
}
