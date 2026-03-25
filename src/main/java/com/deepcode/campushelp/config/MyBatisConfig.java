//固定格式,规范//
package com.deepcode.campushelp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
        basePackages = "com.deepcode.campushelp.mapper",
        factoryBean = org.mybatis.spring.mapper.MapperFactoryBean.class
)
public class MyBatisConfig {

}
