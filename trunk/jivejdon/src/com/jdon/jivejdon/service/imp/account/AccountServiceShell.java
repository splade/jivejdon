/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.service.imp.account;

import java.util.HashMap;
import java.util.Map;

import com.jdon.annotation.intercept.SessionContextAcceptable;
import com.jdon.annotation.intercept.Stateful;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.auth.ResourceAuthorization;
import com.jdon.jivejdon.manager.email.ForgotPasswdEmail;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.repository.dao.AccountDao;
import com.jdon.jivejdon.repository.dao.SequenceDao;
import com.jdon.jivejdon.service.util.JtaTransactionUtil;
import com.jdon.jivejdon.service.util.SessionContextUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.Debug;
import com.jdon.util.StringUtil;

@Stateful
public class AccountServiceShell extends AccountServiceImp {
	private final static String module = AccountServiceShell.class.getName();

	protected SessionContextUtil sessionContextUtil;

	protected SessionContext sessionContext;

	protected ResourceAuthorization resourceAuthorization;

	private ForgotPasswdEmail forgotPasswdEmail;

	private Map<String, Integer> cachedforgetPasswdEmails = new HashMap(1);

	public AccountServiceShell(AccountDao accountDao, SequenceDao sequenceDao, SessionContextUtil sessionContextUtil,
			JtaTransactionUtil jtaTransactionUtil, ResourceAuthorization resourceAuthorization, ForgotPasswdEmail forgotPasswdEmail) {
		super(accountDao, sequenceDao, jtaTransactionUtil);
		this.sessionContextUtil = sessionContextUtil;
		this.resourceAuthorization = resourceAuthorization;
		this.forgotPasswdEmail = forgotPasswdEmail;
	}

	public Account getloginAccount() {
		return sessionContextUtil.getLoginAccount(sessionContext);
	}

	public Account getAccountByName(String username) {
		Account account = getloginAccount();
		if (resourceAuthorization.isAdmin(account))// if now is Admin
			return super.getAccountByName(username);
		else
			return account;

	}

	public void updateAccount(EventModel em) {
		Debug.logVerbose("enter updateAccount", module);
		Account accountInput = (Account) em.getModelIF();
		try {
			Account account = getloginAccount();
			if (account == null) {
				Debug.logError("this user not login", module);
				return;
			}
			if (resourceAuthorization.isOwner(account, accountInput)) {
				super.updateAccount(accountInput);
			}
		} catch (Exception daoe) {
			Debug.logError(" updateAccount error : " + daoe, module);
			em.setErrors(Constants.ERRORS);
		}
	}

	public void deleteAccount(EventModel em) {
		Account accountInput = (Account) em.getModelIF();
		try {
			Account account = getloginAccount();
			if (resourceAuthorization.isOwner(account, accountInput)) {
				super.deleteAccount(accountInput);
			}
		} catch (Exception e) {
			em.setErrors(Constants.ERRORS);
		}
	}

	public void updateAccountAttachment(EventModel em) {
		Account accountInput = (Account) em.getModelIF();
		Account account = this.getAccount(accountInput.getUserIdLong());
		account.setUploadFile(true);
	}

	public void forgetPasswd(EventModel em) {
		Account accountParam = (Account) em.getModelIF();
		if (cachedforgetPasswdEmails.isEmpty())
			cachedforgetPasswdEmails.put(accountParam.getEmail(), 1);
		else if (!cachedforgetPasswdEmails.containsKey(accountParam.getEmail())) {
			em.setErrors(Constants.NOT_FOUND);
			return;
		} else {
			int count = cachedforgetPasswdEmails.get(accountParam.getEmail());
			if (count > 2 || count == 0) {
				em.setErrors(Constants.NOT_FOUND);
				return;
			} else {
				count++;
				cachedforgetPasswdEmails.put(accountParam.getEmail(), count);
			}
		}

		Account account = null;
		try {
			account = accountDao.getAccountByEmail(accountParam.getEmail());
			if (account == null) {
				em.setErrors(Constants.NOT_FOUND);
				return;
			}
			if ((account.getPasswdanswer().equalsIgnoreCase(accountParam.getPasswdanswer()))
					&& (account.getPasswdtype().equalsIgnoreCase(accountParam.getPasswdtype()))) {
				String newpasswd = StringUtil.getPassword(8);
				account.setPassword(ToolsUtil.hash(newpasswd));
				accountDao.updateAccount(account);
				forgotPasswdEmail.send(account, newpasswd);
			} else {
				em.setErrors(Constants.NOT_FOUND);
				return;
			}

		} catch (Exception e) {
			em.setErrors(Constants.ERRORS);
		}
	}

	/**
	 * @return Returns the sessionContext.
	 */
	public SessionContext getSessionContext() {
		return sessionContext;
	}

	/**
	 * @param sessionContext
	 *            The sessionContext to set.
	 */
	@SessionContextAcceptable
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

}
