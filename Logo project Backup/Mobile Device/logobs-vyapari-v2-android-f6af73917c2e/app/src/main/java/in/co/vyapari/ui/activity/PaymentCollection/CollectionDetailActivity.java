package in.co.vyapari.ui.activity.PaymentCollection;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.robinhood.ticker.TickerView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.constant.MobileConstants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.InvoiceService;
import in.co.vyapari.middleware.service.ProductService;
import in.co.vyapari.model.ArApCollection;
import in.co.vyapari.model.CalendarModel;
import in.co.vyapari.model.Firm;
import in.co.vyapari.model.GlCrossAccount;
import in.co.vyapari.model.Invoice;
import in.co.vyapari.model.Transaction;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.ArApCollectionDTO;
import in.co.vyapari.model.response.dto.CollectionTypeDTO;
import in.co.vyapari.model.response.dto.FirmDTO;
import in.co.vyapari.model.response.dto.InvoiceDTO;
import in.co.vyapari.model.response.summary.CollectionSum;
import in.co.vyapari.ui.activity.invoice.SelectFirmForInvoiceActivity;
import in.co.vyapari.ui.adapter.CollectionPaymentAdapter;
import in.co.vyapari.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.util.CurrencyUtil;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.DateUtil;
import in.co.vyapari.util.Utils;

