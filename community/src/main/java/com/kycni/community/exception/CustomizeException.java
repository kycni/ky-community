package com.kycni.community.exception;

/**
 * @author Kycni
 * @date 2022/1/20 21:47
 */
public class CustomizeException extends RuntimeException {
    
    private String message;
    private Integer code;
            
    public CustomizeException(ICustomizeErrorCode customizeExceptionErrorInfo) {
        this.code = customizeExceptionErrorInfo.getCode();
        this.message = customizeExceptionErrorInfo.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
    
    public Integer getCode() {
        return code;
    }
}