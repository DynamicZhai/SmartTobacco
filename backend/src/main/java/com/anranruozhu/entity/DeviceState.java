package com.anranruozhu.entity;

import lombok.Data;

import java.sql.Timestamp;
/**
 * @author anranruozhu
 * @ClassName DeviceState
 * @Description 设备控制指令存储类
 * @create 2024/4/10 下午3:03
 **/
@Data
public class DeviceState {
    private int id;
    private int pumpCtrlState;
    private int pumpPowerState;
    private int fanMode;
    private int fanLevel;
    private Timestamp createdAt;
}
