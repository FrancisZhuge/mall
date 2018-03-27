package com.netease.mall.domain;

import org.springframework.stereotype.Component;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/27,14:39
 * @update :
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }
    public void setUsers(User user){
        users.set(user);
    }
    public void clear(){
        users.remove();
    }
}