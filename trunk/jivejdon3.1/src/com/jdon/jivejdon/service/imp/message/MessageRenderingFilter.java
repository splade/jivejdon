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
package com.jdon.jivejdon.service.imp.message;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.manager.filter.InFilterManager;
import com.jdon.jivejdon.manager.filter.OutFilterManager;
import com.jdon.jivejdon.manager.throttle.ThrottleManagerIF;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.output.RenderingFilterManager;
import com.jdon.jivejdon.repository.UploadRepository;

/**
 * @author banq(http://www.jdon.com)
 *
 */
public class MessageRenderingFilter {
    private final static Logger logger = Logger.getLogger(MessageRenderingFilter.class);
   
    protected InFilterManager inFilterManager;
    protected OutFilterManager outFilterManager;
    protected UploadRepository uploadRepository;
    protected MessageKernel messageKernel;
    protected ThrottleManagerIF throttleManager;
    
    public MessageRenderingFilter(InFilterManager inFilterManager, 
    		OutFilterManager outFilterManager,
            UploadRepository uploadRepository,
            MessageKernel messageKernel,
            ThrottleManagerIF throttleManager){         
         this.inFilterManager = inFilterManager;
         this.outFilterManager = outFilterManager;
         this.uploadRepository = uploadRepository;
         this.messageKernel = messageKernel;
         this.throttleManager = throttleManager;
    }
    
    
    public void createTopicMessage(EventModel em) throws Exception{
        ForumMessage forumMessage = (ForumMessage)em.getModelIF();
        createMessage(forumMessage);
    }
    
    public void createReplyMessage(EventModel em) throws Exception{
        ForumMessage forumMessage = (ForumMessage)em.getModelIF();
        createMessage(forumMessage);
    }
    
    private void createMessage(ForumMessage forumMessage) throws Exception{
    	   // throttling protection against spammers
        throttleManager.processHitFilter(forumMessage.getAccount().getPostIP());
        saveUploadFiles(forumMessage);
        inFilterManager.applyFilters(forumMessage);
    }
    
    public void updateMessage(EventModel em) throws Exception{
        ForumMessage newForumMessageInputparamter = (ForumMessage)em.getModelIF();        
        ForumMessage oldforumMessage = messageKernel.getMessage(newForumMessageInputparamter.getMessageId());
        
//      throttling protection against spammers
//      throttleManager.processHitFilter(forumMessage.getAccount().getPostIP());
        
        //replace old forumMessage with  modified new content 
        oldforumMessage.setSubject(newForumMessageInputparamter.getSubject());
        oldforumMessage.setBody(newForumMessageInputparamter.getBody());
        oldforumMessage.setUploadFiles(newForumMessageInputparamter.getUploadFiles());
        oldforumMessage.setOperator(newForumMessageInputparamter.getOperator());
        if (oldforumMessage.getForum().getForumId() != newForumMessageInputparamter.getForum().getForumId()){
        	//the message has been moved to a new forum
        	Forum newForum = messageKernel.getForumBuilder().getForum(newForumMessageInputparamter.getForum().getForumId());        	
        	oldforumMessage.setForum(newForum);
        	oldforumMessage.getForumThread().setForum(newForum);
        }
        
        //setup it modified, so now is in updating
        oldforumMessage.setModified(true);
        
        //now oldforumMessage becomes a new full forumMessage.
        em.setModelIF(oldforumMessage);         
                
        //save uploads, this must be before applyFilters.
        saveUploadFiles(oldforumMessage);
                
        //apply in filter
        inFilterManager.applyFilters(oldforumMessage);
                
    }
    
    /**
     * for display filter (replace filter)
     * apply the ForumMessageFilter to this method 
     */
    public ForumMessage findFilteredMessage(Long messageId){    
        logger.debug("MessageContentFilter.findFilteredMessage");
        ForumMessage forumMessage = messageKernel.getMessage(messageId);
        if (forumMessage.isFiltered()) return forumMessage;
        
        outFilterManager.applyFilters(forumMessage);
        forumMessage.setFiltered(true);
        return forumMessage;
    }
   
    private void saveUploadFiles(ForumMessage forumMessage) {
        Collection uploadFiles = forumMessage.getUploadFiles();
        uploadRepository.saveAllUploadFiles(forumMessage.getMessageId().toString(), uploadFiles);
    }
    
    public RenderingFilterManager getFilterManager(){
    	return outFilterManager.getRenderingFilterManager();
    }
  
}
