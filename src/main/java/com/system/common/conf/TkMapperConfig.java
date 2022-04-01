package com.system.common.conf;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
public class TkMapperConfig {
	
	 @Bean 
	 public MapperScannerConfigurer mapperScannerConfigurer(){ 
		 
		 MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer(); 
		 mapperScannerConfigurer.setBasePackage("com.system.mapper");
		 Properties propertiesMapper = new Properties(); //通用mapper位置，不要和其他mapper、dao放在同一个目录
		 propertiesMapper.setProperty("mappers", "com.system.common.base.MyMapper");
		 propertiesMapper.setProperty("notEmpty", "false"); 
		 propertiesMapper.setProperty("IDENTITY","SELECT UUID()");
		 //主键UUID回写方法执行顺序,默认AFTER,可选值为(BEFORE|AFTER) 
		 propertiesMapper.setProperty("ORDER","BEFORE"); 
		 mapperScannerConfigurer.setProperties(propertiesMapper);
		 return mapperScannerConfigurer;
		 
	 }
	
}
