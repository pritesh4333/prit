package com.reels.video.download.instagram.video.downloader.saver.story.video;


import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import android.util.Log;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.reels.video.download.instagram.video.downloader.saver.story.video.Utils.Helper;
import com.reels.video.download.instagram.video.downloader.saver.story.video.View.MainActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Retrofit;

public class DownloadService extends IntentService {

    public DownloadService() {
        super("Download Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;
    String url;
       String  urlpath;

    @Override
    protected void onHandleIntent(Intent intent) {


        url = intent.getStringExtra("url");
        urlpath = intent.getStringExtra("path");

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon2)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());
        initDownload();

    }

    private void initDownload(){



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://instagram.fbom3-2.fna.fbcdn.net")
                .build();
//        /v/t50.2886-16/127269787_211801773747556_5761008962867874100_n.mp4?_nc_ht=instagram.fbom3-2.fna.fbcdn.net&_nc_cat=104&_nc_ohc=8_Pfz4ckLEwAX_66hnk&oe=5FFA45F4&oh=d7a338221fdb4a5816a0f4dd0089e85a
//        https://instagram.fbom3-1.fna.fbcdn.net/v/t50.2886-16/10000000_455577532272035_2963416881860536950_n.mp4?_nc_ht=instagram.fbom3-1.fna.fbcdn.net&_nc_cat=111&_nc_ohc=CTpAJtMs7pUAX__smvO&oe=5FFA8DF1&oh=a4e77b10008b8a3b0247c886717cf5d1

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<ResponseBody> request = retrofitInterface.downloadFile(url);


        try {

            downloadFile(request.execute().body());

        } catch (IOException e) {

            e.printStackTrace();
        //    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    private void downloadFile(ResponseBody body) throws IOException {




        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
        String outputs=urlpath+"/Reels_" + dateFormat.format(new Date()) + ".mp4";
        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
       // File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "reel.mp4");
        OutputStream output = new FileOutputStream(outputs);
        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;
        while ((count = bis.read(data)) != -1) {

            total += count;
            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
            double current = Math.round(total / (Math.pow(1024, 2)));

            int progress = (int) ((total * 100) / fileSize);

            long currentTime = System.currentTimeMillis() - startTime;

            Download download = new Download();
            long sizes = fileSize;
            long Totoalsizes = total;
            double b = sizes;
            double k = sizes / 1024.0;
            double m = ((sizes / 1024.0) / 1024.0);
            double g = (((sizes / 1024.0) / 1024.0) / 1024.0);
            double t = ((((sizes / 1024.0) / 1024.0) / 1024.0) / 1024.0);


            double bb = Totoalsizes;
            double kk = Totoalsizes / 1024.0;
            double mm = ((Totoalsizes / 1024.0) / 1024.0);
            double gg = (((Totoalsizes / 1024.0) / 1024.0) / 1024.0);
            double tt = ((((Totoalsizes / 1024.0) / 1024.0) / 1024.0) / 1024.0);

            DecimalFormat dec = new DecimalFormat("0.00");

            String hrSize;
            String totoalSize;
            if (t > 1) {
                hrSize = dec.format(t).concat("TB");
            } else if (g > 1) {
                hrSize = dec.format(g).concat("GB");
            } else if (m > 1) {
                hrSize = dec.format(m).concat("MB");
            } else if (k > 1) {
                hrSize = dec.format(k).concat("KB");
            } else {
                hrSize = dec.format(b).concat("Bytes");
            }

            if (tt > 1) {
                totoalSize = dec.format(tt).concat("TB");
            } else if (gg > 1) {
                totoalSize = dec.format(gg).concat("GB");
            } else if (mm > 1) {
                totoalSize = dec.format(mm).concat("MB");
            } else if (kk > 1) {
                totoalSize = dec.format(kk).concat("KB");
            } else {
                totoalSize = dec.format(bb).concat("Bytes");
            }
            download.setTotalFileSize(hrSize);
            download.setCurrentFileSize(totoalSize);
            if (currentTime > 1000 * timeCount) {
                download.setProgress(progress);
                sendNotification(download);
                timeCount++;
            }

            output.write(data, 0, count);
        }
        onDownloadComplete();
        output.flush();
        output.close();
        bis.close();

    }

    private void sendNotification(Download download){

        sendIntent(download);
        notificationBuilder.setProgress(100,download.getProgress(),false);
        notificationBuilder.setContentText("Downloading file "+ download.getCurrentFileSize() +"/"+totalFileSize +" MB");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(Download download){


        Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
        intent.putExtra("download",download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(){

        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0,0,false);
        notificationBuilder.setContentText("File Downloaded");
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

}
