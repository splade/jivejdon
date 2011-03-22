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
package com.jdon.jivejdon.repository.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.util.CachedCollection;
import com.jdon.jivejdon.repository.PropertyFactory;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import com.jdon.jivejdon.service.util.ContainerUtil;
import com.jdon.util.UtilValidate;

public class PropertyFactoryDao implements PropertyFactory {
	private final static Logger logger = Logger.getLogger(PropertyFactoryDao.class);

	private PropertyDao propertyDao;

	private ContainerUtil containerUtil;

	public PropertyFactoryDao(PropertyDao propertyDao, ContainerUtil containerUtil) {
		super();
		this.propertyDao = propertyDao;
		this.containerUtil = containerUtil;
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.PropertyFactory#saveForumPropertys(int, java.util.Collection)
	 */
	public void saveForumPropertys(int id, Collection props) {
		try {
			Collection propss = new ArrayList();
			Iterator iter = props.iterator();
			while (iter.hasNext()) {
				Property prop = (Property) iter.next();
				if (!UtilValidate.isEmpty(prop.getName()) && !UtilValidate.isEmpty(prop.getValue()))
					propss.add(prop);
			}
			propertyDao.deleteProperties(Constants.FORUM, new Long(id));
			containerUtil.getCacheManager().getCache().remove(new Long(id));
			containerUtil.clearCache(new Long(id));
			propertyDao.updateProperties(Constants.FORUM, new Long(id), propss);
		} catch (Exception e) {
			logger.error(" savePropertys error: " + e);

		}
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.PropertyFactory#saveThreadPropertys(int, java.util.Collection)
	 */
	public void saveThreadPropertys(int threadID, Collection props) {
		try {
			Collection propss = new ArrayList();
			Iterator iter = props.iterator();
			while (iter.hasNext()) {
				Property prop = (Property) iter.next();
				if (!UtilValidate.isEmpty(prop.getName()) && !UtilValidate.isEmpty(prop.getValue()))
					propss.add(prop);
			}
			propertyDao.deleteProperties(Constants.THREAD, new Long(threadID));
			containerUtil.getCacheManager().getCache().remove(new Integer(threadID));
			propertyDao.updateProperties(Constants.THREAD, new Long(threadID), propss);
		} catch (Exception e) {
			logger.error(" savePropertys error: " + e);

		}
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.PropertyFactory#getForumPropertys(java.lang.Long)
	 */
	public Collection getForumPropertys(Long id) {
		CachedCollection cc = (CachedCollection) containerUtil.getModelFromCache(id, CachedCollection.class);
		if (cc == null) {
			Collection props = propertyDao.getProperties(Constants.FORUM, id);
			if ((props != null) && (props.size() != 0)) {
				cc = new CachedCollection(Integer.toString(Constants.FORUM), props);
				containerUtil.addModeltoCache(id, cc);
			} else
				return new ArrayList();
		}
		return cc.getList();
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.repository.PropertyFactory#getThreadPropertys(int)
	 */
	public Collection getThreadPropertys(int threadID) {
		Collection props = (Collection) containerUtil.getCacheManager().getCache().get(new Integer(threadID));
		if (props == null) {
			props = propertyDao.getProperties(Constants.THREAD, new Long(threadID));
			if ((props != null) && (props.size() != 0))
				containerUtil.getCacheManager().getCache().put(threadID, props);
			else
				return new ArrayList();
		}
		return props;
	}

}
