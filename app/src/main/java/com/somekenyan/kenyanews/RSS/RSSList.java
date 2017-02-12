package com.somekenyan.kenyanews.RSS;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import android.app.Activity;

public class RSSList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8532179698111293218L;
	private int itemCount=0;
	private List<RSSItem> list;
	
	public RSSList(){
		list = new Vector<RSSItem>(0);
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public List<RSSItem> getList() {
		return list;
	}
	
	public void setList(List<RSSItem> list) {
		this.list = list;
	}
	public void addItem(RSSItem rsi){
		list.add(rsi);
		itemCount++;
	}
	public RSSItem getItem(int pos) {
		// TODO Auto-generated method stub
		return list.get(pos);
	}

}
