/*
 * Copyright 2003-2006 the original author or authors.
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
package com.jdon.jivejdon.repository.builder;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.manager.MessageDeletor;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.MessageRepository;
import com.jdon.jivejdon.repository.TagRepository;
import com.jdon.jivejdon.repository.UploadRepository;
import com.jdon.jivejdon.repository.dao.MessageDaoFacade;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.treepatterns.TreeNodeFactory;
import com.jdon.treepatterns.TreeNodeVisitable;
import com.jdon.treepatterns.TreeVisitor;
import com.jdon.treepatterns.model.TreeModel;

/**
 * Kernel of Message business operations
 * 
 * @author banq(http://www.jdon.com)
 * 
 */
public class MessageRepositoryDao extends ThreadRepositoryDao implements MessageRepository {
	private final static Logger logger = Logger.getLogger(MessageRepositoryDao.class);

	protected ForumFactory forumBuilder;

	private TagRepository tagRepository;

	protected UploadRepository uploadRepository;

	protected PropertyDao propertyDao;

	public MessageRepositoryDao(MessageDaoFacade messageDaoFacade, ForumFactory forumBuilder, ContainerUtil containerUtil, TagRepository tagRepository,
			UploadRepository uploadRepository, PropertyDao propertyDao) {
		super(messageDaoFacade);
		this.messageDaoFacade = messageDaoFacade;
		this.forumBuilder = forumBuilder;
		this.tagRepository = tagRepository;
		this.uploadRepository = uploadRepository;
		this.propertyDao = propertyDao;
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.MessageRepository#initMessage(com.jdon.controller.events.EventModel)
	 */
	public ForumMessage initMessage(EventModel em) {
		logger.debug(" enter service: initMessage ");
		ForumMessage forumMessage = (ForumMessage) em.getModelIF();
		try {
			if (forumMessage.getForum() == null) {
				logger.error(" no Forum in this ForumMessage");
				return forumMessage;
			}
			Long forumId = forumMessage.getForum().getForumId();
			logger.debug(" paremter forumId =" + forumId);
			Forum forum = forumBuilder.getForum(forumId);
			forumMessage.setForum(forum);
		} catch (Exception e) {
			logger.error(e);
		}
		return forumMessage;
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.MessageRepository#initReplyMessage(com.jdon.controller.events.EventModel)
	 */
	public ForumMessage initReplyMessage(EventModel em) {
		logger.debug(" enter service: initReplyMessage ");
		ForumMessageReply forumMessageReply = (ForumMessageReply) initMessage(em);
		try {
			Long pmessageId = forumMessageReply.getParentMessage().getMessageId();
			if (pmessageId == null) {
				logger.error(" no the parentMessage.messageId parameter");
				return null;
			}
			ForumMessage pMessage = forumBuilder.getMessage(pmessageId);
			forumMessageReply.setParentMessage(pMessage);
			forumMessageReply.getMessageVO().setSubject(pMessage.getMessageVO().getSubject());
		} catch (Exception e) {
			logger.error(e);
		}
		return forumMessageReply;
	}

	/*
	 * create the topic message
	 */
	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.MessageRepository#createTopicMessage(com.jdon.jivejdon.model.ForumMessage)
	 */
	public void createTopicMessage(ForumMessage forumMessage) throws Exception {
		try {
			logger.debug(" enter service: createMessage ");
			Forum forum = forumBuilder.getForum(forumMessage.getForum().getForumId());
			if (forum == null) {
				logger.error(" no this forum, forumId = " + forumMessage.getForum().getForumId());
				return;
			}
			forumMessage.setForum(forum);

			ForumThread forumThread = super.createThread(forumMessage);
			forumMessage.setForumThread(forumThread);
			messageDaoFacade.getMessageDao().createMessage(forumMessage);

			uploadRepository.saveAllUploadFiles(forumMessage.getMessageId().toString(), forumMessage.getAttachment().exportUploadFiles());

			propertyDao.updateProperties(Constants.MESSAGE, forumMessage.getMessageId(), forumMessage.exportPropertys());

			tagRepository.addTagTitle(forumThread.getThreadId(), forumMessage.getMessageVO().getTagTitle());

		} catch (Exception e) {
			String error = e + " createTopicMessage forumMessageId=" + forumMessage.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.MessageRepository#createReplyMessage(com.jdon.jivejdon.model.ForumMessageReply)
	 */
	public void createReplyMessage(ForumMessageReply forumMessageReply) throws Exception {
		try {
			logger.debug(" enter service: createReplyMessage ....");

			// create
			messageDaoFacade.getMessageDao().createMessageReply(forumMessageReply);

			uploadRepository.saveAllUploadFiles(forumMessageReply.getMessageId().toString(), forumMessageReply.getAttachment().exportUploadFiles());

			propertyDao.updateProperties(Constants.MESSAGE, forumMessageReply.getMessageId(), forumMessageReply.exportPropertys());

			super.updateThread(forumMessageReply.getForumThread());
		} catch (Exception e) {
			String error = e + " createReplyMessage forumMessageId=" + forumMessageReply.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	/*
	 * update the message, update the message's subject and body we must mark
	 * the message that has been updated. there are two kinds of parameters: the
	 * primary key /new entity data in DTO ForumMessage of the method patameter
	 */
	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.MessageRepository#updateMessage(com.jdon.jivejdon.model.ForumMessage)
	 */
	public void updateMessage(ForumMessage forumMessage) throws Exception {
		logger.debug(" enter updateMessage id =" + forumMessage.getMessageId());
		try {

			messageDaoFacade.getMessageDao().updateMessage(forumMessage);

			updateMessageProperties(forumMessage);

		} catch (Exception e) {
			String error = e + " updateMessage forumMessageId=" + forumMessage.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}

	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.MessageRepository#updateMessageProperties(com.jdon.jivejdon.model.ForumMessage)
	 */
	public void updateMessageProperties(ForumMessage forumMessage) throws Exception {
		try {
			propertyDao.deleteProperties(Constants.MESSAGE, forumMessage.getMessageId());
			propertyDao.updateProperties(Constants.MESSAGE, forumMessage.getMessageId(), forumMessage.exportPropertys());
		} catch (Exception e) {
			String error = e + " updateMessageProperties forumMessageId=" + forumMessage.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.MessageRepository#deleteMessageComposite(com.jdon.jivejdon.model.ForumMessage)
	 */
	public void deleteMessageComposite(ForumMessage delforumMessage) throws Exception {
		Long key = delforumMessage.getMessageId();
		logger.debug("deleteNode messageId =" + key);
		try {
			ForumThread forumThread = forumBuilder.getThread(delforumMessage.getForumThread().getThreadId());

			// because forumMessage can be cached, so we do need create a node
			// every time.
			TreeModel treeModel = forumThread.getState().getTreeModel();
			TreeNodeFactory TreeNodeFactory = new TreeNodeFactory(treeModel);
			TreeNodeVisitable treeNode = TreeNodeFactory.createNode(key);

			TreeVisitor messageDeletor = new MessageDeletor(this);
			logger.debug(" begin to walk into tree, and delete them");
			treeNode.accept(messageDeletor);

		} catch (Exception e) {
			String error = e + " deleteMessageComposite forumMessageId=" + delforumMessage.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.MessageRepository#deleteMessage(java.lang.Long)
	 */
	public void deleteMessage(Long messageId) throws Exception {
		try {
			messageDaoFacade.getMessageDao().deleteMessage(messageId);
			uploadRepository.deleteAllUploadFiles(messageId.toString());
			propertyDao.deleteProperties(Constants.MESSAGE, messageId);
		} catch (Exception e) {
			String error = e + " deleteMessage forumMessageId=" + messageId;
			logger.error(error);
			throw new Exception(error);
		}

	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.MessageRepository#getNextId(int)
	 */
	public synchronized Long getNextId(int idType) throws Exception {
		try {
			Long mIDInt = messageDaoFacade.getSequenceDao().getNextId(Constants.MESSAGE);
			return mIDInt;
		} catch (SQLException e) {
			logger.error(e);
			throw new Exception(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.MessageRepository#getForumBuilder()
	 */
	public ForumFactory getForumBuilder() {
		return forumBuilder;
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.MessageRepository#getMessageDaoFacade()
	 */
	public MessageDaoFacade getMessageDaoFacade() {
		return messageDaoFacade;
	}

}
