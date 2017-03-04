package com.zzw.action;

import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

public class FormPostAction extends ActionSupport {
	private static final long serialVersionUID = -2701435552091943728L;

	private String user;
	private String password;
	private String encoding;
	
	public void setUser(String u) { user=u; }
	public void setPassword(String p) { password=p; }
	public void setEncoding(String e) { encoding=e; }
	public String getUser() { return user; }
	public String getPassword() { return password; }
	public String getEncoding() { return encoding; }
	
	@Override
	@Action(value="/zzw/form_post", 
			params={"encoding", "GBK"}, 
			results={@Result(name="success", location="user.jsp")})
	public String execute() throws Exception {
		user=URLDecoder.decode(user, encoding);
		return SUCCESS;
	}
}
