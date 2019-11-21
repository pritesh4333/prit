package in.co.vyaparienterprise.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.robinhood.ticker.TickerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.constant.MobileConstants;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.listener.ServiceCallV;
import in.co.vyaparienterprise.middleware.service.CommonService;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.BaseModelV;
import in.co.vyaparienterprise.model.response.ErrorModel;
import in.co.vyaparienterprise.model.response.dto.LoginDTO;
import in.co.vyaparienterprise.model.response.dto.UserDTO;
import in.co.vyaparienterprise.ui.activity.Dashboard.DashboardActivity;
import in.co.vyaparienterprise.ui.activity.app.MainActivity;
import in.co.vyaparienterprise.ui.fragment.SalesOrder.SalesOrderFragment;
import in.co.vyaparienterprise.util.Utils;
import static in.co.vyaparienterprise.ui.activity.app.MainActivity.mainFragmentId;
import static in.co.vyaparienterprise.util.CurrencyUtil.indianCurrency;
import static java.text.NumberFormat.getCurrencyInstance;

public class HomeFragment extends Fragment {
    @BindView(R.id.company_logo)
    CircleImageView company_logo;
    @BindView(R.id.Username)
    TextView Username;
    @BindView(R.id.partybalance)
    TickerView partybalance;
    ArrayList<KeyValue> dashbordata;
    private Context mContext;
    FragmentTransaction fragmentTransaction;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        GenrateTokenForPortal();


        LoginDTO username = (LoginDTO) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_RESPONSE_INFO, LoginDTO.class);
        Username.setText(username.getUser().getFullName());

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        return rootView;
    }



    @OnClick(R.id.sales_invoice)
    public void salesInvoiceClick() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setToolbarConfig(getString(R.string.sales));
        SalesOrderFragment salesOrderFragment = new SalesOrderFragment();
        fragmentTransaction.replace(R.id.frame, salesOrderFragment);
        fragmentTransaction.commit();
        mainFragmentId = R.id.draw_order;
    }

    @OnClick(R.id.customer_suppliers)
    public void customer_suppliersClick() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setToolbarConfig(getString(R.string.firms));
        FirmsFragment firmsFragment = new FirmsFragment();
        fragmentTransaction.replace(R.id.frame, firmsFragment);
        fragmentTransaction.commit();
        mainFragmentId = R.id.draw_firm;
    }

    @OnClick(R.id.collection_payment)
    public void collection_paymentClick() {


        ViewGroup viewGroup = null;
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.setting_update_popup, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        TextView title = (TextView) dialogView.findViewById(R.id.title);
        title.setText("Payments/Collections");
        final AlertDialog alertDialog = builder.create();
        LinearLayout dashboardpaymentcollection = (LinearLayout) dialogView.findViewById(R.id.dashboardpaymentcollection);
        dashboardpaymentcollection.setVisibility(View.VISIBLE);
        ImageView closedialog = (ImageView) dialogView.findViewById(R.id.closes);
        TextView Payment = (TextView) dialogView.findViewById(R.id.Payment);
        TextView collection = (TextView) dialogView.findViewById(R.id.collection);
        Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setToolbarConfig(getString(R.string.payments));
                PaymentFragment paymentFragments = new PaymentFragment();
                fragmentTransaction.replace(R.id.frame, paymentFragments);
                fragmentTransaction.commit();
                mainFragmentId = R.id.draw_payment;
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setToolbarConfig(getString(R.string.Collection));
                CollectionFragment collectionFragment = new CollectionFragment();
                fragmentTransaction.replace(R.id.frame, collectionFragment);
                fragmentTransaction.commit();
                mainFragmentId = R.id.draw_collection;
            }
        });
        closedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @OnClick(R.id.dashboard)
    public void dashboardClik() {
        Intent i = new Intent(mContext, DashboardActivity.class);
        i.putParcelableArrayListExtra("DashboardData", dashbordata);
        startActivity(i);

    }

    private void GenrateTokenForPortal() {


        CommonService.getTokenForPortal(new ServiceCall<BaseModel<KeyValue>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<KeyValue> response) {


                if (!response.isError()) {
                    if (response.getData() != null) {
                        MobileConstants.portalToken = response.getData().getKey();
                        Log.e("Portal Token", response.getData().getKey());
                        getUserData(MobileConstants.portalToken);

                    }
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

    private void getUserData(String portalToken) {
        CommonService.getUserData(portalToken, new ServiceCallV<BaseModelV<UserDTO>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModelV<UserDTO> response) {


                if (!response.isError()) {
                    UserDTO userdro = response.getData();
                    Bitmap bitmap = null;

                    try {
                        byte[] encodeByte = Base64.decode(String.valueOf(userdro.getUserTenant().getTenant().getIcon()), Base64.DEFAULT);
                        bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        if (bitmap == null) {
                            company_logo.setImageDrawable(getResources().getDrawable(R.drawable.default_profile));
                        } else {
                            company_logo.setImageBitmap(bitmap);
                        }
                        Utils.hideLoading();
                        GetDashbordData();
                    } catch (Exception e) {
                        e.getMessage();
                    }


                    //Toasty.success(mContext, response.getMessage()).show();
                } else {
                    Utils.hideLoading();
                    Toasty.success(mContext, response.getMessage()).show();
                }
            }


            @Override
            public void onFailure(boolean isOnline, ErrorModel throwable) {
                Utils.hideLoading();

                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    public void GetDashbordData() {
        CommonService.DashbordInformation(new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {

                Utils.hideLoading();

                if (response.getData().size() != 0) {
                    dashbordata = response.getData();

                    for (int i = 0; i < dashbordata.size(); i++) {
                        if (dashbordata.get(i).getKey().equalsIgnoreCase("NET_PARTY_BALANCE")) {
                            if (format2DecAmountDouble(Double.parseDouble(dashbordata.get(i).getValue())).contains("-"))
                            {

                                partybalance.setText("₹" + indianCurrency(Double.parseDouble(dashbordata.get(i).getValue().substring(1)))+" Cr", true);

                            }else{
                                partybalance.setText("₹" + indianCurrency(Double.valueOf(dashbordata.get(i).getValue()))+" Dr", true);
                            }
                        }
                    }

                } else {
                    Utils.hideLoading();
                    partybalance.setText("₹0.00");
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onResume() {
        super.onResume();
    }
    public static String format2DecAmountDouble(Double amount) {
        String formattedAmount = String.format("%.2f", amount);
        return formattedAmount;
    }
}
