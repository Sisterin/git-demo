package com.system.controller.manage;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.system.common.util.ResultUtil;
import com.system.entity.dto.PageDto;
import com.system.entity.pojo.TeChatMessage;
import com.system.entity.vo.ResultVo;
import com.system.entity.vo.TeResultVo;
import com.system.service.TeChatMessageService;


/**
 * 消息回调控制器
 * @author 555
 *
 */
@Controller
public class TeChatMessageController {
	
	   @Autowired
	   TeChatMessageService teChatMessageService;
	   
	  

	/**
	 * 腾讯云回调获取聊天记录
	 * @param jsonString
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/callbackMessages")
	public TeResultVo callbackMessages(HttpServletRequest  request,@RequestBody Map<String,Object> map) throws Exception {
		
		String CallbackCommand=request.getParameter("CallbackCommand");
		String SdkAppid=request.getParameter("SdkAppid");
		String ClientIP=request.getParameter("ClientIP");
		String OptPlatform=request.getParameter("OptPlatform");
		
		String GroupId=(String) map.get("GroupId");
		String Type=(String) map.get("Type");
		String From_Account=(String) map.get("From_Account");
		String Operator_Account=(String) map.get("Operator_Account");
		
		Object Random= map.get("Random");
		Integer MsgSeq=(Integer) map.get("MsgSeq");
		Integer MsgTime=(Integer) map.get("MsgTime");
		String MsgBody=JSON.toJSONString(map.get("MsgBody"));
		
		TeChatMessage teChatMessage=new TeChatMessage();
		teChatMessage.setCallbackcommand(CallbackCommand);
		teChatMessage.setClientip(ClientIP);
		teChatMessage.setSdkappid(SdkAppid);
		teChatMessage.setOptplatform(OptPlatform);
		teChatMessage.setGroupid(GroupId);
		teChatMessage.setType(Type);
		teChatMessage.setFromAccount(From_Account);
		teChatMessage.setOperatorAccount(Operator_Account);
		teChatMessage.setRandom(Random);
		teChatMessage.setMsgseq(MsgSeq);
		teChatMessage.setMsgtime(MsgTime);
		teChatMessage.setMsgbody(MsgBody);
		
		 try{
			 int result=teChatMessageService.callbackMessages(teChatMessage);
	     	 return  new TeResultVo("OK","",0);
		    }catch(Exception e){
			e.printStackTrace();
			 return  new TeResultVo("FAIL",e.getMessage(),1);
		   }
	}
	
	

	/**
	 * 得到消息列表
	 * @param sysOrder
	 * @return 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/getTeMessageList",method = RequestMethod.GET)
	@ResponseBody
	public ResultVo getTeMessageList(TeChatMessage teChatMessage,PageDto<TeChatMessage> pageDto,HttpServletRequest request) throws Exception{
			    	 try{
			    		String token=request.getHeader("token");
			    		 if(!StringUtils.equals(token, "TMWAZ4J2hHt9YF2AjGB2do6EdGDHGLESWNFOSWHJGJFGDF")){
			    			 return  ResultUtil.fail(null,"获取数据未授权");
			    		 }
			    		 if(StringUtils.isEmpty(teChatMessage.getGroupid())){
			    			 return  ResultUtil.fail(null,"groupid不能为空");
			    		 }
			    		 pageDto =  teChatMessageService.getTeMessageList(teChatMessage, pageDto);
				     	 return  ResultUtil.success(pageDto,"成功");
					    }catch(Exception e){
						e.printStackTrace();
						return  ResultUtil.exception();
					   }
			 
	}
	
	
	
	/**
	 * 得到消息列表(对于内部系统)
	 * @param sysOrder
	 * @return 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/getTeMessageListForSystem",method = RequestMethod.GET)
	@ResponseBody
	public ResultVo getTeMessageListForSystem(TeChatMessage teChatMessage,PageDto<TeChatMessage> pageDto,HttpServletRequest request) throws Exception{
			    	 try{
			    		String token=request.getHeader("token");
			    		 if(!StringUtils.equals(token, "TMWAZ4J2hHt9YF2AjGB2do6EdGDHGLESWNFOSWHJGJFGDF")){
			    			 return  ResultUtil.fail(null,"获取数据未授权");
			    		 }
			    		 if(StringUtils.isEmpty(teChatMessage.getGroupid())){
			    			 return  ResultUtil.fail(null,"groupid不能为空");
			    		 }
			    		 pageDto =  teChatMessageService.getTeMessageListForSystem(teChatMessage, pageDto);
				     	 return  ResultUtil.success(pageDto,"成功");
					    }catch(Exception e){
						e.printStackTrace();
						return  ResultUtil.exception();
					   }
			 
	}
	
	/**
	 * 导入账号
	 * @param account
	 * @param nickName
	 * @return
	 */
	@RequestMapping(value = "/importAccount", method = {RequestMethod.POST })
	@ResponseBody
	public ResultVo importAccount(@RequestParam(required=true)String account,@RequestParam(required=true)String nickName,String faceUrl){
		try{
		   	 int result=teChatMessageService.importAccount(account, nickName,faceUrl);
			 return  ResultUtil.success(result);
		}catch(Exception e){
			e.printStackTrace();
			return  ResultUtil.exception();
		}
	}
	
