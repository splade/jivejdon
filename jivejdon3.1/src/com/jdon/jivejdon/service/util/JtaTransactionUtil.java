package com.jdon.jivejdon.service.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;

public class JtaTransactionUtil {
	
	private TransactionManager transactionManager;

	public JtaTransactionUtil(String txName) {
		try {
			transactionManager = (TransactionManager)new InitialContext().lookup(txName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}


	public void rollback(TransactionManager tx) {
		if (tx != null){
	     	try{tx.rollback();}catch(Exception ex){}
	     }
		
	}
	
	 

}
