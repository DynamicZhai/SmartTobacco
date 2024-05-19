/******************************************************************************\
**  版    权 :  深圳开鸿数字产业发展有限公司（2021）
**  文件名称 :  PIR.c
**  功能描述 :  人体感应
**  作    者 :  王滨泉
**  日    期 :  2021.12.6
**  版    本 :  V0.0.1
**  变更记录 :  V0.0.1/2021.12.6
                1 首次创建                 
\******************************************************************************/

/******************************************************************************\
                                 Includes
\******************************************************************************/
#include <stdio.h>
#include <stdint.h>
#include <unistd.h>
#include <securec.h>

#include "ohos_init.h"
#include "cmsis_os2.h"
#include "ohos_types.h"
#include "wifiiot_errno.h"
#include "hi_io.h"
#include "hi_pwm.h"
#include <hi_adc.h>
#include "wifiiot_gpio.h"
/******************************************************************************\
                             Variables definitions
\******************************************************************************/
#define PIR_TASK_STACK_SIZE             1024*4                  //任务栈大小
#define PIR_TASK_PRIO                   10                      //任务优先等级

#define RED_PIN_NAME                    HI_IO_NAME_GPIO_11
#define RED_PIN_FUNC               	    HI_IO_FUNC_GPIO_11_PWM2_OUT
#define RED_PIN_PWM_NUM              	HI_PWM_PORT_PWM2

#define GREEN_PIN_NAME                  HI_IO_NAME_GPIO_10
#define GREEN_PIN_FUNC               	HI_IO_FUNC_GPIO_10_PWM1_OUT
#define GREEN_PIN_PWM_NUM              	HI_PWM_PORT_PWM1

// #define BLUE_PIN_NAME                   HI_IO_NAME_GPIO_12
// #define BLUE_PIN_FUNC               	HI_IO_FUNC_GPIO_12_PWM3_OUT
// #define BLUE_PIN_PWM_NUM              	HI_PWM_PORT_PWM3

#define LIGTH_ADC_CHANNEL               HI_ADC_CHANNEL_4        //光敏检测
#define HUMAN_ADC_CHANNEL               HI_ADC_CHANNEL_3        //人体感应

#define LED_DELAY_TIME_US               300*1000
#define PWM_FREQ_DIVITION               64000

