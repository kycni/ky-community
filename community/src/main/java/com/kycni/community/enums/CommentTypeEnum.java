package com.kycni.community.enums;

/**
 * @author Kycni
 * @date 2022/1/22 19:28
 */
public enum CommentTypeEnum {
    /**/
    QUESTION(1),
    COMMENT(2);
    private final Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
