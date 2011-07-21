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

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.auth.Role;
import com.jdon.jivejdon.repository.dao.AccountDao;
import com.jdon.jivejdon.repository.dao.SequenceDao;
import com.jdon.jivejdon.repository.dao.util.OldUpdateNewUtil;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.util.JtaTransactionUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.Debug;
import com.jdon.util.task.TaskEngine;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public abstract class AccountServiceImp implements AccountService {
	private final static String module = AccountServiceImp.class.getName();

	protected AccountDao accountDao;

	private SequenceDao sequenceDao;

	protected JtaTransactionUtil jtaTransactionUtil;

	private OldUpdateNewUtil oldUpdateNewUtil;

	public AccountServiceImp(AccountDao accountDao, SequenceDao sequenceDao, JtaTransactionUtil jtaTransactionUtil) {
		this.accountDao = accountDao;
		this.sequenceDao = sequenceDao;
		this.jtaTransactionUtil = jtaTransactionUtil;
		// this.oldUpdateNewUtil = oldUpdateNewUtil;

	}

	public Account getAccountByName(String username) {
		return accountDao.getAccountByName(username);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.framework.samples.jpetstore.service.AccountService#insertAccount
	 * (com.jdon.framework.samples.jpetstore.domain.Account)
	 */

	public void createAccount(EventModel em) {
		Account account = (Account) em.getModelIF();
		Debug.logVerbose("createAccount username=" + account.getUsername(), module);
		try {
			if (accountDao.getAccountByEmail(account.getEmail()) != null) {
				em.setErrors(Constants.EMAIL_EXISTED);
				return;
			}
			if (accountDao.getAccountByName(account.getUsername()) != null) {
				em.setErrors(Constants.USERNAME_EXISTED);
				return;
			}
			jtaTransactionUtil.beginTransaction();
			Long userIDInt = sequenceDao.getNextId(Constants.USER);
			Debug.logVerbose("new userIDInt =" + userIDInt, module);
			account.setUserId(userIDInt.toString().trim());

			// setup the role is User
			account.setRoleName(Role.USER);

			account.setPassword(ToolsUtil.hash(account.getPassword()));

			accountDao.createAccount(account);
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			Debug.logError(" createAccount error : " + e, module);
			em.setErrors(Constants.ERRORS);
			jtaTransactionUtil.rollback();
		}
	}

	public void updateAccount(Account accountInput) throws Exception {
		Debug.logVerbose("enter updateAccount", module);

		try {
			Account checkAccount = getAccount(accountInput.getUserIdLong());
			if (checkAccount == null)
				return;
			if (checkAccount.isEmailValidate()) {
				accountInput.setEmail(checkAccount.getEmail());
			}

			jtaTransactionUtil.beginTransaction();
			accountInput.setPassword(ToolsUtil.hash(accountInput.getPassword()));
			accountDao.updateAccount(accountInput);
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			Debug.logError(" updateAccount error : " + e, module);
			jtaTransactionUtil.rollback();
			throw new Exception(e);
		}
	}

	public void deleteAccount(Account account) throws Exception {
		try {
			jtaTransactionUtil.beginTransaction();
			accountDao.deleteAccount(account);
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			Debug.logError(" deleteAccount error : " + e, module);
			jtaTransactionUtil.rollback();
			throw new Exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.service.AccountService#getAccounts(int, int)
	 */
	public PageIterator getAccounts(int start, int count) {
		return accountDao.getAccounts(start, count);
	}

	public void update() {
		TaskEngine.addTask(oldUpdateNewUtil);
		Debug.logVerbose("work is over", module);
		Debug.logVerbose("work is over", module);
	}

}
