package com.example.cananblog.control.admin;

import com.example.cananblog.bean.Essay;
import com.example.cananblog.bean.Review;
import com.example.cananblog.log.LogAnnotation;
import com.example.cananblog.mapper.EssayMapper;
import com.example.cananblog.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ReviewController {

    @Autowired
    ReviewMapper reviewMapper;
    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @LogAnnotation(detail = "查看文章评论内容",operator = "查询")
    @GetMapping("/reviewtable")
    public String ReviewTableShow(Model model){
        List<Review> reviews = null;
        if(redisTemplate.hasKey("reviews")){
            reviews = (List<Review>) redisTemplate.opsForValue().get("reviews");
        }
        else{
            reviews = reviewMapper.queryReviewList();
        }
        model.addAttribute("reviews",reviews);
        return "admin/reviewtable";
    }


    /**
     * 通过id删除对应的评论
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/deletereview")
    public String ReviewDelete(long id){
        reviewMapper.deleteReviewById(id);
        redisTemplate.delete("reviews");
        return "删除成功";
    }

    /**
     * 通过id修改评论信息
     * @param id
     * @param content
     * @param model
     * @return
     */
    @PostMapping("/postreview")
    public String PostReview(long id,String content,Model model){
        reviewMapper.modifyReviewContentById(id,content);
        List<Review> reviews = reviewMapper.queryReviewList();
        redisTemplate.opsForValue().set("reviews",reviews);
        model.addAttribute("reviews",reviews);
        return "/admin/reviewtable";
    }
}
