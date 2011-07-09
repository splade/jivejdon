package com.jdon.jivejdon.model.subscription;

import com.jdon.annotation.Model;
import com.jdon.jivejdon.manager.subscription.SubscriptionAction;
import com.jdon.jivejdon.manager.subscription.SubscriptionActionHolder;
import com.jdon.jivejdon.manager.subscription.SubscriptionNotify;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.subscription.messsage.NotifyMessage;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;

@Model
public class Subscription {

	private Long subscriptionId;

	private Account account;

	private Subscribed subscribed;

	private String creationDate;

	private SubscriptionActionHolder subscriptionActionHolder;

	public Subscription() {
		subscriptionActionHolder = new SubscriptionActionHolder();

	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Subscribed getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(Subscribed subscribed) {
		this.subscribed = subscribed;
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public int getSubscribeType() {
		if (subscribed != null)
			return subscribed.getSubscribeType();
		return -1;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void updateSubscriptionCount(int count) {
		subscribed.updateSubscriptionCount(count);
	}

	// for Jsp set/get see subAccountList.jsp
	public boolean getActionType(String actionClassS) {
		Class actionCalss = null;
		try {
			actionCalss = Class.forName(actionClassS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return subscriptionActionHolder.hasActionType(actionCalss);
	}

	public SubscriptionActionHolder getSubscriptionActionHolder() {
		return subscriptionActionHolder;
	}

	public void setSubscriptionActionHolder(SubscriptionActionHolder subscriptionActionHolder) {
		this.subscriptionActionHolder = subscriptionActionHolder;
	}

	public void addAction(SubscriptionAction subscriptionAction) {
		subscriptionActionHolder.addAction(subscriptionAction);
	}

	public void doAction(NotifyMessage notifyMessage, SubscriptionNotify subscriptionNotify) {
		this.subscriptionActionHolder.doAction(notifyMessage, subscriptionNotify);

	}

}
