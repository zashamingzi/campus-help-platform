package com.deepcode.campushelp.mapper;

import com.deepcode.campushelp.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    // 新增用户
    @Insert("INSERT INTO user(username, password, nickname, phone, email, student_id, avatar, gender, status, create_time) " +
            "VALUES(#{username}, #{password}, #{nickname}, #{phone}, #{email}, #{studentId}, #{avatar}, #{gender}, #{status}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    // 校验旧密码
    @Select("SELECT COUNT(*) FROM user WHERE username = #{username} AND password = #{password}")
    int checkOldPwd(@Param("username") String username, @Param("password") String oldPwd);

    // 修改密码
    @Update("UPDATE user SET password = #{newPwd}, update_time = NOW() WHERE username = #{username}")
    int updatePwd(@Param("username") String username, @Param("newPwd") String newPwd);

    // 修改个人信息
    @Update("UPDATE user SET nickname=#{nickname}, phone=#{phone}, email=#{email}, " +
            "student_id=#{studentId}, avatar=#{avatar}, gender=#{gender}, update_time=NOW() WHERE id = #{id}")
    int updateInfo(User user);

    // 修改用户状态（封禁/解封）
    @Update("UPDATE user SET status = #{status}, update_time = NOW() WHERE id = #{id} AND status IN (0,1)")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}

