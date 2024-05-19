package com.anranruozhu.service.impl;

import cn.hutool.json.JSONObject;
import com.anranruozhu.common.Result;
import com.anranruozhu.entity.AlertData;
import com.anranruozhu.entity.LightIntensityData;
import com.anranruozhu.entity.TemperatureData;
import com.anranruozhu.entity.soilData;
import com.anranruozhu.mapper.AlertDataMapper;
import com.anranruozhu.mapper.LightDataMapper;
import com.anranruozhu.mapper.SoilDataMapper;
import com.anranruozhu.mapper.TemperstureDataMapper;
import com.anranruozhu.service.MonitorService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anranruozhu
 * @ClassName MonitorServiceImpl
 * @Description 数据监控服务的实现类
 * @create 2024/4/9 下午8:35
 **/
@Slf4j
@Component
public class MonitorServiceImpl implements MonitorService {
    @Autowired
    private LightDataMapper lightDataMapper;
    @Autowired
    private TemperstureDataMapper temperstureDataMapper;
    @Autowired
    private SoilDataMapper soilDataMapper;
    @Autowired
    private AlertDataMapper alertDataMapper;
    @Override
    public Result getNewHumidity() {
        Result rs=new Result();
        try {
           float Humidity = soilDataMapper.ShowLast();
           rs.setCode(200);
            JSONObject data=new JSONObject().set("humidity",Humidity);
           rs.setData(data);
           rs.setMsg("获取成功");
        }catch (Exception e){
            log.error(e.getMessage());
            rs.setCode(500);
            rs.setMsg("获取失败");
        }
        return rs;
    }

    @Override
    public Result getNewTemperature() {
        Result rs=new Result();
        try {
            float Temperature = temperstureDataMapper.ShowLast();
            rs.setCode(200);
            JSONObject data=new JSONObject().set("temperature",Temperature);
            rs.setData(data);
            rs.setMsg("获取成功");
        }catch (Exception e){
            log.error(e.getMessage());
            rs.setCode(500);
            rs.setMsg("获取失败");
        }
        return rs;
    }
    @Override
    public Result getNewLightIntensity() {
        Result rs=new Result();
        try {
            float LightIntensity = lightDataMapper.ShowLast();
            rs.setCode(200);
            JSONObject data=new JSONObject().set("lightIntensity",LightIntensity);
            rs.setData(data);
            rs.setMsg("获取成功");
        }catch (Exception e){
            log.error(e.getMessage());
            rs.setCode(500);
            rs.setMsg("获取失败");
        }
        return rs;
    }
    @Override
    public Result getAllTemperature() {
        Result rs=new Result();
        try {
            List<TemperatureData> data=temperstureDataMapper.ShowAll();
            rs.setCode(200);
            rs.setMsg("获取成功");
            rs.setData(data);
        }catch (Exception e){
            log.error("{}查询失败", e.getMessage());
            rs.setCode(500);
            rs.setMsg("查询失败");
            return rs;
        }
        rs.setCode(200);
        return rs;
    }

    @Override
    public Result getAllLightIntensity() {
        Result rs=new Result();
        try {
            List<LightIntensityData> data=lightDataMapper.ShowAll();
            rs.setCode(200);
            rs.setMsg("获取成功");
            rs.setData(data);
        }catch (Exception e){
            log.error("{}查询失败", e.getMessage());
            rs.setCode(500);
            rs.setMsg("查询失败");
            return rs;
        }
        rs.setCode(200);
        return rs;
    }

    @Override
    public Result getAllHumidity() {
        Result rs = new Result();
        try {
            List<soilData> data = soilDataMapper.ShowAll();
            rs.setCode(200);
            rs.setMsg("获取成功");
            rs.setData(data);
        } catch (Exception e) {
            log.error("{}查询失败", e.getMessage());
            rs.setCode(500);
            rs.setMsg("查询失败");
            return rs;
        }
        rs.setCode(200);
        return rs;
    }

