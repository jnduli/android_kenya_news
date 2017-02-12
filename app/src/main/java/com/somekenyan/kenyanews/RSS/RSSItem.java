package com.somekenyan.kenyanews.RSS;

import java.io.Serializable;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RSSItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3567590647730921166L;
	private String title, description, date, image, link;
	private String TITLE_TAG = "title";
	private String DESCRIPTION_TAG = "description";
	private String DATE_TAG ="pubDate";
	private String IMAGE_TAG = "img";
	private String LINK_TAG = "link";
	
	public RSSItem(){}
	
	public RSSItem(Node n){
		NodeList nl = n.getChildNodes();
		int length = nl.getLength();
		for(int i=1; i< length; i+=2){
			Node thisNode = nl.item(i);
			String text = null;
			String nodeName = thisNode.getNodeName();
			text = nl.item(i).getFirstChild().getNodeValue();
			if(text!=null){
				if(nodeName.equals(TITLE_TAG)){
					this.title = text;
				}else if(nodeName.equals(DESCRIPTION_TAG)){
					String html = text;
					org.jsoup.nodes.Document docHtml = Jsoup
					.parse(html);
					Elements imgEle = docHtml.select("img");
					this.setImage(imgEle.attr("src"));

					this.description = text;
				}else if(nodeName.equals(DATE_TAG)){
					String formatedDate = text.replace(" +0000",
							"");
					this.setDate(formatedDate);
				}else if(nodeName.equals(LINK_TAG)){
					this.setLink(text);
				}
			}
		}
				 
	}	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink(){
		return link;
	}
	public void setLink(String link){
		this.link =link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
