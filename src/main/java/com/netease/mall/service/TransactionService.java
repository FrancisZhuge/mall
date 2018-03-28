package com.netease.mall.service;

import com.netease.mall.domain.Finance;
import com.netease.mall.domain.Transaction;

import java.util.List;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,2:45
 * @update :
 */
public interface TransactionService {

    boolean isBought(int buyerId, int productId);

    List<Transaction> saveAllTransactions(List<Transaction> transactions);

    List<Finance> listFinanceByBuyerId(int buyerId);
}
