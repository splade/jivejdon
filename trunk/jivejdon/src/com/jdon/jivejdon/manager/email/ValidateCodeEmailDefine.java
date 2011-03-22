package com.jdon.jivejdon.manager.email;

public class ValidateCodeEmailDefine {

	private String jndiname;

	private String title;

	private String body;

	String url;

	String fromName;
	String fromEmail;

	public ValidateCodeEmailDefine(String jndiname, String title, String body, String url, String fromName, String fromEmail) {
		super();
		this.jndiname = jndiname;
		this.title = title;
		this.body = body;
		this.url = url;
		this.fromName = fromName;
		this.fromEmail = fromEmail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getJndiname() {
		return jndiname;
	}

	public void setJndiname(String jndiname) {
		this.jndiname = jndiname;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
