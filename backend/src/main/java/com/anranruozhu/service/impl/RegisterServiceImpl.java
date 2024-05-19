package com.anranruozhu.service.impl;

import com.anranruozhu.common.Result;
import com.anranruozhu.mapper.UserInfoMapper;
import com.anranruozhu.mapper.UserMapper;
import com.anranruozhu.service.RegisterService;
import com.anranruozhu.utils.MD5;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author anranruozhu
 * @ClassName RegisterServiceImpl
 * @Description 注册服务的实现
 * @create 2024/4/9 上午9:33
 **/
@Data
@Slf4j
@Component
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Result register(String phoneNumber, String password) {
        Result result=new Result();
        String pwd = MD5.md5(password, phoneNumber);
        try {
            userMapper.register(phoneNumber,pwd);
        }catch(Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("用户注册失败");
        }
        try {
            int id= Integer.parseInt(userMapper.findByPhone(phoneNumber));
            userInfoMapper.InsertID(id);
        }catch(Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("用户注册失败");
        }
        result.setCode(200);
        result.setData("注册成功");
        result.setMsg("ok");
        return result;
    }
}
