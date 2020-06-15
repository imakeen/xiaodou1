package com.xinzu.xiaodou.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

public class cardTypeBean {
    /**
     * cardTypeList : [{"type":1,"typeName":"身份证"},{"type":2,"typeName":"护照"},{"type":7,"typeName":"回乡证 "},{"type":8,"typeName":"台胞证"}]
     * message : OK
     * status : 1
     */

    private String message;
    private int status;
    private List<CardTypeListBean> cardTypeList;

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

    public List<CardTypeListBean> getCardTypeList() {
        return cardTypeList;
    }

    public void setCardTypeList(List<CardTypeListBean> cardTypeList) {
        this.cardTypeList = cardTypeList;
    }

    public static class CardTypeListBean  implements IPickerViewData {
        /**
         * type : 1
         * typeName : 身份证
         */

        private int type;
        private String typeName;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        @Override
        public String getPickerViewText() {
            return typeName;
        }
    }
}
