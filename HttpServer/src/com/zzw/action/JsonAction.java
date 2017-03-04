package com.zzw.action;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.zzw.bean.Person;

public class JsonAction extends ActionSupport {
	private static final long serialVersionUID = 1059643779198325028L;

	@Action(value="/zzw/json", 
			results={@Result(name="success", location="json.jsp")})
	public String execute() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setCharacterEncoding("GBK");
		request.getSession(true);
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("GBK");
		PrintWriter writer=response.getWriter();
		
		Gson gson=new Gson();
		Person person=new Person("张三", 22);
		String single=URLEncoder.encode(gson.toJson(person), "GBK");
		
		List<Person> persons=new ArrayList<>();
		persons.add(new Person("李四", 23));
		persons.add(new Person("王五", 24));
		String list=URLEncoder.encode(gson.toJson(persons), "GBK");
		
		writer.write(single+URLEncoder.encode("\r\n", "GBK")+list);
		writer.flush();
		writer.close();
		return SUCCESS;
	}
}
