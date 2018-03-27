package com.netease.mall.service.impl;

import com.netease.mall.dao.TransactionDao;
import com.netease.mall.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,2:45
 * @update :
 */
@Service
public class TransactionServiceImpl implements TransactionService{

    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    private TransactionDao transactionDao;

    @Override
    public boolean isBought(int buyerId, int productId) {
        Integer count = transactionDao.getTransactionCountByProductIdAndUserId(productId, buyerId);
        if(count == null || count == 0){
            return false;
        }
        return true;
    }
}
