package com.acumengroup.mobile.reports;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripRequest;
import com.acumengroup.greekmain.core.network.EDISHoldingInfoResponse;
import com.acumengroup.greekmain.core.network.EDISHoldingInfoResponse1;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.EdisPledgeWebContentNSDL;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.WebContentPledgeCDSL;
import com.acumengroup.mobile.model.AuthTransectionModel;
import com.acumengroup.mobile.model.CDSLReturnResponse;
import com.acumengroup.mobile.model.SendAuthorizationResponse;
import com.acumengroup.mobile.model.UpdateNSDLAuthorizationResponse;
import com.acumengroup.mobile.model.sendAuthorizationRequest_NSDLResponse;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;


public class EdisMarginPledgeReport extends GreekBaseFragment {


    private OrderStreamingController orderStreamingController;
    public ArrayList<AuthTransectionModel.AgeingTransData> edisHoldingdatalist;
    private RecyclerView sell_transection_list;
    private HorizontalScrollView horizonscroll;
    private GreekTextView empty_view, txt_ttlPNL, dtotal;
    private ArrayList<EDISHoldingInfoResponse1.Response.Data.StockDetail> authorizeTransDataList;
    private ArrayList<String> checkedÌlist;
    private boolean cdslValidation = true;
    private String cdslValidationMassage = "Enter valid Qty to Authorize";
    private String reqType;
    private ServiceResponseHandler serviceResponseHandler;
    private Spinner pledge_spinner;
    private LinearLayout edis_parent_layout, dashboard_bg;
    private GreekButton prc_to_cdsl;
    private View view;
    private ImageView sort_btn;
    private GreekTextView txt_report;
    //    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<EDISHoldingInfoResponse1.Response.Data.StockDetail> stockDetailArrayList;
    private int K = 0;


    public EdisMarginPledgeReport() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);
        checkedÌlist = new ArrayList<>();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_edis_margin_pledge_report).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_edis_margin_pledge_report).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_EDIS_MARGIN;
        orderStreamingController = new OrderStreamingController();
        LinearLayoutManager linearLayOutExeption = new LinearLayoutManager(getMainActivity(), LinearLayoutManager.VERTICAL, false);
        sell_transection_list = view.findViewById(R.id.recyclerView);
        horizonscroll = view.findViewById(R.id.horizonscroll);
        edis_parent_layout = view.findViewById(R.id.edis_parent_layout);
        empty_view = view.findViewById(R.id.txt_error_msg);
        txt_report = view.findViewById(R.id.txt_report);
        prc_to_cdsl = view.findViewById(R.id.prc_to_cdsl);
        dtotal = view.findViewById(R.id.dtotal);
        pledge_spinner=view.findViewById(R.id.dpledge_spinner);
        sell_transection_list.setLayoutManager(linearLayOutExeption);
        sell_transection_list.setHasFixedSize(true);
        txt_ttlPNL = view.findViewById(R.id.cost_report_txt_ttlPNL);
        dashboard_bg = view.findViewById(R.id.dashboard_bg);
        sort_btn = view.findViewById(R.id.sort_btn);
        prc_to_cdsl.setEnabled(false);
        prc_to_cdsl.setBackground(getResources().getDrawable(R.drawable.single_line_border_bajaj_gray));


//        EPledgeHoldingInfo request is send to Iris server
        sendRequestForhHolding();

        setTheme();

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
        Calendar cal = Calendar.getInstance();
        txt_ttlPNL.setText(dateFormat.format(cal.getTime()));

        final ArrayList pledgeList= new ArrayList();
        pledgeList.add("Normal Margin Pledge");
        pledgeList.add("MTF Pledge");
        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");


        ArrayAdapter acctNoSpinAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), pledgeList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")){
                    v.setTextColor(getResources().getColor(R.color.black));
                }else{
                    v.setTextColor(getResources().getColor(R.color.white));
                }
                v.setPadding(15, 15, 15, 15);
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };
        acctNoSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
        pledge_spinner.setAdapter(acctNoSpinAdapter);

        pledge_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reqType=  pledgeList.get(i).toString();
                if (reqType.equalsIgnoreCase("MTF Pledge")){
                    reqType="MTF";
                }else{
                    reqType="NORMAL";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        view.findViewById(R.id.prc_to_cdsl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<EDISHoldingInfoResponse1.Response.Data.StockDetail> selecteddatalist = new ArrayList<>();

                if (authorizeTransDataList != null) {
                    for (int i = 0; i < authorizeTransDataList.size(); i++) {
                        if (authorizeTransDataList.get(i).isChekbox()) {

                            if (authorizeTransDataList.get(i).getSellmarket().isEmpty()) {
                                cdslValidation = false;
                                cdslValidationMassage = "Enter quantity should be between one to free available quantity for selected contract";
                                break;

                            } else if (Integer.parseInt(authorizeTransDataList.get(i).getSellmarket()) <= 0) {
                                cdslValidation = false;
                                cdslValidationMassage = "Enter quantity should be between one to free available quantity for selected contract";
                                break;

                            } else if (Integer.parseInt(authorizeTransDataList.get(i).getSellmarket()) >
                                    Integer.parseInt(authorizeTransDataList.get(i).getFreeQTY())) {

                                cdslValidation = false;
                                cdslValidationMassage = "Enter quantity should be between one to free available quantity for selected contract";
                                break;

                            } else {
                                cdslValidation = true;
                                selecteddatalist.add(authorizeTransDataList.get(i));
                            }
                        }
                    }

                    if (!cdslValidation) {

                        GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, cdslValidationMassage, getString(R.string.GREEK_OK));
                        return;
                    }

                    if (selecteddatalist.size() > 0) {

                        if (selecteddatalist.size() > 35) {

                            GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, "Selection of more then 35 stock not allowed in single request", getString(R.string.GREEK_OK));

                        } else {

                            if (cdslValidation) {

                                  for(int j=0;j<selecteddatalist.size();j++) {
                                      if (Double.parseDouble(selecteddatalist.get(j).getLotSize()) > 1) {
                                          if (Double.parseDouble(selecteddatalist.get(j).getSellmarket()) % Double.parseDouble(selecteddatalist.get(j).getLotSize()) != 0) {
                                              GreekDialog.alertDialog(getActivity(), 0, GREEK, "Entered Quantity is not in a multiple of Lot Size (" + selecteddatalist.get(j).getLotSize() + ") For " + selecteddatalist.get(j).getSymbol(), "OK", false, null);
                                              return;
                                          }
                                      }
                                  }
                                showProgress();
                                if (AccountDetails.getDPType().equalsIgnoreCase("NSDL")){
                                    sendAuthorizationRequestNSDL(selecteddatalist);
                                }else{
//                                    false means new
//                                    true means old
                                    if(AccountDetails.getPledgeOffline().equalsIgnoreCase("false")){
                                        sendAuthorizationRequestCDSLNew(selecteddatalist);
                                    }else {
                                        sendAuthorizationRequestCDSLoldFlow(selecteddatalist);
                                    }
                                }



                            } else {
                                GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, cdslValidationMassage, getString(R.string.GREEK_OK));

                            }
                        }


                    } else {
                        if (cdslValidation) {
                            GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, "Please select stock to proceed", getString(R.string.GREEK_OK));

                        } else {
                            GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, cdslValidationMassage, getString(R.string.GREEK_OK));
                        }


                    }
                }
            }
        });


        return view;
    }



    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            edis_parent_layout.setBackgroundColor(getResources().getColor(R.color.white));
            empty_view.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dashboard_bg.setBackgroundColor(getResources().getColor(R.color.white));
            dtotal.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_report.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sort_btn.setBackgroundColor(getResources().getColor(R.color.white));
            sort_btn.setBackground(getResources().getDrawable(R.drawable.ic_filter));

