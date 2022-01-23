package com.kycni.community.controller;

import com.kycni.community.dto.CommentCreateDTO;
import com.kycni.community.dto.CommentDTO;
import com.kycni.community.dto.ResultDTO;
import com.kycni.community.exception.CustomizeErrorCode;
import com.kycni.community.model.Comment;
import com.kycni.community.model.User;
import com.kycni.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author Kycni
 * @date 2022/1/22 17:12
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * @RequestBody 自动反序列化JSON
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        /*content还要引入questionMapper去查，需要引用Service*/
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        
        /*@ResponseBody将对象序列化成JSON返回前端*/
        return ResultDTO.okOf();
        
    }
}