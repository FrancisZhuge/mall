package com.netease.mall.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/27,16:28
 * @update :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private ProductService productService;
    @Test
    public void contextLoads() {
        System.out.println(productService.list());
    }

}
