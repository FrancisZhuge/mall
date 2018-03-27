package com.netease.mall.dao;

import com.netease.mall.domain.Ticket;
import org.apache.ibatis.annotations.*;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/27,15:36
 * @update :
 */
@Mapper
public interface TicketDao {
    String TABLE_NAME = "ticket";
    String INSERT_FIELDS = " user_id, expired, status, ticket ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 新增一个ticket
     * @param ticket
     * @return
     */
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,") values (#{userId},#{expired},#{status},#{ticket})"})
    int saveTicket(Ticket ticket);

    /**
     * 查找ticket
     * @param ticket
     * @return
     */
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    Ticket getByTicket(String ticket);

    /**
     * 更改状态
     * @param ticket
     * @param status
     */
    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
