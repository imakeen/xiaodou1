package com.xinzu.xiaodou.bean;

public class CreatOrderBean {


    /**
     * appKey : xzcxzfb
     * channelId : 1
     * orderChannel : 1
     * orderSource : 2
     * payAmount : 195
     * payMode : 2
     * pickoffOndoorAddr : {"address":"河北省平山县"}
     * address : 河北省平山县
     * pickupCityCode : 130100
     * pickupDate : 2020-02-14 14:00
     * pickupOndoorAddr : {"address":"河北省平山县"}
     * pickupStoreCode : gehong072
     * priceType : 1
     * returnCityCode : 130100
     * returnDate : 2020-02-16 14:00
     * returnStoreCode : gehong072
     * sign : 861e961f046157e73efea61fd6bfbf9d
     * timeStamp : 1581651478866
     * totalDays : 共2天
     * userId : 503
     * userInfo : {"name":"张佳兴","idType":1,"idNo":"130131199710183316","mobile":"17600882560"}
     * idNo : 130131199710183316
     * idType : 1
     * mobile : 17600882560
     * name : 张佳兴
     * vehicleCode : zhonghua01
     */

    private String appKey;
    private int channelId;
    private int orderChannel;
    private int orderSource;
    private String payAmount;
    private int payMode;
    private PickoffOndoorAddrBean pickoffOndoorAddr;
    private String address;
    private String pickupCityCode;
    private String pickupDate;
    private PickupOndoorAddrBean pickupOndoorAddr;
    private String pickupStoreCode;
    private int priceType;
    private String returnCityCode;
    private String returnDate;
    private String returnStoreCode;
    private String sign;
    private String timeStamp;
    private String totalDays;
    private String userId;
    private UserInfoBean userInfo;

    private String vehicleCode;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(int orderChannel) {
        this.orderChannel = orderChannel;
    }

    public int getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(int orderSource) {
        this.orderSource = orderSource;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public int getPayMode() {
        return payMode;
    }

    public void setPayMode(int payMode) {
        this.payMode = payMode;
    }

    public PickoffOndoorAddrBean getPickoffOndoorAddr() {
        return pickoffOndoorAddr;
    }

    public void setPickoffOndoorAddr(PickoffOndoorAddrBean pickoffOndoorAddr) {
        this.pickoffOndoorAddr = pickoffOndoorAddr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPickupCityCode() {
        return pickupCityCode;
    }

    public void setPickupCityCode(String pickupCityCode) {
        this.pickupCityCode = pickupCityCode;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public PickupOndoorAddrBean getPickupOndoorAddr() {
        return pickupOndoorAddr;
    }

    public void setPickupOndoorAddr(PickupOndoorAddrBean pickupOndoorAddr) {
        this.pickupOndoorAddr = pickupOndoorAddr;
    }

    public String getPickupStoreCode() {
        return pickupStoreCode;
    }

    public void setPickupStoreCode(String pickupStoreCode) {
        this.pickupStoreCode = pickupStoreCode;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public String getReturnCityCode() {
        return returnCityCode;
    }

    public void setReturnCityCode(String returnCityCode) {
        this.returnCityCode = returnCityCode;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnStoreCode() {
        return returnStoreCode;
    }

    public void setReturnStoreCode(String returnStoreCode) {
        this.returnStoreCode = returnStoreCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public static class PickoffOndoorAddrBean {
        /**
         * address : 河北省平山县
         */

        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class PickupOndoorAddrBean {
        /**
         * address : 河北省平山县
         */

        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class UserInfoBean {
        /**
         * name : 张佳兴
         * idType : 1
         * idNo : 130131199710183316
         * mobile : 17600882560
         */

        private String name;
        private int idType;
        private String idNo;
        private String mobile;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIdType() {
            return idType;
        }

        public void setIdType(int idType) {
            this.idType = idType;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
