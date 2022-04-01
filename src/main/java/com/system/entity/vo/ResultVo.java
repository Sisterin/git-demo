package com.system.entity.vo;

public class ResultVo {
	
	
	private String resultCode;
	
	private String resultMsg;
	
	private Object data;
	

	public ResultVo(String resultCode, Object data) {
		super();
		this.resultCode = resultCode;
		this.data = data;
	}
	public ResultVo(String resultCode, Object data, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.data = data;
		this.resultMsg = resultMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	
	
	
	

}
