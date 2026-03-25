//接口实例后面再改//
package com.deepcode.campushelp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "✅ 校园互助平台启动成功！<br/>" +
                "📖 接口文档地址：/campus-help/swagger-ui/index.html<br/>" +
                "🔗 核心可用接口示例：<br/>" +
                "   - 👤 用户模块：注册(/campus-help/user/register) | 登录(/campus-help/user/login) | 改密码(/campus-help/user/change-pwd)<br/>" +
                "   - 📋 任务模块：发布(/campus-help/task/add) | 接单(/campus-help/task/receive) | 完成(/campus-help/task/complete)<br/>" +
                "   - 💬 评论模块：留言(/campus-help/task/comment/add)<br/>" +
                "   - 📤 上传模块：文件(/campus-help/upload/file)";
    }
}
