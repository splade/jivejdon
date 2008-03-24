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
package com.jdon.jivejdon.presentation.action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.UploadFile;
import com.jdon.jivejdon.model.message.upload.MessageRenderingFile;
import com.jdon.jivejdon.service.UploadService;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

/**
 * @author banq(http://www.jdon.com)
 *
 */
public class UploadShowAction extends Action {
    public final static String module = UploadShowAction.class.getName();

    private static final byte[] BLANK = { 71, 73, 70, 56, 57, 97, 1, 0, 1, 0, -111, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, 0, 0, 0, 33, -7, 4, 1, 0,
            0, 2, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 76, 1, 0, 59 };

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String imageId = request.getParameter("id");
        if (UtilValidate.isEmpty(imageId))
            throw new Exception("parameter id is null");
        
//      see com.jdon.jivejdon.model.message.upload.MessageRenderingFile/MessageRenderingImage
        String type = request.getParameter("type");
        if (UtilValidate.isEmpty(type))
            throw new Exception("parameter type is null");
        
        String dlname = request.getParameter(MessageRenderingFile.DOWNLOAD_NAME);
            
        UploadFile uploadFile = null;
        try {
            UploadService uploadService = (UploadService) WebAppUtil.getService("uploadService", request);
            uploadFile = uploadService.getUploadFile(imageId);
            if (uploadFile == null)
                throw new Exception("uploadFile == null");
            outUploadFile(response, uploadFile.getData(), type, dlname);
        } catch (Exception ex) {
            Debug.logError("get the image error:" + ex, module);
            outUploadFile(response, BLANK, "images/jpeg", null);
        }
        return null;
    }

    private void outUploadFile(HttpServletResponse response, byte[] data, String type, String dlname) throws Exception {
        //response.setContentType("images/jpeg");        
        response.setContentType(type);
        if (!UtilValidate.isEmpty(dlname)){
        	response.setHeader("Content-Disposition",
        			"attachment; filename=\"" + dlname + "\"");
        }
        long d = System.currentTimeMillis();
        response.addDateHeader("Last-Modified", d);
        response.addDateHeader("Expires", d+1800000); 
        OutputStream toClient = response.getOutputStream();
        try {
            toClient.write(data);
        } catch (Exception ex) {
            Debug.logError("get the image error:" + ex, module);
        } finally {
            toClient.close();
        }
    }

}
