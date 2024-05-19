package com.anranruozhu.service;

import com.anranruozhu.common.Result;

/**
 * @author anranruozhu
 * @ClassName RegisterService
 * @Description 注册服务类
 * @create 2024/4/7 10:29
 **/

public interface RegisterService {
    Result register(String phoneNumber, String password);
}
