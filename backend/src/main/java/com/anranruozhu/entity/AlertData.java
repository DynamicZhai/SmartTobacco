package com.anranruozhu.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author anranruozhu
 * @ClassName AlertData
 * @Description 预警信息类
 * @create 2024/4/11 上午10:57
 **/
@Data
public class AlertData {
        // 预警类型 1:光照，2：土壤湿度，3：温度
    private int id;
    private int alertType;
    private float alertData;
    private String alertMessage;
    private Timestamp alertTime;
}
