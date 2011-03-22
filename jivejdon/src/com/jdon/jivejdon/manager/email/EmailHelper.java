package com.jdon.jivejdon.manager.email;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.jdon.async.EventProcessor;
import com.jdon.async.observer.ObservableAdapter;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ShortMessage;
import com.jdon.jivejdon.util.EmailTask;
import com.jdon.util.Debug;

public class EmailHelper {
	private final static String module = EmailHelper.class.getName();

	private ForgotPasswdEmailDefine emailDefine;

	private ValidateCodeEmailDefine validateCodeEmailDefine;

	private EventProcessor ep;

	private Map<String, Integer> validatecodes;

	public EmailHelper(ForgotPasswdEmailDefine emailDefine, ValidateCodeEmailDefine validateCodeEmailDefine, EventProcessor ep) {
		super();
		this.emailDefine = emailDefine;
		this.validateCodeEmailDefine = validateCodeEmailDefine;
		this.ep = ep;
		this.validatecodes = new HashMap(20);
	}

	private void sendComposer(EmailTask emailTask) {
		EmailTaskListerner emailTaskListerner = new EmailTaskListerner(emailTask);
		ObservableAdapter subscriptionObservable = new ObservableAdapter(ep);
		subscriptionObservable.addObserver(emailTaskListerner);
		subscriptionObservable.notifyObservers(null);
	}

	public void sendForgotPasswdEmail(Account account, String newpasswd) {
		Debug.logVerbose("send email ", module);
		String subject = emailDefine.getSubject();
		String body = createForgotPasswdEmailBody(account, newpasswd);
		String toEmail = account.getEmail();
		String toName = account.getUsername();
		String fromName = emailDefine.getFromName();
		String fromEmail = emailDefine.getFromName();

		EmailTask emailTask = new EmailTask(emailDefine.getJndiname());
		emailTask.addMessage(toName, toEmail, fromName, fromEmail, subject, body, EmailTask.NOHTML_FORMAT);
		sendComposer(emailTask);
		Debug.logVerbose("email is over", module);

	}

	private String createForgotPasswdEmailBody(Account account, String newpasswd) {
		StringBuffer buffer = new StringBuffer(emailDefine.getHeader());
		buffer.append("\n\n").append("username:").append(account.getUsername());
		buffer.append("\n");
		buffer.append("password:").append(newpasswd);
		buffer.append("\n\n");
		buffer.append(emailDefine.getFooter());
		return buffer.toString();
	}

	public void sendValidateCodeEmail(Account account) {
		Debug.logVerbose("sendValidateCodeEmail  ", module);

		Random r = new Random();
		int validateCode = r.nextInt();
		if (validatecodes.size() > 100) {// no out of memeory
			validatecodes.clear();
		}
		validatecodes.put(account.getUserId(), validateCode);

		String body = createValidateEmailBody(account, validateCode);

		String subject = validateCodeEmailDefine.getTitle();
		String toEmail = account.getEmail();
		String toName = account.getUsername();
		String fromName = validateCodeEmailDefine.getFromName();
		String fromEmail = validateCodeEmailDefine.getFromEmail();
		EmailTask emailTask = new EmailTask(validateCodeEmailDefine.getJndiname());
		emailTask.addMessage(toName, toEmail, fromName, fromEmail, subject, body, EmailTask.HTML_FORMAT);
		sendComposer(emailTask);
		Debug.logVerbose("email is over", module);

	}

	private String createValidateEmailBody(Account account, int validateCode) {
		StringBuffer buffer = new StringBuffer(validateCodeEmailDefine.getBody());
		buffer.append("\n\n");
		String url = validateCodeEmailDefine.getUrl() + "&validateCode=" + validateCode + "&account.userId=" + account.getUserId();
		buffer.append("<a href='").append(url).append("' target=_blank>").append(url).append("</a>");
		buffer.append("\n\n");
		return buffer.toString();
	}

	public boolean emailValidate(String userId, int validateCode) {
		if (validatecodes.size() > 20) // make the map immutable, prevent for
			// memory leak.
			validatecodes.clear();
		if (validatecodes.containsKey(userId)) {
			if (validatecodes.get(userId).intValue() == validateCode) {
				validatecodes.remove(userId);
				return true;
			}
		}
		return false;
	}

	public void sendSubscriptionEmail(Account account, ShortMessage sm) {
		Debug.logVerbose("sendSubscriptionEmail  ", module);
		if (!account.isEmailValidate()) {
			Debug.logWarning("this email not Validate :" + account.getEmail());
			return;
		}

		String body = sm.getMessageBody();
		String subject = sm.getMessageTitle();
		String toEmail = account.getEmail();
		String toName = account.getUsername();
		String fromName = sm.getMessageFrom();
		String fromEmail = sm.getMessageFrom();
		EmailTask emailTask = new EmailTask(validateCodeEmailDefine.getJndiname());
		emailTask.addMessage(toName, toEmail, fromName, fromEmail, subject, body, EmailTask.HTML_FORMAT);
		sendComposer(emailTask);
		Debug.logVerbose("email is over", module);

	}

}
