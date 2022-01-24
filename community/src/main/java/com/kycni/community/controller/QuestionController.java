package com.kycni.community.controller;

import com.kycni.community.dto.CommentDTO;
import com.kycni.community.dto.QuestionDTO;
import com.kycni.community.enums.CommentTypeEnum;
import com.kycni.community.exception.CustomizeErrorCode;
import com.kycni.community.exception.CustomizeException;
import com.kycni.community.mapper.UserMapper;
import com.kycni.community.service.CommentService;
import com.kycni.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Kycni
 * @date 2022/1/20 8:19
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private CommentService commentService;
    
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        
        /*通过问题页，返回问题和用户信息，*/
        QuestionDTO questionDTO = questionService.getById(id);

        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        
        List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        
        /*浏览一次问题页，浏览数加1*/
        questionService.incView(id);
        /*将用户问题和用户信息返还给前端*/
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentDTOList);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
    
}
