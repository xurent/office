package com.xurent.office.exception.pleiades;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServerException extends RuntimeException {

    private Integer code;

    public ServerException(ServerCode serverCode) {
        super(serverCode.getMessage());
        this.code = serverCode.getCode();
    }

    public ServerException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public static ServerException code(int code, String message, Object... args) {
        if (args.length == 0) {
            return new ServerException(code, message);
        }
        return new ServerException(code, String.format(message, args));
    }

    public static ServerException code(ServerCode code, Object... args) {
        return code(code.getCode(), code.getMessage(), args);
    }

}

