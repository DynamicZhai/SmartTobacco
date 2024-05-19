package com.anranruozhu.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.anranruozhu.common.Result;
import com.anranruozhu.entity.AutoStatus;
import com.anranruozhu.entity.DeviceState;
import com.anranruozhu.entity.LightInstrustions;
import com.anranruozhu.mapper.*;
import com.anranruozhu.service.DataAccess;
import com.anranruozhu.service.mqtt.sendclient.MqttSendClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author anranruozhu
 * @ClassName DataAccessImpl
 * @Description 对订阅数据的存储保存
 * @create 2024/4/9 上午11:42
 **/
@Component
@Slf4j
public class DataAccessImpl implements DataAccess {
    @Autowired
    private DataAccess dataAccess;
    @Autowired
    private MqttSendClient client1;
    @Autowired
    private AlertDataMapper alertDataMapper;
    @Autowired
    private SoilDataMapper soilDataMapper;
    @Autowired
    private TemperstureDataMapper temperstureDataMapper;
    @Autowired
    private LightDataMapper lightDataMapper;

    @Autowired
    private DeviceStateMapper deviceStateMapper;
    @Autowired
    private LightInstrustionsMapper lightInstrustionsMapper;
    @Autowired
    private AutoStatusMapper autoStatusMapper;
    @Override
    public void SaveSersor(String message,int soilAuto,int temperAotu) {
        //数据
        Random random = new Random();
        JSONObject jsonObject = JSONUtil.parseObj(message);
        float soilHumidity = jsonObject.getFloat("soil_humidity")*((float) 20 /6)+55 + random.nextInt((3 + 3) + 1) - 3;
        float airTemperature = jsonObject.getFloat("air_temperature");
        try {
            soilDataMapper.addData(soilHumidity);
            temperstureDataMapper.addData(airTemperature);
            TemperatureIsNormal(airTemperature,temperAotu);
            soilHumidityIsNormal(soilHumidity,soilAuto);
        }catch (Exception e){
            log.error("error: " + e.getMessage());
            throw new RuntimeException("湿度气温保存失败");
        }
        log.info("土壤湿度为：{}", soilHumidity);
        log.info("气温为：{}", airTemperature);
    }

    @Override
    public void SaveLight(String message,int lightAuto) {
        JSONObject jsonObject = JSONUtil.parseObj(message);
        float lightIntensity = jsonObject.getFloat("light_intensity");
        try {
            lightDataMapper.addData(lightIntensity);
            lightIntensityIsNormal(lightIntensity,lightAuto);
        }catch (Exception e){
            log.error("error: " + e.getMessage());
            throw new RuntimeException("光照强度保存失败");
        }
        log.info("光照强度为：{}", lightIntensity);
    }

    @Override
    public void SaveDeviceState(int pumpCtrlState, int pumpPowerState, int fanMode, int fanLevel) {
        try {
            deviceStateMapper.addData(pumpCtrlState, pumpPowerState,fanMode,fanLevel);
        }catch (Exception e){
            log.error("error: " + e.getMessage());
            throw new RuntimeException("devicedata 保存失败");
        }
        log.info("devicedata 保存成功");
    }

    @Override
    public void SaveInstructions(int lightMode, int lightLevel) {
        try {
            lightInstrustionsMapper.addData(lightMode,lightLevel);
        } catch (Exception e) {
            log.error("error: " + e.getMessage());
            throw new RuntimeException("lightInstruction 保存失败");
        }
        log.info("lightInstruction 保存成功");
    }

    @Override
    public DeviceState getDeviceState() {
        DeviceState res = new DeviceState();
        try {
            res = deviceStateMapper.ShowLast();
        }catch (Exception e){
            log.error("error: " + e.getMessage());
            throw new RuntimeException("获取设备状态失败");
        }
        return res;
    }

    @Override
    public LightInstrustions getLightState() {
        LightInstrustions res = new LightInstrustions();
        try {
            res = lightInstrustionsMapper.ShowLast();
        } catch (Exception e){
            log.error("error: " + e.getMessage());
            throw new RuntimeException("获取灯光状态失败");
        }
        return res;
    }

    public Result getDeviceAndLightState() {
        Result res = new Result();
        DeviceState device = getDeviceState();
        LightInstrustions light = getLightState();
        AutoStatus autoStatus = autoStatusMapper.getStatus();
        res.setData(new JSONObject().set("pumpCtrlState", device.getPumpCtrlState())
                .set("pumpPowerState", device.getPumpPowerState())
                .set("fanMode", device.getFanMode())
                .set("fanLevel", device.getFanLevel())
                .set("lightMode", light.getLightMode())
                .set("light_level", light.getLightLevel())
                .set("LightStatus",autoStatus.getLightStatus())
                .set("FenStatus",autoStatus.getFenStatus())
                .set("PumpStatus",autoStatus.getPumpStatus())
        );
        res.setCode(200);
        res.setMsg("获取成功");
        return res;
    }

    @Override
    public Result getLightInstrustions() {
        Result rs=new Result();
        try{
            LightInstrustions res= lightInstrustionsMapper.ShowLast();
            AutoStatus autoStatus = autoStatusMapper.getStatus();
            log.info(res.toString());
            rs.setCode(200);
            rs.setData(new JSONObject()
                    .set("lightMode",res.getLightMode())
                    .set("light_level",res.getLightLevel())
                    .set("autoStatus",autoStatus.getLightStatus()));
            rs.setMsg("查询成功");
            return rs;
        }catch (Exception e){
            rs.setCode(500);
            rs.setMsg("查询失败");
            log.error("error: " + e.getMessage());
        }
        return rs;
    }