    @Override
    public Result getMathTemperature() {
        List<TemperatureData> datas=temperstureDataMapper.ShowAll();
        Result rs=new Result();
        int n=datas.size();
        float total=0;
        float max=0;
        float min=1000;
        for(TemperatureData data:datas){
            total+=data.getAirTemperature();
            if(data.getAirTemperature()>max){
                max=data.getAirTemperature();
            }
            if(data.getAirTemperature()<min){
                min=data.getAirTemperature();
            }
        }
        return getResult(rs, n, total, max, min);
    }


    @Override
    public Result getMathHumidity() {
        List<soilData> datas=soilDataMapper.ShowAll();
        Result rs=new Result();
        int n=datas.size();
        float total=0;
        float max=0;
        float min=1000;
        for(soilData data:datas){
            total+=data.getSoilHumidity();
            if(data.getSoilHumidity()>max){
                max=data.getSoilHumidity();
            }
            if(data.getSoilHumidity()<min){
                min=data.getSoilHumidity();
            }
        }
        return getResult(rs, n, total, max, min);
    }

    @Override
    public Result getMathLightIntensity() {
        List<LightIntensityData> datas=lightDataMapper.ShowAll();
        Result rs=new Result();
        int n=datas.size();
        float total=0;
        float max=0;
        float min=1000;
        for(LightIntensityData data:datas){
            total+=data.getLightIntensity();
            if(data.getLightIntensity()>max){
                max=data.getLightIntensity();
            }
            if(data.getLightIntensity()<min){
                min=data.getLightIntensity();
            }
        }
        return getResult(rs, n, total, max, min);
    }

    @Override
    public Result getWeekLightIntensity() {
        Result rs=new Result();
        List<LightIntensityData> data=new ArrayList<>();
        try{
            data=lightDataMapper.ShowWeekData();
            rs.setCode(200);
            rs.setMsg("获取成功");
            rs.setData(data);
        }catch (Exception e){
            rs.setCode(500);
            rs.setMsg("获取失败");
        }
        return rs;
    }

    @Override
    public Result getWeekHumidity() {
        Result rs=new Result();
        List<soilData> data=new ArrayList<>();
        try{
            data=soilDataMapper.ShowWeekData();
            rs.setCode(200);
            rs.setMsg("获取成功");
            rs.setData(data);
        }catch (Exception e){
            rs.setCode(500);
            rs.setMsg("获取失败");
        }
        return rs;
    }

    @Override
    public Result getWeekTemperature() {
        Result rs=new Result();
        List<TemperatureData> data;
        try{
            data=temperstureDataMapper.ShowWeekData();
            rs.setCode(200);
            rs.setMsg("获取成功");
            rs.setData(data);
        }catch (Exception e){
            rs.setCode(500);
            rs.setMsg("获取失败");
        }
        return rs;
    }

    @Override
    public Result getAlarm(int alertType) {
        Result  result=new Result();
        try {
            AlertData data=alertDataMapper.getAlertData(alertType);
        result.setCode(200);
        result.setData(data);
            result.setMsg("获取成功");
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("获取失败");
        }
        return result;
    }

    @Override
    public Result getAllShow() {
        Result  result=new Result();
        try{
           Float soilData= soilDataMapper.ShowLast();
            Float temp=temperstureDataMapper.ShowLast();
            Float light=lightDataMapper.ShowLast();
            result.setCode(200);
            result.setData(new JSONObject()
                    .set("soilData",soilData)
                    .set("temp",temp)
                    .set("light",light));
            result.setMsg("获取成功");
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("获取成功");
        }
        return result;
    }

    @NotNull
    private Result getResult(Result rs, int n, float total, float max, float min) {
        float avg=total/n;
        JSONObject data=new JSONObject().set("max",max).set("min",min).set("avg",avg);
        rs.setCode(200);
        rs.setData(data);
        rs.setMsg("获取成功");
        return rs;
    }
}
