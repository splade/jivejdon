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
package com.jdon.jivejdon.dao.filter;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.AccountDao;
import com.jdon.jivejdon.dao.PropertyDao;
import com.jdon.jivejdon.dao.UploadFileDao;
import com.jdon.jivejdon.dao.search.MessageSearchProxy;
import com.jdon.jivejdon.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.dao.util.MessagePageIteratorSolver;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.service.util.ContainerUtil;
import com.jdon.model.query.PageIteratorSolver;

/**
 * Cache decorator
 * 
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class MessageDaoDecorator extends MessageAssociationDao {
    private final static Logger logger = Logger.getLogger(MessageDaoDecorator.class);
    
    private ContainerUtil containerUtil;
    protected PageIteratorSolver pageIteratorSolver;
    protected MessageSearchProxy messageSearchProxy;

    public MessageDaoDecorator(MessagePageIteratorSolver messagePageIteratorSolver, 
            JdbcTempSource jdbcTempSource, 
            ContainerUtil containerUtil, 
            AccountDao accountDao,
            PropertyDao propertyDao,
            UploadFileDao uploadFileDao,
            MessageSearchProxy messageSearchProxy, Constants constants) {
        super(jdbcTempSource, accountDao, propertyDao, uploadFileDao, constants);
        this.containerUtil = containerUtil;
        this.pageIteratorSolver = messagePageIteratorSolver.getPageIteratorSolver();
        this.messageSearchProxy = messageSearchProxy;
    }
    
    /**
     * active the cache
     */
    public ForumMessage getMessage(Long messageId){
    	logger.debug("MessageDaoDecorator's getMessage, id=" + messageId);
        ForumMessage forumMessage = (ForumMessage) containerUtil.getModelFromCache(messageId, ForumMessage.class);
        if (forumMessage == null) {
            logger.debug("not found the forumMessage in the cache, id=" + messageId);
            forumMessage = super.getMessage(messageId);
            containerUtil.addModeltoCache(messageId, forumMessage);
        }
        return forumMessage;
    }
    
    /**
     * active the cache
     */
    public ForumThread getThread(Long threadId){
        ForumThread forumThread = (ForumThread) containerUtil.getModelFromCache(threadId, ForumThread.class);
        if (forumThread == null) {
            forumThread = super.getThread(threadId);
            containerUtil.addModeltoCache(threadId, forumThread);
        }
        return forumThread;        
    } 
        
    public void createMessage(ForumMessage  forumMessage) throws Exception{
        super.createMessage(forumMessage);//db
        messageSearchProxy.createMessage(forumMessage);//
        
        //clear forumThread cache
        containerUtil.clearCache(forumMessage.getForumThread().getThreadId());
        //refresh the batch inquiry cache
        pageIteratorSolver.clearCache(); 
    }
    
    public void createMessageReply(ForumMessageReply  forumMessageReply) throws Exception{
        super.createMessageReply(forumMessageReply);
        messageSearchProxy.createMessageReply(forumMessageReply);
        //clear forumThread cache
        containerUtil.clearCache(forumMessageReply.getForumThread().getThreadId());
        //refresh the batch inquiry cache
        pageIteratorSolver.clearCache();
    }
    
    public void updateMessage(ForumMessage  forumMessage) throws Exception{
        super.updateMessage(forumMessage);
        messageSearchProxy.updateMessage(forumMessage);
        containerUtil.clearCache(forumMessage.getMessageId());
        containerUtil.clearCache(forumMessage.getForumThread().getThreadId());
        //refresh the batch inquiry cache
        pageIteratorSolver.clearCache();
    }
    
    /**
     * if this deleted message is the last message, we must
     * refresh the forum state.
     */
    public void deleteMessage(Long  forumMessageId) throws Exception{
        ForumMessage forumMessage = getMessage(forumMessageId);
        //second  delete the message from database
        super.deleteMessage(forumMessageId);
        messageSearchProxy.deleteMessage(forumMessageId);
        
        //clear the message from the cache
        containerUtil.clearCache(forumMessageId);
        containerUtil.clearCache(forumMessage.getForumThread().getThreadId());
    }
     
    public void updateThread(ForumThread  forumThread) throws Exception{
        super.updateThread(forumThread);
        containerUtil.clearCache(forumThread.getThreadId());
        //refresh the batch inquiry cache
        pageIteratorSolver.clearCache();
    }
    
    /**
     * deleteThread always combined with deleteMessage.
     */
    public void deleteThread(Long  forumThreadId) throws Exception{
        super.deleteThread(forumThreadId);
        containerUtil.clearCache(forumThreadId);
        //refresh the batch inquiry cache
        pageIteratorSolver.clearCache();
    }
    
  
}
