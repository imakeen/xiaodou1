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
        long diff = endDate.getTime() - nowDate.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        if (days == 0) {
            if (0 <= hours && hours <= 2) {
                return "-2";
            } else if (hours < 0) {
                return "-1";
            } else {
                return hours + "小时";
            }

        } else if (days > 0) {
            if (hours == 0) {
                return days + "天";
            } else {
                return days + "天" + hours + "小时";
            }
        } else {
            return "-1";
        }
    }
}
