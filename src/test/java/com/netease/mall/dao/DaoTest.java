package com.netease.mall.dao;

import com.netease.mall.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/27,18:47
 * @update :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {

    @Autowired
    private UserDao userDao;
    @Test
    public void contextLoads() {
        System.out.println(userDao.getByName("seller"));
    }

}