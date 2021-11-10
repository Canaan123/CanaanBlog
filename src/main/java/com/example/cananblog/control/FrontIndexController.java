package com.example.cananblog.control;

import com.example.cananblog.mapper.InformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontIndexController {

    @Autowired
    InformationMapper informationMapper;

    @RequestMapping({"/","/index","/index.html"})
    public String index(Model model){
        model.addAttribute("bloggername",informationMapper.queryBloggerName());
        model.addAttribute("blogdescribe",informationMapper.queryBlogDescribe());
        return "index";
    }
}
