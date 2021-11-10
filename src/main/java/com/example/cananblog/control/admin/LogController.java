package com.example.cananblog.control.admin;

import com.example.cananblog.bean.Essay;
import com.example.cananblog.bean.Log;
import com.example.cananblog.utils.VerificationCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class LogController {

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/log")
    public String ShowLog(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        // 读取redis里key为log的list
        PageHelper.startPage(pageNum,5);
        List<Log> logs = (List) redisTemplate.opsForList().range("logs",0,-1);

        PageInfo<Log> pageInfo = new PageInfo<>(logs);

        model.addAttribute("loglist",pageInfo);
        return "admin/log";
    }

    @ResponseBody
    @PostMapping("/clearlog")
    public String ClearLog(){
        redisTemplate.delete("logs");
        return "清空成功";
    }



}
