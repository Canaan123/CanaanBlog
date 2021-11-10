package com.example.cananblog.bean;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Data
public class Review implements Serializable {
    private long reviewid;
    private String reviewcontent;
    private String reviewessayid;
    private String reviewtime;
    private String reviewmanid;
}
