package com.kycni.community.advice;

import com.kycni.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Kycni
 * @date 2022/1/20 21:47
 */

@ControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(Throwable e, Model model) {

        if (e instanceof CustomizeException) {
            model.addAttribute("message", e.getMessage());
        } else {
            model.addAttribute("message", "不是代码中定义的异常");
        }
        return new ModelAndView("error");
    }
    
}

