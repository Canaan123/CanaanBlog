package com.example.cananblog.control.admin;

import com.example.cananblog.mapper.EssayMapper;
import com.example.cananblog.mapper.InformationMapper;
import com.example.cananblog.mapper.ReviewMapper;
import com.example.cananblog.utils.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class IndexController {

    @Autowired
    EssayMapper essayMapper;
    @Autowired
    InformationMapper informationMapper;
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping({"/admin/*", "/admin/index"})
    public String index(Model model){
        Map<String,Long> map = new HashMap<>();
        long essayNumber = essayMapper.queryEssayNumber();// 文章数量
        long visits = essayMapper.queryVisits(); // 文章总阅读量
        long reviewNumber =reviewMapper.queryReviewNumber(); // 评论数量
        long originalNumber = essayMapper.queryOriginalNumber();// 原创文章数量
        long reprintNumber = essayNumber - originalNumber; // 转载文章数量
        map.put("essaynum",essayNumber);
        map.put("visits",visits);
        map.put("reviewnum",reviewNumber);
        map.put("originalNumber",originalNumber);
        map.put("reprintNumber",reprintNumber);
        model.addAttribute("infor",map);
        model.addAttribute("bloggername",informationMapper.queryBloggerName());
        model.addAttribute("blogdescribe",informationMapper.queryBlogDescribe());

        return "admin/index";
    }

    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        redisTemplate.opsForValue().set("verify_code",text,1, TimeUnit.MINUTES);
        VerificationCode.output(image,resp.getOutputStream());//输出验证码图片流
    }

    @RequestMapping("/login")
    public String login(){
        return "admin/login";
    }


}
