package com.jdon.jivejdon.model.query.specification;

import java.util.Collection;

import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;

public class ApprovedListSpec extends ThreadListSpec {

	private int needCount = 50;

	public ApprovedListSpec() {
		sorttableName = "creationDate";
	}

	public boolean isApproved(ForumThread thread, Account account) {
		if (isGoodBlog(thread, account) || isExcelledDiscuss(thread)) {
			return true;
		} else
			return false;
	}

	protected boolean isGoodBlog(ForumThread thread, Account account) {
		return (hasTags(thread, 1) && isGoodAuthor(account, 10) && isDigged(thread, 1) && hasReply(thread, 1));
	}

	protected boolean isExcelledDiscuss(ForumThread thread) {
		return (hasTags(thread, 2) && isDigged(thread, 3) && hasReply(thread, 3));

	}

	private boolean hasTags(ForumThread thread, int throttle) {
		Collection tags = thread.getTags();
		if (tags != null && tags.size() >= throttle)
			return true;
		else
			return false;
	}

	private boolean isGoodAuthor(Account account, int throttle) {
		if (account.getMessageCountNow() >= throttle)
			return true;
		else
			return false;
	}

	private boolean isDigged(ForumThread thread, int digcount) {
		ForumMessage message = thread.getRootMessage();
		if (message.getMessageDigVo().getDigCount() >= digcount)
			return true;
		else
			return false;
	}

	private boolean hasReply(ForumThread thread, int throttle) {
		if (thread.getState().getMessageCount() >= throttle)
			return true;
		else
			return false;
	}

	public int getNeedCount() {
		return needCount;
	}

	public void setNeedCount(int needCount) {
		this.needCount = needCount;
	}

}
