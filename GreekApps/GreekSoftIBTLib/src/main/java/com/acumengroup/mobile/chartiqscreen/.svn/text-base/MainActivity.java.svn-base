package com.acumengroup.mobile.chartiqscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.intradaychart.DailyChartRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripRequest;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.BuildConfig;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.MovableFloatingActionButton;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.chartiqscreen.chartiqsample.ChartOptions;
import com.acumengroup.mobile.chartiqscreen.chartiqsample.ColorAdapter;
import com.acumengroup.mobile.chartiqscreen.chartiqsample.StudiesActivity;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.acumengroup.chartiq.sdk.ChartIQ;
import com.acumengroup.chartiq.sdk.Promise;
import com.acumengroup.chartiq.sdk.model.Hud;
import com.acumengroup.chartiq.sdk.model.OHLCChart;
import com.acumengroup.chartiq.sdk.model.Study;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import de.greenrobot.event.EventBus;

import static com.acumengroup.mobile.chartiqscreen.chartiqsample.StudiesActivity.ACTIVE_STUDIES;
import static com.acumengroup.mobile.chartiqscreen.chartiqsample.StudiesActivity.STUDIES_LIST;

//import com.acumengroup.chartiq.sdk.model.Hud;


public class MainActivity extends AppCompatActivity implements GreekUIServiceHandler, GreekConstants {

    private static final int DRAW_REQUEST_CODE = 1;
    private static final int SEARCH_REQUEST_CODE = 4;
    private static final int STUDIES_REQUEST_CODE = 2;
    private static final int CHART_OPTIONS_REQUEST_CODE = 3;
    private static final String defaultSymbol = "AAPL";
    public static final String chartUrl = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port() + "/Chartiq/sample-template-native-sdk.html";
//        public static final String chartUrl =  "http://tester.greeksoft.in//Chartiq/sample-template-native-sdk.html";
    ChartIQ.DataSourceCallback call;
    ChartIQ chartIQ;
    GreekBaseActivity greekBaseActivity = new GreekBaseActivity();
    private String token, searchtoken;
    public static final String SCRIP_NAME = "Scrip";
    private MovableFloatingActionButton movableExpandableFab;
    private boolean isExpanded = false;
    private boolean iscrosshairEnbled = false;
    Date printTime = null, lastDT = null;
    String lastHigh, lastLow, lastOpen, lastClose, lastVolume;

    double total_vol=0.0;

    //intervals
    private RadioGroup resolutionBar;
    private RadioButton btn1M;
    private RadioButton btn5M;
    private RadioButton btn10M;
    private RadioButton btn15M;
    private RadioButton btn20M;
    private RadioButton btn1W;
    private RadioButton btnM1;
    private RadioButton btnY1;
    private RadioButton btnM;
    private RadioButton btn45M;

    //top toolbar
    EditText symbolInput;
    TextView clear;
    Button interval;
    Button compare;
    ImageView crosshair;

    //drawing toolbar
    LinearLayout drawingToolbar;
    LinearLayout blankView;
    GreekTextView drawingToolName;
    LinearLayout fill;
    LinearLayout line;
    ImageView lineType;
    PopupWindow fillColorPalette;
    PopupWindow lineColorPalette;
    PopupWindow lineTypePalette;
    RecyclerView fillColorRecycler;
    RecyclerView lineColorRecycler;

    //variables
    String chartStyle;
    boolean logScale;
    String drawingTool;
    ArrayList<Study> activeStudies = new ArrayList<>();
    OHLCChart[] data, updateddata;
    boolean chartLoaded = false;
    Button drawbtn, studies;
    private ImageButton charttypes, candletype, ohcltype, searchtype;
    private GreekTextView lastTextValue, symbolTextValue, changeTextValue, hightext, lowtext, closetext, volText, ltpText;
    HashMap<String, Boolean> talkbackFields = new HashMap<String, Boolean>();
    LinearLayout roverlay, roverlaybtn,parent_layout;

    private final Item[] items = new Item[]{
            new Item("header", "Intervals", -1),
            new Item("divider", null, -1),
            new Item("item", "1 minute", R.id.m1),
            new Item("item", "3 minute", R.id.m3),
            new Item("item", "5 minute", R.id.m5),
            new Item("item", "10 minute", R.id.m10),
            new Item("item", "30 minute", R.id.m30),
            new Item("divider", null, -1),
            new Item("item", "1 hour", R.id.h1),
            new Item("item", "4 hour", R.id.h4),
            new Item("divider", null, -1),
            new Item("item", "1 day", R.id.d1, true),
            new Item("item", "2 day", R.id.d2),
            new Item("item", "3 day", R.id.d3),
            new Item("item", "5 day", R.id.d5),
            new Item("item", "10 day", R.id.d10),
            new Item("item", "20 day", R.id.d10),
            new Item("divider", null, -1),
            new Item("item", "1 week", R.id.w1),
            new Item("divider", null, -1),
            new Item("item", "1 month", R.id.month1)
    };
    private ServiceResponseListener serviceResponseHandler;
    private Intent args;
    private String LastCurrentDate;
    private String LastStoredResponseDate;
    private boolean isDailybasis;
    private String LastInterval;
    private int checkedId;
    MarketsSingleScripResponse quoteResponse;
    private StreamingController streamController;
    private ArrayList<String> sym;
    private boolean isfirsttime = true;
    private boolean iscrosshairon = false;
    private boolean isFromsearch = false;
    private boolean iscomparision = false;
    private String searchsymbol = "";

