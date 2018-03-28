package com.netease.mall.service;

import com.netease.mall.domain.Product;
import com.netease.mall.domain.ProductTransactionVo;

import java.util.List;


/**
 * @author : francis
 * @description :
 * @time : 2018/3/27,16:03
 * @update :
 */
public interface ProductService {

    /**
     * 获取所有Product
     * @return
     */
    List<Product> list();

    /**
     * 根据用户id获取带有Transcation数据的Product
     * @param sellerId
     * @return
     */
    List<ProductTransactionVo> getProductTranscationByUserId(int sellerId);

    /**
     * 根据产品id获取产品
     * @param productId
     * @return
     */
    Product getProductById(int productId);

    /**
     * 根据产品id删除产品
     * @param productId
     */
    void deleteProductById(int productId);

    List<ProductTransactionVo> listProductWithBuyInfo(int buyerId);

    List<ProductTransactionVo> listAllUnboughtProducts(int id);

    ProductTransactionVo getProductTranscationById(int productId);

    ProductTransactionVo getProductTranscationWithBuyByUserIdAndProductId(int userId, int productId);

    int saveProduct(Product product);

    void updateProduct(Product productNew);
}
