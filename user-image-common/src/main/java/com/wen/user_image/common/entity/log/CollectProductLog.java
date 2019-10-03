package com.wen.user_image.common.entity.log;

public class CollectProductLog {

    /**
     * 商品Id
     */
    private int productId;

    /**
     * 商品种类ID
     */
    private int productTypeId;

    /**
     * 操作时间
     */

    private String operatorTime;

    /**
     * 操作类型
     * 0 收藏 1 取消
     */
    private String operatorType;

    /**
     * 用户Id
     */
    private int userId;

    /**
     * 终端类别
     */

    private int userType;

    /**
     * ip 地址
     */
    private String ip;

    /**
     * 品牌
     */
    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(String operatorTime) {
        this.operatorTime = operatorTime;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
