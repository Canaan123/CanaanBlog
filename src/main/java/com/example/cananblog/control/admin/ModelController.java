package com.example.cananblog.control.admin;

import com.example.cananblog.bean.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/model")
public class ModelController {

    @GetMapping("/essay")
    public Essay essay(){
        return new Essay();
    }

    @GetMapping("/jsonResult")
    public JsonResult jsonResult(){
        return new JsonResult();
    }

    @GetMapping("/log")
    public Log log(){
        return new Log();
    }

    @GetMapping("/message")
    public Message message(){
        return new Message();
    }

    @GetMapping("/myFile")
    public MyFile myFile(){
        return new MyFile();
    }

    @GetMapping("/privateMessage")
    public PrivateMessage privateMessage(){
        return new PrivateMessage();
    }

    @GetMapping("/review")
    public Review review(){
        return new Review();
    }

    @GetMapping("/emailModel")
    public EmailModel emailModel(){
        return new EmailModel();
    }

}
