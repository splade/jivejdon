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
package com.jdon.jivejdon.dao.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.dao.MessageQueryDao;
import com.jdon.jivejdon.dao.util.MessagePageIteratorSolver;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.model.query.QuerySpecification;
import com.jdon.jivejdon.model.query.ResultSort;
import com.jdon.jivejdon.model.query.specification.QuerySpecDBModifiedDate;
import com.jdon.model.query.PageIteratorSolver;
import com.jdon.model.query.block.Block;
import com.jdon.treepatterns.model.TreeModel;

/**
 * 
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public abstract class MessageQueryDaoSql implements MessageQueryDao{
    private final static Logger logger = Logger.getLogger(MessageQueryDaoSql.class);
    
    
    protected JdbcTempSource jdbcTempSource;
    protected PageIteratorSolver pageIteratorSolver;
        
    /**
     * @param jdbcTempSource
     */
    public MessageQueryDaoSql(JdbcTempSource jdbcTempSource, MessagePageIteratorSolver messagePageIteratorSolver) {
        this.pageIteratorSolver = messagePageIteratorSolver.getPageIteratorSolver();
        this.jdbcTempSource = jdbcTempSource;
    }
    
    public int getMessageCountOfUser(Long userId){
        logger.debug("enter getMessageCountOfUser  for userId:" + userId);
        String USER_MESSAGE_COUNT =
            "SELECT count(1) FROM jiveMessage WHERE jiveMessage.userID=?";
        List queryParams = new ArrayList();
        queryParams.add(userId);
        return pageIteratorSolver.getDatasAllCount(queryParams, USER_MESSAGE_COUNT);
    }      
      
    
    public int getMessageCount(Long threadId){
        logger.debug("enter getMessageCount  for threadId:" + threadId);
        String ALL_THREADS =
            "SELECT count(1) from jiveMessage WHERE threadID=? ";
        List queryParams = new ArrayList();
        queryParams.add(threadId);
        int count = 0;     
        try {
            count = (Integer)jdbcTempSource.getJdbcTemp().querySingleObject(queryParams, ALL_THREADS);       
        } catch (Exception e) {
           logger.error(e);
        }
        return count;       
    }      
    
    /**
     * get laste message from the message db
     * 
     */
    public Long getLastPostMessageId(Long forumId, Long threadId){
        logger.debug("enter getLastPostMessageId  for threadId:" + threadId);
        String LAST_MESSAGES =
            "SELECT messageID from jiveMessage WHERE forumID=? and threadID = ? ORDER BY modifiedDate DESC";
        List queryParams2 = new ArrayList();
        queryParams2.add(forumId);  
        queryParams2.add(threadId);                 
        
        Long messageId = null;  
        try {            
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams2, LAST_MESSAGES);
            Iterator iter = list.iterator();
            if (iter.hasNext()) {
                Map map = (Map) iter.next();
                messageId = (Long)map.get("messageID");
            }
        } catch (Exception e) {
           logger.error(e);
        }
        return messageId;               
    }
    
    public  TreeModel getTreeModel(ForumThread forumThread){
        logger.debug("getTreeModel from jdbc for threadId=" + forumThread.getThreadId());
        int messagesCount = getMessageCount(forumThread.getThreadId());
        Long rootId = forumThread.getRootMessage().getMessageId();
        TreeModel treeModel = new TreeModel(rootId.longValue(), messagesCount + 1);
        
        //get the messagId collection only except the root messageId
        String SQL =
            "SELECT messageID, parentMessageID, creationDate FROM jiveMessage " +
            "WHERE threadID=? AND parentMessageID IS NOT NULL " +
            "ORDER BY creationDate ASC";
        //parentMessageID IS NOT NULL or parentMessageID != 0 ?
        List queryParams = new ArrayList();
        queryParams.add(forumThread.getThreadId());      
        try {
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, SQL);
    
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                Map map = (Map) iter.next();
                Long messageID = (Long) map.get("messageID");
                Long parentMessageID = (Long) map.get("parentMessageID");
                treeModel.addChild(parentMessageID.longValue(), messageID.longValue());
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return treeModel;
    }
    


    /*
     * (non-Javadoc)
     * 
     * @see com.jdon.jivejdon.dao.MessageDao#getMessages(java.lang.String, int,
     *      int)
     */
    public PageIterator getMessages(Long threadId, int start, int count) {
        String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveMessage where threadID=? ";

        String GET_ALL_ITEMS = "select messageID  from jiveMessage WHERE threadID=? ORDER BY creationDate ASC";

        Collection params = new ArrayList(1);
        params.add(threadId);
        return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT,
                GET_ALL_ITEMS, params, start, count);
    }


    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.MessageDao#getThreads(java.lang.String, int, int)
     */
    public PageIterator getThreads(Long forumId, int start, int count, ResultSort resultSort) {
        String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveThread where forumId=?";

        String GET_ALL_ITEMS = "select threadID  from jiveThread WHERE forumId=? " 
        	+ " ORDER BY modifiedDate  " + resultSort.toString();  

        Collection params = new ArrayList(1);
        params.add(forumId);
        return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT,
                GET_ALL_ITEMS, params, start, count);    
    }

    /* 
     *  get the threads collection include prev/cuurent/next threads.
     */
    public List getThreadsPrevNext(Long forumId, Long currentThreadId) {     
        String GET_ALL_ITEMS = "select threadID  from jiveThread WHERE forumId=? ORDER BY modifiedDate DESC ";
        Collection params = new ArrayList(1);
        params.add(forumId);        
        Block block = pageIteratorSolver.locate(GET_ALL_ITEMS, params, currentThreadId);
        if (block == null){
            return new ArrayList();
        }else{
        	return block.getList();	
        }
        
    }
    
    /**
     * return all threadId satify by QueryCriteria
     */
    public Collection getThreads(QueryCriteria qc) {
    	Collection result = new TreeSet();
		try {
			QuerySpecification qs = new QuerySpecDBModifiedDate(qc);
			qs.parse();
			
			StringBuffer itemIDsSQL = new StringBuffer(
					"SELECT threadID FROM jiveMessage ");
			itemIDsSQL.append(qs.getWhereSQL());
			//itemIDsSQL.append(" GROUP BY threadID O RDER BY msgCount DESC ");
			logger.debug("GET_threadID Collection=" + itemIDsSQL);
			
			List sqlresult = jdbcTempSource.getJdbcTemp().queryMultiObject(
					qs.getParams(), itemIDsSQL.toString());
			Iterator iter = sqlresult.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				Long threadID = (Long) map.get("threadID");
				result.add(threadID);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return result;

	}

    public PageIterator getMessages(QueryCriteria mqc, int start, int count){
    	logger.debug("enter getMessagesSQL for QueryCriteria" );
    	return getQueryCriteriaResult("messageID", mqc, start, count);
    }
    
    /**
     * Template that return QueryCriteria
     * @param keyName the key name that included in PageIterator key collection, such as threadId or messageId
     * @param mqc
     * @param start
     * @param count
     * @return
     */
    public PageIterator getQueryCriteriaResult(String keyName, QueryCriteria qc, int start, int count){
    	logger.debug("enter getQueryCriteriaResult" );
    	try {    		
    		QuerySpecification qs = new QuerySpecDBModifiedDate(qc);
    		qs.parse();
    		
    		StringBuffer allCountSQL = new  StringBuffer(
    				"SELECT count(1)  FROM jiveMessage ");
    		allCountSQL.append(qs.getWhereSQL());
    		
    		StringBuffer itemIDsSQL =  new  StringBuffer(
    				"SELECT "+ keyName  +" FROM jiveMessage ");
    		itemIDsSQL.append(qs.getWhereSQL());
    		itemIDsSQL.append(qs.getResultSortSQL());
			logger.debug("GET_ALL_ITEMS=" + itemIDsSQL);
            return pageIteratorSolver.getPageIterator(allCountSQL.toString(),
            		itemIDsSQL.toString(), qs.getParams(), start, count);
            
		} catch (Exception e) {
			e.printStackTrace();
			return new PageIterator();
		} 
    }
    

    
    public PageIterator popularThreads(QueryCriteria queryCriteria, int count){    	
    	String POPULAR_THREADS_AllCOUNT =
            "SELECT  count(1)  FROM jiveMessage WHERE " +
            "modifiedDate > ?  ";
    	String POPULAR_THREADS =
    	      "SELECT threadID, count(1) AS msgCount FROM jiveMessage " +
    	      "WHERE modifiedDate > ? GROUP BY threadID ORDER BY msgCount DESC";
    	  List queryParams = new ArrayList();    	  
          queryParams.add(queryCriteria.getFromDateNoMillisString());          
    	  return pageIteratorSolver.getPageIterator(POPULAR_THREADS_AllCOUNT,
    			  POPULAR_THREADS, queryParams, 0, count);
    }
}
