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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.dao.SequenceDao;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class SequenceDaoSql implements SequenceDao {
    private final static Logger logger = Logger.getLogger(SequenceDaoSql.class);
    private JdbcTempSource jdbcTempSource;    

    /**
     * @param jdbcTempSource
     */
    public SequenceDaoSql(JdbcTempSource jdbcTempSource) {
        super();
        this.jdbcTempSource = jdbcTempSource;
    }
    
    
    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.SequenceDao#getNextId(java.lang.String)
     */
    public Long getNextId(int idType) throws SQLException {
        String sql = "select id from jiveID where idType = ?";
        List queryParams = new ArrayList();
        queryParams.add(new Integer(idType));

        Long nextid = null;        
        try {            
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, sql);
            Iterator iter = list.iterator();
            if (iter.hasNext()) {
                Map map = (Map) iter.next();
                nextid = (Long)map.get("id");                
            }
            if (nextid == null) {
                throw new SQLException("Error: A null sequence was returned from the database (could not get next " + idType + " sequence).");
            }
            
            nextid = new Long(nextid.longValue() + 1);
            
            String updatesql = "update jiveID set id = ? where idType = ?";
            queryParams.clear();
            queryParams.add(nextid);
            queryParams.add(new Integer(idType));
            
            jdbcTempSource.getJdbcTemp().operate(queryParams, updatesql);
            
        } catch (Exception e) {
           logger.error(e);
           throw new SQLException(e.getMessage());
        }
        
        return nextid;
    }

}