public class CollectionDetailActivity extends SohoActivity  {
    private Context mContext;
    private InvoiceDTO invoicedto;
    private in.co.vyapari.model.response.dto.ArApCollectionDTO ArApCollectionDTO;
    private Invoice invoice;
    private boolean isApproved;
    String getcollectioncode;
    String invoiceDayMonth;
    CalendarModel cm;
    List<CollectionTypeDTO> arapNo;
    String Create;
    Date date;
    ArrayList<CollectionSum> arraylist ;
    private List<CollectionSum> invoiceSumList;
    @BindView(R.id.invoice_detail_firm_selected)
    LinearLayout firmSelectedRL;
    @BindView(R.id.create_invoice_select_firm_msg)
    TextView firmNameTV;
    @BindView(R.id.discription)
    TextView discription;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.payment_invoice_total)
    TickerView payment_invoice_total;
    @BindView(R.id.collection_type_spinner)
    Spinner collection_type_spinner;
    @BindView(R.id.slip_no)
    TextView slipno;
    @BindView(R.id.invoice_detail_day_month)
    TextView invoice_detail_day_month;
    @BindView(R.id.invoice_detail_year)
    TextView invoice_detail_year;
    @BindView(R.id.referenceNo)
    TextView referenceNo;
    String  Type;
    String id;
    @BindView(R.id.edit)
    ImageView collectionedit;
    @BindView(R.id.options)
    ImageView options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_details);

        ButterKnife.bind(this);
        mContext = this;
        invoice = new Invoice();
        Gson gson = new Gson();
        String jsn = gson.toJson(invoicedto);
        Log.e("JSON OBJECT-", jsn);
        options.setVisibility(View.INVISIBLE);

        Create = getIntent().getExtras().getString("Create");
        invoiceSumList= (List<CollectionSum>) getIntent().getExtras().getSerializable("collectionSum");
        Type=  getIntent().getExtras().getString("Type");
        id=getIntent().getExtras().getString("id");
        if (Type.equalsIgnoreCase("2")) {
            setToolbarConfig(getString(R.string.Collection), false);
        }else{
            setToolbarConfig(getString(R.string.payments), false);
        }
            ArApCollectionDTO = DataUtil.getBundleAndRemove(ArApCollectionDTO.class);
            referenceNo.setText(ArApCollectionDTO.getReceiptNo());
            discription.setText(ArApCollectionDTO.getDescription());

            for(int i =0;i<invoiceSumList.size();i++){
                if (invoiceSumList.get(i).getARPCode().equalsIgnoreCase(ArApCollectionDTO.getTransactions().get(0).getArpCode())){
                    firmNameTV.setText(invoiceSumList.get(i).getARPName());
                }
            }


        setFirmArea();
        setInvoiceDate();
        setshipmentNo();

        amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String amount = s.toString();
                if (!amount.equalsIgnoreCase("")) {
                    //payment_invoice_total.setText("₹ " + amount);
                    payment_invoice_total.setText("₹"+CurrencyUtil.doubleToCurrency(Double.parseDouble(amount), invoice.getCurrency()).toString(), true);
                } else {
                    payment_invoice_total.setText("");
                }
            }
        });
        collectionedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getARAPDetailCall(ArApCollectionDTO.getReference());
            }
        });
    }

    private void getARAPDetailCall(String id) {
        Log.e("id",id);

        Utils.showLoading(mContext);
        InvoiceService.getARAPDetail(id, new ServiceCall<BaseModel<ArApCollectionDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArApCollectionDTO> response) {
                Utils.hideLoading();

                if (!response.isError()) {
                    if (response.getData() != null) {

                        ArApCollectionDTO ArApCollectionDTO = response.getData();
                        DataUtil.post(ArApCollectionDTO);

                        if (Type.equalsIgnoreCase("2")) {

                            Intent i = new Intent(mContext, CreateCollectionActivity.class);

                            i.putExtra("Create","arapdetail");
                            i.putExtra("collectionSum", (Serializable) invoiceSumList);
                            mContext.startActivity(i);
                        }else{
                            Intent i = new Intent(mContext, CreatePaymentActivity.class);
                            i.putExtra("Create","arapdetail");
                            i.putExtra("collectionSum",(Serializable) invoiceSumList);
                            mContext.startActivity(i);
                        }
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

                Utils.hideLoading();

            }
        });
    }


    private void setFirmArea() {

            firmSelectedRL.setVisibility(View.VISIBLE);
            //firmNameTV.setText(ArApCollectionDTO.get);
            amount.setText("₹"+String.valueOf(format2DecAmountDouble(ArApCollectionDTO.getTransactions().get(0).getAmount())));
            payment_invoice_total.setText("₹" +  format2DecAmountDouble(Double.valueOf(ArApCollectionDTO.getTransactions().get(0).getAmount())));

    }

    private void setInvoiceDate() {

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            DateFormat format1 = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);



            try {
                date = format.parse(ArApCollectionDTO.getSlipDate());
                String date1= format1.format(date);
                String dates[]=date1.split("/");
                invoiceDayMonth = dates[0]+ " " + dates[1];
                invoice_detail_day_month.setText(invoiceDayMonth);
                invoice_detail_year.setText(dates[2]);

            } catch (ParseException e) {
                e.printStackTrace();
            }


    }

    private void setshipmentNo() {

            slipno.setText(ArApCollectionDTO.getSlipNo());
            setCollectionType();


    }

    private void setCollectionType() {
        Utils.showLoading(mContext);
        ProductService.getCollectionType(new ServiceCall<BaseModel<List<CollectionTypeDTO>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<List<CollectionTypeDTO>> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    List<CollectionTypeDTO> data = response.getData();
                    if (data == null) {
                        data = new ArrayList<>();
                    }
                    arapNo = data;

                    collection_type_spinner.setAdapter(new OneLineSpinnerAdapter(mContext, arapNo, Color.parseColor("#000000")));
                    collection_type_spinner.setSelection(0);
                    if (Create.equalsIgnoreCase("arapdetail")){
                        for (int i=0;i<arapNo.size();i++){

                            if (ArApCollectionDTO.getTransactions().get(0).getGlCrossAccount().getLogicalref()==arapNo.get(i).getLogicalRefernce()){
                                collection_type_spinner.setSelection(i);
                            }


                        }

                    }
                    collection_type_spinner.setEnabled(false);


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




    public static String format2DecAmountDouble(Double amount) {
        String formattedAmount = String.format("%.2f", amount);
        return formattedAmount;
    }





}
