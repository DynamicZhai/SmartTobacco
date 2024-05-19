package com.anranruozhu.mapper;

import com.anranruozhu.entity.DeviceState;
import com.anranruozhu.entity.LightInstrustions;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LightInstrustionsMapper {
    // 添加设备状态数据
    void addData(int lightMode, int lightLevel);
    //获取最新得到数据
    LightInstrustions ShowLast();

}
