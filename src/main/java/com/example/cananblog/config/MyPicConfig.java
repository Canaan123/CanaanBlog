package com.example.cananblog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyPicConfig implements WebMvcConfigurer {

    @Value("${file.pictureurl}")
    String PictureUrl;
    @Value("${file.essayurl}")
    String EssayUrl;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + PictureUrl + "/"); // 博客上传图片映射
        registry.addResourceHandler("/essayupload/**").addResourceLocations("file:" + EssayUrl); // 文章相关内容存储路径
    }

}
