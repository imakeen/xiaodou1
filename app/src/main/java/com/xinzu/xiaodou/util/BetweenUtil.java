package com.xinzu.xiaodou.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2020/6/6 11:04
 */
public class BetweenUtil {


    public static String getDatePoor(Date endDate, Date nowDate) {
        long diff = endDate.getTime()-nowDate.getTime() ;
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);


        return days + "天" + hours+ "小时";
    }
}
