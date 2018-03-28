package com.netease.mall.domain;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,11:24
 * @update :
 */
public class Cart {
    private int id;
    private int buyerId;
    private int productId;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
