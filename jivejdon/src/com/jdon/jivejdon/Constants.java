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
package com.jdon.jivejdon;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class Constants {

	public static final int FORUM = 0;

	public static final int THREAD = 1;

	public static final int MESSAGE = 2;

	public static final int USER = 3;

	public static final int OTHERS = 4;

	public static final int SHORTMESSAGE = 5;

	public static final int SUBSCRIPTION = 6;

	public static final String ERRORS = "system.error";

	public static final String NOPERMISSIONS = "nopermission.error";

	public static final String NOPERMISSIONS2 = "hasChildern.nopermission.error";

	public static final String USERNAME_EXISTED = "username.existed";

	public static final String EMAIL_EXISTED = "email.existed";

	public static final String NOT_FOUND = "id.notfound";

	public static final String EXCEED_MAX_UPLOADS = "upload.error.exceed.max";

	public static final String INPUT_PERMITTED = "Input.Permitted";

	public static final String IP_PERMITTED = "IP.Permitted";

	public static final String EXISTED_ERROR = "USER.CREATE.ERROR";
	
	private static String date_format = "yyyy-MM-dd HH:mm";
	
	public Constants(String format){
		date_format = format;
	}
	
	public String getDateTimeDisp(String datetime) {
		if ((datetime == null) || (datetime.equals("")))
			return "";
		long datel = Long.parseLong(datetime);
		return  new SimpleDateFormat(date_format).format(new Date(datel));
	}

	public static String getDefaultDateTimeDisp(String datetime) {
		if ((datetime == null) || (datetime.equals("")))
			return "";
		long datel = Long.parseLong(datetime);
		return  new SimpleDateFormat(date_format).format(new Date(datel));
	}

	public static Date parseDateTime(String datetime) {
		try {
			return  new SimpleDateFormat(date_format).parse(datetime);
		} catch (ParseException e) {
		}
		return new Date();
	}

	public Date date_parse(String dateS) {
		try {
			return  new SimpleDateFormat(date_format).parse(dateS);
		} catch (ParseException e) {
			return null;
		}
	}

}
