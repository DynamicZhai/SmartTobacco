#include <stdio.h>
#include <unistd.h>

#include "ohos_init.h"
#include "cmsis_os2.h"
#include <unistd.h>
#include "hi_wifi_api.h"
// #include "wifi_sta.h"
#include "lwip/ip_addr.h"
#include "lwip/netifapi.h"

#include "lwip/sockets.h"

#include "MQTTPacket.h"
#include "transport.h"
#include <stdlib.h>
#include "cJSON.h"
// #include "traffic_light.h"

#define HOST_ADDR "139.155.150.150"
typedef struct
{
    uint8_t Temp;                       //温度值
    uint8_t Humidity;                   //湿度值
    uint8_t TempThreshold;              //温度报警阈值
    uint8_t HumiThreshold;              //湿度报警阈值
}TS_SHT40_PARAM;
// 土壤湿度控制模组句柄
typedef struct
{
    uint8_t Humidity;                   //湿度值
    uint8_t HumiThreshold;              //水泵开启对应湿度阈值   
}TS_SOIL_PARAM;

extern TS_SOIL_PARAM s_Soil;
extern TS_SHT40_PARAM s_Sht40;
uint8_t pump_ctrl_state = 0;
uint8_t pump_power_state = 0;
int fan_level = 50;
int fan_mode = 0;
int mqtt_connect_conn2(void)
{
	printf("into mqtt_connect\n");
	MQTTPacket_connectData data = MQTTPacket_connectData_initializer;
	int mysock = 0;
	unsigned char buf[200];
	int buflen = sizeof(buf);
	int msgid = 1;
	MQTTString topicString = MQTTString_initializer;
	int req_qos = 0;
	char payload[200] = "baidu.com"; // 发送数据
	int len = 0;
	char *host = HOST_ADDR; // MQTT服务器的IP地址
	int port = 1883;

	// 打开一个接口，并且和服务器 建立连接，创建socket连接mqtt服务器函数
	mysock = transport_open(host, port);
	printf("mysock:%d\r\n", mysock);
	if (mysock < 0)
	{
		return mysock;
	}

	printf("Sending to hostname %s port %d\n", host, port);

	data.clientID.cstring = "mqtt_conn01B"; // 修改成自己名称，
	data.keepAliveInterval = 60;	// 心跳时间
	data.cleansession = 1;
	data.username.cstring = "tobacco_01a";
	data.password.cstring = "1024";
	// 数据封装成数据报文
	len = MQTTSerialize_connect(buf, buflen, &data);
	// 以TCP方式发送数据, <0发送数据失败
	transport_sendPacketBuffer(mysock, buf, len);
	/* wait for connack读取服务器返回的报文是否是connack */
	if (MQTTPacket_read(buf, buflen, transport_getdata) == CONNACK)
	{
		unsigned char sessionPresent, connack_rc;

		if (MQTTDeserialize_connack(&sessionPresent, &connack_rc, buf, buflen) != 1 || connack_rc != 0)
		{
			printf("Unable to connect, return code %d\n", connack_rc);
			goto exit;
		}
	}
	else
		goto exit;
	/* subscribe 订阅主题 */
	topicString.cstring = "ctl-b-1";
	len = MQTTSerialize_subscribe(buf, buflen, 0, msgid, 1, &topicString, &req_qos); // 打包橙订阅主题包保存在buf中
	transport_sendPacketBuffer(mysock, buf, len);									 // 客户端发送订阅主题至服务器
	if (MQTTPacket_read(buf, buflen, transport_getdata) == SUBACK)					 /* 等待服务器返回订阅主题ACK响应包 */
	{
		unsigned short submsgid;
		int subcount;
		int granted_qos;

		MQTTDeserialize_suback(&submsgid, 1, &subcount, &granted_qos, buf, buflen);
		if (granted_qos != 0)
		{
			printf("granted qos != 0, %d\n", granted_qos);
			goto exit;
		}
	}
	else
		goto exit;

	/* loop getting msgs on subscribed topic */
	int cnt = 0;
	while (1)
	{
		/* transport_getdata() has a built-in 1 second timeout,
		your mileage will vary */
		if (MQTTPacket_read(buf, buflen, transport_getdata) == PUBLISH)
		{
			unsigned char dup;
			int qos;
			unsigned char retained;
			unsigned short msgid;
			int payloadlen_in;
			unsigned char *payload_in;
			MQTTString receivedTopic;

			// MQTTSerialize_publish  构造PUBLISH报文的函数，需要发送消息的依靠此函数构造报文
			// MQTTDeserialize_publish  函数是解析PUBLISH报文的函数，接收消息就靠它
			MQTTDeserialize_publish(&dup, &qos, &retained, &msgid, &receivedTopic, &payload_in, &payloadlen_in, buf, buflen);
			printf("message arrived %d,%s\n", payloadlen_in, payload_in);


			// 解析 JSON 字符串
    		cJSON *json = cJSON_Parse((const char*)(char*)payload_in);
			if (json == NULL) {
				// const	 char *error_ptr = cJSON_GetErrorPtr();
				// if (error_ptr != NULL) {
				// 	printf("解析 JSON 字符串 error");
				// 	// fprintf(stderr, "Error before: %s\n", error_ptr);
				// }
				printf("解析 JSON 字符串 error");
			}
			// 获取并打印 "pump_ctrl_state" 属性的值
			cJSON *pump_ctrl_state_json = cJSON_GetObjectItemCaseSensitive(json, "pump_ctrl_state");
			if (cJSON_IsNumber(pump_ctrl_state_json)) {
				int value = pump_ctrl_state_json->valueint;
				pump_ctrl_state = value;
				// printf("pump_ctrl_state: %d\n", value);
			}
			// 获取并打印 "pump_power_state" 属性的值
			cJSON *pump_power_state_json = cJSON_GetObjectItemCaseSensitive(json, "pump_power_state");
			if (cJSON_IsNumber(pump_power_state_json)) {
				int value = pump_power_state_json->valueint;
				pump_power_state = value;
				// printf("pump_power_state: %d\n", value);
			}
			// 获取并打印 "fan_mode" 属性的值
			cJSON *fan_mode_json = cJSON_GetObjectItemCaseSensitive(json, "fan_mode");
			if (cJSON_IsNumber(fan_mode_json)) {
				int value = fan_mode_json->valueint;
				fan_mode = value;
				// printf("fan_mode: %d\n", value);
			}
			// 获取并打印 "fan_level" 属性的值
			cJSON *fan_level_json = cJSON_GetObjectItemCaseSensitive(json, "fan_level");
			if (cJSON_IsNumber(fan_level_json)) {
				int value = fan_level_json->valueint;
				fan_level = value;
				// printf("fan_level: %d\n", value);
			}
		}
		topicString.cstring = "tobacco_01"; // 发布设置主题
		sprintf(payload, "{\"title\":2,\"soil_humidity\":%u,\"air_temperature\":%u}", s_Soil.Humidity, s_Sht40.Temp);
		int payloadlen = strlen(payload);
		len = MQTTSerialize_publish(buf, buflen, 0, 0, 0, 0, topicString, (unsigned char *)payload, payloadlen);
		if(cnt % 3 == 0){
		transport_sendPacketBuffer(mysock, buf, len);
		}
		cnt++;
		sleep(1);
	}
exit:
	printf("disconnecting\n");
	len = MQTTSerialize_disconnect(buf, buflen);
	transport_sendPacketBuffer(mysock, buf, len); // 客户端发送发布主题包至服务器
	transport_close(mysock);

	printf("out mqtt_connect");
	return 0;
}
