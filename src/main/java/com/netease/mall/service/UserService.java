package com.netease.mall.service;

import java.util.Map;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/27,18:09
 * @update :
 */
public interface UserService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    Map<String, Object> login(String username, String password);


    void logout(String ticket);
}
