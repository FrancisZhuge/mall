package com.netease.mall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,22:05
 * @update :
 */
public class DateUtils {
    public static String date2String(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(date);
        return format;
    }

    public static void main(String[] args) {
        System.out.println(date2String(new Date()));
    }
}
