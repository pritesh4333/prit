package in.co.vyaparienterprise.ui.generic.pdf;


import android.content.Context;
import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import es.voghdev.pdfviewpager.library.remote.DownloadFile;

public class DownloadFileUrlConnectionImpl implements DownloadFile {
    private static final int KILOBYTE = 1024;

    private static final int BUFFER_LEN = 1 * KILOBYTE;
    private static final int NOTIFY_PERIOD = 10 * KILOBYTE;

    Context context;
    Handler uiThread;
    Listener listener = new NullListener();

    public DownloadFileUrlConnectionImpl(Context context, Handler uiThread, Listener listener) {
        this.context = context;
        this.uiThread = uiThread;
        this.listener = listener;
    }

    @Override
    public void download(final String url, final String destinationPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(destinationPath);
                    FileOutputStream fileOutput = new FileOutputStream(file);
                    HttpURLConnection urlConnection;
                    URL urlObj = new URL(url);
                    urlConnection = (HttpURLConnection) urlObj.openConnection();
                    int totalSize = urlConnection.getContentLength();
                    int downloadedSize = 0;
                    int counter = 0;
                    byte[] buffer = new byte[BUFFER_LEN];
                    int bufferLength;
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    while ((bufferLength = in.read(buffer)) > 0) {
                        fileOutput.write(buffer, 0, bufferLength);
                        downloadedSize += bufferLength;
                        counter += bufferLength;
                        if (listener != null && counter > NOTIFY_PERIOD) {
                            notifyProgressOnUiThread(downloadedSize, totalSize);
                            counter = 0;
                        }
                    }

                    urlConnection.disconnect();
                    fileOutput.close();

                } catch (MalformedURLException e) {
                    notifyFailureOnUiThread(e);
                } catch (IOException e) {
                    notifyFailureOnUiThread(e);
                }

                notifySuccessOnUiThread(url, destinationPath);
            }
        }).start();
    }

    protected void notifySuccessOnUiThread(final String url, final String destinationPath) {
        if (uiThread == null) {
            return;
        }

        uiThread.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(url, destinationPath);
            }
        }, 250);
    }

    protected void notifyFailureOnUiThread(final Exception e) {
        if (uiThread == null) {
            return;
        }

        uiThread.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(e);
            }
        }, 250);
    }

    private void notifyProgressOnUiThread(final int downloadedSize, final int totalSize) {
        if (uiThread == null) {
            return;
        }

        uiThread.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onProgressUpdate(downloadedSize, totalSize);
            }
        }, 250);
    }

    protected class NullListener implements Listener {
        public void onSuccess(String url, String destinationPath) {
        }

        public void onFailure(Exception e) {
        }

        public void onProgressUpdate(int progress, int total) {
        }
    }
}
