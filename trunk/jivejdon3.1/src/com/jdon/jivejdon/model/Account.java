package com.jdon.jivejdon.model;

import com.jdon.controller.model.Model;
/**
 * we have a SSO server, all auth information
 * will be save to the sso server, and in this system,
 * we keep some additional fields.
 *
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class Account extends Model {

	private String userId;

	private String password;

	private String username;

	private String email;

	private String roleName;

	private boolean emailVisible;

	private String creationDate;

	private String modifiedDate;

	private String postIP;

	private int messageCount;

	private Reward reward;

	private String passwdanswer;

	private String passwdtype;

	private boolean anonymous;

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
	 * @return Returns the emailVisible.
	 */
	public boolean isEmailVisible() {
		return emailVisible;
	}

	/**
	 * @param emailVisible The emailVisible to set.
	 */
	public void setEmailVisible(boolean emailVisible) {
		this.emailVisible = emailVisible;
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
	 * @return Returns the reward.
	 */
	public Reward getReward() {
		return reward;
	}

	/**
	 * @param reward The reward to set.
	 */
	public void setReward(Reward reward) {
		this.reward = reward;
	}

	/**
	 * @return Returns the postIP.
	 */
	public String getPostIP() {
		return postIP;
	}

	/**
	 * @param postIP The postIP to set.
	 */
	public void setPostIP(String postIP) {
		this.postIP = postIP;
	}

	/**
	 * @return Returns the userId.
	 */
	public Long getUserIdLong() {
		return new Long(this.getUserId());
	}

	/**
	 * @param userId The userId to set.
	 */
	public void setUserIdLong(Long userId) {
		this.setUserId(userId.toString().trim());
	}

	/**
	 * @return Returns the messageCount.
	 */
	public int getMessageCount() {
		return messageCount;
	}

	/**
	 * @param messageCount The messageCount to set.
	 */
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	/**
	 * @return Returns the passwdanswer.
	 */
	public String getPasswdanswer() {
		return passwdanswer;
	}

	/**
	 * @param passwdanswer The passwdanswer to set.
	 */
	public void setPasswdanswer(String passwdanswer) {
		this.passwdanswer = passwdanswer;
	}

	/**
	 * @return Returns the passwdtype.
	 */
	public String getPasswdtype() {
		return passwdtype;
	}

	/**
	 * @param passwdtype The passwdtype to set.
	 */
	public void setPasswdtype(String passwdtype) {
		this.passwdtype = passwdtype;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

}