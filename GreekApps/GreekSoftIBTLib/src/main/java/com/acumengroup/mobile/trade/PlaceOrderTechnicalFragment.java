package com.acumengroup.mobile.trade;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class PlaceOrderTechnicalFragment extends GreekBaseFragment {

    private View poTechnicalView;
    private GreekTextView technical_txt1, technical_txt2, technical_txt3, technical_txt4, technical_txt5,
            technical_txt6, technical_txt7, R1txt, R2txt, R3txt, txt_S1, txt_S2, txt_S3, pivot_txt, pivotLevels_txt;
    private Spinner spinner;
    private MarketsSingleScripResponse quoteResponse = null;
    ArrayList<String> sym = new ArrayList<>();
    String token, assetType;
    List<String> spinner1List;
    LinearLayout linear1, linear2, linear3, linear4, linear5, heading_linear, main_headin_linear;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlaceOrderTechnicalFragment() {
        // Required empty public constructor
    }


    public static PlaceOrderTechnicalFragment newInstance(String source, String type) {
        PlaceOrderTechnicalFragment fragment = new PlaceOrderTechnicalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, source);
        args.putString(ARG_PARAM2, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinner1List = new ArrayList<>();
        spinner1List.add("Intraday");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        poTechnicalView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_place_order_technical).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_place_order_technical).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        setUpViews(poTechnicalView);
        return poTechnicalView;
    }

    private void setUpViews(View poTechnicalView) {

        token = AccountDetails.globalPlaceOrderBundle.getString(TOKEN);
        assetType = AccountDetails.globalPlaceOrderBundle.getString("AssetType");
        technical_txt1 = poTechnicalView.findViewById(R.id.technical_txt1);
        technical_txt2 = poTechnicalView.findViewById(R.id.technical_txt2);
        technical_txt3 = poTechnicalView.findViewById(R.id.technical_txt3);
        technical_txt4 = poTechnicalView.findViewById(R.id.technical_txt4);
        technical_txt5 = poTechnicalView.findViewById(R.id.technical_txt5);
        technical_txt6 = poTechnicalView.findViewById(R.id.technical_txt6);
        technical_txt7 = poTechnicalView.findViewById(R.id.technical_txt7);
        R1txt = poTechnicalView.findViewById(R.id.R1txt);
        R2txt = poTechnicalView.findViewById(R.id.R2txt);
        R3txt = poTechnicalView.findViewById(R.id.R3txt);
        txt_S1 = poTechnicalView.findViewById(R.id.txt_S1);
        txt_S2 = poTechnicalView.findViewById(R.id.txt_S2);
        txt_S3 = poTechnicalView.findViewById(R.id.txt_S3);
        pivot_txt = poTechnicalView.findViewById(R.id.pivot_txt);
        pivotLevels_txt = poTechnicalView.findViewById(R.id.pivotLevels_txt);
        spinner = poTechnicalView.findViewById(R.id.spinner);

        linear1 = poTechnicalView.findViewById(R.id.linear1);
        linear2 = poTechnicalView.findViewById(R.id.linear2);
        linear3 = poTechnicalView.findViewById(R.id.linear3);
        linear4 = poTechnicalView.findViewById(R.id.linear4);
        linear5 = poTechnicalView.findViewById(R.id.linear5);
        heading_linear = poTechnicalView.findViewById(R.id.heading_linear);
        main_headin_linear = poTechnicalView.findViewById(R.id.main_headin_linear);

        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), spinner1List) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
//                  v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                //v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//                    v.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    v.setBackground(getResources().getDrawable(R.color.white));
                }
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };

        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner.setAdapter(assetTypeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                    sendReportRequest();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setTheme ();
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            technical_txt1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            technical_txt2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            technical_txt3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            technical_txt4.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            technical_txt5.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            technical_txt6.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            technical_txt7.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            txt_S1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_S2.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_S3.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            R1txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            R2txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            R3txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            pivot_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            pivotLevels_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            linear1.setBackground(getResources().getDrawable(R.color.white));
            linear2.setBackground(getResources().getDrawable(R.color.white));
            linear3.setBackground(getResources().getDrawable(R.color.white));
            linear4.setBackground(getResources().getDrawable(R.color.white));
            linear5.setBackground(getResources().getDrawable(R.color.white));
            heading_linear.setBackground(getResources().getDrawable(R.color.market_depth_light));
            main_headin_linear.setBackground(getResources().getDrawable(R.color.market_grey));
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser) {
            getDataFromServer();
        }
    }


    private void getDataFromServer() {
//        showProgress();
        //for Technical details get request is send  to aracane server
        WSHandler.getRequest(getMainActivity(), "getTechnicalDetailsForScript?token=" + token, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.e("POTechnicalFragment", "" + response);

                try {
                    JSONArray respCategory = response.getJSONArray("data");

                    for (int i = 0; i < respCategory.length(); i++) {

                        String pivot = respCategory.getJSONObject(i).getString("pivot");
                        String support1 = respCategory.getJSONObject(i).getString("support1");
                        String support2 = respCategory.getJSONObject(i).getString("support2");
                        String support3 = respCategory.getJSONObject(i).getString("support3");
                        String resist1 = respCategory.getJSONObject(i).getString("resist1");
                        String resist2 = respCategory.getJSONObject(i).getString("resist2");
                        String resist3 = respCategory.getJSONObject(i).getString("resist3");

                        if (!pivot.equalsIgnoreCase("null") && !support1.equalsIgnoreCase("null")
                                && !support3.equalsIgnoreCase("null")
                                && !resist1.equalsIgnoreCase("null")
                                && !resist2.equalsIgnoreCase("null")
                                && !resist3.equalsIgnoreCase("null") &&
                                !support2.equalsIgnoreCase("null")) {
if(getAssetTypeFromToken(token).equalsIgnoreCase("currency")){
    technical_txt4.setText(String.format("%.4f", Double.parseDouble(pivot)));

    technical_txt1.setText(String.format("%.4f", Double.parseDouble(resist1)));
    technical_txt2.setText(String.format("%.4f", Double.parseDouble(resist2)));
    technical_txt3.setText(String.format("%.4f", Double.parseDouble(resist3)));

    technical_txt5.setText(String.format("%.4f", Double.parseDouble(support1)));
    technical_txt6.setText(String.format("%.4f", Double.parseDouble(support2)));
    technical_txt7.setText(String.format("%.4f", Double.parseDouble(support3)));

}else {
    technical_txt4.setText(String.format("%.2f", Double.parseDouble(pivot)));

    technical_txt1.setText(String.format("%.2f", Double.parseDouble(resist1)));
    technical_txt2.setText(String.format("%.2f", Double.parseDouble(resist2)));
    technical_txt3.setText(String.format("%.2f", Double.parseDouble(resist3)));

    technical_txt5.setText(String.format("%.2f", Double.parseDouble(support1)));
    technical_txt6.setText(String.format("%.2f", Double.parseDouble(support2)));
    technical_txt7.setText(String.format("%.2f", Double.parseDouble(support3)));
}
                        }


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

    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onFragmentPause() {
        EventBus.getDefault().unregister(this);
        super.onFragmentPause();
    }


    private String getAssetTypeFromToken(String token) {
        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 101999999)) || ((tokenInt >= 201000000) && (tokenInt <= 201999999))) {
            return "equity";
        } else if (((tokenInt >= 102000000) && (tokenInt <= 102999999)) || ((tokenInt >= 202000000) && (tokenInt <= 202999999))) {
            return "fno";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999)) || ((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "commodity";
        } else {
            return "currency";
        }

        // return "";
    }
}


