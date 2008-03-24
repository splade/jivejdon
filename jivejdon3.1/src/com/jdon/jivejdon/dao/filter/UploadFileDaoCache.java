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
package com.jdon.jivejdon.dao.filter;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.dao.sql.UploadFileDaoSql;
import com.jdon.jivejdon.model.UploadFile;
import com.jdon.jivejdon.service.util.ContainerUtil;

/**
 * @author banq(http://www.jdon.com)
 *
 */
public class UploadFileDaoCache extends UploadFileDaoSql {
    private ContainerUtil containerUtil;
    
    public UploadFileDaoCache(JdbcTempSource jdbcTempSource,
    		ContainerUtil containerUtil, Constants constants) {
        super(jdbcTempSource, containerUtil, constants);
        this.containerUtil = containerUtil;
     }
    
    public UploadFile getUploadFile(String objectId) {
        UploadFile uploadFile = (UploadFile) containerUtil.getModelFromCache(objectId, UploadFile.class);
        if (uploadFile == null) {
            uploadFile = super.getUploadFile(objectId);
            containerUtil.addModeltoCache(objectId, uploadFile);
        }
        return uploadFile;
    }
}
