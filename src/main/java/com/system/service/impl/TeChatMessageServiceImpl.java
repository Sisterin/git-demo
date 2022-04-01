package com.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageRowBounds;
import com.system.common.util.ConvertUtil;
import com.system.common.util.HttpClientUtil;
import com.system.common.util.LogUtil;
import com.system.entity.dto.FileMsgContent;
import com.system.entity.dto.ImageMsgContent;
import com.system.entity.dto.ImageMsgContentDetail;
import com.system.entity.dto.PageDto;
import com.system.entity.dto.SoundMsgContent;
import com.system.entity.dto.VideoMsgContent;
import com.system.entity.dto.chat.TLSSigAPIv2;
import com.system.entity.pojo.TeChatMessage;
import com.system.entity.vo.TeAccountDetailVo;
import com.system.entity.vo.TeAccountVo;
import com.system.mapper.TeChatMessageMapper;
import com.system.service.TeChatMessageService;


@Service
public class TeChatMessageServiceImpl implements TeChatMessageService{

	
	@Autowired
	  private  TeChatMessageMapper teChatMessageMapper;
	
	
	
	@Value(value = "${SDKAppID}")
	private   String SDKAppID;
	
	@Value(value = "${Key}")
	private   String Key ;
	
	@Value(value = "${ADMINACCOUNT}")
	private   String ADMINACCOUNT ;
	
	 //转变聊天记录为自己的文件的文件地址
	@Value(value = "${storepath}")
	 private  String storepath;
	/**
	 * 腾讯云当发消息之后,会会回调我们
	 */
	public int callbackMessages(TeChatMessage teChatMessage) {
			
		teChatMessage.setId(UUID.randomUUID().toString());
		//先插入消息,在去转换,然后根据id去更新
		teChatMessage.setCreatetime(new Date());
		teChatMessageMapper.insertSelective(teChatMessage);
		
		//首先判断消息类型
		String msgBody=teChatMessage.getMsgbody();
		Object obj = JSON.parse(msgBody);
	    if (obj instanceof JSONObject) {
	    	JSONObject jsonobject= this.needFileConvert((JSONObject)obj);
	    	if(jsonobject!=null && !jsonobject.isEmpty() ){
	    		teChatMessage.setAfterconversionmsgbody(jsonobject.toString());
		    	teChatMessageMapper.updateByPrimaryKeySelective(teChatMessage);
	    	}
	    	
	       }
	    if (obj instanceof JSONArray) {
	            JSONArray jsonArray= this.needFileConvert((JSONArray)obj);
	            if(jsonArray!=null && !jsonArray.isEmpty()){
	            	teChatMessage.setAfterconversionmsgbody(jsonArray.toString());
			    	teChatMessageMapper.updateByPrimaryKeySelective(teChatMessage);
	            }
		    	
	        }

		return 0;
	}

	
	 
	/**
	 * 需要文件转换
	 * @param jsonObject
	 * @return
	 */
	 private JSONObject needFileConvert(JSONObject jsonObject){
		    
			String MsgType=jsonObject.getString("MsgType");
			
			//下面3个类型需要把里面的文件转换
			if(StringUtils.equals(MsgType, "TIMImageElem")){
				//如果消息类型是图像
				String MsgContent=jsonObject.getString("MsgContent");
				ImageMsgContent imageMsgContent=JSONObject.parseObject(MsgContent, ImageMsgContent.class);
				for(ImageMsgContentDetail imageMsgContentDetail:imageMsgContent.getImageInfoArray()){
					String url=ConvertUtil.convertImage(storepath,imageMsgContentDetail.getURL());
					imageMsgContentDetail.setURL(url);
				}
				jsonObject.put("MsgContent", imageMsgContent);
				return jsonObject;
				
			}else if(StringUtils.equals(MsgType, "TIMFileElem")){
				//如果消息类型是文件
				String MsgContent=jsonObject.getString("MsgContent");
				FileMsgContent fileMsgContent=JSONObject.parseObject(MsgContent, FileMsgContent.class);
				Map<String,Object> map=ConvertUtil.convertFile(storepath,fileMsgContent.getUrl(),fileMsgContent.getFileName());
				String url=(String) map.get("url");
				String fileName=(String) map.get("fileName");
				fileMsgContent.setUrl(url);
				fileMsgContent.setFileName(fileName);
				jsonObject.put("MsgContent", fileMsgContent);
				return jsonObject;
			}else if(StringUtils.equals(MsgType, "TIMSoundElem")){
				//如果消息类型是语音
				String MsgContent=jsonObject.getString("MsgContent");
				SoundMsgContent soundMsgContent=JSONObject.parseObject(MsgContent, SoundMsgContent.class);
				String url=ConvertUtil.convertSound(storepath,soundMsgContent.getUrl());
				soundMsgContent.setUrl(url);
				jsonObject.put("MsgContent", soundMsgContent);
				return jsonObject;
			}else if(StringUtils.equals(MsgType, "TIMVideoFileElem")){
				//如果消息类型是视频文件
				String MsgContent=jsonObject.getString("MsgContent");
				VideoMsgContent videoMsgContent=JSONObject.parseObject(MsgContent, VideoMsgContent.class);
				Map<String,Object> map=ConvertUtil.convertVideoThumb(storepath,videoMsgContent.getVideoUrl(),videoMsgContent.getThumbUrl());
				if(map.get("videoUrl")!=null){
					videoMsgContent.setVideoUrl((String)map.get("videoUrl"));
					if(map.get("thumbUrl")!=null){
						videoMsgContent.setThumbUrl((String)map.get("thumbUrl"));
					}
					jsonObject.put("MsgContent", videoMsgContent);
					return jsonObject;
				}
			}
		 return null;
		 
	 }
	 
	 
	 /**
		 * 数组需要文件转换(如果消息是组合消息的话,消息是一个数组)
		 * @param jsonObject
		 * @return
		 */
	 private JSONArray needFileConvert(JSONArray array){
		    JSONArray jsonArray=new JSONArray();
		    for(int i=0; i<array.size();i++){
			JSONObject  jsonobject=(JSONObject) array.get(i);
			JSONObject json=this.needFileConvert(jsonobject);
			if(json!=null && !json.isEmpty()){
				jsonArray.add(json);
			}
		    }
		    return jsonArray;
	}



