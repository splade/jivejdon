package com.jdon.jivejdon.repository.builder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.repository.ThreadRepository;
import com.jdon.jivejdon.repository.dao.MessageDaoFacade;

public class ThreadRepositoryDao implements ThreadRepository {
	private final static Logger logger = Logger.getLogger(ThreadRepositoryDao.class);

	protected MessageDaoFacade messageDaoFacade;

	public ThreadRepositoryDao(MessageDaoFacade messageDaoFacade) {
		this.messageDaoFacade = messageDaoFacade;
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.ThreadRepository#createThread(com.jdon.jivejdon.model.ForumMessage)
	 */
	public ForumThread createThread(ForumMessage rootForumMessage) throws Exception {
		logger.debug(" createThread");
		ForumThread forumThread = new ForumThread(rootForumMessage);
		try {
			Long tIDInt = messageDaoFacade.getSequenceDao().getNextId(Constants.THREAD);

			forumThread.setThreadId(tIDInt);
			forumThread.setForum(rootForumMessage.getForum());
			forumThread.setName(rootForumMessage.getMessageVO().getSubject());
			messageDaoFacade.getMessageDao().createThread(forumThread);
		} catch (SQLException e) {
			logger.error(e);
			throw new Exception(e);

		}
		return forumThread;
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.ThreadRepository#updateThread(com.jdon.jivejdon.model.ForumThread)
	 */
	public void updateThread(ForumThread thread) throws Exception {
		messageDaoFacade.getMessageDao().updateThread(thread);
		// thread.setModified(true);
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.ThreadRepository#deleteThread(com.jdon.jivejdon.model.ForumThread)
	 */
	public void deleteThread(ForumThread thread) throws Exception {
		messageDaoFacade.getMessageDao().deleteThread(thread.getThreadId());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.service.ForumMessageService#getThreadsPrevNext(java
	 *      .lang.String, int)
	 */
	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.ThreadRepository#getThreadsPrevNext(java.lang.Long, java.lang.Long)
	 */
	public List getThreadsPrevNext(Long forumId, Long currentThreadId) {
		List resultObject = new ArrayList();
		if (messageDaoFacade.getMessageDao().getThreadCore(currentThreadId) == null) {// if
			// not
			// this
			// currentThreadId
			logger.error("not found this currentThreadId:" + currentThreadId);
			return resultObject;
		}
		return messageDaoFacade.getMessageQueryDao().getThreadsPrevNext(forumId, currentThreadId);
	}

}
