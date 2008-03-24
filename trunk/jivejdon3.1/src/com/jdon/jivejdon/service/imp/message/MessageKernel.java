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

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.dao.PropertyDao;
import com.jdon.jivejdon.manager.ForumThreadWalker;
import com.jdon.jivejdon.manager.TreeManager;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.repository.ForumBuilder;
import com.jdon.jivejdon.repository.MessageRepository;
import com.jdon.jivejdon.service.ForumMessageQueryService;


/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class MessageKernel {
    private final static Logger logger = Logger.getLogger(MessageKernel.class);
    
    protected MessageRepository messageRepository;   
    protected ForumMessageQueryService forumMessageQueryService;
    protected ForumBuilder forumBuilder;
    private TreeManager treeManager;
    
    public MessageKernel(MessageRepository messageRepository,
    		ForumMessageQueryService forumMessageQueryService,
    		ForumBuilder forumBuilder, TreeManager treeManager){
        this.messageRepository = messageRepository;
        this.forumMessageQueryService = forumMessageQueryService;
        this.forumBuilder = forumBuilder;
        this.treeManager = treeManager;
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
		return messageRepository.getMessage(messageId);
	}
    
    public ForumMessage getMessageWithPropterty(Long messageId) {
		return messageRepository.getMessageWithPropterty(messageId);
	}
    /**
	 * return a full ForumThread one ForumThread has one rootMessage need solve
	 * the realtion with Forum rootForumMessage lastPost
	 * 
	 * @param threadId
	 * @return
	 */
    public ForumThread getThread(Long threadId) {
    	logger.debug("enter getThread");
        return messageRepository.getThread(threadId);
      
    }
    
    public  Long getNextId(int idType) throws Exception{
    	return messageRepository.getNextId(idType);
    }
   
    /*
     * create the topic message
     */
    public void createTopicMessage(EventModel em) throws Exception{
    	logger.debug("enter createTopicMessage");
        ForumMessage forumMessage = (ForumMessage)em.getModelIF();
        messageRepository.createTopicMessage(em);
        logger.debug("createTopicMessage ok!");
        forumMessage = getMessage(forumMessage.getMessageId());
        em.setModelIF(forumMessage);//forummessage refresh notice front layer        
        forumBuilder.loadForumState(forumMessage.getForum());//refresh this forum
    }
    
    
    
   
    /**
     * the relation about creating reply forumMessage only need a parameter : parent message.
     * we can get the Forum or ForumThread from the parent message.
     * the hypelink parameter in jsp must be a paremeter: the Id of parent message.
     * 
     */
    public void createReplyMessage(EventModel em) throws Exception{  
    	logger.debug("enter createReplyMessage");
        ForumMessage forumMessage = (ForumMessage)em.getModelIF();
        messageRepository.createReplyMessage(em);
        logger.debug("createReplyMessage ok!");
        forumMessage = getMessage(forumMessage.getMessageId());
        em.setModelIF(forumMessage);//forummessage refresh notice front layer
        forumBuilder.loadThreadState(forumMessage.getForumThread());//refresh thread
        forumBuilder.loadForumState(forumMessage.getForum());//refresh forum
    }
  
    
    /* 
     * update the message, 
     * update the message's subject and body
     * we must mark the message that has been updated.
     * there are two kinds of parameters: the primary key /new entity data 
     * in DTO ForumMessage of the method patameter   
     * 
     */
    public void updateMessage(EventModel em) throws Exception{
    	logger.debug("enter updateMessage");
    	ForumMessage forumMessage = (ForumMessage)em.getModelIF();
        messageRepository.updateMessage(em);
        
        logger.debug("updateMessage ok!");       
        forumMessage = getMessage(forumMessage.getMessageId());
        em.setModelIF(forumMessage);//forummessage refresh notice front layer        
        forumBuilder.loadThreadState(forumMessage.getForumThread());//refresh thread
        forumBuilder.loadForumState(forumMessage.getForum());//refresh forum
    }
    
    /*
     * delete a message and not inlcude its childern
     */
    public void deleteMessage(EventModel em) throws Exception{   
    	logger.debug("enter deleteMessage");
    	ForumMessage delforumMessage = (ForumMessage)em.getModelIF();
        if ((delforumMessage == null) || (delforumMessage.getMessageId() == null))
              return;
        
        delforumMessage = getMessage(delforumMessage.getMessageId());
        if (delforumMessage == null){
            logger.equals("the messageId that will be deleted don't existed: "+ delforumMessage.getMessageId());
            return;
        }
        if (isLeaf(delforumMessage)){
        	messageRepository.deleteMessage(delforumMessage);	
        }
        
        logger.debug("deleteMessage ok!");
        if (!delforumMessage.isRoot()){
        	forumBuilder.loadThreadState(delforumMessage.getForumThread());//refresh thread	
        }
        forumBuilder.loadForumState(delforumMessage.getForum());//refresh forum
    }
    
    private boolean isLeaf(ForumMessage forumMessage){
        logger.debug(" it is a leaf?  id =" + forumMessage.getMessageId());
        boolean ret = false;
        try {
            ForumThread forumThread = forumMessage.getForumThread();
            ForumThreadWalker treeWalker = treeManager.getTreeWalker(forumThread);
            ret = treeWalker.isLeaf(forumMessage);
        } catch (Exception e) {
            logger.error(e);
        }
        return ret;
    }
    
    
    /**
     * delete a message and inlcude its childern
     */
    public void deleteRecursiveMessage(EventModel em) throws Exception{
    	logger.debug("enter deleteRecursiveMessage");
        ForumMessage delforumMessage = (ForumMessage)em.getModelIF();
        if ((delforumMessage == null) || (delforumMessage.getMessageId() == null))
            return;
    	delforumMessage = getMessage(delforumMessage.getMessageId());
        if (delforumMessage == null){
            logger.error("the messageId that will be deleted don't existed: "+ delforumMessage.getMessageId());
            return;
        }
        messageRepository.deleteRecursiveMessage(delforumMessage);
        logger.debug("deleteRecursiveMessage ok!");
        if (!delforumMessage.isRoot()){
        	forumBuilder.loadThreadState(delforumMessage.getForumThread());//refresh thread	
        }
        forumBuilder.loadForumState(delforumMessage.getForum());//refresh forum

    }
    
    public void deleteUserMessages(String username) throws Exception{
    	logger.debug("enter userMessageListDelete username=" + username);
    	MultiCriteria mqc = new MultiCriteria("1970/01/01");
    	mqc.setUsername(username);
    	
    	//iterate all messages
    	int oneMaxSize = 100;
    	PageIterator pi = forumMessageQueryService.getMessages(mqc, 0, oneMaxSize);
    	int allCount = pi.getAllCount();

    	int wheelCount = allCount/oneMaxSize;
        int start = 0;
        int end = 0 ;
     	for(int i= 0; i<= wheelCount; i ++){
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
				messageRepository.deleteRecursiveMessage(message);
				if (!message.isRoot()) {
					forumBuilder.loadThreadState(message.getForumThread());// refresh thread
				}
				forumBuilder.loadForumState(message.getForum());// refresh forum
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

	public ForumBuilder getForumBuilder() {
		return forumBuilder;
	}

	public void setForumBuilder(ForumBuilder forumBuilder) {
		this.forumBuilder = forumBuilder;
	}

	public TreeManager getTreeManager() {
		return treeManager;
	}

	public void setTreeManager(TreeManager treeManager) {
		this.treeManager = treeManager;
	}
    
   
}
