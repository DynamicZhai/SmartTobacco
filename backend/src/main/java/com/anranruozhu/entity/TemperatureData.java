package com.anranruozhu.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author anranruozhu
 * @ClassName TemperatureData
 * @Description 温度的数据类
 * @create 2024/4/10 上午11:41
 **/
@Data
public class TemperatureData {
    private float airTemperature;
    private Timestamp readingTime;
}
