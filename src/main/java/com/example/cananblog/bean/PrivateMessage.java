package com.example.cananblog.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class PrivateMessage extends Message implements Serializable {
    String qq;

    public PrivateMessage(String message, String messagetime, String qq) {
        super(message, messagetime);
        this.qq = qq;
    }

    public PrivateMessage() {
    }
}
