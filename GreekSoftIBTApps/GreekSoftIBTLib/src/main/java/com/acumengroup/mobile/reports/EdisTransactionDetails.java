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
import androidx.appcompat.widget.Toolbar;
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
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.EdisTransactionDetailsResponse;
import com.acumengroup.mobile.model.PandLReportRequest;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.columnsliderlib.AdaptiveTableLayout;
import com.acumengroup.columnsliderlib.LinkedAdaptiveTableAdapter;
import com.acumengroup.columnsliderlib.ViewHolderImpl;
import com.google.gson.Gson;

import org.json.JSONObject;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.acumengroup.greekmain.util.date.DateTimeFormatter.getDateFromTimeStamp;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;


public class EdisTransactionDetails extends GreekBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private AdaptiveTableLayout adaptiveTableLayout;
    private DateFormat df;
    private DateFormat sdf;
    private Spinner spinner1;
    private GreekTextView errorMsgLayout, txt_ttlPNL;
    private GreekTextView edis;
    private ImageButton sort_btn;
    private String startDate = "", endDate = "";
    private int year, month, day;
    private Calendar calendar;
    List<String> spinner1List;
    private View view;

    public EdisTransactionDetails() {
        // Required empty public constructor
    }

    public static EdisTransactionDetails newInstance(String param1, String param2) {
        EdisTransactionDetails fragment = new EdisTransactionDetails();
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
        spinner1List = new ArrayList<>();
        spinner1List.add("E-DIS Transaction Details");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_edis_transaction_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_edis_transaction_details).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);

        AccountDetails.currentFragment = NAV_TO_EDIS_TRANSACTION_DETAILS;

        adaptiveTableLayout = view.findViewById(R.id.cost_report_tableLayout);
        df = new SimpleDateFormat("MMM dd yyyy 23:59:59", Locale.US);
        sdf = new SimpleDateFormat("MMM dd yyyy 00:00:00", Locale.US);
        spinner1 = view.findViewById(R.id.cost_report_spinner);
        errorMsgLayout = view.findViewById(R.id.empty_view);
        txt_ttlPNL = view.findViewById(R.id.cost_report_txt_ttlPNL);
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        txt_ttlPNL.setText(dateFormat.format(cal.getTime()));

        edis = view.findViewById(R.id.edis);
        sort_btn = view.findViewById(R.id.sort_btn);

//       getEDISTransactionDashboard request is send to Apollo server.
        sendReportRequest();
        setTheme();

        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup;
                Context wrapper = new ContextThemeWrapper(getMainActivity(), R.style.popupMenuWhite);

                if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                    popup = new PopupMenu(wrapper, sort_btn);
                } else {
                    popup = new PopupMenu(getMainActivity(), sort_btn);
                }

                popup.getMenuInflater().inflate(R.menu.sort_report_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        DateFormat df = new SimpleDateFormat("MMM dd yyyy", Locale.US);

//                        data sorting according to current month calculated
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


                        }
//                        data sorting according to last month calculated.
                        else if (item.getItemId() == R.id.two) {

                            Calendar aCalendar = Calendar.getInstance();
                            aCalendar.add(Calendar.MONTH, -1);
                            aCalendar.set(Calendar.DATE, 1);

                            Date firstDateOfPreviousMonth = aCalendar.getTime();
                            startDate = df.format(firstDateOfPreviousMonth);

                            aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                            Date lastDateOfPreviousMonth = aCalendar.getTime();
                            endDate = df.format(lastDateOfPreviousMonth);
                            sendReportRequest();


                        }
//                        data sorting according to previous year calculated
                        else if (item.getItemId() == R.id.three) {

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


                        }
//                        data sorting according to current year calculated
                        else if (item.getItemId() == R.id.five) {

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
                        }
