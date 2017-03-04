package com.zzw.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

public class GetAction extends ActionSupport {
	private static final long serialVersionUID = -7442519273670774235L;

	private String user;
	private String password;
	
	public void setUser(String u) { user=u; }
	public void setPassword(String p) { password=p; }
	public String getUser() { return user; }
	public String getPassword() { return password; }
	
	@Override
	@Action(value="/zzw/get", 
			results={@Result(name="success", location="user.jsp")})
	public String execute() throws Exception {
		return SUCCESS;
	}
}
