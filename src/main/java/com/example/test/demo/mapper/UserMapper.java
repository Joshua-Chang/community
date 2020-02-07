package com.example.test.demo.mapper;

import com.example.test.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     * 冲user中取值放入database（！！！手写的变量名和表的列名可能不一样）
     * @param user
     */
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);//#{token} 把函数的形參放到此处 类不需要加注解，其他要注解param
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
