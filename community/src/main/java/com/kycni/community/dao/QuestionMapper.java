package com.kycni.community.dao;

import com.kycni.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Kycni
 * @date 2022/1/17 18:42
 */
@Mapper
public interface QuestionMapper {
    /**
     * 在数据库中创建问题表
     */
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insert(Question question);
    
    @Select("select count(1) from question")
    Integer count();
    
    /**
     * 在数据库中查找问题信息
     */
    @Select("SELECT * FROM QUESTION ORDER BY GMT_CREATE DESC LIMIT #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

}
