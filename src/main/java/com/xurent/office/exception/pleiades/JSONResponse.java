package com.xurent.office.exception.pleiades;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JSONResponse {

    private int code;
    private String message;

    public JSONResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public JSONResponse(ServerCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
