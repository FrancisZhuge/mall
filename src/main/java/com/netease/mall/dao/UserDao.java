package com.netease.mall.dao;

import com.netease.mall.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/27,18:16
 * @update :
 */
@Mapper
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " username, password, nick_name, user_type ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    @Select({" select ", SELECT_FIELDS ," from ", TABLE_NAME ," where username=#{username} "})
    User getByName(@Param("username") String username);


    /**
     * 更具用户主键查找用户
     * @param userId
     * @return
     */
    @Select({" select ", SELECT_FIELDS ," from ", TABLE_NAME ," where id=#{userId} "})
    User getById(@Param("userId") int userId);

}
