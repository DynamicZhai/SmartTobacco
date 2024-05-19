package com.anranruozhu.api;

import com.anranruozhu.common.Result;
import com.anranruozhu.entity.User;
import com.anranruozhu.entity.registerInfo;
import com.anranruozhu.mapper.AlertDataMapper;
import com.anranruozhu.service.LoginService;
import com.anranruozhu.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author anranruozhu
 * @ClassName AuthController
 * @Description 身份验证接口
 * @create 2024/4/7 11:12
 **/

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;    //注入redis
    // 登录接口
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return loginService.login(user.getPhoneNumber(), user.getPassword());
    }
    // 注册接口
    @PostMapping("/register")
    public Result register(@RequestBody registerInfo ri) {
        // 处理注册逻辑
        // 返回注册结果
        //1、从redis中获取验证码，如果获取到就直接返回
        log.info("登陆信息"+ ri);
        String code = redisTemplate.opsForValue().get(ri.getPhoneNumber());
        log.info(code);
        Result rs=new Result();
        rs.setCode(500);
        rs.setMsg("验证码错误");
        if(ri.getCode().equals(code)){
            //验证码验证注册后删除对应值
            redisTemplate.delete(ri.getPhoneNumber());

            return registerService.register(ri.getPhoneNumber(),ri.getPassword());
        }else{
            return rs;
        }
    }
}
