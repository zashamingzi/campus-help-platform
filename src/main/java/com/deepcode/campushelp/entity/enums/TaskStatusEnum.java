package com.deepcode.campushelp.entity.enums;

import lombok.Getter;

@Getter
public enum TaskStatusEnum {
    PENDING(0, "待接单"),
    PROCESSING(1, "进行中"),
    COMPLETED(2, "已完成");

    private final Integer code;
    private final String desc;

    TaskStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TaskStatusEnum getByCode(Integer code) {
        for (TaskStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}