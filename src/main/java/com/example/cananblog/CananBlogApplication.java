package com.example.cananblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class CananBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CananBlogApplication.class, args);
    }

}
