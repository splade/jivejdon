package com.jdon.jivejdon.repository.builder;

import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.auth.Role;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.dao.AccountDao;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

public class AccountFactoryDao implements AccountFactory {
	private final static String module = AccountFactoryDao.class.getName();

	private final AccountDao accountDao;

	public AccountFactoryDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.AccountFactory#getFullAccount(com.jdon.jivejdon.model.Account)
	 */
	public Account getFullAccount(Account accountIn) {
		Debug.logVerbose("enter AccountFactory create", module);
		if (accountIn == null)
			return createAnonymous();
		Account account = null;
		if (!UtilValidate.isEmpty(accountIn.getUserId())) {
			account = accountDao.getAccount(accountIn.getUserId());
		} else if (!UtilValidate.isEmpty(accountIn.getUsername())) {
			account = accountDao.getAccountByName(accountIn.getUsername());
			// ensure one instance in cache for one key;
			if (account != null)
				account = accountDao.getAccount(account.getUserId());
		}
		if (account == null) {
			Debug.logVerbose("the user has been delete, it is Anonymous, userId=" + accountIn.getUserId(), module);
			account = createAnonymous();
		}
		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.AccountFactory#getFullAccount(java.lang.String)
	 */
	public Account getFullAccount(String userId) {
		Debug.logVerbose("enter AccountFactory create", module);
		if (userId == null)
			return createAnonymous();
		Account account = null;
		if (userId != null) {
			account = accountDao.getAccount(userId);
		}
		if (account == null) {
			Debug.logVerbose("the user has been delete, it is Anonymous, userId=" + userId, module);
			account = createAnonymous();
		}
		return account;
	}

	private Account createAnonymous() {
		Account account = new Account();
		account.setUsername("anonymous");
		account.setUserIdLong(new Long(0));
		account.setEmail("anonymous@anonymous.com");
		account.setRoleName(Role.ANONYMOUS);
		account.setModifiedDate("");
		account.setCreationDate("");
		account.setAnonymous(true);
		return account;
	}

}
