package com.somekenyan.kenyanews.providers;

import com.somekenyan.kenyanews.data.FeedNewsData;
import com.somekenyan.kenyanews.data.SiteData;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class KenyaNewsProvider extends ContentProvider {

	/**
	 * class provides access to database
	 * should have methods to insert, update and delete records
	 * static table: table of feed sources
	 * dynamic table: table of feed data
	 * feed sources
	 * 	can be updated .... show whether allowed or not allowed
	 * feed data
	 * 	data inserted from feed url
	 * 	data inserted from html link
	 * 	data deleted when its too old
	 */
	private static final String DATABASE_NAME = "kenyanews.db";	
	private static final int DATABASE_VERSION = 13;
	
	private static final int URI_SOURCES = 1;
	private static final int URI_SOURCE = 2;
	private static final int URI_FEEDS = 3;
	private static final int URI_FEED =4;

	private static final String TABLE_SOURCES = "sources";
	private static final String TABLE_DATA = "data";
	
	private static final UriMatcher URI_MATCHER;
	
	static{
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(KenyaNewsData.AUTHORITY, "sources/", URI_SOURCES);//uri for accessing sources in general
		URI_MATCHER.addURI(KenyaNewsData.AUTHORITY, "sources/#", URI_SOURCE);//uri for accessing a specified source
		URI_MATCHER.addURI(KenyaNewsData.AUTHORITY, "sources/#/feeds", URI_FEEDS);//general access of feeds
		URI_MATCHER.addURI(KenyaNewsData.AUTHORITY, "sources/#/feeds/#", URI_FEED);//access specific feed
	}

	//method to generate content values for news sources table
	public static ContentValues setSourcesTableValues(String name, String logo, String url, String feed_url, String last_update, boolean allowed){
		ContentValues cv =new ContentValues();
		cv.put(FeedNewsData.Sources.NAME, name);
		cv.put(FeedNewsData.Sources.LOGO, logo);
		cv.put(FeedNewsData.Sources.URL, url);
		cv.put(FeedNewsData.Sources.FEED_URL, feed_url);
		cv.put(FeedNewsData.Sources.LAST_UPDATE, last_update);
		cv.put(FeedNewsData.Sources.ALLOWED, allowed);
		return cv;
	}

	
	private static class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(createTable(TABLE_SOURCES, KenyaNewsData.SourcesColumns.COLUMNS, KenyaNewsData.SourcesColumns.TYPES));
			db.execSQL(createTable(TABLE_DATA, KenyaNewsData.SourcesEntriesColumns.COLUMNS, KenyaNewsData.SourcesEntriesColumns.TYPES));
			//db.insert(TABLE_SOURCES, null, setSourcesTableValues("LOCALHOST", SiteData.NATION_LOGO, SiteData.LOCAL_URL, SiteData.LOCAL_FEED_URL, "2010-01-01 10:00:00", true));
			db.insert(TABLE_SOURCES, null, setSourcesTableValues("Nation", SiteData.NATION_LOGO, SiteData.NATION_URL, SiteData.NATION_FEED_URL, "2010-01-01 10:00:00", true));
			db.insert(TABLE_SOURCES, null, setSourcesTableValues("STANDARD", SiteData.STANDARD_LOGO, SiteData.STANDARD_URL, SiteData.STANDARD_FEED_URL, "2010-01-01 10:00:00", true));
		}
		
		private ContentValues setSourcesTableValues(String name, String logo, String url, String feed_url, String last_update, boolean allowed){
			ContentValues cv =new ContentValues();
			cv.put(FeedNewsData.Sources.NAME, name);
			cv.put(FeedNewsData.Sources.LOGO, logo);
			cv.put(FeedNewsData.Sources.URL, url);
			cv.put(FeedNewsData.Sources.FEED_URL, feed_url);
			cv.put(FeedNewsData.Sources.LAST_UPDATE, last_update);
			cv.put(FeedNewsData.Sources.ALLOWED, allowed);
			return cv;
		}

		private String createTable(String tableName, String[] columns, String[] types) {
			if (tableName == null || columns == null || types == null || types.length != columns.length || types.length == 0) {
				throw new IllegalArgumentException("Invalid parameters for creating table "+tableName);
			} else {
				StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
				
				stringBuilder.append(tableName);
				stringBuilder.append(" (");
				for (int n = 0, i = columns.length; n < i; n++) {
					if (n > 0) {
						stringBuilder.append(", ");
					}
					stringBuilder.append(columns[n]).append(' ').append(types[n]);
				}
				return stringBuilder.append(");").toString();
			}
		}


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}

	private DatabaseHelper databaseHelper;
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		int option = URI_MATCHER.match(uri);
		switch (option) {
		case URI_SOURCE:
			return "vnd.android.cursor.dir/vnd.kenyanewsdata.source";
		case URI_SOURCES:
			return "vnd.android.cursor.dir/vnd.kenyanewsdata.sources";
		case URI_FEED:
			return "vnd.android.cursor.dir/vnd.kenyanewsdata.feed";
		case URI_FEEDS:
			return "vnd.android.cursor.dir/vnd.kenyanewsdata.feeds";
		default:
			throw new IllegalArgumentException("Unknown URI: "+uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		long newId;
		
		int option = URI_MATCHER.match(uri);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		switch(option){
		case URI_SOURCE:
			newId =db.insert(TABLE_SOURCES, null, values);
			break;
		case URI_FEED:
			newId= db.insert(TABLE_DATA, null, values);
			break;
		default:
			throw new IllegalArgumentException("Illegal arg");

		}
		
		if (newId > -1) {
			getContext().getContentResolver().notifyChange(uri, null);
			Log.i("sql", "success in inserting");
			return ContentUris.withAppendedId(uri, newId);
		} else {
			Log.e("sql", "could not insert");
		}
		return uri;

	}

	@Override
	public boolean onCreate() {
		databaseHelper = new DatabaseHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		if(sortOrder== null)
			sortOrder = KenyaNewsData.SourcesColumns.NAME;
		int option = URI_MATCHER.match(uri);
		switch(option){
		case URI_SOURCES:
			queryBuilder.setTables(TABLE_SOURCES);
			break;
		case URI_SOURCE:
			queryBuilder.setTables(TABLE_SOURCES);
			break;

		}
		SQLiteDatabase database = databaseHelper.getReadableDatabase();
		Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		int option = URI_MATCHER.match(uri);
		
		String table = null;
		
		StringBuilder where = new StringBuilder();
		
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		
		switch(option) {
			case URI_SOURCES : {
			}
			case URI_FEEDS : {
				table = TABLE_DATA;
				where.append(KenyaNewsData.SourcesEntriesColumns._ID).append('=').append(uri.getPathSegments().get(2));
				break;
			}
			
		}
		
		if (!TextUtils.isEmpty(selection)) {
			if (where.length() > 0) {
				where.append(" AND ").append(selection);
			} else {
				where.append(selection);
			}
		}
		
		int count = database.update(table, values, where.toString(), selectionArgs);
		
		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;

	}
}
