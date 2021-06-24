package com.acumengroup.mobile.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.model.las.LASRequest;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LASFragment extends GreekBaseFragment {

    private GreekButton applyNowSubmit;
    private GreekTextView txt_eligible;
    private ServiceResponseHandler serviceResponseHandler;


    public LASFragment() {
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_la, container, false);
        applyNowSubmit = view.findViewById(R.id.applyNowSubmit);
        GreekTextView las_message=(GreekTextView)view.findViewById(R.id.las_message);
        GreekTextView hl_message=(GreekTextView)view.findViewById(R.id.hl_message);
        GreekTextView wd_message=(GreekTextView)view.findViewById(R.id.wd_message);
        String lasString=getString(R.string.las_message);
        String hlstring=getString(R.string.hl_message);
        String wdstring=getString(R.string.wd_message);
        String lasmessage=lasString.replaceAll("APPNAME", GreekBaseActivity.GREEK);
        String hlmessage=hlstring.replaceAll("APPNAME", GreekBaseActivity.GREEK);
        String wd_messae=wdstring.replaceAll("APPNAME", GreekBaseActivity.GREEK);
        las_message.setText(lasmessage);
        hl_message.setText(hlmessage);
        wd_message.setText(wd_messae);
        applyNowSubmit.setEnabled(false);
        applyNowSubmit.setBackground(getResources().getDrawable(R.drawable.gray_trading_button_effect));

        sendGetLoanEligibility();
        sendForClientDp();

        txt_eligible = view.findViewById(R.id.txt_eligible);
        txt_eligible.setText("As per your current Portfolio you are eligible for instant Loan Against Securities of RS. 0");


        AccountDetails.currentFragment = NAV_TO_LAS_MESSAG_SCREEN;


        applyNowSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navigateTo(NAV_TO_LAS_STEPS_FORMS_SCREEN, new Bundle(), true);
            }
        });

        return view;
    }

    private void sendGetLoanEligibility() {
        showProgress();
        LASRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), getMainActivity(), serviceResponseHandler);
    }

    private void sendForClientDetails() {
        showProgress();
        LASRequest.sendRequestForClientDetails(AccountDetails.getUsername(getMainActivity()), getMainActivity(), serviceResponseHandler);
    }

    private void sendForClientDp() {
        showProgress();

        WSHandler.getRequest(getMainActivity(), "getClientDpId?gscid=" + AccountDetails.getUsername(getMainActivity()), new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                hideProgress();
                Log.e("LASFragment", "response=======>" + response);
                //{"success":"true","message":"","ErrorCode":0,"data":{"DPID":"1208860000000310"}}

                try {

                    String DPID = String.valueOf(response.getJSONArray("data").getJSONObject(0).getString("DPID"));
                    AccountDetails.setDPID(DPID);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String message) {
                hideProgress();

            }
        });


    }

    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);
        hideProgress();
        Log.e("LASFragment", "response======>" + response);
        JSONResponse jsonResponse = (JSONResponse) response;

        if (jsonResponse.getServiceName().equalsIgnoreCase("getLoanEligibility")) {

            try {

                String amount = (String) ((JSONObject) ((JSONArray) ((JSONResponse) response).getData("data")).get(0)).get("EligibilityAmt");

                Double DAmount = Double.parseDouble(amount);

                AccountDetails.setBankamount(String.format("%.2f", DAmount));
                txt_eligible.setText("As per your current Portfolio you are eligible for instant Loan Against Securities of RS." + String.format("%.2f", DAmount));


                if (DAmount <= 0) {

                    applyNowSubmit.setEnabled(false);
                    applyNowSubmit.setBackground(getResources().getDrawable(R.drawable.gray_trading_button_effect));
                } else {

                    applyNowSubmit.setEnabled(true);
                    applyNowSubmit.setBackground(getResources().getDrawable(R.drawable.green_trading_button_effect));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (jsonResponse.getServiceName().equalsIgnoreCase("getClientDetailsForLAS")) {

            JSONArray arraydata = null;
            try {
                arraydata = jsonResponse.getResObject().getJSONObject("response").getJSONArray("data");

                for (int i = 0; i < arraydata.length(); i++) {


                    JSONObject jsonObject = arraydata.getJSONObject(i);

                    String cCity = jsonObject.getString("cCity");
                    String cGreekUserOrInvestorName = jsonObject.getString("cGreekUserOrInvestorName");
                    String cPanNo = jsonObject.getString("cPanNo");
                    String lPinCode = jsonObject.getString("lPinCode");
                    String cAddress = jsonObject.getString("cAddress");
                    String lMobileNo = jsonObject.getString("lMobileNo");
                    String bankName = jsonObject.getString("bankName");
                    String ifscCode = jsonObject.getString("ifscCode");
                    String bankAcNo = jsonObject.getString("bankAcNo");
                    String cEmailId = jsonObject.getString("cEmailId");

                    AccountDetails.setcCity(cCity);
                    AccountDetails.setcEmailId(cEmailId);
                    AccountDetails.setcGreekUserOrInvestorName(cGreekUserOrInvestorName);
                    AccountDetails.setcPanNo(cPanNo);
                    AccountDetails.setlPinCode(lPinCode);
                    AccountDetails.setcAddress(cAddress);
                    AccountDetails.setlMobileNo(lMobileNo);

                    AccountDetails.setBankName(bankName);
                    AccountDetails.setIfscCode(ifscCode);
                    AccountDetails.setBankAcNo(bankAcNo);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }
}



