package com.zzw.action;

import java.io.FileInputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;

public class DownloadAction extends ActionSupport {
	private static final long serialVersionUID = 9078680027961237229L;
	
	private String srcPath;
	
	public String getSrcPath() { return srcPath; }
	public void setSrcPath(String path) { srcPath=path; }

	public InputStream getTarget() throws Exception {
		return new FileInputStream(srcPath);
	}
}
