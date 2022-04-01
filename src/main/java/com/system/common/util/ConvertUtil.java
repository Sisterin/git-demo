package com.system.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 腾讯云图像和音频的转换工具类
 * @author 555
 *
 */
public class ConvertUtil {
	
	
	
	
	
	/**
	 * 转换图片
	 * @param url
	 * @return
	 */
	public static String convertImage(String storepath,String url){
		if(StringUtils.contains(url, "?")){
			url=StringUtils.substring(url,0, url.lastIndexOf("?"));
		}
		String  fileName=StringUtils.substring(url, url.lastIndexOf("/")+1);
    	String path="image/"+UUID.randomUUID().toString().replaceAll("-", "");
    	String savepath=storepath+path;
    	Map<String, Object> map=HttpUtil.downloadFileByUrl(url,fileName,savepath);
    	String result=(String) map.get("result");
    	if(StringUtils.equals(result,"ok")){
    		return path+"/"+fileName;
    	}else if(StringUtils.equals(result,"fail")){
    		return null;
    	}
		return  null;
	}
	
	/**
	 * 转换音频文件
	 * @param url
	 * @return
	 */
	public static String convertSound(String storepath,String url){
		String path="sound/"+UUID.randomUUID().toString().replaceAll("-", "");
    	String savepath=storepath+path;
    	Map<String, Object> map=HttpUtil.downloadFileByUrl(url,null,savepath);
     	String result=(String) map.get("result");
    	String fileName=(String) map.get("fileName");
    	if(StringUtils.equals(result,"ok")){
    		return path+"/"+fileName;
    	}else if(StringUtils.equals(result,"fail")){
    		return null;
    	}
		return  null;
	}
	
	/**
	 * 转换文件
	 * @param url
	 * @return
	 */
	public static Map<String,Object> convertFile(String storepath,String url,String fileName){
    	String path="file/"+UUID.randomUUID().toString().replaceAll("-", "");
    	String savepath=storepath+path;
    	if(fileName.contains("/")){
    		fileName=StringUtils.substring(fileName, fileName.lastIndexOf("/")+1);
    		
    	}
    	Map<String, Object> map=HttpUtil.downloadFileByUrl(url,null,savepath);
     	String result=(String) map.get("result");
    	 fileName=(String) map.get("fileName");
    	if(StringUtils.equals(result,"ok")){
    		map.put("url", path+"/"+fileName);
    		map.put("fileName", fileName);
    		return map;
    	}else if(StringUtils.equals(result,"fail")){
    		return null;
    	}
		return  null;
	}
	
	   public static void main(String[] args) {
	      String url="http://xxx/3200490432214177468_144115198371610486_D61040894AC3DE44CDFFFB3EC7EB720F/0";
	      String [] array=StringUtils.split(url,"/");
	      System.out.println(array[2]);
	      System.out.println(array[3]);
		}

	   
	   
	   
	public static Map<String,Object> convertVideoThumb(String storepath, String videoUrl,String thumbUrl) {
		String path="video/"+UUID.randomUUID().toString().replaceAll("-", "");
		String savepath=storepath+path;
		String  fileName=StringUtils.substring(videoUrl, videoUrl.lastIndexOf("/")+1);
		Map<String, Object> map=HttpUtil.downloadFileByUrl(videoUrl,fileName,savepath);
		String result=(String) map.get("result");
		if(StringUtils.equals(result,"ok")){
    		map.put("videoUrl", path+"/"+fileName);
    	}
		
		String savepath1=storepath+path;
		String  fileName1=StringUtils.substring(thumbUrl, thumbUrl.lastIndexOf("/")+1);
		Map<String, Object> maps=HttpUtil.downloadFileByUrl(thumbUrl,fileName1,savepath1);
		String result1=(String) maps.get("result");
		if(StringUtils.equals(result1,"ok")){
    		map.put("thumbUrl", path+"/"+fileName1);
    	}
		return map;
	}
	   
	   
	   

}
