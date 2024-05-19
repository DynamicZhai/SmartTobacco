package com.anranruozhu.service;

import com.anranruozhu.common.Result;
import com.anranruozhu.entity.UserInfo;

public interface UserInfoService {
    Result UpdateUserInfo(UserInfo userInfo);

    Result getUserInfo(int id);
}
