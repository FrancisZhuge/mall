package com.netease.mall.service.impl;

import com.netease.mall.dao.ProductDao;
import com.netease.mall.dao.TransactionDao;
import com.netease.mall.domain.Product;
import com.netease.mall.domain.ProductTransactionVo;
import com.netease.mall.domain.Transaction;
import com.netease.mall.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/27,16:03
 * @update :
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private final static Logger LOGGER = LoggerFactory.getLogger(Product.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public List<Product> list() {
        List<Product> productList = null;
        try {
            productList = productDao.list();
        } catch (Exception e) {
            LOGGER.error("list() err.");
        }
        return productList;
    }

    @Override
    public List<ProductTransactionVo> getProductTranscationByUserId(int sellerId) {
        List<Product> products = productDao.getByUserId(sellerId);
        List<ProductTransactionVo> result = null;
        if (products != null){
            result = new LinkedList<>();
            for(Product product:products){
                Integer count = transactionDao.getTransactionCountByProductId(product.getId());
                ProductTransactionVo productTransactionVo = new ProductTransactionVo();
                productTransactionVo.setId(product.getId());
                productTransactionVo.setSellerId(product.getSellerId());
                productTransactionVo.setPrice(product.getPrice());
                productTransactionVo.setTitle(product.getTitle());
                productTransactionVo.setImage(product.getImage());
                productTransactionVo.setSummary(product.getSummary());
                productTransactionVo.setContent(product.getContent());
                if (count == null){
                    productTransactionVo.setNum(0);
                    productTransactionVo.setIsSell(false);
                }else {
                    productTransactionVo.setNum(count);
                    productTransactionVo.setIsSell(true);
                }
                result.add(productTransactionVo);
            }
        }
        return result;
    }

    @Override
    public Product getProductById(int productId) {
        Product product = productDao.getProductByProductId(productId);
        return product;
    }

    @Override
    public void deleteProductById(int productId) {
        try {
            //删除商品信息
            productDao.deleteProductById(productId);
            //删除交易信息
            transactionDao.deleteTransactionByProductId(productId);
        } catch (Exception e) {
            LOGGER.error("delete err.");
        }
    }

    @Override
    public List<ProductTransactionVo> listProductWithBuyInfo(int buyerId) {
        List<Product> products = productDao.list();
        List<ProductTransactionVo> result = null;
        if (products != null){
            result = new LinkedList<>();
            for(Product product: products){
                Integer count = transactionDao.getTransactionCountByProductIdAndUserId(product.getId(),buyerId);
                ProductTransactionVo productTransactionVo = new ProductTransactionVo();
                productTransactionVo.setId(product.getId());
                productTransactionVo.setSellerId(product.getSellerId());
                productTransactionVo.setPrice(product.getPrice());
                productTransactionVo.setTitle(product.getTitle());
                productTransactionVo.setImage(product.getImage());
                productTransactionVo.setSummary(product.getSummary());
                productTransactionVo.setContent(product.getContent());
                if (count == null){
                    productTransactionVo.setNum(0);
                    productTransactionVo.setIsBuy(false);
                }else {
                    productTransactionVo.setNum(count);
                    productTransactionVo.setIsBuy(true);
                }
                result.add(productTransactionVo);
            }
        }
        return result;
    }

    @Override
    public List<ProductTransactionVo> listAllUnboughtProducts(int buyerId) {
        List<Product> products = productDao.listAllUnboughtProductsByBuyerId(buyerId);
        List<ProductTransactionVo> result = null;
        if (products != null) {
            result = new LinkedList<>();
            for (Product product : products) {
                ProductTransactionVo productTransactionVo = new ProductTransactionVo();
                productTransactionVo.setId(product.getId());
                productTransactionVo.setSellerId(product.getSellerId());
                productTransactionVo.setPrice(product.getPrice());
                productTransactionVo.setTitle(product.getTitle());
                productTransactionVo.setImage(product.getImage());
                productTransactionVo.setSummary(product.getSummary());
                productTransactionVo.setContent(product.getContent());
                productTransactionVo.setIsBuy(false);
                result.add(productTransactionVo);
            }
        }
        return result;
    }

    @Override
    public ProductTransactionVo getProductTranscationById(int productId) {
        Product product = productDao.getProductByProductId(productId);
        System.out.println(product);
        if(product == null)
            return null;
        ProductTransactionVo productTransactionVo = new ProductTransactionVo();
        productTransactionVo.setId(product.getId());
        productTransactionVo.setSellerId(product.getSellerId());
        productTransactionVo.setPrice(product.getPrice());
        productTransactionVo.setTitle(product.getTitle());
        productTransactionVo.setImage(product.getImage());
        productTransactionVo.setSummary(product.getSummary());
        productTransactionVo.setContent(product.getContent());
        productTransactionVo.setIsSell(false);
        return productTransactionVo;
    }

    @Override
    public ProductTransactionVo getProductTranscationWithBuyByUserIdAndProductId(int userId, int productId) {
        Transaction transaction = transactionDao.getLastTransactionByProductIdAndUserId(productId, userId);
        Product product = productDao.getProductByProductId(productId);
        ProductTransactionVo productTransactionVo = null;
        if(transaction!=null){
            productTransactionVo = new ProductTransactionVo();
            productTransactionVo.setId(product.getId());
            productTransactionVo.setSellerId(product.getSellerId());
            productTransactionVo.setPrice(product.getPrice());
            productTransactionVo.setTitle(product.getTitle());
            productTransactionVo.setImage(product.getImage());
            productTransactionVo.setSummary(product.getSummary());
            productTransactionVo.setContent(product.getContent());
            productTransactionVo.setIsBuy(true);
            //交易信息
            productTransactionVo.setBuyPrice(transaction.getBuyPrice());
        }
        return productTransactionVo;
    }
}

