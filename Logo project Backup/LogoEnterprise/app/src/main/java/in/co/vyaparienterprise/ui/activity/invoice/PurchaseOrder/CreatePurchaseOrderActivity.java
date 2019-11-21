package in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.VyapariApp;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.constant.MobileConstants;
import in.co.vyaparienterprise.middleware.ServiceCreator;
import in.co.vyaparienterprise.middleware.ServiceRequest;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.CommonService;
import in.co.vyaparienterprise.middleware.service.FirmService;
import in.co.vyaparienterprise.middleware.service.InvoiceService;
import in.co.vyaparienterprise.middleware.service.ProductService;
import in.co.vyaparienterprise.model.Address;
import in.co.vyaparienterprise.model.ArApCollection;
import in.co.vyaparienterprise.model.CalendarModel;
import in.co.vyaparienterprise.model.DefaultAccount;
import in.co.vyaparienterprise.model.Firm;
import in.co.vyaparienterprise.model.GlCrossAccount;
import in.co.vyaparienterprise.model.Invoice;
import in.co.vyaparienterprise.model.InvoiceLine;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.model.Login;
import in.co.vyaparienterprise.model.POS;
import in.co.vyaparienterprise.model.Transaction;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.ErrorModel;
import in.co.vyaparienterprise.model.response.dto.ArApCollectionDTO;
import in.co.vyaparienterprise.model.response.dto.CollectionTypeDTO;
import in.co.vyaparienterprise.model.response.dto.ExchangeRateDTO;
import in.co.vyaparienterprise.model.response.dto.FirmDTO;
import in.co.vyaparienterprise.model.response.dto.InvoiceDTO;
import in.co.vyaparienterprise.model.response.dto.LoginDTO;
import in.co.vyaparienterprise.ui.activity.app.MainActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SelectAddressForInvoiceActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SelectFirmForInvoiceActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SelectProductDetailForInvoiceActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SelectProductForInvoiceActivity;
import in.co.vyaparienterprise.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyaparienterprise.ui.adapter.invoice.InvoiceProductAdapter;
import in.co.vyaparienterprise.ui.adapter.invoice.InvoicesAdapter;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.ui.generic.MyDatePicker;
import in.co.vyaparienterprise.ui.generic.MyItemSelectedListener;
import in.co.vyaparienterprise.ui.generic.MySpinner;
import in.co.vyaparienterprise.ui.generic.MyTextWatcher;
import in.co.vyaparienterprise.ui.generic.MyTimePicker;
import in.co.vyaparienterprise.ui.generic.dialog.DiscountDialog;
import in.co.vyaparienterprise.ui.generic.showcase.Showcase;
import in.co.vyaparienterprise.ui.listener.ActionListener;
import in.co.vyaparienterprise.ui.listener.ClickListener;
import in.co.vyaparienterprise.ui.listener.RecyclerViewClickListener;
import in.co.vyaparienterprise.util.CurrencyUtil;
import in.co.vyaparienterprise.util.DataUtil;
import in.co.vyaparienterprise.util.DateUtil;
import in.co.vyaparienterprise.util.ErrorUtils;
import in.co.vyaparienterprise.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePurchaseOrderActivity extends SohoActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.create_invoice_day_month)
    TextView invoiceDayMonthTV;
    @BindView(R.id.create_invoice_payment_day_month)
    TextView invoicePaymentDayMonthTV;
    @BindView(R.id.create_invoice_payment_year)
    TextView invoicePaymentYearTV;
    @BindView(R.id.create_invoice_document_number_ll)
    LinearLayout documentNumberLL;
    @BindView(R.id.createinvoicedocumentnumber)
    EditText documentNumberET;
//    @BindView(R.id.create_invoice_showDatePickerDialogDocumentDate)

/*    TextView documentDateYearTV;
    @BindView(R.id.create_invoice_delivery_day_month)*/
