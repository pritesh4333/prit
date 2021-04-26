package app.reelsdownloadervideo.storydownloader;



import java.util.ArrayList;
import java.util.List;

import static app.reelsdownloadervideo.storydownloader.MyReelsList.Myitems;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import static app.reelsdownloadervideo.storydownloader.MyReelsList.Mymenu_shares;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;


public class ReelsAdapter extends ArrayAdapter<ReelsModel> {


    Activity activitys;

    List<ReelsModel> al_menus = new ArrayList<>();
    public int ssharecounts=0;
    public static String sMyAnySelectcs="";
    public int showcasecount=0;



    public ReelsAdapter(Activity context, int resId, List<ReelsModel> laptops) {
        super(context, resId, laptops);
         this.activitys = context;
        this.al_menus=laptops;

    }
    @Override
    public int getViewTypeCount() {
        return al_menus.size();

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return al_menus.size();
    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holders;

        if (convertView == null) {
            LayoutInflater inflaters = (LayoutInflater) activitys

                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflaters.inflate(R.layout.reels_list_data, null);

            holders = new ViewHolder();


            holders.select_videos= (ImageView) convertView.findViewById(R.id.sselect_videos);
            holders.video_details = (TextView) convertView.findViewById(R.id.svideo_details);
            holders.iv_images = (ImageView) convertView.findViewById(R.id.siv_images);
            convertView.setTag(holders);
        } else {
            holders = (ViewHolder) convertView.getTag();
        }
        final ReelsModel model_video= al_menus.get(position);
        if (!model_video.getDuration().equalsIgnoreCase("0")) {


            holders.video_details.setText(model_video.getSize());

            Glide.with(activitys).load("file://" + model_video.getStr_thumb())
                    .skipMemoryCache(false)
                    .centerCrop()
                    .into(holders.iv_images);


            holders.iv_images.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (sMyAnySelectcs.equalsIgnoreCase("true")) {

                        if (!al_menus.get(position).getSelected()) {
                            holders.select_videos.setVisibility(View.VISIBLE);
                            model_video.setSelected(true);
                            Myitems.setVisible(true);

                        } else {
                            holders.select_videos.setVisibility(View.GONE);
                            model_video.setSelected(false);
                            Myitems.setVisible(false);


                        }
                        for (int i = 0; i < al_menus.size(); i++) {
                            if (al_menus.get(i).getSelected()) {
                                sMyAnySelectcs = "true";
                                Myitems.setVisible(true);
                                break;
                            } else {
                                sMyAnySelectcs = "";
                                Myitems.setVisible(false);
                            }
                        }
                        ssharecounts = 0;
                        for (int i = 0; i < al_menus.size(); i++) {
                            if (al_menus.get(i).getSelected()) {
                                ssharecounts++;

                            }
                        }
                        if (ssharecounts == 1) {
                            Mymenu_shares.setVisible(true);
                        } else {
                            Mymenu_shares.setVisible(false);
                        }

                    } else {


                        Intent intent_gallerys = new Intent(activitys, PlayerActivity.class);

                        intent_gallerys.putExtra("OutoutPath", model_video.getStr_path());
                        intent_gallerys.putExtra("Scrren", "MyVideo");
                        activitys.startActivity(intent_gallerys);

                        activitys.finish();
                    }


                }
            });
            holders.iv_images.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {


                    if (!al_menus.get(position).getSelected()) {
                        holders.select_videos.setVisibility(View.VISIBLE);
                        model_video.setSelected(true);
                        Myitems.setVisible(true);
                    } else {
                        holders.select_videos.setVisibility(View.GONE);
                        model_video.setSelected(false);
                        Myitems.setVisible(false);
                    }

                    for (int i = 0; i < al_menus.size(); i++) {
                        if (al_menus.get(i).getSelected()) {
                            sMyAnySelectcs = "true";
                            Myitems.setVisible(true);
                            break;
                        } else {
                            sMyAnySelectcs = "";
                            Myitems.setVisible(false);
                        }
                    }
                    for (int i = 0; i < al_menus.size(); i++) {
                        if (al_menus.get(i).getSelected()) {
                            if (!Mymenu_shares.isVisible()) {
                                Mymenu_shares.setVisible(true);
                                break;
                            } else {
                                Mymenu_shares.setVisible(false);
                            }

                        } else {
                            Mymenu_shares.setVisible(false);
                        }
                    }
                    return true;
                }
            });
        }
        return convertView;
    }
    private static class ViewHolder {

        public ImageView iv_images,select_videos;
        public RelativeLayout rl_selects;
        public TextView video_details;



    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }



   


}