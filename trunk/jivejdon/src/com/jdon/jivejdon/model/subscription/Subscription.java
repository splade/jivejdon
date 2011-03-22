package com.jdon.jivejdon.model.subscription;

import com.jdon.annotation.Model;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;

@Model
public class Subscription {

	private Long subscriptionId;

	private Account account;

	private Subscribed subscribed;

	private String creationDate;

	private boolean sendmsg;

	private boolean sendemail;

	public Subscription() {
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

	public boolean isSendmsg() {
		return sendmsg;
	}

	public void setSendmsg(boolean sendmsg) {
		this.sendmsg = sendmsg;
	}

	public boolean isSendemail() {
		return sendemail;
	}

	public void setSendemail(boolean sendemail) {
		this.sendemail = sendemail;
	}

	public void updateSubscriptionCount(int count) {
		subscribed.updateSubscriptionCount(count);
	}
}
