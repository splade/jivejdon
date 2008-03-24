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
package com.jdon.jivejdon.dao.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.service.util.ContainerUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.model.query.PageIteratorSolver;
import com.jdon.jivejdon.model.UploadFile;
/**
 * @author banq(http://www.jdon.com)
 *
 */
public class UploadFileDaoSql implements com.jdon.jivejdon.dao.UploadFileDao {
    private final static Logger logger = Logger.getLogger(UploadFileDaoSql.class);

    protected PageIteratorSolver pageIteratorSolver;

    protected JdbcTempSource jdbcTempSource;
    
    private Constants constants;
    
    public UploadFileDaoSql(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, Constants constants) {
        this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
        this.jdbcTempSource = jdbcTempSource;
        this.constants = constants;
     }
    
    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.UploadFileDao#getUploadFile(java.lang.String)
     */
    public UploadFile getUploadFile(String objectId) {
        logger.debug("enter getForum for id:" + objectId);
        String LOAD_SQL =
            "SELECT objectId, name, description, datas, messageId, size" +
            " FROM upload WHERE objectId=?";
        List queryParams = new ArrayList();
        queryParams.add(objectId);                 
        UploadFile ret = null;
        try {
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams,
                    LOAD_SQL);
            Iterator iter = list.iterator();
            if (iter.hasNext()) {
                ret = new UploadFile();
                Map map = (Map) iter.next();
                ret.setId((String)map.get("objectId"));
                ret.setName((String) map.get("name"));                
                ret.setDescription((String) map.get("description"));
                ret.setData((byte[])map.get("datas"));
                ret.setParentId((String) map.get("messageId"));
                ret.setSize(((Integer) map.get("size")).intValue());
               
                   
            }
        } catch (Exception se) {
            logger.error("getUploadFile objectId=" + objectId + se);
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.UploadFileDao#createUploadFile(com.jdon.strutsutil.file.UploadFile)
     */
    public void createUploadFile(UploadFile uploadFile) {
    	logger.debug("enter createUploadFile uploadId =" + uploadFile.getId());
        try {
            String ADD_SQL =
                "INSERT INTO upload(objectId, name, description, datas, messageId, size, creationDate) " +
                " VALUES (?,?,?,?,?,?,?)";            
            List queryParams = new ArrayList();
            queryParams.add(uploadFile.getId());
            queryParams.add(uploadFile.getName());            
            queryParams.add(uploadFile.getDescription());
            queryParams.add(uploadFile.getTheFile().getFileData());
            queryParams.add(uploadFile.getParentId());         
            queryParams.add(new Integer(uploadFile.getSize()));
            
            long now = System.currentTimeMillis();   
            String saveDateTime = ToolsUtil.dateToMillis(now);
            String displayDateTime = constants.getDateTimeDisp(saveDateTime); 
            queryParams.add(saveDateTime);
            
            jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_SQL);
            clearCache();
            
        } catch (Exception e) {
            logger.error("createUploadFile uploadId =" + uploadFile.getId() + e);
        }

    }

  

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.UploadFileDao#deleteUploadFile(java.lang.String)
     */
    public void deleteUploadFile(String objectId) {
        try {
            String sql = "DELETE FROM upload WHERE objectId=?";
            List queryParams = new ArrayList();
            queryParams.add(objectId);
            jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
            clearCache();
        } catch (Exception e) {
            logger.error("deleteUploadFile objectId" + objectId + e);
        }

    }

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.dao.UploadFileDao#getAdjunct(java.lang.Long, int, int)
     */
    public Collection getAdjunct(Long messageId) {
        String GET_ALL_ITEMS = "select objectId, name, description,size  from upload where messageId = ?";
        Collection params = new ArrayList(1);
        params.add(messageId);
        Collection results = new ArrayList();
        try {
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(params,  GET_ALL_ITEMS);
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                UploadFile ret = new UploadFile();
                Map map = (Map) iter.next();
                ret.setId((String)map.get("objectId"));
                ret.setName((String) map.get("name"));                
                ret.setDescription((String) map.get("description"));
                ret.setParentId((String) map.get("messageId"));
                ret.setSize(((Integer) map.get("size")).intValue());
                results.add(ret);   
            }
        } catch (Exception se) {
            logger.error("getAdjunct messageId=" + messageId + se);
        }
        return results;
    }
    
    public void clearCache() {
        pageIteratorSolver.clearCache();
    }

}
