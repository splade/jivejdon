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
package com.jdon.jivejdon.manager.subscription.action;

import com.jdon.jivejdon.manager.subscription.SubscriptionAction;
import com.jdon.jivejdon.manager.subscription.SubscriptionNotify;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.messsage.NotifyMessage;

public class WeiboAction implements SubscriptionAction {

	private Subscription subscription;

	public WeiboAction(Subscription subscription) {
		super();
		this.subscription = subscription;
	}

	public WeiboAction() {
		super();
	}

	public void exec(NotifyMessage notifyMessage, SubscriptionNotify subscriptionNotify) {
		if (subscription == null) {
			System.err.print("subscription is null in WeiboAction");
			return;
		}
		// todo

		subscriptionNotify.sinaWeboSubmitter.weiboOAuthParamVO.setUserId("");
		subscriptionNotify.sinaWeboSubmitter.weiboOAuthParamVO.setPasswd("");
		subscriptionNotify.sinaWeboSubmitter.submitWeibo(notifyMessage.getShortMessage().getMessageBody());
	}

}
