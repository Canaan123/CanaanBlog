package com.example.cananblog.utils;

import com.example.cananblog.bean.Essay;
import com.example.cananblog.mapper.EssayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RedisServiceUtil {

    @Resource
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private EssayMapper essayMapper;

    /**
     * 判断缓存中有没有该文章列表数据,返回文章列表
     * @return
     */
    public List<Essay> getRedisEssays(){
        List<Essay> essays = null;
        if(redisTemplate.hasKey("essays")){
            essays = (List<Essay>) redisTemplate.opsForValue().get("essays");
        }
        else{
            essays = essayMapper.queryEssayList();
            redisTemplate.opsForValue().set("essays",essays);
        }
        return essays;
    }
    /**
     * 判断缓存中有没有该文章数据,返回该文章
     */
    public Essay getRedisEssayById(long id){
        Essay essay;
        String key = "essayby" + id;
        if(redisTemplate.hasKey(key)){
            essay = (Essay) redisTemplate.opsForValue().get(key);
        }
        else{
            essay = essayMapper.queryEssayById(id);
            redisTemplate.opsForValue().set(key,essay);
        }
        return essay;
    }
}
