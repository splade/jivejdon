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
package com.jdon.jivejdon.model.message.output.html.beaninfo;

import com.jdon.jivejdon.model.message.output.beanutil.FilterBeanInfo;
import com.jdon.jivejdon.model.message.output.html.WordBreak;

/**
 * BeanInfo class for the Word Break filter.
 */
public class WordBreakBeanInfo extends FilterBeanInfo {

    private static final String [] PROPERTY_NAMES = {
        "maxSubjectWordLength",
        "maxBodyWordLength",
        "filteringSubject",
        "filteringBody"
    };

    public WordBreakBeanInfo() {
        super();
    }

    public Class getBeanClass() {
        return WordBreak.class;
    }

    public String [] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    public String getName() {
        return "WordBreak";
    }
}