	@Override
	public PageDto<TeChatMessage> getTeMessageList(TeChatMessage teChatMessage, PageDto<TeChatMessage> pageDto) {
		PageRowBounds pageRowBounds = new PageRowBounds(pageDto.getPageNum(), pageDto.getPageSize());
		List<TeChatMessage> data = teChatMessageMapper.selectBySelective(teChatMessage, pageRowBounds);
		pageDto.setTotal(pageRowBounds.getTotal());
		pageDto.setResult(data);
		return pageDto;
	}
	
	public PageDto<TeChatMessage> getTeMessageListForSystem(TeChatMessage teChatMessage, PageDto<TeChatMessage> pageDto) {
		PageRowBounds pageRowBounds = new PageRowBounds(pageDto.getPageNum(), pageDto.getPageSize());
		List<TeChatMessage> data = teChatMessageMapper.selectMessageList(teChatMessage, pageRowBounds);
		pageDto.setTotal(pageRowBounds.getTotal());
		pageDto.setResult(data);
		return pageDto;
	}
	/**
	 * 导入账号
	 * @throws JSONException 
	 */
	
	public  int importAccount(String account,String nickName,String faceUrl) throws Exception {
		String adminAccount=ADMINACCOUNT;
		long expire=3600 * 24 * 180 ;// 签名有效时长 例：3600 * 24 * 180
		TLSSigAPIv2 api = new TLSSigAPIv2(Long.valueOf(SDKAppID), Key);
		String sign = api.genSig(adminAccount,expire);
		String url="https://console.tim.qq.com/v4/im_open_login_svc/account_check?sdkappid={sdkappid}&identifier={identifier}&usersig={usersig}&random={random}&contenttype=json";
		url=url.replace("{sdkappid}", SDKAppID).replace("{identifier}", adminAccount).replace("{usersig}", sign).replace("{random}", String.valueOf(new Date().getTime()));
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> maps=new HashMap<String,Object>();
		maps.put("UserID", account);
		list.add(maps);
		Map<String,Object> jsonMap=new HashMap<String,Object>();
		jsonMap.put("CheckItem", list);
		
		String json=JSON.toJSONString(jsonMap);
		String result=HttpClientUtil.doPostJson(url,json);
		 TeAccountVo teAccountVo=JSONObject.parseObject(result, TeAccountVo.class);
		 if(teAccountVo.getErrorCode()==0){
			 List<TeAccountDetailVo>  lists= teAccountVo.getResultItem();
			 TeAccountDetailVo teAccountDetailVo=lists.get(0);
			if(StringUtils.equals("NotImported", teAccountDetailVo.getAccountStatus())){
				//导入账号
				String importUrl="https://console.tim.qq.com/v4/im_open_login_svc/account_import?sdkappid={sdkappid}&identifier={identifier}&usersig={usersig}&random={random}&contenttype=json";
				importUrl=importUrl.replace("{sdkappid}", SDKAppID).replace("{identifier}", adminAccount).replace("{usersig}", sign).replace("{random}", String.valueOf(new Date().getTime()));
			    Map<String,String>  map=new HashMap<String,String>();
			    map.put("Identifier", account);
			    map.put("Nick", nickName);
			    if(StringUtils.isNotEmpty(faceUrl)  && (faceUrl.contains("http") || faceUrl.contains("https"))){
			    	  map.put("FaceUrl", faceUrl);
			    }
			    String jsons=JSON.toJSONString(map);
				String resulta=HttpClientUtil.doPostJson(importUrl,jsons);
				JSONObject resultjson=JSONObject.parseObject(resulta);
				int ErrorCode=(int) resultjson.get("ErrorCode");
				if(ErrorCode==0){
					return 1;
				}else{
					//导入账号失败
					return -1;
				}
		   }else{
			   
			   return 1;
		   }
		
		}
			//查询账号失败
			return -2;
		
		
	}
	
	
	public  int getLoginStatus(String account) throws Exception {
		
		String adminAccount="administrator";
		long expire=3600 * 24 * 180 ;// 签名有效时长 例：3600 * 24 * 180
		TLSSigAPIv2 api = new TLSSigAPIv2(Long.valueOf(SDKAppID), Key);
		String sign = api.genSig(adminAccount,expire);
		String url="https://console.tim.qq.com/v4/openim/querystate?sdkappid={sdkappid}&identifier={identifier}&usersig={usersig}&random={random}&contenttype=json";
		url=url.replace("{sdkappid}", SDKAppID).replace("{identifier}", adminAccount).replace("{usersig}", sign).replace("{random}", String.valueOf(new Date().getTime()));
		
		String[] accounts=new String[]{account};
		Map<String,Object> maps=new HashMap<String,Object>();
		maps.put("To_Account", accounts);
		String json=JSON.toJSONString(maps);
		LogUtil.info(json);
		String result=HttpClientUtil.doPostJson(url,json);
		LogUtil.info(result);
		return 1;
	}

	
	




