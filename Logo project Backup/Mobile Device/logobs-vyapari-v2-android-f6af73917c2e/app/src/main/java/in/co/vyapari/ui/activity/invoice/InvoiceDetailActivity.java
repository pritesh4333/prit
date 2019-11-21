package in.co.vyapari.ui.activity.invoice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.google.gson.Gson;
import com.robinhood.ticker.TickerView;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.constant.MobileConstants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.InvoiceService;
import in.co.vyapari.model.Address;
import in.co.vyapari.model.CalendarModel;
import in.co.vyapari.model.Invoice;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.FirmDTO;
import in.co.vyapari.model.response.dto.InvoiceDTO;
import in.co.vyapari.ui.adapter.invoice.InvoiceProductAdapter;
import in.co.vyapari.ui.base.SohoActivity;

import in.co.vyapari.ui.generic.pdf.DownloadFileUrlConnectionImpl;
import in.co.vyapari.ui.listener.ClickListener;
import in.co.vyapari.ui.listener.RecyclerViewClickListener;
import in.co.vyapari.util.CurrencyUtil;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.DateUtil;
import in.co.vyapari.util.Utils;
import in.co.vyapari.ui.generic.BottomSheetLayout;

public class InvoiceDetailActivity extends SohoActivity {

    @BindView(R.id.invoice_detail_day_month)
    TextView invoiceDayMonthTV;
    @BindView(R.id.invoice_detail_year)
    TextView invoiceYearTV;
    @BindView(R.id.invoice_detail_payment_day_month)
    TextView invoicePaymentDayMonthTV;
    @BindView(R.id.invoice_detail_payment_year)
    TextView invoicePaymentYearTV;
    @BindView(R.id.invoice_detail_document_number_ll)
    LinearLayout documentNumberLL;
    @BindView(R.id.invoice_detail_document_number)
    EditText documentNumberET;
    @BindView(R.id.invoice_detail_showDatePickerDialogDocumentDate)
    LinearLayout documentDateLL;
    @BindView(R.id.invoice_detail_document_day_month)
    TextView documentDateDayMonthTV;
    @BindView(R.id.invoice_detail_document_year)
    TextView documentDateYearTV;
    @BindView(R.id.invoice_detail_delivery_day_month)
    TextView invoiceDeliveryDayMonthTV;
    @BindView(R.id.invoice_detail_delivery_year)
    TextView invoiceDeliveryYearTV;
    @BindView(R.id.invoice_detail_delivery_hour_minute)
    TextView invoiceDeliveryHourMinuteTV;
    @BindView(R.id.invoice_detail_firm_selected)
    RelativeLayout firmSelectedRL;
    @BindView(R.id.invoice_detail_icon_person)
    ImageView iconPersonIW;
    @BindView(R.id.invoice_detail_firm_name)
    TextView firmNameTV;
    @BindView(R.id.invoice_detail_firm_bottom_info)
    LinearLayout addressAndVatLL;
    @BindView(R.id.invoice_detail_firm_address)
    TextView addressAreaTV;
    @BindView(R.id.invoice_detail_firm_gstin_ll)
    LinearLayout GSTINInfoLL;
    @BindView(R.id.invoice_detail_firm_gstin)
    TextView GSTINInfoTV;
    @BindView(R.id.invoice_detail_firm_pan_ll)
    LinearLayout PANInfoLL;
    @BindView(R.id.invoice_detail_firm_pan)
    TextView PANInfoTV;
    @BindView(R.id.invoice_detail_delivery_address_ll)
    LinearLayout deliveryAddressLL;
    @BindView(R.id.invoice_detail_diff_delivery_address_cb)
    CheckBox diffDeliveryAddressCB;
    @BindView(R.id.invoice_detail_delivery_address_bottom_ll)
    LinearLayout deliveryAddressBottomLL;
    @BindView(R.id.invoice_detail_delivery_address_tv)
    TextView deliveryAddressTV;
    @BindView(R.id.invoice_detail_invoice_number)
    EditText invoiceNumberET;
    @BindView(R.id.invoice_detail_warehouse_ll)
    LinearLayout warehouseLL;
    @BindView(R.id.invoice_detail_warehouse_tv)
    TextView warehouseTV;
    @BindView(R.id.invoice_detail_product_recyclerView)
    RecyclerView productsRW;
    @BindView(R.id.invoice_detail_currency_symbol)
    TextView currencySymbolTV;
    @BindView(R.id.invoice_detail_middle_area)
    LinearLayout middleAreaLL;
    @BindView(R.id.invoice_detail_totalwotax)
    TextView totalWoTax;
    @BindView(R.id.invoice_detail_general_discount)
    TextView generalDiscountTV;
    @BindView(R.id.invoice_detail_total_discount)
    TextView totalDiscountTV;
    @BindView(R.id.invoice_detail_cess)
    TextView cessTV;
    @BindView(R.id.invoice_detail_cgst_title)
    TextView cgstTitleTV;
    @BindView(R.id.invoice_detail_cgst)
    TextView cgstTV;
    @BindView(R.id.invoice_detail_sgst_title)
    TextView sgstTitleTV;
    @BindView(R.id.invoice_detail_sgst)
    TextView sgstTV;
    @BindView(R.id.invoice_detail_igst_title)
    TextView igstTitleTV;
    @BindView(R.id.invoice_detail_igst)
    TextView igstTV;
    @BindView(R.id.invoice_detail_total)
    TickerView totalTV;
    @BindView(R.id.bottom_sheet_layout)
    BottomSheetLayout bottomSheetLayout;
    @BindView(R.id.icon_up)
    ImageView iconUp;

