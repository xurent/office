package com.xurent.office.exception;

import com.xurent.office.emums.ServerCodeEnum;
import com.xurent.office.exception.pleiades.ServerCode;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author xurent
 * @date 2021/4/9 16:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = -5661907224553017158L;

    private Integer code;

    public CommonException(ServerCode serverCode) {
        super(serverCode.getMessage());
        this.code = serverCode.getCode();
    }

    public CommonException(ServerCodeEnum serverCodeEnum) {
        super("");
        this.code = serverCodeEnum.getCode();
    }

    public CommonException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(Integer code) {
        super("");
        this.code = code;
    }

    public static CommonException code(int code, String message, Object... args) {
        if (args.length == 0) {
            return new CommonException(code, message);
        }
        return new CommonException(code, String.format(message, args));
    }

    public static CommonException code(ServerCode code, Object... args) {
        return code(code.getCode(), code.getMessage(), args);
    }

}
