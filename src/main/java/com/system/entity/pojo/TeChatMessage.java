package com.system.entity.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;



@Table(name = "te_chat_message")
public class TeChatMessage implements Serializable {
    @Id
    private String id;

    /**
     * 回调命令
     */
    @Column(name = "CallbackCommand")
    private String callbackcommand;

    /**
     * 群组id
     */
    @Column(name = "GroupId")
    private String groupid;

    /**
     * 群组类型
     */
    @Column(name = "Type")
    private String type;

    /**
     * 发送者
     */
    @Column(name = "From_Account")
    private String fromAccount;

    /**
     * 请求的发起者
     */
    @Column(name = "Operator_Account")
    private String operatorAccount;

    /**
     * 随机数
     */
    @Column(name = "Random")
    private Object random;

    /**
     * 消息的序列号
     */
    @Column(name = "MsgSeq")
    private Integer msgseq;

    /**
     * 消息的时间
     */
    @Column(name = "MsgTime")
    private Integer msgtime;

    /**
     * 客户端ip
     */
    @Column(name = "ClientIP")
    private String clientip;

    /**
     * 客户端平台
     */
    @Column(name = "OptPlatform")
    private String optplatform;

    /**
     * 创建应用分配的id
     */
    @Column(name = "SdkAppid")
    private String sdkappid;

    /**
     * 消息体
     */
    @Column(name = "MsgBody")
    private String msgbody;

    @Column(name = "AfterConversionMsgBody")
    private String afterconversionmsgbody;
    
    @Column(name = "CreateTime")
    private Date createtime;
    
    /**
     * 开始时间
     */
    @Transient
    private String stime;
    /**
     * 结束时间
     */
    @Transient
    private String etime;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取回调命令
     *
     * @return CallbackCommand - 回调命令
     */
    public String getCallbackcommand() {
        return callbackcommand;
    }

    /**
     * 设置回调命令
     *
     * @param callbackcommand 回调命令
     */
    public void setCallbackcommand(String callbackcommand) {
        this.callbackcommand = callbackcommand == null ? null : callbackcommand.trim();
    }

    /**
     * 获取群组id
     *
     * @return GroupId - 群组id
     */
    public String getGroupid() {
        return groupid;
    }

    /**
     * 设置群组id
     *
     * @param groupid 群组id
     */
    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    /**
     * 获取群组类型
     *
     * @return Type - 群组类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置群组类型
     *
     * @param type 群组类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取发送者
     *
     * @return From_Account - 发送者
     */
    public String getFromAccount() {
        return fromAccount;
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
     * 获取随机数
     *
     * @return Random - 随机数
     */
    public Object getRandom() {
        return random;
    }

    /**
     * 设置随机数
     *
     * @param random 随机数
     */
    public void setRandom(Object random) {
        this.random = random;
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
     * 获取创建应用分配的id
     *
     * @return SdkAppid - 创建应用分配的id
     */
    public String getSdkappid() {
        return sdkappid;
    }

    /**
     * 设置创建应用分配的id
     *
     * @param sdkappid 创建应用分配的id
     */
    public void setSdkappid(String sdkappid) {
        this.sdkappid = sdkappid == null ? null : sdkappid.trim();
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

    /**
     * @return AfterConversionMsgBody
     */
    public String getAfterconversionmsgbody() {
        return afterconversionmsgbody;
    }

    /**
     * @param afterconversionmsgbody
     */
    public void setAfterconversionmsgbody(String afterconversionmsgbody) {
        this.afterconversionmsgbody = afterconversionmsgbody == null ? null : afterconversionmsgbody.trim();
    }

    public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", callbackcommand=").append(callbackcommand);
        sb.append(", groupid=").append(groupid);
        sb.append(", type=").append(type);
        sb.append(", fromAccount=").append(fromAccount);
        sb.append(", operatorAccount=").append(operatorAccount);
        sb.append(", random=").append(random);
        sb.append(", msgseq=").append(msgseq);
        sb.append(", msgtime=").append(msgtime);
        sb.append(", clientip=").append(clientip);
        sb.append(", optplatform=").append(optplatform);
        sb.append(", sdkappid=").append(sdkappid);
        sb.append(", msgbody=").append(msgbody);
        sb.append(", afterconversionmsgbody=").append(afterconversionmsgbody);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}