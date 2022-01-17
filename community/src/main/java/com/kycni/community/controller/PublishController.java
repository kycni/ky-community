package com.kycni.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Kycni
 * @date 2022/1/17 16:27
 */
@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publish () {
        return "publish";
    }
}
