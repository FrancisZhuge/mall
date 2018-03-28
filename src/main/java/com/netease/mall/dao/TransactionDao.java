package com.netease.mall.dao;

import com.netease.mall.domain.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,0:34
 * @update :
 */
@Mapper
public interface TransactionDao {
    String TABLE_NAME = "transaction";
    String INSERT_FIELDS = " product_id, buyer_id, buy_price, num, buy_time ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;
    /**
     * 根据产品id获取产品的销售量
     * @param productId
     * @return
     */
    @Select({" select SUM(num) from ", TABLE_NAME ," where product_id=#{productId} "})
    Integer getTransactionCountByProductId(@Param("productId") int productId);

    @Delete({" delete from ", TABLE_NAME ," where product_id=#{productId}"})
    void deleteTransactionByProductId(@Param("productId") int productId);

    @Select({" select SUM(num) from ", TABLE_NAME ," where product_id=#{productId} and buyer_id = #{buyerId} "})
    Integer getTransactionCountByProductIdAndUserId(@Param("productId") int productId, @Param("buyerId") int buyerId);

    @Select({" select ", SELECT_FIELDS ," from ", TABLE_NAME ," where product_id=#{productId} and buyer_id = #{buyerId} limit 0,1"})
    Transaction getLastTransactionByProductIdAndUserId(@Param("productId") int productId, @Param("buyerId") int buyerId);

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,") values (#{transaction.productId},#{transaction.buyerId},#{transaction.buyPrice},#{transaction.num},#{transaction.buyTime})"})
    @Options(useGeneratedKeys = true,keyProperty = "transaction.id")
    int saveTransaction(@Param("transaction") Transaction transaction);

    @Select({" select ", SELECT_FIELDS ," from ", TABLE_NAME ," where buyer_id = #{buyerId} ORDER BY buy_time "})
    List<Transaction> getAllTransactionsByBuyerId(@Param("buyerId") int buyerId);
}
