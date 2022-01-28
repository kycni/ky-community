package com.kycni.community.exception;

/**
 * @author Kycni
 * @date 2022/1/21 19:44
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    
    /*枚举类自定义错误信息*/
    QUESTION_NOT_FIND(2001,"您要找的问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题或者评论进行回复"),
    NO_LOGIN(2003, "未登录不能进行评论，请先登录"),
    SYSTEM_ERROR(2004,"服务器冒烟了，请稍后再试"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不在了"),
    INVALID_OPERATION(2007,"非法操作"),
    INVALID_INPUT(2008,"非法输入"), 
    CONTENT_IS_EMPTY(2009,"评论不能为空"),
    READ_NOTIFICATION_FAIL(2010,"你这是读别人的信息呢"),
    NOTIFICATION_NOT_FIND(2011,"消息离家出走了");
    

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
