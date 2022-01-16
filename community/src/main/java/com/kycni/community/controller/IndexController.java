package com.kycni.community.controller;

import org.springframework.stereotype.Controller;

/**
 * @author Kycni
 * @date 2022/1/15 17:48
 */
@Controller
public class IndexController {
    public String index () {
        return "/";
    }
}
