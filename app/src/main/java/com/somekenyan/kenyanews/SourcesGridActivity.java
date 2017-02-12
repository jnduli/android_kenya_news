package com.somekenyan.kenyanews;

import com.somekenyan.kenyanews.RSS.RSSList;
import com.somekenyan.kenyanews.RSS.RSSParser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class SourcesGridActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_sites_view);
		
		GridView grid = (GridView) findViewById(R.id.gridview);
		grid.setAdapter(new SourcesAdapter(this));
	}
	public class SourcesAdapter extends BaseAdapter{
		Context mContext;
		public Integer [] pics = {R.drawable.standard, R.drawable.nation};
		public String [] sites = {"standard","nation"};
		public SourcesAdapter(Context context){
			this.mContext= context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pics.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
		       ImageView imageView;
		        if (convertView == null) {  // if it's not recycled, initialize some attributes
		            imageView = new ImageView(mContext);
		            imageView.setBackgroundColor(Color.RED);
		      //      imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
		    //        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		            imageView.setPadding(8, 8, 8, 8);
		        } else {
		            imageView = (ImageView) convertView;
		        }

		        imageView.setImageResource(pics[position]);
		        imageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new AsyncLoadXML(position).execute();
					}
				});
		        return imageView;		
		        
		}
	}
	
	public class AsyncLoadXML extends AsyncTask{
		String [] urls ={"http://www.standardmedia.co.ke/rss/headlines.php",
				"http://192.168.137.1/nationdata/feed.xml"};// "http://www.nation.co.ke/-/1148/1148/-/view/asFeed/-/vtvnjq/-/index.xml";
		RSSList rsslist;
		String feedurl = "http://192.168.137.1/nationdata/feed.xml";

		public AsyncLoadXML(int position){
			feedurl = urls[position];
		}
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
			
			Intent intent = new Intent(SourcesGridActivity.this, NewsIconsActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			 
			// kill this activity
			finish();
		}
		
		
	}



}
