package com.anranruozhu.service.mqtt.receiveclient;

import cn.hutool.core.date.DateUtil;
import com.anranruozhu.utils.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class  MqttAcceptClient {


    @Autowired
    private MqttAcceptCallback mqttAcceptCallback;

    @Autowired
    private MqttProperties mqttProperties;


    private static MqttClient mqttClient;

    public static MqttClient getMqttClient() {
        return mqttClient;
    }

    public static void setMqttClient(MqttClient mqttClient) {
        MqttAcceptClient.mqttClient = mqttClient;
    }


    /**
     * 客户端连接
     */
    public void connect() {
        MqttClient client;
        try {
            client = new MqttClient(mqttProperties.getHostUrl(), mqttProperties.getClientId(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(mqttProperties.getUsername());
            options.setPassword(mqttProperties.getPassword().toCharArray());
            options.setConnectionTimeout(mqttProperties.getTimeout());
            options.setKeepAliveInterval(mqttProperties.getKeepAlive());
            options.setAutomaticReconnect(mqttProperties.getReconnect());
            options.setCleanSession(mqttProperties.getCleanSession());
            MqttAcceptClient.setMqttClient(client);
            try {
                // 设置回调
                client.setCallback(mqttAcceptCallback);
                client.connect(options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新连接
     */
    public void reconnection() {
        try {
            mqttClient.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅某个主题
     *
     * @param topic 主题
     * @param qos   连接方式
     */
    public void subscribe(String topic, int qos) {

        log.info("==============开始订阅主题==============" + topic);
        try {
            mqttClient.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消订阅某个主题
     *
     * @param topic
     */
    public void unsubscribe(String topic) {
        log.info("==============开始取消订阅主题==============" + topic);
        try {
            mqttClient.unsubscribe(topic);
        } catch (MqttException e) {


            e.printStackTrace();
        }
    }
}

