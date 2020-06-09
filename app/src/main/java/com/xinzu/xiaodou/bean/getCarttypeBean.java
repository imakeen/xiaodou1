package com.xinzu.xiaodou.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/6/9 10:53
 */
public class getCarttypeBean implements Parcelable {


    /**
     * appKey : xzcxzfb
     * carGroupId : 0
     * channelId : 1
     * orderChannel : 1
     * pickupDate : 2020-02-11 18:07
     * pickuplatitude : 38.027913
     * pickuplongitude : 114.502713
     * returnDate : 2020-02-12 18:07
     * returnlatitude : 38.027913
     * returnlongitude : 114.502713
     * sign : 6cbfb40ce123967d5e1fa1e0217264cf
     * storeList : [{"pickupCityCode":"130100","returnCityCode":"130100"}]
     * 0 : {"pickupCityCode":"130100","returnCityCode":"130100"}
     * timeStamp : 1581307054504
     */

    private String appKey;
    private int carGroupId;
    private int channelId;
    private int orderChannel;
    private String pickupDate;
    private String pickuplatitude;
    private String pickuplongitude;
    private String returnDate;
    private String returnlatitude; //维度
    private String returnlongitude;//经度
    private String sign;
    private String timeStamp;
    private List<StoreListBean> storeList;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public int getCarGroupId() {
        return carGroupId;
    }

    public void setCarGroupId(int carGroupId) {
        this.carGroupId = carGroupId;
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

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickuplatitude() {
        return pickuplatitude;
    }

    public void setPickuplatitude(String pickuplatitude) {
        this.pickuplatitude = pickuplatitude;
    }

    public String getPickuplongitude() {
        return pickuplongitude;
    }

    public void setPickuplongitude(String pickuplongitude) {
        this.pickuplongitude = pickuplongitude;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnlatitude() {
        return returnlatitude;
    }

    public void setReturnlatitude(String returnlatitude) {
        this.returnlatitude = returnlatitude;
    }

    public String getReturnlongitude() {
        return returnlongitude;
    }

    public void setReturnlongitude(String returnlongitude) {
        this.returnlongitude = returnlongitude;
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

    public List<StoreListBean> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreListBean> storeList) {
        this.storeList = storeList;
    }


    public static class StoreListBean implements Parcelable {
        /**
         * pickupCityCode : 130100
         * returnCityCode : 130100
         */

        private String pickupCityCode;
        private String returnCityCode;

        public String getPickupCityCode() {
            return pickupCityCode;
        }

        public void setPickupCityCode(String pickupCityCode) {
            this.pickupCityCode = pickupCityCode;
        }

        public String getReturnCityCode() {
            return returnCityCode;
        }

        public void setReturnCityCode(String returnCityCode) {
            this.returnCityCode = returnCityCode;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.pickupCityCode);
            dest.writeString(this.returnCityCode);
        }

        public StoreListBean() {
        }

        protected StoreListBean(Parcel in) {
            this.pickupCityCode = in.readString();
            this.returnCityCode = in.readString();
        }

        public static final Creator<StoreListBean> CREATOR = new Creator<StoreListBean>() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appKey);
        dest.writeInt(this.carGroupId);
        dest.writeInt(this.channelId);
        dest.writeInt(this.orderChannel);
        dest.writeString(this.pickupDate);
        dest.writeString(this.pickuplatitude);
        dest.writeString(this.pickuplongitude);
        dest.writeString(this.returnDate);
        dest.writeString(this.returnlatitude);
        dest.writeString(this.returnlongitude);
        dest.writeString(this.sign);
        dest.writeString(this.timeStamp);
        dest.writeList(this.storeList);
    }

    public getCarttypeBean() {
    }

    protected getCarttypeBean(Parcel in) {
        this.appKey = in.readString();
        this.carGroupId = in.readInt();
        this.channelId = in.readInt();
        this.orderChannel = in.readInt();
        this.pickupDate = in.readString();
        this.pickuplatitude = in.readString();
        this.pickuplongitude = in.readString();
        this.returnDate = in.readString();
        this.returnlatitude = in.readString();
        this.returnlongitude = in.readString();
        this.sign = in.readString();
        this.timeStamp = in.readString();
        this.storeList = new ArrayList<StoreListBean>();
        in.readList(this.storeList, StoreListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<getCarttypeBean> CREATOR = new Parcelable.Creator<getCarttypeBean>() {
        @Override
        public getCarttypeBean createFromParcel(Parcel source) {
            return new getCarttypeBean(source);
        }

        @Override
        public getCarttypeBean[] newArray(int size) {
            return new getCarttypeBean[size];
        }
    };
}
