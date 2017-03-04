package com.zzw.http;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
	private static OkHttpClient client=new OkHttpClient();
	
	private static Request getGetRequest(String url, String[] params) {
		// If there are some request parameters, 
		// then add these parameters into the url.
		if(params!=null && params.length>0) {
			if(params.length%2!=0)
				throw new IllegalArgumentException("The number of request parameters must be even!");
			StringBuilder sb=new StringBuilder(url);
			for(int i=0; i<params.length-1; i+=2) {
				sb.append((i>0) ? "&" : "?").
				append(params[i]).
				append("=").
				append(params[i+1]);
			}
			url=sb.toString();
		}
		// build the request.
		return new Request.Builder().url(url).build();
	}
	
	private static Request getPostRequest(String url, String[] params) {
		FormBody.Builder builder=new FormBody.Builder();
		// If there are some request parameters, 
		// then add these parameters into the request body.
		if(params!=null && params.length>0) {
			if(params.length%2!=0)
				throw new IllegalArgumentException("The number of request parameters must be even!");
			for(int i=0; i<params.length-1; i+=2)
				builder.add(params[i], params[i+1]);
		}
		// build the request.
		RequestBody requestBody=builder.build();
		return new Request.Builder().url(url).post(requestBody).build();
	}
	
	private static Request getPostRequest(String url, Part[] parts) {
		MultipartBody.Builder builder=new MultipartBody.Builder().
				setType(MultipartBody.FORM);
		// If there are some request parameters, 
		// then add these parameters into the request body.
		if(parts!=null && parts.length>0) {
			for(int i=0; i<parts.length; i++)
				builder.addPart(parts[i].getHeaders(), parts[i].getRequestBody());
		}
		// build the request.
		RequestBody requestBody=builder.build();
		return new Request.Builder().url(url).post(requestBody).build();
	}

	public static void get(String url, String[] params, Callback callback) {
		Request request=getGetRequest(url, params);
		Call call=client.newCall(request);
		try {
			Response response=call.execute();
			callback.onResponse(call, response);
		} catch (IOException e) {
			callback.onFailure(call, e);
		}
	}
	
	public static void post(String url, String[] params, Callback callback) {
		Request request=getPostRequest(url, params);
		Call call=client.newCall(request);
		try {
			Response response=call.execute();
			callback.onResponse(call, response);
		} catch (IOException e) {
			callback.onFailure(call, e);
		}
	}
	
	public static void post(String url, Part[] parts, Callback callback) {
		Request request=getPostRequest(url, parts);
		Call call=client.newCall(request);
		try {
			Response response=call.execute();
			callback.onResponse(call, response);
		} catch (IOException e) {
			callback.onFailure(call, e);
		}
	}
	
	/**
	 * This class will be used when upload something to server.
	 * @author zzw
	 */
	public interface Part {
		Headers getHeaders();
		RequestBody getRequestBody();
	}
	
	/**
	 * This class will be used when upload file to server.
	 * Include the file which will be uploaded, 
	 * the media type of this file, 
	 * and the form name of this file in server's action or form, so on.
	 * @author zzw
	 */
	public static class FilePart implements Part {
		private File file;
		private MediaType mediaType;
		private String formName;
		
		public FilePart(File f, String mt, String fn) {
			file=f;
			mediaType=MediaType.parse(mt);
			formName=fn;
		}
		
		@Override
		public Headers getHeaders() {
			return Headers.of("Content-Disposition", 
					"form-data; name=\""+formName+
					"\"; filename=\""+file.getName()+"\"");
		}

		@Override
		public RequestBody getRequestBody() {
			return RequestBody.create(mediaType, file);
		}
	}
	
	/**
	 * This class will be used when upload string to server.
	 * Include the string which will be uploaded and 
	 * the form name of this file in server's action or form, so on.
	 * @author zzw
	 */
	public static class StringPart implements Part {
		private String param;
		private String formName;
		
		public StringPart(String p, String fn) {
			param=p;
			formName=fn;
		}

		@Override
		public Headers getHeaders() {
			return Headers.of("Content-Disposition", 
					"form-data; name=\""+formName+"\"");
		}

		@Override
		public RequestBody getRequestBody() {
			return RequestBody.create(null, param);
		}
	}
	
	public static void showHeaders(Headers header) {
		Iterator<String> iter=header.names().iterator();
		while(iter.hasNext()) {
			String name=iter.next();
			System.out.println("<"+name+", "+header.get(name)+">");
		}
	}

}
