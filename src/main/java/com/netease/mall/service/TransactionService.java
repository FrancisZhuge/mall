package com.netease.mall.service;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,2:45
 * @update :
 */
public interface TransactionService {

    boolean isBought(int buyerId, int productId);
}