    public MainActivity() {
        call = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        AccountDetails.setIsMainActivity(false);
        AccountDetails.setIsbackmainActivity(true);
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
            editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
            editor.commit();

            if(sym.size() > 0)
                streamController.pauseStreaming(this, "marketPicture", sym);
//            if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);

//            }
            finish();
        }
        return super.onKeyDown(keyCode, event);


    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if(streamingResponse.getStreamingType().equalsIgnoreCase("marketpicture")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
                updateOhlc(broadcastResponse);
            }



        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateOhlc(StreamerBroadcastResponse response) {
        if(response.getSymbol().equals(args.getExtras().getString("Token"))) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyyHH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String currentDateTimeString = null;
            Date currentDateTime = null;
            try {
                currentDateTime = simpleDateFormat.parse(response.getLtt());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:'00.000Z'");
                currentDateTimeString = sdf.format(currentDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            int checkedIds = resolutionBar.getCheckedRadioButtonId();
            if(checkedIds == R.id.btn1M) {
                livechart(response, "minute", "1");
            } else if(checkedIds == R.id.btn5M) {
            livechart(response, "minute", "5");
            } else if(checkedIds == R.id.btn10M) {
                livechart(response, "minute", "30");
            } else if(checkedIds == R.id.btn15M) {
//                updateChart(response, 60);
                livechart(response, "minute", "60");
            } else if(checkedIds == R.id.btn20M) {
                livechart(response, "day", "1");
            } else if(checkedIds == R.id.btn1H) {
                livechart(response, "week", "1");
            } else if(checkedIds == R.id.btn30D) {
                livechart(response, "month", "1");
            } else if(checkedIds == R.id.btn1y) {
                livechart(response, "year", "1");
            } else if(checkedIds == R.id.btnM) {
                livechart(response, "minute", "15");
            } else if(checkedIds == R.id.btn45M) {
                livechart(response, "minute", "45");
            }

            chartIQ.setVisibility(View.VISIBLE);
            blankView.setVisibility(View.GONE);
            setInitialOHLCData();


        }
    }

    private void updateChart(StreamerBroadcastResponse response, int interval) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat simpledtf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String[] bCastData = response.getLtt().split(" ");
        String[] bCastDate = bCastData[0].split("-");
        String[] bcastTime = bCastData[1].split(":");

        Calendar cal = Calendar.getInstance();
        cal.setTime(lastDT);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        Date bcastDate = null;
        try {
            bcastDate = simpledtf.parse(response.getLtt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(String.valueOf(hours).equalsIgnoreCase(bcastTime[0])) {

            long mills = bcastDate.getTime() - lastDT.getTime();
            int hr = (int) (mills / (1000 * 60 * 60));
            int mn = (int) ((mills / (1000 * 60)) % 60);
//            if(String.valueOf(minute).equalsIgnoreCase(bcastTime[1])) {
            if(mn < interval) {
                if(Double.parseDouble(response.getLast()) > Double.parseDouble(lastHigh)) {
                    lastHigh = response.getLast();
                } else if(Double.parseDouble(response.getLast()) < Double.parseDouble(lastLow)) {
                    lastLow = response.getLast();
                }
                lastClose = response.getLast();
                String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(lastDT) + "\"}]";
                updateddata = new Gson().fromJson(body, OHLCChart[].class);
                chartIQ.pushUpdate(updateddata);
//                lastDT= lastDT;
            } else {

                lastOpen = lastHigh = lastLow = lastClose = response.getLast();
                String bTime = bCastDate[1] + "/" + bCastDate[0] + "/" + bCastDate[2] + " " + bcastTime[0] + ":" + bcastTime[1] + ":" + bcastTime[2];
                Date a = new Date(bTime);

                long timeStamp = (a.getTime() / 1000);
                String d = simpleDateFormat.format(new Date(timeStamp * 1000)).toString();

                String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp * 1000)) + "\"}]";
//                String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
                updateddata = new Gson().fromJson(body, OHLCChart[].class);
                chartIQ.pushUpdate(updateddata);
                try {
                    lastDT = simpleDateFormat.parse(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } else {
            lastOpen = lastHigh = lastLow = lastClose = response.getLast();
            String bTime = bCastDate[1] + "/" + bCastDate[0] + "/" + bCastDate[2] + " " + bcastTime[0] + ":" + bcastTime[1] + ":" + "00";

            Date a = new Date(bTime);
//            String d = sdf.format(a);
            long timeStamp = (a.getTime() / 1000);
            String d = sdf.format(new Date(timeStamp * 1000)).toString();

            String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp * 1000)) + "\"}]";
//            String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
            updateddata = new Gson().fromJson(body, OHLCChart[].class);
            chartIQ.pushUpdate(updateddata);
            try {
                lastDT = simpleDateFormat.parse(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }

    private void livechart(StreamerBroadcastResponse response, String g_interval, String g_period) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat simpledtf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String[] bCastData = response.getLtt().split(" ");
        String[] bCastDate = bCastData[0].split("-");
        String[] bcastTime = bCastData[1].split(":");

        Calendar cal = Calendar.getInstance();
        cal.setTime(lastDT);

        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        Date bcastDate = null;
        try {
            bcastDate = simpledtf.parse(response.getLtt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = bcastDate.getTime() - lastDT.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hr = minutes / 60;
        long days = hr / 24;

        if(g_interval == "minute") {
            if(String.valueOf(hours).equalsIgnoreCase(bcastTime[0]) || g_period.equalsIgnoreCase("30") || g_period.equalsIgnoreCase("45") || g_period.equalsIgnoreCase("60")) {
                if(minutes < Integer.parseInt(g_period)) {
                    if(Double.parseDouble(response.getLast()) > Double.parseDouble(lastHigh)) {
                        lastHigh = response.getLast();
                    } else if(Double.parseDouble(response.getLast()) < Double.parseDouble(lastLow)) {
                        lastLow = response.getLast();
                    }
                    lastClose = response.getLast();

                    double new_Vol = Double.parseDouble(response.getTot_vol());

                    double temp = new_Vol- total_vol;
                    //total_vol = temp;
                    //tot_volume[tot_volume.length-1] = total_vol;
//                    volume[volume.length-1] = new_Vol;

                    String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + /*response.getTot_vol()*/ temp+ ",\"DT\":\"" + simpleDateFormat.format(lastDT) + "\"}]";
                    updateddata = new Gson().fromJson(body, OHLCChart[].class);
                    chartIQ.pushUpdate(updateddata);
//                lastDT= lastDT;
                } else {

                    lastOpen = lastHigh = lastLow = lastClose = response.getLast();
                    String bTime = bCastDate[1] + "/" + bCastDate[0] + "/" + bCastDate[2] + " " + bcastTime[0] + ":" + bcastTime[1] + ":" + bcastTime[2];
                    Date a = new Date(bTime);

                    long timeStamp = (a.getTime() / 1000);
                    String d = simpleDateFormat.format(new Date(timeStamp * 1000)).toString();
                    double new_Vol = Double.parseDouble(response.getTot_vol());

                    double temp = new_Vol- total_vol;
                    String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + /*response.getTot_vol()*/ temp+ ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp * 1000)) + "\"}]";
//                String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
                    updateddata = new Gson().fromJson(body, OHLCChart[].class);
                    chartIQ.pushUpdate(updateddata);
                    try {
                        lastDT = simpleDateFormat.parse(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                lastOpen = lastHigh = lastLow = lastClose = response.getLast();
                String bTime = bCastDate[1] + "/" + bCastDate[0] + "/" + bCastDate[2] + " " + bcastTime[0] + ":" + bcastTime[1] + ":" + "00";

                Date a = new Date(bTime);
//            String d = sdf.format(a);
                long timeStamp = (a.getTime() / 1000);
                String d = sdf.format(new Date(timeStamp * 1000)).toString();
                double new_Vol = Double.parseDouble(response.getTot_vol());

                double temp = new_Vol- total_vol;
                String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + /*response.getTot_vol()*/temp + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp * 1000)) + "\"}]";
//            String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
                updateddata = new Gson().fromJson(body, OHLCChart[].class);
                chartIQ.pushUpdate(updateddata);
                try {
                    lastDT = simpleDateFormat.parse(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else if(g_interval == "day" || g_interval == "week" || g_interval == "month" || g_interval == "year") {

            String d1 = simpleDateFormat.format(lastDT);
            Date d2 = null;

            try {
                d2 = sdf.parse(d1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

//        try {
            if(d2.compareTo(bcastDate) == 0) {
                if(Double.parseDouble(response.getLast()) > Double.parseDouble(lastHigh)) {
                    lastHigh = response.getLast();
                } else if(Double.parseDouble(response.getLast()) < Double.parseDouble(lastLow)) {
                    lastLow = response.getLast();
                }
                lastClose = response.getLast();

                String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(lastDT) + "\"}]";
                //                String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
                updateddata = new Gson().fromJson(body, OHLCChart[].class);
                chartIQ.pushUpdate(updateddata);

            } else if(d2.compareTo(bcastDate) < 0) {
                if(g_interval.equalsIgnoreCase("day")) {
                    lastOpen = lastHigh = lastLow = lastClose = response.getLast();
                    String bTime = bCastDate[1] + "/" + bCastDate[0] + "/" + bCastDate[2] + " " + bcastTime[0] + ":" + bcastTime[1] + ":" + "00";

                    Date a = new Date(bTime);
                    //            String d = sdf.format(a);
                    long timeStamp = (a.getTime() / 1000);
                    String d = sdf.format(new Date(timeStamp * 1000)).toString();

                    String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp * 1000)) + "\"}]";
                    //            String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
                    updateddata = new Gson().fromJson(body, OHLCChart[].class);
                    chartIQ.pushUpdate(updateddata);
                    try {
                        lastDT = simpleDateFormat.parse(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else if(g_interval.equalsIgnoreCase("week")) {
                    if(days <= 7) {
                        lastOpen = lastHigh = lastLow = lastClose = response.getLast();
                        String bTime = bCastDate[1] + "/" + bCastDate[0] + "/" + bCastDate[2] + " " + bcastTime[0] + ":" + bcastTime[1] + ":" + "00";

                        Date a = new Date(bTime);
                        //            String d = sdf.format(a);
                        long timeStamp = (a.getTime() / 1000);
                        String d = sdf.format(new Date(timeStamp * 1000)).toString();

                        String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp * 1000)) + "\"}]";
                        //            String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
                        updateddata = new Gson().fromJson(body, OHLCChart[].class);
                        chartIQ.pushUpdate(updateddata);
                        try {
                            lastDT = simpleDateFormat.parse(d);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                } else if(g_interval.equalsIgnoreCase("month")) {
                    if(days <= 30) {
                        lastOpen = lastHigh = lastLow = lastClose = response.getLast();
                        String bTime = bCastDate[1] + "/" + bCastDate[0] + "/" + bCastDate[2] + " " + bcastTime[0] + ":" + bcastTime[1] + ":" + "00";

                        Date a = new Date(bTime);
                        //            String d = sdf.format(a);
                        long timeStamp = (a.getTime() / 1000);
                        String d = sdf.format(new Date(timeStamp * 1000)).toString();

                        String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp * 1000)) + "\"}]";
                        //            String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
                        updateddata = new Gson().fromJson(body, OHLCChart[].class);
                        chartIQ.pushUpdate(updateddata);
                        try {
                            lastDT = simpleDateFormat.parse(d);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if(Double.parseDouble(response.getLast()) > Double.parseDouble(lastHigh)) {
                            lastHigh = response.getLast();
                        } else if(Double.parseDouble(response.getLast()) < Double.parseDouble(lastLow)) {
                            lastLow = response.getLast();
                        }
                        lastClose = response.getLast();

                        String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(lastDT) + "\"}]";
                        //                String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
                        updateddata = new Gson().fromJson(body, OHLCChart[].class);
                        chartIQ.pushUpdate(updateddata);
                    }

                } else if(g_interval.equalsIgnoreCase("year")) {

                    if(days == 366) {
                        lastOpen = lastHigh = lastLow = lastClose = response.getLast();
                        String bTime = bCastDate[1] + "/" + bCastDate[0] + "/" + bCastDate[2] + " " + bcastTime[0] + ":" + bcastTime[1] + ":" + "00";

                        Date a = new Date(bTime);
                        //            String d = sdf.format(a);
                        long timeStamp = (a.getTime() / 1000);
                        String d = sdf.format(new Date(timeStamp * 1000)).toString();

                        String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp * 1000)) + "\"}]";
                        //            String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
                        updateddata = new Gson().fromJson(body, OHLCChart[].class);
                        chartIQ.pushUpdate(updateddata);
                        try {
                            lastDT = simpleDateFormat.parse(d);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if(Double.parseDouble(response.getLast()) > Double.parseDouble(lastHigh)) {
                            lastHigh = response.getLast();
                        } else if(Double.parseDouble(response.getLast()) < Double.parseDouble(lastLow)) {
                            lastLow = response.getLast();
                        }
                        lastClose = response.getLast();

                        String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(lastDT) + "\"}]";
                        //                String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
                        updateddata = new Gson().fromJson(body, OHLCChart[].class);
                        chartIQ.pushUpdate(updateddata);
                    }

                }
            }

        }


    }

    private void updateChartintra(StreamerBroadcastResponse response, int interval) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat simpledtf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String[] bCastData = response.getLtt().split(" ");
        String[] bCastDate = bCastData[0].split("-");
        String[] bcastTime = bCastData[1].split(":");

        Calendar cal = Calendar.getInstance();
        cal.setTime(lastDT);

        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        Date bcastDate = null;
        try {
            bcastDate = simpledtf.parse(response.getLtt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        if(String.valueOf(hours).equalsIgnoreCase(bcastTime[0])) {

        long mills = bcastDate.getTime() - lastDT.getTime();
        int hr = (int) (mills / (1000 * 60 * 60));
        int mn = (int) ((mills / (1000 * 60)) % 60);
        if(lastDT.compareTo(bcastDate) == 0) {
            if(Double.parseDouble(response.getLast()) > Double.parseDouble(lastHigh)) {
                lastHigh = response.getLast();
            } else if(Double.parseDouble(response.getLast()) < Double.parseDouble(lastLow)) {
                lastLow = response.getLast();
            }
            lastClose = response.getLast();
            String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(lastDT) + "\"}]";
            updateddata = new Gson().fromJson(body, OHLCChart[].class);
            chartIQ.pushUpdate(updateddata);
//                lastDT= lastDT;
        } else {

            lastOpen = lastHigh = lastLow = lastClose = response.getLast();
            String bTime = bCastDate[1] + "/" + bCastDate[0] + "/" + bCastDate[2] + " " + bcastTime[0] + ":" + bcastTime[1] + ":" + bcastTime[2];
            Date a = new Date(bTime);

            long timeStamp = (a.getTime() / 1000);
            String d = simpleDateFormat.format(new Date(timeStamp * 1000)).toString();

            String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"Volume\":" + response.getTot_vol() + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp * 1000)) + "\"}]";
//                String body = "[{\"Close\":" + lastClose + ",\"Open\":" + lastOpen + ",\"High\":" + lastHigh + ",\"Low\":" + lastLow + ",\"DT\":\"" + simpleDateFormat.format(new Date(timeStamp*1000)) + "\"}]";
            updateddata = new Gson().fromJson(body, OHLCChart[].class);
            chartIQ.pushUpdate(updateddata);
            try {
                lastDT = simpleDateFormat.parse(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {
        try {

            if(args.getExtras().getString("Token").equals(response.getSymbol())) {

                if(((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    ltpText.setText(String.format("%.4f", Double.parseDouble(response.getLast())));

                } else {
                    ltpText.setText(String.format("%.2f", Double.parseDouble(response.getLast())));
                }

                if(ltpText.getText().toString().startsWith("-")) {

                    ltpText.setTextColor(getResources().getColor(R.color.dark_red_negative));

                } else {
                    if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                        ltpText.setTextColor(getResources().getColor(R.color.whitetheambuyColor));

                    }else {
                        ltpText.setTextColor(getResources().getColor(R.color.dark_green_positive));
                    }
                }

            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }



    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
        editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
        editor.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chartiq);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        Log.e("in oncreate initial",System.currentTimeMillis()+"");
        doMappings();
        createTalkbackFields();
        serviceResponseHandler = new ServiceResponseHandler(this, this);
        args = getIntent();
        symbolTextValue.setText(args.getExtras().getString(SCRIP_NAME));
        sendQuotesRequest(args.getExtras().getString(TOKEN), getAssetTypeFromToken(args.getExtras().getString(TOKEN)));
        chartIQ.setVisibility(View.GONE);
        blankView.setVisibility(View.VISIBLE);
        chartIQ.requestFocus(View.FOCUS_DOWN);
  /*      if(args.getExtras().getBoolean("iscurrency")) {
            chartIQ.setEngineProperty("YAxis.prototype.decimalPlaces", 4);
        } else {
            chartIQ.setEngineProperty("YAxis.prototype.decimalPlaces", 2);
        }*/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        streamController = new StreamingController();

        sym = new ArrayList<>();
        if(args.getExtras() != null) {
            sym.add(args.getExtras().getString("Token"));
            streamController.sendStreamingRequest(this, sym, "marketPicture", null, null, false);
            //addToStreamingList("marketPicture", sym);
        }
        chartIQ.setDataSource(new ChartIQ.DataSource() {

            @Override
            public void pullInitialData(Map<String, Object> params, ChartIQ.DataSourceCallback callback) {
                loadChartData(params, callback);
                Log.e("in pull initial",System.currentTimeMillis()+"");
                chartIQ.disableCrosshairs();

            }

            @Override
            public void pullUpdateData(Map<String, Object> params, ChartIQ.DataSourceCallback callback) {
                loadChartData(params, callback);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void pullPaginationData(Map<String, Object> params, ChartIQ.DataSourceCallback callback) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                try {
                    Date date2 = simpleDateFormat.parse(params.get("end").toString());
                    date2.getTime();
                    DateFormat time = new SimpleDateFormat("hh:mm:ss a");
                    System.out.println("Time: " + time.format(date2));
                    String strTime = time.format(date2);


                    if(strTime.compareTo("5:30") < 0) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                        Date pdate1 = dateFormat.parse(params.get("start").toString());
                        Date pdate2 = dateFormat.parse(params.get("end").toString());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(pdate1);
                        calendar.add(Calendar.DATE, -1);
                        String pstartdate = dateFormat.format(calendar.getTime());
                        Calendar c = Calendar.getInstance();
                        c.setTime(pdate2);
                        c.add(Calendar.DATE, -1);

                        String penddate = dateFormat.format(c.getTime());


                        params.replace("start", params.get("start"), pstartdate);
                        params.replace("end", params.get("end"), penddate);
                    }

                } catch (Exception e) {

                }

                loadChartData(params, callback);
            }


        });

        chartIQ.start(chartUrl, new ChartIQ.CallbackStart() {
            @Override
            public void onStart() {
                try {
                    chartIQ.setDataMethod(ChartIQ.DataMethod.PULL, defaultSymbol);
                    chartIQ.setSymbol(defaultSymbol);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        chartIQ.setShowDebugInfo("debug".equals(BuildConfig.BUILD_TYPE));

        chartIQ.setOnTouchListener(new chartIQTouchListener());

        drawingToolbar.setVisibility(View.GONE);
        interval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopupMenu();
            }
        });
        interval.setOnTouchListener(new HideKeyboardOnTouchListener());
        clear.setVisibility(View.INVISIBLE);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                symbolInput.setText("");
            }
        });
        symbolInput.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        symbolInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT
                        || actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_DONE) {
                    if(!v.getText().toString().isEmpty()) {
//                        loadChartData(v.getText().toString());
                        chartIQ.setSymbol(v.getText().toString());
                    }
                    Util.hideKeyboard(symbolInput);
                    v.clearFocus();
                    return true;
                }
                return false;
            }
        });
        symbolInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()) {
                    clear.setVisibility(View.VISIBLE);
                } else {
                    clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        chartIQ.setEngineProperty("yaxisLabelStyle", "roundRectArrow");
//        crosshair.setTag("off");
        crosshair.setOnTouchListener(new HideKeyboardOnTouchListener());
        crosshair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!iscrosshairEnbled) {
                    if (AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
                        crosshair.setBackground(getResources().getDrawable(R.color.whitetheambuyColor));
                    }else {
                        crosshair.setBackground(getResources().getDrawable(R.color.buyColor));
                    }
                    chartIQ.enableCrosshairs();
                    showCrossHairHUDDetails();
                    v.setTag("on");
                    iscrosshairon = true;
                    iscrosshairEnbled =true;
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ((ImageView) v).setImageDrawable(getResources().getDrawable(R.drawable.pointer_inactive_ic_30, null));

                    } else {
                        ((ImageView) v).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pointer_inactive_ic_30, null));
                    }
                } else {
                    chartIQ.disableCrosshairs();
                    v.setTag("off");
                    iscrosshairon = false;
                    iscrosshairEnbled =false;
                    crosshair.setBackground(getResources().getDrawable(R.drawable.button_boarder));
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ((ImageView) v).setImageDrawable(getResources().getDrawable(R.drawable.pointer_inactive_ic_30, null));
                    } else {
                        ((ImageView) v).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pointer_inactive_ic_30, null));
                    }
                }
            }
        });

        fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFillColorPalette();
            }
        });
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLineColorPalette();
            }
        });
        lineType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLineTypePalette();
            }
        });
        fillColorPalette = new PopupWindow(this);
        fillColorPalette.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        fillColorPalette.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        fillColorPalette.setContentView(getLayoutInflater().inflate(R.layout.color_palette, null));

        lineColorPalette = new PopupWindow(this);
        lineColorPalette.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        lineColorPalette.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        lineColorPalette.setContentView(getLayoutInflater().inflate(R.layout.color_palette, null));

        lineTypePalette = new PopupWindow(this);
        lineTypePalette.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        lineTypePalette.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        lineTypePalette.setContentView(getLayoutInflater().inflate(R.layout.line_type_palette, null));

        fillColorRecycler = (RecyclerView) fillColorPalette.getContentView().findViewById(R.id.recycler);
        fillColorRecycler.setLayoutManager(new GridLayoutManager(this, 5));
        fillColorRecycler.setAdapter(new ColorAdapter(this, R.array.colors, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFillColor(v);
            }
        }));

        lineColorRecycler = (RecyclerView) lineColorPalette.getContentView().findViewById(R.id.recycler);
        lineColorRecycler.setLayoutManager(new GridLayoutManager(this, 5));
        lineColorRecycler.setAdapter(new ColorAdapter(this, R.array.colors, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLineColor(v);
            }
        }));


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setInitialOHLCData() {
        if(AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("White")){
            lastTextValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            symbolTextValue.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            hightext.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            closetext.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            lowtext.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            volText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            parent_layout.setBackground(getDrawable(R.drawable.bg_drawable_white));
            studies.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            drawbtn.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn1M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn5M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn10M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn15M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn45M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn1W.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btnM1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btnY1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btn20M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            btnM.setTextColor(getResources().getColor(AccountDetails.textColorDropdown)); btn1M.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            btn1M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn5M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn10M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn15M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn45M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn1W.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btnM1.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btnY1.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btn20M.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            btnM.setBackground(getDrawable(R.drawable.chart_radio__white_style));
            compare.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//
            searchtype.setImageResource(R.drawable.area_chart_ic_30);
            candletype.setImageResource(R.drawable.candle_chart_ic_30);
            charttypes.setImageResource(R.drawable.ic_chart_black_24dp);
            ohcltype.setImageResource(R.drawable.ohlc_chart_ic_30);
            crosshair.setImageResource(R.drawable.pointer_active_ic_30);
        }
//        lastTextValue.setText("0  " + String.format("%.2f", Double.parseDouble(quoteResponse.getOpen())));
        symbolTextValue.setText(args.getExtras().getString(SCRIP_NAME));

if(getExchangeFromToken(quoteResponse.getToken()).equalsIgnoreCase("NSECURR")||getExchangeFromToken(quoteResponse.getToken()).equalsIgnoreCase("BSECURR")||quoteResponse.getInstrument().equalsIgnoreCase("FUTCUR")){
    lastTextValue.setText("0  " + String.format("%.4f", Double.parseDouble(quoteResponse.getOpen())));
    hightext.setText("H  " + String.format("%.4f", Double.parseDouble(quoteResponse.getHigh())));
    lowtext.setText("L  " + String.format("%.4f", Double.parseDouble(quoteResponse.getLow())));
    closetext.setText("C  " + String.format("%.4f", Double.parseDouble(quoteResponse.getClose())));
        }else {
    lastTextValue.setText("0  " + String.format("%.2f", Double.parseDouble(quoteResponse.getOpen())));
    hightext.setText("H  " + String.format("%.2f", Double.parseDouble(quoteResponse.getHigh())));
    lowtext.setText("L  " + String.format("%.2f", Double.parseDouble(quoteResponse.getLow())));
    closetext.setText("C  " + String.format("%.2f", Double.parseDouble(quoteResponse.getClose())));
}
        volText.setText("V  " + quoteResponse.getTot_vol());
    }

    private void showCrossHairHUDDetails() {
        chartIQ.getCrosshairsHUDDetail().then(new Promise.Callback<Hud>() {
            @Override
            public void call(Hud object) {
                if(object != null) {
                    if(object.open != null && !object.open.equalsIgnoreCase("")) {
            /*            lastTextValue.setText("0  " + String.format("%.2f", Double.parseDouble(object.open)));
                        hightext.setText("H  " + String.format("%.2f", Double.parseDouble(object.high)));
                        lowtext.setText("L  " + String.format("%.2f", Double.parseDouble(object.low)));
                        closetext.setText("C  " + String.format("%.2f", Double.parseDouble(object.close)));*/
                        volText.setText("V  " + object.volume);

                        if(getExchangeFromToken(quoteResponse.getToken()).equalsIgnoreCase("NSECURR")||getExchangeFromToken(quoteResponse.getToken()).equalsIgnoreCase("BSECURR")||quoteResponse.getInstrument().equalsIgnoreCase("FUTCUR")){
                            lastTextValue.setText("0  " + String.format("%.4f", Double.parseDouble(object.open)));
                            hightext.setText("H  " + String.format("%.4f", Double.parseDouble(object.high)));
                            lowtext.setText("L  " + String.format("%.4f", Double.parseDouble(object.low)));
                            closetext.setText("C  " + String.format("%.4f", Double.parseDouble(object.close)));
                        }else {
                            lastTextValue.setText("0  " + String.format("%.2f", Double.parseDouble(object.open)));
                            hightext.setText("H  " + String.format("%.2f", Double.parseDouble(object.high)));
                            lowtext.setText("L  " + String.format("%.2f", Double.parseDouble(object.low)));
                            closetext.setText("C  " + String.format("%.2f", Double.parseDouble(object.close)));
                        }
                    }
                }
            }
        });
    }


    public class chartIQTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(final View view, MotionEvent event) {
            Util.hideKeyboard(view);
            if(crosshair.isEnabled()) {
                showCrossHairHUDDetails();
            }
            return false;
        }
    }

    private void showFillColorPalette() {
        if(fillColorPalette.isShowing()) {
            fillColorPalette.dismiss();
        } else {
            lineColorPalette.dismiss();
            lineTypePalette.dismiss();
            fillColorPalette.showAtLocation(fill, Gravity.NO_GRAVITY, (int) fill.getX() - 90, 580);
        }
    }

    private void showLineColorPalette() {
        if(lineColorPalette.isShowing()) {
            lineColorPalette.dismiss();
        } else {
            fillColorPalette.dismiss();
            lineTypePalette.dismiss();
            lineColorPalette.showAtLocation(line, Gravity.NO_GRAVITY, (int) line.getX() - 90, 580);
        }
    }

    private String getAssetTypeFromToken(String token) {
        int tokenInt = Integer.parseInt(token);
        if(((tokenInt >= 101000000) && (tokenInt <= 101999999)) || ((tokenInt >= 201000000) && (tokenInt <= 201999999))) {
            return "equity";
        } else if(((tokenInt >= 102000000) && (tokenInt <= 102999999)) || ((tokenInt >= 202000000) && (tokenInt <= 202999999))) {
            return "fno";
        } else if(((tokenInt >= 403000000) && (tokenInt <= 403999999)) || ((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "commodity";
        } else {
            return "currency";
        }

        // return "";
    }

    private void showLineTypePalette() {
        if(lineTypePalette.isShowing()) {
            lineTypePalette.dismiss();
        } else {
            fillColorPalette.dismiss();
            lineColorPalette.dismiss();
            lineTypePalette.showAtLocation(lineType, Gravity.NO_GRAVITY, (int) lineType.getX(), 580);
        }
    }

    private void
    doMappings() {
        chartIQ = (ChartIQ) findViewById(R.id.webview);
        chartIQ.setTheme("day");
        blankView = (LinearLayout) findViewById(R.id.blankview);
        charttypes = findViewById(R.id.btn_chart);
        drawbtn = findViewById(R.id.btn_draw);
        studies = findViewById(R.id.btn_indicator);
        ohcltype = findViewById(R.id.btn_ohcl_chart);
        candletype = findViewById(R.id.btn_candle_chart);


        searchtype = findViewById(R.id.btn_search);
        drawingToolbar = (LinearLayout) findViewById(R.id.drawing_toolbar);
        drawingToolName = (GreekTextView) findViewById(R.id.drawing_tool_name);
        fill = (LinearLayout) findViewById(R.id.fill);
        line = (LinearLayout) findViewById(R.id.line);
        lineType = (ImageView) findViewById(R.id.line_type);
        interval = (Button) findViewById(R.id.interval);
        compare = (Button) findViewById(R.id.btn_compare);
        clear = (TextView) findViewById(R.id.clear);
        symbolInput = (EditText) findViewById(R.id.symbol);
        crosshair = (ImageView) findViewById(R.id.btn_crosshair);
        roverlay = findViewById(R.id.rlOverlay);
        parent_layout = findViewById(R.id.parent_layout);
        roverlaybtn = findViewById(R.id.rlOverlaybtn);
        chartIQ.setChartType("mountain");
        if (AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
            searchtype.setBackground(getResources().getDrawable(R.color.whitetheambuyColor));
        }else {
            searchtype.setBackground(getResources().getDrawable(R.color.buyColor));
        }
        drawbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDrawMenuClick(v);
            }
        });

        studies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStudiesActivity(v);
            }
        });
