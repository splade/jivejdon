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
package com.jdon.jivejdon.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.PropertyDao;
import com.jdon.jivejdon.model.HotKeys;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.service.util.ContainerUtil;
import com.jdon.util.UtilValidate;

public class ForumPropertyFactory {
	private final static Logger logger = Logger.getLogger(ForumPropertyFactory.class);

	private PropertyDao propertyDao;

	private ContainerUtil containerUtil;

	public ForumPropertyFactory(PropertyDao propertyDao, ContainerUtil containerUtil) {
		super();
		this.propertyDao = propertyDao;
		this.containerUtil = containerUtil;
	}
	
	public void savePropertys(int type, Collection props) {
		try {
			Collection propss = new ArrayList();
			Iterator iter = props.iterator();
			while (iter.hasNext()) {
				Property prop = (Property) iter.next();
				if (!UtilValidate.isEmpty(prop.getName()) && !UtilValidate.isEmpty(prop.getValue()))
					propss.add(prop);
			}
			propertyDao.deleteProperties(Constants.FORUM, new Long(type));
			containerUtil.getCacheManager().getCache().remove(new Integer(type));
			propertyDao.updateProperties(Constants.FORUM, new Long(type), propss);
		} catch (Exception e) {
			logger.error(" savePropertys error: " + e);

		}
	}

	public Collection getPropertys(int type) {
		Collection props = (Collection)containerUtil.getCacheManager().getCache().get(new Integer(type));
		if (props == null){
			props = propertyDao.getProperties(Constants.FORUM, new Long(type));
			if ((props != null) && (props.size() != 0)) 	
			   containerUtil.getCacheManager().getCache().put(type, props);
			else
				return new ArrayList();
		}
		return props;
	}
	
	public HotKeys getHotKeys() {
		HotKeys hotKeys = new HotKeys();
		Collection props = getPropertys(hotKeys.getId());
		hotKeys.setProps(props);
		return hotKeys;
	}
	
}
