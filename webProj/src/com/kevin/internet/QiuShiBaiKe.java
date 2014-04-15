package com.kevin.internet;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class QiuShiBaiKe {
	private HttpClient http;
	private HttpURL url;   // request url
	private HttpClientParams params;  //参数
	private GetMethod method;
	private String response;
	private Header header;
	
 
	public QiuShiBaiKe(  ) {
		// TODO Auto-generated constructor stub
		
		http = new HttpClient();   
		
	}
	
	public void setGet(GetMethod method , Header header){
		
		this.header = header;
		
		this.params =params;
		this.method = method;
		this.method.setRequestHeader(header);
		
	}
	
	public String  get(){
		
		try {
			http.executeMethod(method);   //执行post请求
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (method.getStatusCode() == HttpStatus.SC_OK){
			try {
				response = method.getResponseBodyAsString();
				HttpMethodParams param1 = new HttpMethodParams();
				param1 = method.getParams();
//				System.out.print(response);
//				System.out.println(param1);
				return response;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QiuShiBaiKe in;
		String url = "http://www.qiushibaike.com";
		String source = null;   //html response
		HtmlAnalysis  htmlAnalysis = null;   
		String signal;
		Scanner scanner = new Scanner(System.in);
		
		if(!url.startsWith("http")){
			System.out.println("error url");
			return ;
		}
		
		Header header = new Header();
		
		
		
/*		Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*//*;q=0.8
				Accept-Encoding:gzip,deflate,sdch
				Accept-Language:en-GB,en;q=0.8,en-US;q=0.6,zh-CN;q=0.4,zh;q=0.2,zh-TW;q=0.2,ja;q=0.2
				Connection:keep-alive
				Cookie:bdshare_firstime=1393496210593; Hm_lvt_2670efbdd59c7e3ed3749b458cafaa37=1393498924,1393498950,1393499202,1395127136; __utma=210674965.871976304.1393496210.1393498923.1395127136.3; __utmz=210674965.1395127136.3.3.utmcsr=baidu|utmccn=(organic)|utmcmd=organic|utmctr=%E7%B3%97%E4%BA%8B%E7%99%BE%E7%A7%91
				Host:www.qiushibaike.com
				RA-Sid:B7DD0CC7-20140318-064649-6dc5e1-c9c78c
				RA-Ver:2.0.4
				User-Agent:Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36
//		param.setParameter(name, value);*/
		header.setName("Accept");
		header.setValue("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*//*;q=0.8");
		
		header.setName("Accept-Encoding");
		header.setValue("gzip,deflate,sdch");
		
		header.setName("Accept-Language");
		header.setValue("en-GB,en;q=0.8,en-US;q=0.6,zh-CN;q=0.4,zh;q=0.2,zh-TW;q=0.2,ja;q=0.2");

		header.setName("Connection");
		header.setValue("keep-alive");
		
		header.setName("Host");
		header.setValue("www.qiushibaike.com");
		
		header.setName("RA-Sid");
		header.setValue("B7DD0CC7-20140318-064649-6dc5e1-c9c78c");
		
		header.setName("RA-Ver");
		header.setValue("2.0.4");
		
		header.setName("User-Agent");
		header.setValue("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36");
		
		in = new QiuShiBaiKe();
		
		GetMethod get = new GetMethod(url );  //设置方法的请求地址
		in.setGet(get, header);
		 source = in.get();
	//	System.out.println(temple);
		htmlAnalysis = new HtmlAnalysis(source);   //将html解析
		htmlAnalysis.getContent();
		while(!(signal = scanner.next()).equals("end")){
			try {
				get.setURI(new URI(url + htmlAnalysis.getNextPage()));
			} catch (URIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			source = in.get();
			htmlAnalysis = new HtmlAnalysis(source);   //将html解析
			htmlAnalysis.getContent();
		}
		
		
	}

}
