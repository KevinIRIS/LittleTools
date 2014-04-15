package com.kevin.internet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class ShanbayPost {
	private HttpClient http;
	private PostMethod post;
	private Header header;
	private File file;
	private FileReader fileReader;
	private BufferedReader bufReader;
	private String temple;
	private String[] templeArray;
	private NameValuePair[] body = new NameValuePair[3];
	private NameValuePair[] bodyOfWord = new NameValuePair[2];
	private String id;

	public ShanbayPost() {
		// TODO Auto-generated constructor stub
		http = new HttpClient();
		// this.post = post;
		// this.post.setRequestHeader(this.header);
		body[0] = new NameValuePair();
		body[1] = new NameValuePair();
		body[2] = new NameValuePair();
		bodyOfWord[0] = new NameValuePair();
		bodyOfWord[1] = new NameValuePair();

	}

	public void getWordBook(File file) {

		this.file = file;

		if (!file.exists() || !file.isFile()) {
			System.out.print("Path error");
			return;
		}
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bufReader = new BufferedReader(fileReader);
	}

	public void Post() throws IOException {

		int wordNum = 0;
		int unitNum = 1;

		/*
		 * 创建单元列表
		 */
		post = new PostMethod(
				"http://www.shanbay.com/api/v1/wordbook/wordlist/");
		header = new Header();

		header.setName("Cookie");
		header.setValue("language_code=zh-CN; sessionid=f6aqw1wxjara1wbjmc7q8znhayn6jsod; csrftoken=xi0uzIo7Clq0R9Oo9hBw6i2B49nuyo2m; username=kevinanu; userid=4807153; __utma=183787513.962742120.1393150799.1395988010.1396004897.116; __utmb=183787513.36.10.1396004897; __utmc=183787513; __utmz=183787513.1396004897.116.69.utmcsr=baidu.com|utmccn=(referral)|utmcmd=referral|utmcct=/");

		// header.setName("Content-Length");
		// header.setValue(String.valueOf(("Unit"+unitNum).length() + 37));

		body[0].setName("name");
		body[0].setValue("Unit" + unitNum);
		body[1].setName("description");
		body[1].setValue(" ");
		body[2].setName("wordbook_id");
		body[2].setValue("83752");

		post.setRequestHeader(header);
		post.setRequestBody(body);

		http.executeMethod(post);

		String response = post.getResponseBodyAsString();
		System.out.println(response);
		id = response.substring(response.indexOf("id") + 5,
				response.indexOf("id") + 10);
		post.releaseConnection();
		/*
		 * 向列表中传单词
		 */
		int signal = 0;
		while ((temple = bufReader.readLine()) != null) {

			if (temple.equals("\r"))
				continue;
			templeArray = temple.split(" ");
			if (templeArray.length == 0 || templeArray[0].equals("Unit")
					|| templeArray.equals(" "))
				continue;
			else {
				/*
				 * 每个列表最多200个单词 如果超过200个单词重新建立一个列表
				 */
				if (wordNum == 200) {
					unitNum++;
					http = new HttpClient();
					post = new PostMethod(
							"http://www.shanbay.com/api/v1/wordbook/wordlist/");
					header = new Header();

					header.setName("Cookie");
					header.setValue("language_code=zh-CN; sessionid=f6aqw1wxjara1wbjmc7q8znhayn6jsod; csrftoken=xi0uzIo7Clq0R9Oo9hBw6i2B49nuyo2m; __utma=183787513.962742120.1393150799.1395988010.1396004897.116; __utmb=183787513.36.10.1396004897; __utmc=183787513; __utmz=183787513.1396004897.116.69.utmcsr=baidu.com|utmccn=(referral)|utmcmd=referral|utmcct=/; username=kevinanu; userid=4807153");

					body[0].setName("name");
					body[0].setValue("Unit" + unitNum);
					body[1].setName("description");
					body[1].setValue(" ");
					body[2].setName("wordbook_id");
					body[2].setValue("83752");

					post.setRequestHeader(header);
					post.setRequestBody(body);

					http.executeMethod(post);
					response = post.getResponseBodyAsString();

					id = response.substring(response.indexOf("id") + 5,
							response.indexOf("id") + 10);
					System.out.println(id);
					post.releaseConnection();
					wordNum = 0;
					signal = 0;
				}
				http = new HttpClient();
				post = new PostMethod(
						"http://www.shanbay.com/api/v1/wordlist/vocabulary/");
				header = new Header();
				if (signal == 0) {
					header.setName("Cookie");
					header.setValue("language_code=zh-CN; sessionid=f6aqw1wxjara1wbjmc7q8znhayn6jsod; csrftoken=xi0uzIo7Clq0R9Oo9hBw6i2B49nuyo2m; username=kevinanu; userid=4807153; __utma=183787513.962742120.1393150799.1395988010.1396004897.116; __utmb=183787513.37.10.1396004897; __utmc=183787513; __utmz=183787513.1396004897.116.69.utmcsr=baidu.com|utmccn=(referral)|utmcmd=referral|utmcct=/");
					// header.setName("Content-Length");
					// header.setValue("14"+templeArray[0].length());
				} else {
					header.setName("Cookie");
					header.setValue("language_code=zh-CN; sessionid=f6aqw1wxjara1wbjmc7q8znhayn6jsod; csrftoken=xi0uzIo7Clq0R9Oo9hBw6i2B49nuyo2m; __utma=183787513.962742120.1393150799.1395988010.1396004897.116; __utmb=183787513.37.10.1396004897; __utmc=183787513; __utmz=183787513.1396004897.116.69.utmcsr=baidu.com|utmccn=(referral)|utmcmd=referral|utmcct=/; username=kevinanu; userid=4807153");
					signal = 1;
				}

				post.setRequestHeader(header);

				bodyOfWord[0].setName("id");
				bodyOfWord[0].setValue(id);
				bodyOfWord[1].setName("word");
				bodyOfWord[1].setValue(templeArray[0]);
				post.setRequestBody(bodyOfWord);

				http.executeMethod(post);

				System.out.println(post.getStatusCode() + "      ok");
				wordNum++;
				post.releaseConnection();

			}

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("E:\\english.txt");
		// PostMethod postMethod = new
		// PostMethod("http://www.shanbay.com/api/v1/wordbook/wordlist/");
		ShanbayPost shanbay = new ShanbayPost();
		shanbay.getWordBook(file);
		try {
			shanbay.Post();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
