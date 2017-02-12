package com.somekenyan.kenyanews.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class HtmlManipulation {
	
	public static void getImage(){
		
	}
	
	public static String getHtml(String url){
		String safeurl = null;
		try {
			safeurl = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			Log.e("encode", e1.toString());
			e1.printStackTrace();
			
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet, localContext);
			String result = "";
			 
			BufferedReader reader = new BufferedReader(
			    new InputStreamReader(
			      response.getEntity().getContent()
			    )
			  );
			 
			String line = null;
			while ((line = reader.readLine()) != null){
			  result += line + "\n";
			}
			return result;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e("html", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("html", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	

	public static String getNews(String url, String base){
		String html ="page not loaded";
		String webpage = getHtml(url);
		//Document doc = (webpage==null||webpage.length()==0?null:Jsoup.parse(webpage));
		Document doc = Jsoup.parse(webpage);
		
		if(doc != null){
			Elements head = doc.select("head");
			Elements content = doc.getElementsByAttributeValue("class", "body-copy");
			html= "<html>"+head.toString()+"<body>" +content.toString()+"</body></html>";
		}else {
			html = "The document is null";
		}
		return html;
		
/*		URL surl = null;
		try {
			surl = new URL(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String html = "Sorry could not load page";
		Document doc = null;
		try {
			
			doc = Jsoup.connect(surl.toString())
					.timeout(30000).followRedirects(true).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("htmlmanip",e.toString());
		}

		if(doc != null){
			Elements content = doc.getElementsByAttributeValue("class", "body-copy");
			Elements group = new Elements(content);
			html= group.toString();
		}else {
			html = "The document is null";
		}
		return html;*/
	}
	
	public static void main(){
		System.out.println("this is good");
	}

}
