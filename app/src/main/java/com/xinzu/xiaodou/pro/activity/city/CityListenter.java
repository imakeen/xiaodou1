package com.xinzu.xiaodou.pro.activity.city;


public class CityListenter {
    public interface onListener {
        void select(String city_title, String city, String citycode,
                    String pickuplongitude,
                    String pickuplatitude);
    }

    public void setonListener(onListener onListener) {
        this.listener = onListener;
    }

    public static onListener listener;


}
