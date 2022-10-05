package com.acumengroup.mobile.symbolsearch;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.acumengroup.greekmain.core.model.AvailableSchemeListModel;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MFAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private final Context mContext;
    private List<AvailableSchemeListModel> resultList = new ArrayList<>();

    public MFAutoCompleteAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public AvailableSchemeListModel getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_mf_search_suggestions, parent, false);
        }
        viewColor(position, convertView);
        ((GreekTextView) convertView.findViewById(R.id.text2)).setText(getItem(position).getSchemeName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                try {

                    if (constraint != null) {

                        String url = "SearchSymbolMF?code=" + URLEncoder.encode(constraint.toString(), "utf-8");

                        JSONObject jsonObject = WebServiceDirectCall.getData(url);

                        JSONArray array = jsonObject.getJSONArray("data");
                        Log.d("MFSEARCH", array.toString());
                        ArrayList<AvailableSchemeListModel> modelList = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject e = array.getJSONObject(i);
                            AvailableSchemeListModel model = new AvailableSchemeListModel();
                            model.setSchemeName(e.getString("schemeName"));
                            model.setMinPurchaseAmount(e.getString("minPurchaseAmt"));
                            model.setMaxPurchaseAmount(e.getString("maxPurchaseAmt"));
                            model.setAMCName(e.getString("AMCName"));
                            model.setISIN(e.getString("ISIN"));
                            model.setSchemeCode(e.getString("schemeCode"));
                            model.setTradingISIN(e.getString("tradingISIN"));
                            model.setSipISIN(e.getString("sipISIN"));
                            model.setMfSchemeCode(e.getString("corpSchCode"));
                            //model.setSchemeType(e.getString("sip_isin"));
                            model.setNAVDate(e.getString("navDate"));
                            model.setNAV(e.getString("navValue"));
                            model.setBseRTACode(e.getString("bseRTACode"));
                            model.setBseCode(e.getString("bseCode"));
                            //model.setMinAddPurchaseAmount(e.getString("trading_isin"));
                            modelList.add(model);
                        }

                        // Assign the data to the FilterResults
                        filterResults.values = modelList;
                        filterResults.count = modelList.size();

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<AvailableSchemeListModel>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    private void viewColor(int position, View convertView) {
        String light_white = "#F5F5F5";
        String white = "#FFFFFF";
        if (position % 2 == 1) {
            convertView.findViewById(R.id.twolineListitem).setBackgroundColor(Color.parseColor(light_white));
        } else {
            convertView.findViewById(R.id.twolineListitem).setBackgroundColor(Color.parseColor(white));
        }
    }
}
