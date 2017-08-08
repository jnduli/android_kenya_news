package com.somekenyan.kenyanews.RSS;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.pm.FeatureInfo;
import android.util.Log;

public class RSSParser {
	RSSList rsl = new RSSList();
	
	public RSSList parseXML(String xml){
		URL url = null;
		try {
			url = new URL(xml);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(url.openStream()));
			doc.getDocumentElement().normalize();
			
			NodeList nl = doc.getElementsByTagName("item");
			for(int i=0; i<nl.getLength(); i++){
				Node currentNode = nl.item(i);
				Log.d("RSSLIst", currentNode.getNodeName());
				RSSItem item = new RSSItem(currentNode);
				rsl.addItem(item);
			}
			return rsl;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsl;
	}
}