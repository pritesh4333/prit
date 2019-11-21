package in.co.vyapari.ui.activity.invoice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.ui.generic.pdf.RemotePDFViewPager;
import in.co.vyapari.ui.generic.showcase.Showcase;
import in.co.vyapari.util.Utils;

public class InvoicePdfShowActivity extends SohoActivity implements DownloadFile.Listener {

    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.share)
    ImageView share;

    private RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter adapter;

    private Context mContext;
    private String pdfUrl;
    private String destinationPath;
    private int invoiceType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_pdf_show);
        ButterKnife.bind(this);
        setToolbarConfig(getString(R.string.invoice), false);
        mContext = this;

        Utils.showLoading(mContext);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pdfUrl = bundle.getString(Constants.URL);
            invoiceType = bundle.getInt(Constants.INVOICE_TYPE);
        }

        share.setVisibility(View.GONE);

        remotePDFViewPager = new RemotePDFViewPager(mContext, pdfUrl, InvoicePdfShowActivity.this);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        Utils.hideLoading();
        this.destinationPath = destinationPath;
        adapter = new PDFPagerAdapter(this, destinationPath);
        remotePDFViewPager.setAdapter(adapter);

        root.removeAllViewsInLayout();
        root.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        share.setVisibility(View.VISIBLE);

        title(0);

        remotePDFViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (adapter.getCount() > 1) {
            Showcase.from(InvoicePdfShowActivity.this)
                    .setContentView(R.layout.showcase_pdf_other_pages)
                    .on(R.id.showcase_pdf_other_pages_swipe)
                    .displaySwipableLeft()
                    .showOnce(Constants.INVOICE_PDF_DETAIL);
        }
    }

    @Override
    public void onFailure(Exception e) {
        Utils.hideLoading();
        Toasty.error(mContext, getString(R.string.error)).show();
        finish();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
    }

    private void title(int currentPage) {
        currentPage = currentPage + 1;
        String paging = String.format("%s - (%s/%s %s)", getString(R.string.invoice), String.valueOf(currentPage), String.valueOf(adapter.getCount()), getString(R.string.page));
        setToolbarConfig(paging, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }

        //TODO kaydedilen pdf'leri sil.
    }

    @OnClick(R.id.share)
    public void printClick() {

        //launchOpenPDFIntent();

    }

    private void launchOpenPDFIntent() {
        File file = new File(destinationPath);
        Uri uri = FileProvider.getUriForFile(mContext, "in.co.vyapari.provider", file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, getString(R.string.send_email)));
    }
}