//                        user navigates to custom dialog to customise sorting of data manually.
                        else if (item.getItemId() == R.id.four) {
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
                v.setTextColor(getResources().getColor(R.color.white));
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
                v.setTextColor(getResources().getColor(R.color.white));
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
//          theme setup done here
            edis.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            errorMsgLayout.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sort_btn.setBackground(getResources().getDrawable(R.drawable.ic_filter));

        }
    }

    private void sendReportRequest() {
//      whene there is no data in adaptive table, No data screen display
        adaptiveTableLayout.setVisibility(View.GONE);
        errorMsgLayout.setVisibility(View.VISIBLE);

        if (startDate.length() <= 0 || endDate.length() <= 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            Date monthFirstDay = calendar.getTime();
            calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date monthLastDay = calendar.getTime();

            startDate = sdf.format(monthFirstDay);
            Date c = Calendar.getInstance().getTime();

            endDate = df.format(c);
        }

        try {

            Date strDate = new SimpleDateFormat("MMM dd yyyy HH:mm:ss").parse(sdf.format(new Date(startDate)));
            Date EDdate = new SimpleDateFormat("MMM dd yyyy HH:mm:ss").parse(df.format(new Date(endDate)));
            String endTIMESTAMP = String.valueOf(Long.parseLong(String.valueOf(EDdate.getTime())) / 1000);
            String startTIMESTAMP = String.valueOf(Long.parseLong(String.valueOf(strDate.getTime())) / 1000);

            showProgress();
//            getEDISTransactionDashboard request is send here with startDate, EndDate, endTimeStamp and startTimeStamp data.
            WSHandler.getRequest(getMainActivity(), "getEDISTransactionDashboard?gscid=" + AccountDetails.getUsername(getMainActivity())
                    + "&startDate=" + startTIMESTAMP + "&endDate=" + endTIMESTAMP, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    hideProgress();
                    try {

                        Gson gson = new Gson();
                        EdisTransactionDetailsResponse staff = gson.fromJson(String.valueOf(response), EdisTransactionDetailsResponse.class);

                        ArrayList<EdisTransactionDetailsResponse.EdisTransactionDetailsData> orderBookDataArrayList = staff.getData();
                        orderBookDataArrayList.add(0, new EdisTransactionDetailsResponse.EdisTransactionDetailsData("",
                                "", "", "", "", "",
                                "", ""));

                        adaptiveTableLayout.setVisibility(View.VISIBLE);
                        errorMsgLayout.setVisibility(View.GONE);

                        EdisTransactionDetails.ReportDataTableAdapter reportDataTableAdapter = new EdisTransactionDetails.ReportDataTableAdapter(getMainActivity(), orderBookDataArrayList);
                        adaptiveTableLayout.setAdapter(reportDataTableAdapter);
                        adaptiveTableLayout.notifyLayoutChanged();

                    } catch (Exception e) {
                        toggleErrorLayout(true);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String message) {
                    toggleErrorLayout(true);
                    Log.e("Response fail", "" + message);
                    hideProgress();
                }
            });

        } catch (Exception e) {
            Log.e("date error ", "" + e);
        }
    }

    public class ReportDataTableAdapter extends LinkedAdaptiveTableAdapter<ViewHolderImpl> {

        private final LayoutInflater mLayoutInflater;
        private ArrayList<EdisTransactionDetailsResponse.EdisTransactionDetailsData> mTableDataSource;
        private final int mColumnWidth;
        private final int mRowHeight;
        private final int mHeaderHeight;
        private final int mHeaderWidth;
        private Context context;

        public ReportDataTableAdapter(Context context, ArrayList<EdisTransactionDetailsResponse.EdisTransactionDetailsData> tableDataSource) {
            mLayoutInflater = LayoutInflater.from(context);
            mTableDataSource = new ArrayList<>();
            mTableDataSource.clear();
            mTableDataSource = tableDataSource;
            Resources res = context.getResources();
            mColumnWidth = res.getDimensionPixelSize(R.dimen.column_width_reportEDIS);
            mRowHeight = res.getDimensionPixelSize(R.dimen.row_height_reportEDIS);
            mHeaderHeight = res.getDimensionPixelSize(R.dimen.column_header_height_reportEDIS);
            mHeaderWidth = res.getDimensionPixelSize(R.dimen.row_header_width_reportEDIS);
        }

        public void setAppList(ArrayList<EdisTransactionDetailsResponse.EdisTransactionDetailsData> categoryModel) {

            mTableDataSource.clear();
            mTableDataSource.addAll(categoryModel);
            notifyDataSetChanged();
        }

        public EdisTransactionDetailsResponse.EdisTransactionDetailsData getItemScanData(int position) {
            return mTableDataSource.get(position);
        }

        @Override
        public int getRowCount() {
            return mTableDataSource.size();
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateItemViewHolder(@NonNull ViewGroup parent) {
//            Layout of data items of Adaptive Table
            return new EdisTransactionDetails.ReportDataTableAdapter.TestViewHolder(mLayoutInflater.inflate(R.layout.item_card, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent) {
//            Layout of Column Header of Adaptive Table
            return new EdisTransactionDetails.ReportDataTableAdapter.TestHeaderColumnViewHolder(mLayoutInflater.inflate(R.layout.item_header_column, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateRowHeaderViewHolder(@NonNull ViewGroup parent) {
//            Layout of Row Header of Adaptive Table
            return new EdisTransactionDetails.ReportDataTableAdapter.TestHeaderRowViewHolder(mLayoutInflater.inflate(R.layout.report_header_row, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateLeftTopHeaderViewHolder(@NonNull ViewGroup parent) {
//            Layout of Left Top Header of Adaptive Table
            return new EdisTransactionDetails.ReportDataTableAdapter.TestHeaderLeftTopViewHolder(mLayoutInflater.inflate(R.layout.item_header_left_top, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderImpl viewHolder, int row, int column) {
            EdisTransactionDetails.ReportDataTableAdapter.TestViewHolder vh = (EdisTransactionDetails.ReportDataTableAdapter.TestViewHolder) viewHolder;


            EdisTransactionDetailsResponse.EdisTransactionDetailsData itemData = mTableDataSource.get(row); // skip headers
            vh.dataSubtitle.setVisibility(View.GONE);
            if (column == 1) {
                if (itemData.getSymbol() != null && !itemData.getSymbol().isEmpty()) {
                    vh.dataTitle.setText(itemData.getSymbol());
                } else {
                    vh.dataTitle.setText(" ");
                }


                vh.dataTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            } else if (column == 2) {
                if (itemData.getFreeQty() != null && !itemData.getFreeQty().isEmpty()) {
                    vh.dataTitle.setText(itemData.getFreeQty());
                } else {
                    vh.dataTitle.setText(" ");

                }
                vh.dataTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            } else if (column == 3) {
                if (itemData.getAuthorizedQty() != null && !itemData.getAuthorizedQty().isEmpty()) {
                    vh.dataTitle.setText(itemData.getAuthorizedQty());
                } else {
                    vh.dataTitle.setText(" ");

                }
                vh.dataTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);


            } else if (column == 4) {
                if (itemData.getStatus() != null && !itemData.getStatus().isEmpty()) {

                    vh.dataTitle.setText(itemData.getStatus());

//                    if (itemData.getStatus().equalsIgnoreCase("Unsuccessful")) {
//                        vh.dataTitle.setTextColor(getResources().getColor(R.color.light_orange));
//
//                    } else {
//                        vh.dataTitle.setTextColor(getResources().getColor(R.color.green_400));
//                    }


                } else {
                    vh.dataTitle.setText(" ");

                }
                vh.dataTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            } else if (column == 5) {
                if (itemData.getErrorDesc() != null && !itemData.getErrorDesc().isEmpty()) {
                    vh.dataTitle.setText(itemData.getErrorDesc());
                } else {
                    vh.dataTitle.setText(" ");

                }
                vh.dataTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            }
        }

        @Override
        public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder, final int column) {
            EdisTransactionDetails.ReportDataTableAdapter.TestHeaderColumnViewHolder vh = (EdisTransactionDetails.ReportDataTableAdapter.TestHeaderColumnViewHolder) viewHolder;
            vh.headerColumnSubtitle.setVisibility(View.GONE);

//          setting Column header title manually
            if (column == 1) {
                vh.headerColumnTitle.setText("Scrip Name");
                vh.headerColumnTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            } else if (column == 2) {
                vh.headerColumnTitle.setText("Free Qty");
                vh.headerColumnTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

                //vh.headerColumnSubtitle.setText("");
            } else if (column == 3) {
                vh.headerColumnTitle.setText("Authorize Qty");
                vh.headerColumnTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

                //vh.headerColumnSubtitle.setText("");
            } else if (column == 4) {
                vh.headerColumnTitle.setText("Status");
                vh.headerColumnTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

                //vh.headerColumnSubtitle.setText("");
            } else if (column == 5) {
                vh.headerColumnTitle.setText("Error Description");
                vh.headerColumnTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

                //vh.headerColumnSubtitle.setText("");
            }

        }

        @Override
        public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {

            EdisTransactionDetails.ReportDataTableAdapter.TestHeaderRowViewHolder vh = (EdisTransactionDetails.ReportDataTableAdapter.TestHeaderRowViewHolder) viewHolder;
            vh.headerRowSubtitle.setVisibility(View.GONE);

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                vh.vLine.setBackgroundColor(getResources().getColor(R.color.black));
                vh.viewLine2.setBackgroundColor(getResources().getColor(R.color.black));
            }

            if (mTableDataSource.get(row).getDate() != null && !mTableDataSource.get(row).getDate().isEmpty()) {

                try {
                    vh.headerRowTitle.setText(getDateFromTimeStamp(mTableDataSource.get(row).getDate(), "dd/MM/yyyy", "BSE"));
                } catch (Exception e) {
                    Log.e("date error ", e.getMessage());
                }
            } else {
                vh.headerRowTitle.setText(" ");
            }
        }

        @Override
        public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {
//          Left top header data binding
            EdisTransactionDetails.ReportDataTableAdapter.TestHeaderLeftTopViewHolder vh = (EdisTransactionDetails.ReportDataTableAdapter.TestHeaderLeftTopViewHolder) viewHolder;
            vh.leftTopSubtitle.setVisibility(View.GONE);
            vh.leftTopTitle.setText("Date");
            vh.leftTopSubtitle.setText("");

            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                vh.vLine.setBackgroundColor(getResources().getColor(R.color.black));
                vh.viewLine2.setBackgroundColor(getResources().getColor(R.color.black));
            }
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
