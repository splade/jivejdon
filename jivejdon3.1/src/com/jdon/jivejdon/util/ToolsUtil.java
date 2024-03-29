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
package com.jdon.jivejdon.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jdon.util.UtilDateTime;

/**
 * tools for this project.
 * 
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 * 
 */
public class ToolsUtil {
	private static final long ROOT_PARENTMESSAGEID = 0;

	private static final char[] zeroArray = "0000000000000000".toCharArray();

	public static final String zeroPadString(String string, int length) {
		if (string == null || string.length() > length) {
			return string;
		}
		StringBuffer buf = new StringBuffer(length);
		buf.append(zeroArray, 0, length - string.length()).append(string);
		return buf.toString();
	}

	public static final String dateToMillis(long now) {
		return zeroPadString(Long.toString(now), 15);
	}

	public static Long getParentIDOfRoot() {
		return new Long(ROOT_PARENTMESSAGEID);
	}

	public static boolean isRoot(Long parentID) {
		if (parentID.longValue() == ROOT_PARENTMESSAGEID)
			return true;
		else
			return false;

	}

	public static final String dateToMillis(Date date) {
		return zeroPadString(Long.toString(date.getTime()), 15);
	}

	/**
	 * the String can be as the key of cache
	 * 
	 * @param date
	 * @return a long string with no Hour/Minute/Mills
	 */
	public static String dateToNoMillis(Date date) {
		// 001184284800000
		String s = dateToMillis(date);
		StringBuffer sb = new StringBuffer(s.substring(0, 10));
		sb.append("00000");
		return sb.toString();
	}

	/**
	 * Converts a date String and a time String into a Date
	 * 
	 * @param date
	 *            The date String: YYYY-MM-DD
	 * @param time
	 *            The time String: either HH:MM or HH:MM:SS
	 * @return A Date made from the date and time Strings
	 */
	public static java.util.Date toDate(String date, String time, String split) {
		if (date == null || time == null)
			return null;
		String month;
		String day;
		String year;
		String hour;
		String minute;
		String second;

		int dateSlash1 = date.indexOf(split);
		int dateSlash2 = date.lastIndexOf(split);

		if (dateSlash1 <= 0 || dateSlash1 == dateSlash2)
			return null;
		int timeColon1 = time.indexOf(":");
		int timeColon2 = time.lastIndexOf(":");

		if (timeColon1 <= 0)
			return null;
		year = date.substring(0, dateSlash1);
		month = date.substring(dateSlash1 + 1, dateSlash2);
		day = date.substring(dateSlash2 + 1);
		hour = time.substring(0, timeColon1);

		if (timeColon1 == timeColon2) {
			minute = time.substring(timeColon1 + 1);
			second = "0";
		} else {
			minute = time.substring(timeColon1 + 1, timeColon2);
			second = time.substring(timeColon2 + 1);
		}

		return UtilDateTime.toDate(month, day, year, hour, minute, second);
	}

	public static String toDateString(java.util.Date date, String splite) {
		if (date == null)
			return "";
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		String monthStr;
		String dayStr;
		String yearStr;

		if (month < 10) {
			monthStr = "0" + month;
		} else {
			monthStr = "" + month;
		}
		if (day < 10) {
			dayStr = "0" + day;
		} else {
			dayStr = "" + day;
		}
		yearStr = "" + year;
		return yearStr + splite + monthStr + splite + dayStr;
	}

	public static String toDateHourString(java.util.Date date) {
		if (date == null)
			return "";
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		StringBuffer sb = new StringBuffer(UtilDateTime.toDateString(date));
		sb.append(toHourString(calendar.get(Calendar.HOUR_OF_DAY)));
		return sb.toString();
	}

	private static String toHourString(int hour) {
		String hourStr;

		if (hour < 10) {
			hourStr = "0" + hour;
		} else {
			hourStr = "" + hour;
		}
		return hourStr;
	}

	/**
	 * Used by the hash method.
	 */
	private static MessageDigest digest = null;

