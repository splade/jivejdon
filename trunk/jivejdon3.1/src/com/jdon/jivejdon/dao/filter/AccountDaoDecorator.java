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
package com.jdon.jivejdon.dao.filter;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.sql.AccountDaoSql;
import com.jdon.jivejdon.dao.sql.AccountSSOSql;
import com.jdon.jivejdon.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.service.util.ContainerUtil;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class AccountDaoDecorator extends AccountDaoSql {

    private ContainerUtil containerUtil;

    public AccountDaoDecorator(JdbcTempSource jdbcTempSource, 
    		AccountSSOSql accountSSOSql, ContainerUtil containerUtil, Constants constants) {
        super(jdbcTempSource, accountSSOSql, containerUtil, constants);
        this.containerUtil = containerUtil;
    }
    
    public com.jdon.jivejdon.model.Account getAccount(String userId) {
        com.jdon.jivejdon.model.Account account = (com.jdon.jivejdon.model.Account) containerUtil.getModelFromCache(userId, com.jdon.jivejdon.model.Account.class);
        if (account == null) {
            account = super.getAccount(userId);            
            containerUtil.addModeltoCache(userId, account);        
        }
        return account;
    }
          
}
