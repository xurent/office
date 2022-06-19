package com.xurent.office.model.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    /**
     * 状态码 消息 数据
     */
    @ApiModelProperty(value = "响应状态码",example = "0")
    private  int code;
    @ApiModelProperty(value = "响应描述",example = "success")
    private String msg;
    @ApiModelProperty(value = "响应数据",example = "<T>")
    private  Object data;

    private Result(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.code == ResultCode.SUCCESS.getCode();
    }

    public static Result ofSuccess(){
        return new Result(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getDesc());
    }

    public static Result ofSuccess(Object o){
        return new Result(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getDesc(),o);
    }

    public static Result ofSuccess(int code,String msg,Object o){
        return new Result(code,msg,o);
    }

    public static Result ofError(int code,String msg,Object o){
        return new Result(code,msg,o);
    }

    public static Result ofSuccess(String msg){
        return new Result(ResultCode.SUCCESS.getCode(),msg);
    }

    public static Result ofSuccess(String msg,Object o){
        return new Result(ResultCode.SUCCESS.getCode(),msg,o);
    }

    public static Result ofError(){
        return new Result(ResultCode.ERROR.getCode(),ResultCode.ERROR.getDesc());
    }

    public static Result ofError(String msg){
        return new Result(ResultCode.ERROR.getCode(),msg);
    }

    public static Result ofError(Object o){
        return new Result(ResultCode.ERROR.getCode(),ResultCode.ERROR.getDesc(),o);
    }

    public static Result ofError(String msg,Object o){
        return new Result(ResultCode.ERROR.getCode(),msg,o);
    }


}
