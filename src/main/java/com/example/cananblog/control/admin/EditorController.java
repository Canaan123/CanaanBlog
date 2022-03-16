package com.example.cananblog.control.admin;

import com.example.cananblog.bean.Essay;
import com.example.cananblog.mapper.EssayMapper;
import com.example.cananblog.mapper.InformationMapper;
import com.example.cananblog.utils.FileUpload;
import com.example.cananblog.utils.IpUtil;
import com.example.cananblog.utils.SaveFile;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class EditorController {

    @Autowired
    EssayMapper essayMapper;
    @Autowired
    InformationMapper informationMapper;
    @Autowired
    IpUtil ipUtil;
    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Value("${file.essayurl}")
    String essayurl;
    @Value("${file.pictureurl}")
    String pictureurl;






    @RequestMapping("/editessay")
    public String edit(Model model){
        Essay essayTemplate = (Essay)redisTemplate.opsForValue().get("essayTemplate");
        if(essayTemplate == null){
            return "admin/editessay";
        }
        model.addAttribute("essaycontent",essayTemplate.getEssaycontent());
        model.addAttribute("essaydescribe",essayTemplate.getEssaydescribe());
        model.addAttribute("essaytitle",essayTemplate.getEssaytitle());
        return "admin/editessay";
    }

    /**
     * 文章编辑提交接口，数据保存到essay表中
     * @param text
     * @param title
     * @param author
     * @param original
     * @param label
     * @param essaydescribe
     * @param essayimg
     * @return
     */
    @ResponseBody
    @PostMapping("/postessay")
    public String postessay(String text, String title, String author, String original, String[] label, String essaydescribe, MultipartFile essayimg){
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

        // 标签整合，用逗号分隔
        String labels = "";
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
        long id = essayMapper.queryLastId() + 1;
        // 设置当前日期
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String formatTime = formatter.format(date);
        // 文章封面上传，保存路径
        String path = essayurl + id;
        FileUpload.upload2(essayimg,path,"coverimg.jpg");
        // 保存文章md
        SaveFile saveFile = new SaveFile();
        saveFile.SavaAsmd(id,text,essayurl);
        Essay essay = new Essay(id,title,text,author,labels,formatTime,original,0,path,essaydescribe,0);
        essayMapper.addEssay(essay);

        // 删除临时文章记录
        informationMapper.deleteEssayTemp();
        redisTemplate.delete("essays");
        return "发表成功";
    }


    /**
     * 本地文件上传
     * @param file
     * @param request
     * @param response
     */
    @RequestMapping("/imageUpload")
    public void imageUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file,
                            HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter wirte = null;
        JSONObject json = new JSONObject();
        try {
            wirte = response.getWriter();
            //文件存放的路径
            String path = pictureurl;
            String url = ipUtil.getUrl() + "/upload/" + FileUpload.upload(file, path);
//            String url = "http://localhost:8081"
//                    + request.getContextPath()
//                    + "/upload/"
//                    + FileUpload.upload(file, path);
            System.out.println(path);
            System.out.println(url);
            json.put("success", 1);
            json.put("message", "hello");
            json.put("url", url);
            System.out.println(json);
        } catch (Exception e) {
        }finally{
            wirte.print(json);
            wirte.flush();
            wirte.close();
        }
        System.out.println("imageUpload");
    }


    @ResponseBody
    @PostMapping("/postessaytemporary")
    public String postEssayTemporary(String essaycontent,String essaydescribe,String essaytitle){
        // 保存临时文章记录到数据库information表中
        //保存临时文章记录到redis中
        redisTemplate.opsForValue().set("essayTemplate",new Essay().setEssaycontent(essaycontent).setEssaydescribe(essaydescribe).setEssaytitle(essaytitle));
        return "success";
    }
}
