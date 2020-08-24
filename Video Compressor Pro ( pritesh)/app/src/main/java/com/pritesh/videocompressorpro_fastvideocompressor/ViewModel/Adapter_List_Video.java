package com.pritesh.videocompressorpro_fastvideocompressor.ViewModel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pritesh.videocompressorpro_fastvideocompressor.Model.Model_Video;
import com.pritesh.videocompressorpro_fastvideocompressor.R;
import com.pritesh.videocompressorpro_fastvideocompressor.View.CompressorActivity;
import com.pritesh.videocompressorpro_fastvideocompressor.View.MyVideoListActivity;
import com.pritesh.videocompressorpro_fastvideocompressor.View.OutputActivity;

import java.util.ArrayList;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.shape.CircleShape;

import static android.content.Context.MODE_PRIVATE;
import static com.pritesh.videocompressorpro_fastvideocompressor.View.MainActivity.MY_PREFS_NAME;
import static com.pritesh.videocompressorpro_fastvideocompressor.View.MyVideoListActivity.Myitem;
import static com.pritesh.videocompressorpro_fastvideocompressor.View.MyVideoListActivity.Mymenu_share;
import static com.pritesh.videocompressorpro_fastvideocompressor.View.VideoListActivity.item;
import static com.pritesh.videocompressorpro_fastvideocompressor.View.VideoListActivity.menu_share;


public class Adapter_List_Video extends ArrayAdapter<Model_Video> {


    Activity activity;

    List<Model_Video> al_menu = new ArrayList<>();
    public static String MyAnySelectc="";
    public int showcasecount=0;
    public int sharecount=0;
    //private SparseBooleanArray mSelectedItemsIds;


    public Adapter_List_Video(Activity context, int resId, List<Model_Video> laptops) {
        super(context, resId, laptops);
        // mSelectedItemsIds = new SparseBooleanArray();
        this.activity = context;
        this.al_menu=laptops;

    }




//    public void setClickLisner(VideoListActivity videoListActivity) {
//        this.ClickListener=videoListActivity;
//    }




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
            convertView = inflater.inflate(R.layout.adapter_videos, null);
            holder = new ViewHolder();

            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.select_video= (ImageView) convertView.findViewById(R.id.select_video);
            holder.video_detail = (TextView) convertView.findViewById(R.id.video_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Model_Video model_video= al_menu.get(position);
        if (!model_video.getDuration().equalsIgnoreCase("0")) {
            Glide.with(activity).load("file://" + model_video.getStr_thumb())
                    .skipMemoryCache(false)
                    .centerCrop()
                    .into(holder.iv_image);


            holder.video_detail.setText(model_video.getSize());


            //viewHolder.iv_image.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //viewHolder.rl_select.setAlpha(0);
            holder.iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (MyAnySelectc.equalsIgnoreCase("true")) {

                        if (!al_menu.get(position).getSelected()) {
                            holder.select_video.setVisibility(View.VISIBLE);
                            model_video.setSelected(true);
                            Myitem.setVisible(true);

                        } else {
                            holder.select_video.setVisibility(View.GONE);
                            model_video.setSelected(false);
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


                        Intent intent_gallery = new Intent(activity, OutputActivity.class);
                        intent_gallery.putExtra("OutoutPath", model_video.getStr_path());
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
                        model_video.setSelected(true);
                        Myitem.setVisible(true);


                    } else {
                        holder.select_video.setVisibility(View.GONE);
                        model_video.setSelected(false);
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


                    //ClickListener.onLongClick(position,activity);
                    return true;
                }
            });
        }
        return convertView;
    }




    private static class ViewHolder {

        public ImageView iv_image,select_video;
        public RelativeLayout rl_select;
        public TextView video_detail;



    }


}