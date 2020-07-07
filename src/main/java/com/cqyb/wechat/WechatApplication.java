package com.cqyb.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("com.cqyb.wechat.dao")
@EnableWebMvc
@Configuration
@EnableScheduling
public class WechatApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WechatApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);
    }

//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //单个文件最大100MB
//        factory.setMaxFileSize("30MB"); //KB,MB
//        /// 设置单次上传总上传数据总大小1G
//        factory.setMaxRequestSize("1024MB");
//        return factory.createMultipartConfig();
//    }

}
