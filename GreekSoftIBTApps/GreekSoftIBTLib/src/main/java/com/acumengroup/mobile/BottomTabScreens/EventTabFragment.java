package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.SimpleDividerItemDecoration;
import com.acumengroup.mobile.model.EventData;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EventTabFragment extends GreekBaseFragment {


    ArrayList<EventData> eventDataArrayList;
    ArrayList<String> typeList;
    private RecyclerView recyclerView;
    SwipeRefreshLayout refreshList;
    private MyRecyclerviewAdapter myRecyclerviewAdapter;
    private Spinner spinner_types;
    private GreekTextView emptyView;
    private String startDate = "", endDate = "";
    private DateFormat df;
    private LinearLayout eventbg;
    private int selectedspinerId;
    private View EventParentView;



    public EventTabFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventDataArrayList = new ArrayList<>();
        typeList = new ArrayList<>();
        typeList.add("Today");
        typeList.add("This Week");
        typeList.add("Next Week");
        typeList.add("This Month");

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventParentView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_event_tab).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_event_tab).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        spinner_types = EventParentView.findViewById(R.id.spinner);
        df = new SimpleDateFormat("MMM dd yyyy", Locale.US);


        eventbg = EventParentView.findViewById(R.id.event_bg);
        recyclerView = EventParentView.findViewById(R.id.recyclerView);
        refreshList = EventParentView.findViewById(R.id.refreshList);
        refreshList.setOnRefreshListener(onRefreshListener);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        myRecyclerviewAdapter = new MyRecyclerviewAdapter(getMainActivity(), eventDataArrayList);
        recyclerView.setAdapter(myRecyclerviewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        emptyView = EventParentView.findViewById(R.id.empty_view);

        setTheme();

        return EventParentView;
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            emptyView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            spinner_types.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            // ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), typeList);
            ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), typeList) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    // v.setTypeface(font);
                    if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                        v.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        v.setTextColor(getResources().getColor(R.color.white));
                    }

                    v.setPadding(15, 15, 15, 15);
                    return v;
                }
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    //  v.setTypeface(font);
                    v.setTextColor(Color.BLACK);
                    v.setPadding(15, 15, 15, 15);
                    return v;
                }
            };
            assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
            spinner_types.setAdapter(assetTypeAdapter);
            spinner_types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selectedspinerId = position;

                    Calendar calendar = Calendar.getInstance();
                    if (position == 0) {
                        //Today


                        calendar.add(Calendar.MONTH, 0);
                        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                        Date c = Calendar.getInstance().getTime();
                        startDate = df.format(c);
                        endDate = df.format(c);

                        getDataFromServer();

                    } else if (position == 1) {
                        //this week

                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

                        startDate = df.format(calendar.getTime());
                        calendar.add(Calendar.DATE, 6);
                        endDate = df.format(calendar.getTime());


                        getDataFromServer();

                    }
                    else if (position == 2) {
                        //Next Week

                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        calendar.add(Calendar.DATE, 7);
                        startDate = df.format(calendar.getTime());

                        calendar.add(Calendar.DATE, 6);
                        endDate = df.format(calendar.getTime());

                        getDataFromServer();

                    } else if (position == 3) {
                        //this Month

                        calendar.add(Calendar.MONTH, 0);
                        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                        Date monthFirstDay = calendar.getTime();
                        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                        Date monthLastDay = calendar.getTime();

                        startDate = df.format(monthFirstDay);
                        Date c = Calendar.getInstance().getTime();
                        endDate = df.format(monthLastDay);

                        getDataFromServer();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void getDataFromServer() {
        // Get Request is call to get data from server
        showProgress();
        WSHandler.getRequest(getMainActivity(), "getEventData?startDate=" + startDate + "&endDate=" + endDate, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                refreshComplete();

                eventDataArrayList.clear();
                try {
                    JSONArray respCategory = response.getJSONArray("data");
                    toggleErrorLayout(false);


                    for (int i = 0; i < respCategory.length(); i++) {

                        String srno = respCategory.getJSONObject(i).getString("srno");
                        String token = respCategory.getJSONObject(i).getString("token");
                        String symbol = respCategory.getJSONObject(i).getString("symbol");
                        String news_dateTime = respCategory.getJSONObject(i).getString("news_dateTime");
                        String heading = respCategory.getJSONObject(i).getString("heading");
                        String news_type = respCategory.getJSONObject(i).getString("news_type");
                        String news_source = respCategory.getJSONObject(i).getString("news_source");
                        String news_level = respCategory.getJSONObject(i).getString("news_level");
                        String news_description = respCategory.getJSONObject(i).getString("news_description");
                        String news_arttext = respCategory.getJSONObject(i).getString("news_arttext");

                        eventDataArrayList.add(new EventData(srno, token, symbol, news_dateTime, heading, news_type, news_source,
                                news_level, news_description, news_arttext));


                    }
                    toggleErrorLayout(false);


                    hideProgress();

                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgress();
                    refreshComplete();
                    toggleErrorLayout(true);
                }

                myRecyclerviewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String message) {
                refreshComplete();
                hideProgress();
                toggleErrorLayout(true);
                eventDataArrayList.clear();
                myRecyclerviewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void toggleErrorLayout(boolean show) {

        if (show) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    private void refreshComplete() {
        hideProgress();
        if (refreshList.isRefreshing()) refreshList.setRefreshing(false);
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            ArrayAdapter<String> expiryTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), typeList) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    //v.setTypeface(font);
                    if(AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                        v.setTextColor(getResources().getColor(R.color.black));
                    } else {
                        v.setTextColor(getResources().getColor(R.color.white));
                    }
                    v.setPadding(15, 15, 15, 15);
                    return v;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    TextView v = (TextView) super.getView(position, convertView, parent);
                    // v.setTypeface(font);
                    v.setTextColor(Color.BLACK);
                    v.setPadding(15, 15, 15, 15);
                    return v;
                }
            };
            expiryTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
            spinner_types.setAdapter(expiryTypeAdapter);
            spinner_types.setSelection(selectedspinerId);

        }
    };


    private class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.ViewHolder> {

        Context context;
        ArrayList<EventData> list;

        public MyRecyclerviewAdapter(Context context, ArrayList<EventData> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public MyRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.row_news_layout, parent, false);

            return new MyRecyclerviewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyRecyclerviewAdapter.ViewHolder holder, int position) {
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                holder.news_row_layout.setBackgroundColor(getResources().getColor(R.color.white));
                //holder.txt_read.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_time.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.txt_title.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            }


            final EventData orderBookData = list.get(position);

            holder.txt_title.setText(orderBookData.getHeading().replaceAll("LIS:",""));

            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            String dayNumberSuffix = getDayNumberSuffix(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

            cal.setTimeInMillis(Long.parseLong(orderBookData.getNews_dateTime()) * 1000);
            String date = android.text.format.DateFormat.format("hh:mm a, d'" + dayNumberSuffix + "' MMMM yyyy", cal).toString();

            holder.txt_time.setText(DateTimeFormatter.getDateFromTimeStampForchatMSG(orderBookData.getNews_dateTime(), "dd MMM yyyy", "bse"));
            if (orderBookData.getNews_description().length() - orderBookData.getHeading().length() < 50) {

                holder.txt_title.setText(orderBookData.getNews_description().replaceAll("LIS:",""));
                holder.txt_read.setVisibility(View.VISIBLE);
                holder.txt_title.setMaxLines(2);//your TextView

            } else {
                holder.txt_title.setText(orderBookData.getHeading().replaceAll("LIS:",""));
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

                    String heading = holder.txt_title.getText().toString();

                    if (holder.txt_read.getText().toString().equalsIgnoreCase("Read More")) {

                        holder.txt_title.setText(heading.replaceAll("LIS:",""));
                        holder.txt_title.setMaxLines(Integer.MAX_VALUE);//your TextView
                        holder.txt_read.setText("Read Less");
                    } else {
                        holder.txt_title.setText(heading.replaceAll("LIS:",""));
                        holder.txt_title.setMaxLines(2);//your TextView
                        holder.txt_read.setText("Read More");
                    }
                }

            });


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
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            GreekTextView txt_title, txt_time, txt_read;
            private LinearLayout news_row_layout;

            public ViewHolder(View itemView) {
                super(itemView);
                news_row_layout = itemView.findViewById(R.id.news_row_layout);
                txt_title = itemView.findViewById(R.id.txt_title);
                txt_time = itemView.findViewById(R.id.txt_time);
                txt_read = itemView.findViewById(R.id.txt_read);

            }
        }
    }


}
