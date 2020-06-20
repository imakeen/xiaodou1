package com.xinzu.xiaodou.bean;

import java.util.List;

public class OrPriceDetailBean {

    /**
     * actualAmount : 200.0
     * addedServiceList : [{"amount":100,"fixed":true,"isFixed":true,"price":50,"quantity":2,"serviceCode":"basicInsuranceFee","serviceDesc":"基本保障服务","serviceName":"基本保障服务","unit":"天"},{"amount":20,"fixed":true,"isFixed":true,"price":20,"quantity":1,"serviceCode":"ServiceCharge","serviceDesc":"手续费","serviceName":"手续费","unit":"元/次"}]
     * depositAmount : 0
     * diffStoreFee : 0
     * exceededFee : 0
     * message : OK
     * payAmount : 200
     * priceInfo : {"baleTotalPrice":0,"baleUnitPrice":0,"prepayFirstDayPrice":0,"prepayTotalPrice":0,"prepayUnitPrice":0,"quantity":2,"standardFirstDayPrice":0,"standardTotalPrice":80,"standardUnitPrice":40}
     * reduceAmount : 0
     * rentQuantity : 2
     * serviceFee : 0
     * status : 1
     * violationAmount : 0
     */

    private double actualAmount;
    private int depositAmount;
    private int diffStoreFee;
    private int exceededFee;
    private String message;
    private int payAmount;
    private PriceInfoBean priceInfo;
    private int reduceAmount;
    private int rentQuantity;
    private int serviceFee;
    private int status;
    private int violationAmount;
    private List<AddedServiceListBean> addedServiceList;

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public int getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(int depositAmount) {
        this.depositAmount = depositAmount;
    }

    public int getDiffStoreFee() {
        return diffStoreFee;
    }

    public void setDiffStoreFee(int diffStoreFee) {
        this.diffStoreFee = diffStoreFee;
    }

    public int getExceededFee() {
        return exceededFee;
    }

    public void setExceededFee(int exceededFee) {
        this.exceededFee = exceededFee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public PriceInfoBean getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(PriceInfoBean priceInfo) {
        this.priceInfo = priceInfo;
    }

    public int getReduceAmount() {
        return reduceAmount;
    }

    public void setReduceAmount(int reduceAmount) {
        this.reduceAmount = reduceAmount;
    }

    public int getRentQuantity() {
        return rentQuantity;
    }

    public void setRentQuantity(int rentQuantity) {
        this.rentQuantity = rentQuantity;
    }

    public int getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(int serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getViolationAmount() {
        return violationAmount;
    }

    public void setViolationAmount(int violationAmount) {
        this.violationAmount = violationAmount;
    }

    public List<AddedServiceListBean> getAddedServiceList() {
        return addedServiceList;
    }

    public void setAddedServiceList(List<AddedServiceListBean> addedServiceList) {
        this.addedServiceList = addedServiceList;
    }

    public static class PriceInfoBean {
        /**
         * baleTotalPrice : 0
         * baleUnitPrice : 0
         * prepayFirstDayPrice : 0
         * prepayTotalPrice : 0
         * prepayUnitPrice : 0
         * quantity : 2
         * standardFirstDayPrice : 0
         * standardTotalPrice : 80
         * standardUnitPrice : 40
         */

        private int baleTotalPrice;
        private int baleUnitPrice;
        private int prepayFirstDayPrice;
        private int prepayTotalPrice;
        private int prepayUnitPrice;
        private int quantity;
        private int standardFirstDayPrice;
        private int standardTotalPrice;
        private int standardUnitPrice;

        public int getBaleTotalPrice() {
            return baleTotalPrice;
        }

        public void setBaleTotalPrice(int baleTotalPrice) {
            this.baleTotalPrice = baleTotalPrice;
        }

        public int getBaleUnitPrice() {
            return baleUnitPrice;
        }

        public void setBaleUnitPrice(int baleUnitPrice) {
            this.baleUnitPrice = baleUnitPrice;
        }

        public int getPrepayFirstDayPrice() {
            return prepayFirstDayPrice;
        }

        public void setPrepayFirstDayPrice(int prepayFirstDayPrice) {
            this.prepayFirstDayPrice = prepayFirstDayPrice;
        }

        public int getPrepayTotalPrice() {
            return prepayTotalPrice;
        }

        public void setPrepayTotalPrice(int prepayTotalPrice) {
            this.prepayTotalPrice = prepayTotalPrice;
        }

        public int getPrepayUnitPrice() {
            return prepayUnitPrice;
        }

        public void setPrepayUnitPrice(int prepayUnitPrice) {
            this.prepayUnitPrice = prepayUnitPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getStandardFirstDayPrice() {
            return standardFirstDayPrice;
        }

        public void setStandardFirstDayPrice(int standardFirstDayPrice) {
            this.standardFirstDayPrice = standardFirstDayPrice;
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
    }

    public static class AddedServiceListBean {
        /**
         * amount : 100
         * fixed : true
         * isFixed : true
         * price : 50
         * quantity : 2
         * serviceCode : basicInsuranceFee
         * serviceDesc : 基本保障服务
         * serviceName : 基本保障服务
         * unit : 天
         */

        private int amount;
        private boolean fixed;
        private boolean isFixed;
        private int price;
        private int quantity;
        private String serviceCode;
        private String serviceDesc;
        private String serviceName;
        private String unit;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public boolean isFixed() {
            return fixed;
        }

        public void setFixed(boolean fixed) {
            this.fixed = fixed;
        }

        public boolean isIsFixed() {
            return isFixed;
        }

        public void setIsFixed(boolean isFixed) {
            this.isFixed = isFixed;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public String getServiceDesc() {
            return serviceDesc;
        }

        public void setServiceDesc(String serviceDesc) {
            this.serviceDesc = serviceDesc;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
