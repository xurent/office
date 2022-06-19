package com.xurent.office.controller;


import com.xurent.office.annotation.UserInfo;
import com.xurent.office.model.User;
import com.xurent.office.model.message.Result;
import com.xurent.office.model.properties.CommonProperties;
import com.xurent.office.model.properties.FileProperties;
import com.xurent.office.utils.PDFUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/office/poi")
public class PDFController {

    @Autowired
    private FileProperties fileProperties;

    @Autowired
    private CommonProperties commonProperties;


    /**
     * pdf转word
     * @param file
     * @param user
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/pdf-to-word")
    public Result uploadImage(@RequestParam("file") MultipartFile file, @UserInfo User user) throws IOException {

        if(file==null){
            return Result.ofError("上传失败!");
        }
        if(!file.getOriginalFilename().endsWith(".pdf")){
            log.warn("用户：{} 上传Pdf 转 Word 失败。{}",user.getAccount(),file.getOriginalFilename());
            return Result.ofError("文件格式错误!");
        }
        String fileName=file.getOriginalFilename().trim();
        //临时存储路径
        String fileDir=fileProperties.getTempPath()+user.getAccount()+"/pdf/";
        String wordPath=fileDir+fileName;
        File word=new File(wordPath);
        File worddir=new File(fileDir);
        //路径不存在则创建
        if(!worddir.exists()){
            worddir.mkdirs();
        }
        file.transferTo(word);
        //保存路径
        String saveFile=user.getAccount()+"/"+UUID.randomUUID().toString().replaceAll("-","")+".docx";
        String saveDir= fileProperties.getWordPath()+saveFile;

        log.warn("用户：{} ,temp_dir:{}，trans_dir:{}",user.getAccount(),fileDir,saveDir);
        PDFUtil.trans(wordPath,saveDir);

        String path=commonProperties.getResource()+fileProperties.wordPath.
                replaceAll("/static/","")+saveFile;
        log.warn("用户：{} Pdf 转 Word 成功，返回路径：{}",user.getAccount(),path);
        return Result.ofSuccess("上传成功",path);

    }



}
