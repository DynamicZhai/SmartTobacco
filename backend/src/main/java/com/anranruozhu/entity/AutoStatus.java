package com.anranruozhu.entity;

import lombok.Data;

/**
 * @author anranruozhu
 * @ClassName Auto_status
 * @Description 自动化状态存储表
 * @create 2024/4/15 下午2:37
 **/
@Data
public class AutoStatus {
    private Integer lightStatus;
    private Integer pumpStatus;
    private Integer fenStatus;
}
