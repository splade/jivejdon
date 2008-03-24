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
package com.jdon.jivejdon.manager.treewalker;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.dao.MessageDao;
import com.jdon.jivejdon.manager.ForumThreadWalker;
import com.jdon.jivejdon.manager.exception.ForumMessageNotFoundException;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class ForumThreadWalkerImp implements ForumThreadWalker {
    private final static Logger logger = Logger.getLogger(ForumThreadWalkerImp.class);
    
    private ForumThread forumThread;
    private LongTreeWalker longTreeWalker;
    private MessageDao messageDao; 
    
    public ForumThreadWalkerImp(ForumThread forumThread, MessageDao messageDao){
        this.forumThread = forumThread;  
        this.longTreeWalker = new LongTreeWalker(forumThread.getTreeModel());
        this.messageDao = messageDao;
    }

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.manager.ForumThreadWalker#children(com.jdon.jivejdon.model.ForumMessage)
     */
    public Long[] children(ForumMessage forumMessage) {
        logger.debug("get children for messageId =" + forumMessage.getMessageId());
        long[] children = longTreeWalker.getChildren(forumMessage.getMessageId().longValue());    
        return  longToLong(children);
    }
    
    private Long[] longToLong(long[] inputs) {
        Long[] results = new Long[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            results[i] = new Long(inputs[i]);
        }
        return results;
    }

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.manager.ForumThreadWalker#getChild(com.jdon.jivejdon.model.ForumMessage, int)
     */
    public ForumMessage getChild(ForumMessage forumMessage, int index) throws ForumMessageNotFoundException{
        logger.debug(" enter getChild");
        long childID = longTreeWalker.getChild(forumMessage.getMessageId().longValue(), index);
        if ((childID == -1) || (childID == 0)) {
            throw new ForumMessageNotFoundException();
        }        
        return messageDao.getMessage(new Long(childID));
    }

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.manager.ForumThreadWalker#getMessageDepth(com.jdon.jivejdon.model.ForumMessage)
     */
    public int getMessageDepth(ForumMessage forumMessage) {
        logger.debug(" enter getMessageDepth");
        return longTreeWalker.getDepth(forumMessage.getMessageId().longValue());
    }


    /* (non-Javadoc)
     * @see com.jdon.jivejdon.manager.ForumThreadWalker#getIndexOfChild(com.jdon.jivejdon.model.ForumMessage, com.jdon.jivejdon.model.ForumMessage)
     */
    public int getIndexOfChild(ForumMessage parent, ForumMessage child) {
        return longTreeWalker.getIndexOfChild(parent.getMessageId().longValue(), child.getMessageId().longValue());
    }

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.manager.ForumThreadWalker#isLeaf(com.jdon.jivejdon.model.ForumMessage)
     */
    public boolean isLeaf(ForumMessage forumMessage) {
        logger.debug(" enter isLeaf");
        return longTreeWalker.isLeaf(forumMessage.getMessageId().longValue());
    }

}
