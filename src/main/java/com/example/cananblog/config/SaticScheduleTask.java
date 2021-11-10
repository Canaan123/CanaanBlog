package com.example.cananblog.config;

import com.example.cananblog.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SaticScheduleTask {

    static final long UpdateEssayVisitsTime = 5000; // 5s

    /**
     * 定时更新文章的访问量
     */
    @Scheduled(fixedRate = UpdateEssayVisitsTime)
    private void configureTasks(){
//        System.out.println(TimeUtil.getCurrentDateTime());
    }
}
