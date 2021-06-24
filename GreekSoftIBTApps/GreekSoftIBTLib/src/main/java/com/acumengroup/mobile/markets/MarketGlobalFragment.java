package com.acumengroup.mobile.markets;

import android.content.Context;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.ActionCode;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketsglobalindices.GlobalIndice;
import com.acumengroup.greekmain.core.model.marketsglobalindices.MarketsGlobalIndicesResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.GlobalMarketModel;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.adapter.CommonRowData;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

/**
 * Created by Arcadia
 */
public class MarketGlobalFragment extends GreekBaseFragment {
    private CustomAdapter commonAdapter;
    StreamingController streamController;
    private boolean pullToRefreshFlag = false;

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            pullToRefreshFlag = true;
            sendGlobalIndicesRequest();
        }
    };
    private RelativeLayout errorLayout;
    private SwipeRefreshLayout swipeRefresh;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View newsView = super.onCreateView(inflater, container, savedInstanceState);
        streamController = new StreamingController();

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_market_global).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_market_global).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        hideAppTitle();
        setupViews(newsView);


        return newsView;
    }

    private void setupViews(View parent) {

        errorLayout = parent.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");
        swipeRefresh = parent.findViewById(R.id.refreshList);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        ListView listGlobal = parent.findViewById(R.id.listMarketComodityCurency);
        commonAdapter = new CustomAdapter(getMainActivity(), new ArrayList<GlobalMarketModel>());
        listGlobal.setAdapter(commonAdapter);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            LinearLayout header_layout = parent.findViewById(R.id.header_layout);
            LinearLayout header_layout1 = parent.findViewById(R.id.header_layout1);
            header_layout.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
            header_layout1.setBackgroundColor(getResources().getColor(R.color.grayStrip_bg));
        }

        sendGlobalIndicesRequest();
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        hideProgress();
        if (action == ActionCode.ACT_CODE_OK.value) {
            GreekDialog.alertDialog(getActivity(), 0, GREEK, msg, "OK", true, null);
        }
    }

    public void sendGlobalIndicesRequest() {
        commonAdapter.clear();
        commonAdapter.notifyDataSetChanged();

        if (!pullToRefreshFlag) {
            showProgress();
        }
        streamController.sendGlobalIndicesRequest(getMainActivity(), serviceResponseHandler); //TODO PK
    }


    @Override
    public void handleResponse(Object response) {
        hideProgress();
        JSONResponse jsonResponse = (JSONResponse) response;
        if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GLOBAL_INDICES_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                MarketsGlobalIndicesResponse globalIndicesResponse = (MarketsGlobalIndicesResponse) jsonResponse.getResponse();
                handleGlobalIndicesResponsex(globalIndicesResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        refreshComplete();
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
        if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }

    private void handleGlobalIndicesResponsex(MarketsGlobalIndicesResponse globalIndicesResponse) {
        List<GlobalIndice> indices = globalIndicesResponse.getGlobalIndices();
        for (GlobalIndice indianIndice : indices) {
            CommonRowData commonRow = new CommonRowData();
            commonRow.setHead1(indianIndice.getIndicesName());
            commonRow.setHead2(String.format("%.2f", Double.parseDouble(indianIndice.getClose())));
            commonRow.setHead3(String.format("%.2f", Double.parseDouble(indianIndice.getChange())));
            commonRow.setHead4(indianIndice.getCountry());
            commonRow.setSubHead1(indianIndice.getDate());

            commonRow.setSubHead1(DateTimeFormatter.getDateFromTimeStamp(indianIndice.getDate(), "dd MMM yyyy", "bse")); //default bse sent
            commonRow.setSubHead2(indianIndice.getPerChange() + "%");
            commonAdapter.add(commonRow);
        }
        commonAdapter.notifyDataSetChanged();
        toggleErrorLayout(commonAdapter.getCount() <= 0);
    }

    public class CustomAdapter extends BaseAdapter {
        private final Context mContext;
        ArrayList<GlobalMarketModel> globalMarketList = new ArrayList<>();

        public CustomAdapter(Context context, ArrayList<GlobalMarketModel> globalMarketList) {
            this.mContext = context;
            this.globalMarketList = globalMarketList;
        }

        public void clear() {
            globalMarketList.clear();
        }

        public void add(CommonRowData commonRow) {
            GlobalMarketModel model = new GlobalMarketModel(commonRow.getHead1(), commonRow.getSubHead1(), commonRow.getHead2(), commonRow.getHead3(), commonRow.getSubHead2(), commonRow.getHead4());
            globalMarketList.add(model);
        }

        @Override
        public int getCount() {
            return globalMarketList.size();
        }

        @Override
        public Object getItem(int position) {
            return globalMarketList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_market_global, null);
                holder.tvChange = convertView.findViewById(R.id.tvChange);
                holder.tvCurrentPrice = convertView.findViewById(R.id.tvCurrentPrice);
                holder.tvName = convertView.findViewById(R.id.tvName);
                holder.tvCountry = convertView.findViewById(R.id.tvCountry);
                holder.tvDate = convertView.findViewById(R.id.tvDate);
                holder.tvChangePer = convertView.findViewById(R.id.tvChangePer);
                holder.dividerGlobalLabel = convertView.findViewById(R.id.dividerglobal);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            GlobalMarketModel model = globalMarketList.get(position);

            holder.tvName.setText(model.getName());
            holder.tvCountry.setText(model.getCountry());
            holder.tvCurrentPrice.setText(model.getClose());
            holder.tvDate.setText(model.getDate());
            holder.tvChange.setText(model.getChange());
            holder.tvChangePer.setText(model.getPerChange());

            if (model.getChange().startsWith("-")) {
                holder.tvChange.setTextColor(getResources().getColor(R.color.red));
            } else {
                holder.tvChange.setTextColor(getResources().getColor(AccountDetails.textColorBlue));
            }

            if (model.getPerChange().startsWith("-")) {
                holder.tvChangePer.setTextColor(getResources().getColor(R.color.red));
            } else {
                holder.tvChangePer.setTextColor(getResources().getColor(AccountDetails.textColorBlue));
            }

//           [TODO: SUSHANT]
            int textColor = AccountDetails.getTextColorDropdown();

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.tvName.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvCurrentPrice.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvChange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvCountry.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.tvDate.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.dividerGlobalLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.tvName.setTextColor(getResources().getColor(textColor));
                holder.tvCurrentPrice.setTextColor(getResources().getColor(textColor));
                holder.tvChange.setTextColor(getResources().getColor(textColor));
                holder.tvCountry.setTextColor(getResources().getColor(textColor));
                holder.tvDate.setTextColor(getResources().getColor(textColor));
                holder.dividerGlobalLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));
            }

            return convertView;
        }

        public class Holder {
            GreekTextView tvName, tvCurrentPrice, tvChange, tvCountry, tvDate, tvChangePer;
            View dividerGlobalLabel;
        }
    }
}
