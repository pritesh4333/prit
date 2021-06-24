package com.acumengroup.mobile.trade;

import android.accounts.Account;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.widget.NestedScrollView;

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

import de.greenrobot.event.EventBus;

public class PlaceOrderStockDetailsFragment extends GreekBaseFragment {

    private View poStockDetailsView;
    private GreekTextView totalBUYtxt, totalSelltxt, opentxt, prevclosetxt, dayhightxt, daylowtxt, volumetxt, atptxt, WHightxt, wLowtxt, hicktlttxt, locktlttxt, mcaptxt, peratiotxt;
    private MarketsSingleScripResponse quoteResponse = null;
    private LinearLayout bottom_header, view1, view2, view3, view4, view5, view6, view7;
    private GreekTextView open, prev_close, days_high, days_low, volume_txt, atp, whight, wlow, hickt, lockt, mcap, ration, totalbuy, viewsell;
    private GreekTextView line1, line2, line3, line4, line5, line6, line7;
    private NestedScrollView stock_scroll;
    ArrayList<String> sym = new ArrayList<>();
    String token, assetType;
    static MarketsSingleScripResponse marketresponse;

    public static PlaceOrderStockDetailsFragment newInstance(String source, String type, MarketsSingleScripResponse response) {
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        PlaceOrderStockDetailsFragment fragment = new PlaceOrderStockDetailsFragment();
        fragment.setArguments(args);

        marketresponse = response;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        poStockDetailsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_po_stockdetails).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_po_stockdetails).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        setUpViews(poStockDetailsView);
        setTheam();
        return poStockDetailsView;
    }

    private void setTheam() {
        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            //stock_scroll.setBackgroundColor(getResources().getColor(R.color.white));
            bottom_header.setBackgroundColor(getResources().getColor(R.color.white));
            view1.setBackgroundColor(getResources().getColor(R.color.white));
            view2.setBackgroundColor(getResources().getColor(R.color.white));
            view3.setBackgroundColor(getResources().getColor(R.color.white));
            view4.setBackgroundColor(getResources().getColor(R.color.white));
            view5.setBackgroundColor(getResources().getColor(R.color.white));
            view6.setBackgroundColor(getResources().getColor(R.color.white));
            view7.setBackgroundColor(getResources().getColor(R.color.white));
            totalBUYtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalSelltxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            opentxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            prevclosetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dayhightxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            daylowtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            volumetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            atptxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            WHightxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            wLowtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            hicktlttxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            locktlttxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            mcaptxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            peratiotxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            open.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            prev_close.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            days_high.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            days_low.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            volume_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            atp.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            whight.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            wlow.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            hickt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lockt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            mcap.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            ration.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalbuy.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            viewsell.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            line1.setBackgroundColor(getResources().getColor(R.color.grey_textcolor));
            line2.setBackgroundColor(getResources().getColor(R.color.grey_textcolor));
            line3.setBackgroundColor(getResources().getColor(R.color.grey_textcolor));
            line4.setBackgroundColor(getResources().getColor(R.color.grey_textcolor));
            line5.setBackgroundColor(getResources().getColor(R.color.grey_textcolor));
            line6.setBackgroundColor(getResources().getColor(R.color.grey_textcolor));
            line7.setBackgroundColor(getResources().getColor(R.color.grey_textcolor));
        }
    }

    private void setUpViews(View poStockDetailsView) {
        token = AccountDetails.globalPlaceOrderBundle.getString(TOKEN);
        assetType = AccountDetails.globalPlaceOrderBundle.getString("AssetType");
        totalBUYtxt = poStockDetailsView.findViewById(R.id.totalBUYtxt);
        totalSelltxt = poStockDetailsView.findViewById(R.id.totalSelltxt);
        opentxt = poStockDetailsView.findViewById(R.id.opentxt);
        prevclosetxt = poStockDetailsView.findViewById(R.id.prevclosetxt);
        dayhightxt = poStockDetailsView.findViewById(R.id.dayhightxt);
        daylowtxt = poStockDetailsView.findViewById(R.id.daylowtxt);
        volumetxt = poStockDetailsView.findViewById(R.id.volumetxt);
        atptxt = poStockDetailsView.findViewById(R.id.atptxt);
        WHightxt = poStockDetailsView.findViewById(R.id.WHightxt);
        wLowtxt = poStockDetailsView.findViewById(R.id.wLowtxt);
        hicktlttxt = poStockDetailsView.findViewById(R.id.hicktlttxt);
        locktlttxt = poStockDetailsView.findViewById(R.id.locktlttxt);
        mcaptxt = poStockDetailsView.findViewById(R.id.mcaptxt);
        peratiotxt = poStockDetailsView.findViewById(R.id.peratiotxt);
        stock_scroll = poStockDetailsView.findViewById(R.id.stock_scroll);
        bottom_header = poStockDetailsView.findViewById(R.id.bottom_header);
        view1 = poStockDetailsView.findViewById(R.id.view1);
        view2 = poStockDetailsView.findViewById(R.id.view2);
        view3 = poStockDetailsView.findViewById(R.id.view3);
        view4 = poStockDetailsView.findViewById(R.id.view4);
        view5 = poStockDetailsView.findViewById(R.id.view5);
        view6 = poStockDetailsView.findViewById(R.id.view6);
        view7 = poStockDetailsView.findViewById(R.id.view7);
        open = poStockDetailsView.findViewById(R.id.open);
        prev_close = poStockDetailsView.findViewById(R.id.prev_close);
        days_high = poStockDetailsView.findViewById(R.id.days_high);
        days_low = poStockDetailsView.findViewById(R.id.days_low);
        volume_txt = poStockDetailsView.findViewById(R.id.volume_txt);
        atp = poStockDetailsView.findViewById(R.id.atp);
        whight = poStockDetailsView.findViewById(R.id.whight);
        wlow = poStockDetailsView.findViewById(R.id.wlow);
        hickt = poStockDetailsView.findViewById(R.id.hickt);
        lockt = poStockDetailsView.findViewById(R.id.lockt);
        mcap = poStockDetailsView.findViewById(R.id.mcap);
        ration = poStockDetailsView.findViewById(R.id.ration);
        totalbuy = poStockDetailsView.findViewById(R.id.totalbuy);
        viewsell = poStockDetailsView.findViewById(R.id.viewsell);

        line1 = poStockDetailsView.findViewById(R.id.line1);
        line2 = poStockDetailsView.findViewById(R.id.line2);
        line3 = poStockDetailsView.findViewById(R.id.line3);
        line4 = poStockDetailsView.findViewById(R.id.line4);
        line5 = poStockDetailsView.findViewById(R.id.line5);
        line6 = poStockDetailsView.findViewById(R.id.line6);
        line7 = poStockDetailsView.findViewById(R.id.line7);

        view6.setVisibility(View.GONE);// As per suggestion of Pravin pasi

        if (marketresponse != null) {
            quoteResponse = marketresponse;
            MultiQuoteResponse();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser)
            getDataFromServer();
    }

    private void getDataFromServer() {
        // TO get company summary we are requesting get request to aracane server
//        showProgress();
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
                            mcaptxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(nsemcap))));
                        } else {
                            mcaptxt.setText(StringStuff.commaDecorator(String.format("%.2f", Double.parseDouble(bsemcap))));
                        }
                        peratiotxt.setText(pe);

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


    private void MultiQuoteResponse() {
        if (quoteResponse.getTot_buyQty() != null || !quoteResponse.getTot_buyQty().equalsIgnoreCase("")) {
            totalBUYtxt.setText(quoteResponse.getTot_buyQty());
        }
        if (quoteResponse.getTot_sellQty() != null || !quoteResponse.getTot_sellQty().equalsIgnoreCase("")) {

            totalSelltxt.setText(quoteResponse.getTot_sellQty());
        }
        if (((Integer.valueOf(quoteResponse.getToken()) >= 502000000) && (Integer.valueOf(quoteResponse.getToken()) <= 502999999))
                || ((Integer.valueOf(quoteResponse.getToken()) >= 1302000000) && (Integer.valueOf(quoteResponse.getToken()) <= 1302999999))) {
            /*totalBUYtxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getTot_buyQty())));
            totalSelltxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getTot_sellQty())));*/

            opentxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getOpen())));
            prevclosetxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getClose())));

            dayhightxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getHigh())));

            daylowtxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getLow())));


            volumetxt.setText(quoteResponse.getTot_vol());

            if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("equity")) {
                if (Double.parseDouble(quoteResponse.getHigh()) > 0) {
                    if (Double.parseDouble(quoteResponse.getHigh()) > Double.parseDouble(quoteResponse.getYhigh()) && getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("equity")) {
                        WHightxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getHigh())));
                    } else {

                        WHightxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYhigh())));
                    }

                } else {
                    if (Double.parseDouble(quoteResponse.getYhigh()) != 0.00) {
                        WHightxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYhigh())));
                    }
                }
            } else if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("currency")) {
                WHightxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYhigh())));
            } else {
                WHightxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYhigh())));
            }

            if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("equity")) {
                if (Double.parseDouble(quoteResponse.getLow()) > 0) {

                    if (Double.parseDouble(quoteResponse.getLow()) > Double.parseDouble(quoteResponse.getYlow())) {

                        wLowtxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYlow())));

                    } else {


                        wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLow())));


                    }
                } else {
                    if (Double.parseDouble(quoteResponse.getYlow()) != 0.00) {
                        wLowtxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYlow())));
                    }
                }
            } else if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("currency")) {
                wLowtxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYlow())));
            } else {
                wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYlow())));
            }


            if (!quoteResponse.getAtp().equalsIgnoreCase("")) {
                atptxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getAtp())));

            }


            if (!quoteResponse.getHighRange().isEmpty()) {
                hicktlttxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getHighRange())));

            }
            if (!quoteResponse.getLowRange().isEmpty()) {
                locktlttxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getLowRange())));

            }

        } else {
           /* totalBUYtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getTot_buyQty())));
            totalSelltxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getTot_sellQty())));*/

            opentxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getOpen())));
            prevclosetxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getClose())));

            dayhightxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getHigh())));

            daylowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLow())));

            if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("equity")) {
                if (Double.parseDouble(quoteResponse.getHigh()) > 0) {
                    if (Double.parseDouble(quoteResponse.getHigh()) > Double.parseDouble(quoteResponse.getYhigh())) {
                        WHightxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getHigh())));
                    } else {

                        WHightxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYhigh())));
                    }

                } else {
                    if (Double.parseDouble(quoteResponse.getYhigh()) != 0.00) {
                        WHightxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYhigh())));
                    }
                }
            } else if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("currency")) {
                WHightxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYhigh())));
            } else {
                WHightxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYhigh())));
            }

            if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("equity")) {
                if (Double.parseDouble(quoteResponse.getLow()) > 0) {
                    if (Double.parseDouble(quoteResponse.getLow()) > Double.parseDouble(quoteResponse.getYlow())) {

                        wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYlow())));

                    } else {

                        wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLow())));

                    }
                } else {
                    if (Double.parseDouble(quoteResponse.getYlow()) != 0.00) {
                        wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYlow())));
                    }
                }
            } else if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("currency")) {
                wLowtxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYlow())));
            } else {
                wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYlow())));
            }


            volumetxt.setText(quoteResponse.getTot_vol());
            if (!quoteResponse.getAtp().equalsIgnoreCase("")) {
                atptxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getAtp())));

            }


            hicktlttxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getHighRange())));


            locktlttxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLowRange())));
        }

        sym.clear();
        sym.add(quoteResponse.getToken());
        streamController.sendStreamingRequest(getActivity(), sym, "marketPicture", null, null, true);
    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equalsIgnoreCase("marketpicture")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {
        try {

            if (quoteResponse.getToken().equals(response.getSymbol())) {
                if (response.getTot_buyQty() != null || !response.getTot_buyQty().equalsIgnoreCase("")) {
                    totalBUYtxt.setText(String.valueOf((int) Double.parseDouble(response.getTot_buyQty())));
                }
                if (response.getTot_sellQty() != null || !response.getTot_sellQty().equalsIgnoreCase("")) {

                    totalSelltxt.setText(String.valueOf((int) Double.parseDouble(response.getTot_sellQty())));
                }

                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {

                   /* totalBUYtxt.setText(String.format("%.4f", Double.parseDouble(response.getTot_buyQty())));
                    totalSelltxt.setText(String.format("%.4f", Double.parseDouble(response.getTot_sellQty())));*/

                    opentxt.setText(String.format("%.4f", Double.parseDouble(response.getOpen())));
                    prevclosetxt.setText(String.format("%.4f", Double.parseDouble(response.getClose())));
                    dayhightxt.setText(String.format("%.4f", Double.parseDouble(response.getHigh())));
                    daylowtxt.setText(String.format("%.4f", Double.parseDouble(response.getLow())));

                    if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("equity")) {
                        if (Double.parseDouble(quoteResponse.getHigh()) > 0) {
                            if (Double.parseDouble(quoteResponse.getHigh()) > Double.parseDouble(quoteResponse.getYhigh()) && getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("equity")) {
                                WHightxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getHigh())));
                            } else {

                                WHightxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYhigh())));
                            }

                        } else {
                            if (Double.parseDouble(quoteResponse.getYhigh()) != 0.00) {
                                WHightxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYhigh())));
                            }
                        }
                    } else if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("currency")) {
                        WHightxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYhigh())));
                    } else {
                        WHightxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYhigh())));
                    }

                    if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("equity")) {
                        if (Double.parseDouble(quoteResponse.getLow()) > 0) {
                            if (Double.parseDouble(quoteResponse.getLow()) > Double.parseDouble(quoteResponse.getYlow())) {

                                wLowtxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYlow())));

                            } else {
                                if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("equity")) {
                                    wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLow())));
                                } else {
                                    wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYlow())));
                                }

                            }
                        } else {
                            if (Double.parseDouble(quoteResponse.getYlow()) != 0.00) {
                                wLowtxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYlow())));
                            }
                        }
                    } else if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("currency")) {
                        wLowtxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYlow())));
                    } else {
                        wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYlow())));
                    }


                    volumetxt.setText(response.getTot_vol());
                    atptxt.setText(String.format("%.4f", Double.parseDouble(response.getATP())));

                } else {

                   /* totalBUYtxt.setText(String.format("%.2f", Double.parseDouble(response.getTot_buyQty())));
                    totalSelltxt.setText(String.format("%.2f", Double.parseDouble(response.getTot_sellQty())));*/

                    opentxt.setText(String.format("%.2f", Double.parseDouble(response.getOpen())));
                    prevclosetxt.setText(String.format("%.2f", Double.parseDouble(response.getClose())));
                    dayhightxt.setText(String.format("%.2f", Double.parseDouble(response.getHigh())));
                    daylowtxt.setText(String.format("%.2f", Double.parseDouble(response.getLow())));
                    if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("equity")) {
                        if (Double.parseDouble(quoteResponse.getHigh()) > 0) {
                            if (Double.parseDouble(quoteResponse.getHigh()) > Double.parseDouble(quoteResponse.getYhigh())) {
                                WHightxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getHigh())));
                            } else {

                                WHightxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYhigh())));
                            }

                        } else {
                            if (Double.parseDouble(quoteResponse.getYhigh()) != 0.00) {
                                WHightxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYhigh())));
                            }
                        }
                    } else if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("currency")) {
                        WHightxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYhigh())));
                    } else {
                        WHightxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYhigh())));
                    }

                    if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("equity")) {
                        if (Double.parseDouble(quoteResponse.getLow()) > 0) {
                            if (Double.parseDouble(quoteResponse.getLow()) > Double.parseDouble(quoteResponse.getYlow())) {

                                wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYlow())));


                            } else {

                                wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLow())));


                            }
                        } else {
                            if (Double.parseDouble(quoteResponse.getYlow()) != 0.00) {
                                wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYlow())));
                            }
                        }
                    } else if (getAssetTypeFromToken(quoteResponse.getToken()).equalsIgnoreCase("currency")) {
                        wLowtxt.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getYlow())));
                    } else {
                        wLowtxt.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getYlow())));
                    }

                    volumetxt.setText(response.getTot_vol());
                    atptxt.setText(String.format("%.2f", Double.parseDouble(response.getATP())));

                }
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
}