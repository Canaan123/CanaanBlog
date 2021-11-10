package com.example.cananblog.bean;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Essay {
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

    public Essay(long essayid,String essaytitle, String essaycontent, String essayauthor, String label, String updatetime, String original, String essaypicture, String essaydescribe) {
        this.essayid = essayid;
        this.essaytitle = essaytitle;
        this.essaycontent = essaycontent;
        this.essayauthor = essayauthor;
        this.label = label;
        this.updatetime = updatetime;
        this.original = original;
        this.visits = visits;
        this.essaypicture = essaypicture;
        this.essaydescribe = essaydescribe;
    }

    public Essay(String essaytitle, String essaycontent, String essayauthor, String label, String updatetime, String original) {
        this.essaytitle = essaytitle;
        this.essaycontent = essaycontent;
        this.essayauthor = essayauthor;
        this.label = label;
        this.updatetime = updatetime;
        this.original = original;
    }

    public Essay(long essayid, String essaytitle, String essaycontent, String essayauthor, String label, String updatetime, String original) {
        this.essayid = essayid;
        this.essaytitle = essaytitle;
        this.essaycontent = essaycontent;
        this.essayauthor = essayauthor;
        this.label = label;
        this.updatetime = updatetime;
        this.original = original;
    }



    public Essay() {

    }
}
