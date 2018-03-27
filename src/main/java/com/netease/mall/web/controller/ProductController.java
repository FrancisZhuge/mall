package com.netease.mall.web.controller;

import com.netease.mall.dao.TransactionDao;
import com.netease.mall.domain.HostHolder;
import com.netease.mall.domain.Product;
import com.netease.mall.domain.ProductTransactionVo;
import com.netease.mall.domain.User;
import com.netease.mall.service.ProductService;
import com.netease.mall.service.TransactionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,0:57
 * @update :
 */
@Controller
public class ProductController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping("/api/delete")
    @ResponseBody
    Map<String, Object> deleteContent(HttpSession session, @RequestParam(value = "id",required = false) String id){
        Map<String, Object> result = null;
        if (StringUtils.isBlank(id)){
            result = new HashMap<>();
            result.put("code",400);
            result.put("message","请输入ID");
            return result;
        }
        int productId;
        try {
            productId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            result = new HashMap<>();
            result.put("code",400);
            result.put("message","请输入整数ID");
            return result;
        }
        Product product = productService.getProductById(productId);
        if (product == null){
            result = new HashMap<>();
            result.put("code",400);
            result.put("message","请输入正确的ID");
            return result;
        }
        try {
            productService.deleteProductById(productId);
        } catch (Exception e) {
            result = new HashMap<>();
            result.put("code",400);
            result.put("message","删除失败");
            return result;
        }
        result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success!");
        return result;
    }

    @RequestMapping("/show")
    public String show(@RequestParam("id") String productId, Model model){
        User user = hostHolder.getUser();
        if(StringUtils.isNotBlank(productId)){
            if(transactionService.isBought(user.getId(),Integer.valueOf(productId))){
                //购买过
                ProductTransactionVo productTransactionVo = productService.getProductTranscationWithBuyByUserIdAndProductId(user.getId(),Integer.valueOf(productId));
            }else {
                //未购买过
                ProductTransactionVo productTransactionVo= productService.getProductTranscationById(Integer.valueOf(productId));
                model.addAttribute("product",productTransactionVo);
            }
        }
        return "show";
    }
}
