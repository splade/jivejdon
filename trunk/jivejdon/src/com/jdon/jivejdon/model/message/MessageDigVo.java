package com.jdon.jivejdon.model.message;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumMessage;

/**
 * 
 * @author oojdon
 * 
 */
public class MessageDigVo {

	private ForumMessage message;

	private volatile int number = -1;

	private DomainMessage digCountAsyncResult;

	public MessageDigVo(ForumMessage message) {
		super();
		this.message = message;
	}

	public int getDigCount() {
		if (number == -1) {
			preloadDigCount();			
		}
		return number;
	}

	public void preloadDigCount() {
		if (digCountAsyncResult == null){
			digCountAsyncResult = message.getDomainEvents().loadMessageDigCount(message.getMessageId());
			number = (Integer) digCountAsyncResult.getEventResult();
		}

	}

	public void increment() {
		if (number == -1) {
			preloadDigCount();
		}
		number = number + 1;
		message.getDomainEvents().addMessageDigCount(new DigDataBag(message.getMessageId(), number));
	}

}