//ADC.h
typedef struct
{
  unsigned short ohm;   //光敏电阻值
  unsigned short lux;   //流明
}PhotoRes_TypeDef;
//ADC.c
//GL5528光敏电阻的阻值与流明对应的关系
const  PhotoRes_TypeDef GL5528[281]=
{
{40000 , 	1 },
{26350 , 	2 },
{20640 , 	3 },
{17360 , 	4 },
{15170 , 	5 },
{13590 , 	6 },
{12390 , 	7 },
{11430 , 	8 },
{10650 , 	9 },
{9990 , 	10 },
{9440 , 	11 },
{8950 , 	12 },
{8530 , 	13 },
{8160 , 	14 },
{7830 , 	15 },
{7530 , 	16 },
{7260 , 	17 },
{7010 , 	18 },
{6790 , 	19 },
{6580 , 	20 },
{6390 , 	21 },
{6210 , 	22 },
{6050 , 	23 },
{5900 , 	24 },
{5750 , 	25 },
{5620 , 	26 },
{5490 , 	27 },
{5370 , 	28 },
{5260 , 	29 },
{5160 , 	30 },
{5050 , 	31 },
{4960 , 	32 },
{4870 , 	33 },
{4780 , 	34 },
{4700 , 	35 },
{4620 , 	36 },
{4540 , 	37 },
{4470 , 	38 },
{4400 , 	39 },
{4330 , 	40 },
{4270 , 	41 },
{4210 , 	42 },
{4150 , 	43 },
{4090 , 	44 },
{4040 , 	45 },
{3980 , 	46 },
{3930 , 	47 },
{3880 , 	48 },
{3840 , 	49 },
{3790 , 	50 },
{3740 , 	51 },
{3700 , 	52 },
{3660 , 	53 },
{3620 , 	54 },
{3580 , 	55 },
{3540 , 	56 },
{3500 , 	57 },
{3460 , 	58 },
{3430 , 	59 },
{3390 , 	60 },
{3360 , 	61 },
{3330 , 	62 },
{3300 , 	63 },
{3270 , 	64 },
{3230 , 	65 },
{3210 , 	66 },
{3180 , 	67 },
{3150 , 	68 },
{3120 , 	69 },
{3090 , 	70 },
{3070 , 	71 },
{3040 , 	72 },
{3020 , 	73 },
{2990 , 	74 },
{2970 , 	75 },
{2940 , 	76 },
{2920 , 	77 },
{2900 , 	78 },
{2880 , 	79 },
{2850 , 	80 },
{2830 , 	81 },
{2810 , 	82 },
{2790 , 	83 },
{2770 , 	84 },
{2750 , 	85 },
{2730 , 	86 },
{2710 , 	87 },
{2690 , 	88 },
{2680 , 	89 },
{2660 , 	90 },
{2640 , 	91 },
{2620 , 	92 },
{2610 , 	93 },
{2590 , 	94 },
{2570 , 	95 },
{2560 , 	96 },
{2540 , 	97 },
{2530 , 	98 },
{2510 , 	99 },
{2490 , 	100 },
{2480 , 	101 },
{2460 , 	102 },
{2450 , 	103 },
{2440 , 	104 },
{2420 , 	105 },
{2410 , 	106 },
{2390 , 	107 },
{2380 , 	108 },
{2370 , 	109 },
{2360 , 	110 },
{2340 , 	111 },
{2330 , 	112 },
{2320 , 	113 },
{2300 , 	114 },
{2290 , 	115 },
{2280 , 	116 },
{2270 , 	117 },
{2260 , 	118 },
{2250 , 	119 },
{2230 , 	120 },
{2220 , 	121 },
{2210 , 	122 },
{2200 , 	123 },
{2190 , 	124 },
{2180 , 	125 },
{2170 , 	126 },
{2160 , 	127 },
{2150 , 	128 },
{2140 , 	129 },
{2130 , 	130 },
{2120 , 	131 },
{2110 , 	132 },
{2100 , 	133 },
{2090 , 	134 },
{2080 , 	135 },
{2070 , 	136 },
{2060 , 	137 },
{2050 , 	138 },
{2040 , 	139 },
{2030 , 	141 },
{2020 , 	142 },
{2010 , 	143 },
{2000 , 	144 },
{1990 , 	145 },
{1980 , 	147 },
{1970 , 	148 },
{1960 , 	149 },
{1950 , 	150 },
{1940 , 	152 },
{1930 , 	153 },
{1920 , 	154 },
{1910 , 	155 },
{1900 , 	157 },
{1890 , 	158 },
{1880 , 	160 },
{1870 , 	161 },
{1860 , 	162 },
{1850 , 	164 },
{1840 , 	165 },
{1830 , 	167 },
{1820 , 	168 },
{1810 , 	170 },
{1800 , 	171 },
{1790 , 	173 },
{1780 , 	175 },
{1770 , 	176 },
{1760 , 	178 },
{1750 , 	180 },
{1740 , 	181 },
{1730 , 	183 },
{1720 , 	185 },
{1710 , 	187 },
{1700 , 	188 },
{1690 , 	190 },
{1680 , 	192 },
{1670 , 	194 },
{1660 , 	196 },
{1650 , 	198 },
{1640 , 	200 },
{1630 , 	202 },
{1620 , 	204 },
{1610 , 	206 },
{1600 , 	208 },
{1590 , 	210 },
{1580 , 	212 },
{1570 , 	215 },
{1560 , 	217 },
{1550 , 	219 },
{1540 , 	222 },
{1530 , 	224 },
{1520 , 	226 },
{1510 , 	229 },
{1500 , 	231 },
{1490 , 	234 },
{1480 , 	237 },
{1470 , 	239 },
{1460 , 	242 },
{1450 , 	245 },
{1440 , 	248 },
{1430 , 	250 },
{1420 , 	253 },
{1410 , 	256 },
{1400 , 	259 },
{1390 , 	262 },
{1380 , 	266 },
{1370 , 	269 },
{1360 , 	272 },
{1350 , 	275 },
{1340 , 	279 },
{1330 , 	282 },
{1320 , 	286 },
{1310 , 	289 },
{1300 , 	293 },
{1290 , 	297 },
{1280 , 	300 },
{1270 , 	304 },
{1260 , 	308 },
{1250 , 	312 },
{1240 , 	317 },
{1230 , 	321 },
{1220 , 	325 },
{1210 , 	330 },
{1200 , 	334 },
{1190 , 	339 },
{1180 , 	344 },
{1170 , 	348 },
{1160 , 	353 },
{1150 , 	358 },
{1140 , 	364 },
{1130 , 	369 },
{1120 , 	374 },
{1110 , 	380 },
{1100 , 	386 },
{1090 , 	391 },
{1080 , 	397 },
{1070 , 	403 },
{1060 , 	410 },
{1050 , 	416 },
{1040 , 	423 },
{1030 , 	430 },
{1020 , 	436 },
{1010 , 	444 },
{1000 , 	451 },
{990 , 	458 },
{980 , 	466 },
{970 , 	474 },
{960 , 	482 },
{950 , 	491 },
{940 , 	499 },
{930 , 	508 },
{920 , 	517 },
{910 , 	526 },
{900 , 	536 },
{890 , 	546 },
{880 , 	556 },
{870 , 	567 },
{860 , 	578 },
{850 , 	589 },
{840 , 	600 },
{830 , 	612 },
{820 , 	625 },
{810 , 	637 },
{800 , 	650 },
{790 , 	664 },
{780 , 	678 },
{770 , 	692 },
{760 , 	707 },
{750 , 	723 },
{740 , 	739 },
{730 , 	756 },
{720 , 	773 },
{710 , 	791 },
{700 , 	809 },
{690 , 	829 },
{680 , 	849 },
{670 , 	869 },
{660 , 	891 },
{650 , 	914 },
{640 , 	937 },
{630 , 	961 },
{620 , 	987 },

};

