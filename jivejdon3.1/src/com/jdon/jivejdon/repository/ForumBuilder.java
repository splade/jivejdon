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
package com.jdon.jivejdon.repository;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.ForumDao;
import com.jdon.jivejdon.dao.MessageDao;
import com.jdon.jivejdon.dao.MessageQueryDao;
import com.jdon.jivejdon.dao.PropertyDao;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumState;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ForumThreadState;
import com.jdon.jivejdon.service.imp.ForumServiceImp;

/**
 * Build a full Forum or a ForumMessage or a ForumThread.
 * Embed a Model into a Model 
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 *
 */
public class ForumBuilder {
    private final static Logger logger = Logger.getLogger(ForumBuilder.class);
    
    private ForumDao forumDao;
    private MessageDao messageDao;
    private MessageQueryDao messageQueryDao;
    private AccountFactory accountFactory;
    private PropertyDao propertyDao;
    private ForumPropertyFactory forumPropertyFactory;
    
    /**
     * @param forumDao
     * @param messageDao
     * @param accountDao
     * @param messageQueryDao
     */
    public ForumBuilder(ForumDao forumDao, MessageDao messageDao, 
    		AccountFactory accountFactory, MessageQueryDao messageQueryDao,
    		PropertyDao propertyDao, ForumPropertyFactory forumPropertyFactory) {
        this.forumDao = forumDao;
        this.messageDao = messageDao;
        this.accountFactory = accountFactory;
        this.messageQueryDao = messageQueryDao;
        this.propertyDao = propertyDao;
        this.forumPropertyFactory = forumPropertyFactory;
    }
    
    
    public Forum getForum(Long forumId){
        logger.debug(" enter getForum for forumId=" + forumId);
        if (forumId == null) return null;
        Forum forum = forumDao.getForum(forumId);
        if (forum.isEmbedded()) 
            return forum;
         
        loadForumState(forum);
        forum.setEmbedded(true);
        return forum;
    }
    
    public ForumMessage getMessage(Long messageId){
    	return getMessage(messageId, null);
    }
    
    public ForumMessage getMessageWithPropterty(Long messageId){
    	ForumMessage message = getMessage(messageId);    	
    	Collection propterties = propertyDao.getProperties(Constants.MESSAGE, messageId);
    	message.setPropertys(propterties);
    	return message;
    }
    
    /*
     * return a full ForumMessage need solve the relations with Forum
     * ForumThread parentMessage
     */
    public ForumMessage getMessage(Long messageId, Forum forum) {
        logger.debug(" enter get a full Message for id=" + messageId);
        ForumMessage forumMessage = null;
		try {
			forumMessage = messageDao.getMessage(messageId);
			if (forumMessage == null){
			    logger.error("no this message in database id=" + messageId);
			    return null;
			}             
			if(forumMessage.isEmbedded()) return forumMessage;
			
			logger.debug("Embed Message ---->  start id=" + messageId);        
			if (forum == null)
			    forum = getForum(forumMessage.getForum().getForumId());
			forumMessage.setForum(forum);
			
			logger.debug("Embed Message ---->  embed thread start");
			Long threadId = forumMessage.getForumThread().getThreadId();
			ForumThread forumThread = getThread(threadId, forum);         
			forumMessage.setForumThread(forumThread);
			logger.debug("Embed Message ---->  embed thread end");
     
			embedAccount(forumMessage);
			
			forumMessage.setHotKeys(forumPropertyFactory.getHotKeys());
			
			forumMessage.setEmbedded(true);			
			logger.debug("Embed Message ---->  end id=" + messageId);
		} catch (Exception e) {
			logger.error(e);
		}
        return forumMessage;
    }
    
    private void  embedAccount(ForumMessage forumMessage) {
        logger.debug(" embed getAccount ");
        com.jdon.jivejdon.model.Account account = accountFactory.getFullAccount(forumMessage.getAccount());
        if (!account.isAnonymous()){
            //get messageCount of the account;
            int count = messageQueryDao.getMessageCountOfUser(account.getUserIdLong());
            account.setMessageCount(count);
        }
        logger.debug("  got the Account");
        forumMessage.setAccount(account);
    }    
    
    
    private ForumMessage getLastPostMessage(Long messageId, ForumThread forumThread) {
        logger.debug(" enter get getLastPostMessage for id=" + messageId + " for threadId=" + forumThread.getThreadId());
        ForumMessage forumMessage = null;
		try {
			if (forumThread == null) {
			    return null;
			}
			forumMessage = messageDao.getMessage(messageId);        
			if(forumMessage.isEmbedded()) return forumMessage;
			
			forumMessage.setForum(forumThread.getForum());
			forumMessage.setForumThread(forumThread);
     
			embedAccount(forumMessage);
			logger.debug("getLastPostMessage  ---->  end id=" + messageId);
		} catch (Exception e) {
			logger.error(e);
		}
        return forumMessage;
    }
  
