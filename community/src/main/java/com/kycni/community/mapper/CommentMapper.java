package com.kycni.community.mapper;

import com.kycni.community.model.Comment;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Kycni
 * @date 2022/1/22 17:06
 */
@org.apache.ibatis.annotations.Mapper
public interface CommentMapper extends Mapper<Comment> {

    @Update("update COMMENT set COMMENT_COUNT = COMMENT_COUNT + #{commentCount} where id = #{id}")
    void incCommentCount(Comment parentComment);
}
