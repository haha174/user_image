package com.wen.user_image.common.entity;

/**
 * Created by li on 2019/1/6.
 */
public class UserTypeInfo {
    /**
     * 0 pc 1. 移动 2 小程序
     */
    private String userType;
    private long count;
    private String groupField;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }
}
