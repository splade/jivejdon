package com.jdon.jivejdon.manager.subscription;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.domain.message.DomainMessage;
import com.jdon.domain.message.MessageListener;
import com.jdon.jivejdon.manager.email.SubscriptionEmail;
import com.jdon.jivejdon.manager.shortmessage.ShortMessageFactory;
import com.jdon.jivejdon.manager.weibo.SinaWeboSubmitter;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.messsage.NotifyMessage;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.dao.SubscriptionDao;

@Component("subscriptionNotify")
public class SubscriptionNotify implements MessageListener {
	private final static Logger logger = Logger.getLogger(SubscriptionNotify.class);

	private SubscriptionDao subscriptionDao;
	public ShortMessageFactory shortMessageFactory;
	private NotifyMessageFactory notifyMessageFactory;

	private AccountFactory accountFactory;
	public SubscriptionEmail subscriptionEmail;
	public SinaWeboSubmitter sinaWeboSubmitter;

	public SubscriptionNotify(SubscriptionDao subscriptionDao, ShortMessageFactory shortMessageFactory, AccountFactory accountFactory,
			SubscriptionEmail subscriptionEmail, NotifyMessageFactory notifyMessageFactory, SinaWeboSubmitter sinaWeboSubmitter) {
		this.subscriptionDao = subscriptionDao;
		this.shortMessageFactory = shortMessageFactory;
		this.accountFactory = accountFactory;
		this.subscriptionEmail = subscriptionEmail;
		this.notifyMessageFactory = notifyMessageFactory;
		this.sinaWeboSubmitter = sinaWeboSubmitter;

	}

	public void action(DomainMessage eventMessage) {
		Subscribed subscribed = (Subscribed) eventMessage.getEventSource();
		logger.debug("subscribed action " + subscribed.getName());
		sendSub(subscribed);

	}

	private void sendSub(Subscribed subscribed) {
		try {
			Collection<Subscription> subscriptions = subscriptionDao.getSubscriptionsForsubscribed(subscribed.getSubscribeId());
			int i = 1;
			for (Subscription sub : subscriptions) {

				sub.setSubscribed(subscribed);
				sub.setAccount(accountFactory.getFullAccount(sub.getAccount()));
				NotifyMessage notifyMessage = notifyMessageFactory.create(sub);
				if (notifyMessage == null)
					return;
				notifyMessage.fullfillNotifyMessage(subscribed);
				sub.doAction(notifyMessage, this);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
