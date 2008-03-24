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
package com.jdon.jivejdon.model.message.upload;

import java.util.Collection;
import java.util.Iterator;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.UploadFile;
import com.jdon.jivejdon.model.message.MessageRendering;

/**
 * @author banq(http://www.jdon.com)
 *
 */
public class MessageRenderingImage  extends MessageRendering{
    private final static String SHOW_NAME = "/imageShow.jsp";  
    
    public MessageRendering clone(ForumMessage message) {
        MessageRenderingImage filter = new MessageRenderingImage();
        filter.message = message;
        return filter;
      }
    
    public String applyFilteredBody() {
        return appendTags(message.getFilteredBody());
      }
    /**
     * return the String:
     * 
     * [img]/imageShow.jsp?id=3464[/img]
     *
     * @param str
     * @return
     */
    public  String appendTags(String str){
    	Collection uploadFiles = message.getUploadFiles();
    	if ((uploadFiles == null) || (uploadFiles.size() == 0)){
    		return str;
    	}
    	UploadHelper uploadHelper = new UploadHelper();
        StringBuffer sb = new StringBuffer(str);
        sb.append("\n");
        Iterator iter = uploadFiles.iterator();
        while(iter.hasNext()){
            UploadFile uploadFile = (UploadFile)iter.next();
            if (uploadHelper.isImage(uploadFile.getName())){
                append(sb, uploadFile);
            }
        }
        return sb.toString();
    }
    
    //see com.jdon.jivejdon.presentation.action.UploadShowAction
    private void append(StringBuffer sb, UploadFile uploadFile){
        sb.append("[img]");
        sb.append(uploadFile.getPath());
        sb.append(SHOW_NAME);
        sb.append("?type=images/jpeg");
        sb.append("&id=").append(uploadFile.getId());
        sb.append("[/img]");
        sb.append("\n");
    }
}
