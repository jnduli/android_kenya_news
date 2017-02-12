package com.somekenyan.kenyanews.data;

public class FeedNewsData {
	
	protected static final String TYPE_PRIMARY_KEY = "INTEGER PRIMARY KEY AUTOINCREMENT";	
	protected static final String TYPE_TEXT = "TEXT";
	protected static final String TYPE_TEXT_UNIQUE ="TEXT UNIQUE";
	protected static final String TYPE_DATETIME = "DATETIME";
	protected static final String TYPE_INT = "INT";
	protected static final String TYPE_BOOLEAN = "INTEGER(1)";

	public static class Sources {
		public static final String ID = "id";
		public static final String NAME = "Name";
		public static final String LOGO ="Logo";
		public static final String URL ="Url";
		public static final String FEED_URL ="Feed_url";
		public static final String LAST_UPDATE ="Last_update";
		public static final String ALLOWED ="Allowed";
		
		public static final String[] COLUMNS = new String[] {ID, NAME, LOGO, URL,FEED_URL, LAST_UPDATE, ALLOWED};
		public static final String[] TYPES =  new String[] {TYPE_PRIMARY_KEY, TYPE_TEXT, TYPE_TEXT, TYPE_TEXT,TYPE_TEXT_UNIQUE, TYPE_DATETIME,TYPE_BOOLEAN};
	}
	
	public static class SourcesFeeds{
		
	}

}
