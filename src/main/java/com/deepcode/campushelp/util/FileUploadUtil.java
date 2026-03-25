package com.deepcode.campushelp.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileUploadUtil {

    @Value("${custom.upload.path}")
    private String uploadPath;

    public String upload(MultipartFile file) throws Exception {
        if (file.isEmpty()) throw new RuntimeException("上传文件不能为空");
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();
        String original = file.getOriginalFilename();
        String suffix = original.substring(original.lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        File dest = new File(dir, fileName);
        file.transferTo(dest);
        return "/upload/" + fileName;
    }
}
