package com.netease.mall.service;

import java.util.Set;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,13:02
 * @update :
 */
public interface RedisService {
    //增删改查
    //增，改
    Long save(String key, String field, String value);

    //获取指定key给定域的值
    String get(String key, String field);

    Set<String> keys (String key);

    //哈希表中指定key给定域是否存在
    boolean isMember(String key, String field);

    Long del(String key);
}
