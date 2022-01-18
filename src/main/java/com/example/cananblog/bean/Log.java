package com.example.cananblog.bean;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@ApiModel("日志实体类")
@Data
public class Log implements Serializable {
//    long id;
    public String username;
    public String operator;
    public String detail;
    public String time;
    public String backstage;

    public Log(String username, String operator, String detail, String time, String backstage) {
        this.username = username;
        this.operator = operator;
        this.detail = detail;
        this.time = time;
        this.backstage = backstage;
    }

    public Log() {
    }
}
