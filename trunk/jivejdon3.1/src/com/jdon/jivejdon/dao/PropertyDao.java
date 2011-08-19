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
package com.jdon.jivejdon.dao;

import java.util.Collection;

import com.jdon.jivejdon.model.Property;

/**
 * @author banq(http://www.jdon.com)
 *
 */
public interface PropertyDao {
    Collection getProperties(int type, Long id);

    void updateProperties(int type, Long id, Collection c);

    void deleteProperties(int type, Long id);
    
    void deleteProperty(int type, Long id, Property property);
}