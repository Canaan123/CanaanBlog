package com.example.cananblog.bean;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ApiModel("评论实体类")
@Data
public class Review implements Serializable {
    private long reviewid;
    private String reviewcontent;
    private String reviewessayid;
    private String reviewtime;
    private String reviewmanid;
}
