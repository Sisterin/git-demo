package com.system.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志管理工具类
 */
public final class LogUtil {
	
	private static Logger logger  =  LoggerFactory.getLogger(LogUtil.class);
	
	private LogUtil(){}
	
	
	public static void debug(String message){
		logger.debug(message);
	}
	
	public static void info(String message){
		logger.info(message);
	}
	
	public static void error(String message){
		logger.error(message);
	}
	
	public static void error(String message,Exception ex){
		logger.error(message,ex);
	}
	
}