	public synchronized static final String hash(String data) {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.err.println("Failed to load the MD5 MessageDigest. " + "Jive will be unable to function normally.");
				nsae.printStackTrace();
			}
		}
		// Now, compute hash.
		digest.update(data.getBytes());
		return encodeHex(digest.digest());
	}

	/**
	 * Turns an array of bytes into a String representing each byte as an unsigned hex number.
	 * <p>
	 * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
	 * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
	 * Distributed under LGPL.
	 * 
	 * @param bytes
	 *            an array of bytes to convert to a hex-string
	 * @return generated hex string
	 */
	public static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/**
	 * 从请求的url字符串中解析参数，当request.getParameterValues()取得的参数值编码值不正确的时候可以使用该方法
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param paramName
	 *            String
	 * @return String[] 返回多个同参数名的值
	 */
	public static String[] getParamsFromQueryString(HttpServletRequest request, String paramName) {
		return getParamsFromQueryString(request.getQueryString(), paramName);
	}

	/**
	 * 从请求的url字符串中解析参数，当request.getParameterValues()取得的参数值编码值不正确的时候可以使用该方法
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param paramName
	 *            String
	 * @return String[]
	 */
	public static String getParameterFromQueryString(HttpServletRequest request, String paramName) {
		return getParameterFromQueryString(request.getQueryString(), paramName);
	}

	/**
	 * 从请求的url字符串中解析参数，当request.getParameter()取得的参数值编码值不正确的时候可以使用该方法
	 * 
	 * @param queryString
	 *            String
	 * @param paramName
	 *            String
	 * @return String 只返回一个值
	 */
	public static String getParameterFromQueryString(String queryString, String paramName) {
		String[] s = getParamsFromQueryString(queryString, paramName);
		if (s != null && s.length >= 1) {
			return s[0];
		}
		return null;
	}

	/**
	 * 从请求的url字符串中解析参数，当request.getParameter()取得的参数值编码值不正确的时候可以使用该方法
	 * 
	 * @param queryString
	 *            String
	 * @param paramName
	 *            String
	 * @return String[] 返回多个同参数名的值
	 */
	public static String[] getParamsFromQueryString(String queryString, String paramName) {
		if (paramName == null || paramName.length() < 1 || paramName == null || paramName.length() < 1) {
			return new String[0];
		}
		List rsl = new ArrayList();
		String params[] = queryString.split("&");
		for (int i = 0; i < params.length; i++) {
			// System.out.println(params[i]);
			if (params[i] != null && params[i].startsWith(paramName + "=")) {
				try {
					rsl.add(java.net.URLDecoder.decode(params[i].substring(paramName.length() + 1), "UTF-8")); // 与tomcat中URIEncoding="UTF-8"。
				} catch (UnsupportedEncodingException ex) {
				}
			}
		}
		return (String[]) rsl.toArray(new String[0]);
	}
	
	 public static String gbToUtf8(String str) throws UnsupportedEncodingException {    
	        StringBuffer sb = new StringBuffer();    
	        for (int i = 0; i < str.length(); i++) {    
	            String s = str.substring(i, i + 1);    
	            if (s.charAt(0) > 0x80) {    
	                byte[] bytes = s.getBytes("Unicode");    
	                String binaryStr = "";    
	                for (int j = 2; j < bytes.length; j += 2) {    
	                    // the first byte    
	                    String hexStr = getHexString(bytes[j + 1]);    
	                    String binStr = getBinaryString(Integer.valueOf(hexStr, 16));    
	                    binaryStr += binStr;    
	                    // the second byte    
	                    hexStr = getHexString(bytes[j]);    
	                    binStr = getBinaryString(Integer.valueOf(hexStr, 16));    
	                    binaryStr += binStr;    
	                }    
	                // convert unicode to utf-8    
	                String s1 = "1110" + binaryStr.substring(0, 4);    
	                String s2 = "10" + binaryStr.substring(4, 10);    
	                String s3 = "10" + binaryStr.substring(10, 16);    
	                byte[] bs = new byte[3];    
	                bs[0] = Integer.valueOf(s1, 2).byteValue();    
	                bs[1] = Integer.valueOf(s2, 2).byteValue();    
	                bs[2] = Integer.valueOf(s3, 2).byteValue();    
	                String ss = new String(bs, "UTF-8");    
	                sb.append(ss);    
	            } else {    
	                sb.append(s);    
	            }    
	        }    
	        return sb.toString();    
	    }    
	   
	    private static String getHexString(byte b) {    
	        String hexStr = Integer.toHexString(b);    
	        int m = hexStr.length();    
	        if (m < 2) {    
	            hexStr = "0" + hexStr;    
	        } else {    
	            hexStr = hexStr.substring(m - 2);    
	        }    
	        return hexStr;    
	    }    
	   
	    private static String getBinaryString(int i) {    
	        String binaryStr = Integer.toBinaryString(i);    
	        int length = binaryStr.length();    
	        for (int l = 0; l < 8 - length; l++) {    
	            binaryStr = "0" + binaryStr;    
	        }    
	        return binaryStr;    
	    }    


}
