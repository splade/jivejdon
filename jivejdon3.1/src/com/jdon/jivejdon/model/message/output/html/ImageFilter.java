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
package com.jdon.jivejdon.model.message.output.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRendering;

/**
 * A ForumMessageFilter that replaces  to <img src=url></img>
 */

public class ImageFilter
    extends MessageRendering {
    
  
  private boolean filteringSubject = false;
  private boolean filteringBody = true;

  public MessageRendering clone(ForumMessage message) {
    ImageFilter filter = new ImageFilter();
    filter.filteringSubject = filteringSubject;
    filter.filteringBody = filteringBody;
    filter.message = message;
    return filter;
  }

  public String applyFilteredSubject() {
    return message.getFilteredSubject();
  }

  public String applyFilteredBody() {
    return convertTags(message.getFilteredBody());
  }


  
  private String convertTags(String str) {
    if (str == null || str.length() == 0) {
      return str;
    }
    String patt = "(\\[img\\])([^\\[]+)(\\[/img\\])";
    Pattern p = Pattern.compile(patt);
    Matcher m = p.matcher(str);
    StringBuffer sb = new StringBuffer();
    int i = 0;
    boolean result = m.find();
    while (result) {
      i++;
      m.appendReplacement(sb, "<img src=\"" + m.group(2) + "\" border='0' >");
      result = m.find();
    }
    m.appendTail(sb);
    return sb.toString();
  }
  
  

}
