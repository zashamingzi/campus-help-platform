package com.deepcode.campushelp.service.impl;

import com.deepcode.campushelp.entity.User;
import com.deepcode.campushelp.entity.enums.UserStatusEnum;
import com.deepcode.campushelp.mapper.UserMapper;
import com.deepcode.campushelp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int register(User user) {
        // 校验用户名是否存在
        User exist = userMapper.selectByUsername(user.getUsername());
        if (exist != null) return 0;

        // 密码加密 + 默认值填充
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatusEnum.NORMAL.getCode());
        user.setCreateTime(LocalDateTime.now());

        // 插入数据库
        return userMapper.insert(user);
    }

    @Override
    public User login(String username, String password) {
        // 查询用户
        User user = userMapper.selectByUsername(username);
        if (user == null) return null;

        // 校验密码（密文对比）
        if (!passwordEncoder.matches(password, user.getPassword())) return null;

        // 校验状态（是否封禁）
        if (UserStatusEnum.FORBIDDEN.getCode().equals(user.getStatus())) return null;

        return user;
    }

    @Override
    public int updatePwd(String username, String oldPwd, String newPwd) {
        // 查询用户
        User user = userMapper.selectByUsername(username);
        if (user == null) return 0;

        // 校验旧密码
        if (!passwordEncoder.matches(oldPwd, user.getPassword())) return 0;

        // 新密码加密后更新
        String encodeNewPwd = passwordEncoder.encode(newPwd);
        return userMapper.updatePwd(username, encodeNewPwd);
    }

    @Override
    public int updateInfo(User user) {
        // 校验用户是否存在 & 用户名匹配
        User exist = userMapper.selectByUsername(user.getUsername());
        if (exist == null || !exist.getId().equals(user.getId())) return 0;

        // 填充更新时间
        user.setUpdateTime(LocalDateTime.now());

        // 更新信息
        return userMapper.updateInfo(user);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        // 校验状态值（只能是0/1）
        if (!UserStatusEnum.NORMAL.getCode().equals(status) && !UserStatusEnum.FORBIDDEN.getCode().equals(status)) {
            return 0;
        }

        // 更新状态
        return userMapper.updateStatus(id, status);
    }
}