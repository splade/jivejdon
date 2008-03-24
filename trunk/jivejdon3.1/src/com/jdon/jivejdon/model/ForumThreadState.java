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
package com.jdon.jivejdon.model;

import java.io.Serializable;



/**
 * ForumThread State ValueObject
 * this is embeded class in ForumThread
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class ForumThreadState implements Serializable{
    /**
     * the number of messages in the thread. This includes the root
     * message. So, to find the number of replies to the root message,
     * subtract one from the answer of this method.
     */
    private int messageCount = 0;
    
    
    private ForumMessage lastPost; 

    /**
     * @return Returns the messageCount.
     */
    public int getMessageCount() {
        return messageCount;
    }
    /**
     * @param messageCount The messageCount to set.
     */
    public void setMessageCount(int messageCount) {       
        if (messageCount >= 1)
           this.messageCount = messageCount - 1;
        else
            this.messageCount = messageCount;
    }
   
    
    
    /**
     * @return Returns the lastPost.
     */
    public ForumMessage getLastPost() {
        return lastPost;
    }
    /**
     * @param lastPost The lastPost to set.
     */
    public void setLastPost(ForumMessage lastPost) {
        this.lastPost = lastPost;
    }
}
