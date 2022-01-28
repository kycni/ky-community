package com.kycni.community.controller;

import com.kycni.community.dto.CommentCreatDTO;
import com.kycni.community.dto.CommentDTO;
import com.kycni.community.dto.ResultDTO;
import com.kycni.community.enums.CommentTypeEnum;
import com.kycni.community.exception.CustomizeErrorCode;
import com.kycni.community.model.Comment;
import com.kycni.community.model.User;
import com.kycni.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Kycni
 * @date 2022/1/22 17:12
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreatDTO commentCreatDTO,
                       HttpServletRequest request) {
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        
        if(commentCreatDTO == null || StringUtils.isBlank(commentCreatDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        
        /*创建并注入评论消息，*/
        Comment comment = new Comment();
        comment.setParentId(commentCreatDTO.getParentId());
        comment.setContent(commentCreatDTO.getContent());
        comment.setType(commentCreatDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        
        return ResultDTO.okOf();
        
    }
    
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id") Long commentId) {
        List<CommentDTO> commentDTOList = commentService.listByTargetId(commentId, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOList);
    }
}