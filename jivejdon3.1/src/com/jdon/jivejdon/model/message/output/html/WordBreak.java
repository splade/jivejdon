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
package com.jdon.jivejdon.model.message.output.html;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRendering;

/**
 * A ForumMessageFilter that converts newline characters into HTML &lt;br&gt; tags.
 * This filter should only be run after any HTML stripping filters.
 */
public class WordBreak extends MessageRendering {

    int maxSubjectWordLength = 30;
    int maxBodyWordLength = 80;
    boolean filteringSubject = true;
    boolean filteringBody = true;

    /**
     * Clones a new filter that will have the same properties and that
     * will wrap around the specified message.
     *
     * @param message the ForumMessage to wrap the new filter around.
     */
    public MessageRendering clone(ForumMessage message){
        WordBreak filter = new WordBreak();
        filter.maxSubjectWordLength = maxSubjectWordLength;
        filter.maxBodyWordLength = maxBodyWordLength;
        filter.filteringSubject = filteringSubject;
        filter.filteringBody = filteringBody;
        filter.message = message;
        return filter;
    }


    /**
     * Returns the maximum word length for subjects (before the words will be
     * broken apart).
     *
     * @return the maximum word length in subjects.
     */
    public int getMaxSubjectWordLength() {
        return maxSubjectWordLength;
    }

    /**
     * Sets the maximum word length for subjects (before the words will be
     * broken apart).
     *
     * @param maxSubjectWordLength the maximum word length in subjects.
     */
    public void setMaxSubjectWordLength(int maxSubjectWordLength) {
        this.maxSubjectWordLength = maxSubjectWordLength;
    }

    /**
     * Returns the maximum word length for bodies (before the words will be
     * broken apart).
     *
     * @return the maximum word length in bodies.
     */
    public int getMaxBodyWordLength() {
        return maxBodyWordLength;
    }

    /**
     * Sets the maximum word length for bodies (before the words will be
     * broken apart).
     *
     * @param maxBodyWordLength the maximum word length in bodies.
     */
    public void setMaxBodyWordLength(int maxBodyWordLength) {
        this.maxBodyWordLength = maxBodyWordLength;
    }

    /**
     * Returns true if filtering on the subject is enabled.
     *
     * @return true if filtering on the subject is enabled.
     */
    public boolean isFilteringSubject() {
        return filteringSubject;
    }

    /**
     * Enables or disables filtering on the subject.
     *
     * @param filteringSubject toggle value for filtering on subject.
     */
    public void setFilteringSubject(boolean filteringSubject) {
        this.filteringSubject = filteringSubject;
    }

    /**
     * Returns true if filtering on the body is enabled.
     *
     * @return true if filtering on the body is enabled.
     */
    public boolean isFilteringBody() {
        return filteringBody;
    }

    /**
     * Enables or disables filtering on the body.
     *
     * @param filteringBody toggle value for filtering on body.
     */
    public void setFilteringBody(boolean filteringBody) {
        this.filteringBody = filteringBody;
    }

    /**
     * Returns the subject of the message with words greater than
     * <tt>maxSubjectWordLength</tt> broken apart with spaces.
     *
     * @return the subject of the message with long words broken apart.
     */
    public String applyFilteredSubject() {
        if (message.getFilteredSubject() == null) {
            return null;
        }
        return createBreaks(message.getFilteredSubject(), maxSubjectWordLength);
    }

    /**
     * Returns the body of the message with words greater than
     * <tt>maxBodyWordLength</tt> broken apart with spaces.
     *
     * @return the body of the message with long words broken apart.
     */
    public String applyFilteredBody() {
        if (message.getFilteredBody() == null) {
            return null;
        }
        return createBreaks(message.getFilteredBody(), maxBodyWordLength);
    }

    /**
    * Breaks up words that are longer than <tt>maxCount</tt> with spaces.
    *
    * @param input the String to check for long words in.
    * @param maxLength the maximum length of any one word.
    * @return a new String with words broken apart as necessary.
    */
    private static String createBreaks(String input, int maxLength) {
        char [] chars = input.toCharArray();
        int len = chars.length;
        StringBuffer buf = new StringBuffer(len);
        int count = 0;
        int cur = 0;
        // Loop through each character, looking for words that are too long.
        for (int i=0; i<len; i++) {
            // If we find whitespace, reset the count to 0.
            if (Character.isWhitespace(chars[i])) {
                count = 0;
            }
            // If the word is too long, insert a space.
            if (count >= maxLength) {
                count = 0;
                buf.append(chars, cur, i-cur).append(" ");
                cur = i;
            }
            count++;
        }
        // Add whatever chars are left to buffer.
        buf.append(chars, cur, len-cur);
        return buf.toString();
    }
}
