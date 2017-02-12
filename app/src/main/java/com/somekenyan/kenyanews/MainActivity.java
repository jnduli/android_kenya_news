package com.somekenyan.kenyanews;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.somekenyan.kenyanews.RSS.RSSList;
import com.somekenyan.kenyanews.RSS.RSSParser;
import com.somekenyan.kenyanews.data.SiteData;
import com.somekenyan.kenyanews.providers.KenyaNewsData.SourcesColumns;
import com.somekenyan.kenyanews.providers.KenyaNewsProvider;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	String feedurl ="http://192.168.137.1/nationdata/feed.xml";// "http://www.nation.co.ke/-/1148/1148/-/view/asFeed/-/vtvnjq/-/index.xml";
	RSSList rsslist;
	private final String [] PROJECTION ={
			SourcesColumns._ID,
			SourcesColumns.NAME
	};
	private static final int COLUMN_INDEX_TITLE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	       Intent intent = getIntent();
	        if (intent.getData() == null) {
	            intent.setData(SourcesColumns.CONTENT_URI(2));
	        }
	  getContentResolver().insert(SourcesColumns.CONTENT_URI(2), KenyaNewsProvider.setSourcesTableValues("LOCALHOSTS", SiteData.LOCAL_LOGO+"s", SiteData.LOCAL_URL+"s", SiteData.LOCAL_FEED_URL+"s", "2010-01-01 10:00:00", true));
	        
	        Cursor cursor = managedQuery(getIntent().getData(), PROJECTION, null, null, null);
		setContentView(R.layout.feed_list);
		Toast.makeText(this, "atleast this happends", Toast.LENGTH_LONG).show();
		
		ListView lv = (ListView) findViewById(R.id.listView);
		SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.tester, cursor, new String[]{SourcesColumns.NAME}, new int[]{R.id.text1});
		lv.setAdapter(sca);
/*		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getActiveNetworkInfo() == null
		&& !conMgr.getActiveNetworkInfo().isConnected()
		&& !conMgr.getActiveNetworkInfo().isAvailable()) {
		// No connectivity - Show alert
			
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
		"Unable to reach server, \nPlease check your connectivity.")
		.setTitle("TD RSS Reader")
		.setCancelable(false)
		.setPositiveButton("Exit",
		new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog,
		int id) {
		finish();
		}
		});
		 
		AlertDialog alert = builder.create();
		alert.show();
		 
		} else {
		// Connected - Start parsing
		new AsyncLoadXML().execute();
		 
		}*/
		//new AsyncLoadXML().execute();
		
		/*Button but = (Button) findViewById(R.id.button1);
		but.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),getPageTitle() , Toast.LENGTH_LONG).show();
			}
		});*/
	}
	
	public class AsyncLoadXML extends AsyncTask{

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			
			RSSParser myparser = new RSSParser();
			rsslist = myparser.parseXML(feedurl);
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Bundle bundle = new Bundle();
			bundle.putSerializable("feed", rsslist);
			 
			// launch List activity
			
			Intent intent = new Intent(MainActivity.this, SourcesGridActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			 
			// kill this activity
			finish();
			

		}
		
		
	}
	
	public String getPageTitle(){
		try {
			Document document = Jsoup.connect("http://www.nation.co.ke/news/Kenya-50-Celebrations-Trees-Seedlings-Campaign/-/1056/2218492/-/8kmn79z/-/index.html").timeout(0).get();
			Elements headers = document.select("header h1");
			return headers.get(0).text();
			
			//return "this is good at least";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return e.toString();
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
