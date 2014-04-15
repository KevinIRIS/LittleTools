package com.kevin.internet;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlAnalysis {
	private String source;
	private Document doc; // 转source to document
	private Element body;
	private Elements nextPage;
	private Elements link;
	private Elements content;
	private Iterator iter;
	private int next = 0;
	private String linkString = null;
	private String temple;

	public HtmlAnalysis(String source) {
		// TODO Auto-generated constructor stub
		this.source = source;
		doc = Jsoup.parse(source);
		getBody();
	}

	private void getBody() {
		body = doc.body();
	}

	public void getContent() {

		content = body.getElementsByAttributeValue("class", "content");
		iter = content.iterator();
		int num = 1;
		while (iter.hasNext()) {

			Element contentNow = (Element) iter.next();

			System.out.println(num++ + "\n" + contentNow.text().toString());

		}
	}

	public String getNextPage() {

		nextPage = body.getElementsByTag("a"); // 列出所有标签为a的元素
		iter = nextPage.iterator();
		while (iter.hasNext()) {
			Element nowOne = (Element) iter.next(); // 当前标签

			if (nowOne.text().equals("下一页")) { // 当前元素为下一页的元素
				temple = nowOne.toString();
				// System.out.println(temple);
				linkString = temple.substring(temple.indexOf("href") + 6,
						temple.indexOf("title") - 2);
			}

		}

		return linkString;
	}
}
