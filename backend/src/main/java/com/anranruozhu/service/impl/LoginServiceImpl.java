package com.anranruozhu.service.impl;

import cn.hutool.json.JSONObject;
import com.anranruozhu.common.Result;
import com.anranruozhu.entity.User;
import com.anranruozhu.mapper.UserMapper;
import com.anranruozhu.service.LoginService;
import com.anranruozhu.utils.JwtUtil;
import com.anranruozhu.utils.MD5;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author anranruozhu
 * @ClassName LoginServiceImpl
 * @Description 登陆服务的实现类
 * @create 2024/4/9 上午9:30
 **/
@Data
@Component
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    Result result=new Result();
    @Override
    public Result login(String phone, String password) {
        String encodedPwd = MD5.md5(password, phone);
        User user= userMapper.login(phone, encodedPwd);
        JSONObject json = new JSONObject();
        Result result=new Result();
        if (user!=null) {
            log.info("登录成功！生成token！");
            String token = JwtUtil.createToken(user);
            result.setCode(200);
            result.setMsg("登录成功！");
            json =  new JSONObject()
                    .set("token", token)
                    .set("user", new JSONObject()
                            .set("id", user.getId())
                            .set("phoneNumber",user.getPhoneNumber()
                            ));
            result.setData(json);
            return result;
        }else{
            log.info("登录失败！");
            result.setCode(400);
            if (phone == null || phone.length() == 0) {
                result.setMsg("电话号码不能为空");
            }else{
                result.setMsg("账号或密码错误！");
            }
            return result;
        }
    }

    @Override
    public Result UpdatePassword(String phone, String password) {
        Result res = new Result();
        String encodedPwd = MD5.md5(password,phone);
        try {
            userMapper.UpdatePassword(phone,encodedPwd);
        }catch (Exception e){
            log.error(e.getMessage());
            res.setCode(500);
            res.setMsg("密码更新失败");
            res.setData(phone);
            return res;
        }
        res.setCode(200);
        res.setMsg("密码更新成功");
        res.setData(phone);
        return res;
    }

    @Override
    public boolean isIdOk(int id, String phone) {
        boolean res;
        try {
           res =  id == Integer.parseInt(userMapper.findByPhone(phone));
        }catch (Exception e){
            log.error(e.getMessage());
            res = false;
        }
        return res;
    }
}
