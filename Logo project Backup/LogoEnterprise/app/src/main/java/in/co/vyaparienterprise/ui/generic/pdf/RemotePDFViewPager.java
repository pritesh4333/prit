package in.co.vyaparienterprise.ui.generic.pdf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.io.File;
import java.util.UUID;

import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import in.co.vyaparienterprise.R;

public class RemotePDFViewPager extends ViewPager implements DownloadFile.Listener {
    protected Context context;
    protected DownloadFile.Listener listener;

    public RemotePDFViewPager(Context context, String pdfUrl, DownloadFile.Listener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        init(pdfUrl);
    }

    public RemotePDFViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    private void init(String pdfUrl) {
        String guid = UUID.randomUUID().toString();
        DownloadFile downloadFile = new DownloadFileUrlConnectionImpl(context, new Handler(), this);
        //downloadFile.download(pdfUrl, new File(context.getCacheDir(), FileUtil.extractFileNameFromURL(pdfUrl)).getAbsolutePath());
        downloadFile.download(pdfUrl, new File(context.getExternalFilesDir("pdf"), "invoice-" + guid + ".pdf").getAbsolutePath());
    }

    @SuppressLint("CustomViewStyleable")
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a;

            a = context.obtainStyledAttributes(attrs, R.styleable.PDFViewPager);
            String pdfUrl = a.getString(R.styleable.PDFViewPager_pdfUrl);

            if (pdfUrl != null && pdfUrl.length() > 0) {
                init(pdfUrl);
            }

            a.recycle();
        }
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        listener.onSuccess(url, destinationPath);
    }

    @Override
    public void onFailure(Exception e) {
        listener.onFailure(e);
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        listener.onProgressUpdate(progress, total);
    }

    public class NullListener implements DownloadFile.Listener {
        public void onSuccess(String url, String destinationPath) {
        }

        public void onFailure(Exception e) {
        }

        public void onProgressUpdate(int progress, int total) {
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}