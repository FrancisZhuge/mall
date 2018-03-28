package com.netease.mall.domain;

import java.util.Date;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,21:32
 * @update :
 */
public class Finance {
    private int id;
    private int buyerId;
    private int productId;
    private String title;
    private String image;
    private String buyTime;
    private double buyPrice;
    private int num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Finance{" +
                "id=" + id +
                ", buyerId=" + buyerId +
                ", productId=" + productId +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", buyTime='" + buyTime + '\'' +
                ", buyPrice=" + buyPrice +
                ", num=" + num +
                '}';
    }
}