	public int deleteAccount(String account) throws Exception {
		String adminAccount="administrator";
		long expire=3600 * 24 * 180 ;// 签名有效时长 例：3600 * 24 * 180
		TLSSigAPIv2 api = new TLSSigAPIv2(Long.valueOf(SDKAppID), Key);
		String sign = api.genSig(adminAccount,expire);
		String url="https://console.tim.qq.com/v4/im_open_login_svc/account_delete?sdkappid={sdkappid}&identifier={identifier}&usersig={usersig}&random={random}&contenttype=json";
		url=url.replace("{sdkappid}", SDKAppID).replace("{identifier}", adminAccount).replace("{usersig}", sign).replace("{random}", String.valueOf(new Date().getTime()));
		
		String[] accounts=account.split(",");
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(String acc:accounts){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("UserID", acc);
			list.add(map);
		}
		Map<String,Object> maps=new HashMap<String,Object>();
		maps.put("DeleteItem", list);
		String json=JSON.toJSONString(maps);
		LogUtil.info(json);
		String result=HttpClientUtil.doPostJson(url,json);
		LogUtil.info(result);
		return 1;
	}



	/**
	 * 通过管理员账号发送消息
	 */
	public String sendMsgByAdmin(String msg,String toAccount,String type)throws Exception {
		String adminAccount="administrator";
		long expire=3600 * 24 * 180 ;// 签名有效时长 例：3600 * 24 * 180
		TLSSigAPIv2 api = new TLSSigAPIv2(Long.valueOf(SDKAppID), Key);
		String sign = api.genSig(adminAccount,expire);
		String url="https://console.tim.qq.com/v4/openim/batchsendmsg?sdkappid={sdkappid}&identifier={identifier}&usersig={usersig}&random={random}&contenttype=json";
		url=url.replace("{sdkappid}", SDKAppID).replace("{identifier}", adminAccount).replace("{usersig}", sign).replace("{random}", String.valueOf(new Date().getTime()));
		
		String[] accountArray=toAccount.split(",");
		Map<String,Object> maps=new HashMap<String,Object>();
		maps.put("To_Account", accountArray);
		maps.put("SyncOtherMachine", 2);
		maps.put("MsgRandom", 19901224);
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> cmap=new HashMap<String,Object>();
		cmap.put("Text", "");
		map.put("MsgType", "TIMTextElem");
		map.put("MsgContent", cmap);
		list.add(map);
		
		maps.put("MsgBody", list);
		
		Map<String,Object> OfflinePushInfoMap=new HashMap<String,Object>();
		OfflinePushInfoMap.put("PushFlag", 0);
		OfflinePushInfoMap.put("Desc", "医生正在呼叫您,请点击进入");
		OfflinePushInfoMap.put("Ext", msg);
		Map<String,Object> childMap=new HashMap<String,Object>();
		childMap.put("Sound", "call.mp3");
		OfflinePushInfoMap.put("AndroidInfo", childMap);
		
		Map<String,Object> childMap1=new HashMap<String,Object>();
		childMap1.put("Sound", "call.mp3");
		childMap1.put("BadgeMode",0);
		childMap1.put("Title", type+"通知");
		//childMap1.put("SubTitle", "医生正在呼叫您,请点击进入");
		OfflinePushInfoMap.put("ApnsInfo", childMap1);
		
		
		maps.put("OfflinePushInfo", OfflinePushInfoMap);
		
		
		
		String json=JSON.toJSONString(maps);
		LogUtil.info(json);
		String result=HttpClientUtil.doPostJson(url,json);
		LogUtil.info(result);
		return result;
	}
	
	
	
