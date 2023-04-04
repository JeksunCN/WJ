package com.evan.wj.result;

import com.sun.xml.internal.txw2.output.ResultFactory;
import lombok.Data;

/**
 * 结果响应类
 */
@Data
public class Result{
    //响应码
    private  int code;
    private String message;
    private Object result;

    public Result(int code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Result(int code) {
        this.code = code;
    }
}
