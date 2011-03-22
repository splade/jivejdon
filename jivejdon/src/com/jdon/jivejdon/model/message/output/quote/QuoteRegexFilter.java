package com.jdon.jivejdon.model.message.output.quote;

import java.util.ArrayList;
import java.util.List;

import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.model.ForumMessage;

/**
 * Qutote filter is responsible for replacing the [quoto][/quote] tags in the body of ForumMessage
 * @author oojdon
 *
 */
public class QuoteRegexFilter implements MessageRenderSpecification{
	private List<RegexFilter> filters = new ArrayList<RegexFilter>();
		
	public QuoteRegexFilter(){
		filters.add(new SummarytFilter());
		filters.add(new FromFilter());
		filters.add(new AuthorDateFilter());
	}
	
	@Override
	public ForumMessage render(ForumMessage message) {
		MessageVO messageVO = message.getMessageVO();
		String body = messageVO.getBody();
		for(RegexFilter filter : filters){
			body = filter.doFilter(body);
		}			
		body = body.replace("[/quote]", "</div>");
		messageVO.setBody(body);
		return message;
	}
	

}
