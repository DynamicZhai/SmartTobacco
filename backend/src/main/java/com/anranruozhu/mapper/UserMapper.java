package com.anranruozhu.mapper;

import com.anranruozhu.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int UpdatePassword(String phoneNumber,String password);
    String findByPhone(String phoneNumber);
    User login(String phoneNumber, String password);
    void register(String phoneNumber,String password);
}
