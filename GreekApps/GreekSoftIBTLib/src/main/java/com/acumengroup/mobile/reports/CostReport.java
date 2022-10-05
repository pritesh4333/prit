package com.acumengroup.mobile.reports;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.OrderBookResponses;
import com.acumengroup.mobile.model.FNOResponse;
import com.acumengroup.mobile.model.PandLReportRequest;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.columnsliderlib.AdaptiveTableLayout;
import com.acumengroup.columnsliderlib.LinkedAdaptiveTableAdapter;
import com.acumengroup.columnsliderlib.ViewHolderImpl;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

public class CostReport extends GreekBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private AdaptiveTableLayout adaptiveTableLayout;
    private DateFormat df;
    private Spinner spinner1;
    private GreekTextView errorMsgLayout, txt_ttlPNL,empty_view, txt_cost_report, total_view, total_txt;
    private ImageButton sort_btn;
    private String startDate = "", endDate = "";
    private int year, month, day;
    private Calendar calendar;
    List<String> spinner1List;
    private String serviceName;
    View view;
    private LinearLayout cost_report_parentLayout, linear2;
    private RelativeLayout table_relativeLayout;


    public static CostReport newInstance(String param1, String param2) {
        CostReport fragment = new CostReport();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CostReport() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        spinner1List = new ArrayList<>();
        spinner1List.add("EQ");
        spinner1List.add("FNO");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        view = inflater.inflate(R.layout.fragment_cost_report, container, false);
        view = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_cost_report).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_cost_report).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);

        adaptiveTableLayout = view.findViewById(R.id.cost_report_tableLayout);
        df = new SimpleDateFormat("MMM dd yyyy", Locale.US);
        spinner1 = view.findViewById(R.id.cost_report_spinner);
        errorMsgLayout = view.findViewById(R.id.empty_view);
        txt_ttlPNL = view.findViewById(R.id.cost_report_txt_ttlPNL);
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        txt_ttlPNL.setText(dateFormat.format(cal.getTime()));
        errorMsgLayout.setVisibility(View.VISIBLE);
        adaptiveTableLayout.setVisibility(View.GONE);
        sort_btn = view.findViewById(R.id.sort_btn);

        empty_view = view.findViewById(R.id.empty_view);
        txt_cost_report = view.findViewById(R.id.txt_cost_report);
        cost_report_parentLayout = view.findViewById(R.id.cost_report_parentLayout);
        linear2 = view.findViewById(R.id.linear2);
        table_relativeLayout = view.findViewById(R.id.table_relativeLayout);
        total_txt = view.findViewById(R.id.total_txt);
        total_view = view.findViewById(R.id.total_view);

        setTheme();
        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                PopupMenu popup = new PopupMenu(getContext(), sort_btn);
                PopupMenu popup;

                Context wrapper = new ContextThemeWrapper(getMainActivity(), R.style.popupMenuWhite);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    popup = new PopupMenu(wrapper, sort_btn);
                } else {
                    popup = new PopupMenu(getMainActivity(), sort_btn);
                }

                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.sort_report_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        DateFormat df = new SimpleDateFormat("MMM dd yyyy", Locale.US);

                        if (item.getItemId() == R.id.one) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.MONTH, 0);
                            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                            Date monthFirstDay = calendar.getTime();
                            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                            Date monthLastDay = calendar.getTime();

                            startDate = df.format(monthFirstDay);
                            Date c = Calendar.getInstance().getTime();
                            endDate = df.format(c);
                            sendReportRequest();


                        } else if (item.getItemId() == R.id.two) {

                            Calendar aCalendar = Calendar.getInstance();
                            aCalendar.add(Calendar.MONTH, -1);
                            aCalendar.set(Calendar.DATE, 1);

                            Date firstDateOfPreviousMonth = aCalendar.getTime();
                            startDate = df.format(firstDateOfPreviousMonth);

                            aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                            Date lastDateOfPreviousMonth = aCalendar.getTime();
                            endDate = df.format(lastDateOfPreviousMonth);
                            sendReportRequest();


                        } else if (item.getItemId() == R.id.three) {

                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);

                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.YEAR, year - 2);
                            cal.set(Calendar.MONTH, 3);
                            cal.set(Calendar.DAY_OF_MONTH, 1);
                            Date start = cal.getTime();
                            startDate = df.format(start);

                            cal.set(Calendar.YEAR, year - 1);
                            cal.set(Calendar.MONTH, 2); // 11 = december
                            cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve

                            Date end = cal.getTime();

                            endDate = df.format(end);
                            sendReportRequest();


                        } else if (item.getItemId() == R.id.five) {

                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);

                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.YEAR, year);
                            cal.set(Calendar.MONTH, 3);
                            cal.set(Calendar.DAY_OF_MONTH, 1);
                            Date start = cal.getTime();
                            startDate = df.format(start);

                            cal.set(Calendar.YEAR, year + 1);
                            cal.set(Calendar.MONTH, 2); // 11 = december
                            cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve


                            Date end = cal.getTime();

                            endDate = df.format(end);
                            sendReportRequest();
                        } else if (item.getItemId() == R.id.four) {
                            showCustomDialog();


                        }


                        return true;
                    }
                });

                popup.show();


            }
        });

        final Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple(), spinner1List) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }else {
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
        spinner1.setAdapter(assetTypeAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    // If 0th position selected It will send request to aracane server to get LeviesCM details
                    serviceName = "getLeviesCM_Bajaj";

                } else if (position == 1) {
//                    If 1st position selected It will send request to aracane server to get FNO details
                    serviceName = "getLeviesFNO_Bajaj";

                }

                sendReportRequest();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> TypeAdapter = new ArrayAdapter<String>(getMainActivity(), AccountDetails.getRowSpinnerSimple()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }else {
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


        return view;


    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
        txt_cost_report.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        empty_view.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        total_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        total_view.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        sort_btn.setBackground(getResources().getDrawable(R.drawable.ic_filter));
        adaptiveTableLayout.setBackground(getResources().getDrawable(R.color.white));
        table_relativeLayout.setBackground(getResources().getDrawable(R.color.white));
        cost_report_parentLayout.setBackground(getResources().getDrawable(R.color.white));
        linear2.setBackground(getResources().getDrawable(R.color.white));
        spinner1.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
    }

    }

    private void sendReportRequest() {

        if (startDate.length() <= 0 || endDate.length() <= 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            Date monthFirstDay = calendar.getTime();
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date monthLastDay = calendar.getTime();

            startDate = df.format(monthFirstDay);
            Date c = Calendar.getInstance().getTime();
            endDate = df.format(c);
        }
        errorMsgLayout.setVisibility(View.VISIBLE);
        adaptiveTableLayout.setVisibility(View.GONE);

        showProgress();
        PandLReportRequest.sendRequest(AccountDetails.getUsername(getActivity()), serviceName, startDate, endDate, getActivity(), serviceResponseHandler);
    }

    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);
        errorMsgLayout.setVisibility(View.GONE);
        adaptiveTableLayout.setVisibility(View.VISIBLE);

        try {

            Gson gson = new Gson();
            FNOResponse staff = gson.fromJson(String.valueOf(response), FNOResponse.class);

            ArrayList<FNOResponse.Response.FNOData> orderBookDataArrayList = staff.getResponse().getData();
            orderBookDataArrayList.add(0, new FNOResponse.Response.FNOData("",
                    "", "", "",
                    "", "", "",
                    "", "", "",
                    "", "", "",
                    "", "", "",
                    "", "", "",
                    "", "", "",
                    "", "", "",
                    "", "", "",
                    "", "", "",
                    "", ""));
            adaptiveTableLayout.setVisibility(View.VISIBLE);

            ReportDataTableAdapter reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), orderBookDataArrayList);
            adaptiveTableLayout.setAdapter(reportDataTableAdapter);
            adaptiveTableLayout.notifyLayoutChanged();
            reportDataTableAdapter.notifyLayoutChanged();
            hideProgress();
            toggleErrorLayout(false);

