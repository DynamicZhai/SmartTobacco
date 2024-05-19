package com.anranruozhu.service;


import java.util.Map;

public interface SmsService {

    /**
     * 发送短信验证码
     * @param mobile 手机号码
     * @return 是否发送成功
     */
    boolean send(Map<String, Object> param, String phone);
    boolean send1(Map<String, Object> param, String phone);
}