package com.wen.user_image.common.entity;

/**
 * Created by li on 2019/1/6.
 */
public class BrandLike {
    private String brand;
    private long count;
    private String groupField;

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
