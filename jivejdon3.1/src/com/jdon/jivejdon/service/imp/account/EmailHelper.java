package com.jdon.jivejdon.service.imp.account;

import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.util.EmailTask;
import com.jdon.util.Debug;
import com.jdon.util.task.TaskEngine;

public class EmailHelper {
	private final static String module = EmailHelper.class.getName();

	private EmailTask emailTask;

	private EmailDefine emailDefine;

	public EmailHelper(EmailTask emailTask, EmailDefine emailDefine) {
		super();
		this.emailTask = emailTask;
		this.emailDefine = emailDefine;
	}

	public void send(Account account, String newpasswd) {
		Debug.logVerbose("send email ", module);
		String subject = emailDefine.getSubject();
		String body = createEmailBody(account, newpasswd);
		String toEmail = account.getEmail();
		String toName = account.getUsername();
		String fromName = emailDefine.getFromName();
		String fromEmail = emailDefine.getFromName();
		emailTask.addMessage(toName, toEmail, fromName, fromEmail, subject,
				body);
		TaskEngine.addTask(emailTask);
		Debug.logVerbose("email is over", module);

	}

	private String createEmailBody(Account account, String newpasswd) {
		StringBuffer buffer = new StringBuffer(emailDefine.getHeader());
		buffer.append("\n\n").append("username:").append(account.getUsername());
		buffer.append("\n");
		buffer.append("password:").append(newpasswd);
		buffer.append("\n\n");
		buffer.append(emailDefine.getFooter());
		return buffer.toString();
	}

}
