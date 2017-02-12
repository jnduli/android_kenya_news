package com.somekenyan.kenyanews.providers;

import android.net.Uri;
import android.provider.BaseColumns;

public class KenyaNewsData {
	public static final String CONTENT = "content://";
	public static final String AUTHORITY = "com.somekenyan.kenyanews.providers.kenyanewsdata";
	
	protected static final String TYPE_PRIMARY_KEY = "INTEGER PRIMARY KEY AUTOINCREMENT";	
	protected static final String TYPE_TEXT = "TEXT";
	protected static final String TYPE_TEXT_UNIQUE ="TEXT UNIQUE";
	protected static final String TYPE_DATETIME = "DATETIME";
	protected static final String TYPE_INT = "INT";
	protected static final String TYPE_BOOLEAN = "INTEGER(1)";

	public static class SourcesColumns implements BaseColumns{
		public static final String NAME = "Name";
		public static final String LOGO ="Logo";
		public static final String URL ="Url";
		public static final String FEED_URL ="Feed_url";
		public static final String LAST_UPDATE ="Last_update";
		public static final String ALLOWED ="Allowed";
		
		public static final String[] COLUMNS = new String[] {_ID, NAME, LOGO, URL,FEED_URL, LAST_UPDATE, ALLOWED};
		public static final String[] TYPES =  new String[] {TYPE_PRIMARY_KEY, TYPE_TEXT, TYPE_TEXT, TYPE_TEXT,TYPE_TEXT_UNIQUE, TYPE_DATETIME,TYPE_BOOLEAN};
		
		public static final Uri CONTENT_URI(){
			return Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append("/sources").toString());
		}
		public static final Uri CONTENT_URI(String feedId) {
			return Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append("/sources/").append(feedId).toString());
		}
		
		public static final Uri CONTENT_URI(long feedId) {
			return Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append("/sources/").append(feedId).toString());
		}

	}
	public static class SourcesEntriesColumns implements BaseColumns{
		public static final String SOURCE_ID = "Source_ID";
		public static final String AUTHOR = "Author";
		public static final String TITLE ="Title";
		public static final String PUB_DATE = "Pub_date";
		public static final String IMAGE_URL = "Image_url";
		public static final String LOCAL_IMAGE_URL ="Local_image_url";
		public static final String NEWS ="news";
		public static final String IMAGE_DOWNLOADED="image_downloaded";
		public static final String NEWS_DOWNLOADED ="news_downloaded";
		
		public static final String[] COLUMNS = new String[]{_ID, SOURCE_ID, AUTHOR,TITLE, PUB_DATE, IMAGE_URL,IMAGE_DOWNLOADED, LOCAL_IMAGE_URL,NEWS, NEWS_DOWNLOADED};
		public static final String[] TYPES = new String [] {TYPE_PRIMARY_KEY, TYPE_INT, TYPE_TEXT,TYPE_TEXT,TYPE_DATETIME, TYPE_TEXT, TYPE_BOOLEAN, TYPE_TEXT, TYPE_TEXT, TYPE_BOOLEAN};
		public static final Uri CONTENT_URI(String feedId){
			return Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append("/sources").append(feedId).toString());
		}
	}
	
}
