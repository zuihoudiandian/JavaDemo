package com.example.mapper;

import com.example.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/4.
 * PS: Not easy to write code, please indicate.
 */
@Mapper

public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl}) ")
    void insert(User user);

    @Select("select top 1  * from  user where token =#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user  where id=#{creator} ")
    User fiandByid(@Param("creator") Integer creator);

}

