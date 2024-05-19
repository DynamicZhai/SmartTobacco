package com.anranruozhu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * @author anranruozhu
 * @ClassName User
 * @Description 用户
 * @create 2024/4/3 15:47
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
@Table(name = "users")
public class User {
    @ApiModelProperty(value = "用户id",example = "1")
    private int id;
    @ApiModelProperty(value = "用户密码")
    private String password;
    @ApiModelProperty(value = "用户手机号")
    private String phoneNumber;
    //getter/setter用@Data注解自动生成
}