    private Context mContext;
    private InvoiceDTO invoice;
    private InvoiceProductAdapter invoiceProductAdapter;

    private boolean isExpendedBSL;
    private boolean isApproved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);
        setToolbarConfig(getString(R.string.sales_invoice), false);
        ButterKnife.bind(this);
        mContext = this;

        isApproved = DataUtil.getBundle(Boolean.class) != null ? DataUtil.getBundleAndRemove(Boolean.class) : false;
        invoice = DataUtil.getBundleAndRemove(InvoiceDTO.class);

        if (invoice.getInvoiceType() == Constants.SALES_INVOICE) {
            setToolbarConfig(getString(R.string.sales_invoice), false);
            documentNumberLL.setVisibility(View.GONE);
            documentDateLL.setVisibility(View.GONE);
        } else {
            setToolbarConfig(getString(R.string.purchase_invoce), false);
            documentNumberLL.setVisibility(View.VISIBLE);
            documentDateLL.setVisibility(View.VISIBLE);
        }

        setInvoice();
        setDateArea();
        setInvoiceLineArea();
        bottomSheetLayout.setOnProgressListener(new BottomSheetLayout.OnProgressListener() {
            @Override
            public void onProgress(float progress) {
                if (progress == 1) {
                    isExpendedBSL = true;
                } else if (progress == 0) {
                    isExpendedBSL = false;
                }

                if (isExpendedBSL) {
                    iconUp.setRotation(180 * progress);
                } else {
                    iconUp.setRotation(-180 * progress);
                }
            }
        });

        firstInvoiceDetail();

        //totalTV.setPaintFlags(totalTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void setFirmArea() {
        firmSelectedRL.setVisibility(View.VISIBLE);
        firmNameTV.setText(invoice.getFirm().getName());
        addressAndVatLL.setVisibility(View.VISIBLE);

        if (invoice.getInvoiceType() == Constants.SALES_INVOICE && invoice.isDeliveryAddressDifferent()) {
            deliveryAddressLL.setVisibility(View.VISIBLE);
            diffDeliveryAddressCB.setChecked(true);
        } else {
            deliveryAddressLL.setVisibility(View.GONE);
        }

        FirmDTO firmDTO = invoice.getFirm();
        String addressArea = Utils.createFirmAddressDetail(mContext, firmDTO);

        addressAreaTV.setText(addressArea);

        String gstin = firmDTO.getGSTIN();
        if (gstin != null && !gstin.isEmpty()) {
            GSTINInfoLL.setVisibility(View.VISIBLE);
            GSTINInfoTV.setText(gstin);
        } else {
            GSTINInfoLL.setVisibility(View.GONE);
        }

        String pan = firmDTO.getPAN();
        if (pan != null && !pan.isEmpty()) {
            PANInfoLL.setVisibility(View.VISIBLE);
            PANInfoTV.setText(pan);
        } else {
            PANInfoLL.setVisibility(View.GONE);
        }
    }

    private void setInvoiceLineArea() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        productsRW.setItemAnimator(new DefaultItemAnimator());
        productsRW.setHasFixedSize(true);
        productsRW.setNestedScrollingEnabled(false);
        productsRW.setLayoutManager(mLayoutManager);
        productsRW.addOnItemTouchListener(new RecyclerViewClickListener(mContext, productsRW, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, final int position) {

            }
        }));

        invoiceProductAdapter = new InvoiceProductAdapter(invoice.getInvoiceLines());
        productsRW.setAdapter(invoiceProductAdapter);

        currencySymbolTV.setText(MobileConstants.defaultCurrency.getValue());
        totalWoTax.setText(CurrencyUtil.doubleToCurrency(invoice.getAmountWithoutGST(), invoice.getCurrency()));
        generalDiscountTV.setText(CurrencyUtil.doubleToCurrency(invoice.getGeneralDiscountAmount(), invoice.getCurrency()));
        totalDiscountTV.setText(CurrencyUtil.doubleToCurrency(invoice.getDiscountAmount(), invoice.getCurrency()));
        cessTV.setText(CurrencyUtil.doubleToCurrency(invoice.getCESSAmount(), invoice.getCurrency()));
        cgstTV.setText(CurrencyUtil.doubleToCurrency(invoice.getSGSTAmount(), invoice.getCurrency()));
        sgstTV.setText(CurrencyUtil.doubleToCurrency(invoice.getSGSTAmount(), invoice.getCurrency()));
        igstTV.setText(CurrencyUtil.doubleToCurrency(invoice.getIGSTAmount(), invoice.getCurrency()));
        totalTV.setText(CurrencyUtil.doubleToCurrency(invoice.getTotalAmount(), invoice.getCurrency()).toString(), true);

        if (invoice.getInvoiceLines().get(0).getCGSTRate() > 0) {
            String cgstTitleText;
            if (Utils.isInteger(invoice.getInvoiceLines().get(0).getCGSTRate())) {
                cgstTitleText = String.format(getString(R.string.CGST3), String.valueOf((int) invoice.getInvoiceLines().get(0).getCGSTRate() + "%"));
            } else {
                cgstTitleText = String.format(getString(R.string.CGST3), String.valueOf(invoice.getInvoiceLines().get(0).getCGSTRate() + "%"));
            }
            cgstTitleTV.setText(cgstTitleText);
        }

        if (invoice.getInvoiceLines().get(0).getIGSTRate() > 0) {
            String igstTitleText;
            if (Utils.isInteger(invoice.getInvoiceLines().get(0).getIGSTRate())) {
                igstTitleText = String.format(getString(R.string.IGST3), String.valueOf((int) invoice.getInvoiceLines().get(0).getIGSTRate() + "%"));
            } else {
                igstTitleText = String.format(getString(R.string.IGST3), String.valueOf(invoice.getInvoiceLines().get(0).getIGSTRate() + "%"));
            }
            igstTitleTV.setText(igstTitleText);
        }

        if (invoice.getInvoiceLines().get(0).getSGSTRate() > 0) {
            String sgstTitleText;
            if (Utils.isInteger(invoice.getInvoiceLines().get(0).getSGSTRate())) {
                sgstTitleText = String.format(getString(R.string.SGST3), String.valueOf((int) invoice.getInvoiceLines().get(0).getSGSTRate() + "%"));
            } else {
                sgstTitleText = String.format(getString(R.string.SGST3), String.valueOf(invoice.getInvoiceLines().get(0).getSGSTRate() + "%"));
            }
            sgstTitleTV.setText(sgstTitleText);
        }

        /*
        if (invoice.getDiscountType() != null || invoice.getDiscountType().equals(Constants.PERCENT)) {
            discountPercentTV.setText(String.valueOf("%" + invoice.getDiscount()));
        } else {
            discountPercentTV.setText("-");
        }*/
    }

    private void setAddressSelectArea() {
        if (invoice.isDeliveryAddressDifferent()) {
            Address address = invoice.getDeliveryAddress();
            if (address != null && address.getDescription() != null) {
                deliveryAddressBottomLL.setVisibility(View.VISIBLE);
                deliveryAddressTV.setText(address.getDescription());
            } else {
                deliveryAddressBottomLL.setVisibility(View.GONE);
            }
        } else {
            deliveryAddressBottomLL.setVisibility(View.GONE);
        }
    }

    private void setInvoice() {
        invoiceNumberET.setText(invoice.getInvoiceNumber());
        documentNumberET.setText(invoice.getDocumentNumber());
//        warehouseTV.setText(invoice.getWarehouse().getValue());
        //kdvIncludeSC.setChecked(invoice.isGSTIncluded());
        //currencyTypeTV.setText(invoice.getCurrency().getValue());
        //currencyValueET.setText(String.valueOf(invoice.getExchangeRate()));

        FirmDTO firmDTO = invoice.getFirm();
        if (firmDTO != null) {
            invoice.setFirm(firmDTO);
            setFirmArea();
            setAddressSelectArea();
        }

        if (invoice.getDeliveryAddress() == null) {
            diffDeliveryAddressCB.setChecked(false);
        }
    }

    public void setDateArea() {
        setInvoiceDate();
        setDocumentDate();
        setInvoicePaymentDate();
        setInvoiceDeliveryDate();
    }

    private void setInvoiceDate() {
        CalendarModel cm = DateUtil.dateToCM(invoice.getInvoiceDate());
        String invoiceDayMonth = cm.getDay() + " " + cm.getMonthName();
        invoiceDayMonthTV.setText(invoiceDayMonth);
        invoiceYearTV.setText(cm.getYear());
    }

    private void setDocumentDate() {
        CalendarModel cm = DateUtil.dateToCM(invoice.getDocumentDate());
        String documentDayMonth = cm.getDay() + " " + cm.getMonthName();
        documentDateDayMonthTV.setText(documentDayMonth);
        documentDateYearTV.setText(cm.getYear());
    }

    private void setInvoicePaymentDate() {
        if (invoice.getPaymentDate() != null) {
            CalendarModel cm = DateUtil.dateToCM(invoice.getPaymentDate());
            String invoicePaymentDayMonth = cm.getDay() + " " + cm.getMonthName();
            invoicePaymentDayMonthTV.setText(invoicePaymentDayMonth);
            invoicePaymentYearTV.setText(cm.getYear());
        }
    }

    private void setInvoiceDeliveryDate() {
        if (invoice.getDeliveryDate() != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(invoice.getDeliveryDate());

            CalendarModel cm = DateUtil.dateToCM(invoice.getDeliveryDate());
            String invoicePaymentDayMonth = cm.getDay() + " " + cm.getMonthName();
            invoiceDeliveryDayMonthTV.setText(invoicePaymentDayMonth);
            invoiceDeliveryYearTV.setText(cm.getYear());

            int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            String _hourOfDay = hourOfDay > 9 ? String.valueOf(hourOfDay) : String.valueOf("0" + hourOfDay);
            String _minute = minute > 9 ? String.valueOf(minute) : String.valueOf("0" + minute);

            invoiceDeliveryHourMinuteTV.setText(String.valueOf(_hourOfDay + ":" + _minute));
        }
    }

    @OnClick(R.id.icon_up)
    public void iconUpClick() {
        bottomSheetLayout.toggle();
    }

    @OnClick(R.id.bottom_sheet_layout)
    public void bottomSheetLayoutClick() {
        bottomSheetLayout.toggle();
    }

    @OnClick(R.id.invoice_detail_delivery_address_ll)
    public void deliveryAddressClick() {
        if (invoice.isDeliveryAddressDifferent()) {
            Address addressObject = invoice.getDeliveryAddress();
            String title = addressObject.getDescription();
            String address = Utils.createAddressDetail(mContext, addressObject);

            Utils.generateDialog(mContext, title, address, getString(R.string.close), null);
        }
    }

    @OnClick(R.id.edit)
    public void editInvoice() {
        if (!isApproved) {
            openEditInvoice();
        } else {
            String message = getString(R.string.edit_invoice_change_status_msg);
            Utils.generateDialog(mContext, getString(R.string.edit_invoice), message,
                    getString(R.string.remove_approve), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            changeStatusCall(true);
                        }
                    },
                    getString(R.string.cancel), null);
        }
    }

    private void openEditInvoice() {
        Invoice editInvoice = new Invoice(invoice, null);
        DataUtil.post(editInvoice);
        startActivity(new Intent(mContext, CreateInvoiceActivity.class));
        finish();
    }

    private void changeStatusCall(final boolean goToEdit) {
        InvoiceService.changeStatus(invoice.getId(), invoice.getInvoiceType(), new ServiceCall<BaseModel<Boolean>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<Boolean> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    MobileConstants.invoiceId = "changeStatus";
                    isApproved = !isApproved;
                    if (goToEdit) {
                        openEditInvoice();
                    }
                    Toasty.success(mContext, response.getMessage()).show();
                } else {
                    Toasty.error(mContext, response.getMessage()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    @OnClick(R.id.options)
    void options() {
        new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog_Custom)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setMenu(isApproved ? R.menu.menu_bottom_invoice2 : R.menu.menu_bottom_invoice)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(final MenuItem item) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                switch (item.getItemId()) {
                                    case R.id.menu_invoice_send_pdf:
                                        printAskPerm();
                                        break;
                                    case R.id.menu_invoice_print:
                                        printAskPerm();
                                        break;
                                    case R.id.menu_invoice_approve:
                                        changeStatusCall(false);
                                        break;
                                }
                            }
                        }, 500);
                    }
                }).createDialog().show();
    }

    private void printAskPerm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, Constants.PERMISSION_READ);
        } else {
            printInvoice();
        }
    }

    private void printInvoice() {

//        Intent intent = new Intent(mContext,InvoicePrint.class);
//        intent.putExtra("Type","print");
//        intent.putExtra("Invoice",  new Gson().toJson(invoice));
//        startActivity(intent);
        Utils.showLoading(mContext);
        InvoiceService.getPrintUrl(invoice.getId(), new ServiceCall<BaseModel<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<String> response) {
                if (!response.isError()) {
                    String url = response.getData();
                    if (url != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Utils.hideLoading();
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(mContext, InvoicePdfShowActivity.class);
                            bundle.putString(Constants.URL, url);
                            bundle.putInt(Constants.INVOICE_TYPE, invoice.getInvoiceType());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            downloadPdf(url);
                        }
                    } else {
                        Toasty.error(mContext, response.getErrorDescription()).show();
                    }
                } else {
                    Utils.hideLoading();
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    private void downloadPdf(String url) {
        String guid = UUID.randomUUID().toString();
        DownloadFile downloadFile = new DownloadFileUrlConnectionImpl(mContext, new Handler(), new DownloadFile.Listener() {
            @Override
            public void onSuccess(String url, String destinationPath) {
                Utils.hideLoading();
                launchOpenPDFIntent(destinationPath);
            }

            @Override
            public void onFailure(Exception e) {
                Utils.hideLoading();

            }

            @Override
            public void onProgressUpdate(int progress, int total) {

            }
        });
        downloadFile.download(url, new File(mContext.getExternalFilesDir("pdf"), "invoice-" + guid + ".pdf").getAbsolutePath());
    }

    private void launchOpenPDFIntent(String destinationPath) {
        File pdfFile = new File(destinationPath);
        Uri uri = FileProvider.getUriForFile(mContext, "in.co.vyapari.provider", pdfFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
        finish();
    }

    private void firstInvoiceDetail() {
        if (Utils.isFirstOpen(mContext, Constants.INVOICE_DETAIL)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bottomSheetLayout.expand();
                }
            }, 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bottomSheetLayout.collapse();
                }
            }, 3000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_READ: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    printInvoice();
                } else {
                    Toasty.error(mContext, getString(R.string.permission_warn)).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetLayout.isExpended()) {
            bottomSheetLayout.collapse();
        } else {
            super.onBackPressed();
        }
    }
}