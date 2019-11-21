package in.co.vyapari.ui.activity.invoice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mswipetech.wisepad.sdk.Print;
import com.socsi.smartposapi.printer.Align;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.InvoiceService;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.InvoiceDTO;
import in.co.vyapari.model.response.dto.PlaceofSupplyDTO;
import in.co.vyapari.ui.activity.app.MainActivity;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.util.Utils;


public class InvoicePrint extends SohoActivity {
    @BindView(R.id.webview)
    WebView webView;
    String printformate;
    String additem;
    String filepath;
    private Context mContext;
    @BindView(R.id.share)
    ImageView share;

    InvoiceDTO invoiceobj;
    String plaseofsupply="",arapgstin="";
    StringBuilder replace = new StringBuilder();
    String Name = "", address = "", contact = "", email = "", gstin = "", statkey = "", state = "", invoicenumber = "", invoicedate = "",arapname="", gst = "", disccount = "",DiscountAmount="", cgst = "", cgstrate = "", sgst = "", sgstrate = "", igst = "", igstrate = "", contactandemail = "", totalcgst = "", totalsgst = "", totaligst = "",
            plaseofsupplydata="",arapgstindata="",totalamount = "", productname = "", quantity = "", saleprice = "", total = "", hsncode = "", discoundamount = "";
    Double grossTotal=0.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_print);
        mContext = this;
        ButterKnife.bind(this);
        setToolbarConfig(getString(R.string.invoice), false);
        title(0);
        String invoice = getIntent().getExtras().getString("Invoice");
        invoiceobj = new Gson().fromJson(invoice, InvoiceDTO.class);
        Gson gson = new Gson();
        String jsn = gson.toJson(invoiceobj);
        Log.e("JSON OBJECT-", jsn);
        webView.setVisibility(View.VISIBLE);
        //share.setVisibility(View.GONE);
        getPlaseofSupply();

    }

    private void getPlaseofSupply() {

        Utils.showLoading(mContext);

        InvoiceService.getPlaseofSupply(invoiceobj.getInvoiceNumber(), new ServiceCall<BaseModel<PlaceofSupplyDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<PlaceofSupplyDTO> response) {


                if (!response.isError()) {
                    if (response.getData() != null) {
                        PlaceofSupplyDTO PlaceofSupplyDTO = response.getData();
                        plaseofsupply=PlaceofSupplyDTO.getPlaceOfService();
                        arapgstin=PlaceofSupplyDTO.getARPGSTID();
                        Name=PlaceofSupplyDTO.getOrgName();
                        address=PlaceofSupplyDTO.getAddress();
                        email=PlaceofSupplyDTO.getEmail();
                        contact=PlaceofSupplyDTO.getContactNo();
                        state=PlaceofSupplyDTO.getOrgStateDesc();
                        statkey=PlaceofSupplyDTO.getOrgStateCode();
                        arapname=PlaceofSupplyDTO.getARPDescription();
                        gstin=PlaceofSupplyDTO.getOrgGST();
                        getPrintData();

                    }
                } else {
                    Utils.hideLoading();
                    Toasty.error(mContext, response.getErrorDescription()).show();

                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    private void getPrintData() {
        for (int i = 0; i < invoiceobj.getInvoiceLines().size(); i++) {

            //header data
            if(Name.equalsIgnoreCase("")||Name.equalsIgnoreCase("null")){
                Name="";
            }
            if (address.equalsIgnoreCase("null") || address.equalsIgnoreCase("")) {
                address = "";
            }
            if (contact.equalsIgnoreCase("null") || contact.equalsIgnoreCase("")) {
                contact = "";
            }
            if (email.equalsIgnoreCase("null") || email.equalsIgnoreCase("")) {
                email = "";
            }
            if (gstin.equalsIgnoreCase("null") || gstin.equalsIgnoreCase("")) {
                gstin = "";
            }
            if (statkey.equalsIgnoreCase("null") || statkey.equalsIgnoreCase("")) {
                statkey = "";
            }
            if (state.equalsIgnoreCase("null") || state.equalsIgnoreCase("")) {
                state = "";
            }
            if (arapname.equalsIgnoreCase("null") || arapname.equalsIgnoreCase("")) {
                arapname = "";
            }
            if (invoiceobj.getInvoiceNumber().equalsIgnoreCase("null") || invoiceobj.getInvoiceNumber().equalsIgnoreCase("")) {
                invoicenumber = "";
            } else {
                invoicenumber = invoiceobj.getInvoiceNumber();
            }

            if (String.valueOf(invoiceobj.getInvoiceDate()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceDate()).equalsIgnoreCase("")) {
                invoicedate = "";
            } else {
                String DATE_FORMAT = "dd/MM/yyyy";
                // Create object of SimpleDateFormat and pass the desired date format.
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

                invoicedate = String.valueOf(sdf.format(invoiceobj.getInvoiceDate()));
            }
            if (String.valueOf(invoiceobj.getAmountWithoutGST()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getAmountWithoutGST()).equalsIgnoreCase("")) {
                gst = "";
            } else {
                gst = String.valueOf(invoiceobj.getAmountWithoutGST());
                gst = format2DecAmountDouble(invoiceobj.getAmountWithoutGST());
            }
            if (String.valueOf(invoiceobj.getInvoiceLines().get(i).getDiscountAmount()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceLines().get(i).getDiscountAmount()).equalsIgnoreCase("")) {
                disccount = "";
            } else {
                disccount = String.valueOf(invoiceobj.getInvoiceLines().get(i).getDiscountAmount());
                disccount=format2DecAmountDouble(invoiceobj.getInvoiceLines().get(i).getDiscountAmount());
            }

            if (String.valueOf(invoiceobj.getDiscountAmount()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getDiscountAmount()).equalsIgnoreCase("")) {
                discoundamount = "";
            } else {
                discoundamount = String.valueOf(invoiceobj.getDiscountAmount());
                discoundamount=format2DecAmountDouble(invoiceobj.getDiscountAmount());
            }


            if (String.valueOf(invoiceobj.getTotalAmount()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getTotalAmount()).equalsIgnoreCase("")) {
                totalamount = "";
            } else {
                totalamount = String.valueOf(invoiceobj.getTotalAmount());
                totalamount=format2DecAmountDouble(invoiceobj.getTotalAmount());
            }


            //item product
            if (invoiceobj.getInvoiceLines().size() == 0) {
                productname = "";
            } else {
                if (String.valueOf(invoiceobj.getInvoiceLines().get(i).getProduct().getName()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceLines().get(i).getProduct().getName()).equalsIgnoreCase("")) {
                    productname = "";
                } else {
                    productname = String.valueOf(invoiceobj.getInvoiceLines().get(i).getProduct().getName());

                }
            }
            if (invoiceobj.getInvoiceLines().size() == 0) {
                total = "";
            } else {
                if (String.valueOf(invoiceobj.getInvoiceLines().get(i).getTotal()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceLines().get(i).getTotal()).equalsIgnoreCase("")) {
                    total = "";
                } else {
                    total = String.valueOf(invoiceobj.getInvoiceLines().get(i).getTotal());
                    total=format2DecAmountDouble(Double.parseDouble(total));
                }
            }
            if (invoiceobj.getInvoiceLines().size() == 0) {
                quantity = "";
            } else {
                if (String.valueOf(invoiceobj.getInvoiceLines().get(i).getQuantity()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceLines().get(i).getQuantity()).equalsIgnoreCase("")) {
                    quantity = "";
                } else {
                    quantity = String.valueOf(invoiceobj.getInvoiceLines().get(i).getQuantity());

                }
            }

            if (invoiceobj.getInvoiceLines().size() == 0) {
                saleprice = "";
            } else {
                if (String.valueOf(invoiceobj.getInvoiceLines().get(i).getUnitPrice()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceLines().get(i).getUnitPrice()).equalsIgnoreCase("")) {
                    saleprice = "";
                } else {
                    saleprice = String.valueOf(invoiceobj.getInvoiceLines().get(i).getUnitPrice());
                    saleprice = format2DecAmountDouble(invoiceobj.getInvoiceLines().get(i).getUnitPrice());
                }
            }


            if (invoiceobj.getInvoiceLines().size() == 0) {
                hsncode = "";
            } else {
                if (invoiceobj.getInvoiceLines().get(i).getProduct().getSACHSNCode().getKey().equalsIgnoreCase("null") || invoiceobj.getInvoiceLines().get(i).getProduct().getSACHSNCode().getKey().equalsIgnoreCase("")) {
                    hsncode = "";
                } else {
                    hsncode = invoiceobj.getInvoiceLines().get(i).getProduct().getSACHSNCode().getKey();
                }
            }

            if (invoiceobj.getInvoiceLines().size() == 0) {
                discoundamount = "";
            } else {
                if (String.valueOf(invoiceobj.getDiscountAmount()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getDiscountAmount()).equalsIgnoreCase("")) {
                    discoundamount = "";
                } else {
                    discoundamount = String.valueOf(invoiceobj.getDiscountAmount());
                    discoundamount = format2DecAmountDouble(invoiceobj.getDiscountAmount());
                }
            }


            if (String.valueOf(invoiceobj.getInvoiceLines().get(i).getCGSTAmount()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceLines().get(i).getCGSTAmount()).equalsIgnoreCase("")) {
                cgst = "";
            } else {
                cgst = String.valueOf(invoiceobj.getInvoiceLines().get(i).getCGSTAmount());
                cgst = format2DecAmountDouble(invoiceobj.getInvoiceLines().get(i).getCGSTAmount());
            }

            if (String.valueOf(invoiceobj.getInvoiceLines().get(i).getCGSTRate()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceLines().get(i).getCGSTRate()).equalsIgnoreCase("")) {
                cgstrate = "";
            } else {
                cgstrate = String.valueOf(invoiceobj.getInvoiceLines().get(i).getCGSTRate());
                cgstrate = "<div> <div style='float:left'>CGST " + cgstrate + "% </div> <div style='padding-left:50%'> " + cgst + "</div> </div> ";
            }
            if (cgst.equalsIgnoreCase("0") || cgst.equalsIgnoreCase("0.00")) {
                cgstrate = "";
            }


            if (String.valueOf(invoiceobj.getInvoiceLines().get(i).getSGSTAmount()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceLines().get(i).getSGSTAmount()).equalsIgnoreCase("")) {
                sgst = "";
            } else {
                sgst = String.valueOf(invoiceobj.getInvoiceLines().get(i).getSGSTAmount());
                sgst = format2DecAmountDouble(invoiceobj.getInvoiceLines().get(i).getSGSTAmount());
            }
            if (String.valueOf(invoiceobj.getInvoiceLines().get(i).getSGSTRate()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceLines().get(i).getSGSTRate()).equalsIgnoreCase("")) {
                sgstrate = "";
            } else {
                sgstrate = String.valueOf(invoiceobj.getInvoiceLines().get(i).getSGSTRate());
                sgstrate = "<div> <div style='float:left'>SGST " + sgstrate + "% </div> <div style='padding-left:50%'> " + sgst + "</div>";
            }
            if (sgst.equalsIgnoreCase("0") || sgst.equalsIgnoreCase("0.00")) {
                sgstrate = "";
            }


            if (String.valueOf(invoiceobj.getInvoiceLines().get(i).getIGSTAmount()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceLines().get(i).getIGSTAmount()).equalsIgnoreCase("")) {
                igst = "";
            } else {
                igst = String.valueOf(invoiceobj.getInvoiceLines().get(i).getIGSTAmount());
                igst=format2DecAmountDouble(Double.parseDouble(igst));
            }
            if (String.valueOf(invoiceobj.getInvoiceLines().get(i).getIGSTRate()).equalsIgnoreCase("null") || String.valueOf(invoiceobj.getInvoiceLines().get(i).getIGSTRate()).equalsIgnoreCase("")) {
                igstrate = "";
            } else {
                igstrate = String.valueOf(invoiceobj.getInvoiceLines().get(i).getIGSTRate());
                igstrate = "<div> <div style='float:left'>IGST " + igstrate + "% </div> <div style='padding-left:50%'> " + igst + "</div>";
            }
            if (igst.equalsIgnoreCase("0") || igst.equalsIgnoreCase("0.00")) {
                igstrate = "";
            }


            Double totalgstdiduction = Double.parseDouble(total) - Double.parseDouble(disccount)+Double.parseDouble(cgst) + Double.parseDouble(sgst) + Double.parseDouble(igst);
            grossTotal+= Double.parseDouble(total);
            additem = "<div style='width:100%;'> <div style='float:left'>" + productname + "</div> <div style='float:right'>" + format2DecAmountDouble(totalgstdiduction) + "</div><br><br> </div><div> <div style='float:left'>" + quantity + " Qty @" + saleprice + "Rate </div>  <div style='text-align: center'> " + total + "</div> </div>  <div> <div style='float:left'>HSN/SAC " + hsncode + "</div> </div>  <br> <div> <div style='float:left'>Discount:</div> <div style='padding-left:50%'> " + disccount + "</div> </div>  " + cgstrate + "" + sgstrate + "" + igstrate + "<br><br>";
            replace.append(additem);
        }

        if (address.equalsIgnoreCase("")||address.equalsIgnoreCase("null")){
            address="";
        }else{
            address="<div style='text-align: left; '>" + address + "&nbsp;</div><div style='text-align: left; '></div>";
        }



        if (contact.equalsIgnoreCase("")||contact.equalsIgnoreCase("null") && email.equalsIgnoreCase("null")||email.equalsIgnoreCase("")) {
            contactandemail = "";
        } else {
            if (!email.equalsIgnoreCase("")||email.equalsIgnoreCase(null)){
                email=","+email;
            }
            contactandemail = "<div style='text-align: left; '> " + contact + "" + email + "&nbsp;</div>";
        }

        if (gstin.equalsIgnoreCase("")){
            gstin="";
        }else{
            gstin="<div style='text-align: left;'>GSTIN: " + gstin + "&nbsp;</div>";
        }

        if (plaseofsupply.equalsIgnoreCase("")){
            plaseofsupplydata="";
        }else{
            plaseofsupplydata="<div style='float: right;'>POS:"+plaseofsupply+"</div>";
        }



        if(arapgstin.equalsIgnoreCase("")||arapgstin.equalsIgnoreCase("null")){
            arapgstindata="";
        }else{
            arapgstindata="<div style='text-align: left;'> "+arapgstin+"</div>";
        }

        if (discoundamount.equalsIgnoreCase("")||discoundamount.equalsIgnoreCase("0.0")||discoundamount.equalsIgnoreCase("0")){
            discoundamount="";
        }else{
            discoundamount="<div> <div style='float:left'>Total Discount (-)</div> <div style='float:right'> " + discoundamount + "</div> </div> <br> ";
        }


        if (String.valueOf(invoiceobj.getCGSTAmount()).equalsIgnoreCase("0") || String.valueOf(invoiceobj.getCGSTAmount()).equalsIgnoreCase("0.0") || String.valueOf(invoiceobj.getCGSTAmount()).equalsIgnoreCase("")) {
            totalcgst = "";
        } else {
            totalcgst = "<div> <div style='float:left'>Total CGST</div> <div style='float:right'> " + format2DecAmountDouble(invoiceobj.getCGSTAmount()) + "</div> </div> <br> ";
        }

        if (String.valueOf(invoiceobj.getSGSTAmount()).equalsIgnoreCase("0") || String.valueOf(invoiceobj.getSGSTAmount()).equalsIgnoreCase("0.0") || String.valueOf(invoiceobj.getSGSTAmount()).equalsIgnoreCase("")) {
            totalsgst = "";
        } else {
            totalsgst = "<div> <div style='float:left'>Total SGST</div> <div style='float:right'> " + format2DecAmountDouble(invoiceobj.getSGSTAmount()) + "</div> </div> <br>";
        }
        if (String.valueOf(invoiceobj.getIGSTAmount()).equalsIgnoreCase("0") || String.valueOf(invoiceobj.getIGSTAmount()).equalsIgnoreCase("0.0") || String.valueOf(invoiceobj.getIGSTAmount()).equalsIgnoreCase("")) {
            totaligst = "";
        } else {
            totaligst = "<div> <div style='float:left'>Total IGST</div> <div style='float:right'> " +format2DecAmountDouble(invoiceobj.getIGSTAmount()) + "</div> </div> <br>";
        }




        printformate = "<!DOCTYPE html> <html><head><style>hr{border:1px solid black}</style></head><body style='font-size:16px;'> <div style='text-align: center; '><br><b><div style='text-align: center; '>TAX INVOICE</div></b><hr></div><div style='text-align: center; '>" + Name + "&nbsp;</div><div style='text-align: center; '></div><div style='text-align: center; '><br></div>"+address+"" + contactandemail + "<div style='text-align: left; '></div></div><div style='text-align: left; '>" + statkey + ", " + state + "&nbsp;</div>"+gstin+"<div><div style='text-align: center;'><hr></div><div style='text-align: left;'>Invoice Number:" + invoicenumber + "</div><div style='text-align: left;'></div><div><div style='float: left;'>Date:"+invoicedate+"</div>"+plaseofsupplydata+"</div><br><hr></div><div style='text-align: left;'>"+arapname+"</div>"+arapgstindata+"<div></div><div><hr></div><div></div>" + replace + " <hr> <div> <div style='float:left'>Total Gross Amount</div> <div style='float:right'> " + format2DecAmountDouble(grossTotal) + "</div> </div> <br> "+discoundamount+"" + totalcgst + "" + totalsgst + "" + totaligst + "  <hr> <div> <div style='float:left'><b>Invoice Total</b> </div> <div style='float:right'><b> &#8377;&nbsp;" + totalamount + "</b></div> </div></div><div>&nbsp;</div><div><br></div><div style='text-align: center;'>*This is a computer generated Invoice and requires no signature.</div><div style='text-align: center;'><br></div><div style='text-align: center;'><br></div><div style='text-align: center;'><br></div><div style='text-align: center;'><br></div> </body> </html>";


        Log.e("HTML FINAL", printformate);
        webView.loadData( printformate, "text/html",  null);


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, WebResourceRequest request) {

                webView.loadUrl(printformate);
                return true;
            }

            @Override
            public void onPageStarted(
                    WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                //SHOW LOADING IF IT ISNT ALREADY VISIBLE
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                ViewTreeObserver viewTreeObserver  = webView.getViewTreeObserver();

                viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        int height = webView.getMeasuredHeight();
                        if( height != 0 ){
                            Utils.hideLoading();
                            // getImageFromWebviewStore();
                            webView.getViewTreeObserver().removeOnPreDrawListener(this);
                        }
                        return false;
                    }
                });
            }
        });

    }

    private void title(int currentPage) {
        currentPage = currentPage + 1;
        String paging = String.format("%s - (%s/%s %s)", getString(R.string.invoice), String.valueOf(currentPage), 1, getString(R.string.page));
        setToolbarConfig(paging, false);
    }

    @OnClick(R.id.share)
    public void printClick() {




        getImageFromWebviewStore();
        //Print.StartPrintingImage(bm, Align.CENTER);
        onBackPressed();



    }

    private void Share() {
        File file = new File(String.valueOf(filepath));
        Uri uri = FileProvider.getUriForFile(mContext, "in.co.vyapari.provider", file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Image"));
    }

    private void getImageFromWebviewStore() {

        webView.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView.layout(0, 0, webView.getMeasuredWidth(),
                webView.getMeasuredHeight());
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        Bitmap bm;
        bm = Bitmap.createBitmap(webView.getMeasuredWidth(),
                webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas bigcanvas = new Canvas(bm);
        Paint paint = new Paint();
        int iHeight = bm.getHeight();
        bigcanvas.drawBitmap(bm, 0, iHeight, paint);
        webView.draw(bigcanvas);

        if (bm != null) {

            FileOutputStream outputStream = null;
            String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/share.jpeg";
            try {
                outputStream = new FileOutputStream(filepath); //here is set your file path where you want to save or also here you can set file object directly

                bm.compress(Bitmap.CompressFormat.PNG, 100, outputStream); // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Print.StartPrintingImage(bm, Align.CENTER);

            Utils.hideLoading();
            webView.setVisibility(View.INVISIBLE);
            Intent i = new Intent(mContext,MainActivity.class);
            startActivity(i);
            //onBackPressed();

        }
    }
    public static String format2DecAmountDouble(Double amount) {
        String formattedAmount = String.format("%.2f", amount);
        return formattedAmount;
    }
}
