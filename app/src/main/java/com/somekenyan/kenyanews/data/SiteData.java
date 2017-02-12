package com.somekenyan.kenyanews.data;

public class SiteData {
	
	public static String LOCAL_URL="http://192.168.173.1";
	public static String LOCAL_FEED_URL= LOCAL_URL+"/"+"nationdata/feed.xml";
	public static String LOCAL_LOGO="logo/localhost.png";
	
	//fields for parsing html
	public static String LOCAL_AUTHOR ="article.article section.author strong";
	public static String LOCAL_IMAGE="img.photo_article";
	public static String LOCAL_NEWS ="section.body-copy div";
	
	//data for nation
	public static String NATION_URL ="http://www.nation.co.ke";
	public static String NATION_FEED_URL=NATION_URL+"/"+"-/1148/1148/-/view/asFeed/-/vtvnjq/-/index.xml";
	public static String NATION_LOGO="logo/nation.png";
	
	public static String NATION_AUTHOR="article.article section.author strong";
	public static String NATION_IMAGE="img.photo_article";
	public static String NATION_NEWS="section.body-copy div";
	
	//data for standard
	public static String STANDARD_URL ="http://www.standardmedia.co.ke";
	public static String STANDARD_FEED_URL=STANDARD_URL+"/rss/headlines.php";
	public static String STANDARD_LOGO="logo/standard.png";
	
	public static String STANDARD_AUTHOR="";
	public static String STANDARD_IMAGE="";
	public static String STANDARD_NEWS="";

	//fields for getting images and feeds
	public static String[] site_logos= {LOCAL_LOGO, NATION_LOGO, STANDARD_LOGO};
	public static String[] site_feed_urls ={LOCAL_FEED_URL, NATION_FEED_URL, STANDARD_FEED_URL};

}
