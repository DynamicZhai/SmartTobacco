package com.anranruozhu.mapper;

import com.anranruozhu.entity.TemperatureData;
import com.anranruozhu.entity.soilData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TemperstureDataMapper {
    // 添加数据
    void addData(Float tempersture);
    //获取最新得到数据
    Float ShowLast();
    //获取历史数据
    List<TemperatureData> ShowAll();
    List<TemperatureData> ShowWeekData();
}
