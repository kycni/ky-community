package com.kycni.community.exception;

/**
 * @author Kycni
 * @date 2022/1/21 19:45
 */
public interface ICustomizeErrorCode {
    /**
     * 显示错误信息方法 
     */
    String getMessage();
    Integer getCode();
}