//ADC.c
/**
  * @brief 通过电阻值算出光照度
  * @param 光敏电阻值
  * @retval None
  */    
unsigned short GetLux(uint32_t PhotoResistor)
{
  unsigned short lux = 0;
  //查表法，根据电阻值得出光照度
  for(int i = 0 ; i < 281 ; i++)
  {
    if (PhotoResistor > GL5528[i].ohm)
    {
      lux = GL5528[i].lux;
      break;
    }
  }
  return lux;
}


//------------------------------------------------------------------------------
//COLOUR id
typedef enum
{
    COLOUR_RED = 0,				    //红
	COLOUR_GREEN,					//绿
	// COLOUR_BLUE,					//黄
	COLOUR_ID_MAX
}TE_COLOUR_ID;

//------------------------------------------------------------------------------
//human status
typedef enum
{
    HUMAN_LEAVE = 0,				//离开
	HUMAN_COME,					    //有人
}TE_HUMAN_STATUS;
//------------------------------------------------------------------------------
//light state
typedef enum
{
    LIGHT_DAY = 0,				    //白天
	LIGHT_NIGHT,					//夜晚
}TE_LIGHT_STATE;

//-----------------------------------------------------------------------------
//led info
typedef struct {
	uint8_t pin;
    uint8_t mode;
    uint8_t func;
    uint8_t pwm_num;
	uint8_t on;
	uint8_t off;
}TS_LED_CONTROL_INFO;

// 炫彩灯，不要蓝灯，与风扇有引脚冲突，后续换成单独一个开发板后未改回
TS_LED_CONTROL_INFO s_LedControl[] = {
	{RED_PIN_NAME,	    WIFI_IOT_GPIO_DIR_OUT,   RED_PIN_FUNC,   RED_PIN_PWM_NUM,    1, 0},
	{GREEN_PIN_NAME,	WIFI_IOT_GPIO_DIR_OUT,   GREEN_PIN_FUNC, GREEN_PIN_PWM_NUM,  1, 0},
	// {BLUE_PIN_NAME,     WIFI_IOT_GPIO_DIR_OUT,   BLUE_PIN_FUNC,  BLUE_PIN_PWM_NUM,   1, 0},
};

