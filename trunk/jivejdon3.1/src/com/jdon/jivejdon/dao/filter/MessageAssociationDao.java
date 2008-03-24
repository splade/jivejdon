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
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.AccountDao;
import com.jdon.jivejdon.dao.PropertyDao;
import com.jdon.jivejdon.dao.UploadFileDao;
import com.jdon.jivejdon.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.dao.sql.MessageDaoSql;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.UploadFile;

/**
 * Dao Filter about all loose associations with FourmMessage.
 * such as: Upload; Property 
 * 
 * @author banq(http://www.jdon.com)
 *
 */
public class MessageAssociationDao extends MessageDaoSql{
    private final static Logger logger = Logger.getLogger(MessageAssociationDao.class);
    
    private UploadFileDao uploadFileDao;
    private PropertyDao propertyDao;
    
    public MessageAssociationDao(JdbcTempSource jdbcTempSource, 
            AccountDao accountDao,
            PropertyDao propertyDao,
            UploadFileDao uploadFileDao, Constants constants) {
        super(jdbcTempSource, accountDao, constants);
        this.propertyDao = propertyDao;
        this.uploadFileDao = uploadFileDao;
    }
    
    public ForumMessage getMessage(Long messageId){
        logger.debug("MessageAssociationDao.getMessage");
        Collection props = propertyDao.getProperties(Constants.MESSAGE, messageId);
        ForumMessage message = super.getMessage(messageId);
        if (message != null) message.setPropertys(props);
        logger.debug("MessageAssociationDao.getMessage end");
        return message;
    }
    
    public void createMessage(ForumMessage  forumMessage) throws Exception{
        logger.debug("MessageAssociationDao.createMessage");
        super.createMessage(forumMessage);
        Collection props = forumMessage.getPropertys();
        propertyDao.updateProperties(Constants.MESSAGE, forumMessage.getMessageId(), props);
        
    }
    
    public void createMessageReply(ForumMessageReply  forumMessageReply) throws Exception{
        logger.debug("MessageAssociationDao.createMessageReply");
        super.createMessageReply(forumMessageReply);
        Collection props = forumMessageReply.getPropertys();
        propertyDao.updateProperties(Constants.MESSAGE, forumMessageReply.getMessageId(), props);
        
    }
    
    public void updateMessage(ForumMessage  forumMessage) throws Exception{
        logger.debug("MessageAssociationDao.updateMessage");
        super.updateMessage(forumMessage);
        Collection props = forumMessage.getPropertys();
        propertyDao.deleteProperties(Constants.MESSAGE, forumMessage.getMessageId());
        propertyDao.updateProperties(Constants.MESSAGE, forumMessage.getMessageId(), props);
    }
    
    public void deleteMessage(Long  forumMessageId) throws Exception{
        logger.debug("MessageAssociationDao.deleteMessage");
        super.deleteMessage(forumMessageId);
        deleteAllUploadFiles(forumMessageId);
        propertyDao.deleteProperties(Constants.MESSAGE, forumMessageId);
    }
    
    
    private void deleteAllUploadFiles(Long messageId){
        logger.debug(" MessageUpLoadsFilter.deleteAllUploadFiles ");        
        try {
            Collection uploads = uploadFileDao.getAdjunct(messageId);
            Iterator iter = uploads.iterator();
            while(iter.hasNext()){
                UploadFile uploadFile = (UploadFile)iter.next();
                uploadFileDao.deleteUploadFile(uploadFile.getId());
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
}
