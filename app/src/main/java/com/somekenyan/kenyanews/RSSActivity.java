package com.somekenyan.kenyanews;

import com.somekenyan.kenyanews.RSS.RSSList;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RSSActivity extends Activity {
	Application myApp;
	RSSList feed;
	ListView lv;
	CustomListAdapter adapter;
	 
	View detailedItem =null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_list);
		myApp = getApplication();
	// Get feed form the file
		feed = (RSSList) getIntent().getExtras().get("feed");
	 
		// Initialize the variables:
		lv = (ListView) findViewById(R.id.listView);
		lv.setVerticalFadingEdgeEnabled(true);
		 
		// Set an Adapter to the ListView
		adapter = new CustomListAdapter(this);
		lv.setAdapter(adapter);
		 
		/*// Set on item click listener to the ListView
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3) {
				// actions to be performed when a list item clicked
				int pos = arg2;
				 
				Bundle bundle = new Bundle();
				bundle.putSerializable("feed", feed);
				Intent intent = new Intent(RSSActivity.this,
				FullArticle.class);
				intent.putExtras(bundle);
				intent.putExtra("pos", pos);
				startActivity(intent);			 
			}
	
		});C*/
		 
	}
	 
	@Override
	protected void onDestroy() {
	super.onDestroy();
	//adapter.imageLoader.clearCache();
	adapter.notifyDataSetChanged();
	}
	 
	class CustomListAdapter extends BaseAdapter {
		private LayoutInflater layoutInflater;
		//public ImageLoader imageLoader;		 
		public CustomListAdapter(RSSActivity activity) {		 
			layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//imageLoader = new ImageLoader(activity.getApplicationContext());
		}
		 
		public int getCount() {
		// Set the total list item count
			return feed.getItemCount();
		}
		 
		@Override
		public Object getItem(int position) {
			return position;
		}
		 
		@Override
		public long getItemId(int position) {
			return position;
		}
		 
		@Override
		public View getView(int position, final View convertView, ViewGroup parent) {
		 
		// Inflate the item layout and set the views
			View listItem = convertView;
			int pos = position;
			if (listItem == null) {
				listItem = layoutInflater.inflate(R.layout.list_item, null);
			}
		// Initialize the views in the layout
	//	ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
			TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
			TextView tvDate = (TextView) listItem.findViewById(R.id.date);
			final TextView tvDescripion = (TextView) listItem.findViewById(R.id.description); 
			// Set the views in the layout
		
			
			tvTitle.setText(feed.getItem(pos).getTitle());
			tvDate.setText(feed.getItem(pos).getDate());
			tvDescripion.setText(feed.getItem(pos).getDescription());
			tvDescripion.setVisibility(View.GONE);
			
			tvTitle.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(tvDescripion.getVisibility() == View.GONE){
						if(detailedItem!=null)detailedItem.setVisibility(View.GONE);
						tvDescripion.setVisibility(View.VISIBLE);
						detailedItem = tvDescripion;
					}else if(tvDescripion.getVisibility() == View.VISIBLE){
						tvDescripion.setVisibility(View.GONE);
					}

				}
			});
			return listItem;
		}

	}
	

}

