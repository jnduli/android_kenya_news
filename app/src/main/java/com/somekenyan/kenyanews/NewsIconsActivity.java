package com.somekenyan.kenyanews;

import java.util.List;


import com.somekenyan.kenyanews.RSS.RSSList;
import com.somekenyan.kenyanews.RSS.RSSParser;
import com.somekenyan.kenyanews.manipulation.HtmlManipulation;
import com.somekenyan.kenyanews.manipulation.ImageManipulation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;

public class NewsIconsActivity extends FragmentActivity{
	 private static int NUM_PAGES = 5;

	    /**
	     * The pager widget, which handles animation and allows swiping horizontally to access previous
	     * and next wizard steps.
	     */
	    private ViewPager mPager;

	    /**
	     * The pager adapter, which provides the pages to the view pager widget.
	     */
	    private PagerAdapter mPagerAdapter;
	    RSSList feed;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_screen_slide);
	        feed = (RSSList) getIntent().getExtras().get("feed");
	        NUM_PAGES = feed.getItemCount();

	        // Instantiate a ViewPager and a PagerAdapter.
	        mPager = (ViewPager) findViewById(R.id.pager);
	        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
	        mPager.setAdapter(mPagerAdapter);
	    }

	    @Override
	    public void onBackPressed() {
	        if (mPager.getCurrentItem() == 0) {
	            // If the user is currently looking at the first step, allow the system to handle the
	            // Back button. This calls finish() on this activity and pops the back stack.
	            super.onBackPressed();
	        } else {
	            // Otherwise, select the previous step.
	            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
	        }
	    }
	    
	    
	    /**
	     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
	     * sequence.
	     */
	    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
	        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
	            super(fm);
	        }

	        @Override
	        public Fragment getItem(int position) {
	            return new ScreenSlidePageFragment(position);
	        }

	        @Override
	        public int getCount() {
	            return NUM_PAGES;
	        }
	    }
	
	@SuppressLint("ValidFragment")
	public class ScreenSlidePageFragment extends Fragment {

		int pos;
		public ScreenSlidePageFragment(int position){
			super();
			pos = position;
		}
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        ViewGroup listItem = (ViewGroup) inflater.inflate(
	                R.layout.list_item, container, false);
	        ImageView tvImage = (ImageView) listItem.findViewById(R.id.imageView1);
			TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
			TextView tvDate = (TextView) listItem.findViewById(R.id.date);
			final Button more = (Button) listItem.findViewById(R.id.more_button);
			final TextView tvDescripion = (TextView) listItem.findViewById(R.id.description);
			final WebView tvWeb = (WebView) listItem.findViewById(R.id.rss_web_view);
			// Set the views in the layout		
			tvTitle.setText(feed.getItem(pos).getTitle());
			//Bitmap pic = ImageManipulation.getBitmapReflections(BitmapFactory.decodeResource(getResources(), R.drawable.sampleimage));
			//tvImage.setImageBitmap(pic);
			loadImageFromUrl(tvImage);
			tvDate.setText(feed.getItem(pos).getDate());
			tvDescripion.setText(feed.getItem(pos).getDescription());

			more.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String url =feed.getItem(pos).getLink();
					Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
					new AsyncLoadXML(tvWeb).execute(url);
					more.setVisibility(View.INVISIBLE);
				}
			});
	        return listItem;
	    }
	}
	
	
	
	private void loadImageFromUrl(ImageView iv){
		//if(url == null){
			iv.setImageBitmap(ImageManipulation.getBitmapReflections(BitmapFactory.decodeResource(getResources(), R.drawable.general)));
		//}else{
			
		//}		
	}
	public class AsyncLoadXML extends AsyncTask<String, Void, String>{

		WebView wv;
		public AsyncLoadXML(WebView wv){
			this.wv =wv;
		}
		@Override
		protected String doInBackground(String... params) {
			return HtmlManipulation.getNews(params[0], "nation");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			wv.loadData(result, "text/html", null);
		}


		
/*		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			html = HtmlManipulation.getNews(url, "nation");
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			 
			// launch List activity
			
			Intent intent = new Intent(NewsIconsActivity.this, FullArticle.class);
			intent.putExtra("html", html);
			startActivity(intent);
			 
		}*/
		
		
	}

}
