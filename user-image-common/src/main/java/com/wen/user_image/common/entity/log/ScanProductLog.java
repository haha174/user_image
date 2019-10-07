package com.wen.user_image.common.entity.log;

import java.io.Serializable;

public class ScanProductLog implements Serializable {
    /**
     * 商品Id
     */
    private int productId;

    /**
     * 商品种类ID
     */
    private int productTypeId;

    /**
     * 浏览时间
     */

    private String scanTime;

    /**
     * 停留时间
     */
    private String stayTime;

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
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getScanTime() {
        return scanTime;
    }

    public void setScanTime(String scanTime) {
        this.scanTime = scanTime;
    }

    public String getStayTime() {
        return stayTime;
    }

    public void setStayTime(String stayTime) {
        this.stayTime = stayTime;
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
}