    @Override
    public Result getPumpStatus() {
        Result rs=new Result();

        try{
            DeviceState ds=deviceStateMapper.ShowLast();
            AutoStatus autoStatus = autoStatusMapper.getStatus();
            rs.setCode(200);
            rs.setData(new JSONObject()
                    .set("pumpCtrlState",ds.getPumpCtrlState())
                    .set("pumpPowerState",ds.getPumpPowerState())
                    .set("autoStatus",autoStatus.getPumpStatus()));
            rs.setMsg("查询成功");
            return rs;
        }catch (Exception e){
            rs.setCode(500);
            rs.setMsg("查询失败");
            log.error("error: " + e.getMessage());
        }
        return rs;
    }

    @Override
    public Result getFanStatus() {
        Result rs=new Result();

        try{
            DeviceState ds=deviceStateMapper.ShowLast();
            AutoStatus autoStatus = autoStatusMapper.getStatus();
            rs.setCode(200);
            rs.setData(new JSONObject()
                    .set("fanMode",ds.getFanMode())
                    .set("fanLevel",ds.getFanLevel())
                    .set("autoStatus",autoStatus.getFenStatus()));
            rs.setMsg("查询成功");
            return rs;
        }catch (Exception e){
            rs.setCode(500);
            rs.setMsg("查询失败");
            log.error("error: " + e.getMessage());
        }
        return rs;
    }
    public void TemperatureIsNormal(float temperature,int temperAotu){
        if(temperature>30){
            temperatureAuto(temperature,temperAotu);
            alertDataMapper.AlertNew(3,"当前温度过高",temperature);
        }else if(temperature<10){
            temperatureAuto(temperature,temperAotu);
            alertDataMapper.AlertNew(3,"当前温度过低",temperature);
        }
    }
    public void soilHumidityIsNormal(float soilHumidity,int soilAuto){
        if(soilHumidity<20||soilHumidity>80) {
            soilHumidityAuto(soilHumidity,soilAuto);
            alertDataMapper.AlertNew(2, "当前土壤湿度异常", soilHumidity);
        }
    }
    public void lightIntensityIsNormal(float lightIntensity,int lightAuto){
        if(lightIntensity>400){
            lightIntensityAuto(lightIntensity,lightAuto);
            alertDataMapper.AlertNew(1,"当前光照强度过低",lightIntensity);
        }else if(lightIntensity<100){
            lightIntensityAuto(lightIntensity,lightAuto);
            alertDataMapper.AlertNew(1,"当前光照强度过高",lightIntensity);
        }
    }

    public void soilHumidityAuto(float soilHumidity,int soilAuto){
        if(soilAuto==1){
            client1.connect();
            String topic = "ctl-a-1";
            JSONObject data=new JSONObject();
            if(soilHumidity<26){
                data.set("pump_ctrl_state",1)
                        .set("pump_power_state",1)
                        .set("fan_mode",0)
                        .set("fan_level",0);
                client1.publish(topic, String.valueOf(data));
                dataAccess.SaveDeviceState(1, 1, 0, 0);
            }else if(soilHumidity>80){
                data.set("pump_ctrl_state",0)
                        .set("pump_power_state",0)
                        .set("fan_mode",0)
                        .set("fan_level",0);
                client1.publish(topic, String.valueOf(data));
                dataAccess.SaveDeviceState(0, 0, 0, 0);
            }
            client1.disconnect();
            client1.close();
        }else{
            //不开起自动化
            log.info("自动控制已关闭");
        }
    }
    public void temperatureAuto(float temperature,int temperAotu){
        if(temperAotu==1){
            client1.connect();
            String topic = "ctl-a-1";
            JSONObject data=new JSONObject();
            if(temperature>30){
                data.set("pump_ctrl_state",0)
                        .set("pump_power_state",0)
                        .set("fan_mode",1)
                        .set("fan_level",50);
                client1.publish(topic, String.valueOf(data));
                dataAccess.SaveDeviceState(0, 0, 1, 50);
            }else if(temperature<20){
                data.set("pump_ctrl_state",0)
                        .set("pump_power_state",0)
                        .set("fan_mode",0)
                        .set("fan_level",0);
                client1.publish(topic, String.valueOf(data));
                dataAccess.SaveDeviceState(0, 0, 0, 0);
            }
            client1.disconnect();
            client1.close();
        }else{
            //不开起自动化
            log.info("温度自动控制已关闭");
        }
    }

    public void lightIntensityAuto(float lightIntensity,int lightAuto){
        if(lightAuto==1){
            client1.connect();
            String topic = "ctl-b-1";
            JSONObject data=new JSONObject();
            if(lightIntensity>400){
                data.set("light_mode", 1)
                        .set("light_level", lightIntensity/17);
                client1.publish(topic, String.valueOf(data));
                dataAccess.SaveInstructions(1, (int) (lightIntensity/17));
            }else if(lightIntensity<200){
                data.set("light_mode", 0)
                        .set("light_level", 0);
                client1.publish(topic, String.valueOf(data));
                dataAccess.SaveInstructions(0, 0);
            }
            client1.disconnect();
            client1.close();
        }else{
            //不开起自动化
            log.info("光照自动控制已关闭");
        }
    }
}
