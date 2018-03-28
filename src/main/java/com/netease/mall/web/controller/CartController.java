package com.netease.mall.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.mall.domain.Finance;
import com.netease.mall.domain.HostHolder;
import com.netease.mall.domain.Transaction;
import com.netease.mall.service.CartService;
import com.netease.mall.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,3:32
 * @update :
 */
@Controller
public class CartController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    CartService cartService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping("/api/addCart")
    @ResponseBody
    public Map<String, Object> addCart(@RequestBody Map<String, Object> map,
                                       HttpServletResponse response){
        Map<String, Object> result = new HashMap<>();
        System.out.println(map);
        if (map != null){
            cartService.addCart(map);
            response.setStatus(200);
            result.put("code", 200);
            result.put("message", "加入购物车成功");
            return result;
        }else {
            response.setStatus(400);
            result.put("code",400);
            result.put("message","无商品添加");
            return result;
        }
    }

    @RequestMapping("/settleAccount")
    public String settleAccount(Model model,HttpServletResponse response) throws Exception{
        List<Map<String, Object>> cartProductVos = null;
        try {
             cartProductVos = cartService.list();

        } catch (Exception e) {
            LOGGER.error("list failed",e.getMessage());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(cartProductVos);
        String urlEncodedJsonString = URLEncoder.encode(jsonString, "utf-8");
        Cookie messageCookie = new Cookie("products", urlEncodedJsonString);
        response.addCookie(messageCookie);
        return "settleAccount";
    }

    @RequestMapping("/api/buy")
    @ResponseBody
    public Map<String, Object> buy(@RequestBody List<Map<String, Object>> maps){
        Map<String, Object> result = new HashMap<>();
        List<Transaction> transactions = new LinkedList<>();
        for(Map<String, Object> map:maps){
            Transaction transaction = new Transaction();
            transaction.setBuyTime(new Date());
            transaction.setBuyPrice((Double)map.get("price"));
            transaction.setProductId((Integer)map.get("id"));
            transaction.setNum((Integer)map.get("number"));
            transaction.setBuyerId(hostHolder.getUser().getId());
            transactions.add(transaction);
        }
        transactionService.saveAllTransactions(transactions);
        cartService.del(hostHolder.getUser().getId());
        result.put("code", 200);
        result.put("message", "购买成功");
        System.out.println("here");
        return result;
    }

    @RequestMapping("/account")
    public String account(Model model){
        try {
            List<Finance> finances = transactionService.listFinanceByBuyerId(hostHolder.getUser().getId());
            System.out.println(finances);
            model.addAttribute("buyList",finances);
        } catch (Exception e) {
            LOGGER.error("list failed");
        }
        return "account";
    }
}

















