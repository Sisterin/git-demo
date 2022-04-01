package com.system.mapper;

import java.util.List;

import com.github.pagehelper.PageRowBounds;
import com.system.common.base.MyMapper;
import com.system.entity.pojo.TeChatMessage;

public interface TeChatMessageMapper extends MyMapper<TeChatMessage> {

	List<TeChatMessage> selectBySelective(TeChatMessage teChatMessage, PageRowBounds pageRowBounds);

	List<TeChatMessage> selectMessageList(TeChatMessage teChatMessage, PageRowBounds pageRowBounds);
}