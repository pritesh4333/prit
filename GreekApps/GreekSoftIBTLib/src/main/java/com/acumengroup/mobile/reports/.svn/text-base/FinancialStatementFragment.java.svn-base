package com.acumengroup.mobile.reports;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.columnsliderlib.AdaptiveTableLayout;
import com.acumengroup.columnsliderlib.LinkedAdaptiveTableAdapter;
import com.acumengroup.columnsliderlib.ViewHolderImpl;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.FinancialStmModel.FinancialStmRequest;
import com.acumengroup.mobile.model.FinancialStmModel.FinancialStmResponse;
import com.acumengroup.mobile.model.PortfolioReportModel.PortfolioReportRequest;
import com.acumengroup.mobile.model.PortfolioReportModel.PortfolioReportResponse;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FinancialStatementFragment extends GreekBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AdaptiveTableLayout adaptiveTableLayout;
    private DateFormat df;
    private GreekTextView errorMsgLayout, txt_ttlPNL,empty_view, txt_cost_report, total_view, total_txt, txt_party_cade_value, txt_party_cade;
    private String startDate = "", endDate = "";
    private int year, month, day;
    private Calendar calendar;
    private Spinner spinner_segments, spinner_exchange;
    private String servicetype, exchangeType, segmentType, serviceName;
    View view;
    private LinearLayout cost_report_parentLayout, linear2, linear_top_layout, total_linear;
    private RelativeLayout table_relativeLayout;
    private boolean isFirstTime = true;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FinancialStatementFragment() {
        // Required empty public constructor
    }

    public static FinancialStatementFragment newInstance(String param1, String param2) {
        FinancialStatementFragment fragment = new FinancialStatementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_financial_statement, container, false);
        view = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_financial_statement).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_financial_statement).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);

        adaptiveTableLayout = view.findViewById(R.id.cost_report_tableLayout);
        df = new SimpleDateFormat("MMM dd yyyy", Locale.US);

        spinner_exchange = view.findViewById(R.id.spin1);
        spinner_segments = view.findViewById(R.id.spin);

        errorMsgLayout = view.findViewById(R.id.empty_view);
        txt_ttlPNL = view.findViewById(R.id.cost_report_txt_ttlPNL);
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        txt_ttlPNL.setText(dateFormat.format(cal.getTime()));
        errorMsgLayout.setVisibility(View.VISIBLE);
        adaptiveTableLayout.setVisibility(View.GONE);
//        sort_btn = view.findViewById(R.id.sort_btn);

        empty_view = view.findViewById(R.id.empty_view);
        txt_cost_report = view.findViewById(R.id.txt_cost_report);
        cost_report_parentLayout = view.findViewById(R.id.cost_report_parentLayout);
        linear2 = view.findViewById(R.id.linear2);
        linear_top_layout = view.findViewById(R.id.linear_top_layout);
        total_linear = view.findViewById(R.id.total_linear);
        table_relativeLayout = view.findViewById(R.id.table_relativeLayout);
        total_txt = view.findViewById(R.id.total_txt);
        total_view = view.findViewById(R.id.total_view);
        txt_party_cade_value = view.findViewById(R.id.txt_party_cade_value);
        txt_party_cade = view.findViewById(R.id.txt_party_cade);

        AccountDetails.currentFragment = NAV_TO_FINANCIAL_STM_REPORT;

        setTheme();
        sendReportRequest();

