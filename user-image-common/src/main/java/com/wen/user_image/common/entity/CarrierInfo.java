package com.wen.user_image.common.entity;

public class CarrierInfo {
    private String carrierName;
    private String carrierPhone;
    private long carrierCount;
    private String groupField;

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCarrierPhone() {
        return carrierPhone;
    }

    public void setCarrierPhone(String carrierPhone) {
        this.carrierPhone = carrierPhone;
    }

    public long getCarrierCount() {
        return carrierCount;
    }

    public void setCarrierCount(long carrierCount) {
        this.carrierCount = carrierCount;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }

    @Override
    public String  toString() {
        return "CarrierInfo{" +
                "carrierName='" + carrierName + '\'' +
                ", carrierPhone=" + carrierPhone +
                ", carrierCount=" + carrierCount +
                ", groupField='" + groupField + '\'' +
                '}';
    }
}
