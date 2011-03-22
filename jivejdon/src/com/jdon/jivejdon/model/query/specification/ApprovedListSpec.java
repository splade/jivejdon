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

	public boolean isApproved(ForumThread thread) {
		if (isGoodBlog(thread) || isExcelledDiscuss(thread)) {
			return true;
		} else
			return false;
	}

	protected boolean isGoodBlog(ForumThread thread) {
		return (hasTags(thread) && isGoodAuthor(thread, 10) && isDigged(thread, 1) && hasReply(thread, 1));
	}

	protected boolean isExcelledDiscuss(ForumThread thread) {
		return (hasTags(thread) && isDigged(thread, 3) && hasReply(thread, 3));

	}

	private boolean hasTags(ForumThread thread) {
		Collection tags = thread.getTags();
		if (tags != null && tags.size() >= 1)
			return true;
		else
			return false;
	}

	private boolean isGoodAuthor(ForumThread thread, int throttle) {
		Account account = thread.getRootMessage().getAccount();
		if (account.getMessageCount() >= throttle)
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
