package com.pritesh.all.in.one.god.goddess.allinoneaarti.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.Activity.LikeActivity;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.Activity.MainActivity;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.Player.PlayerAndViewActivity;
import com.pritesh.all.in.one.god.goddess.allinoneaarti.R;


public class GodName_Adapter extends RecyclerView.Adapter<GodName_Adapter.MyViewHolder> {

    private String[] godNameList;
    private int[] godImageList;
    private String[] postionList;
    Activity activity;
    String like;

    public GodName_Adapter(MainActivity mainActivity, String[] godName,int[] godImage,String like) {
        this.activity=mainActivity;
        this.godNameList=godName;
        this.godImageList=godImage;
        this.like=like;
    }

    public GodName_Adapter(LikeActivity likeActivity, String[] godnameslist, int[] imgs, String like, String[] postionList) {
        this.activity=likeActivity;
        this.godNameList=godnameslist;
        this.godImageList=imgs;
        this.postionList=postionList;
        this.like=like;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView godDp,playIcon;
        public LinearLayout godLinear;
        public TextView godName;

        public MyViewHolder(View view) {
            super(view);
            godName = (TextView) view.findViewById(R.id.godName);
            godDp = (ImageView) view.findViewById(R.id.godDp);
            playIcon = (ImageView) view.findViewById(R.id.playIcon);
            godLinear = (LinearLayout) view.findViewById(R.id.godLinear);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.godname_custome_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Glide.with(activity).load(godImageList[position])
                .skipMemoryCache(false)
                .centerCrop()
                .into(holder.godDp);

        holder.godName.setText( godNameList[position]);





        holder.godLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int resID=0;
                  int pos = 0;
                String lyrics="";
                if (postionList!=null) {
                String positions=postionList[position];
                String lastthree= positions.substring(positions.length() - 2);
                if (lastthree.contains("n")){
                    lastthree=positions.substring(positions.length() - 1);
                }
                    pos=Integer.parseInt(lastthree);
                }else {
                    pos = position;
                }
                if (pos==0) {
                      resID = activity.getResources().getIdentifier("jaiganesh", "raw", activity.getPackageName());
                      lyrics=activity.getResources().getString(R.string.ganpatilyrics);
                }else if (pos==1){
                    resID = activity.getResources().getIdentifier("shreeramchandra", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.ramchandralyrics);
                }else if (pos==2){
                    resID = activity.getResources().getIdentifier("vishnuarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.visnuaartilyrics);
                }else if (pos==3){
                    resID = activity.getResources().getIdentifier("hanumanjiaarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.hanumanaartilyrics);
                }else if (pos==4){
                    resID = activity.getResources().getIdentifier("shivaarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.shivaartilyrics);
                }else if (pos==5){
                    resID = activity.getResources().getIdentifier("laxmiaarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.laxmiaartilyrics);
                }else if (pos==6){
                    resID = activity.getResources().getIdentifier("ambemaaarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.ambemaaartilyrics);
                }else if (pos==7){
                    resID = activity.getResources().getIdentifier("krishnaarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.krishartilyrics);
                }else if (pos==8){
                    resID = activity.getResources().getIdentifier("saibabaarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.saibabartilyrics);
                }else if (pos==9){
                    resID = activity.getResources().getIdentifier("swaminarayarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.swaminarayanlyrics);
                }else if (pos==10){
                    resID = activity.getResources().getIdentifier("gayatriarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.gaytriaartilyrics);
                }else if (pos==11){
                    resID = activity.getResources().getIdentifier("kalimaarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.kalimalyrics);
                }else if (pos==12){
                    resID = activity.getResources().getIdentifier("saraswatiarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.sarasvatiartilyrics);
                }else if (pos==13){
                    resID = activity.getResources().getIdentifier("santoshiarti", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.santoshiaartilyrics);
                }else if (pos==14){
                    resID = activity.getResources().getIdentifier("ganpatimarathi", "raw", activity.getPackageName());
                    lyrics=activity.getResources().getString(R.string.ganpatimarathilyrics);
                }

                Intent i = new Intent(activity, PlayerAndViewActivity.class);
                i.putExtra("song",resID);
                i.putExtra("lyrics",lyrics);
                i.putExtra("postion",pos);
                if (postionList!=null) {
                    i.putExtra("postionList", pos);
                }
                if (like.equalsIgnoreCase("like")) {
                    i.putExtra("like", "true");
                }else{
                    i.putExtra("like", "false");

                }
                activity.startActivity(i);



            }
        });
    }

    @Override
    public int getItemCount() {
        return godNameList.length;
    }
}