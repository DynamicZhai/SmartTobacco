package com.anranruozhu.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.anranruozhu.common.ErrorCode.SYSTEM_ERROR;

/*全局错误处理*/
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * 默认全局异常处理。
     * @param e the e
     * @return ResultData
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result exception(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        Result res = new Result();
        res.setCode(SYSTEM_ERROR.getCode());
        res.setMsg(e.getMessage());
        return res;
    }

}
