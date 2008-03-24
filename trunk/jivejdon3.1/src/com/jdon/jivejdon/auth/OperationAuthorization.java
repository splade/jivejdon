package com.jdon.jivejdon.auth;

import com.jdon.jivejdon.model.auth.PermissionRule;

public class OperationAuthorization {
	private final static String module = OperationAuthorization.class.getName();
	private PermissionRule permissionRule;
	
	private PermissionXmlParser permissionXmlParser;
	public OperationAuthorization(PermissionXmlParser permissionXmlParser){
		this.permissionXmlParser = permissionXmlParser;	 
	}
	
	
	public PermissionRule getPermissionRule() {
		if (permissionRule == null){
			permissionRule = permissionXmlParser.parse();
		}
		return permissionRule;
	}

}
