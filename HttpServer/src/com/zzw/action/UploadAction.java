package com.zzw.action;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {
	private static final long serialVersionUID = -7408370789285125065L;

	private File src;
	private String srcContentType;
	private String srcFileName;
	private String destDir;
	private String destFileName;
	
	public void setSrc(File f) { src=f; }
	public void setSrcContentType(String ct) { srcContentType=ct; }
	public void setSrcFileName(String fn) { srcFileName=fn; }
	public void setDestDir(String d) { destDir=d; }
	
	public File getSrc() { return src; }
	public String getSrcContentType() { return srcContentType; }
	public String getSrcFileName() { return srcFileName; }
	public String getDestDir() { return destDir; }
	public String getDestFileName() { return destFileName; }
	
	@Action(value="/zzw/upload", 
			params={"destDir", "F:\\workspace\\HttpServer\\res\\dest\\"}, 
			results={@Result(name="success", location="upload.jsp")})
	public String upload() throws Exception {
		destFileName=UUID.randomUUID().toString().replaceAll("-", "")+
				srcFileName.substring(srcFileName.indexOf('.'));
		File dest=new File(destDir+destFileName);
		FileUtils.copyFile(src, dest);
		return SUCCESS;
	}
}
