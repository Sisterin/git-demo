package com.system.entity.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "chat_message")
public class ChatMessage implements Serializable {
    @Id
    private String id;

    @Column(name = "msg_id")
    private String msgId;

    private Date timestamp;

    
    @Column(name = "to_user")
    private String toUser;

    @Column(name = "from_user")
    private String fromUser;

    private String msg;

    private String type;

    private String url;
    /**
     * 文件长度
     */
    @Column(name = "file_length")
    private String fileLength;
    
    private String filename;
    
    private String secret;
    
    private String size;
    
    private String length;
    
    /**
     * 创建日期
     */
    @Column(name = "create_date")
    private Date createDate;

    private static final long serialVersionUID = 1L;

 

    /**
     * @return msg_id
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * @param msgId
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    /**
     * @return timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileLength() {
		return fileLength;
	}

	public void setFileLength(String fileLength) {
		this.fileLength = fileLength;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSecret() {
		return secret;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}


	/**
     * @return to_user
     */
    public String getToUser() {
        return toUser;
    }

    /**
     * @param toUser
     */
    public void setToUser(String toUser) {
        this.toUser = toUser == null ? null : toUser.trim();
    }

    /**
     * @return from_user
     */
    public String getFromUser() {
        return fromUser;
    }

    /**
     * @param fromUser
     */
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser == null ? null : fromUser.trim();
    }

    /**
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", msgId=").append(msgId);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", toUser=").append(toUser);
        sb.append(", fromUser=").append(fromUser);
        sb.append(", msg=").append(msg);
        sb.append(", type=").append(type);
        sb.append(", url=").append(url);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}