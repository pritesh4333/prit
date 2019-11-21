package in.co.vyaparienterprise.ui.activity.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.robinhood.ticker.TickerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.CommonService;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.util.Utils;

import static in.co.vyaparienterprise.util.CurrencyUtil.indianCurrency;

public class DashboardActivity extends SohoActivity {
    private Context mContext;
    @BindView(R.id.options)
    ImageView optionss;
    @BindView(R.id.edit)
    ImageView edit;
    @BindView(R.id.cash)
    TickerView cash;
    @BindView(R.id.Bank)
    TickerView Bank;
    @BindView(R.id.Collection)
    TickerView Collection;
    @BindView(R.id.Payment)
    TickerView Payment;
    @BindView(R.id.material)
    ImageView material;
    @BindView(R.id.customer)
    ImageView customer;
    ArrayList<KeyValue> dashbordataTopItemsByQuantity;
    ArrayList<KeyValue> dashbordataTopItemsByValue;
    ArrayList<KeyValue> dashbordataTopCustomers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setToolbarConfig(getString(R.string.dashboard), false);
        mContext = this;
        optionss.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);


        ArrayList<KeyValue> DashboardData = getIntent().getParcelableArrayListExtra("DashboardData");
        if (DashboardData != null) {
            if (DashboardData.get(0).getValue() != null) {
                if (DashboardData.get(0).getValue().contains("-")) {

                    Bank.setText("₹" + indianCurrency(Double.valueOf(DashboardData.get(0).getValue().substring(1))), true);
                }
                else {
                    Bank.setText("₹" + indianCurrency(Double.valueOf(DashboardData.get(0).getValue())), true);
                }
            } else {
                Bank.setText("₹0.00", true);
            }
            if (DashboardData.get(1).getValue() != null) {
                if (DashboardData.get(1).getValue().contains("-")) {
                    cash.setText("₹" + indianCurrency(Double.valueOf(DashboardData.get(1).getValue().substring(1))), true);
                }else{
                    cash.setText("₹" + indianCurrency(Double.valueOf(DashboardData.get(1).getValue())), true);
                }
            } else {
                cash.setText("₹0.00", true);
            }
            if (DashboardData.get(2).getValue() != null) {
                if (DashboardData.get(2).getValue().contains("-")) {
                    Collection.setText("₹" + indianCurrency(Double.valueOf(DashboardData.get(2).getValue().substring(1))), true);
                }else{
                    Collection.setText("₹"+ indianCurrency(Double.valueOf(DashboardData.get(2).getValue())), true);
                }
            } else {
                Collection.setText("₹0.00", true);
            }
            if (DashboardData.get(3).getValue() != null) {
                if (DashboardData.get(3).getValue().contains("-")) {
                    Payment.setText("₹" + indianCurrency(Double.valueOf(DashboardData.get(3).getValue().substring(1))), true);
                }else{
                    Payment.setText("₹" + indianCurrency(Double.valueOf(DashboardData.get(3).getValue())), true);
                }
            } else {
                Payment.setText("₹0.00", true);
            }
        }


        GetDashbordDataForGraphTopItem("1");
        material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dashbordataTopItemsByQuantity!=null&dashbordataTopItemsByValue!=null) {
                    Intent i = new Intent(mContext, MaterialChartActivity.class);
                    i.putParcelableArrayListExtra("TopMaterial", dashbordataTopItemsByQuantity);
                    i.putParcelableArrayListExtra("TopMaterialByValue", dashbordataTopItemsByValue);
                    startActivity(i);
                }

            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dashbordataTopCustomers!=null) {
                    Intent i = new Intent(mContext, CustomerChartActivity.class);
                    i.putParcelableArrayListExtra("TopCustomers", dashbordataTopCustomers);
                    startActivity(i);
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void GetDashbordDataForGraphTopItem(final String QueryBase) {
        CommonService.DashbordInformationTopItemsGraph(QueryBase, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {

                Utils.hideLoading();

                if (response.getData().size() != 0) {

                    dashbordataTopItemsByQuantity = response.getData();


                    GetDashbordDataForGraphTopCustomers();
                } else {
                    Utils.hideLoading();
                    //Toasty.error(mContext, getString(R.string.error)).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, getString(R.string.error)).show();
            }


        });
    }

    public void GetDashbordDataForGraphTopCustomers() {
        CommonService.DashbordInformationTopCustomerGraph(new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {

                Utils.hideLoading();

                if (response.getData().size() != 0) {
                    dashbordataTopCustomers = response.getData();

                    GetDashbordDataForGraphTopValues("2");

                } else {
                    Utils.hideLoading();
                    //Toasty.error(mContext, getString(R.string.error)).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, getString(R.string.error)).show();
            }


        });
    }

    private void GetDashbordDataForGraphTopValues(final String QueryBase) {
        CommonService.DashbordInformationTopItemsGraph(QueryBase, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {

                Utils.hideLoading();

                if (response.getData().size() != 0) {


                    dashbordataTopItemsByValue = response.getData();


                } else {
                    Utils.hideLoading();
                    //Toasty.error(mContext, getString(R.string.error)).show();
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
