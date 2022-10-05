package com.acumengroup.mobile.markets;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.acumengroup.mobile.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by sushant.patil on 7/14/2016.
 */
public class PdfActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener {

    private PDFView pdfview;
    private String pdfFileName;
    private ProgressDialog dialogue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.pdfview_layout);

            String showPDFUrl = getIntent().getStringExtra("showPDFUrl");

            //pdfFileName = "Help.pdf";
            pdfview = findViewById(R.id.pdfView);

            new ShowPdf().execute(showPDFUrl);

//            pdfview.fromUri(Uri.fromFile(new File(showPDFUrl)))
//                    .defaultPage(0)
//                    .onPageChange(PdfActivity.this)
//                    .enableAnnotationRendering(true)
//                    .onLoad(this)
//                    .scrollHandle(new DefaultScrollHandle(this))
//                    .spacing(10) // in dp
//                    .onPageError(this)
//                    .load();

        } catch (Exception e) {
            Toast.makeText(getApplication(), "File not supported", Toast.LENGTH_LONG).show();
        }
    }

    private class ShowPdf extends AsyncTask<String, Void, InputStream> {


        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL uri = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }
            } catch (MalformedURLException ex) {
                ex.getMessage();
            } catch (IOException ex) {
                ex.getMessage();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // pdfview.fromStream(inputStream).load();

            pdfview.fromStream(inputStream).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                  dialogue.dismiss();
                }
            }).load();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialogue = new ProgressDialog(PdfActivity.this);
            dialogue.setTitle("Loading...");
            dialogue.show();

        }
    }


    @Override
    public void loadComplete(int i) {


    }

    @Override
    public void onPageChanged(int i, int i1) {

    }

    @Override
    public void onPageError(int i, Throwable throwable) {

        Toast.makeText(getApplication(), "Something wrong happened", Toast.LENGTH_LONG).show();
    }
}
