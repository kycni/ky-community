package com.kycni.community.mapper;

import com.kycni.community.model.Question;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Kycni
 * @date 2022/1/17 18:42
 */
@org.apache.ibatis.annotations.Mapper
public interface QuestionMapper extends Mapper<Question> {
    /**
     * 在数据库中创建问题表
     */
    
    @Update("UPDATE QUESTION SET VIEW_COUNT = VIEW_COUNT + #{viewCount} where id = #{id}")
    int incView(Question record);
    
    @Update("UPDATE QUESTION SET COMMENT_COUNT = COMMENT_COUNT + #{commentCount} where id = #{id}")
    void incCommentCount(Question question);
    
    @Select("select count(1) from question")
    Integer count();
    
    /**
     * 在数据库中查找问题信息
     */
    @Select("SELECT * FROM QUESTION ORDER BY GMT_CREATE DESC LIMIT #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Long userId);
    
    @Select("select * from question where creator = #{userId} ORDER BY GMT_CREATE DESC limit #{offset},#{size}")
    List<Question> listByUserId(@Param("userId") Long userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    
    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void update(Question question);


}
