package com.acumengroup.mobile.reports;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.OrderBookResponses;
import com.acumengroup.mobile.model.CombinedResponse;
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
import java.util.Locale;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

public class CombinedLedgerReport extends GreekBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private AdaptiveTableLayout adaptiveTableLayout;
    private DateFormat df;
    private GreekTextView errorMsgLayout, txt_ttlPNL,combined_titile;
    private ImageButton sort_btn;
    private String startDate = "", endDate = "";
    private int year, month, day;
    private Calendar calendar;
    private LinearLayout combined_layout,combined_header;
    private RelativeLayout combined_adaptive_layout;

    public CombinedLedgerReport() {
        // Required empty public constructor
    }


    public static CombinedLedgerReport newInstance(String param1, String param2) {
        CombinedLedgerReport fragment = new CombinedLedgerReport();
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
        View view = inflater.inflate(R.layout.fragment_combined_ledger_report, container, false);
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);
        adaptiveTableLayout = view.findViewById(R.id.tableLayout);
        df = new SimpleDateFormat("MMM dd yyyy", Locale.US);
        errorMsgLayout = view.findViewById(R.id.empty_view);
        combined_layout = view.findViewById(R.id.combined_layout);
        combined_header = view.findViewById(R.id.combined_header);
        combined_adaptive_layout = view.findViewById(R.id.combined_adaptive_layout);
        txt_ttlPNL = view.findViewById(R.id.combined_titile);
        combined_titile = view.findViewById(R.id.txt_ttlPNL);
       DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        txt_ttlPNL.setText(dateFormat.format(cal.getTime()));
        sendReportRequest();

        errorMsgLayout.setVisibility(View.VISIBLE);
        adaptiveTableLayout.setVisibility(View.GONE);
        sort_btn = view.findViewById(R.id.sort_btn);


        setupTheam(view);

        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup;
                //Inflating the Popup using xml file
                Context wrapper = new ContextThemeWrapper(getMainActivity(), R.style.popupMenuWhite);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    popup = new PopupMenu(wrapper, sort_btn);
                } else {
                    popup = new PopupMenu(getMainActivity(), sort_btn);
                }
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
        return view;
    }

    private void setupTheam(View view) {
        if (AccountDetails.getThemeFlag(view.getContext()).equals("white")) {
            combined_layout.setBackgroundColor(getResources().getColor(R.color.white));
            sort_btn.setBackground(view.getContext().getResources().getDrawable(R.drawable.ic_filter));
            combined_header.setBackgroundColor(getResources().getColor(R.color.white));
            errorMsgLayout.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            combined_titile.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            txt_ttlPNL.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            combined_adaptive_layout.setBackgroundColor(getResources().getColor(R.color.white));

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
        PandLReportRequest.sendRequest(AccountDetails.getUsername(getActivity()), "getCombinedLedger_Bajaj", startDate, endDate, getActivity(), serviceResponseHandler);
    }

    @Override
    public void handleResponse(Object response) {
        super.handleResponse(response);
        errorMsgLayout.setVisibility(View.GONE);
        adaptiveTableLayout.setVisibility(View.VISIBLE);

        try {

            Gson gson = new Gson();
            CombinedResponse staff = gson.fromJson(String.valueOf(response), CombinedResponse.class);

             ArrayList<CombinedResponse.Response.CombinedData> orderBookDataArrayList =  staff.getResponse().getData();
            orderBookDataArrayList.add(0, new CombinedResponse.Response.CombinedData("",
                    "", "", "", "", "", "",
                    "", "", ""
            ));

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
            toggleErrorLayout(true);
        }

    }

    private void addEmptyRecord() {

        ArrayList<CombinedResponse.Response.CombinedData> orderBookDataArrayList = new ArrayList<>();
        orderBookDataArrayList.add(new CombinedResponse.Response.CombinedData("",
                "", "", "", "", "", "", "", "",
                ""));

        adaptiveTableLayout.setAdapter(new ReportDataTableAdapter(getMainActivity(), orderBookDataArrayList));

    }


    public class ReportDataTableAdapter extends LinkedAdaptiveTableAdapter<ViewHolderImpl> {

        private final LayoutInflater mLayoutInflater;
        private ArrayList<CombinedResponse.Response.CombinedData> mTableDataSource;
        private final int mColumnWidth;
        private final int mRowHeight;
        private final int mHeaderHeight;
        private final int mHeaderWidth;
        private Context context;

        public ReportDataTableAdapter(Context context, ArrayList<CombinedResponse.Response.CombinedData> tableDataSource) {
            mLayoutInflater = LayoutInflater.from(context);
            mTableDataSource = new ArrayList<>();
            mTableDataSource.clear();
            mTableDataSource = tableDataSource;
            Resources res = context.getResources();
            mColumnWidth = res.getDimensionPixelSize(R.dimen.column_width_Combined_report);
            mRowHeight = res.getDimensionPixelSize(R.dimen.row_height_report_single);
            mHeaderHeight = res.getDimensionPixelSize(R.dimen.column_header_height_Combined_report);
            mHeaderWidth = res.getDimensionPixelSize(R.dimen.row_header_width_report_single);
            context=context;
        }

        public void setAppList(ArrayList<CombinedResponse.Response.CombinedData> categoryModel) {

            mTableDataSource.clear();
            mTableDataSource.addAll(categoryModel);
            notifyDataSetChanged();
        }

        public CombinedResponse.Response.CombinedData getItemScanData(int position) {
            return mTableDataSource.get(position);
        }

        @Override
        public int getRowCount() {
            return mTableDataSource.size();
        }

        @Override
        public int getColumnCount() {
            return 7;
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




                CombinedResponse.Response.CombinedData itemData = mTableDataSource.get(row); // skip headers
                //vh.dataSubtitle.setVisibility(View.GONE);

                if (column == 1) {

                    if (itemData.getVoucherType() != null && !itemData.getVoucherType().isEmpty()) {
                        vh.dataTitle.setText(itemData.getVoucherType());
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        // vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getStatus())));

                    }else {
                        vh.dataTitle.setText(" ");

                    }
                } else if (column == 2) {

                    if (itemData.getExchange() != null && !itemData.getExchange().isEmpty()) {
                        vh.dataTitle.setText(itemData.getExchange());
                        vh.dataTitle.setGravity(Gravity.LEFT);
                        // vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getStatus())));

                    }else {
                        vh.dataTitle.setText(" ");

                    }
                }
//                else if (column == 3) {
//                    if (itemData.getSettlementNo() != null && !itemData.getSettlementNo().trim().isEmpty()) {
//                        vh.dataTitle.setText(itemData.getSettlementNo());
//                    } else {
//                        vh.dataTitle.setText(" ");
//
//                    }
//                    //  vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getStatus())));
//                }
                else if (column == 3) {
                    if (itemData.getNarration() != null && !itemData.getNarration().isEmpty()) {
                        vh.dataTitle.setText(itemData.getNarration());
                        vh.dataTitle.setGravity(Gravity.LEFT);
                        // vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getStatus())));

                    }else {
                        vh.dataTitle.setText(" ");

                    }
                }
//                else if (column == 5) {
//                    if (itemData.getReferenceNo() != null && !itemData.getReferenceNo().isEmpty()) {
//                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getReferenceNo())));
//                        // vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getStatus())));
//
//                    }else {
//                        vh.dataTitle.setText(" ");
//
//                    }
//
//                }
                else if (column == 4) {
                    if (itemData.getDebit() != null && !itemData.getDebit().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDebit())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        // vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getStatus())));

                    }else {
                        vh.dataTitle.setText(" ");

                    }
                } else if (column == 5) {
                    if (itemData.getCredit() != null && !itemData.getCredit().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getCredit())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        // vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getStatus())));

                    }else {
                        vh.dataTitle.setText(" ");

                    }
                } else if (column == 6) {
                    if (itemData.getRunnBal() != null && !itemData.getRunnBal().isEmpty()) {
                        vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getRunnBal())));
                        vh.dataTitle.setGravity(Gravity.RIGHT);
                        // vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getStatus())));

                    }else {
                        vh.dataTitle.setText(" ");

                    }
                }

            }

        }

        @Override
        public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder, final int column) {
            TestHeaderColumnViewHolder vh = (TestHeaderColumnViewHolder) viewHolder;
            vh.headerColumnSubtitle.setVisibility(View.GONE);

            if (column == 1) {
                vh.headerColumnTitle.setText("Voucher Type");  // skip left top header
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                // vh.headerColumnSubtitle.setText("Buy Qty");  // skip left top header

            } else if (column == 2) {

                vh.headerColumnTitle.setText("Exchange ");
                vh.headerColumnTitle.setGravity(Gravity.LEFT);
                // vh.headerColumnSubtitle.setText("Sell Qty");
            }
//            else if (column == 3) {
//                vh.headerColumnTitle.setText("Settlement No ");
//                // vh.headerColumnSubtitle.setText("Net Qty");
//
//            }
            else if (column == 3) {
                vh.headerColumnTitle.setText("Narration ");
                vh.headerColumnTitle.setGravity(Gravity.LEFT);
                // vh.headerColumnSubtitle.setText("Today (%)");
            }
//            else if (column == 5) {
//                vh.headerColumnTitle.setText("Reference No ");
//                // vh.headerColumnSubtitle.setText("Unrealised(%)");
//            }
            else if (column == 4) {
                vh.headerColumnTitle.setText("Debit (Rs.) ");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                // vh.headerColumnSubtitle.setText("Realised (%)");
            } else if (column == 5) {
                vh.headerColumnTitle.setText("Credit (Rs.) ");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                // vh.headerColumnSubtitle.setText(" ( % )");
            } else if (column == 6) {
                vh.headerColumnTitle.setText("Running Balance (Rs.)");
                vh.headerColumnTitle.setGravity(Gravity.RIGHT);
                //vh.headerColumnSubtitle.setText("");
            }

        }

        @Override
        public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {
            TestHeaderRowViewHolder vh = (TestHeaderRowViewHolder) viewHolder;


            if (mTableDataSource.get(row).getDate() != null && !mTableDataSource.get(row).getDate().isEmpty()) {
                vh.headerRowTitle.setText(mTableDataSource.get(row).getDate());

            }else {
                vh.headerRowTitle.setText(" ");
            }
          //  vh.headerRowSubtitle.setVisibility(View.GONE);
        }

        @Override
        public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {
            TestHeaderLeftTopViewHolder vh = (TestHeaderLeftTopViewHolder) viewHolder;

            vh.leftTopTitle.setText("Date");
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
            GreekTextView  dataTitle;
            LinearLayout linear;
            View vLine,viewLine2;
            private TestViewHolder(@NonNull View itemView) {
                super(itemView);
                dataTitle = itemView.findViewById(R.id.dataTitle);

                linear = itemView.findViewById(R.id.linear);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    linear.setBackgroundColor(getResources().getColor(R.color.white));
                    vLine.setBackgroundColor(getResources().getColor(R.color.black));
                    viewLine2.setBackgroundColor(getResources().getColor(R.color.black));
                    dataTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                }
            }
        }

        private class TestHeaderColumnViewHolder extends ViewHolderImpl {
            GreekTextView headerColumnSubtitle, headerColumnTitle;
            View vLine,viewLine2;
            LinearLayout headerColumn;
            private TestHeaderColumnViewHolder(@NonNull View itemView) {
                super(itemView);
                headerColumnTitle = itemView.findViewById(R.id.dataTitle);
                headerColumnSubtitle = itemView.findViewById(R.id.dataSubtitle);
                headerColumn = itemView.findViewById(R.id.layout1);
                vLine = itemView.findViewById(R.id.vLine);
                viewLine2 = itemView.findViewById(R.id.viewLine2);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    headerColumn.setBackgroundColor(getResources().getColor(R.color.bajaj_blue));
                    vLine.setBackgroundColor(getResources().getColor(R.color.black));
                    viewLine2.setBackgroundColor(getResources().getColor(R.color.black));
//                    headerColumnTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//                    headerColumnSubtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
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
            GreekTextView headerRowTitle;
            LinearLayout headerRow;
            View  viewLine1;
            TestHeaderRowViewHolder(@NonNull View itemView) {
                super(itemView);
                headerRowTitle = itemView.findViewById(R.id.title);

                headerRow = itemView.findViewById(R.id.linear);
                viewLine1 = itemView.findViewById(R.id.viewLine2);
                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    headerRow.setBackgroundColor(getResources().getColor(R.color.white));
                    headerRowTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

                }
            }
        }

        private class TestHeaderLeftTopViewHolder extends ViewHolderImpl {
            GreekTextView leftTopSubtitle, leftTopTitle;
            LinearLayout layoutHeaderLeft;
            View  viewLine1;
            private TestHeaderLeftTopViewHolder(@NonNull View itemView) {
                super(itemView);
                leftTopTitle = itemView.findViewById(R.id.title);
                leftTopSubtitle = itemView.findViewById(R.id.subTitle);
                layoutHeaderLeft = itemView.findViewById(R.id.layout);
                viewLine1 = itemView.findViewById(R.id.viewLine2);


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
