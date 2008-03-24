package com.jdon.jivejdon.service.imp.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.PropertyDao;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.AccountProfile;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.service.AccountProfileService;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.util.UtilValidate;

public class AccountProfileServiceImp implements AccountProfileService {
	
	private PropertyDao propertyDao;
	private AccountService accountService;
	
	public AccountProfileServiceImp(AccountService accountService, PropertyDao propertyDao) {
		this.propertyDao = propertyDao;
		this.accountService = accountService;
	}
	
	public AccountProfile getAccountProfile(String userId) {		
		Long userIdL = Long.parseLong(userId);		
		Account account = accountService.getAccount(userIdL);
		if (account == null){
			return null;
		}
		AccountProfile accountProfile = new AccountProfile();
		accountProfile.setAccount(account);
		accountProfile.setUserId(account.getUserId());
		
		Collection oldpropertys = propertyDao.getProperties(Constants.USER, userIdL);
		accountProfile.setPropertys(oldpropertys);
		return accountProfile;
	}
	
	


	public void updateAccountProfile(EventModel em) {
		AccountProfile accountProfiler = (AccountProfile)em.getModelIF();
		Long userIdL = Long.parseLong(accountProfiler.getAccount().getUserId());
		Collection propss = new ArrayList();
		Iterator iter = accountProfiler.getPropertys().iterator();
		while (iter.hasNext()) {
			Property prop = (Property) iter.next();
			if (!UtilValidate.isEmpty(prop.getName()) && !UtilValidate.isEmpty(prop.getValue()))
				propss.add(prop);
		}
		propertyDao.deleteProperties(Constants.USER, userIdL);
	    propertyDao.updateProperties(Constants.USER, userIdL, propss);
	}

}
