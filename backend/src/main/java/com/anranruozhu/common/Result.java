package com.anranruozhu.common;

import lombok.Data;

/**
 * @author anranruozhu
 * @ClassName Result
 * @Description 返回结果类
 * @create 2024/4/3 10:54
 **/
@Data
public class Result
{
    private Integer code;
    private Object data;
    private String msg;
}
