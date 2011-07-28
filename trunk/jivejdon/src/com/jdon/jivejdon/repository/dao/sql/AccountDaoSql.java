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
package com.jdon.jivejdon.repository.dao.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.repository.builder.AccountInitFactory;
import com.jdon.jivejdon.repository.dao.AccountDao;
import com.jdon.jivejdon.service.util.ContainerUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.model.query.PageIteratorSolver;

/**
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 * 
 */
@Introduce("modelCache")
public class AccountDaoSql implements AccountDao {
	private final static Logger logger = Logger.getLogger(AccountDaoSql.class);

	private JdbcTempSource jdbcTempSource;

	private PageIteratorSolver pageIteratorSolver;

	private AccountSSOSql accountSSOSql;

	private Constants constants;

	private AccountInitFactory accountInitFactory;

	public AccountDaoSql(JdbcTempSource jdbcTempSource, AccountSSOSql accountSSOSql, ContainerUtil containerUtil,
			AccountInitFactory accountInitFactory, Constants constants) {
		this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
		this.jdbcTempSource = jdbcTempSource;
		this.accountSSOSql = accountSSOSql;
		this.accountInitFactory = accountInitFactory;
		this.constants = constants;
	}

	/*
	 * get the account from database, we cache the account object by using
	 * SessionContext
	 */
	@Around()
	public Account getAccount(String userId) {
		logger.debug("enter getAccount");
		String LOAD_USER_BY_ID = "SELECT userID,username,passwordHash,name,nameVisible,email,emailVisible,"
				+ "rewardPoints,creationDate,modifiedDate FROM jiveUser WHERE userID=?";
		List queryParams = new ArrayList();
		queryParams.add(new Long(userId));
		return fetchAccount(queryParams, LOAD_USER_BY_ID);
	}

	private Account fetchAccount(List queryParams, String sql) {
		Account ret = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, sql);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				logger.debug("found the account");
				Map map = (Map) iter.next();
				ret = accountInitFactory.create(map);
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
	@Around()
	public Account getAccountByName(String username) {
		logger.debug("enter getAccountByName for username=" + username);
		String LOAD_USER_BY_USERNAME = "SELECT userID,username,passwordHash,name,nameVisible,email,emailVisible,"
				+ "rewardPoints,creationDate,modifiedDate FROM jiveUser WHERE username=?";
		List queryParams = new ArrayList();
		queryParams.add(username);
		return fetchAccount(queryParams, LOAD_USER_BY_USERNAME);
	}

	@Around()
	public Account getAccountByEmail(String email) {
		logger.debug("enter getAccountByName");
		String LOAD_USER_BY_USERNAME = "SELECT userID,username,passwordHash,name,nameVisible,email,emailVisible,"
				+ "rewardPoints,creationDate,modifiedDate FROM jiveUser WHERE email=?";
		List queryParams = new ArrayList();
		queryParams.add(email);
		return fetchAccount(queryParams, LOAD_USER_BY_USERNAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.AccountDao#createAccount(Account)
	 */
	public void createAccount(Account account) throws Exception {
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

	public int getAccountEmailVisible(Long userId) {
		logger.debug("enter getAccount");
		String SQL = "SELECT emailVisible FROM jiveUser WHERE userID=?";
		List queryParams = new ArrayList();
		queryParams.add(userId);
		int emailVisible = 0;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				logger.debug("found the account");
				Map map = (Map) iter.next();
				emailVisible = ((Integer) map.get("emailVisible")).intValue();
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return emailVisible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.AccountDao#updateAccount(Account)
	 */
	public void updateAccount(Account account) throws Exception {
		logger.debug("enter updateAccount");
		String SAVE_USER = "UPDATE jiveUser SET username=?, passwordHash=?,name=?,nameVisible=?,email=?,"
				+ "emailVisible=?,rewardPoints=?,modifiedDate=? WHERE " + "userID=?";
		try {
			List queryParams = new ArrayList();
			queryParams.add(account.getUsername());
			queryParams.add(account.getPassword());
			queryParams.add(account.getUsername());
			queryParams.add(new Integer(0));
			queryParams.add(account.getEmail());
			int emailVisible = accountInitFactory.getEmailVisible(account.isAnonymous(), getAccountEmailVisible(account.getUserIdLong()));
			queryParams.add(emailVisible);
			queryParams.add(new Integer(0));
			// queryParams.add(new
			// Integer(account.getReward().getRewardPoints()));

			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
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

	public void updateAccountEmailValidate(Account account) throws Exception {
		logger.debug("enter updateAccount");
		String SQL = "UPDATE jiveUser SET emailVisible=? WHERE userID=?";
		try {
			List queryParams = new ArrayList();
			int oldEmailVisible = getAccountEmailVisible(account.getUserIdLong());
			int emailVisible = accountInitFactory.addEmailValidate(account.isEmailValidate(), oldEmailVisible);
			queryParams.add(emailVisible);
			queryParams.add(account.getUserIdLong());
			jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
			clearCache();
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.AccountDao#deleteAccount(Account)
	 */
	public void deleteAccount(Account account) throws Exception {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.AccountDao#getAccounts(int, int)
	 */
	public PageIterator getAccounts(int start, int count) {
		logger.debug("enter getAccounts");
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveUser ";
		String GET_ALL_ITEMS = "select userID from jiveUser ORDER BY creationDate ASC";
		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, "", start, count);
	}

	public PageIterator getAccountByNameLike(String username, int start, int count) {
		logger.debug("enter getAccounts");
		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from jiveUser where username like '" + username + "'";
		String GET_ALL_ITEMS = "select userID from jiveUser  where username like '" + username + "'";
		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, "", start, count);

	}

	public void clearCache() {
		pageIteratorSolver.clearCache();
	}

}
