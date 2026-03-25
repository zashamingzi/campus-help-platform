package com.deepcode.campushelp.entity.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    NORMAL(0, "正常"),
    FORBIDDEN(1, "封禁");

    private final Integer code;
    private final String desc;

    UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 根据code获取枚举
    public static UserStatusEnum getByCode(Integer code) {
        for (UserStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}