static uint8_t s_HumanStatus = 0;
static uint8_t s_LightState = 0;
/******************************************************************************\
                             Functions definitions
\******************************************************************************/
/*
* 函数名称 : Set_Light_State
* 功能描述 : 设置光照状态
* 参    数 : State - 0：白天，1：夜晚
* 返回值   : 空
* 示    例 : Set_Light_State(State);
*/
/******************************************************************************/ 
void Set_Light_State(uint8_t State)
/******************************************************************************/ 
{
    s_LightState = State;
}
/*
* 函数名称 : Get_Light_State
* 功能描述 : 获取光照状态
* 参    数 : 空
* 返回值   : 光照状态 - 0：白天，1：夜晚
* 示    例 : State = Get_Light_State();
*/
/******************************************************************************/ 
uint8_t Get_Light_State(void)
/******************************************************************************/ 
{
    return s_LightState;
}
/*
* 函数名称 : Set_Human_Status
* 功能描述 : 设置感应状态
* 参    数 : Status - 0：无人，1：有人经过
* 返回值   : 空
* 示    例 : Set_Human_Status(State);
*/
/******************************************************************************/ 
void Set_Human_Status(uint8_t Status)
/******************************************************************************/ 
{
    s_HumanStatus = Status;
}
/*
* 函数名称 : Get_Human_Status
* 功能描述 : 获取感应状态
* 参    数 : 空
* 返回值   : 感应状态 - 0：无人，1：有人经过
* 示    例 : Status = Get_Human_Status();
*/
/******************************************************************************/ 
uint8_t Get_Human_Status(void)
/******************************************************************************/ 
{
    return s_HumanStatus;
}
/*
* 函数名称 : PIR_Init
* 功能描述 : 人体感应初始化
* 参    数 : 空
* 返回值   : 空
* 示    例 : PIR_Init();
*/
/******************************************************************************/ 
void PIR_Init(void)
/******************************************************************************/ 
{
    uint8_t i;
    // 初始化炫彩灯的颜色
    for(i =0; i<COLOUR_ID_MAX; i++)
    {
        hi_io_set_func(s_LedControl[i].pin,s_LedControl[i].func); 
        hi_pwm_init(s_LedControl[i].pwm_num);
        hi_pwm_set_clock(PWM_CLK_160M);
        hi_pwm_stop(s_LedControl[i].pwm_num);
    }
    // 初始化后每个颜色闪一遍
    for(i =0; i<1; i++)
    {
        for(uint8_t j =0; j<COLOUR_ID_MAX; j++)
        {
            hi_pwm_start(s_LedControl[j].pwm_num,PWM_FREQ_DIVITION/2, PWM_FREQ_DIVITION);
            usleep(LED_DELAY_TIME_US);
            hi_pwm_stop(s_LedControl[j].pwm_num);
            usleep(LED_DELAY_TIME_US);
        }
    }

}


/*
* 函数名称 : PIR_Task
* 功能描述 : 人体感应任务
* 参    数 : 空
* 返回值   : 空
* 示    例 : PIR_Task(&argument);
*/
/******************************************************************************/ 





extern int light_level;
extern int light_mode;
// uint16_t uk_data;
uint16_t light_intensity;
void PIR_Task(void *argument)
/******************************************************************************/ 
{
    (void)argument;
    // uint8_t i = 0;
    // uint16_t data;
    // static uint16_t light_cnt = 0;

    PIR_Init();

    while(1)
    {
        usleep(10*1000);	//10ms
        if(hi_adc_read(LIGTH_ADC_CHANNEL,&light_intensity,HI_ADC_EQU_MODEL_8,HI_ADC_CUR_BAIS_DEFAULT,0) == HI_ERR_SUCCESS)
        {
            // light_intensity = 10240000 / (1.1 * uk_data) - 2500;
            sleep(1);
            // printf("\n\n\nlight_intensity is %d\n\n\n", light_intensity);
            // printf("\n\nlight_mode is %d\n\n\n", light_mode);
            if(light_mode == 0)
            {
                Set_Light_State(LIGHT_DAY);
                hi_pwm_stop(s_LedControl[COLOUR_RED].pwm_num);
            }
            else if(light_mode == 1)
            {
                Set_Light_State(LIGHT_NIGHT);
                hi_pwm_start(s_LedControl[COLOUR_RED].pwm_num,PWM_FREQ_DIVITION * light_level * 0.01, PWM_FREQ_DIVITION);
            }
        }
    }
}
/*
* 函数名称 : PIR_Demo
* 功能描述 : 人体感应任务
* 参    数 : 空
* 返回值   : 空
* 示    例 : PIR_Demo(void);
*/
/******************************************************************************/ 
void PIR_Demo(void)
/******************************************************************************/ 
{
    osThreadAttr_t attr;

    attr.name       = "PIR-Task";
    attr.attr_bits  = 0U;
    attr.cb_mem     = NULL;
    attr.cb_size    = 0U;
    attr.stack_mem  = NULL;
    attr.stack_size = PIR_TASK_STACK_SIZE;
    attr.priority   = PIR_TASK_PRIO;

    if (osThreadNew((osThreadFunc_t)PIR_Task, NULL, &attr) == NULL) {
        printf("\r\n[PIR_Demo] Falied to create PIR task!\n");
    }else{
        printf("\r\n[PIR_Demo] Succ to create PIR task!\n");
    }
}

APP_FEATURE_INIT(PIR_Demo);
/******************************* End of File (C) ******************************/