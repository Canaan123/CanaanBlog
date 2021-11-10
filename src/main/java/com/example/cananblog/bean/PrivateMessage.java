package com.example.cananblog.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrivateMessage extends Message{
    String qq;

    public PrivateMessage(String message, String messagetime, String qq) {
        super(message, messagetime);
        this.qq = qq;
    }

    public PrivateMessage() {
    }
}
