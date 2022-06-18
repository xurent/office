package com.xurent.office.exception;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.xurent.office.emums.ServerCodeEnum;
import com.xurent.office.exception.pleiades.JSONResponse;
import com.xurent.office.exception.pleiades.ServerCode;
import com.xurent.office.exception.pleiades.ServerException;
import com.xurent.office.model.properties.ErrorMapProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;


import javax.annotation.Resource;
import java.util.List;

/**
 * @author xurent
 * @date 2021/4/12 11:54
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    ErrorMapProperties errorMapProperties;

    /**
     * 异常统一消息处理
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public JSONResponse httpMessageNotReadableExceptionHandler(Exception e) {
        log.error(">>>>>>>> 请求参数格式不正确", e);
        return new JSONResponse(ServerCodeEnum.REQUEST_PARAM_ERROR.getCode(),
                errorMapProperties.getMaps().get(ServerCodeEnum.REQUEST_PARAM_ERROR.getCode()));
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public JSONResponse bindExceptionHandler(MethodArgumentNotValidException e) {
        log.warn(">>>>>>>> MethodArgumentNotValidException", e);
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> objErrorList = bindingResult.getAllErrors();
        return objErrorList.size() > 0 ? new JSONResponse(ServerCode.ILLEGAL_REQUEST.getCode(),
                objErrorList.get(0).getDefaultMessage())
                : new JSONResponse(ServerCode.ILLEGAL_REQUEST.getCode()
                , errorMapProperties.getMaps().get(ServerCodeEnum.REQUEST_PARAM_ERROR.getCode()));
    }

    /**
     * 请求参数类型不支持
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public JSONResponse handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.warn(">>>>>>>> 请求参数类型不支持", e);
        return new JSONResponse(ServerCodeEnum.COMMON.getCode(), e.getMessage());
    }

    /**
     * 参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public JSONResponse bindExceptionHandler(BindException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> errorMsg.append("入参[").append(error.getField()).append("]").append(error.getDefaultMessage()).append("；"));
        }
        log.warn(">>>>>>>> MethodArgumentBindException fields: {}", errorMsg.toString());
        return new JSONResponse(ServerCode.ILLEGAL_REQUEST.getCode(),
                errorMapProperties.getMaps().get(ServerCodeEnum.REQUEST_PARAM_ERROR.getCode()));
    }

    /**
     * 异常统一消息处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public JSONResponse httpRequestMethodNotSupportedExceptionHandler(Exception e) {
        log.error(">>>>>>>> 请求方式不支持", e);
        return new JSONResponse(ServerCodeEnum.REQUEST_METHOD_NOT_SUPPORT.getCode(),
                errorMapProperties.getMaps().get(ServerCodeEnum.REQUEST_METHOD_NOT_SUPPORT.getCode()));
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public JSONResponse bindExceptionHandler(CommonException e) {
        Integer code = e.getCode();
        String message = e.getMessage();

        /*
         * 主动跑出异常时，可只提供错误代码，通过message.yml获取对应message
         */
        if (StringUtils.isBlank(message)) {
            message = errorMapProperties.getMaps().containsKey(code) ? errorMapProperties.getMaps().get(code) :
                    errorMapProperties.getMaps().get(ServerCodeEnum.COMMON.getCode());
        }
        log.warn(">>>>>>>> 业务主动抛出异常: {}", message, e);
        return new JSONResponse(code, message);
    }

    @ExceptionHandler(CannotGetJdbcConnectionException.class)
    @ResponseBody
    public JSONResponse bindCannotGetJdbcConnectionExceptionHandler(CannotGetJdbcConnectionException e) {
        log.error(">>>>>>>> DB链接超时异常", e);
        return new JSONResponse(ServerCodeEnum.COMMON.getCode(), e.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public JSONResponse handleMultipartException(MultipartException e) {
        log.error(">>>>>>>> 文件上传异常", e);
        return new JSONResponse(ServerCodeEnum.COMMON.getCode(), e.getMessage());
    }

    /**
     * 服务异常统一消息处理
     */
    @ExceptionHandler(ServerException.class)
    @ResponseBody
    public JSONResponse bindExceptionHandler(ServerException e) {
        log.error(">>>>>>>> 服务异常统一消息处理", e);
        return new JSONResponse(e.getCode(), e.getMessage());
    }

    /**
     * 异常统一消息处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JSONResponse bindExceptionHandler(Exception e) {
        log.error(">>>>>>>> 统一异常处理", e);
        return new JSONResponse(ServerCodeEnum.COMMON.getCode(),
                errorMapProperties.getMaps().get(ServerCodeEnum.COMMON.getCode()));
    }
}
