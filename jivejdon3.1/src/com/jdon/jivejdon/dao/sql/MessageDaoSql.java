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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.AccountDao;
import com.jdon.jivejdon.dao.MessageDao;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.util.ToolsUtil;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class MessageDaoSql implements MessageDao {
    private final static Logger logger = Logger.getLogger(MessageDaoSql.class);
    
    protected JdbcTempSource jdbcTempSource;
    
    private AccountDao accountDao;
    
    private Constants constants;
    
    public MessageDaoSql(JdbcTempSource jdbcTempSource,
            AccountDao accountDao, Constants constants) {
        this.jdbcTempSource = jdbcTempSource;
        this.accountDao = accountDao;
        this.constants = constants;
    }

    /* 
     * ForumMessage中包含的ForumThread Forum等对象只是包涵ID的空对象,完整对象需要
     * 前台再行处理.
     * 
     * @see com.jdon.jivejdon.dao.MessageDao#getMessage(java.lang.String)
     */
    public ForumMessage getMessage(Long messageId) {
        logger.debug("enter getMessage  for id:" + messageId);
        String LOAD_MESSAGE =
            "SELECT threadID, forumID, userID, subject, body, modValue, rewardPoints, "+
            "creationDate, modifiedDate FROM jiveMessage WHERE messageID=?";
        List queryParams = new ArrayList();
        queryParams.add(messageId);
        
        ForumMessage forumMessage = null;        
        try {
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams,
                    LOAD_MESSAGE);
            Iterator iter = list.iterator();
            if (iter.hasNext()) {
                forumMessage = new ForumMessage();
                Map map = (Map) iter.next();
                forumMessage.setMessageId(messageId);
                forumMessage.setSubject((String) map.get("subject"));
                forumMessage.setBody((String) map.get("body"));
                
                String saveDateTime = ((String) map.get("modifiedDate")).trim();
                String displayDateTime = constants.getDateTimeDisp(saveDateTime);                 
                forumMessage.setModifiedDate(displayDateTime);            
                
                saveDateTime = ((String) map.get("creationDate")).trim();
                displayDateTime = constants.getDateTimeDisp(saveDateTime);                                                 
                forumMessage.setCreationDate(displayDateTime);      
                //get the formatter so later can transfer String to Date
                forumMessage.setDateTime_formatter(constants.getDateTime_formatter());     
                
                ForumThread forumThread = new ForumThread();
                forumThread.setThreadId((Long) map.get("threadID"));
                forumMessage.setForumThread(forumThread);
                
                Forum forum  = new Forum();
                forum.setForumId((Long) map.get("forumID"));
                forumMessage.setForum(forum);
                
                com.jdon.jivejdon.model.Account account = new com.jdon.jivejdon.model.Account();
                forumMessage.setAccount(account);
                Object o = map.get("userID");
                if (o != null){
                    account.setUserIdLong((Long)o);
                }                
                //lazy load
                //forumMessage.setPropertys(propertyDaoSql.getAllPropertys(Constants.MESSAGE, messageId));
            }
        } catch (Exception e) {
            logger.error("messageId="+ messageId+ " happend  " + e);
        }
        logger.debug("getMessage end");
        return forumMessage;
    }
    
    
    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.MessageDao#getThread(java.lang.String)
     */
    public ForumThread getThread(Long threadId) {
        logger.debug("enter getThread for id:" + threadId);
        String LOAD_THREAD =
            "SELECT forumID, rootMessageID, modValue, rewardPoints, creationDate, " +
            "modifiedDate FROM jiveThread WHERE threadID=?";
        List queryParams = new ArrayList();
        queryParams.add(threadId);
        
        ForumThread forumThread = null;        
        try {
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams,
                    LOAD_THREAD);
            Iterator iter = list.iterator();
            if (iter.hasNext()) {
                forumThread = new ForumThread();
                Map map = (Map) iter.next();
                forumThread.setThreadId(threadId);
                
                Forum forum  = new Forum();
                forum.setForumId((Long) map.get("forumID"));
                forumThread.setForum(forum);
                
                ForumMessage forumMessage = new ForumMessage();
                forumMessage.setMessageId((Long) map.get("rootMessageID"));
                forumThread.setRootMessage(forumMessage);
                
                String saveDateTime = ((String) map.get("modifiedDate")).trim();
                String displayDateTime = constants.getDateTimeDisp(saveDateTime);                 
                forumThread.setModifiedDate(displayDateTime);            
                
                saveDateTime = ((String) map.get("creationDate")).trim();
                displayDateTime = constants.getDateTimeDisp(saveDateTime);                                                 
                forumThread.setCreationDate(displayDateTime);
                                
                //forumThread.setPropertys(propertyDaoSql.getAllPropertys(Constants.THREAD, threadId));
                
            }
        } catch (Exception e) {
            logger.error("threadId="+ threadId+ " happend  " + e);
        }
        return forumThread;
    }    
        

    /*
     *topic message insert, no parentMessageID value,
     *so the table's field default value must be null 
     */
    public void createMessage(ForumMessage forumMessage) throws Exception {
        logger.debug("enter createTopicMessage for id:" + forumMessage.getMessageId());
        //differnce with createRpleyMessage: parentMessageID,
        
        String INSERT_MESSAGE =
            "INSERT INTO jiveMessage(messageID, threadID, forumID, " +
            "userID, subject, body, modValue, rewardPoints, creationDate, modifiedDate) " +
            "VALUES(?,?,?,?,?,?,?,?,?,?)";
        List queryParams = new ArrayList();
        queryParams.add(forumMessage.getMessageId());        
        queryParams.add(forumMessage.getForumThread().getThreadId());
        queryParams.add(forumMessage.getForum().getForumId());
        queryParams.add(forumMessage.getAccount().getUserId());
        queryParams.add(forumMessage.getSubject());
        queryParams.add(forumMessage.getBody());
        queryParams.add(new Integer(0));
        queryParams.add(new Integer(forumMessage.getRewardPoints()));
        
        long now = System.currentTimeMillis();   
        String saveDateTime = ToolsUtil.dateToMillis(now);
        String displayDateTime = constants.getDateTimeDisp(saveDateTime);
        queryParams.add(saveDateTime);
        forumMessage.setCreationDate(displayDateTime);
        
        queryParams.add(saveDateTime);
        forumMessage.setModifiedDate(displayDateTime);            
        
        try {
            jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("messageId="+ forumMessage.getMessageId() + " happend " + e);
        }
    }
    
    public  void createMessageReply(ForumMessageReply  forumMessage) throws Exception{
        logger.debug("enter createMessageReply for id:" + forumMessage.getMessageId());
        //differnce with createTopicMessage: parentMessageID,
        String INSERT_MESSAGE =
            "INSERT INTO jiveMessage(messageID, parentMessageID, threadID, forumID, " +
            "userID, subject, body, modValue, rewardPoints, creationDate, modifiedDate) " +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        List queryParams = new ArrayList();
        queryParams.add(forumMessage.getMessageId());
        queryParams.add(forumMessage.getParentMessage().getMessageId());
        queryParams.add(forumMessage.getForumThread().getThreadId());
        queryParams.add(forumMessage.getForum().getForumId());
        queryParams.add(forumMessage.getAccount().getUserId());
        queryParams.add(forumMessage.getSubject());
        queryParams.add(forumMessage.getBody());
        queryParams.add(new Integer(0));
        queryParams.add(new Integer(forumMessage.getRewardPoints()));
        
        long now = System.currentTimeMillis();   
        String saveDateTime = ToolsUtil.dateToMillis(now);
        String displayDateTime = constants.getDateTimeDisp(saveDateTime);
        queryParams.add(saveDateTime);
        forumMessage.setCreationDate(displayDateTime);
        
        queryParams.add(saveDateTime);
        forumMessage.setModifiedDate(displayDateTime);            
        
        try {
            jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_MESSAGE);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("messageId="+ forumMessage.getMessageId() + " happend " + e);
        }
    }
    
    public void createThread(ForumThread  forumThread) throws Exception{
        String INSERT_THREAD =
            "INSERT INTO jiveThread(threadID,forumID,rootMessageID,modValue, " +
            "rewardPoints,creationDate,modifiedDate) VALUES(?,?,?,?,?,?,?)";
        List queryParams = new ArrayList();
        queryParams.add(forumThread.getThreadId());
        queryParams.add(forumThread.getForum().getForumId());
        queryParams.add(forumThread.getRootMessage().getMessageId());
        queryParams.add(new Integer(0));
        queryParams.add(new Integer(forumThread.getRootMessage().getRewardPoints()));
        
        long now = System.currentTimeMillis();   
        String saveDateTime = ToolsUtil.dateToMillis(now);
        String displayDateTime = constants.getDateTimeDisp(saveDateTime);
        queryParams.add(saveDateTime);
        forumThread.setCreationDate(displayDateTime);
        
        queryParams.add(saveDateTime);
        forumThread.setModifiedDate(displayDateTime);            
        
        try {
            jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_THREAD);
        } catch (Exception e) {
            logger.error("forumThread="+ forumThread.getThreadId() + " happend " + e);
            throw new Exception(e);
        }
    }
    
    /**
     * when update the root message, call this method
     * @param forumThread
     */
    public void updateThread(ForumThread  forumThread) throws Exception{
        String SAVE_THREAD =
            "UPDATE jiveThread SET  modifiedDate=? WHERE threadID=?";
        
        List queryParams = new ArrayList();
        
        long now = System.currentTimeMillis();   
        String saveDateTime = ToolsUtil.dateToMillis(now);
        String displayDateTime = constants.getDateTimeDisp(saveDateTime); 
        queryParams.add(saveDateTime);
        forumThread.setModifiedDate(displayDateTime);                      
        queryParams.add(forumThread.getThreadId());
        try {
            jdbcTempSource.getJdbcTemp().operate(queryParams, SAVE_THREAD);
            
        } catch (Exception e) {
            logger.error("forumThread="+ forumThread.getThreadId() + " happend " + e);
            throw new Exception(e);
        }
    }
    
    public void updateMovingForum(ForumThread  forumThread) throws Exception{
    	  String SQL =
              "UPDATE jiveMessage SET  forumID=? WHERE messageID=?";
          List queryParams = new ArrayList();        
          queryParams.add(forumThread.getForum().getForumId());
          queryParams.add(forumThread.getRootMessage().getMessageId());
          
          String SQL2 =
              "UPDATE jiveThread SET  forumID=? WHERE threadID=?";
          List queryParams2 = new ArrayList();        
          queryParams2.add(forumThread.getForum().getForumId());
          queryParams2.add(forumThread.getThreadId());
          try {
              jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
              jdbcTempSource.getJdbcTemp().operate(queryParams2, SQL2);
          } catch (Exception e) {
              logger.error(" updateMovingForum forumThread="+ forumThread.getThreadId() + " happend " + e);
              throw new Exception(e);
          }
          
         

          
    }

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.MessageDao#updateMessage(com.jdon.jivejdon.model.ForumMessage)
     */
    public void updateMessage(ForumMessage forumMessage) throws Exception {
        String SAVE_MESSAGE =
            "UPDATE jiveMessage SET userID=?, subject=?, body=?, modValue=?, " +
            "rewardPoints=?, modifiedDate=? WHERE messageID=?";
        List queryParams = new ArrayList();
        queryParams.add(forumMessage.getAccount().getUserId());
        queryParams.add(forumMessage.getSubject());
        queryParams.add(forumMessage.getBody());
        queryParams.add(new Integer(0));
        queryParams.add(new Integer(forumMessage.getRewardPoints()));     
        
        long now = System.currentTimeMillis();   
        String saveDateTime = ToolsUtil.dateToMillis(now);
        String displayDateTime = constants.getDateTimeDisp(saveDateTime); 
        queryParams.add(saveDateTime);
        forumMessage.setModifiedDate(displayDateTime);       
                
        queryParams.add(forumMessage.getMessageId());
        try {
            jdbcTempSource.getJdbcTemp().operate(queryParams, SAVE_MESSAGE);
            //propertyDaoSql.deleteProperties(Constants.MESSAGE, forumMessage.getMessageId());
            //propertyDaoSql.insertProperties(Constants.MESSAGE, forumMessage.getMessageId(), forumMessage.getPropertys());
            
        } catch (Exception e) {
            logger.error("messageId="+ forumMessage.getMessageId() + " happend " + e);
            throw new Exception(e);
        }
    }

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.MessageDao#deleteMessage(com.jdon.jivejdon.model.ForumMessage)
     */
    public void deleteMessage(Long  forumMessageId) throws Exception {
        String DELETE_MESSAGE =
            "DELETE FROM jiveMessage WHERE messageID=?";
        List queryParams = new ArrayList();
        queryParams.add(forumMessageId);
        try {
            jdbcTempSource.getJdbcTemp().operate(queryParams, DELETE_MESSAGE);
            
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("messageId="+ forumMessageId + " happend " + e);
        }
    }
    
    
    public void deleteThread(Long threadId) throws Exception{
        String DELETE_THREAD =
            "DELETE FROM jiveThread WHERE threadID=?";        
        List queryParams = new ArrayList();
        queryParams.add(threadId);
        try {
            jdbcTempSource.getJdbcTemp().operate(queryParams, DELETE_THREAD);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("threadId="+ threadId + " happend " + e);
        }
    }

}
