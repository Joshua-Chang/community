package com.example.test.demo.mapper;

import com.example.test.demo.dto.QuestionDTO;
import com.example.test.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    /**
     * 冲user中取值放入database（！！！手写的变量名和表的列名可能不一样）
     * @param question
     */
//    @Insert("insert into question (title,description,creater,comment_count,view_count,like_count,tag,gmt_create,gmt_modified) values (#{title},#{description},#{creater},#{comment_count},#{view_count},#{like_count},#{tag},#{gmt_create},#{gmt_modified)")
    @Insert("insert into question (title,description,creater,tag,gmt_create,gmt_modified) values (#{title},#{description},#{creater},#{tag},#{gmt_create},#{gmt_modified})")
    void create(Question question);
    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question;")
    Integer count();

    @Select("select count(1) from question where creater = #{userID};")
    Integer countByUser(@Param("userID")Integer userID);


    @Select("select * from question where creater = #{userID}limit #{offset},#{size}")
    List<Question> listByUser(@Param("userID")Integer userID,@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id")Integer id);
}