//            prc_to_cdsl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            prc_to_cdsl.setBackgroundColor(getResources().getColor(R.color.buttonColor));



        }
    }

    public void sendRequestForhHolding() {
//        EPledgeHoldingInfo request is send to Iris server
        if (AccountDetails.isIsIrisConnected()) {
            orderStreamingController.sendEPledgeHoldingInfoRequest(getContext(),
                    AccountDetails.getSessionId(getMainActivity()), AccountDetails.getUsername(getMainActivity()));
        } else {
            EventBus.getDefault().post("Socket IRIS Reconnect Attempts exceeds");
        }
    }

    private void sendAuthorizationRequestCDSLNew(ArrayList<EDISHoldingInfoResponse1.Response.Data.StockDetail> selecteddatalist) {
        String gscid = AccountDetails.getUsername(getMainActivity());

        String deviceId = Settings.Secure.getString(getMainActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        String BOID=AccountDetails.getPOADPId();
//        String BOID="1207420000022698";

        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < selecteddatalist.size(); i++) {
                JSONObject jsonobj = new JSONObject();
                  jsonobj.put("token", selecteddatalist.get(i).getToken());

          /*      int freeQTY = (Integer.parseInt(selecteddatalist.get(i).getHQty())) -
                        (Integer.parseInt(selecteddatalist.get(i).getOpenAuthQty()) +
                                Integer.parseInt(selecteddatalist.get(i).getTodayAuthQty())); */
                double Dvalue = Integer.parseInt(selecteddatalist.get(i).getSellmarket()) *
                        Double.parseDouble(selecteddatalist.get(i).getRate());

                jsonobj.put("FreeQty", selecteddatalist.get(i).getFreeQTY());
                jsonobj.put("isin", selecteddatalist.get(i).getIsin());
                jsonobj.put("quantity", selecteddatalist.get(i).getSellmarket());
                jsonobj.put("value", Dvalue);

                jsonArray.put(jsonobj);

            }

            MarketsSingleScripRequest.sendRequestPledgeNewFlow(AccountDetails.getDPType(),gscid, deviceId,reqType,BOID,jsonArray, getMainActivity(), serviceResponseHandler);

        } catch (JSONException e) {
            Log.e("Json error", e.toString());
        }

    }


    private void sendAuthorizationRequestCDSLoldFlow(ArrayList<EDISHoldingInfoResponse1.Response.Data.StockDetail> selecteddatalist) {

        String gscid = AccountDetails.getUsername(getMainActivity());

        String deviceId = Settings.Secure.getString(getMainActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        String BOID=AccountDetails.getPOADPId();
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < selecteddatalist.size(); i++) {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("isin", selecteddatalist.get(i).getIsin());
                jsonobj.put("token", selecteddatalist.get(i).getToken());

          /*      int freeQTY = (Integer.parseInt(selecteddatalist.get(i).getHQty())) -
                        (Integer.parseInt(selecteddatalist.get(i).getOpenAuthQty()) +
                                Integer.parseInt(selecteddatalist.get(i).getTodayAuthQty()));
                double Dvalue = Integer.parseInt(selecteddatalist.get(i).getHQty()) *
                        Double.parseDouble(selecteddatalist.get(i).getClose());*/

                jsonobj.put("FreeQty", selecteddatalist.get(i).getFreeQTY());
                jsonobj.put("description", selecteddatalist.get(i).getSymbol());
                jsonobj.put("instrument", selecteddatalist.get(i).getInstrument());
                jsonobj.put("PledgeQty", selecteddatalist.get(i).getSellmarket());
                jsonobj.put("PledgeVal", selecteddatalist.get(i).getValue());
                jsonobj.put("PledgeRate", selecteddatalist.get(i).getRate());
                if (AccountDetails.getDPType().equalsIgnoreCase("NSDL")){
                    jsonobj.put("lockInReasonCode", " ");
                    jsonobj.put("lockInReleaseDate", " ");
                }



                jsonArray.put(jsonobj);

            }

            MarketsSingleScripRequest.sendRequestPledge(gscid, deviceId,reqType,BOID,jsonArray, getMainActivity(), serviceResponseHandler);

        } catch (JSONException e) {
            Log.e("Json error", e.toString());
        }


    }

    private void sendAuthorizationRequestNSDL(ArrayList<EDISHoldingInfoResponse1.Response.Data.StockDetail> selecteddatalist) {

        String gscid = AccountDetails.getUsername(getMainActivity());

        String deviceId = Settings.Secure.getString(getMainActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
         String dPType = "NSDL";
        String requestIdentifier = "S";
        String RU = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":"
                + AccountDetails.getArachne_Port() + "/getEDISAuthorizationResponseMobile";
        String BOID=AccountDetails.getPOADPId();
        String channel="WEB";
        String Segment="00";
        String ExID="01";


        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < selecteddatalist.size(); i++) {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("ISIN", selecteddatalist.get(i).getIsin());
                jsonobj.put("token", selecteddatalist.get(i).getToken());

          /*      int freeQTY = (Integer.parseInt(selecteddatalist.get(i).getHQty())) -
                        (Integer.parseInt(selecteddatalist.get(i).getOpenAuthQty()) +
                                Integer.parseInt(selecteddatalist.get(i).getTodayAuthQty()));
                double Dvalue = Integer.parseInt(selecteddatalist.get(i).getHQty()) *
                        Double.parseDouble(selecteddatalist.get(i).getClose());*/

                jsonobj.put("FreeQty", selecteddatalist.get(i).getFreeQTY());
                jsonobj.put("description", selecteddatalist.get(i).getSymbol());
                jsonobj.put("instrument", selecteddatalist.get(i).getInstrument());
                jsonobj.put("Quantity", selecteddatalist.get(i).getSellmarket());
                jsonobj.put("lockInReasonCode", "");
                jsonobj.put("lockInReleaseDate", "");

                jsonArray.put(jsonobj);

            }

            MarketsSingleScripRequest.sendRequestPledgeNSDL(RU,requestIdentifier,dPType,channel,Segment,ExID,gscid, deviceId,reqType,BOID,jsonArray, getMainActivity(), serviceResponseHandler);

        } catch (JSONException e) {
            Log.e("Json error", e.toString());
        }


    }

    public void onEventMainThread(EDISHoldingInfoResponse streamingResponse) {
        try {
            ArrayList<EDISHoldingInfoResponse.Response.Data.StockDetail> stockArrayList =
                    streamingResponse.getResponse().getData().getStockDetails();

            int noofrecords = Integer.parseInt(streamingResponse.getResponse().getData().getNoofrecords());
            int islast = Integer.parseInt(streamingResponse.getResponse().getData().getIslast());

//            Log.e("EDISDashboard", "noofrecords====>>" + noofrecords);
//            Log.e("EDISDashboard", "islast====>>" + islast);

            if (islast == 1 || stockArrayList.size() == noofrecords) {
                // Vanish previous records and show current records ( this is the 1st package )

                if (noofrecords > 0) {
                    stockDetailArrayList = new ArrayList<>();

                    for (int i = 0; i < stockArrayList.size(); i++) {

                        EDISHoldingInfoResponse1.Response.Data.StockDetail ageingTransData = new EDISHoldingInfoResponse1.Response.Data.StockDetail();

                        ageingTransData.setHQty(stockArrayList.get(i).getHQty());
                        ageingTransData.setSymbol(stockArrayList.get(i).getSymbol());
                        ageingTransData.setInstrument(stockArrayList.get(i).getInstrument());
                        ageingTransData.setIsin(stockArrayList.get(i).getIsin());
                        ageingTransData.setToken(stockArrayList.get(i).getToken());
                        ageingTransData.setOpenAuthQty(stockArrayList.get(i).getOpenAuthQty());
                        ageingTransData.setTodaySoldQty(stockArrayList.get(i).getTodaySoldQty());
                        ageingTransData.setTodayAuthQty(stockArrayList.get(i).getTodayAuthQty());
                        ageingTransData.setClose(stockArrayList.get(i).getClose());
                        ageingTransData.setFreeQTY(stockArrayList.get(i).getFreeQty());
                        ageingTransData.setPledgedQty(stockArrayList.get(i).getPledgedQty());
                        ageingTransData.setRate(stockArrayList.get(i).getRate());
                        ageingTransData.setValue(stockArrayList.get(i).getValue());
                        ageingTransData.setChekbox(false);
                        ageingTransData.setLotSize(stockArrayList.get(i).getLotSize());
                        stockDetailArrayList.add(ageingTransData);
                    }
                    Log.e("\"Total if  ", "" + stockDetailArrayList.size());

                    if (stockDetailArrayList != null && stockDetailArrayList.size() > 0) {
                        empty_view.setVisibility(View.GONE);
                        sell_transection_list.setVisibility(View.VISIBLE);
//                        swipeRefreshLayout.setVisibility(View.VISIBLE);

                        AuthorizeTransAdapter adapter = new AuthorizeTransAdapter(getMainActivity(), stockDetailArrayList);
                        ((SimpleItemAnimator) sell_transection_list.getItemAnimator()).setSupportsChangeAnimations(false);
                        sell_transection_list.setAdapter(adapter);

                    } else {
                        empty_view.setVisibility(View.VISIBLE);
                        sell_transection_list.setVisibility(View.GONE);
//                        swipeRefreshLayout.setVisibility(View.GONE);
                    }
                } else {
                    empty_view.setVisibility(View.VISIBLE);
                    sell_transection_list.setVisibility(View.GONE);
//                    swipeRefreshLayout.setVisibility(View.GONE);
                }

            } else {

                //Appends new records with previous already exists records.

                if (stockDetailArrayList != null) {
                    for (int i = 0; i < stockArrayList.size(); i++) {

                        EDISHoldingInfoResponse1.Response.Data.StockDetail ageingTransData = new EDISHoldingInfoResponse1.Response.Data.StockDetail();

                        ageingTransData.setHQty(stockArrayList.get(i).getHQty());
                        ageingTransData.setSymbol(stockArrayList.get(i).getSymbol());
                        ageingTransData.setInstrument(stockArrayList.get(i).getInstrument());
                        ageingTransData.setIsin(stockArrayList.get(i).getIsin());
                        ageingTransData.setToken(stockArrayList.get(i).getToken());
                        ageingTransData.setOpenAuthQty(stockArrayList.get(i).getOpenAuthQty());
                        ageingTransData.setTodaySoldQty(stockArrayList.get(i).getTodaySoldQty());
                        ageingTransData.setTodayAuthQty(stockArrayList.get(i).getTodayAuthQty());
                        ageingTransData.setClose(stockArrayList.get(i).getClose());
                        ageingTransData.setFreeQTY(stockArrayList.get(i).getFreeQty());
                        ageingTransData.setPledgedQty(stockArrayList.get(i).getPledgedQty());
                        ageingTransData.setRate(stockArrayList.get(i).getRate());
                        ageingTransData.setValue(stockArrayList.get(i).getValue());
                        ageingTransData.setChekbox(false);
                        ageingTransData.setLotSize(stockArrayList.get(i).getLotSize());
                        stockDetailArrayList.add(ageingTransData);
                    }
                }


                if (stockDetailArrayList != null && stockDetailArrayList.size() > 0) {
                    empty_view.setVisibility(View.GONE);
                    sell_transection_list.setVisibility(View.VISIBLE);
//                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    Log.e("Total else ", "" + stockDetailArrayList.size());
                    AuthorizeTransAdapter adapter = new AuthorizeTransAdapter(getMainActivity(), stockDetailArrayList);
                    ((SimpleItemAnimator) sell_transection_list.getItemAnimator()).setSupportsChangeAnimations(false);
                    sell_transection_list.setAdapter(adapter);
//                    sell_transection_list.notifyAll();
//                    adapter.notifyDataSetChanged();

                } else {
                    empty_view.setVisibility(View.VISIBLE);
                    sell_transection_list.setVisibility(View.GONE);
                    //                   swipeRefreshLayout.setVisibility(View.GONE);
                }


            }


        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);
        JSONResponse jsonResponse = (JSONResponse) response;
        hideProgress();



        if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SEND_PLEDGE_DASHBOARD.equals(jsonResponse.getServiceName())) {
            try {


                String message = jsonResponse.getResponseData().getString("Message");
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View poaDialogView = factory.inflate(R.layout.poapopup_layout, null);
                final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setCancelable(false);
                dialog.setView(poaDialogView);
                GreekButton btn_proceed=poaDialogView.findViewById(R.id.btn_proceed);
                GreekButton btn_cancel=poaDialogView.findViewById(R.id.btn_cancel);
                GreekTextView dmessage=poaDialogView.findViewById(R.id.dmessage);
                LinearLayout parent_layout=poaDialogView.findViewById(R.id.parent_layout);
                ImageView poalogo=poaDialogView.findViewById(R.id.poalogo);

                String packagname =  getActivity().getPackageName();
                if (packagname.equalsIgnoreCase("com.vishwas.mobile")){

                    if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                        poalogo.setBackground(getResources().getDrawable(R.drawable.vishwas_action_top_bar_blue));
                    }else{
                        poalogo.setBackground(getResources().getDrawable(R.drawable.vishwas_action_top_bar_white));
                    }
                }else  if (packagname.equalsIgnoreCase("com.tradeongo.mobile")){
                    poalogo.setBackground(getResources().getDrawable(R.drawable.philips_login_icon));
                }else  if (packagname.equalsIgnoreCase("com.mandot.mobile")){
                    poalogo.setBackground(getResources().getDrawable(R.mipmap.mandot_login_icon));
                }else  if (packagname.equalsIgnoreCase("com.msfl.mobile")){
                    poalogo.setBackground(getResources().getDrawable(R.drawable.marwadi_login_icon));
                }else  if (packagname.equalsIgnoreCase("com.clicktrade.mobile")){
                    poalogo.setBackground(getResources().getDrawable(R.drawable.cliqtrade_actionbar));
//                    poalogo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams.gravity= Gravity.CENTER;
//                    poalogo.setLayoutParams(layoutParams);
                }

                dmessage.setText(message);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    parent_layout.setBackgroundColor(getResources().getColor(R.color.white));
                    dmessage.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
                btn_cancel.setVisibility(View.GONE);
                btn_proceed.setText("OK");
                btn_proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendRequestForhHolding();
//                        Post API sendEPledgeRequest request is send to Iris server
                        dtotal.setText("Total 0.00");
                        dialog.dismiss();

                    }
                });
                dialog.show();



            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && PlEDGE_CDSL_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                Gson gson = new Gson();
                SendAuthorizationResponse sendAuthorizationResponse = gson.fromJson(String.valueOf(response), SendAuthorizationResponse.class);

                Intent intent = new Intent(getMainActivity(), WebContentPledgeCDSL.class);
                intent.putExtra("DPId", sendAuthorizationResponse.getResponse().getData().getDPId());
                intent.putExtra("ReqId", sendAuthorizationResponse.getResponse().getData().getReqId());
                intent.putExtra("Version", sendAuthorizationResponse.getResponse().getData().getVersion());
                intent.putExtra("TransDtls", sendAuthorizationResponse.getResponse().getData().getResponse());
                intent.putExtra("Url", sendAuthorizationResponse.getResponse().getData().getUrl());
                startActivityForResult(intent, 40);
                AccountDetails.setIsMainActivity(true);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if(MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SEND_PLEDGE_REPORT.equals(jsonResponse.getServiceName())){
            try {
                Gson gson = new Gson();
                sendAuthorizationRequest_NSDLResponse sendAuthorizationResponse = gson.fromJson(String.valueOf(response), sendAuthorizationRequest_NSDLResponse.class);

                Intent intent = new Intent(getMainActivity(), EdisPledgeWebContentNSDL.class);
                intent.putExtra("MPIRqst", sendAuthorizationResponse.getResponse().getData().getUrl());
                intent.putExtra("transactionType", sendAuthorizationResponse.getResponse().getData().getTransactionType());
                intent.putExtra("requestor", sendAuthorizationResponse.getResponse().getData().getRequestor());
                intent.putExtra("requestorId", sendAuthorizationResponse.getResponse().getData().getRequestorId());
                intent.putExtra("requestReference", sendAuthorizationResponse.getResponse().getData().getRequestReference());
                intent.putExtra("channel", sendAuthorizationResponse.getResponse().getData().getChannel());
                intent.putExtra("orderReqDtls", sendAuthorizationResponse.getResponse().getData().getOrderReqDtls());
                intent.putExtra("requestTime", sendAuthorizationResponse.getResponse().getData().getRequestTime());
                intent.putExtra("digitalSignature", sendAuthorizationResponse.getResponse().getData().getSignature());
                startActivityForResult(intent, 41);
                AccountDetails.setIsMainActivity(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{

        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AccountDetails.setIsMainActivity(false);
        if (requestCode == 41 && data != null) {



            String messageNSDL = data.getStringExtra("NSDLResult");
            //NSDL Flow

            Gson gson = new Gson();
            UpdateNSDLAuthorizationResponse edisHoldingInfoResponse = gson.fromJson(messageNSDL, UpdateNSDLAuthorizationResponse.class);
            ArrayList<UpdateNSDLAuthorizationResponse.Response.Data.StockDetail> dataArrayList =
                    (ArrayList<UpdateNSDLAuthorizationResponse.Response.Data.StockDetail>) edisHoldingInfoResponse.getResponse().getData().getStockDetails();

            if (edisHoldingInfoResponse.getResponse().getData().getStockDetails().size()>0) {

                try {
                    JSONObject symbols = new JSONObject();
                    JSONArray jsonArray = new JSONArray();

                    for (int i = 0; i < dataArrayList.size(); i++) {

                        if (dataArrayList.get(i).getStatus().equalsIgnoreCase("success")) {
                            JSONObject jsonobj = new JSONObject();
                            jsonobj.put("token", dataArrayList.get(i).getToken());
                            jsonobj.put("Qty", dataArrayList.get(i).getQuantity());
                            jsonobj.put("Isin", dataArrayList.get(i).getISIN());
                            jsonobj.put("Status", dataArrayList.get(i).getStatus());
                            jsonobj.put("ReqType", "");
                            jsonobj.put("ReqIdentifier", "");
                            jsonobj.put("TxnId", dataArrayList.get(i).getTxnId());

                            jsonArray.put(jsonobj);
                        }
                    }

                    symbols.put("symbols", jsonArray);


//                    if (jsonArray.length() > 0) {
//                        orderStreamingController.sendCDSLTransferDetailsRequest(getContext(),
//                                symbols, "",
//                                AccountDetails.getUsername(getContext()), AccountDetails.getSessionId(getContext()));
//                    }


                } catch (JSONException e) {
                    Log.e("Json error", e.toString());
                }
                if (dataArrayList.size() > 0) {

                    showAuthorizeTransectionReturnnResponseNSDL(dataArrayList);
                } else {
                    GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, "NO Records Found", getString(R.string.GREEK_OK));
                }
            } else {
                GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, "Transaction Failed", getString(R.string.GREEK_OK));
            }



        }
        else  if(resultCode==40 && data != null){
            // CDSL flow
            String message = data.getStringExtra("Result");

            org.jsoup.nodes.Document doc1 = Jsoup.parse(message);
            Elements type1 = doc1.select("pre");

            String response = type1.first().html();
            Gson gson = new Gson();
            CDSLReturnResponse cdslReturnResponse = gson.fromJson(String.valueOf(response), CDSLReturnResponse.class);
            ArrayList<CDSLReturnResponse.Response.Data.StockDetail> dataArrayList = cdslReturnResponse.getResponse().getData().getStockDetails();

            if (cdslReturnResponse.getResponse().getErrorCode().equalsIgnoreCase("0")) {

                try {
                    JSONObject symbols = new JSONObject();
                    JSONArray jsonArray = new JSONArray();

                    for (int i = 0; i < dataArrayList.size(); i++) {

                        if (dataArrayList.get(i).getStatus().equalsIgnoreCase("success")) {
                            JSONObject jsonobj = new JSONObject();
                            jsonobj.put("token", dataArrayList.get(i).getToken());
                            jsonobj.put("Qty", dataArrayList.get(i).getQuantity());
                            jsonobj.put("Isin", dataArrayList.get(i).getISIN());
                            jsonobj.put("Status", dataArrayList.get(i).getStatus());
                            jsonobj.put("ReqType", dataArrayList.get(i).getReqType());
                            jsonobj.put("ReqIdentifier", dataArrayList.get(i).getReqIdentifier());
                            jsonobj.put("TxnId", dataArrayList.get(i).getTxnId());

                            jsonArray.put(jsonobj);
                        }
                    }

                    symbols.put("symbols", jsonArray);


                    if (jsonArray.length() > 0) {
                        orderStreamingController.sendCDSLTransferDetailsRequest(getContext(),
                                symbols, cdslReturnResponse.getResponse().getData().getReqId(),
                                AccountDetails.getUsername(getContext()), AccountDetails.getSessionId(getContext()));
                    }


                } catch (JSONException e) {
                    Log.e("Json error", e.toString());
                }
                if (dataArrayList.size() > 0) {

                    showAuthorizeTransectionReturnnResponse(dataArrayList);
                } else {
                    GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, "NO Records Found", getString(R.string.GREEK_OK));
                }
            } else {
                GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, "Transaction Failed", getString(R.string.GREEK_OK));
            }
        }
        else {
            GreekDialog.alertDialogOnly(getContext(), GreekBaseActivity.GREEK, "Transaction cancelled ", getString(R.string.GREEK_OK));
        }

    }

    private void showAuthorizeTransectionReturnnResponse(ArrayList<CDSLReturnResponse.Response.Data.StockDetail> returnlist) {

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View authDialogView = factory.inflate(R.layout.authorize_trancection_status, null);
        final androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity()).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(authDialogView);
        //743748
        LinearLayoutManager linearLayOutExeption = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView sell_transection_list = authDialogView.findViewById(R.id.sell_transection_Return_list);
        LinearLayout parentLayout = authDialogView.findViewById(R.id.parentLayout);
        GreekTextView txt_header = authDialogView.findViewById(R.id.txt_header);
        GreekTextView instrument_txt = authDialogView.findViewById(R.id.instrument_txt);
        GreekTextView qty_txt = authDialogView.findViewById(R.id.qty_txt);
        GreekTextView status_txt = authDialogView.findViewById(R.id.status_txt);
        GreekTextView empty_view = authDialogView.findViewById(R.id.empty_view);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            txt_header.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            instrument_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            status_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            empty_view.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            parentLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }


        sell_transection_list.setLayoutManager(linearLayOutExeption);
        sell_transection_list.setHasFixedSize(true);

        final EdisMarginPledgeReport.AuthorizeTransStatusAdapter adapter = new EdisMarginPledgeReport.AuthorizeTransStatusAdapter(getActivity(), returnlist);
        ((SimpleItemAnimator) sell_transection_list.getItemAnimator()).setSupportsChangeAnimations(false);
        sell_transection_list.setAdapter(adapter);


        authDialogView.findViewById(R.id.prc_to_cdsl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (stockDetailArrayList != null) {
                    stockDetailArrayList.clear();
                }
                sendRequestForhHolding();
                // sendAuthorizationRequest request is send to Iris server and navigate to WebContentCDSL page for verification.

                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public class AuthorizeTransStatusAdapter extends RecyclerView.Adapter<EdisMarginPledgeReport.AuthorizeTransStatusAdapter.MyViewHolder> {

        Context context;
        ArrayList<CDSLReturnResponse.Response.Data.StockDetail> authorizeTransDataList;

        public AuthorizeTransStatusAdapter(FragmentActivity activity, ArrayList<CDSLReturnResponse.Response.Data.StockDetail> authorizeTransModels) {
            this.context = activity;
            authorizeTransDataList = authorizeTransModels;
        }

        @Override
        public EdisMarginPledgeReport.AuthorizeTransStatusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.authorize_trans_status_data,  parent,false);
            View itemView = LayoutInflater.from(context).inflate(R.layout.authorize_trans_status_data, parent, false);
            return new EdisMarginPledgeReport.AuthorizeTransStatusAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final EdisMarginPledgeReport.AuthorizeTransStatusAdapter.MyViewHolder holder, final int position) {
            final CDSLReturnResponse.Response.Data.StockDetail optionChainData = authorizeTransDataList.get(position);

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                holder.symbole_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.qty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.status_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.layoutParent.setBackgroundColor(getResources().getColor(R.color.white));
            }


            if (!optionChainData.getSymbol().isEmpty() && optionChainData.getSymbol() != null) {
                holder.symbole_txt.setText(optionChainData.getSymbol());
            } else {
                holder.symbole_txt.setText("");
            }

            holder.qty_txt.setText(optionChainData.getQuantity());

            if (!optionChainData.getStatus().isEmpty() && optionChainData.getStatus() != null) {
                holder.status_txt.setText(optionChainData.getStatus());
            } else {
                holder.status_txt.setText("Failed");
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return authorizeTransDataList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public GreekTextView symbole_txt, qty_txt, status_txt;
            public LinearLayout layoutParent;

            public MyViewHolder(View view) {
                super(view);

                symbole_txt = view.findViewById(R.id.instrument_txt);
                qty_txt = view.findViewById(R.id.qty_txt);
                status_txt = view.findViewById(R.id.status_txt);
                layoutParent = view.findViewById(R.id.layoutParent);


            }
        }


    }


    private void showAuthorizeTransectionReturnnResponseNSDL(ArrayList<UpdateNSDLAuthorizationResponse.Response.Data.StockDetail> dataArrayList) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View authDialogView = factory.inflate(R.layout.authorize_trancection_status, null);
        final androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(getActivity()).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(authDialogView);
        //743748
        LinearLayoutManager linearLayOutExeption = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView sell_transection_list = authDialogView.findViewById(R.id.sell_transection_Return_list);
        LinearLayout parentLayout = authDialogView.findViewById(R.id.parentLayout);
        GreekTextView txt_header = authDialogView.findViewById(R.id.txt_header);
        GreekTextView instrument_txt = authDialogView.findViewById(R.id.instrument_txt);
        GreekTextView qty_txt = authDialogView.findViewById(R.id.qty_txt);
        GreekTextView status_txt = authDialogView.findViewById(R.id.status_txt);
        GreekTextView empty_view = authDialogView.findViewById(R.id.empty_view);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            txt_header.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            instrument_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            status_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            empty_view.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            parentLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }


        sell_transection_list.setLayoutManager(linearLayOutExeption);
        sell_transection_list.setHasFixedSize(true);

        final EdisMarginPledgeReport.AuthorizeTransStatusAdapterNSDL adapter = new EdisMarginPledgeReport.AuthorizeTransStatusAdapterNSDL(getActivity(), dataArrayList);
        ((SimpleItemAnimator) sell_transection_list.getItemAnimator()).setSupportsChangeAnimations(false);
        sell_transection_list.setAdapter(adapter);


        authDialogView.findViewById(R.id.prc_to_cdsl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (stockDetailArrayList != null) {
                    stockDetailArrayList.clear();
                }

                sendRequestForhHolding();
                // sendAuthorizationRequest request is send to Iris server and navigate to WebContentCDSL page for verification.

                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public class AuthorizeTransStatusAdapterNSDL extends RecyclerView.Adapter<EdisMarginPledgeReport.AuthorizeTransStatusAdapterNSDL.MyViewHolder> {

        Context context;
        ArrayList<UpdateNSDLAuthorizationResponse.Response.Data.StockDetail> authorizeTransDataList;

        public AuthorizeTransStatusAdapterNSDL(FragmentActivity activity, ArrayList<UpdateNSDLAuthorizationResponse.Response.Data.StockDetail> authorizeTransModels) {
            this.context = activity;
            authorizeTransDataList = authorizeTransModels;
        }

        @Override
        public EdisMarginPledgeReport.AuthorizeTransStatusAdapterNSDL.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.authorize_trans_status_data,  parent,false);
            View itemView = LayoutInflater.from(context).inflate(R.layout.authorize_trans_status_data, parent, false);
            return new EdisMarginPledgeReport.AuthorizeTransStatusAdapterNSDL.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final EdisMarginPledgeReport.AuthorizeTransStatusAdapterNSDL.MyViewHolder holder, final int position) {
            final UpdateNSDLAuthorizationResponse.Response.Data.StockDetail optionChainData = authorizeTransDataList.get(position);

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                holder.symbole_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.qty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.status_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.layoutParent.setBackgroundColor(getResources().getColor(R.color.white));
            }


            if (!optionChainData.getSymbol().isEmpty() && optionChainData.getSymbol() != null) {
                holder.symbole_txt.setText(optionChainData.getSymbol());
            } else {
                holder.symbole_txt.setText("");
            }

            holder.qty_txt.setText(optionChainData.getQuantity());

            if (!optionChainData.getStatus().isEmpty() && optionChainData.getStatus() != null) {
                holder.status_txt.setText(optionChainData.getStatus());
            } else {
                holder.status_txt.setText("Failed");
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return authorizeTransDataList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public GreekTextView symbole_txt, qty_txt, status_txt;
            public LinearLayout layoutParent;

            public MyViewHolder(View view) {
                super(view);

                symbole_txt = view.findViewById(R.id.instrument_txt);
                qty_txt = view.findViewById(R.id.qty_txt);
                status_txt = view.findViewById(R.id.status_txt);
                layoutParent = view.findViewById(R.id.layoutParent);


            }
        }


    }

    public class AuthorizeTransAdapter extends RecyclerView.Adapter<AuthorizeTransAdapter.MyViewHolder> {

        Context context;
        private final int minDelta = 300;           // threshold in ms
        private long focusTime = 0;                 // time of last touch
        private View focusTarget = null;
        private double total=0;

        public AuthorizeTransAdapter(Context activity, ArrayList<EDISHoldingInfoResponse1.Response.Data.StockDetail> authorizeTransModels) {
            this.context = activity;
            authorizeTransDataList = authorizeTransModels;
            checkedÌlist = new ArrayList<>();
        }

        public void checkALlStock(boolean Checkflag) {

            if (Checkflag) {

                for (int i = 0; i < authorizeTransDataList.size(); i++) {
                    authorizeTransDataList.get(i).setChekbox(true);

                }

            } else {
                for (int i = 0; i < authorizeTransDataList.size(); i++) {
                    authorizeTransDataList.get(i).setChekbox(false);

                }
            }
            notifyDataSetChanged();
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.margin_row_items, parent, false);
            return new MyViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final EDISHoldingInfoResponse1.Response.Data.StockDetail optionChainData = authorizeTransDataList.get(position);

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                holder.margin_rowbg.setBackgroundColor(getResources().getColor(R.color.white));
                holder.dscript_name.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.dfree_qty.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.drate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.dvalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.disin.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.dsell_market.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.dsell_market.getBackground().setColorFilter(getResources().getColor(R.color.black),
                        PorterDuff.Mode.SRC_ATOP);
                holder.view1.setBackgroundColor(getResources().getColor(R.color.black));
                holder.view2.setBackgroundColor(getResources().getColor(R.color.black));
                holder.view3.setBackgroundColor(getResources().getColor(R.color.black));
                holder.view4.setBackgroundColor(getResources().getColor(R.color.black));
                holder.view5.setBackgroundColor(getResources().getColor(R.color.black));
                holder.view6.setBackgroundColor(getResources().getColor(R.color.black));
                holder.view7.setBackgroundColor(getResources().getColor(R.color.black));

            }


            holder.authrize_checkbox.setChecked(optionChainData.isChekbox());
            holder.authrize_checkbox.setOnCheckedChangeListener(null);


            if (!optionChainData.getSymbol().isEmpty() && optionChainData.getSymbol() != null) {
                holder.dscript_name.setText(optionChainData.getSymbol());
            } else {
                holder.dscript_name.setText("");
            }

            /*int balaceQTY =
                    (Integer.parseInt(optionChainData.getOpenAuthQty()) + Integer.parseInt(optionChainData.getTodayAuthQty()))
                            - Integer.parseInt(optionChainData.getTodaySoldQty());*/

            /*int freeQTY = (Integer.parseInt(optionChainData.getHQty())) -
                    (Integer.parseInt(optionChainData.getOpenAuthQty()) + Integer.parseInt(optionChainData.getTodayAuthQty()));*/
            int freeQTY = Integer.parseInt(optionChainData.getFreeQTY());


            holder.dfree_qty.setText(StringStuff.commaINRDecorator(freeQTY + ""));
            final int qty_auth_total = freeQTY;

            holder.dsell_market.setText(String.valueOf(qty_auth_total));

            View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    long t = System.currentTimeMillis();
                    long delta = t - focusTime;
                    if (hasFocus) {     // gained focus
                        if (delta > minDelta) {
                            focusTime = t;
                            focusTarget = view;
                        }
                    } else {              // lost focus
                        if (view == focusTarget) {
                            focusTarget.post(new Runnable() {   // reset focus to target
                                public void run() {
                                    focusTarget.requestFocus();
                                }
                            });
                        }
                    }
                }
            };


            holder.dsell_market.setOnFocusChangeListener(onFocusChangeListener);
            holder.disin.setText(optionChainData.getIsin());


            holder.drate.setText(optionChainData.getRate());
