package com.jdon.jivejdon.model.subscription.messsage;

import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ShortMessage;
import com.jdon.jivejdon.model.ThreadTag;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.util.StringUtil;

public class TagNotifyMessage implements NotifyMessage {

	private Subscription subscription;
	private String notifyUrlTemp;
	private ShortMessage shortMessage;

	public TagNotifyMessage(String notifyTitle, String notifyUrlTemp, String notifier) {
		super();
		shortMessage = new ShortMessage();
		shortMessage.setMessageTitle(notifyTitle);
		shortMessage.setMessageFrom(notifier);
		this.notifyUrlTemp = notifyUrlTemp;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
		//
		this.shortMessage.setAccount(subscription.getAccount());
		this.shortMessage.setMessageTo(subscription.getAccount().getUsername());
	}

	public String getNotifyUrlTemp() {
		return notifyUrlTemp;
	}

	public TagNotifyMessage clone() {
		return new TagNotifyMessage(shortMessage.getMessageTitle(), this.notifyUrlTemp, shortMessage.getMessageFrom());
	}

	public void fullfillNotifyMessage(Subscribed subscribed) {
		ThreadTag tag = (ThreadTag) subscribed.getSubscribed()[0];
		ForumThread thread = (ForumThread) subscribed.getSubscribed()[1];
		// http://www.jdon.com/jivejdon/thread/threadId#messageId
		String newSubscribedUrl = StringUtil.replace(getNotifyUrlTemp(), "tagId", tag.getTagID().toString());
		String body = shortMessage.getMessageTitle() + "<br> <a href='" + newSubscribedUrl + "' target=_blank>" + thread.getName() + "</a> ";
		body = body + ":" + thread.getName();
		shortMessage.setMessageBody(body);
		shortMessage.setMessageTitle(shortMessage.getMessageTitle() + ":" + subscribed.getName());
	}

	public ShortMessage getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(ShortMessage shortMessage) {
		this.shortMessage = shortMessage;
	}

}