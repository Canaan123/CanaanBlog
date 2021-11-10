package com.example.cananblog.log;

import com.alibaba.fastjson.JSON;
import com.example.cananblog.bean.Log;
import com.example.cananblog.utils.IpUtil;
import com.example.cananblog.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.example.cananblog.log.LogAnnotation)")
    public void pt(){

    }

    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Long beginTime = System.currentTimeMillis();
         // 执行方法
        Object result = joinPoint.proceed();
        // 执行时长
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        recordLog(joinPoint,time);
        return result;
    }

    public void recordLog(ProceedingJoinPoint joinPoint,long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("------------------log start-----------------");
        log.info("detail:{}", logAnnotation.detail());
        log.info("operation:{}", logAnnotation.operator());
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className + "." + methodName + "()");
        // 请求的参数  （文件上传有问题）
//        Object[] args = joinPoint.getArgs();
//        String params = JSON.toJSONString(args[0]);
//        log.info("params:{}",params);

        // 获取request 设置ip地址
        HttpServletRequest request = getHttpServletRequest();
        log.info("ip:{}", IpUtil.getIP());
        log.info("excute time : {}",time);
        log.info("----------------log end-----------------------------");
        // 日志信息存储到redis
        String backstage = "ip:" + IpUtil.getIP() + "    method:" + methodName + "    excute time:" + time;
        System.out.println(backstage);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForList().leftPush("logs",new Log("canaan", logAnnotation.operator(), logAnnotation.detail(), TimeUtil.getCurrentDateTime(),backstage));
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
