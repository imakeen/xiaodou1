package com.xinzu.xiaodou.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CarUserBean {

    /**
     * consumers : [{"consumerId":"1039","idNo":"130406199807262715","mobile":"15127016133","type":1,"userName":"111"},{"consumerId":"1040","idNo":"130406199807262715","mobile":"15127016133","type":1,"userName":"test"}]
     * message : OK
     * orderChannel : 0
     * status : 1
     */

    private String message;
    private int orderChannel;
    private int status;


    private List<ConsumersBean> consumers;

    private ConsumersBean consumersBean;

    public ConsumersBean getConsumersBean() {
        return consumersBean;
    }

    public void setConsumersBean(ConsumersBean consumersBean) {
        this.consumersBean = consumersBean;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(int orderChannel) {
        this.orderChannel = orderChannel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ConsumersBean> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<ConsumersBean> consumers) {
        this.consumers = consumers;
    }

    public static class ConsumersBean implements Parcelable {
        /**
         * consumerId : 1039
         * idNo : 130406199807262715
         * mobile : 15127016133
         * type : 1
         * userName : 111
         */

        private String consumerId;
        private String idNo;
        private String mobile;
        private int type;
        private String userName;

        public String getConsumerId() {
            return consumerId;
        }

        public void setConsumerId(String consumerId) {
            this.consumerId = consumerId;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.consumerId);
            dest.writeString(this.idNo);
            dest.writeString(this.mobile);
            dest.writeInt(this.type);
            dest.writeString(this.userName);
        }

        protected ConsumersBean(Parcel in) {
            this.consumerId = in.readString();
            this.idNo = in.readString();
            this.mobile = in.readString();
            this.type = in.readInt();
            this.userName = in.readString();
        }

        public static final Parcelable.Creator<ConsumersBean> CREATOR = new Parcelable.Creator<ConsumersBean>() {
            @Override
            public ConsumersBean createFromParcel(Parcel source) {
                return new ConsumersBean(source);
            }

            @Override
            public ConsumersBean[] newArray(int size) {
                return new ConsumersBean[size];
            }
        };
    }
}
