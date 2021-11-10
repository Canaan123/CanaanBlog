package com.example.cananblog.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Random;

public class FileUpload {


    /**
     * 文件上传到指定路径 生成随机名字
     * @param file 文件
     * @param path 路径
     * @return
     */
    public static String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        Random random = new Random();
        fileName =  random.nextInt() + fileName.substring(fileName.indexOf("."), fileName.length());
        File targetFile = new File(path, fileName);
        //保存
        try {
            // 判断父级目录师父存在
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();//创建父级文件路径
                targetFile.createNewFile();//创建文件
            }
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 文件上传到指定路径 生成指定名字
     * @param file
     * @param path
     * @param name
     * @return
     */
    public static String upload2(MultipartFile file, String path,String name) {
        String fileName = name;
        Random random = new Random();
        File targetFile = new File(path, fileName);
        //保存
        try {
            // 判断父级目录师父存在
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();//创建父级文件路径
                targetFile.createNewFile();//创建文件
            }
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
