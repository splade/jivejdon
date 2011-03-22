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
package com.jdon.jivejdon.model;

import org.apache.log4j.Logger;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.model.Send;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.attachment.UploadFile;
import com.jdon.jivejdon.model.message.DigDataBag;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.model.realtime.Notification;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.model.thread.ViewCounter;

@Introduce("message")
public class DomainEvents {
	private final static Logger logger = Logger.getLogger(DomainEvents.class);

	@Send("subscriptionCounter")
	public DomainMessage computeSubscriptionNumbers(Long subscribeId) {
		return new DomainMessage(subscribeId);
	}

	@Send("subscriptionNotify")
	public DomainMessage subscriptionNotify(Subscribed subscribed) {
		return new DomainMessage(subscribed);
	}

	@Send("lobbyNotify")
	public DomainMessage notifyLobby(Notification notification) {
		return new DomainMessage(notification);
	}

	@Send("accountMessageCounter")
	public DomainMessage computeAccountMessageCount(Long userId) {
		return new DomainMessage(userId);
	}

	@Send("reloadMessageVO")
	public DomainMessage reloadMessageVO(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	@Send("addReplyMessage")
	public DomainMessage addReplyMessage(ForumMessageReply forumMessageReply) {
		ForumMessageReply newMessage = null;
		try {
			newMessage = (ForumMessageReply) forumMessageReply.clone();
			MessageVO messageVO = forumMessageReply.getMessageVOClone();
			newMessage.setMessageVO(messageVO);
		} catch (CloneNotSupportedException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return new DomainMessage(newMessage);
	}

	@Send("saveMessage")
	public DomainMessage saveMessage(ForumMessage forumMessage) {
		ForumMessage newMessage = null;
		try {
			newMessage = (ForumMessage) forumMessage.clone();
			MessageVO messageVO = forumMessage.getMessageVOClone();
			newMessage.setMessageVO(messageVO);
		} catch (CloneNotSupportedException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return new DomainMessage(newMessage);

	}

	@Send("moveMessage")
	public DomainMessage moveMessage(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	@Send("updateMessageProperties")
	public DomainMessage updateMessageProperties(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	@Send("loadMessageProperties")
	public DomainMessage loadMessageProperties(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	@Send("loadUploadEntity")
	public DomainMessage loadUploadEntity(UploadFile uploadFile) {
		return new DomainMessage(uploadFile);
	}

	@Send("loadUploadFiles")
	public DomainMessage loadUploadFiles(Long parentId) {
		return new DomainMessage(parentId);
	}

	@Send("saveUploadFiles")
	public DomainMessage saveUploadFiles(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	@Send("changeTags")
	public DomainMessage changeTags(ForumThread forumThread) {
		return new DomainMessage(forumThread);
	}

	@Send("loadTags")
	public DomainMessage loadTags(ForumThread forumThread) {
		return new DomainMessage(forumThread);
	}

	@Send("addChildZToThreadTree")
	public DomainMessage addChildZToThreadTree(ForumThread forumThread) {
		return new DomainMessage(forumThread);
	}

	@Send(value = "loadAccount", asyn = false)
	// no use
	public DomainMessage loadAccount(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	@Send(value = "loadTreeModel", asyn = false)
	// no use
	public DomainMessage loadTreeModel(ForumThread forumThread) {
		return new DomainMessage(forumThread);
	}

	@Send("addMessageDigCount")
	public DomainMessage addMessageDigCount(DigDataBag topEventVo) {
		return new DomainMessage(topEventVo);
	}

	@Send("loadMessageDigCount")
	public DomainMessage loadMessageDigCount(Long messageId) {
		return new DomainMessage(messageId);
	}

	@Send("threadViewCountManager")
	public DomainMessage refreshThreadCount(ViewCounter viewCounter) {
		return new DomainMessage(viewCounter);
	}

	@Send("shortMessageService")
	public DomainMessage loadNewShortMessageCount(Account account) {
		return new DomainMessage(account);
	}

}
