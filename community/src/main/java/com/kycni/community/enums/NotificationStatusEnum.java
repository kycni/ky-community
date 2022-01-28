package com.kycni.community.enums;

/**
 * @author Kycni
 * @date 2022/1/28 20:12
 */
public enum NotificationStatusEnum {
    /**/
    UNREAD(0),READ(1);
    
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
