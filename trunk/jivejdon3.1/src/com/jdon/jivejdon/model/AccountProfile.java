package com.jdon.jivejdon.model;

import java.util.Collection;

import com.jdon.controller.model.Model;

public class AccountProfile extends Model{
	
	private String userId;
	
	private Account account;
	
	private UploadFile uploadFile;
	private String signature;
	private Collection propertys;
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public UploadFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UploadFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public Collection getPropertys() {
		return propertys;
	}

	public void setPropertys(Collection propertys) {
		this.propertys = propertys;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	

}
