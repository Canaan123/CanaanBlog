package com.example.cananblog.control;

import com.example.cananblog.bean.Resource;
import com.example.cananblog.mapper.ResourceMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class LinkController {

    @Value("${file.resourceurl}")
    String resourceurl;

    @Autowired
    ResourceMapper resourceMapper;

//    @JsonFormat(pattern="yyyy-MM-dd")
    @RequestMapping("/link")
    public String ShowLink(Model model){
        List<Resource> resources = resourceMapper.queryResourceList();
        model.addAttribute("resources",resources);
        return "link";
    }

    @GetMapping("/downloadresource")
    public String DownloadResource(String resourcename, HttpServletResponse response, HttpServletRequest request) throws IOException {
        //获取文件的绝对路径
        String realPath = resourceurl + "/";
        String fileName = resourcename;
        //获取输入流对象（用于读文件）
        FileInputStream fis = new FileInputStream(new File(realPath, fileName));
        //获取文件后缀（.txt）
        String extendFileName = fileName.substring(fileName.lastIndexOf('.'));
        //动态设置响应类型，根据前台传递文件类型设置响应类型
        response.setContentType(request.getSession().getServletContext().getMimeType(extendFileName));
        //设置响应头,attachment表示以附件的形式下载，inline表示在线打开
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"UTF-8"));
        //获取输出流对象（用于写文件）
        ServletOutputStream os = response.getOutputStream();
        //下载文件,使用spring框架中的FileCopyUtils工具
        FileCopyUtils.copy(fis,os);
        fis.close();
        os.flush();
        os.close();
        return "下载成功";
    }
}
