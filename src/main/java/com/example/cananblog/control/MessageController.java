package com.example.cananblog.control;

import com.example.cananblog.bean.Message;
import com.example.cananblog.bean.Review;
import com.example.cananblog.mapper.ReviewMapper;
import com.example.cananblog.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    ReviewMapper reviewMapper;

    @RequestMapping("/message")
    public String showMessage(HttpServletRequest request, Model model){
        String editorContent = request.getParameter("editorContent");
        if(editorContent == null){
            model.addAttribute("messages",reviewMapper.queryMessageList());
            return "message";
        }

        // 设置当前日期时间
        String formatTime = TimeUtil.getCurrentDateTime();
        // 添加一条留言
        Message message = new Message(editorContent,formatTime);
        reviewMapper.addMessage(message);

        model.addAttribute("messages",reviewMapper.queryMessageList());
        return "message";
    }
}
