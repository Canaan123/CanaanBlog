package com.example.cananblog.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Essay implements Serializable {
    private long essayid;
    private String essaytitle;
    private String essaycontent;
    private String essayauthor;
    private String label;
    private String updatetime;
    private String original;
    private long visits;
    private String essaypicture;
    private String essaydescribe;

}
