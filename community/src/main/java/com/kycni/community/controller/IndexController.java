package com.kycni.community.controller;

import com.kycni.community.dto.PaginationDto;
import com.kycni.community.dto.QuestionDto;
import com.kycni.community.dao.UserMapper;
import com.kycni.community.model.User;
import com.kycni.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Kycni
 * @date 2022/1/15 17:48
 */

@Controller
public class IndexController {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private QuestionService questionService;
    
    @GetMapping("/")
    public String index (HttpServletRequest request,
                         Model model,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "5") Integer size) {
        /*
          获得cookies, 根据token值查找用户
         */
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        
        PaginationDto pagination = questionService.indexList(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
        
    }
}
