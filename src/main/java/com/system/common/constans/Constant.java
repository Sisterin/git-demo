package com.system.common.constans;

public class Constant {
	
	

	
	/**
	 * 统一返回状态码
	 * 
	 * @author
	 *
	 */
	public static enum RESULT_STATE {
		/**
		 * 200-成功
		 */
		OK("200"),
		/**
		 * 500-异常
		 */
		EXCEPTION("500"),
		/**
		 * 400-失败
		 */
		FAIL("400"),
		/**
		 * 没登录
		 */
		NOT_LOGIN("401"),
		/**
		 * 没权限
		 */
		NOT_AUTH("403"),
		/**
		 * 找不到资源
		 */
		ERROR_404("404"),
		/**
		 * 长时间未登录
		 */
		LOGN_TIME_NOT_LOGIN("408"),
		/**
		 * 另一个地方登录(踢出)
		 */
		ANTHER_PLACE_LOGIN("409");
		private String resultState;

		RESULT_STATE(String resultState) {
			this.resultState = resultState;
		}

		public String getResultState() {
			return resultState;
		}
	}

}
