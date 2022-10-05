package com.acumengroup.mobile.reports;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.EdisTransactionDetailsResponse;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.columnsliderlib.AdaptiveTableLayout;
import com.acumengroup.columnsliderlib.LinkedAdaptiveTableAdapter;
import com.acumengroup.columnsliderlib.ViewHolderImpl;
import com.google.gson.Gson;

import org.json.JSONObject;

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


public class MarginPledgeTransactionDetails extends GreekBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private AdaptiveTableLayout adaptiveTableLayout;
    private DateFormat df;
    private DateFormat sdf;
    private Spinner spinner1;
    private GreekTextView errorMsgLayout, txt_ttlPNL;
    private ImageButton sort_btn;
    private String startDate = "", endDate = "";
    private int year, month, day;
    private Calendar calendar;
    private List<String> spinner1List;
    private String selectedReqType = "MTF";

    public MarginPledgeTransactionDetails() {
        // Required empty public constructor
    }

    public static MarginPledgeTransactionDetails newInstance(String param1, String param2) {
        MarginPledgeTransactionDetails fragment = new MarginPledgeTransactionDetails();
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
        spinner1List.add("Margin Pledge Details");
        spinner1List.add("Normal Pledge Details");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pledge_transaction_details, container, false);
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);

        adaptiveTableLayout = view.findViewById(R.id.cost_report_tableLayout);
        df = new SimpleDateFormat("MMM dd yyyy 23:59:59", Locale.US);
        sdf = new SimpleDateFormat("MMM dd yyyy 00:00:00", Locale.US);
        spinner1 = view.findViewById(R.id.report_spinner);
        errorMsgLayout = view.findViewById(R.id.empty_view);
        txt_ttlPNL = view.findViewById(R.id.cost_report_txt_ttlPNL);
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        txt_ttlPNL.setText(dateFormat.format(cal.getTime()));

        sort_btn = view.findViewById(R.id.sort_btn);
        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(getContext(), sort_btn);
                popup.getMenuInflater().inflate(R.menu.sort_report_menu, popup.getMenu());
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

                if (spinner1List.get(position).equalsIgnoreCase("Margin Pledge Details")) {
                    selectedReqType = "MTF";
                } else {
                    selectedReqType = "NORMAL";
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

    private void sendReportRequest() {

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
            WSHandler.getRequest(getMainActivity(), "getEPledgeTransactionDashboard?gscid=" + AccountDetails.getUsername(getMainActivity())
                    + "&startDate=" + startTIMESTAMP + "&endDate=" + endTIMESTAMP +
                    "&reqType=" + selectedReqType, new WSHandler.GreekResponseCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    hideProgress();
                    try {

                        Gson gson = new Gson();
                        EdisTransactionDetailsResponse staff = gson.fromJson(String.valueOf(response), EdisTransactionDetailsResponse.class);

                        ArrayList<EdisTransactionDetailsResponse.EdisTransactionDetailsData> orderBookDataArrayList = staff.getData();
                        orderBookDataArrayList.add(0, new EdisTransactionDetailsResponse.EdisTransactionDetailsData("",
                                "", "", "","","",
                                "", ""));

                        adaptiveTableLayout.setVisibility(View.VISIBLE);
                        errorMsgLayout.setVisibility(View.GONE);

                        ReportDataTableAdapter reportDataTableAdapter = new ReportDataTableAdapter(getMainActivity(), orderBookDataArrayList);
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
            return new TestViewHolder(mLayoutInflater.inflate(R.layout.item_card, parent, false));
        }

        @NonNull
        @Override
        public ViewHolderImpl onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent) {
            return new TestHeaderColumnViewHolder(mLayoutInflater.inflate(R.layout.item_header_column, parent, false));
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
            TestViewHolder vh = (TestViewHolder) viewHolder;


            EdisTransactionDetailsResponse.EdisTransactionDetailsData itemData = mTableDataSource.get(row); // skip headers
            vh.dataSubtitle.setVisibility(View.GONE);
            if (column == 1) {
                if (itemData.getScripName() != null && !itemData.getScripName().isEmpty()) {
                    vh.dataTitle.setText(itemData.getScripName());
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
                if (itemData.getPledgedQty() != null && !itemData.getPledgedQty().isEmpty()) {
                    vh.dataTitle.setText(itemData.getPledgedQty());
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
            TestHeaderColumnViewHolder vh = (TestHeaderColumnViewHolder) viewHolder;

            if (column == 1) {
                vh.headerColumnTitle.setText("Scrip Name");
                vh.headerColumnTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

                //  vh.headerColumnSubtitle.setText("");
            } else if (column == 2) {
                vh.headerColumnTitle.setText("Free Qty");
                vh.headerColumnTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

                //vh.headerColumnSubtitle.setText("");
            } else if (column == 3) {
                vh.headerColumnTitle.setText("Pledged Qty");
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

            TestHeaderRowViewHolder vh = (TestHeaderRowViewHolder) viewHolder;
            vh.headerRowSubtitle.setVisibility(View.GONE);

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
            TestHeaderLeftTopViewHolder vh = (TestHeaderLeftTopViewHolder) viewHolder;
            vh.leftTopSubtitle.setVisibility(View.GONE);
            vh.leftTopTitle.setText("Date");
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

            private TestViewHolder(@NonNull View itemView) {
                super(itemView);
                dataTitle = itemView.findViewById(R.id.dataTitle);
                dataSubtitle = itemView.findViewById(R.id.dataSubtitle);
            }
        }

        private class TestHeaderColumnViewHolder extends ViewHolderImpl {
            GreekTextView headerColumnSubtitle, headerColumnTitle;
            View vLine;

            private TestHeaderColumnViewHolder(@NonNull View itemView) {
                super(itemView);
                headerColumnTitle = itemView.findViewById(R.id.dataTitle);
                headerColumnSubtitle = itemView.findViewById(R.id.dataSubtitle);
                vLine = itemView.findViewById(R.id.vLine);

                //  headerColumnSubtitle.setVisibility(View.GONE);


            }
        }

        private class TestHeaderRowViewHolder extends ViewHolderImpl {
            GreekTextView headerRowTitle, headerRowSubtitle;

            TestHeaderRowViewHolder(@NonNull View itemView) {
                super(itemView);
                headerRowTitle = itemView.findViewById(R.id.title);
                headerRowSubtitle = itemView.findViewById(R.id.subTitle);
            }
        }

        private class TestHeaderLeftTopViewHolder extends ViewHolderImpl {
            GreekTextView leftTopSubtitle, leftTopTitle;

            private TestHeaderLeftTopViewHolder(@NonNull View itemView) {
                super(itemView);
                leftTopTitle = itemView.findViewById(R.id.title);
                leftTopSubtitle = itemView.findViewById(R.id.subTitle);
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
