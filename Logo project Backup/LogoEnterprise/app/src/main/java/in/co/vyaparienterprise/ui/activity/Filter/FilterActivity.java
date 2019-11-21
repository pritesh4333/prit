package in.co.vyaparienterprise.ui.activity.Filter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.FirmService;
import in.co.vyaparienterprise.model.CalendarModel;
import in.co.vyaparienterprise.model.request.filter.Filter;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.dto.FirmsSumDTO;
import in.co.vyaparienterprise.model.response.summary.FirmSum;
import in.co.vyaparienterprise.ui.activity.app.MainActivity;
import in.co.vyaparienterprise.ui.activity.warehouse.WarehouseView;
import in.co.vyaparienterprise.ui.adapter.CustomerSupplierAdapter;
import in.co.vyaparienterprise.ui.adapter.FirmsAdapter;
import in.co.vyaparienterprise.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.ui.generic.MyDatePicker;
import in.co.vyaparienterprise.ui.generic.MyEditText;
import in.co.vyaparienterprise.ui.generic.MyItemSelectedListener;
import in.co.vyaparienterprise.ui.generic.MySpinner;
import in.co.vyaparienterprise.ui.generic.MyTimePicker;
import in.co.vyaparienterprise.ui.listener.EndlessRecyclerOnScrollListener;
import in.co.vyaparienterprise.util.DateUtil;
import in.co.vyaparienterprise.util.FilterUtil;
import in.co.vyaparienterprise.util.Utils;