    public ForumThread getThread(Long threadId){
    	return getThread(threadId, null);
    }
    
    /**
     * return a full ForumThread
     * one ForumThread has one rootMessage
     * need solve the realtion with Forum rootForumMessage lastPost
     * 
     * @param threadId
     * @return
     */
    public ForumThread getThread(Long threadId, Forum forum) {
        logger.debug("TH----> enter getThread, threadId=" + threadId);
        ForumThread forumThread = null;
		try {
			forumThread = messageDao.getThread(threadId);
			if (forumThread == null) {
			    logger.error("no this forumThread in database threadId=" + threadId);
			    return null;
			}
			if (forumThread.isEmbedded())
			    return forumThread;
			
			logger.debug("<Embed ForumThread---->  start, threadId=" + threadId);
			if (forum == null)
			    forum = getForum(forumThread.getForum().getForumId());
			
			forumThread.setForum(forum);
			
			//2.embed Root ForumMessage
			logger.debug("<Embed ForumThread----> embed the root forummessage start, threadId=" + threadId );
			Long rootmessageId = forumThread.getRootMessage().getMessageId();
			ForumMessage rootforumMessage = messageDao.getMessage(rootmessageId);
			if (rootforumMessage == null){
				logger.error("not found the root message, transaction error! , messageId=" + rootmessageId);
				return null;
			}
			rootforumMessage.setForum(forum);
			rootforumMessage.setForumThread(forumThread);
			forumThread.setRootMessage(rootforumMessage);
			embedAccount(rootforumMessage);
			logger.debug("<Embed ForumThread----> embed the root forummessage end, threadId=" + threadId);
			
			forumThread.setName(rootforumMessage.getSubject());
			
			loadThreadState(forumThread);
			forumThread.setEmbedded(true);
			logger.debug("<Embed ForumThread---->  end, threadId=" + threadId);
		} catch (Exception e) {
			logger.error(e);
		}
        
        return forumThread;
        
    }
   
    /**
     *   get a ForumState, but the lastPost message onely has a messageId, other
	 * fields are null, after we get this forumState, we can get all other
	 * fields from cache.
	 *      * @param forum
     */
    
    public void loadForumState(Forum forum) {
		try {
			logger.debug(" loadForumState for forumId=" + forum.getForumId());
			ForumState forumState = new ForumState();
			forum.setForumState(forumState);

			Long lastMessageId = forumDao.getLastPostMessageId(forum.getForumId());
			if (lastMessageId != null) {				
				ForumMessage forumMessage = getMessage(lastMessageId, forum);
				forumState.setLastPost(forumMessage);
				forumState.setMessageCount(forumDao.getMessageCount(forum.getForumId()));
				forumState.setThreadCount(forumDao.getThreadCount(forum.getForumId()));
			} else {
				logger.warn("maybe first running, not found lastMessageId for forumId: " + forum.getForumId());
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}   

    /**
     * get a state of a thread
     * @param forumThread
     */
    public void loadThreadState(ForumThread forumThread) {
		try {
			logger.debug(" loadThreadState for forumThread=" + forumThread.getThreadId());
			ForumThreadState forumThreadState = new ForumThreadState();
			forumThread.setForumThreadState(forumThreadState);

			Long lastMessageId = messageQueryDao.getLastPostMessageId(forumThread.getForum().getForumId(), forumThread.getThreadId());
			if (lastMessageId != null) {
				ForumMessage lstForumMessage = getLastPostMessage(lastMessageId, forumThread);
				forumThreadState.setLastPost(lstForumMessage);
				forumThreadState.setMessageCount(messageQueryDao.getMessageCount(forumThread.getThreadId()));
			} else {
				logger.warn("maybe first running, not found lastMessageId for forumId: " + forumThread.getForum().getForumId());
			}
			// clear the TreeModel
			forumThread.setTreeModel(null);
		} catch (Exception e) {
			logger.error(e);
		}
	}

    /**
	 * @return Returns the forumDao.
	 */
    public ForumDao getForumDao() {
        return forumDao;
    }
    /**
     * @return Returns the messageDao.
     */
    public MessageDao getMessageDao() {
        return messageDao;
    }
    /**
     * @return Returns the messageQueryDao.
     */
    public MessageQueryDao getMessageQueryDao() {
        return messageQueryDao;
    }
    
   
}