//        sort_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                PopupMenu popup = new PopupMenu(getContext(), sort_btn);
//                PopupMenu popup;
//
//                Context wrapper = new ContextThemeWrapper(getMainActivity(), R.style.popupMenuWhite);
//
//                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//                    popup = new PopupMenu(wrapper, sort_btn);
//                } else {
//                    popup = new PopupMenu(getMainActivity(), sort_btn);
//                }
//
//                //Inflating the Popup using xml file
//                popup.getMenuInflater().inflate(R.menu.sort_report_menu, popup.getMenu());
//
//                //registering popup with OnMenuItemClickListener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//
//                        DateFormat df = new SimpleDateFormat("MMM dd yyyy", Locale.US);
//
//                        if (item.getItemId() == R.id.one) {
//
//                            Calendar calendar = Calendar.getInstance();
//                            calendar.add(Calendar.MONTH, 0);
//                            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
//                            Date monthFirstDay = calendar.getTime();
//                            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//                            Date monthLastDay = calendar.getTime();
//
//                            startDate = df.format(monthFirstDay);
//                            Date c = Calendar.getInstance().getTime();
//                            endDate = df.format(c);
//                            sendReportRequest();
//
//
//                        } else if (item.getItemId() == R.id.two) {
//
//                            Calendar aCalendar = Calendar.getInstance();
//                            aCalendar.add(Calendar.MONTH, -1);
//                            aCalendar.set(Calendar.DATE, 1);
//
//                            Date firstDateOfPreviousMonth = aCalendar.getTime();
//                            startDate = df.format(firstDateOfPreviousMonth);
//
//                            aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//                            Date lastDateOfPreviousMonth = aCalendar.getTime();
//                            endDate = df.format(lastDateOfPreviousMonth);
//                            sendReportRequest();
//
//
//                        } else if (item.getItemId() == R.id.three) {
//
//                            Calendar calendar = Calendar.getInstance();
//                            int year = calendar.get(Calendar.YEAR);
//
//                            Calendar cal = Calendar.getInstance();
//                            cal.set(Calendar.YEAR, year - 2);
//                            cal.set(Calendar.MONTH, 3);
//                            cal.set(Calendar.DAY_OF_MONTH, 1);
//                            Date start = cal.getTime();
//                            startDate = df.format(start);
//
//                            cal.set(Calendar.YEAR, year - 1);
//                            cal.set(Calendar.MONTH, 2); // 11 = december
//                            cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
//
//                            Date end = cal.getTime();
//
//                            endDate = df.format(end);
//                            sendReportRequest();
//
//
//                        } else if (item.getItemId() == R.id.five) {
//
//                            Calendar calendar = Calendar.getInstance();
//                            int year = calendar.get(Calendar.YEAR);
//
//                            Calendar cal = Calendar.getInstance();
//                            cal.set(Calendar.YEAR, year);
//                            cal.set(Calendar.MONTH, 3);
//                            cal.set(Calendar.DAY_OF_MONTH, 1);
//                            Date start = cal.getTime();
//                            startDate = df.format(start);
//
//                            cal.set(Calendar.YEAR, year + 1);
//                            cal.set(Calendar.MONTH, 2); // 11 = december
//                            cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
//
//
//                            Date end = cal.getTime();
//
//                            endDate = df.format(end);
//                            sendReportRequest();
//                        } else if (item.getItemId() == R.id.four) {
//                            showCustomDialog();
//
//
//                        }
//
//
//                        return true;
//                    }
//                });
////
//                popup.show();
//
//
//            }
//        });

        return view;

    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            txt_cost_report.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            empty_view.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_party_cade.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_party_cade_value.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            total_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            total_view.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            sort_btn.setBackground(getResources().getDrawable(R.drawable.ic_filter));
            adaptiveTableLayout.setBackground(getResources().getDrawable(R.color.black));
            //table_relativeLayout.setBackground(getResources().getDrawable(R.color.white));
            cost_report_parentLayout.setBackground(getResources().getDrawable(R.color.white));
            linear2.setBackground(getResources().getDrawable(R.color.white));
            linear_top_layout.setBackground(getResources().getDrawable(R.color.white));
            total_linear.setBackground(getResources().getDrawable(R.color.white));
