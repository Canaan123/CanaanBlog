package com.example.cananblog.config;

import com.example.cananblog.mapper.EssayMapper;
import com.example.cananblog.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.HashMap;
import java.util.List;

@Configuration
@EnableScheduling
public class SaticScheduleTask {

    static final long UpdateEssayVisitsTime = 60000 * 1; // 一分钟
    static final int UpdateHotEssays = 60000 * 30; // 30分钟
    @Autowired
    EssayMapper essayMapper;
    @Autowired
    RedisTemplate redisTemplate;
    /**
     * 定时把redis中文章访问量更像到数据库
     */
    @Scheduled(fixedRate = UpdateEssayVisitsTime)
    private void configureTasks(){
        List<Long> essayId = essayMapper.queryEssayId();
        for (Long essayid : essayId) {
            String EssayviewCount = "viewCount:" + essayid; // 文章id浏览量
            if(redisTemplate.hasKey(EssayviewCount)){
                Integer num = (Integer) redisTemplate.opsForValue().get(EssayviewCount); // 缓存中的访问量
                redisTemplate.opsForValue().decrement(EssayviewCount,num); // redis中减去num
                HashMap hashMap = new HashMap();
                hashMap.put("essayid",essayid);
                hashMap.put("num",num);
                essayMapper.increaseVisits(hashMap); // 数据库对应文章访问量增加num
                redisTemplate.delete("essayby" + essayid);
            }
        }
    }

    /**
     *
     */
    @Scheduled(fixedRate = UpdateHotEssays)
    public void UpdateRedisTask(){
        // 删除热点文章key
        redisTemplate.delete("hotessays");
    }
//     配置线程池任务调度器
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        return taskScheduler;
    }
}
