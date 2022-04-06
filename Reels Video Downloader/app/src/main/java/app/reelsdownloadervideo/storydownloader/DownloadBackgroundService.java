package app.reelsdownloadervideo.storydownloader;


import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;

import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.Intent;
import android.view.Display;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import okhttp3.ResponseBody;
public class DownloadBackgroundService extends IntentService {

    public DownloadBackgroundService() {
        super("Download Service");
    }

    private NotificationCompat.Builder notificationBuilders;
    private NotificationManager notificationManagers;
    private int totalFileSizes;
    String urls;
    String  urlpaths;

    @Override
    protected void onHandleIntent(Intent intent) {


        urls = intent.getStringExtra("url");
        urlpaths = intent.getStringExtra("path");

        notificationManagers = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilders = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setAutoCancel(true);
        notificationManagers.notify(0, notificationBuilders.build());

        Download();

    }




    private void downloadFiles(ResponseBody bodys) throws IOException {

        final SimpleDateFormat dateFormats = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);

        String outputs=urlpaths+"/Reels_" + dateFormats.format(new Date()) + ".mp4";

        int count;
        byte data[] = new byte[1024 * 4];

        long fileSize = bodys.contentLength();
        InputStream bis = new BufferedInputStream(bodys.byteStream(), 1024 * 8);
       // File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "reel.mp4");
        OutputStream output = new FileOutputStream(outputs);
        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;

        while ((count = bis.read(data)) != -1) {

            total += count;
            totalFileSizes = (int) (fileSize / (Math.pow(1024, 2)));
            double current = Math.round(total / (Math.pow(1024, 2)));

            int progress = (int) ((total * 100) / fileSize);

            long currentTime = System.currentTimeMillis() - startTime;

            ModelDownload download = new ModelDownload();

            long sizes = fileSize;

            long Totoalsizes = total;

            double b = sizes;
            double k = sizes / 1024.0;
            double g = (((sizes / 1024.0) / 1024.0) / 1024.0);
            double t = ((((sizes / 1024.0) / 1024.0) / 1024.0) / 1024.0);
            double m = ((sizes / 1024.0) / 1024.0);



            double bb = Totoalsizes;
            double gg = (((Totoalsizes / 1024.0) / 1024.0) / 1024.0);
            double tt = ((((Totoalsizes / 1024.0) / 1024.0) / 1024.0) / 1024.0);
            double kk = Totoalsizes / 1024.0;
            double mm = ((Totoalsizes / 1024.0) / 1024.0);



            DecimalFormat dec = new DecimalFormat("0.00");

            String hrSizes;
            String totoalSizes;

            if (t > 1) {
                hrSizes = dec.format(t).concat("TB");
            } else if (g > 1) {
                hrSizes = dec.format(g).concat("GB");
            } else if (m > 1) {
                hrSizes = dec.format(m).concat("MB");
            } else if (k > 1) {
                hrSizes = dec.format(k).concat("KB");
            } else {
                hrSizes = dec.format(b).concat("Bytes");
            }

            if (tt > 1) {
                totoalSizes = dec.format(tt).concat("TB");
            } else if (gg > 1) {
                totoalSizes = dec.format(gg).concat("GB");
            } else if (mm > 1) {
                totoalSizes = dec.format(mm).concat("MB");
            } else if (kk > 1) {
                totoalSizes = dec.format(kk).concat("KB");
            } else {
                totoalSizes = dec.format(bb).concat("Bytes");
            }
            download.setTotalFileSize(hrSizes);
            download.setCurrentFileSize(totoalSizes);
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
    private void Download(){
        Retrofit retroit = new Retrofit.Builder()
                .baseUrl("https://scontent")
                .build();
//        /v/t50.2886-16/127269787_211801773747556_5761008962867874100_n.mp4?_nc_ht=instagram.fbom3-2.fna.fbcdn.net&_nc_cat=104&_nc_ohc=8_Pfz4ckLEwAX_66hnk&oe=5FFA45F4&oh=d7a338221fdb4a5816a0f4dd0089e85a
//        https://instagram.fbom3-1.fna.fbcdn.net/v/t50.2886-16/10000000_455577532272035_2963416881860536950_n.mp4?_nc_ht=instagram.fbom3-1.fna.fbcdn.net&_nc_cat=111&_nc_ohc=CTpAJtMs7pUAX__smvO&oe=5FFA8DF1&oh=a4e77b10008b8a3b0247c886717cf5d1

        RetroInterface retrofitInterfaces = retroit.create(RetroInterface.class);
        Call<ResponseBody> request = retrofitInterfaces.downloadFiles(urls);


        try {

            downloadFiles(request.execute().body());

        } catch (IOException e) {

        }
    }
    private void sendIntent(ModelDownload download){


        Intent intent = new Intent("message_progress");
        intent.putExtra("download",download);
        LocalBroadcastManager.getInstance(DownloadBackgroundService.this).sendBroadcast(intent);
    }
    private void sendNotification(ModelDownload downloads){

        sendIntent(downloads);
        notificationBuilders.setProgress(100,downloads.getProgress(),false);
        notificationBuilders.setContentText("Downloading file "+ downloads.getCurrentFileSize() +"/"+totalFileSizes +" MB");
        notificationManagers.notify(0, notificationBuilders.build());
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManagers.cancel(0);
    }

    private void onDownloadComplete(){

        ModelDownload download = new ModelDownload();
        download.setProgress(100);
        sendIntent(download);

        notificationManagers.cancel(0);
        notificationBuilders.setProgress(0,0,false);
        notificationBuilders.setContentText("File Downloaded");
        notificationManagers.notify(0, notificationBuilders.build());

    }


}
