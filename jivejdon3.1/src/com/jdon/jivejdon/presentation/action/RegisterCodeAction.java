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
package com.jdon.jivejdon.presentation.action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.presentation.form.SkinUtils;
import com.jdon.util.Debug;
import com.jdon.util.RegisterCode;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class RegisterCodeAction   extends Action {
    private final static String module = RegisterCodeAction.class.getName();
    public ActionForward execute(ActionMapping actionMapping,
                                 ActionForm actionForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

      try {
        renderImage(request, response);
      }
      catch (Exception ex) {
        Debug.logError(" renderImage error " + ex, module);
      }
      return null;
    }

    private void renderImage(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
      OutputStream toClient = null;
      try {

        response.setContentType("images/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        toClient = response.getOutputStream();

        RegisterCode registerCode = new RegisterCode();
        String rand = SkinUtils.getRegisterCode(request, response);
        registerCode.create(60, 20, rand, "Times New Roman", 18, 2, 16, toClient);
        toClient.flush();
      }
      catch (Exception e) {

      }
      finally {
        if (toClient != null)
          toClient.close();
      }

    }

}
