package com.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.system.entity.pojo.ChatMessage;
import com.system.mapper.ChatMessageMapper;
import com.system.service.ChatMessageService;

@Service
public class ChatMessageServiceImpl implements ChatMessageService{

	
	 @Autowired
	  private  ChatMessageMapper chatMessageMapper;
	 /**
     * 处理环信返回的内容,写入实体
     *
     * @param content
     */
    @Override
    public void handleContent(String content) {
        JSONArray jsonArray = JSON.parseArray("[" + content + "]");
        List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
        for(int i = 0; i < jsonArray.size(); i++){
            ChatMessage chatMessage = new ChatMessage();
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            JSONArray bodyJsons = (JSONArray)((JSONObject) jsonObject.get("payload")).get("bodies");
            for(int j = 0; j < bodyJsons.size(); j ++) {
                JSONObject bodyJson = (JSONObject) bodyJsons.get(j);
                chatMessage.setId(UUID.randomUUID().toString());
                chatMessage.setMsgId(jsonObject.getString("msg_id"));
                chatMessage.setTimestamp(new Date(Long.parseLong(jsonObject.getString("timestamp"))));
                chatMessage.setToUser(jsonObject.getString("to"));
                chatMessage.setFromUser(jsonObject.getString("from"));
                chatMessage.setMsg(bodyJson.getString("msg"));
                chatMessage.setType(bodyJson.getString("type"));
                chatMessage.setUrl(bodyJson.getString("url"));
                chatMessage.setFileLength(bodyJson.getString("fileLength"));
                chatMessage.setFilename(bodyJson.getString("filename"));
                chatMessage.setSecret(bodyJson.getString("secret"));
                chatMessage.setSize(bodyJson.getString("size"));
                chatMessage.setLength(bodyJson.getString("length"));
                chatMessage.setCreateDate(new Date());
                chatMessageList.add(chatMessage);
            }
        }
        //批量插入到数据库
        chatMessageMapper.insertBatch(chatMessageList);
    }

}
