package com.example.cananblog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**
     * 获取当前日期时间
     * @return
     */
    public static String getCurrentDateTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
