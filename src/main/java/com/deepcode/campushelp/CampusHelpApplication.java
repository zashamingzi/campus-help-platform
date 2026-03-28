package com.deepcode.campushelp;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.deepcode.campushelp.mapper") // 纯 MyBatis 扫描 Mapper
public class CampusHelpApplication {


    private static final Logger logger = LoggerFactory.getLogger(CampusHelpApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CampusHelpApplication.class, args);

        logger.info("=====================================");
        logger.info(" 校园互助平台启动成功");
        logger.info(" 接口前缀：http://localhost:8080/campus-help");
        logger.info("=====================================");
    }
}
