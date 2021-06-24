

package com.acumengroup.mobile.BottomTabScreens;

import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.BottomTabScreens.adapter.DerivativeRecycleAdapter;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData;
import com.acumengroup.mobile.BottomTabScreens.adapter.GainerMainHeader;
import com.acumengroup.mobile.BottomTabScreens.adapter.holder.DerivativeChildDataHolder;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.FnoMarketDataResponse;
import com.acumengroup.mobile.model.MarketDataModel;
import com.acumengroup.mobile.model.OIAnalysisDataResponse;
import com.acumengroup.mobile.model.RollOverDataResponse;
import com.acumengroup.mobile.model.SubHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;


public class DerivativeTabFragment extends GreekBaseFragment {


    public static final String FNO_ACTIVITY = "FNO ACTIVITY";
    public static final String OPTION_CHAIN = "OPTION CHAIN";
    public static final String OPEN_INTEREST_ANALYSIS = "OPEN INTEREST ANALYSIS";
    public static final String ROLL_OVER = "ROLL OVER";
    private RecyclerView derivativeRecycleView;
    private SwipeRefreshLayout swipeRefresh;
    private ArrayList<GainerMainHeader> mainHeadersList;
    private ArrayList<SubHeader> fnoActivityHeaderList;
    private ArrayList<SubHeader> openAnalysisHeaderList;
    private ArrayList<SubHeader> rollOverHeaderList;
    private ArrayList<SubHeader> optionChainHeaderList;
    private ArrayList<String> streamingList, oiStreamingList, fnoStreamingList, rollOverStreamingList;
    private FnoMarketDataResponse fnoMarketDataResponse;
    private OIAnalysisDataResponse oiAnalysisDataResponse;
    private RollOverDataResponse rollOverDataResponse;
    private MarketDataModel marketDataModel;
    private DerivativeRecycleAdapter adapter;
    private List<MarketDataModel> temp_list;
    private int selectedExpiryposition = 0;
    private ArrayList<String> expiryList, expiryTimeStampList;
    private ArrayList<GainerData> mItemListArray2;
    private ArrayList<GainerData> highestRollList, lowestRollList;
    private ArrayList<String> mItemListArray2Token;
    private RelativeLayout parentlayout;
    private LinearLayOutExeption linearLayOutExeption;
    private ArrayList<GainerData> ma_futureList, longBuildupList, shortBuildUpList, longUnwindingList, shortCoveringList, ma_OptionList, ma_OptionIndexList;
    private ArrayList<String> ma_futureListToken, ma_OptionListToken, ma_OptionIndexListToken, highestRollListToken, lowestRollListToken, longBuildupListToken,
            shortBuildUpListToken, longUnwindingListToken, shortCoveringListToken;
    String exchangeStr;
    String selectedExpiryTimeStamp="";

    public DerivativeTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            mainHeadersList.clear();
            fnoActivityHeaderList.clear();
            openAnalysisHeaderList.clear();
            rollOverHeaderList.clear();

            derivativeRecycleView.getRecycledViewPool().clear();
            // after pull down request we call this method
            mainHeadersList.add(new GainerMainHeader(fnoActivityHeaderList, FNO_ACTIVITY));
            mainHeadersList.add(new GainerMainHeader(optionChainHeaderList, OPTION_CHAIN));
            mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
            mainHeadersList.add(new GainerMainHeader(rollOverHeaderList, ROLL_OVER));
            //adapter.notifyDataSetChanged();

