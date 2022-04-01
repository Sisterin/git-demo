package com.system.service;

import com.system.entity.dto.PageDto;
import com.system.entity.pojo.TeChatMessage;

public interface TeChatMessageService {

	int callbackMessages(TeChatMessage teChatMessage);

	PageDto<TeChatMessage> getTeMessageList(TeChatMessage teChatMessage, PageDto<TeChatMessage> pageDto);

	public  int importAccount(String account,String nickName, String faceUrl) throws Exception;

	int deleteAccount(String account) throws Exception;

	String sendMsgByAdmin(String msg,String toAccount,String type)throws Exception;

	PageDto<TeChatMessage> getTeMessageListForSystem(TeChatMessage teChatMessage, PageDto<TeChatMessage> pageDto);

	String sendMsgByAdmin1(String msg, String toAccount, String type)throws Exception;

	int getLoginStatus(String toAccount)throws Exception;
	
	public String sendOnlineGroupMsgByAdmin(String groupId,String msg,String toAccount)throws Exception;

	public String sendOnlineGroupCustomMsgByAdmin(String groupId,String msg,String toAccount)throws Exception;
	
}
