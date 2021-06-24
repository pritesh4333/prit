package com.acumengroup.mobile.symbolsearch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PeerComparisonFragment extends GreekBaseFragment {
    private ListView lv_peerComparison;
    private View ftPayInView;
    private LinearLayout ll_header_peer;
    private RelativeLayout errorMsgLayout;
    private GreekTextView errorTextView;
    GreekTextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ftPayInView = super.onCreateView(inflater, container, savedInstanceState);

        attachLayout(R.layout.fragment_peer_comparison);
        setupViews();

        return ftPayInView;
    }


    private void setupViews() {
        Bundle bundle = getArguments();
        String schemCode = bundle.getString("schemCode");
        lv_peerComparison = ftPayInView.findViewById(R.id.lv_peerComparison);
        ll_header_peer = ftPayInView.findViewById(R.id.ll_header_peer);
        errorMsgLayout = ftPayInView.findViewById(R.id.showmsgLayout);
        errorTextView = ftPayInView.findViewById(R.id.errorHeader);
        title = ftPayInView.findViewById(R.id.peer_title);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            title.setTextColor(getResources().getColor(R.color.black));
        } else {
            title.setTextColor(getResources().getColor(R.color.white));
        }

        String sipFrequency = "getPeer_Comparison?mf_schcode=" + schemCode + "&top=10";
        WSHandler.getRequest(getMainActivity(), sipFrequency, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    ArrayList<LinkedHashMap<String, String>> arrayList = new ArrayList<>();
                    LinkedHashMap<String, String> linkedHashMap;
                    JSONArray respCategory = response.getJSONArray("data");
                    for (int i = 0; i < respCategory.length(); i++) {
                        JSONObject jsonObject = respCategory.getJSONObject(i);
                        linkedHashMap = new LinkedHashMap<>();
                        linkedHashMap.put("name", jsonObject.getString("cSchemeName"));
                        linkedHashMap.put("NAV", jsonObject.getString("dNAV"));
                        linkedHashMap.put("one_year", jsonObject.getString("d1YearRet"));
                        arrayList.add(linkedHashMap);
                    }

                    if (arrayList.size() == 0) {
                        ll_header_peer.setVisibility(View.GONE);
                        errorMsgLayout.setVisibility(View.VISIBLE);
                        errorTextView.setText("No data available");
                        errorTextView.setVisibility(View.VISIBLE);
                        lv_peerComparison.setVisibility(View.GONE);
                    } else {
                        lv_peerComparison.setAdapter(new PeerCompareCustomAdapter(getMainActivity(), arrayList));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgress();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
            }
        });
    }

    public class PeerCompareCustomAdapter extends BaseAdapter {
        final ArrayList<LinkedHashMap<String, String>> strAsset;
        private LayoutInflater inflater;

        public PeerCompareCustomAdapter(Context context, ArrayList<LinkedHashMap<String, String>> strAsset) {
            this.strAsset = strAsset;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return strAsset.size();
        }

        @Override
        public LinkedHashMap<String, String> getItem(int position) {
            return strAsset.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row_performance_layout, null);
                holder = new Holder();

                holder.tv_scheme = convertView.findViewById(R.id.tv_co_name);

                holder.tv_nav_title = convertView.findViewById(R.id.txt_two_title);
                holder.tv_nav = convertView.findViewById(R.id.txt_two_value);

                holder.tv_yr_title = convertView.findViewById(R.id.txt_one_title);
                holder.tv_yr = convertView.findViewById(R.id.txt_one_value);


                holder.layout_block1 = convertView.findViewById(R.id.block_one);
                holder.layout_block2 = convertView.findViewById(R.id.block_two);
                holder.layout_block3 = convertView.findViewById(R.id.block_three);


                holder.layout_block3.setVisibility(View.GONE);


                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            LinkedHashMap<String, String> curr = getItem(position);

            holder.tv_scheme.setText(curr.get("name"));

            holder.tv_nav_title.setText("Market(Cr.)");
            holder.tv_nav.setText(curr.get("NAV"));


            holder.tv_yr_title.setText("1 Year");
            holder.tv_yr.setText(String.format("%.2f%%", Double.valueOf(curr.get("one_year"))));

            convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_1 : R.color.white);
            return convertView;
        }

        public class Holder {
            GreekTextView tv_scheme, tv_nav, tv_yr, tv_yr_title, tv_nav_title;
            LinearLayout layout_block1, layout_block2, layout_block3;

        }
    }
}
