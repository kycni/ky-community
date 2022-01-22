package com.kycni.community.exception;

/**
 * @author Kycni
 * @date 2022/1/21 19:44
 */
public enum CustomizeEnumErrorInfo implements CustomizeErrorInfo {
    
    /*枚举类自定义错误信息*/
    QUESTION_NOT_FIND("您要找的问题不存在");
    
    @Override
    public String getMessage() {
        return message;
    }
    
    private final String message;

    CustomizeEnumErrorInfo(String message) {
        this.message = message;
    }
}
