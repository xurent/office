package com.xurent.office.model.vo;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class IdCardVo {

    /**
     * 源路径
     */
    @NotBlank
    private String srcPath;

    /**
     * 尺寸
     */
    @NotBlank
    private String size;

    /**
     * 背景色
     */
    @Max(value = 255,message = "rgb最大255")
    @Min(value = 0,message = "rgb最小0")
    @NotNull(message = "rgb不能为空")
    private Integer color_r;

    @Max(value = 255,message = "rgb最大255")
    @Min(value = 0,message = "rgb最小0")
    @NotNull(message = "rgb不能为空")
    private Integer color_g;

    @Max(value = 255,message = "rgb最大255")
    @Min(value = 0,message = "rgb最小0")
    @NotNull(message = "rgb不能为空")
    private Integer color_b;

}
