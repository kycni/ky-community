package com.kycni.community.controller;

import com.kycni.community.mapper.UserMapper;
import com.kycni.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    
    @GetMapping("/")
    public String index (HttpServletRequest request) {
        /*
          获得cookies, 根据token值查找用户
         */
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                break;
            }
        }
        return "index";
    }
}
