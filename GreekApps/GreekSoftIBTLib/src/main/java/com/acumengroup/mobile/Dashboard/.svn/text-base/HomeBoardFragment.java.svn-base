package com.acumengroup.mobile.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.IndicesModel;
import com.acumengroup.mobile.model.IndicesResponce;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class HomeBoardFragment extends GreekBaseFragment {

    private ListView listView;
    private IndicesResponce indicesResponce;
    private HomeBoardFragment.CustomAdapter commonAdapter;
    private int maxSizeList = 2;
    private boolean isExpanded = false;
    private ArrayList streamingList;
    private StreamingController streamController = new StreamingController();
    private RelativeLayout errorLayout;

    public HomeBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void onEventMainThread(final DashboardAnimate dashboardAnimate) {

        if (dashboardAnimate.getPosition() == 1) {

            if (dashboardAnimate.isExpand()) {

                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = AccountDetails.getFragHeight();
                listView.setLayoutParams(params);
                listView.requestLayout();

                isExpanded = true;
                updateAdapterSize();
                commonAdapter.notifyDataSetChanged();

            } else {

                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = AccountDetails.getFragHeight() / 3;
                listView.setLayoutParams(params);
                listView.requestLayout();

                isExpanded = false;
                updateAdapterSize();
                commonAdapter.notifyDataSetChanged();

            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_commodity).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_commodity).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        listView = view.findViewById(R.id.listMarketComodityCurency);
        errorLayout = view.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");
        //listView.setEmptyView(errorLayout);


        commonAdapter = new HomeBoardFragment.CustomAdapter(getMainActivity(), new ArrayList<IndicesModel>());
        listView.setAdapter(commonAdapter);

        sendPortfolioTrendingRequest();

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = 540 + 12;
        listView.setLayoutParams(params);
        listView.requestLayout();

        return view;
    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equals("index")) {

                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());

                IndicesModel indicesModel = new IndicesModel();
                indicesModel.setChange(broadcastResponse.getChange());
                indicesModel.setLast(broadcastResponse.getLast());
                indicesModel.setP_change(broadcastResponse.getP_change());
                indicesModel.setName(broadcastResponse.getName());
                if (commonAdapter.containsSymbolName(broadcastResponse.getName().toUpperCase())) {
                    commonAdapter.setBroadcastData(commonAdapter.indexOfName(broadcastResponse.getName().toUpperCase()), indicesModel);
                    commonAdapter.notifyDataSetChanged();
                }

            } else if (streamingResponse.getStreamingType().equalsIgnoreCase("ltpinfo")) {

                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());

                IndicesModel indicesModel = new IndicesModel();
                indicesModel.setChange(broadcastResponse.getChange());


                if (((Integer.valueOf(broadcastResponse.getSymbol()) >= 502000000) && (Integer.valueOf(broadcastResponse.getSymbol()) <= 502999999)) || ((Integer.valueOf(broadcastResponse.getSymbol()) >= 1302000000) && (Integer.valueOf(broadcastResponse.getSymbol()) <= 1302999999))) {
                    indicesModel.setLast(String.format("%.4f", Double.parseDouble(broadcastResponse.getLast())));

                } else {
                    indicesModel.setLast(String.format("%.2f", Double.parseDouble(broadcastResponse.getLast())));

                }
                indicesModel.setLast(broadcastResponse.getLast());
                indicesModel.setP_change(broadcastResponse.getP_change());
                indicesModel.setName(broadcastResponse.getName());
                indicesModel.setToken(broadcastResponse.getSymbol());

                if (commonAdapter.containsSymbol(broadcastResponse.getSymbol().toUpperCase())) {
                    commonAdapter.setBroadcastData(commonAdapter.indexOf(broadcastResponse.getSymbol().toUpperCase()), indicesModel);
                    commonAdapter.notifyDataSetChanged();
                }

            } else if (streamingResponse.getStreamingType().equalsIgnoreCase("marketpicture")) {

                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());

                IndicesModel indicesModel = new IndicesModel();
                indicesModel.setChange(broadcastResponse.getChange());


                if (((Integer.valueOf(broadcastResponse.getSymbol()) >= 502000000) && (Integer.valueOf(broadcastResponse.getSymbol()) <= 502999999)) || ((Integer.valueOf(broadcastResponse.getSymbol()) >= 1302000000) && (Integer.valueOf(broadcastResponse.getSymbol()) <= 1302999999))) {
                    indicesModel.setLast(String.format("%.4f", Double.parseDouble(broadcastResponse.getLast())));

                } else {
                    indicesModel.setLast(String.format("%.2f", Double.parseDouble(broadcastResponse.getLast())));

                }
                indicesModel.setLast(broadcastResponse.getLast());
                indicesModel.setP_change(broadcastResponse.getP_change());
                indicesModel.setName(broadcastResponse.getName());
                indicesModel.setToken(broadcastResponse.getSymbol());

                if (commonAdapter.containsSymbol(broadcastResponse.getSymbol().toUpperCase())) {
                    commonAdapter.setBroadcastData(commonAdapter.indexOf(broadcastResponse.getSymbol().toUpperCase()), indicesModel);
                    commonAdapter.notifyDataSetChanged();
                }

            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    private void sendPortfolioTrendingRequest() {

        final DashboardAnimate dashBoardCommunicate = new DashboardAnimate();
        dashBoardCommunicate.setShowProgress(true);
        EventBus.getDefault().post(dashBoardCommunicate);

        WSHandler.getRequest(getMainActivity(), "getAllIndicesValueNew", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                toggleErrorLayout(false);
//                hideProgress();

                dashBoardCommunicate.setShowProgress(false);
                EventBus.getDefault().post(dashBoardCommunicate);

                streamingList = new ArrayList();
                try {

                    indicesResponce = new IndicesResponce();
                    indicesResponce.fromJSON(response);

                    updateAdapterSize();
                    commonAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    toggleErrorLayout(true);

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                toggleErrorLayout(true);
                Log.e("Failure", "resp in common activity ");

                dashBoardCommunicate.setShowProgress(false);
                EventBus.getDefault().post(dashBoardCommunicate);
            }
        });
    }

    public void updateAdapterSize() {

        List<IndicesModel> temp_list = new ArrayList<>();
        IndicesModel indicesModel = new IndicesModel();

        if (indicesResponce != null) {
            if (isExpanded) {

                maxSizeList = indicesResponce.getOrderBookModelList().size();

            } else {

                commonAdapter.clear();
                commonAdapter.clear();

                if (indicesResponce.getOrderBookModelList().size() > 5) {
                    maxSizeList = 5;

                } else {
                    maxSizeList = indicesResponce.getOrderBookModelList().size();
                }
            }

            streamingList.clear();
            commonAdapter.clear();
            for (int i = 0; i < maxSizeList; i++) {

                indicesModel = indicesResponce.getOrderBookModelList().get(i);
                if (!indicesModel.getName().equals("")) {


                    temp_list.add(indicesModel);

                    if (!indicesResponce.getOrderBookModelList().get(i).getToken().equalsIgnoreCase("0")) {
                        streamingList.add(indicesResponce.getOrderBookModelList().get(i).getToken());
                    }

                    commonAdapter.addSymbol(indicesResponce.getOrderBookModelList().get(i).getName().toUpperCase());
                    commonAdapter.addToken(indicesResponce.getOrderBookModelList().get(i).getToken().toUpperCase());
                }
            }
            commonAdapter.setData(temp_list);
            sendStreamingRequest();
            setListViewHeightBasedOnItems(listView);
        }
    }

    private void sendStreamingRequest() {

        streamController.sendStreamingRequest(getMainActivity(), streamingList, "ltpinfo", null, null, false);
        addToStreamingList("ltpinfo", streamingList);
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                if (item != null) {
                    item.measure(0, 0);
                    totalItemsHeight += item.getMeasuredHeight();
                }
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    public class CustomAdapter extends BaseAdapter {
        private final Context mContext;
        private List<IndicesModel> commCurrList;
        ArrayList<String> tokenList;
        ArrayList<String> nameList;

        public CustomAdapter(Context context, List<IndicesModel> commCurrList) {
            this.mContext = context;
            this.commCurrList = commCurrList;
            tokenList = new ArrayList<>();
            nameList = new ArrayList<>();
        }

        public void setData(List<IndicesModel> commCurrList) {
            this.commCurrList = commCurrList;
        }

        public void setBroadcastData(int position, IndicesModel indicesModel) {

            this.commCurrList.set(position, indicesModel);
        }

        public int indexOf(String symbol) {
            return tokenList.indexOf(symbol);
        }

        public int indexOfName(String symbol) {
            return nameList.indexOf(symbol);
        }

        public void addSymbol(String symbol) {

            nameList.add(symbol);
        }

        public void addToken(String token) {

            tokenList.add(token);
        }

        public boolean containsSymbol(String symbol) {
            return tokenList.contains(symbol);
        }

        public boolean containsSymbolName(String symbol) {
            return nameList.contains(symbol);
        }

        public void clear() {
            this.commCurrList.clear();
            this.tokenList.clear();
        }

        @Override
        public int getCount() {
            return commCurrList.size();
        }

        @Override
        public IndicesModel getItem(int position) {
            return commCurrList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HomeBoardFragment.CustomAdapter.Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_table_list, parent, false);
                holder = new HomeBoardFragment.CustomAdapter.Holder();

                holder.tvName = convertView.findViewById(R.id.symbolname_text);
                holder.tvltp = convertView.findViewById(R.id.ltp_text);
                holder.tvChg = convertView.findViewById(R.id.change_text);
                holder.tvPchange = convertView.findViewById(R.id.perchange_text);
                holder.updownArrow = convertView.findViewById(R.id.updownArrow);

                convertView.setTag(holder);

            } else {
                holder = (HomeBoardFragment.CustomAdapter.Holder) convertView.getTag();
            }

            IndicesModel model = getItem(position);

            String roundOffChange;
            if (!model.getName().equalsIgnoreCase("")) {

                holder.tvName.setText(model.getName().toUpperCase());
            }

            if (model.getToken() != null) {
                if (((Integer.valueOf(model.getToken()) >= 502000000) && (Integer.valueOf(model.getToken()) <= 502999999)) || ((Integer.valueOf(model.getToken()) >= 1302000000) && (Integer.valueOf(model.getToken()) <= 1302999999))) {

                    holder.tvltp.setText(String.format("%.4f", Double.parseDouble(model.getLast())));
                    roundOffChange = String.format("%.4f", Double.parseDouble(model.getChange()));

                    holder.tvChg.setText(roundOffChange);

                } else {

                    //holder.tvltp.setText(StringStuff.commaDecorator(model.getLast()));
                    holder.tvltp.setText(model.getLast());
                    roundOffChange = String.format("%.2f", Double.parseDouble(model.getChange()));
                    //holder.tvChg.setText(roundOffChange);
                    holder.tvChg.setText(model.getChange());
                }
            } else {
                //holder.tvltp.setText(StringStuff.commaDecorator(model.getLast()));
                holder.tvltp.setText(model.getLast());
                roundOffChange = String.format("%.2f", Double.parseDouble(model.getChange()));
                holder.tvChg.setText(model.getChange());
                //holder.tvChg.setText(roundOffChange);
            }

            String roundOffChangePer = String.format("%.2f", Double.parseDouble(model.getP_change()));

            if (holder.tvPchange != null) {
                holder.tvPchange.setTextColor(getColorForChange(model.getP_change()));
            }

            int flashBluecolor, flashRedColor, textColorPositive, textColorNegative, arrow_image;

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                flashBluecolor = R.drawable.light_blue_positive;
                flashRedColor = R.drawable.light_red_negative;

                textColorPositive = R.color.dark_blue_positive;
                textColorNegative = R.color.dark_red_negative;
                arrow_image = R.drawable.up_arrow;

            } else {
                flashBluecolor = R.drawable.light_green_textcolor;
                flashRedColor = R.drawable.dark_red_negative;
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    textColorPositive = R.color.whitetheambuyColor;
                }else {
                    textColorPositive = R.color.dark_green_positive;
                }
                textColorNegative = R.color.dark_red_negative;
                arrow_image = R.drawable.up_arrow_green;
            }


            Double perChange;

            if (model.getP_change().equals("null")) {
                perChange = 0.0;
            } else {
                perChange = Double.valueOf(model.getP_change());
            }

            holder.tvPchange.setText(String.format("%.2f%%", perChange));

            if (perChange >= 0) {

                holder.tvltp.setBackground(getResources().getDrawable(flashBluecolor));
                holder.tvltp.setTextColor(getResources().getColor(textColorPositive));
                holder.updownArrow.setImageResource(arrow_image);

            } else {

                holder.tvltp.setBackground(getResources().getDrawable(flashRedColor));
                holder.tvltp.setTextColor(getResources().getColor(textColorNegative));
                holder.updownArrow.setImageResource(R.drawable.down_arrow_red);

            }

            if (roundOffChange.startsWith("-")) {

                holder.tvChg.setTextColor(getResources().getColor(R.color.red));

            } else {
                holder.tvChg.setTextColor(getResources().getColor(AccountDetails.textColorBlue));
            }

            if (roundOffChangePer.startsWith("-")) {
                holder.tvPchange.setTextColor(getResources().getColor(R.color.red));

            } else {
                holder.tvPchange.setTextColor(getResources().getColor(AccountDetails.textColorBlue));
            }

            int textColor = AccountDetails.getTextColorDropdown();

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.tvName.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.tvName.setTextColor(getResources().getColor(textColor));
            }
            return convertView;
        }

        private int getColorForChange(String change) {
            int color = 0;
            if (change.isEmpty() || change.equals("NA")) {
                color = getResources().getColor(R.color.action_bar_positive);
            } else {

                if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                    float diff = Float.parseFloat(change.replace("%", ""));

                    if (diff > 0) {
                        color = getResources().getColor(R.color.action_bar_positive);
                    } else if (diff < 0) {
                        color = getResources().getColor(R.color.red_textcolor);
                    } else {
                        color = getResources().getColor(R.color.action_bar_positive);
                    }

                } else if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("black")) {

                    float diff = Float.parseFloat(change.replace("%", ""));

                    if (diff > 0) {
                        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                            color = getResources().getColor(R.color.whitetheambuyColor);
                        }else {
                            color = getResources().getColor(R.color.green_textcolor);
                        }

                    } else if (diff < 0) {
                        color = getResources().getColor(R.color.red_textcolor);
                    } else {
                        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                            color = getResources().getColor(R.color.whitetheambuyColor);
                        }else {
                            color = getResources().getColor(R.color.green_textcolor);
                        }
                    }
                }
            }
            return color;
        }


        public class Holder {
            GreekTextView tvltp, tvChg, tvName, tvPchange;
            ImageView updownArrow;
        }


    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }
    }

    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        updateAdapterSize();
        commonAdapter.notifyDataSetChanged();
    }


}
