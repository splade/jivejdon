package com.jdon.jivejdon.manager.email;

public class ForgotPasswdEmailDefine {

	String header;
	String subject;
	String footer;

	String fromName;
	String fromEmail;

	String jndiname;

	public ForgotPasswdEmailDefine(String jndiname, String subject, String header, String footer, String fromName, String fromEmail) {
		super();
		this.header = header;
		this.subject = subject;
		this.footer = footer;
		this.fromName = fromName;
		this.fromEmail = fromEmail;
		this.jndiname = jndiname;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getJndiname() {
		return jndiname;
	}

	public void setJndiname(String jndiname) {
		this.jndiname = jndiname;
	}

}
