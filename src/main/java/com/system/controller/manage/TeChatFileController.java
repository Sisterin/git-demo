package com.system.controller.manage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.system.common.util.LogUtil;


/**
 * 读取腾讯文件(本地存的)
 * @author 555
 *
 */
@Controller
public class TeChatFileController {
	
	
	 @Value(value = "${storepath}")
	 private  String storepath;
	 
	 
	 
    /**
     * 读取图片(有后缀)
     */
    @RequestMapping(value="/image/{pathId}/{imageId}.{suffix}")
    public void readImageWithSuffix(@PathVariable(value = "pathId") String pathId,@PathVariable(value = "imageId") String imageId, @PathVariable(value = "suffix") String suffix, HttpServletResponse response){
    	InputStream fileIs = null;
    	try {
    		imageId=URLEncoder.encode( imageId, "UTF-8" );
    		imageId=imageId.replaceAll("\\+", "%20");
    		String url = storepath +"image/"+pathId+"/" + imageId + "." + suffix;
			File file = new File(url);
			fileIs = new FileInputStream(file);
			IOUtils.copy(fileIs, response.getOutputStream());
			//设置缓存
			response.setHeader("Cache-Control", "max-age=604800");
			response.setHeader("Pragrma", "Pragma");
			response.setDateHeader("Expires", DateUtils.addDays(new Date(), 30).getTime());
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage(), e);
		}finally {
			IOUtils.closeQuietly(fileIs);
		}
    }
    
    
    /**
     * 读取图片(无后缀)
     */
    @RequestMapping(value="/image/{pathId}/{imageId}")
    public void readImage(@PathVariable(value = "pathId") String pathId,@PathVariable(value = "imageId") String imageId,HttpServletResponse response){
    	InputStream fileIs = null;
    	try {
    		imageId=URLEncoder.encode( imageId, "UTF-8" );
    		imageId=imageId.replaceAll("\\+", "%20");
    		String url = storepath +"image/"+pathId+"/" + imageId;
			File file = new File(url);
			fileIs = new FileInputStream(file);
			IOUtils.copy(fileIs, response.getOutputStream());
			//设置缓存
			response.setHeader("Cache-Control", "max-age=604800");
			response.setHeader("Pragrma", "Pragma");
			 response.setContentType("image/jpg");
			response.setDateHeader("Expires", DateUtils.addDays(new Date(), 30).getTime());
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e.getMessage(), e);
		}finally {
			IOUtils.closeQuietly(fileIs);
		}
    }
   
	
    @RequestMapping(value="/file/{pathId}/{fileId}.{suffix}")
    public void readFile(@PathVariable(value = "pathId") String pathId,@PathVariable(value = "fileId") String fileId, @PathVariable(value = "suffix") String suffix, HttpServletResponse response){
    	InputStream fileIs = null;
    	try {
    		fileId=URLEncoder.encode( fileId, "UTF-8" );
    		String url = storepath+"file/" +pathId+"/" + fileId + "." + suffix;
			File file = new File(url);
			fileIs = new FileInputStream(file);
			IOUtils.copy(fileIs, response.getOutputStream());
			//设置缓存
			response.setHeader("Cache-Control", "max-age=604800");
			response.setHeader("Pragrma", "Pragma");
			response.setDateHeader("Expires", DateUtils.addDays(new Date(), 30).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(fileIs);
		}
    }
    


}
