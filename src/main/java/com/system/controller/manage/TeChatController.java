package com.system.controller.manage;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.common.util.ResultUtil;
import com.system.entity.dto.chat.TLSSigAPIv2;
import com.system.entity.vo.ResultVo;

/**
 * 得到腾讯云服务器生成的签名UserSig
 *
 */
@Controller
public class TeChatController {
	
	@Value(value = "${SDKAppID}")
	private String SDKAppID;
	@Value(value = "${Key}")
	private   String Key ;
	
	
	
	/**
	 * identifier 为所要生成签名的用户id
	 * @param identifier
	 * @return
	 */
	@RequestMapping(value = "/getUserSig", method = {RequestMethod.GET })
	@ResponseBody
	public ResultVo getUserSig(@RequestParam(required=true)String identifier,HttpServletRequest request){
	
		try{
			String token=request.getHeader("userSigToken");
   		 if(!StringUtils.equals(token, "TMWAZ4J2hHt9YF2AjGB2do6EdGDHGLESWNFOSWFOAE")){
   			 return  ResultUtil.fail(null,"获取数据未授权");
   		 }
		long expire=3600 * 24 * 180 ;// 签名有效时长 例：3600 * 24 * 180
		TLSSigAPIv2 api = new TLSSigAPIv2(Long.valueOf(SDKAppID), Key);
		String sign = api.genSig(identifier,expire);
		 return  ResultUtil.success(sign,"成功");
		}catch(Exception e){
			e.printStackTrace();
			return  ResultUtil.exception();
		}
	}
	
	

	
	
	
	
	
	
}
