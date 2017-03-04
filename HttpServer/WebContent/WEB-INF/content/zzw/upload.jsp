<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>upload page</title>
</head>
<body>
  客户端上传文件名: <s:property value="srcFileName"/><br>
  服务端保存文件名: <s:property value="destFileName"/><br>
  文件类型: <s:property value="srcContentType"/>
</body>
</html>