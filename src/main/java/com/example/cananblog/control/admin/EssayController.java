package com.example.cananblog.control.admin;

import com.example.cananblog.bean.Essay;
import com.example.cananblog.log.LogAnnotation;
import com.example.cananblog.mapper.EssayMapper;
import com.example.cananblog.utils.FileUpload;
import com.example.cananblog.utils.RedisServiceUtil;
import com.example.cananblog.utils.SaveFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class EssayController {

    @Autowired
    private EssayMapper essayMapper;
    @Value("${file.essayurl}")
    String essayurl;
    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    RedisServiceUtil redisServiceUtil;
    /**
     * 文章表格界面显示
     * @param model
     * @return
     */
    @LogAnnotation(detail = "查看文章管理内容",operator = "查询")
    @RequestMapping("/essaytable")
    public String EssayTableShow(Model model){
        List<Essay> essays = redisServiceUtil.getRedisEssays();
        model.addAttribute("essays",essays);
        return "/admin/essaytable";
    }

    /**
     * 通过文章id删除该文章
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteessay")
    @LogAnnotation(detail = "删除了一篇文章",operator = "删除")
    public String EssayDelete(long id){
        String key = "essayby" + id;
        essayMapper.deleteEssayById(id);
        redisTemplate.delete(key);
        redisTemplate.delete("essays");
        return "删除成功";
    }

    /**
     * 通过文章id跳转到对应的文章修改界面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/modifyessay_id={id}")
    @LogAnnotation(detail = "修改了一篇文章",operator = "修改")
    public String GoModifyEssay(@PathVariable("id") Long id,Model model){
        Essay essay = redisServiceUtil.getRedisEssayById(id);
        model.addAttribute("essay",essay);
        return "admin/modifyessay";
    }

    /**
     * 修改文章的提交接口
     * @param text
     * @param id
     * @param title
     * @param author
     * @param original
     * @param label
     * @param essaydescribe
     * @param essayimg
     * @return
     */
    @LogAnnotation(detail = "修改了一篇文章",operator = "上传")
    @ResponseBody
    @PostMapping("/modfifyessay")
    public String ModifyEssay(String text,Long id,String title,String author,String original,String[] label,String essaydescribe,MultipartFile essayimg){
        // 判断标题是否为空
        if(title.equals("")){
            return "文章标题不能为空";
        }
        // 判断文章内容是否为空
        else if(text.equals("")){
            return "文章内容不能为空";
        }
        // 判断label是否为空
        else if(label == null){
            return "请选择文章的标签";
        }
        // 判断文章描述是否为空
        else if(essaydescribe.equals("")){
            return "文章描述不能为空";
        }
        // 判断封面图片是否为空
        else if(essayimg.isEmpty()){
            return "请上传封面图片";
        }
        String labels = ""; // 标签整合，用逗号分隔
        int start = 0;
        for(String s:label){
            if(start == 0){
                labels += s;
                start = 1;
            }
            else{
                labels += ',' + s;
            }
        }
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String formatTime = formatter.format(date); //当前日期时间

        // 文章封面上传，保存路径
        String path = essayurl + id;
        System.out.println(path);
        FileUpload.upload2(essayimg,path,"coverimg.jpg");

        // 保存文章md
        SaveFile saveFile = new SaveFile();
        saveFile.SavaAsmd(id,text,essayurl);
        Essay essay = new Essay(id,title,text,author,labels,formatTime,original,0,path,essaydescribe);
        essayMapper.modifyEssayById(essay);
        redisTemplate.delete("essays");
        redisTemplate.delete("essayby" + id);
        return "文章修改成功";
    }

}
