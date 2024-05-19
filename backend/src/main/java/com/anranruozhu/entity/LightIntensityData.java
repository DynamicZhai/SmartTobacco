package com.anranruozhu.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author anranruozhu
 * @ClassName SersorData
 * @Description 光照温度数据类
 * @create 2024/4/9 上午11:22
 **/
@Data
public class LightIntensityData {
    private float lightIntensity;
    private Timestamp readingTime;
}
