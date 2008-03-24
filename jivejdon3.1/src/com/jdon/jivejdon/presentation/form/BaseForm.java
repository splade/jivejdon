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
package com.jdon.jivejdon.presentation.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.jdon.model.ModelForm;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public abstract class BaseForm extends ModelForm {
    /* Public Methods */

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
      ActionErrors actionErrors = null;
      ArrayList errorList = new ArrayList();
      doValidate(mapping, request, errorList);
      request.setAttribute("errors", errorList);
      if (!errorList.isEmpty()) {
        actionErrors = new ActionErrors();
        actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("system.error"));
      }
      return actionErrors;
    }

    public  abstract void doValidate(ActionMapping mapping, HttpServletRequest request, List errors);
    /* Protected Methods */

    protected void addErrorIfStringEmpty(List errors, String message, String value) {
      if (value == null || value.trim().length() < 1) {
        errors.add(message);
      }
    }

}
