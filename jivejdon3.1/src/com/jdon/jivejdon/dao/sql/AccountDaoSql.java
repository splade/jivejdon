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

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.AccountDao;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.Reward;
import com.jdon.jivejdon.service.util.ContainerUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.model.query.PageIteratorSolver;

/**
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 *
 */

public class AccountDaoSql implements AccountDao {
    private final static Logger logger = Logger.getLogger(AccountDaoSql.class);

    private JdbcTempSource jdbcTempSource;
    
    private PageIteratorSolver pageIteratorSolver;
    
    private AccountSSOSql accountSSOSql;
    
    private Constants constants;

    public AccountDaoSql(JdbcTempSource jdbcTempSource, AccountSSOSql accountSSOSql, 
    		ContainerUtil containerUtil, Constants constants) {
        this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
        this.jdbcTempSource = jdbcTempSource;
        this.accountSSOSql = accountSSOSql;
        this.constants = constants;
    }

    /* 
     * get the account from database,
     * we cache the account object by using SessionContext
     * 
     */
    public Account getAccount(String userId) {
        logger.debug("enter getAccount");
        String LOAD_USER_BY_ID = "SELECT userID,username,passwordHash,name,nameVisible,email,emailVisible," +
        "rewardPoints,creationDate,modifiedDate FROM jiveUser WHERE userID=?";
        List queryParams = new ArrayList();
        queryParams.add(userId);         
        return fetchAccount(queryParams, LOAD_USER_BY_ID);
    }
    
