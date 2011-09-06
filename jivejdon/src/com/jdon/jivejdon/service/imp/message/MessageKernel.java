/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.service.imp.message;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.manager.TreeManager;
import com.jdon.jivejdon.manager.subscription.SubscribedFactory;
import com.jdon.jivejdon.manager.subscription.action.ShortMsgAction;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.model.subscription.subscribed.ThreadSubscribed;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.MessageRepository;
import com.jdon.jivejdon.repository.SubscriptionRepository;
import com.jdon.jivejdon.repository.TagRepository;
import com.jdon.jivejdon.service.ForumMessageQueryService;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Component
public class MessageKernel {
	private final static Logger logger = Logger.getLogger(MessageKernel.class);

	protected ForumMessageQueryService forumMessageQueryService;

	protected ForumFactory forumAbstractFactory;

	protected MessageTransactionPersistence messageTransactionPersistence;

	protected MessageRepository messageRepository;

	protected SubscriptionRepository subscriptionRepository;

	public MessageKernel(MessageRepository messageRepository, ForumMessageQueryService forumMessageQueryService, ForumFactory forumAbstractFactory,
			TreeManager treeManager, MessageTransactionPersistence messageTransactionPersistence, TagRepository tagRepository,
			SubscriptionRepository subscriptionRepository) {
		this.messageRepository = messageRepository;
		this.forumMessageQueryService = forumMessageQueryService;
		this.forumAbstractFactory = forumAbstractFactory;
		this.messageTransactionPersistence = messageTransactionPersistence;
		this.subscriptionRepository = subscriptionRepository;
	}

	/**
	 * get the full forum in forumMessage, and return it.
	 */
	public ForumMessage initMessage(EventModel em) {
		logger.debug("enter initMessage");
		return messageRepository.initMessage(em);
	}

	public ForumMessage initReplyMessage(EventModel em) {
		logger.debug("enter initReplyMessage");
		return messageRepository.initReplyMessage(em);
	}

	/*
	 * return a full ForumMessage need solve the relations with Forum
	 * ForumThread parentMessage
	 */
	public ForumMessage getMessage(Long messageId) {
		logger.debug("enter MessageServiceImp's getMessage");
		return forumAbstractFactory.getMessage(messageId);
	}

	public ForumMessage getMessageWithPropterty(Long messageId) {
		return forumAbstractFactory.getMessageWithPropterty(messageId);
	}

	/**
	 * return a full ForumThread one ForumThread has one rootMessage need solve
	 * the realtion with Forum rootForumMessage lastPost
	 * 
	 * @param threadId
	 * @return
	 */
	public ForumThread getThread(Long threadId) throws Exception {
		logger.debug("enter getThread");
		return forumAbstractFactory.getThread(threadId);

	}

	public Long getNextId(final int idType) throws Exception {
		try {
			return messageRepository.getNextId(idType);
		} catch (Exception e) {
			String error = e + " getNextId ";
			logger.error(error);
			throw new Exception(error);
		}

	}

	/*
	 * create the topic message
	 */
	public void addTopicMessage(EventModel em) throws Exception {
		messageTransactionPersistence.insertTopicMessage(em);

		ForumMessage forumMessage = (ForumMessage) em.getModelIF();
		boolean replynotify = forumMessage.isReplyNotify();

		forumMessage = getMessage(forumMessage.getMessageId());
		forumMessage.getForumThread().addTopicMessage(forumMessage);

		// if enable reply notify, so the author is the thread's fans
		if (replynotify) {
			createSubscription(forumMessage);
		}
	}

