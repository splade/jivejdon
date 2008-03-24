package com.jdon.jivejdon.model.query;

import com.jdon.jivejdon.model.ForumMessage;

public class MessageSearchSpec {
	
	private ForumMessage message;
	
	private Long messageId;
	
	private String body;
	
	private int resultAllCount;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}


	public int getResultAllCount() {
		return resultAllCount;
	}

	public void setResultAllCount(int resultAllCount) {
		this.resultAllCount = resultAllCount;
	}

	public ForumMessage getMessage() {
		return message;
	}

	public void setMessage(ForumMessage message) {
		this.message = message;
	}
	
	
	
	
	

}
