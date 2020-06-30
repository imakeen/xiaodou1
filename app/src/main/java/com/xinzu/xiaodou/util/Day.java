package com.xinzu.xiaodou.util;

import com.radish.baselibrary.utils.LogUtils;
import com.xinzu.xiaodou.bean.backTimeBean;
import com.xinzu.xiaodou.util.ChineseNumToArabicNumUtil;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

public class Day {
    static Calendar calendar;

    public static String pickcar_date(String dayofweek, boolean b_r) {

        calendar = Calendar.getInstance();
        if (b_r) {
            calendar.set(Calendar.MINUTE, 0);
            calendar.add(Calendar.HOUR, 3);
            calendar.add(Calendar.DAY_OF_MONTH, 0);
            //减填负数
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, +2);
            calendar.set(Calendar.MINUTE, 0);
            calendar.add(Calendar.HOUR, 3);
        }
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEEE", Locale.CHINESE);
        String format = simpleDateFormat1.format(calendar.getTime());
        String subst = format.substring(0, 10);
        String sub = format.substring(10);
        String subs2 = sub.substring(1, 6);
        String substring = sub.substring(10, 13);
            if (dayofweek.equals("day")) {
                return subst;
            } else {
                return substring + subs2;
            }

    }

    public static String[] getTitles() {
        String[] titles = new String[90];
//        String mMonth; // 月
//        String mDay;
        String week = "";
//
//        int current_day;
//        int current_month;
//        final Calendar c = Calendar.getInstance();
//        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        current_day = c.get(Calendar.DAY_OF_MONTH);
//        current_month = c.get(Calendar.MONTH);
        for (int i = 0; i < titles.length; i++) {
//            c.clear();
//            c.set(Calendar.MONTH, current_month);
//            c.set(Calendar.DAY_OF_MONTH, current_day);
//            c.add(Calendar.DATE, +i);//j记住是DATE
//            mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
//            mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前日份的日期号码

            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.DAY_OF_MONTH, 0);
            c1.add(Calendar.DATE, +i);

            switch (c1.get(Calendar.DAY_OF_WEEK) - 1) {
                case 0:
                    week = "天";
                    break;
                case 1:
                    week = "一";
                    break;
                case 2:
                    week = "二";
                    break;
                case 3:
                    week = "三";
                    break;
                case 4:
                    week = "四";
                    break;
                case 5:
                    week = "五";
                    break;
                case 6:
                    week = "六";
                    break;

            }
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM月dd日");
            String format = simpleDateFormat1.format(c1.getTime());
//            String date = mMonth + "月" + mDay + "日" + " 星期" + week;
            String date = format + " 星期" + week;
            titles[i] = date;
        }
        return titles;
    }


    public static String dialog_start(String month, String day) {
        calendar.clear();
        calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        SimpleDateFormat simpleDateFormatday = new SimpleDateFormat("MM月dd日");
        String format = simpleDateFormatday.format(calendar.getTime());
        return format;


    }

    public static String dialog_start_hour(String hour, String fen) {
        calendar.clear();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        SimpleDateFormat simpleDateFormatday = new SimpleDateFormat("HH");
        String format = simpleDateFormatday.format(calendar.getTime()) + ":" + fen;
        return format;
    }
}