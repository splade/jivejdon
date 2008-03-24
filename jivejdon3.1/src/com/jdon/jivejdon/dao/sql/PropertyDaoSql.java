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
package com.jdon.jivejdon.dao.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.PropertyDao;
import com.jdon.jivejdon.model.Property;
import com.jdon.util.UtilValidate;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */

public class PropertyDaoSql implements PropertyDao {
    private final static Logger logger = Logger.getLogger(PropertyDaoSql.class);
    private JdbcTempSource jdbcTempSource;
    
    private Map tables = new HashMap(); 
    
    
    /**
     * @param jdbcTempSource
     */
    public PropertyDaoSql(JdbcTempSource jdbcTempSource) {

        this.jdbcTempSource = jdbcTempSource;
        
        Property property = new Property();
        property.setName("jiveForumProp");
        property.setValue("forumID");
        tables.put(new Integer(Constants.FORUM), property);
        
        property = new Property();
        property.setName("jiveThreadProp");
        property.setValue("threadID");
        tables.put(new Integer(Constants.THREAD), property);

        property = new Property();
        property.setName("jiveMessageProp");
        property.setValue("messageID");
        tables.put(new Integer(Constants.MESSAGE), property);

        property = new Property();
        property.setName("jiveUserProp");
        property.setValue("userID");
        tables.put(new Integer(Constants.USER), property);

    }
    
    public Collection getProperties(int type, Long id){
        Property tproperty = (Property)tables.get(new Integer(type));
        String LOAD_PROPERTIES =
            "SELECT name, propValue FROM "+ tproperty.getName() +" WHERE "+ tproperty.getValue()+"=?";
        
        List queryParams = new ArrayList();
        queryParams.add(id);
        Collection c = new ArrayList();
        
        try {
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams,
                    LOAD_PROPERTIES);
            Iterator iter = list.iterator();
            
            while (iter.hasNext()) {
                Map map = (Map) iter.next();
                Property property = new Property();
                property.setName((String) map.get("name"));
                property.setValue((String) map.get("propValue"));
                c.add(property);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return c;
        
    }
    
    public void updateProperties(int type, Long id, Collection c){
        if (c == null) return;
        Property tproperty = (Property)tables.get(new Integer(type));
        try {
            Iterator iter = c.iterator();
            List queryParams = new ArrayList();
            while(iter.hasNext()){
                Property property = (Property) iter.next();                
            	if (!UtilValidate.isEmpty(property.getName()) && 
    					UtilValidate.isEmpty(property.getValue())){
//            		if a property's value is null , delete it from db.
    				deleteProperty(Constants.USER, id, property);    				 			
    			}else{
                    queryParams.add(id);
                    queryParams.add(property.getName());
                    queryParams.add(property.getValue());
                    String INSERT_PROPERTY =
                        "REPLACE INTO "+tproperty.getName()+"("+tproperty.getValue()+",name,propValue) VALUES(?,?,?)";             
                    jdbcTempSource.getJdbcTemp().operate(queryParams, INSERT_PROPERTY);
                    queryParams.clear();    				
    			}
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
    
    public void deleteProperties(int type, Long id){        
        Property tproperty = (Property)tables.get(new Integer(type));
        try {
            String DELETE_PROPERTIES =
                "DELETE FROM "+tproperty.getName()+" WHERE "+tproperty.getValue()+"=?";
            List queryParams = new ArrayList();   
            queryParams.add(id);
            jdbcTempSource.getJdbcTemp().operate(queryParams, DELETE_PROPERTIES);
        } catch (Exception e) {
            logger.error(e);
        }
        
    }    
    
    public void deleteProperty(int type, Long id, Property property){        
        Property tproperty = (Property)tables.get(new Integer(type));
        try {
            String DELETE_PROPERTIES =
                "DELETE FROM "+tproperty.getName()+" WHERE "+tproperty.getValue()+"=?"
                + " and  name=" + property.getName();
            List queryParams = new ArrayList();   
            queryParams.add(id);
            jdbcTempSource.getJdbcTemp().operate(queryParams, DELETE_PROPERTIES);
        } catch (Exception e) {
        }
        
    }    
    
}