//            spinner_exchange.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
//            spinner_segments.setBackground(getResources().getDrawable(R.drawable.gradient_spinner_black));
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
        FinancialStmRequest.sendRequest(AccountDetails.getUsername(getActivity()), "getClientFinancial_Phillip", startDate, endDate, getActivity(), serviceResponseHandler);
    }

    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);
        errorMsgLayout.setVisibility(View.GONE);
        adaptiveTableLayout.setVisibility(View.VISIBLE);

        try {

            Gson gson = new Gson();
            FinancialStmResponse staff = gson.fromJson(String.valueOf(response), FinancialStmResponse.class);

            ArrayList<FinancialStmResponse.Response.Data.FinancialData.ClientFinSumSummaryViews> orderBookDataArrayList = (ArrayList<FinancialStmResponse.Response.Data.FinancialData.ClientFinSumSummaryViews>) staff.getResponse().getData().getFinancialData().getClientFinSumSummaryView();
            orderBookDataArrayList.add(0, new FinancialStmResponse.Response.Data.FinancialData.ClientFinSumSummaryViews("",
                    "", "", "",
                    "", "", "",
                    "", "", "",
                    "", "", "", "", "", "", "", "",
                    "", "","","","","",""));
            adaptiveTableLayout.setVisibility(View.VISIBLE);

            FinancialStatementFragment.ReportDataTableAdapter reportDataTableAdapter = new FinancialStatementFragment.ReportDataTableAdapter(getMainActivity(), orderBookDataArrayList);
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

        ArrayList<FinancialStmResponse.Response.Data.FinancialData.ClientFinSumSummaryViews> orderBookDataArrayList = new ArrayList<>();
//        orderBookDataArrayList.add(new FinancialStmResponse.Response.Data.FinancialData.ClientFinSumSummaryViews(
//                "", "", "",
//                "", "", "", "", "", "",
//                "", "", "", "",
//                "", "", "", "", "", "",
//                "", "", "", ""));

        adaptiveTableLayout.setAdapter(new FinancialStatementFragment.ReportDataTableAdapter(getMainActivity(), orderBookDataArrayList));

    }


    public class ReportDataTableAdapter extends LinkedAdaptiveTableAdapter<ViewHolderImpl> {

        private final LayoutInflater mLayoutInflater;
        private ArrayList<FinancialStmResponse.Response.Data.FinancialData.ClientFinSumSummaryViews> mTableDataSource;
        private final int mColumnWidth;
        private final int mRowHeight;
        private final int mHeaderHeight;
        private final int mHeaderWidth;
        private Context ctx;

        public ReportDataTableAdapter(Context context, ArrayList<FinancialStmResponse.Response.Data.FinancialData.ClientFinSumSummaryViews> tableDataSource) {
            mLayoutInflater = LayoutInflater.from(context);
            mTableDataSource = new ArrayList<>();
            mTableDataSource.clear();
            mTableDataSource = tableDataSource;
            Resources res = context.getResources();
            mColumnWidth = res.getDimensionPixelSize(R.dimen.row_header_width_report);
            mRowHeight = res.getDimensionPixelSize(R.dimen.row_height_report);
            mHeaderHeight = res.getDimensionPixelSize(R.dimen.column_header_height_report);
            mHeaderWidth = res.getDimensionPixelSize(R.dimen.row_header_width_report);
            ctx=context;
        }

        public void setAppList(ArrayList<FinancialStmResponse.Response.Data.FinancialData.ClientFinSumSummaryViews> categoryModel) {

            mTableDataSource.clear();
            mTableDataSource.addAll(categoryModel);
            notifyDataSetChanged();
        }

        public FinancialStmResponse.Response.Data.FinancialData.ClientFinSumSummaryViews getItemScanData(int position) {
            return mTableDataSource.get(position);
        }

        @Override
        public int getRowCount() {
            return mTableDataSource.size();
        }

        @Override
        public int getColumnCount() {
            return 8;
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateItemViewHolder(@NonNull ViewGroup parent) {
            return new FinancialStatementFragment.ReportDataTableAdapter.TestViewHolder(mLayoutInflater.inflate(R.layout.second_report_item_card, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent) {
            return new FinancialStatementFragment.ReportDataTableAdapter.TestHeaderColumnViewHolder(mLayoutInflater.inflate(R.layout.item_header_column, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateRowHeaderViewHolder(@NonNull ViewGroup parent) {
            return new FinancialStatementFragment.ReportDataTableAdapter.TestHeaderRowViewHolder(mLayoutInflater.inflate(R.layout.second_report_header_row, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateLeftTopHeaderViewHolder(@NonNull ViewGroup parent) {
            return new FinancialStatementFragment.ReportDataTableAdapter.TestHeaderLeftTopViewHolder(mLayoutInflater.inflate(R.layout.item_header_left_top, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderImpl viewHolder, int row, int column) {
            final FinancialStatementFragment.ReportDataTableAdapter.TestViewHolder vh = (FinancialStatementFragment.ReportDataTableAdapter.TestViewHolder) viewHolder;


            FinancialStmResponse.Response.Data.FinancialData.ClientFinSumSummaryViews itemData = mTableDataSource.get(row); // skip headers
//            vh.dataSubtitle.setVisibility(View.GONE);
            txt_party_cade_value.setText(AccountDetails.getUsername(getMainActivity()));


            if (column == 1) {
                if (itemData.getCash() != null && !itemData.getCash().isEmpty()) {
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getCash())));
                    vh.dataTitle.setGravity(Gravity.RIGHT);
                    vh.dataSubtitle.setText(" ");

                } else {
                    vh.dataTitle.setText(" ");
                    vh.dataSubtitle.setText(" ");

                }
            } else if (column == 2) {
                if (itemData.getMTFCASH() != null && !itemData.getMTFCASH().isEmpty()) {
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getMTFCASH())).trim());
                    vh.dataTitle.setGravity(Gravity.RIGHT);
                    vh.dataSubtitle.setText(" ");
                } else {
                    vh.dataTitle.setText(" ");
                    vh.dataSubtitle.setText(" ");

                }
            } else if (column == 3) {
                if (itemData.getBSEMFSS() != null && !itemData.getBSEMFSS().isEmpty()) {
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getBSEMFSS())).trim());
                    vh.dataTitle.setGravity(Gravity.RIGHT);
                    vh.dataSubtitle.setText(" ");

                } else {
                    vh.dataTitle.setText(" ");
                    vh.dataSubtitle.setText(" ");

                }
            } else if (column == 4) {
                if (itemData.getDerivatives() != null && !itemData.getDerivatives().isEmpty()) {
                    vh.dataTitle.setGravity(Gravity.RIGHT);
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDerivatives())).trim());
                    vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getCurrencyderivatives())).trim());
                    vh.dataSubtitle.setGravity(Gravity.RIGHT);

                } else {
                    vh.dataTitle.setText(" ");
                    vh.dataSubtitle.setText(" ");

                }
            }else if (column == 5) {
                if (itemData.getNseslbs() != null && !itemData.getNseslbs().isEmpty()) {
                    vh.dataTitle.setGravity(Gravity.RIGHT);
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getNseslbs())).trim());
                    vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getBseslbs())).trim());
                    vh.dataSubtitle.setGravity(Gravity.RIGHT);

                } else {
                    vh.dataTitle.setText(" ");
                    vh.dataSubtitle.setText(" ");

                }
            }else if (column == 6) {
                if (itemData.getMCDX() != null && !itemData.getMCDX().isEmpty()) {
                    vh.dataTitle.setGravity(Gravity.RIGHT);
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getMCDX())).trim());
                    vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getNCDX())).trim());
                    vh.dataSubtitle.setGravity(Gravity.RIGHT);

                } else {
                    vh.dataTitle.setText(" ");
                    vh.dataSubtitle.setText(" ");

                }
            }else if (column == 7) {
                if (itemData.getTOTAL_COMBINED() != null && !itemData.getTOTAL_COMBINED().isEmpty()) {
                    vh.dataTitle.setGravity(Gravity.RIGHT);
                    vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getTOTAL_COMBINED())).trim());
                    vh.dataSubtitle.setText(" ");

                } else {
                    vh.dataTitle.setText(" ");
                    vh.dataSubtitle.setText(" ");

                }
            }
        }

        @Override
        public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder, final int column) {
            FinancialStatementFragment.ReportDataTableAdapter.TestHeaderColumnViewHolder vh = (FinancialStatementFragment.ReportDataTableAdapter.TestHeaderColumnViewHolder) viewHolder;

//            vh.headerColumnSubtitle.setVisibility(View.GONE);

            if (column == 1) {
                vh.headerColumnTitle.setText("Cash");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("");
            } else if (column == 2) {
                vh.headerColumnTitle.setText("MTF");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("");
            } else if (column == 3) {
                vh.headerColumnTitle.setText("BS EMFSS");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("");
            } else if (column == 4) {
                vh.headerColumnTitle.setText("Derivative");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("Currency Derivatives");
                vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
            }else if (column == 5) {
                vh.headerColumnTitle.setText("NSE SLBS");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("BSE SLBS");
                vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
            }else if (column == 6) {
                vh.headerColumnTitle.setText("MCDX");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("NCDX");
                vh.headerColumnSubtitle.setGravity(Gravity.RIGHT);
            }else if (column == 7) {
                vh.headerColumnTitle.setText("Comm Combined");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("");
            }

        }

        @Override
        public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {

            FinancialStatementFragment.ReportDataTableAdapter.TestHeaderRowViewHolder vh = (FinancialStatementFragment.ReportDataTableAdapter.TestHeaderRowViewHolder) viewHolder;
            vh.headerRowSubtitle.setVisibility(View.GONE);

            if (mTableDataSource.get(row).getDescrip() != null && !mTableDataSource.get(row).getDescrip().isEmpty()) {
                vh.headerRowTitle.setText(mTableDataSource.get(row).getDescrip());

                if (AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("white")) {
                    vh.headerRowTitle.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                    vh.headerRowTitle.setBackground(ctx.getResources().getDrawable(R.color.white));

                    vh.headerRowTitle.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                    vh.headerRowTitle.setBackground(ctx.getResources().getDrawable(R.color.white));
                }
            } else {
                vh.headerRowTitle.setText(" ");
            }
        }

        @Override
        public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {
            FinancialStatementFragment.ReportDataTableAdapter.TestHeaderLeftTopViewHolder vh = (FinancialStatementFragment.ReportDataTableAdapter.TestHeaderLeftTopViewHolder) viewHolder;
            vh.leftTopSubtitle.setVisibility(View.GONE);
            vh.leftTopTitle.setText("Description");
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

                if (AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("white")) {
                    layout_itemcard.setBackgroundColor(ctx.getResources().getColor(R.color.white));
                    vLine.setBackgroundColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                    viewLine2.setBackgroundColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                    dataTitle.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                    dataSubtitle.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                }else {
//                    vLine.setBackgroundColor(getResources().getColor(R.color.white));
//                    viewLine2.setBackgroundColor(getResources().getColor(R.color.white));
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
                headerColumn = itemView.findViewById(R.id.layout);
                headerColumn.setBackgroundColor(ctx.getResources().getColor(R.color.bajaj_blue));

                //  headerColumnSubtitle.setVisibility(View.GONE);
                if (AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("white")) {
                    vLine.setBackgroundColor(ctx.getResources().getColor(R.color.black));
                    viewLine2.setBackgroundColor(ctx.getResources().getColor(R.color.black));
//                    headerColumnTitle.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
//                    headerColumnSubtitle.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                }else {
                    vLine.setBackgroundColor(ctx.getResources().getColor(R.color.white));
                    viewLine2.setBackgroundColor(ctx.getResources().getColor(R.color.white));
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

                if (AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("white")) {
                    headerRow.setBackgroundColor(ctx.getResources().getColor(R.color.white));
                    headerRowTitle.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
                    headerRowSubtitle.setTextColor(ctx.getResources().getColor(AccountDetails.textColorDropdown));
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
                layoutHeaderLeft.setBackgroundColor(ctx.getResources().getColor(R.color.bajaj_blue));

//                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//                    layoutHeaderLeft.setBackgroundColor(getResources().getColor(R.color.bajaj_blue));
//                    leftTopTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//                    leftTopSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//                }
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
            selectDate_txt.setTextColor(getActivity().getResources().getColor(AccountDetails.textColorDropdown));
            fron_txt.setTextColor(getActivity().getResources().getColor(AccountDetails.textColorDropdown));
            to_txt.setTextColor(getActivity().getResources().getColor(AccountDetails.textColorDropdown));
            edt_from.setTextColor(getActivity().getResources().getColor(AccountDetails.textColorDropdown));
            edt_to.setTextColor(getActivity().getResources().getColor(AccountDetails.textColorDropdown));
//            edt_from.setBackgroundTintList(getResources().getColorStateList(R.color.gray_border));
//            edt_to.setBackgroundTintList(getResources().getColorStateList(R.color.gray_border));
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

                if(edt_to.getText().toString().length()>0&&edt_from.getText().toString().length()>0) {
                    sendReportRequest();
                    alertDialog.cancel();
                }else{
                    GreekDialog.alertDialog(getActivity(), 0, GREEK, "Please select from date and to date", "OK", true, null);
                }

            }
        });


    }

    private void toggleErrorLayout(boolean show) {

        errorMsgLayout.setVisibility(show ? View.VISIBLE : View.GONE);

    }

}