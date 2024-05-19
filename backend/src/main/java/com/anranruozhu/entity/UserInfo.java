package com.anranruozhu.entity;

import lombok.Data;

/**
 * @author anranruozhu
 * @ClassName UserINfo
 * @Description 用户的个人信息
 * @create 2024/4/7 15:37
 **/
@Data
public class UserInfo {
    private  int id;
    private String nickname;
    private String email;
    private String address;
}
