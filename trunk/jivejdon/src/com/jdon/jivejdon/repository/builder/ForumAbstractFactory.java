/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.repository.builder;

import org.apache.log4j.Logger;

import com.jdon.async.EventProcessor;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumState;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ForumThreadState;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.dao.SequenceDao;
import com.jdon.jivejdon.util.ContainerUtil;

public class ForumAbstractFactory implements ForumFactory {
	private final static Logger logger = Logger.getLogger(ForumAbstractFactory.class);

	public final ForumDirector forumDirector;
	public final MessageDirector messageDirector;
	public final ThreadDirector threadDirector;

	protected final MessageBuilder messageBuilder;
	protected final ThreadBuilder threadBuilder;
	protected final ForumBuilder forumBuilder;
	protected final ContainerUtil containerUtil;

	private final SequenceDao sequenceDao;

	// define in manager.xml
	public final EventProcessor eventProcessor;

	public ForumAbstractFactory(MessageBuilder messageBuilder, ThreadBuilder threadBuilder, ForumBuilder forumBuilder, ContainerUtil containerUtil,
			EventProcessor eventProcessor, SequenceDao sequenceDao) {
		this.containerUtil = containerUtil;
		this.messageBuilder = messageBuilder;
		this.messageBuilder.setForumAbstractFactory(this);

		this.threadBuilder = threadBuilder;
		this.threadBuilder.setForumAbstractFactory(this);

		this.forumBuilder = forumBuilder;

		this.eventProcessor = eventProcessor;
		this.sequenceDao = sequenceDao;

		this.forumDirector = new ForumDirector(this, forumBuilder);
		this.messageDirector = new MessageDirector(messageBuilder);
		this.threadDirector = new ThreadDirector(this, threadBuilder);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.ForumFactory#getForum(java.lang.
	 * Long)
	 */
	public Forum getForum(Long forumId) {
		return forumDirector.getForum(forumId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.ForumFactory#getMessageWithPropterty
	 * (java.lang.Long)
	 */
	public ForumMessage getMessageWithPropterty(Long messageId) {
		return messageDirector.getMessageWithPropterty(messageId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.ForumFactory#getMessage(java.lang
	 * .Long)
	 */
	public ForumMessage getMessage(Long messageId) {
		return messageDirector.getMessage(messageId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.ForumFactory#getThread(java.lang
	 * .Long)
	 */
	public ForumThread getThread(Long threadId) throws Exception {
		return threadDirector.getThread(threadId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.ForumFactory#reloadThreadState(com
	 * .jdon.jivejdon.model.ForumThread)
	 */
	public void reloadThreadState(ForumThread forumThread) throws Exception {
		try {
			ForumThreadState forumThreadState = new ForumThreadState(forumThread);
			forumThread.setState(forumThreadState);
			threadBuilder.buildTreeModel(forumThread);
			threadBuilder.buildState(forumThread, forumThread.getRootMessage(), messageDirector);

			ForumState forumState = new ForumState(forumThread.getForum());
			forumThread.getForum().setForumState(forumState);
			Forum forum = getForum(forumThread.getForum().getForumId());
			forumBuilder.buildState(forum, forumThread, null, messageDirector);

		} catch (Exception e) {
			String error = e + " refreshAllState forumThread=" + forumThread.getThreadId();
			logger.error(error);
			throw new Exception(error);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.builder.ForumFactory#reloadhForumState(java
	 * .lang.Long)
	 */
	public void reloadhForumState(Long forumId) throws Exception {
		Forum forum = getForum(forumId);
		ForumState forumState = new ForumState(forum);
		forum.setForumState(forumState);
		forumBuilder.buildState(forum, null, null, messageDirector);
	}

	public Long getNextId(final int idType) throws Exception {
		try {
			return sequenceDao.getNextId(idType);
		} catch (Exception e) {
			String error = e + " getNextId ";
			logger.error(error);
			throw new Exception(error);
		}

	}

}
