package in.co.vyapari.ui.activity.PaymentCollection;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import java.text.DateFormat;
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
import in.co.vyapari.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.util.CurrencyUtil;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.DateUtil;
import in.co.vyapari.util.Utils;

public class CreatePaymentActivity extends SohoActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    private Context mContext;
    private InvoiceDTO invoicedto;
    private Invoice invoice;
    private boolean isApproved;
    String  getcollectioncode;
    String invoiceDayMonth;
    CalendarModel cm;
    List<CollectionTypeDTO> arapNo;
    String Create;
    private List<CollectionSum> invoiceSumList;
    private ArApCollectionDTO ArApCollectionDTO;

    @BindView(R.id.invoice_detail_firm_selected)
    LinearLayout firmSelectedRL;
    @BindView(R.id.create_invoice_select_firm_msg)
    TextView firmNameTV;
    @BindView(R.id.addicon)
    ImageView addicon;
    @BindView(R.id.discription)
    EditText discription;
    @BindView(R.id.amount)
    EditText amount;
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
    @BindView(R.id.create_payment_cance_button)
    Button create_payment_cance_button;
    @BindView(R.id.referenceNo)
    EditText referenceNo;
    Date date;
    @BindView(R.id.modes)
    TextView modes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection_payment);

        ButterKnife.bind(this);
        setToolbarConfig(getString(R.string.payments), false);
        mContext = this;
        invoice = new Invoice();
        Gson gson = new Gson();
        String jsn = gson.toJson(invoicedto);
        //Log.e("JSON OBJECT-", jsn);
        Create = getIntent().getExtras().getString("Create");
        invoiceSumList= (List<CollectionSum>) getIntent().getExtras().getSerializable("collectionSum");
        if (Create.equalsIgnoreCase("false")){
            isApproved = DataUtil.getBundle(Boolean.class) != null ? DataUtil.getBundleAndRemove(Boolean.class) : false;
            invoicedto = DataUtil.getBundleAndRemove(InvoiceDTO.class);
        } else if (Create.equalsIgnoreCase("arapdetail")) {
            ArApCollectionDTO = DataUtil.getBundleAndRemove(ArApCollectionDTO.class);
            referenceNo.setText(ArApCollectionDTO.getReceiptNo());
            discription.setText(ArApCollectionDTO.getDescription());

            for(int i =0;i<invoiceSumList.size();i++){
                if (invoiceSumList.get(i).getARPCode().equalsIgnoreCase(ArApCollectionDTO.getTransactions().get(0).getArpCode())){
                    firmNameTV.setText(invoiceSumList.get(i).getARPName());
                }
            }

        }
        modes.setText(R.string.Paymentmodes);
        setFirmArea();
        setInvoiceDate();
        setshipmentNo();


        collection_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getcollectioncode = arapNo.get(i).getCode();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String amount=s.toString();
                if (!amount.equalsIgnoreCase("")) {
                    payment_invoice_total.setText("₹" + format2DecAmountDouble(Double.parseDouble(amount)));
                }else{
                    payment_invoice_total.setText("");
                }
            }
        });
    }
    @OnClick(R.id.create_invoice_showDatePickerDialogInvoiceDate)
    public void datePicker(){
        if (Create.equalsIgnoreCase("false")) {
            cm = DateUtil.dateToCM(invoicedto.getInvoiceDate());
            date = invoicedto.getInvoiceDate();
            invoiceDayMonth = cm.getDay() + " " + cm.getMonthName();
            invoice_detail_day_month.setText(invoiceDayMonth);
            invoice_detail_year.setText(cm.getYear());
        } else {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

            DatePickerDialog dialog = new DatePickerDialog(mContext, this,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }

    }
    @OnClick(R.id.create_payment_cance_button)
    public void onCancelClik(){
        onBackPressed();
    }

    @OnClick(R.id.invoice_detail_firm_selected)
    public void firmUnselectedClick() {
        startActivityForResult(new Intent(mContext, SelectFirmForInvoiceActivity.class), Constants.SELECT_FIRM_FOR_INVOICE);

    }

    @OnClick(R.id.create_payment_submit_button)
    public void saveButtonClick() {
        Boolean save=true;
        String amt=amount.getText().toString().trim();
        String ARapname=firmNameTV.getText().toString().trim();
        String description=discription.getText().toString().trim();
        String logicalref=referenceNo.getText().toString().trim();

        if(ARapname.equalsIgnoreCase("Add AR/AP")){
            save=false;
            Toasty.error(mContext, getString(R.string.entercustomer)).show();
        }else if(amt.equalsIgnoreCase("")){
            save=false;
            Toasty.error(mContext, getString(R.string.enteramound)).show();
        }

        if(save){
            ArApCollection arap= new ArApCollection();
            Transaction transaction= new Transaction();
            GlCrossAccount glCrossAccount= new GlCrossAccount();
            arap.setSlipNo(slipno.getText().toString());
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");
            sdf.setTimeZone(TimeZone.getTimeZone("IST"));
            arap.setSlipDate(sdf.format(date));
            if (logicalref.equalsIgnoreCase("")){
                arap.setReceiptNo("");
            }else{
                arap.setReceiptNo(logicalref);
            }
            if (description.equalsIgnoreCase("")){
                arap.setDescription("");
            }else{
                arap.setDescription(description);
            }
            glCrossAccount.setKey("0");
            glCrossAccount.setValue(getcollectioncode);
            transaction.setAmount((int) Math.round(Double.valueOf(amt)));
            transaction.setDescription(description);
            if (Create.equalsIgnoreCase("false")) {
                transaction.setArpCode(invoicedto.getFirm().getCode());
            }else if (Create.equalsIgnoreCase("arapdetail")){
                transaction.setArpCode(ArApCollectionDTO.getTransactions().get(0).getArpCode());
            }else{
                transaction.setArpCode(invoice.getFirm().getCode());
            }
            transaction.setGlCrossAccount(glCrossAccount);
            arap.setTransactions(Collections.singletonList(transaction));
            Gson gson = new Gson();
            String jsn = gson.toJson(arap);
            //Log.e("JSON OBJECT COLLEction-", jsn);
            saveCollectionARAP(2,arap);
        }


    }

    private void saveCollectionARAP(int i,ArApCollection ArapCollection) {
        Utils.showLoading(mContext);
        ProductService.saveCollection(i,ArapCollection, new ServiceCall<BaseModel<ArApCollectionDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArApCollectionDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    MobileConstants.invoiceId = "changeStatus";
                    onBackPressed();

                } else {
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

    private void setFirmArea() {
        if(Create.equalsIgnoreCase("false")) {
            firmSelectedRL.setVisibility(View.VISIBLE);
            addicon.setVisibility(View.GONE);
            firmNameTV.setText(invoicedto.getFirm().getName());
            amount.setText(String.valueOf(invoicedto.getTotalAmount()));
            payment_invoice_total.setText(CurrencyUtil.doubleToCurrency(invoicedto.getTotalAmount(), invoicedto.getCurrency()).toString(), true);
        } else if (Create.equalsIgnoreCase("arapdetail")) {
            firmSelectedRL.setVisibility(View.VISIBLE);
            addicon.setVisibility(View.GONE);
            //firmNameTV.setText(ArApCollectionDTO.get);
            amount.setText(String.valueOf(format2DecAmountDouble(ArApCollectionDTO.getTransactions().get(0).getAmount())));
            payment_invoice_total.setText("₹" + format2DecAmountDouble(ArApCollectionDTO.getTransactions().get(0).getAmount()));
        } else{
            firmSelectedRL.setVisibility(View.VISIBLE);
            addicon.setVisibility(View.VISIBLE);
        }
    }

    private void setInvoiceDate() {
        if(Create.equalsIgnoreCase("false")) {
            cm = DateUtil.dateToCM(invoicedto.getInvoiceDate());
            date=invoicedto.getInvoiceDate();
            invoiceDayMonth = cm.getDay() + " " + cm.getMonthName();
            invoice_detail_day_month.setText(invoiceDayMonth);
            invoice_detail_year.setText(cm.getYear());
        }else if (Create.equalsIgnoreCase("arapdetail")) {
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

        } else{
            Date dates= new Date(new Date().getTime());

            cm = DateUtil.dateToCM(dates);
            date=dates;
            invoiceDayMonth = cm.getDay() + " " + cm.getMonthName();
            invoice_detail_day_month.setText(invoiceDayMonth);
            invoice_detail_year.setText(cm.getYear());
        }
    }

    private void setshipmentNo() {
        if (Create.equalsIgnoreCase("arapdetail")) {
            slipno.setText(ArApCollectionDTO.getSlipNo());
            setCollectionType();
        } else {

            Utils.showLoading(mContext);
            ProductService.getArpSlipNumber(new ServiceCall<BaseModel<String>>() {
                @Override
                public void onResponse(boolean isOnline, BaseModel<String> response) {
                    Utils.hideLoading();
                    if (!response.isError()) {
                        String arapNo = response.getData();
                        //Log.e("Arapno", arapNo);
                        slipno.setText(arapNo);
                        setCollectionType();

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

    }

    private void setCollectionType() {
        Utils.showLoading(mContext);
        ProductService.getCollectionType( new ServiceCall<BaseModel<List<CollectionTypeDTO>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<List<CollectionTypeDTO>> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    List<CollectionTypeDTO> data = response.getData();
                    if (data == null) {
                        data = new ArrayList<>();
                    }
                    arapNo = data;

                    collection_type_spinner.setAdapter(new OneLineSpinnerAdapter(mContext, arapNo,Color.parseColor("#000000")));
                    collection_type_spinner.setSelection(0);
                    if (Create.equalsIgnoreCase("arapdetail")){
                        for (int i=0;i<arapNo.size();i++){

                            if (ArApCollectionDTO.getTransactions().get(0).getGlCrossAccount().getLogicalref()==arapNo.get(i).getLogicalRefernce()){
                                collection_type_spinner.setSelection(i);
                            }


                        }

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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SELECT_FIRM_FOR_INVOICE) {
            if (resultCode == Activity.RESULT_OK) {
                Firm firm = data.getParcelableExtra(Constants.FIRM);
                FirmDTO fto= new FirmDTO(firm);
                if (firm != null) {
                    if (Create.equalsIgnoreCase("false")) {
                        invoicedto.setFirm(fto);
                        setFirmArea();
                    }else{
                        invoice.setFirm(firm);
                        firmNameTV.setText(invoice.getFirm().getName());
                        addicon.setVisibility(View.GONE);
                    }



                }
            }
        }
//        else if (requestCode == Constants.SELECT_DELIVERY_ADDRESS_FOR_INVOICE) {
//            if (resultCode == Activity.RESULT_OK) {
//                Address address = data.getParcelableExtra(Constants.ADDRESS);
//                if (address != null) {
//                    ArrayList<Address> addresses = invoicedto.getFirm().getShippingAddresses();
//
//                    boolean isEqualsAddress = false;
//                    for (Address a : addresses) {
//                        if (a.equals(address)) {
//                            isEqualsAddress = true;
//                            break;
//                        }
//                    }
//                    if (!isEqualsAddress) {
//                        addresses.add(address);
//                        invoicedto.getFirm().setShippingAddresses(addresses);
//                    }
//                    invoicedto.setDeliveryAddress(address);
//                    deliveryAddress = address;
//                    setAddressSelectArea();
//                    if (invoicedto.getInvoiceLines().size() > 0) {
//                        updateInvoice();
//                    }
//                }
//
//                ArrayList<Address> allAddresses = data.getParcelableArrayListExtra(Constants.ALL_ADDRESSES);
//                if (allAddresses != null && allAddresses.size() > 0) {
//                    invoicedto.getFirm().setShippingAddresses(allAddresses);
//                }
//            }
//
//            if (invoicedto.getDeliveryAddress() == null) {
//                diffDeliveryAddressCB.setChecked(false);
//            }
//        } else if (requestCode == Constants.EDIT_PRODUCT_FOR_INVOICE) {
//            if (resultCode == Activity.RESULT_OK) {
//                invoicedto.getInvoiceLines().remove(selectedInvoiceLine);
//                InvoiceLine invoiceLine = data.getParcelableExtra(Constants.INVOICE_LINE);
//                if (invoiceLine != null) {
//                    invoicedto.getInvoiceLines().add(0, invoiceLine);
//                    updateInvoice();
//                }
//            }
//        } else if (requestCode == Constants.SELECT_PRODUCT_FOR_INVOICE) {
//            if (resultCode == Activity.RESULT_OK) {
//                ArrayList<InvoiceLine> invoiceLines = data.getParcelableArrayListExtra(Constants.INVOICE_LINE);
//                if (invoiceLines != null && invoiceLines.size() > 0) {
//                    invoicedto.getInvoiceLines().addAll(invoiceLines);
//                    updateInvoice();
//                }
//            }
//        }
    }
    public static String format2DecAmountDouble(Double amount) {
        String formattedAmount = String.format("%.2f", amount);
        return formattedAmount;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        date=c.getTime();
        String _dayOfMonth = dayOfMonth > 9 ? String.valueOf(dayOfMonth) : String.valueOf("0" + dayOfMonth);
        String invoiceDayMonth = _dayOfMonth + " " + DateUtil.getMonthForInt(monthOfYear);
        invoice_detail_day_month.setText(invoiceDayMonth);
        invoice_detail_year.setText(String.valueOf(year));
    }
    @Override
    public void onClick(View v) {
    }
}
