/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.repository.listener;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.domain.message.DomainMessage;
import com.jdon.domain.message.MessageListener;
import com.jdon.jivejdon.manager.filter.OutFilterManager;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.service.imp.message.MessageTransactionPersistence;

@Component("addReplyMessage")
public class AddReplyMessage implements MessageListener {
	private final static Logger logger = Logger.getLogger(SaveMessage.class);

	protected MessageTransactionPersistence messageTransactionPersistence;
	protected ForumFactory forumAbstractFactory;
	protected OutFilterManager outFilterManager;

	public AddReplyMessage(MessageTransactionPersistence messageTransactionPersistence, ForumFactory forumAbstractFactory,
			OutFilterManager outFilterManager) {
		super();
		this.messageTransactionPersistence = messageTransactionPersistence;
		this.forumAbstractFactory = forumAbstractFactory;
		this.outFilterManager = outFilterManager;
	}

	public void action(DomainMessage eventMessage) {
		ForumMessageReply forumMessageReply = (ForumMessageReply) eventMessage.getEventSource();
		if (forumMessageReply == null)
			return;
		try {
			messageTransactionPersistence.insertReplyMessage(forumMessageReply);
			// load the new message into cache, prepare for next GET request
			forumAbstractFactory.getMessage(forumMessageReply.getMessageId());
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
