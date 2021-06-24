package com.acumengroup.mobile.symbolsearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.acumengroup.mobile.chartiqscreen.MainActivity;
import com.acumengroup.mobile.trade.RotateChartActivity;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.ui.button.GreekButton;
import com.loopj.android.http.Base64;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.ScripModel;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Hiren on 17-09-2015.
 */
public class SymbolAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private final LayoutInflater inflater;
    private List<ScripModel> resultList = new ArrayList<>();
    private String assetType, exchangeType, instName;
    private Context context;


    public SymbolAutoCompleteAdapter(Context context, String exchangeType, String assetType) {
        this.inflater = LayoutInflater.from(context);
        this.assetType = assetType;
        this.exchangeType = exchangeType;
        this.context = context;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }


    public void setInstName(String instName) {
        this.instName = instName;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public ScripModel getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        HolderView holderView;

//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.row_symbol_search_suggestions, parent, false);
//            holderView = new HolderView(convertView);
//            convertView.setTag(holderView);
//        } else {
//            holderView = (HolderView) convertView.getTag();
//        }

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_symbol_search_suggestions, parent, false);
            holderView = new HolderView(convertView);
            convertView.setTag(holderView);

        } else {

            holderView = (HolderView) convertView.getTag();
        }


        if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {

            convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
        } else {
            convertView.setBackgroundResource(position % 2 == 0 ? R.drawable.single_border_odd : R.drawable.single_border_even);
        }

        if (assetType.equals("Equity")) {

            holderView.text.setText(getItem(position).getTradeSymbol() + " - " + getItem(position).getSeries());
            holderView.exchange.setText(getItem(position).getExchange());
            holderView.description.setText(getItem(position).getDescription());
            //holderView.description.setVisibility(View.GONE);
        } else if (assetType.equalsIgnoreCase("MutualFund")) {

            viewColor(position, convertView);
            holderView.text.setText(getItem(position).getSchemeName());
            holderView.exchange.setText(getItem(position).getCorp_isin());
            holderView.description.setVisibility(View.GONE);

        } else {
            //viewColor(position, convertView);
//            holderView.text.setText(getItem(position).getTradeSymbol() + " - " + getItem(position).getSeries());
            holderView.exchange.setText(getItem(position).getExchange());
            holderView.text.setText(getItem(position).getScriptName());
//            holderView.text.setText(getItem(position).getDescription());
            holderView.description.setText(getItem(position).getDetailName());

        }

        holderView.buyTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args2 = new Bundle();
                args2.putString(TradeFragment.SCRIP_NAME, getItem(position).getName());
                args2.putString(TradeFragment.EXCHANGE_NAME, getItem(position).getExchange());
                args2.putString(TradeFragment.TOKEN, getItem(position).getToken());
                args2.putString(TradeFragment.TICK_SIZE, getItem(position).getTickSize());
                args2.putString(TradeFragment.TRADE_ACTION, "Buy");
                AccountDetails.globalPlaceOrderBundle = args2;
                EventBus.getDefault().post("placeorder");
            }
        });

        holderView.sellTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args2 = new Bundle();
                args2.putString(TradeFragment.SCRIP_NAME, getItem(position).getName());
                args2.putString(TradeFragment.EXCHANGE_NAME, getItem(position).getExchange());
                args2.putString(TradeFragment.TOKEN, getItem(position).getToken());
                args2.putString(TradeFragment.TICK_SIZE, getItem(position).getTickSize());
                args2.putString(TradeFragment.TRADE_ACTION, "Sell");
                AccountDetails.globalPlaceOrderBundle = args2;
                EventBus.getDefault().post("placeorder");

            }
        });
        holderView.chartTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("Scrip", getItem(position).getDescription() + " " + getExchange(getItem(position).getToken()) + " |");
                bundle.putString("Token", getItem(position).getToken());
                bundle.putString("TradeSymbol", AccountDetails.globalPlaceOrderBundle.getString("TradeSymbol"));
                bundle.putString("description", "");
                bundle.putString("ltp", getItem(position).getLtp());
                bundle.putString("Lots", AccountDetails.globalPlaceOrderBundle.getString("Lots"));
                bundle.putString("Action", "1");
                bundle.putString("AssetType", AccountDetails.globalPlaceOrderBundle.getString("AssetType"));
                bundle.putString("from", "placeorderchart");
                bundle.putBoolean("iscurrency", false);
                AccountDetails.setIsMainActivity(true);
                if (AccountDetails.getChartSetting().equalsIgnoreCase("m4")) {
                    Intent rotatechart = new Intent(context, RotateChartActivity.class);
                    rotatechart.putExtras(bundle);
                    context.startActivity(rotatechart);
                } else {
                    Intent rotatechart = new Intent(context, MainActivity.class);
                    rotatechart.putExtras(bundle);
                    context.startActivity(rotatechart);
                }
            }
        });

        if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
            holderView.text.setTextColor(context.getResources().getColor(R.color.black));
            holderView.exchange.setTextColor(context.getResources().getColor(R.color.black));
            holderView.description.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            convertView.setBackgroundResource(position % 2 == 0 ? R.drawable.single_border_odd : R.drawable.single_border_even);
        }


        return convertView;
    }

    @Override
    public Filter getFilter() {


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                try {
                    //0032163 Reason was code parameter passing blank value and url responses  is coming very late that's why checked if is it not empty case.
                    if (constraint != null && !encodeToBase64(constraint.toString()).isEmpty()) {
                        assetType = assetType.trim();
                        if (assetType.equalsIgnoreCase("mutual fund")) {
                            assetType = "mutualfund";
                        }
                        String url = "";
                        if (!assetType.equalsIgnoreCase("options")) {
                            url = "getSymbolLookup_Bajaj_Mobile?assetType=" + URLEncoder.encode(assetType, "utf-8").toLowerCase() + "&code=" + encodeToBase64(constraint.toString());
                        } else {
                            url = "getSymbolLookup_Bajaj_Mobile?assetType=" + URLEncoder.encode("option", "utf-8").toLowerCase() + "&code=" + encodeToBase64(constraint.toString());
                        }
                        Log.e("SymbolSearch","SymbolSearch=====>>"+url);
                        JSONObject jsonObject = WebServiceDirectCall.getData(url);
                        JSONArray array = null;
                        Log.e("SymbolSearch","=====>>"+jsonObject.toString());
                        if (jsonObject.has("data")) {
                            array = jsonObject.getJSONArray("data");


                            ArrayList<ScripModel> modelList = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject e = array.getJSONObject(i);
                                ScripModel model = new ScripModel();
                                model.fromJSONObject(e);
                                modelList.add(model);
                            }
                            // Assign the data to the FilterResults
                            filterResults.values = modelList;
                            filterResults.count = modelList.size();
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<ScripModel>) results.values;
                    notifyDataSetChanged();
                } else {
                    resultList.clear();
                    notifyDataSetChanged();
                    // notifyDataSetInvalidated();
                }
            }
        };
    }

    private void viewColor(int position, View convertView) {
        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
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

    class HolderView {

        GreekTextView text;
        GreekTextView exchange;
        GreekTextView description;
        GreekTextView buyTxt, sellTxt, chartTxt;

        public HolderView(View parent) {
            text = parent.findViewById(R.id.text2);
            exchange = parent.findViewById(R.id.tvExchange);
            buyTxt = parent.findViewById(R.id.buyTxt);
            sellTxt = parent.findViewById(R.id.sellTxt);
            chartTxt = parent.findViewById(R.id.chartTxt);
            description = parent.findViewById(R.id.desc);
        }
    }

    public String encodeToBase64(String stringToEncode) {
        byte[] data = new byte[0];
        data = stringToEncode.getBytes(StandardCharsets.UTF_8);
        String encyrpt = Base64.encodeToString(data, Base64.NO_WRAP);

        return encyrpt;
    }
}