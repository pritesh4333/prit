package com.acumengroup.mobile.symbolsearch;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.ScripModel;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiren on 17-09-2015.
 */
public class ChartSymbolAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private final LayoutInflater inflater;
    private List<ScripModel> resultList = new ArrayList<>();
    private String assetType, exchangeType, instName;
    private Context context;

    public ChartSymbolAutoCompleteAdapter(Context context, String exchangeType, String assetType) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        HolderView holderView;

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

        holderView.buyTxt.setVisibility(View.GONE);
        holderView.sellTxt.setVisibility(View.GONE);
        holderView.chartTxt.setVisibility(View.GONE);

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
            holderView.text.setText(getItem(position).getTradeSymbol() + " - " + getItem(position).getSeries());
            holderView.exchange.setText(getItem(position).getExchange());
            holderView.description.setText(getItem(position).getDescription());

        }

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
                    if (constraint != null && !URLEncoder.encode((constraint.toString())).isEmpty()) {
                        assetType = assetType.trim();
                        if (assetType.equalsIgnoreCase("mutual fund")) {
                            assetType = "mutualfund";
                        }

//  When user search any script, getSymbolLookup_Bajaj_Chart request is send to server  and script list is displayed with details.
                        String url = "";
                        if (!assetType.equalsIgnoreCase("options")) {
                            url = "getSymbolLookup_Bajaj_Chart?assetType=" + URLEncoder.encode(assetType, "utf-8").toLowerCase() + "&code=" + URLEncoder.encode((constraint.toString()));
                        } else {
                            url = "getSymbolLookup_Bajaj_Chart?assetType=" + URLEncoder.encode("option", "utf-8").toLowerCase() + "&code=" + URLEncoder.encode((constraint.toString()));
                        }

                        JSONObject jsonObject = WebServiceDirectCall.getData(url);
                        JSONArray array = null;
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
                    notifyDataSetInvalidated();
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

    class HolderView {
        GreekTextView text;
        GreekTextView exchange;
        GreekTextView description;
        GreekTextView buyTxt, sellTxt, chartTxt;

        public HolderView(View parent) {
            buyTxt = parent.findViewById(R.id.buyTxt);
            sellTxt = parent.findViewById(R.id.sellTxt);
            chartTxt = parent.findViewById(R.id.chartTxt);
            text = parent.findViewById(R.id.text2);
            exchange = parent.findViewById(R.id.tvExchange);
            description = parent.findViewById(R.id.desc);
            if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                text.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));
                exchange.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));
                description.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));
            }
        }
    }
}