public class FilterActivity extends SohoActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Context mContext;
    @BindView(R.id.options)
    ImageView optionss;
    @BindView(R.id.edit)
    ImageView edit;
    @BindView(R.id.Ordr_no_spinner)
    MyEditText Ordr_no_spinner;
    @BindView(R.id.date_layout)
    LinearLayout date_layout;
    @BindView(R.id.filter_status)
    MyEditText filter_status;
    @BindView(R.id.CUSTOMERSUPPLIER)
    MyEditText customersupply;
    @BindView(R.id.filter_customer_staging)
    MyEditText filter_customer_staging;
    @BindView(R.id.formtypetextview)
    TextView formtypetextview;
    @BindView(R.id.filter_form_type)
    MyEditText filter_form_type;
    @BindView(R.id.FromDatetextview)
    TextView FromDatetextview;
    @BindView(R.id.ToDatetextview)
    TextView ToDatetextview;
    @BindView(R.id.formNo_selection_textview)
    TextView formNo_selection_textview;
    @BindView(R.id.datetextview)
    TextView datetextview;
    @BindView(R.id.customersupp_textview)
    TextView customersupp_textview;
    @BindView(R.id.customer_stagingtextview)
    TextView customer_stagingtextview;
    @BindView(R.id.OrderNo_textview)
    TextView OrderNo_textview;
    @BindView(R.id.OrderNo_Edittext)
    MyEditText OrderNo_Edittext;
    @BindView(R.id.Paymentdroptextview)
    TextView Paymentdroptextview;
    @BindView(R.id.PaymentDropEdittext)
    MyEditText PaymentDropEdittext;
    @BindView(R.id.Collectiondroptextview)
    TextView Collectiondroptextview;
    @BindView(R.id.CollectionDropEdittext)
    MyEditText CollectionDropEdittext;
    @BindView(R.id.Warehouseinventorytextview)
    TextView Warehouseinventorytextview;
    @BindView(R.id.WarehouseinventoryEdittext)
    MyEditText WarehouseinventoryEdittext;
    @BindView(R.id.Dispatchtextview)
    TextView Dispatchtextview;
    @BindView(R.id.DispatchEdittext)
    MyEditText DispatchEdittext;
    @BindView(R.id.PAymentstatustextview)
    TextView PAymentstatustextview;
    @BindView(R.id.PAymentstatusEdittext)
    MyEditText PAymentstatusEdittext;
    @BindView(R.id.Collectionstatustextview)
    TextView Collectionstatustextview;
    @BindView(R.id.CollectionstatusEdittext)
    MyEditText CollectionstatusEdittext;

    private String DateType = "";
    TextView From_date;
    TextView To_date;
    private String FilterStatus;
    private ArrayList<FirmSum> firmSumList = new ArrayList<>();
    private CustomerSupplierAdapter customer_supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        setToolbarConfig(getString(R.string.Filters), false);
        mContext = this;
        try {
            FilterStatus = getIntent().getExtras().getString("FilterType");

            if (FilterStatus.equalsIgnoreCase("SalesOrder")) {
                formNo_selection_textview.setText(getResources().getString(R.string.ORDERNO));
                Paymentdroptextview.setVisibility(View.GONE);
                PaymentDropEdittext.setVisibility(View.GONE);
                Collectiondroptextview.setVisibility(View.GONE);
                CollectionDropEdittext.setVisibility(View.GONE);
                Warehouseinventorytextview.setVisibility(View.GONE);
                WarehouseinventoryEdittext.setVisibility(View.GONE);
                formtypetextview.setVisibility(View.VISIBLE);
                filter_form_type.setVisibility(View.VISIBLE);
                OrderNo_textview.setVisibility(View.GONE);
                OrderNo_Edittext.setVisibility(View.GONE);

            } else if (FilterStatus.equalsIgnoreCase("SalesDispatch")) {
                formNo_selection_textview.setText(getResources().getString(R.string.DISPATCHNOS));
                customer_stagingtextview.setVisibility(View.GONE);
                filter_customer_staging.setVisibility(View.GONE);
                Paymentdroptextview.setVisibility(View.GONE);
                PaymentDropEdittext.setVisibility(View.GONE);
                Collectiondroptextview.setVisibility(View.GONE);
                CollectionDropEdittext.setVisibility(View.GONE);
                Warehouseinventorytextview.setVisibility(View.GONE);
                WarehouseinventoryEdittext.setVisibility(View.GONE);
            } else if (FilterStatus.equalsIgnoreCase("SalesInvoice")) {
                formNo_selection_textview.setText(getResources().getString(R.string.INVOICENO));
                customer_stagingtextview.setVisibility(View.GONE);
                filter_customer_staging.setVisibility(View.GONE);
                Paymentdroptextview.setVisibility(View.GONE);
                PaymentDropEdittext.setVisibility(View.GONE);
                Collectiondroptextview.setVisibility(View.GONE);
                CollectionDropEdittext.setVisibility(View.GONE);
                Warehouseinventorytextview.setVisibility(View.GONE);
                WarehouseinventoryEdittext.setVisibility(View.GONE);
                Dispatchtextview.setVisibility(View.VISIBLE);
                DispatchEdittext.setVisibility(View.VISIBLE);
                Collectionstatustextview.setVisibility(View.VISIBLE);
                CollectionstatusEdittext.setVisibility(View.VISIBLE);
                PAymentstatustextview.setVisibility(View.GONE);
                        PAymentstatusEdittext.setVisibility(View.GONE);
            } else if (FilterStatus.equalsIgnoreCase("SalesReturn")) {
                formNo_selection_textview.setText(getResources().getString(R.string.RETURNNO));
                customer_stagingtextview.setVisibility(View.GONE);
                filter_customer_staging.setVisibility(View.GONE);
                Paymentdroptextview.setVisibility(View.GONE);
                PaymentDropEdittext.setVisibility(View.GONE);
                Collectiondroptextview.setVisibility(View.GONE);
                CollectionDropEdittext.setVisibility(View.GONE);
                Warehouseinventorytextview.setVisibility(View.GONE);
                WarehouseinventoryEdittext.setVisibility(View.GONE);
                OrderNo_textview.setText(getResources().getString(R.string.INVOICENO));

            } else if (FilterStatus.equalsIgnoreCase("PurchaseOrder")) {
                formNo_selection_textview.setVisibility(View.GONE);
                Ordr_no_spinner.setVisibility(View.GONE);
                OrderNo_textview.setVisibility(View.GONE);
                OrderNo_Edittext.setVisibility(View.GONE);
                Paymentdroptextview.setVisibility(View.GONE);
                PaymentDropEdittext.setVisibility(View.GONE);
                Collectiondroptextview.setVisibility(View.GONE);
                CollectionDropEdittext.setVisibility(View.GONE);
                Warehouseinventorytextview.setVisibility(View.GONE);
                WarehouseinventoryEdittext.setVisibility(View.GONE);
            } else if (FilterStatus.equalsIgnoreCase("PurchaseReceipt")) {
                formNo_selection_textview.setText(getResources().getString(R.string.RECEIPTNO));
                customer_stagingtextview.setVisibility(View.GONE);
                filter_customer_staging.setVisibility(View.GONE);
                Paymentdroptextview.setVisibility(View.GONE);
                PaymentDropEdittext.setVisibility(View.GONE);
                Collectiondroptextview.setVisibility(View.GONE);
                CollectionDropEdittext.setVisibility(View.GONE);
                Warehouseinventorytextview.setVisibility(View.GONE);
                WarehouseinventoryEdittext.setVisibility(View.GONE);
            } else if (FilterStatus.equalsIgnoreCase("PurchaseInvoice")) {
                formNo_selection_textview.setText(getResources().getString(R.string.INVOICENO));
                customer_stagingtextview.setVisibility(View.GONE);
                filter_customer_staging.setVisibility(View.GONE);
                Paymentdroptextview.setVisibility(View.GONE);
                PaymentDropEdittext.setVisibility(View.GONE);
                Collectiondroptextview.setVisibility(View.GONE);
                CollectionDropEdittext.setVisibility(View.GONE);
                Warehouseinventorytextview.setVisibility(View.GONE);
                WarehouseinventoryEdittext.setVisibility(View.GONE);
                PAymentstatustextview.setVisibility(View.VISIBLE);
                PAymentstatusEdittext.setVisibility(View.VISIBLE);
                Collectionstatustextview.setVisibility(View.GONE);
                CollectionstatusEdittext.setVisibility(View.GONE);
            } else if (FilterStatus.equalsIgnoreCase("PurchaseReturn")) {
                formNo_selection_textview.setText(getResources().getString(R.string.RETURNNO));
                customer_stagingtextview.setVisibility(View.GONE);
                filter_customer_staging.setVisibility(View.GONE);
                Paymentdroptextview.setVisibility(View.GONE);
                PaymentDropEdittext.setVisibility(View.GONE);
                Collectiondroptextview.setVisibility(View.GONE);
                CollectionDropEdittext.setVisibility(View.GONE);
                Warehouseinventorytextview.setVisibility(View.GONE);
                WarehouseinventoryEdittext.setVisibility(View.GONE);
                OrderNo_textview.setText(getResources().getString(R.string.INVOICENO));
            } else if (FilterStatus.equalsIgnoreCase("PaymentFilter")) {
                formNo_selection_textview.setText(getResources().getString(R.string.SLIPNO));
                customer_stagingtextview.setVisibility(View.GONE);
                filter_customer_staging.setVisibility(View.GONE);
                OrderNo_textview.setVisibility(View.GONE);
                OrderNo_Edittext.setVisibility(View.GONE);
                Warehouseinventorytextview.setVisibility(View.GONE);
                WarehouseinventoryEdittext.setVisibility(View.GONE);
                CollectionDropEdittext.setVisibility(View.GONE);
                        Collectiondroptextview.setVisibility(View.GONE);
            } else if (FilterStatus.equalsIgnoreCase("CollectionFilter")) {
                formNo_selection_textview.setText(getResources().getString(R.string.SLIPNO));
                customer_stagingtextview.setVisibility(View.GONE);
                filter_customer_staging.setVisibility(View.GONE);
                OrderNo_textview.setVisibility(View.GONE);
                OrderNo_Edittext.setVisibility(View.GONE);
                Warehouseinventorytextview.setVisibility(View.GONE);
                WarehouseinventoryEdittext.setVisibility(View.GONE);
                Paymentdroptextview.setVisibility(View.GONE);
                PaymentDropEdittext.setVisibility(View.GONE);
            } else if (FilterStatus.equalsIgnoreCase("ProductFilter")) {
                formNo_selection_textview.setText(getResources().getString(R.string.CODE));
                datetextview.setVisibility(View.GONE);
                date_layout.setVisibility(View.GONE);
                customersupp_textview.setVisibility(View.GONE);
                customersupply.setVisibility(View.GONE);
                customer_stagingtextview.setVisibility(View.GONE);
                filter_customer_staging.setVisibility(View.GONE);
                OrderNo_textview.setVisibility(View.GONE);
                OrderNo_Edittext.setVisibility(View.GONE);
                Paymentdroptextview.setVisibility(View.GONE);
                PaymentDropEdittext.setVisibility(View.GONE);
                Collectiondroptextview.setVisibility(View.GONE);
                CollectionDropEdittext.setVisibility(View.GONE);
            } else if (FilterStatus.equalsIgnoreCase("FirmFilter")) {
                formNo_selection_textview.setText(getResources().getString(R.string.CODE));
                datetextview.setVisibility(View.GONE);
                date_layout.setVisibility(View.GONE);
                customersupp_textview.setVisibility(View.GONE);
                customersupply.setVisibility(View.GONE);
                customer_stagingtextview.setVisibility(View.GONE);
                filter_customer_staging.setVisibility(View.GONE);
                OrderNo_textview.setVisibility(View.GONE);
                OrderNo_Edittext.setVisibility(View.GONE);
                Paymentdroptextview.setVisibility(View.GONE);
                PaymentDropEdittext.setVisibility(View.GONE);
                Collectiondroptextview.setVisibility(View.GONE);
                CollectionDropEdittext.setVisibility(View.GONE);
                Warehouseinventorytextview.setVisibility(View.GONE);
                WarehouseinventoryEdittext.setVisibility(View.GONE);
            }

        } catch (Exception e) {

        }
        optionss.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);


        Ordr_no_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopup("OrderNoLayout");
            }
        });
        date_layout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showpopup("DateLayout");
                    }
                }
        );
        filter_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showpopup("FilterStatusLayout");
            }
        });
        customersupply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopup("CustomerSupplierLayout");
            }
        });
        filter_customer_staging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showpopup("FiltercustomerLayout");
            }
        });
        filter_form_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showpopup("Filterform_typeLayout");
            }
        });


    }


    public void showpopup(String orderNo) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(FilterActivity.this).inflate(R.layout.activity_filter_popup, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(FilterActivity.this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        LinearLayout Order_no_layout = (LinearLayout) dialogView.findViewById(R.id.Order_no_layout);
        LinearLayout Date_layout = (LinearLayout) dialogView.findViewById(R.id.Date_layout);
        LinearLayout status_layout = (LinearLayout) dialogView.findViewById(R.id.status_layout);
        LinearLayout customer_supplier = (LinearLayout) dialogView.findViewById(R.id.customer_supplier);
        LinearLayout customer_staging_layout = (LinearLayout) dialogView.findViewById(R.id.customer_staging_layout);
        LinearLayout Form_type_layout = (LinearLayout) dialogView.findViewById(R.id.Form_type_layout);
        RecyclerView customer_supplier_list = (RecyclerView) dialogView.findViewById(R.id.customer_supplier_list);


        From_date = (TextView) dialogView.findViewById(R.id.From_date);
        To_date = (TextView) dialogView.findViewById(R.id.To_date);
        if (orderNo.equalsIgnoreCase("OrderNoLayout")) {
            Order_no_layout.setVisibility(View.VISIBLE);
        } else if (orderNo.equalsIgnoreCase("DateLayout")) {
            Date_layout.setVisibility(View.VISIBLE);
            From_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b) {
                        DateType = "FromDate";
                        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                        DatePickerDialog dialog = new DatePickerDialog(mContext, FilterActivity.this,
                                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        dialog.show();

                    }

                }
            });
            To_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b) {
                        DateType = "ToDate";
                        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                        DatePickerDialog dialog = new DatePickerDialog(mContext, FilterActivity.this,
                                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        dialog.show();

                    }
                }
            });
        } else if (orderNo.equalsIgnoreCase("FilterStatusLayout")) {
            status_layout.setVisibility(View.VISIBLE);
        } else if (orderNo.equalsIgnoreCase("CustomerSupplierLayout")) {
            customer_supplier.setVisibility(View.VISIBLE);
            configforcustomersupplier(customer_supplier_list);
            getFirmsCall(1, true, customer_supplier_list);
        } else if (orderNo.equalsIgnoreCase("FiltercustomerLayout")) {
            customer_staging_layout.setVisibility(View.VISIBLE);
        } else if (orderNo.equalsIgnoreCase("Filterform_typeLayout")) {
            Form_type_layout.setVisibility(View.VISIBLE);
            MySpinner Form_type_spinner = (MySpinner) dialogView.findViewById(R.id.Form_type_spinner);
            ArrayList<String> FormtypList = new ArrayList<>();
            FormtypList.add("ALL");
            FormtypList.add("LEAD");
            FormtypList.add("ORDER");
            Form_type_spinner.setAdapter(new OneLineSpinnerAdapter(mContext, FormtypList, "", "", ""));
            Form_type_spinner.setSelection(0);

        }
        alertDialog.show();
    }

    private void configforcustomersupplier(final RecyclerView customer_supplier_list) {
        RecyclerView.OnScrollListener endlessSV;

        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        customer_supplier_list.setItemAnimator(new DefaultItemAnimator());
        customer_supplier_list.setHasFixedSize(true);
        customer_supplier_list.setNestedScrollingEnabled(false);
        customer_supplier_list.setLayoutManager(mLayoutManager);
        endlessSV = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getFirmsCall(page, false, customer_supplier_list);
            }
        };
        customer_supplier_list.addOnScrollListener(endlessSV);

    }

    private void getFirmsCall(final int page, final boolean isClean, final RecyclerView customer_supplier_list) {
        Filter filter = FilterUtil.createFilter(page, "");
        FirmService.getFirms(filter, new ServiceCall<BaseModel<FirmsSumDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmsSumDTO> response) {
                if (page == 1) {
                    Utils.hideLoading();
                }

                if (!response.isError()) {
                    if (response.getData() != null) {
                        if (isClean) {
                            resetList(customer_supplier_list);
                        }

                        FirmsSumDTO firmsSumDTO = response.getData();
                        if (firmsSumDTO.getFirms().size() > 0) {

                            firmSumList.addAll(firmsSumDTO.getFirms());

                            //resetList( customer_supplier_list);
                            customer_supplier.notifyDataSetChanged();

                        } else {
                            if (page == 1) {
                                customer_supplier_list.setVisibility(View.VISIBLE);
                            } else {
                                customer_supplier_list.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();

                resetList(customer_supplier_list);
            }
        });
    }

    private void resetList(RecyclerView customer_supplier_list) {
        firmSumList = new ArrayList<>();
        customer_supplier = new CustomerSupplierAdapter(mContext, firmSumList);
        customer_supplier_list.setAdapter(customer_supplier);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        String _dayOfMonth = dayOfMonth > 9 ? String.valueOf(dayOfMonth) : String.valueOf("0" + dayOfMonth);
        String invoiceDayMonth = _dayOfMonth + " " + DateUtil.getMonthForInt(month) + " " + year;
        if (DateType.equalsIgnoreCase("FromDate")) {
            From_date.setText(invoiceDayMonth);
        } else {
            To_date.setText(invoiceDayMonth);
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

    }


}
