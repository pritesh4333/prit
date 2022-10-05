package com.acumengroup.mobile.reports;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FpiDerivativeHistoryRequest;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FpiDerivativeRequest;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FpiDerivativeResponse;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FpiDerivativesModel;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FpiInvestmentDetails;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.tablefixheader.TableFixHeaders;
import com.acumengroup.mobile.tablefixheader.trade.WatchlistLayoutAdapter;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static java.util.Calendar.getInstance;

public class FpiDerivativesFragment extends GreekBaseFragment implements View.OnClickListener, GreekUIServiceHandler, GreekConstants {
    private TableFixHeaders tableFixHeaders;
    private ListView listFpiDerivatives;
    private View fpiDerivativeView;
    private static GreekBaseFragment previousFragment;
    private WatchlistLayoutAdapter baseTableAdapter;
    private AlertDialog levelDialog;
    private GreekTextView reportingdatetxt, reportingdatevalue, conversiontxt, conversionvalue;
    private LinearLayout layout1;
    FpiDerivativeResponse fpiDerivativeResponse;
    private Spinner reportdateSpinner;
    ArrayAdapter<String> reportDateSpinAdapter;
    private List<String> reportingdatelist = new ArrayList<>();
    private CustomAdapter commonAdapter;
    private List<FpiDerivativesModel> fpiDerivativesModelList = new ArrayList<>();
    private GreekEditText startDate_text, endDate_text;
    private int year, month, day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fpiDerivativeView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_fpi_derivatives).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_fpi_derivatives).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        reportingdatetxt = fpiDerivativeView.findViewById(R.id.reportingdatetxt);
        //reportingdatevalue = (TextView)fpiDerivativeView.findViewById(R.id.reportingdate_value);
        conversiontxt = fpiDerivativeView.findViewById(R.id.conversiontxt);
        conversionvalue = fpiDerivativeView.findViewById(R.id.conversion_value);
        layout1 = fpiDerivativeView.findViewById(R.id.layout1);
        layout1.setVisibility(View.GONE);

        reportdateSpinner = fpiDerivativeView.findViewById(R.id.reportingdatespinner);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            reportingdatetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //reportingdatevalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            conversiontxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            conversionvalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        } else {
            reportingdatetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //reportingdatevalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            conversiontxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            conversionvalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }

        listFpiDerivatives = fpiDerivativeView.findViewById(R.id.listFpiInvestment);

        commonAdapter = new FpiDerivativesFragment.CustomAdapter(getMainActivity(), new ArrayList<FpiDerivativesModel>());
        listFpiDerivatives.setAdapter(commonAdapter);

        reportDateSpinAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), reportingdatelist);

        reportDateSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
        reportdateSpinner.setAdapter(reportDateSpinAdapter);
        reportdateSpinner.setOnItemSelectedListener(reportDateItemSelectedListener);

        sendFpiRequest();

        startDate_text = fpiDerivativeView.findViewById(R.id.start_text);
        startDate_text.setShowSoftInputOnFocus(false);
        startDate_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate_text.setText("");
                showDialog(startDate_text);
            }
        });

        endDate_text = fpiDerivativeView.findViewById(R.id.endDate_text);
        endDate_text.setShowSoftInputOnFocus(false);
        endDate_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDate_text.setText("");
                showDialog(endDate_text);
            }
        });
        /*tableFixHeaders = (TableFixHeaders) fpiDerivativeView.findViewById(R.id.table);
        String headers[] = {"Derivative Products", "No. of contracts(Buy)", "Amount(Buy)(Cr.)", "No. of contracts(Sell)", "Amount(Sell)(Cr.)", "No. of contracts(OI)","Amount(OI)(Cr.)"};
        int[] widths = {85, 90, 90, 75, 90, 80, 80};
        baseTableAdapter = new WatchlistLayoutAdapter(getMainActivity(), headers, widths);
        sendFpiRequest();
        baseTableAdapter.setSortingEnabled(false);
        *//*baseTableAdapter.setOnRowClickListener(new WatchlistLayoutAdapter.TableItemClickListener() {
            @Override
            public void onClick(int row, View view) {
                View layout = LayoutInflater.from(getMainActivity()).inflate(R.layout.alert_quick_four_actions, null);
                TextView view1 = (TextView) layout.findViewById(R.id.pop_quote);
                view1.setText("Get Quote");
                TextView view2 = (TextView) layout.findViewById(R.id.pop_advice);
                view2.setText("Redeem");
                TextView view3 = (TextView) layout.findViewById(R.id.pop_trade);
                view3.setText("Add Purchase");
                TextView view4 = (TextView) layout.findViewById(R.id.pop_trade_sell);
                view4.setText("SIP");

                view1.setOnClickListener(FpiDerivativesFragment.this);
                view2.setOnClickListener(FpiDerivativesFragment.this);
                view3.setOnClickListener(FpiDerivativesFragment.this);
                view4.setOnClickListener(FpiDerivativesFragment.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
                builder.setView(layout);
                levelDialog = builder.create();
                levelDialog.show();

            }

            @Override
            public void onGreekViewClick(int row, View view) {

            }
        });*//*
        tableFixHeaders.setAdapter(baseTableAdapter);*/

        hideAppTitle();
        //setAppTitle(getClass().toString(), "FPI Investment");
        return fpiDerivativeView;
    }

    private void showDialog(final EditText Date_text) {

        Calendar now = getInstance();

        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH);
        day = now.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dpd = new DatePickerDialog(getMainActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Date_text.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                if (startDate_text.getText().toString() != null && startDate_text.getText().toString().length() > 0 &&
                        endDate_text.getText().toString() != null && endDate_text.getText().toString().length() > 0) {

                    String startDate = startDate_text.getText().toString();
                    String endDate = endDate_text.getText().toString();
                    showProgress();


                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date strDate = sdf.parse(startDate);
                        Date EDdate = sdf.parse(endDate);

                        FpiDerivativeHistoryRequest.sendRequest(String.valueOf(strDate.getTime() / 1000), String.valueOf(EDdate.getTime() / 1000), getMainActivity(), serviceResponseHandler);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, year, month, day);
        dpd.show();
    }

    private final AdapterView.OnItemSelectedListener reportDateItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            showProgress();
            //handleResponseOnReportDateSelection();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void sendFpiRequest() {

        FpiDerivativeRequest.sendRequest(getActivity(), serviceResponseHandler);
    }

    private void handleResponseOnReportDateSelection() {
        baseTableAdapter.clear();
        if (fpiDerivativeResponse.getFpiInvestmentDetails().size() > 0) {
            //reportingdatevalue.setText(fpiInvestmentResponse.getFpiInvestmentDetails().get(0).getReportingDate());

            for (FpiInvestmentDetails fpiInvestmentDetails : fpiDerivativeResponse.getFpiInvestmentDetails()) {
                if (reportdateSpinner.getSelectedItem().toString().equalsIgnoreCase(fpiInvestmentDetails.getReportingDate())) {
                    LinkedHashMap<String, String> hm = new LinkedHashMap<>();
                    hm.put("Derivative Products", fpiInvestmentDetails.getDerivative_product());
                    hm.put("No. of contracts(Buy)", fpiInvestmentDetails.getBuy_contracts());
                    hm.put("Amount(Buy)(Cr.)", fpiInvestmentDetails.getBuy_ammount());
                    hm.put("No. of contracts(Sell)", fpiInvestmentDetails.getSell_contracts());
                    hm.put("Amount(Sell)(Cr.)", fpiInvestmentDetails.getSell_ammount());
                    hm.put("No. of contracts(OI)", fpiInvestmentDetails.getOi_contracts());
                    hm.put("Amount(OI)(Cr.)", fpiInvestmentDetails.getOi_ammount());
                    baseTableAdapter.add(hm);
                }
            }

        }
        baseTableAdapter.notifyDataSetChanged();
        hideProgress();
    }

    public static FpiDerivativesFragment newInstance(String source, String type, GreekBaseFragment previousFragment) {
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        FpiDerivativesFragment fragment = new FpiDerivativesFragment();
        fragment.setArguments(args);
        FpiDerivativesFragment.previousFragment = previousFragment;
        return fragment;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        try {
            fpiDerivativeResponse = (FpiDerivativeResponse) jsonResponse.getResponse();
            // commonAdapter.setData(fpiDerivativeResponse.getFpiInvestmentDetails());
            // commonAdapter.notifyDataSetChanged();
            //List<FpiDerivativesModel> fpiDerivativesModelList = new ArrayList<>();
            fpiDerivativesModelList.clear();
            List<String> reportingdatelist = new ArrayList<>();
            for (int i = 0; i < fpiDerivativeResponse.getFpiInvestmentDetails().size(); i++) {
                if (!reportingdatelist.contains(fpiDerivativeResponse.getFpiInvestmentDetails().get(i).getReportingDate())) {
                    reportingdatelist.add(fpiDerivativeResponse.getFpiInvestmentDetails().get(i).getReportingDate());
                }
            }

            for (int k = 0; k < reportingdatelist.size(); k++) {
                FpiDerivativesModel fpiDerivativesModel = new FpiDerivativesModel();
                fpiDerivativesModel.setReportingDate(reportingdatelist.get(k));
                for (int j = 0; j < fpiDerivativeResponse.getFpiInvestmentDetails().size(); j++) {
                    if (reportingdatelist.get(k).equalsIgnoreCase(fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getReportingDate())) {
                        if (fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getDerivative_product().equalsIgnoreCase("index futures")) {
                            Double NetAmt = Double.parseDouble(fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getBuy_ammount()) - Double.parseDouble(fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getSell_ammount());
                            fpiDerivativesModel.setIndex_futures(String.format("%.2f", NetAmt));
                        } else if (fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getDerivative_product().equalsIgnoreCase("index options")) {
                            Double NetAmt = Double.parseDouble(fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getBuy_ammount()) - Double.parseDouble(fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getSell_ammount());
                            fpiDerivativesModel.setIndex_options(String.format("%.2f", NetAmt));
                        } else if (fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getDerivative_product().equalsIgnoreCase("stock futures")) {
                            Double NetAmt = Double.parseDouble(fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getBuy_ammount()) - Double.parseDouble(fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getSell_ammount());
                            fpiDerivativesModel.setStock_futures(String.format("%.2f", NetAmt));
                        } else if (fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getDerivative_product().equalsIgnoreCase("stock options")) {
                            Double NetAmt = Double.parseDouble(fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getBuy_ammount()) - Double.parseDouble(fpiDerivativeResponse.getFpiInvestmentDetails().get(j).getSell_ammount());
                            fpiDerivativesModel.setStock_options(String.format("%.2f", NetAmt));
                        }
                    }

                }

                fpiDerivativesModelList.add(fpiDerivativesModel);
            }

            commonAdapter.setData(fpiDerivativesModelList);
            commonAdapter.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class CustomAdapter extends BaseAdapter {
        private final Context mContext;
        private List<FpiDerivativesModel> fpiList;
        ArrayList<String> tokenList;

        public CustomAdapter(Context context, List<FpiDerivativesModel> fpiList) {
            this.mContext = context;
            this.fpiList = fpiList;
            tokenList = new ArrayList<>();
        }

        public void setData(List<FpiDerivativesModel> fpiList) {
            this.fpiList = fpiList;
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

        public void updateData(int position, FpiDerivativesModel model) {
            fpiList.set(position, model);
        }

        public void clear() {
            this.fpiList.clear();
            this.tokenList.clear();
        }

        @Override
        public int getCount() {
            return fpiList.size();
        }

        @Override
        public FpiDerivativesModel getItem(int position) {
            return fpiList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FpiDerivativesFragment.CustomAdapter.Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_fpi_derivatives, parent, false);
                holder = new Holder();

                holder.row00 = convertView.findViewById(R.id.row00);
                holder.row11 = convertView.findViewById(R.id.row11);
                holder.row12 = convertView.findViewById(R.id.row12);
                holder.row13 = convertView.findViewById(R.id.row13);
                holder.row14 = convertView.findViewById(R.id.row14);
                // holder.dividerLabel = (View) convertView.findViewById(R.id.dividerDerivative);

                convertView.setTag(holder);
            } else {
                holder = (FpiDerivativesFragment.CustomAdapter.Holder) convertView.getTag();
            }

            FpiDerivativesModel model = getItem(position);

            holder.row00.setText(model.getReportingDate());
            holder.row11.setText(model.getIndex_futures());
            holder.row12.setText(model.getIndex_options());
            holder.row13.setText(model.getStock_futures());
            holder.row14.setText(model.getStock_options());



//            [TODO: SUSHANT]
            int textColor = AccountDetails.getTextColorDropdown();

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.row00.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row11.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row12.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row13.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row14.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                // holder.dividerLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.row00.setTextColor(getResources().getColor(textColor));
                holder.row11.setTextColor(getResources().getColor(textColor));
                holder.row12.setTextColor(getResources().getColor(textColor));
                holder.row13.setTextColor(getResources().getColor(textColor));
                holder.row14.setTextColor(getResources().getColor(textColor));
                //  holder.dividerLabel.setBackgroundColor(getResources().getColor(textColor));
            }
            return convertView;
        }

        public class Holder {

            GreekTextView row00, row11, row12, row13, row14;
            //View dividerLabel;
        }
    }
}
