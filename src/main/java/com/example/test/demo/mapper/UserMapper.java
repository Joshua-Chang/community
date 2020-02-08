package com.example.test.demo.mapper;

import com.example.test.demo.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    /**
     * 冲user中取值放入database（！！！手写的变量名和表的列名可能不一样）
     * @param user
     */
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatar})")
    void insert(User user);
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//#{token} 把函数的形參放到此处 类不需要加注解，其他要注解param

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId")String accountId);
//    @Update("update user set(name,token,gmt_modified,avatar) values(#{}) where () =values(#{})")
    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified} where id = #{id}")
    void update(User myUser);
    /**
     * CREATE TABLE user
     * 				(
     * 				id int AUTO_INCREMENT PRIMARY KEY,
     * 				account_id varchar(100),
     * 				name varchar(50),
     * 				avatr varchar(150),
     * 				token char(36),
     * 				gmt_create bigint,
     * 				gmt_modified bigint
     * 				)
     */
}
