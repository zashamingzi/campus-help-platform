package com.deepcode.campushelp.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;                // 主键
    private String username;        // 用户名（唯一）
    private String password;        // 密码（BCrypt加密）
    private String nickname;        // 昵称
    private String phone;           // 手机号
    private String email;           // 邮箱
    private String studentId;       // 学号
    private String avatar;          // 头像URL
    private Integer gender;         // 性别（0=女 1=男 2=未知）
    private Integer status;         // 状态（0=正常 1=封禁）
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}