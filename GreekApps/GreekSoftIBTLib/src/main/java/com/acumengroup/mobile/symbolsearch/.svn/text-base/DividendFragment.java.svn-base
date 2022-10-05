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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.DivinModel;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DividendFragment extends GreekBaseFragment {
    private ListView lv_dividend;
    private View ftPayInView;
    private LinearLayout ll_header_dividend;
    private RelativeLayout errorMsgLayout;
    private GreekTextView errorTextView;
    private List<DivinModel> arraylist_divin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ftPayInView = super.onCreateView(inflater, container, savedInstanceState);

        attachLayout(R.layout.fragment_dividend);
        arraylist_divin = new ArrayList<>();
        setupViews();

        return ftPayInView;
    }


    private void setupViews() {
        Bundle bundle = getArguments();
        String schemCode = bundle.getString("schemCode");
        errorMsgLayout = ftPayInView.findViewById(R.id.showmsgLayout);
        errorTextView = ftPayInView.findViewById(R.id.errorHeader);

        lv_dividend = ftPayInView.findViewById(R.id.lv_dividend);
        ll_header_dividend = ftPayInView.findViewById(R.id.ll_header_dividend);

        String accord_Token = Util.getPrefs(getContext()).getString("accord_Token", null);


        accordServiceRequest(" ", " ", schemCode, " ",
                " ", "1", "30", " ", " ", accord_Token);

    }


    private void accordServiceRequest(final String FundCode, final String CategoryCode, final String SchemeCode,
                                      final String StartDate, final String EndDate, final String PageNo,
                                      final String Pagesize, final String SortExpression, final String SortDirection, final String token) {


        String url = "http://mf.accordwebservices.com/MF/GetDividendDetails?FundCode=&CategoryCode=&SchemeCode=" + SchemeCode + "&StartDate&EndDate&PageNo=1&Pagesize=30&SortExpression=&SortDirection=&token=" + token;
        showProgress();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgress();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("Table1");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        DivinModel divinModel = new DivinModel();

                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String srno = jsonObject1.getString("srno");
                        String CLASSNAME = jsonObject1.getString("CLASSNAME");
                        String SCHEMECODE = jsonObject1.getString("SCHEMECODE");
                        String S_NAME = jsonObject1.getString("S_NAME");
                        String dividentRs = jsonObject1.getString("dividentRs");
                        String oneWEEKRET = jsonObject1.getString("1WEEKRET");
                        String oneMONTHRET = jsonObject1.getString("1MONTHRET");
                        String threeMONTHRET = jsonObject1.getString("3MONTHRET");
                        String sixMONTHRET = jsonObject1.getString("6MONTHRET");
                        String oneYEARRET = jsonObject1.getString("1YEARRET");
                        String twoYEARRET = jsonObject1.getString("2YEARRET");
                        String threeYEARRET = jsonObject1.getString("3YEARRET");
                        String fiveYEARRET = jsonObject1.getString("5YEARRET");
                        String INCRET = jsonObject1.getString("INCRET");
                        String NAVRS = jsonObject1.getString("NAVRS");
                        String RECORDDATE = jsonObject1.getString("RECORDDATE");
                        String GROSS = jsonObject1.getString("GROSS");
                        String CLASSCODE = jsonObject1.getString("CLASSCODE");

                        divinModel.setS_NAME(S_NAME);
                        divinModel.setRECORDDATE(RECORDDATE);
                        divinModel.setDividentRs(dividentRs);

                        arraylist_divin.add(divinModel);
                    }

                    lv_dividend.setAdapter(new DividendCustomAdapter(getMainActivity(), arraylist_divin));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ll_header_dividend.setVisibility(View.GONE);
                errorMsgLayout.setVisibility(View.VISIBLE);
                errorTextView.setText("No data available");
                errorTextView.setVisibility(View.VISIBLE);
                lv_dividend.setVisibility(View.GONE);

                hideProgress();
                error.printStackTrace();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        Volley.newRequestQueue(getMainActivity()).add(postRequest);
    }

    public class DividendCustomAdapter extends BaseAdapter {
        final List<DivinModel> arrayList;
        private LayoutInflater inflater = null;

        public DividendCustomAdapter(Context context, List<DivinModel> arrayList) {
            this.arrayList = arrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row_mf_dividend, null);
                holder = new Holder();
                holder.tv_sch_name = convertView.findViewById(R.id.tv_sch_name);
                holder.tv_recorddate = convertView.findViewById(R.id.tv_recorddate);
                holder.tv_DivAmount = convertView.findViewById(R.id.tv_DivAmount);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.tv_sch_name.setText(arrayList.get(position).getS_NAME());
            holder.tv_recorddate.setText(arrayList.get(position).getRECORDDATE());

            String value = String.format("%.2f", Double.parseDouble(arrayList.get(position).getDividentRs()));

            holder.tv_DivAmount.setText(value);

            convertView.setBackgroundResource(position % 2 == 0 ? R.color.light_white : R.color.white);
            return convertView;
        }

        public class Holder {
            GreekTextView tv_sch_name, tv_recorddate, tv_DivAmount;
        }
    }
}
