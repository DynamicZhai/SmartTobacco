package com.anranruozhu.service;

import com.anranruozhu.common.Result;
import com.anranruozhu.entity.DeviceState;
import com.anranruozhu.entity.LightInstrustions;

public interface DataAccess {

    void SaveSersor(String message,int soilAuto,int temperAuto);
    void SaveLight(String message,int lightAuto);
    void SaveDeviceState(int pumpCtrlState,
                         int pumpPowerState,
                         int fanMode,
                         int fanLevel);
    void SaveInstructions(int lightMode, int lightLevel);
    DeviceState getDeviceState();
    LightInstrustions getLightState();
    Result getDeviceAndLightState();
    Result getLightInstrustions();
    Result getPumpStatus();
    Result getFanStatus();
}
