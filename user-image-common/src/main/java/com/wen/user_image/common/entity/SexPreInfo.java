package com.wen.user_image.common.entity;

/**
 * 性别预测
 */
public class SexPreInfo {

    /**
     * 用户id 订单次数 订单频次 浏览男装
     * 浏览小孩 浏览老人 浏览女士 订单平均金额 浏览商品频次 标签
     */
    private int userId;
    private long orderNum;//订单的总数
    private long orderFre;//隔多少天下单
    private int manClothes;//浏览男装次数
    private int womenClothes;//浏览女装的次数
    private int childClothes;//浏览小孩衣服的次数
    private int oldmanClothes;//浏览老人的衣服的次数
    private double avrAmt;//订单平均金额
    private int productTimes;//每天浏览商品数
    private int label;//男，女

    private String groupField;//分组

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public long getOrderFre() {
        return orderFre;
    }

    public void setOrderFre(long orderFre) {
        this.orderFre = orderFre;
    }

    public int getManClothes() {
        return manClothes;
    }

    public void setManClothes(int manClothes) {
        this.manClothes = manClothes;
    }

    public int getWomenClothes() {
        return womenClothes;
    }

    public void setWomenClothes(int womenClothes) {
        this.womenClothes = womenClothes;
    }

    public int getChildClothes() {
        return childClothes;
    }

    public void setChildClothes(int childClothes) {
        this.childClothes = childClothes;
    }

    public int getOldmanClothes() {
        return oldmanClothes;
    }

    public void setOldmanClothes(int oldmanClothes) {
        this.oldmanClothes = oldmanClothes;
    }

    public double getAvrAmt() {
        return avrAmt;
    }

    public void setAvrAmt(double avrAmt) {
        this.avrAmt = avrAmt;
    }

    public int getProductTimes() {
        return productTimes;
    }

    public void setProductTimes(int productTimes) {
        this.productTimes = productTimes;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }
}
