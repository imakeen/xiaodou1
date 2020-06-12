package com.xinzu.xiaodou.bean;

import java.util.List;

/**
 * Created by Administrator on 2020/6/9 15:01
 */
public  class CarGroupBean {

    /**
     * carGroupList : [{"carGroupId":6,"carGroupName":"商务车"},{"carGroupId":8,"carGroupName":"SUV"}]
     * message : OK
     * pickuplatitude : 0
     * pickuplongitude : 0
     * returnlatitude : 0
     * returnlongitude : 0
     * status : 1
     */

    private String message;
    private int pickuplatitude;
    private int pickuplongitude;
    private int returnlatitude;
    private int returnlongitude;
    private int status;
    private List<CarGroupListBean> carGroupList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPickuplatitude() {
        return pickuplatitude;
    }

    public void setPickuplatitude(int pickuplatitude) {
        this.pickuplatitude = pickuplatitude;
    }

    public int getPickuplongitude() {
        return pickuplongitude;
    }

    public void setPickuplongitude(int pickuplongitude) {
        this.pickuplongitude = pickuplongitude;
    }

    public int getReturnlatitude() {
        return returnlatitude;
    }

    public void setReturnlatitude(int returnlatitude) {
        this.returnlatitude = returnlatitude;
    }

    public int getReturnlongitude() {
        return returnlongitude;
    }

    public void setReturnlongitude(int returnlongitude) {
        this.returnlongitude = returnlongitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CarGroupListBean> getCarGroupList() {
        return carGroupList;
    }

    public void setCarGroupList(List<CarGroupListBean> carGroupList) {
        this.carGroupList = carGroupList;
    }

    public static class CarGroupListBean {
        /**
         * carGroupId : 6
         * carGroupName : 商务车
         */

        private int carGroupId;
        private String carGroupName;

        public int getCarGroupId() {
            return carGroupId;
        }

        public void setCarGroupId(int carGroupId) {
            this.carGroupId = carGroupId;
        }

        public String getCarGroupName() {
            return carGroupName;
        }

        public void setCarGroupName(String carGroupName) {
            this.carGroupName = carGroupName;
        }
    }
}
