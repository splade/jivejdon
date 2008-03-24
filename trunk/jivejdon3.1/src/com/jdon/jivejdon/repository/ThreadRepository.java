package com.jdon.jivejdon.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.MessageDaoFacade;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;

public class ThreadRepository {
	private final static Logger logger = Logger
			.getLogger(ThreadRepository.class);

	protected MessageDaoFacade messageDaoFacade;

	protected ForumBuilder forumBuilder;

	public ThreadRepository(MessageDaoFacade messageDaoFacade,
			ForumBuilder forumBuilder) {
		this.messageDaoFacade = messageDaoFacade;
		this.forumBuilder = forumBuilder;
	}

	/**
	 * return a full ForumThread
	 * one ForumThread has one rootMessage
	 * need solve the realtion with Forum rootForumMessage lastPost
	 * 
	 * @param threadId
	 * @return
	 */
	public ForumThread getThread(Long threadId) {
		return forumBuilder.getThread(threadId, null);

	}

	/**
	 * create a new Thread, this is for topic message
	 * @param rootForumMessage
	 * @return
	 * @throws Exception
	 */
	public ForumThread createThread(ForumMessage rootForumMessage)
			throws Exception {
		logger.debug(" createThread");
		ForumThread forumThread = new ForumThread();
		try {
			Long tIDInt = messageDaoFacade.getSequenceDao().getNextId(
					Constants.THREAD);

			forumThread.setThreadId(tIDInt);
			forumThread.setForum(rootForumMessage.getForum());
			forumThread.setModifiedDate(rootForumMessage.getModifiedDate());
			forumThread.setName(rootForumMessage.getSubject());
			forumThread.setRootMessage(rootForumMessage);
			messageDaoFacade.getMessageDao().createThread(forumThread);
		} catch (SQLException e) {
			logger.error(e);
			throw new Exception(e);

		}
		return forumThread;
	}

	public void updateThread(ForumThread thread) throws Exception {
		messageDaoFacade.getMessageDao().updateThread(thread);
		//thread.setModified(true);		
	}
	
	public void updateMovingForum(ForumThread thread) throws Exception {
		messageDaoFacade.getMessageDao().updateMovingForum(thread);
		//thread.setModified(true);		
	}

	public void deleteThread(ForumThread thread) throws Exception {
		messageDaoFacade.getMessageDao().deleteThread(thread.getThreadId());
	}

	
	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.service.ForumMessageService#getThreadsPrevNext(java.lang.String, int)
	 */
	public List getThreadsPrevNext(Long forumId, Long currentThreadId) {
		List resultObject = new ArrayList();
		if (messageDaoFacade.getMessageDao().getThread(currentThreadId) == null) {// if not this currentThreadId
			logger.error("not found this currentThreadId:" + currentThreadId);
			return resultObject;
		}
		return messageDaoFacade.getMessageQueryDao().getThreadsPrevNext(
				forumId, currentThreadId);
	}

}
