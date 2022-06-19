package com.xurent.office.model.properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;


@Configuration
@Getter
@Slf4j
public class FileProperties {

    @Value(value = "${store.tmp.uploadpath}")
    public  String tempPath;

    @Value(value = "${store.file.uploadpath}")
    public  String filePath;

    @Value(value = "${store.file.pdf}")
    public  String pdfPath;

    @Value(value = "${store.file.word}")
    public  String wordPath;

    @Value(value = "${store.file.img}")
    public  String imgPath;



    private String commom(String tempPath){
        String urlPath=System.getProperty("user.dir");
        if(urlPath.equals(File.separator)){
            urlPath=tempPath;
        }else{
            urlPath=urlPath+tempPath;
        }
        File file =new File(urlPath);
        if(!file.exists()){
            file.mkdirs();
            log.warn("创建路径:{}",urlPath);
        }
        return  urlPath;
    }


    public String getTempPath() {

        return commom(tempPath);
    }

    public String getFilePath() {

        return commom(filePath);
    }


    public String getPdfPath() {
        return commom(pdfPath);
    }

    public String getWordPath() {
        return commom(wordPath);
    }

    public String getImgPath() {
        return commom(imgPath);
    }
}
