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

import org.apache.log4j.Logger;

import com.jdon.treepatterns.model.TreeModel;


/**
 * A simple tree structure for long values. It's nowhere near a complete tree
 * implementation since we don't really need one. However, if anyone is
 * interested in finishing this class, or optimizing it, that would be
 * appreciated.<p>
 *
 * The tree uses three arrays to keep the tree structure. It works as in the
 * following example:
 *
 * <pre>
 *   1
 *   |-- 3
 *   |-- |--4
 *   |-- |--6
 *   |-- 5
 *
 * array index:   0 | 1 | 2 | 3 | 4
 *
 * key:           1 | 3 | 4 | 5 | 6
 * leftChild:     1 | 2 |-1 |-1 |-1
 * rightChild    -1 | 3 | 4 |-1 |-1
 * </pre>
 *
 * Where the key array holds key values, and the leftChild and rightChild arrays
 * are pointers to other array indices.<p>
 *
 * The tree holds a maximum of 65534 nodes. It is not intended to be thread-safe.
 * Based on algorithm found in the book "Introduction To Algorithms" by Cormen
 * et all, MIT Press, 1997.
 */
public class LongTreeWalker {
    private static Logger logger = Logger.getLogger(LongTreeWalker.class);

    long [] keys;
    //char arrays let us address get about 65K nodes.
    char [] leftChildren;
    char [] rightSiblings;

    // Pointer to next available slot.
    char nextIndex = 2;

