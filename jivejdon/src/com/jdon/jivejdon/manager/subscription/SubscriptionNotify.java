package com.jdon.jivejdon.manager.subscription;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.domain.message.DomainMessage;
import com.jdon.domain.message.MessageListener;
import com.jdon.jivejdon.manager.email.SubscriptionEmail;
import com.jdon.jivejdon.manager.shortmessage.ShortMessageFactory;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ShortMessage;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.messsage.NotifyMessage;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.dao.SubscriptionDao;

@Component("subscriptionNotify")
public class SubscriptionNotify implements MessageListener {
	private final static Logger logger = Logger.getLogger(SubscriptionNotify.class);

	private SubscriptionDao subscriptionDao;
	private ShortMessageFactory shortMessageFactory;
	private NotifyMessageFactory notifyMessageFactory;

	private AccountFactory accountFactory;
	private SubscriptionEmail subscriptionEmail;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public SubscriptionNotify(SubscriptionDao subscriptionDao, ShortMessageFactory shortMessageFactory, AccountFactory accountFactory,
			SubscriptionEmail subscriptionEmail, NotifyMessageFactory notifyMessageFactory) {
		this.subscriptionDao = subscriptionDao;
		this.shortMessageFactory = shortMessageFactory;
		this.accountFactory = accountFactory;
		this.subscriptionEmail = subscriptionEmail;
		this.notifyMessageFactory = notifyMessageFactory;

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
				if (sub.isSendmsg())
					sendNotifyShortMessage(notifyMessage.getShortMessage());
				if (sub.isSendemail()) {
					sendNotifyEmailMessage(sub, notifyMessage.getShortMessage(), i);
					i = i + 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendNotifyShortMessage(ShortMessage sm) {
		try {
			shortMessageFactory.sendShortMessage(sm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendNotifyEmailMessage(Subscription sub, ShortMessage sm, int i) {
		try {
			sendLater(sub.getAccount(), sm, i);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendLater(final Account account, final ShortMessage sm, final int i) {
		final Runnable sender = new Runnable() {
			public void run() {
				try {
					subscriptionEmail.send(account, sm);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		// send per five min * count.
		int delay = i * 300;// 300
		scheduler.schedule(sender, delay, TimeUnit.SECONDS);
	}

}
