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
package com.jdon.jivejdon.repository;

import java.sql.SQLException;

import javax.transaction.TransactionManager;

import org.apache.log4j.Logger;

import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.MessageDaoFacade;
import com.jdon.jivejdon.manager.ForumThreadWalker;
import com.jdon.jivejdon.manager.TreeManager;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.service.util.JtaTransactionUtil;

/**
 * Kernel of Message business operations
 * 
 * @author banq(http://www.jdon.com)
 *
 */
public class MessageRepository  extends ThreadRepository{
    private final static Logger logger = Logger.getLogger(MessageRepository.class);
    
    protected MessageDaoFacade messageDaoFacade;
    protected ForumBuilder forumBuilder;
    protected TreeManager treeManager;
    protected JtaTransactionUtil jtaTransactionUtil;
    
    public MessageRepository(MessageDaoFacade messageDaoFacade,
            ForumBuilder forumBuilder, TreeManager treeManager,
            JtaTransactionUtil jtaTransactionUtil){
    	super(messageDaoFacade, forumBuilder);
        this.messageDaoFacade = messageDaoFacade;
        this.forumBuilder = forumBuilder;
        this.treeManager = treeManager;
        this.jtaTransactionUtil = jtaTransactionUtil;
    }
    
    /**
     * get the full forum in forumMessage, and return it.
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
    
    public ForumMessage initReplyMessage(EventModel em) {
        logger.debug(" enter service: initReplyMessage ");
        ForumMessageReply forumMessageReply = (ForumMessageReply)initMessage(em);
        try {
            Long pmessageId= forumMessageReply.getParentMessage().getMessageId();
            if (pmessageId == null){
                logger.error(" no the parentMessage.messageId parameter");
                return null;
            }
            ForumMessage pMessage = forumBuilder.getMessage(pmessageId, forumMessageReply.getForum());
            forumMessageReply.setParentMessage(pMessage);
            forumMessageReply.setSubject("回复:" + pMessage.getSubject());
        } catch (Exception e) {
            logger.error(e);
        }
        return forumMessageReply;
    }    
  
    public ForumMessage getMessage(Long messageId) {
       return forumBuilder.getMessage(messageId, null);
    }
    
    public ForumMessage getMessageWithPropterty(Long messageId){
       return forumBuilder.getMessageWithPropterty(messageId);
    }
    
    /*
     * create the topic message
     */
    public void createTopicMessage(EventModel em) throws Exception {
    	
        logger.debug(" enter service: createMessage ");
        ForumMessage forumMessage = (ForumMessage)em.getModelIF();
        Forum forum = forumBuilder.getForum(forumMessage.getForum().getForumId());
        if (forum == null){
            logger.error(" no this forum, forumId = " + forumMessage.getForum().getForumId());
            em.setErrors(Constants.ERRORS);
            return;            
        }
        forumMessage.setForum(forum);
        TransactionManager tx = jtaTransactionUtil.getTransactionManager();        
        try {
          	tx.begin();
            ForumThread forumThread = super.createThread(forumMessage);
            forumMessage.setForumThread(forumThread);
            messageDaoFacade.getMessageDao().createMessage(forumMessage);
            tx.commit();
        } catch (Exception e) {
            logger.error(e);
            jtaTransactionUtil.rollback(tx);
            throw new Exception(e);
        }
    }
    
    
    /**
     * the relation about creating reply forumMessage only need a parameter : parent message.
     * we can get the Forum or ForumThread from the parent message.
     * the hypelink parameter in jsp must be a paremeter: the Id of parent message.
     * 
     */
    public void createReplyMessage(EventModel em) throws Exception {    
        ForumMessage forumReplyMessage = (ForumMessage)em.getModelIF();
        logger.debug(" enter service: createReplyMessage ...." );
        ForumMessageReply forumMessage = (ForumMessageReply)forumReplyMessage;
        if ((forumMessage.getParentMessage() == null) || (forumMessage.getParentMessage().getMessageId() == null)){
            logger.error("parentMessage is null, this is not reply message!");
            em.setErrors(Constants.ERRORS);
            return;
        }
        //verify the parentMessageId existed.
        ForumMessage pforumMessage = messageDaoFacade.getMessageDao().getMessage(forumMessage.getParentMessage().getMessageId());
        if (pforumMessage == null){
            logger.error("not this parent Message: " + forumMessage.getParentMessage().getMessageId());
            em.setErrors(Constants.ERRORS);
            return;                
        }
        TransactionManager tx = jtaTransactionUtil.getTransactionManager();
        try {
         	tx.begin();
            forumMessage.setForum(pforumMessage.getForum());
            forumMessage.setForumThread(pforumMessage.getForumThread());
            
            //update thread
            forumMessage.getForumThread().setModifiedDate(forumMessage.getModifiedDate());
            super.updateThread(forumMessage.getForumThread());
                  
            //create
            messageDaoFacade.getMessageDao().createMessageReply(forumMessage);
            tx.commit();
        } catch (Exception e) {
            logger.error(e);
            jtaTransactionUtil.rollback(tx);
            throw new Exception(e);
        }
    }
  
    
    /* 
     * update the message, 
     * update the message's subject and body
     * we must mark the message that has been updated.
     * there are two kinds of parameters: the primary key /new entity data 
     * in DTO ForumMessage of the method patameter   
     * 
     */
    public void updateMessage(EventModel em) throws Exception {
        ForumMessage forumMessage = (ForumMessage)em.getModelIF();
        logger.debug(" enter updateMessage id =" + forumMessage.getMessageId());
        TransactionManager tx = jtaTransactionUtil.getTransactionManager();             
        try {            
        	//merge
        	tx.begin();
            messageDaoFacade.getMessageDao().updateMessage(forumMessage);            
            //update the forumThread's updatetime
            forumMessage.getForumThread().setModifiedDate(forumMessage.getModifiedDate());
            super.updateThread(forumMessage.getForumThread());
            //update moving a forum , when it is root message and has no reply.
            if ((forumMessage.isRoot()) && (isLeaf(forumMessage)))
            	messageDaoFacade.getMessageDao().updateMovingForum(forumMessage.getForumThread());
            
            tx.commit();
        } catch (Exception e) {
            logger.error(e);
            jtaTransactionUtil.rollback(tx);
            throw new Exception(e);
        }
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
    /*
     * delete a message and not inlcude its childern
     */
    public void deleteMessage(ForumMessage delforumMessage) throws Exception {
		TransactionManager tx = jtaTransactionUtil.getTransactionManager();
		try {
			tx.begin();
			messageDaoFacade.getMessageDao().deleteMessage(delforumMessage.getMessageId());

			ForumThread forumThread = delforumMessage.getForumThread();
			// if the root message was deleted, the thread that it be in
			// will all be deleted
			if (delforumMessage.isRoot()) {
				logger.debug("1. it is a root message, delete the forumThread");
				super.deleteThread(forumThread);
			}
			tx.commit();
		} catch (Exception e) {
			logger.error(e);
			jtaTransactionUtil.rollback(tx);
			throw new Exception(e);
		}
	}
    
    /**
	 * delete a message and inlcude its childern
	 * 
	 * @throws Exception
	 */
    public void deleteRecursiveMessage(ForumMessage delforumMessage) throws Exception{
        TransactionManager tx = jtaTransactionUtil.getTransactionManager();
        try {
            logger.debug("1. delete the node and its childern"); 
            tx.begin();
            treeManager.deleteForumMessageNode(delforumMessage);
                  
            ForumThread forumThread = delforumMessage.getForumThread();
            //if the root message was deleted, the thread that it be in
            //will all be deleted
            if (delforumMessage.isRoot()){
                logger.debug("1. it is a root message, delete the forumThread");
                super.deleteThread(forumThread);
            }
            tx.commit();
        } catch (Exception e) {
            logger.error(e);
            jtaTransactionUtil.rollback(tx);
            throw new Exception(e);
        } 

    }
   
    
    public  Long getNextId(int idType) throws Exception{
        Long mIDInt = new Long(0);
        try {
            mIDInt = messageDaoFacade.getSequenceDao().getNextId(Constants.MESSAGE);
        } catch (SQLException e) {
            logger.error(e);
            throw new Exception(e);
        }
        return mIDInt;
    }
    
  
    /**
     * @return Returns the forumBuilder.
     */
    public ForumBuilder getForumBuilder() {
        return forumBuilder;
    }
    /**
     * @return Returns the messageDaoFacade.
     */
    public MessageDaoFacade getMessageDaoFacade() {
        return messageDaoFacade;
    }
    /**
     * @return Returns the treeManager.
     */
    public TreeManager getTreeManager() {
        return treeManager;
    }
}
