package com.system.entity.vo;

public class TeResultVo {
	
	  private String  ActionStatus;
	  
	  private String  ErrorInfo;
	  
	  private Integer  ErrorCode;
	  
	  
	  

	public TeResultVo(String actionStatus, String errorInfo, Integer errorCode) {
		super();
		ActionStatus = actionStatus;
		ErrorInfo = errorInfo;
		ErrorCode = errorCode;
	}

	public String getActionStatus() {
		return ActionStatus;
	}

	public void setActionStatus(String actionStatus) {
		ActionStatus = actionStatus;
	}

	public String getErrorInfo() {
		return ErrorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		ErrorInfo = errorInfo;
	}

	public Integer getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(Integer errorCode) {
		ErrorCode = errorCode;
	}

	
	   

}
