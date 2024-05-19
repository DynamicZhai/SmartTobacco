package com.anranruozhu.service.impl;

import com.anranruozhu.common.Result;
import com.anranruozhu.entity.UserInfo;
import com.anranruozhu.mapper.UserInfoMapper;
import com.anranruozhu.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author anranruozhu
 * @ClassName UserInfoServiceImpl
 * @Description 对用户信息的服务管理的实现类
 * @create 2024/4/9 上午10:19
 **/
@Component
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public Result UpdateUserInfo(UserInfo userInfo) {
        Result res = new Result();
        if (userInfoMapper.FindByID(userInfo.getId()) == null){
            log.info("保存用户 id 不存在");
            res.setCode(400);
            res.setData(userInfo);
            res.setMsg("用户 id 不存在");
            return res;
        }
        try {
            userInfoMapper.UpdateUserInfo(userInfo);
        }catch(Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("用户个人信息保存失败");
        }
        res.setCode(200);
        res.setMsg("用户个人信息保存成功");
        res.setData(userInfo);
        return res;
    }
    @Override
    public Result getUserInfo(int id) {
        Result rs=new Result();
        try{
          UserInfo userInfo=userInfoMapper.FindByID(id);
            rs.setCode(200);
            rs.setMsg("查询成功");
            rs.setData(userInfo);
            return rs;
        }catch (Exception e){
            log.error(e.getMessage());
            rs.setCode(500);
            rs.setMsg("查询失败");
            return rs;
        }
    }

}
