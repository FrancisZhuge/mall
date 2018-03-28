package com.netease.mall.dao;

import com.netease.mall.domain.Product;
import com.netease.mall.domain.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/27,16:12
 * @update :
 */
@Mapper
public interface ProductDao {
    String TABLE_NAME = "product";
    String INSERT_FIELDS = " seller_id, price, title, image, summary, content ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;
    /**
     * 获取所有Product
     * @return
     */
    @Select({"select ", SELECT_FIELDS ," from ", TABLE_NAME})
    public List<Product> list();

    /**
     * 根据sellerId获取所有Product
     * @param sellerId
     * @return
     */
    @Select({"select ", SELECT_FIELDS ," from ", TABLE_NAME , " where seller_Id=#{sellerId}"})
    List<Product> getByUserId(@Param("sellerId") int sellerId);

    @Select({"select ", SELECT_FIELDS ," from ", TABLE_NAME , " where id=#{productId}"})
    Product getProductByProductId(@Param("productId") int productId);

    @Delete({" delete from ", TABLE_NAME ," where id=#{productId}"})
    void deleteProductById(@Param("productId") int productId);

    @Select({" select ", SELECT_FIELDS ," from ", TABLE_NAME ," where id not in (select product_id from transaction where buyer_id = #{buyerId} and num>0) "})
    List<Product> listAllUnboughtProductsByBuyerId(int buyerId);

    @Insert({"insert into ", TABLE_NAME , "(", INSERT_FIELDS ,") values (#{product.sellerId},#{product.price},#{product.title},#{product.image},#{product.summary},#{product.content} )"})
    @Options(useGeneratedKeys = true,keyProperty = "product.id")
    int saveProduct(@Param("product") Product product);

    @Update({" update ", TABLE_NAME ," set seller_id=#{product.sellerId}, price=#{product.price}, title=#{product.title}, image=#{product.image}, summary=#{product.summary}, content=#{product.content} where id=#{product.id}"})
    void updateProduct(@Param("product") Product product);
}
