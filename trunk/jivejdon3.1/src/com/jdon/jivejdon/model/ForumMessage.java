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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.SearchableReference;

import com.jdon.jivejdon.model.message.props.MessagePropertyProxy;

/**
 * 
 * ForumMessage is a message in Forum.
 *   
 * ForumMessageDecorator is its child class.
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
@Searchable
public class ForumMessage extends ModelState implements MessagePropertyProxy {    
        
	public final static String PROPERTY_IP = "IP";
	
	public final static String PROPERTY_MASKED = "MASKED";
	
    @SearchableId
    private Long messageId;

    private String creationDate;
    private String modifiedDate;
    private DateFormat dateTime_formatter;//remmeber its datatime, so can transfer String to Date;
    
    private String subject;    
    private String filteredSubject;
    
    @SearchableProperty
    private String body;
    private String filteredBody;
    
    private int rewardPoints;
    private Account account; //owner
    
    private Account operator; //operator this message,maybe Admin or others;
    
    private ForumThread forumThread;
    
    @SearchableReference
    private Forum forum;
    
    //@SearchableReference(refAlias = "property") 
    private Collection propertys;
    //for upload files
    private Collection uploadFiles;
  
    //unused until now
    private Collection keywords;
    
    private HotKeys hotKeys;
    
    public ForumMessage(){
    	propertys = new ArrayList();
    	uploadFiles = new ArrayList();
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
    
    public Account getOperator() {
		return operator;
	}



	public void setOperator(Account operator) {
		this.operator = operator;
	}



	/**
     * @return Returns the body.
     */
    public String getBody() {    
            return body;
    }
    
    /**
     * @return Returns the subject.
     */
    public String getSubject() {    	
            return subject;
    }
	
    public String getFilteredBody() {
    	if (this.filteredBody != null)
		  return this.filteredBody;
    	else
    	  return body;
	}
	
	public void setFilteredBody(String filteredBody) {
		this.filteredBody = filteredBody;
	}
	
	public String getFilteredSubject() {
		if (this.filteredSubject != null)
		   return this.filteredSubject;
		else
		   return this.subject;
	}
	
	public void setFilteredSubject(String filteredSubject) {
		this.filteredSubject = filteredSubject;
	}
  
    /**
     * @param body The body to set.
     */
    public void setBody(String body) {
        this.body = body;
    }
    
    /**
     * @param subject The subject to set.
     */
    public void setSubject(String subject) {
        this.subject = subject;
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
    
    
    
    /**
     * @return Returns the uploadFiles.
     */
    public Collection getUploadFiles() {
        return uploadFiles;
    }
    /**
     * @param uploadFiles The uploadFiles to set.
     */
    public void setUploadFiles(Collection uploadFiles) {
        this.uploadFiles = uploadFiles;
    }
	
	public boolean isRoot() {
		if (getMessageId().longValue() == getForumThread().getRootMessage().getMessageId().longValue()) {
			return true;
		} else
			return false;
	}

	public String getPostip() {
		String ipaddress = "";
		if (this.propertys == null)
			return "not yet load propertys from persistence";
		Iterator iter = propertys.iterator();
		while (iter.hasNext()) {
			Property property = (Property) iter.next();
			if (property.getName().equals(PROPERTY_IP)) {
				ipaddress = property.getValue();
				break;
			}
		}
		return ipaddress;
	}

	/**
	 * this mesages is masked by admin
	 * 
	 * @return boolean
	 */
	public boolean isMasked() {
		if (getPropertyValue(PROPERTY_MASKED) != null)
			return true;
		else 
		    return false;
	}
	
	public void setMasked(boolean masked){
		if (masked)
		   addProperty(PROPERTY_MASKED, PROPERTY_MASKED);
		else
		   removeProperty(PROPERTY_MASKED);			
	}
		
	
	 /**
     * add property to propertys;
     * @param propName
     * @param propValue
     */
    public void addProperty(String propName, String propValue){
        Property property = new Property();            
        property.setName(propName);
        property.setValue(propValue);
        propertys.add(property);
    }
    
    public String getPropertyValue(String propName){
        Iterator iter = propertys.iterator();
        while(iter.hasNext()){
        	Property property  = (Property)iter.next();
        	if (property.getName().equalsIgnoreCase(propName)){
        		return property.getValue();
        	}
        }
        return null;
    }
    
    public void removeProperty(String propName){
        Iterator iter = propertys.iterator();
        Property removePorp = null;
        while(iter.hasNext()){
        	Property property  = (Property)iter.next();
        	if (property.getName().equalsIgnoreCase(propName)){
        		removePorp = property;
        		break;
        	}
        }
        propertys.remove(removePorp);
    }



	public DateFormat getDateTime_formatter() {
		return dateTime_formatter;
	}



	public void setDateTime_formatter(DateFormat dateTime_formatter) {
		this.dateTime_formatter = dateTime_formatter;
	}



	public Collection getKeywords() {
		return keywords;
	}

	public void setKeywords(Collection keywords) {
		this.keywords = keywords;
	}



	public HotKeys getHotKeys() {
		return hotKeys;
	}



	public void setHotKeys(HotKeys hotKeys) {
		this.hotKeys = hotKeys;
	}
    
	
}