	private void createSubscription(ForumMessage forumMessage) {
		try {
			Subscription sub = new Subscription();
			sub.setAccount(forumMessage.getAccount());
			sub.addAction(new ShortMsgAction());
			Subscribed subscribed = SubscribedFactory.createTransient(ThreadSubscribed.TYPE, forumMessage.getForumThread().getThreadId());
			sub.setSubscribed(subscribed);
			subscriptionRepository.createSubscription(sub);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * the relation about creating reply forumMessage only need a parameter :
	 * parent message. we can get the Forum or ForumThread from the parent
	 * message. the hypelink parameter in jsp must be a paremeter: the Id of
	 * parent message.
	 * 
	 */
	public void addReplyMessage(EventModel em) throws Exception {
		try {
			ForumMessageReply forumMessageReply = (ForumMessageReply) em.getModelIF();
			ForumMessage parentMessage = getMessage(forumMessageReply.getParentMessage().getMessageId());
			if (parentMessage == null) {
				logger.error("not this parent Message: " + forumMessageReply.getParentMessage().getMessageId());
				return;
			}
			parentMessage.addReplyMessage(forumMessageReply);
			//
			// messageTransactionPersistence.insertReplyMessage(em);

			// ForumMessageReply forumMessageReply = (ForumMessageReply)
			// em.getModelIF();
			// ForumMessage forumMessageParent =
			// getMessage(forumMessageReply.getParentMessage().getMessageId());
			// forumMessageReply = (ForumMessageReply)
			// getMessage(forumMessageReply.getMessageId());
			// ensure all messages have single ForumThread object existed in
			// cache.
			// ForumThread forumThread =
			// this.getThread(forumMessageReply.getForumThread().getThreadId());
			// forumThread.addNewMessage(forumMessageParent, forumMessageReply);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}

	}

	/*
	 * update the message, update the message's subject and body we must mark
	 * the message that has been updated. there are two kinds of parameters: the
	 * primary key /new entity data in DTO ForumMessage of the method patameter
	 */
	public void updateMessage(EventModel em) throws Exception {
		logger.debug("enter updateMessage");

		ForumMessage newForumMessageInputparamter = (ForumMessage) em.getModelIF();
		try {

			ForumMessage forumMessage = this.getMessage(newForumMessageInputparamter.getMessageId());
			if (forumMessage == null)
				return;
			forumMessage.update(newForumMessageInputparamter);
			em.setModelIF(forumMessage);

			Forum newForum = this.forumAbstractFactory.getForum(newForumMessageInputparamter.getForum().getForumId());
			if (newForum == null)
				return;
			newForumMessageInputparamter.setForum(newForum);
			forumMessage.moveForum(newForumMessageInputparamter);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
		logger.debug("updateMessage ok!");
	}

	public void updateMask(ForumMessage forumMessage, boolean masked) throws Exception {
		try {
			forumMessage.updateMasked(masked);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	/*
	 * delete a message and not inlcude its childern
	 */
	public void deleteMessage(ForumMessage delforumMessage) throws Exception {
		try {
			messageTransactionPersistence.deleteMessage(delforumMessage);
			// update memory
			if (!delforumMessage.getForumThread().isRoot(delforumMessage)) {// this
				// thread
				// is be
				// deleted
				// only
				ForumThread forumThread = this.getThread(delforumMessage.getForumThread().getThreadId());
				this.forumAbstractFactory.reloadThreadState(forumThread);// refresh
			} else
				this.forumAbstractFactory.reloadhForumState(delforumMessage.getForum().getForumId());
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}

	}

	public void deleteUserMessages(String username) throws Exception {
		logger.debug("enter userMessageListDelete username=" + username);
		MultiCriteria mqc = new MultiCriteria("1970/01/01");
		mqc.setUsername(username);

		// iterate all messages
		int oneMaxSize = 100;
		PageIterator pi = forumMessageQueryService.getMessages(mqc, 0, oneMaxSize);
		int allCount = pi.getAllCount();

		int wheelCount = allCount / oneMaxSize;
		int start = 0;
		int end = 0;
		for (int i = 0; i <= wheelCount; i++) {
			end = oneMaxSize + oneMaxSize * i;
			logger.debug("start = " + start + " end = " + end);
			if (pi == null)
				pi = forumMessageQueryService.getMessages(mqc, start, end);
			messagesDelete(pi, username);
			pi = null;
			start = end;
		}
	}

	private void messagesDelete(PageIterator pi, String username) throws Exception {
		Object[] keys = pi.getKeys();
		for (int i = 0; i < keys.length; i++) {
			Long messageId = (Long) keys[i];
			logger.debug("delete messageId =" + messageId);
			ForumMessage message = getMessage(messageId);
			if (message.getAccount().getUsername().equals(username)) {
				deleteMessage(message);
			}
		}
	}

	public MessageRepository getMessageRepository() {
		return messageRepository;
	}

	public void setMessageRepository(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public ForumMessageQueryService getForumMessageQueryService() {
		return forumMessageQueryService;
	}

	public void setForumMessageQueryService(ForumMessageQueryService forumMessageQueryService) {
		this.forumMessageQueryService = forumMessageQueryService;
	}

}
