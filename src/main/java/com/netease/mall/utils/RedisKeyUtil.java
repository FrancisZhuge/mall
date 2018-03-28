package com.netease.mall.utils;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,15:55
 * @update :
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String MALL_CART = "CART";

    public static String getCartKey(int buyerId){
        return MALL_CART + SPLIT + String.valueOf(buyerId);
    }

}
