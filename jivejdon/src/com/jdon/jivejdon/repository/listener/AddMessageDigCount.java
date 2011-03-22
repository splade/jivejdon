package com.jdon.jivejdon.repository.listener;

import com.jdon.annotation.Component;
import com.jdon.domain.message.DomainMessage;
import com.jdon.domain.message.MessageListener;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.message.DigDataBag;
import com.jdon.jivejdon.model.proptery.MessagePropertys;
import com.jdon.jivejdon.repository.dao.PropertyDao;

@Component("addMessageDigCount")
public class AddMessageDigCount implements MessageListener {
	private PropertyDao propertyDao;
	
	
	public AddMessageDigCount(PropertyDao propertyDao) {
		super();
		this.propertyDao = propertyDao;
	}


	public void action(DomainMessage eventMessage) {
		DigDataBag digData = (DigDataBag)eventMessage.getEventSource();
		Long messageId = digData.messageId;
		Property Numberproperty = new Property(MessagePropertys.DIG_NUMBER,String.valueOf(digData.number));
		
		propertyDao.updateProperty(Constants.MESSAGE, messageId, Numberproperty);
		
	}
}