	public String sendOnlineGroupMsgByAdmin(String groupId,String msg,String toAccount)throws Exception {
		
		String adminAccount="administrator";
		long expire=3600 * 24 * 180 ;// 签名有效时长 例：3600 * 24 * 180
		TLSSigAPIv2 api = new TLSSigAPIv2(Long.valueOf(SDKAppID), Key);
		String sign = api.genSig(adminAccount,expire);
		String url="https://console.tim.qq.com/v4/group_open_http_svc/send_group_msg?sdkappid={sdkappid}&identifier={identifier}&usersig={usersig}&random={random}&contenttype=json";
		url=url.replace("{sdkappid}", SDKAppID).replace("{identifier}", adminAccount).replace("{usersig}", sign).replace("{random}", String.valueOf(new Date().getTime()));
		
		Map<String,Object> maps=new HashMap<String,Object>();
		maps.put("GroupId", groupId);
	    List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> cmap=new HashMap<String,Object>();
		cmap.put("Text",msg);
		map.put("MsgType", "TIMTextElem");
		map.put("MsgContent", cmap);
		list.add(map);
		
		maps.put("MsgBody", list);
		String json=JSON.toJSONString(maps);
		LogUtil.info(json);
		String result=HttpClientUtil.doPostJson(url,json);
		LogUtil.info(result);
		return result;
		
	}

	
	
	public String sendOnlineGroupCustomMsgByAdmin(String groupId,String msg,String toAccount)throws Exception {
		
		String adminAccount="administrator";
		long expire=3600 * 24 * 180 ;// 签名有效时长 例：3600 * 24 * 180
		TLSSigAPIv2 api = new TLSSigAPIv2(Long.valueOf(SDKAppID), Key);
		String sign = api.genSig(adminAccount,expire);
		String url="https://console.tim.qq.com/v4/group_open_http_svc/send_group_msg?sdkappid={sdkappid}&identifier={identifier}&usersig={usersig}&random={random}&contenttype=json";
		url=url.replace("{sdkappid}", SDKAppID).replace("{identifier}", adminAccount).replace("{usersig}", sign).replace("{random}", String.valueOf(new Date().getTime()));
		
		Map<String,Object> maps=new HashMap<String,Object>();
		maps.put("GroupId", groupId);
	    List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> cmap=new HashMap<String,Object>();
		cmap.put("Data",msg);
		cmap.put("Desc","notification");
		cmap.put("Ext","url");
		cmap.put("Sound","dingdong.aiff");
		map.put("MsgType", "TIMCustomElem");
		map.put("MsgContent", cmap);
		list.add(map);
		
		maps.put("MsgBody", list);
		String json=JSON.toJSONString(maps);
		LogUtil.info(json);
		String result=HttpClientUtil.doPostJson(url,json);
		LogUtil.info(result);
		return result;
	}

