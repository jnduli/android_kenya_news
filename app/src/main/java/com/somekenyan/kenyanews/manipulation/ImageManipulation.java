package com.somekenyan.kenyanews.manipulation;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.util.DisplayMetrics;
import android.util.Log;

public class ImageManipulation {
	
	public static int getDeviceHeight(Activity con){
		DisplayMetrics dimen = new DisplayMetrics();
		con.getWindowManager().getDefaultDisplay().getMetrics(dimen);
		return dimen.heightPixels;
	}
	public static int getDeviceWidth(Activity con){
		DisplayMetrics dimen = new DisplayMetrics();
		con.getWindowManager().getDefaultDisplay().getMetrics(dimen);
		return dimen.widthPixels;
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
	
	public static Bitmap getBitmapFromAssets(Context c,String fileName){
		try {
			InputStream is = c.getAssets().open(fileName);
			Bitmap bit = BitmapFactory.decodeStream(is);
			return bit;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Bitmap getBitmapReflections(Bitmap image){
		int reflectionGap = 2;
		Bitmap original = image;
		int width = original.getWidth();
		int height = original.getHeight();
		
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflection = Bitmap.createBitmap(original, 0, height/3, width, height/3,matrix, false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,height+height/3,Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(original, 0, 0, null);
		Paint defaultPaint = new Paint();
		canvas.drawRect(0,height,width, height-reflectionGap, defaultPaint);
		canvas.drawBitmap(reflection, 0, height+reflectionGap, null);
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, original.getHeight(),0,bitmapWithReflection.getHeight()+reflectionGap, 0x70ffffff,0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()+reflectionGap, paint);
		return bitmapWithReflection;
	}

}
