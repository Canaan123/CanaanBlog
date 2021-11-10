package com.example.cananblog.utils;


import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {

    public void SavaAsmd(long id,String content,String saveurl){
        FileWriter writer = null;
        String url = saveurl + id + "\\essay.md";
        System.out.println(url);
        try {
            writer = new FileWriter(url, false);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
