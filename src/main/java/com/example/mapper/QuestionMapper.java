package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    @Insert("insert into  QUESTION (TITLE,DESCRIPTION,GMT_CREATE,CREATOR,TAG)  values( #{title},#{description},#{gmtCreate},#{creator},#{tag})")
     void creat(Question questiong);

    @Select("select * from question  order by   GMT_CREATE  desc  limit #{ nowrow}  ,#{size}  ")
     List <Question>  Allselect(@Param(value = "nowrow") Integer nowrow, @Param(value = "size") Integer size);

    @Select("select count(*)  from question ")
    Integer count();


    @Select("select * from question  where   CREATOR = #{id}  order by GMT_CREATE  desc  limit #{nowrow}  ,#{size}   ")
    List<Question> myquestion(@Param(value = "id") Long id, @Param(value = "nowrow")  Integer offset,@Param(value = "size") Integer size);

    @Select("select * from question  where id = #{id}")
    Question getquetionByid(@Param(value = "id") Integer id);

    @Update("update question set title =#{title},DESCRIPTION =#{description} ,TAG=#{tag}   where id = #{id} ")
     int updateQuestion(Question question);
    @Update("update question  set VIEW_COUNT=VIEW_COUNT+1  where id = #{id}   ")
    void addview(Integer id);

    @Insert(" update QUESTION\n" +
            "    set\n" +
            "    COMMENT_COUNT = COMMENT_COUNT + #{commentCount,jdbcType=INTEGER}\n" +
            "    where id = #{id}")
    int incCommentCount(Question question);
    @Select("select *from QUESTION  where TAG regexp  #{tag} and ID != #{id} order by GMT_CREATE  desc  limit 10")
    List<Question> selectRelated(Question question);


    @Select("<script>"+
            "select count(*) from QUESTION\n" +
            "        <where>\n" +
            "            <if test=\"search != null and search != ''\">\n" +
            "                and title regexp #{search}\n" +
            "            </if>\n" +
            "        </where>"+
            "</script>")
    Integer countbysearch(@Param(value = "search") String search);

    @Select("<script> " +
            "  select * from QUESTION\n" +
            "        <where>\n" +
            "            <if test=\"search != null and search != ''\">\n" +
            "                and title regexp #{search}\n" +
            "            </if>\n" +
            "        </where>"+
            "        order by gmt_create desc limit #{nowrow},#{size}"+
            " </script>"  )
    List<Question> AllselectBysearch(@Param(value = "search") String search, @Param(value = "nowrow")  Integer nowrow,@Param(value = "size") Integer size);

    @Select("SELECT * FROM question ORDER BY VIEW_COUNT DESC  LIMIT 6")
    List<Question> selectquestionByView();
}