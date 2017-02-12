package com.somekenyan.kenyanews;

import java.util.ArrayList;


import com.somekenyan.kenyanews.RSS.RSSList;
import com.somekenyan.kenyanews.RSS.RSSParser;
import com.somekenyan.kenyanews.manipulation.ImageManipulation;
import com.somekenyan.kenyanews.providers.KenyaNewsData.SourcesColumns;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class NewsSitesActivity extends Activity {	
	private final String [] PROJECTION ={
			SourcesColumns._ID,
			SourcesColumns.NAME,
			SourcesColumns.LOGO,
			SourcesColumns.FEED_URL
	};
	//private static final int COLUMN_INDEX_TITLE = 1;
	private static final int COLUMN_LOGO =2;
	private static final int COLUMN_FEEDURL =3;
	
	private static final int TAG_URL =1;
	private ArrayList<String > array;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		array = new ArrayList<String>();
		Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(SourcesColumns.CONTENT_URI(2));
        }
		setContentView(R.layout.grid_sites_view);
		
		GridView grid = (GridView) findViewById(R.id.gridview);
		Cursor c = managedQuery(getIntent().getData(), PROJECTION, null, null, null);
		startManagingCursor(c);
		NewsSqlAdapter nsa = new NewsSqlAdapter(this, c);
		grid.setAdapter(nsa);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				ImageView view = (ImageView) parent.getChildAt(position);
				view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_view_click));
				Log.d("itemclick", array.get(position));
				new AsyncLoadXML().execute(array.get(position));	
			}
		});
	}
	
	
	private class NewsSqlAdapter extends CursorAdapter{

		public NewsSqlAdapter(Context context, Cursor c) {
			super(context, c);
		}

		@Override
		public void bindView(View view, final Context context, Cursor cursor) {
			((ImageView) view).setLayoutParams(new GridView.LayoutParams(ImageManipulation.getDeviceWidth(NewsSitesActivity.this),85 ));
			((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			((ImageView) view).setPadding(4, 4, 4, 4);
			view.setFocusable(false);
			view.setFocusableInTouchMode(false);
			String imageUrl = cursor.getString(COLUMN_LOGO);
			String url = cursor.getString(COLUMN_FEEDURL);
	        ((ImageView) view).setImageBitmap(ImageManipulation.getBitmapFromAssets(context, imageUrl));
	        array.add(url);
	        //view.setTag(TAG_URL, url);
	        /*view.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_view_click));
					//Toast.makeText(context, url,Toast.LENGTH_SHORT).show();
					new AsyncLoadXML().execute(url);
				}
			});*/
		}

		@Override
		public View newView(final Context context, Cursor cursor, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			bindView(imageView, context, cursor);
			return imageView;
		}
		
	}

	private class AsyncLoadXML extends AsyncTask<String, Void, RSSList>{
		ProgressDialog dialog = new ProgressDialog(NewsSitesActivity.this);
		
		@Override
		protected RSSList doInBackground(String... params) {
			RSSParser myparser = new RSSParser();
			return myparser.parseXML(params[0]);
		}

	    @Override
	    protected void onPreExecute() {
	        //or show splash here!
	        dialog.setMessage("Cool Progressbar!");
	        dialog.show();
	        super.onPreExecute();
	    }

	    protected void onProgressUpdate(Integer... values) {
	            //insult user here
	    }

		protected void onPostExecute(RSSList result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Bundle bundle = new Bundle();
			bundle.putSerializable("feed", result);			 
			Intent intent = new Intent(NewsSitesActivity.this, NewsIconsActivity.class);
			intent.putExtras(bundle);
			dialog.dismiss();
			startActivity(intent);
		}

	}
}
