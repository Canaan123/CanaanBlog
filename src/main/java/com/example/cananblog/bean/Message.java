package com.example.cananblog.bean;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel("消息实体类")
@Data
public class Message implements Serializable {
    private long messageid;
    private String message;
    private String messagetime;

    public Message(String message, String messagetime) {
        this.message = message;
        this.messagetime = messagetime;
    }

    public Message() {
    }
}
