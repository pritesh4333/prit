package com.acumengroup.mobile.trade;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PlaceOrderFundaMentalFragment extends GreekBaseFragment {


    private View view;
    private ViewPager viewPager;
    private String isiNumber;
    private GreekTextView txt_inYear, txt_office, txt_tel, txt_chairman, txt_director, txt_register;
    private GreekTextView txt_mrkCap, txt_pe, txt_div, txt_bv, txt_ml, txt_eps, txt_pb, txt_dy, txt_fv, txt_isin;


    public static PlaceOrderFundaMentalFragment newInstance(String isin, String type) {
        Bundle args = new Bundle();
        args.putString("isin", isin);
        args.putString("Type", type);
        PlaceOrderFundaMentalFragment fragment = new PlaceOrderFundaMentalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceOrderFundaMentalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_place_order_funda_mental).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_place_order_funda_mental).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_FUNDAMENTAL_SCREEN;

        txt_inYear = view.findViewById(R.id.txt_inYear);
        txt_office = view.findViewById(R.id.txt_office);
        txt_tel = view.findViewById(R.id.txt_tel);
        txt_chairman = view.findViewById(R.id.txt_chairman);
        txt_director = view.findViewById(R.id.txt_director);
        txt_register = view.findViewById(R.id.txt_register);


        txt_register.setMovementMethod(new ScrollingMovementMethod());


        txt_mrkCap = view.findViewById(R.id.txt_mrkCap);
        txt_pe = view.findViewById(R.id.txt_pe);
        txt_div = view.findViewById(R.id.txt_div);
        txt_bv = view.findViewById(R.id.txt_bv);
        txt_ml = view.findViewById(R.id.txt_ml);

        txt_eps = view.findViewById(R.id.txt_eps);
        txt_pb = view.findViewById(R.id.txt_pb);
        txt_dy = view.findViewById(R.id.txt_dy);
        txt_fv = view.findViewById(R.id.txt_fv);
        txt_isin = view.findViewById(R.id.txt_isin);


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser) {
            getDataAddressServer();
            getDataRegisterServer();
            getDataFromServer();
        }

    }

    private void getDataFromServer() {


        if (AccountDetails.isiSCompanySummaryAvailbale()) {

            if (AccountDetails.getAssetsType().equalsIgnoreCase("NSE")) {
                txt_mrkCap.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(AccountDetails.getNsemcap()))));
            } else {
                txt_mrkCap.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(AccountDetails.getBsemcap()))));
            }
            txt_pe.setText(AccountDetails.getPe());
            txt_div.setText("NA");
            txt_bv.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(AccountDetails.getBv()))));
            txt_eps.setText(AccountDetails.getEps());
            txt_pb.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(AccountDetails.getPb()))));
            txt_dy.setText(AccountDetails.getDivyield());
            txt_fv.setText(AccountDetails.getFv());
            txt_isin.setText(AccountDetails.getIsinumber());

        } else {

            showProgress();
            WSHandler.getRequest(getMainActivity(), "getCompanySummary?isin=" + AccountDetails.getIsinumber(), new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    Log.e("POFundFragment", "" + response);

                    try {
                        JSONArray respCategory = response.getJSONArray("data");

                        for (int i = 0; i < respCategory.length(); i++) {

                            String bsecode = respCategory.getJSONObject(i).getString("bsecode");
                            String symbol = respCategory.getJSONObject(i).getString("symbol");
                            String co_code = respCategory.getJSONObject(i).getString("co_code");
                            String co_name = respCategory.getJSONObject(i).getString("co_name");
                            String complongname = respCategory.getJSONObject(i).getString("complongname");
                            String isin = respCategory.getJSONObject(i).getString("isin");
                            String industryname = respCategory.getJSONObject(i).getString("industryname");
                            String eps = respCategory.getJSONObject(i).getString("eps");
                            String pe = respCategory.getJSONObject(i).getString("pe");
                            String pb = respCategory.getJSONObject(i).getString("pb");

                            String divyield = respCategory.getJSONObject(i).getString("divyield");
                            String bsemcap = respCategory.getJSONObject(i).getString("bsemcap");
                            String nsemcap = respCategory.getJSONObject(i).getString("nsemcap");
                            String fv = respCategory.getJSONObject(i).getString("fv");
                            String bv = respCategory.getJSONObject(i).getString("bv");

                            AccountDetails.setIsinumber(isin);
                            AccountDetails.setEps(eps);
                            AccountDetails.setPe(pe);
                            AccountDetails.setPb(pb);
                            AccountDetails.setDivyield(divyield);
                            AccountDetails.setBsemcap(bsemcap);
                            AccountDetails.setNsemcap(nsemcap);
                            AccountDetails.setFv(fv);
                            AccountDetails.setBv(bv);

                            AccountDetails.setiSCompanySummaryAvailbale(true);


                            if (AccountDetails.getAssetsType().equalsIgnoreCase("NSE")) {
                                txt_mrkCap.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(nsemcap))));
                            } else {
                                txt_mrkCap.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(bsemcap))));
                            }
                            txt_pe.setText(pe);
                            txt_div.setText("NA");
                            txt_bv.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(bv))));
                            txt_eps.setText(eps);
                            txt_pb.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(pb))));
                            txt_dy.setText(divyield);
                            txt_fv.setText(fv);
                            txt_isin.setText(isin);
                        }


                        hideProgress();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        hideProgress();
                        AccountDetails.setiSCompanySummaryAvailbale(false);


                    }

                }

                @Override
                public void onFailure(String message) {
                    hideProgress();
                    AccountDetails.setiSCompanySummaryAvailbale(false);
                }
            });

        }
    }

    private void getDataRegisterServer() {

        showProgress();

        WSHandler.getRequest(getMainActivity(), "getCompanyRegistrar?isin=" + AccountDetails.getIsinumber(), new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.e("POFundFragment", "" + response);

                try {
                    JSONArray respCategory = response.getJSONArray("data");

                    for (int i = 0; i < respCategory.length(); i++) {

                        String address = respCategory.getJSONObject(i).getString("address");
                        String reg_name = respCategory.getJSONObject(i).getString("reg_name");

                        address.replaceAll("\\s+", "");
                        txt_register.setText(address);


                    }


                    hideProgress();

                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgress();

                }

            }

            @Override
            public void onFailure(String message) {
                hideProgress();

            }
        });
    }

    private void getDataAddressServer() {

        showProgress();

        WSHandler.getRequest(getMainActivity(), "getCompanyAddress?isin=" + AccountDetails.getIsinumber(), new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.e("POFundFragment", "" + response);

                try {
                    JSONArray respCategory = response.getJSONArray("data");

                    for (int i = 0; i < respCategory.length(); i++) {

                        String regadd1 = respCategory.getJSONObject(i).getString("regadd1");
                        String regadd2 = respCategory.getJSONObject(i).getString("regadd2");
                        String regdist = respCategory.getJSONObject(i).getString("regdist");
                        String regstate = respCategory.getJSONObject(i).getString("regstate");
                        String regpin = respCategory.getJSONObject(i).getString("regpin");
                        String inc_dt = respCategory.getJSONObject(i).getString("inc_dt");
                        String mkt_lot = respCategory.getJSONObject(i).getString("mkt_lot");

                        String chairman = respCategory.getJSONObject(i).getString("chairman");
                        String dir_name = respCategory.getJSONObject(i).getString("dir_name");
                        String dir_desg = respCategory.getJSONObject(i).getString("dir_desg");
                        String tel1 = respCategory.getJSONObject(i).getString("tel1");

                        txt_inYear.setText(inc_dt);
                        txt_office.setText(regadd1 + ", " + regadd2 + ", " + regdist + ", " + regstate + ", " + regpin);
                        txt_tel.setText(tel1);
                        if (chairman != null) {
                            txt_chairman.setText(chairman);
                        }
                        txt_director.setText(dir_name);

                        txt_ml.setText(mkt_lot);

                    }


                    hideProgress();

                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgress();

                }

            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
    }


    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_FUNDAMENTAL_SCREEN;
    }
}
