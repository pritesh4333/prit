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
import android.widget.ListView;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FiiInvestmentModel;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FpiInvestmentDetails;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FpiInvestmentHistoryRequest;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FpiInvestmentRequest;
import com.acumengroup.greekmain.core.model.FiiDiiActivity.FpiInvestmentResponse;
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

public class FpiInvestmentFragment extends GreekBaseFragment implements View.OnClickListener, GreekUIServiceHandler, GreekConstants {
    private TableFixHeaders tableFixHeaders;
    private View fpiInvestmentView;
    private ListView listFpiInvestment;
    private static GreekBaseFragment previousFragment;
    private WatchlistLayoutAdapter baseTableAdapter;
    private AlertDialog levelDialog;
    private GreekTextView reportingdatetxt, reportingdatevalue, conversiontxt, conversionvalue;
    private List<String> reportingdatelist = new ArrayList<>();
    private List<FiiInvestmentModel> fpiInvestmentModelList = new ArrayList<>();
    private Spinner reportdateSpinner;
    ArrayAdapter<String> reportDateSpinAdapter;
    FpiInvestmentResponse fpiInvestmentResponse;
    private CustomAdapter commonAdapter;
    private GreekEditText startDate_text, endDate_text;
    private int year, month, day;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fpiInvestmentView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_fpi_investment).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_fpi_investment).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        reportingdatetxt = fpiInvestmentView.findViewById(R.id.reportingdatetxt);
        //reportingdatevalue = (TextView)fpiInvestmentView.findViewById(R.id.reportingdate_value);
        conversiontxt = fpiInvestmentView.findViewById(R.id.conversiontxt);
        conversionvalue = fpiInvestmentView.findViewById(R.id.conversion_value);

        reportdateSpinner = fpiInvestmentView.findViewById(R.id.reportingdatespinner);

        startDate_text = fpiInvestmentView.findViewById(R.id.start_text);
        startDate_text.setShowSoftInputOnFocus(false);
        startDate_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate_text.setText("");
                showDialog(startDate_text);
            }
        });

        endDate_text = fpiInvestmentView.findViewById(R.id.endDate_text);
        endDate_text.setShowSoftInputOnFocus(false);
        endDate_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDate_text.setText("");
                showDialog(endDate_text);
            }
        });

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            reportingdatetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //reportingdatevalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            conversiontxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            conversionvalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            startDate_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            startDate_text.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            endDate_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            endDate_text.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        } else {
            reportingdatetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            //reportingdatevalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            conversiontxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            conversionvalue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            startDate_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            startDate_text.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            endDate_text.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            endDate_text.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }

        listFpiInvestment = fpiInvestmentView.findViewById(R.id.listFpiInvestment);

        commonAdapter = new CustomAdapter(getMainActivity(), new ArrayList<FiiInvestmentModel>());
        listFpiInvestment.setAdapter(commonAdapter);

        sendFpiRequest();

        reportDateSpinAdapter = new ArrayAdapter<>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), reportingdatelist);
        reportDateSpinAdapter.setDropDownViewResource(R.layout.custom_spinner);
        reportdateSpinner.setAdapter(reportDateSpinAdapter);
        reportdateSpinner.setOnItemSelectedListener(reportDateItemSelectedListener);


        /*tableFixHeaders = (TableFixHeaders) fpiInvestmentView.findViewById(R.id.table);
        String headers[] = {"Category", "Investment Route", "Gross Purchases(Cr.)", "Gross Sales(Cr.)", "Net Investment(Cr.)", "Net Inv US($) million"};
        int[] widths = {80, 90, 90, 75, 90, 80};
        baseTableAdapter = new WatchlistLayoutAdapter(getMainActivity(), headers, widths);
        sendFpiRequest();
        baseTableAdapter.setSortingEnabled(false);
        baseTableAdapter.setOnRowClickListener(new WatchlistLayoutAdapter.TableItemClickListener() {
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

                view1.setOnClickListener(FpiInvestmentFragment.this);
                view2.setOnClickListener(FpiInvestmentFragment.this);
                view3.setOnClickListener(FpiInvestmentFragment.this);
                view4.setOnClickListener(FpiInvestmentFragment.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
                builder.setView(layout);
                levelDialog = builder.create();
                levelDialog.show();

            }

            @Override
            public void onGreekViewClick(int row, View view) {

            }
        });
        tableFixHeaders.setAdapter(baseTableAdapter);*/

        hideAppTitle();
        //setAppTitle(getClass().toString(), "FPI Investment");
        return fpiInvestmentView;
    }

    private void showDialog(final EditText Date_text) {

        Calendar now = getInstance();

        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH);
        day = now.get(Calendar.DAY_OF_MONTH);

