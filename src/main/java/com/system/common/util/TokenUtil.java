package com.system.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class TokenUtil {
	
	

   //创建应用的时候会对应生成
	public static String client_id = "YXA61x4vtgOZSWykftFl01XPJg";

	public static String client_secret = "YXA6CH1AJfO6h_6_CAzeFGiuzX6OyUk";
    
	public static String Orgname="1102200515200401";
    		
	public static String appname="bjwydoctor";
    
	public static String tokenurl="http://a1.easemob.com/{org_name}/{app_name}/token";
    
	private static Map<String,Object>  map=new HashMap<String,Object>();
	
	
	
	 @SuppressWarnings("deprecation")
	    public static String getToken()
	            throws Exception {
		 
		 Date b= new Date();
		 if(map.get("startTime")!=null){
			 
			 Date a =(Date) map.get("startTime");
			 Integer expires_in =(Integer) map.get("expires_in");
			 long interval = (b.getTime() - a.getTime())/1000;
			 if(interval<expires_in){
				 return (String) map.get("token");
			 }
		   }
		
		 
	        String resultStr = null;
	        @SuppressWarnings("resource")
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	       String url= tokenurl.replace("{org_name}", Orgname).replace("{app_name}", appname);
	        HttpPost post = new HttpPost(url);
	        //JsonParser jsonparer =JsonParser;// 初始化解析json格式的对象
	        // 接收参数json列表
	        JSONObject jsonParam = new JSONObject();
	        jsonParam.put("grant_type", "client_credentials");
	        jsonParam.put("client_id", client_id);
	        jsonParam.put("client_secret", client_secret);
	        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
	        entity.setContentEncoding("UTF-8");
	        entity.setContentType("application/json");

	        post.setEntity(entity);
	        map.put("startTime", new Date());
	        // 请求结束，返回结果
	            HttpResponse res = httpClient.execute(post);
	            // 如果服务器成功地返回响应
	            String responseContent = null; // 响应内容
	            HttpEntity httpEntity = res.getEntity();
	            responseContent = EntityUtils.toString(httpEntity, "UTF-8");

	            //System.out.println( responseContent);
	           //JsonObject json = JsonParser.parse(responseContent);
	            JSONObject json = JSONObject.parseObject(responseContent);
	                   // .getAsJsonObject();
	           if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                if (json.get("errcode") != null){
	                    //resultStr = json.get("errcode").getAsString();
	                } else {// 正常情况下
	                    resultStr = "Bearer "+json.get("access_token").toString();
	                    map.put("token", resultStr);
	                    map.put("expires_in", json.getInteger("expires_in"));
	                }
	            }
	            // 关闭连接 ,释放资源
	            httpClient.getConnectionManager().shutdown();
	            return resultStr;
	    }
	 
	 public static void main(String[] args) throws Exception {
		 String  result=getToken();
	}
	

}
