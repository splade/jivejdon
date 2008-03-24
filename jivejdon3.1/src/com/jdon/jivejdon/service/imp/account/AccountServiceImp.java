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
package com.jdon.jivejdon.service.imp.account;

import javax.transaction.TransactionManager;

import com.jdon.container.visitor.data.SessionContext;
import com.jdon.container.visitor.data.SessionContextAcceptable;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.controller.service.Stateful;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.auth.ResourceAuthorization;
import com.jdon.jivejdon.dao.AccountDao;
import com.jdon.jivejdon.dao.SequenceDao;
import com.jdon.jivejdon.dao.util.OldUpdateNewUtil;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.auth.Role;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.util.JtaTransactionUtil;
import com.jdon.jivejdon.service.util.SessionContextUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.Debug;
import com.jdon.util.StringUtil;
import com.jdon.util.task.TaskEngine;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class AccountServiceImp implements AccountService, Stateful, SessionContextAcceptable {
    private final static String module = AccountServiceImp.class.getName();

    private AccountDao accountDao;

    private SequenceDao sequenceDao;

    protected SessionContext sessionContext;

    protected SessionContextUtil sessionContextUtil;
    
    protected JtaTransactionUtil jtaTransactionUtil;
    
    protected ResourceAuthorization resourceAuthorization;
    
    private OldUpdateNewUtil oldUpdateNewUtil; 
    
    private EmailHelper emailHelper;
    
   
    public AccountServiceImp(AccountDao accountDao, SequenceDao sequenceDao, 
    		SessionContextUtil sessionContextUtil, JtaTransactionUtil jtaTransactionUtil,
    		ResourceAuthorization resourceAuthorization,
            OldUpdateNewUtil oldUpdateNewUtil,
            EmailHelper emailHelper) {
        this.accountDao = accountDao;
        this.sequenceDao = sequenceDao;
        this.sessionContextUtil = sessionContextUtil;
        this.jtaTransactionUtil = jtaTransactionUtil;
        this.resourceAuthorization = resourceAuthorization;
        this.oldUpdateNewUtil = oldUpdateNewUtil;
        this.emailHelper = emailHelper;
    }
    
    public Account getloginAccount(){
    	return sessionContextUtil.getLoginAccount(sessionContext);
    }
    
    public Account getAccountByName(String username){
        Account account = getloginAccount();
        if (resourceAuthorization.isAdmin(account))// if now is Admin
        	return accountDao.getAccountByName(username);
        else
        	return account;
    	
    }
    
    public Account getAccount(Long userId) {
       	return accountDao.getAccount(userId.toString());
    }

    /**
     * init the account
     */
    public Account initAccount(EventModel em) {
        return new Account();
    }
    
    
  

    /* (non-Javadoc)
     * @see com.jdon.framework.samples.jpetstore.service.AccountService#insertAccount(com.jdon.framework.samples.jpetstore.domain.Account)
     */
    public void createAccount(EventModel em) {
        Account account = (Account) em.getModelIF();
        Debug.logVerbose("createAccount username=" + account.getUsername(), module);
        TransactionManager tx = jtaTransactionUtil.getTransactionManager();
        try {
            if (accountDao.getAccountByEmail(account.getEmail()) != null) {
                em.setErrors(Constants.EMAIL_EXISTED);
                return;
            }
            if (accountDao.getAccountByName(account.getUsername()) != null) {
                em.setErrors(Constants.USERNAME_EXISTED);
                return;
            }
            tx.begin();
            Long userIDInt = sequenceDao.getNextId(Constants.USER);
            Debug.logVerbose("new userIDInt =" + userIDInt, module);
            account.setUserId(userIDInt.toString().trim());
            
            //setup the role is User
            account.setRoleName(Role.USER);
            
            account.setPassword(ToolsUtil.hash(account.getPassword()));
            
            accountDao.createAccount(account);
            tx.commit();
        } catch (Exception e) {
            Debug.logError(" createAccount error : " + e, module);
            em.setErrors(Constants.ERRORS);
            jtaTransactionUtil.rollback(tx);
        }
    }
    
  

    /* (non-Javadoc)
     * @see com.jdon.framework.samples.jpetstore.service.AccountService#updateAccount(com.jdon.framework.samples.jpetstore.domain.Account)
     */
    public void updateAccount(EventModel em) {
    	Debug.logVerbose("enter updateAccount", module);
        Account accountInput = (Account) em.getModelIF();
        TransactionManager tx = jtaTransactionUtil.getTransactionManager();
        try {
            Account account = getloginAccount();
            if (account == null) {
                Debug.logError("this user not login", module);
                return;
            }
            tx.begin();
            if (resourceAuthorization.isOwner(account, accountInput)) { 
            	accountInput.setPassword(ToolsUtil.hash(accountInput.getPassword()));
                accountDao.updateAccount(accountInput);
            }
            tx.commit();
        } catch (Exception daoe) {
            Debug.logError(" updateAccount error : " + daoe, module);
            em.setErrors(Constants.ERRORS);
            jtaTransactionUtil.rollback(tx);
        }
    }

    public void deleteAccount(EventModel em) {
        Account account = (Account) em.getModelIF();
        TransactionManager tx = jtaTransactionUtil.getTransactionManager();
        try {
        	 tx.begin();
			accountDao.deleteAccount(account);
			tx.commit();
		} catch (Exception e) {
			 em.setErrors(Constants.ERRORS);
			 jtaTransactionUtil.rollback(tx);
		}
    }
    
  

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.service.AccountService#getAccounts(int, int)
     */
    public PageIterator getAccounts(int start, int count) {
        return accountDao.getAccounts(start, count);
    }
    
    
    public void forgetPasswd(EventModel em){
    	Account accountParam = (Account) em.getModelIF();
    	Account account = null;
    	TransactionManager tx = jtaTransactionUtil.getTransactionManager();
    	  try {
    		  account = accountDao.getAccountByEmail(accountParam.getEmail());
    		  if (account == null) {
    			  em.setErrors(Constants.NOT_FOUND);
    			  return;
    		  }
    		  if ((account.getPasswdanswer().equalsIgnoreCase(accountParam.getPasswdanswer()))
    			 && (account.getPasswdtype().equalsIgnoreCase(accountParam.getPasswdtype()))){
    			  tx.begin();
    			  String newpasswd = StringUtil.getPassword(8);
    			  account.setPassword(ToolsUtil.hash(newpasswd));
                  accountDao.updateAccount(account);
    			  emailHelper.send(account, newpasswd);
    			  tx.commit();
    		  }else{
    			  em.setErrors(Constants.NOT_FOUND);
    			  return;
    		  }
    		  
 		} catch (Exception e) {
 			 em.setErrors(Constants.ERRORS);
 			 jtaTransactionUtil.rollback(tx);
 		}
    }
  
    
    /**
     * @return Returns the sessionContext.
     */
    public SessionContext getSessionContext() {
        return sessionContext;
    }

    /**
     * @param sessionContext The sessionContext to set.
     */
    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    
    public void update(){
    	TaskEngine.addTask(oldUpdateNewUtil);
    	Debug.logVerbose("work is over", module);
    	Debug.logVerbose("work is over", module);
    }
 
}
