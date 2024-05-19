package com.anranruozhu.service.mqtt.sendclient;

import cn.hutool.json.JSONObject;
import com.anranruozhu.utils.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class MqttSendClient {

    @Autowired
    private MqttSentCallback mqttSendCallBack;

    @Autowired
    private MqttProperties mqttProperties;

    private static MqttClient mqttClient;

    private static MqttClient getClient() {
        return mqttClient;
    }

    private static void setClient(MqttClient client) {

        MqttSendClient.mqttClient = client;
    }
    /**
     * 客户端连接
     * @return
     */
    public void connect(){
        MqttClient client = null;
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-",""); //设置每一个客户端的id
            client = new MqttClient(mqttProperties.getHostUrl(),uuid , new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(mqttProperties.getUsername());
            options.setPassword(mqttProperties.getPassword().toCharArray());
            options.setConnectionTimeout(mqttProperties.getTimeout());
            options.setKeepAliveInterval(mqttProperties.getKeepAlive());
            options.setCleanSession(true);
            options.setAutomaticReconnect(false);

            MqttSendClient.setClient(client);
            try {

                // 设置回调
                client.setCallback(mqttSendCallBack);
                client.connect(options);
            } catch (Exception e) {

                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发布，默认qos为0，非持久化
     *
     * @param topic 主题名
     * @param pushMessage 消息
     */
    public void publish(String topic, String pushMessage) {
        publish(0, false, topic, pushMessage);
    }
    /**
     * 发布
     *
     * @param qos
     * @param retained
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());
        MqttTopic mTopic = MqttSendClient.getClient().getTopic(topic);
        if (null == mTopic) {
            log.error("主题不存在:{}",mTopic);
        }
        try {
            mTopic.publish(message);
            log.info("消息发送成功");
        } catch (Exception e) {
            log.error("mqtt发送消息异常:",e);
        }
    }
    /**
     * 关闭连接
     *
     * @param mqttClient
     */
    public  void disconnect() {
        try {
            if (mqttClient != null) mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     *
     * @param mqttClient
     */
    public  void close() {
        try {
            if (mqttClient != null) mqttClient.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

