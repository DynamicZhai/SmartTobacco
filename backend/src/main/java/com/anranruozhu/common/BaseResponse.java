package com.anranruozhu.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author anranruozhu
 * @ClassName BaseResponse
 * @Description 通用的请求返回类实现序列化
 * @create 2024/4/3 10:56
 **/
@Data
public class BaseResponse<T> implements Serializable {
    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
