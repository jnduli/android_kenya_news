package com.somekenyan.kenyanews;

import com.somekenyan.kenyanews.manipulation.HtmlManipulation;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

public class FullArticle extends Activity {

	String prefix="http://192.168.137.1/nationdata/";
	String url ="15 governors set for US trip - News - nation.co.ke.htm";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webarticle);
		String html = getIntent().getExtras().getString("html");
		String imageUrl ="";
		WebView web = (WebView) findViewById(R.id.web1);
		ImageView iv = (ImageView) findViewById(R.id.articleImage);
//		web.setHorizontalScrollBarEnabled(true);
	//	web.setVerticalScrollBarEnabled(false);
		//web.loadUrl(url);
		web.loadData(html, "text/html", null);
	}

}
