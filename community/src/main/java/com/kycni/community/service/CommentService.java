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
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    /**
     * 插入评论
     * 当插入成功，将回复数也包裹在同一个事务里
     * 当其中有一个失败，则全部回滚到之前。
     * @param comment 评论
     */
    @Transactional(rollbackFor = Exception.class)
    public void insert(Comment comment) {
        
        /*未选择问题，进行评论*/
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        /*评论的类型不存在或者为空*/
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        
        /*如果一切正常，判断是评论还是回复*/
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            // 是评论，主键查找保存数据库中该ParentId问题的回复
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            // 如果回复不存在，则抛出异常，“回复已不存在“
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            // 回复存在，则将评论插入数据库
            commentMapper.insert(comment);
            // 并且评论数加1
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentMapper.incCommentCount(parentComment);
        } else {
            // 通过评论查找问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            // 如果问题为空
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FIND);
            }
            //否则将问题插入评论中
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionMapper.incCommentCount(question);
        }
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        
        /*按条件查询：问题id和类型降序查询评论信息，保存为List数组*/
        Example commentExample = new Example(Comment.class);
        commentExample.createCriteria().andEqualTo("parentId",id)
                .andEqualTo("type",type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        
        /*没查到，返回一个空数组,用来保存新的评论信息*/
        if (commentList.size() == 0) {
            return new ArrayList<>();    
        }

        /*获取去重的评论人和评论信息,用流式编程，将对象数组转化成流存入map，用方法调用获得评论人属性方法，并用集合类将其转换为set数组，set数组本身就是不存在重复元素的*/
        Set<Long> commentators = commentList.stream().map(Comment::getCommentator).collect(Collectors.toSet());
        /*将评论人和评论信息存入List数组中*/
        List<Long> userIds = new ArrayList<>(commentators);
        
        /*获取评论人和评论人信息合并转换成Map(一次就可以获得到，大大降低了时间复杂度)*/
        Example userExample = new Example(User.class);
        userExample.createCriteria().andIn("id", userIds);
        List<User> userList = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, user -> user));
        
        /*转换Comment为CommentDTO*/
        List<CommentDTO> commentDTOList = commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOList;
    }
    
}
