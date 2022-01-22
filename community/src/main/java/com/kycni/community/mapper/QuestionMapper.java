package com.kycni.community.mapper;

import com.kycni.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Kycni
 * @date 2022/1/17 18:42
 */
@Mapper
public interface QuestionMapper extends tk.mybatis.mapper.common.Mapper<Question> {
    /**
     * 在数据库中创建问题表
     */
    
    
    @Select("select count(1) from question")
    Integer count();
    
    /**
     * 在数据库中查找问题信息
     */
    @Select("SELECT * FROM QUESTION ORDER BY GMT_CREATE DESC LIMIT #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);
    
    @Select("select * from question where creator = #{userId} ORDER BY GMT_CREATE DESC limit #{offset},#{size}")
    List<Question> listByUserId(@Param("userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void update(Question question);
}
