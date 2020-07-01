package com.prit.videotomp3.View.MyFiles;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prit.videotomp3.R;
import com.prit.videotomp3.View.Player.BarVisualizerActivity;
import com.prit.videotomp3.View.Player.CircleBarVisualizerActivity;
import com.prit.videotomp3.View.Player.CircleVisualizerActivity;
import com.prit.videotomp3.View.Player.LineBarVisualizerActivity;
import com.prit.videotomp3.View.Player.LineVisualizerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;




public class Mp3FileAdapter extends RecyclerView.Adapter<Mp3FileAdapter.ViewHolder> {

    private Context context;
    public static ArrayList<String> name;
    public static ArrayList<String> size;
    public static ArrayList<String> path;
    ArrayList<Integer> number;


    public Mp3FileAdapter(Context context, ArrayList<String> filenames, ArrayList<String> size, ArrayList<String> path) {
        this.context = context;
        this.name = filenames;
        this.size = size;
        this.path=path;

        number= new ArrayList<>();
        number.add(0);
        number.add(1);
        number.add(2);
        number.add(3);
        number.add(4);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_mp3_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.file_name.setText(name.get(position));
        viewHolder.size.setText(size.get(position));
        viewHolder.song_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!size.get(position).equalsIgnoreCase("0.00Bytes")) {
                    sendtoplayer(path.get(position),position);
                }else{
                    Toast.makeText(context,"File Size Is Zero",Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.more_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context,   viewHolder.more_option);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.list_popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Share")){

                            MediaScannerConnection.scanFile(context, new String[] {String.valueOf(path.get(position))},

                                    null, new MediaScannerConnection.OnScanCompletedListener() {
                                        public void onScanCompleted(String path, Uri uri) {


                                            Intent shareIntent = new Intent(
                                                    android.content.Intent.ACTION_SEND);
                                            shareIntent.setType("audio/*");
                                            shareIntent.putExtra(
                                                    android.content.Intent.EXTRA_SUBJECT, "Share");
                                            shareIntent.putExtra(
                                                    android.content.Intent.EXTRA_TITLE, "Share");
                                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                            shareIntent
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                                            context.startActivity(Intent.createChooser(shareIntent,"Share"));

                                        }
                                    });

                        }else {
                            AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                                    // set message, title, and icon
                                    .setTitle("Delete")
                                    .setMessage("Do you want to Delete")
                                    .setIcon(R.drawable.ic_delete)
                                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            //your deleting code
                                            try {
                                                File output=new File(path.get(position));
                                                if (output.exists()) {
                                                    final String where = MediaStore.MediaColumns.DATA + "=?";
                                                    final String[] selectionArgs = new String[] {
                                                            output.getAbsolutePath()
                                                    };
                                                    final ContentResolver contentResolver = context.getContentResolver();
                                                    final Uri filesUri = MediaStore.Files.getContentUri("external");

                                                    contentResolver.delete(filesUri, where, selectionArgs);

                                                    if (output.exists()) {

                                                        contentResolver.delete(filesUri, where, selectionArgs);
                                                    }
                                                    dialog.dismiss();
                                                    System.out.println("file Deleted :" + output);

                                                } else {
                                                    dialog.dismiss();
                                                    Toast.makeText(context,"File Not Found.", Toast.LENGTH_LONG).show();
                                                    System.out.println("file not Found :" + output);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                dialog.dismiss();
                                            }

                                            Toast.makeText(context,"Delete", Toast.LENGTH_LONG).show();
                                            name.remove(position);
                                            size.remove(position);
                                            path.remove(position);
                                            notifyDataSetChanged();
//
                                        }
                                    })
                                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .create();
                            myQuittingDialogBox.show();
                        }

                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
    }


    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView file_name;
        public TextView size;
        public LinearLayout song_layout;
        public ImageView more_option;

        public ViewHolder(View itemView) {
            super(itemView);

            file_name = (TextView) itemView.findViewById(R.id.file_name);
            size = (TextView) itemView.findViewById(R.id.size);
            song_layout=(LinearLayout)itemView.findViewById(R.id.song_layout);
            more_option=(ImageView)itemView.findViewById(R.id.more_option);

        }
    }
    public void sendtoplayer(String output, int position){
        Random randomGenerator= new Random();
        int index = randomGenerator.nextInt(number.size());

        if(index==0){
            Intent i = new Intent(context, BarVisualizerActivity.class);
            i.putExtra("OutputPath",output);
            i.putExtra("position",position);
            context.startActivity(i);
        }
        if(index==1){
            Intent i = new Intent(context, CircleBarVisualizerActivity.class);
            i.putExtra("OutputPath",output);
            i.putExtra("position",position);
            context.startActivity(i);
        }
        if(index==2){
            Intent i = new Intent(context, CircleVisualizerActivity.class);
            i.putExtra("OutputPath",output);
            i.putExtra("position",position);
            context.startActivity(i);
        }
        if(index==3){
            Intent i = new Intent(context, LineBarVisualizerActivity.class);
            i.putExtra("OutputPath",output);
            i.putExtra("position",position);
            context.startActivity(i);
        }
        if(index==4){
            Intent i = new Intent(context, LineVisualizerActivity.class);
            i.putExtra("OutputPath",output);
            i.putExtra("position",position);
            context.startActivity(i);
        }

    }
}

