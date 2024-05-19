package com.anranruozhu.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author anranruozhu
 * @ClassName LightInstrustions
 * @Description 光照控制指令
 * @create 2024/4/10 下午3:01
 **/
@Data
public class LightInstrustions {
    private int id;
    private int lightMode;
    private int lightLevel ;
    private Timestamp createdAt ;
}