    private Account fetchAccount(List queryParams, String sql){
        Account ret = null;
        try {
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, sql);
            Iterator iter = list.iterator();
            if (iter.hasNext()) {
                logger.debug("found the account");
                ret = new Account();
                Map map = (Map) iter.next();
                ret.setUserIdLong((Long) map.get("userID"));
                ret.setUsername((String) map.get("username"));
                ret.setPassword((String) map.get("passwordHash"));
                ret.setEmail((String) map.get("email"));
                int emailVisible = ((Integer) map.get("emailVisible")).intValue();
                ret.setEmailVisible((emailVisible == 1));
                
                int rewardPoints = ((Integer) map.get("rewardPoints")).intValue();
                ret.setReward(new Reward(rewardPoints));
                
                String saveDateTime = ((String) map.get("modifiedDate")).trim();
                String displayDateTime = constants.getDateTimeDisp(saveDateTime);                 
                ret.setModifiedDate(displayDateTime);            
                
                saveDateTime = ((String) map.get("creationDate")).trim();
                displayDateTime = constants.getDateTimeDisp(saveDateTime);                                                 
                ret.setCreationDate(displayDateTime);
                
                //get the other info. form SSO DB
                String roleName = accountSSOSql.getRoleNameFByusername(ret.getUsername());
                if (roleName != null)
                    ret.setRoleName(roleName);
                else
                	logger.debug("the roleName is null");
                accountSSOSql.embedPasswordassit(ret);
            }
            
        } catch (Exception se) {
            logger.error(se);
        }
        return ret;
    }

    public Account getAccount(String username, String password) {
        String AUTHORIZE = "SELECT userID FROM jiveUser WHERE username=? AND passwordHash=?";
        List queryParams = new ArrayList();
        queryParams.add(username);
        queryParams.add(password);
        Account account = null;
        try {
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, AUTHORIZE);
            Iterator iter = list.iterator();
            String userId = null;
            if (iter.hasNext()) {
                Map map = (Map) iter.next();
                userId = ((Long) map.get("userID")).toString();                
            }
            if ((userId != null && (userId.length() != 0)))
                account = getAccount(userId);
            
        } catch (Exception se) {
            logger.error(se);
        }
        return account;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdon.jivejdon.dao.AccountDao#getAccountByName(java.lang.String)
     */
    public Account getAccountByName(String username) {
        logger.debug("enter getAccountByName for username=" + username);
        String LOAD_USER_BY_USERNAME =
            "SELECT userID,username,passwordHash,name,nameVisible,email,emailVisible,"+
            "rewardPoints,creationDate,modifiedDate FROM jiveUser WHERE username=?";
        List queryParams = new ArrayList();
        queryParams.add(username);    
        return fetchAccount(queryParams, LOAD_USER_BY_USERNAME);
    }
    
    public Account getAccountByEmail(String email) {
        logger.debug("enter getAccountByName");
        String LOAD_USER_BY_USERNAME =
            "SELECT userID,username,passwordHash,name,nameVisible,email,emailVisible,"+
            "rewardPoints,creationDate,modifiedDate FROM jiveUser WHERE email=?";
        List queryParams = new ArrayList();
        queryParams.add(email);    
        return fetchAccount(queryParams, LOAD_USER_BY_USERNAME);
    }    

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.AccountDao#createAccount(Account)
     */
    public void createAccount(Account account) throws Exception{
        logger.debug("enter createAccount");
        String INSERT_USER = "INSERT INTO jiveUser(userID,username,passwordHash,name,nameVisible,"
                + "email,emailVisible,rewardPoints, creationDate,modifiedDate) " + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            List queryParams = new ArrayList();
            queryParams.add(account.getUserIdLong());
            queryParams.add(account.getUsername());
            queryParams.add(account.getPassword());
            queryParams.add(account.getUsername());
            queryParams.add(new Integer(0));
            queryParams.add(account.getEmail());
            queryParams.add(new Integer(account.isEmailVisible() ? 1 : 0));
            queryParams.add(new Integer(0));

            long now = System.currentTimeMillis();
            String saveDateTime = ToolsUtil.dateToMillis(now);
            String displayDateTime = constants.getDateTimeDisp(saveDateTime);
            queryParams.add(saveDateTime);
            account.setModifiedDate(displayDateTime);
            queryParams.add(saveDateTime);
            account.setCreationDate(displayDateTime);

            jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_USER);
            accountSSOSql.insertSSOServer(account);            
            clearCache();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }
    

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.AccountDao#updateAccount(Account)
     */
    public void updateAccount(Account account) throws Exception{
        logger.debug("enter updateAccount");
        String SAVE_USER = "UPDATE jiveUser SET username=?, passwordHash=?,name=?,nameVisible=?,email=?,"
                + "emailVisible=?,rewardPoints=?,creationDate=?,modifiedDate=? WHERE " + "userID=?";
        try {
            List queryParams = new ArrayList();
            queryParams.add(account.getUsername());
            queryParams.add(account.getPassword());
            queryParams.add(account.getUsername());
            queryParams.add(new Integer(0));
            queryParams.add(account.getEmail());
            queryParams.add(new Integer(account.isEmailVisible() ? 1 : 0));
            queryParams.add(new Integer(0));
            //queryParams.add(new
            // Integer(account.getReward().getRewardPoints()));

            long now = System.currentTimeMillis();
            String saveDateTime = ToolsUtil.dateToMillis(now);
            String displayDateTime = constants.getDateTimeDisp(saveDateTime);
            queryParams.add(saveDateTime);
            account.setCreationDate(displayDateTime);
            queryParams.add(saveDateTime);
            account.setModifiedDate(displayDateTime);

            queryParams.add(account.getUserIdLong());

            accountSSOSql.updateSSOServer(account);
            jdbcTempSource.getJdbcTemp().operate(queryParams, SAVE_USER);
            clearCache();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }
    

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.AccountDao#deleteAccount(Account)
     */
    public void deleteAccount(Account account) throws Exception{
        logger.debug("enter deleteAccount");
        String SQL = "DELETE FROM jiveUser WHERE userID=?";
        List queryParams = new ArrayList();
        queryParams.add(account.getUserId());    
        try {
            accountSSOSql.deleteSSOServer(account);
            jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
            clearCache();
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }
  
    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.AccountDao#getAccounts(int, int)
     */
    public PageIterator getAccounts(int start, int count) {
        logger.debug("enter getAccounts");
        String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveUser ";
        String GET_ALL_ITEMS = "select userID from jiveUser ORDER BY creationDate ASC";
        return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT,
                GET_ALL_ITEMS,"",  start, count);    
    }
    
    public void clearCache() {
        pageIteratorSolver.clearCache();
    }

}
