package com.deepcode.campushelp.service;

import com.deepcode.campushelp.entity.User;

public interface UserService {
    // 用户注册
    int register(User user);

    // 用户登录
    User login(String username, String password);

    // 修改密码
    int updatePwd(String username, String oldPwd, String newPwd);

    // 修改个人信息
    int updateInfo(User user);

    // 管理员修改用户状态
    int updateStatus(Long id, Integer status);
}
