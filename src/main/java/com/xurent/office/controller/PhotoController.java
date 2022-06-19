package com.xurent.office.controller;


import com.xurent.office.annotation.UserInfo;
import com.xurent.office.constant.CommonConstant;
import com.xurent.office.emums.ServerCodeEnum;
import com.xurent.office.exception.CommonException;
import com.xurent.office.exception.pleiades.JSONData;
import com.xurent.office.model.User;
import com.xurent.office.model.properties.CommonProperties;
import com.xurent.office.model.properties.FileProperties;
import com.xurent.office.model.util.PythonPhoto;
import com.xurent.office.model.vo.IdCardVo;
import com.xurent.office.utils.BeanCopyUtils;
import com.xurent.office.utils.IDPhotoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.xurent.office.constant.CommonConstant.*;

@Slf4j
@RestController
@RequestMapping("/office/photo")
public class PhotoController {


    @Autowired
    private FileProperties fileProperties;

    @Autowired
    private CommonProperties commonProperties;

    /**
     * 上传证件照
     * @param file
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/up/id-card")
    public JSONData upIDCard(@RequestParam("file") MultipartFile file, @UserInfo User user) throws IOException {

        if(file==null||file.getSize()<1){
            throw new CommonException(ServerCodeEnum.ID_PHOTO_FAIL);
        }
        String name=file.getOriginalFilename();
        String fileType = name.substring(name.lastIndexOf(".")+1,name.length());
        if(!FILE_JPG.equals(fileType)||!FILE_JPG.equals(fileType)||!FILE_JPG.equals(fileType)){
            throw new CommonException(ServerCodeEnum.ID_PHOTO_TYPE_FAIL);
        }
        //临时存储路径
        String fileDir=fileProperties.getTempPath()+user.getAccount()+"/id-card/";
        String savePath=fileDir+name;
        File image=new File(savePath);
        //路径不存在则创建
        if(!image.getParentFile().exists()){
           image.getParentFile().mkdirs();
        }
        file.transferTo(image);

        log.warn("用户：{} 上传id-card成功。{}",user.getAccount(),savePath);
        return JSONData.success(savePath);
    }


    /**
     *  生成证件照
     * @param cardVo
     * @param user
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/id-card")
    public JSONData makeIDCard(@RequestBody @Validated IdCardVo cardVo, @UserInfo User user) throws IOException {
        File file=new File(cardVo.getSrcPath());
        if(!file.exists()){
            throw new CommonException(ServerCodeEnum.ID_PHOTO_EXIT_FAIL);
        }
        PythonPhoto photo=new PythonPhoto();
        BeanCopyUtils.copy(cardVo,photo);

        //存储路径
        String savePath=fileProperties.getIdCard()+user.getAccount()+"/";
        photo.setSavePath(savePath);
        String color=cardVo.getColor_r()+" "+cardVo.getColor_g()+" "+cardVo.getColor_b();
        photo.setColor(color);
        photo.setSize(IDPhotoUtil.getSizeName(cardVo.getSize()));

        //文件名称
        String fileName=UUID.randomUUID().toString().replaceAll("-","");
        photo.setFileName(fileName);

        //执行脚本file
        photo.setExec(fileProperties.getPy_env_file());

        IDPhotoUtil.makePhoto(photo,savePath+fileName+FILE_IDCard);

        //访问路径
        String path=commonProperties.getResource()+fileProperties.idCard.
                replaceAll("/static/","")+user.getAccount()+"/"+fileName+FILE_IDCard;

        log.warn("用户：{} id-card 生成。{}",user.getAccount(),path);
        return JSONData.success(path);
    }

}
