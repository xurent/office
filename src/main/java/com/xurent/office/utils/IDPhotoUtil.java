package com.xurent.office.utils;

import com.xurent.office.emums.ServerCodeEnum;
import com.xurent.office.exception.CommonException;
import com.xurent.office.model.util.PythonPhoto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class IDPhotoUtil {

    private static final String run="python ";

    private static Map<Integer,String> map=new HashMap<>();
    static {

        map.put(1,"一寸");
        map.put(2,"小一寸");
        map.put(3,"大一寸");
        map.put(4,"二寸");
        map.put(5,"五寸");
    }

    /**
     *
     * @param pythonPhoto
     * @param path  照片生产路径
     * @return
     */
    public  static boolean makePhoto(PythonPhoto pythonPhoto,String path){
        File file=new File(path);
        //创建存储路径
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
            log.warn("python 创建照片存储路径:{}",path);
        }
        //处理照片
        getPython(pythonPhoto);

        if(file.exists()){
            log.warn("python 生成证件照成功:{}",path);
            return true;
        }else{
            log.warn("python 生成证件照失败:{}",path);
            throw new CommonException(ServerCodeEnum.ID_PHOTO_FAIL);
        }

    }

    //cmd  源文件  存储路径  尺寸   颜色  名称
    private   static void getPython(PythonPhoto pythonPhoto){
        try {
            String cmd = run + pythonPhoto.getExec() + " " + pythonPhoto.getSrcPath() + " "
                    + pythonPhoto.getSavePath() + " " + pythonPhoto.getSize() + " " +pythonPhoto.getColor()
                    + " " + pythonPhoto.getFileName();
            log.warn("python cmd:{}",cmd);
            Process pr = Runtime.getRuntime().exec(cmd, null, new File(pythonPhoto.getExec()).getParentFile());
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            String result = "";
            while ((line = in.readLine()) != null) {
                result += line;

            }
            in.close();
            pr.waitFor();
            if(StringUtils.isNotBlank(result)&&result.contains("finish")){
                log.warn("python 执行成功: {} ",result);
            }else{
                log.warn("python 执行失败: {} ",result);
                throw new CommonException(ServerCodeEnum.ID_PHOTO_FAIL);
            }

        }catch (Exception e){
            log.warn("python 调用失败:{}",e.getMessage());
            throw new CommonException(ServerCodeEnum.ID_PHOTO_FAIL);
        }


    }


    /**
     * 获取尺寸名
     * @param src
     * @return
     */
    public static  String getSizeName(String src){

        Integer p=Integer.parseInt(src);
        return map.get(p);

    }

}
