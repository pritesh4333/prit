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
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.OrderBookResponses;
import com.acumengroup.mobile.model.LasHoldingResponse;
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


public class LoanAgainstReport extends GreekBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private AdaptiveTableLayout adaptiveTableLayout;
    private DateFormat df;
    private GreekTextView errorMsgLayout, txt_ttlPNL,las_tv;
    private LinearLayout las_bg,las_top;
    private ImageButton sort_btn;
    private String startDate = "", endDate = "";
    private int year, month, day;
    private Calendar calendar;
    private View LasParentView;
    private ArrayList<LasHoldingResponse.Response.LASData> orderBookDataArrayList;

    public LoanAgainstReport() {
        // Required empty public constructor
    }


    public static LoanAgainstReport newInstance(String param1, String param2) {
        LoanAgainstReport fragment = new LoanAgainstReport();
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
        LasParentView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_loan_against_report).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_loan_against_report).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        //View view = inflater.inflate(R.layout.fragment_loan_against_report, container, false);

        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);

        adaptiveTableLayout = (AdaptiveTableLayout) LasParentView.findViewById(R.id.tableLayoutview);
        df = new SimpleDateFormat("MMM dd yyyy", Locale.US);
        errorMsgLayout = LasParentView.findViewById(R.id.empty_view);
        txt_ttlPNL = LasParentView.findViewById(R.id.txt_ttlPNL);
        las_bg = LasParentView.findViewById(R.id.las_bg);
        las_top = LasParentView.findViewById(R.id.las_top);

        las_tv = LasParentView.findViewById(R.id.las_tv);
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        txt_ttlPNL.setText(dateFormat.format(cal.getTime()));

        sendReportRequest();
        errorMsgLayout.setVisibility(View.VISIBLE);
        adaptiveTableLayout.setVisibility(View.GONE);

        sort_btn = LasParentView.findViewById(R.id.sort_btn);
        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup ;
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
        setTheme();
        return LasParentView;
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("White")) {
            las_tv.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            errorMsgLayout.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            las_bg.setBackgroundColor(getResources().getColor(R.color.white));
            las_top.setBackgroundColor(getResources().getColor(R.color.white));
            sort_btn.setBackground(getResources().getDrawable(R.drawable.ic_filter));



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
        //To get Las Holding details Get request is send to aracane server
        PandLReportRequest.sendRequest(AccountDetails.getUsername(getActivity()),
                "getLasHolding_Bajaj", startDate, endDate, getActivity(), serviceResponseHandler);
    }

    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);


        try {

            Gson gson = new Gson();
            LasHoldingResponse staff = gson.fromJson(String.valueOf(response), LasHoldingResponse.class);

            ArrayList<LasHoldingResponse.Response.LASData> orderBookDataArrayList = staff.getResponse().getData();
            orderBookDataArrayList.add(0, new LasHoldingResponse.Response.LASData(
                    "", "", "", "", "", "",
                    "", "", ""
            ));

            errorMsgLayout.setVisibility(View.GONE);
            adaptiveTableLayout.setVisibility(View.VISIBLE);
            ReportDataTableAdapter reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), orderBookDataArrayList);
            adaptiveTableLayout.setAdapter(reportDataTableAdapter);
            reportDataTableAdapter.notifyDataSetChanged();

            hideProgress();
            toggleErrorLayout(false);


        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
            adaptiveTableLayout.setVisibility(View.GONE);
            // addEmptyRecord();
            toggleErrorLayout(true);

        }

    }

    private void addEmptyRecord() {

        orderBookDataArrayList.add(0, new LasHoldingResponse.Response.LASData("",
                "", "", "", "", "", "", "", ""));

        adaptiveTableLayout.setAdapter(new ReportDataTableAdapter(getMainActivity(), orderBookDataArrayList));


    }


    public class ReportDataTableAdapter extends LinkedAdaptiveTableAdapter<ViewHolderImpl> {

        private final LayoutInflater mLayoutInflater;
        private ArrayList<LasHoldingResponse.Response.LASData> mTableDataSource;
        private final int mColumnWidth;
        private final int mRowHeight;
        private final int mHeaderHeight;
        private final int mHeaderWidth;
        private Context context;

        public ReportDataTableAdapter(Context context, ArrayList<LasHoldingResponse.Response.LASData> tableDataSource) {
            mLayoutInflater = LayoutInflater.from(context);
            mTableDataSource = new ArrayList<>();
            mTableDataSource.clear();
            mTableDataSource = tableDataSource;
            Resources res = context.getResources();
            mColumnWidth = res.getDimensionPixelSize(R.dimen.column_width_l);
            mRowHeight = res.getDimensionPixelSize(R.dimen.row_height_l);
            mHeaderHeight = res.getDimensionPixelSize(R.dimen.column_header_height_l);
            mHeaderWidth = res.getDimensionPixelSize(R.dimen.row_header_width_l);
        }

        public void setAppList(ArrayList<LasHoldingResponse.Response.LASData> categoryModel) {

            mTableDataSource.clear();
            mTableDataSource.addAll(categoryModel);
            notifyDataSetChanged();
        }

        public LasHoldingResponse.Response.LASData getItemScanData(int position) {
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
            return new TestViewHolder(mLayoutInflater.inflate(R.layout.item_card_single, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent) {
            return new TestHeaderColumnViewHolder(mLayoutInflater.inflate(R.layout.item_header_column, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateRowHeaderViewHolder(@NonNull ViewGroup parent) {
            return new TestHeaderRowViewHolder(mLayoutInflater.inflate(R.layout.report_header_row_single, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateLeftTopHeaderViewHolder(@NonNull ViewGroup parent) {
            return new TestHeaderLeftTopViewHolder(mLayoutInflater.inflate(R.layout.item_header_left_top, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderImpl viewHolder, int row, int column) {
            final TestViewHolder vh = (TestViewHolder) viewHolder;

            if (mTableDataSource.size() > 1) {

                LasHoldingResponse.Response.LASData itemData = mTableDataSource.get(row); // skip headers
                // vh.dataSubtitle.setVisibility(View.GONE);

                if (column == 1) {
                    if (itemData.getISIN() != null && !itemData.getISIN().isEmpty()) {
                        vh.dataTitle.setText(itemData.getISIN());
                        vh.dataTitle.setGravity(Gravity.LEFT);
                    } else {
                        vh.dataTitle.setText(" ");
                    }

                } else if (column == 2) {
                    if (itemData.getPledgeQty() != null && !itemData.getPledgeQty().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getPledgeQty())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText(" ");
                    }

                } else if (column == 3) {
                    if (itemData.getFreeQty() != null && !itemData.getFreeQty().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getFreeQty())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText(" ");
                    }
                } else if (column == 4) {
                    if (itemData.getTotalQty() != null && !itemData.getTotalQty().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getTotalQty())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText(" ");
                    }
                } else if (column == 5) {
                    if (itemData.getTodaysSellingQty() != null && !itemData.getTodaysSellingQty().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getTodaysSellingQty())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText(" ");
                    }
                } else if (column == 6) {
                    if (itemData.getAvailableforPledging() != null && !itemData.getAvailableforPledging().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getAvailableforPledging())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText(" ");
                    }
                } else if (column == 7) {
                    if (itemData.getUnpledge() != null && !itemData.getUnpledge().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getUnpledge())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                    } else {
                        vh.dataTitle.setText(" ");
                    }
                }
//                else if (column == 8) {
//                    if (itemData.getAction() != null && !itemData.getAction().isEmpty()) {
//
//                        if (itemData.getAction().equalsIgnoreCase("1")) {
//                            vh.dataTitle.setText("BUY");
//
//                        } else if (itemData.getAction().equalsIgnoreCase("2")) {
//                            vh.dataTitle.setText("SELL");
//
//                        } else {
//                            vh.dataTitle.setText("-");
//
//                        }
//
//                    } else {
//                        vh.dataTitle.setText(" ");
//                    }
                //}
            }

        }

        @Override
        public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder, final int column) {
            TestHeaderColumnViewHolder vh = (TestHeaderColumnViewHolder) viewHolder;
            vh.headerColumnSubtitle.setVisibility(View.VISIBLE);

            if (column == 1) {

                vh.headerColumnTitle.setText("ISIN");
                vh.headerColumnTitle.setGravity(Gravity.LEFT);
                vh.headerColumnSubtitle.setText("");

            } else if (column == 2) {

                vh.headerColumnTitle.setText("Pledge Qty");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("");

            } else if (column == 3) {

                vh.headerColumnTitle.setText("Free Qty");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("");

            } else if (column == 4) {
                vh.headerColumnTitle.setText("Total Qty");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("");

            } else if (column == 5) {
                vh.headerColumnTitle.setText("Today's Selling Qty");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("");

            } else if (column == 6) {
                vh.headerColumnTitle.setText("Available for Pledging");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("");

            } else if (column == 7) {
                vh.headerColumnTitle.setText("Unpledge");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                vh.headerColumnSubtitle.setText("");
            }
//            } else if (column == 8) {
//                vh.headerColumnTitle.setText("Action");
//                vh.headerColumnSubtitle.setText("");
//            }

        }

        @Override
        public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {
            TestHeaderRowViewHolder vh = (TestHeaderRowViewHolder) viewHolder;
            if (mTableDataSource.get(row).getScriptName() != null && !mTableDataSource.get(row).getScriptName().isEmpty()) {
                vh.headerRowTitle.setText(mTableDataSource.get(row).getScriptName());
            } else {
                vh.headerRowTitle.setText(" ");
            }
            //  vh.headerRowSubtitle.setVisibility(View.GONE);
        }

        @Override
        public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {
            TestHeaderLeftTopViewHolder vh = (TestHeaderLeftTopViewHolder) viewHolder;
            vh.leftTopTitle.setText("Script Name");
            vh.leftTopSubtitle.setText("");
            vh.leftTopSubtitle.setVisibility(View.GONE);
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
            LinearLayout linear;
            View vLine,viewLine2;

            private TestViewHolder(@NonNull View itemView) {
                super(itemView);
                dataTitle = itemView.findViewById(R.id.dataTitle);
                dataSubtitle = itemView.findViewById(R.id.dataSubtitle);
                linear = itemView.findViewById(R.id.linear);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    linear.setBackgroundColor(getResources().getColor(R.color.white));
                    dataTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    dataSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    vLine.setBackgroundColor(getResources().getColor(R.color.black));
                    viewLine2.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }
        }

        private class TestHeaderColumnViewHolder extends ViewHolderImpl {
            GreekTextView headerColumnSubtitle, headerColumnTitle;
            View vLine,viewLine2;
            LinearLayout item_headerParent;

            private TestHeaderColumnViewHolder(@NonNull View itemView) {
                super(itemView);
                headerColumnTitle = itemView.findViewById(R.id.dataTitle);
                headerColumnSubtitle = itemView.findViewById(R.id.dataSubtitle);
                item_headerParent = itemView.findViewById(R.id.layout1);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    item_headerParent.setBackgroundColor(getResources().getColor(R.color.bajaj_blue));
                    vLine.setBackgroundColor(getResources().getColor(R.color.black));
                    viewLine2.setBackgroundColor(getResources().getColor(R.color.black));
                    headerColumnSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    headerColumnTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                }

                headerColumnTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.e("Strategy", "OnClick=====Title======>>");
                    }
                });

                headerColumnSubtitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.e("Strategy", "OnClick=====SubTitle======>>");

                    }
                });


            }
        }

        private class TestHeaderRowViewHolder extends ViewHolderImpl {
            GreekTextView headerRowTitle, headerRowSubtitle;
            LinearLayout layout_header_row;
            View vLine,viewLine2;


            TestHeaderRowViewHolder(@NonNull View itemView) {
                super(itemView);
                headerRowTitle = itemView.findViewById(R.id.title);
                headerRowSubtitle = itemView.findViewById(R.id.subTitle);
                layout_header_row = itemView.findViewById(R.id.linear);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    layout_header_row.setBackgroundColor(getResources().getColor(R.color.white));
                    headerRowTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    headerRowSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    vLine.setBackgroundColor(getResources().getColor(R.color.black));
                    viewLine2.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }
        }

        private class TestHeaderLeftTopViewHolder extends ViewHolderImpl {
            GreekTextView leftTopSubtitle, leftTopTitle;
            LinearLayout header_left_parent;
            View vLine,viewLine2;

            private TestHeaderLeftTopViewHolder(@NonNull View itemView) {
                super(itemView);
                leftTopTitle = itemView.findViewById(R.id.title);
                leftTopSubtitle = itemView.findViewById(R.id.subTitle);
                header_left_parent = itemView.findViewById(R.id.layout);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    header_left_parent.setBackgroundColor(getResources().getColor(R.color.bajaj_blue));
                    leftTopSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    leftTopTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                    vLine.setBackgroundColor(getResources().getColor(R.color.black));
                    viewLine2.setBackgroundColor(getResources().getColor(R.color.black));
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
