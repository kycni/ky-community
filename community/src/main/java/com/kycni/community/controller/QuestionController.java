package com.kycni.community.controller;

import com.kycni.community.dto.QuestionDTO;
import com.kycni.community.exception.CustomizeErrorCode;
import com.kycni.community.exception.CustomizeException;
import com.kycni.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Kycni
 * @date 2022/1/20 8:19
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        System.out.println(id);
        /*通过问题页，返回问题和用户信息，*/
        QuestionDTO questionDTO = questionService.getById(id);
        /*浏览一次问题页，浏览数加1*/
        questionService.incView(id);
        /*将用户问题和用户信息返还给前端*/
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
