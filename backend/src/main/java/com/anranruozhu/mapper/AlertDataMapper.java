package com.anranruozhu.mapper;

import com.anranruozhu.entity.AlertData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlertDataMapper {
    void AlertNew(int alertType,
                  String alertMessage,float alertData);
    AlertData getAlertData(int alertType);
    List<AlertData> getAlertAll();
}
