package com.anranruozhu.api;

import com.anranruozhu.common.Result;
import com.anranruozhu.service.MonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author anranruozhu
 * @ClassName MonitorApi
 * @Description 监控业务的接口
 * @create 2024/4/7 11:28
 **/
@RestController
@RequestMapping("/secure")
@Slf4j
@CrossOrigin(origins = "*")
public class MonitorApi {
    @Autowired
    private MonitorService monitorService;

    //获取实时的温度
    @GetMapping("/temperature")
    public Result getTemperature(){
        return monitorService.getNewTemperature();
    }
    //获取实时的湿度
    @GetMapping("/humidity")
    public Result getHumidity(){
        return monitorService.getNewHumidity();
    }
    //获取实时的光照强度
    @GetMapping("/light-intensity")
    public Result getLightIntensity() {
        return monitorService.getNewLightIntensity();
    }
    //获取全部的温度信息
    @GetMapping("/temperature-all")
    public Result getAllTemperature(){
        return monitorService.getAllTemperature();
    }
    //获取所有的光照信息
    @GetMapping("/light-intensity-all")
    public Result getAllLightIntensity(){
        return monitorService.getAllLightIntensity();
    }
    //获取所有的湿度信息
    @GetMapping("/humidity-all")
    public Result getAllHumidity(){
        return monitorService.getAllHumidity();
    }
    //获取最近200条数据的计数结果
    @GetMapping("/temperature-math")
    public Result getTemperatureMath(){
        return monitorService.getMathTemperature();
    }
    @GetMapping("/light-intensity-math")
    public Result getLightIntensityMath(){
        return monitorService.getMathLightIntensity();
    }
    @GetMapping("/humidity-math")
    public Result getHumidityMath(){
        return monitorService.getMathHumidity();
    }
    @GetMapping("/temperature-week")
    public Result getTemperatureWeek(){
        return monitorService.getWeekTemperature();
    }
    @GetMapping("/light-intensity-week")
    public Result getLightIntensityWeek(){
        return monitorService.getWeekLightIntensity();
    }
    @GetMapping("/humidity-week")
    public Result getHumidityWeek(){
        return monitorService.getWeekHumidity();
    }
    @GetMapping("alert-info")
    public Result getAlertInfo(@RequestParam int alertType){
        return monitorService.getAlarm(alertType);
    }
}
