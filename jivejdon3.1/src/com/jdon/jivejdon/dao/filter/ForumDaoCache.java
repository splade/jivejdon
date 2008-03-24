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
import com.jdon.jivejdon.dao.sql.ForumDaoSql;
import com.jdon.jivejdon.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.dao.util.MessagePageIteratorSolver;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.service.util.ContainerUtil;
import com.jdon.model.query.PageIteratorSolver;

/**
 * Cache Decorator
 * 
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class ForumDaoCache extends ForumDaoSql {
    private final static Logger logger = Logger.getLogger(ForumDaoCache.class);
    
    private ContainerUtil containerUtil;
    protected PageIteratorSolver pageIteratorSolver;

    public ForumDaoCache(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil,
    		MessagePageIteratorSolver messagePageIteratorSolver, Constants constants) {
        super(jdbcTempSource, containerUtil, constants);
        this.containerUtil = containerUtil;
        this.pageIteratorSolver = messagePageIteratorSolver.getPageIteratorSolver();
    }

    public Forum getForum(Long forumId) {
        Forum forum = (Forum) containerUtil.getModelFromCache(forumId, Forum.class);
        if (forum == null){
            forum = super.getForum(forumId);
            containerUtil.addModeltoCache(forumId, forum);
        }
        return forum;
    }
    
    /**
    public ForumState getForumState(Long forumId){
        ForumState forumState = (ForumState) containerUtil.getModelFromCache(forumId, ForumState.class);
        if ((forumState == null) || (forumState.isModified())){
            forumState = super.getForumState(forumId);
            containerUtil.addModeltoCache(forumId, forumState);
        }
        return forumState;
    }
     **/
 
    public void createForum(Forum forum){
        super.createForum(forum);
        forum.setModified(true);  // or directly clear the cache by containerUtil
    }
    public void updateForum(Forum forum){
        super.updateForum(forum);
        forum.setModified(true);
        
    }
    public void deleteForum(Forum forum){
        super.deleteForum(forum);
        forum.setModified(true);
    }

    public void clearCache(){
    	containerUtil.clearAllModelCache();
    	pageIteratorSolver.clearCache();
    }
    
}
