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

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;


/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
@Searchable
public class Forum extends ModelState {
	
	@SearchableId
    private Long forumId;
	
	@SearchableProperty
    private String name;
    private String description;
    private String creationDate;
    
    /**
     * the forum modified date, not the message modified date in 
     * the forum,
     */
    private String modifiedDate;
    private Collection propertys;
    
    
	/**
	 * @link aggregation
	 */
	
    private ForumState forumState;         

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
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /**
     * @return Returns the forumId.
     */
    public Long getForumId() {
        return forumId;
    }
    /**
     * @param forumId The forumId to set.
     */
    public void setForumId(Long forumId) {
        this.forumId = forumId;
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
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
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
     * @param propertys The propertys to set.
     */
    public void setPropertys(Collection propertys) {
        this.propertys = propertys;
    }
    
    
  
    /**
     * @return Returns the forumState.
     */
    public ForumState getForumState() {
        return forumState;
    }
    /**
     * @param forumState The forumState to set.
     */
    public void setForumState(ForumState forumState) {
        this.forumState = forumState;
    }
    
    
  
}
