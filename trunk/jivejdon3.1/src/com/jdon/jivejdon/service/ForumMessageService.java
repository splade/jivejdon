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
package com.jdon.jivejdon.service;

import java.util.List;

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.message.output.RenderingFilterManager;
import com.jdon.jivejdon.model.query.QueryCriteria;

/**
 * Message operations interface.
 * if modify this interface, remmeber modify com.jdon.jivejdon.model.jivejdon_permission.xml
 * 
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public interface ForumMessageService {
    
    ForumMessage initMessage(EventModel em);
    
    ForumMessage initReplyMessage(EventModel em);
    
    /**
     * for display 
     * the result will be filtered by the fiilters.
     * for the batch inquiry  
     */
    ForumMessage findFilteredMessage(Long messageId);
    
    
    /**
     * Called by using message's modify or deletion 
     * at first accessing this method  must be checked. 
     * it is configured in jdonframework.xml
     * 
     * <getMethod    name="findMessage"/>
     */
    ForumMessage findMessage(Long messageId) ;
        
    /**
     * Called by using message's modify or deletion,
     * but not need check access auth, so be carefully using this method
     * now MessageRecursiveListAction.java call this method
     */
    ForumMessage getMessage(Long messageId);
    
    ForumMessage getMessageWithPropterty(Long messageId);
        
    /**
     * create a topic message, it is a root message
     * @param em
     */
    void createTopicMessage(EventModel em) throws Exception;
    
    /**
     * create a reply message.
     * @param em
     */
    void createReplyMessage(EventModel em) throws Exception;
    
    void updateMessage(EventModel em) throws Exception;
    
    void deleteMessage(EventModel em) throws Exception;
    
    void deleteRecursiveMessage(EventModel em) throws Exception;
    
    void deleteUserMessages(String username) throws Exception;
    
   
    /**
     * for batch inquiry
     */
    ForumThread getThread(Long id);
    
  
    RenderingFilterManager getFilterManager();
    
    /**
     * check if forumMessage is Authenticated by current login user.
     * @param forumMessage
     * @return
     */
    boolean isAuthenticated(ForumMessage forumMessage);
        
 
    //for /message/messageMaskAction.shtml
    void maskMessage(EventModel em)  throws Exception ;
   
    
}
