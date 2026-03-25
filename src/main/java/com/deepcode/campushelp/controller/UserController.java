package com.deepcode.campushelp.controller;

import com.deepcode.campushelp.entity.Result;
import com.deepcode.campushelp.entity.User;
import com.deepcode.campushelp.service.UserService;
import com.deepcode.campushelp.util.JwtUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "用户模块", description = "用户注册、登录、信息管理相关接口")
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "获取登录验证码")
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String verifyCode = defaultKaptcha.createText();
        request.getSession().setAttribute("verifyCode", verifyCode);
        BufferedImage image = defaultKaptcha.createImage(verifyCode);
        response.setContentType("image/jpeg");
        OutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
        out.close();
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<User> register(@RequestBody @Valid @NotNull User user) {
        int res = userService.register(user);
        return res == 1 ? Result.success("注册成功", user) : Result.error("用户名已存在");
    }

    @Operation(summary = "用户登录（返回JWT Token）")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(
            @RequestParam @NotBlank(message = "用户名不能为空") String username,
            @RequestParam @NotBlank(message = "密码不能为空") String password,
            @RequestParam @NotBlank(message = "验证码不能为空") String captcha,
            HttpServletRequest request) {
        String sessionCaptcha = (String) request.getSession().getAttribute("verifyCode");
        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(captcha)) {
            return Result.error("验证码错误");
        }
        User user = userService.login(username, password);
        if (user == null) {
            return Result.error("用户名/密码错误或账号已封禁");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        String token = jwtUtil.createToken(claims);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userId", user.getId());
        result.put("username", user.getUsername());
        result.put("nickname", user.getNickname());
        result.put("avatar", user.getAvatar());
        return Result.success("登录成功", result);
    }

    @Operation(summary = "修改密码")
    @PostMapping("/updatePwd")
    public Result<Void> updatePwd(
            @RequestParam @NotBlank(message = "用户名不能为空") String username,
            @RequestParam @NotBlank(message = "旧密码不能为空") String oldPwd,
            @RequestParam @NotBlank(message = "新密码不能为空") String newPwd) {
        int res = userService.updatePwd(username, oldPwd, newPwd);
        return res == 1 ? Result.success("密码修改成功", null) : Result.error("旧密码错误");
    }

    @Operation(summary = "修改个人信息")
    @PostMapping("/updateInfo")
    public Result<User> updateInfo(@RequestBody @Valid @NotNull User user) {
        int res = userService.updateInfo(user);
        return res == 1 ? Result.success("个人信息修改成功", user) : Result.error("个人信息修改失败");
    }

    @Operation(summary = "管理员修改用户状态")
    @PostMapping("/admin/updateStatus")
    public Result<Void> updateStatus(
            @RequestParam @NotNull(message = "用户ID不能为空") Long id,
            @RequestParam @NotNull(message = "状态值不能为空（0=解封，1=封禁）") Integer status) {
        int res = userService.updateStatus(id, status);
        String msg = status == 1 ? "封禁成功" : "解封成功";
        return res == 1 ? Result.success(msg, null) : Result.error("操作失败（状态值只能是0/1）");
    }
}
