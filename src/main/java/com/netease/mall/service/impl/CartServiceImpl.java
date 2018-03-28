package com.netease.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.netease.mall.domain.CartProductVo;
import com.netease.mall.domain.HostHolder;
import com.netease.mall.service.CartService;
import com.netease.mall.service.RedisService;
import com.netease.mall.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,3:33
 * @update :
 */
@Service
@Transactional
public class CartServiceImpl implements CartService{

    private final static Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    RedisService redisService;

    @Autowired
    HostHolder hostHolder;

    @Override
    public void addCart(Map<String, Object> map) {
        int productId = (int) map.get("id");
        double price = (double) map.get("price");
        int num = (int) map.get("num");
        String title = (String) map.get("title");
        //判断是否在redis中存在
        if(redisService.isMember(RedisKeyUtil.getCartKey(hostHolder.getUser().getId()),productId+"")){
            //存在, 增加数目和价格
            String s = redisService.get(RedisKeyUtil.getCartKey(hostHolder.getUser().getId()),productId+"");
            JSONObject jsonObject = JSONObject.parseObject(s);
            CartProductVo cartProductVo = JSONObject.parseObject(String.valueOf(jsonObject),CartProductVo.class);
            cartProductVo.setProductId(productId);
            cartProductVo.setNum(cartProductVo.getNum()+num);
            cartProductVo.setPrice(price);
            cartProductVo.setTitle(title);
            cartProductVo.setBuyerId(hostHolder.getUser().getId());
            String cartProductJson = JSONObject.toJSONString(cartProductVo);
            System.out.println("增加数量"+cartProductJson);
            redisService.save(RedisKeyUtil.getCartKey(hostHolder.getUser().getId()), productId+"",cartProductJson);
        }else {
            //不存在,新增
            CartProductVo cartProductVo = new CartProductVo();
            cartProductVo.setProductId(productId);
            cartProductVo.setBuyerId(hostHolder.getUser().getId());
            cartProductVo.setTitle(title);
            cartProductVo.setPrice(price);
            cartProductVo.setNum(num);
            String cartProductJson = JSONObject.toJSONString(cartProductVo);
            System.out.println("新增"+cartProductJson);
            redisService.save(RedisKeyUtil.getCartKey(hostHolder.getUser().getId()), productId+"",cartProductJson);
        }
    }

    @Override
    public List<Map<String, Object>> list() {
        List<Map<String, Object>> cartViews = new LinkedList<Map<String, Object>>();
        Set<String> keys = redisService.keys(RedisKeyUtil.getCartKey(hostHolder.getUser().getId()));
        if (keys.size() > 0){
            for(String field : keys){
                String s = redisService.get(RedisKeyUtil.getCartKey(hostHolder.getUser().getId()),field);
                JSONObject jsonObject = JSONObject.parseObject(s);
                CartProductVo cartProductVo = JSONObject.parseObject(String.valueOf(jsonObject),CartProductVo.class);
                Map<String, Object> cartView = new HashMap<String, Object>();
                cartView.put("id", cartProductVo.getProductId());
                cartView.put("title", cartProductVo.getTitle());
                cartView.put("num", cartProductVo.getNum());
                cartView.put("price", cartProductVo.getPrice());
                cartViews.add(cartView);
            }
        }
        return cartViews;
    }

    @Override
    public void del(int userId) {
        redisService.del(RedisKeyUtil.getCartKey(userId));
    }
}
