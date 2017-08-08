package com.somekenyan.kenyanews;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.somekenyan.kenyanews.RSS.RSSList;
import com.somekenyan.kenyanews.RSS.RSSParser;
import com.somekenyan.kenyanews.RSS.RssListAdapter;
import com.somekenyan.kenyanews.data.RSSLinks;

import java.util.LinkedHashMap;
import java.util.Map;

public class NewsMenuActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private LinkedHashMap newsMenu;
    ListView rssList;
    RssListAdapter rssListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsMenu = (LinkedHashMap) RSSLinks.supported_Sites().get(this.getIntent().getExtras().getSerializable(NewsSitesActivity.NEWSMENU));
        //newsMenu = (LinkedHashMap) (this.getIntent().getExtras()).getSerializable(NewsSitesActivity.NEWSMENU);
        setContentView(R.layout.activity_news_menu);

        rssList = (ListView) findViewById(R.id.menu_listView);
        RSSList rl = (RSSList) getIntent().getExtras().getSerializable(NewsSitesActivity.FIRSTRSSMENU);
        Log.d("RSSLIst", "Random things I think");
        Log.d("RSSLIst", rl.getList().toString());
        rssListAdapter = new RssListAdapter(rl, this);
        rssList.setAdapter(rssListAdapter);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    @Override
    public String[] getMenuItems() {
        String [] menu = (String[]) newsMenu.keySet().toArray(new String[0]);
        return menu;
    }

    public RSSList getRssList(int number){
        String [] menuItems = getMenuItems();
        final String url = (String) newsMenu.get(menuItems[number]);
        //TODO load RSS list into the main bar
        final RSSList[] rssList = {null};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    rssList[0] = new RSSParser().parseXML(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        return rssList[0];
    }
    public void onSectionAttached(int number) {
        String [] menuItems = getMenuItems();
        String url = (String) newsMenu.get(menuItems[number]);
        mTitle = menuItems[number];
        //TODO load RSS list into the main bar
        new SectionRSSSetter().execute(number);
        //RSSList rssList = new RSSParser().parseXML(url);
        //rssListAdapter.setRssList(rssList);
/**
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
 **/
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_news_menu, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((NewsMenuActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public class SectionRSSSetter extends AsyncTask<Integer, Void, RSSList>{
        ProgressDialog dialog = new ProgressDialog(NewsMenuActivity.this); @Override
	    protected void onPreExecute() {
	        //or show splash here!
	        dialog.setMessage("Loading!");
	        dialog.show();
	        super.onPreExecute();
	    }

        @Override
        protected RSSList doInBackground(Integer... integers) {
            String [] menuItems = getMenuItems();
            String url = (String) newsMenu.get(menuItems[integers[0]]);
            Log.d("RSSLIst", url);
            //TODO load RSS list into the main bar
            RSSList rssList = new RSSParser().parseXML(url);
            return rssList;
        }

        @Override
        protected void onPostExecute(RSSList rssList) {
            super.onPostExecute(rssList);
            rssListAdapter.setRssList(rssList);
            NewsMenuActivity.this.rssList.setAdapter(rssListAdapter);
            dialog.dismiss();
        }
    }

}
