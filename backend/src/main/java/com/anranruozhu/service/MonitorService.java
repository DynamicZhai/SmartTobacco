package com.anranruozhu.service;

import com.anranruozhu.common.Result;

public interface MonitorService {
    /**
     * 获取最新的土壤湿度数据
     */
    Result getNewHumidity();
    /**
     * 获取最新的温度数据
     */
    Result getNewTemperature();
    /**
     * 获取最新的光照数据
     */
    Result getNewLightIntensity();
    /**
     * 获取已记录的所有温度信息
     */
    Result getAllTemperature();
    /**
     * 获取已记录的所有光照信息
     */
    Result getAllLightIntensity();
    /**
     * 获取已记录的所有土壤湿度信息
     */
    Result getAllHumidity();
    /**
     * 获取已记录温度信息的最大值，最小值，平均值
     */
    Result getMathTemperature();
    /**
     * 获取已记录土壤湿度信息的最大值，最小值，平均值
     */
    Result getMathHumidity();
    /**
     * 获取已记录光照信息的最大值，最小值，平均值
     */
    Result getMathLightIntensity();
    /**
     * 获取一周的光照
     */
    Result getWeekLightIntensity();
    /**
     * 获取一周的湿度
     */
    Result getWeekHumidity();
    /**
     * 获取一周的温度
     */
    Result getWeekTemperature();
    /**
     * 获取预警信息
     */
    Result getAlarm(int alertType);
    /**
     *获取实时的所有信息
     */
    Result getAllShow();
}
