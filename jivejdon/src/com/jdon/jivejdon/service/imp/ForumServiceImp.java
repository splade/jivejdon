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
package com.jdon.jivejdon.service.imp;


import org.apache.log4j.Logger;

import com.jdon.annotation.Singleton;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.dao.ForumDao;
import com.jdon.jivejdon.repository.dao.SequenceDao;
import com.jdon.jivejdon.repository.search.ReBuildIndex;
import com.jdon.jivejdon.service.ForumService;
import com.jdon.util.task.TaskEngine;
;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 *
 */
@Singleton
public class ForumServiceImp implements ForumService{
    private final static Logger logger = Logger.getLogger(ForumServiceImp.class);
    
    private ForumDao forumDao;
    private ForumFactory forumBuilder;
    private SequenceDao sequenceDao;
    private ReBuildIndex reBuildIndex;

    
    public ForumServiceImp(ForumDao forumDao, ForumFactory forumBuilder, 
            SequenceDao sequenceDao,
            ReBuildIndex reBuildIndex){
        this.forumDao = forumDao;
        this.sequenceDao = sequenceDao;
        this.forumBuilder = forumBuilder;
        this.reBuildIndex = reBuildIndex;
      
    }
  
    public Forum getForum(Long forumId) {
       return forumBuilder.getForum(forumId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdon.jivejdon.service.ForumService#createForum(com.jdon.controller.events.EventModel)
     */
    public void createForum(EventModel em) {
        Forum forum = (Forum)em.getModelIF();      
        logger.debug(" enter create Forum" );
        try {
            Long forumIDInt = sequenceDao.getNextId(Constants.FORUM);
            forum.setForumId(forumIDInt);
            
            //创建时间使用long字符串
            long dateTime = System.currentTimeMillis();
            forum.setCreationDate(Long.toString(dateTime));
            forum.setModifiedDate(Long.toString(dateTime));
            forumDao.createForum(forum);
        } catch (Exception e) {
            logger.error(" createForum error: " + e);
           
        }
    }

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.service.ForumService#updateForum(com.jdon.controller.events.EventModel)
     */
    public void updateForum(EventModel em) {
        Forum forum = (Forum)em.getModelIF();
        forumDao.updateForum(forum);

    }

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.service.ForumService#deleteForum(com.jdon.controller.events.EventModel)
     */
    public void deleteForum(EventModel em) {
        Forum forum = (Forum)em.getModelIF();
        forumDao.deleteForum(forum);

    }

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.service.ForumService#getForums(int, int)
     */
    public PageIterator getForums(int start, int count) {       
        PageIterator pageIterator = new PageIterator();
        try {
            pageIterator = forumDao.getForums(start, count);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return pageIterator;
    }
    
    public void clearCache(){
    	logger.debug(" clear all  Forum cache");
    	forumDao.clearCache();
    }
    
    public void doRebuildIndex(){
    	TaskEngine.addTask(reBuildIndex);
    	logger.debug("work is over");
    }

    
}
