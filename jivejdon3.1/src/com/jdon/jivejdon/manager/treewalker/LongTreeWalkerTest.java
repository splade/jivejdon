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
package com.jdon.jivejdon.manager.treewalker;

import com.jdon.treepatterns.model.TreeModel;

import junit.framework.TestCase;

/**
* * 1000
   |-- 3000
   |-- |--4000
   |-- |--6000 
   |-- |-- |-- 6100
   |-- |-- |-- 6200 
   |-- |--6700
   |-- |--7000
   |-- |-- |--8000
   |-- |-- |-- |--9000
   |-- |-- |-- |--10000   
   |-- 5000 
 * @author <a href="mailto:banqiao@jdon.com">banq </a>
 *  
 */
public class LongTreeWalkerTest extends TestCase {
    LongTreeWalker longTreeWalker;
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        //12 = 11 +1 , actual is 11, construtor must be 11 + 1
        TreeModel treeModel = new TreeModel(1000, 12);

        treeModel.addChild(1000, 3000);
        treeModel.addChild(1000, 5000);
        treeModel.addChild(3000, 4000);
        treeModel.addChild(3000, 6000);
        treeModel.addChild(6000, 6100);
        treeModel.addChild(6000, 6200);
        treeModel.addChild(3000, 6700);
        treeModel.addChild(3000, 7000);
        treeModel.addChild(7000, 8000);
        treeModel.addChild(8000, 9000);
        treeModel.addChild(8000, 10000);

        longTreeWalker = new LongTreeWalker(treeModel);
    }

    public void testGetParent() {
        long result = longTreeWalker.getParent(5000);
        assertEquals(result, 1000);
    }

    public void testGetChild() {
        long result = longTreeWalker.getChild(3000, 1);
        assertEquals(result, 6000);
    }

    public void testGetChildCount() {
        int result = longTreeWalker.getChildCount(3000);        
        System.out.println("result=" + result);
        assertEquals(result, 4);
    }

    public void testGetChildren() {
        long[] childern = longTreeWalker.getChildren(6000);
        for(int i=0; i<childern.length; i ++){
            System.out.println(" child=" + childern[i]);
        }
    }

    public void testGetIndexOfChild() {
        int index = longTreeWalker.getIndexOfChild(6000, 6200);
        System.out.println("index=" + index);
        assertEquals(index, 1);
    }

    public void testGetDepth() {
        int index = longTreeWalker.getDepth(7000);
        System.out.println("index=" + index);
        assertEquals(index, 2);
    }

    public void testGetRecursiveChildren() {
        System.out.println("RecursiveChildren Error, pass by");
    }

    public void testIsLeaf() {
        boolean result = longTreeWalker.isLeaf(3000);
        assertEquals(result, false);
        
        result = longTreeWalker.isLeaf(4000);
        assertEquals(result, true);
        
        result = longTreeWalker.isLeaf(8000);
        assertEquals(result, false);
        
        result = longTreeWalker.isLeaf(9000);
        assertEquals(result, true);
    }

}
