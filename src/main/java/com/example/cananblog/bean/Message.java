package com.example.cananblog.bean;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
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
