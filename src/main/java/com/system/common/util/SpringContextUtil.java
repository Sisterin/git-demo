package com.system.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * © All Rights Reserved.
 */

@Component
public class SpringContextUtil implements ApplicationContextAware {


	 private static ApplicationContext context = null;
	 
	
	    @Override
	    public void setApplicationContext(ApplicationContext applicationContext)
	            throws BeansException {
	        this.context = applicationContext;
	    }
    
    
    // 传入线程中
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }
 

 
    /// 获取当前环境
    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    } 
    
    
    /**
     * 通过名称获取bean
     */
    public static Object get(String name) {
        return context.getBean(name);
    }


    /**
     * 通过类型获取bean
     */
    public static Object get(Class<?> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 判断某个bean是不是存在
     */
    public static boolean has(String name) {
        return context.containsBean(name);
    }
    
}