	/**
	 * 删除账号
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/deleteAccount", method = {RequestMethod.GET })
	@ResponseBody
	public ResultVo deleteAccount(@RequestParam(required=true)String account){
		try{
		   	 int result=teChatMessageService.deleteAccount(account);
			 return  ResultUtil.success(result);
		}catch(Exception e){
			e.printStackTrace();
			return  ResultUtil.exception();
		}
	}
	
	
	/**
	 * 通过管理员下放消息
	 * @param account
	 * @param nickName
	 * @param faceUrl
	 * @return
	 */
	@RequestMapping(value = "/sendMsgByAdmin", method = {RequestMethod.POST })
	@ResponseBody
	public ResultVo sendMsgByAdmin(HttpServletRequest request,@RequestParam(required=true)String msg,@RequestParam(required=true)String toAccount,String type){
		try{
			String token=request.getHeader("token");
   		 if(!StringUtils.equals(token, "TMWAZ4J2hHt9YF2AjGB2do6EdGDHGLESWNFOSWHJGJFGDF")){
   			 return  ResultUtil.fail(null,"获取数据未授权");
   		 }
		   	 String result=teChatMessageService.sendMsgByAdmin(msg,toAccount,type);
			 return  ResultUtil.success(result);
		}catch(Exception e){
			e.printStackTrace();
			return  ResultUtil.exception();
		}
	}
	
	
	/**
	 * 通过管理员下放在线群组普通消息
	 * @param account
	 * @param nickName
	 * @param faceUrl
	 * @return
	 */
	@RequestMapping(value = "/sendOnlineGroupMsgByAdmin", method = {RequestMethod.POST })
	@ResponseBody
	public ResultVo sendOnlineGroupMsgByAdmin(HttpServletRequest request,@RequestParam(required=true)String groupId,@RequestParam(required=true)String msg,@RequestParam(required=true)String toAccount){
		try{
			String token=request.getHeader("token");
   		 if(!StringUtils.equals(token, "TMWAZ4J2hHt9YF2AjGB2do6EdGDHGLESWNFOSWHJGJFGDF")){
   			 return  ResultUtil.fail(null,"获取数据未授权");
   		 }
		   	 String result=teChatMessageService.sendOnlineGroupMsgByAdmin(groupId,msg,toAccount);
			 return  ResultUtil.success(result);
		}catch(Exception e){
			e.printStackTrace();
			return  ResultUtil.exception();
		}
	}
	/**
	 * 通过管理员下放在线群组自定义消息
	 * @param account
	 * @param nickName
	 * @param faceUrl
	 * @return
	 */
	@RequestMapping(value = "/sendOnlineGroupCustomMsgByAdmin", method = {RequestMethod.POST })
	@ResponseBody
	public ResultVo sendOnlineGroupCustomMsgByAdmin(HttpServletRequest request,@RequestParam(required=true)String groupId,@RequestParam(required=true)String msg,@RequestParam(required=true)String toAccount){
		try{
			String token=request.getHeader("token");
   		 if(!StringUtils.equals(token, "TMWAZ4J2hHt9YF2AjGB2do6EdGDHGLESWNFOSWHJGJFGDF")){
   			 return  ResultUtil.fail(null,"获取数据未授权");
   		 }
		   	 String result=teChatMessageService.sendOnlineGroupCustomMsgByAdmin(groupId,msg,toAccount);
			 return  ResultUtil.success(result);
		}catch(Exception e){
			e.printStackTrace();
			return  ResultUtil.exception();
		}
	}

	
	/**
	 * 通过管理员下放消息
	 * @param account
	 * @param nickName
	 * @param faceUrl
	 * @return
	 */
	@RequestMapping(value = "/sendMsgByAdmin1", method = {RequestMethod.POST })
	@ResponseBody
	public ResultVo sendMsgByAdmin1(HttpServletRequest request,@RequestParam(required=true)String msg,@RequestParam(required=true)String toAccount,String type){
		try{
			String token=request.getHeader("token");
   		 if(!StringUtils.equals(token, "TMWAZ4J2hHt9YF2AjGB2do6EdGDHGLESWNFOSWHJGJFGDF")){
   			 return  ResultUtil.fail(null,"获取数据未授权");
   		 }
		   	 String result=teChatMessageService.sendMsgByAdmin1(msg,toAccount,type);
			 return  ResultUtil.success(result);
		}catch(Exception e){
			e.printStackTrace();
			return  ResultUtil.exception();
		}
	}
}
