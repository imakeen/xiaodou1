package com.xinzu.xiaodou.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderdetailsBean implements Parcelable {

    /**
     * Displacement : 1.5L
     * PassengerNumber : 5
     * TransmissionType : 2
     * brandName : 比亚迪
     * cardType : 身份证
     * days : 2
     * displacement : 1.5L
     * driverName : 11111
     * idNo : 130406199807262715
     * image : https://image.xiaodouzuche.com/5d0467518ef98.jpg
     * orderState : 0
     * passengerNumber : 5
     * payMount : 200
     * payments : 0
     * phone : 15127016133
     * pickupAddress : 石家庄市人民政府
     * pickupCityName : 石家庄市
     * pickupDate : 2020-06-17 17:00
     * returnAddress : 石家庄市人民政府
     * returnCityName : 石家庄市
     * returnDate : 2020-06-19 17:00
     * status : 0
     * totalDays : 共2天
     * transmissionType : 2
     * vehicleName : F3 2015款 节能版舒适型
     */

    private String brandName;
    private String cardType;
    private int days;
    private String displacement;
    private String driverName;
    private String idNo;
    private String image;
    private int orderState;
    private int passengerNumber;
    private int payMount;
    private int payments;
    private String phone;
    private String pickupAddress;
    private String pickupCityName;
    private String pickupDate;
    private String returnAddress;
    private String returnCityName;
    private String returnDate;
    private int status;
    private String totalDays;
    private int transmissionType;
    private String vehicleName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public void setPassengerNumber(int passengerNumber) {
        this.passengerNumber = passengerNumber;
    }

    public int getPayMount() {
        return payMount;
    }

    public void setPayMount(int payMount) {
        this.payMount = payMount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getPickupCityName() {
        return pickupCityName;
    }

    public void setPickupCityName(String pickupCityName) {
        this.pickupCityName = pickupCityName;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(String returnAddress) {
        this.returnAddress = returnAddress;
    }

    public String getReturnCityName() {
        return returnCityName;
    }

    public void setReturnCityName(String returnCityName) {
        this.returnCityName = returnCityName;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }

    public int getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(int transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.brandName);
        dest.writeString(this.cardType);
        dest.writeInt(this.days);
        dest.writeString(this.displacement);
        dest.writeString(this.driverName);
        dest.writeString(this.idNo);
        dest.writeString(this.image);
        dest.writeInt(this.orderState);
        dest.writeInt(this.passengerNumber);
        dest.writeInt(this.payMount);
        dest.writeInt(this.payments);
        dest.writeString(this.phone);
        dest.writeString(this.pickupAddress);
        dest.writeString(this.pickupCityName);
        dest.writeString(this.pickupDate);
        dest.writeString(this.returnAddress);
        dest.writeString(this.returnCityName);
        dest.writeString(this.returnDate);
        dest.writeInt(this.status);
        dest.writeString(this.totalDays);
        dest.writeInt(this.transmissionType);
        dest.writeString(this.vehicleName);
    }

    public OrderdetailsBean() {
    }

    protected OrderdetailsBean(Parcel in) {
        this.brandName = in.readString();
        this.cardType = in.readString();
        this.days = in.readInt();
        this.displacement = in.readString();
        this.driverName = in.readString();
        this.idNo = in.readString();
        this.image = in.readString();
        this.orderState = in.readInt();
        this.passengerNumber = in.readInt();
        this.payMount = in.readInt();
        this.payments = in.readInt();
        this.phone = in.readString();
        this.pickupAddress = in.readString();
        this.pickupCityName = in.readString();
        this.pickupDate = in.readString();
        this.returnAddress = in.readString();
        this.returnCityName = in.readString();
        this.returnDate = in.readString();
        this.status = in.readInt();
        this.totalDays = in.readString();
        this.transmissionType = in.readInt();
        this.vehicleName = in.readString();
    }

    public static final Parcelable.Creator<OrderdetailsBean> CREATOR = new Parcelable.Creator<OrderdetailsBean>() {
        @Override
        public OrderdetailsBean createFromParcel(Parcel source) {
            return new OrderdetailsBean(source);
        }

        @Override
        public OrderdetailsBean[] newArray(int size) {
            return new OrderdetailsBean[size];
        }
    };
}
