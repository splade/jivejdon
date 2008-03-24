/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.model.message.output.hotkeys;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.HotKeys;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.message.MessageRendering;
import com.jdon.util.StringUtil;

/**
 * replace hot keywords with HotKeys added by administrator
 * @author banq
 *
 */
public class HotKeysFilter extends MessageRendering {
	private String prefix_regEx = "([\\u4e00-\\u9fa5]|[\\s])(?i)"; // chinese or whitespace
	private String suffix_regEx = "";
	
	public MessageRendering clone(ForumMessage message) {
		HotKeysFilter filter = new HotKeysFilter();
		filter.message = message;
		filter.prefix_regEx = prefix_regEx;
		filter.suffix_regEx = suffix_regEx;
		return filter;
	}

	/**
	 * space + keyword + space will be replaced
	 */
	public String applyFilteredBody() {
		String body = message.getFilteredBody();
		HotKeys hotKeys = message.getHotKeys();
		
		Iterator iter = hotKeys.getProps().iterator();
		while(iter.hasNext()){
			Property prop = (Property)iter.next();
			String regEx = prefix_regEx + prop.getName() + suffix_regEx;
			String replcaement =  getKeyUrlStr(prop.getName(), prop.getValue());
		    body = body.replaceAll(regEx, "$1" + replcaement);
		}
        return body;		
      
	}
	
	private String getKeyUrlStr(String name, String url){
		StringBuffer bf = new StringBuffer();
		bf.append("<a href='");
		bf.append(url);
		bf.append("' target='_blank' title='??'><b>");
		bf.append(name);
		bf.append("</b></a>");
		return bf.toString();
	}

	public String getPrefix_regEx() {
		return prefix_regEx;
	}

	public void setPrefix_regEx(String prefix_regEx) {
		if (prefix_regEx != null)
			this.prefix_regEx = prefix_regEx;
	}

	public String getSuffix_regEx() {
		return suffix_regEx;
	}

	public void setSuffix_regEx(String suffix_regEx) {
		if (suffix_regEx != null)
		   this.suffix_regEx = suffix_regEx;
	}

	
	
}
