//也是固定格式//
package com.deepcode.campushelp.config;

import com.deepcode.campushelp.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 运行时异常
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常：", e);
        return Result.error("idea操作失败：" + e.getMessage());
    }

    // 数据库异常
    @ExceptionHandler(java.sql.SQLException.class)
    public Result<?> handleSqlException(java.sql.SQLException e) {
        log.error("数据库异常：", e);
        return Result.serverError("数据库操作失败：" + e.getMessage());
    }

    // 兜底异常
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常：", e);
        return Result.serverError("其他操作失败,请稍后再试");
    }
}
