package com.example.cananblog.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GalleryController {

    @RequestMapping("/admin/gallery")
    public String showgallery(){
        return "admin/gallery";
    }

    @RequestMapping("/admin/historygallery")
    public String showHistoryGallery(Model model){
        return "admin/gallery";
    }
}
