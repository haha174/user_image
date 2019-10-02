package com.wen.user_image.job.entity;

import java.util.List;

/**
 * 用户败家指数的实体类
 */
public class BlackSheepEntity {
    /*
      * 区间   0~20  ， 20~50 ， 50~70， 70 ~ 80 ， 80~90， 90~100
     */
    private String blackSheepScore;  //
    private long count;
    private String groupField;

    private List<BlackSheepEntity> BlackSheepList;

    public List<BlackSheepEntity> getBlackSheepList() {
        return BlackSheepList;
    }

    public void setBlackSheepList(List<BlackSheepEntity> blackSheepList) {
        BlackSheepList = blackSheepList;
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

    /**
     * 用户Id
     */
    private String userId;
    /**
     * 支付金额
     */
    private String amt ;
    /**
     * 支付方式
     */
    private String payType ;
    /**
     * 支付时间
     */
    private String payTime;
    /**
     * 支付状态
     */
    private String payStatus;//0、未支付 1、已支付 2、已退款
    private String couponAmt;
    /**
     *
     */
    private String totalAmt;
    /**
     *
     */
    private String refundAmt;
    /**
     * 创建时间
     */
    private String createTime;

    public String getBlackSheepScore() {
        return blackSheepScore;
    }

    public void setBlackSheepScore(String blackSheepScore) {
        this.blackSheepScore = blackSheepScore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getCouponAmt() {
        return couponAmt;
    }

    public void setCouponAmt(String couponAmt) {
        this.couponAmt = couponAmt;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(String refundAmt) {
        this.refundAmt = refundAmt;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
