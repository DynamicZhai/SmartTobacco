package com.anranruozhu.service.mqtt.sendclient;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
@Slf4j
@Component
public class MqttSentCallback implements MqttCallbackExtended {

    /**
     * 链接EMQ服务器后触发
     * @param reconnect
     * @param serverURI
     */
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("————————————————-ClientID:{"+serverURI+"}——————————————"+"链接成功");
    }

    /**
     * 客户端连接断开后触发
     * 这里可以做重新链接操作
     */
    @Override
    public void connectionLost(Throwable cause) {
        log.error("【MQTT-发送端】链接断开！");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("【MQTT-发送端】接收消息主题 : " + topic);
        log.info("【MQTT-发送端】接收消息Qos : " + message.getQos());
        String s=new String(message.getPayload());
        log.info("【MQTT-发送端】接收消息内容 : " + new String(message.getPayload()));
    }

    /**
     * 发送消息回调
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

        String[] topics = token.getTopics();

        if (topics!=null && topics.length>0){
            for (String topic : topics) {
                log.info("【MQTT-发送端】向主题：" + topic + "发送消息成功！");
            }
        }
        try {
            MqttMessage message = token.getMessage();
            byte[] payload = message.getPayload();
            String s = new String(payload, "UTF-8");
            log.info("【MQTT-发送端】消息的内容是：" + s);
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
    }
}

