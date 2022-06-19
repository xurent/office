package com.xurent.office.exception;

import com.xurent.office.exception.pleiades.ServerCode;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author xurent
 * @date 2021/4/9 16:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -5661907224553017158L;

    private Integer code;

    public BusinessException(ServerCode serverCode) {
        super(serverCode.getMessage());
        this.code = serverCode.getCode();
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public static BusinessException code(int code, String message, Object... args) {
        if (args.length == 0) {
            return new BusinessException(code, message);
        }
        return new BusinessException(code, String.format(message, args));
    }

    public static BusinessException code(ServerCode code, Object... args) {
        return code(code.getCode(), code.getMessage(), args);
    }

}
