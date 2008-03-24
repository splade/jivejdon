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
package com.jdon.jivejdon.presentation.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.Property;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class MessageForm extends BaseForm {
	private int bodyMaxLength = 4096;

	private Long messageId;

	private String creationDate;

	private String modifiedDate;

	private String subject;

	private String body;

	private int rewardPoints;

	private ForumThread forumThread;

	private Forum forum;

	private Collection propertys;

	private com.jdon.jivejdon.model.Account account;

	private ForumMessage parentMessage;

	private boolean authenticated = true;

	/**
	 * for initMessage of the ForumMessageService
	 */
	public MessageForm() {
		forum = new Forum(); // for parameter forum.forumId=xxx
		forumThread = new ForumThread();
		propertys = new ArrayList();
		account = new com.jdon.jivejdon.model.Account();
		parentMessage = new ForumMessage();
	}

	/**
	 * @return Returns the account.
	 */
	public com.jdon.jivejdon.model.Account getAccount() {
		return account;
	}

	/**
	 * @param account The account to set.
	 */
	public void setAccount(com.jdon.jivejdon.model.Account account) {
		this.account = account;
	}

	/**
	 * @return Returns the creationDate.
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate The creationDate to set.
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Returns the messageId.
	 */
	public Long getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId The messageId to set.
	 */
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return Returns the modifiedDate.
	 */
	public String getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate The modifiedDate to set.
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return Returns the propertys.
	 */
	public Collection getPropertys() {
		return propertys;
	}

	/**
	 * @param propertys The propertys to set.
	 */
	public void setPropertys(Collection propertys) {
		this.propertys = propertys;
	}

	/**
	 * @return Returns the rewardPoints.
	 */
	public int getRewardPoints() {
		return rewardPoints;
	}

	/**
	 * @param rewardPoints The rewardPoints to set.
	 */
	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	/**
	 * @return Returns the parentMessage.
	 */
	public ForumMessage getParentMessage() {
		return parentMessage;
	}

	/**
	 * @param parentMessage The parentMessage to set.
	 */
	public void setParentMessage(ForumMessage parentMessage) {
		this.parentMessage = parentMessage;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the forumThread.
	 */
	public ForumThread getForumThread() {
		return forumThread;
	}

	/**
	 * @param forumThread The forumThread to set.
	 */
	public void setForumThread(ForumThread forumThread) {
		this.forumThread = forumThread;
	}

	/**
	 * @return Returns the forum.
	 */
	public Forum getForum() {
		return forum;
	}

	/**
	 * @param forum The forum to set.
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
		if (getMethod() == null || !getMethod().equalsIgnoreCase("delete") && (!getMethod().equalsIgnoreCase("deleteRecursiveMessage"))) {
			addErrorIfStringEmpty(errors, "subject is required.", this.getSubject());
			addErrorIfStringEmpty(errors, "body is required.", getBody());
			if (this.getSubject().length() > 100) {
				errors.add("subject lengt too long");
			}
			if (getBody().length() >= bodyMaxLength) {
				errors.add("body's max length should < " + bodyMaxLength);
			}
		}
	}

	public int getBodyMaxLength() {
		return bodyMaxLength;
	}

	public void setBodyMaxLength(int bodyMaxLength) {
		this.bodyMaxLength = bodyMaxLength;
	}

	public boolean isMasked() {
		if (getPropertyValue(ForumMessage.PROPERTY_MASKED) != null)
			return true;
		else
			return false;
	}

	public void setMasked(boolean masked) {
		if (masked)
			addProperty(ForumMessage.PROPERTY_MASKED, ForumMessage.PROPERTY_MASKED);
		else
			removeProperty(ForumMessage.PROPERTY_MASKED);
	}

	public void addProperty(String propName, String propValue) {
		Property property = new Property();
		property.setName(propName);
		property.setValue(propValue);
		propertys.add(property);
	}

	public String getPropertyValue(String propName) {
		Iterator iter = propertys.iterator();
		while (iter.hasNext()) {
			Property property = (Property) iter.next();
			if (property.getName().equalsIgnoreCase(propName)) {
				return property.getValue();
			}
		}
		return null;
	}

	public void removeProperty(String propName) {
		Iterator iter = propertys.iterator();
		Property removePorp = null;
		while (iter.hasNext()) {
			Property property = (Property) iter.next();
			if (property.getName().equalsIgnoreCase(propName)) {
				removePorp = property;
				break;
			}
		}
		propertys.remove(removePorp);
	}

}
