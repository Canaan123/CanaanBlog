package com.example.cananblog.control;

import com.example.cananblog.bean.Message;
import com.example.cananblog.bean.PrivateMessage;
import com.example.cananblog.mapper.ReviewMapper;
import com.example.cananblog.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PrivateLetterController {

    @Autowired
    ReviewMapper reviewMapper;

    @RequestMapping("/privateletter")
    public String ShowPrivateLetter(){
        return "privateletter";
    }

    @ResponseBody
    @PostMapping("/sendprivate")
    public String SendEmail(String content,String qq){
        if(content == null || content.equals("")){
            return "文章内容不能为空";
        }
        if(qq == null || qq.equals("")){
            return "QQ号码不能为空";
        }
        // 向数据库添加一条私信
        String formatTime = TimeUtil.getCurrentDateTime();
        Message message = new PrivateMessage(content,formatTime,qq);
        reviewMapper.addPrivateMessage(message);
        return "添加成功";
    }

}
