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

import java.util.Collection;

import org.apache.log4j.Logger;

import com.jdon.container.visitor.data.SessionContext;
import com.jdon.container.visitor.data.SessionContextAcceptable;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.pool.Poolable;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.auth.NoPermissionException;
import com.jdon.jivejdon.auth.ResourceAuthorization;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.message.output.RenderingFilterManager;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.service.UploadService;
import com.jdon.jivejdon.service.util.SessionContextUtil;
import com.jdon.util.UtilValidate;

/**
 * ForumMessageShell is the shell of ForumMessage core implementions.
 * 
 * invoking order:
 * ForumMessageShell --> MessageContentFilter ---> ForumMessageServiceImp
 * 
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class ForumMessageShell  implements SessionContextAcceptable, Poolable,ForumMessageService {
    private final static Logger logger = Logger.getLogger(ForumMessageShell.class);
   
    protected SessionContext sessionContext;
    protected SessionContextUtil sessionContextUtil;
    
    protected ResourceAuthorization resourceAuthorization;
    protected MessageRenderingFilter messageRenderingFilter;
    protected MessageKernel messageKernel;
    protected UploadService uploadService;
    
    
    public ForumMessageShell(SessionContextUtil sessionContextUtil,           
            ResourceAuthorization messageServiceAuth,
            MessageRenderingFilter messageRenderingFilter,
            MessageKernel messageKernel,
            UploadService uploadService) {
        this.sessionContextUtil = sessionContextUtil;
        this.resourceAuthorization = messageServiceAuth;
        this.messageRenderingFilter = messageRenderingFilter;
        this.messageKernel = messageKernel;
        this.uploadService = uploadService;
    }
    

    /**
     * find a Message for modify
     */
    public ForumMessage findMessage(Long messageId) {
    	logger.debug("enter ForumMessageShell's findMessage");
        ForumMessage forumMessage = messageKernel.getMessage(messageId);
        if (forumMessage == null)
            return null;
        
        try {
            com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);
            resourceAuthorization.verifyAuthenticated(forumMessage, account);
        } catch (NoPermissionException e) {
        	logger.error("No Permission to operate it");
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
        return forumMessage;
    }
      
    
    public boolean isAuthenticated(ForumMessage forumMessage){
    	boolean authenticated = false;
		try {
			com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);
			authenticated = resourceAuthorization.isAuthenticated(forumMessage,	account);
		} catch (Exception e) {	}
		return authenticated;
    }
    
    /**
	 * create Topic Message
	 */
    public void createTopicMessage(EventModel em) throws Exception{
        ForumMessage forumMessage = (ForumMessage)em.getModelIF();
        try {
            Long mIDInt = messageKernel.getNextId(Constants.MESSAGE);            
            forumMessage.setMessageId(mIDInt);
            
            prepareCreate(forumMessage);
            
            messageRenderingFilter.createTopicMessage(em);
            if (!UtilValidate.isEmpty(forumMessage.getBody())){
            	messageKernel.createTopicMessage(em);
            }            
        } catch (Exception e) {
            logger.error(e);
            em.setErrors(Constants.ERRORS);
        }
    }
    
    /**
     * set the login account into the domain model
     */
    public void createReplyMessage(EventModel em) throws Exception{
        ForumMessage forumMessage = (ForumMessage)em.getModelIF();
        try {
            Long mIDInt = messageKernel.getNextId(Constants.MESSAGE);
            forumMessage.setMessageId(mIDInt);
            
            prepareCreate(forumMessage);
            
            messageRenderingFilter.createReplyMessage(em);
            if(!UtilValidate.isEmpty(forumMessage.getBody())){
               messageKernel.createReplyMessage(em);            	
            }            
        } catch (Exception e) {
            logger.error(e);
            em.setErrors(Constants.ERRORS);
        }
    }        
    
    private void prepareCreate(ForumMessage forumMessage) throws Exception{
        try {
            //the poster
            com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);
            forumMessage.setAccount(account);
            forumMessage.setOperator(account);
            
            //upload
            Collection uploads = uploadService.loadNewUploadFiles(this.sessionContext);
            forumMessage.setUploadFiles(uploads);
            
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }
    
   
    /**
     *    1. auth check: amdin and the owner can modify this nessage.
     *    2. if the message has childern, only admin can update it.
     *    before business logic, we must get a true message from persistence layer,
     *    now the ForumMessage packed in EventModel object is not full, it is 
     *    a DTO from prensentation layer.
     * 
     *   
     */
    public void updateMessage(EventModel em) throws Exception{
        ForumMessage newForumMessageInputparamter = (ForumMessage)em.getModelIF();
        ForumMessage oldforumMessage = getMessage(newForumMessageInputparamter.getMessageId());
        if (oldforumMessage == null) return;
        try {
            com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);            
            resourceAuthorization.verifyAuthenticated(oldforumMessage, account);
            
            newForumMessageInputparamter.setOperator(account);
            
            Collection uploads = uploadService.loadNewUploadFiles(this.sessionContext);
            newForumMessageInputparamter.setUploadFiles(uploads);   
            
            messageRenderingFilter.updateMessage(em);//newForumMessageInputparamter in em, not forumMessage
            messageKernel.updateMessage(em);
            
        } catch (NoPermissionException e) {
            em.setErrors(e.getMessage());   
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
    /**
     * delete a message
     * the auth is same as to the updateMessage
     * 
     * 
     */
    public void deleteMessage(EventModel em) throws Exception {
		ForumMessage forumMessage = (ForumMessage) em.getModelIF();
		forumMessage = getMessage(forumMessage.getMessageId());
		if (forumMessage == null)
			return;

		try {
			com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);
			resourceAuthorization.verifyAuthenticated(forumMessage, account);

			messageKernel.deleteMessage(em);
		} catch (NoPermissionException e) {
			em.setErrors(e.getMessage());
		} catch (Exception e) {
			logger.error(e);
		}
	}
    
    /**
	 * only admin can deletedeleteRecursiveMessage, so we only setup it in jivejdon_permisiion.xml
	 */
    public void deleteRecursiveMessage(EventModel em) throws Exception{
        ForumMessage forumMessage = (ForumMessage)em.getModelIF();
        forumMessage = getMessage(forumMessage.getMessageId());
        if (forumMessage == null) return;        
        
        try {
            com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);
            resourceAuthorization.verifyAuthenticated(forumMessage, account);
            messageKernel.deleteRecursiveMessage(em);
        } catch (NoPermissionException e) {
            em.setErrors(e.getMessage());   
        } catch (Exception e) {
            logger.error(e);
        }                
    }
    
    public void deleteUserMessages(String username) throws Exception {
		try {
			com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);
			if (resourceAuthorization.isAdmin(account))
			    messageKernel.deleteUserMessages(username);
		} catch (Exception e) {
			logger.error(e);
		}
	}
    
    public ForumMessage findFilteredMessage(Long messageId) {
		return messageRenderingFilter.findFilteredMessage(messageId);
	}
    
    public RenderingFilterManager getFilterManager(){
    	return messageRenderingFilter.getFilterManager();
    }

    
    /**
     * get the full forum in forumMessage, and return it.
     */
    public ForumMessage initMessage(EventModel em) {
        return messageKernel.initMessage(em);
    }
    
    public ForumMessage initReplyMessage(EventModel em) {
        return messageKernel.initReplyMessage(em);
    }    
  
    /*
     * return a full ForumMessage need solve the relations with Forum
     * ForumThread parentMessage
     */
    public ForumMessage getMessage(Long messageId) {
       return messageKernel.getMessage(messageId);
    }
    
    public ForumMessage getMessageWithPropterty(Long messageId){
    	return messageKernel.getMessageWithPropterty(messageId);
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
    	return messageKernel.getThread(threadId);	
    	/**see SpamFilter replace below:
    	String clientIP = sessionContextUtil.getClientIP(sessionContext);
    	if (messageRenderingFilter.threadIsFiltered(threadId, clientIP)){
    		return messageKernel.getThread(threadId);	
    	}else
    		return null;**/
    }
    
    /**
     * @return Returns the sessionContext.
     */
    public SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * @param sessionContext The sessionContext to set.
     */
    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;        
    }
    
    
    
    public void maskMessage(EventModel em) throws Exception {
		logger.debug("enter maskMessage");
		ForumMessage forumMessage = (ForumMessage) em.getModelIF();
		//masked value is from messageForm  and from /message/messageMaskAction.shtml?method=maskMessage&masked=false
		boolean masked = forumMessage.isMasked();
		forumMessage = getMessage(forumMessage.getMessageId());
		if (forumMessage == null) {
			logger.error("the message don't existed: " + forumMessage.getMessageId());
			return;
		}
		forumMessage.setMasked(masked);//modify full message
		em.setModelIF(forumMessage);
		this.updateMessage(em);
	}
    
    
}
