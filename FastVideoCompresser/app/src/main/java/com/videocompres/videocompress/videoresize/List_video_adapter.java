package com.videocompres.videocompress.videoresize;



import static com.videocompres.videocompress.videoresize.AcitivityMyvideolist.Myitem;
import static com.videocompres.videocompress.videoresize.AcitivityMyvideolist.Mymenu_share;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class List_video_adapter extends ArrayAdapter<Video_model> {


    Activity activity;

    List<Video_model> al_menu = new ArrayList<>();
    public static String MyAnySelectc="";
     public int sharecount=0;


    public List_video_adapter(Activity context, int resId, List<Video_model> laptops) {
        super(context, resId, laptops);
         this.activity = context;
        this.al_menu=laptops;
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
            return al_menu.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.video_adapts, null);
            holder = new ViewHolder();

            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.select_video= (ImageView) convertView.findViewById(R.id.select_video);
            holder.video_detail = (TextView) convertView.findViewById(R.id.video_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Video_model _videoModel = al_menu.get(position);
        if (!_videoModel.getDuration().equalsIgnoreCase("0")) {
            Glide.with(activity).load("file://" + _videoModel.getStr_thumb())
                    .skipMemoryCache(false)
                    .centerCrop()
                    .into(holder.iv_image);


            holder.video_detail.setText(_videoModel.getSize());

            holder.iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (MyAnySelectc.equalsIgnoreCase("true")) {

                        if (!al_menu.get(position).getSelected()) {
                            holder.select_video.setVisibility(View.VISIBLE);
                            _videoModel.setSelected(true);
                            Myitem.setVisible(true);

                        } else {
                            holder.select_video.setVisibility(View.GONE);
                            _videoModel.setSelected(false);
                            Myitem.setVisible(false);
                        }
                        for (int i = 0; i < al_menu.size(); i++) {
                            if (al_menu.get(i).getSelected()) {
                                MyAnySelectc = "true";
                                Myitem.setVisible(true);
                                break;
                            } else {
                                MyAnySelectc = "";
                                Myitem.setVisible(false);
                            }
                        }
                        sharecount = 0;
                        for (int i = 0; i < al_menu.size(); i++) {
                            if (al_menu.get(i).getSelected()) {
                                sharecount++;
                            }
                        }
                        if (sharecount == 1) {
                            Mymenu_share.setVisible(true);
                        } else {
                            Mymenu_share.setVisible(false);
                        }
                    } else {
                        Intent intent_gallery = new Intent(activity, ActivityOutput.class);
                        intent_gallery.putExtra("OutoutPath", _videoModel.getStr_path());
                        intent_gallery.putExtra("Scrren", "MyVideo");
                        activity.startActivity(intent_gallery);
                        activity.finish();
                    }
                }
            });
            holder.iv_image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!al_menu.get(position).getSelected()) {
                        holder.select_video.setVisibility(View.VISIBLE);
                        _videoModel.setSelected(true);
                        Myitem.setVisible(true);
                    } else {
                        holder.select_video.setVisibility(View.GONE);
                        _videoModel.setSelected(false);
                        Myitem.setVisible(false);
                    }
                    for (int i = 0; i < al_menu.size(); i++) {
                        if (al_menu.get(i).getSelected()) {
                            MyAnySelectc = "true";
                            Myitem.setVisible(true);
                            break;
                        } else {
                            MyAnySelectc = "";
                            Myitem.setVisible(false);
                        }
                    }
                    for (int i = 0; i < al_menu.size(); i++) {

                        if (al_menu.get(i).getSelected()) {
                            if (!Mymenu_share.isVisible()) {
                                Mymenu_share.setVisible(true);
                                break;
                            } else {
                                Mymenu_share.setVisible(false);
                            }
                        } else {
                            Mymenu_share.setVisible(false);
                        }
                    }
                    return true;
                }
            });
        }
        return convertView;
    }
    private static class ViewHolder {
        public ImageView iv_image,select_video;
        public TextView video_detail;
    }
}