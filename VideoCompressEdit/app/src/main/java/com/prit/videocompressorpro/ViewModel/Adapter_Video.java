package com.prit.videocompressorpro.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.prit.videocompressorpro.Model.Model_Video;
import com.prit.videocompressorpro.R;
import com.prit.videocompressorpro.View.CompressorActivity;


import java.util.ArrayList;


public class Adapter_Video extends ArrayAdapter<Model_Video> {

    Context context;
    Activity activity;
    ViewHolder viewHolder;
    ArrayList<Model_Video> al_menu = new ArrayList<>();



    public Adapter_Video(Context context, ArrayList<Model_Video> al_menu) {
        super(context, R.layout.adapter_videos, al_menu);
        this.al_menu = al_menu;
        this.context = context;
        this.activity = (Activity) context;



    }

    @Override
    public int getCount() {


        return al_menu.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.size() > 0) {
            return al_menu.size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            viewHolder= new  ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_videos, parent, false);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.rl_select = (RelativeLayout) convertView.findViewById(R.id.rl_select);
            Glide.with(context).load("file://" + al_menu.get(position).getStr_thumb())
                    .skipMemoryCache(false)
                    .centerCrop()
                    .into(viewHolder.iv_image);


            //viewHolder.iv_image.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //viewHolder.rl_select.setAlpha(0);
            viewHolder.iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_gallery = new Intent(context, CompressorActivity.class);
                    intent_gallery.putExtra("video",al_menu.get(position).getStr_path());
                    activity.startActivity(intent_gallery);


                    //  VideoConver(al_video.get(position).getStr_path());

                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Adapter_Video.ViewHolder) convertView.getTag();
        }













        return convertView;

    }

    private static class ViewHolder {

        public ImageView iv_image;
        public RelativeLayout rl_select;


    }


}