//    TextView invoiceDeliveryDayMonthTV;
//    @BindView(R.id.create_invoice_delivery_year)
//    TextView invoiceDeliveryYearTV;
//    @BindView(R.id.create_invoice_delivery_hour_minute)
//    TextView invoiceDeliveryHourMinuteTV;
    @BindView(R.id.create_invoice_firm_unselected)
    LinearLayout firmUnselectedLL;
    @BindView(R.id.create_invoice_select_firm_msg)
    TextView selectFirmMsg;
    @BindView(R.id.create_invoice_firm_selected)
    RelativeLayout firmSelectedRL;
    @BindView(R.id.create_invoice_icon_person)
    ImageView iconPersonIW;
    @BindView(R.id.create_invoice_firm_name)
    TextView firmNameTV;
    @BindView(R.id.create_invoice_firm_bottom_info)
    LinearLayout addressAndVatLL;
    @BindView(R.id.create_invoice_firm_address)
    TextView addressAreaTV;
    @BindView(R.id.create_invoice_firm_gstin_ll)
    LinearLayout GSTINInfoLL;
    @BindView(R.id.create_invoice_firm_gstin)
    TextView GSTINInfoTV;
    @BindView(R.id.create_invoice_firm_pan_ll)
    LinearLayout PANInfoLL;
    @BindView(R.id.create_invoice_firm_pan)
    TextView PANInfoTV;
    @BindView(R.id.create_invoice_delivery_address_ll)
    LinearLayout deliveryAddressLL;
    @BindView(R.id.create_invoice_diff_delivery_address_cb)
    CheckBox diffDeliveryAddressCB;
    @BindView(R.id.create_invoice_delivery_address_bottom_ll)
    LinearLayout deliveryAddressBottomLL;
    @BindView(R.id.create_invoice_delivery_address_tv)
    TextView deliveryAddressTV;
    @BindView(R.id.create_invoice_Order_number)
    EditText invoiceNumberET;
    @BindView(R.id.create_invoice_warehouse_ll)
    LinearLayout warehouseLL;
    @BindView(R.id.create_invoice_warehouse_sp)
    MySpinner warehouseSP;
    //@BindView(R.id.create_invoice_currency_type)
    //MySpinner currencyTypeSP;
    //@BindView(R.id.create_invoice_currency_value)
    //EditText currencyValueET;
    //@BindView(R.id.create_invoice_is_kdv_include)
    //SwitchCompat kdvIncludeSC;
    @BindView(R.id.create_invoice_product_recyclerView)
    RecyclerView productsRW;
    @BindView(R.id.create_invoice_apply_discount)
    Button applyDiscount;
    @BindView(R.id.create_invoice_currency_symbol)
    TextView currencySymbolTV;
    @BindView(R.id.create_invoice_totalwotax)
    TextView totalWoTax;
    @BindView(R.id.create_invoice_general_discount)
    TextView generalDiscountTV;
    @BindView(R.id.create_invoice_total_discount)
    TextView totalDiscountTV;
    @BindView(R.id.create_invoice_cess)
    TextView cessTV;
    @BindView(R.id.create_invoice_cgst_title)
    TextView cgstTitleTV;
    @BindView(R.id.create_invoice_cgst)
    TextView cgstTV;
    @BindView(R.id.create_invoice_sgst_title)
    TextView sgstTitleTV;
    @BindView(R.id.create_invoice_sgst)
    TextView sgstTV;
    @BindView(R.id.create_invoice_igst_title)
    TextView igstTitleTV;
    @BindView(R.id.create_invoice_igst)
    TextView igstTV;
    @BindView(R.id.create_invoice_bottom_area)
    LinearLayout bottomAreaLL;
    @BindView(R.id.create_invoice_total)
    TickerView totalTV;
    @BindView(R.id.create_invoice_delete_button)
    Button deleteButton;
    @BindView(R.id.discription)
    EditText discription;
    @BindView(R.id.Note)
    EditText Note;
    @BindView(R.id.invoice_amount)
    TextView invoice_amount;
    @BindView(R.id.create_invoice_documentdate)
    LinearLayout create_invoice_documentdate;
    @BindView(R.id.documentnumber)
    TextView documentnumber;
    @BindView(R.id.invoice_pos)
    MySpinner invoice_pos;
    List<POS> Listpos;
    private Context mContext;
    private Invoice invoice;
    private Double PaymentAmount;
    private boolean isEditInvoice;
    private boolean isManuelClickToSelectAddress = true;
    private int dateTypeKey;
    private KeyValue invoiceDiscount = null;

    private ArrayList<KeyValue> warehouses = null;

    private double currencyValue;

    private InvoiceProductAdapter invoiceProductAdapter;

    private Address deliveryAddress;
    private InvoiceLine selectedInvoiceLine;
    private Firm selectedFirm;
    private boolean isSaleInvoice;
    private boolean isFirstEdit = true;
    public String arapNo;
    private static String paymentType;
    Spinner otherspinner;
    List<CollectionTypeDTO> collectioncode;
    LoginDTO login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);
        setToolbarConfig(getString(R.string.purchase_Order), false);
        ButterKnife.bind(this);
        mContext = this;
        login= (LoginDTO) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_RESPONSE_INFO, LoginDTO.class);
        invoice_amount.setText(getString(R.string.Order)+" "+getString(R.string.amount));
        invoice = DataUtil.getBundleAndRemove(Invoice.class);
        create_invoice_documentdate.setVisibility(View.GONE);
        documentnumber.setText(getString(R.string.DOCUMNET_NUMBER));
        if (invoice == null) {
            invoice = new Invoice();
        } else {
            if (invoice.getFirm() != null) {
                isEditInvoice = true;
                deleteButton.setVisibility(View.VISIBLE);
                if (invoice.isDeliveryAddressDifferent()) {
                    isManuelClickToSelectAddress = false;
                    deliveryAddress = invoice.getDeliveryAddress();
                    diffDeliveryAddressCB.setChecked(true);
                    setAddressSelectArea();
                } else {
                    isManuelClickToSelectAddress = true;
                }
            }

            isSaleInvoice = invoice.getInvoiceType() == Constants.SALES_INVOICE;
        }

