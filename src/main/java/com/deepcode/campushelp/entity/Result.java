package com.deepcode.campushelp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code; // 200成功/400业务失败/500系统失败
    private String msg;   // 提示信息
    private T data;       // 返回数据

    // 成功响应（带数据）
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    // 成功响应（自定义提示）
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    // 业务失败响应
    public static <T> Result<T> error(String msg) {
        return new Result<>(400, msg, null);
    }

    // 系统失败响应
    public static <T> Result<T> serverError(String msg) {
        return new Result<>(500, msg, null);
    }
}
