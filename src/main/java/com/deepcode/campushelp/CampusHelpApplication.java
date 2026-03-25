package com.deepcode.campushelp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 最简启动类，仅保留核心注解
 */
@SpringBootApplication
@MapperScan("com.deepcode.campushelp.mapper") // 纯 MyBatis 扫描 Mapper
public class CampusHelpApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusHelpApplication.class, args);
        System.out.println("=====================================");
        System.out.println("✅ 校园互助平台（SpringBoot 3.x）启动成功");
        System.out.println("📌 接口前缀：http://localhost:8080/campus-help");
        System.out.println("=====================================");
    }
} 