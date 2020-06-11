package com.xinzu.xiaodou.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2020/6/9 17:42
 */
public class CarBean {

    /**
     * message : OK
     * status : 1
     * storeList : [{"amount":948,"basicInsuranceFee":120,"brand":"英菲尼迪","carGroup":"SUV","diffStoreFee":0,"displacement":"2.5L","distance":0.9,"image":"https://xinzu.xinzuchuxing.com/2019/10/dc3dc201910242425.jpg","isVipCar":true,"nightServiceFee":0,"passengerNumber":5,"pickupStoreCode":"shandianhu016","pickupStoreName":"王府井","quantity":0,"returnStoreCode":"shandianhu016","serviceFee":30,"sesametype":0,"standardTotalPrice":0,"standardUnitPrice":0,"status":0,"transmissionType":"1","unitPrice":399,"vehicleCode":"QX50bj","vehicleName":"QX50 2015款 2.5L 尊享版(北京专用)","vipCar":true}]
     */

    private String message;
    private int status;
    private List<StoreListBean> storeList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<StoreListBean> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreListBean> storeList) {
        this.storeList = storeList;
    }

    public static class StoreListBean implements Parcelable {
        /**
         * amount : 948
         * basicInsuranceFee : 120
         * brand : 英菲尼迪
         * carGroup : SUV
         * diffStoreFee : 0
         * displacement : 2.5L
         * distance : 0.9
         * image : https://xinzu.xinzuchuxing.com/2019/10/dc3dc201910242425.jpg
         * isVipCar : true
         * nightServiceFee : 0
         * passengerNumber : 5
         * pickupStoreCode : shandianhu016
         * pickupStoreName : 王府井
         * quantity : 0
         * returnStoreCode : shandianhu016
         * serviceFee : 30
         * sesametype : 0
         * standardTotalPrice : 0
         * standardUnitPrice : 0
         * status : 0
         * transmissionType : 1
         * unitPrice : 399
         * vehicleCode : QX50bj
         * vehicleName : QX50 2015款 2.5L 尊享版(北京专用)
         * vipCar : true
         */

        private int amount;
        private int basicInsuranceFee;
        private String brand;
        private String carGroup;
        private int diffStoreFee;
        private String displacement;
        private double distance;
        private String image;
        private boolean isVipCar;
        private int nightServiceFee;
        private int passengerNumber;
        private String pickupStoreCode;
        private String pickupStoreName;
        private int quantity;
        private String returnStoreCode;
        private int serviceFee;
        private int sesametype;
        private int standardTotalPrice;
        private int standardUnitPrice;
        private int status;
        private String transmissionType;
        private int unitPrice;
        private String vehicleCode;
        private String vehicleName;
        private boolean vipCar;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getBasicInsuranceFee() {
            return basicInsuranceFee;
        }

        public void setBasicInsuranceFee(int basicInsuranceFee) {
            this.basicInsuranceFee = basicInsuranceFee;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCarGroup() {
            return carGroup;
        }

        public void setCarGroup(String carGroup) {
            this.carGroup = carGroup;
        }

        public int getDiffStoreFee() {
            return diffStoreFee;
        }

        public void setDiffStoreFee(int diffStoreFee) {
            this.diffStoreFee = diffStoreFee;
        }

        public String getDisplacement() {
            return displacement;
        }

        public void setDisplacement(String displacement) {
            this.displacement = displacement;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isIsVipCar() {
            return isVipCar;
        }

        public void setIsVipCar(boolean isVipCar) {
            this.isVipCar = isVipCar;
        }

        public int getNightServiceFee() {
            return nightServiceFee;
        }

        public void setNightServiceFee(int nightServiceFee) {
            this.nightServiceFee = nightServiceFee;
        }

        public int getPassengerNumber() {
            return passengerNumber;
        }

        public void setPassengerNumber(int passengerNumber) {
            this.passengerNumber = passengerNumber;
        }

        public String getPickupStoreCode() {
            return pickupStoreCode;
        }

        public void setPickupStoreCode(String pickupStoreCode) {
            this.pickupStoreCode = pickupStoreCode;
        }

        public String getPickupStoreName() {
            return pickupStoreName;
        }

        public void setPickupStoreName(String pickupStoreName) {
            this.pickupStoreName = pickupStoreName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getReturnStoreCode() {
            return returnStoreCode;
        }

        public void setReturnStoreCode(String returnStoreCode) {
            this.returnStoreCode = returnStoreCode;
        }

        public int getServiceFee() {
            return serviceFee;
        }

        public void setServiceFee(int serviceFee) {
            this.serviceFee = serviceFee;
        }

        public int getSesametype() {
            return sesametype;
        }

        public void setSesametype(int sesametype) {
            this.sesametype = sesametype;
        }

        public int getStandardTotalPrice() {
            return standardTotalPrice;
        }

        public void setStandardTotalPrice(int standardTotalPrice) {
            this.standardTotalPrice = standardTotalPrice;
        }

        public int getStandardUnitPrice() {
            return standardUnitPrice;
        }

        public void setStandardUnitPrice(int standardUnitPrice) {
            this.standardUnitPrice = standardUnitPrice;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTransmissionType() {
            return transmissionType;
        }

        public void setTransmissionType(String transmissionType) {
            this.transmissionType = transmissionType;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getVehicleCode() {
            return vehicleCode;
        }

        public void setVehicleCode(String vehicleCode) {
            this.vehicleCode = vehicleCode;
        }

        public String getVehicleName() {
            return vehicleName;
        }

        public void setVehicleName(String vehicleName) {
            this.vehicleName = vehicleName;
        }

        public boolean isVipCar() {
            return vipCar;
        }

        public void setVipCar(boolean vipCar) {
            this.vipCar = vipCar;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.amount);
            dest.writeInt(this.basicInsuranceFee);
            dest.writeString(this.brand);
            dest.writeString(this.carGroup);
            dest.writeInt(this.diffStoreFee);
            dest.writeString(this.displacement);
            dest.writeDouble(this.distance);
            dest.writeString(this.image);
            dest.writeByte(this.isVipCar ? (byte) 1 : (byte) 0);
            dest.writeInt(this.nightServiceFee);
            dest.writeInt(this.passengerNumber);
            dest.writeString(this.pickupStoreCode);
            dest.writeString(this.pickupStoreName);
            dest.writeInt(this.quantity);
            dest.writeString(this.returnStoreCode);
            dest.writeInt(this.serviceFee);
            dest.writeInt(this.sesametype);
            dest.writeInt(this.standardTotalPrice);
            dest.writeInt(this.standardUnitPrice);
            dest.writeInt(this.status);
            dest.writeString(this.transmissionType);
            dest.writeInt(this.unitPrice);
            dest.writeString(this.vehicleCode);
            dest.writeString(this.vehicleName);
            dest.writeByte(this.vipCar ? (byte) 1 : (byte) 0);
        }

        public StoreListBean() {
        }

        protected StoreListBean(Parcel in) {
            this.amount = in.readInt();
            this.basicInsuranceFee = in.readInt();
            this.brand = in.readString();
            this.carGroup = in.readString();
            this.diffStoreFee = in.readInt();
            this.displacement = in.readString();
            this.distance = in.readDouble();
            this.image = in.readString();
            this.isVipCar = in.readByte() != 0;
            this.nightServiceFee = in.readInt();
            this.passengerNumber = in.readInt();
            this.pickupStoreCode = in.readString();
            this.pickupStoreName = in.readString();
            this.quantity = in.readInt();
            this.returnStoreCode = in.readString();
            this.serviceFee = in.readInt();
            this.sesametype = in.readInt();
            this.standardTotalPrice = in.readInt();
            this.standardUnitPrice = in.readInt();
            this.status = in.readInt();
            this.transmissionType = in.readString();
            this.unitPrice = in.readInt();
            this.vehicleCode = in.readString();
            this.vehicleName = in.readString();
            this.vipCar = in.readByte() != 0;
        }

        public static final Parcelable.Creator<StoreListBean> CREATOR = new Parcelable.Creator<StoreListBean>() {
            @Override
            public StoreListBean createFromParcel(Parcel source) {
                return new StoreListBean(source);
            }

            @Override
            public StoreListBean[] newArray(int size) {
                return new StoreListBean[size];
            }
        };
    }
}