//
        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
            toggleErrorLayout(true);
            adaptiveTableLayout.setVisibility(View.GONE);
//            addEmptyRecord();
        }

    }

    private void addEmptyRecord() {

        ArrayList<FNOResponse.Response.FNOData> orderBookDataArrayList = new ArrayList<>();
//        orderBookDataArrayList.add(new FNOResponse.Response.FNOData(
//                "", "", "",
//                "", "", "", "", "", "",
//                "", "", "", "",
//                "", "", "", "", "", "",
//                "", "", "", ""));

        adaptiveTableLayout.setAdapter(new ReportDataTableAdapter(getMainActivity(), orderBookDataArrayList));

    }


    public class ReportDataTableAdapter extends LinkedAdaptiveTableAdapter<ViewHolderImpl> {

        private final LayoutInflater mLayoutInflater;
        private ArrayList<FNOResponse.Response.FNOData> mTableDataSource;
        private final int mColumnWidth;
        private final int mRowHeight;
        private final int mHeaderHeight;
        private final int mHeaderWidth;
        private Context context;

        public ReportDataTableAdapter(Context context, ArrayList<FNOResponse.Response.FNOData> tableDataSource) {
            mLayoutInflater = LayoutInflater.from(context);
            mTableDataSource = new ArrayList<>();
            mTableDataSource.clear();
            mTableDataSource = tableDataSource;
            Resources res = context.getResources();
            mColumnWidth = res.getDimensionPixelSize(R.dimen.column_width_report);
            mRowHeight = res.getDimensionPixelSize(R.dimen.row_height_report);
            mHeaderHeight = res.getDimensionPixelSize(R.dimen.column_header_height_report);
            mHeaderWidth = res.getDimensionPixelSize(R.dimen.row_header_width_report);
        }

        public void setAppList(ArrayList<FNOResponse.Response.FNOData> categoryModel) {

            mTableDataSource.clear();
            mTableDataSource.addAll(categoryModel);
            notifyDataSetChanged();
        }

        public FNOResponse.Response.FNOData getItemScanData(int position) {
            return mTableDataSource.get(position);
        }

        @Override
        public int getRowCount() {
            return mTableDataSource.size();
        }

        @Override
        public int getColumnCount() {
            return 9;
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateItemViewHolder(@NonNull ViewGroup parent) {
            return new TestViewHolder(mLayoutInflater.inflate(R.layout.item_card, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent) {
            return new TestHeaderColumnViewHolder(mLayoutInflater.inflate(R.layout.item_header_column_single, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateRowHeaderViewHolder(@NonNull ViewGroup parent) {
            return new TestHeaderRowViewHolder(mLayoutInflater.inflate(R.layout.report_header_row, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateLeftTopHeaderViewHolder(@NonNull ViewGroup parent) {
            return new TestHeaderLeftTopViewHolder(mLayoutInflater.inflate(R.layout.item_header_left_top, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderImpl viewHolder, int row, int column) {
            final TestViewHolder vh = (TestViewHolder) viewHolder;


            FNOResponse.Response.FNOData itemData = mTableDataSource.get(row); // skip headers
            vh.dataSubtitle.setVisibility(View.GONE);


            if (column == 1) {
                if (itemData.getBrokerage() != null && !itemData.getBrokerage().isEmpty()) {
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getBrokerage())));
                    vh.dataTitle.setGravity(Gravity.RIGHT);

                } else {
                    vh.dataTitle.setText(" ");

                }
            } else if (column == 2) {
                if (itemData.getGST() != null && !itemData.getGST().isEmpty()) {
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getGST())));
                    vh.dataTitle.setGravity(Gravity.RIGHT);

                } else {
                    vh.dataTitle.setText(" ");

                }
            } else if (column == 3) {
                if (itemData.getSecurityTrxTax() != null && !itemData.getSecurityTrxTax().isEmpty()) {
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getSecurityTrxTax())));
                    vh.dataTitle.setGravity(Gravity.RIGHT);

                } else {
                    vh.dataTitle.setText(" ");

                }
            } else if (column == 4) {
                if (itemData.getStampDuty() != null && !itemData.getStampDuty().isEmpty()) {
                    vh.dataTitle.setGravity(Gravity.RIGHT);
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getStampDuty())));

                } else {
                    vh.dataTitle.setText(" ");

                }
            } else if (column == 5) {
                if (itemData.getSebiFees() != null && !itemData.getSebiFees().isEmpty()) {
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getSebiFees())));
                    vh.dataTitle.setGravity(Gravity.RIGHT);

                } else {
                    vh.dataTitle.setText(" ");

                }
            } else if (column == 6) {
                if (itemData.getTransactionCharges() != null && !itemData.getTransactionCharges().isEmpty()) {
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getTransactionCharges())));
                    vh.dataTitle.setGravity(Gravity.RIGHT);

                } else {
                    vh.dataTitle.setText(" ");

                }
            } else if (column == 7) {
                if (itemData.getOtherCharges() != null && !itemData.getOtherCharges().isEmpty()) {
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getOtherCharges())));
                    vh.dataTitle.setGravity(Gravity.RIGHT);

                } else {
                    vh.dataTitle.setText(" ");

                }
            } else if (column == 8) {
                if (itemData.getTotalCharges() != null && !itemData.getTotalCharges().isEmpty()) {
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getTotalCharges())));
                    vh.dataTitle.setGravity(Gravity.RIGHT);

                } else {
                    vh.dataTitle.setText(" ");

                }
            }

        }

        @Override
        public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder, final int column) {
            TestHeaderColumnViewHolder vh = (TestHeaderColumnViewHolder) viewHolder;

//            vh.headerColumnSubtitle.setVisibility(View.GONE);

            if (column == 1) {
                vh.headerColumnTitle.setText("Brokerage");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
              //  vh.headerColumnSubtitle.setText("");
            } else if (column == 2) {
                vh.headerColumnTitle.setText("GST");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                //vh.headerColumnSubtitle.setText("");
            } else if (column == 3) {
                vh.headerColumnTitle.setText("Security Trx Tax");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                //vh.headerColumnSubtitle.setText("");
            } else if (column == 4) {
                vh.headerColumnTitle.setText("Stamp Duty");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                //vh.headerColumnSubtitle.setText("");
            } else if (column == 5) {
                vh.headerColumnTitle.setText("Sebi Fees");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                //vh.headerColumnSubtitle.setText("");
            } else if (column == 6) {
                vh.headerColumnTitle.setText("Transaction Charges");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                //vh.headerColumnSubtitle.setText("");
            } else if (column == 7) {
                vh.headerColumnTitle.setText("Other Charges");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
//                vh.headerColumnSubtitle.setText("");
            } else if (column == 8) {
                vh.headerColumnTitle.setText("To tal Charges");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
  //              vh.headerColumnSubtitle.setText("");
            }

        }

        @Override
        public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {

            TestHeaderRowViewHolder vh = (TestHeaderRowViewHolder) viewHolder;
            vh.headerRowSubtitle.setVisibility(View.GONE);

            if (mTableDataSource.get(row).getTransactionDate() != null && !mTableDataSource.get(row).getTransactionDate().isEmpty()) {
                vh.headerRowTitle.setText(mTableDataSource.get(row).getTransactionDate());

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    vh.headerRowTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    vh.headerRowTitle.setBackground(getResources().getDrawable(R.color.white));

                    vh.headerRowTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    vh.headerRowTitle.setBackground(getResources().getDrawable(R.color.white));
                }
            } else {
                vh.headerRowTitle.setText(" ");
            }
        }

        @Override
        public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {
            TestHeaderLeftTopViewHolder vh = (TestHeaderLeftTopViewHolder) viewHolder;
            vh.leftTopSubtitle.setVisibility(View.GONE);
            vh.leftTopTitle.setText("Transaction Date");
            vh.leftTopSubtitle.setText("");

        }

        @Override
        public int getColumnWidth(int column) {
            return mColumnWidth;
        }

        @Override
        public int getHeaderColumnHeight() {
            return mHeaderHeight;
        }

        @Override
        public int getRowHeight(int row) {
            return mRowHeight;
        }

        @Override
        public int getHeaderRowWidth() {
            return mHeaderWidth;
        }

        //------------------------------------- view holders ------------------------------------------

        private class TestViewHolder extends ViewHolderImpl {
            GreekTextView dataSubtitle, dataTitle;
            LinearLayout layout_itemcard;
            View vLine, viewLine2;

            private TestViewHolder(@NonNull View itemView) {
                super(itemView);
                dataTitle = itemView.findViewById(R.id.dataTitle);
                dataSubtitle = itemView.findViewById(R.id.dataSubtitle);
                layout_itemcard = itemView.findViewById(R.id.linear1);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    layout_itemcard.setBackgroundColor(getResources().getColor(R.color.white));
                    vLine.setBackgroundColor(getResources().getColor(R.color.black));
                    viewLine2.setBackgroundColor(getResources().getColor(R.color.black));
                    dataTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    dataSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
            }
        }

        private class TestHeaderColumnViewHolder extends ViewHolderImpl {
            GreekTextView headerColumnSubtitle, headerColumnTitle;
            LinearLayout headerColumn;
            View vLine, viewLine2;

            private TestHeaderColumnViewHolder(@NonNull View itemView) {
                super(itemView);
                headerColumnTitle = itemView.findViewById(R.id.dataTitle);
                headerColumnSubtitle = itemView.findViewById(R.id.dataSubtitle);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);
                headerColumn = itemView.findViewById(R.id.layout1);

              //  headerColumnSubtitle.setVisibility(View.GONE);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    headerColumn.setBackgroundColor(getResources().getColor(R.color.bajaj_blue));
                    vLine.setBackgroundColor(getResources().getColor(R.color.black));
                    viewLine2.setBackgroundColor(getResources().getColor(R.color.black));
//                    headerColumnTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//                    headerColumnSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }

            }
        }

        private class TestHeaderRowViewHolder extends ViewHolderImpl {
            GreekTextView headerRowTitle, headerRowSubtitle;

            View vLine, viewLine2;
            LinearLayout headerRow;


            TestHeaderRowViewHolder(@NonNull View itemView) {
                super(itemView);
                headerRowTitle = itemView.findViewById(R.id.title);
                headerRowSubtitle = itemView.findViewById(R.id.subTitle);
                headerRow = itemView.findViewById(R.id.linear);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    headerRow.setBackgroundColor(getResources().getColor(R.color.white));
                    headerRowTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    headerRowSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
            }
        }

        private class TestHeaderLeftTopViewHolder extends ViewHolderImpl {
            GreekTextView leftTopSubtitle, leftTopTitle;
            LinearLayout layoutHeaderLeft;
            View vLine, viewLine2;

            private TestHeaderLeftTopViewHolder(@NonNull View itemView) {
                super(itemView);
                leftTopTitle = itemView.findViewById(R.id.title);
                leftTopSubtitle = itemView.findViewById(R.id.subTitle);
                layoutHeaderLeft = itemView.findViewById(R.id.layout);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    layoutHeaderLeft.setBackgroundColor(getResources().getColor(R.color.bajaj_blue));
//                    leftTopTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//                    leftTopSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }
            }
        }
    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = getMainActivity().findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getMainActivity()).inflate(R.layout.custom_date_layout, viewGroup, false);


        final GreekEditText edt_from = dialogView.findViewById(R.id.edt_from);
        final GreekEditText edt_to = dialogView.findViewById(R.id.edt_to);
        final LinearLayout data_popup_layout = dialogView.findViewById(R.id.custom_date_parentLayout);
        final LinearLayout middle_layout = dialogView.findViewById(R.id.middle_layout);
        final GreekTextView selectDate_txt = dialogView.findViewById(R.id.selectDate_txt);
        final GreekTextView fron_txt = dialogView.findViewById(R.id.fron_txt);
        final GreekTextView to_txt = dialogView.findViewById(R.id.to_txt);
        final View line = dialogView.findViewById(R.id.line);

        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            data_popup_layout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            middle_layout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
            selectDate_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            fron_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            to_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edt_from.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edt_to.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            edt_from.setBackgroundTintList(getResources().getColorStateList(R.color.gray_border));
            edt_to.setBackgroundTintList(getResources().getColorStateList(R.color.gray_border));
            line.setBackgroundColor(getActivity().getResources().getColor(R.color.gray_border));


        }

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        edt_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = getInstance();
                year = calendar.get(YEAR);
                month = calendar.get(MONTH);
                day = calendar.get(DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getMainActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        edt_from.setText(dayOfMonth + "/" + (month + 1) + "/" + year);


                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        try {
                            Date date = simpleDateFormat.parse(edt_from.getText().toString());
                            startDate = df.format(date);
                            edt_from.setText(startDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
                dpd.show();
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis());

            }
        });


        edt_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = getInstance();
                year = calendar.get(YEAR);
                month = calendar.get(MONTH);
                day = calendar.get(DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getMainActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        edt_to.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        try {
                            Date date = simpleDateFormat.parse(edt_to.getText().toString());
                            endDate = df.format(date);
                            edt_to.setText(endDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }, year, month, day);

                dpd.show();
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis());

            }
        });


        Button btn_save = dialogView.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendReportRequest();
                alertDialog.cancel();
            }
        });


    }

    private void toggleErrorLayout(boolean show) {

        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);

    }

}
