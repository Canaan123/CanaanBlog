package com.example.cananblog.control.admin;

import com.example.cananblog.bean.Essay;
import com.example.cananblog.bean.Review;
import com.example.cananblog.mapper.EssayMapper;
import com.example.cananblog.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    ReviewMapper reviewMapper;

    @GetMapping("/admin/reviewtable")
    public String ReviewTableShow(Model model){
        List<Review> reviews = reviewMapper.queryReviewList();
        model.addAttribute("reviews",reviews);
        return "admin/reviewtable";
    }


    /**
     * 通过id删除对应的评论
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/admin/deletereview")
    public String ReviewDelete(long id){
        reviewMapper.deleteReviewById(id);
        return "删除成功";
    }

    /**
     * 通过id修改评论信息
     * @param id
     * @param content
     * @param model
     * @return
     */
    @PostMapping("/admin/postreview")
    public String PostReview(long id,String content,Model model){
        reviewMapper.modifyReviewContentById(id,content);
        List<Review> reviews = reviewMapper.queryReviewList();
        model.addAttribute("reviews",reviews);
        return "/admin/reviewtable";
    }
}
