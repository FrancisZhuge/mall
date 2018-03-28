package com.netease.mall.service;

import com.netease.mall.domain.CartProductVo;


import java.util.List;
import java.util.Map;
/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,3:33
 * @update :
 */
public interface CartService {
    void addCart(Map<String, Object> map);

    List<Map<String, Object>> list();

    void del(int userId);
}
