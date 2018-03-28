package com.netease.mall.web.controller;

import com.netease.mall.domain.HostHolder;
import com.netease.mall.domain.Product;
import com.netease.mall.domain.ProductTransactionVo;
import com.netease.mall.domain.User;
import com.netease.mall.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author : francis
 * @description : 首页
 * @time : 2018/3/27,10:55
 * @update :
 */
@Controller
public class IndexController {
    private final static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private ProductService productService;

    /**
     * 首页信息展示
     * @param model
     * @param type
     * @return
     */
    @RequestMapping(value = {"/","/index"})
    public String toIndex(Model model,
                          @RequestParam(value = "type",required = false)String type){
        User user = hostHolder.getUser();
        if (user == null){
            //未登录用户
            List<Product> productList = productService.list();
            model.addAttribute("productList",productList);
        }else if (0 == user.getUserType()){
            //用户是买家
            if (StringUtils.isBlank(type)|| !type.equals("1")){
                //所有内容
                List<ProductTransactionVo> productTransactionVos = productService.listProductWithBuyInfo(user.getId());
                model.addAttribute("productList", productTransactionVos);
            }else {
                List<ProductTransactionVo> productTransactionVos = productService.listAllUnboughtProducts(user.getId());
                model.addAttribute("productList", productTransactionVos);
            }
        }else if (1 == user.getUserType()){
            //用户是卖家
            List<ProductTransactionVo> productTransactionVoList = productService.getProductTranscationByUserId(user.getId());
            model.addAttribute("productList", productTransactionVoList);
        }
        return "index";
    }
}
