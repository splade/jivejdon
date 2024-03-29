package com.jdon.jivejdon.dao.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.dao.MessageDao;
import com.jdon.jivejdon.dao.util.MessagePageIteratorSolver;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.model.query.PageIteratorSolver;

public class ReBuildIndex implements Runnable {
	private final static Logger logger = Logger.getLogger(ReBuildIndex.class);
	
	private Object lock = new Object();

	private boolean busy = false;

	private MessageSearchProxy messageSearchProxy ;
	
	private MessageDao messageDao;

	protected PageIteratorSolver pageIteratorSolver;
	

	public ReBuildIndex(MessageDao messageDao, MessagePageIteratorSolver messagePageIteratorSolver) {
		messageSearchProxy = new MessageSearchProxy(true);
		this.messageDao = messageDao;
        this.pageIteratorSolver = messagePageIteratorSolver.getPageIteratorSolver();
	       
	}

	public void run() {
		synchronized (lock) {
			// If another index operation is already occuring, do nothing.
			if (busy) {
				return;
			}
			busy = true;
		}
		try {
			Date now = new Date();
			// Do a rebuild if we were told to do so, or if the index
			// has never been built before.
			rebuildIndex();
		} finally {		
			// Reset state of the search manager to idle.
			busy = false;
		}
		logger.warn("rebuildIndex finished!");
	}
	
	 private final void rebuildIndex() {
		 int start = 0;
		 int count = 30;
			boolean found = false;
	    	PageIterator pi = getAllMessages(start, count);
	    	int allCount = pi.getAllCount();
	     	while((start < allCount) && (!found)){//loop all 
	     		while(pi.hasNext()){
	    			Long messageId = (Long)pi.next();
	    			ForumMessage message = messageDao.getMessage(messageId);
	    			addMessage(message);
	    		}
	     		if (found) break;
	     		start = start + count ;
	     		logger.debug("rebuildIndex start = " + start + " count = " + count);
	    		pi = getAllMessages(start, count);     			     		
	    	}
	     	
		 
	 }
	 
	 private void addMessage(ForumMessage message){
		 try {
			messageSearchProxy.createMessage(message);
		} catch (Exception e) {
			logger.error(e);
		}
	 }
	    
	 
	  public PageIterator getAllMessages(int start, int count) {
	        String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveMessage ";

	        String GET_ALL_ITEMS = "select messageID  from jiveMessage ";
	        Collection params = new ArrayList(1);
	        return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT,
	                GET_ALL_ITEMS, params, start, count);
	    }


}
