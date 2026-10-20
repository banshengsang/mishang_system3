package com.mishang.mishang_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.mishang.mishang_system.mapper")
public class MishangSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MishangSystemApplication.class, args);
    }

}
