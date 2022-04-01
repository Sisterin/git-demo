package com.system.mapper;

import java.util.List;

import com.system.common.base.MyMapper;
import com.system.entity.pojo.ChatMessage;

public interface ChatMessageMapper extends MyMapper<ChatMessage> {

	void insertBatch(List<ChatMessage> chatMessageList);
}