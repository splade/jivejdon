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

import java.util.Collection;

import com.jdon.treepatterns.model.TreeModel;
import com.jdon.util.StringUtil;

/**
 * ForumThread = ForumTopic
 * ForumThread相当于主题Topic； 
 * 但Topic主要内容放入rootMessage中，可以说相当于所有rootMessage的主题提要，
 * 包括回复rootMessage的最后的一个回帖，包括rootMessage在内的所有帖子数等，
 * 主要服务于显示一个论坛中所有rootMessage集合。
 * ForumThread和Forum之间是N:1关系
 * 
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class ForumThread extends ModelState {
    private Long threadId;
    
    /**
     * the subject of the root message of the thread. This is a
     * convenience method equivalent to <code>getRootMessage().getSubject()</code>.
     *
     * @return the name of the thread, which is the subject of the root message.
     */
    private String name;
    private String creationDate;
    private String modifiedDate;
    private Collection propertys;
    private Forum forum;
    
    /**
     * the root message of a thread. The root message is a special
     * first message that is intimately tied to the thread for most forumViews.
     * All other messages in the thread are children of the root message.
     */
    private ForumMessage rootMessage;
    
    private ForumThreadState forumThreadState;
    
    private TreeModel treeModel ;
        
    /**
     * @return Returns the creationDate.
     */
    public String getCreationDate() {
        return creationDate;
    }
    /**
     * @param creationDate The creationDate to set.
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * @return Returns the forum.
     */
    public Forum getForum() {
        return forum;
    }
    /**
     * @param forum The forum to set.
     */
    public void setForum(Forum forum) {
        this.forum = forum;
    }
       
    /**
     * @return Returns the threadId.
     */
    public Long getThreadId() {
        return threadId;
    }
    /**
     * @param threadId The threadId to set.
     */
    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }
   
    /**
     * @return Returns the modifiedDate.
     */
    public String getModifiedDate() {
        return modifiedDate;
    }
    /**
     * @param modifiedDate The modifiedDate to set.
     */
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    
    public String getShortname(){
    	return StringUtil.shorten(name);
    }
    
    public String getShortRootMessageFilteredBody(int length) {
    	if (this.rootMessage.getFilteredBody() != null)
		   return StringUtil.shorten(this.rootMessage.getFilteredBody(), length);
    	else
    	   return "";
	}
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return Returns the propertys.
     */
    public Collection getPropertys() {
        return propertys;
    }
    /**
     * @param propertys The propertys to set.
     */
    public void setPropertys(Collection propertys) {
        this.propertys = propertys;
    }
    
    /**
     * @return Returns the rootMessage.
     */
    public ForumMessage getRootMessage() {
        return rootMessage;
    }
    /**
     * @param rootMessage The rootMessage to set.
     */
    public void setRootMessage(ForumMessage rootMessage) {
        this.rootMessage = rootMessage;
    }
    
   
    
    
    /**
     * @return Returns the forumThreadState.
     */
    public ForumThreadState getForumThreadState() {
    
        return forumThreadState;
    }
    /**
     * @param forumThreadState The forumThreadState to set.
     */
    public void setForumThreadState(ForumThreadState forumThreadState) {
        this.forumThreadState = forumThreadState;
    }
    
    
   
    /**
     * @return Returns the treeModel.
     */
    public TreeModel getTreeModel() {
        return treeModel;
    }
    /**
     * @param treeModel The treeModel to set.
     */
    public void setTreeModel(TreeModel treeModel) {
        this.treeModel = treeModel;
    }
    

  
}
