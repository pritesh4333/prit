package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
//import com.bfsl.core.model.tradeorderbook.NewsDataModel;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.NewsDataModel;
import com.acumengroup.mobile.model.NewsDetailsDataRequest;
import com.acumengroup.mobile.model.NewsDetailsDataResponse;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.acumengroup.greekmain.core.network.WSHandler.encodeToBase64;


public class NewsTabFragment extends GreekBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Spinner spinner;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText edtSearch;
    public ServiceResponseHandler serviceResponseHandler;
    private  LinearLayout newstabbg;
    NewsDetailsDataResponse newsDetailsDataResponse;
    private TextView errorMsgLayout;
    private String Searchtype;
    List<String> optionsList;
    RecyclerView recyclerView;
    NewsListAdapter newsListAdapter;
    List<NewsDataModel> newsDataModelList;
    private GreekButton btn_search;
    private View newsParentView;

    public NewsTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);
        newsDataModelList = new ArrayList<>();
        optionsList = new ArrayList<>();
        optionsList.add("All");
        optionsList.add("Block Deals");
        optionsList.add("Equity");
        optionsList.add("Commentary");
        optionsList.add("Special Coverage");
        optionsList.add("Global");
        optionsList.add("Fixed Income");
        optionsList.add("Commodities");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Fetch data or something...
            final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");

            ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), optionsList) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    v.setTypeface(font);
                    if(AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")){
                        v.setTextColor(getResources().getColor(R.color.black));
                    }else{
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
            spinner.setAdapter(assetTypeAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 0) {
                        Searchtype = "All";
                        edtSearch.setError(null);
                    } else if (position == 1) {

                        Searchtype = "Block_Details";
                        edtSearch.setError(null);
                    } else if (position == 2) {

                        Searchtype = "Default";
                        edtSearch.setError(null);
                    } else if (position == 3) {

                        Searchtype = "Commentary";
                        edtSearch.setError(null);
                    } else if (position == 4) {

                        Searchtype = "Special Coverage";
                        edtSearch.setError(null);
                    } else if (position == 5) {

                        Searchtype = "Global";
                        edtSearch.setError(null);
                    } else if (position == 6) {

                        Searchtype = "Fixed_income";
                        edtSearch.setError(null);
                    } else if (position == 7) {

                        Searchtype = "Commodities";
                        edtSearch.setError(null);
                    }

                    showProgress();
                    sendRequest(Searchtype);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        newsParentView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_news_tab).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_news_tab).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        edtSearch = newsParentView.findViewById(R.id.edt_search);
        spinner = newsParentView.findViewById(R.id.spinner);
        errorMsgLayout = newsParentView.findViewById(R.id.txt_error_msg);
        btn_search = newsParentView.findViewById(R.id.btn_search);
        newstabbg = newsParentView.findViewById(R.id.news_background);
        recyclerView = newsParentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        newsListAdapter = new NewsListAdapter(newsDataModelList, getMainActivity());
        recyclerView.setAdapter(newsListAdapter);

        swipeRefreshLayout = newsParentView.findViewById(R.id.refreshList);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtSearch.getText().toString().length() <= 0) {
                    edtSearch.setError("Please enter search");
                } else
                    Searchnewsdata();

            }
        });

        setTheme();
        AccountDetails.currentFragment = NAV_TO_NEWS_TAB;

        return newsParentView;
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            newstabbg.setBackgroundColor(getResources().getColor(R.color.white));
            edtSearch.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            /*btn_search.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn_search.setBackgroundColor(getResources().getColor(R.color.buttonColor));*/
            errorMsgLayout.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            spinner.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
        }
    }

    private void Searchnewsdata() {
        showProgress();
        // Get Request is call to search news data
        WSHandler.getRequest(getMainActivity(), "getNewsSearch?type=" + Searchtype + "&topN=20&searchString=" + encodeToBase64(edtSearch.getText().toString()), new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.e("Newsdata", "Responces=========>>>" + response);
                newsDataModelList.clear();
                hideProgress();
                refreshComplete();

                try {
                    JSONArray arraydata = response.getJSONArray("data");

                    for (int i = 0; i < arraydata.length(); i++) {

                        NewsDataModel newsDataModel = new NewsDataModel();

                        JSONObject jsonObject = arraydata.getJSONObject(i);

                        newsDataModel.setSno(jsonObject.getString("Sno"));
                        newsDataModel.setSection_name(jsonObject.getString("Section_name"));
                        newsDataModel.setDate(jsonObject.getString("Date"));
                        newsDataModel.setTime(jsonObject.getString("Time"));
                        newsDataModel.setHeading(jsonObject.getString("Heading"));
                        newsDataModel.setCaption(jsonObject.getString("Caption"));
                        newsDataModel.setArttext(jsonObject.getString("Arttext"));
                        newsDataModel.setCo_code(jsonObject.getString("co_code"));
                        newsDataModel.setDateformat(jsonObject.getString("dateformat"));

                        newsDataModelList.add(newsDataModel);

                    }

                    toggleErrorLayout(false);

                    hideProgress();
                } catch (JSONException e) {
                    e.printStackTrace();
                    toggleErrorLayout(true);
                    refreshComplete();
                }
                newsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                hideProgress();
                toggleErrorLayout(true);
                newsDataModelList.clear();
                newsListAdapter.notifyDataSetChanged();
                refreshComplete();

            }
        });
    }


    void sendRequest(String type) {

        NewsDetailsDataRequest.sendRequest(type, "20", getMainActivity(), serviceResponseHandler);
    }


    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            sendRequest(Searchtype);
        }
    };

    private void refreshComplete() {
        hideProgress();
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);
        hideProgress();

        Log.e("NewsDetails", "res=======>" + response);
        refreshComplete();
        newsDataModelList.clear();
        try {

            JSONResponse jsonResponse = (JSONResponse) response;
            JSONArray arraydata = jsonResponse.getResObject().getJSONArray("data");


            for (int i = 0; i < arraydata.length(); i++) {

                NewsDataModel newsDataModel = new NewsDataModel();

                JSONObject jsonObject = arraydata.getJSONObject(i);

                newsDataModel.setSno(jsonObject.getString("Sno"));
                newsDataModel.setSection_name(jsonObject.getString("Section_name"));
                newsDataModel.setDate(jsonObject.getString("Date"));
                newsDataModel.setTime(jsonObject.getString("Time"));
                newsDataModel.setHeading(jsonObject.getString("Heading"));
                newsDataModel.setCaption(jsonObject.getString("Caption"));
                newsDataModel.setArttext(jsonObject.getString("Arttext"));
                newsDataModel.setCo_code(jsonObject.getString("co_code"));
                newsDataModel.setDateformat(jsonObject.getString("dateformat"));

                newsDataModelList.add(newsDataModel);

            }


            toggleErrorLayout(false);


        } catch (JSONException e) {
            e.printStackTrace();
            toggleErrorLayout(true);
            refreshComplete();
        } catch (Exception e) {
            toggleErrorLayout(true);
            refreshComplete();
            e.printStackTrace();
        }
        newsListAdapter.notifyDataSetChanged();

    }


    public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

        private List<NewsDataModel> orderBooklist;
        Context context;

        // RecyclerView recyclerView;
        public NewsListAdapter(List<NewsDataModel> orderBook, Context context) {

            this.context = context;
            this.orderBooklist = orderBook;
        }

        public void clear() {
            int size = orderBooklist.size();
            orderBooklist.clear();
            notifyItemRangeRemoved(0, size);
        }

        @Override
        public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.row_news_layout, parent, false);

            return new NewsListAdapter.ViewHolder(listItem);
        }

        private String getDayNumberSuffix(int day) {
            if (day >= 11 && day <= 13) {
                return "th";
            }
            switch (day % 10) {
                case 1:
                    return "st";
                case 2:
                    return "nd";
                case 3:
                    return "rd";
                default:
                    return "th";
            }
        }

        @Override
        public void onBindViewHolder(final NewsListAdapter.ViewHolder holder, int position) {
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                holder.news_row_Layout.setBackgroundColor(getResources().getColor(R.color.white));
                holder.txt_title.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_time.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                //holder.txt_read.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            }

            NewsDataModel orderBookData = orderBooklist.get(position);


            holder.txt_title.setText(decodeBase64(orderBookData.getArttext()));

            Calendar cal = Calendar.getInstance(Locale.ENGLISH);

            String dayNumberSuffix = getDayNumberSuffix(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

            cal.setTimeInMillis(Long.parseLong(orderBookData.getTime()) * 1000);

            String date = DateFormat.format("hh:mm a, d'" + dayNumberSuffix + "' MMMM yyyy", cal).toString();

            holder.txt_time.setText(DateTimeFormatter.getDateFromTimeStampForchatMSG(orderBookData.getTime(), "hh:mm a, dd MMM yyyy", "bse"));


            if (decodeBase64(orderBookData.getArttext()).length() - decodeBase64(orderBookData.getHeading()).length() < 50) {

                holder.txt_title.setText(decodeBase64(orderBookData.getArttext()));
                holder.txt_read.setVisibility(View.VISIBLE);
                holder.txt_title.setMaxLines(2);//your TextView

            } else {
                holder.txt_title.setText(decodeBase64(orderBookData.getHeading()));
                holder.txt_read.setVisibility(View.GONE);
            }


            holder.txt_title.post(new Runnable() {
                @Override
                public void run() {
                    int lineCount = holder.txt_title.getLineCount();
                    Log.e("NewsTabFragment", "lineCount=====>" + lineCount);
                    if (lineCount > 2) {
                        holder.txt_read.setVisibility(View.VISIBLE);
                    } else {
                        holder.txt_read.setVisibility(View.GONE);
                    }
                    // Use lineCount here
                }
            });


            holder.txt_read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (holder.txt_read.getText().toString().equalsIgnoreCase("Read More")) {
                        holder.txt_title.setMaxLines(Integer.MAX_VALUE);//your TextView
                        holder.txt_read.setText("Read Less");
                    } else {
                        holder.txt_title.setMaxLines(2);//your TextView
                        holder.txt_read.setText("Read More");
                    }
                }

            });

        }

        public String decodeBase64(String decodeData) {
            String decodedString = new String(android.util.Base64.decode(decodeData, android.util.Base64.DEFAULT));
            return decodedString;
        }


        @Override
        public int getItemCount() {
            return orderBooklist.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            GreekTextView txt_title, txt_time, txt_read;
            LinearLayout news_row_Layout;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_title = itemView.findViewById(R.id.txt_title);
                txt_time = itemView.findViewById(R.id.txt_time);
                txt_read = itemView.findViewById(R.id.txt_read);
                news_row_Layout = itemView.findViewById(R.id.news_row_layout);

            }
        }
    }


    private void toggleErrorLayout(boolean show) {
        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
