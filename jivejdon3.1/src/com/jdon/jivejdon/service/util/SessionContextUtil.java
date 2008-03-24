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
package com.jdon.jivejdon.service.util;

import org.apache.log4j.Logger;

import com.jdon.container.visitor.data.SessionContext;
import com.jdon.container.visitor.data.SessionContextSetup;
import com.jdon.jivejdon.dao.AccountDao;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class SessionContextUtil {
    private final static Logger logger = Logger.getLogger(SessionContextUtil.class);
    public static final String ACCOUNT = "Account";
    
    private AccountDao accountDao;
    private ContainerUtil containerUtil;
        
    /**
     * @param accountDao
     */
    public SessionContextUtil(ContainerUtil containerUtil, AccountDao accountDao) {
        this.containerUtil = containerUtil;
        this.accountDao = accountDao;
    }
    
    public com.jdon.jivejdon.model.Account getLoginAccount(SessionContext sessionContext) {
        com.jdon.jivejdon.model.Account account = null;
        try {
        	 account =  (com.jdon.jivejdon.model.Account)sessionContext.getArrtibute(ACCOUNT);
             if (account == null){
            	 String username = getPrinciple(sessionContext);
            	 if (username != null){
                     account = accountDao.getAccountByName(username);   
                     sessionContext.setArrtibute(ACCOUNT, account);
            		 logger.debug("save Account to session: accout.userId" + account.getUserId());
            	 }
             }
            if (account != null) {  //set user IP         
                logger.debug(" got the account, userId:" + account.getUserId() +" " + ACCOUNT.hashCode() + " role=" + account.getRoleName());
                account.setPostIP(getClientIP(sessionContext));
            }
            
        } catch (Exception e) {
            logger.debug(" getLoginAccount error: " + e);
        }
        return account;
    }    
    
    public String getClientIP(SessionContext sessionContext){
    	SessionContextSetup sessionContextSetup  = containerUtil.getSessionContextSetup();
    	return (String) sessionContextSetup.getArrtibute(SessionContextSetup.REMOTE_ADDRESS, sessionContext);
    }
    
    private String getPrinciple(SessionContext sessionContext){
    	   SessionContextSetup sessionContextSetup  = containerUtil.getSessionContextSetup();
           String principleName = (String)sessionContextSetup.getArrtibute(SessionContextSetup.PRINCIPAL_NAME, sessionContext);
           if (principleName == null) {
               logger.debug("the login principle name is null");
           }else
        	   logger.debug(" the login name is:" + principleName);
           return principleName;
    }

}
