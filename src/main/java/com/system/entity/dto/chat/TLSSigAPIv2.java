package com.system.entity.dto.chat;


//使用旧版本 base64 编解码实现增强兼容性
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.nio.charset.Charset;

import java.util.Arrays;
import java.util.zip.Deflater;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;

public class TLSSigAPIv2 {
	
	
 private  long sdkappid;
 
 private String key;

 public TLSSigAPIv2(long sdkappid, String key) {
     this.sdkappid = sdkappid;
     this.key = key;
 }

 private String hmacsha256(String identifier, long currTime, long expire, String base64Userbuf) {
     String contentToBeSigned = "TLS.identifier:" + identifier + "\n"
             + "TLS.sdkappid:" + sdkappid + "\n"
             + "TLS.time:" + currTime + "\n"
             + "TLS.expire:" + expire + "\n";
     if (null != base64Userbuf) {
         contentToBeSigned += "TLS.userbuf:" + base64Userbuf + "\n";
     }
     try {
         byte[] byteKey = key.getBytes("UTF-8");
         Mac hmac = Mac.getInstance("HmacSHA256");
         SecretKeySpec keySpec = new SecretKeySpec(byteKey, "HmacSHA256");
         hmac.init(keySpec);
         byte[] byteSig = hmac.doFinal(contentToBeSigned.getBytes("UTF-8"));
         return (new BASE64Encoder().encode(byteSig)).replaceAll("\\s*", "");
     } catch (UnsupportedEncodingException e) {
         return "";
     } catch (NoSuchAlgorithmException e) {
         return "";
     } catch (InvalidKeyException  e) {
         return "";
     }
 }

 private String genSig(String identifier, long expire, byte[] userbuf) throws JSONException {

     long currTime = System.currentTimeMillis()/1000;

     JSONObject sigDoc = new JSONObject();
     sigDoc.put("TLS.ver", "2.0");
     sigDoc.put("TLS.identifier", identifier);
     sigDoc.put("TLS.sdkappid", sdkappid);
     sigDoc.put("TLS.expire", expire);
     sigDoc.put("TLS.time", currTime);

     String base64UserBuf = null;
     if (null != userbuf) {
         base64UserBuf = new BASE64Encoder().encode(userbuf);
         sigDoc.put("TLS.userbuf", base64UserBuf);
     }
     String sig = hmacsha256(identifier, currTime, expire, base64UserBuf);
     if (sig.length() == 0) {
         return "";
     }
     sigDoc.put("TLS.sig", sig);
     Deflater compressor = new Deflater();
     compressor.setInput(sigDoc.toString().getBytes(Charset.forName("UTF-8")));
     compressor.finish();
     byte [] compressedBytes = new byte[2048];
     int compressedBytesLength = compressor.deflate(compressedBytes);
     compressor.end();
     return (new String(Base64URL.base64EncodeUrl(Arrays.copyOfRange(compressedBytes,
             0, compressedBytesLength)))).replaceAll("\\s*", "");
 }

 public String genSig(String identifier, long expire) throws JSONException {
     return genSig(identifier, expire, null);
 }

 public String genSigWithUserBuf(String identifier, long expire, byte[] userbuf) throws JSONException {
     return genSig(identifier, expire, userbuf);
 }
 
 
 

	public static void main(String[] args) throws JSONException {
// 根据生成的IM用户生成签名
long SDKAppID = 1400372169;//去应用里边找自己相应的SDKAppID为long类型 例：1400344574
String miyao = "4494584c4d5342bf899852558651a40d0067f76137bf060807fcaf25bf85232a";//去应用里边找自己相应的秘钥 例：39efc328c747e7f8aa655be2398266dfd32dc752a979710d9e21b9666618bbed1
String identifier="a2";// 所要生成签名的用户 例：zx5263
long expire ;// 签名有效时长 例：3600 * 24 * 180  //有效期默认为 180 天
TLSSigAPIv2 api = new TLSSigAPIv2(SDKAppID, miyao);
String sign = api.genSig(identifier,3600 * 24 * 180);
System.out.println(sign);
}

}