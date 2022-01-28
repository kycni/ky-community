package com.kycni.community.enums;

/**
 * @author Kycni
 * @date 2022/1/28 19:55
 */
public enum NotificationTypeEnum {
    /**/
    RELY_QUESTION(1,"回复了问题"),
    RELY_COMMENT(2,"回复了评论");
    
    private final int type;
    private final String name;
    
    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }

    public int getType() {
        return type;
    }
    

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
    
}
