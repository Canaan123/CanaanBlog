package com.example.cananblog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@ApiModel("资源实体类")
@Data
public class Resource {
    private long resourceid;
    private String resourcename;
    private String resourcetime;
    private double resourcesize;
}
