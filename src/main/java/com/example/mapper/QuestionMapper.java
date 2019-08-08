package com.example.mapper;

import com.example.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into  QUESTION (TITLE,DESCRIPTION,GMT_CREATE,CREATOR,TAG)  values( #{title},#{description},#{gmtCreate},#{creator},#{tag})")
     void creat(Question questiong);

    @Select("select * from question limit #{ nowrow}  ,#{size} ")
     List <Question>  Allselect(@Param(value = "nowrow") Integer nowrow, @Param(value = "size") Integer size);

    @Select("select count(*)  from question ")
    Integer count();


    @Select("select * from question  where   CREATOR = #{id}  limit #{nowrow}  ,#{size}   ")
    List<Question> myquestion(@Param(value = "id") Integer userId, @Param(value = "nowrow")  Integer offset,@Param(value = "size") Integer size);

}