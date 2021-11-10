package com.example.cananblog.control;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.cananblog.bean.Essay;
import com.example.cananblog.mapper.EssayMapper;
import com.example.cananblog.utils.MarkdownUtils;
import com.example.cananblog.utils.RedisServiceUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

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

    /**
     * 文章展示界面
     * @param model
     * @param pageNum
     * @return
     */
    @RequestMapping("/essay")
    public String GoEssay(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        // 查询访问量排名前6的文章
        List<Essay> hotessay = essayMapper.queryhotEssay();
        model.addAttribute("hotessay",hotessay);
        // 引入分页
        PageHelper.startPage(pageNum,5);
        List<Essay> essays = essayMapper.queryEssayList();

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
    public String ShowEssay(@PathVariable("id")long id, Model model){
        Essay essay = redisServiceUtil.getRedisEssayById(id);
        essay.setEssaycontent(MarkdownUtils.markdownToHtmlExtensions(essay.getEssaycontent()));// md转化为html
        model.addAttribute("essay",essay);
        essayMapper.increaseVisits(id);
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



}
