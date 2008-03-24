package com.jdon.jivejdon.repository;

import com.jdon.jivejdon.dao.AccountDao;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.auth.Role;
import com.jdon.util.Debug;

public class AccountFactory {
	 private final static String module = AccountFactory.class.getName();

	private AccountDao accountDao;
	
	public AccountFactory(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public Account getFullAccount(Account accountIn) {
		Debug.logVerbose("enter AccountFactory create", module);
		if (accountIn == null)
			return createAnonymous();
		Account account = null;
		if (accountIn.getUserId() != null){
			account = accountDao.getAccount(accountIn.getUserId());			
		}else if(accountIn.getUsername() != null){
			account = accountDao.getAccountByName(accountIn.getUsername());
		}        
		if (account == null) {
			Debug.logVerbose("the user has been delete, it is Anonymous, userId=" + accountIn.getUserId(), module);
			account = createAnonymous();
		}
		return account;
	}
	
	private Account createAnonymous(){
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
