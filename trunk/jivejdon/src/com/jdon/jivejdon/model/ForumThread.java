/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.model;

import java.util.ArrayList;
import java.util.Collection;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.jivejdon.manager.listener.TreeModelRole;
import com.jdon.jivejdon.model.realtime.LobbyPublisherRoleIF;
import com.jdon.jivejdon.model.realtime.Notification;
import com.jdon.jivejdon.model.repository.LazyLoaderRole;
import com.jdon.jivejdon.model.repository.RepositoryRole;
import com.jdon.jivejdon.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.model.subscription.subscribed.ThreadSubscribed;
import com.jdon.jivejdon.model.thread.ThreadTagsVO;
import com.jdon.treepatterns.model.TreeModel;
import com.jdon.util.StringUtil;

/**
 * 
 * ForumThread is ValueObject with entity character
 * 
 * it is modified when message's CRUD operations happen.
 * 
 * it reload from DB will cost, user will browse all forums by ForumThread
 * collection. so when ForumThread be modified should let all users found that
 * this forum has changed or refreshed.
 * 
 * so ForumThread cann't be prototype clone, should be shared. saved in cache.
 * 
 * ForumThread's left is equal to Root ForumMessage
 * 
 * ForumThread = Forum Topic = Root ForumMessage ForumThread相当于主题Topic；
 * 但Topic主要内容放入rootMessage中，可以说相当于所有rootMessage的主题提要，
 * 包括回复rootMessage的最后的一个回帖，包括rootMessage在内的所有帖子数等， 主要服务于显示一个论坛中所有rootMessage集合。
 * ForumThread和Forum之间是N:1关系
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Model
public class ForumThread extends ForumModel {
	private static final long serialVersionUID = 1L;

	private Long threadId;

	/**
	 * the subject of the root message of the thread. This is a convenience
	 * method equivalent to <code>getRootMessage().getSubject()</code>.
	 * 
	 * @return the name of the thread, which is the subject of the root message.
	 */
	private String name;

	// same as rootMessage's creationDate
	private String creationDate;

	// contain some abstract properties
	private Collection propertys;

	private ThreadTagsVO threadTagsVO;

	private volatile Forum forum;

	/**
	 * the root message of a thread. The root message is a special first message
	 * that is intimately tied to the thread for most forumViews. All other
	 * messages in the thread are children of the root message.
	 */
	private volatile ForumMessage rootMessage;

	private volatile ForumThreadState state;

	@Inject
	public LazyLoaderRole lazyLoaderRole;

	@Inject
	public SubPublisherRoleIF subPublisherRole;

	@Inject
	public LobbyPublisherRoleIF lobbyPublisherRole;

	@Inject
	public RepositoryRole repositoryRole;

	@Inject
	public TreeModelRole treeModelRole;

	/**
	 * normal can be cached reused
	 * 
	 * @param rootMessage
	 */
	public ForumThread(ForumMessage rootMessage) {
		this();
		this.rootMessage = rootMessage;
	}

	/**
	 * temp object
	 */
	public ForumThread() {
		this.threadTagsVO = new ThreadTagsVO(this);
		this.propertys = new ArrayList();
		this.state = new ForumThreadState(this);
	}

	/**
	 * @return Returns the creationDate.
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            The creationDate to set.
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Returns the forum.
	 */
	public Forum getForum() {
		return forum;
	}

	/**
	 * @param forum
	 *            The forum to set.
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
		if (this.rootMessage != null) {
			rootMessage.setForum(forum);
		}
	}

	/**
	 * @return Returns the threadId.
	 */
	public Long getThreadId() {
		return threadId;
	}

	/**
	 * @param threadId
	 *            The threadId to set.
	 */
	public void setThreadId(Long threadId) {
		this.threadId = threadId;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	public String getShortname() {
		return StringUtil.shorten(name);
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the propertys.
	 */
	public Collection getPropertys() {
		return propertys;
	}

	/**
	 * @param propertys
	 *            The propertys to set.
	 */
	public void setPropertys(Collection propertys) {
		this.propertys = propertys;
	}

	/**
	 * @return Returns the rootMessage.
	 */
	public ForumMessage getRootMessage() {
		return rootMessage;
	}

	/**
	 * @param rootMessage
	 *            The rootMessage to set.
	 */
	public void setRootMessage(ForumMessage rootMessage) {
		this.rootMessage = rootMessage;
		this.rootMessage.setForumThread(this);
	}

	/**
	 * @return Returns the forumThreadState.
	 */
	public ForumThreadState getState() {
		return state;
	}

	public void setState(ForumThreadState state) {
		this.state = state;
	}

	public Collection getTags() {
		return this.threadTagsVO.getTags();
	}

	public void setTags(Collection tags) {
		this.threadTagsVO.setTags(tags);
	}

	public void changeTags(String[] tagTitles) {
		this.threadTagsVO.changeTags(tagTitles);
	}

	public String[] getTagTitles() {
		return this.threadTagsVO.getTagTitles();
	}

	public void tagsSubscriptionNotify() {
		threadTagsVO.subscriptionNotify();
	}

	public boolean isRoot(ForumMessage message) {
		try {
			if (message.getMessageId().longValue() == getRootMessage().getMessageId().longValue())
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	public synchronized void addNewMessage(ForumMessage forumMessageParent, ForumMessageReply forumMessageReply) {
		try {
			forumMessageReply.setParentMessage(forumMessageParent);
			forumMessageParent.setForumThread(this);
			forumMessageReply.setForumThread(this);

			state.addMessageCount();

			state.setLastPost(forumMessageReply);
			state.setModifiedDate(forumMessageReply.getModifiedDate());
			this.forum.addNewMessage(forumMessageReply);

			Notification notification = new Notification();
			notification.setSource(forumMessageReply);
			lobbyPublisherRole.notifyLobby(notification);

			subPublisherRole.subscriptionNotify(new ThreadSubscribed(this));
			// this.domainEvent.subscriptionNotify(new
			// AccountSubscribed(forumMessageReply.getAccount()));

			// async exec add tree that cost more performance.
			treeModelRole.addChildZToThreadTree(forumMessageReply);
		} catch (Exception e) {
			System.err.print("error in forumThread:" + this.threadId + " " + e);
		}
	}

	public synchronized void moveForum(ForumMessage forumMessage, Forum newForum) {
		if ((isRoot(forumMessage)) && (forumMessage.isLeaf())) {
			setForum(newForum);
			forumMessage.setForum(newForum);
			repositoryRole.moveMessage(forumMessage);
		}
	}

	public synchronized void update(ForumMessage forumMessage) {
		if (isRoot(forumMessage)) {
			this.setRootMessage(forumMessage);
		}
		forumMessage.setForumThread(this);

		state.setLastPost(forumMessage);
		state.setModifiedDate(forumMessage.getModifiedDate());

		this.forum.updateNewMessage(forumMessage);

		if (isRoot(forumMessage)) {// update a message in this thread
			this.setRootMessage(forumMessage);
		}

		changeTags(forumMessage.getMessageVO().getTagTitle());
	}

	public boolean isLeaf(ForumMessage forumMessage) {
		if (!this.isEmbedded()) {
			System.err.print("this thread is not embedded, threadId = " + threadId + " " + this.hashCode());
			return false;
		}

		boolean ret = false;
		try {
			TreeModel treeModel = state.getTreeModel();
			ret = treeModel.isLeaf(forumMessage.getMessageId());
		} catch (Exception e) {
			String error = e + " isLeaf forumMessageId=" + forumMessage.getMessageId();
			System.err.print(error);
		}
		return ret;
	}

	public void viewCountAction(String ip) {
		getState().addViewCount(ip);
	}

	public void setViewCount(int count) {
		getState().setViewCount(count);
	}

}
