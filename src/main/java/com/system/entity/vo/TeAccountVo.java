package com.system.entity.vo;

import java.util.List;

public class TeAccountVo {
	
	private String ActionStatus;
	
	private int ErrorCode;
	
	private String ErrorInfo;
	
	private List<TeAccountDetailVo> ResultItem;

	public String getActionStatus() {
		return ActionStatus;
	}

	public void setActionStatus(String actionStatus) {
		ActionStatus = actionStatus;
	}

	public int getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(int errorCode) {
		ErrorCode = errorCode;
	}

	public String getErrorInfo() {
		return ErrorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		ErrorInfo = errorInfo;
	}

	public List<TeAccountDetailVo> getResultItem() {
		return ResultItem;
	}

	public void setResultItem(List<TeAccountDetailVo> resultItem) {
		ResultItem = resultItem;
	}
	

	
	
}