//            double Dvalue = Integer.parseInt(optionChainData.getHQty()) * Double.parseDouble(optionChainData.getClose());
            double Dvalue = Double.parseDouble(optionChainData.getValue());
            holder.dvalue.setText(String.format("%.2f", Dvalue));
            //  int dtotoasell = Integer.parseInt(optionChainData.getTodayAuthQty()) + Integer.parseInt(optionChainData.getOpenAuthQty());

            optionChainData.setSellmarket(String.valueOf(qty_auth_total));


            holder.dsell_market.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                    if (holder.dsell_market.getText().toString().equalsIgnoreCase("")) {
                        holder.dsell_market.setSelectAllOnFocus(false);
                    }

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }


                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {

                    optionChainData.setSellmarket(s.toString());
                    holder.dsell_market.clearFocus();
                    holder.dsell_market.setSelectAllOnFocus(false);

                }
            });


            optionChainData.setFreeQTY(String.valueOf(qty_auth_total));

            holder.dsell_market.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.dsell_market.setSelectAllOnFocus(true);

                }
            });

            holder.authrize_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    authorizeTransDataList.get(position).setChekbox(isChecked);

                    if (isChecked) {
                        checkedÌlist.add(String.valueOf(position));
                    } else {

                        checkedÌlist.remove(String.valueOf(position));

                    }
                    if (isChecked) {
                        holder.dsell_market.setEnabled(true);
//                        holder.dsell_market.requestFocus();
                        holder.dsell_market.setSelection(holder.dsell_market.getText().length());
                        holder.dsell_market.setSelectAllOnFocus(true);
                        double Dvalue = Double.parseDouble(holder.dvalue.getText().toString().trim());
                        total=total + Dvalue;
                    } else {
                        holder.dsell_market.setEnabled(false);
                        holder.dsell_market.requestFocus();
                        holder.dsell_market.clearFocus();
                        holder.dsell_market.setSelectAllOnFocus(false);
                        double Dvalue = Double.parseDouble(holder.dvalue.getText().toString().trim());
                        total=total - Dvalue;
                    }
                    dtotal.setText("Total "+String.format("%.2f",total));

                    if (checkedÌlist.size() > 0) {
                        prc_to_cdsl.setEnabled(true);
                        prc_to_cdsl.setBackground(getActivity().getResources().getDrawable(R.drawable.single_line_border_bajaj));


                    } else {
                        prc_to_cdsl.setEnabled(false);
                        prc_to_cdsl.setBackground(getResources().getDrawable(R.drawable.single_line_border_bajaj_gray));

                    }


                }
            });
        }


        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return authorizeTransDataList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public GreekTextView dscript_name, dfree_qty, drate, dvalue,disin;
            public CheckBox authrize_checkbox;
            public GreekEditText dsell_market;
            public LinearLayout margin_rowbg;
            public View view1, view2, view3, view4, view5, view6, view7;

            public MyViewHolder(View view) {
                super(view);
                view1 = view.findViewById(R.id.view1);
                view2 = view.findViewById(R.id.view2);
                view3 = view.findViewById(R.id.view3);
                view4 = view.findViewById(R.id.view4);
                view5 = view.findViewById(R.id.view5);
                view6 = view.findViewById(R.id.view6);
                view7 = view.findViewById(R.id.view7);

                margin_rowbg = view.findViewById(R.id.margin_rowbg);
                dscript_name = view.findViewById(R.id.dscript_name);
                dfree_qty = view.findViewById(R.id.dfree_qty);
                drate = view.findViewById(R.id.drate);
                dvalue = view.findViewById(R.id.dvalue);
                disin = view.findViewById(R.id.disin);

                authrize_checkbox = view.findViewById(R.id.authrize_checkbox);
                dsell_market = view.findViewById(R.id.dsell_market);
                dsell_market.setEnabled(false);


            }
        }
    }
}
