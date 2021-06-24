package com.acumengroup.mobile.trade;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.marketssinglescrip.Ask;
import com.acumengroup.greekmain.core.model.marketssinglescrip.Bid;
import com.acumengroup.greekmain.core.model.marketssinglescrip.Level2;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;

import com.acumengroup.ui.textview.GreekTextView;


import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class PlaceOrderMarketDepthFragment extends GreekBaseFragment {

    private View mChangePwdView;
    private MarketsSingleScripResponse quoteResponse = null;
    private ListView buyList, sellList;
    LinearLayout depth_header,depth_header_two;
    CustomAdapterBuy customAdapter1;
    CustomAdapterSell customAdapter2;
    GreekTextView total_sell,sell_price,sell_qty,seller,total_buy,buyers_price,buyers_qty,buyers;
    ArrayList<String> sym = new ArrayList<>();
    static MarketsSingleScripResponse marketresponse;
    private String totalBUY, totalSELL;

    public static PlaceOrderMarketDepthFragment newInstance(String source, String type, MarketsSingleScripResponse response) {
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        PlaceOrderMarketDepthFragment fragment = new PlaceOrderMarketDepthFragment();
        fragment.setArguments(args);
        marketresponse = response;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChangePwdView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_po_depth).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_po_depth).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        buyList = mChangePwdView.findViewById(R.id.buyList);
        sellList = mChangePwdView.findViewById(R.id.sellList);

        depth_header = mChangePwdView.findViewById(R.id.depth_header);
        depth_header_two = mChangePwdView.findViewById(R.id.depth_header_two);
        total_sell=mChangePwdView.findViewById(R.id.total_sell);
        sell_price=mChangePwdView.findViewById(R.id.sell_price);
        sell_qty=mChangePwdView.findViewById(R.id.sell_qty);
        seller=mChangePwdView.findViewById(R.id.seller);
        total_buy=mChangePwdView.findViewById(R.id.total_buy);
        buyers_price=mChangePwdView.findViewById(R.id.buyers_price);
        buyers_qty=mChangePwdView.findViewById(R.id.buyers_qty);
        buyers=mChangePwdView.findViewById(R.id.buyers);
        customAdapter1 = new CustomAdapterBuy(getMainActivity(), new ArrayList<Level2>());
        customAdapter2 = new CustomAdapterSell(getMainActivity(), new ArrayList<Level2>());
        buyList.setAdapter(customAdapter1);
        sellList.setAdapter(customAdapter2);
        if (marketresponse != null) {
            try {
                SingleQuoteResponse(marketresponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setTheam();
        return mChangePwdView;
    }

    private void setTheam() {
        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")){
            depth_header.setBackgroundColor(getResources().getColor(R.color.selectColor));
            depth_header_two.setBackgroundColor(getResources().getColor(R.color.selectColor));

        }
    }


    private void SingleQuoteResponse(MarketsSingleScripResponse quoteResponse) throws Exception {
//        quoteResponse = (MarketsSingleScripResponse) jsonResponse.getResponse();
        List<Level2> mdList = quoteResponse.getLevel2();
        totalBUY = quoteResponse.getTot_buyQty();
        totalSELL = quoteResponse.getTot_sellQty();

        List<Level2> broadcastlevel2 = null;
        broadcastlevel2 = customAdapter1.getData();
        for (int i = 0; i < mdList.size(); i++) {
            Level2 level2 = mdList.get(i);
            Ask a = level2.getAsk();
            Bid b = level2.getBid();


            if (!level2.getBid().getNo().equalsIgnoreCase("0")) {

                if (((Integer.valueOf(marketresponse.getToken()) >= 502000000) && (Integer.valueOf(marketresponse.getToken()) <= 502999999)) || ((Integer.valueOf(marketresponse.getToken()) >= 1302000000) && (Integer.valueOf(marketresponse.getToken()) <= 1302999999))) {
                    level2.getBid().setPrice(String.format("%.4f", Double.parseDouble(b.getPrice())));
//                    broadcastlevel2.get(i).getAsk().setPrice(String.format("%.4f", Double.parseDouble(a.getPrice())));
                    level2.getBid().setQty(b.getQty());
//                    broadcastlevel2.get(i).getAsk().setQty(a.getQty());
                    level2.getBid().setNo(b.getNo());
//                    broadcastlevel2.get(i).getAsk().setNo(a.getNo());


                } else {
                    level2.getBid().setPrice(String.format("%.2f", Double.parseDouble(b.getPrice())));
//                    broadcastlevel2.get(i).getAsk().setPrice(String.format("%.2f", Double.parseDouble(a.getPrice())));
                    level2.getBid().setQty(b.getQty());
//                    broadcastlevel2.get(i).getAsk().setQty(a.getQty());
                    level2.getBid().setNo(b.getNo());
//                    broadcastlevel2.get(i).getAsk().setNo(a.getNo());
                }
            }
            if (!level2.getAsk().getNo().equalsIgnoreCase("0")) {
                if (((Integer.valueOf(marketresponse.getToken()) >= 502000000) && (Integer.valueOf(marketresponse.getToken()) <= 502999999)) || ((Integer.valueOf(marketresponse.getToken()) >= 1302000000) && (Integer.valueOf(marketresponse.getToken()) <= 1302999999))) {
                    level2.getAsk().setPrice(String.format("%.4f", Double.parseDouble(b.getPrice())));
//                    broadcastlevel2.get(i).getAsk().setPrice(String.format("%.4f", Double.parseDouble(a.getPrice())));
                    level2.getAsk().setQty(b.getQty());
//                    broadcastlevel2.get(i).getAsk().setQty(a.getQty());
                    level2.getAsk().setNo(b.getNo());
//                    broadcastlevel2.get(i).getAsk().setNo(a.getNo());


                } else {
//                    broadcastlevel2.get(i).getBid().setPrice(String.format("%.2f", Double.parseDouble(b.getPrice())));
                    level2.getAsk().setPrice(String.format("%.2f", Double.parseDouble(a.getPrice())));
//                    broadcastlevel2.get(i).getBid().setQty(b.getQty());
                    level2.getAsk().setQty(a.getQty());
//                    broadcastlevel2.get(i).getBid().setNo(b.getNo());
                    level2.getAsk().setNo(a.getNo());
                }

            }

            if (!level2.getBid().getNo().equalsIgnoreCase("0") || !level2.getAsk().getNo().equalsIgnoreCase("0")) {
                broadcastlevel2.add(level2);
            }
        }


        customAdapter1.setData(broadcastlevel2);
        customAdapter2.setData(broadcastlevel2);
        buyList.setAdapter(customAdapter1);
        sellList.setAdapter(customAdapter2);

        customAdapter1.notifyDataSetChanged();
        customAdapter2.notifyDataSetChanged();

        sym.clear();
        sym.add(marketresponse.getToken());
        streamController.sendStreamingRequest(getActivity(), sym, "marketPicture", null, null, true);
    }


    public class CustomAdapterBuy extends BaseAdapter {
        private final Context mContext;
        List<Level2> mdList = new ArrayList<>();


        public CustomAdapterBuy(Context context, List<Level2> mdList) {
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
            final CustomAdapterBuy.Holder holder;

            if (convertView == null) {
                holder = new CustomAdapterBuy.Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_po_marketdepth, null);
                holder.notxt = convertView.findViewById(R.id.notxt);
                holder.qtytxt = convertView.findViewById(R.id.qtytxt);
                holder.pricetxt = convertView.findViewById(R.id.pricetxt);
                holder.totaltxt = convertView.findViewById(R.id.totalBUY);

                convertView.setTag(holder);
            } else {
                holder = (CustomAdapterBuy.Holder) convertView.getTag();
            }


            final Level2 model = mdList.get(position);

            holder.notxt.setText(model.getBid().getNo());
            holder.qtytxt.setText(model.getBid().getQty());
            holder.pricetxt.setText(model.getBid().getPrice());
//            holder.totaltxt.setText(totalBUY);

            //convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_dark : R.color.market_grey_light);

            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")){
                holder.notxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.qtytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.pricetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            }
            return convertView;
        }

        public class Holder {
            GreekTextView notxt, qtytxt, totaltxt, pricetxt;
        }
    }

    public class CustomAdapterSell extends BaseAdapter {
        private final Context mContext;
        List<Level2> mdList = new ArrayList<>();


        public CustomAdapterSell(Context context, List<Level2> mdList) {
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
            final CustomAdapterSell.Holder holder;

            if (convertView == null) {
                holder = new CustomAdapterSell.Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_po_marketdepth, null);
                holder.notxt = convertView.findViewById(R.id.notxt);
                holder.qtytxt = convertView.findViewById(R.id.qtytxt);
                holder.pricetxt = convertView.findViewById(R.id.pricetxt);
                holder.totalTxt = convertView.findViewById(R.id.totalBUY);

                convertView.setTag(holder);
            } else {
                holder = (CustomAdapterSell.Holder) convertView.getTag();
            }


            final Level2 model = mdList.get(position);

            holder.notxt.setText(model.getAsk().getNo());
            holder.qtytxt.setText(model.getAsk().getQty());
            holder.pricetxt.setText(model.getAsk().getPrice());
//            holder.totalTxt.setText(totalSELL);


//            convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_dark : R.color.market_grey_light);
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")){
                holder.notxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.qtytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.pricetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            }
            return convertView;
        }

        public class Holder {
            GreekTextView notxt, qtytxt, totalTxt, pricetxt;
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

            if (marketresponse.getToken().equals(response.getSymbol())) {

                List<com.acumengroup.greekmain.core.model.streamerbroadcast.Level2> mdList = response.getLevel2();
                String totalBUY = response.getTot_buyQty();
                String totalSELL = response.getTot_sellQty();

                List<Level2> broadcastlevel2 = null;
                List<Level2> listLevel = new ArrayList<>();
                broadcastlevel2 = customAdapter1.getData();
                for (int i = 0; i < mdList.size(); i++) {
                    com.acumengroup.greekmain.core.model.streamerbroadcast.Level2 level2 = mdList.get(i);
                    com.acumengroup.greekmain.core.model.streamerbroadcast.Ask a = level2.getAsk();
                    com.acumengroup.greekmain.core.model.streamerbroadcast.Bid b = level2.getBid();

                    if (((Integer.valueOf(marketresponse.getToken()) >= 502000000) && (Integer.valueOf(marketresponse.getToken()) <= 502999999)) || ((Integer.valueOf(marketresponse.getToken()) >= 1302000000) && (Integer.valueOf(marketresponse.getToken()) <= 1302999999))) {
                        broadcastlevel2.get(i).getBid().setPrice(String.format("%.4f", Double.parseDouble(b.getPrice())));
                        broadcastlevel2.get(i).getAsk().setPrice(String.format("%.4f", Double.parseDouble(a.getPrice())));
                        broadcastlevel2.get(i).getBid().setQty(b.getQty());
                        broadcastlevel2.get(i).getAsk().setQty(a.getQty());
                        broadcastlevel2.get(i).getBid().setNo(b.getNo());
                        broadcastlevel2.get(i).getAsk().setNo(a.getNo());

                        broadcastlevel2.get(i).getBid().setNo(b.getNo());
                        broadcastlevel2.get(i).getAsk().setNo(a.getNo());


                    } else {
                        broadcastlevel2.get(i).getBid().setPrice(String.format("%.2f", Double.parseDouble(b.getPrice())));
                        broadcastlevel2.get(i).getAsk().setPrice(String.format("%.2f", Double.parseDouble(a.getPrice())));
                        broadcastlevel2.get(i).getBid().setQty(b.getQty());
                        broadcastlevel2.get(i).getAsk().setQty(a.getQty());
                        broadcastlevel2.get(i).getBid().setNo(b.getNo());
                        broadcastlevel2.get(i).getAsk().setNo(a.getNo());
                    }
                }

                customAdapter1.setData(broadcastlevel2);
                customAdapter2.setData(broadcastlevel2);
                customAdapter1.notifyDataSetChanged();
                customAdapter2.notifyDataSetChanged();
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

}
