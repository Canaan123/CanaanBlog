package com.example.cananblog.control;

import com.example.cananblog.bean.Essay;
import com.example.cananblog.mapper.EssayMapper;
import com.example.cananblog.utils.RedisServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    EssayMapper essayMapper;
    @Autowired
    RedisServiceUtil redisServiceUtil;

    @GetMapping("/test/getEssayCache")
    public List<Essay> getEssayCache(){
        return essayMapper.queryEssayList();
    }

    @GetMapping("/test/getEssayNoCache")
    public List<Essay> getEssayNoCache(){
        return redisServiceUtil.getRedisEssays();
    }


}
