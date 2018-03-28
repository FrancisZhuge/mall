package com.netease.mall.service.impl;

import com.netease.mall.dao.ProductDao;
import com.netease.mall.dao.TransactionDao;
import com.netease.mall.domain.Finance;
import com.netease.mall.domain.Product;
import com.netease.mall.domain.Transaction;
import com.netease.mall.service.TransactionService;
import com.netease.mall.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

    @Autowired
    private ProductDao productDao;

    @Override
    public boolean isBought(int buyerId, int productId) {
        Integer count = transactionDao.getTransactionCountByProductIdAndUserId(productId, buyerId);
        if(count == null || count == 0){
            return false;
        }
        return true;
    }

    @Override
    public List<Transaction> saveAllTransactions(List<Transaction> transactions) {
        for (Transaction transaction:transactions){
            transactionDao.saveTransaction(transaction);
        }
        return transactions;
    }

    @Override
    public List<Finance> listFinanceByBuyerId(int buyerId) {
        List<Transaction> transactions = transactionDao.getAllTransactionsByBuyerId(buyerId);
        List<Finance> result = new LinkedList<>();
        if(transactions!=null){
            for(Transaction transaction:transactions){
                Finance finance = new Finance();
                finance.setId(transaction.getId());
                finance.setProductId(transaction.getProductId());
                finance.setBuyTime(DateUtils.date2String(transaction.getBuyTime()));
                finance.setBuyPrice(transaction.getBuyPrice());
                finance.setNum(transaction.getNum());
                Product product = productDao.getProductByProductId(transaction.getProductId());
                finance.setImage(product.getImage());
                finance.setTitle(product.getTitle());
                result.add(finance);
            }
        }
        return result;
    }
}