            //HeaderList
            fnoActivityHeaderList = new ArrayList<SubHeader>();
            openAnalysisHeaderList = new ArrayList<SubHeader>();
            rollOverHeaderList = new ArrayList<SubHeader>();
            adapter.notifyDataSetChanged();
            loadExpirySpinnerData();
            if (AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
                // loadFnoActivity(selectedExpiryTimeStamp, "getMostActiveByVolumeExpiry_MobileV2_Redis");
            } else {
//                        loadFnoActivity(selectedExpiryTimeStamp, "getMostActiveByVolumeExpiry_MobileV2");
                // loadFnoMostActive(selectedExpiryTimeStamp, "getMostActiveByVolumeExpiry_Mobile");
                derivativeLoadRollOverIndexData();
                loadOIAnalysisLong("","getOpenInterestAnalysis_Mobile");

//                loadRollOverData("", "getRollOverDataV2");
                loadHighestRollOver("", "getRollOverData");
            }

        }
    };

    private void refreshComplete() {
        hideProgress();
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            AccountDetails.derivativeTab = true;
            //clear headerList
            mainHeadersList.clear();
            fnoActivityHeaderList.clear();
            openAnalysisHeaderList.clear();
            rollOverHeaderList.clear();

            derivativeRecycleView.getRecycledViewPool().clear();

            mainHeadersList.add(new GainerMainHeader(fnoActivityHeaderList, FNO_ACTIVITY));
            mainHeadersList.add(new GainerMainHeader(optionChainHeaderList, OPTION_CHAIN));
            mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
            mainHeadersList.add(new GainerMainHeader(rollOverHeaderList, ROLL_OVER));
            derivativeRecycleView.setAdapter(adapter);
            fnoActivityHeaderList = new ArrayList<SubHeader>();
            openAnalysisHeaderList = new ArrayList<SubHeader>();
            rollOverHeaderList = new ArrayList<SubHeader>();

            loadExpirySpinnerData();
            if (AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
                // loadFnoActivity(selectedExpiryTimeStamp, "getMostActiveByVolumeExpiry_MobileV2_Redis");
            } else {
//                        loadFnoActivity(selectedExpiryTimeStamp, "getMostActiveByVolumeExpiry_MobileV2");
                // loadFnoMostActive(selectedExpiryTimeStamp, "getMostActiveByVolumeExpiry_Mobile");
                derivativeLoadRollOverIndexData();
                loadOIAnalysisLong("","getOpenInterestAnalysis_Mobile");

//                loadRollOverData("", "getRollOverDataV2");
                loadHighestRollOver("", "getRollOverData");

            }

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View derivativeView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_derivative).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_derivative).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        temp_list = new ArrayList<>();
        expiryList = new ArrayList<>();
        expiryTimeStampList = new ArrayList<>();
        marketDataModel = new MarketDataModel();

        mItemListArray2 = new ArrayList<GainerData>();
        mItemListArray2Token = new ArrayList<String>();


        fnoActivityHeaderList = new ArrayList<SubHeader>();
        optionChainHeaderList = new ArrayList<SubHeader>();
        openAnalysisHeaderList = new ArrayList<SubHeader>();
        rollOverHeaderList = new ArrayList<SubHeader>();

        optionChainHeaderList.add(new SubHeader("OPTION_CHAIN", mItemListArray2));

        setupView(derivativeView);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            parentlayout.setBackgroundColor(getActivity().getColor(R.color.white));
            derivativeRecycleView.setBackgroundColor(getActivity().getColor(R.color.white));
        }

        return derivativeView;
    }

    private void setupView(View derivativeView) {

        serviceResponseHandler = new ServiceResponseHandler(getActivity(), this);
        mainHeadersList = new ArrayList<>();

        swipeRefresh = derivativeView.findViewById(R.id.refreshList);
        parentlayout = derivativeView.findViewById(R.id.parent_layout);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        derivativeRecycleView = derivativeView.findViewById(R.id.derivative_list);
        adapter = new DerivativeRecycleAdapter(getContext(), expiryList, mainHeadersList, serviceResponseHandler);
        linearLayOutExeption = new LinearLayOutExeption(getMainActivity(), LinearLayoutManager.VERTICAL, false);
//        derivativeRecycleView.setLayoutManager(linearLayOutExeption);
        derivativeRecycleView.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        derivativeRecycleView.setHasFixedSize(true);
        ((SimpleItemAnimator) derivativeRecycleView.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    private void loadExpirySpinnerData() {
        showProgress();
        //request for get expirydata
        WSHandler.getRequest(getMainActivity(), "getExpiryFNOActivity", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
//                hideProgress();
                Log.e("DeriVativeScreen", "=====>" + response);
                try {
                    JSONArray respCategory = response.getJSONArray("data");
                    expiryList.clear();
                    for (int i = 0; i < respCategory.length(); i++) {

                        String pattern = "ddMMMyyyy";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                        expiryTimeStampList.add(String.valueOf(respCategory.getJSONObject(i).getLong("expiry_date")));

                        Date D = new Date((respCategory.getJSONObject(i).getLong("expiry_date")) * 1000);
                        String date = simpleDateFormat.format(D);
                        expiryList.add(date);
                    }

                    selectedExpiryTimeStamp = expiryTimeStampList.get(0);
                    AccountDetails.setSelectedExpiryposition(0);
                    exchangeStr = "NSE";
                    exchangeStr = exchangeStr.toLowerCase();
                    loadFnoMostActive(selectedExpiryTimeStamp, "getMostActiveByVolumeExpiry_Mobile");
                } catch (JSONException e) {
                    e.printStackTrace();

                    loadFnoMostActive(selectedExpiryTimeStamp, "getMostActiveByVolumeExpiry_Mobile");
                }
            }

            @Override
            public void onFailure(String message) {

                loadFnoMostActive(selectedExpiryTimeStamp, "getMostActiveByVolumeExpiry_Mobile");
            }
        });
    }

    private void loadFnoMostActive(final String expiry, final String service) {
// get request is call to Load FnoMostActive
        WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr +
                "&expiry=" + expiry +
                "&type=" + "future", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getJSONArray("data") != null) {
                        ma_futureList = new ArrayList<GainerData>();
                        ma_futureListToken = new ArrayList<String>();
                        streamingList = new ArrayList();
                        streamingList.clear();
                        fnoStreamingList = new ArrayList();
                        fnoStreamingList.clear();
                        fnoMarketDataResponse = new FnoMarketDataResponse();
//                        fnoMarketDataResponse.setFutureMarketDataModelList(response.getJSONArray("data"));
                        JSONArray ja1 = response.getJSONArray("data");

                        ArrayList<MarketDataModel> marketDataModels = new ArrayList<>();
                        for (int i = 0; i < ja1.length(); ++i) {
                            Object o = ja1.get(i);
                            if (o instanceof JSONObject) {
                                MarketDataModel data = new MarketDataModel();
                                data.fromJSON((JSONObject) o);
                                marketDataModels.add(data);

                            }
                        }
                        fnoMarketDataResponse.setFutureMarketDataModelList(marketDataModels);
                        if (fnoMarketDataResponse.getFutureMarketDataModelList().size() > 1) {
                            for (int i = 0; i < fnoMarketDataResponse.getFutureMarketDataModelList().size(); i++) {
                                if (fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getInstName().equalsIgnoreCase("FUTSTK") || fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getInstName().equalsIgnoreCase("FUTIDX")) {

                                    ma_futureList.add(new GainerData(
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getSymbol(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getDescription(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getExchange(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLotSize(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getChange(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getPerChange(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getAssetType(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getVolume(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getTickSize(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getMultiply_factor(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getExpiryDate(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getoptiontype(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getstrikeprice(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getinstrumentname()
                                    ));
                                    ma_futureListToken.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
                                    streamingList.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
                                    fnoStreamingList.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
                                }
                            }
                        } else {
                            ma_futureList.add(new GainerData(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "", "", "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }
                        if (ma_futureList.size() > 0) {

                            fnoActivityHeaderList.add(new SubHeader("MOST ACTIVE(FUTURES)", ma_futureList));
//                            adapter.addSymbol(FNO_ACTIVITY, "MOST ACTIVE(FUTURES)", ma_futureListToken);
                        }

                    }
                    loadFnoOptionIndex(expiry, service);
                } catch (Exception e) {
                    loadFnoOptionIndex(expiry, service);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                Log.e("failure >>>>>", message);
                loadFnoOptionIndex(expiry, service);
            }
        });


    }

    private void loadFnoOptionIndex(final String expiry, final String service) {
        exchangeStr = "NSE";
        exchangeStr = exchangeStr.toLowerCase();
        // Get request is call to load FnoOptionIndex
        WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr +
                "&expiry=" + expiry +
                "&type=" + "option_index", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getJSONArray("data") != null) {
                        ma_OptionIndexList = new ArrayList<GainerData>();
                        ma_OptionIndexListToken = new ArrayList<String>();
                        streamingList = new ArrayList();
                        streamingList.clear();
                        fnoStreamingList = new ArrayList();
                        fnoStreamingList.clear();
                        fnoMarketDataResponse = new FnoMarketDataResponse();
//                        fnoMarketDataResponse.setFutureMarketDataModelList(response.getJSONArray("data"));
                        JSONArray ja1 = response.getJSONArray("data");

                        ArrayList<MarketDataModel> marketDataModels = new ArrayList<>();
                        for (int i = 0; i < ja1.length(); ++i) {
                            Object o = ja1.get(i);
                            if (o instanceof JSONObject) {
                                MarketDataModel data = new MarketDataModel();
                                data.fromJSON((JSONObject) o);
                                marketDataModels.add(data);

                            }
                        }
                        fnoMarketDataResponse.setFutureMarketDataModelList(marketDataModels);
                        if (fnoMarketDataResponse.getFutureMarketDataModelList().size() > 1) {
                            for (int i = 0; i < fnoMarketDataResponse.getFutureMarketDataModelList().size(); i++) {
                                if (fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getInstName().equalsIgnoreCase("OPTIDX")) {

                                    ma_OptionIndexList.add(new GainerData(
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getSymbol(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getDescription(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getExchange(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLotSize(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getChange(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getPerChange(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getAssetType(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getVolume(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getTickSize(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getMultiply_factor(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getExpiryDate(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getoptiontype(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getstrikeprice(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getinstrumentname()
                                    ));
                                    ma_OptionIndexListToken.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
                                    streamingList.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
                                    fnoStreamingList.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
                                }
                            }
                        } else {
                            ma_OptionIndexList.add(new GainerData(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "", "", "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }
                        if (ma_OptionIndexList.size() > 0) {

                            fnoActivityHeaderList.add(new SubHeader("MOST ACTIVE(OPTIONS INDEX)", ma_OptionIndexList));
//                            adapter.addSymbol(FNO_ACTIVITY, "MOST ACTIVE(OPTION INDEX)", ma_OptionIndexListToken);
                        }
                        mainHeadersList.clear();

                    }
                    loadFnoOptionStock(expiry, service);
                } catch (Exception e) {
                    e.printStackTrace();
                    loadFnoOptionStock(expiry, service);
                }
            }

            @Override
            public void onFailure(String message) {
                Log.e("failure >>>>>", message);
                loadFnoOptionStock(expiry, service);
            }
        });


    }

    private void loadFnoOptionStock(String expiry, final String service) {
        // Get Request is call to LoadFnooptionStock
        WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr +
                "&expiry=" + expiry +
                "&type=" + "option_stock", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getJSONArray("data") != null) {
                        ma_OptionList = new ArrayList<GainerData>();
                        ma_OptionListToken = new ArrayList<String>();
                        streamingList = new ArrayList();
                        streamingList.clear();
                        fnoStreamingList = new ArrayList();
                        fnoStreamingList.clear();
                        fnoMarketDataResponse = new FnoMarketDataResponse();
//                        fnoMarketDataResponse.setFutureMarketDataModelList(response.getJSONArray("data"));
                        JSONArray ja1 = response.getJSONArray("data");

                        ArrayList<MarketDataModel> marketDataModels = new ArrayList<>();
                        for (int i = 0; i < ja1.length(); ++i) {
                            Object o = ja1.get(i);
                            if (o instanceof JSONObject) {
                                MarketDataModel data = new MarketDataModel();
                                data.fromJSON((JSONObject) o);
                                marketDataModels.add(data);

                            }
                        }
                        fnoMarketDataResponse.setFutureMarketDataModelList(marketDataModels);
                        if (fnoMarketDataResponse.getFutureMarketDataModelList().size() > 1) {
                            for (int i = 0; i < fnoMarketDataResponse.getFutureMarketDataModelList().size(); i++) {
                                if (fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getInstName().equalsIgnoreCase("OPTSTK")) {

                                    ma_OptionList.add(new GainerData(
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getSymbol(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getDescription(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getExchange(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLotSize(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getChange(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getPerChange(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getAssetType(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getVolume(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getTickSize(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getMultiply_factor(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getExpiryDate(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getoptiontype(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getstrikeprice(),
                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getinstrumentname()
                                    ));
                                    ma_OptionListToken.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
                                    streamingList.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
                                    fnoStreamingList.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
                                }
                            }
                        } else {
                            ma_OptionList.add(new GainerData(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "", "", "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }
                        if (ma_OptionList.size() > 0) {

                            fnoActivityHeaderList.add(new SubHeader("MOST ACTIVE(OPTIONS STOCK)", ma_OptionList));
//                        adapter.addSymbol(FNO_ACTIVITY, "MOST ACTIVE(OPTION STOCK)", ma_OptionListToken);
                        }
                        derivativeRecycleView.getRecycledViewPool().clear();
                        mainHeadersList.clear();
                        mainHeadersList.add(new GainerMainHeader(fnoActivityHeaderList, FNO_ACTIVITY));
                        mainHeadersList.add(new GainerMainHeader(optionChainHeaderList, OPTION_CHAIN));
                        mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
                        mainHeadersList.add(new GainerMainHeader(rollOverHeaderList, ROLL_OVER));
                        adapter.notifyDataSetChanged();
                        // derivativeRecycleView.setAdapter(adapter);
                        // loadOIAnalysisLong("","getOpenInterestAnalysis_Mobile");

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(String message) {
                Log.e("failure >>>>>", message);

            }
        });


    }

    //    private void loadFnoActivity(String expiry, final String service) {
//        String exchangeStr = "NSE";
//        exchangeStr = exchangeStr.toLowerCase();
//        fnoActivityHeaderList.clear();
//        showProgress();
//        WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr +
//                "&expiry=" + expiry, new WSHandler.GreekResponseCallback() {
//            @Override
//            public void onSuccess(JSONObject response) {
//                try {
//                    fnoActivityHeaderList.clear();
//                    hideProgress();
//                    streamingList = new ArrayList();
//                    streamingList.clear();
//                    fnoStreamingList = new ArrayList();
//                    fnoStreamingList.clear();
//
//                    ma_futureList = new ArrayList<GainerData>();
//                    ma_OptionList = new ArrayList<GainerData>();
//                    ma_OptionIndexList = new ArrayList<GainerData>();
//
//                    ma_futureListToken = new ArrayList<String>();
//                    ma_OptionListToken = new ArrayList<String>();
//                    ma_OptionIndexListToken = new ArrayList<String>();
//
//                    fnoMarketDataResponse = new FnoMarketDataResponse();
//                    fnoMarketDataResponse.fromJSON(response.getJSONArray("data").getJSONObject(0));
//
//                    if (response.getJSONArray("data").getJSONObject(0).getJSONArray("future") != null) {
//                        if (fnoMarketDataResponse.getFutureMarketDataModelList().size() > 1) {
//                            for (int i = 0; i < fnoMarketDataResponse.getFutureMarketDataModelList().size(); i++) {
//                                if (fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getInstName().equalsIgnoreCase("FUTSTK") || fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getInstName().equalsIgnoreCase("FUTIDX")) {
//
//                                    ma_futureList.add(new GainerData(
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getSymbol(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getDescription(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getExchange(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLotSize(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getLtp(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getChange(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getPerChange(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getAssetType(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getVolume(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getTickSize(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getMultiply_factor(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getExpiryDate(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getoptiontype(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getstrikeprice(),
//                                            fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getinstrumentname()
//                                    ));
//                                    ma_futureListToken.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
//                                    streamingList.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
//                                    fnoStreamingList.add(fnoMarketDataResponse.getFutureMarketDataModelList().get(i).getToken());
//                                }
//                            }
//                        } else {
//                            ma_futureList.add(new GainerData(
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "", "", "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    ""));
//                        }
//                    }
//
//
//                    if (response.getJSONArray("data").getJSONObject(0).getJSONArray("option_index") != null) {
//                        if (fnoMarketDataResponse.getIndexMarketDataModelList().size() > 1) {
//                            for (int i = 0; i < fnoMarketDataResponse.getIndexMarketDataModelList().size(); i++) {
//                                if (fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getInstName().equalsIgnoreCase("OPTIDX")) {
//
//                                    ma_OptionIndexList.add(new GainerData(
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getSymbol(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getDescription(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getExchange(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getLotSize(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getLtp(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getLtp(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getLtp(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getChange(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getPerChange(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getAssetType(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getToken(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getToken(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getToken(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getVolume(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getTickSize(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getMultiply_factor(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getExpiryDate(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getoptiontype(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getstrikeprice(),
//                                            fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getinstrumentname()
//                                    ));
//                                    ma_OptionIndexListToken.add(fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getToken());
//                                    streamingList.add(fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getToken());
//                                    fnoStreamingList.add(fnoMarketDataResponse.getIndexMarketDataModelList().get(i).getToken());
//
//                                }
//                            }
//                        } else {
//                            ma_OptionIndexList.add(new GainerData(
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "", "", "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    ""));
//                        }
//                    }
//
//
//                    if (response.getJSONArray("data").getJSONObject(0).getJSONArray("option_stock") != null) {
//                        if (fnoMarketDataResponse.getStockMarketDataModelList().size() > 1) {
//                            for (int i = 0; i < fnoMarketDataResponse.getStockMarketDataModelList().size(); i++) {
//                                if (fnoMarketDataResponse.getStockMarketDataModelList().get(i).getInstName().equalsIgnoreCase("OPTSTK")) {
//
//                                    ma_OptionList.add(new GainerData(
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getSymbol(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getDescription(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getExchange(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getLotSize(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getLtp(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getLtp(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getLtp(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getChange(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getPerChange(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getAssetType(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getToken(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getToken(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getToken(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getVolume(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getTickSize(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getMultiply_factor(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getExpiryDate(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getoptiontype(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getstrikeprice(),
//                                            fnoMarketDataResponse.getStockMarketDataModelList().get(i).getinstrumentname()
//                                    ));
//
//                                    ma_OptionListToken.add(fnoMarketDataResponse.getStockMarketDataModelList().get(i).getToken());
//                                    streamingList.add(fnoMarketDataResponse.getStockMarketDataModelList().get(i).getToken());
//                                    fnoStreamingList.add(fnoMarketDataResponse.getStockMarketDataModelList().get(i).getToken());
//
//                                }
//                            }
//                        } else {
//                            ma_OptionList.add(new GainerData(
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "", "", "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    ""));
//                        }
//
//                    }
//
//                    if (ma_futureList.size() > 0) {
//
//                        fnoActivityHeaderList.add(new SubHeader("MOST ACTIVE(FUTURES)", ma_futureList));
//                        adapter.addSymbol(FNO_ACTIVITY, "MOST ACTIVE(FUTURES)", ma_futureListToken);
//                    }
//
//                    if (ma_OptionIndexList.size() > 0) {
//
//                        fnoActivityHeaderList.add(new SubHeader("MOST ACTIVE(OPTIONS INDEX)", ma_OptionIndexList));
//                        adapter.addSymbol(FNO_ACTIVITY, "MOST ACTIVE(OPTION INDEX)", ma_OptionIndexListToken);
//                    }
//
//                    if (ma_OptionList.size() > 0) {
//
//                        fnoActivityHeaderList.add(new SubHeader("MOST ACTIVE(OPTIONS STOCK)", ma_OptionList));
//                        adapter.addSymbol(FNO_ACTIVITY, "MOST ACTIVE(OPTION STOCK)", ma_OptionListToken);
//                    }
//
//                    mainHeadersList.clear();
//                    mainHeadersList.add(new GainerMainHeader(fnoActivityHeaderList, FNO_ACTIVITY));
//                    mainHeadersList.add(new GainerMainHeader(optionChainHeaderList, OPTION_CHAIN));
//                    mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
//                    mainHeadersList.add(new GainerMainHeader(rollOverHeaderList, ROLL_OVER));
//                    derivativeRecycleView.setAdapter(adapter);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    hideProgress();
//                    refreshComplete();
//
//                }
//                refreshComplete();
//            }
//
//            @Override
//            public void onFailure(String message) {
//                hideProgress();
//                refreshComplete();
//
//            }
//        });
//    }


    private void loadOIAnalysisLong(final String expiry, final String service) {
        exchangeStr = "NSE";
        exchangeStr = exchangeStr.toLowerCase();
        // Get Request is call to LoadOIAnalysisLong
        WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr + "&expiry=" + expiry + "&type=long_build_up", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    openAnalysisHeaderList.clear();

                    // hideProgress();
                    streamingList = new ArrayList();
                    oiStreamingList = new ArrayList();

                    longBuildupList = new ArrayList<GainerData>();
                    shortBuildUpList = new ArrayList<GainerData>();
                    longUnwindingList = new ArrayList<GainerData>();
                    shortCoveringList = new ArrayList<GainerData>();


                    longBuildupListToken = new ArrayList<String>();
                    shortBuildUpListToken = new ArrayList<String>();
                    longUnwindingListToken = new ArrayList<String>();
                    shortCoveringListToken = new ArrayList<String>();


                    oiAnalysisDataResponse = new OIAnalysisDataResponse();
                    //oiAnalysisDataResponse.fromJSON(response.getJSONArray("data").getJSONObject(0));
                    JSONArray ja1 = response.getJSONArray("data");

                    ArrayList<MarketDataModel> marketDataModels = new ArrayList<>();
                    for (int i = 0; i < ja1.length(); ++i) {
                        Object o = ja1.get(i);
                        if (o instanceof JSONObject) {
                            MarketDataModel data = new MarketDataModel();
                            data.fromJSON((JSONObject) o);
                            marketDataModels.add(data);

                        }
                    }
                    oiAnalysisDataResponse.setLongBuildupDataModelList(marketDataModels);
                    if (response.getJSONArray("data") != null) {
                        if (oiAnalysisDataResponse.getLongBuildupDataModelList().size() >= 1) {
                            for (int i = 0; i < oiAnalysisDataResponse.getLongBuildupDataModelList().size(); i++) {
                                longBuildupList.add(new GainerData(
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getSymbol(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getDescription(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getExchange(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLotSize(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getChange(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getPerChange(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getAssetType(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getVolume(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getTickSize(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getMultiply_factor(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getExpiryDate(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getoptiontype(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getstrikeprice(),
                                        oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getinstrumentname()
                                ));


                                longBuildupListToken.add(oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken());
                                streamingList.add(oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken());
                                oiStreamingList.add(oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken());
                            }
                        } else {
                            longBuildupList.add(new GainerData(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "", "", "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }
                    }
                    if (longBuildupList.size() > 0) {

//                        openAnalysisHeaderList.remove(new SubHeader("LONG BUILD UP(OI UP, PRICE UP)", longBuildupList));
                        openAnalysisHeaderList.add(new SubHeader("LONG BUILD UP(OI UP, PRICE UP)", longBuildupList));
                        adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "LONG BUILD UP(OI UP, PRICE UP)", longBuildupListToken);
                    }

                    //  refreshComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    loadOIAnalysisshort(expiry,service);
                }
                loadOIAnalysisshort(expiry,service);
            }

            @Override
            public void onFailure(String message) {
                Log.e("failure>>>>", message);
                loadOIAnalysisshort(expiry,service);
            }
        });


    }

    private void loadOIAnalysisshort(final String expiry, final String service) {

        exchangeStr = "NSE";
        exchangeStr = exchangeStr.toLowerCase();
        // Get Request is call to LoadOiAnalysisShort
        WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr + "&expiry=" + expiry + "&type=short_build_up", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

//                    openAnalysisHeaderList.clear();

                    // hideProgress();
                    streamingList = new ArrayList();
                    oiStreamingList = new ArrayList();


                    shortBuildUpList = new ArrayList<GainerData>();
                    longUnwindingList = new ArrayList<GainerData>();
                    shortCoveringList = new ArrayList<GainerData>();



                    shortBuildUpListToken = new ArrayList<String>();
                    longUnwindingListToken = new ArrayList<String>();
                    shortCoveringListToken = new ArrayList<String>();


                    oiAnalysisDataResponse = new OIAnalysisDataResponse();
                    //oiAnalysisDataResponse.fromJSON(response.getJSONArray("data").getJSONObject(0));
                    JSONArray ja1 = response.getJSONArray("data");

                    ArrayList<MarketDataModel> marketDataModels = new ArrayList<>();
                    for (int i = 0; i < ja1.length(); ++i) {
                        Object o = ja1.get(i);
                        if (o instanceof JSONObject) {
                            MarketDataModel data = new MarketDataModel();
                            data.fromJSON((JSONObject) o);
                            marketDataModels.add(data);

                        }
                    }
                    oiAnalysisDataResponse.setShortBuildupDataModelList(marketDataModels);
                    if (response.getJSONArray("data") != null) {
                        if (oiAnalysisDataResponse.getShortBuildupDataModelList().size() >= 1) {
                            for (int i = 0; i < oiAnalysisDataResponse.getShortBuildupDataModelList().size(); i++) {
                                shortBuildUpList.add(new GainerData(
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getSymbol(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getDescription(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getExchange(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLotSize(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getChange(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getPerChange(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getAssetType(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getVolume(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getTickSize(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getMultiply_factor(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getExpiryDate(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getoptiontype(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getstrikeprice(),
                                        oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getinstrumentname()
                                ));


                                shortBuildUpListToken.add(oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken());
                                streamingList.add(oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken());
                                oiStreamingList.add(oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken());
                            }
                        } else {
                            shortBuildUpList.add(new GainerData(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "", "", "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }
                    }
                    if (shortBuildUpList.size() > 0) {

//                        openAnalysisHeaderList.remove(new SubHeader("LONG BUILD UP(OI UP, PRICE UP)", longBuildupList));
                        openAnalysisHeaderList.add(new SubHeader("SHORT BUILD UP(OI UP, PRICE DOWN)", shortBuildUpList));
                        adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "SHORT BUILD UP(OI UP, PRICE DOWN)", shortBuildUpListToken);
                    }

                    // refreshComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    loadOIAnalysiswinding(expiry,service);
                }
                loadOIAnalysiswinding(expiry,service);
            }

            @Override
            public void onFailure(String message) {
                Log.e("failure>>>>", message);
                loadOIAnalysiswinding(expiry,service);
            }
        });




    }

    private void loadOIAnalysiswinding(final String expiry, final String service) {

        exchangeStr = "NSE";
        exchangeStr = exchangeStr.toLowerCase();
        // Get Request is call to LoadOIAnalysisWinding
        WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr + "&expiry=" + expiry + "&type=long_unwinding", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

//                    openAnalysisHeaderList.clear();

                    //  hideProgress();
                    streamingList = new ArrayList();
                    oiStreamingList = new ArrayList();



                    longUnwindingList = new ArrayList<GainerData>();
                    shortCoveringList = new ArrayList<GainerData>();



                    longUnwindingListToken = new ArrayList<String>();
                    shortCoveringListToken = new ArrayList<String>();


                    oiAnalysisDataResponse = new OIAnalysisDataResponse();
                    //oiAnalysisDataResponse.fromJSON(response.getJSONArray("data").getJSONObject(0));
                    JSONArray ja1 = response.getJSONArray("data");

                    ArrayList<MarketDataModel> marketDataModels = new ArrayList<>();
                    for (int i = 0; i < ja1.length(); ++i) {
                        Object o = ja1.get(i);
                        if (o instanceof JSONObject) {
                            MarketDataModel data = new MarketDataModel();
                            data.fromJSON((JSONObject) o);
                            marketDataModels.add(data);

                        }
                    }
                    oiAnalysisDataResponse.setLongUnwindingDataModelList(marketDataModels);
                    if (response.getJSONArray("data") != null) {
                        if (oiAnalysisDataResponse.getLongUnwindingDataModelList().size() >= 1) {
                            for (int i = 0; i < oiAnalysisDataResponse.getLongUnwindingDataModelList().size(); i++) {
                                longUnwindingList.add(new GainerData(
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getSymbol(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getDescription(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getExchange(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLotSize(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getChange(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getPerChange(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getAssetType(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getVolume(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getTickSize(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getMultiply_factor(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getExpiryDate(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getoptiontype(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getstrikeprice(),
                                        oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getinstrumentname()
                                ));


                                longUnwindingListToken.add(oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken());
                                streamingList.add(oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken());
                                oiStreamingList.add(oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken());
                            }
                        } else {
                            longUnwindingList.add(new GainerData(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "", "", "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }
                    }
                    if (longUnwindingList.size() > 0) {

//                         openAnalysisHeaderList.remove(new SubHeader("LONG UNWINDING(OI DOWN, PRICE DOWN)", longUnwindingList));
                        openAnalysisHeaderList.add(new SubHeader("LONG UNWINDING(OI DOWN, PRICE DOWN)", longUnwindingList));
                        adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "LONG UNWINDING(OI DOWN, PRICE DOWN)", longUnwindingListToken);
                    }

                    //  refreshComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    loadOIAnalysisshortCovering(expiry,service);
                }
                loadOIAnalysisshortCovering(expiry,service);
            }

            @Override
            public void onFailure(String message) {
                Log.e("failure>>>>", message);
                loadOIAnalysisshortCovering(expiry,service);
            }
        });


    }

    private void loadOIAnalysisshortCovering(String expiry, final String service) {


        exchangeStr = "NSE";
        exchangeStr = exchangeStr.toLowerCase();
        // Get Request is call to LoadOiAnalysisStockCovering
        WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr + "&expiry=" + expiry + "&type=short_covering", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

//                    openAnalysisHeaderList.clear();

                    //   hideProgress();
                    streamingList = new ArrayList();
                    oiStreamingList = new ArrayList();




                    shortCoveringList = new ArrayList<GainerData>();




                    shortCoveringListToken = new ArrayList<String>();


                    oiAnalysisDataResponse = new OIAnalysisDataResponse();
                    //oiAnalysisDataResponse.fromJSON(response.getJSONArray("data").getJSONObject(0));
                    JSONArray ja1 = response.getJSONArray("data");

                    ArrayList<MarketDataModel> marketDataModels = new ArrayList<>();
                    for (int i = 0; i < ja1.length(); ++i) {
                        Object o = ja1.get(i);
                        if (o instanceof JSONObject) {
                            MarketDataModel data = new MarketDataModel();
                            data.fromJSON((JSONObject) o);
                            marketDataModels.add(data);

                        }
                    }
                    oiAnalysisDataResponse.setShortCoveringDataModelList(marketDataModels);
                    if (response.getJSONArray("data") != null) {
                        if (oiAnalysisDataResponse.getShortCoveringDataModelList().size() >= 1) {
                            for (int i = 0; i < oiAnalysisDataResponse.getShortCoveringDataModelList().size(); i++) {
                                shortCoveringList.add(new GainerData(
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getSymbol(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getDescription(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getExchange(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLotSize(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLtp(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getChange(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getPerChange(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getAssetType(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getVolume(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getTickSize(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getMultiply_factor(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getExpiryDate(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getoptiontype(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getstrikeprice(),
                                        oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getinstrumentname()
                                ));


                                shortCoveringListToken.add(oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken());
                                streamingList.add(oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken());
                                oiStreamingList.add(oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken());
                            }
                        } else {
                            shortCoveringList.add(new GainerData(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "", "", "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }
                    }
                    if (shortCoveringList.size() > 0) {

//                        openAnalysisHeaderList.remove(new SubHeader("SHORT COVERING (OI DOWN, PRICE UP)", shortCoveringList));
                        openAnalysisHeaderList.add(new SubHeader("SHORT COVERING (OI DOWN, PRICE UP)", shortCoveringList));
                        adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "SHORT COVERING (OI DOWN, PRICE UP)", shortCoveringListToken);
                    }
                    derivativeRecycleView.getRecycledViewPool().clear();
                    mainHeadersList.clear();
                    mainHeadersList.add(new GainerMainHeader(fnoActivityHeaderList, FNO_ACTIVITY));
                    mainHeadersList.add(new GainerMainHeader(optionChainHeaderList, OPTION_CHAIN));
                    mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
                    mainHeadersList.add(new GainerMainHeader(rollOverHeaderList, ROLL_OVER));
//                    derivativeRecycleView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

//                    loadHighestRollOver("", "getRollOverData");
                    //   refreshComplete();

                } catch (Exception e) {
                    e.printStackTrace();
//                    loadHighestRollOver("", "getRollOverData");
                }
            }

            @Override
            public void onFailure(String message) {
                Log.e("failure>>>>", message);
//                loadHighestRollOver("", "getRollOverData");
            }
        });




    }

    /* private void loadOIAnalysisData(String expiry, final String service) {

         String exchangeStr = "NSE";
         exchangeStr = exchangeStr.toLowerCase();
         openAnalysisHeaderList.clear();

         WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr + "&expiry=" + expiry, new WSHandler.GreekResponseCallback() {
             @Override
             public void onSuccess(JSONObject response) {
                 try {

                     openAnalysisHeaderList.clear();

                     hideProgress();
                     streamingList = new ArrayList();
                     oiStreamingList = new ArrayList();

                     longBuildupList = new ArrayList<GainerData>();
                     shortBuildUpList = new ArrayList<GainerData>();
                     longUnwindingList = new ArrayList<GainerData>();
                     shortCoveringList = new ArrayList<GainerData>();


                     longBuildupListToken = new ArrayList<String>();
                     shortBuildUpListToken = new ArrayList<String>();
                     longUnwindingListToken = new ArrayList<String>();
                     shortCoveringListToken = new ArrayList<String>();


                     oiAnalysisDataResponse = new OIAnalysisDataResponse();
                     oiAnalysisDataResponse.fromJSON(response.getJSONArray("data").getJSONObject(0));

                     if (response.getJSONArray("data").getJSONObject(0).getJSONArray("long_build_up") != null) {
                         if (oiAnalysisDataResponse.getLongBuildupDataModelList().size() >= 1) {
                             for (int i = 0; i < oiAnalysisDataResponse.getLongBuildupDataModelList().size(); i++) {
                                 longBuildupList.add(new GainerData(
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getSymbol(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getDescription(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getExchange(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLotSize(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getChange(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getPerChange(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getAssetType(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getVolume(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getTickSize(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getMultiply_factor(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getExpiryDate(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getoptiontype(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getstrikeprice(),
                                         oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getinstrumentname()
                                 ));


                                 longBuildupListToken.add(oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken());
                                 streamingList.add(oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken());
                                 oiStreamingList.add(oiAnalysisDataResponse.getLongBuildupDataModelList().get(i).getToken());
                             }
                         } else {
                             longBuildupList.add(new GainerData(
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "", "", "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     ""));
                         }
                     }

                     if (response.getJSONArray("data").getJSONObject(0).getJSONArray("short_build_up") != null) {

                         if (oiAnalysisDataResponse.getShortBuildupDataModelList().size() >= 1) {
                             for (int i = 0; i < oiAnalysisDataResponse.getShortBuildupDataModelList().size(); i++) {


                                 shortBuildUpList.add(new GainerData(
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getSymbol(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getDescription(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getExchange(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLotSize(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getChange(), oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getPerChange(), oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getAssetType(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getVolume(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getTickSize(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getMultiply_factor(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getExpiryDate(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getoptiontype(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getstrikeprice(),
                                         oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getinstrumentname()
                                 ));

                                 shortBuildUpListToken.add(oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken());
                                 streamingList.add(oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken());
                                 oiStreamingList.add(oiAnalysisDataResponse.getShortBuildupDataModelList().get(i).getToken());
                             }
                         } else {
                             shortBuildUpList.add(new GainerData(
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "", "", "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     ""));
                         }
                     }

                     if (response.getJSONArray("data").getJSONObject(0).getJSONArray("long_unwinding") != null) {

                         if (oiAnalysisDataResponse.getLongUnwindingDataModelList().size() >= 1) {
                             for (int i = 0; i < oiAnalysisDataResponse.getLongUnwindingDataModelList().size(); i++) {

                                 longUnwindingList.add(new GainerData(
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getSymbol(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getDescription(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getExchange(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLotSize(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getChange(), oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getPerChange(), oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getAssetType(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getVolume(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getTickSize(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getMultiply_factor(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getExpiryDate(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getoptiontype(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getstrikeprice(),
                                         oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getinstrumentname()
                                 ));

                                 longUnwindingListToken.add(oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken());
                                 streamingList.add(oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken());
                                 oiStreamingList.add(oiAnalysisDataResponse.getLongUnwindingDataModelList().get(i).getToken());

                             }
                         } else {
                             longUnwindingList.add(new GainerData(
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "", "", "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     ""));
                         }
                     }

                     if (response.getJSONArray("data").getJSONObject(0).getJSONArray("short_covering") != null) {
                         if (oiAnalysisDataResponse.getShortCoveringDataModelList().size() >= 1) {
                             for (int i = 0; i < oiAnalysisDataResponse.getShortCoveringDataModelList().size(); i++) {

                                 shortCoveringList.add(new GainerData(
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getSymbol(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getDescription(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getExchange(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLotSize(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getLtp(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getChange(), oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getPerChange(), oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getAssetType(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getVolume(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getTickSize(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getMultiply_factor(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getExpiryDate(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getoptiontype(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getstrikeprice(),
                                         oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getinstrumentname()
                                 ));

                                 shortCoveringListToken.add(oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken());
                                 streamingList.add(oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken());
                                 oiStreamingList.add(oiAnalysisDataResponse.getShortCoveringDataModelList().get(i).getToken());

                             }
                         } else {
                             shortCoveringList.add(new GainerData(
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "", "", "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     "",
                                     ""));
                         }

                     }

                     if (longBuildupList.size() > 0) {

                         openAnalysisHeaderList.remove(new SubHeader("LONG BUILD UP(OI UP, PRICE UP)", longBuildupList));
                         openAnalysisHeaderList.add(new SubHeader("LONG BUILD UP(OI UP, PRICE UP)", longBuildupList));
                         adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "LONG BUILD UP(OI UP, PRICE UP)", longBuildupListToken);
                     }


                     if (shortBuildUpList.size() > 0) {

                         openAnalysisHeaderList.remove(new SubHeader("SHORT BUILD UP(OI UP, PRICE DOWN)", shortBuildUpList));
                         openAnalysisHeaderList.add(new SubHeader("SHORT BUILD UP(OI UP, PRICE DOWN)", shortBuildUpList));
                         adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "SHORT BUILD UP(OI UP, PRICE DOWN)", shortBuildUpListToken);
                     }

                     if (longUnwindingList.size() > 0) {

                         openAnalysisHeaderList.remove(new SubHeader("LONG UNWINDING(OI DOWN, PRICE DOWN)", longUnwindingList));
                         openAnalysisHeaderList.add(new SubHeader("LONG UNWINDING(OI DOWN, PRICE DOWN)", longUnwindingList));
                         adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "LONG UNWINDING(OI DOWN, PRICE DOWN)", longUnwindingListToken);
                     }


                     if (shortCoveringList.size() > 0) {
                         openAnalysisHeaderList.remove(new SubHeader("SHORT COVERING (OI DOWN, PRICE UP)", shortCoveringList));
                         openAnalysisHeaderList.add(new SubHeader("SHORT COVERING (OI DOWN, PRICE UP)", shortCoveringList));
                         adapter.addSymbol(OPEN_INTEREST_ANALYSIS, "SHORT COVERING (OI DOWN, PRICE UP)", shortCoveringListToken);
                     }


                     mainHeadersList.clear();
                     mainHeadersList.add(new GainerMainHeader(fnoActivityHeaderList, FNO_ACTIVITY));
                     mainHeadersList.add(new GainerMainHeader(optionChainHeaderList, OPTION_CHAIN));
                     mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
                     mainHeadersList.add(new GainerMainHeader(rollOverHeaderList, ROLL_OVER));
                     derivativeRecycleView.setAdapter(adapter);
                     refreshComplete();

                 } catch (JSONException e) {
                     e.printStackTrace();
                     hideProgress();
                 }
             }

             @Override
             public void onFailure(String message) {
                 hideProgress();
                 refreshComplete();
             }
         });
     }*/
    private void loadHighestRollOver(final String expiry, final String service) {
        String exchangeStr = "NSE";
        exchangeStr = exchangeStr.toLowerCase();
        rollOverHeaderList.clear();

//       showProgress();
        // Get Request is call to LoadHighestRollOver
        WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr + "&expiry=" + expiry+"&type=gainer", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    //   hideProgress();
                    streamingList = new ArrayList();
                    streamingList.clear();
                    rollOverStreamingList = new ArrayList();
                    rollOverStreamingList.clear();

                    highestRollList = new ArrayList<GainerData>();


                    highestRollList.clear();


                    highestRollListToken = new ArrayList<String>();

                    rollOverDataResponse = new RollOverDataResponse();
//                   rollOverDataResponse.fromJSON(response.getJSONArray("data").getJSONObject(0));
                    JSONArray ja1 = response.getJSONArray("data");

                    ArrayList<MarketDataModel> marketDataModels = new ArrayList<>();
                    for (int i = 0; i < ja1.length(); ++i) {
                        Object o = ja1.get(i);
                        if (o instanceof JSONObject) {
                            MarketDataModel data = new MarketDataModel();
                            data.fromJSON((JSONObject) o);
                            marketDataModels.add(data);

                        }
                    }
                    rollOverDataResponse.setGainerRollerDataModelList(marketDataModels);

                    if (response.getJSONArray("data") != null) {
                        if (rollOverDataResponse.getGainerRollerDataModelList().size() > 1) {
                            for (int i = 0; i < rollOverDataResponse.getGainerRollerDataModelList().size(); i++) {

                                highestRollList.add(new GainerData(
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getSymbol(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getDescription(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getExchange(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getLotSize(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getLtp(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getLtp1(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getLtp2(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getChange(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getPerChange(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getAssetType(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken1(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken2(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getVolume(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getTickSize(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getMultiply_factor(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getExpiryDate(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getoptiontype(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getstrikeprice(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getinstrumentname()
                                ));

                                highestRollListToken.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken1());
                                streamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken());
                                streamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken1());
                                streamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken2());
                                rollOverStreamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken());
                                rollOverStreamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken1());
                                rollOverStreamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken2());

                            }
                        } else {
                            highestRollList.add(new GainerData(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "", "", "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }


                        if (highestRollList.size() > 0) {

                            rollOverHeaderList.add(new SubHeader("Highest Rollovers", highestRollList));
                            adapter.addSymbol(ROLL_OVER, "Highest Rollovers", highestRollListToken);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    loadLowestRollOver(expiry,service);
                }
                loadLowestRollOver(expiry,service);
            }

            @Override
            public void onFailure(String message) {
                Log.e("Failure>>>>", message);
                loadLowestRollOver(expiry,service);
            }
        });
    }
    private void loadLowestRollOver(String expiry, final String service) {

        String exchangeStr = "NSE";
        exchangeStr = exchangeStr.toLowerCase();

//       showProgress();
        // Get Request is call to LoadLowestRollOver
        WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr + "&expiry=" + expiry+"&type=looser", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {


                    streamingList = new ArrayList();
                    streamingList.clear();
                    rollOverStreamingList = new ArrayList();
                    rollOverStreamingList.clear();


                    lowestRollList = new ArrayList<GainerData>();


                    lowestRollList.clear();


                    lowestRollListToken = new ArrayList<String>();

                    rollOverDataResponse = new RollOverDataResponse();
//                   rollOverDataResponse.fromJSON(response.getJSONArray("data").getJSONObject(0));
                    JSONArray ja1 = response.getJSONArray("data");

                    ArrayList<MarketDataModel> marketDataModels = new ArrayList<>();
                    for (int i = 0; i < ja1.length(); ++i) {
                        Object o = ja1.get(i);
                        if (o instanceof JSONObject) {
                            MarketDataModel data = new MarketDataModel();
                            data.fromJSON((JSONObject) o);
                            marketDataModels.add(data);

                        }
                    }
                    rollOverDataResponse.setLooserRollerDataModelList(marketDataModels);

                    if (response.getJSONArray("data") != null) {
                        if (rollOverDataResponse.getLooserRollerDataModelList().size() > 1) {
                            for (int i = 0; i < rollOverDataResponse.getLooserRollerDataModelList().size(); i++) {

                                lowestRollList.add(new GainerData(
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getSymbol(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getDescription(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getExchange(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getLotSize(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getLtp(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getLtp1(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getLtp2(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getChange(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getPerChange(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getAssetType(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken1(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken2(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getVolume(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getTickSize(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getMultiply_factor(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getExpiryDate(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getoptiontype(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getstrikeprice(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getinstrumentname()
                                ));

                                lowestRollListToken.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken1());
                                streamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken());
                                streamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken1());
                                streamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken2());
                                rollOverStreamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken());
                                rollOverStreamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken1());
                                rollOverStreamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken2());

                            }
                        } else {
                            lowestRollList.add(new GainerData(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "", "", "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }


                        if (lowestRollList.size() > 0) {
                            rollOverHeaderList.add(new SubHeader("Lowest Rollovers", lowestRollList));
                            adapter.addSymbol(ROLL_OVER, "Lowest Rollovers", lowestRollListToken);

                        }
                        derivativeRecycleView.getRecycledViewPool().clear();
                        mainHeadersList.clear();
                        mainHeadersList.add(new GainerMainHeader(fnoActivityHeaderList, FNO_ACTIVITY));
                        mainHeadersList.add(new GainerMainHeader(optionChainHeaderList, OPTION_CHAIN));
                        mainHeadersList.add(new GainerMainHeader(openAnalysisHeaderList, OPEN_INTEREST_ANALYSIS));
                        mainHeadersList.add(new GainerMainHeader(rollOverHeaderList, ROLL_OVER));
//                        derivativeRecycleView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        refreshComplete();
                    }
                } catch (Exception e) {
                    refreshComplete();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(String message) {
                Log.e("Failure>>>>", message);
                refreshComplete();
            }
        });


    }
    public void derivativeLoadRollOverIndexData() {

        Log.e("DerivativeRoll","loadRollOverIndexData============>>>>");
        // Get Request is call to LoadRollOverIndexData
        WSHandler.getRequest(getActivity(), "getRollOverForIndex?indexName1=NIFTY50&indexName2=Nifty Bank", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.e("DerivativeRoll","loadRollOverIndexData====onSuccess========>>>>");

                adapter.getDataRollOverIndexData(response);
            }

            @Override
            public void onFailure(String message) {

            }
        });

    }
   /* private void loadRollOverData(String expiry, final String service) {

        String exchangeStr = "NSE";
        exchangeStr = exchangeStr.toLowerCase();
        rollOverHeaderList.clear();

        showProgress();
        WSHandler.getRequest(getMainActivity(), service + "?exchange=" + exchangeStr + "&expiry=" + expiry, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    hideProgress();
                    streamingList = new ArrayList();
                    streamingList.clear();
                    rollOverStreamingList = new ArrayList();
                    rollOverStreamingList.clear();

                    highestRollList = new ArrayList<GainerData>();
                    lowestRollList = new ArrayList<GainerData>();

                    highestRollList.clear();
                    lowestRollList.clear();

                    highestRollListToken = new ArrayList<String>();
                    lowestRollListToken = new ArrayList<String>();

                    rollOverDataResponse = new RollOverDataResponse();
                    rollOverDataResponse.fromJSON(response.getJSONArray("data").getJSONObject(0));

                    if (response.getJSONArray("data").getJSONObject(0).getJSONArray("gainer") != null) {
                        if (rollOverDataResponse.getGainerRollerDataModelList().size() > 1) {
                            for (int i = 0; i < rollOverDataResponse.getGainerRollerDataModelList().size(); i++) {

                                highestRollList.add(new GainerData(
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getSymbol(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getDescription(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getExchange(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getLotSize()
                                        , rollOverDataResponse.getGainerRollerDataModelList().get(i).getLtp(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getLtp1(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getLtp2(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getChange(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getPerChange(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getAssetType(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken1(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken2(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getVolume(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getTickSize(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getMultiply_factor(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getExpiryDate(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getoptiontype(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getstrikeprice(),
                                        rollOverDataResponse.getGainerRollerDataModelList().get(i).getinstrumentname()
                                ));

                                highestRollListToken.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken1());
                                streamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken());
                                streamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken1());
                                streamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken2());
                                rollOverStreamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken());
                                rollOverStreamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken1());
                                rollOverStreamingList.add(rollOverDataResponse.getGainerRollerDataModelList().get(i).getToken2());

                            }
                        } else {
                            highestRollList.add(new GainerData(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "", "", "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }
                    }

                    if (response.getJSONArray("data").getJSONObject(0).getJSONArray("looser") != null) {
                        if (rollOverDataResponse.getGainerRollerDataModelList().size() > 1) {

                            for (int i = 0; i < rollOverDataResponse.getLooserRollerDataModelList().size(); i++) {

                                lowestRollList.add(new GainerData(
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getSymbol(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getDescription(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getExchange(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getLotSize()
                                        , rollOverDataResponse.getLooserRollerDataModelList().get(i).getLtp(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getLtp1(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getLtp2(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getChange(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getPerChange(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getAssetType(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken1(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken2(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getVolume(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getTickSize(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getMultiply_factor(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getExpiryDate(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getoptiontype(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getstrikeprice(),
                                        rollOverDataResponse.getLooserRollerDataModelList().get(i).getinstrumentname()
                                ));

                                lowestRollListToken.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken1());
                                streamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken());
                                streamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken1());
                                streamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken2());
                                rollOverStreamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken());
                                rollOverStreamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken1());
                                rollOverStreamingList.add(rollOverDataResponse.getLooserRollerDataModelList().get(i).getToken2());

                            }
                        } else {
                            lowestRollList.add(new GainerData(
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "", "", "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
                        }
                    }

                    if (highestRollList.size() > 0) {

                        rollOverHeaderList.add(new SubHeader("Highest Rollovers", highestRollList));
                        adapter.addSymbol(ROLL_OVER, "Highest Rollovers", highestRollListToken);
                    }

                    if (lowestRollList.size() > 0) {

                        rollOverHeaderList.add(new SubHeader("Lowest Rollovers", lowestRollList));
                        adapter.addSymbol(ROLL_OVER, "Lowest Rollovers", lowestRollListToken);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgress();
                }
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
                refreshComplete();
            }
        });

    }*/


    public void onEventMainThread(String expiryPosition) {

        if (expiryPosition.equalsIgnoreCase("LONG BUILD UP(OI UP, PRICE UP)")) {
            openAnalysisHeaderList.clear();

            if (AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
                loadOIAnalysisLong("", "getOpenInterestAnalysis_Mobile_Redis");
            } else {
                loadOIAnalysisLong("", "getOpenInterestAnalysis_Mobile");
            }
        } else if (expiryPosition.equalsIgnoreCase("SHORT BUILD UP(OI UP, PRICE DOWN)")) {
            openAnalysisHeaderList.clear();

            if (AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
                loadOIAnalysisLong("", "getOpenInterestAnalysis_Mobile_Redis");
            } else {
                loadOIAnalysisLong("", "getOpenInterestAnalysis_Mobile");
            }

        } else if (expiryPosition.equalsIgnoreCase("LONG UNWINDING(OI DOWN, PRICE DOWN)")) {
            openAnalysisHeaderList.clear();

            if (AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
                loadOIAnalysisLong("", "getOpenInterestAnalysis_Mobile_Redis");
            } else {
                loadOIAnalysisLong("", "getOpenInterestAnalysis_Mobile");
            }
        } else if (expiryPosition.equalsIgnoreCase("SHORT COVERING (OI DOWN, PRICE UP)")) {
            openAnalysisHeaderList.clear();

            if (AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
                loadOIAnalysisLong("", "getOpenInterestAnalysis_Mobile_Redis");
            } else {
                loadOIAnalysisLong("", "getOpenInterestAnalysis_Mobile");
            }
        } else {
            if (expiryList.contains(expiryPosition)) {
                int selectedExpiry = expiryList.indexOf(expiryPosition);
                if (selectedExpiry >= 0) {

                    String selectedExpiryTimeStamp = expiryTimeStampList.get(selectedExpiry);
                    fnoActivityHeaderList = new ArrayList<SubHeader>();
                    if (AccountDetails.getIsRedisEnabled().equalsIgnoreCase("true")) {
                        loadFnoMostActive(selectedExpiryTimeStamp, "getMostActiveByVolumeExpiry_Mobile_Redis");
                    } else {
                        loadFnoMostActive(selectedExpiryTimeStamp, "getMostActiveByVolumeExpiry_Mobile");
                    }
                }
            }
        }
    }

    private void sendStreamingRequest(ArrayList<String> streamingList) {

        if (streamingList != null)
            if (streamingList.size() > 0) {
//            streamController.pauseStreaming(activity, "ltpinfo", OIAnalysysTokenList);
                streamController.sendStreamingRequest(getActivity(), streamingList, "ltpinfo", null, null, false);
                addToStreamingList("ltpinfo", streamingList);

            }
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

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        if (oiStreamingList != null && oiStreamingList.size() > 0) {
            sendStreamingRequest(oiStreamingList);

        }
        if (fnoStreamingList != null && fnoStreamingList.size() > 0) {
            sendStreamingRequest(fnoStreamingList);

        }
        if (rollOverStreamingList != null && rollOverStreamingList.size() > 0) {
            sendStreamingRequest(rollOverStreamingList);

        }

    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();

    }

}
