package com.code;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@ComponentScan(basePackages = "com.code")
public class WebRoot {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebRoot.class, args);
    }
}
