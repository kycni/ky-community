package com.kycni.community.mapper;

import com.kycni.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
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
    void create(Question question);

    /**
     * 在数据库中查找问题信息
     */
    @Select("select * from question")
    List<Question> list();
}
