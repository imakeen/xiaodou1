package com.xinzu.xiaodou.pro.fragment.home.utils;

import com.radish.baselibrary.utils.LogUtils;
import com.xinzu.xiaodou.bean.backTimeBean;
import com.xinzu.xiaodou.util.ChineseNumToArabicNumUtil;

import java.util.Calendar;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

public class Day {

    public static String pickcar_date(String dayofweek, boolean b_r) {
        Calendar c1 = Calendar.getInstance();


        if (b_r) {
            c1.set(Calendar.MINUTE, 0);
            c1.add(Calendar.HOUR, 3); //减填负数
        } else {
            c1.add(Calendar.DAY_OF_MONTH, +2);
            c1.set(Calendar.MINUTE, 0);
            c1.add(Calendar.HOUR, 3);
        }
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEEE");
        String format = simpleDateFormat1.format(c1.getTime());
        String subst = format.substring(0, 10);
        String sub = format.substring(10);
        String subs2 = sub.substring(1, 6);
        String substring = sub.substring(10, 13);
        if (b_r) {
            if (dayofweek.equals("day")) {
                return subst;
            } else {
                return substring + subs2;
            }

        } else {
            if (dayofweek.equals("day")) {
                return subst;
            } else {
                return substring + subs2;
            }
        }
    }

    public static String[] getTitles() {
        String[] titles = new String[90];
        String mMonth; // 月
        String mDay;
        String week = "";

        int current_day;
        int current_month;
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        current_day = c.get(Calendar.DAY_OF_MONTH);
        current_month = c.get(Calendar.MONTH);
        for (int i = 0; i < titles.length; i++) {
            c.clear();
            c.set(Calendar.MONTH, current_month);
            c.set(Calendar.DAY_OF_MONTH, current_day);


            c.add(Calendar.DATE, +i);//j记住是DATE
            mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
            mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前日份的日期号码

            Calendar c1 = Calendar.getInstance();
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
            String date = format + " 星期" + week;
            titles[i] = date;
        }
        return titles;
    }

}