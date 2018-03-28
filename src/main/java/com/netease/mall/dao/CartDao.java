package com.netease.mall.dao;

import com.netease.mall.domain.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,11:28
 * @update :
 */
@Mapper
public interface CartDao {
    String TABLE_NAME = "cart";
    String INSERT_FIELDS = " buyer_id, product_id, num ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;


    @Insert({"insert into ", TABLE_NAME , "(", INSERT_FIELDS ,") values (#{buyerId},#{productId},#{num} )"})
    void saveCart(@Param("cart")Cart cart);

    @Delete({" delete from ", TABLE_NAME ," where buyer_id = #{buyerId}"})
    void deleteAllCartByBuyerId(@Param("buyerId") int buyerId);

    List<Cart> listCartByBuyerId(@Param("buyerId") int buyerId);
}
