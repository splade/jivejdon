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
package com.jdon.jivejdon.manager;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.dao.MessageDao;
import com.jdon.treepatterns.composite.Branch;
import com.jdon.treepatterns.composite.Leaf;
import com.jdon.treepatterns.visitor.RecursiveNodeWalker;
import com.jdon.util.Debug;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class MessageDeletor extends RecursiveNodeWalker {
    private final static Logger logger = Logger.getLogger(MessageDeletor.class);

    private MessageDao messageDao;

    /**
     * @param messageDao
     */
    public MessageDeletor(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void visitforLeaf(Leaf leaf) {
        Debug.logVerbose(" leaf=" + leaf.getKey());
        deleteTheData(leaf.getKey());
    }

   
    public void visitforOneBranch(Branch branch) {
        Debug.logVerbose("  Branch=" + branch.getKey());
        deleteTheData(branch.getKey());
    }

    private void deleteTheData(Long messageId) {
        try {
            messageDao.deleteMessage(messageId);
        } catch (Exception e) {
            logger.error(" deleteTheData error:" + e);
        }
    }

}
