// UserStatusEnum.java
package com.deepcode.campushelp.entity.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    FEMALE(0, "女"),
    MALE(1, "男"),
    UNKNOWN(2, "未知");

    private final Integer code;
    private final String desc;

    GenderEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
