package com.jdon.jivejdon.model.query;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jdon.jivejdon.model.ForumThread;

public class HotThreadSpecification {

	public void sortByMessageCount(List threads) {
		Comparator comp = new ThreadReplyComparator();
		Collections.sort(threads, Collections.reverseOrder(comp));
	}

	class ThreadReplyComparator implements Comparator {
		public int compare(Object x, Object y) {
			ForumThread a = (ForumThread) x, b = (ForumThread) y;
			int replya = a.getForumThreadState().getMessageCount();
			int replyb = b.getForumThreadState().getMessageCount();
			return replya - replyb;
		}
	}
}
