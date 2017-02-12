package com.somekenyan.kenyanews.RSS;

public class RSSLinkData {

	private static String DOM_LINK;
	private static String DOM_AUTHOR;
	private static String DOM_IMAGE;
	//private static String DOM_SUMMARY;
	private static String DOM_NEWS;
	
	public RSSLinkData(String link, String author, String image, String news){
		this.DOM_LINK = link;
		this.DOM_AUTHOR = author;
		this.DOM_IMAGE = image;
		this.DOM_NEWS = news;
	}
	
}
