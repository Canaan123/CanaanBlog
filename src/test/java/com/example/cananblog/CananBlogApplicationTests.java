package com.example.cananblog;


import com.example.cananblog.bean.EmailModel;
import com.example.cananblog.bean.Essay;
import com.example.cananblog.bean.Log;
import com.example.cananblog.utils.MailSendUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;


import javax.annotation.Resource;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CananBlogApplicationTests {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    MailSendUtils mailSendUtils;

    @Test
    void contextLoads() throws IOException {
        redisTemplate.opsForValue().set("essayTemplate",new Essay().setEssaytitle("123").setEssaycontent("456").setEssaydescribe("1111"));
        Essay essayTemplate = (Essay)redisTemplate.opsForValue().get("essayTemplate");
        System.out.println(essayTemplate.getEssaytitle());
    }

    @Test
    public void EmaiTest() {
        EmailModel emailModel = new EmailModel();
        emailModel.setEmailContent("登录成功");
        emailModel.setRecieverEmailAddress("1106126069@qq.com");
        mailSendUtils.sendEmailAsText(emailModel);

    }

}
