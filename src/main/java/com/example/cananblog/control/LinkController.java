package com.example.cananblog.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LinkController {

    @RequestMapping("/link")
    public String ShowLink(){
        return "link";
    }
}