//To select date
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

                        FpiInvestmentHistoryRequest.sendRequest(String.valueOf(strDate.getTime() / 1000), String.valueOf(EDdate.getTime() / 1000), getMainActivity(), serviceResponseHandler);

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
            handleResponseOnReportDateSelection();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void handleResponseOnReportDateSelection() {
        baseTableAdapter.clear();
        if (fpiInvestmentResponse.getFpiInvestmentDetails().size() > 0) {
            //reportingdatevalue.setText(fpiInvestmentResponse.getFpiInvestmentDetails().get(0).getReportingDate());

            for (FpiInvestmentDetails fpiInvestmentDetails : fpiInvestmentResponse.getFpiInvestmentDetails()) {
                if (reportdateSpinner.getSelectedItem().toString().equalsIgnoreCase(fpiInvestmentDetails.getReportingDate())) {
                    LinkedHashMap<String, String> hm = new LinkedHashMap<>();
                    hm.put("Category", fpiInvestmentDetails.getInvestment_category());
                    hm.put("Investment Route", fpiInvestmentDetails.getInvestment_route());
                    hm.put("Gross Purchases(Cr.)", fpiInvestmentDetails.getGross_purchases());
                    hm.put("Gross Sales(Cr.)", fpiInvestmentDetails.getGross_sales());
                    hm.put("Net Investment(Cr.)", fpiInvestmentDetails.getNet_investement_inr());
                    hm.put("Net Inv US($) million", fpiInvestmentDetails.getNet_investment_usd());
                    conversionvalue.setText(fpiInvestmentResponse.getFpiInvestmentDetails().get(0).getConversion());
                    //commonAdapter.add()
                    baseTableAdapter.add(hm);
                }
            }

        }
        baseTableAdapter.notifyDataSetChanged();
        hideProgress();
    }

    private void sendFpiRequest() {

        FpiInvestmentRequest.sendRequest(getActivity(), "", "","", serviceResponseHandler);
    }

    public static FpiInvestmentFragment newInstance(String source, String type, GreekBaseFragment previousFragment) {
        Bundle args = new Bundle();
        args.putString("Source", source);
        args.putString("Type", type);
        FpiInvestmentFragment fragment = new FpiInvestmentFragment();
        fragment.setArguments(args);
        FpiInvestmentFragment.previousFragment = previousFragment;
        return fragment;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        try {
            fpiInvestmentResponse = (FpiInvestmentResponse) jsonResponse.getResponse();

            fpiInvestmentModelList.clear();
            List<String> reportingdatelist = new ArrayList<>();
            for (int i = 0; i < fpiInvestmentResponse.getFpiInvestmentDetails().size(); i++) {
                if (!reportingdatelist.contains(fpiInvestmentResponse.getFpiInvestmentDetails().get(i).getReportingDate())) {
                    reportingdatelist.add(fpiInvestmentResponse.getFpiInvestmentDetails().get(i).getReportingDate());
                }
            }

            for (int k = 0; k < reportingdatelist.size(); k++) {
                FiiInvestmentModel fiiInvestmentModel = new FiiInvestmentModel();
                fiiInvestmentModel.setReportingDate(reportingdatelist.get(k));
                for (int j = 0; j < fpiInvestmentResponse.getFpiInvestmentDetails().size(); j++) {
                    if (reportingdatelist.get(k).equalsIgnoreCase(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getReportingDate())) {
                        if (fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getInvestment_category().toLowerCase().contains("fii")) {
                            fiiInvestmentModel.setFii_investment_category(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getInvestment_category());
                            fiiInvestmentModel.setFii_gross_purchases(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getGross_purchases());
                            fiiInvestmentModel.setFii_gross_sales(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getGross_sales());
                            fiiInvestmentModel.setFii_net_investement_inr(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getNet_investement_inr());

                        } else if (fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getInvestment_category().toLowerCase().contains("dii")) {
                            fiiInvestmentModel.setDii_investment_category(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getInvestment_category());
                            fiiInvestmentModel.setDii_gross_purchases(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getGross_purchases());
                            fiiInvestmentModel.setDii_gross_sales(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getGross_sales());
                            fiiInvestmentModel.setDii_net_investement_inr(fpiInvestmentResponse.getFpiInvestmentDetails().get(j).getNet_investement_inr());
                        }
                    }

                }

                fpiInvestmentModelList.add(fiiInvestmentModel);
            }

            commonAdapter.setData(fpiInvestmentModelList);
            commonAdapter.notifyDataSetChanged();
            //commonAdapter.setData(fpiInvestmentResponse.getFpiInvestmentDetails());


            //commonAdapter.notifyDataSetChanged();

            hideProgress();
        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
        }
    }


    public class CustomAdapter extends BaseAdapter {
        private final Context mContext;
        private List<FiiInvestmentModel> fpiList;
        ArrayList<String> tokenList;

        public CustomAdapter(Context context, List<FiiInvestmentModel> fpiList) {
            this.mContext = context;
            this.fpiList = fpiList;
            tokenList = new ArrayList<>();
        }

        public void setData(List<FiiInvestmentModel> fpiList) {
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

        public void updateData(int position, FiiInvestmentModel model) {
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
        public FiiInvestmentModel getItem(int position) {
            return fpiList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomAdapter.Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_fpi_investment, parent, false);
                holder = new CustomAdapter.Holder();

                holder.row00 = convertView.findViewById(R.id.row00);
                holder.row11 = convertView.findViewById(R.id.row11);
                holder.row12 = convertView.findViewById(R.id.row12);
                holder.row13 = convertView.findViewById(R.id.row13);
                holder.row14 = convertView.findViewById(R.id.row14);
                holder.row21 = convertView.findViewById(R.id.row21);
                holder.row22 = convertView.findViewById(R.id.row22);
                holder.row23 = convertView.findViewById(R.id.row23);
                holder.row24 = convertView.findViewById(R.id.row24);
                // holder.dividerLabel = (View) convertView.findViewById(R.id.dividerDerivative);

                convertView.setTag(holder);
            } else {
                holder = (CustomAdapter.Holder) convertView.getTag();
            }

            FiiInvestmentModel model = getItem(position);

            holder.row00.setText(model.getReportingDate());
            holder.row11.setText(model.getFii_investment_category());
            holder.row12.setText(model.getFii_gross_purchases());
            holder.row13.setText(model.getFii_gross_sales());
            holder.row14.setText(model.getFii_net_investement_inr());
            holder.row21.setText(model.getDii_investment_category());
            holder.row22.setText(model.getDii_gross_purchases());
            holder.row23.setText(model.getDii_gross_sales());
            holder.row24.setText(model.getDii_net_investement_inr());


//            [TODO: SUSHANT]
            int textColor = AccountDetails.getTextColorDropdown();

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                convertView.setBackgroundResource(position % 2 == 0 ? AccountDetails.backgroundColor : AccountDetails.backgroundColor);
                holder.row00.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row11.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row12.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row13.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row14.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row21.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row22.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row23.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.row24.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                // holder.dividerLabel.setBackgroundColor(getResources().getColor(AccountDetails.textColorDropdown));

            } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
                convertView.setBackgroundResource(position % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
                holder.row00.setTextColor(getResources().getColor(textColor));
                holder.row11.setTextColor(getResources().getColor(textColor));
                holder.row12.setTextColor(getResources().getColor(textColor));
                holder.row13.setTextColor(getResources().getColor(textColor));
                holder.row14.setTextColor(getResources().getColor(textColor));
                holder.row21.setTextColor(getResources().getColor(textColor));
                holder.row22.setTextColor(getResources().getColor(textColor));
                holder.row23.setTextColor(getResources().getColor(textColor));
                holder.row24.setTextColor(getResources().getColor(textColor));


                //  holder.dividerLabel.setBackgroundColor(getResources().getColor(textColor));
            }
            return convertView;
        }

        public class Holder {

            GreekTextView row00, row11, row12, row13, row14, row21, row22, row23, row24;
            //View dividerLabel;
        }
    }
}
