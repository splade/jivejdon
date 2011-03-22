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
package com.jdon.jivejdon.manager;


/**
 * @author <a href="mailto:banq@163.com">banq</a>
 *
 */
public interface ForumThreadWalker {
    
    Long[] children(Long messageId);

    Long getChildId(Long messageId, int index);
        
    int getMessageDepth(Long messageId);
    
    int getIndexOfChild(Long parent, Long child);

    boolean isLeaf(Long messageId);
    
    Long getParentId(long childKey);

}
