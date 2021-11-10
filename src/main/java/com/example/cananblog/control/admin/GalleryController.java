package com.example.cananblog.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class GalleryController {

    @RequestMapping("/gallery")
    public String showgallery(){
        return "admin/gallery";
    }

    @RequestMapping("/historygallery")
    public String showHistoryGallery(Model model){
        return "admin/gallery";
    }
}
