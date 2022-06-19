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

    /**
     * 临时上传目录
     */
    @Value(value = "${store.tmp.uploadpath}")
    public  String tempPath;

    /**
     * 文件存储目录
     */
    @Value(value = "${store.file.uploadpath}")
    public  String filePath;

    /**
     * pdf存储目录
     */
    @Value(value = "${store.file.pdf}")
    public  String pdfPath;

    /**
     * word存储目录
     */
    @Value(value = "${store.file.word}")
    public  String wordPath;

    /**
     * 图片存储目录
     */
    @Value(value = "${store.file.img}")
    public  String imgPath;

    /**
     * 证件照存储目录
     */
    @Value(value = "${store.file.idcard}")
    public  String idCard;


    /**
     * Python 目录
     */
    @Value(value = "${python.env}")
    private String py_env;

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

    public String getIdCard() {
        return commom(idCard);
    }

    public String getPy_env() {
        String path=py_env.substring(0,py_env.lastIndexOf("/"));
        return commom(path)+"/";
    }

    public String getPy_env_file() {
        String file=py_env.substring(py_env.lastIndexOf("/")+1,py_env.length());
        return getPy_env()+file;
    }
}
