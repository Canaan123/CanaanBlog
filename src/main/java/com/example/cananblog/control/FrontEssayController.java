package com.example.cananblog.control;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.cananblog.bean.Essay;
import com.example.cananblog.mapper.EssayMapper;
import com.example.cananblog.utils.IpUtil;
import com.example.cananblog.utils.MarkdownUtils;
import com.example.cananblog.utils.RedisServiceUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class FrontEssayController {

    @Autowired
    EssayMapper essayMapper;
    @Resource
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    ServletContext servletContext;
    @Autowired
    RedisServiceUtil redisServiceUtil;
    @Value("${file.essayurl}")
    String essayurl;

    /**
     * 文章展示界面
     * @param model
     * @param pageNum
     * @return
     */
    @RequestMapping("/essay")
    public String GoEssay(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        // 查询访问量排名前6的文章
        List<Essay> hotessay = redisServiceUtil.getRedisHotEssays();
        model.addAttribute("hotessay",hotessay);
        // 引入分页
        PageHelper.startPage(pageNum,5);
        List<Essay> essays = essayMapper.queryEssayList();
//        List<Essay> essays = redisServiceUtil.getRedisEssays();

        PageInfo<Essay> pageInfo = new PageInfo<>(essays);
        model.addAttribute("essays",pageInfo);

        return "essay";
    }

    /**
     * 展示对应id的文章
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("readessayid={id}")
    public String ShowEssay(@PathVariable("id")long essayId, Model model,HttpServletRequest request){
        String ipAddr = IpUtil.getIpAddr(request);
        String IpVisitEssay = "isViewd:" + essayId + ":" + ipAddr; // ip近期访问文章id
        String EssayviewCount = "viewCount:" + essayId; // 文章id浏览量
        // 该ip十分钟内没有浏览过该文章,浏览记录+1
        if(!redisTemplate.hasKey(IpVisitEssay)){
            redisTemplate.opsForValue().set(IpVisitEssay,1,10, TimeUnit.MINUTES);
            if(redisTemplate.hasKey(EssayviewCount)){
                redisTemplate.opsForValue().increment(EssayviewCount);
            }
            else{
                redisTemplate.opsForValue().set(EssayviewCount,1);
            }
        }

        Essay essay = redisServiceUtil.getRedisEssayById(essayId);
        essay.setEssaycontent(MarkdownUtils.markdownToHtmlExtensions(essay.getEssaycontent()));// md转化为html
        essay.setVisits(essay.getVisits()+(Integer)redisTemplate.opsForValue().get(EssayviewCount)); // 文章id访问量 = redis中访问量+数据库中访问量
        model.addAttribute("essay",essay);

        return "read";
    }

    /**
     * 文章搜索 text为搜索的内容
     * @param text
     * @return
     */
    @RequestMapping("/searchessay")
    public String SearchEssay(String text,Model model){
        model.addAttribute("text",text);
        // 查询包括标签的文章
        String text2 = "%"; // 进行完全模糊匹配
        for(int i = 0; i < text.length(); i++){
            text2 = text2 + text.charAt(i) + "%";
        }
        List<Essay> essays = essayMapper.queryMatchingEssay(text2);
        model.addAttribute("essays",essays);
        model.addAttribute("text",text);
        // 查询访问量排名前6的文章
        List<Essay> hotessay = essayMapper.queryhotEssay();
        model.addAttribute("hotessay",hotessay);
        return "searchessay";
    }

    /**
     * 文章分类显示
     * @param model
     * @param type
     * @return
     */
    @RequestMapping("/classifyessay")
    public String ShowAllEssay(Model model,String type){
        // 查询包括标签的文章
        List<Essay> essays = essayMapper.queryEssayContainLabel(type);
        model.addAttribute("essays",essays);
        model.addAttribute("type",type);
        // 查询访问量排名前6的文章
        List<Essay> hotessay = essayMapper.queryhotEssay();
        model.addAttribute("hotessay",hotessay);
        return "category";
    }

    /**
     * 文章下载
     * @param essayid
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping ("/downloadEssay")
    public void DownloadEssayById(String essayid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取文件的绝对路径
        String realPath = essayurl + essayid + "/";
        String fileName = "essay.md";
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
    }
    /**
     * 点赞量增加
     */

    @ResponseBody
    @PostMapping("/essayLike")
    public String essayLike(Long essayid,HttpServletRequest request){
        String ipAddr = IpUtil.getIpAddr(request);
        String key = ipAddr + "::" + essayid + ":" + "like";
        if(redisTemplate.hasKey(key)){
            return "不可以重复点赞哦";
        }
        redisTemplate.opsForValue().set(key,1,3,TimeUnit.DAYS);
        essayMapper.addLikeNumber(essayid);
        return "点赞成功";
    }





}
