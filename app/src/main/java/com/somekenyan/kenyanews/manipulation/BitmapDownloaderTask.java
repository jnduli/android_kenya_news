package com.somekenyan.kenyanews.manipulation;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import com.somekenyan.kenyanews.data.ApplicationData;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView iv;
	private String url, name;
	public BitmapDownloaderTask(ImageView iv){
		this.iv = iv;
	}
	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		url = params[1];
		return downloadBitmapFromUrl(params[0]);
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		if(saveBitmapFromUrl(result, url)){
			iv.setImageBitmap(result);
		}else{
			Log.e("Image saving", "Something happened while saving");
		}
		
	}
	
	public static boolean saveBitmapFromUrl(Bitmap bit, String name){
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(ApplicationData.APP_FOLDER+"/"+name));
			if(bit.compress(CompressFormat.JPEG, 90, os)){
				os.close();
				return true;
			}else {
				os.close();
				return false;
			}
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false; 
	}
	public static Bitmap downloadBitmapFromUrl(String url){
		AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
		HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse response = client.execute(getRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode!=HttpStatus.SC_OK){
				Log.w("Imagedowloading", "Error"+statusCode+" while getting bitmap from "+url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if(entity != null){
				InputStream is =null;
				try{
					is = entity.getContent();
					Bitmap bit = BitmapFactory.decodeStream(is);
					return bit;
				}finally{
					if(is !=null) is.close();
					entity.consumeContent();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Imagedownloading", e.toString());
		}finally{
			if(client!=null)
				client.close();
		}
		return null;
	}


}