chartIQ.enableCrosshairs();
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                chartIQ.addComparison("ZEEL","black",true);
                Intent drawIntent = new Intent(MainActivity.this, SearchSynbolActivity.class);
                startActivityForResult(drawIntent, SEARCH_REQUEST_CODE);

            }
        });
        charttypes.setOnClickListener(selectedtype);
        ohcltype.setOnClickListener(selectedtype);
        candletype.setOnClickListener(selectedtype);
        searchtype.setOnClickListener(selectedtype);
        movableExpandableFab = findViewById(R.id.movablefab);
        lastTextValue = findViewById(R.id.lastText);
        symbolTextValue = findViewById(R.id.symbolText);
        hightext = findViewById(R.id.highText);
        lowtext = findViewById(R.id.lowText);
        closetext = findViewById(R.id.closeText);
        volText = findViewById(R.id.volText);
        ltpText = findViewById(R.id.ltpText);
        changeTextValue = findViewById(R.id.changeTextchart);


        resolutionBar = findViewById(R.id.resolutionBar);
        btn1M = findViewById(R.id.btn1M);
        btn5M = findViewById(R.id.btn5M);
        btn10M = findViewById(R.id.btn10M);//30M
        btn15M = findViewById(R.id.btn15M);//1H
        btn20M = findViewById(R.id.btn20M);//1D
        btn1W = findViewById(R.id.btn1H);//1W
        btnM1 = findViewById(R.id.btn30D);//M1
        btnY1 = findViewById(R.id.btn1y);//y1
        btnM = findViewById(R.id.btnM);//15M
        btn45M = findViewById(R.id.btn45M);//45M

        int selectedinteval = com.acumengroup.greekmain.util.Util.getPrefs(this).getInt("selected interval", 0);
        if(selectedinteval == R.id.btn1M) {
            btn1M.setChecked(true);
        } else if(selectedinteval == R.id.btn5M) {
            btn5M.setChecked(true);
        } else if(selectedinteval == R.id.btn10M) {
            btn10M.setChecked(true);
        } else if(selectedinteval == R.id.btn15M) {
            btn15M.setChecked(true);
        } else if(selectedinteval == R.id.btn20M) {
            btn20M.setChecked(true);
        } else if(selectedinteval == R.id.btn1H) {
            btn1W.setChecked(true);
        } else if(selectedinteval == R.id.btn30D) {
            btnM1.setChecked(true);
        } else if(selectedinteval == R.id.btn1y) {
            btnY1.setChecked(true);
        } else if(selectedinteval == R.id.btnM) {
            btnM.setChecked(true);
        } else if(selectedinteval == R.id.btn45M) {
            btn45M.setChecked(true);
        } else {
            btn1M.setChecked(true);
        }
        resolutionBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedIds) {


//                chartIQ.setEngineProperty("YAxis.prototype.decimalPlaces", 2);
//                chartIQ.addComparison(searchsymbol,"yellow",true);
                if(checkedIds == R.id.btn1M) {
               /*     if(args.getExtras().getBoolean("iscurrency")) {
                        chartIQ.setEngineProperty("YAxis.prototype.decimalPlaces", 4);
                    } else {
                        chartIQ.setEngineProperty("YAxis.prototype.decimalPlaces", 2);
                    }*/
                    checkedId = R.id.btn1M;
                    if(iscomparision) {
                        chartIQ.removeComparison(searchsymbol);
                    }
                    isfirsttime = true;
                    SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                    editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
                    editor.commit();
                    editor.apply();
                    chartIQ.setPeriodicity(1, "1", "minute");
                } else if(checkedIds == R.id.btn5M) {
                    isfirsttime = true;
                    if(iscomparision) {
                        chartIQ.removeComparison(searchsymbol);
                    }
                    checkedId = R.id.btn5M;
                    chartIQ.setPeriodicity(1, "5", "minute");
                    SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                    editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
                    editor.commit();
                    editor.apply();
                } else if(checkedIds == R.id.btn10M) {
                    isfirsttime = true;
                    if(iscomparision) {
                        chartIQ.removeComparison(searchsymbol);
                    }
                    checkedId = R.id.btn10M;
                    chartIQ.setPeriodicity(1, "30", "minute");
                    SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                    editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
                    editor.commit();
                    editor.apply();
                } else if(checkedIds == R.id.btn15M) {
                    isfirsttime = true;
                    if(iscomparision) {
                        chartIQ.removeComparison(searchsymbol);
                    }
                    checkedId = R.id.btn15M;
                    chartIQ.setPeriodicity(1, "60", "minute");
                    SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                    editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
                    editor.commit();
                    editor.apply();
                } else if(checkedIds == R.id.btn20M) {
                    isfirsttime = true;
                    if(iscomparision) {
                        chartIQ.removeComparison(searchsymbol);
                    }
                    checkedId = R.id.btn20M;
                    chartIQ.setPeriodicity(1, "day", "minute");
                    SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                    editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
                    editor.commit();
                    editor.apply();
                } else if(checkedIds == R.id.btn1H) {
                    checkedId = R.id.btn1H;
                    isfirsttime = true;
                    if(iscomparision) {
                        chartIQ.removeComparison(searchsymbol);
                    }
                    chartIQ.setPeriodicity(1, "week", "minute");
                    SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                    editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
                    editor.commit();
                    editor.apply();
                } else if(checkedIds == R.id.btn30D) {
                    isfirsttime = true;
                    if(iscomparision) {
                        chartIQ.removeComparison(searchsymbol);
                    }
                    checkedId = R.id.btn30D;
                    chartIQ.setPeriodicity(1, "month", "minute");
                    SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                    editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
                    editor.commit();
                    editor.apply();
                } else if(checkedIds == R.id.btn1y) {
                    isfirsttime = true;
                    if(iscomparision) {
                        chartIQ.removeComparison(searchsymbol);
                    }
                    checkedId = R.id.btn1y;
                    chartIQ.setPeriodicity(1, "year", "minute");
                    SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                    editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
                    editor.commit();
                    editor.apply();
                } else if(checkedIds == R.id.btnM) {
                    checkedId = R.id.btnM;
                    isfirsttime = true;
                    if(iscomparision) {
                        chartIQ.removeComparison(searchsymbol);
//                        chartIQ.addComparison(searchsymbol,"yellow",true);
                    }
                    chartIQ.setPeriodicity(1, "15", "minute");
                    SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                    editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
                    editor.commit();
                    editor.apply();
                } else if(checkedIds == R.id.btn45M) {
                    isfirsttime = true;
                    if(iscomparision) {
                        chartIQ.removeComparison(searchsymbol);
                    }
                    checkedId = R.id.btn45M;
                    chartIQ.setPeriodicity(1, "45", "minute");
                    SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                    editor.putInt("selected interval", resolutionBar.getCheckedRadioButtonId());
                    editor.commit();
                    editor.apply();
                }

            }
        });

        movableExpandableFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roverlaybtn.getVisibility() == View.VISIBLE) {

                    roverlay.setVisibility(View.GONE);
                    roverlaybtn.setVisibility(View.GONE);

                } else {
                    roverlay.setVisibility(View.VISIBLE);
                    roverlaybtn.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private final View.OnClickListener selectedtype = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_chart) {
                chartIQ.setChartType("line");

                if (AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
                    charttypes.setBackground(getResources().getDrawable(R.color.whitetheambuyColor));
                }else {
                    charttypes.setBackground(getResources().getDrawable(R.color.buyColor));
                }
                SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                editor.putString("selected charttype", "line");
                editor.putString("charttype", "chart");
                editor.commit();

            } else {
                charttypes.setBackground(getResources().getDrawable(R.drawable.button_boarder));
            }
            if(v.getId() == R.id.btn_ohcl_chart) {

                chartIQ.setChartType("bar");
                if (AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
                    ohcltype.setBackground(getResources().getDrawable(R.color.whitetheambuyColor));
                }else {
                    ohcltype.setBackground(getResources().getDrawable(R.color.buyColor));
                }
                SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                editor.putString("selected charttype", "bar");
                editor.putString("charttype", "ohlc");
                editor.commit();
            } else {
                ohcltype.setBackground(getResources().getDrawable(R.drawable.button_boarder));
            }

            if(v.getId() == R.id.btn_candle_chart) {

                chartIQ.setChartType("candle");
                if (AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
                    candletype.setBackground(getResources().getDrawable(R.color.whitetheambuyColor));
                }else {
                    candletype.setBackground(getResources().getDrawable(R.color.buyColor));
                }
                SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                editor.putString("selected charttype", "candle");
                editor.putString("charttype", "candle");
                editor.commit();

            } else {
                candletype.setBackground(getResources().getDrawable(R.drawable.button_boarder));
            }
            if(v.getId() == R.id.btn_search) {

                chartIQ.setChartType("mountain");
                if (AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
                    searchtype.setBackground(getResources().getDrawable(R.color.whitetheambuyColor));
                }else {
                    searchtype.setBackground(getResources().getDrawable(R.color.buyColor));
                }

                SharedPreferences.Editor editor = com.acumengroup.greekmain.util.Util.getPrefs(getApplicationContext()).edit();
                editor.putString("selected charttype", "mountain");
                editor.putString("charttype", "search");
                editor.commit();
            } else {
                searchtype.setBackground(getResources().getDrawable(R.drawable.button_boarder));
            }
        }
    };


    private void loadChartData(Map<String, Object> params, final ChartIQ.DataSourceCallback callback) {
        call = callback;
        Long diff = null;
        long elapsedDays = 0;
        Date d2 = null;
        Timestamp ts2 = null;
        Timestamp ts1 = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {


            Date date1 = simpleDateFormat.parse(params.get("start").toString());
            Date date2 = simpleDateFormat.parse(params.get("end").toString());

//            obj.printDifference(date1, date2);

            // Date date = new Date();
            ts1 = new Timestamp(date1.getTime());
            ts2 = new Timestamp(date2.getTime());
            SimpleDateFormat simpleformatter = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = simpleformatter.parse(String.valueOf(ts1));
            d2 = simpleformatter.parse(String.valueOf(ts2));
//            Log.d("timestamp",formatter.format(ts));
            diff = d2.getTime() - d1.getTime();
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            elapsedDays = diff / daysInMilli;
//            diff = diff % daysInMilli;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(params.get("interval").toString().equalsIgnoreCase("minute")) {
            if(!isFromsearch) {
                if(searchsymbol.equalsIgnoreCase(params.get("symbol").toString())) {
                    DailyChartRequest.sendRequestChart(searchtoken, Integer.parseInt(params.get("period").toString()) + "", new SimpleDateFormat("yyyyMMdd").format(ts2), "", elapsedDays + "", this, serviceResponseHandler);
                    iscomparision = true;
                } else {
                    DailyChartRequest.sendRequestChart(args.getExtras().getString("Token"), Integer.parseInt(params.get("period").toString()) + "", new SimpleDateFormat("yyyyMMdd").format(ts2), "", elapsedDays + "", this, serviceResponseHandler);
                }
            } else {
                isFromsearch = false;
                iscomparision = true;
                DailyChartRequest.sendRequestChart(searchtoken, Integer.parseInt(params.get("period").toString()) + "", new SimpleDateFormat("yyyyMMdd").format(ts2), "", elapsedDays + "", this, serviceResponseHandler);
            }
//            }
        } else if(params.get("interval").toString().equalsIgnoreCase("day") || params.get("interval").toString().equalsIgnoreCase("month") || params.get("interval").toString().equalsIgnoreCase("year")) {
            if(!isFromsearch) {
                if(searchsymbol.equalsIgnoreCase(params.get("symbol").toString())) {
                    DailyChartRequest.sendRequestForDailychart(searchtoken, params.get("period").toString(), new SimpleDateFormat("yyyyMMdd").format(ts2), new SimpleDateFormat("yyyyMMdd").format(ts1), this, serviceResponseHandler);
                    iscomparision = true;
                } else {
                    DailyChartRequest.sendRequestForDailychart(args.getExtras().getString("Token"), params.get("period").toString(), new SimpleDateFormat("yyyyMMdd").format(ts2), new SimpleDateFormat("yyyyMMdd").format(ts1), this, serviceResponseHandler);
                }
            } else {
                isFromsearch = false;
                iscomparision = true;
                DailyChartRequest.sendRequestForDailychart(searchtoken, params.get("period").toString(), new SimpleDateFormat("yyyyMMdd").format(ts2), new SimpleDateFormat("yyyyMMdd").format(ts1), this, serviceResponseHandler);
            }
        } else if(params.get("interval").toString().equalsIgnoreCase("week")) {
            if(!isFromsearch) {
                if(searchsymbol.equalsIgnoreCase(params.get("symbol").toString())) {
                    DailyChartRequest.sendRequestForWeeklychart(searchtoken, params.get("period").toString(), new SimpleDateFormat("yyyyMMdd").format(ts2), "0", this, serviceResponseHandler);
                    iscomparision = true;
                } else {
                    DailyChartRequest.sendRequestForWeeklychart(args.getExtras().getString("Token"), params.get("period").toString(), new SimpleDateFormat("yyyyMMdd").format(ts2), "0", this, serviceResponseHandler);
                }
            } else {
                isFromsearch = false;
                iscomparision = true;
                DailyChartRequest.sendRequestForWeeklychart(searchtoken, params.get("period").toString(), new SimpleDateFormat("yyyyMMdd").format(ts2), "0", this, serviceResponseHandler);
            }

        }

    }



    public void onDrawMenuClick(View view) {
        if(drawingToolbar.getVisibility() == View.VISIBLE) {
            fillColorPalette.dismiss();
            lineColorPalette.dismiss();
            lineTypePalette.dismiss();
            chartIQ.clearDrawing();
            drawingToolbar.setVisibility(View.GONE);
            chartIQ.disableDrawing();
        } else {
            startDrawActivity(null);
        }
    }

    public void startDrawActivity(View view) {
        AccountDetails.setIsMainActivity(true);
        Intent drawIntent = new Intent(this, DrawActivity.class);
        startActivityForResult(drawIntent, DRAW_REQUEST_CODE);
//        startActivity(drawIntent);
//        drawingToolbar.setVisibility(View.VISIBLE);
    }

    public void expandToFullScreen(View view) {
        if(roverlaybtn.getVisibility() == View.VISIBLE) {

            roverlay.setVisibility(View.GONE);
            roverlaybtn.setVisibility(View.GONE);
            //crosshairLayout.setVisibility(View.VISIBLE);

            LastStoredResponseDate = "";
            //changeIntraDayInterval(AccountDetails.getLastInterval());
//                                   parseChartData(responseData);

        } else {
            roverlay.setVisibility(View.VISIBLE);
            roverlaybtn.setVisibility(View.VISIBLE);
            //crosshairLayout.setVisibility(View.GONE);
            LastStoredResponseDate = "";


        }
    }

    public void startStudiesActivity(View view) {
        if(chartLoaded) {
            AccountDetails.setIsMainActivity(true);
            chartIQ.getStudyList().then(new Promise.Callback<Study[]>() {
                @Override
                public void call(final Study[] studies) {
                    chartIQ.getActiveStudies().then(new Promise.Callback<Study[]>() {
                        @Override
                        public void call(Study[] array) {
                            Intent studiesIntent = new Intent(MainActivity.this, StudiesActivity.class);
                            ArrayList<Study> allStudies = new ArrayList<>(Arrays.asList(studies));
                            activeStudies = new ArrayList<>(Arrays.asList(array));
                            studiesIntent.putExtra(STUDIES_LIST, allStudies);
                            studiesIntent.putExtra(ACTIVE_STUDIES, activeStudies);
                            startActivityForResult(studiesIntent, STUDIES_REQUEST_CODE);
                        }
                    });
                }
            });
        }
    }

    public void startChartOptionsActivity(View view) {
        Intent chartOptionsIntent = new Intent(this, ChartOptions.class);
        chartOptionsIntent.putExtra("chartStyle", chartStyle);
        chartOptionsIntent.putExtra("logScale", logScale);
        startActivityForResult(chartOptionsIntent, CHART_OPTIONS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(DRAW_REQUEST_CODE == requestCode) {
            if(RESULT_OK == resultCode) {
                if(data.getStringExtra("drawingTool") != null) {
                    drawingTool = data.getStringExtra("drawingTool");
                    activateDrawingTool(drawingTool);
                    drawingToolbar.setVisibility(View.VISIBLE);
                } else if(data.getBooleanExtra("clearAllDrawings", false)) {
                    fillColorPalette.dismiss();
                    lineColorPalette.dismiss();
                    lineTypePalette.dismiss();
                    drawingToolbar.setVisibility(View.GONE);
                    chartIQ.clearDrawing();
                }
            }
        } else if(CHART_OPTIONS_REQUEST_CODE == requestCode) {
            if(RESULT_OK == resultCode) {
                chartStyle = data.getStringExtra("chartStyle");
                switch (chartStyle) {
                    case "Heikin Ashi":
                        chartIQ.setAggregationType("heikinashi");
                        break;
                    case "Kagi":
                        chartIQ.setAggregationType("kagi");
                        break;
                    case "Renko":
                        chartIQ.setAggregationType("renko");
                        break;
                    case "Range Bars":
                        chartIQ.setAggregationType("rangebars");
                        break;
                    case "Point & Figure":
                        chartIQ.setAggregationType("pandf");
                        break;
                    default:
                        chartIQ.setChartType(chartStyle.toLowerCase().replace(" ", "_"));
                }
                logScale = data.getBooleanExtra("logScale", false);
                chartIQ.setChartScale(logScale ? "log" : "linear");
            }
        } else if(STUDIES_REQUEST_CODE == requestCode) {
            if(RESULT_OK == resultCode) {
                for (Study activeStudy : activeStudies) {
                    chartIQ.removeStudy(activeStudy.shortName);
                }
                activeStudies = (ArrayList<Study>) data.getSerializableExtra(ACTIVE_STUDIES);
                for (Study activeStudy : activeStudies) {
                    chartIQ.addStudy(activeStudy, true);
                }
            }
        } else if(requestCode == SEARCH_REQUEST_CODE) {
            if(data != null) {
                chartIQ.addComparison(data.getExtras().getString("symbol"), "Green", true);
                searchsymbol = data.getExtras().getString("symbol");
                searchtoken = data.getExtras().getString("Token");
                isFromsearch = true;
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void activateDrawingTool(String drawingTool) {
        drawingToolName.setText(drawingTool);
        drawingToolbar.setVisibility(View.VISIBLE);
        switch (drawingTool) {
            case "Annotation":
                chartIQ.enableDrawing("annotation");
                break;
            case "Channel":
                chartIQ.enableDrawing("channel");
                break;
            case "Doodle":
                chartIQ.enableDrawing("freeform");
                break;
            case "Ellipse":
                chartIQ.enableDrawing("ellipse");
                break;
            case "Fib-arc":
                chartIQ.enableDrawing("fibarc");
                break;
            case "Fib-fan":
                chartIQ.enableDrawing("fibfan");
                break;
            case "Fib-retrace":
                chartIQ.enableDrawing("fibonacci");
                break;
            case "Fib-timezone":
                chartIQ.enableDrawing("fibtimezone");
                break;
            case "Gartley":
                chartIQ.enableDrawing("gartley");
                break;
            case "Horizontal line":
                chartIQ.enableDrawing("horizontal");
                break;
            case "Line":
                chartIQ.enableDrawing("line");
                break;
            case "Pitch fork":
                chartIQ.enableDrawing("pitchfork");
                break;
            case "Ray":
                chartIQ.enableDrawing("ray");
                break;
            case "Rectangle":
                chartIQ.enableDrawing("rectangle");
                break;
            case "Segment":
                chartIQ.enableDrawing("segment");
                break;
            case "Vertical line":
                chartIQ.enableDrawing("vertical");
                break;
        }
    }

    public void changeFillColor(View view) {
        fillColorPalette.dismiss();
        fill.getChildAt(1).setBackgroundColor(Color.parseColor(String.valueOf(view.getTag())));
        chartIQ.setDrawingParameter("\"fillColor\"", "\"" + String.valueOf(view.getTag()) + "\"");
    }

    public void changeLineColor(View view) {
        lineColorPalette.dismiss();
        line.getChildAt(1).setBackgroundColor(Color.parseColor(String.valueOf(view.getTag())));
        chartIQ.setDrawingParameter("\"currentColor\"", "\"" + String.valueOf(view.getTag()) + "\"");
    }

    public void changeLineType(View view) {
        lineTypePalette.dismiss();
        lineType.setImageDrawable(((ImageView) view).getDrawable());
        String pattern = String.valueOf(view.getTag());
        chartIQ.setDrawingParameter("\"pattern\"", "\"" + pattern.substring(0, pattern.length() - 1) + "\"");
        chartIQ.setDrawingParameter("\"lineWidth\"", pattern.substring(pattern.length() - 1));
    }

    // set field to true if talkback mode needs to announce the value
    private void createTalkbackFields() {
        talkbackFields.put(ChartIQ.QuoteFields.DATE.value(), true);
        talkbackFields.put(ChartIQ.QuoteFields.CLOSE.value(), true);
        talkbackFields.put(ChartIQ.QuoteFields.OPEN.value(), true);
        talkbackFields.put(ChartIQ.QuoteFields.HIGH.value(), true);
        talkbackFields.put(ChartIQ.QuoteFields.LOW.value(), true);
        talkbackFields.put(ChartIQ.QuoteFields.VOLUME.value(), true);

        chartIQ.setTalkbackFields(talkbackFields);
    }

    class Item {
        String type;
        String title;
        int id;
        boolean selected;

        public Item(String type, String title, int id) {
            this.type = type;
            this.title = title;
            this.id = id;
        }

        public Item(String type, String title, int id, boolean selected) {
            this(type, title, id);
            this.selected = selected;
        }
    }

    class IntervalsAdapter implements ListAdapter {
        private List<Item> items;

        public IntervalsAdapter(List<Item> items) {
            this.items = items;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return "item".equals(items.get(position).type);
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return items.get(position).id;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item = items.get(position);
            View view;
            if("divider".equals(item.type)) {
                view = getLayoutInflater().inflate(R.layout.menu_divider, parent, false);
            } else if("item".equals(item.type)) {
                view = getLayoutInflater().inflate(R.layout.menu_item, parent, false);
                ((TextView) view).setText(items.get(position).title);
                if(item.selected) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.bajaj_blue));
                }
            } else {
                view = getLayoutInflater().inflate(R.layout.menu_header, parent, false);
                ((TextView) view).setText(items.get(position).title);
            }
            return view;
        }

        @Override
        public int getItemViewType(int position) {
            Item item = items.get(position);
            if("item".equals(item.type))
                return 1;

            if("divider".equals(item.type))
                return 2;

            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public boolean isEmpty() {
            return items.isEmpty();
        }
    }

    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {

    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {

    }

    @Override
    public void handleInvalidSession(String msg, int actionCode, JSONResponse jsonResponse) {

    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {

    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {

    }


   /* private void sendQuotesRequest(String token, String assetType) {
        showProgress();

        if(GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {

            MarketsSingleScripRequest.sendRequest(AccountDetails.getDeviceID(this), token, assetType, AccountDetails.getClientCode(this), this, serviceResponseHandler);

        } else {

            MarketsSingleScripRequest.sendRequest(AccountDetails.getUsername(this), token, assetType, AccountDetails.getClientCode(this), this, serviceResponseHandler);

        }
    }*/

    @Override
    public void process(Object response) {

    }


    private String getInterval() {

        String interval = "1";

        if(checkedId == R.id.btn1M) {

         /*      if(isDailybasis) {

             interval = "day";

            } else {*/
            interval = "1";
//            selectedChartduration = "1";

//            }

        } else if(checkedId == R.id.btn5M) {

        /*    if(isDailybasis) {

                interval = "week";

            } else {*/
//            chartIQ.setPeriodicity(1, "5", "minute");
            interval = "5";
//            selectedChartduration = "5";

//            }

        } else if(checkedId == R.id.btn10M) {

          /*  if(isDailybasis) {

                interval = "month";


            } else {*/
//            chartIQ.setPeriodicity(1, "30", "minute");
            interval = "30";
//            selectedChartduration = "30";
//            }

        } else if(checkedId == R.id.btn15M) {

     /*       if(isDailybasis) {

                interval = "year";

            } else {*/
//            chartIQ.setPeriodicity(1, "60", "minute");
            interval = "60";
//            }

        } else if(checkedId == R.id.btn20M) {
//            chartIQ.setPeriodicity(1, "day", "minute");
            interval = "day";
//            selectedChartduration = "day";
        } else if(checkedId == R.id.btn1H) {
//            chartIQ.setPeriodicity(1, "week", "minute");
            interval = "week";
//            selectedChartduration = "week";
        } else if(checkedId == R.id.btn30D) {
//            chartIQ.setPeriodicity(1, "month", "minute");
            interval = "month";
//            selectedChartduration = "month";
        } else if(checkedId == R.id.btn1y) {
//            selectedChartduration = "year";
//            chartIQ.setPeriodicity(1, "year", "minute");
            interval = "year";
        }

        return interval;
    }


    private void changeIntraDayInterval(String interval) {

        //if(chartsLayout != null) chartsLayout.removeAllViews();
//        showProgress();

//      LastInterval = interval;
        LastInterval = getInterval();


        if(LastStoredResponseDate.length() > 0) {

            if(isDailybasis) {
                LastCurrentDate = getYesterdayDate(LastStoredResponseDate);
            } else {
                LastCurrentDate = getYesterdayDate(DateTimeFormatter.getDateFromTimeStamp(LastStoredResponseDate, "yyyyMMdd", "nse"));
            }

        }


        if(isDailybasis) {

            if(checkedId == R.id.btn1M || checkedId == R.id.btn5M || checkedId == R.id.btn10M || checkedId == R.id.btn15M || checkedId == R.id.btn20M) {

                DailyChartRequest.sendRequestForDailychart(token, getInterval(), LastCurrentDate, "0", this, serviceResponseHandler);

            } else {
                DailyChartRequest.sendRequestForWeeklychart(token, getInterval(), LastCurrentDate, "0", this, serviceResponseHandler);
            }

        } else {

            DailyChartRequest.sendRequestChart(token, getInterval(), LastCurrentDate, "0", "1", this, serviceResponseHandler);
        }
    }

    private String getYesterdayDate(String today) {
        String yesterdayAsString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(today);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
            yesterdayAsString = dateFormat.format(calendar.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return yesterdayAsString;

    }


    @Override
    public void handleResponse(Object response) {
//        super.handleResponse(response);
        if(response instanceof JSONResponse) {
            JSONResponse jsonResponse = (JSONResponse) response;

            if(MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SINGLEQUOTE_SVC_NAME.equals(jsonResponse.getServiceName())) {

                try {
                    quoteResponse = (MarketsSingleScripResponse) jsonResponse.getResponse();

                    if(((Integer.valueOf(quoteResponse.getToken()) >= 502000000) && (Integer.valueOf(quoteResponse.getToken()) <= 502999999)) || ((Integer.valueOf(quoteResponse.getToken()) >= 1302000000) && (Integer.valueOf(quoteResponse.getToken()) <= 1302999999))) {
                        ltpText.setText(String.format("%.4f", Double.parseDouble(quoteResponse.getLast())));

                    } else {
                        ltpText.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLast())));
                    }

//                    ltpText.setText(String.format("%.2f", Double.parseDouble(quoteResponse.getLast())));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(CHART_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INTRADAY_CHART_SVC_NAME.equals(jsonResponse.getServiceName())) {
                try {
                    JSONArray array = null;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
                    try {
//                        hideProgress();

                        array = jsonResponse.getResponseData().getJSONArray("data");
//                        chartIQ.setEngineProperty("YAxis.prototype.decimalPlaces", 2);
//                        if(!iscomparision) {
                        if(array.length() > 2) {
                            JSONObject cobj = array.getJSONObject(array.length() - 1);
                            JSONObject d = array.getJSONObject(array.length() - 2);
                            Date date1 = simpleDateFormat.parse(cobj.get("DT").toString());
                            date1.getTime();
                            DateFormat time = new SimpleDateFormat("hh:mm:ss a");
                            System.out.println("Time: " + time.format(date1));
                            String lasttime = time.format(date1);

                            Date date2 = simpleDateFormat.parse(d.get("DT").toString());
                            date2.getTime();
                            DateFormat time2 = new SimpleDateFormat("hh:mm:ss a");
                            System.out.println("Time: " + time2.format(date2));
                            String lasttime2 = time2.format(date2);

                            int checkedIds = resolutionBar.getCheckedRadioButtonId();
                            int selected = 0;
                            String interval = "";
                            if(checkedIds == R.id.btn1M) {
                                selected = 1;
                                interval = "minute";
                            } else if(checkedIds == R.id.btn5M) {
                                selected = 5;
                                interval = "minute";
                            } else if(checkedIds == R.id.btn10M) {
                                selected = 30;
                                interval = "minute";
                            } else if(checkedIds == R.id.btn15M) {
                                selected = 60;
                                interval = "minute";
                            } else if(checkedIds == R.id.btn20M) {

                            } else if(checkedIds == R.id.btn1H) {

                            } else if(checkedIds == R.id.btn30D) {

                            } else if(checkedIds == R.id.btn1y) {

                            } else if(checkedIds == R.id.btnM) {
                                selected = 15;
                                interval = "minute";
                            } else if(checkedIds == R.id.btn45M) {
                                selected = 45;
                                interval = "minute";
                            }
                            long mills = date1.getTime() - date2.getTime();
                            int hours = (int) (mills / (1000 * 60 * 60));
                            int mins = (int) ((mills / (1000 * 60)) % 60);
                            if(interval.equalsIgnoreCase("minute")) {
                                if(mins != selected) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(date2);
                                    cal.add(Calendar.MINUTE, selected);

                                    String modifytime = simpleDateFormat.format(cal.getTime());


//                            JSONObject e = array.getJSONObject(array.length()-1);
                            /*e.remove("DT");
                            e.put("DT",modifytime);*/
                                    array.getJSONObject(array.length() - 1).remove("DT");
                                    array.getJSONObject(array.length() - 1).put("DT", modifytime);
                                }

                            }
                        }

//


                        data = new Gson().fromJson(array.toString(), OHLCChart[].class);
                        call.execute(data);


                        if(args.getExtras().getBoolean("iscurrency")) {

                            chartIQ.setYAxisDecimalPlace("4");
                        }else{

                            chartIQ.setYAxisDecimalPlace("2");
                        }


                        JSONObject cobj = array.getJSONObject(0);
                       double[] tot_volume = new double[array.length()];
                        String prevDate =DateTimeFormatter.getDateFromTimeStamp(cobj.getString("lDate"), "yyyy/MM/dd", "nse");


                        for(int i=0;i<array.length();i++){

                            JSONObject rec = array.getJSONObject(i);
                           /* int id = rec.getInt("id");
                            String loc = rec.getString("loc");*/
                            String currentDate = DateTimeFormatter.getDateFromTimeStamp(rec.getString("lDate"), "yyyy/MM/dd", "nse");
                            if(prevDate.equalsIgnoreCase(currentDate)){
                                total_vol = total_vol + Double.parseDouble(rec.getString("Volume"));
                                tot_volume[i] = total_vol;
                            }else{
                                prevDate = DateTimeFormatter.getDateFromTimeStamp(rec.getString("lDate"), "yyyy/MM/dd", "nse");
                                total_vol = 0 + Double.parseDouble(rec.getString("Volume"));
                                tot_volume[i] = total_vol;
                            }
                        }

                        /*}else{
                            data = new Gson().fromJson(array.toString(), OHLCChart[].class);
//                            call.execute(data);
                            chartIQ.pushUpdate(data);
                        }*/
                        if(isfirsttime) {
                            isfirsttime = false;

                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                            String currentDateTimeString = simpleDateFormat.format(new Date());

                            try {
                                JSONObject c = array.getJSONObject(array.length() - 1);
                                /* printTime = simpleDateFormat.parse(c.getString("DT"));*/
//                            printTime = simpleDateFormat.parse(currentDateTimeString);
                                lastHigh = c.getString("High");
                                lastLow = c.getString("Low");

                                lastOpen = c.getString("Open");
                                lastClose = c.getString("Close");
                                lastVolume = c.getString("Volume");
                                lastDT = simpleDateFormat.parse(c.getString("DT"));

                                printTime = simpleDateFormat.parse(c.getString("DT"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        chartLoaded = true;

                        //chartIQ.setChartProperty("xAxis.futureTicks", false);
                        //chartIQ.setChartProperty("allowScrollFuture ", false);
                        int selectedinteval = com.acumengroup.greekmain.util.Util.getPrefs(this).getInt("selected interval", 0);
                        if(selectedinteval == 0) {
                            chartIQ.setPeriodicity(1, "1", "minute");
                        }

                        if(!AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
                            chartIQ.setTheme("night");
                        }
                        if(iscrosshairon) {
                            chartIQ.enableCrosshairs();
                        } else {
                            chartIQ.disableCrosshairs();
                        }
//                        chartIQ.setEngineProperty("YAxis.prototype.decimalPlaces", 2);
                        String type = com.acumengroup.greekmain.util.Util.getPrefs(this).getString("selected charttype", "mountain");
                        String charttype = com.acumengroup.greekmain.util.Util.getPrefs(this).getString("charttype", "mountain");

                        if(type.equalsIgnoreCase("")) {
                            chartIQ.setChartType("mountain");
                            if (AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
                                searchtype.setBackground(getResources().getDrawable(R.color.whitetheambuyColor));
                            }else {
                                searchtype.setBackground(getResources().getDrawable(R.color.buyColor));
                            }                        } else {
                            chartIQ.setChartType(type);
                        }
                        if(charttype.equalsIgnoreCase("search")) {
                            if (AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
                                searchtype.setBackground(getResources().getDrawable(R.color.whitetheambuyColor));
                            }else {
                                searchtype.setBackground(getResources().getDrawable(R.color.buyColor));
                            }                            ohcltype.setBackground(getResources().getDrawable(R.drawable.button_boarder));
                            candletype.setBackground(getResources().getDrawable(R.drawable.button_boarder));
                            charttypes.setBackground(getResources().getDrawable(R.drawable.button_boarder));

                        } else if(charttype.equalsIgnoreCase("ohlc")) {
                            searchtype.setBackground(getResources().getDrawable(R.drawable.button_boarder));
                            if (AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
                                ohcltype.setBackground(getResources().getDrawable(R.color.whitetheambuyColor));
                            }else {
                                ohcltype.setBackground(getResources().getDrawable(R.color.buyColor));
                            }
                            candletype.setBackground(getResources().getDrawable(R.drawable.button_boarder));
                            charttypes.setBackground(getResources().getDrawable(R.drawable.button_boarder));

                        } else if(charttype.equalsIgnoreCase("candle")) {
                            if (AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
                                ohcltype.setBackground(getResources().getDrawable(R.color.whitetheambuyColor));
                            }else {
                                ohcltype.setBackground(getResources().getDrawable(R.color.buyColor));
                            }
                            searchtype.setBackground(getResources().getDrawable(R.drawable.button_boarder));
                            ohcltype.setBackground(getResources().getDrawable(R.drawable.button_boarder));
                            charttypes.setBackground(getResources().getDrawable(R.drawable.button_boarder));

                        } else if(charttype.equalsIgnoreCase("chart")) {
                            if (AccountDetails.getThemeFlag(MainActivity.this).equalsIgnoreCase("white")) {
                                charttypes.setBackground(getResources().getDrawable(R.color.whitetheambuyColor));
                            }else {
                                charttypes.setBackground(getResources().getDrawable(R.color.buyColor));
                            }
                            searchtype.setBackground(getResources().getDrawable(R.drawable.button_boarder));
                            ohcltype.setBackground(getResources().getDrawable(R.drawable.button_boarder));
                            candletype.setBackground(getResources().getDrawable(R.drawable.button_boarder));

                        }

//                        chartIQ.setChartType("mountain");
                        chartIQ.getActiveStudies().then(new Promise.Callback<Study[]>() {
                            @Override
                            public void call(Study[] array) {
                                if(array.length <= 0) {

                                    Study s = new Study();
                                    s.name = "Volume Chart";
                                    s.shortName = "volume";
                                    s.customRemoval = false;
                                    chartIQ.addStudy(s, true);

                                }


                            }
                        });
                        //chartLoaded = true;

                        /*int checkedIds = resolutionBar.getCheckedRadioButtonId();
                        if(checkedIds == R.id.btn1M) {
                            chartIQ.setPeriodicity(1, "1", "minute");
                        } else if(checkedIds == R.id.btn5M) {
                            chartIQ.setPeriodicity(1, "5", "minute");
                        } else if(checkedIds == R.id.btn10M) {
                            chartIQ.setPeriodicity(1, "30", "minute");
                        } else if(checkedIds == R.id.btn15M) {
                            chartIQ.setPeriodicity(1, "60", "minute");
                        } else if(checkedIds == R.id.btn20M) {
                            chartIQ.setPeriodicity(1, "day", "minute");
                        } else if(checkedIds == R.id.btn1H) {
                            chartIQ.setPeriodicity(1, "week", "minute");
                        } else if(checkedIds == R.id.btn30D) {
                            chartIQ.setPeriodicity(1, "month", "minute");
                        } else if(checkedIds == R.id.btn1y) {
                            chartIQ.setPeriodicity(1, "year", "minute");
                        }else if(checkedIds == R.id.btnM) {
                            chartIQ.setPeriodicity(1, "15", "minute");
                        }else if(checkedIds == R.id.btn45M) {
                            chartIQ.setPeriodicity(1, "45", "minute");
                        }*/

                        String interval = "1";


                        chartIQ.setVisibility(View.VISIBLE);
                        blankView.setVisibility(View.GONE);
                        setInitialOHLCData();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onPause() {

//        chartIQ.clearChart();


        super.onPause();
    }

    @Override
    protected void onResume() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }

        super.onResume();
    }


    private void sendQuotesRequest(String token, String assetType) {
//        showProgress();

        if(GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {

            MarketsSingleScripRequest.sendRequest(AccountDetails.getDeviceID(this), token, assetType, AccountDetails.getClientCode(this), this, serviceResponseHandler);

        } else {

            MarketsSingleScripRequest.sendRequest(AccountDetails.getUsername(this), token, assetType, AccountDetails.getClientCode(this), this, serviceResponseHandler);

        }
    }


    private String getExchangeFromToken(String token) {
        /*int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NSEEX";
        } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "NSE";
        } else {
            return "NSE";
        }*/
        //  return "";

        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NCDEX";
        } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "MCX";
        } else if ((tokenInt >= 1903000000) && (tokenInt <= 1903999999)) {
            return "NSECOMM";
        } else if ((tokenInt >= 2003000000) && (tokenInt <= 2003999999)) {
            return "BSECOMM";
        } else if ((tokenInt >= 502000000) && (tokenInt <= 502999999)) {
            return "NSECURR";
        } else if ((tokenInt >= 1302000000) && (tokenInt <= 1302999999)) {
            return "BSECURR";
        } else {
            return "BSE";
        }
    }


}