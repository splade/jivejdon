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
package com.jdon.jivejdon.dao;

import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public interface MessageDao {

    ForumMessage getMessage(Long messageId);
    
    void createMessage(ForumMessage  forumMessage) throws Exception;
    
    void createMessageReply(ForumMessageReply  forumMessageReply) throws Exception;
    
    void updateMessage(ForumMessage  forumMessage) throws Exception;
   
    void deleteMessage(Long  forumMessageId) throws Exception;
    
    ForumThread getThread(Long id);
        
    void createThread(ForumThread  forumThread) throws Exception;
    
    void updateThread(ForumThread  forumThread) throws Exception;
    
    void updateMovingForum(ForumThread  forumThread) throws Exception;
    
    void deleteThread(Long  forumThreadId) throws Exception;
    
}