	/**
	 * 通过管理员账号发送消息
	 */
	public String sendMsgByAdmin1(String msg,String toAccount,String type)throws Exception {
		String adminAccount="administrator";
		long expire=3600 * 24 * 180 ;// 签名有效时长 例：3600 * 24 * 180
		TLSSigAPIv2 api = new TLSSigAPIv2(Long.valueOf(SDKAppID), Key);
		String sign = api.genSig(adminAccount,expire);
		String url="https://console.tim.qq.com/v4/openim/batchsendmsg?sdkappid={sdkappid}&identifier={identifier}&usersig={usersig}&random={random}&contenttype=json";
		url=url.replace("{sdkappid}", SDKAppID).replace("{identifier}", adminAccount).replace("{usersig}", sign).replace("{random}", String.valueOf(new Date().getTime()));
		
		String[] accountArray=toAccount.split(",");
		Map<String,Object> maps=new HashMap<String,Object>();
		maps.put("To_Account", accountArray);
		maps.put("SyncOtherMachine", 2);
		maps.put("MsgRandom", 19901224);
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> cmap=new HashMap<String,Object>();
		cmap.put("Text", "");
		map.put("MsgType", "TIMTextElem");
		map.put("MsgContent", cmap);
		list.add(map);
		
		maps.put("MsgBody", list);
		
	    Map<String,Object> OfflinePushInfoMap=new HashMap<String,Object>();
		OfflinePushInfoMap.put("PushFlag", 0);
		OfflinePushInfoMap.put("Desc", "医生正在呼叫您,请点击进入");
		JSONObject jsonObject=new  JSONObject();
		jsonObject.put("a", "123");
		OfflinePushInfoMap.put("Ext", JSONObject.toJSONString(jsonObject));
		OfflinePushInfoMap.put("Title", "3453");
		Map<String,Object> childMap=new HashMap<String,Object>();
		childMap.put("Sound", "call.mp3");
		OfflinePushInfoMap.put("AndroidInfo", childMap);
		
	/*	Map<String,Object> childMap1=new HashMap<String,Object>();
		childMap1.put("Sound", "call.mp3");
		childMap1.put("BadgeMode",1);
		childMap1.put("Title", type+"通知");*/
		//childMap1.put("SubTitle", "医生正在呼叫您,请点击进入");
		//OfflinePushInfoMap.put("ApnsInfo", childMap1);
		
		maps.put("OfflinePushInfo", OfflinePushInfoMap);
	/*	
		Map<String,Object> messageMap=new HashMap<String,Object>();
		Map<String,Object> childMap=new HashMap<String,Object>();
		childMap.put("title", "message title");
		childMap.put("body", "message body");
		messageMap.put("notification", childMap);
		
		Map<String,Object> click_actionMap=new HashMap<String,Object>();
		click_actionMap.put("type", "1");
		click_actionMap.put("intent", "intent://com.huawei.codelabpush/deeplink?#Intent;scheme=pushscheme;launchFlags=0x4000000;S.ext=opvideo%3A%2F%2Ft%2FfYUa2im529xnplem2OmKCLeolRq5ycnKzTyJB6ZlhVUHDByZSim5OeepTGoMSYU0uAmcfKfbqaklGSfEHF5xTV6BbR3kbR6k9ZUcHHemXTTpeYoJ56l2SJZV1besPITZJOWFlkWIuWXYpeYV2Qj3qohE5qmJuXucuTe2dPdMbOvZ2qm5aWUFZ6r57ImnKnoZ56ZXqQXVpfYZCPkL%2BTZmOMjpBeuZBbXo9biL6Sj5CQZpG%2Bu02ETnB2gpnDwp17Z0%2BFoqyoe4mfX26ma9GocoxheX6QtKd%2FmWF6gXtir9SVj2WYk8W0nICDcHhxY1m%2BkaKicHOfpNTPjK6ZeYpwgdCeUYVPgqK9zKGZvptJY4mletKivp92c3qUiWOJX1NLo529z3TRdnFRknyQYr6QXYyQjb6PZZFhk5CQvIpcvY1bXWJavL5nkmCSknqGeqDLkZl3j5e9f2l7EunPP%2BniEue2SVVQn8vCoa2mnZR6lIpchE6KnqCcvcuje2eTkMTNvaiEp0meoY%2FKpnN7Z15niJGETc2fjJtzoqGhUZNPlmCrpJxbjF%2BSlnyRy6RwoniOZXqGeqDLkZl3j5e9f2l7pKRRhHzNnr2ee6Kej3qXZIVPkKTKzL2ZzE5hnaCfvdqMhU9%2FnsfHoW96ZlpbZWGEf5DJlk9pesLMn8hmVlhfWo6LYpJbXmCMiJBgh05TS4KTxcKizY6an3qUiWCRYl5hZlqKkGiQZao%3D;end");
		
		Map<String,Object> notificationMap=new HashMap<String,Object>();
		notificationMap.put("click_action", click_actionMap);
		
		Map<String,Object> androidMap=new HashMap<String,Object>();
		androidMap.put("notification", notificationMap);
		messageMap.put("android", androidMap);
		
		
		
		
		
		maps.put("OfflinePushInfo", messageMap);*/
		
		String json=JSON.toJSONString(maps);
		LogUtil.info(json);
		String result=HttpClientUtil.doPostJson(url,json);
		LogUtil.info(result);
		return result;
	}

}
