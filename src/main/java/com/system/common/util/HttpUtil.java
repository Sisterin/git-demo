package com.system.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HttpUtil {
	 
	 
	   private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	 
	 
	    public static String getEasemobChatMessageDownloadUrl(String getUrl, String token) {
	        String downloadUrl = "";
	        try {
	            URL url = new URL(getUrl);    //把字符串转换为URL请求地址
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
	            //设置Head参数
	            connection.setRequestProperty("Content-Type", " application/json");//设定 请求格式 json，也可以设定xml格式的
	            connection.setRequestProperty("Accept-Charset", "utf-8");  //设置编码语言
	            connection.setRequestProperty("Connection", "keep-alive");  //设置连接的状态
	            connection.setRequestProperty("Authorization", token);
	            connection.connect();// 连接会话
	            // 获取输入流
	            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
	            String line;
	            StringBuilder sb = new StringBuilder();
	            while ((line = br.readLine()) != null) {// 循环读取流
	                sb.append(line);
	            }
	            br.close();// 关闭流
	            connection.disconnect();// 断开连接
	            //返回结果处理
	            JSONArray jsonArray = JSON.parseArray("[" + sb.toString() + "]");
	            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
	            JSONArray urlJSON = JSON.parseArray(jsonObject.get("data").toString());
	            downloadUrl = ((JSONObject) urlJSON.get(0)).get("url").toString();
	        } catch (Exception e) {
	        	e.printStackTrace();
	            return "fail";
	        }
	        return downloadUrl;
	    }
	 
	    
	    
	    public static String getTeAccount(String getUrl) {
	        try {
	            URL url = new URL(getUrl);    //把字符串转换为URL请求地址
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
	            //设置Head参数
	            connection.setRequestProperty("Content-Type", " application/json");//设定 请求格式 json，也可以设定xml格式的
	            connection.setRequestProperty("Accept-Charset", "utf-8");  //设置编码语言
	            connection.setRequestProperty("Connection", "keep-alive");  //设置连接的状态
	            connection.connect();// 连接会话
	            // 获取输入流
	            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
	            String line;
	            StringBuilder sb = new StringBuilder();
	            while ((line = br.readLine()) != null) {// 循环读取流
	                sb.append(line);
	            }
	            br.close();// 关闭流
	            connection.disconnect();// 断开连接
	          System.out.println(sb.toString());
	          return sb.toString();
	        } catch (Exception e) {
	        	e.printStackTrace();
	            return "fail";
	        }
	    }
	 
	 
	    /**
	     * 通过url下载文件到本地
	    **/
	    public static String  downloadFileByUrls(String urlStr,String fileName,String savePath){
	        try {
	            URL url = new URL(urlStr);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            //设置超时间为3秒
	            conn.setConnectTimeout(3 * 1000);
	            //防止屏蔽程序抓取而返回403错误
	            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	           
	            //得到输入流
	            InputStream inputStream = conn.getInputStream();
	            //获取自己数组
	            byte[] getData = readInputStream(inputStream);
	            //文件保存位置
	            File saveDir = new File(savePath);
	            if (!saveDir.exists()) {
	                saveDir.mkdir();
	            }
	            File file = new File(saveDir + File.separator + fileName);
	            FileOutputStream fos = new FileOutputStream(file);
	            fos.write(getData);
	            if (fos != null) {
	                fos.close();
	            }
	            if (inputStream != null) {
	                inputStream.close();
	            }
	            return "ok";
	        }catch (Exception e){
	            e.printStackTrace();
	            return "fail";
	        }
	    }
	    
	    
	    /**
	     * 通过url下载文件到本地
	    **/
	    public static Map<String,Object>  downloadFileByUrl(String urlStr,String fileName,String savePath){
	    	Map<String,Object> map=new HashMap<String,Object>();
	    	map.put("fileName", fileName);
	        try {
	            URL url = new URL(urlStr);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            //设置超时间为3秒
	            conn.setConnectTimeout(3 * 1000);
	            //防止屏蔽程序抓取而返回403错误
	            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	            if(StringUtils.isEmpty(fileName)){
	            	 fileName= getFileName(conn,urlStr);
	 	        	 map.put("fileName", fileName);
	            }
	            //得到输入流
	            InputStream inputStream = conn.getInputStream();
	            //获取自己数组
	            byte[] getData = readInputStream(inputStream);
	            //文件保存位置
	            File file = new File(savePath+ File.separator + fileName);
	            if(!file.getParentFile().exists()){
	                file.getParentFile().mkdirs();
	            }
	            
	            if(!file.exists()){
	                file.createNewFile();
	            }
	          //  File file = new File(saveDir + File.separator + fileName);
	            FileOutputStream fos = new FileOutputStream(file);
	            fos.write(getData);
	            if (fos != null) {
	                fos.close();
	            }
	            if (inputStream != null) {
	                inputStream.close();
	            }
	            map.put("result", "ok");
	            return map;
	        }catch (Exception e){
	            e.printStackTrace();
	            map.put("result", "fail");
	            return map;
	        }
	    }
	 
	    /**
		 * description: 获取文件名 
		 * @param http
		 * @param urlPath
		 * @throws UnsupportedEncodingException
		 * @return String
		 * @version v1.0
		 * @author w
		 * @date 2019年9月3日 下午8:25:55
		 */
		private static String getFileName(HttpURLConnection http , String urlPath) throws UnsupportedEncodingException {
			String headerField = http.getHeaderField("Content-Disposition");
			String fileName = null ;
			if(null != headerField) {
				String decode = URLDecoder.decode(headerField, "UTF-8");
				fileName = decode.split(";")[1].split("=")[1].replaceAll("\"", "");
				System.out.println("文件名是： "+ fileName);
			}
			if(null == fileName) {
				// 尝试从url中获取文件名
				String[] arr  = urlPath.split("/");
				fileName = arr[arr.length - 1];
				System.out.println("url中获取文件名："+ fileName);
			}
			return fileName;
		}
	   
	 
	 
	 
	    /**
	     * 从输入流中获取字节数组
	     */
	    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        while((len = inputStream.read(buffer)) != -1) {
	            bos.write(buffer, 0, len);
	        }
	        bos.close();
	        return bos.toByteArray();
	    }
	}