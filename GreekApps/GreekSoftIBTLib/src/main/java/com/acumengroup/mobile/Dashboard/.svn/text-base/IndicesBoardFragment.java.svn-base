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
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.ActionCode;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketsindianindices.IndianIndice;
import com.acumengroup.greekmain.core.model.marketsindianindices.MarketsIndianIndicesResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class IndicesBoardFragment extends GreekBaseFragment {

    private IndicesBoardFragment.CustomAdapter commonAdapter;
    StreamingController streamController;
    private RelativeLayout errorLayout;
    private MarketsIndianIndicesResponse indianIndicesResponse = null;
    private int maxSizeList = 5;
    private boolean isExpanded = false;
    private ListView listGlobal;
    private ArrayList streamingList;

    public IndicesBoardFragment() {
        // Required empty public constructor
    }

    public void onEventMainThread(final DashboardAnimate dashboardAnimate) {

        if (dashboardAnimate.getPosition() == 1) {

            if (dashboardAnimate.isExpand()) {
                ViewGroup.LayoutParams params = listGlobal.getLayoutParams();
                params.height = AccountDetails.getFragHeight();
                listGlobal.setLayoutParams(params);
                listGlobal.requestLayout();

                isExpanded = true;

            } else {

                ViewGroup.LayoutParams params = listGlobal.getLayoutParams();
                params.height = AccountDetails.getFragHeight() / 3;
                listGlobal.setLayoutParams(params);
                listGlobal.requestLayout();

                isExpanded = false;
            }

            showProgress();
            sendIndianIndicesRequest();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View newsView = super.onCreateView(inflater, container, savedInstanceState);
        streamController = new StreamingController();

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_commodity).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_commodity).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        hideAppTitle();
        setupViews(newsView);

        return newsView;
    }

    private void setupViews(View parent) {
        listGlobal = parent.findViewById(R.id.listMarketComodityCurency);

        errorLayout = parent.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");
        //listGlobal.setEmptyView(errorLayout);

        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);
        commonAdapter = new IndicesBoardFragment.CustomAdapter(getMainActivity(), new ArrayList<IndianIndice>());
        listGlobal.setAdapter(commonAdapter);

        sendIndianIndicesRequest();

        ViewGroup.LayoutParams params = listGlobal.getLayoutParams();
        params.height = 540 + 12;
        listGlobal.setLayoutParams(params);
        listGlobal.requestLayout();
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        hideProgress();
        if (action == ActionCode.ACT_CODE_OK.value) {
            GreekDialog.alertDialog(getActivity(), 0, GREEK, msg, "OK", true, null);
        }
    }

    public void sendIndianIndicesRequest() {

        if (isExpanded) {

            streamController.sendIndianIndicesRequest(getMainActivity(), serviceResponseHandler);

        } else {

            streamController.sendIndianIndicesRequesNew(getMainActivity(), serviceResponseHandler);
        }

    }

    public void onEventMainThread(StreamingResponse streamingResponse) {
        hideProgress();
        try {

            if (streamingResponse.getStreamingType().equals("ltpinfo") || streamingResponse.getStreamingType().equals("index")) {

                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());

                IndianIndice indicesModel = new IndianIndice();
                indicesModel.setChange(broadcastResponse.getChange());
                indicesModel.setLtp(broadcastResponse.getLast());
                indicesModel.setP_change(broadcastResponse.getP_change());
                indicesModel.setToken(broadcastResponse.getName());

                commonAdapter.setBroadcastData(commonAdapter.indexOf(broadcastResponse.getName()), indicesModel);
                commonAdapter.notifyDataSetChanged();

            } else if (streamingResponse.getStreamingType().equalsIgnoreCase("marketpicture")) {

                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());

                IndianIndice indicesModel = new IndianIndice();
                indicesModel.setChange(broadcastResponse.getChange());
                indicesModel.setLtp(broadcastResponse.getLast());
                indicesModel.setP_change(broadcastResponse.getP_change());
                indicesModel.setToken(broadcastResponse.getName());

                commonAdapter.setBroadcastData(commonAdapter.indexOf(broadcastResponse.getName()), indicesModel);
                commonAdapter.notifyDataSetChanged();

            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    @Override
    public void handleResponse(Object response) {
        streamingList = new ArrayList();
        toggleErrorLayout(false);
        JSONResponse jsonResponse = (JSONResponse) response;

        if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INDIAN_INDICES_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {

                indianIndicesResponse = (MarketsIndianIndicesResponse) jsonResponse.getResponse();
                updateAdapterSize();
                hideProgress();
                commonAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                toggleErrorLayout(true);
                e.printStackTrace();
            }

        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INDIAN_INDICES_SVC_NAME_NEW.equals(jsonResponse.getServiceName())) {
            try {

                indianIndicesResponse = (MarketsIndianIndicesResponse) jsonResponse.getResponse();
                updateAdapterSize();
                hideProgress();
                commonAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                toggleErrorLayout(true);
                e.printStackTrace();
            }

        }

        refreshComplete();
    }

    public void updateAdapterSize() {

        List<IndianIndice> temp_list = new ArrayList<>();
        IndianIndice indianIndice = new IndianIndice();

        if (indianIndicesResponse != null) {

            if (isExpanded) {

                maxSizeList = indianIndicesResponse.getIndianIndices().size();

            } else {

                if (indianIndicesResponse.getIndianIndices().size() > 5) {
                    maxSizeList = 5;
                } else {
                    maxSizeList = indianIndicesResponse.getIndianIndices().size();
                }
            }

            streamingList.clear();
            commonAdapter.clear();

            for (int i = 0; i < maxSizeList; i++) {

                indianIndice = indianIndicesResponse.getIndianIndices().get(i);
                temp_list.add(indianIndice);

                commonAdapter.addSymbol(indianIndicesResponse.getIndianIndices().get(i).getToken());
                streamingList.add(indianIndicesResponse.getIndianIndices().get(i).getIndexCode());

            }
            commonAdapter.setData(temp_list);

            sendStreamingRequest();
            setListViewHeightBasedOnItems(listGlobal);

        }
    }

    private void sendStreamingRequest() {

        streamController.sendStreamingRequest(getMainActivity(), streamingList, "index", null, null, false);
        addToStreamingList("index", streamingList);
    }

    public boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;

            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
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
        private List<IndianIndice> commCurrList;
        private ArrayList<String> tokenList;

        public CustomAdapter(Context context, List<IndianIndice> commCurrList) {
            this.mContext = context;
            this.commCurrList = commCurrList;
            tokenList = new ArrayList<>();
        }

        public void setData(List<IndianIndice> commCurrList) {
            this.commCurrList = commCurrList;
        }

        public void setBroadcastData(int position, IndianIndice indicesModel) {

            this.commCurrList.set(position, indicesModel);
        }

        public int indexOf(String symbol) {

            return tokenList.indexOf(symbol);
        }

        public void addSymbol(String symbol) {

            tokenList.add(symbol);
        }

        public boolean containsSymbol(String symbol) {

            return tokenList.contains(symbol);
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
        public IndianIndice getItem(int position) {
            return commCurrList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            IndicesBoardFragment.CustomAdapter.Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_table_list, parent, false);
                holder = new IndicesBoardFragment.CustomAdapter.Holder();

                holder.tvName = convertView.findViewById(R.id.symbolname_text);
                holder.tvltp = convertView.findViewById(R.id.ltp_text);
                holder.tvChg = convertView.findViewById(R.id.change_text);
                holder.tvPchange = convertView.findViewById(R.id.perchange_text);
                holder.updownArrow = convertView.findViewById(R.id.updownArrow);

                convertView.setTag(holder);

            } else {

                holder = (IndicesBoardFragment.CustomAdapter.Holder) convertView.getTag();
            }

            IndianIndice model = getItem(position);
            holder.tvltp.setText(StringStuff.commaDecorator(model.getLtp()));


            Double perChange;

            if (model.getP_change().equals("null")) {
                perChange = 0.0;
            } else {
                perChange = Double.valueOf(model.getP_change());
            }

            int flashBluecolor, flashRedColor, textColorPositive, textColorNegative,arrow_image;

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                flashBluecolor = R.drawable.light_blue_positive;
                flashRedColor = R.drawable.light_red_negative;
                arrow_image = R.drawable.up_arrow;

            } else {
                flashBluecolor = R.drawable.light_green_textcolor;
                flashRedColor = R.drawable.dark_red_negative;
                arrow_image = R.drawable.up_arrow_green;
            }

            if (perChange < 0) {
                holder.tvltp.setTextColor(getResources().getColor(R.color.red));
                holder.tvltp.setBackground(getResources().getDrawable(flashRedColor));
                holder.updownArrow.setImageResource(R.drawable.down_arrow_red);


            } else if (perChange >= 0) {
                holder.tvltp.setTextColor(getResources().getColor(AccountDetails.textColorBlue));
                holder.tvltp.setBackground(getResources().getDrawable(flashBluecolor));
                holder.updownArrow.setImageResource(arrow_image);

            }

            if (!model.getToken().equalsIgnoreCase("")) {
                holder.tvName.setText(model.getToken());
            }
            String roundOffChangePer = " ";
            String roundOffChange = " ";

            if (!model.getP_change().equalsIgnoreCase("null") && model.getP_change() != null) {
                roundOffChangePer = String.format("%.2f", Double.parseDouble(model.getP_change()));

            }

            if (!model.getChange().equalsIgnoreCase("null") && model.getChange() != null) {
                roundOffChange = String.format("%.2f", Double.parseDouble(model.getChange()));

            }

            holder.tvChg.setText(roundOffChange);

            holder.tvPchange.setText(String.format("%.2f%%", perChange));


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

        public class Holder {
            GreekTextView tvChg, tvltp, tvName, tvPchange;
            ImageView updownArrow;

        }
    }


    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {
        toggleErrorLayout(true);
        refreshComplete();
    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {
        hideProgress();
        toggleErrorLayout(true);
        refreshComplete();
    }

    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void refreshComplete() {
        hideProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        if (EventBus.getDefault().isRegistered(this)) {
        } else {
            EventBus.getDefault().register(this);
        }
        updateAdapterSize();
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentPause() {
        if (streamingList.size() > 0)
            streamController.pauseStreaming(getActivity(), "index", streamingList);
        super.onFragmentPause();
    }

    @Override
    public void onPause() {
        if (streamingList != null) {
            if (streamingList.size() > 0)
                streamController.pauseStreaming(getActivity(), "index", streamingList);
        }
        super.onPause();
    }
}
