package com.netease.mall.web.controller;

import com.netease.mall.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : francis
 * @description : 登录
 * @time : 2018/3/27,15:40
 * @update :
 */
@Controller
public class LoginController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param username
     * @param password
     * @param response
     * @return
     */
    @RequestMapping("/api/login")
    @ResponseBody
    public Map<String, Object> apiLogin(@RequestParam(value = "userName", required = false)String username,
                                        @RequestParam(value = "password", required = false)String password,
                                        HttpServletResponse response){
        Map<String, Object> map = null;
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            LOGGER.error("missing params");
            map = new HashMap<>();
            map.put("code",400);
            map.put("message","请输入用户名或者密码");
        }
        try {
             map = userService.login(username, password);
            if (map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                cookie.setMaxAge(3600*24*1);
                response.addCookie(cookie);
            }
        } catch (Exception e) {
            LOGGER.error("注册异常" + e.getMessage());
            map = new HashMap<>();
            map.put("message", "服务器错误");
        }
        return map;
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }

}