    /**
     * Constructs a new tree.
     *
     * @param rootKey the value of the root node of the tree.
     * @param size the maximum size of the tree. It cannot grow beyond this size.
     */
    public LongTreeWalker(TreeModel treeModel) {
        try {
            keys = treeModel.getNodeLongKeys().getKeys();
            leftChildren = treeModel.getBinaryTreeAlgorithm().getLeftChildren();
            rightSiblings = treeModel.getBinaryTreeAlgorithm().getRightSiblings();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    

    /**
     * Returns a parent of <code>childKey</code>.
     */
    public long getParent(long childKey) {
        // If the root key was passed in, return -1;
        if (keys[1] == childKey) {
            return -1;
        }

        // Otherwise, perform a search to find the parent.
        char childIndex = findKey(childKey, (char)1);
        if (childIndex == 0) {
            return -1;
        }

        // Adjust the childIndex pointer until we find the left most sibling of
        // childKey.
        char leftSiblingIndex = getLeftSiblingIndex(childIndex);
        while (leftSiblingIndex != 0) {
            childIndex = leftSiblingIndex;
            leftSiblingIndex = getLeftSiblingIndex(childIndex);
        }

        // Now, search the leftChildren array until we find the parent of
        // childIndex. First, search backwards from childIndex.
        for(int i=childIndex-1; i>=0; i--) {
            if (leftChildren[i] == childIndex) {
                return keys[i];
            }
        }

        // Now, search forward from childIndex.
        for(int i=childIndex+1; i<=leftChildren.length; i++) {
            if (leftChildren[i] == childIndex) {
                return keys[i];
            }
        }

        // We didn't find the parent, so giving up. This shouldn't happen.
        return -1;
    }

    /**
     * Returns a child of <code>parentKey</code> at index <code>index</code>.
     */
    public long getChild(long parentKey, int index) {
        char parentIndex = findKey(parentKey, (char)1);
        if (parentIndex == 0) {
            return -1;
        }

        char siblingIndex = leftChildren[parentIndex];
        if (siblingIndex == -1) {
            return -1;
        }
        int i = index;
        while (i > 0) {
            siblingIndex = rightSiblings[siblingIndex];
            if (siblingIndex == 0) {
                return -1;
            }
            i--;
        }
        return keys[siblingIndex];
    }

    /**
     * Returns the number of children of <code>parentKey</code>.
     */
    public int getChildCount(long parentKey) {
        int count = 0;
        char parentIndex = findKey(parentKey, (char)1);
        if (parentIndex == 0) {
            return 0;
        }
        char siblingIndex = leftChildren[parentIndex];
        while (siblingIndex != 0) {
            count++;
            siblingIndex = rightSiblings[siblingIndex];
        }
        return count;
    }

    /**
     * Returns an array of the children of the parentKey, or an empty array
     * if there are no children or the parent key is not in the tree.
     *
     * @param parentKey the parent to get the children of.
     * @return the children of parentKey
     */
    public long [] getChildren(long parentKey) {
        int childCount = getChildCount(parentKey);
        if (childCount == 0) {
            return new long [0];
        }

        long [] children = new long[childCount];

        int i = 0;
        char parentIndex = findKey(parentKey, (char)1);
        char siblingIndex = leftChildren[parentIndex];
        while (siblingIndex != 0) {
            children[i] = keys[siblingIndex];
            i++;
            siblingIndex = rightSiblings[siblingIndex];
        }
        return children;
    }

    /**
     * Returns the index of <code>childKey</code> in <code>parentKey</code> or
     * -1 if <code>childKey</code> is not a child of <code>parentKey</code>.
     */
    public int getIndexOfChild(long parentKey, long childKey) {
        int parentIndex = findKey(parentKey, (char)1);

        int index = 0;

        char siblingIndex = leftChildren[new Long(parentIndex).intValue()];
        if (siblingIndex == 0) {
            return -1;
        }
        while (keys[siblingIndex] != childKey) {
            index++;
            siblingIndex = rightSiblings[siblingIndex];
            if (siblingIndex == 0) {
                return -1;
            }
        }
        return index;
    }

    /**
     * Returns the depth in the tree that the element can be found at or -1
     * if the element is not in the tree. For example, the root element is
     * depth 0, direct children of the root element are depth 1, etc.
     *
     * @param key the key to find the depth for.
     * @return the depth of <tt>key</tt> in the tree hiearchy.
     */
    public int getDepth(long key) {
        int [] depth = { 0 };
        if (findDepth(key, (char)1, depth) == 0) {
            return -1;
        }
        return depth[0];
    }

    

    /**
     * Returns true if the tree node is a leaf.
     *
     * @return true if <code>key</code> has no children.
     */
    public boolean isLeaf(long key) {
        int keyIndex = findKey(key, (char)1);
        if (keyIndex == 0) {
            return false;
        }
        return (leftChildren[keyIndex] == 0);
    }

    /**
     * Returns the keys in the tree.
     */
    public long[] keys() {
        long [] k = new long[nextIndex-1];
        for (int i=0; i<k.length; i++) {
            k[i] = keys[i];
        }
        return k;
    }

   

    /**
     * Returns the index of the specified value, or 0 if the key could not be
     * found. Tail recursion was removed, but not the other recursive step.
     * Using a stack instead often isn't even faster under Java.
     *
     * @param value the key to search for.
     * @param startIndex the index in the tree to start searching at. Pass in
     *      the root index to search the entire tree.
     */
    private char findKey(long value, char startIndex) {
        if (startIndex == 0) {
            return 0;
        }

        if (keys[startIndex] == value) {
            return startIndex;
        }

        char siblingIndex = leftChildren[startIndex];
        while (siblingIndex != 0) {
            char recursiveIndex = findKey(value, siblingIndex);
            if (recursiveIndex != 0) {
                return recursiveIndex;
            }
            else {
                siblingIndex = rightSiblings[siblingIndex];
            }
        }
        return 0;
    }

    /**
     * Identical to the findKey method, but it also keeps track of the
     * depth.
     */
    private char findDepth(long value, char startIndex, int [] depth) {
        if (startIndex == 0) {
            return 0;
        }

        if (keys[startIndex] == value) {
            return startIndex;
        }

        char siblingIndex = leftChildren[startIndex];
        while (siblingIndex != 0) {
            depth[0]++;
            char recursiveIndex = findDepth(value, siblingIndex, depth);
            if (recursiveIndex != 0) {
                return recursiveIndex;
            }
            else {
                depth[0]--;
                siblingIndex = rightSiblings[siblingIndex];
            }
        }
        return 0;
    }

    /**
     * Recursive method that fills the depthKeys array with all the child keys in
     * the tree in depth first order.
     *
     * @param startIndex the starting index for the current recursive iteration.
     * @param depthKeys the array of depth-first keys that is being filled.
     * @param cursor the current index in the depthKeys array.
     * @return the new cursor value after a recursive run.
     */
    private int fillDepthKeys(char startIndex, long [] depthKeys, int cursor) {
        depthKeys[cursor] = keys[startIndex];
        cursor++;
        char siblingIndex = leftChildren[startIndex];
        while (siblingIndex != 0) {
            cursor = fillDepthKeys(siblingIndex, depthKeys, cursor);
            // Move to next sibling
            siblingIndex = rightSiblings[siblingIndex];
        }
        return cursor;
    }

    /**
     * Returs the left sibling index of index. There is no easy way to find a
     * left sibling. Therefore, we are forced to linearly scan the rightSiblings
     * array until we encounter a reference to index. We'll make the assumption
     * that entries are added in order since that assumption can yield big
     * performance gain if it's true (and no real performance hit otherwise).
     */
    private char getLeftSiblingIndex(char index) {
        //First, search backwards throw rightSiblings array
        for (int i=index-1; i>=0; i--) {
            if (rightSiblings[i] == index) {
                return (char)i;
            }
        }

        //Now, search forwards
        for (int i=index+1; i<rightSiblings.length; i++) {
            if (rightSiblings[i] == index) {
                return (char)i;
            }
        }

        //No sibling found, give up.
        return (char)0;
    }
    
}
