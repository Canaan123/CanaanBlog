package com.example.cananblog;


import com.example.cananblog.bean.EmailModel;
import com.example.cananblog.bean.Essay;
import com.example.cananblog.bean.Log;
import com.example.cananblog.mapper.EssayEsMapper;
import com.example.cananblog.mapper.EssayMapper;
import com.example.cananblog.utils.MailSendUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;


import javax.annotation.Resource;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CananBlogApplicationTests {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    MailSendUtils mailSendUtils;
    @Autowired
    EssayEsMapper essayEsMapper;
    @Autowired
    EssayMapper essayMapper;
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
    @Test
    public void esTest(){
//        elasticsearchRestTemplate.e
        List<Essay> essays = essayMapper.queryhotEssay();
        for(Essay essay:essays) System.out.println(essay.getEssayid());
    }
    @Test
    public void termQuery(){
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title","好");
        Iterable<Essay> products = essayEsMapper.search(termQueryBuilder);
        for (Essay product : products) {
            System.out.println(product);
        }
    }


}
