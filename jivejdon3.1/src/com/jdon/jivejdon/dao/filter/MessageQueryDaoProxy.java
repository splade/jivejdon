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
package com.jdon.jivejdon.dao.filter;

import java.util.Collection;

import com.jdon.jivejdon.dao.AccountDao;
import com.jdon.jivejdon.dao.search.MessageSearchProxy;
import com.jdon.jivejdon.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.dao.sql.MessageQueryDaoSql;
import com.jdon.jivejdon.dao.util.MessagePageIteratorSolver;
import com.jdon.jivejdon.service.util.ContainerUtil;

/**
 * @author banq(http://www.jdon.com)
 *
 */
public class MessageQueryDaoProxy extends MessageQueryDaoSql{
    
    private ContainerUtil containerUtil;
    private AccountDao accountDao;
    private MessageSearchProxy messageSearchProxy;
    
    public MessageQueryDaoProxy(ContainerUtil containerUtil, JdbcTempSource jdbcTempSource, 
    		MessagePageIteratorSolver messagePageIteratorSolver,
    		AccountDao accountDao, MessageSearchProxy messageSearchProxy) {
        super(jdbcTempSource,messagePageIteratorSolver);
        this.containerUtil = containerUtil;
        this.accountDao = accountDao;
        this.messageSearchProxy = messageSearchProxy;
    }
    
    /**

    public ForumThreadState getForumThreadState(ForumThread forumThread){
        ForumThreadState forumThreadState = (ForumThreadState) containerUtil.getModelFromCache(forumThread.getThreadId(), ForumThreadState.class);
        if ((forumThreadState == null) || (forumThreadState.isModified())){
            forumThreadState = super.getForumThreadState(forumThread);
            containerUtil.addModeltoCache(forumThread.getThreadId(), forumThreadState);
        }
        return forumThreadState;
    }
    */
    
    public Collection find(String query, int start, int count){
    	return messageSearchProxy.find(query, start, count);
    }    
    
    
}
