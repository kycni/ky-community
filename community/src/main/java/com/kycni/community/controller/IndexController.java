package com.kycni.community.controller;

import com.kycni.community.dao.QuestionMapper;
import com.kycni.community.dao.UserMapper;
import com.kycni.community.dto.PaginationDTO;
import com.kycni.community.model.User;
import com.kycni.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Kycni
 * @date 2022/1/15 17:48
 */

@Controller
public class IndexController {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private QuestionMapper questionMapper;
    
    @Autowired
    private QuestionService questionService;
    
    @GetMapping("/")
    public String index (HttpServletRequest request,
                         Model model,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "5") Integer size) {

        
        PaginationDTO paginationDTO = questionService.list(page, size);
        model.addAttribute("pagination", paginationDTO);
        return "index";
        
    }
}
