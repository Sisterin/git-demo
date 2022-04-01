package com.system.common.util;

import com.system.common.constans.Constant;
import com.system.entity.vo.ResultVo;

public class ResultUtil {
	
	
	
	/**
	 * 成功
	 * @return
	 */
	public static ResultVo success(Object ...object){
		if(object.length>=2){
			return new ResultVo(Constant.RESULT_STATE.OK.getResultState(), object[0],object[1]!=null?object[1].toString():null);
		}else if(object.length==1){
			return new ResultVo(Constant.RESULT_STATE.OK.getResultState(), object[0]);
		}else{
			return new ResultVo(Constant.RESULT_STATE.OK.getResultState(), null);
		}
	}
	
	/**
	 * 失败
	 * @return
	 */
	public static ResultVo fail(Object ...object){
		if(object.length>=2){
			return new ResultVo(Constant.RESULT_STATE.FAIL.getResultState(), object[0],object[1]!=null?object[1].toString():null);
		}else if(object.length==1){
			return new ResultVo(Constant.RESULT_STATE.FAIL.getResultState(), object[0]);
		}else{
			return new ResultVo(Constant.RESULT_STATE.FAIL.getResultState(), "返回的结果，传入参数错误");
		}
	}
	
	
	/**
	 * 异常
	 * @return
	 */
	public static ResultVo exception(Object ...object){
		if(object.length>=2){
			return new ResultVo(Constant.RESULT_STATE.EXCEPTION.getResultState(), object[0],object[1]!=null?object[1].toString():null);
		}else if(object.length==1){
			return new ResultVo(Constant.RESULT_STATE.EXCEPTION.getResultState(), object);
		}else{
			return new ResultVo(Constant.RESULT_STATE.FAIL.getResultState(), "返回的结果，传入参数错误");
		}
	}
	

}
