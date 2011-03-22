package com.jdon.jivejdon.presentation.action.admin;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.ModelIF;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.strutsutil.ModelListAction;

public class UserListAction extends ModelListAction {

	@Override
	public PageIterator getPageIterator(HttpServletRequest request, int start,
			int count) {
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
		return accountService.getAccounts(start, count);
	}
	
	 public Object findModelIFByKey(HttpServletRequest request, Object key) {
		 AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
		 return accountService.getAccount((Long)key);
		 
	 }

}
