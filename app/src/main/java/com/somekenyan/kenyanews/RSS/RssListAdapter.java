package com.somekenyan.kenyanews.RSS;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.somekenyan.kenyanews.R;
import com.somekenyan.kenyanews.RSSActivity;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * Created by rookie on 7/15/17.
 */

public class RssListAdapter extends BaseAdapter {
    RSSList rssList;
    Activity mActivity;
    public RssListAdapter(RSSList rl, Activity activity){
        this.rssList = rl;
        mActivity = activity;
    }

    public void setRssList(RSSList rssList){
        this.rssList = rssList;
    }

    @Override
    public int getCount() {
        return rssList.getList().size();
    }

    @Override
    public Object getItem(int i) {
        return rssList.getItem(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View listItem = view;
        if (listItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItem = layoutInflater.inflate(R.layout.rss_list_item, null);
        }
        TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
        TextView tvDate = (TextView) listItem.findViewById(R.id.date);
        final TextView tvDescripion = (TextView) listItem.findViewById(R.id.description);
        ImageView articleImage = (ImageView) listItem.findViewById(R.id.article_image);
        // Set the views in the layout


        tvTitle.setText(rssList.getItem(i).getTitle());
        tvDate.setText(rssList.getItem(i).getDate());
        tvDescripion.setText(rssList.getItem(i).getDescription());
        if ( rssList.getItem(i).getImage()== null || rssList.getItem(i).getImage().isEmpty()){
            articleImage.setImageBitmap(null);
            Random ran = new Random();
            articleImage.setBackgroundColor(Color.rgb(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)));
        }else{
            Picasso.with(mActivity)
                    .load(rssList.getItem(i).getImage())
                    .resize(150, 150)
                    .centerCrop()
                    .into(articleImage);
        }

        /**
        if ( rssList.getItem(i).getImage() != null || !rssList.getItem(i).getImage().isEmpty()){
            Picasso.with(mActivity)
                    .load(rssList.getItem(i).getImage())
                    .resize(150, 150)
                    .centerCrop()
                    .into(articleImage);
        }else {
            articleImage.setVisibility(View.INVISIBLE);
        }
         **/



//          tvDescripion.setVisibility(View.GONE);

        /**
         tvTitle.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
        // TODO Auto-generated method stub

        if(tvDescripion.getVisibility() == View.GONE){
        if(detailedItem!=null)detailedItem.setVisibility(View.GONE);
        tvDescripion.setVisibility(View.VISIBLE);
        detailedItem = tvDescripion;
        }else if(tvDescripion.getVisibility() == View.VISIBLE){
        tvDescripion.setVisibility(View.GONE);
        }

        }
        });
         **/
        return listItem;
    }
}

