package com.example.cananblog.bean;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Review {
    private long reviewid;
    private String reviewcontent;
    private String reviewessayid;
    private String reviewtime;
    private String reviewmanid;
}
