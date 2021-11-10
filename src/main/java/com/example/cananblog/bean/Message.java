package com.example.cananblog.bean;


import lombok.Data;

import java.io.Serializable;

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
