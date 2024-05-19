package com.anranruozhu.utils;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author anranruozhu
 * @ClassName MqttCondition
 * @Description Mqtt的bean加载类
 * @create 2024/3/28 11:19
 **/
public class MqttCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        System.out.println("MqttCondition。。。。");
        //1、能获取到ioc使用的beanfactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        //2、获取类加载器
        ClassLoader classLoader = context.getClassLoader();
        //3、获取当前环境信息
        Environment environment = context.getEnvironment();
        String isOpen = environment.getProperty("mqtt.isOpen");
        return Boolean.valueOf(isOpen);
    }
}