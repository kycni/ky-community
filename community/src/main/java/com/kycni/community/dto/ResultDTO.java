package com.kycni.community.dto;

import com.kycni.community.exception.CustomizeErrorCode;
import com.kycni.community.exception.CustomizeException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

/**
 * 结果dto
 *
 * @author Kycni
 * @date 2022/1/22 19:17
 */
@Data
public class ResultDTO {
    
    private Integer code;
    private String message;
    
    //定义返回显示异常信息的静态方法
    public static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    /**
     * 返回显示通用异常信息
     * @param errorCode
     * @return
     */
    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    /**
     * 返回显示通用异常信息
     * @param e
     * @return
     */
    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    /**
     * 返回成功信息
     * @return
     */
    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
}
