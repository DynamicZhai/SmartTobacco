package com.anranruozhu.service;

import cn.hutool.json.JSONObject;
import com.anranruozhu.common.Result;
import com.anranruozhu.entity.User;
import com.anranruozhu.mapper.UserMapper;
import com.anranruozhu.utils.JwtUtil;
import com.anranruozhu.utils.MD5;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author anranruozhu
 * @ClassName LoginService
 * @Description 登陆业务类
 * @create 2024/4/7 10:28
 **/
public interface LoginService {
    /**
     * 发送短信验证码
     * @param phone 手机号码
     * @param password 用户密码
     * @return Result
     */
    Result login(String phone, String password) ;
    Result UpdatePassword(String phone, String password);
    boolean isIdOk(int id, String phone);
}
