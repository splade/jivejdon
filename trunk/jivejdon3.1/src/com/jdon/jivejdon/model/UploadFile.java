/*
 * Copyright 2003-2006 the original author or authors.
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

import org.apache.struts.upload.FormFile;

/**
 * @author banq(http://www.jdon.com)
 *
 */
public class UploadFile extends com.jdon.strutsutil.file.UploadFile{
  
    private FormFile theFile;
    
    private String path;
    
    private boolean isNew = true;
    
    public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	/**
     * @return Returns the theFile.
     */
    public FormFile getTheFile() {
        return theFile;
    }
    /**
     * @param theFile The theFile to set.
     */
    public void setTheFile(FormFile theFile) {
        this.theFile = theFile;
    }
    
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}        
   
}
