package com.anranruozhu.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author anranruozhu
 * @ClassName soilData
 * @Description 土壤数据
 * @create 2024/4/9 上午11:26
 **/
@Data
public class soilData {
    private float soilHumidity;
    private Timestamp readingTime;
}
