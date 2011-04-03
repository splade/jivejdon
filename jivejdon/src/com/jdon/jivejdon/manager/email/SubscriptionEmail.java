/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.manager.email;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ShortMessage;
import com.jdon.jivejdon.util.EmailTask;
import com.jdon.util.Debug;

@Component("subscriptionEmail")
public class SubscriptionEmail {
	private final static String module = SubscriptionEmail.class.getName();

	private EmailHelper emailHelper;

	private SubscriptionEmailParams subscriptionEmailParam;

	public SubscriptionEmail(EmailHelper emailHelper, SubscriptionEmailParams subscriptionEmailParam) {
		super();
		this.emailHelper = emailHelper;
		this.subscriptionEmailParam = subscriptionEmailParam;
	}

	public void send(Account account, ShortMessage sm) {
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
		String fromEmail = subscriptionEmailParam.getFromEmail();
		EmailTask emailTask = new EmailTask(subscriptionEmailParam.getJndiname());
		emailTask.addMessage(toName, toEmail, fromName, fromEmail, subject, body, EmailTask.HTML_FORMAT);
		emailHelper.sendComposer(emailTask);
		Debug.logVerbose("email is over", module);

	}

}
