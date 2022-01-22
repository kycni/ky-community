package com.kycni.community.exception;

/**
 * @author Kycni
 * @date 2022/1/20 21:47
 */
public class CustomizeException extends RuntimeException {
    
    private final String message;
            
    public CustomizeException(CustomizeErrorInfo customizeExceptionErrorInfo) {
        this.message = customizeExceptionErrorInfo.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}