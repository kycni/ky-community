package com.kycni.community.advice;

import com.alibaba.fastjson.JSON;
import com.kycni.community.dto.ResultDTO;
import com.kycni.community.exception.CustomizeErrorCode;
import com.kycni.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Kycni
 * @date 2022/1/20 21:47
 */

@ControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(CustomizeException.class)
    Object handleControllerException(Throwable e, Model model, 
                                     HttpServletRequest request,
                                     HttpServletResponse response) {

        /*异常处理，用内容类型区分*/
        String contentType = request.getContentType();
        
        if ("application/json".equals(contentType)) {
            /*JSON页面跳转*/
            ResultDTO resultDTO;
            if (e instanceof CustomizeException) {
                /*类型转换，父类型转换为子类型*/
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }

            try {
                /*CTRL + SHIFT + U 大写*/
                /*还需设置字符集，不然会乱码*/
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                /*需要写入JSON对象*/
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        } else {
            //错误页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message",e.getMessage());
            } else {
                model.addAttribute("message",CustomizeErrorCode.SYSTEM_ERROR);

            }
        }
        return new ModelAndView("error");
    }
    
}

