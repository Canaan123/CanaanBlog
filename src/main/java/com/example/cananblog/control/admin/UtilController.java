package com.example.cananblog.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Controller
public class UtilController {

    @RequestMapping("/tool/")
    public String GoToolHtml(Model model){
        return "admin/tool";
    }

    @RequestMapping("/tool/markdowntohtml")
    public String MdToHtml(MultipartFile multipartFile){
        return "hi";
    }
}
