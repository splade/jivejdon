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
package com.jdon.jivejdon.presentation.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.jdon.util.Debug;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class SidebarShowController extends DispatchAction {
    private final static String module = SidebarShowController.class.getName();

    public ActionForward forum(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Debug.logVerbose("enter Forum menu ", module);
        return mapping.findForward("forum");
    }
    
    public ActionForward system(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Debug.logVerbose("enter Forum menu ", module);
        return mapping.findForward("system");
    }

    public ActionForward users(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Debug.logVerbose("enter Forum menu ", module);
        return mapping.findForward("users");
    }

}
