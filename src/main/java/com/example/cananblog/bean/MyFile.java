package com.example.cananblog.bean;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@ApiModel("文件实体类")
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFile implements Serializable {

    //文件编号
    private long id;
    //文件名字
    private String name;
    //文件内容
    private byte[] content;
    //文件扩展名
    private String ext;
    //文件创建作者
    private String author;
    // 文件创建时间
    private String createtime;
    // 最近更新时间
    private String updatetime;
    // 组名
    private String groudName;
    // 文件完整路径
    private String pathName;
    // 下载文件的密码
    private String password;
    // 文件提取码
    private String code;
}
