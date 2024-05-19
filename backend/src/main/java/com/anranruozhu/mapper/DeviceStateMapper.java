package com.anranruozhu.mapper;

import com.anranruozhu.entity.DeviceState;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceStateMapper {
    // 添加设备状态数据
    void addData(int pumpCtrlState,
                 int pumpPowerState,
                 int fanMode,
                 int fanLevel);
    //获取最新得到数据
    DeviceState ShowLast();
}
