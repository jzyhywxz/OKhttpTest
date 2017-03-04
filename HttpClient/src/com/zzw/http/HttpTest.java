package com.zzw.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.zzw.http.HttpUtils.Part;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzw.bean.Person;
import com.zzw.http.HttpUtils.FilePart;

public class HttpTest {
	public static void main(String[] args) {
		int test=4;
		// test get request
		if(test==0) {
		HttpUtils.get(
			"http://localhost:8080/HttpServer/zzw/get", 
			new String[]{"user", "张三", "password", "123456"}, 
			new Callback(){
				@Override
				public void onFailure(Call call, IOException e) {
					e.printStackTrace();
				}
				@Override
				public void onResponse(Call call, Response response) throws IOException {
					System.out.println(response.body().string());
				}
			});
		}
		else if(test==1) {
		// test form post request
		String user=null;
		try {
			user=URLEncoder.encode("张三", "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpUtils.post(
			"http://localhost:8080/HttpServer/zzw/form_post", 
			new String[]{"user", user, "password", "123456"}, 
			new Callback(){
				@Override
				public void onFailure(Call call, IOException e) {
					e.printStackTrace();
				}
				@Override
				public void onResponse(Call call, Response response) throws IOException {
					System.out.println(response.body().string());
				}
			});
		}
		else if(test==2) {
		// test upload request
		HttpUtils.post(
			"http://localhost:8080/HttpServer/zzw/upload", 
			new Part[]{new FilePart(new File("res\\picture.jpg"), "image/jpg", "src")}, 
			new Callback(){
				@Override
				public void onFailure(Call call, IOException e) {
					e.printStackTrace();
				}
				@Override
				public void onResponse(Call call, Response response) throws IOException {
					System.out.println(response.body().string());
				}
			});
		}
		else if(test==3) {
		// test download request
		HttpUtils.get(
			"http://localhost:8080/HttpServer/zzw/download", 
			null, new Callback(){
				@Override
				public void onFailure(Call call, IOException e) {
					e.printStackTrace();
				}
				@Override
				public void onResponse(Call call, Response response) throws IOException {
					String fileName=response.header("Content-Disposition");
					int end=fileName.lastIndexOf('"');
					int begin=fileName.lastIndexOf('"', end-1)+1;
					fileName=fileName.substring(begin, end);
					OutputStream os=new FileOutputStream("res\\"+fileName);
					os.write(response.body().bytes());
					os.flush();
					os.close();
					System.out.println("download completed!");
				}
			});
		}
		else if(test==4) {
		HttpUtils.get("http://localhost:8080/HttpServer/zzw/json", null, new Callback(){
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String msg=URLDecoder.decode(response.body().string(), "GBK");
				String[] msgs=msg.split("\r\n");
				System.out.println("response body="+msg);
				
				Gson gson=new Gson();
				Person person=gson.fromJson(msgs[0], Person.class);
				System.out.println("person="+person);
				
				List<Person> persons=gson.fromJson(msgs[1], new TypeToken<List<Person>>(){}.getType());
				System.out.println("persons="+persons);
			}
		});
		}
	}

}
