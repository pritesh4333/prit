package com.acumengroup.mobile.markets;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.marketssinglescrip.Ask;
import com.acumengroup.greekmain.core.model.marketssinglescrip.Bid;
import com.acumengroup.greekmain.core.model.marketssinglescrip.Level2;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.OpenInterestResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.DPRUpdatedResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class MarketDepthFragment extends GreekBaseFragment {

    private View marketDepthView;
    private ArrayList<String> sym;
    private ListView marketDepthList;
    private String symbol, assetType = "";
    private GreekTextView openTxt, closeTxt, highTxt, lowTxt, volTxt, lTxt, hTxt, symbolNameTxt, ltpTxt, lttTxt, changeTxt, perChangeTxt, oiText, dprhightxt, dprlowtxt, luttext;
    private GreekTextView openLabel, closeLabel, highLabel, lowLabel, volLabel, wHigh52Label, wLow52Label, changeLabel, perChangeLabel, ltpLabel, lttLabel, openInterest, dprhighlbl, dprlowlbl, lutlbl;
    private LinearLayout symbolNameViewBg,symbolnameViewLbl,depthUpperViewBg,stripView1,stripView2,stripView3,stripView4,stripView5,stripView6,stripView7,stripView8;
    private LinearLayout view1, view2, view3, view4, view5, view6, view7, view8;
    CustomAdapter customAdapter;
    MarketsSingleScripResponse mdResponse;
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        marketDepthView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_market_depth).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_market_depth).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_MARKET_DEPTH_SCREEN;

        setupViews();
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            settingThemeAssetMarketDepth();
        }
        return marketDepthView;
    }


    private void setupViews() {

        marketDepthList = marketDepthView.findViewById(R.id.marketDepthList);

        marketDepthList.setScrollContainer(false);
        symbolNameTxt = marketDepthView.findViewById(R.id.symbolName);
        openTxt = marketDepthView.findViewById(R.id.open_txt);
        closeTxt = marketDepthView.findViewById(R.id.close_txt);
        highTxt = marketDepthView.findViewById(R.id.high_txt);
        lowTxt = marketDepthView.findViewById(R.id.low_txt);
        volTxt = marketDepthView.findViewById(R.id.vol_txt);
        lTxt = marketDepthView.findViewById(R.id.l_txt);
        hTxt = marketDepthView.findViewById(R.id.h_txt);
        ltpTxt = marketDepthView.findViewById(R.id.ltp_txt);
        lttTxt = marketDepthView.findViewById(R.id.ltt_txt);
        changeTxt = marketDepthView.findViewById(R.id.change_txt);
        perChangeTxt = marketDepthView.findViewById(R.id.perChange_txt);
        oiText = marketDepthView.findViewById(R.id.oi_text);
        dprlowtxt = marketDepthView.findViewById(R.id.dprlow_txt);
        dprhightxt = marketDepthView.findViewById(R.id.dprhigh_text);
        luttext = marketDepthView.findViewById(R.id.lut_txt);
        scrollView = marketDepthView.findViewById(R.id.scrollView);

        symbolNameTxt.setText(getArguments().getString("Description") + " - " + getArguments().getString("instName"));

//        TODO:[SUSHANT - LABELS HAVE BEEN ADDED]
        openLabel = marketDepthView.findViewById(R.id.openLbl);
        closeLabel = marketDepthView.findViewById(R.id.closeLbl);
        highLabel = marketDepthView.findViewById(R.id.highLbl);
        lowLabel = marketDepthView.findViewById(R.id.lowLbl);
        volLabel = marketDepthView.findViewById(R.id.volLbl);
        wHigh52Label = marketDepthView.findViewById(R.id.WHigh52lbl);
        wLow52Label = marketDepthView.findViewById(R.id.WLow52Lbl);
        changeLabel = marketDepthView.findViewById(R.id.changeLbl);
        perChangeLabel = marketDepthView.findViewById(R.id.pChangelbl);
        ltpLabel = marketDepthView.findViewById(R.id.ltpLbl);
        lttLabel = marketDepthView.findViewById(R.id.lttLbl);
        dprhighlbl = marketDepthView.findViewById(R.id.dprhigh);
        dprlowlbl = marketDepthView.findViewById(R.id.dprlow);
        openInterest = marketDepthView.findViewById(R.id.openInterestlbl);
        lutlbl = marketDepthView.findViewById(R.id.lut);
        symbolNameViewBg = marketDepthView.findViewById(R.id.symbolnameViewLbl);
        symbolnameViewLbl=marketDepthView.findViewById(R.id.symbolnameViewLbl);
        depthUpperViewBg=marketDepthView.findViewById(R.id.depthUpperViewBg);

        stripView1=marketDepthView.findViewById(R.id.stripView1);
        stripView2=marketDepthView.findViewById(R.id.stripView2);
        stripView3=marketDepthView.findViewById(R.id.stripView3);
        stripView4=marketDepthView.findViewById(R.id.stripView4);
        stripView5=marketDepthView.findViewById(R.id.stripView5);
        stripView6=marketDepthView.findViewById(R.id.stripView6);
        stripView7=marketDepthView.findViewById(R.id.stripView7);
        stripView8=marketDepthView.findViewById(R.id.stripView8);



        setAppTitle(getClass().toString(), "Market Depth");
        setupAdapter();
    }


    private void settingThemeAssetMarketDepth() {
        symbolnameViewLbl.setBackgroundColor(getResources().getColor(R.color.white));
        depthUpperViewBg.setBackgroundColor(getResources().getColor(R.color.white));
        stripView1.setBackgroundColor(getResources().getColor(R.color.white));
        stripView2.setBackgroundColor(getResources().getColor(R.color.white));
        stripView3.setBackgroundColor(getResources().getColor(R.color.white));
        stripView4.setBackgroundColor(getResources().getColor(R.color.white));
        stripView5.setBackgroundColor(getResources().getColor(R.color.white));
        stripView6.setBackgroundColor(getResources().getColor(R.color.white));
        stripView7.setBackgroundColor(getResources().getColor(R.color.white));
        stripView8.setBackgroundColor(getResources().getColor(R.color.white));
        symbolNameTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        openTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        closeTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        highTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        lowTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        volTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        lTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        hTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        ltpTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        lttTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        changeTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        perChangeTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        oiText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        dprhightxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        dprlowtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        luttext.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        openLabel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        closeLabel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        highLabel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        lowLabel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        volLabel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        wHigh52Label.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        wLow52Label.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        changeLabel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        perChangeLabel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        ltpLabel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        lttLabel.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        openInterest.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        dprlowlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        dprhighlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        lutlbl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        symbolNameViewBg.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        marketDepthList.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));


    }

    private void setupAdapter() {
        symbol = getArguments().getString("Symbol");
        assetType = getArguments().getString("AssetType");

        if (symbol != null && assetType != null) {
            showProgress();
            sendMDRequest();
        }

    }


    private void sendMDRequest() {

        MarketsSingleScripRequest.sendRequest(AccountDetails.getUsername(getMainActivity()),
                symbol, assetType, AccountDetails.getClientCode(getMainActivity()), getMainActivity(), serviceResponseHandler);
    }


    @Override
    public void handleResponse(Object response) {
        hideProgress();
        JSONResponse jsonResponse = (JSONResponse) response;
        if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SINGLEQUOTE_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                mdResponse = (MarketsSingleScripResponse) jsonResponse.getResponse();
                if (mdResponse.getAsset_type().equalsIgnoreCase("currency") || mdResponse.getAsset_type().equalsIgnoreCase("6")) {
                    openTxt.setText(String.format("%.4f", Double.parseDouble(mdResponse.getOpen())));
                    closeTxt.setText(String.format("%.4f", Double.parseDouble(mdResponse.getClose())));
                    highTxt.setText(String.format("%.4f", Double.parseDouble(mdResponse.getHigh())));
                    lowTxt.setText(String.format("%.4f", Double.parseDouble(mdResponse.getLow())));
                    ltpTxt.setText(String.format("%.4f", Double.parseDouble(mdResponse.getLast())));
                    changeTxt.setText(String.format("%.4f", Double.parseDouble(mdResponse.getChange())));
                    lTxt.setText(String.format("%.4f", Double.parseDouble(mdResponse.getYlow())));
                    hTxt.setText(String.format("%.4f", Double.parseDouble(mdResponse.getYhigh())));
                    dprlowtxt.setText(String.format("%.4f", Double.parseDouble(mdResponse.getLowRange())));
                    dprhightxt.setText(String.format("%.4f", Double.parseDouble(mdResponse.getHighRange())));
                    oiText.setText(mdResponse.getOi());
                } else {
                    openTxt.setText(String.format("%.2f", Double.parseDouble(mdResponse.getOpen())));
                    closeTxt.setText(String.format("%.2f", Double.parseDouble(mdResponse.getClose())));
                    highTxt.setText(String.format("%.2f", Double.parseDouble(mdResponse.getHigh())));
                    lowTxt.setText(String.format("%.2f", Double.parseDouble(mdResponse.getLow())));
                    ltpTxt.setText(String.format("%.2f", Double.parseDouble(mdResponse.getLast())));
                    changeTxt.setText(String.format("%.2f", Double.parseDouble(mdResponse.getChange())));
                    lTxt.setText(String.format("%.2f", Double.parseDouble(mdResponse.getYlow())));
                    hTxt.setText(String.format("%.2f", Double.parseDouble(mdResponse.getYhigh())));
                    dprlowtxt.setText(String.format("%.2f", Double.parseDouble(mdResponse.getLowRange())));
                    dprhightxt.setText(String.format("%.2f", Double.parseDouble(mdResponse.getHighRange())));
                    oiText.setText(mdResponse.getOi());

                }


                if (getExchange(mdResponse.getToken()).equalsIgnoreCase("mcx")) {

                    if(mdResponse.getOptiontype().equalsIgnoreCase("XX"))
                    {
                        symbolNameTxt.setText(mdResponse.getSymbol() + DateTimeFormatter.getDateFromTimeStamp(mdResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() +"-" + mdResponse.getInstrument());
                    }
                    else {
                        symbolNameTxt.setText(mdResponse.getSymbol() + DateTimeFormatter.getDateFromTimeStamp(mdResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + mdResponse.getStrikeprice() + mdResponse.getOptiontype()+"-" + mdResponse.getInstrument());
                    }


                } else {

                    symbolNameTxt.setText(mdResponse.getDescription() + " - " + mdResponse.getInstrument());
                }

                perChangeTxt.setText(String.format("%.2f", Double.parseDouble(mdResponse.getP_change())));
                volTxt.setText(mdResponse.getTot_vol());
                lttTxt.setText(mdResponse.getLtt());
                luttext.setText(mdResponse.getLut());


                List<Level2> mdList = mdResponse.getLevel2();

                customAdapter = new CustomAdapter(getMainActivity(), mdList);
                List<Level2> broadcastlevel2 = null;
                List<Level2> listLevel = new ArrayList<>();
                broadcastlevel2 = customAdapter.getData();
                for (int i = 0; i < mdList.size(); i++) {
                    Level2 level2 = mdList.get(i);
                    Ask a = level2.getAsk();
                    Bid b = level2.getBid();

                    if (((Integer.valueOf(mdResponse.getToken()) >= 502000000) && (Integer.valueOf(mdResponse.getToken()) <= 502999999)) || ((Integer.valueOf(mdResponse.getToken()) >= 1302000000) && (Integer.valueOf(mdResponse.getToken()) <= 1302999999))) {
                        broadcastlevel2.get(i).getBid().setPrice(String.format("%.4f", Double.parseDouble(b.getPrice())));
                        broadcastlevel2.get(i).getAsk().setPrice(String.format("%.4f", Double.parseDouble(a.getPrice())));
                        broadcastlevel2.get(i).getBid().setQty(b.getQty());
                        broadcastlevel2.get(i).getAsk().setQty(a.getQty());


                    } else {
                        broadcastlevel2.get(i).getBid().setPrice(String.format("%.2f", Double.parseDouble(b.getPrice())));
                        broadcastlevel2.get(i).getAsk().setPrice(String.format("%.2f", Double.parseDouble(a.getPrice())));
                        broadcastlevel2.get(i).getBid().setQty(b.getQty());
                        broadcastlevel2.get(i).getAsk().setQty(a.getQty());
                    }
                }

                customAdapter.setData(broadcastlevel2);
                marketDepthList.setAdapter(customAdapter);

                customAdapter.notifyDataSetChanged();
                justifyListViewHeightBasedOnChildren(marketDepthList);
                sym = new ArrayList<>();
                sym.add(symbol);
                addToStreamingList("marketPicture", sym);

                streamController.sendStreamingRequest(getMainActivity(), sym, "marketPicture", null, null, true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();

        AccountDetails.currentFragment = NAV_TO_MARKET_DEPTH_SCREEN;
        EventBus.getDefault().register(this);
        if (sym != null) {
            streamController.sendStreamingRequest(getMainActivity(), sym, "marketPicture", null, null, true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        AccountDetails.globalArg = getArguments();
        streamController.pauseStreaming(getMainActivity(), "marketPicture", sym);
        super.onFragmentPause();
    }

    private String getExchange(String token) {
        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NCDEX";
        } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "MCX";
        } else {
            return "BSE";
        }
    }

    //TODO: Save current script's token and assetType in SharedPreferences


    @Override
    public void onResume() {
        super.onResume();
        if (sym != null) {
            streamController.sendStreamingRequest(getMainActivity(), sym, "marketPicture", null, null, true);
        }
    }

    @Override
    public void onFragmentPause() {
        if (streamController != null) {
            streamController.pauseStreaming(getMainActivity(), "marketPicture", sym);
            EventBus.getDefault().unregister(this);
        }
        super.onFragmentPause();
    }

    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equals("marketPicture")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void onEventMainThread(final DPRUpdatedResponse dprUpdatedResponse) {
        try {
            GreekDialog.alertDialog(getMainActivity(), 0, GREEK, dprUpdatedResponse.getMessage(), "OK", false, new GreekDialog.DialogListener() {

                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    if (dprUpdatedResponse.getToken().equalsIgnoreCase(symbol)) {
                        sendMDRequest();
                    }
                }
            });

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    private void updateBroadcastData(StreamerBroadcastResponse response) {
        if (symbol.equals(response.getSymbol())) {
            if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                openTxt.setText(String.format("%.4f", Double.parseDouble(response.getOpen())));
                closeTxt.setText(String.format("%.4f", Double.parseDouble(response.getClose())));
                highTxt.setText(String.format("%.4f", Double.parseDouble(response.getHigh())));
                lowTxt.setText(String.format("%.4f", Double.parseDouble(response.getLow())));
                ltpTxt.setText(String.format("%.4f", Double.parseDouble(response.getLast())));
                changeTxt.setText(String.format("%.4f", Double.parseDouble(response.getChange())));

            } else {
                openTxt.setText(String.format("%.2f", Double.parseDouble(response.getOpen())));
                closeTxt.setText(String.format("%.2f", Double.parseDouble(response.getClose())));
                highTxt.setText(String.format("%.2f", Double.parseDouble(response.getHigh())));
                lowTxt.setText(String.format("%.2f", Double.parseDouble(response.getLow())));
                ltpTxt.setText(String.format("%.2f", Double.parseDouble(response.getLast())));
                changeTxt.setText(String.format("%.2f", Double.parseDouble(response.getChange())));

            }

            if (response.getExch().equalsIgnoreCase("BSE")) {
                hTxt.setText(String.format("%.2f", Double.parseDouble(response.getYhigh())));
                lTxt.setText(String.format("%.2f", Double.parseDouble(response.getYlow())));
            }

            volTxt.setText(String.format("%s", response.getTot_vol()));

            perChangeTxt.setText(String.format("%.2f", Double.parseDouble(response.getP_change())));
            lttTxt.setText(response.getLtt());
            luttext.setText(response.getLut());




            List<com.acumengroup.greekmain.core.model.streamerbroadcast.Level2> mdList = response.getLevel2();

            List<Level2> broadcastlevel2 = null;
            List<Level2> listLevel = new ArrayList<>();
            broadcastlevel2 = customAdapter.getData();
            for (int i = 0; i < mdList.size(); i++) {
                com.acumengroup.greekmain.core.model.streamerbroadcast.Level2 level2 = mdList.get(i);
                com.acumengroup.greekmain.core.model.streamerbroadcast.Ask a = level2.getAsk();
                com.acumengroup.greekmain.core.model.streamerbroadcast.Bid b = level2.getBid();

                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    broadcastlevel2.get(i).getBid().setPrice(String.format("%.4f", Double.parseDouble(b.getPrice())));
                    broadcastlevel2.get(i).getAsk().setPrice(String.format("%.4f", Double.parseDouble(a.getPrice())));
                    broadcastlevel2.get(i).getBid().setQty(b.getQty());
                    broadcastlevel2.get(i).getAsk().setQty(a.getQty());


                } else {
                    broadcastlevel2.get(i).getBid().setPrice(String.format("%.2f", Double.parseDouble(b.getPrice())));
                    broadcastlevel2.get(i).getAsk().setPrice(String.format("%.2f", Double.parseDouble(a.getPrice())));
                    broadcastlevel2.get(i).getBid().setQty(b.getQty());
                    broadcastlevel2.get(i).getAsk().setQty(a.getQty());
                }
            }

            customAdapter.setData(broadcastlevel2);
            customAdapter.notifyDataSetChanged();
        }
    }

    //TODO: Setting open interest on market depth also
    public void onEventMainThread(OpenInterestResponse openInterestResponse) {
        if (sym.get(0).equalsIgnoreCase(openInterestResponse.getToken()))
            if (!openInterestResponse.getCurrentOI().equalsIgnoreCase("0")) {
                oiText.setText(String.format("%s", openInterestResponse.getCurrentOI()));
            }
    }


    public class CustomAdapter extends BaseAdapter {
        private final Context mContext;
        List<Level2> mdList = new ArrayList<>();


        public CustomAdapter(Context context, List<Level2> mdList) {
            this.mContext = context;
            this.mdList = mdList;
        }

        public void clear() {
            mdList.clear();
        }

        @Override
        public int getCount() {
            return mdList.size();
        }

        @Override
        public Object getItem(int position) {
            return mdList.get(position);
        }

        public List<Level2> getData() {
            return mdList;
        }

        public List<Level2> setData(List<Level2> mdList) {
            this.mdList = mdList;
            return this.mdList;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final MarketDepthFragment.CustomAdapter.Holder holder;

            if (convertView == null) {
                holder = new MarketDepthFragment.CustomAdapter.Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_market_depth, null);
                holder.row1 = convertView.findViewById(R.id.row11);
                holder.row2 = convertView.findViewById(R.id.row12);
                holder.row3 = convertView.findViewById(R.id.row13);
                holder.row4 = convertView.findViewById(R.id.row14);

                convertView.setTag(holder);
            } else {
                holder = (MarketDepthFragment.CustomAdapter.Holder) convertView.getTag();
            }


            int flashBluecolor, flashRedColor,greentextcolor,redtextcolor;

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
               /* flashBluecolor = R.color.light_green_textcolor;
                flashRedColor = R.color.light_red_negative;
                greentextcolor = R.color.dark_green_positive;
                redtextcolor = R.color.dark_red_negative;*/

                flashBluecolor = R.drawable.light_blue_positive;
                flashRedColor = R.drawable.light_red_negative;
                greentextcolor = R.color.dark_blue_positive;
                redtextcolor = R.color.dark_red_negative;

                holder.row1.setBackground(getResources().getDrawable(flashBluecolor));
                holder.row2.setBackground(getResources().getDrawable(flashBluecolor));
                holder.row3.setBackground(getResources().getDrawable(flashRedColor));
                holder.row4.setBackground(getResources().getDrawable(flashRedColor));
            } else {
                flashBluecolor = R.color.light_green_textcolor;
                flashRedColor = R.color.light_red_negative;
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    greentextcolor = R.color.whitetheambuyColor;
                } else {
                    greentextcolor = R.color.dark_green_positive;
                }

                redtextcolor = R.color.dark_red_negative;

                holder.row1.setBackgroundColor(getResources().getColor(flashBluecolor));
                holder.row2.setBackgroundColor(getResources().getColor(flashBluecolor));
                holder.row3.setBackgroundColor(getResources().getColor(flashRedColor));
                holder.row4.setBackgroundColor(getResources().getColor(flashRedColor));
            }




            holder.row1.setTextColor(getResources().getColor(greentextcolor));
            holder.row2.setTextColor(getResources().getColor(greentextcolor));
            holder.row3.setTextColor(getResources().getColor(redtextcolor));
            holder.row4.setTextColor(getResources().getColor(redtextcolor));


            final Level2 model = mdList.get(position);

            //holder.row1.setText(String.format("%.2f", Double.parseDouble(model.getBid().getPrice())));
            holder.row1.setText(model.getBid().getPrice());
            holder.row2.setText(model.getBid().getQty());
            //holder.row3.setText(String.format("%.2f", Double.parseDouble(model.getAsk().getPrice())));
            holder.row3.setText(model.getAsk().getPrice());
            holder.row4.setText(model.getAsk().getQty());


            holder.row1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                        if (getArguments().containsKey("isSquareOff") && !getArguments().getBoolean("isSquareOff") && !getArguments().getBoolean("isModifyOrder")) {
                            Bundle args = new Bundle();
                            args = getArguments();
                            args.putString("TradeSymbol", mdResponse.getSymbol());
                            args.putString("Token", mdResponse.getToken());
                            args.putString("Lots", mdResponse.getLot());
                            args.putString("Action", "sell");
                            args.putString("AssetType", mdResponse.getAsset_type());
                            args.putString("instName", mdResponse.getInstrument());
                            args.putString("ScripName", mdResponse.getDescription());
                            args.putString("ExchangeName", mdResponse.getExch());
                            args.putString("MdPrice", holder.row1.getText().toString());
                            args.putString(TradeFragment.TRADE_ACTION, "sell");
                            args.putString("Multiplier", getArguments().getString(MULTIPLIER));
                            args.putString("TickSize", getArguments().getString(TICKSIZE));

                            if (getArguments().getBundle("BundleFromTrade") != null) {
                                args.putBundle("BundleFromDepth", getArguments().getBundle("BundleFromTrade"));
                            }
                            navigateTo(NAV_TO_TRADE_SCREEN, args, true);
                        } else if (!getArguments().containsKey("isSquareOff")) {
                            Bundle args = new Bundle();
                            args = getArguments();
                            args.putString("TradeSymbol", mdResponse.getSymbol());
                            args.putString("Token", mdResponse.getToken());
                            args.putString("Lots", mdResponse.getLot());
                            args.putString("Action", "sell");
                            args.putString("AssetType", mdResponse.getAsset_type());
                            args.putString("instName", mdResponse.getInstrument());
                            args.putString("ScripName", mdResponse.getDescription());
                            args.putString("ExchangeName", mdResponse.getExch());
                            args.putString("MdPrice", holder.row1.getText().toString());
                            args.putString(TradeFragment.TRADE_ACTION, "sell");
                            args.putString("Multiplier", getArguments().getString(MULTIPLIER));
                            args.putString("TickSize", getArguments().getString(TICKSIZE));

                            if (getArguments().getBundle("BundleFromTrade") != null) {
                                args.putBundle("BundleFromDepth", getArguments().getBundle("BundleFromTrade"));
                            }
                            navigateTo(NAV_TO_TRADE_SCREEN, args, true);
                        }

                    } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", true, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    } else {
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            }
                        });
                    }
                }
            });

            holder.row3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER || GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {
                        if (getArguments().containsKey("isSquareOff") && !getArguments().getBoolean("isSquareOff") && !getArguments().getBoolean("isModifyOrder")) {
                            Bundle args = new Bundle();
                            args = getArguments();
                            args.putString("TradeSymbol", mdResponse.getSymbol());
                            args.putString("Token", mdResponse.getToken());
                            args.putString("Lots", mdResponse.getLot());
                            args.putString("Action", "buy");
                            args.putString("AssetType", mdResponse.getAsset_type());
                            args.putString("instName", mdResponse.getInstrument());
                            args.putString("ScripName", mdResponse.getDescription());
                            args.putString("ExchangeName", mdResponse.getExch());
                            args.putString("MdPrice", holder.row3.getText().toString());
                            args.putString(TradeFragment.TRADE_ACTION, "buy");
                            args.putString("Multiplier", getArguments().getString(MULTIPLIER));
                            args.putString("TickSize", getArguments().getString(TICKSIZE));
                            navigateTo(NAV_TO_TRADE_SCREEN, args, true);
                        } else if (!getArguments().containsKey("isSquareOff")) {
                            Bundle args = new Bundle();
                            args.putString("TradeSymbol", mdResponse.getSymbol());
                            args.putString("Token", mdResponse.getToken());
                            args.putString("Lots", mdResponse.getLot());
                            args.putString("Action", "buy");
                            args.putString("AssetType", mdResponse.getAsset_type());
                            args.putString("instName", mdResponse.getInstrument());
                            args.putString("ScripName", mdResponse.getDescription());
                            args.putString("ExchangeName", mdResponse.getExch());
                            args.putString("MdPrice", holder.row3.getText().toString());
                            args.putString(TradeFragment.TRADE_ACTION, "buy");
                            args.putString("Multiplier", getArguments().getString(MULTIPLIER));
                            args.putString("TickSize", getArguments().getString(TICKSIZE));
                            navigateTo(NAV_TO_TRADE_SCREEN, args, true);
                        }
                    } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, getResources().getString(R.string.MESSAGE_DISPLAY_IBT), "Ok", true, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            }
                        });
                    } else {
                        GreekDialog.alertDialog(getMainActivity(), 0, GreekBaseActivity.GREEK, "Login to enjoy the services", "Ok", false, new GreekDialog.DialogListener() {
                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                            }
                        });
                    }
                }


//                }
            });

            return convertView;
        }

        public class Holder {
            GreekTextView row1, row2, row3, row4;
        }
    }


    public static void justifyListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }


}