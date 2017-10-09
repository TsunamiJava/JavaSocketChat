package com.Lee_Chat.comon;

import java.io.Serializable;

//信息的存储管理
@SuppressWarnings("serial")
public class Message implements Serializable {

	private int MessageType;

	private String content;

	private String sender;

	private String getter;

	private String date;

	public int getMessageType() {
		return MessageType;
	}

	public void setMessageType(int messageType) {
		MessageType = messageType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}

