package com.dil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 应用程序启动类
 *
 * @author dyy
 * @date 2024/01/12
 */
@SpringBootApplication
@MapperScan({"com.dil.*.mapper"})
@CrossOrigin("*")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
