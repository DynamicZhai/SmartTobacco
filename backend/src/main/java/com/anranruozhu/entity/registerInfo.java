package com.anranruozhu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author anranruozhu
 * @ClassName registerInfo
 * @Description 注册信息类
 * @create 2024/4/8 下午4:30
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class registerInfo {
    @ApiModelProperty(value = "用户手机号")
    String phoneNumber;
    @ApiModelProperty(value = "用户密码")
    String password;
    @ApiModelProperty(value = "验证码")
    String code;
}
