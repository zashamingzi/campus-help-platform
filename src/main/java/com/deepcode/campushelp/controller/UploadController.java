package com.deepcode.campushelp.controller;

import com.deepcode.campushelp.entity.Result;
import com.deepcode.campushelp.util.FileUploadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "文件上传模块", description = "图片/文件上传接口（需登录）")
@Validated
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Operation(summary = "图片上传")
    @PostMapping("/img")
    public Result<Map<String, String>> uploadImg(@RequestParam @NotNull(message = "上传文件不能为空") MultipartFile file) {
        try {
            String url = fileUploadUtil.upload(file);
            Map<String, String> result = new HashMap<>();
            result.put("url", url);
            return Result.success("图片上传成功", result);
        } catch (Exception e) {
            return Result.error("图片上传失败：" + e.getMessage());
        }
    }
}
