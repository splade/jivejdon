package com.jdon.jivejdon.repository.listener;

import com.jdon.annotation.Component;
import com.jdon.domain.message.DomainMessage;
import com.jdon.domain.message.MessageListener;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.proptery.MessagePropertys;
import com.jdon.jivejdon.repository.dao.PropertyDao;


@Component("loadMessageDigCount")
public class LoadMessageDigCount implements MessageListener {
	private PropertyDao propertyDao;
	
	
	public LoadMessageDigCount(PropertyDao propertyDao) {
		super();
		this.propertyDao = propertyDao;
	}
	
	public void action(DomainMessage eventMessage) {
		Long messageId = (Long)eventMessage.getEventSource();
		Property p = propertyDao.getMessageProperty(messageId, MessagePropertys.DIG_NUMBER);
		
		if(p == null)
			eventMessage.setEventResult(0);
		else {
			String number = p.getValue();
			
			eventMessage.setEventResult(Integer.valueOf(number));
		}
		
	}

}
