package com.acumengroup.mobile.trade;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.SymbolVarMarginResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.SecurityInfoModel;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class PlaceOrderSecurityFragment extends GreekBaseFragment {
    private View poSecurityView,view5,view6,view7,view8;
    private LinearLayout security_parent,view1,view2,view3,view4;
    private GreekTextView security_nametxt, security_value, isin_txt, isin_value, FreezeQty_text,
            FreezeQty_value, face_valuetxt, face_value, elm_txt, elm_value, var_txt, var_value,
            elm_percentage_txt, elm_percentage_value, var_percentage_txt, var_percentage_value;
    String token, assetType;
    ArrayList<String> sym = new ArrayList<>();
    private MarketsSingleScripResponse quoteResponse = null;
    static SecurityInfoModel securityInfoModel;
    private OrderStreamingController orderStreamingController;


    public PlaceOrderSecurityFragment() {
    }

    public static PlaceOrderSecurityFragment newInstance(String source, String type) {
        PlaceOrderSecurityFragment fragment = new PlaceOrderSecurityFragment();
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderStreamingController = new OrderStreamingController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        poSecurityView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_place_order_security).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_place_order_security).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        setUpViews(poSecurityView);
        setTheme();
        return poSecurityView;
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            security_nametxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            security_value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            isin_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            isin_value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            FreezeQty_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            FreezeQty_value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            face_valuetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            face_value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            elm_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            elm_value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            var_value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            var_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            elm_percentage_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            elm_percentage_value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            var_percentage_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            var_percentage_value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            security_parent.setBackground(getResources().getDrawable(R.color.white));
            view1.setBackgroundColor(getResources().getColor(R.color.white));
            view2 .setBackgroundColor(getResources().getColor(R.color.white));
            view3.setBackgroundColor(getResources().getColor(R.color.white));
            view4.setBackgroundColor(getResources().getColor(R.color.white));
            view5 .setBackgroundColor(getResources().getColor(R.color.grey_textcolor));
            view6 .setBackgroundColor(getResources().getColor(R.color.grey_textcolor));
            view7 .setBackgroundColor(getResources().getColor(R.color.grey_textcolor));
            view8 .setBackgroundColor(getResources().getColor(R.color.grey_textcolor));



        }
    }

    private void setUpViews(View poSecurityView) {
        token = AccountDetails.globalPlaceOrderBundle.getString(TOKEN);
        assetType = AccountDetails.globalPlaceOrderBundle.getString("AssetType");
        security_nametxt = poSecurityView.findViewById(R.id.security_nametxt);
        security_value = poSecurityView.findViewById(R.id.security_value);
        isin_txt = poSecurityView.findViewById(R.id.isin_txt);
        isin_value = poSecurityView.findViewById(R.id.isin_value);
        FreezeQty_text = poSecurityView.findViewById(R.id.FreezeQty_text);
        FreezeQty_value = poSecurityView.findViewById(R.id.FreezeQty_value);
        face_valuetxt = poSecurityView.findViewById(R.id.face_valuetxt);
        face_value = poSecurityView.findViewById(R.id.face_value);
        elm_txt = poSecurityView.findViewById(R.id.elm_txt);
        elm_value = poSecurityView.findViewById(R.id.elm_value);
        var_txt = poSecurityView.findViewById(R.id.var_txt);
        var_value = poSecurityView.findViewById(R.id.var_value);
        elm_percentage_txt = poSecurityView.findViewById(R.id.elm_percentage_txt);
        elm_percentage_value = poSecurityView.findViewById(R.id.elm_percentage_value);
        var_percentage_txt = poSecurityView.findViewById(R.id.var_percentage_txt);
        var_percentage_value = poSecurityView.findViewById(R.id.var_percentage_value);
        security_parent = poSecurityView.findViewById(R.id.security_parent);
        view1 = poSecurityView.findViewById(R.id.view1);
        view2 = poSecurityView.findViewById(R.id.view2);
        view3 = poSecurityView.findViewById(R.id.view3);
        view4 = poSecurityView.findViewById(R.id.view4);
        view5 = poSecurityView.findViewById(R.id.view5);
        view6 = poSecurityView.findViewById(R.id.view6);
        view7 = poSecurityView.findViewById(R.id.view7);
        view8 = poSecurityView.findViewById(R.id.view8);



    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && !token.isEmpty()) {
            // for elm and var value we are send request to iris server
            orderStreamingController.sendSecurityInfo(getMainActivity(), AccountDetails.getSessionId(getContext()),AccountDetails.getClientCode(getActivity()), token);
            getDataFromServer();

        }
    }

    private void getDataFromServer() {
//        showProgress();
        // To get script info we are requesting get request to aracane server
        WSHandler.getRequest(getMainActivity(), "getScripInfo?token=" + token + "&assetType=" + getAssetTypeFromToken(token) +
                "&exchange=" + getExchange(token), new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {

                try {
                    JSONArray respCategory = response.getJSONArray("data");

                    for (int i = 0; i < respCategory.length(); i++) {


                        String FaceValue = respCategory.getJSONObject(i).getString("FaceValue");
                        String FreezeQty = respCategory.getJSONObject(i).getString("FreezeQty");
                        String RecordDate = respCategory.getJSONObject(i).getString("RecordDate");
                        String CorpAction = respCategory.getJSONObject(i).getString("CorpAction");
                        String symbol = respCategory.getJSONObject(i).getString("symbol");
                        String isin = respCategory.getJSONObject(i).getString("isin");


                        security_value.setText(symbol);
                        FreezeQty_value.setText(FreezeQty);

                        isin_value.setText(isin);
                        face_value.setText(FaceValue);


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

    public void onEventMainThread(SymbolVarMarginResponse streamingResponse) {
        try {
            if (getAssetTypeFromToken(token).equalsIgnoreCase("equity")) {
                elm_value.setText(streamingResponse.getResponse().getData().getELMMargin());
                elm_percentage_value.setText(streamingResponse.getResponse().getData().getELMPercentage());
                var_value.setText(streamingResponse.getResponse().getData().getVARMargin());
                var_percentage_value.setText(streamingResponse.getResponse().getData().getVARPercentage());

                elm_txt.setText("ELM");
                elm_percentage_txt.setText("ELM%");
                var_txt.setText("VAR");
                var_percentage_txt.setText("VAR%");

            } else {

                elm_value.setText(streamingResponse.getResponse().getData().getSPANMarginBuy());
                elm_percentage_value.setText(streamingResponse.getResponse().getData().getSPANMarginSell());
                var_value.setText(streamingResponse.getResponse().getData().getExposMargin());
                var_percentage_value.setText(streamingResponse.getResponse().getData().getExposMarginPer());

                elm_txt.setText("SpanMargin(Buy)");
                elm_percentage_txt.setText("SpanMargin(Sell)");
                var_txt.setText("ExposureMargin");
                var_percentage_txt.setText("ExposureMargin(%)");
            }


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

    private String getExchange(String token) {

        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NCDEX";
        } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "MCX";
        } else if ((tokenInt >= 1903000000) && (tokenInt <= 1903999999)) {
            return "NSE";
        } else if ((tokenInt >= 2003000000) && (tokenInt <= 2003999999)) {
            return "BSE";
        } else if ((tokenInt >= 502000000) && (tokenInt <= 502999999)) {
            return "NSE";
        } else if ((tokenInt >= 1302000000) && (tokenInt <= 1302999999)) {
            return "BSE";
        } else {
            return "BSE";
        }
    }

}