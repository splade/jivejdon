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

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.treepatterns.TreeNodeFactory;
import com.jdon.treepatterns.TreeNodeVisitable;
import com.jdon.treepatterns.TreeVisitor;
import com.jdon.treepatterns.model.TreeModel;
import com.jdon.treepatterns.visitor.TreeNodePicker;


/**
 * by Composite + Visitor for the tree node, we can accomplish 
 * variable operations on any node of the tree. 
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 *
 */
public class TreeManager implements Serializable{
    private final static Logger logger = Logger.getLogger(TreeManager.class);
   
    /**
     * @param messageDao
     */
    public TreeManager() {
    }
    
    public List getRecursiveChildren(ForumMessage forumMessage){        
        Long key = forumMessage.getMessageId();
        logger.debug("recursiveChildren messageId =" + key);
        List list = null;
        try {
            // because forumMessage can be cached, so we do need create a node every time.        
            TreeModel treeModel = forumMessage.getForumThread().getState().getTreeModel();
            TreeNodeFactory TreeNodeFactory = new TreeNodeFactory(treeModel); 
            TreeNodeVisitable treeNode = TreeNodeFactory.createNode(key);        
            
            TreeVisitor messagePicker = new TreeNodePicker();
            logger.debug(" begin to walk into tree, and pick them into a list" );
            treeNode.accept(messagePicker);        
            list = ((TreeNodePicker)messagePicker).getResult();
            list.remove(key); //remove the parent

        } catch (Exception e) {
            logger.error(e);
        }        
        return list;
        
    }
    

}
