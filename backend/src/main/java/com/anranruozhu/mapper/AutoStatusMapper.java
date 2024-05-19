package com.anranruozhu.mapper;

import com.anranruozhu.entity.AutoStatus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AutoStatusMapper {
    void addStatus(int lightStatus, int pumpStatus, int fenStatus);
    AutoStatus getStatus();

}
