package com.anranruozhu.utils;

import com.anranruozhu.service.mqtt.receiveclient.MqttAcceptClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author anranruozhu
 * @ClassName MqttConfig
 * @Description 启动时自动加载的配置类。
 * @create 2024/3/28 11:16
 **/
@Configuration
public class MqttConfig {
    @Autowired
    private MqttAcceptClient mqttAcceptClient;

    @Conditional(MqttCondition.class)
    @Bean
    public MqttAcceptClient getMqttSendClient(){
        mqttAcceptClient.connect();
        return mqttAcceptClient;
    }

}
