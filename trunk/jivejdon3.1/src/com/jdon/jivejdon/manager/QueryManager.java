package com.jdon.jivejdon.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.dao.MessageQueryDao;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ForumThreadState;
import com.jdon.jivejdon.model.query.HotThreadSpecification;
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.ForumBuilder;
import com.jdon.jivejdon.service.util.ContainerUtil;


public class QueryManager {
	 private final static Logger logger = Logger.getLogger(QueryManager.class);

	protected MessageQueryDao messageQueryDao;
	
	protected ForumBuilder forumBuilder;

	protected AccountFactory accountFactory;

	protected ContainerUtil containerUtil;

	public QueryManager(MessageQueryDao messageQueryDao,
			AccountFactory accountFactory, 
			ForumBuilder forumBuilder,
			ContainerUtil containerUtil) {
		super();
		this.messageQueryDao = messageQueryDao;
		this.accountFactory = accountFactory;
		this.containerUtil = containerUtil;
		this.forumBuilder = forumBuilder;
	}

	/**
	 * get hot thread before several days, not too long
	 */
	public PageIterator getHotThreadPageKeys(QueryCriteria qc, int start,
			int count) {
		try {
			List resultSortedIDs = getHotThreadKeys(qc);
			if (resultSortedIDs.size() > 0){
				List pageIds = new ArrayList(resultSortedIDs.size());
				for (int i = start; i < start + count; i++) {
					if (i < resultSortedIDs.size()){
						pageIds.add(resultSortedIDs.get(i));	
					}else
						break;
				}			
				logger.debug("return PageIterator size=" + pageIds.size());
				return new PageIterator(resultSortedIDs.size(), pageIds.toArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PageIterator();
	}

	public  List getHotThreadKeys(QueryCriteria qc) {
		String cacheKey = qc.toString() + qc.getMessageReplyCountWindow();
		logger.debug("enter getHotThreadKeys, key=" + cacheKey);
		List resultSortedIDs = (List) containerUtil.getCacheManager().getCache().get(cacheKey);
		if (resultSortedIDs == null) {
			logger.debug("not found it in cache, create it");
			resultSortedIDs = createSortedIDs(qc);
			if (resultSortedIDs.size() > 0){
				containerUtil.getCacheManager().getCache().put(cacheKey, resultSortedIDs);
				logger.debug("resultSortedIDs.size() == " + resultSortedIDs.size());
			}else{
				logger.debug("resultSortedIDs.size() == 0");
			}
		}
		return resultSortedIDs;
	}
	
	private synchronized List createSortedIDs(QueryCriteria qc){
		List resultSortedIDs = new ArrayList();
		try {
			Collection resultIDs = messageQueryDao.getThreads(qc);
			List threads = new LinkedList();
			Iterator iter = resultIDs.iterator();
			while (iter.hasNext()) {
				Long threadId = (Long) iter.next();
				int messageCount = messageQueryDao.getMessageCount(threadId);
				if (messageCount > qc.getMessageReplyCountWindow()){//messageCount inluce root message
					//construte a empty forumthread only include messageCount;
					//we donot get a full forumThread, that will cost meemory.
					ForumThread forumThread = new ForumThread();
					forumThread.setThreadId(threadId);
					ForumThreadState forumThreadState = new ForumThreadState();
					forumThreadState.setMessageCount(messageCount);					
					forumThread.setForumThreadState(forumThreadState);
					threads.add(forumThread);			
				}
			}
			logger.debug(" found messageCount > " + qc.getMessageReplyCountWindow() + " size=" + threads.size());
			HotThreadSpecification hotspec = new HotThreadSpecification();
			//Collection only sort several threads ,not many threads 
			hotspec.sortByMessageCount(threads);
			iter =  threads.iterator();
			while(iter.hasNext()){
				ForumThread thread = (ForumThread)iter.next();
				resultSortedIDs.add(thread.getThreadId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSortedIDs;
	}

}
