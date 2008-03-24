package com.jdon.jivejdon.presentation.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.UploadFile;

public class AccountProfileForm extends BaseForm {
	
	private String userId;
	
	private Account account;

	private UploadFile uploadFile;

	private String signature;

	private Collection propertys;
	
	private int maxSize = 20;
	
	public AccountProfileForm(){
		account = new Account();
		propertys = new ArrayList();
		propertys = new ArrayList();
		for(int i=0; i<maxSize; i++){
			propertys.add(new Property());
		} 
	}

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
	
	public Property getProperty(int index) {		
	        return (Property)((List)propertys).get(index);
	}
	

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void doValidate(ActionMapping mapping, HttpServletRequest request,
			List errors) {
		 if (getPropertys().size() > maxSize) {
             errors.add("max length is " + maxSize);
     }

	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
