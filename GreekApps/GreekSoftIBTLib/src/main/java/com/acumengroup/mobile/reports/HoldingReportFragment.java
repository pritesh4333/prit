package com.acumengroup.mobile.reports;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.PLData;
import com.acumengroup.mobile.model.PandLReportRequest;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HoldingReportFragment extends GreekBaseFragment {

    private View view;
    private DateFormat df;
    private Spinner spinner_report, spinner_clientID;
    private TextView errorMsgLayout;
    private RecyclerView recyclerView;
    private GreekTextView txt_poa;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton sort_btn;
    private String startDate = "", endDate = "";
    private Calendar calendar;
    private MyRecyclerviewAdapter myRecyclerviewAdapter;
    private ArrayList<PLData> arrayList, tempArrayList;
    private String servicetype;
    private List<String> clientList, poaList, reportList;
    private ArrayAdapter<String> assetTypeAdapter;
    private LinearLayout layout_dpHolding, layout_BrokerHolding,linear_top_layout;
    private boolean isDPHoldingActive = true;

    public HoldingReportFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        arrayList = new ArrayList<>();
        tempArrayList = new ArrayList<>();
        clientList = new ArrayList<>();
        reportList = new ArrayList<>();
        poaList = new ArrayList<>();

        reportList.add("DP Holdings");
//        reportList.add("Broker Holdings");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        view = inflater.inflate(R.layout.fragment_holding_report, container, false);
        view= super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_holding_report).setBackgroundColor(getResources().getColor(R.color.floatingBgColor));
        } else {
            attachLayout(R.layout.fragment_holding_report).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);
        df = new SimpleDateFormat("MMM dd yyyy", Locale.US);
        spinner_clientID = view.findViewById(R.id.spinner1);
        spinner_report = view.findViewById(R.id.spinner2);
        errorMsgLayout = view.findViewById(R.id.txt_error_msg);
        myRecyclerviewAdapter = new MyRecyclerviewAdapter(getMainActivity(), tempArrayList);

        txt_poa = view.findViewById(R.id.txt_poa);
        layout_dpHolding = view.findViewById(R.id.layout_dpHolding);
        layout_BrokerHolding = view.findViewById(R.id.layout_BrokerHolding);
        linear_top_layout = view.findViewById(R.id.linear_top_layout);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        recyclerView.setAdapter(myRecyclerviewAdapter);
        swipeRefreshLayout = view.findViewById(R.id.refreshList);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        AccountDetails.currentFragment = NAV_TO_HOLDING_SCREEN;
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            recyclerView.setBackgroundColor(getMainActivity().getColor(R.color.white));
            linear_top_layout.setBackgroundColor(getActivity().getColor(R.color.white));
            txt_poa.setTextColor(getActivity().getColor(R.color.black));
            errorMsgLayout.setTextColor(getActivity().getColor(R.color.black));
        }
        layout_dpHolding.setVisibility(View.GONE);
        layout_BrokerHolding.setVisibility(View.GONE);
        sort_btn = view.findViewById(R.id.sort_btn);

        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(getContext(), sort_btn);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.sort_pop_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        DateFormat df = new SimpleDateFormat("MMM dd yyyy", Locale.US);

                        if (item.getItemId() == R.id.one) {


                        } else if (item.getItemId() == R.id.two) {


                        } else if (item.getItemId() == R.id.three) {


                        } else if (item.getItemId() == R.id.four) {

                        }


                        return true;
                    }
                });

                popup.show();


            }
        });


        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), clientList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
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

        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner_clientID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tempArrayList.clear();
                for (int i = 0; i < arrayList.size(); i++) {

                    if (arrayList.get(i).getCLIENT_ID().equalsIgnoreCase(clientList.get(position))) {
                        tempArrayList.add(arrayList.get(i));
                    }
                }

                txt_poa.setText("POA : " + poaList.get(position));
                myRecyclerviewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> TypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), reportList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
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
        TypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spinner_report.setAdapter(TypeAdapter);
        spinner_report.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    servicetype = "getClientHoldingPOA_Bajaj";

                    isDPHoldingActive = true;
                    spinner_clientID.setVisibility(View.VISIBLE);
                    txt_poa.setVisibility(View.VISIBLE);
                    layout_dpHolding.setVisibility(View.VISIBLE);
                    layout_BrokerHolding.setVisibility(View.GONE);
                    myRecyclerviewAdapter.notifyDataSetChanged();

                } else if (position == 1) {

                    isDPHoldingActive = false;
                    layout_dpHolding.setVisibility(View.GONE);
                    layout_BrokerHolding.setVisibility(View.VISIBLE);
                    spinner_clientID.setVisibility(View.INVISIBLE);
                    txt_poa.setVisibility(View.GONE);
                    servicetype = "getClientHolding_Bajaj";
                    myRecyclerviewAdapter.notifyDataSetChanged();
                }
                sendReportRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }


    private void sendReportRequest() {

        if (startDate.length() <= 0 || endDate.length() <= 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            Date monthFirstDay = calendar.getTime();
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date monthLastDay = calendar.getTime();

            startDate = df.format(monthFirstDay);
            Date c = Calendar.getInstance().getTime();
            endDate = df.format(c);
        }

        showProgress();
        PandLReportRequest.sendRequest(AccountDetails.getUsername(getActivity()), servicetype, startDate, endDate, getActivity(), serviceResponseHandler);
    }


    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);
        refreshComplete();
        arrayList.clear();

        JSONResponse jsonResponse = (JSONResponse) response;
        if (jsonResponse.getServiceName().equalsIgnoreCase("getClientHoldingPOA_Bajaj")) {


            try {

                JSONArray jsonArray = jsonResponse.getResObject().getJSONObject("response").getJSONArray("data");

                clientList.clear();
                poaList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String CLIENT_CODE = jsonObject.getString("CLIENT_CODE");
                    String CLIENT_NAME = jsonObject.getString("CLIENT_NAME");
                    String SCRIP_NAME = jsonObject.getString("SCRIP_NAME");
                    String ISIN = jsonObject.getString("ISIN");
                    String PLEDGED_QTY = jsonObject.getString("PLEDGED_QTY");
                    String FREE_QTY = jsonObject.getString("FREE_QTY");
                    String TOTAL_QTY = jsonObject.getString("TOTAL_QTY");
                    String CLOSING_RATE = jsonObject.getString("CLOSING_RATE");
                    String VALUE = jsonObject.getString("VALUE");
                    String DP_ID = jsonObject.getString("DP_ID");
                    String CLIENT_ID = jsonObject.getString("CLIENT_ID");

                    if (!clientList.contains("Client ID : " + CLIENT_ID)) {
                        clientList.add("Client ID : " + CLIENT_ID);
                    }


                    String DP_TYPE = jsonObject.getString("DP_TYPE");
                    String DEFAULT_POA = jsonObject.getString("DEFAULT_POA");
                    poaList.add(DEFAULT_POA);

                    PLData plData = new PLData();
                    plData.setCLIENT_CODE(CLIENT_CODE);
                    plData.setCLIENT_NAME(CLIENT_NAME);
                    plData.setSCRIP_NAME(SCRIP_NAME);
                    plData.setISIN(ISIN);
                    plData.setPLEDGED_QTY(PLEDGED_QTY);
                    plData.setFREE_QTY(FREE_QTY);
                    plData.setTOTAL_QTY(TOTAL_QTY);
                    plData.setCLOSING_RATE(CLOSING_RATE);
                    plData.setVALUE(VALUE);
                    plData.setDP_ID(DP_ID);
                    plData.setCLIENT_ID("Client ID : " + CLIENT_ID);
                    plData.setDP_TYPE(DP_TYPE);
                    plData.setDEFAULT_POA(DEFAULT_POA);
                    arrayList.add(plData);
                }

                spinner_clientID.setAdapter(assetTypeAdapter);
                assetTypeAdapter.notifyDataSetChanged();

                if (arrayList.size() > 0) {
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    toggleErrorLayout(false);

                } else {

                    swipeRefreshLayout.setVisibility(View.GONE);
                    toggleErrorLayout(true);
                }

                hideProgress();


            } catch (Exception e) {
                e.printStackTrace();
                hideProgress();
                toggleErrorLayout(true);

            }
        } else if (jsonResponse.getServiceName().equalsIgnoreCase("getClientHolding_Bajaj")) {

            try {
                tempArrayList.clear();

                JSONArray jsonArray = jsonResponse.getResObject().getJSONObject("response").getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String SRNO = "", CLIENT_CODE = "", CLIENT_NAME = "", SCRIP_NAME = "", ISIN = "", UNSETTLED_QTY = "", UNPAID_QTY = "", COLLATERAL_QTY = "", MTF_QTY = "", MTF_COLLATERAL = "", TOTAL_QTY = "", CLOSING_RATE = "", VALUE = "";
                    SRNO = jsonObject.getString("SRNO");
                    CLIENT_CODE = jsonObject.getString("CLIENT_CODE");
                    CLIENT_NAME = jsonObject.getString("CLIENT_NAME");
                    SCRIP_NAME = jsonObject.getString("SCRIP_NAME");
                    ISIN = jsonObject.getString("ISIN");
                    UNSETTLED_QTY = jsonObject.getString("UNSETTLED_QTY");
                    UNPAID_QTY = jsonObject.getString("UNPAID_QTY");
                    COLLATERAL_QTY = jsonObject.getString("COLLATERAL_QTY");
                    MTF_QTY = jsonObject.getString("MTF_QTY");
                    MTF_COLLATERAL = jsonObject.getString("MTF_COLLATERAL");
                    TOTAL_QTY = jsonObject.getString("TOTAL_QTY");
                    CLOSING_RATE = jsonObject.getString("CLOSING_RATE");
                    VALUE = jsonObject.getString("VALUE");

                    PLData plData = new PLData();
                    plData.setCLIENT_CODE(CLIENT_CODE);
                    plData.setCLIENT_NAME(CLIENT_NAME);
                    plData.setSCRIP_NAME(SCRIP_NAME);
                    plData.setISIN(ISIN);
                    plData.setTOTAL_QTY(TOTAL_QTY);
                    plData.setCLOSING_RATE(CLOSING_RATE);
                    plData.setVALUE(VALUE);

                    plData.setSRNO(SRNO);
                    plData.setUNSETTLED_QTY(UNSETTLED_QTY);
                    plData.setUNPAID_QTY(UNPAID_QTY);
                    plData.setCOLLATERAL_QTY(COLLATERAL_QTY);
                    plData.setMTF_QTY(MTF_QTY);
                    plData.setMTF_COLLATERAL(MTF_COLLATERAL);


                    tempArrayList.add(plData);
                }


                if (tempArrayList.size() > 0) {
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    toggleErrorLayout(false);

                } else {

                    swipeRefreshLayout.setVisibility(View.GONE);
                    toggleErrorLayout(true);
                }

                hideProgress();


            } catch (Exception e) {
                e.printStackTrace();
                hideProgress();
                toggleErrorLayout(true);

            }
        }


        myRecyclerviewAdapter.notifyDataSetChanged();

    }

    private void refreshComplete() {
        hideProgress();
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }


    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            sendReportRequest();


        }
    };

    private void toggleErrorLayout(boolean show) {
        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.ViewHolder> {

        Context context;
        ArrayList<PLData> list;

        public MyRecyclerviewAdapter(Context context, ArrayList<PLData> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public MyRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.row_holding_layout, parent, false);
            return new MyRecyclerviewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyRecyclerviewAdapter.ViewHolder holder, int position) {

            PLData plData = list.get(position);


            if (isDPHoldingActive) {
                holder.txt_mtfFund.setVisibility(View.GONE);
                holder.txt_FoColtrl.setVisibility(View.GONE);
                holder.txt_mtfColtrl.setVisibility(View.GONE);
                holder.br_layout.setVisibility(View.GONE);
                holder.viewBr.setVisibility(View.GONE);

                holder.txt_script.setText(plData.getSCRIP_NAME());
                holder.txt_plqty.setText(String.valueOf((int)Double.parseDouble(plData.getPLEDGED_QTY())));
                holder.txt_clsRating.setText(StringStuff.commaDecorator(plData.getCLOSING_RATE()));
                holder.value.setText(StringStuff.commaDecorator(plData.getVALUE()));
                holder.txt_freeQty.setText(String.valueOf((int)Double.parseDouble(plData.getFREE_QTY())));
                holder.txt_ttlQty.setText(String.valueOf((int)Double.parseDouble(plData.getTOTAL_QTY())));
                holder.txt_isin.setText(plData.getISIN());

            } else {
                holder.txt_mtfFund.setVisibility(View.VISIBLE);
                holder.txt_FoColtrl.setVisibility(View.VISIBLE);
                holder.txt_mtfColtrl.setVisibility(View.VISIBLE);
                holder.br_layout.setVisibility(View.VISIBLE);
                holder.viewBr.setVisibility(View.VISIBLE);

                holder.txt_script.setText(plData.getSCRIP_NAME());
                holder.txt_plqty.setText(String.valueOf((int)Double.parseDouble(plData.getTOTAL_QTY())));
                holder.txt_ttlQty.setText(String.valueOf((int)Double.parseDouble(plData.getUNSETTLED_QTY())));
                holder.value.setText(String.valueOf((int)Double.parseDouble(plData.getUNPAID_QTY())));
                holder.txt_freeQty.setText(StringStuff.commaINRDecorator(plData.getCLOSING_RATE()));
                holder.txt_clsRating.setText(StringStuff.commaINRDecorator(plData.getVALUE()));
                holder.txt_isin.setText(plData.getISIN());
                holder.txt_mtfFund.setText(String.valueOf((int)Double.parseDouble(plData.getMTF_QTY())));
                holder.txt_FoColtrl.setText(String.valueOf((int)Double.parseDouble(plData.getCOLLATERAL_QTY())));
                holder.txt_mtfColtrl.setText(StringStuff.commaINRDecorator(plData.getMTF_COLLATERAL()));

            }


        }

        @Override
        public int getItemCount() {
            int listSize = 0;
            if (list != null) {
                listSize = list.size();
            }
            return listSize;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            GreekTextView txt_script, txt_plqty, txt_freeQty, txt_isin, txt_clsRating, value, txt_ttlQty, txt_mtfFund, txt_FoColtrl, txt_mtfColtrl;
            View viewBr;
            LinearLayout br_layout;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_script = itemView.findViewById(R.id.txt_script);
                txt_plqty = itemView.findViewById(R.id.txt_quantity);
                txt_freeQty = itemView.findViewById(R.id.txt_ltp);
                txt_isin = itemView.findViewById(R.id.txt_change);
                txt_clsRating = itemView.findViewById(R.id.txt_price);
                value = itemView.findViewById(R.id.txt_cPrice);
                txt_ttlQty = itemView.findViewById(R.id.txt_inv_Price);
                txt_mtfFund = itemView.findViewById(R.id.txt_mtfFund);
                txt_FoColtrl = itemView.findViewById(R.id.txt_inv_FOQty);
                txt_mtfColtrl = itemView.findViewById(R.id.txt_mtfQty);
                viewBr = itemView.findViewById(R.id.viewBr);
                br_layout = itemView.findViewById(R.id.br_layout);
                if(AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")){
                    txt_script.setTextColor(context.getColor(R.color.black));
                    txt_plqty.setTextColor(context.getColor(R.color.black));
                    txt_freeQty.setTextColor(context.getColor(R.color.black));
                    txt_isin.setTextColor(context.getColor(R.color.black));
                    txt_clsRating.setTextColor(context.getColor(R.color.black));
                    value.setTextColor(context.getColor(R.color.black));
                    txt_ttlQty.setTextColor(context.getColor(R.color.black));
                    txt_mtfFund.setTextColor(context.getColor(R.color.black));
                    txt_FoColtrl.setTextColor(context.getColor(R.color.black));
                    txt_mtfColtrl.setTextColor(context.getColor(R.color.black));
                    viewBr.setBackgroundColor(context.getColor(R.color.black));
//                    br_layout.setBackgroundColor((AccountDetails.textColorDropdown));
                }

            }
        }
    }


}
