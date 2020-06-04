package com.xinzu.xiaodou.bean;

import java.io.Serializable;
import java.util.Calendar;

public class backTimeBean {
    String qu_day;
    String qu_week_time;
    String back_day;
    String back_week_time;

    public String getBack_day() {
        return back_day;
    }

    public void setBack_day(String back_day) {
        this.back_day = back_day;
    }

    public String getBack_week_time() {
        return back_week_time;
    }

    public void setBack_week_time(String back_week_time) {
        this.back_week_time = back_week_time;
    }

    public String getQu_day() {
        return qu_day;
    }

    public void setQu_day(String qu_day) {
        this.qu_day = qu_day;
    }

    public String getQu_week_time() {
        return qu_week_time;
    }

    public void setQu_week_time(String qu_week_time) {
        this.qu_week_time = qu_week_time;
    }
}
