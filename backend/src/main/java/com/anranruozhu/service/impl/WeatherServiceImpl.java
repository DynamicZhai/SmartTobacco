package com.anranruozhu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anranruozhu.entity.Weather;
import com.anranruozhu.service.WeatherService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anranruozhu
 * @ClassName WeatherServiceImpl
 * @Description 天气服务的实现类
 * @create 2024/4/10 上午11:06
 **/
@Component
public class WeatherServiceImpl implements WeatherService {
    private final String apiUrl = "http://t.weather.itboy.net/api/weather/city/";

    public List<Weather> getWeather(String cityCode) throws IOException {
        //HTTP请求 天气接口
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl + cityCode)
                .build();
        Response response = client.newCall(request).execute();
        //返回
        String strJson = response.body().string();
        //json字符串解析
        JSONObject data = JSON.parseObject(strJson).getJSONObject("data");
        JSONObject cityInfo = JSON.parseObject(strJson).getJSONObject("cityInfo");

        JSONArray forecast = (JSONArray) data.get("forecast");
        //默认该集合中当天数据为集合第0条，所以我只需要将它取出即可。
        String forecast_0 = forecast.get(0).toString();
        //转Weather对象
        Weather weather = JSON.parseObject(forecast_0, Weather.class);
        weather.setCity(cityInfo.getString("city"));
        weather.setTime(JSON.parseObject(strJson).getString("time"));
        weather.setShidu(data.getString("shidu"));
        weather.setQuality(data.getString("quality"));
        weather.setWendu(data.getString("wendu"));

        List<Weather> res = new ArrayList<>();
        res.add(weather);
        return res;
    }
}
