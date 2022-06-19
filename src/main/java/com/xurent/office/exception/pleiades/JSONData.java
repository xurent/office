package com.xurent.office.exception.pleiades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JSONData extends JSONResponse {

    private Object data;

    JSONData(ServerCode code, Object data) {
        super(code);
        this.data = data;
    }

    JSONData(ServerCode code) {
        this(code, null);
    }

    JSONData(int code, String message, Object data) {
        super(code, message);
        this.data = data;
    }

    public static JSONData create(ServerCode serverCode) {
        return new JSONData(serverCode);
    }

    public static JSONData create(ServerCode serverCode, Object data) {
        return new JSONData(serverCode, data);
    }

    public static JSONData create(int code, String message) {
        return new JSONData(code, message, null);
    }

    public static JSONData create(int code, String message, Object data) {
        return new JSONData(code, message, data);
    }

    public static JSONData success(Object data) {
        return new JSONData(ServerCode.SUCCESS, data);
    }

    public static JSONData success() {
        return new JSONData(ServerCode.SUCCESS);
    }
}