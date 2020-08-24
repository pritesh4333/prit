package com.pritesh.videocompressorpro_fastvideocompressor.ViewModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pritesh.videocompressorpro_fastvideocompressor.Model.Model_Video;
import com.pritesh.videocompressorpro_fastvideocompressor.R;
import com.pritesh.videocompressorpro_fastvideocompressor.View.CompressorActivity;

import java.util.ArrayList;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.shape.CircleShape;

import static android.content.Context.MODE_PRIVATE;
import static com.pritesh.videocompressorpro_fastvideocompressor.View.MainActivity.MY_PREFS_NAME;
import static com.pritesh.videocompressorpro_fastvideocompressor.View.VideoListActivity.item;
import static com.pritesh.videocompressorpro_fastvideocompressor.View.VideoListActivity.menu_share;


public class Adapter_Video extends ArrayAdapter<Model_Video> {


    Activity activity;

    List<Model_Video> al_menu = new ArrayList<>();
    public static String isAnySelectec="";
    public int showcasecount=0;
    public int sharecount=0;
    //private SparseBooleanArray mSelectedItemsIds;


    public Adapter_Video(Activity context, int resId, List<Model_Video> laptops) {
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
        Glide.with(activity).load("file://" + model_video.getStr_thumb())
                .skipMemoryCache(false)
                .centerCrop()
                .into(holder.iv_image);

        holder.video_detail.setText(model_video.getSize()+" "+model_video.getDuration());




        //viewHolder.iv_image.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //viewHolder.rl_select.setAlpha(0);
        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isAnySelectec.equalsIgnoreCase("true")){

                    if (!al_menu.get(position).getSelected()){
                        holder.select_video.setVisibility(View.VISIBLE);
                        model_video.setSelected(true);
                        item.setVisible(true);

                    }else{
                        holder.select_video.setVisibility(View.GONE);
                        model_video.setSelected(false);
                        item.setVisible(false);


                    }
                    for (int i=0;i<al_menu.size();i++){
                        if (al_menu.get(i).getSelected()){
                            isAnySelectec="true";
                            item.setVisible(true);
                            break;
                        }else{
                            isAnySelectec="";
                            item.setVisible(false);
                        }
                    }
                    sharecount=0;
                    for (int i=0;i<al_menu.size();i++){
                        if (al_menu.get(i).getSelected()){
                            sharecount++;

                        }
                    }
                    if (sharecount==1){
                        menu_share.setVisible(true);
                    }else{
                        menu_share.setVisible(false);
                    }

                }else {
                    final MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

                        try {
                            mediaMetadataRetriever.setDataSource(model_video.getStr_path());
                            final String width = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
                            if (Integer.parseInt(width)<187){
                                showAlertError("Can not convert this file video size  is to small for compress");
                            }else {
                                Intent intent_gallery = new Intent(activity, CompressorActivity.class);
                                intent_gallery.putExtra("video", model_video.getStr_path());
                                intent_gallery.putExtra("Scrren", "Gallery");
                                activity.startActivity(intent_gallery);
                                activity.finish();
                            }
                        }catch (Exception e ){

                        }

                }


            }
        });
        holder.iv_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {



                if (!al_menu.get(position).getSelected()){
                    holder.select_video.setVisibility(View.VISIBLE);
                    model_video.setSelected(true);
                    item.setVisible(true);






                }else{
                    holder.select_video.setVisibility(View.GONE);
                    model_video.setSelected(false);
                    item.setVisible(false);

                }

                for (int i=0;i<al_menu.size();i++){

                    if (al_menu.get(i).getSelected()){
                        isAnySelectec="true";
                        item.setVisible(true);
                        break;
                    }else{
                        isAnySelectec="";
                        item.setVisible(false);

                    }
                }
                for (int i=0;i<al_menu.size();i++){

                    if (al_menu.get(i).getSelected()){
                        if (!menu_share.isVisible()) {
                            menu_share.setVisible(true);
                            break;
                        }else{
                            menu_share.setVisible(false);
                        }

                    }else{

                        menu_share.setVisible(false);

                    }
                }


                //ClickListener.onLongClick(position,activity);
                return true;
            }
        });



        if (position==4){
            if (showcasecount==0) {
                showcasecount = 1;
                showShowcase(holder.iv_image);
            }
        }









        return convertView;

    }


    public  void showShowcase(ImageView iv_image){
        SharedPreferences prefs = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String Showcase = prefs.getString("Showcase", "");
try{
        if (Showcase.equalsIgnoreCase("")){


            new MaterialShowcaseView.Builder(activity)
                    .setTarget(iv_image)
                    .setShape(new CircleShape())
                    .setDismissText("GOT IT")
                    .setContentText("Select Video You Want To Compress From The List")
                    .setDelay(1000)
                    .setFadeDuration(500)
                    .setDismissOnTouch(true)
                    .setMaskColour(activity.getResources().getColor(R.color.primary_dark))
                    .setContentTextColor(activity.getResources().getColor(R.color.yellow))
                    // optional but starting animations immediately in onCreate can make them choppy
                   // .singleUse("Videocompress") // provide a unique ID used to ensure it is only shown once
                    .show();


        }else {
        }
    }catch(Exception e){

    }
    }

    private static class ViewHolder {

        public ImageView iv_image,select_video;
        public RelativeLayout rl_select;
        public TextView video_detail;



    }

    public void showAlertError(String message){
        new AlertDialog.Builder(activity)
                .setTitle("Alert")
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();


                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}