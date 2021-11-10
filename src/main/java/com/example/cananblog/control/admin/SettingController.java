package com.example.cananblog.control.admin;

import com.example.cananblog.log.LogAnnotation;
import com.example.cananblog.mapper.InformationMapper;
import com.example.cananblog.utils.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/admin")
public class SettingController {

    @Value("${file.pictureurl}")
    String picurl;

    @Autowired
    InformationMapper informationMapper;

    @RequestMapping("/setting")
    public String GoSetting(Model model){
        // 资料
        model.addAttribute("bloggername",informationMapper.queryBloggerName());
        model.addAttribute("blogdescribe",informationMapper.queryBlogDescribe());

        return "admin/setting";
    }

    /**
     * 修改头像
     * @param headimg
     * @return
     */
    @PostMapping("/modifyheadimg")
    public String ModifyHeadIMG(MultipartFile headimg){
        FileUpload.upload2(headimg,picurl,"admin.jpg");
        return "admin/setting";
    }

    /**
     * 修改背景图片
     * @param backgroundimg
     * @return
     */
    @LogAnnotation(detail = "修改文章背景图片",operator = "修改")
    @PostMapping("/modifybackgroundimg")
    public String ModifyBackgroundIMG(MultipartFile backgroundimg){
        FileUpload.upload2(backgroundimg,picurl,"background.jpg");
        return "admin/setting";
    }

    /**
     * 修改资料
     * @param bloggername
     * @param blogdescribe
     * @return
     */
    @ResponseBody
    @PostMapping("/modifyinformation")
    @LogAnnotation(detail = "修改个人资料",operator = "修改")
    public String ModifyInformation(String bloggername,String blogdescribe){
        if(bloggername.isEmpty()){
            return "博主名称不能为空";
        }
        else if(blogdescribe.isEmpty()){
            return "博客描述不能为空";
        }
        informationMapper.updateBloggerName(bloggername);
        informationMapper.updateBlogDescribe(blogdescribe);
        return "修改成功";
    }
}
