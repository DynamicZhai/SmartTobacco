package com.anranruozhu.service;

import com.anranruozhu.entity.Weather;

import java.io.IOException;
import java.util.List;;
public interface WeatherService {
     List<Weather> getWeather(String cityCode)throws IOException;
}