//        if (isSaleInvoice) {
//            setToolbarConfig(getString(R.string.sales_Order), false);
//            selectFirmMsg.setText(R.string.add_customer);
//            documentNumberLL.setVisibility(View.VISIBLE);
//
//        } else {
//            setToolbarConfig(getString(R.string.purchase_Order), false);
//            selectFirmMsg.setText(R.string.add_supplier);
//            documentNumberLL.setVisibility(View.VISIBLE);
//
//        }

        invoice.setCurrency(MobileConstants.defaultCurrency);
        getPlaseOfSale();
        getInvoiceNumber();
        setDocumentNumber();
        getWarehouses();
        setInvoiceDate();
        setDocumentDate();
        setDeliveryDate();
        setCurrencyConfig();
        setFirmSelectArea();
        setInvoiceLineArea();
        setInvoiceDiscountData();
        updateInvoiceLines();
        displayShowcaseSelectFirm();
        setDiscriptionNotes();

        if (isEditInvoice) {
            if (InvoicesAdapter.CopyInvoiceDetails.equalsIgnoreCase("")) {
                getFirmDetail();
            }
        }
    }



    @OnClick(R.id.create_invoice_firm_unselected)
    public void firmUnselectedClick() {
        startActivityForResult(new Intent(mContext, SelectFirmForInvoiceActivity.class), Constants.SELECT_FIRM_FOR_INVOICE);
    }

    @OnClick(R.id.create_invoice_firm_remove)
    public void firmRemoveClick() {
        selectedFirm = null;
        invoice.setFirm(null);
        invoice.setDeliveryAddress(null);
        diffDeliveryAddressCB.setChecked(false);
        deliveryAddress = null;
        setFirmSelectArea();
    }

    @OnClick(R.id.create_invoice_showDatePickerDialogInvoiceDate)
    public void invoiceDateClick() {
        DialogFragment df = new MyDatePicker(invoice.getInvoiceDate());
        df.show(getFragmentManager(), "datePicker");
        dateTypeKey = 1;
    }

    @OnClick(R.id.create_invoice_showDatePickerDialogPaymentDate)
    public void invoicePaymentDateClick() {
        DialogFragment df = new MyDatePicker(invoice.getPaymentDate());
        df.show(getFragmentManager(), "datePicker");
        dateTypeKey = 2;
    }

    @OnClick(R.id.create_invoice_showDatePickerDialogDeliveryDate)
    public void invoiceDeliveryDateClick() {
        DialogFragment df = new MyDatePicker(invoice.getDeliveryDate(), invoice.getInvoiceDate());
        df.show(getFragmentManager(), "datePicker");
        dateTypeKey = 3;
    }

    @OnClick(R.id.create_invoice_showDatePickerDialogDocumentDate)
    public void invoiceDocumentDateClick() {
        DialogFragment df = new MyDatePicker(invoice.getDocumentDate());
        df.show(getFragmentManager(), "datePicker");
        dateTypeKey = 5;
    }

    @OnCheckedChanged(R.id.create_invoice_diff_delivery_address_cb)
    public void diffDeliveryAddressCBCheck(boolean isChecked) {
        if (!isManuelClickToSelectAddress) {
            isManuelClickToSelectAddress = true;
            return;
        }

        if (isChecked) {
            diffDeliveryAddressLLClick();
        } else {
            deliveryAddressBottomLL.setVisibility(View.GONE);
            deliveryAddressTV.setText(R.string.select_plus);
            invoice.setDeliveryAddress(null);
            deliveryAddress = null;
        }

        invoice.setDeliveryAddressDifferent(isChecked);

        if (invoice.getFirm() != null && !isChecked && invoice.getInvoiceLines().size() > 0) {
            updateInvoice();
        }
    }

    @OnClick(R.id.create_invoice_delivery_address_bottom_ll)
    public void diffDeliveryAddressLLClick() {
        Firm firm = invoice.getFirm();
        Bundle bundle = new Bundle();
        Intent addressIntent = new Intent(mContext, SelectAddressForInvoiceActivity.class);
        bundle.putParcelable(Constants.FIRM, firm);
        addressIntent.putExtras(bundle);
        startActivityForResult(addressIntent, Constants.SELECT_DELIVERY_ADDRESS_FOR_INVOICE);
    }

   /* @OnCheckedChanged(R.id.create_invoice_is_kdv_include)
    public void kdvIncludeSCCheck(boolean isChecked) {
        invoice.setGSTIncluded(isChecked);
        if (invoice.getInvoiceLines().size() > 0) {
            updateInvoice();
        }

        if (isChecked) {
            Toasty.info(mContext, "KDV Dahil").show();
        } else {
            Toasty.info(mContext, "KDV Hariç").show();
        }
    }*/

    @OnClick(R.id.create_invoice_select_product)
    public void selectProductClick() {
        if (invoice.getFirm() == null) {
            Toasty.warning(mContext, getString(R.string.ar_ap_select_warn)).show();
            return;
        } else if (invoice.getInvoiceNumber() == null) {
            Toasty.warning(mContext, getString(R.string.invoice_number_warn)).show();
            return;
        }

        DataUtil.post(invoice.getInvoiceType());
        Intent i = new Intent(mContext,SelectProductForInvoiceActivity.class);
        i.putExtra("type","PurchaseOrder");
        startActivityForResult(i,Constants.SELECT_PRODUCT_FOR_INVOICE);

    }

    @OnClick(R.id.create_invoice_apply_discount)
    public void applyDiscountClick() {
        new DiscountDialog(mContext, invoiceDiscount, new ActionListener<KeyValue>() {
            @Override
            public void results(boolean isPreloaded, KeyValue data) {
                invoiceDiscount = data;
                if (invoiceDiscount != null) {
                    if (Utils.equalsKeyValue(invoiceDiscount, Constants.AMOUNT)) {
                        // TODO: Faturaya currrency eklendiği zaman burası değişebilir.
                        invoice.setDiscountType(1);
                        invoice.setGeneralDiscountAmount(Double.valueOf(invoiceDiscount.getValue()));
                    } else if (Utils.equalsKeyValue(invoiceDiscount, Constants.PERCENT)) {
                        invoice.setDiscountType(2);
                        invoice.setGeneralDiscountRate(Double.valueOf(invoiceDiscount.getValue()));
                    }
                } else {
                    invoice.setDiscountType(0);
                    invoice.setGeneralDiscountRate(0);
                    invoice.setGeneralDiscountAmount(0);
                }

                updateInvoice();
            }
        });
    }

    @OnClick(R.id.create_invoice_submit_button)
    public void createInvoiceClick() {
        if (!VyapariApp.getServiceQueue().isSuccess()) {
            return;
        }

        //String content = String.valueOf(invoice.getFirm().getName() + " firmasına " + CurrencyUtil.doubleToCurrency(invoice.getTotalAmount(), invoice.getCurrency()) + " tutarında fatura kesilecektir.");
        CalendarModel calendarModel = DateUtil.dateToCM(invoice.getInvoiceDate());
        String date = calendarModel.getDay() + ", " + calendarModel.getMonthName() + " " + calendarModel.getYear();
        String content = String.format(getString(R.string.create_Purchase_Order_approve_message), invoice.getFirm().getName(), CurrencyUtil.doubleToCurrency(invoice.getTotalAmount(), invoice.getCurrency()), date);
        String title;
        if (isEditInvoice) {
            if (InvoicesAdapter.CopyInvoiceDetails.equalsIgnoreCase("")) {
                title = getString(R.string.edit_Purchase_order);
            }else{
                title = getString(R.string.create_Purchase_order);
            }
        } else {
            title = getString(R.string.create_Purchase_order);
        }

        if (DateUtil.daysBetween(invoice.getDeliveryDate(), invoice.getInvoiceDate()) > 0) {
            Toasty.error(mContext, getString(R.string.delivery_date_warn)).show();
            return;
        }

        Utils.generateDialog(mContext, title, content,
                getString(R.string.ok), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (isEditInvoice) {
                            if (InvoicesAdapter.CopyInvoiceDetails.equalsIgnoreCase("")) {
                                updateInvoiceCall();
                            } else {
                                createInvoiceCall();
                            }
                        } else {
                            //createInvoice();
                            createInvoiceCall();
                        }
                    }
                },
                getString(R.string.cancel), null);
    }

    @OnClick(R.id.create_invoice_delete_button)
    public void deleteInvoice() {
        Utils.generateDialog(mContext, getString(R.string.delete), invoice.getFirm().getName(),
                getString(R.string.delete), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteInvoiceCall();
                    }
                },
                getString(R.string.cancel), null);
    }

    private void createInvoice() {
        boolean isDiffDeliveryAddress = diffDeliveryAddressCB.isChecked();
        invoice.setDeliveryAddressDifferent(isDiffDeliveryAddress);
        if (isDiffDeliveryAddress) {
            invoice.setDeliveryAddress(deliveryAddress);
        } else {
            invoice.setDeliveryAddress(null);
        }

        ViewGroup viewGroup = findViewById(android.R.id.content);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.collection_popup, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(CreatePurchaseOrderActivity.this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        final LinearLayout collection_option = (LinearLayout) dialogView.findViewById(R.id.collection_option);
        final LinearLayout approveornot = (LinearLayout) dialogView.findViewById(R.id.approveornot);
        final LinearLayout otherlayout=(LinearLayout)dialogView.findViewById(R.id.otherlayout);
        final LinearLayout amountlayout=(LinearLayout)dialogView.findViewById(R.id.amountlayout);
        final EditText amount = (EditText) dialogView.findViewById(R.id.amount);
        final TextView debit_credit=(TextView)dialogView.findViewById(R.id.debit_credit);
        final TextView bank=(TextView)dialogView.findViewById(R.id.bank);
        final TextView cash=(TextView)dialogView.findViewById(R.id.cash);
        final TextView other=(TextView)dialogView.findViewById(R.id.other);
        final ImageView close=(ImageView)dialogView.findViewById(R.id.closes);
        final EditText otheramount = (EditText) dialogView.findViewById(R.id.otheramount);
        otherspinner=(Spinner)dialogView.findViewById(R.id.other_spinner);
        final Button othercancel = (Button) dialogView.findViewById(R.id.othercancel);
        final Button otherok = (Button) dialogView.findViewById(R.id.otherok);
        final Button skip = (Button) dialogView.findViewById(R.id.skip);
        amount.setText("" + format2DecAmountDouble(invoice.getTotalAmount()));
        PaymentAmount= Double.valueOf(format2DecAmountDouble(Double.parseDouble(amount.getText().toString())));
        debit_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentAmount= Double.valueOf(format2DecAmountDouble(Double.parseDouble(amount.getText().toString())));
                alertDialog.dismiss();
                Login login= (Login) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_INFO, Login.class);
                DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
                if(defaultAccount!=null) {
                    if (defaultAccount.getUsername().equalsIgnoreCase(login.getUserName())&&defaultAccount.getUsername()!=null&& defaultAccount.getBankid()!=null) {
                        alertDialog.dismiss();
                        paymentType = "Card";
                        getSlipno();

                    }else{
                        Toasty.error(mContext,getString(R.string.UpdateDefaultAccount)).show();
                    }
                }else{
                    Toasty.error(mContext, mContext.getString(R.string.UpdateDefaultAccount)).show();
                }

            }
        });
        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentAmount= Double.valueOf(format2DecAmountDouble(Double.parseDouble(amount.getText().toString())));

                Login login= (Login) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_INFO, Login.class);
                DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
                if(defaultAccount!=null) {
                    if (defaultAccount.getUsername().equalsIgnoreCase(login.getUserName())&&defaultAccount.getUsername()!=null&& defaultAccount.getBankid()!=null) {
                        alertDialog.dismiss();
                        paymentType = "Bank";
                        getSlipno();

                    }else{
                        Toasty.error(mContext,getString(R.string.UpdateDefaultAccount)).show();
                    }
                }else{
                    Toasty.error(mContext, mContext.getString(R.string.UpdateDefaultAccount)).show();
                }
            }
        });
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentAmount= Double.valueOf(format2DecAmountDouble(Double.parseDouble(amount.getText().toString())));
                Login login= (Login) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_INFO, Login.class);
                DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
                if(defaultAccount!=null) {
                    if (defaultAccount.getUsername().equalsIgnoreCase(login.getUserName())&&defaultAccount.getUsername()!=null&& defaultAccount.getBankid()!=null) {
                        alertDialog.dismiss();
                        paymentType = "Cash";
                        getSlipno();
                    }else{
                        Toasty.error(mContext,getString(R.string.UpdateDefaultAccount)).show();
                    }
                }else{
                    Toasty.error(mContext, mContext.getString(R.string.UpdateDefaultAccount)).show();
                }
            }
        });
        otherspinner.setOnItemSelectedListener(new MyItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                paymentType="other";
                PaymentAmount= Double.valueOf(format2DecAmountDouble(Double.parseDouble(amount.getText().toString())));
                DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);

                DefaultAccount defaultAccountnew = new DefaultAccount();
                defaultAccountnew.setCashcode(defaultAccount.getCashcode());
                defaultAccountnew.setBankcode(defaultAccount.getBankcode());
                defaultAccountnew.setCardcode(defaultAccount.getCardcode());
                defaultAccountnew.setUsername(defaultAccount.getUsername());
                defaultAccountnew.setCashid(defaultAccount.getCashid());
                defaultAccountnew.setBankid(defaultAccount.getBankid());
                defaultAccountnew.setCardid(defaultAccount.getCardid());
                defaultAccountnew.setOthercode(collectioncode.get(position).getCode());
                Utils.setObjectSharedPreferencesValue(mContext, defaultAccountnew, Constants.DEFAULT_ACCOUNT_INFO);


            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentAmount= Double.valueOf(format2DecAmountDouble(Double.parseDouble(amount.getText().toString())));
                        collection_option.setVisibility(View.GONE);
                        approveornot.setVisibility(View.GONE);
                        otherlayout.setVisibility(View.VISIBLE);
                        amountlayout.setVisibility(View.GONE);
                        otheramount.setText("" + format2DecAmountDouble(PaymentAmount));
                        collection_option.setVisibility(View.GONE);
                        approveornot.setVisibility(View.GONE);
                        otherlayout.setVisibility(View.VISIBLE);
                        amountlayout.setVisibility(View.GONE);
                        setCollectionType();


            }
        });
        otherok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentAmount= Double.valueOf(format2DecAmountDouble(Double.parseDouble(otheramount.getText().toString())));
                Login login= (Login) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_INFO, Login.class);
                DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
                if(defaultAccount!=null) {
                    if (defaultAccount.getUsername().equalsIgnoreCase(login.getUserName())&&defaultAccount.getUsername()!=null&& defaultAccount.getBankid()!=null) {
                        paymentType = "Other";
                        alertDialog.dismiss();
                        getSlipno();
                    }else{
                        Toasty.error(mContext,getString(R.string.UpdateDefaultAccount)).show();
                    }
                }else{
                    Toasty.error(mContext, mContext.getString(R.string.UpdateDefaultAccount)).show();
                }
            }
        });
        othercancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
                createInvoiceCall();

            }
        });


        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        alertDialog.show();

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
                    collectioncode = data;

                    otherspinner.setAdapter(new OneLineSpinnerAdapter(mContext, collectioncode, Color.parseColor("#000000")));
                    otherspinner.setSelection(0);



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
    private void getSlipno() {


        Utils.showLoading(mContext);
        ProductService.getArpSlipNumber(new ServiceCall<BaseModel<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<String> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    arapNo = response.getData();
                    Log.e("Arapno", arapNo);

                    CreateArAp();
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
    private void CreateArAp() {
        ArApCollection arap = new ArApCollection();
        Transaction transaction = new Transaction();
        GlCrossAccount glCrossAccount = new GlCrossAccount();
        arap.setSlipNo(arapNo);
        Date dates = new Date(new Date().getTime());
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        arap.setSlipDate(sdf.format(dates.getTime()));

        arap.setReceiptNo("");

        if (discription.getText().toString().equalsIgnoreCase("")) {
            arap.setDescription("");
        } else {
            arap.setDescription(discription.getText().toString());
        }
        glCrossAccount.setKey("0");
        DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);

        if (paymentType.equalsIgnoreCase("Bank")) {
            glCrossAccount.setValue(defaultAccount.getBankcode());
        } else if (paymentType.equalsIgnoreCase("Cash")) {
            glCrossAccount.setValue(defaultAccount.getCashcode());
        } else if (paymentType.equalsIgnoreCase("Card")) {
            glCrossAccount.setValue(defaultAccount.getCardcode());
        } else{
            glCrossAccount.setValue(defaultAccount.getOthercode());
        }
        transaction.setAmount(PaymentAmount);
        if (discription.getText().

                toString().

                equalsIgnoreCase("")) {
            transaction.setDescription("");
        } else {
            transaction.setDescription(discription.getText().toString());
        }


        transaction.setArpCode(invoice.getFirm().getCode());


        transaction.setGlCrossAccount(glCrossAccount);
        arap.setTransactions(Collections.singletonList(transaction));

        Gson gson = new Gson();
        String jsn = gson.toJson(arap);
        Log.e("JSON OBJECT COLLEction-", jsn);
        if (isSaleInvoice) {
            saveCollectionARAP(1, arap);
        } else {
            saveCollectionARAP(2, arap);
        }

    }
    private void saveCollectionARAP(int i, ArApCollection ArapCollection) {
        Utils.showLoading(mContext);
        ProductService.saveCollection(i, ArapCollection, new ServiceCall<BaseModel<ArApCollectionDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArApCollectionDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    MobileConstants.invoiceId = "changeStatus";
                    createInvoiceCall();

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

    private void createInvoiceCall() {
        Utils.showLoading(mContext);
        invoice.setDescription(discription.getText().toString());
        invoice.setNote(Note.getText().toString());
        invoice.setOrderType(2);

        for (int i=0;i<invoice.getInvoiceLines().size();i++){

            KeyValue value=invoice.getInvoiceLines().get(i).getProduct().getSACHSNCode();
            if (value==null||value.equals("")){
                KeyValue values=new KeyValue("","");
                invoice.getInvoiceLines().get(i).getProduct().setSACHSNCode(values);
            }else{

            }
        }
        InvoiceService.addOrder(invoice, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    InvoicesAdapter.CopyInvoiceDetails="";
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    MobileConstants.invoiceId = "changeStatus";
                     finish();

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

    private void updateInvoiceCall() {
        Utils.showLoading(mContext);
        invoice.setDescription(discription.getText().toString());
        invoice.setNote(Note.getText().toString());
        invoice.setOrderType(2);

        for (int i=0;i<invoice.getInvoiceLines().size();i++){

            KeyValue value=invoice.getInvoiceLines().get(i).getProduct().getSACHSNCode();
            if (value==null||value.equals("")){
                KeyValue values=new KeyValue("","");
                invoice.getInvoiceLines().get(i).getProduct().setSACHSNCode(values);
            }else{

            }
        }
        InvoiceService.updateOrder(invoice, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    InvoicesAdapter.CopyInvoiceDetails="";
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    returnResultFinish();
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

    private void deleteInvoiceCall() {
        Utils.showLoading(mContext);
        MobileConstants.invoiceId = invoice.getId();
        InvoiceService.deleteOrder(invoice.getId(), 2, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    InvoicesAdapter.CopyInvoiceDetails="";
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    returnResultFinish();
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

    private void setInvoiceDate() {
        if (InvoicesAdapter.CopyInvoiceDetails.equalsIgnoreCase("")) {
            CalendarModel cm = DateUtil.dateToCM(invoice.getInvoiceDate());
            String invoiceDayMonth = cm.getDay() + " " + cm.getMonthName() + " " + cm.getYear();
            invoice.setInvoiceDate(invoice.getInvoiceDate());
            invoiceDayMonthTV.setText(invoiceDayMonth);

        } else {
            Date dates = new Date(new Date().getTime());
            CalendarModel cm = DateUtil.dateToCM(dates);
            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String datess = sdfs.format(dates);
            try {
                Date date = sdfs.parse(datess);
                invoice.setInvoiceDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String invoiceDayMonth = cm.getDay() + " " + cm.getMonthName() + " " + cm.getYear();
            invoiceDayMonthTV.setText(invoiceDayMonth);
        }

    }

    private void setDocumentDate() {
        CalendarModel cm = DateUtil.dateToCM(invoice.getDocumentDate());
        String documentDayMonth = cm.getDay() + " " + cm.getMonthName();
        //documentDateDayMonthTV.setText(documentDayMonth);
        //documentDateYearTV.setText(cm.getYear());
    }

    private void setDeliveryDate() {
        if (invoice.getDeliveryDate() != null) {
            CalendarModel cm = DateUtil.dateToCM(invoice.getDeliveryDate());
            String invoiceDayMonth = cm.getDay() + " " + cm.getMonthName();
            //invoiceDeliveryDayMonthTV.setText(invoiceDayMonth);
            //invoiceDeliveryYearTV.setText(cm.getYear());
            //invoiceDeliveryHourMinuteTV.setText(String.valueOf(cm.getHour() + ":" + cm.getMinute()));
        }
    }

    private void setCurrencyConfig() {
        invoice.setCurrency(MobileConstants.defaultCurrency);

        /*
        // TODO: Currency açılırsa, aktif edilecek.
        CurrencyService.getCurrencyCodes(new ActionListener<ArrayList<Currency>>() {
            @Override
            public void results(boolean isPreloaded, ArrayList<Currency> data) {
                final ArrayList<Currency> currencies = data;
                currencyTypeSP.setAdapter(new CurrencySpinnerAdapter(mContext, currencies));

                for (int i = 0; i < currencies.size(); i++) {
                    if (Utils.equalsKeyValue(currencies.get(i),MobileConstants.defaultCurrency)) {
                        currencySymbolTV.setText(currencies.get(i).getValue());
                        currencyTypeSP.setSelection(i);
                        break;
                    }
                }

                currencyTypeSP.setOnItemSelectedListener(new MyItemSelectedListener() {
                    @Override
                    public void onItemSelected(int position) {
                        Utils.hideKeyboard(mContext);
                        if (!Utils.equalsKeyValue(currencies.get(position),MobileConstants.defaultCurrency)) {
                            Currency currencyCode = currencies.get(position);
                            doExchangeRate(currencyCode.getKey(), MobileConstants.defaultCurrency.getKey());
                            invoice.setCurrency(currencyCode);

                        } else {
                            currencyValueET.setText("");
                            currencyValueET.setEnabled(false);
                            currencyValue = 1;
                        }

                        currencySymbolTV.setText(currencies.get(position).getValue());
                    }
                });

                invoice.setCurrency(MobileConstants.defaultCurrency);
            }
        });*/
    }

    private void setFirmSelectArea() {
        if (invoice.getFirm() == null) {
            firmSelectedRL.setVisibility(View.GONE);
            firmUnselectedLL.setVisibility(View.VISIBLE);
            addressAndVatLL.setVisibility(View.GONE);
            deliveryAddressLL.setVisibility(View.GONE);
            deliveryAddressTV.setText(R.string.select_plus);

            //ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();
            //invoice.setInvoiceLines(invoiceLines);
            //invoiceProductAdapter = new InvoiceProductAdapter(invoice.getInvoiceLines());
            //productsRW.setAdapter(invoiceProductAdapter);
            updateInvoiceLines();
        } else {
            firmUnselectedLL.setVisibility(View.GONE);
            firmSelectedRL.setVisibility(View.VISIBLE);
            firmNameTV.setText(invoice.getFirm().getName());
            addressAndVatLL.setVisibility(View.VISIBLE);

            if (isSaleInvoice) {
                deliveryAddressLL.setVisibility(View.VISIBLE);
            } else {
                deliveryAddressLL.setVisibility(View.VISIBLE);
            }

            Firm firm = invoice.getFirm();
            String addressArea = Utils.createFirmAddressDetail(mContext, firm);

            addressAreaTV.setText(addressArea);

            String gstin = firm.getGSTIN();
            if (gstin != null && !gstin.isEmpty()) {
                GSTINInfoLL.setVisibility(View.VISIBLE);
                GSTINInfoTV.setText(gstin);
            } else {
                GSTINInfoLL.setVisibility(View.GONE);
            }

            String pan = firm.getPAN();
            if (pan != null && !pan.isEmpty()) {
                PANInfoLL.setVisibility(View.VISIBLE);
                PANInfoTV.setText(pan);
            } else {
                PANInfoLL.setVisibility(View.GONE);
            }
        }
    }

    private void setInvoiceLineArea() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        productsRW.setItemAnimator(new DefaultItemAnimator());
        productsRW.setHasFixedSize(true);
        productsRW.setNestedScrollingEnabled(false);
        productsRW.setLayoutManager(mLayoutManager);

        invoiceProductAdapter = new InvoiceProductAdapter(invoice.getInvoiceLines());

        productsRW.addOnItemTouchListener(new RecyclerViewClickListener(mContext, productsRW, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final InvoiceLine invoiceLine = invoice.getInvoiceLines().get(position);
                selectedInvoiceLine = invoice.getInvoiceLines().get(position);
                Utils.generateDialog(mContext, invoiceLine.getProduct().getName(), null,
                        getString(R.string.edit_product), new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Bundle bundle = new Bundle();
                                Intent productIntent = new Intent(mContext, SelectProductDetailForInvoiceActivity.class);
                                bundle.putInt(Constants.INVOICE_TYPE, invoice.getInvoiceType());
                                bundle.putString("type","PurchaseOrder");
                                bundle.putParcelable(Constants.INVOICE_LINE, invoiceLine);
                                productIntent.putExtras(bundle);
                                startActivityForResult(productIntent, Constants.EDIT_PRODUCT_FOR_INVOICE);
                            }
                        },
                        getString(R.string.delete), new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                invoice.getInvoiceLines().remove(invoiceLine);
                                invoiceProductAdapter.notifyDataSetChanged();
                                updateInvoice();
                            }
                        },
                        getString(R.string.cancel), null);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        productsRW.setAdapter(invoiceProductAdapter);
    }

    private void setInvoiceDiscountData() {
        if (invoiceDiscount == null) {
            invoiceDiscount = new KeyValue();
            if (invoice.getDiscountType() == Constants.AMOUNT_CODE) {
                invoiceDiscount.setKey(Constants.AMOUNT);
                invoiceDiscount.setValue(String.valueOf(invoice.getGeneralDiscountAmount()));
            } else if (invoice.getDiscountType() == Constants.PERCENT_CODE) {
                invoiceDiscount.setKey(Constants.PERCENT);
                invoiceDiscount.setValue(String.valueOf(invoice.getGeneralDiscountRate()));
            }
        }
    }

    private void setAddressSelectArea() {
        Address address = invoice.getDeliveryAddress();
        if (address != null && address.getDescription() != null) {
            deliveryAddressBottomLL.setVisibility(View.VISIBLE);
            deliveryAddressTV.setText(getString(R.string.address)+" "+address.getAddressText());
        }
    }

    private void updateInvoiceLines() {
        setInvoiceLineArea();
        if (invoice.getInvoiceLines().size() > 0) {
            bottomAreaLL.setVisibility(View.VISIBLE);
            applyDiscount.setVisibility(View.VISIBLE);
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
        } else {
            bottomAreaLL.setVisibility(View.GONE);
            applyDiscount.setVisibility(View.GONE);
            invoice.setGeneralDiscountRate(0);
            invoice.setGeneralDiscountAmount(0);
            totalWoTax.setText(CurrencyUtil.doubleToCurrency(0, invoice.getCurrency()));
            generalDiscountTV.setText(CurrencyUtil.doubleToCurrency(0, invoice.getCurrency()));
            totalDiscountTV.setText(CurrencyUtil.doubleToCurrency(0, invoice.getCurrency()));
            cessTV.setText(CurrencyUtil.doubleToCurrency(0, invoice.getCurrency()));
            cgstTV.setText(CurrencyUtil.doubleToCurrency(0, invoice.getCurrency()));
            sgstTV.setText(CurrencyUtil.doubleToCurrency(0, invoice.getCurrency()));
            igstTV.setText(CurrencyUtil.doubleToCurrency(0, invoice.getCurrency()));
            totalTV.setText(CurrencyUtil.doubleToCurrency(0, invoice.getCurrency()).toString(), true);

            cgstTitleTV.setText(R.string.CGST);
            igstTitleTV.setText(R.string.IGST);
            sgstTitleTV.setText(R.string.SGST);
        }

        currencySymbolTV.setText(MobileConstants.defaultCurrency.getValue());
    }

    private void getPlaseOfSale() {


            CommonService.getInvoicePos(new ServiceCall<BaseModel<List<POS>>>() {
                @Override
                public void start() {
                    lockSpinner(invoice_pos);
                }

                @Override
                public void onResponse(boolean isOnline, BaseModel<List<POS>> response) {
                    unlockSpinner(invoice_pos);
                    List<POS> data = response.getData();
                    POS pos= new POS("select");
                    data.add(0,pos);

                    if (data == null) {
                        data = new ArrayList<>();
                    }

                    Listpos = data;

                    int whiteColor = Color.parseColor("#FFFFFFFF");
                    invoice_pos.setAdapter(new OneLineSpinnerAdapter(mContext, Listpos,whiteColor,whiteColor));




                    String firmstate=login.getCompany().getState().getKey();
                    for (int i=0;i<Listpos.size();i++){
                        if (firmstate.equalsIgnoreCase(Listpos.get(i).getTIN())){
                            invoice_pos.setSelection(i);
                        }
                    }
                    if (isEditInvoice) {
                        try{
                        String firmstates=invoice.getPointOfSale();
                        for (int i=0;i<Listpos.size();i++){
                            if (firmstates.equalsIgnoreCase(Listpos.get(i).getTIN())){
                                invoice_pos.setSelection(i);
                                invoice.setPointOfSale(String.valueOf(Listpos.get(i).getTIN()));
                            }
                        }
                        }catch(Exception e){
                            Log.e("POS not found",e.getMessage());
                        }

                    }else{
                        invoice_pos.setOnItemSelectedListener(new MyItemSelectedListener() {
                            @Override
                            public void onItemSelected(int position) {
                                //KeyValue keyvalue= new KeyValue(Listpos.get(position).getCode(),Listpos.get(position).getDescription(),Listpos.get(position).getId());

                                invoice.setPointOfSale(String.valueOf(Listpos.get(position).getTIN()));

                            }
                        });
                    }
                }

                @Override
                public void onFailure(boolean isOnline, Throwable throwable) {

                }
            });



    }
    private void getInvoiceNumber() {
        if (isEditInvoice) {
            if (InvoicesAdapter.CopyInvoiceDetails.equalsIgnoreCase("")) {
                String invoiceNumber = invoice.getInvoiceNumber();
                invoiceNumberET.setText(invoiceNumber);
                invoiceNumberET.setSelection(invoiceNumberET.length());
            }else{
                CommonService.getOrderInvoiceNumber(invoice.getInvoiceType(), new ServiceCall<BaseModel<String>>() {
                    @Override
                    public void onResponse(boolean isOnline, BaseModel<String> response) {
                        if (response.getData() != null) {
                            String invoiceNumber = response.getData();
                            invoice.setInvoiceNumber(invoiceNumber);
                            //documentNumberET.setText(invoiceNumber);
                            invoiceNumberET.setText(invoiceNumber);
                            invoiceNumberET.setSelection(invoiceNumberET.length());
                        }
                    }

                    @Override
                    public void onFailure(boolean isOnline, Throwable throwable) {

                    }
                });
            }

        } else {
            CommonService.getOrderInvoiceNumber(invoice.getInvoiceType(), new ServiceCall<BaseModel<String>>() {
                @Override
                public void onResponse(boolean isOnline, BaseModel<String> response) {
                    if (response.getData() != null) {
                        String invoiceNumber = response.getData();
                        invoice.setInvoiceNumber(invoiceNumber);
                        //documentNumberET.setText(invoiceNumber);
                        invoiceNumberET.setText(invoiceNumber);
                        invoiceNumberET.setSelection(invoiceNumberET.length());
                    }
                }

                @Override
                public void onFailure(boolean isOnline, Throwable throwable) {

                }
            });
        }

        invoiceNumberET.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged() {
                invoice.setInvoiceNumber(invoiceNumberET.getText().toString());
            }
        });
    }

    private void setDocumentNumber() {
        if (invoice.getDocumentNumber() != null && !invoice.getDocumentNumber().isEmpty()) {
            documentNumberET.setText(invoice.getDocumentNumber());
        }

        documentNumberET.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged() {
                invoice.setDocumentNumber(documentNumberET.getText().toString());
            }
        });
    }

    private void getWarehouses() {
        CommonService.getWarehouses(new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                warehouses = response.getData();
                warehouses = Utils.cleanList(warehouses);

                if (warehouses != null && warehouses.size() > 1) {
                    warehouseLL.setVisibility(View.VISIBLE);
                    warehouseSP.setAdapter(new OneLineSpinnerAdapter(mContext, warehouses));

                    warehouseSP.setOnItemSelectedListener(new MyItemSelectedListener() {
                        @Override
                        public void onItemSelected(int position) {
                            Utils.hideKeyboard(mContext);
                            invoice.setWarehouse(warehouses.get(position));
                            if (invoice.getInvoiceLines().size() > 0 && !isFirstEdit) {
                                updateInvoice();
                            }
                            isFirstEdit = false;
                        }
                    });

                    if (isEditInvoice) {
                        if (invoice.getWarehouse() != null) {
                            int selection = Utils.getSelection(warehouses, invoice.getWarehouse().getKey());
                            warehouseSP.setSelection(selection);
                        }
                    }
                } else {
                    warehouseLL.setVisibility(View.GONE);

                    if (warehouses.size() == 1) {
                        invoice.setWarehouse(warehouses.get(0));
                    }
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }

    private void getFirmDetail() {
        FirmService.getFirmDetail(invoice.getFirm().getId(), new ServiceCall<BaseModel<FirmDTO>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmDTO> response) {
                Utils.hideLoading();
                if (response.getData() != null) {
                    Firm newFirm = new Firm(response.getData());

                    if (!Utils.equalsObject(newFirm, invoice.getFirm())) {
                        Toasty.info(mContext, "Customer/Supplier updated.").show();
                        invoice.setFirm(newFirm);
                    }
                    setFirmSelectArea();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
            }
        });
    }

    private void doExchangeRate(String currencyCode, String defaultCurrencyCode) {
        ServiceRequest apiService = ServiceCreator.getClient().create(ServiceRequest.class);
        Call<BaseModel<ExchangeRateDTO>> call = apiService.doExchangeRate(currencyCode, defaultCurrencyCode);
        call.enqueue(new Callback<BaseModel<ExchangeRateDTO>>() {
            @Override
            public void onResponse(Call<BaseModel<ExchangeRateDTO>> call, Response<BaseModel<ExchangeRateDTO>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        ExchangeRateDTO exchangeRage = response.body().getData();
                        currencyValue = exchangeRage.getExchangeRate();
                        //currencyValueET.setText(String.valueOf(currencyValue));
                        //currencyValueET.setEnabled(true);
                        updateInvoice();
                    } else {
                        ErrorModel error = ErrorUtils.parseError(response);
                        Toasty.error(mContext, error.getError()).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseModel<ExchangeRateDTO>> call, Throwable ignored) {
            }
        });
    }

    private void updateInvoice() {
        boolean isDiffDeliveryAddress = diffDeliveryAddressCB.isChecked();
        invoice.setDeliveryAddressDifferent(isDiffDeliveryAddress);
        if (isDiffDeliveryAddress) {
            if (isSaleInvoice) {
                invoice.setDeliveryAddress(deliveryAddress);
            } else {
                invoice.setDeliveryAddress(null);
            }
        } else {
            invoice.setDeliveryAddress(null);
        }
        for (int i = 0; i < invoice.getInvoiceLines().size(); i++) {

            KeyValue value = invoice.getInvoiceLines().get(i).getProduct().getSACHSNCode();
            if (value == null || value.equals("")) {
                KeyValue values = new KeyValue("", "");
                invoice.getInvoiceLines().get(i).getProduct().setSACHSNCode(values);
            } else {

            }
        }
        Utils.showLoading(mContext);
        MobileConstants.invoiceId = invoice.getId();


        InvoiceService.calculateOrder(invoice, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    selectedInvoiceLine = null;
                    if (response.getData() != null) {
                        InvoiceDTO invoiceDTO = response.getData();
                        invoice = new Invoice(invoiceDTO, selectedFirm);

                        if (diffDeliveryAddressCB.isChecked()) {
                            invoice.setDeliveryAddress(deliveryAddress);
                        }

                        updateInvoiceLines();
                    }
                } else {
                    invoice.getInvoiceLines().remove(selectedInvoiceLine);
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        long deliveryDateTS = invoice.getDeliveryDate().getTime();
        Date deliveryDate = new Date(deliveryDateTS);
        Calendar c = Calendar.getInstance();
        c.setTime(deliveryDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        String _dayOfMonth = dayOfMonth > 9 ? String.valueOf(dayOfMonth) : String.valueOf("0" + dayOfMonth);
        String _hourOfDay = hourOfDay > 9 ? String.valueOf(hourOfDay) : String.valueOf("0" + hourOfDay);
        String _minute = minute > 9 ? String.valueOf(minute) : String.valueOf("0" + minute);

        if (dateTypeKey == 4) {
            c.set(year, month, dayOfMonth, hourOfDay, minute);
            String invoiceDeliveryDayMonth = _dayOfMonth + " " + DateUtil.getMonthForInt(month);

            if (DateUtil.daysBetween(invoice.getDeliveryDate(), invoice.getInvoiceDate()) > 0) {
                Toasty.error(mContext, getString(R.string.delivery_date_warn)).show();
                return;
            }
            invoice.setDeliveryDate(c.getTime());
            //invoiceDeliveryDayMonthTV.setText(invoiceDeliveryDayMonth);
            //invoiceDeliveryYearTV.setText(String.valueOf(year));
            //invoiceDeliveryHourMinuteTV.setText(String.valueOf(_hourOfDay + ":" + _minute));
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        String _dayOfMonth = dayOfMonth > 9 ? String.valueOf(dayOfMonth) : String.valueOf("0" + dayOfMonth);
        if (dateTypeKey == 1) {
            invoice.setInvoiceDate(c.getTime());
            String invoiceDayMonth = _dayOfMonth + " " + DateUtil.getMonthForInt(month)+" "+year;
            invoiceDayMonthTV.setText(invoiceDayMonth);

        } else if (dateTypeKey == 2) {
            invoice.setPaymentDate(c.getTime());
            String invoicePaymentDayMonth = _dayOfMonth + " " + DateUtil.getMonthForInt(month);
            invoicePaymentDayMonthTV.setText(invoicePaymentDayMonth);
            invoicePaymentYearTV.setText(String.valueOf(year));
        } else if (dateTypeKey == 3) {
            invoice.setDeliveryDate(c.getTime());
            DialogFragment df = new MyTimePicker();
            df.show(getFragmentManager(), "datePicker");
            dateTypeKey = 4;
        } else if (dateTypeKey == 5) {
            invoice.setDocumentDate(c.getTime());
            String invoiceDocumentDayMonth = _dayOfMonth + " " + DateUtil.getMonthForInt(month);
            //documentDateDayMonthTV.setText(invoiceDocumentDayMonth);
            //documentDateYearTV.setText(String.valueOf(year));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.SELECT_FIRM_FOR_INVOICE) {
            if (resultCode == Activity.RESULT_OK) {
                Firm firm = data.getParcelableExtra(Constants.FIRM);
                if (firm != null) {
                    selectedFirm = firm;
                    invoice.setFirm(firm);
                    setFirmSelectArea();
                    displayShowcaseSelectProduct();

                    if (invoice.getInvoiceLines() != null && invoice.getInvoiceLines().size() > 0) {
                        updateInvoice();
                    }
                }
            }
        } else if (requestCode == Constants.SELECT_DELIVERY_ADDRESS_FOR_INVOICE) {
            if (resultCode == Activity.RESULT_OK) {
                Address address = data.getParcelableExtra(Constants.ADDRESS);
                if (address != null) {
                    ArrayList<Address> addresses = invoice.getFirm().getShippingAddresses();

                    boolean isEqualsAddress = false;
                    for (Address a : addresses) {
                        if (a.equals(address)) {
                            isEqualsAddress = true;
                            break;
                        }
                    }
                    if (!isEqualsAddress) {
                        addresses.add(address);
                        invoice.getFirm().setShippingAddresses(addresses);
                    }
                    invoice.setDeliveryAddress(address);
                    deliveryAddress = address;
                    setAddressSelectArea();
                    if (invoice.getInvoiceLines().size() > 0) {
                        updateInvoice();
                    }
                }

                ArrayList<Address> allAddresses = data.getParcelableArrayListExtra(Constants.ALL_ADDRESSES);
                if (allAddresses != null && allAddresses.size() > 0) {
                    invoice.getFirm().setShippingAddresses(allAddresses);
                }
            }

            if (invoice.getDeliveryAddress() == null) {
                diffDeliveryAddressCB.setChecked(false);
            }
        } else if (requestCode == Constants.EDIT_PRODUCT_FOR_INVOICE) {
            if (resultCode == Activity.RESULT_OK) {
                invoice.getInvoiceLines().remove(selectedInvoiceLine);
                InvoiceLine invoiceLine = data.getParcelableExtra(Constants.INVOICE_LINE);
                if (invoiceLine != null) {


                    invoice.getInvoiceLines().add(0, invoiceLine);
                    updateInvoice();
                }
            }
        } else if (requestCode == Constants.SELECT_PRODUCT_FOR_INVOICE) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<InvoiceLine> invoiceLines = data.getParcelableArrayListExtra(Constants.INVOICE_LINE);
                if (invoiceLines != null && invoiceLines.size() > 0) {
                    invoice.getInvoiceLines().addAll(invoiceLines);
                    updateInvoice();
                }
            }
        }
    }

    private void returnResultFinish() {
        MobileConstants.invoiceId = "id";
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.REFRESH, true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    protected void displayShowcaseSelectFirm() {
        if (!isEditInvoice) {
            Showcase.from(this)
                    .setContentView(R.layout.showcase_create_invoice_select_firm)
                    .setFitsSystemWindows(true)
                    .on(R.id.create_invoice_firm_unselected)
                    .addRoundRect()
                    .withBorder()
                    .openIntent(SelectFirmForInvoiceActivity.class, Constants.SELECT_FIRM_FOR_INVOICE)
                    .showOnce(Constants.SELECT_FIRMS);
        }
    }
protected  void setDiscriptionNotes(){
    if (isEditInvoice) {
        discription.setText(invoice.getDescription());
        Note.setText(invoice.getNote());
    }
}
    protected void displayShowcaseSelectProduct() {
        if (!isEditInvoice) {
            MainActivity.Type="PurchaseOrder";
            SharedPreferences sharedPreferences = getSharedPreferences("SHARED_TUTO", Context.MODE_PRIVATE);
            if (!sharedPreferences.contains(Constants.SELECT_PRODUCTS)) {
                DataUtil.post(invoice.getInvoiceType());
                Showcase.from(this)
                        .setContentView(R.layout.showcase_create_invoice_select_product)
                        .setFitsSystemWindows(true)
                        .on(R.id.create_invoice_select_product)
                        .addRoundRect()
                        .withBorder()
                        .openIntent(SelectProductForInvoiceActivity.class, Constants.SELECT_PRODUCT_FOR_INVOICE)
                        .showOnce(Constants.SELECT_PRODUCTS);
            }
        }
    }
    public static String format2DecAmountDouble(Double amount) {
        String formattedAmount = String.format("%.2f", amount);
        return formattedAmount;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InvoicesAdapter.CopyInvoiceDetails="";
    }

}