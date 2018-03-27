package com.netease.mall.service.impl;

import com.netease.mall.dao.TicketDao;
import com.netease.mall.dao.UserDao;
import com.netease.mall.domain.Ticket;
import com.netease.mall.domain.User;
import com.netease.mall.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/27,18:10
 * @update :
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private TicketDao ticketDao;

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("message","用户名不能为空");
            map.put("code",400);
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("message","密码不能为空");
            map.put("code",400);
            return map;
        }
        User user = userDao.getByName(username);
        if (user == null){
            map.put("message","用户名不存在");
            map.put("code",400);
            return map;
        }
        if(!password.equals(user.getPassword())){
            map.put("message", "密码不正确");
            map.put("code",400);
            return map;
        }
        map.put("code",200);
        //发t票
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        map.put("userId",user.getId());
        return map;
    }

    @Override
    public void logout(String ticket) {
        ticketDao.updateStatus(ticket,1);
    }

    /**
     * 将ticket加入到数据库
     * @param userId
     * @return
     */
    private String addLoginTicket(int userId){
        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        ticketDao.saveTicket(ticket);
        return ticket.getTicket();
    }

}
