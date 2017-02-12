package com.somekenyan.kenyanews.data;

import android.os.Environment;

public class ApplicationData {
	public final static String APP_FOLDER = "kenya_news";
	public final static String APP_DIR =Environment.getExternalStorageDirectory()+"/"+APP_FOLDER;
	public final static String APP_DB = APP_DIR +"/kenyanews.db";

}
