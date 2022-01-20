package com.kycni.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.kycni.community.mapper")
public class CommunityApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
