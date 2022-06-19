package com.xurent.office.model.util;

import lombok.Data;

@Data
public class PythonPhoto {

    /**
     * 源路径
     */
    private String srcPath;

    /**
     * 保存路径
     */
    private String savePath;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 尺寸
     */
    private String size;

    /**
     * 背景色
     */
    private String color;

    /**
     * python运行所在目录
     */
    private  String exec;
}
