package com.jdon.jivejdon.service.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;

import org.apache.log4j.Logger;

public class JtaTransactionUtil {
	private final static Logger logger = Logger.getLogger(JtaTransactionUtil.class);
	private static String JTA = "JTA";

	private TransactionManager transactionManager;

	public JtaTransactionUtil(String[] txpram) {
		if (txpram[0].equals(JTA)) {
			try {
				transactionManager = (TransactionManager) new InitialContext().lookup(txpram[1]);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void beginTransaction() throws Exception {
		if (!checkIsJTA())
			return;
		transactionManager.begin();
	}

	public void commitTransaction() throws Exception {
		if (!checkIsJTA())
			return;
		transactionManager.commit();
	}

	public void rollback() {
		if (transactionManager != null) {
			try {
				transactionManager.rollback();
			} catch (Exception ex) {
			}
		}
	}

	private boolean checkIsJTA() {
		boolean jta = (transactionManager != null ? true : false);
		if (!jta) {
			logger.debug("use autoCommit");
		}
		return jta;
	}
}
