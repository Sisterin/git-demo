package com.system.entity.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TeChatMessageVo implements Serializable {

	 /**
     * 就诊id
     */
    private String groupid;

    /**
     * 发送者
     */
    private String fromAccount;

    /**
     * 请求的发起者
     */
    private String operatorAccount;


    /**
     * 消息的序列号
     */
    private Integer msgseq;

    /**
     * 消息的时间
     */
    private Integer msgtime;

    /**
     * 客户端ip
     */
    private String clientip;

    /**
     * 客户端平台
     */
    private String optplatform;



    /**
     * 消息体
     */
    private String msgbody;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
 	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private static final long serialVersionUID = 1L;

    



    /**
     * 获取发送者
     *
     * @return From_Account - 发送者
     */
    public String getFromAccount() {
        return fromAccount;
    }







	public String getGroupid() {
		return groupid;
	}







	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}







	public Date getCreatetime() {
		return createtime;
	}







	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}







	/**
     * 设置发送者
     *
     * @param fromAccount 发送者
     */
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount == null ? null : fromAccount.trim();
    }

    /**
     * 获取请求的发起者
     *
     * @return Operator_Account - 请求的发起者
     */
    public String getOperatorAccount() {
        return operatorAccount;
    }

    /**
     * 设置请求的发起者
     *
     * @param operatorAccount 请求的发起者
     */
    public void setOperatorAccount(String operatorAccount) {
        this.operatorAccount = operatorAccount == null ? null : operatorAccount.trim();
    }

    /**
     * 获取消息的序列号
     *
     * @return MsgSeq - 消息的序列号
     */
    public Integer getMsgseq() {
        return msgseq;
    }

    /**
     * 设置消息的序列号
     *
     * @param msgseq 消息的序列号
     */
    public void setMsgseq(Integer msgseq) {
        this.msgseq = msgseq;
    }

    /**
     * 获取消息的时间
     *
     * @return MsgTime - 消息的时间
     */
    public Integer getMsgtime() {
        return msgtime;
    }

    /**
     * 设置消息的时间
     *
     * @param msgtime 消息的时间
     */
    public void setMsgtime(Integer msgtime) {
        this.msgtime = msgtime;
    }

    /**
     * 获取客户端ip
     *
     * @return ClientIP - 客户端ip
     */
    public String getClientip() {
        return clientip;
    }

    /**
     * 设置客户端ip
     *
     * @param clientip 客户端ip
     */
    public void setClientip(String clientip) {
        this.clientip = clientip == null ? null : clientip.trim();
    }

    /**
     * 获取客户端平台
     *
     * @return OptPlatform - 客户端平台
     */
    public String getOptplatform() {
        return optplatform;
    }

    /**
     * 设置客户端平台
     *
     * @param optplatform 客户端平台
     */
    public void setOptplatform(String optplatform) {
        this.optplatform = optplatform == null ? null : optplatform.trim();
    }

    /**
     * 获取消息体
     *
     * @return MsgBody - 消息体
     */
    public String getMsgbody() {
        return msgbody;
    }

    /**
     * 设置消息体
     *
     * @param msgbody 消息体
     */
    public void setMsgbody(String msgbody) {
        this.msgbody = msgbody == null ? null : msgbody.trim();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fromAccount=").append(fromAccount);
        sb.append(", operatorAccount=").append(operatorAccount);
        sb.append(", msgseq=").append(msgseq);
        sb.append(", msgtime=").append(msgtime);
        sb.append(", clientip=").append(clientip);
        sb.append(", optplatform=").append(optplatform);
        sb.append(", msgbody=").append(msgbody);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}