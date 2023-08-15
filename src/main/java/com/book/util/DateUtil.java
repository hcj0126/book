package com.book.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * 时间处理工具
 */
public class DateUtil {
    //日期格式化
    private static SimpleDateFormat sdf = new SimpleDateFormat();

    /**
     * 字符串转换为时间
     * @param strDate
     * @param pattern
     * @return
     */
    public static Date parseDate(String strDate,String pattern){
        //创建时间对象
        Date date = null;
        //设置时间格式
        sdf.applyPattern(pattern);
        try {
            //将字符串转换为时间
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    /**
     * 时间转字符串
     * @param date
     * @param pattern
     * @return
     */
    public static String dateFormat(Date date,String pattern){
        sdf.applyPattern(pattern);
        String strDate = sdf.format(date);
        return strDate;
    }

}

