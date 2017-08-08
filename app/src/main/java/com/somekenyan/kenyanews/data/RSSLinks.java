package com.somekenyan.kenyanews.data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rookie on 7/2/17.
 */

public class RSSLinks {
    public static Map<String, Map> supported_Sites(){
        Map<String, Map> supported_sites = new LinkedHashMap<String, Map>();
        supported_sites.put("Nation Kenya", nation_createMap());
        supported_sites.put("Standard Kenya", standard_createMap());
        return supported_sites;
    }
    // NATION.co.ke RSS Links
    //latest news
    public static String NATION_LATEST = "http://www.nation.co.ke/latestrss.rss";
    public static String NATION_BUSINESS = "http://www.nation.co.ke/business/996-996-view-asFeed-35lsruz/index.xml";
    public static String NATION_POLITICS= "http://www.nation.co.ke/news/politics/1064-1064-view-asFeed-gogm2d/index.xml";
    public static String NATION_LIFESTYLE = "http://www.nation.co.ke/lifestyle/1190-1190-view-asFeed-c7vy58/index.xml";
    public static String NATION_BLOG = "http://www.nation.co.ke/oped/1192-1192-view-asFeed-47hl0nz/index.xml";
    public static String NATION_SPORT = "http://www.nation.co.ke/sports/1090-1090-view-asFeed-u6rm5f/index.xml";
    public static String NATION_COUNTIES = "http://www.nation.co.ke/counties/1107872-1107872-view-asFeed-12p0keu/index.xml";

    public static String [] NATION_MENU = {"Latest","Politics","Business","Counties","Lifestyle","Sport", "Blogs & Opinions"};
    public static Map<String, String> nation_createMap(){
        Map<String, String> nationMap = new LinkedHashMap<String, String>();
        nationMap.put("Latest", NATION_LATEST);
        nationMap.put("Politics", NATION_POLITICS);
        nationMap.put("Business",NATION_BUSINESS);
        nationMap.put("Counties", NATION_COUNTIES);
        nationMap.put("Lifestyle", NATION_LIFESTYLE);
        nationMap.put("Sport", NATION_SPORT);
        nationMap.put("Blogs & Opinions", NATION_BLOG);
        return nationMap;
    }


    public static String STANDARDMEDIA_LATEST="http://www.standardmedia.co.ke/rss/headlines.php";
    public static String STANDARDMEDIA_KENYA_NEWS="http://www.standardmedia.co.ke/rss/kenya.php";
    public static String STANDARDMEDIA_WORLD_NEWS="http://www.standardmedia.co.ke/rss/world.php";
    public static String STANDARDMEDIA_POLITICS="http://www.standardmedia.co.ke/rss/politics.php";
    public static String STANDARDMEDIA_OPINTION= "http://www.standardmedia.co.ke/rss/opinion.php";
    public static String STANDARDMEDIA_SPORTS="http://www.standardmedia.co.ke/rss/sports.php";
    public static String STANDARDMEDIA_BUSINESS="http://www.standardmedia.co.ke/rss/business.php";
    public static String STANDARDMEDIA_COLUMNISTS="http://www.standardmedia.co.ke/rss/columnists.php";
    //TODO Standard Media RSS Links
    public static Map<String, String> standard_createMap(){
        Map<String, String> stdMap = new LinkedHashMap<String, String>();
        stdMap.put("Latest",STANDARDMEDIA_LATEST);
        stdMap.put("Kenya", STANDARDMEDIA_KENYA_NEWS);
        stdMap.put("Politics", STANDARDMEDIA_POLITICS);
        stdMap.put("World",STANDARDMEDIA_WORLD_NEWS);
        stdMap.put("Business",STANDARDMEDIA_BUSINESS);
        stdMap.put("Sports", STANDARDMEDIA_SPORTS);
        stdMap.put("Opinion", STANDARDMEDIA_OPINTION);
        return stdMap;
    }

}

