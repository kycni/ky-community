package com.kycni.community.service;

import com.kycni.community.dto.CommentDTO;
import com.kycni.community.enums.CommentTypeEnum;
import com.kycni.community.exception.CustomizeErrorCode;
import com.kycni.community.exception.CustomizeException;
import com.kycni.community.mapper.CommentMapper;
import com.kycni.community.mapper.QuestionMapper;
import com.kycni.community.mapper.UserMapper;
import com.kycni.community.model.Comment;
import com.kycni.community.model.Question;
import com.kycni.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Kycni
 * @date 2022/1/22 19:33
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper; 
    
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        /*评论的类型不存在或者为空*/
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (Objects.equals(comment.getType(), CommentTypeEnum.COMMENT.getType())) {
            
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

            // 增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentMapper.incCommentCount(parentComment);

        } else {
            
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FIND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionMapper.incCommentCount(question);
            
        }
    }

    public List<CommentDTO> listByTargetId(Long questionId, CommentTypeEnum type) {
        
        Example comment = new Example(Comment.class);
        comment.createCriteria().andEqualTo("parentId",questionId)
                .andEqualTo("type", type.getType());
        comment.setOrderByClause("gmt_create desc");
        
        /*获得questionComments，问题下的全部评论*/
        List<Comment> questioncomments = commentMapper.selectByExample(comment);
        
        if (questioncomments.size() == 0) {
            return new ArrayList<>();
        }
        
        /*获得userIds，评论人，通过评论，去重*/
        List<Long> userIds = questioncomments.stream().map(Comment::getCommentator)
                .distinct().collect(Collectors.toList());
        
        /*获得UserMap，通过去重评论人，查找对应的用户信息绑定*/
        Example userExample = new Example(User.class);
        userExample.createCriteria().andIn("id", userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        
        /*将用户信息设置进评论中*/
        return questioncomments.stream().map(comment1 -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment1, commentDTO);
            commentDTO.setUser(userMap.get(comment1.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
    }
    
}
