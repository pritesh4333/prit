package com.acumengroup.mobile;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PictureInPictureParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import com.acumengroup.mobile.messaging.NotifyRegistrationIntentService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.acumengroup.greekmain.core.model.MarketStatusPostResponse;
import com.acumengroup.greekmain.core.model.heartbeat.HeartBeatResponse;
import com.acumengroup.greekmain.core.model.marketssinglescrip.MarketsSingleScripRequest;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.GetUserwatchlist;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.PortfolioGetUserWatchListResponse;
import com.acumengroup.greekmain.core.model.portfoliogetuserwatchlist.SymbolList;
import com.acumengroup.greekmain.core.network.EDISHoldingInfoResponse;
import com.acumengroup.greekmain.core.network.EDISHoldingInfoResponse1;
import com.acumengroup.mobile.BottomTabScreens.CancelledTabFragment;
import com.acumengroup.mobile.BottomTabScreens.ExecutedTabFragment;
import com.acumengroup.mobile.BottomTabScreens.PendingTabFragment;
import com.acumengroup.mobile.BottomTabScreens.RejectedTabFragment;
import com.acumengroup.mobile.BottomTabScreens.NewsTabFragment;
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.view.SFScanFragment;
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.view.StrategyBuildUpFragment;
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.view.StrategyDataListFragment;
import com.acumengroup.mobile.login.LoginPasswordFragment;
import com.acumengroup.mobile.login.TransPasswordFragment;
import com.acumengroup.mobile.model.AuthTransectionModel;
import com.acumengroup.mobile.model.CDSLReturnResponse;
import com.acumengroup.mobile.model.SendAuthorizationResponse;
import com.acumengroup.mobile.reports.EdisDashboardReport;
import com.acumengroup.mobile.reports.EdisMarginPledgeReport;
import com.acumengroup.mobile.reports.EdisTransactionDetails;
import com.acumengroup.mobile.reports.FundTransfer_payout_fragment;
import com.acumengroup.mobile.reports.FundTransfer_tabs_Fragment;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.edittext.GreekEditText;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

//import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.constants.LabelConfig;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.MarketStatusPostRequest;
import com.acumengroup.greekmain.core.model.bankdetail.KycDetailRequest;
import com.acumengroup.greekmain.core.model.bankdetail.KycDetailResponse;
import com.acumengroup.greekmain.core.model.marketsindianindices.IndianIndice;
import com.acumengroup.greekmain.core.model.marketsindianindices.MarketsIndianIndicesResponse;
import com.acumengroup.greekmain.core.model.portfoliotrending.AllowedProduct;
import com.acumengroup.greekmain.core.model.portfoliotrending.AllowedProductRequest;
import com.acumengroup.greekmain.core.model.portfoliotrending.AllowedProductResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.MarketStatusResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.AdminMessagesResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerConnectionStatusResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerOrderConfirmationResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerOrderRejectionResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerOrderSubmitStatus;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerRmsRejectionResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerTradeResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerTriggerResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.UserDetailsModifiedResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.UserLoginValidationResponse;
import com.acumengroup.greekmain.core.network.AlertExecutedStreamingResponse;
import com.acumengroup.greekmain.core.network.GuestLoginOrderStreamingResponse;
import com.acumengroup.greekmain.core.network.GuestLoginStreamingResponse;
import com.acumengroup.greekmain.core.network.HeartBeatOrderResponse;
import com.acumengroup.greekmain.core.network.HeartBeatStreamingResponse;
import com.acumengroup.greekmain.core.network.IrisRejection;
import com.acumengroup.greekmain.core.network.KillSwitchResponse;
import com.acumengroup.greekmain.core.network.LicenseResponse;
import com.acumengroup.greekmain.core.network.LoggedInClientResponse;
import com.acumengroup.greekmain.core.network.NetworkChangeReceiver;
import com.acumengroup.greekmain.core.network.OrderStreamingAuthResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.network.StreamingAuthResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.network.TCPConnectionHandler;
import com.acumengroup.greekmain.core.network.TCPOrderConnectionHandler;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.greekmain.core.quickaction.GreekPopupWindow;
import com.acumengroup.mobile.BottomTabScreens.HoldingBottomSheetFragment;
import com.acumengroup.mobile.BottomTabScreens.MarketBottomFragment;
import com.acumengroup.mobile.BottomTabScreens.NetPositionBottomSheetFragment;
import com.acumengroup.mobile.BottomTabScreens.OrderBottomFragment;
import com.acumengroup.mobile.BottomTabScreens.OrderPreviewBottomSheetFragment;
import com.acumengroup.mobile.BottomTabScreens.PortfolioBottomFragment;
import com.acumengroup.mobile.ChatMessage.ChatMessageFragment;
import com.acumengroup.mobile.Dashboard.BottomNavigationViewHelper;
import com.acumengroup.mobile.Dashboard.DashBoardFragment;
import com.acumengroup.mobile.MutualFund.MFDetailsFragment;
import com.acumengroup.mobile.MutualFund.MFundDashboard;
import com.acumengroup.mobile.MutualFund.MarshMallowPermission;
import com.acumengroup.mobile.MutualFund.UploadAadharDetails;
import com.acumengroup.mobile.MutualFund.UploadBankDetails;
import com.acumengroup.mobile.MutualFund.UploadChequeDetails;
import com.acumengroup.mobile.actionbar.GreekActionBar;
import com.acumengroup.mobile.init.SplashActivity;
import com.acumengroup.mobile.login.AboutActivity;
import com.acumengroup.mobile.login.ChangePasswordFragment;
import com.acumengroup.mobile.login.LASFragment;
import com.acumengroup.mobile.login.LasStepsFormFragment;
import com.acumengroup.mobile.login.LoginActivity;
import com.acumengroup.mobile.login.UserCreationPanDetails;
import com.acumengroup.mobile.login.UserProfile;
import com.acumengroup.mobile.markets.ChartsFragment;
import com.acumengroup.mobile.markets.ContractInformationFragment;
import com.acumengroup.mobile.markets.IndicesStockFragment;
import com.acumengroup.mobile.markets.MarketDepthFragment;
import com.acumengroup.mobile.markets.MarketsFragment;
import com.acumengroup.mobile.markets.NewsFragmentPager;
import com.acumengroup.mobile.markets.PdfActivity;
import com.acumengroup.mobile.markets.QuotesFragment;
import com.acumengroup.mobile.menu.DrawerMenu;
import com.acumengroup.mobile.menu.MenuAdapter;
import com.acumengroup.mobile.menu.MenuGetter;
import com.acumengroup.mobile.messaging.AlertFragment;

import com.acumengroup.mobile.messaging.NotificationsFragment;
import com.acumengroup.mobile.messaging.QuickstartPreferences;
import com.acumengroup.mobile.portfolio.EditWatchlistFragment;
import com.acumengroup.mobile.portfolio.LastVisitedFragment;
import com.acumengroup.mobile.portfolio.MFWatchlistFragment;
import com.acumengroup.mobile.portfolio.PortfolioSectionFragment;
import com.acumengroup.mobile.portfolio.WatchListFragment;
import com.acumengroup.mobile.reports.BankDetailsScreenFragment;
import com.acumengroup.mobile.reports.CumulativePositionFragment;
import com.acumengroup.mobile.reports.DematHoldingFragment;
import com.acumengroup.mobile.reports.DematHoldingFragmentNew;
import com.acumengroup.mobile.reports.FiiDiiActivitiesTabsFragment;
import com.acumengroup.mobile.reports.FundDisplayFragment;
import com.acumengroup.mobile.reports.FundTransferDetailsFragment;
import com.acumengroup.mobile.reports.FundTransferFragment;
import com.acumengroup.mobile.reports.HoldingReportFragment;
import com.acumengroup.mobile.reports.MFOrderBookDetailsFragment;
import com.acumengroup.mobile.reports.MFOrderBookFragment;
import com.acumengroup.mobile.reports.MarginFragment;
import com.acumengroup.mobile.reports.NetPositionFragment;
import com.acumengroup.mobile.reports.NewsDisplayFragment;
import com.acumengroup.mobile.reports.OpenPositionFragment;
import com.acumengroup.mobile.reports.OrderBookDetailsFragment;
import com.acumengroup.mobile.reports.OrderBookFragment;
import com.acumengroup.mobile.reports.RecommDisplayFragment;
import com.acumengroup.mobile.reports.TradeBookDetailsFragment;
import com.acumengroup.mobile.reports.TradeBookFragment;
import com.acumengroup.mobile.service.HeartBeatService;
import com.acumengroup.mobile.settings.AppSettingFragment;
import com.acumengroup.mobile.symbolsearch.ChartingFragment;
import com.acumengroup.mobile.symbolsearch.MutualFundGetQuoteFragment;
import com.acumengroup.mobile.symbolsearch.SymbolSearchFragment;
import com.acumengroup.mobile.trade.AllBankDetailsFragment;
import com.acumengroup.mobile.trade.BankDetailsMandateFragment;
import com.acumengroup.mobile.trade.FilePath;
import com.acumengroup.mobile.trade.KYCUploadPhoto;
import com.acumengroup.mobile.trade.MutualFundFragmentNew;
import com.acumengroup.mobile.trade.MutualFundSTPFragment;
import com.acumengroup.mobile.trade.MutualFundSWPFragment;
import com.acumengroup.mobile.trade.MutualFundSipFragment;
import com.acumengroup.mobile.trade.MutualFundSipSummary;
import com.acumengroup.mobile.trade.MutualFundTradeFragment;
import com.acumengroup.mobile.trade.MutualFundTradePreview;
import com.acumengroup.mobile.trade.MyAccountPersonalDetails;
import com.acumengroup.mobile.trade.PanDetails;
import com.acumengroup.mobile.trade.PlaceOrderBottomSheetFragment;
import com.acumengroup.mobile.trade.SingleUploadBroadcastReciever;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.mobile.trade.TradePreviewFragment;
import com.acumengroup.mobile.trade.UploadIpvp;
import com.acumengroup.mobile.trade.UploadSignature;
import com.acumengroup.mobile.trade.UploadpanDetails;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.GreekDialog.Action;
import com.acumengroup.ui.GreekDialog.DialogListener;
import com.acumengroup.ui.UIOperations;
import com.acumengroup.ui.menu.ScreenDetails;
import com.acumengroup.ui.menu.SubMenu;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;
import com.acumengroup.greekmain.util.logger.GreekLog;
import com.acumengroup.greekmain.util.operation.StringStuff;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;

import de.greenrobot.event.EventBus;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static com.acumengroup.mobile.GreekBaseFragment.getPath;
import static com.acumengroup.mobile.login.UserProfile.getBitmapofPhoto;
import static com.acumengroup.mobile.login.UserProfile.onCapturePhotoResult;
import static com.acumengroup.mobile.login.UserProfile.onSelectPhotoFromGalleryResult;
import static com.acumengroup.mobile.portfolio.WatchListFragment.defaultGroupName;
import static com.acumengroup.mobile.portfolio.WatchListFragment.hashMapWatchListData;
import static com.acumengroup.mobile.trade.KYCUploadPhoto.CAMERA_REQUEST;
import static com.acumengroup.mobile.trade.KYCUploadPhoto.GALLERY_PICTURE;
import static com.acumengroup.mobile.trade.UploadIpvp.OnVideoCaptureResult;
import static com.acumengroup.mobile.trade.UploadIpvp.VIDEO_CAPTURED;

public class GreekBaseActivity extends AppCompatActivity implements GreekUIServiceHandler, GreekConstants, SingleUploadBroadcastReciever.Delegate {
    private static String NIFTY_TOKEN = "Nifty 50", SENSEX_TOKEN = "SENSEX", GOLD_TOKEN = "GOLD", CRUDE_OIL_TOKEN = "CRUDE_OIL";
    public static USER USER_TYPE = USER.OPENUSER;// 0-Customer, 1-Guest, 2-Open
    private final Hashtable<String, ArrayList<String>> streamingSymbolList = new Hashtable<>();
    private final Hashtable<String, ArrayList<String>> streamingMarketSymbolList = new Hashtable<>();
    private final HashMap<String, String> globalTitles = new HashMap<>();
    private int clickedPos = -1;
    public static final int CDSLREQUESTCODE = 4;
    private ExpandableListView menuList;
    private String currentPage = "";
    private MenuAdapter menuAdapter;
    private GreekActionBar actionBar;
    public static String GREEK="";

    private RelativeLayout progressLayout, apptickerLayout, apptickerLayout1, screen_title;
    public ServiceResponseHandler serviceResponseHandler;
    private StreamingController streamingController;
    private TCPOrderConnectionHandler tcpOrderConnectionHandler;
    private TCPConnectionHandler tcpConnectionHandler;
    private DrawerLayout drawerLayout;
    private FrameLayout pipLinearLayput;
    private MarketsIndianIndicesResponse indianIndicesResponse = null;
    public static Bitmap bitmap = null;
    private static File destination;
    private final SingleUploadBroadcastReciever uploadReceiver = new SingleUploadBroadcastReciever();
    private GreekTextView niftyTxt, niftyValueTxt, sensextxt, sensexValueTxt, firstsymbolTxt, firstvalueTxt, secondSymbolTxt, secondvalueTxt, thirdsymbolTxt, thrdvalueTxt;
    private GreekTextView niftyChngTxt, sensexChngTxt, firstChngTxt, secondChnagTxt, thrdChngTxt;
    private ArrayList<String> streamingList;
    private String cdslValidationMassage = "Enter valid Qty to Authorize";
    private final OnClickListener menuslide = new OnClickListener() {

        @Override
        public void onClick(View v) {
            openMenuDrawer();
        }
    };
    private String userTypeValue = "OPEN";
    private int lastExpandedPosition = -1;
    private BroadcastReceiver mRegistrationBroadcastReceiver, networkBroadcastReciever;
    private StreamingController streamController = new StreamingController();
    private OrderStreamingController orderStreamingController;
    private NetworkChangeReceiver networkChangeReceiver;
    private GreekPopupWindow menuPopupWindow;
    private final ArrayList<String> visibleSymbolTable = new ArrayList<>();
    private GreekTextView view1_eq, view2_eq, view1_fno, view2_fno, view1_cd, view2_cd, view1_com, view2_com;
    View layout;
    private String nse_eq = "red", bse_eq = "red", nse_fno = "red", bse_fno = "red", nse_cd = "red", bse_cd = "red", mcx_com = "red", ncdex_com = "red";
    private boolean cdslValidation = true, eq, bseeq = false, fno, bsefno = false, cd, bsecd = false;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar, snackbarApollo, snackbarIris, snackbarIrisApolloOn, snackbarIrisApolloOff, snackbarApolloOn, snackbarIrisOn;
    ViewFlipper viewFlipper, viewFlipper1;
    String orderTime;
    private static CountDownTimer countDownTimer;
    private ArrayList<EDISHoldingInfoResponse1.Response.Data.StockDetail> stockDetailArrayList;
    LinearLayout marketValuesLayout;

    LinearLayout dashboardlayout;
    private BottomNavigationView bottomNavigation;
    private int mMenuId;
    private boolean NeedOfDash = false;
    private Bundle args;
    public static KycDetailResponse kycDetailResponse;
    private UserLoginValidationResponse loginResponse = null;
    private MovableFloatingActionButton movablefab;
    private BottomSheetBehavior sheetBehavior;
    private NestedScrollView bottom_sheet;
    TelephonyManager mTelephonyManager;
    MyPhoneStateListener mPhoneStatelistener;
    int mSignalStrength = 0;
    private ImageView mImageView;
    private ViewGroup mRrootLayout;
    ArrayList<AuthTransectionModel.AgeingTransData> authorizeTransDataList;
    int windowwidth;
    int windowheight;
    public String exchangeToken;
    private ArrayList<AuthTransectionModel.AgeingTransData> edisHoldingdatalist;
    private RecyclerView sell_transection_list;
    private GreekTextView empty_view;
    List<ScreenDetails> groupList = null;

    private int _xDelta;
    private int _yDelta;
    private GreekButton prc_to_cdsl;
    private ArrayList<String> checkedÌlist;


    //Bottom TABS Navigation
    public void checkedBottomTAB(MenuItem item) {
        mMenuId = item.getItemId();

        final Menu menu = bottomNavigation.getMenu();

        for (int i = 0; i < menu.size(); i++) {

            MenuItem menuItem = bottomNavigation.getMenu().getItem(i);

            if (menuItem.getItemId() == item.getItemId()) {

                menu.getItem(i).setCheckable(true);

            } else {

                menu.getItem(i).setCheckable(false);
            }

        }
    }

    public void UncheckedBottomTAB() {
        final Menu menu = bottomNavigation.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setCheckable(false);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
            checkedBottomTAB(item);

            int itemId = item.getItemId();
            if (itemId == R.id.market) {
                performMenuAction(LabelConfig.GREEK_MENU_BOTTOM_MARKET_TXT);
                marketValuesLayout.setVisibility(View.GONE);
                apptickerLayout.setVisibility(View.VISIBLE);
                dashboardlayout.setVisibility(View.VISIBLE);
                hideAppTitle();
                screen_title.setVisibility(View.GONE);
                editor.putString("Last click", "Market");
                editor.commit();

                return true;
            } else if (itemId == R.id.my_portfolio) {
                if (GreekBaseActivity.USER_TYPE != USER.OPENUSER) {

                    performMenuAction(LabelConfig.GREEK_MENU_BOTTOM_PORTFOLIO_TXT);
                    marketValuesLayout.setVisibility(View.GONE);
                    apptickerLayout.setVisibility(View.VISIBLE);
                    dashboardlayout.setVisibility(View.VISIBLE);
                    hideAppTitle();
                    screen_title.setVisibility(View.GONE);
                    editor.putString("Last click", "Portfolio");
                    editor.commit();
                    return true;
                } else if (GreekBaseActivity.USER_TYPE == USER.OPENUSER) {

                    GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Login to enjoy the services.", "OK", false, new DialogListener() {

                        @Override
                        public void alertDialogAction(Action action, Object... data) {

                        }
                    });

                }

                return false;
            } else if (itemId == R.id.my_watchlist) {
                AccountDetails.portfolio = false;
                AccountDetails.watchlist = true;
                AccountDetails.lastvisited = false;
                NeedOfDash = true;
                performMenuAction(LabelConfig.GREEK_MENU_WATCHLIST_TXT);
                marketValuesLayout.setVisibility(View.GONE);
                apptickerLayout.setVisibility(View.VISIBLE);
                dashboardlayout.setVisibility(View.VISIBLE);
                hideAppTitle();
                screen_title.setVisibility(View.GONE);
                editor.putString("Last click", "watchlist");
                editor.commit();


                return true;
            } else if (itemId == R.id.order) {
                if (GreekBaseActivity.USER_TYPE != USER.OPENUSER) {
                    performMenuAction(LabelConfig.GREEK_MENU_BOTTOM_ORDER_TXT);
                    marketValuesLayout.setVisibility(View.GONE);
                    apptickerLayout.setVisibility(View.VISIBLE);
                    dashboardlayout.setVisibility(View.VISIBLE);
                    hideAppTitle();
                    screen_title.setVisibility(View.GONE);
                    editor.putString("Last click", "Order");
                    editor.commit();
                    return true;
                } else if (GreekBaseActivity.USER_TYPE == USER.OPENUSER) {

                    GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Login to enjoy the services.", "OK", false, new DialogListener() {

                        @Override
                        public void alertDialogAction(Action action, Object... data) {

                        }
                    });

                }

                return false;
            }
            return false;
        }
    };


    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (isInPictureInPictureMode) {
            pipLinearLayput.setVisibility(View.VISIBLE);
            drawerLayout.setVisibility(View.GONE);
            sendIndianIndicesRequestForUser();

        } else {
            drawerLayout.setVisibility(View.VISIBLE);
            pipLinearLayput.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        try {
            if (supportsPipMode() && !AccountDetails.getIsMainActivity()) {

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;

                //            int ht= Integer.valueOf((int) (height/1.5));

                try {
                    Rational rational = new Rational(width + 300, Integer.valueOf((int) (height / 1.5)));
                    //            Rational rational = new Rational(progressLayout.getWidth(), progressLayout.getHeight());
                    PictureInPictureParams.Builder builder = new PictureInPictureParams.Builder();
                    builder.setAspectRatio(rational).build();
                    setPictureInPictureParams(builder.build());

                    enterPictureInPictureMode(builder.build());
                    getMaxNumPictureInPictureActions();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean supportsPipMode() {

        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    private void sendIndianIndicesRequestForUser() {
        streamController.sendIndianIndicesRequesForCommodityCurrency(this, "equity", serviceResponseHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountDetails.setApollo_LoginCounter(0);
        AccountDetails.setIris_LoginCounter(0);

        String isthemeApplied = Util.getPrefs(this).getString("themeApplied", "");
        if (streamController != null && !AccountDetails.getIsbackmainActivity()) {
            if (!isthemeApplied.equalsIgnoreCase("true")) {
               /* streamController.sendStreamingLogoffRequest(this,
                        AccountDetails.getUsername(getApplicationContext()),
                        AccountDetails.getClientCode(this),
                        AccountDetails.getSessionId(getApplicationContext()),
                        AccountDetails.getToken(this), null, null);
                streamController = null;*/
            }

            Log.e("GreekActivity", "onDestroy====================>>>>");
        }
        if (orderStreamingController != null && !AccountDetails.getIsbackmainActivity()) {


            if (!isthemeApplied.equalsIgnoreCase("true")) {
                /*orderStreamingController.sendStreamingLogoffRequest(this,
                        AccountDetails.getUsername(this),
                        AccountDetails.getClientCode(this),
                        AccountDetails.getSessionId(getApplicationContext()),
                        AccountDetails.getToken(this));
                orderStreamingController = null;*/
            } else {
                SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                editor.putString("themeApplied", "false");
                editor.commit();
            }

            Log.e("GreekActivity", "onDestroy====================>>>>");

        } else {
            AccountDetails.setIsbackmainActivity(false);
        }
        AccountDetails.setIsThemeApplied(false);

    }

    @SuppressLint("RestrictedApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
            setTheme(R.style.GREEK_2);
        } else if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("black")) {
            setTheme(R.style.GREEK);
        }
        super.onCreate(savedInstanceState);

        String packagname =GreekBaseActivity.this.getPackageName();
        if (packagname.equalsIgnoreCase("com.vishwas.mobile")){
            GREEK="Vishwas";
        }else  if (packagname.equalsIgnoreCase("com.tog.mobile")){
            GREEK="Trade On Go";
        }else  if (packagname.equalsIgnoreCase("com.msfl.mobile")){
            GREEK="MSFL";
        }
        SharedPreferences.Editor editors = Util.getPrefs(getApplicationContext()).edit();
        editors.putString("AppName", GREEK);
        editors.commit();
        checkedÌlist = new ArrayList<>();
        streamingList = new ArrayList<>();

        Log.e("GreekBaseActivity", "OnCreate======Greek==================>>>>>>>>>>>>>");

        mPhoneStatelistener = new MyPhoneStateListener();
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mPhoneStatelistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, new IntentFilter(CONNECTIVITY_ACTION));

        //When app gets killed in Background========================================>>>>>>
        if (AccountDetails.getArachne_Port() == 0 && AccountDetails.getUsername(getApplicationContext()) == null) {
            Log.e("GreekBaseActivity", "logout========================>>>>>>>>>>>>>");
            Log.e("GreekBaseActivity", AccountDetails.getUsername(getApplicationContext()) + "" + AccountDetails.getLogin_user_type());

            Intent i = getIntent();
            int notVal = 0;
            if (i != null) {
                notVal = i.getIntExtra("isProceedFrom", 0);
            }
            Intent firstIntent = new Intent(this, SplashActivity.class);

            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            if (notVal == 1012) {
                firstIntent.putExtra("isProceedFrom", NAV_TO_NOTIFICATION_SCREEN);
            }
            startActivity(firstIntent);

            Log.e("ftTestingbyPass", AccountDetails.getFt_testing_bypass());
            // We are done, so finish this activity and get out now
            AccountDetails.setLastSelectedGroup("");
            finish();
            return;
        }

        if (AccountDetails.getLogin_user_type().equalsIgnoreCase("openuser")) {
            USER_TYPE = USER.OPENUSER;
        } else if (AccountDetails.getLogin_user_type().equalsIgnoreCase("mfcustomer")) {
            USER_TYPE = USER.MFCUSTOMER;
        } else if (AccountDetails.getLogin_user_type().equalsIgnoreCase("ibtcustomer")) {
            USER_TYPE = USER.IBTCUSTOMER;
        } else if (AccountDetails.getLogin_user_type().equalsIgnoreCase("customer")) {
            //mfcustomer,ibtcustomer both types users comes under this UserType===========================>>>>
            USER_TYPE = USER.CUSTOMER;
        }
        setContentView(R.layout.activity_commonbase);


        SharedPreferences.Editor editor = Util.getPrefs(this).edit();
        editor.remove("Last click");
        editor.commit();


        pipLinearLayput = findViewById(R.id.pipLayout);
        niftyTxt = findViewById(R.id.nifty_txt);
        niftyValueTxt = findViewById(R.id.nifty_val_txt);
        sensextxt = findViewById(R.id.sensex_txt);
        sensexValueTxt = findViewById(R.id.sensex_val_txt);
        firstsymbolTxt = findViewById(R.id.fsymbol_txt);
        firstvalueTxt = findViewById(R.id.fsymbol_val_txt);
        secondSymbolTxt = findViewById(R.id.secsymbol_txt);
        secondvalueTxt = findViewById(R.id.secsymbol_val_txt);
        thirdsymbolTxt = findViewById(R.id.thrdsymbol_txt);
        thrdvalueTxt = findViewById(R.id.thrdsymbol_val_txt);
        niftyChngTxt = findViewById(R.id.nifty_chng_txt);
        sensexChngTxt = findViewById(R.id.sensex_chng_txt);
        firstChngTxt = findViewById(R.id.fsymbol_chng_txt);
        secondChnagTxt = findViewById(R.id.secsymbol_chng_txt);
        thrdChngTxt = findViewById(R.id.thrdsymbol_chng_txt);

        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        niftyTxt.setTypeface(font, Typeface.NORMAL);
        niftyValueTxt.setTypeface(font, Typeface.NORMAL);
        sensextxt.setTypeface(font, Typeface.NORMAL);
        sensexValueTxt.setTypeface(font, Typeface.NORMAL);
        firstsymbolTxt.setTypeface(font, Typeface.NORMAL);
        firstvalueTxt.setTypeface(font, Typeface.NORMAL);
        secondSymbolTxt.setTypeface(font, Typeface.NORMAL);
        secondvalueTxt.setTypeface(font, Typeface.NORMAL);
        thirdsymbolTxt.setTypeface(font, Typeface.NORMAL);
        thrdvalueTxt.setTypeface(font, Typeface.NORMAL);
        niftyChngTxt.setTypeface(font, Typeface.NORMAL);
        sensexChngTxt.setTypeface(font, Typeface.NORMAL);
        firstChngTxt.setTypeface(font, Typeface.NORMAL);
        secondChnagTxt.setTypeface(font, Typeface.NORMAL);
        thrdChngTxt.setTypeface(font, Typeface.NORMAL);


        movablefab = findViewById(R.id.movablefab);
        drawerLayout = findViewById(R.id.drawerLay);
        menuList = findViewById(R.id.homeGridView);
        bottomNavigation = findViewById(R.id.navigation);
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        serviceResponseHandler = new ServiceResponseHandler(this, this);
        streamingController = new StreamingController(); //TODO PK
        orderStreamingController = new OrderStreamingController(); //TODO PK
        tcpOrderConnectionHandler = new TCPOrderConnectionHandler(); //TODO PK
        tcpConnectionHandler = new TCPConnectionHandler(); //TODO PK
        progressLayout = findViewById(R.id.customProgress);
        actionBar = findViewById(R.id.actionBarCommon);
        sendIndianIndicesRequestForUser();
        marketValuesLayout = findViewById(R.id.marketValues);
        apptickerLayout = findViewById(R.id.appTicker2);
        apptickerLayout1 = findViewById(R.id.appTicker);
        screen_title = findViewById(R.id.screen_title);
        dashboardlayout = findViewById(R.id.dashboardlayout);
        bottom_sheet = findViewById(R.id.bottom_sheet_layout);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigation.setSelectedItemId(R.id.market);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);
        snackbar = Snackbar.make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
        snackbarIris = Snackbar.make(coordinatorLayout, "Interactive Offline", Snackbar.LENGTH_INDEFINITE);
        snackbarApollo = Snackbar.make(coordinatorLayout, "Broadcast Offline", Snackbar.LENGTH_INDEFINITE);

        snackbarIrisApolloOff = Snackbar.make(coordinatorLayout, "Broadcast & Interactive both Offline", Snackbar.LENGTH_INDEFINITE);
        snackbarIrisApolloOn = Snackbar.make(coordinatorLayout, "Broadcast & Interactive both Online", Snackbar.LENGTH_INDEFINITE);
        snackbarIrisOn = Snackbar.make(coordinatorLayout, "Interactive Online", Snackbar.LENGTH_INDEFINITE);
        snackbarApolloOn = Snackbar.make(coordinatorLayout, "Broadcast Online", Snackbar.LENGTH_INDEFINITE);

        viewFlipper1 = findViewById(R.id.viewFlipper1);


        snackbarApolloOn.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your action method here
                snackbarApolloOn.dismiss();
            }
        });
        snackbarApolloOn.setActionTextColor(Color.RED);


        snackbarIrisOn.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your action method here
                snackbarIrisOn.dismiss();
            }
        });
        snackbarIrisOn.setActionTextColor(Color.RED);


        snackbarIrisApolloOn.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your action method here
                snackbarIrisApolloOn.dismiss();
            }
        });
        snackbarIrisApolloOn.setActionTextColor(Color.RED);


        snackbarIrisApolloOff.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your action method here
                snackbarIrisApolloOff.dismiss();
            }
        });

        snackbarIrisApolloOff.setActionTextColor(Color.RED);


        snackbar.setActionTextColor(Color.RED);

        snackbar.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your action method here
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(Color.RED);

        snackbarIris.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your action method here
                snackbarIris.dismiss();
            }
        });
        snackbarIris.setActionTextColor(Color.RED);

        snackbarApollo.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your action method here
                snackbarApollo.dismiss();
            }
        });
        snackbarApollo.setActionTextColor(Color.RED);


        if (AccountDetails.broadcastServerAuthenticated) {
            actionBar.setNiftyIcon("green");

        } else {
            actionBar.setNiftyIcon("red");
        }

        if (AccountDetails.orderServerAuthenticated) {
            actionBar.setSensexIcon("green");

        } else {
            actionBar.setSensexIcon("red");
        }

//        Edited by sushant to handle green market status on Guest login particularly
        if ("OPEN".equalsIgnoreCase(AccountDetails.getUsertype(getApplicationContext()))) {
            //if user is guest login then user allowed all market types========================>>>
            AccountDetails.allowedmarket_nse = true;
            AccountDetails.allowedmarket_nfo = true;
            AccountDetails.allowedmarket_ncd = true;
            AccountDetails.allowedmarket_ncdex = true;
            AccountDetails.allowedmarket_bse = true;
            AccountDetails.allowedmarket_bfo = true;
            AccountDetails.allowedmarket_bcd = true;
            AccountDetails.allowedmarket_mcx = true;
            AccountDetails.allowedmarket_nCOM = true;
            AccountDetails.allowedmarket_bCOM = true;
        }
        settingThemeAsset();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Check type of intent filter

                if (intent.getAction().equals(NotifyRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    //Registration success
                    String token = intent.getStringExtra("token");
                    SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                    editor.putString("GCMToken", token);
                    editor.commit();
                    //Toast.makeText(getApplication(),token,Toast.LENGTH_SHORT).show();
                } else if (intent.getAction().equals(NotifyRegistrationIntentService.REGISTRATION_ERROR)) {

                } else {
                    //ToDo define
                }
            }
        };
//
        //Check status of Google play service in device
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (ConnectionResult.SUCCESS != resultCode) {
            //Check type of error
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_SHORT).show();
                //So notification
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_SHORT).show();
            }
        } else {
            //Start service
            Intent itent = new Intent(this, NotifyRegistrationIntentService.class);
            startService(itent);
        }


        //Globally added on back stack changed listener.
        getSupportFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();
                if (manager != null) {
                    //Log.d("BackStackCountChange", String.valueOf(manager.getBackStackEntryCount()));
                    GreekBaseFragment currFrag = (GreekBaseFragment) manager.findFragmentById(R.id.activityFrameLayout);

                    currFrag.onFragmentResume();
                    if (currFrag instanceof MFWatchlistFragment) {
                        NeedOfDash = true;
                    }

                    if (currFrag instanceof SymbolSearchFragment) {

                        marketValuesLayout.setVisibility(View.GONE);
                        apptickerLayout.setVisibility(View.VISIBLE);
                        screen_title.setVisibility(View.GONE);
                        dashboardlayout.setVisibility(View.VISIBLE);

                    } else if (AccountDetails.currentFragment == NAV_TO_MARKET_HOME_SCREEN || AccountDetails.currentFragment == NAV_TO_MF_WATCHLIST_MF) {
                        //  screen_title.setVisibility(View.GONE);
                        marketValuesLayout.setVisibility(View.GONE);
                        apptickerLayout.setVisibility(View.VISIBLE);
                        dashboardlayout.setVisibility(View.VISIBLE);
                        hideAppTitle();
                        NeedOfDash = false;

                    } else if (!NeedOfDash) {

                        marketValuesLayout.setVisibility(View.GONE);
                        apptickerLayout.setVisibility(View.VISIBLE);
                        screen_title.setVisibility(View.GONE);
                        dashboardlayout.setVisibility(View.VISIBLE);


                    } else if (currFrag instanceof DashBoardFragment || currFrag instanceof MFundDashboard || currFrag instanceof MFDetailsFragment
                            || currFrag instanceof MFWatchlistFragment || currFrag instanceof MFWatchlistFragment
                            || currFrag instanceof PanDetails || currFrag instanceof MFOrderBookFragment || currFrag instanceof MFOrderBookDetailsFragment || currFrag instanceof MyAccountPersonalDetails || currFrag instanceof MutualFundGetQuoteFragment) {
                        marketValuesLayout.setVisibility(View.GONE);
                        apptickerLayout.setVisibility(View.VISIBLE);
                        dashboardlayout.setVisibility(View.VISIBLE);
                        hideAppTitle();
                    } else if (NeedOfDash) {

                        marketValuesLayout.setVisibility(View.GONE);
                        apptickerLayout.setVisibility(View.VISIBLE);
                        dashboardlayout.setVisibility(View.VISIBLE);
                        NeedOfDash = false;
                        hideAppTitle();


                    } else if (!(currFrag instanceof DashBoardFragment)) {
                        marketValuesLayout.setVisibility(View.GONE);
                        apptickerLayout.setVisibility(View.VISIBLE);
                        screen_title.setVisibility(View.GONE);
                        dashboardlayout.setVisibility(View.VISIBLE);
                    } else {

                        marketValuesLayout.setVisibility(View.GONE);
                        apptickerLayout.setVisibility(View.VISIBLE);
                        dashboardlayout.setVisibility(View.VISIBLE);
                        hideAppTitle();
                    }

                }
            }
        });

        actionBar.setHomeAction(menuslide);

        if (AccountDetails.broadcastServerAuthenticated) {
            actionBar.setNiftyIcon("green");

        } else {
            actionBar.setNiftyIcon("red");
        }

        if (AccountDetails.orderServerAuthenticated) {
            actionBar.setSensexIcon("green");

        } else {
            actionBar.setSensexIcon("red");
        }

        getProductDetails();
        sendMarketStatusRequest();


        actionBar.setPopUpEq(new OnClickListener() {
            @Override
            public void onClick(View view) {
                layout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.quick_two_actions, null);
                view1_eq = layout.findViewById(R.id.action_item1);
                view1_eq.setText("NSE");
                if (AccountDetails.allowedmarket_nse) {
                    view1_eq.setVisibility(View.VISIBLE);
                    if (nse_eq.equalsIgnoreCase("green"))
                        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                            view1_eq.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
                        } else {
                            view1_eq.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                        }
                    else if (nse_eq.equalsIgnoreCase("red"))
                        view1_eq.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                    else if (nse_eq.equalsIgnoreCase("pink"))
                        view1_eq.setBackgroundColor(getResources().getColor(R.color.pink_bg));
                    else if (nse_eq.equalsIgnoreCase("blue"))
                        view1_eq.setBackgroundColor(getResources().getColor(R.color.blue_bg));
                    else if (nse_eq.equalsIgnoreCase("yellow"))
                        view1_eq.setBackgroundColor(getResources().getColor(R.color.yellow_button));
                } else {
                    view1_eq.setVisibility(View.GONE);
                    view1_eq.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                }

                view2_eq = layout.findViewById(R.id.action_item2);
                view2_eq.setText("BSE");

                if (AccountDetails.allowedmarket_bse) {
                    view2_eq.setVisibility(View.VISIBLE);
                    if (bse_eq.equalsIgnoreCase("green"))
                        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                            view2_eq.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
                        } else {
                            view2_eq.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                        }
                    else if (bse_eq.equalsIgnoreCase("red"))
                        view2_eq.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                    else if (bse_eq.equalsIgnoreCase("pink"))
                        view2_eq.setBackgroundColor(getResources().getColor(R.color.pink_bg));
                    else if (bse_eq.equalsIgnoreCase("blue"))
                        view2_eq.setBackgroundColor(getResources().getColor(R.color.blue_bg));
                    else if (bse_eq.equalsIgnoreCase("yellow"))
                        view2_eq.setBackgroundColor(getResources().getColor(R.color.yellow_button));
                } else {
                    view2_eq.setVisibility(View.GONE);
                    view2_eq.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                }

                if (menuPopupWindow == null || !menuPopupWindow.isPopupShowing()) {
                    menuPopupWindow = new GreekPopupWindow(getApplicationContext(), view, layout, GreekPopupWindow.POPUP_DIRECTION.TOP_OR_BOTTOM);
                    menuPopupWindow.setArrowImage(getResources().getDrawable(R.drawable.arrow4_up), UIOperations.rotateImage(getApplicationContext(), R.drawable.arrow4_down, 180));
                    menuPopupWindow.show();
                }
            }
        });

        actionBar.setPopUpFno(new OnClickListener() {
            @Override
            public void onClick(View view) {
                layout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.quick_two_actions, null);
                view1_fno = layout.findViewById(R.id.action_item1);
                view1_fno.setText("NSE");

                if (AccountDetails.allowedmarket_nfo) {
                    view1_fno.setVisibility(View.VISIBLE);
                    if (nse_fno.equalsIgnoreCase("green"))
                        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                            view1_fno.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
                        } else {
                            view1_fno.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                        }
                    else if (nse_fno.equalsIgnoreCase("red"))
                        view1_fno.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                    else if (nse_fno.equalsIgnoreCase("pink"))
                        view1_fno.setBackgroundColor(getResources().getColor(R.color.pink_bg));
                    else if (nse_fno.equalsIgnoreCase("blue"))
                        view1_fno.setBackgroundColor(getResources().getColor(R.color.blue_bg));
                    else if (nse_fno.equalsIgnoreCase("yellow"))
                        view1_fno.setBackgroundColor(getResources().getColor(R.color.yellow_button));
                } else {
                    view1_fno.setVisibility(View.GONE);
                    view1_fno.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                }

                view2_fno = layout.findViewById(R.id.action_item2);
                view2_fno.setText("BSE");
                if (AccountDetails.allowedmarket_bfo) {
                    view2_fno.setVisibility(View.VISIBLE);
                    if (bse_fno.equalsIgnoreCase("green"))
                        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                            view2_fno.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
                        } else {
                            view2_fno.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                        }

                    else if (bse_fno.equalsIgnoreCase("red"))
                        view2_fno.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                    else if (bse_fno.equalsIgnoreCase("pink"))
                        view2_fno.setBackgroundColor(getResources().getColor(R.color.pink_bg));
                    else if (bse_fno.equalsIgnoreCase("blue"))
                        view2_fno.setBackgroundColor(getResources().getColor(R.color.blue_bg));
                    else if (bse_fno.equalsIgnoreCase("yellow"))
                        view2_fno.setBackgroundColor(getResources().getColor(R.color.yellow_button));

                } else {
                    view2_fno.setVisibility(View.GONE);
                    view2_fno.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                }


                if (menuPopupWindow == null || !menuPopupWindow.isPopupShowing()) {
                    menuPopupWindow = new GreekPopupWindow(getApplicationContext(), view, layout, GreekPopupWindow.POPUP_DIRECTION.TOP_OR_BOTTOM);
                    menuPopupWindow.setArrowImage(getResources().getDrawable(R.drawable.arrow4_up), UIOperations.rotateImage(getApplicationContext(), R.drawable.arrow4_down, 180));
                    menuPopupWindow.show();
                }
            }
        });

        actionBar.setPopUpCd(new OnClickListener() {
            @Override
            public void onClick(View view) {
                layout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.quick_two_actions, null);
                view1_cd = layout.findViewById(R.id.action_item1);
                view1_cd.setText("NSE");
                if (AccountDetails.allowedmarket_ncd) {
                    view1_cd.setVisibility(View.VISIBLE);
                    if (nse_cd.equalsIgnoreCase("green"))
                        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                            view1_cd.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
                        } else {
                            view1_cd.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                        }


                    else if (nse_cd.equalsIgnoreCase("red"))
                        view1_cd.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                    else if (nse_cd.equalsIgnoreCase("pink"))
                        view1_cd.setBackgroundColor(getResources().getColor(R.color.pink_bg));
                    else if (nse_cd.equalsIgnoreCase("blue"))
                        view1_cd.setBackgroundColor(getResources().getColor(R.color.blue_bg));
                    else if (nse_cd.equalsIgnoreCase("yellow"))
                        view1_cd.setBackgroundColor(getResources().getColor(R.color.yellow_button));
                } else {
                    view1_cd.setVisibility(View.GONE);
                    view1_cd.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                }

                view2_cd = layout.findViewById(R.id.action_item2);
                view2_cd.setText("BSE");
                if (AccountDetails.allowedmarket_bcd) {
                    view2_cd.setVisibility(View.VISIBLE);
                    if (bse_cd.equalsIgnoreCase("green"))
                        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                            view2_cd.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
                        } else {
                            view2_cd.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                        }
                    else if (bse_cd.equalsIgnoreCase("red"))
                        view2_cd.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                    else if (bse_cd.equalsIgnoreCase("pink"))
                        view2_cd.setBackgroundColor(getResources().getColor(R.color.pink_bg));
                    else if (bse_cd.equalsIgnoreCase("blue"))
                        view2_cd.setBackgroundColor(getResources().getColor(R.color.blue_bg));
                    else if (bse_cd.equalsIgnoreCase("yellow"))
                        view2_cd.setBackgroundColor(getResources().getColor(R.color.yellow_button));

                } else {
                    view2_cd.setVisibility(View.GONE);
                    view2_cd.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                }


                if (menuPopupWindow == null || !menuPopupWindow.isPopupShowing()) {
                    menuPopupWindow = new GreekPopupWindow(getApplicationContext(), view, layout, GreekPopupWindow.POPUP_DIRECTION.TOP_OR_BOTTOM);
                    menuPopupWindow.setArrowImage(getResources().getDrawable(R.drawable.arrow4_up), UIOperations.rotateImage(getApplicationContext(), R.drawable.arrow4_down, 180));
                    menuPopupWindow.show();
                }
            }
        });

        actionBar.setPopUpCom(new OnClickListener() {
            @Override
            public void onClick(View view) {
                layout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.quick_two_actions, null);
                view1_com = layout.findViewById(R.id.action_item1);
                view1_com.setText("MCX");
                if (AccountDetails.allowedmarket_mcx) {
                    view1_com.setVisibility(View.VISIBLE);
                    if (mcx_com.equalsIgnoreCase("green"))
                        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                            view1_com.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
                        } else {
                            view1_com.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                        }

                    else if (mcx_com.equalsIgnoreCase("red"))
                        view1_com.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                    else if (mcx_com.equalsIgnoreCase("pink"))
                        view1_com.setBackgroundColor(getResources().getColor(R.color.pink_bg));
                    else if (mcx_com.equalsIgnoreCase("blue"))
                        view1_com.setBackgroundColor(getResources().getColor(R.color.blue_bg));
                    else if (mcx_com.equalsIgnoreCase("yellow"))
                        view1_com.setBackgroundColor(getResources().getColor(R.color.yellow_button));
                } else {
                    view1_com.setVisibility(View.GONE);
                    view1_com.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                }

                view2_com = layout.findViewById(R.id.action_item2);
                view2_com.setText("NCDEX");

                if (AccountDetails.allowedmarket_ncdex) {
                    view2_com.setVisibility(View.VISIBLE);
                    if (ncdex_com.equalsIgnoreCase("green"))
                        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                            view2_com.setBackgroundColor(getResources().getColor(R.color.whitetheambuyColor));
                        } else {
                            view2_com.setBackgroundColor(getResources().getColor(R.color.green_textcolor));
                        }

                    else if (ncdex_com.equalsIgnoreCase("red"))
                        view2_com.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                    else if (ncdex_com.equalsIgnoreCase("pink"))
                        view2_com.setBackgroundColor(getResources().getColor(R.color.pink_bg));
                    else if (ncdex_com.equalsIgnoreCase("blue"))
                        view2_com.setBackgroundColor(getResources().getColor(R.color.blue_bg));
                    else if (ncdex_com.equalsIgnoreCase("yellow"))
                        view2_com.setBackgroundColor(getResources().getColor(R.color.yellow_button));

                } else {
                    view2_com.setVisibility(View.GONE);
                    view2_com.setBackgroundColor(getResources().getColor(R.color.red_textcolor));
                }
                if (menuPopupWindow == null || !menuPopupWindow.isPopupShowing()) {
                    menuPopupWindow = new GreekPopupWindow(getApplicationContext(), view, layout, GreekPopupWindow.POPUP_DIRECTION.TOP_OR_BOTTOM);
                    menuPopupWindow.setArrowImage(getResources().getDrawable(R.drawable.arrow4_up), UIOperations.rotateImage(getApplicationContext(), R.drawable.arrow4_down, 180));
                    menuPopupWindow.show();
                }


            }
        });

        actionBar.setNiftyDetail(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getCurrentFocus() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                NeedOfDash = false;
                clickedPos = 1;
                actionBar.setNiftyClick(false);
                sendIndianIndicesRequest();
            }
        });

        actionBar.setSensexDetail(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getCurrentFocus() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                NeedOfDash = false;
                clickedPos = 0;
                actionBar.setSensexClick(false);
                sendIndianIndicesRequest();
            }
        });
        actionBar.setSearchAction(new OnClickListener() {
            @Override
            public void onClick(View view) {
                NeedOfDash = false;
                Bundle bundle = new Bundle();
                bundle.putString("Source", "Quote");
                SymbolSearchFragment frag = new SymbolSearchFragment();
                frag.setArguments(bundle);
                addFragment(R.id.activityFrameLayout, frag, true);

                //showdissmisssheet();


            }
        });


        movablefab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                setupFragment(new NotificationsFragment());
            }
        });
        hideAppTitle();
        setupMenuDrawer();

        final Intent in = getIntent();

        if (in != null) {
            String fromActivity = in.getStringExtra("from");
            if (fromActivity != null) {
                if (fromActivity.equalsIgnoreCase("heartBeat")) {
                    in.removeExtra("from");
                    doLogout(0);
                }
            } else if (in.getIntExtra("isProceedFrom", 0) == NAV_TO_NOTIFICATION_SCREEN) {
                setupFragment(new NotificationsFragment());
            } else {
                setupDefaultFragment(getIntent());
            }
        }



        /*if (Util.getPrefs(this).getInt("fcmcount", 0) > 0) {
            movablefab.setVisibility(View.VISIBLE);
        } else {
            movablefab.setVisibility(View.GONE);
        }*/
        checkClientPOSStatus();
        hideProgress();
    }

    private void getProductDetails() {
        AllowedProductRequest.sendRequest(getApplication(), serviceResponseHandler);
    }

    public void startOrderTimer() {

        AccountDetails.orderTimeFlag = false;
        orderTime = AccountDetails.getOrdTime();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(Integer.valueOf(orderTime) * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                AccountDetails.orderTimeFlag = true;
            }
        }.start();

    }

    public void updateUI(String status) {

        if (status.equalsIgnoreCase("off")) {
            snackbar.show();
            actionBar.setNiftyIcon("red");
            actionBar.setSensexIcon("red");
            AccountDetails.isLoginAndDisconnected = true;


        } else if (status.equalsIgnoreCase("on")) {
            snackbar.dismiss();
            AccountDetails.isLoginAndDisconnected = true;

        }
    }

    private void sendIndianIndicesRequest() {
        streamingController.sendIndianIndicesRequest(this, serviceResponseHandler); //TODO PK
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO: Reconnects with apollo and iris server everytime whenever user come in foreground in app as per rahul sir recommendation.(Reason to comment is 0032368)
        TCPConnectionHandler.getInstance().reConnect();
        TCPOrderConnectionHandler.getInstance().reConnect();

        Log.e("GreekBaseActivity", "OnResume======Greek==================>>>>>>>>>>>>>");

        if (CURRENT_URL == null || CURRENT_URL.isEmpty() || AccountDetails.getArachne_Port() == 0 || AccountDetails.getArachne_IP().isEmpty() ||
                AccountDetails.getUsername(getApplicationContext()) == null || AccountDetails.getUsername(getApplicationContext()).equalsIgnoreCase("UserName")
                || AccountDetails.getClientCode(getApplicationContext()).equalsIgnoreCase("ClientCode")) {
            Log.e("GreekBaseActivity", "logout========================>>>>>>>>>>>>>");
            Log.e("GreekBaseActivity", AccountDetails.getUsername(getApplicationContext()) + "" + AccountDetails.getLogin_user_type());

            Intent i = getIntent();
            int notVal = 0;
            if (i != null) {
                notVal = i.getIntExtra("isProceedFrom", 0);
            }
            Intent firstIntent = new Intent(this, SplashActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            if (notVal == 1012) {
                firstIntent.putExtra("isProceedFrom", NAV_TO_NOTIFICATION_SCREEN);
            }
            startActivity(firstIntent);

            // We are done, so finish this activity and get out now
            AccountDetails.setLastSelectedGroup("");
            finish();
            return;
        }

        if (AccountDetails.getHeartBeatApolloCount() > 3) {
            Log.v("GreekBaseOnCapture------","camera open");
            GreekDialog.alertDialog(this, 0, GREEK, "We are facing Network congestion.Kindly try reconnect or Contact with Admin.", "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                    doLogout(0);

                }
            });
        }

        if (AccountDetails.getHeartBeatIrisCount() > 3) {
            GreekDialog.alertDialog(this, 0, GREEK, "We are facing Network congestion.Kindly try reconnect or Contact with Admin.", "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    doLogout(0);

                }
            });
        }


        if (uploadReceiver != null) {
            uploadReceiver.register(getApplicationContext());
        }

        Intent in = getIntent();
        if (in.getIntExtra("isProceedFrom", 0) == NAV_TO_NOTIFICATION_SCREEN) {
            setupFragment(new NotificationsFragment());
        }

        try {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.e("TAG", "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            String token = task.getResult();
                            Log.e("Greekbase", "Fetching FCM registration token" + token);

                            SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                            editor.putString("GCMToken", token);
                            editor.commit();
                            // Log and toast

                            //                        Log.e(TAG, msg);
                            //                        Toast.makeText(GreekBaseActivity.this, token, Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(NotifyRegistrationIntentService.REGISTRATION_SUCCESS));
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(NotifyRegistrationIntentService.REGISTRATION_ERROR));
        }


        if (AccountDetails.isIsApolloConnected()) {

            // BY Ravi and Rahul sir
            //streamingController.sendMarketStatusStreamingRequest(getApplicationContext(), "MarketStatus", AccountDetails.getClientCode(getApplicationContext()), null, null, false); //PK

            if (getStreamingList().containsKey("ltpinfo")) {
                streamingController.sendStreamingRequest(getApplication(), getStreamingList().get("ltpinfo"), "ltpinfo", null, null, false);
            } else if (getStreamingList().containsKey("ohlc")) {
                streamingController.sendStreamingRequest(getApplication(), getStreamingList().get("ohlc"), "ohlc", null, null, false);
            } else if (getStreamingList().containsKey("marketpicture")) {
                streamingController.sendStreamingRequest(getApplication(), getStreamingList().get("marketpicture"), "marketpicture", null, null, false);
            } else if (getStreamingList().containsKey("touchline")) {
                streamingController.sendStreamingRequest(getApplication(), getStreamingList().get("touchline"), "touchline", null, null, false);
            }
        }

        if (AccountDetails.isNetworkConnected) {
            snackbar.dismiss();
        } else {
            snackbar.show();
        }
    }

    @Override
    protected void onPause() {

        AccountDetails.setIsOnPauseApp(true);

        AccountDetails.isLoginAndDisconnected = true;

        if (mPhoneStatelistener != null) {
            mPhoneStatelistener = null;
        }

        if (uploadReceiver != null && !AccountDetails.getIsMainActivity()) {
            uploadReceiver.register(getApplicationContext());
            uploadReceiver.unregister(getApplicationContext());
        }

        if (networkChangeReceiver != null && !AccountDetails.getIsMainActivity()) {
            try {
                unregisterReceiver(networkChangeReceiver);
            } catch (Exception ignored) {
            } finally {
                networkChangeReceiver = null;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (isInPictureInPictureMode()) {

                pipLinearLayput.setVisibility(View.VISIBLE);
                drawerLayout.setVisibility(View.GONE);
                bottom_sheet.setVisibility(View.GONE);

            } else {
                if (!AccountDetails.getIsMainActivity()) {
                    LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);

                    if (getStreamingList().containsKey("ltpinfo")) {
                        streamingController.pauseStreaming(getApplication(), "ltpinfo", getStreamingList().get("ltpinfo"));
                    } else if (getStreamingList().containsKey("ohlc")) {
                        streamingController.pauseStreaming(getApplication(), "ohlc", getStreamingList().get("ohlc"));
                    } else if (getStreamingList().containsKey("marketpicture")) {
                        streamingController.pauseStreaming(getApplication(), "marketpicture", getStreamingList().get("marketpicture"));
                    } else if (getStreamingList().containsKey("touchline")) {
                        streamingController.pauseStreaming(getApplication(), "touchline", getStreamingList().get("touchline"));
                    }
                    EventBus.getDefault().unregister(this);
                }
            }
        }

        super.onPause();
    }

    public void onEventMainThread(HeartBeatResponse heartBeatResponse) {

        // When archane heartbeat request does not respond
        try {
            String errorCode = heartBeatResponse.getErrorCode();

            if (!errorCode.equalsIgnoreCase("0")) {
                doLogout(0);
            }


        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equals("index")) {

                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);

            } else if (streamingResponse.getStreamingType().equals("ltpinfo")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateUsdData(broadcastResponse);
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private void updateUsdData(StreamerBroadcastResponse broadcastResponse) {
        if (broadcastResponse.getSymbol().equalsIgnoreCase(GOLD_TOKEN)) {
            actionBar.setGoldValues(broadcastResponse.getLast(), broadcastResponse.getChange(), broadcastResponse.getP_change());
        }
    }

    public void onEventMainThread(AlertExecutedStreamingResponse alertExecutedStreamingResponse) {
        try {
            GreekDialog.dismissDialog();
            GreekDialog.alertDialog(this, 0, GREEK, "\n Alert Notification " + "\n" + alertExecutedStreamingResponse.getSymbol() + "\n Current Value : " + alertExecutedStreamingResponse.getCurrentValue() + "\n Range : " + alertExecutedStreamingResponse.getRangeValue() + "\n Message : " + alertExecutedStreamingResponse.getMessage(), "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    refreshFragment();
                }
            });
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void onEventMainThread(AdminMessagesResponse adminMessagesResponse) {
        try {
            GreekDialog.dismissDialog();
            GreekDialog.alertDialog(this, 0, GREEK, "\nAdmin Notification : " + "\n" + adminMessagesResponse.getMessage(), "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    NotificationManager notificationManager = (NotificationManager) GreekBaseActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancelAll();
                    refreshFragment();
                }
            });
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void onEventMainThread(UserDetailsModifiedResponse userDetailsModifiedResponse) {
        try {
            GreekDialog.dismissDialog();

            if (userDetailsModifiedResponse.getStatus().equalsIgnoreCase("0")) {
                String message = "You are suspended by the Admin ! Please contact Admin";
                GreekDialog.alertDialog(this, 0, GREEK, message, "OK", false, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        doLogout(0);
                    }
                });
            } else {

                String message = "User Details has been modified by Admin.Kindly Relogin.\n(Warning: If continued App may behave abnormally)";
                GreekDialog.alertDialog(this, 0, GREEK, message, "Relogin",
                        "Continue", false, new DialogListener() {
                            @Override
                            public void alertDialogAction(Action action, Object... data) {
                                if (action.toString().equalsIgnoreCase("ok")) {
                                    doLogout(0);
                                } else {
                                    checkClientPOSStatus();
                                }

                            }
                        });
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void onEventMainThread(KillSwitchResponse killSwitchResponse) {
        try {
            GreekDialog.dismissDialog();
            GreekDialog.alertDialog(this, 0, GREEK, killSwitchResponse.getReason(), "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    //refresh();
                }
            });
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {
        try {
            if (response.getSymbol().equalsIgnoreCase(NIFTY_TOKEN)) {
                actionBar.setNiftyValues(response.getLast(), response.getChange(), response.getP_change(), response.getName());
            } else if (response.getSymbol().equalsIgnoreCase(SENSEX_TOKEN)) {
                actionBar.setSensexValues(response.getLast(), response.getChange(), response.getP_change(), response.getName());
            }

            String indexBrdName = response.getName();

            if (indexBrdName.equalsIgnoreCase("Nifty 50")) {
                indexBrdName = "Nifty";
            }

            if (indexBrdName.equalsIgnoreCase(niftyTxt.getText().toString())) {


                if (getAssetType(response.getIndexCode()).equalsIgnoreCase("currency")) {
                    niftyValueTxt.setText(StringStuff.commaINRDecorator(response.getLast()));
                    niftyChngTxt.setText(String.format("%.4f", Double.parseDouble(response.getChange())) + "(" + String.format("%.2f", Double.parseDouble(response.getP_change())) + "%)");
                    actionBar.updateActionbarNifty(StringStuff.commaINRDecorator(response.getLast()), response.getChange());
                } else {
                    niftyValueTxt.setText(StringStuff.commaDecorator(response.getLast()));
                    niftyChngTxt.setText(String.format("%.2f", Double.parseDouble(response.getChange())) + "(" + String.format("%.2f", Double.parseDouble(response.getP_change())) + "%)");
                    actionBar.updateActionbarNifty(StringStuff.commaDecorator(response.getLast()), response.getChange());
                }

                if (niftyChngTxt.getText().toString().startsWith("-")) {
                    niftyChngTxt.setTextColor(getResources().getColor(R.color.bajaj_light_red));
                } else {
                    if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                        niftyChngTxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
                    } else {
                        niftyChngTxt.setTextColor(getResources().getColor(R.color.dark_green_positive));
                    }
                }
            }
            if (indexBrdName.equalsIgnoreCase(sensextxt.getText().toString())) {

                if (getAssetType(response.getIndexCode()).equalsIgnoreCase("currency")) {
                    sensexValueTxt.setText(StringStuff.commaINRDecorator(response.getLast()));
                    sensexChngTxt.setText(String.format("%.4f", Double.parseDouble(response.getChange())) + "(" + String.format("%.2f", Double.parseDouble(response.getP_change())) + "%)");
                    actionBar.updateActionbarSensex(StringStuff.commaINRDecorator(response.getLast()), response.getChange());
                } else {
                    sensexValueTxt.setText(StringStuff.commaDecorator(response.getLast()));
                    sensexChngTxt.setText(String.format("%.2f", Double.parseDouble(response.getChange())) + "(" + String.format("%.2f", Double.parseDouble(response.getP_change())) + "%)");
                    actionBar.updateActionbarSensex(StringStuff.commaDecorator(response.getLast()), response.getChange());

                }
                if (sensexChngTxt.getText().toString().startsWith("-")) {
                    sensexChngTxt.setTextColor(getResources().getColor(R.color.bajaj_light_red));
                } else {
                    if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                        sensexChngTxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
                    } else {
                        sensexChngTxt.setTextColor(getResources().getColor(R.color.dark_green_positive));
                    }

                }
            }
            if (indexBrdName.equalsIgnoreCase(firstsymbolTxt.getText().toString())) {

                if (getAssetType(response.getIndexCode()).equalsIgnoreCase("currency")) {
                    firstvalueTxt.setText(StringStuff.commaINRDecorator(response.getLast()));
                    firstvalueTxt.invalidate();
                    firstChngTxt.setText(String.format("%.4f", Double.parseDouble(response.getChange())) + "(" + String.format("%.2f", Double.parseDouble(response.getP_change())) + "%)");
                } else {
                    firstvalueTxt.setText(StringStuff.commaINRDecorator(response.getLast()));
                    firstvalueTxt.invalidate();
                    firstChngTxt.setText(String.format("%.2f", Double.parseDouble(response.getChange())) + "(" + String.format("%.2f", Double.parseDouble(response.getP_change())) + "%)");
                }


                if (firstChngTxt.getText().toString().startsWith("-")) {
                    firstChngTxt.setTextColor(getResources().getColor(R.color.bajaj_light_red));
                } else {
                    if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                        firstChngTxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
                    } else {
                        firstChngTxt.setTextColor(getResources().getColor(R.color.dark_green_positive));
                    }

                }

            }
            if (indexBrdName.equalsIgnoreCase(secondSymbolTxt.getText().toString())) {

                if (getAssetType(response.getIndexCode()).equalsIgnoreCase("currency")) {
                    secondvalueTxt.setText(StringStuff.commaINRDecorator(response.getLast()));
                    secondvalueTxt.invalidate();
                    secondChnagTxt.setText(String.format("%.4f", Double.parseDouble(response.getChange())) + "(" + String.format("%.2f", Double.parseDouble(response.getP_change())) + "%)");
                } else {
                    secondvalueTxt.setText(StringStuff.commaINRDecorator(response.getLast()));
                    secondvalueTxt.invalidate();
                    secondChnagTxt.setText(String.format("%.2f", Double.parseDouble(response.getChange())) + "(" + String.format("%.2f", Double.parseDouble(response.getP_change())) + "%)");
                }


                if (secondChnagTxt.getText().toString().startsWith("-")) {
                    secondChnagTxt.setTextColor(getResources().getColor(R.color.bajaj_light_red));
                } else {
                    if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                        secondChnagTxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
                    } else {
                        secondChnagTxt.setTextColor(getResources().getColor(R.color.dark_green_positive));
                    }

                }
            }
            if (indexBrdName.equalsIgnoreCase(thirdsymbolTxt.getText().toString())) {

                if (getAssetType(response.getIndexCode()).equalsIgnoreCase("currency")) {
                    thrdvalueTxt.setText(StringStuff.commaINRDecorator(response.getLast()));
                    thrdvalueTxt.invalidate();
                    thrdChngTxt.setText(String.format("%.4f", Double.parseDouble(response.getChange())) + "(" + String.format("%.2f", Double.parseDouble(response.getP_change())) + "%)");
                } else {
                    thrdvalueTxt.setText(StringStuff.commaINRDecorator(response.getLast()));
                    thrdvalueTxt.invalidate();
                    thrdChngTxt.setText(String.format("%.2f", Double.parseDouble(response.getChange())) + "(" + String.format("%.2f", Double.parseDouble(response.getP_change())) + "%)");
                }

                if (thrdChngTxt.getText().toString().startsWith("-")) {
                    thrdChngTxt.setTextColor(getResources().getColor(R.color.bajaj_light_red));
                } else {
                    if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                        thrdChngTxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
                    } else {
                        thrdChngTxt.setTextColor(getResources().getColor(R.color.dark_green_positive));
                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            //generateNoteOnSd(e.toString() + "-" + response);
        }
    }

    public void onEventMainThread(StreamerBroadcastResponse response) {
        try {
            if (response.getSymbol().equalsIgnoreCase(NIFTY_TOKEN)) {
                actionBar.setNiftyValues(response.getLast(), response.getChange(), response.getP_change(), response.getName());
            } else if (response.getSymbol().equalsIgnoreCase(SENSEX_TOKEN)) {
                actionBar.setSensexValues(response.getLast(), response.getChange(), response.getP_change(), response.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //generateNoteOnSd(e.toString() + "-" + response);
        }
    }

    public void onEventMainThread(LicenseResponse licenseResponse) {
        try {
            GreekDialog.dismissDialog();
            GreekDialog.alertDialog(this, 0, GREEK, licenseResponse.getReason(), "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    doLogout(0);
                }
            });
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void onEventMainThread(LoggedInClientResponse response) {
        try {

            int i = 5;
            Toast.makeText(getApplication(), response.getDisconnect_reason(), Toast.LENGTH_LONG).show();
            doLogout(0);
        } catch (Exception e) {
            e.printStackTrace();
            //generateNoteOnSd(e.toString() + "-" + response);
        }
    }

    public void onEventMainThread(IrisRejection response) {
        try {
            Toast.makeText(GreekBaseActivity.this, response.getReason(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            //generateNoteOnSd(e.toString() + "-" + response);
        }
    }


    public void onEventMainThread(MarketStatusResponse response) {

        try {
            LinkedHashMap<String, String> hm = new LinkedHashMap<>();
            hm.put("green", "1");
            hm.put("yellow", "2");
            hm.put("blue", "3");
            hm.put("pink", "4");
            hm.put("red", "5");


            Log.e("MarketStatusResponse", "response==getMarket_id====>" + response.getMarket_id());

            //FOR EQUITY
            if (AccountDetails.allowedmarket_nse) {

                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("1")) {

                    eq = true;
                    nse_eq = "green";

                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true; //closed
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "yellow";
                    AccountDetails.nse_eq_status = false;//preopen
                    AccountDetails.isPreOpen_nse_eq = true;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "blue";
                    AccountDetails.nse_eq_status = false; //need to chaged straus to true after testing. in web is true
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "pink";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                }
            }

            //FOR FNO
            /*  int fo = 0;*/
            if (AccountDetails.allowedmarket_nfo) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("2")) {

                    nse_fno = "green";
                    fno = true;
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "yellow";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = true;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "blue";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "pink";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                }
            }
            //FOR CURRENCY

            /* int cd = 0;*/
            if (AccountDetails.allowedmarket_ncd) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("3")) {

                    cd = true;
                    nse_cd = "green";
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "yellow";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = true;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "blue";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "pink";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                }
            }

            if (AccountDetails.allowedmarket_mcx) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "green";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "yellow";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = true;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "blue";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "pink";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;
                }
            }

            if (AccountDetails.allowedmarket_bse) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("4")) {

                    bseeq = true;
                    bse_eq = "green";
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;
                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    bseeq = false;
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "yellow";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = true;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    bseeq = false;
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "blue";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "pink";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;
                    bseeq = false;

                }

            }

            if (AccountDetails.allowedmarket_bfo) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("5")) {

                    bsefno = true;
                    bse_fno = "green";
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    bsefno = false;
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "yellow";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = true;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;
                    bsefno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "blue";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "pink";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;
                    bsefno = false;

                }

            }

            if (AccountDetails.allowedmarket_bcd) {

                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("6")) {

                    bsecd = true;
                    bse_cd = "green";
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "yellow";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = true;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "blue";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "pink";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;
                }
            }

            if (AccountDetails.allowedmarket_ncdex) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "green";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "yellow";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = true;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "blue";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "pink";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                }
            }

            if (AccountDetails.allowedmarket_mcx) {
                if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("1") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "green";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("-1") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("0") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "yellow";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = true;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("2") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("3") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "blue";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = true;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("4") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "pink";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.getSession().equalsIgnoreCase("1") && response.getStatus().equalsIgnoreCase("5") && response.getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;
                }
            }


            if (Integer.parseInt(hm.get(nse_eq)) <= Integer.parseInt(hm.get(bse_eq))) {
                //Log.d("color",hm.get(nse_eq)+"-----"+hm.get(bse_eq));
                actionBar.changeStatus("eq", nse_eq);
            } else if (Integer.parseInt(hm.get(bse_eq)) <= Integer.parseInt(hm.get(nse_eq))) {
                actionBar.changeStatus("eq", bse_eq);
            }

            if (Integer.parseInt(hm.get(nse_fno)) <= Integer.parseInt(hm.get(bse_fno))) {
                actionBar.changeStatus("fno", nse_fno);
            } else if (Integer.parseInt(hm.get(bse_fno)) <= Integer.parseInt(hm.get(nse_fno))) {
                actionBar.changeStatus("fno", bse_fno);
            }

            if (Integer.parseInt(hm.get(nse_cd)) <= Integer.parseInt(hm.get(bse_cd))) {
                actionBar.changeStatus("cd", nse_cd);
            } else if (Integer.parseInt(hm.get(bse_cd)) <= Integer.parseInt(hm.get(nse_cd))) {
                actionBar.changeStatus("cd", bse_cd);
            }

            if (Integer.parseInt(hm.get(mcx_com)) <= Integer.parseInt(hm.get(ncdex_com))) {
                actionBar.changeStatus("com", mcx_com);
            } else if (Integer.parseInt(hm.get(ncdex_com)) <= Integer.parseInt(hm.get(mcx_com))) {
                actionBar.changeStatus("com", ncdex_com);
            }

            if (AccountDetails.broadcastServerAuthenticated == true) {
                actionBar.setNiftyIcon("green");
            } else if (AccountDetails.broadcastServerAuthenticated == false) {
                actionBar.setNiftyIcon("red");
            }

            if (AccountDetails.orderServerAuthenticated == true) {
                actionBar.setSensexIcon("green");
            } else if (AccountDetails.orderServerAuthenticated == false) {
                actionBar.setSensexIcon("red");
            }

            sendMarketStatusRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEventMainThread(StreamerOrderSubmitStatus submitStatus) {
        GreekDialog.dismissDialog();


        if (submitStatus.isSubmitStatus()) {

            GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Order sent for: " + submitStatus.getSymbol(), "OK", false, new GreekDialog.DialogListener() {

                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    refreshFragment();
                }
            });
        }
    }

    public void onEventMainThread(final StreamerOrderConfirmationResponse confirmationResponse) {
        try {
            GreekDialog.dismissDialog();
            String statusCode = "";
            String validity = "";
            String cancelledByString = "";

            String confirmSymbol;
            if (Integer.parseInt(confirmationResponse.getGoodTillDate()) > 0) {
                validity = "GTD";
            } else if (getDayResult(Short.parseShort(confirmationResponse.getValidity())) == 1) {
                validity = "Day";
            } else if (getIOCResult(Short.parseShort(confirmationResponse.getValidity())) == 1) {
                validity = "IOC";
            } else if (getGTCResult(Short.parseShort(confirmationResponse.getValidity())) == 1) {
                validity = "GTC";
            } else if (getEOSResult(Short.parseShort(confirmationResponse.getValidity())) == 1) {
                validity = "EOS";
            }

            if (getExchange(confirmationResponse.getGtoken()).equalsIgnoreCase("mcx")) {
//                confirmSymbol = confirmationResponse.getTradeSymbol() + "-" + confirmationResponse.getInstrument();


                if (confirmationResponse.getOptionType().equalsIgnoreCase("XX")) {
                    confirmSymbol = confirmationResponse.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(confirmationResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-M" + " - " + confirmationResponse.getInstrument();

                } else {

                    confirmSymbol = confirmationResponse.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(confirmationResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + confirmationResponse.getStrikePrice() + "" + confirmationResponse.getOptionType() + "-M" + " - " + confirmationResponse.getInstrument();

                }


            } else {
                confirmSymbol = confirmationResponse.getSymbol();
            }

            if (confirmationResponse.getCode().equalsIgnoreCase(String.valueOf(0)) ||
                    confirmationResponse.getCode().equalsIgnoreCase("201") || confirmationResponse.getCode().equalsIgnoreCase("202") || confirmationResponse.getCode().equalsIgnoreCase("101") || confirmationResponse.getCode().equalsIgnoreCase("102") || confirmationResponse.getCode().equalsIgnoreCase("502") || confirmationResponse.getCode().equalsIgnoreCase("1302") || confirmationResponse.getCode().equalsIgnoreCase("1301") || confirmationResponse.getCode().equalsIgnoreCase("114") || confirmationResponse.getCode().equalsIgnoreCase("303")) {
                statusCode = "Success";
            } else if (getExchange(confirmationResponse.getGtoken()).equalsIgnoreCase("ncdex")) {

                statusCode = confirmationResponse.getCode() + ":" + confirmationResponse.getReason();
            } else {
                if (confirmationResponse.getStatus().equalsIgnoreCase("cancelled")) {
                    cancelledByString = getCancelledByDetail(confirmationResponse.getCancelledBy());

                    statusCode = "Success";

                    if (!getCancelledByDetail(confirmationResponse.getCancelledBy()).equalsIgnoreCase("")) {

                        if (confirmationResponse.getCancelledBy().equalsIgnoreCase("8001")) {

                            cancelledByString = "Order Cancelled By :" + getCancelledByDetail(confirmationResponse.getCancelledBy());
                            statusCode = "Success";

                        } else {

                            cancelledByString = "Cancelled By :" + getCancelledByDetail(confirmationResponse.getCancelledBy());
                            statusCode = "Success";
                        }
                    } else {
                        if (validity.equalsIgnoreCase("ioc") && confirmationResponse.getCode().equalsIgnoreCase("16388")) {

                        } else {
                            statusCode = getErrorMessage(getMarketId(confirmationResponse.getGtoken()), confirmationResponse.getCode());
                        }
                    }
                } else if (confirmationResponse.getOrder_state().equalsIgnoreCase("1")) {
                    if (!getCancelledByDetail(confirmationResponse.getCancelledBy()).equalsIgnoreCase("")) {
                        cancelledByString = "Modified By :" + getCancelledByDetail(confirmationResponse.getCancelledBy());
                        statusCode = "Success";
                    }
                } else {
                    if (validity.equalsIgnoreCase("ioc") && confirmationResponse.getCode().equalsIgnoreCase("16388")) {

                    } else {
                        statusCode = getErrorMessage(getMarketId(confirmationResponse.getGtoken()), confirmationResponse.getCode());
                    }
                }
            }

            if (getExchange(confirmationResponse.getGtoken()).equalsIgnoreCase("mcx")) {

                if (confirmationResponse.getStatus().equalsIgnoreCase("cancelled")) {

                    if (!getCancelledByDetail(confirmationResponse.getCancelledBy()).equalsIgnoreCase("")) {

                        cancelledByString = "Cancelled By :" + getCancelledByDetail(confirmationResponse.getCancelledBy());

                        if (confirmationResponse.getCancelledBy().equalsIgnoreCase("E")) {

                            cancelledByString = "";
                            statusCode = "";
                            cancelledByString = cancelledByString + "\n" + getErrorMessage(getMarketId(confirmationResponse.getGtoken()), confirmationResponse.getCode());
                        }

                    }
           /*         else {

                        cancelledByString = "Cancelled By :" + getErrorMessage(getMarketId(confirmationResponse.getGtoken()), confirmationResponse.getCode());

                    }*/


                } else if (confirmationResponse.getOrder_state().equalsIgnoreCase("1")) {
                    if (!getCancelledByDetail(confirmationResponse.getCancelledBy()).equalsIgnoreCase("")) {
                        cancelledByString = "Modified By :" + getCancelledByDetail(confirmationResponse.getCancelledBy());
                    }
                }
            }


//            EDITED BY SUSHANT
//          MANIPULATION OF QUANTITY ACCORDING TO LOT WISE FOR MCX ONLY
            if (getExchange(confirmationResponse.getGtoken()).equalsIgnoreCase("mcx") || getExchange(confirmationResponse.getGtoken()).equalsIgnoreCase("ncdex")) {
                confirmationResponse.setQty(String.valueOf((Integer.parseInt(confirmationResponse.getQty())) / (Integer.parseInt(confirmationResponse.getRegular_lot()))));
                confirmationResponse.setPending_qty(String.valueOf((Integer.parseInt(confirmationResponse.getPending_qty())) / (Integer.parseInt(confirmationResponse.getRegular_lot()))));
            }

            String expirydateText = "";
            if (!confirmationResponse.getExpiryDate().equalsIgnoreCase("0")) {
                expirydateText = "\n Expiry Date:" + DateTimeFormatter.getDateFromTimeStamp(confirmationResponse.getExpiryDate(), "dd MMM yyyy", "bse");
            }

            //Toast.makeText(getApplicationContext(),getBitResult(Short.parseShort(confirmationResponse.getValidity())),Toast.LENGTH_LONG).show();
            if (getOrderType(confirmationResponse.getOrder_type()).equalsIgnoreCase("Stop Loss") || getOrderType(confirmationResponse.getOrder_type()).equalsIgnoreCase("StopLoss Market")) {


                if (((Integer.valueOf(confirmationResponse.getGtoken()) >= 502000000) && ((Integer.valueOf(confirmationResponse.getGtoken()) <= 502999999)) || ((Integer.valueOf(confirmationResponse.getGtoken()) >= 1302000000) && (Integer.valueOf(confirmationResponse.getGtoken()) <= 1302999999)))) {
                    //GreekDialog.alertDialog(GreekBaseActivity.this, ALERTS_ACTION_ID, GREEK, "State: " + getOrderState(confirmationResponse.getOrder_state()) + " \n" + confirmationResponse.getSymbol() + " \n Exchange: " + getExchange(confirmationResponse.getGtoken())+ "\nPrice: " + String.format("%.4f", Double.parseDouble(confirmationResponse.getPrice())) + "\nQty: " + confirmationResponse.getQty() + "\nPending Quantity: " + confirmationResponse.getPending_qty() + "\nOrder Type: " + getOrderType(confirmationResponse.getOrder_type())+"\nTrigger Price: "+confirmationResponse.getTrigger_price() + "\nAction: " + getSide(confirmationResponse.getSide())+"\nOrder Status: "+confirmationResponse.getStatus()+"\nCode: "+statusCode, "Ok", false, null);
                    GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "State: " + getOrderState(confirmationResponse.getOrder_state()) + " \n" + confirmSymbol + " \n Exchange: " + getExchange(confirmationResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(confirmationResponse.getGtoken()) + "\n Ref. No : " + confirmationResponse.getGorderid() + "\n Exchange Order No: " + confirmationResponse.getEorderid() + expirydateText + "\n Product:" + getProductType(confirmationResponse.getProduct()) + "\nPrice: " + String.format("%.4f", Double.parseDouble(confirmationResponse.getPrice())) + "\nQty: " + confirmationResponse.getQty() + "\nPending Quantity: " + confirmationResponse.getPending_qty() + "\nOrder Type: " + getOrderType(confirmationResponse.getOrder_type()) + "\nTrigger Price: " + confirmationResponse.getTrigger_price() + "\nAction: " + getSide(confirmationResponse.getSide())
                            + "\nValidity: " + validity + "\nOrder Status: " + confirmationResponse.getStatus() + "\n" + statusCode + "\n" + cancelledByString, "OK", false, new DialogListener() {

                        @Override
                        public void alertDialogAction(Action action, Object... data) {
                            refreshFragment();
                            if (confirmationResponse.getOrder_state().equalsIgnoreCase("1")) {
                                setupFragment(new OrderBottomFragment());
                            }
                        }
                    });
                } else {
                    //GreekDialog.alertDialog(GreekBaseActivity.this, ALERTS_ACTION_ID, GREEK, "State: " + getOrderState(confirmationResponse.getOrder_state()) + " \n" + confirmationResponse.getSymbol() + " \n Exchange: " + getExchange(confirmationResponse.getGtoken())+ "\nPrice: " + confirmationResponse.getPrice() + "\nQty: " + confirmationResponse.getQty() + "\nPending Quantity: " + confirmationResponse.getPending_qty() + "\nOrder Type: " + getOrderType(confirmationResponse.getOrder_type()) + "\nTrigger Price: " + confirmationResponse.getTrigger_price() + "\nAction: " + getSide(confirmationResponse.getSide()) + "\nOrder Status: " + confirmationResponse.getStatus() + "\nCode: " +statusCode, "Ok", false, null);
                    GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "State: " + getOrderState(confirmationResponse.getOrder_state()) + " \n" + confirmSymbol + " \n Exchange: " + getExchange(confirmationResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(confirmationResponse.getGtoken()) + "\n Ref. No : " + confirmationResponse.getGorderid() + "\n Exchange Order No: " + confirmationResponse.getEorderid() + expirydateText + "\n Product:" + getProductType(confirmationResponse.getProduct()) + "\nPrice: " + confirmationResponse.getPrice() + "\nQty: " + confirmationResponse.getQty() + "\nPending Quantity: " + confirmationResponse.getPending_qty() + "\nOrder Type: " + getOrderType(confirmationResponse.getOrder_type()) + "\nTrigger Price: " + confirmationResponse.getTrigger_price() + "\nAction: " + getSide(confirmationResponse.getSide())
                            + "\nValidity: " + validity + "\nOrder Status: " + confirmationResponse.getStatus() + "\n" + statusCode + "\n" + cancelledByString, "OK", false, new DialogListener() {

                        @Override
                        public void alertDialogAction(Action action, Object... data) {
                            refreshFragment();
                            if (confirmationResponse.getOrder_state().equalsIgnoreCase("1")) {
                                setupFragment(new OrderBottomFragment());
                            }
                        }
                    });
                }
            } else {


                if (((Integer.valueOf(confirmationResponse.getGtoken()) >= 502000000) && ((Integer.valueOf(confirmationResponse.getGtoken()) <= 502999999)) || ((Integer.valueOf(confirmationResponse.getGtoken()) >= 1302000000) && (Integer.valueOf(confirmationResponse.getGtoken()) <= 1302999999)))) {

                    GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "State: " + getOrderState(confirmationResponse.getOrder_state()) + " \n" + confirmSymbol + " \n Exchange: " + getExchange(confirmationResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(confirmationResponse.getGtoken()) + "\n Ref. No : " + confirmationResponse.getGorderid() + "\n Exchange Order No: " + confirmationResponse.getEorderid() + expirydateText + "\n Product:" + getProductType(confirmationResponse.getProduct()) + "\nPrice: " + String.format("%.4f", Double.parseDouble(confirmationResponse.getPrice())) + "\nTrigger Price: " + String.format("%.4f", Double.parseDouble(confirmationResponse.getTrigger_price())) + "\nQty: " + confirmationResponse.getQty() + "\nPending Quantity: " + confirmationResponse.getPending_qty() + "\nOrder Type: " + getOrderType(confirmationResponse.getOrder_type()) + "\nAction: " + getSide(confirmationResponse.getSide()) + "\nValidity: "
                            + validity + "\nOrder Status: " + confirmationResponse.getStatus() + "\n" + statusCode + "\n" + cancelledByString, "OK", false, new DialogListener() {

                        @Override
                        public void alertDialogAction(Action action, Object... data) {
                            refreshFragment();
                            if (confirmationResponse.getOrder_state().equalsIgnoreCase("1")) {
                                setupFragment(new OrderBottomFragment());
                            }
                        }
                    });

                } else {

                    GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "State: " + getOrderState(confirmationResponse.getOrder_state()) + " \n" + confirmSymbol + " \n Exchange: " + getExchange(confirmationResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(confirmationResponse.getGtoken()) + "\n Ref. No : " + confirmationResponse.getGorderid() + "\n Exchange Order No: " + confirmationResponse.getEorderid() + expirydateText + "\n Product:" + getProductType(confirmationResponse.getProduct()) + "\nPrice: " + String.format("%.2f", Double.parseDouble(confirmationResponse.getPrice())) + "\nQty: " + confirmationResponse.getQty() + "\nPending Quantity: " + confirmationResponse.getPending_qty() + "\nOrder Type: " + getOrderType(confirmationResponse.getOrder_type()) + "\nTrigger Price: " + confirmationResponse.getTrigger_price() + "\nAction: " + getSide(confirmationResponse.getSide()) + "\nValidity: " + validity + "\n"
                            + "\nOrder Status: " + confirmationResponse.getStatus() + "\n" + statusCode + "\n" + cancelledByString, "OK", false, new DialogListener() {

                        @Override
                        public void alertDialogAction(Action action, Object... data) {
                            refreshFragment();
                            if (confirmationResponse.getOrder_state().equalsIgnoreCase("1")) {
                                setupFragment(new OrderBottomFragment());
                            }
                        }
                    });
                }

            }
            //Only Vibrate/Play Sound if Setting On
            if (Util.getPrefs(this).getBoolean("GREEK_BEEP_VIBRATE_acumengroupGLE", true)) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(GreekBaseActivity.this, notification);
                r.play();
                Vibrator v = (Vibrator) GreekBaseActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
            }


        } catch (Exception e) {
            e.printStackTrace();
            //generateNoteOnSd(e.toString() + "-" + confirmationResponse);
        }
    }


    private String getCancelledByDetail(String cancelDetail) {
        String cancelledBy = "";

        if (cancelDetail.equalsIgnoreCase("17017")) {
            cancelledBy = "due to Voluntary Closeout";
        } else if (cancelDetail.equalsIgnoreCase("a")) {
            cancelledBy = "Admin";
        } else if (cancelDetail.equalsIgnoreCase("n")) {
            cancelledBy = "Mini Admin";
        } else if (cancelDetail.equalsIgnoreCase("e")) {
            cancelledBy = "Exchange";
        } else if (cancelDetail.equalsIgnoreCase("m")) {
            cancelledBy = "Member Admin";
        } else if (cancelDetail.equalsIgnoreCase("17070") || (cancelDetail.equalsIgnoreCase("10010"))) {
            cancelledBy = "exchange [Price out of current execution range]";
        } else if (cancelDetail.equalsIgnoreCase("8001")) {
            cancelledBy = "exchange RMS";
        } else if (cancelDetail.equalsIgnoreCase("17080")) {
            cancelledBy = "Exchange [Self Trade Prevention]";
        }

        return cancelledBy;

    }

    public void onEventMainThread(StreamerConnectionStatusResponse connectionStatusResponse) {
        GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Server Component Disconnected", "OK", false, new DialogListener() {

            @Override
            public void alertDialogAction(Action action, Object... data) {

            }
        });

        LinkedHashMap<String, String> hm = new LinkedHashMap<>();
        hm.put("green", "1");
        hm.put("yellow", "2");
        hm.put("blue", "3");
        hm.put("pink", "4");
        hm.put("red", "5");

        if (connectionStatusResponse.getStatus().equalsIgnoreCase("1") && connectionStatusResponse.getMarket_id().equalsIgnoreCase("1")) {
            eq = true;
            nse_eq = "red";
            AccountDetails.nse_eq_status = false;
        }

        //FOR FNO
        /*  int fo = 0;*/
        else if (connectionStatusResponse.getStatus().equalsIgnoreCase("1") && connectionStatusResponse.getMarket_id().equalsIgnoreCase("2")) {
            nse_fno = "red";
            fno = true;
            AccountDetails.nse_fno_status = false;
        }
        //FOR CURRENCY

        /* int cd = 0;*/

        else if (connectionStatusResponse.getStatus().equalsIgnoreCase("1") && connectionStatusResponse.getMarket_id().equalsIgnoreCase("3")) {

            cd = true;
            nse_cd = "red";
            AccountDetails.nse_cd_status = false;
        } else if (connectionStatusResponse.getStatus().equalsIgnoreCase("1") && connectionStatusResponse.getMarket_id().equalsIgnoreCase("9")) {

            mcx_com = "red";
            AccountDetails.mcx_com_status = false;

        } else if (connectionStatusResponse.getStatus().equalsIgnoreCase("1") && connectionStatusResponse.getMarket_id().equalsIgnoreCase("4")) {

            bseeq = true;
            bse_eq = "red";
            AccountDetails.bse_eq_status = false;
        } else if (connectionStatusResponse.getStatus().equalsIgnoreCase("1") && connectionStatusResponse.getMarket_id().equalsIgnoreCase("5")) {
            bsefno = true;
            bse_fno = "red";
            AccountDetails.bse_fno_status = false;
        } else if (connectionStatusResponse.getStatus().equalsIgnoreCase("1") && connectionStatusResponse.getMarket_id().equalsIgnoreCase("6")) {
            bsecd = true;
            bse_cd = "red";
            AccountDetails.bse_cd_status = false;
        } else if (connectionStatusResponse.getStatus().equalsIgnoreCase("1") && connectionStatusResponse.getMarket_id().equalsIgnoreCase("7")) {
            ncdex_com = "red";
            AccountDetails.ncdex_com_status = false;
        }


        if (Integer.parseInt(hm.get(nse_eq)) <= Integer.parseInt(hm.get(bse_eq))) {
            //Log.d("color",hm.get(nse_eq)+"-----"+hm.get(bse_eq));
            actionBar.changeStatus("eq", nse_eq);
        } else if (Integer.parseInt(hm.get(bse_eq)) <= Integer.parseInt(hm.get(nse_eq))) {
            actionBar.changeStatus("eq", bse_eq);
        }

        if (Integer.parseInt(hm.get(nse_fno)) <= Integer.parseInt(hm.get(bse_fno))) {
            actionBar.changeStatus("fno", nse_fno);
        } else if (Integer.parseInt(hm.get(bse_fno)) <= Integer.parseInt(hm.get(nse_fno))) {
            actionBar.changeStatus("fno", bse_fno);
        }

        if (Integer.parseInt(hm.get(nse_cd)) <= Integer.parseInt(hm.get(bse_cd))) {
            actionBar.changeStatus("cd", nse_cd);
        } else if (Integer.parseInt(hm.get(bse_cd)) <= Integer.parseInt(hm.get(nse_cd))) {
            actionBar.changeStatus("cd", bse_cd);
        }

        if (Integer.parseInt(hm.get(mcx_com)) <= Integer.parseInt(hm.get(ncdex_com))) {
            actionBar.changeStatus("com", mcx_com);
        } else if (Integer.parseInt(hm.get(ncdex_com)) <= Integer.parseInt(hm.get(mcx_com))) {
            actionBar.changeStatus("com", ncdex_com);
        }

        if (AccountDetails.broadcastServerAuthenticated == true) {
            actionBar.setNiftyIcon("green");
        } else if (AccountDetails.broadcastServerAuthenticated == false) {
            actionBar.setNiftyIcon("red");
        }


        if (AccountDetails.orderServerAuthenticated == true) {
            actionBar.setSensexIcon("green");
        } else if (AccountDetails.orderServerAuthenticated == false) {
            actionBar.setSensexIcon("red");
        }


    }


    private String getValidityFromResponse(String Validity, String goodTillDate) {

        String validity = "";
        if (getDayResult(Short.parseShort(Validity)) == 1) {
            validity = "Day";
        } else if (getIOCResult(Short.parseShort(Validity)) == 1) {
            validity = "IOC";
        } else if (getGTCResult(Short.parseShort(Validity)) == 1) {
            validity = "GTC";
        } else if (getEOSResult(Short.parseShort(Validity)) == 1) {
            validity = "EOS";
        } else if (Integer.parseInt(goodTillDate) > 0) {
            validity = "GTD";
        }
        return validity;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MarshMallowPermission.CAMERA_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }

        } else if (requestCode == MarshMallowPermission.VIDEO_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(android.provider.MediaStore.EXTRA_DURATION_LIMIT, 10);
                startActivityForResult(intent, VIDEO_CAPTURED);
            }

        } else if (requestCode == MarshMallowPermission.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                Intent pictureActionIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, GALLERY_PICTURE);
            }
        }


    }


    public void getCurrentFragmentNameFromId() {
        if (AccountDetails.currentFragment == NAV_TO_ORDER_BOOK_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("orderbook").getArguments();
            //AccountDetails.currentFragment=NAV_TO_ORDER_BOOK_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_ORDER_BOOK_DETAILS_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("orderbookdetailsfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_ORDER_BOOK_DETAILS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_TRADE_BOOK_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("tradebookfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_TRADE_BOOK_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_TRADE_BOOK_DETAILS_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("tradebookdetailsfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_TRADE_BOOK_DETAILS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_NETPOSITION_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("netpositionfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_NETPOSITION_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_TRADE_SCREEN) {
//            AccountDetails.globalArg=getCurrentFragmentByName("tradefragment").getArguments();
            AccountDetails.globalArg = AccountDetails.globalArgTradeFrag;
            //AccountDetails.currentFragment=NAV_TO_TRADE_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_ORDER_PREVIEW_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("tradepreviewfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_ORDER_PREVIEW_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_STRATEGY_DATA_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("strategydatalistfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_ORDER_PREVIEW_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_STRATEGY_BUILDUP_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("strategybuildupfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_ORDER_PREVIEW_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_LAS_STEPS_FORMS_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("lasstepsformfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_ORDER_PREVIEW_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_DEMATHOLDING_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("dematholdingfragmentnew").getArguments();
            //AccountDetails.currentFragment=NAV_TO_DEMATHOLDING_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_DEMATHOLDING_SINGLE_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("dematholdingfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_DEMATHOLDING_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_ALERT_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("alertfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_ALERT_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_WATCHLIST_SCREEN_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("portfoliosectionfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_PORTFOLIO_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MARKET_STARTUP_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("marketsfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_MARKET_STARTUP_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_CHAT_MESSAGE_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("chatmessagefragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_MARKET_STARTUP_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_LAS_MESSAG_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("lasfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_MARKET_STARTUP_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_HOLDING_SCREEN) {
            try {
                AccountDetails.globalArg = getCurrentFragmentByName("holdingreportfragment").getArguments();
            } catch (Exception e) {
                e.printStackTrace();
                AccountDetails.globalArg = new HoldingReportFragment().getArguments();
            }
            //AccountDetails.currentFragment=NAV_TO_MARKET_STARTUP_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_NOTIFICATION_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("notificationsfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_MARKET_STARTUP_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MARGIN_SUMMARY) {
            AccountDetails.globalArg = getCurrentFragmentByName("marginfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_MARGIN_SUMMARY;
        } else if (AccountDetails.currentFragment == NAV_TO_MARKET_DEPTH_SCREEN) {

            AccountDetails.globalArg = getCurrentFragmentByName("marketdepthfragment").getArguments();

            //AccountDetails.currentFragment=NAV_TO_MARKET_DEPTH_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_QUOTES_SCREEN) {
            AccountDetails.globalArg = AccountDetails.globalArgQuoteFrag;
//            AccountDetails.globalArg=getCurrentFragmentByName("quotesfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_QUOTES_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_INDICES_STOCK_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("indicesstockfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_INDICES_STOCK_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_SYMBOL_SEARCH_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("symbolsearchfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_SYMBOL_SEARCH_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MYSCRIPTS_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("portfoliosectionfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_EDIT_WATCHLIST_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_WATCHLIST_SCREEN) {

            if (getCurrentFragmentByName("portfoliosectionfragment") != null) {
                AccountDetails.globalArg = getCurrentFragmentByName("portfoliosectionfragment").getArguments();
            } else {
                AccountDetails.globalArg = getCurrentFragmentByName("watchlistfragment").getArguments();
            }

            //AccountDetails.currentFragment=NAV_TO_EDIT_WATCHLIST_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_PORTFOLIO_BOTTOM_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("portfoliobottomfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_EDIT_WATCHLIST_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_ORDER_BOTTOM_FRAGMENT) {
            AccountDetails.globalArg = getCurrentFragmentByName("orderbottomfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_EDIT_WATCHLIST_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_LASTVISITED_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("portfoliosectionfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_EDIT_WATCHLIST_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_EDIT_WATCHLIST_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("editwatchlistfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_EDIT_WATCHLIST_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_NEWS_DISPLAY) {
            AccountDetails.globalArg = getCurrentFragmentByName("newsdisplayfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_NEWS_DISPLAY;
        } else if (AccountDetails.currentFragment == NAV_TO_NEWS_PAGER) {
            AccountDetails.globalArg = getCurrentFragmentByName("newsfragmentpager").getArguments();
            //AccountDetails.currentFragment=NAV_TO_NEWS_DISPLAY;
        } else if (AccountDetails.currentFragment == NAV_TO_RECOMMENDATION_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("recommdisplayfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_RECOMMENDATION_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_FUNDTRANSFER_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("fundtransferfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_EDIS_MARGIN) {
            AccountDetails.globalArg = getCurrentFragmentByName("edismarginpledgereport").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_TRANSFER_PAYING) {
            AccountDetails.globalArg = getCurrentFragmentByName("fundtransferpayoutfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;}

        } /*else if(AccountDetails.currentFragment == NAV_TO_TRANSFER_PAYOUT) {
            AccountDetails.globalArg = getCurrentFragmentByName("fundtransfertabsfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        }*/ else if (AccountDetails.currentFragment == NAV_TO_PENDING_TAB_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("orderbottomfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_EXECUTED_TAB_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("orderbottomfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_REJECTED_TAB_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("orderbottomfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_ORDER_BOTTOM_FRAGMENT) {
            AccountDetails.globalArg = getCurrentFragmentByName("orderbottomfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_EDIS_DASHBOARD_REPORT) {
            AccountDetails.globalArg = getCurrentFragmentByName("edisdashboardreport").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_EDIS_TRANSACTION_DETAILS) {
            AccountDetails.globalArg = getCurrentFragmentByName("edistransactiondetails").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MFUND_DASHBOARD_SCREEN) {
        } else if (AccountDetails.currentFragment == NAV_TO_LOGINPASSWORD) {
            AccountDetails.globalArg = getCurrentFragmentByName("loginpasswordfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_TRANSPASSWORD) {
            AccountDetails.globalArg = getCurrentFragmentByName("transpasswordfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } /*else if(AccountDetails.currentFragment == NAV_TO_NEWS_TAB) {
            AccountDetails.globalArg = getCurrentFragmentByName("newstabfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        }*/ else if (AccountDetails.currentFragment == NAV_TO_BOTTOM_MARKET) {
            try {
                AccountDetails.globalArg = getCurrentFragmentByName("marketBottomfragment").getArguments();
            } catch (Exception e) {

            }            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_USER_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("userprofile").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MFUND_DASHBOARD_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("mfunddashboard").getArguments();
            //AccountDetails.currentFragment=NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_CHANGE_DEFAULTSCREEN_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("appsettingfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHANGEPASSWORD_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_CHANGEPASSWORD_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("changepasswordfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHANGEPASSWORD_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_CONTRACTINFO_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("contractinformationfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CONTRACTINFO_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_CHARTS_SCREEN) {
            AccountDetails.globalArg = AccountDetails.globalArgChartFrag;
            //AccountDetails.globalArg = getCurrentFragmentByName("chartsfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == GREEK_MENU_OPENPOSITION) {
            AccountDetails.globalArg = getCurrentFragmentByName("openpositionfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_CUMULATIVE_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("cumulativepositionfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_FUNDTRANSFERDETAILS_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("fundtransferdetailsfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_BANK_DETAILS_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("bankdetailsscreenfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MFUND_DETAILS_SCREEN) {
            AccountDetails.globalArg = getCurrentFragmentByName("mfdetailsfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MARKET_HOME_SCREEN) {

            AccountDetails.globalArg = getCurrentFragmentByName("dashboardfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_USERCREATION_PAN_DETAILS) {

            AccountDetails.globalArg = getCurrentFragmentByName("usercreationpandetails").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_ACTION) {

            AccountDetails.globalArg = getCurrentFragmentByName("mutualfundfragmentnew").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_TRADE) {

            AccountDetails.globalArg = getCurrentFragmentByName("mutualfundtradefragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_PAN_DETAILS) {

            AccountDetails.globalArg = getCurrentFragmentByName("pandetails").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_PERSONAL_DETAILS) {

            AccountDetails.globalArg = getCurrentFragmentByName("personaldetail").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_ALL_BANKDETAILS_MF) {
            AccountDetails.globalArg = getCurrentFragmentByName("allbankdetailsfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_BANK_MANDATE) {

            AccountDetails.globalArg = getCurrentFragmentByName("bankdetailsmandatefragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_KYC_DETAILS) {

            AccountDetails.globalArg = getCurrentFragmentByName("kycdetails").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_KYC_UPLOAD) {

            AccountDetails.globalArg = getCurrentFragmentByName("kycuploadphoto").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_KYC_PAN_UPLOAD) {

            AccountDetails.globalArg = getCurrentFragmentByName("uploadpandetails").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_KYC_AADHAR_UPLOAD) {

            AccountDetails.globalArg = getCurrentFragmentByName("uploadaadhardetails").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_KYC_BANK_UPLOAD) {

            AccountDetails.globalArg = getCurrentFragmentByName("uploadbankdetails").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_KYC_CHEQUE_UPLOAD) {

            AccountDetails.globalArg = getCurrentFragmentByName("uploadchequedetails").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_KYC_IPVP_UPLOAD) {

            AccountDetails.globalArg = getCurrentFragmentByName("uploadipvp").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_SIGNATURE_UPLOAD) {

            AccountDetails.globalArg = getCurrentFragmentByName("uploadsignature").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_GET_QUOTE) {

            AccountDetails.globalArg = getCurrentFragmentByName("mutualfundgetquotefragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUAL_FUND_STP) {

            AccountDetails.globalArg = getCurrentFragmentByName("mutualfundstpfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUAL_FUND_SIP) {

            AccountDetails.globalArg = getCurrentFragmentByName("mutualfundsipfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUAL_FUND_SWP) {

            AccountDetails.globalArg = getCurrentFragmentByName("mutualfundswpfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUAL_FUND_SIP_SUMMARY) {

            AccountDetails.globalArg = getCurrentFragmentByName("mutualfundsipsummary").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MF_WATCHLIST_MF) {

            AccountDetails.globalArg = getCurrentFragmentByName("mfwatchlistfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MF_ORDER_BOOK_SCREEN) {

            AccountDetails.globalArg = getCurrentFragmentByName("mforderbookfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MF_ORDER_BOOK_DETAILS_SCREEN) {

            AccountDetails.globalArg = getCurrentFragmentByName("mforderbookdetailsfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_CHARTING_SCREEN) {

//            AccountDetails.globalArg = getCurrentFragmentByName("chartingfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MUTUALFUND_TRADE_PREVIEW) {

            AccountDetails.globalArg = getCurrentFragmentByName("mutualfundtradepreview").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_STRATEGY_FINDER) {

            //AccountDetails.globalArg = getCurrentFragmentByName("strategyfinderfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_STRATEGY_FILTER) {

            AccountDetails.globalArg = getCurrentFragmentByName("strategyfilterfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_STRATEGY_OPTION_FILTER) {

            AccountDetails.globalArg = getCurrentFragmentByName("optionfilterfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_STRATEGY_SCAN) {

            AccountDetails.globalArg = getCurrentFragmentByName("scanfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        } else if (AccountDetails.currentFragment == NAV_TO_MARKET_EDIT_FRAGMENT) {

            AccountDetails.globalArg = getCurrentFragmentByName("marketbottomfragment").getArguments();
            //AccountDetails.currentFragment=NAV_TO_CHARTS_SCREEN;
        }

    }


    public void getCurrentFragment() {
        if (getCurrentFragmentByName("") instanceof OrderBookFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_ORDER_BOOK_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof OrderBookDetailsFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_ORDER_BOOK_DETAILS_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof TradeBookFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_TRADE_BOOK_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof TradeBookDetailsFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_TRADE_BOOK_DETAILS_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof NetPositionFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_NETPOSITION_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof TradeFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_TRADE_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof TradePreviewFragment) {
            AccountDetails.currentFragment = NAV_TO_ORDER_PREVIEW_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof StrategyDataListFragment) {
            AccountDetails.currentFragment = NAV_TO_STRATEGY_DATA_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof StrategyBuildUpFragment) {
            AccountDetails.currentFragment = NAV_TO_STRATEGY_BUILDUP_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof LasStepsFormFragment) {
            AccountDetails.currentFragment = NAV_TO_LAS_STEPS_FORMS_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof DematHoldingFragmentNew) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_DEMATHOLDING_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof DematHoldingFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_DEMATHOLDING_SINGLE_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof AlertFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_ALERT_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof TradePreviewFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_ORDER_BOOK_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof PortfolioSectionFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_WATCHLIST_SCREEN_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof MarketsFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_MARKET_STARTUP_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof ChatMessageFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_CHAT_MESSAGE_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof MarginFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_MARGIN_SUMMARY;
        } else if (getCurrentFragmentByName("") instanceof MarketDepthFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_MARKET_DEPTH_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof LASFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_LAS_MESSAG_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof HoldingReportFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_HOLDING_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof QuotesFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_QUOTES_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof IndicesStockFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_INDICES_STOCK_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof SymbolSearchFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_SYMBOL_SEARCH_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof SnapshotFloatingFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_MYSCRIPTS_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof WatchListFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_WATCHLIST_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof PortfolioBottomFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_PORTFOLIO_BOTTOM_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof OrderBottomFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_ORDER_BOTTOM_FRAGMENT;
        } else if (getCurrentFragmentByName("") instanceof LastVisitedFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_LASTVISITED_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof EditWatchlistFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_EDIT_WATCHLIST_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof NewsDisplayFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_NEWS_DISPLAY;
        } else if (getCurrentFragmentByName("") instanceof NewsFragmentPager) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_NEWS_PAGER;
        } else if (getCurrentFragmentByName("") instanceof RecommDisplayFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_RECOMMENDATION_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof FundDisplayFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_FUNDTRANSFER_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof FundTransfer_payout_fragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_TRANSFER_PAYOUT;
        } else if (getCurrentFragmentByName("") instanceof EdisMarginPledgeReport) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_EDIS_MARGIN;
        } else if (getCurrentFragmentByName("") instanceof FundTransfer_tabs_Fragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_TRANSFER_PAYING;
        } else if (getCurrentFragmentByName("") instanceof PendingTabFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_PENDING_TAB_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof ExecutedTabFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_EXECUTED_TAB_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof RejectedTabFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_REJECTED_TAB_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof CancelledTabFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_CANCELLED_TAB_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof MFundDashboard) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();

        } else if (getCurrentFragmentByName("") instanceof LoginPasswordFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_LOGINPASSWORD;
        } else if (getCurrentFragmentByName("") instanceof TransPasswordFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_TRANSPASSWORD;
        } else if (getCurrentFragmentByName("") instanceof NewsTabFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_NEWS_TAB;
        } else if (getCurrentFragmentByName("") instanceof UserProfile) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_USER_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof MFundDashboard) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_MFUND_DASHBOARD_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof EdisDashboardReport) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_EDIS_DASHBOARD_REPORT;
        } else if (getCurrentFragmentByName("") instanceof EdisTransactionDetails) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_EDIS_TRANSACTION_DETAILS;
        } else if (getCurrentFragmentByName("") instanceof AlertFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_ALERT_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof AppSettingFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_CHANGE_DEFAULTSCREEN_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof ChangePasswordFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_CHANGEPASSWORD_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof ContractInformationFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_CONTRACTINFO_SCREEN;
        } else if (getCurrentFragmentByName("") instanceof ChartsFragment) {
            AccountDetails.globalArg = getCurrentFragmentByName("").getArguments();
            AccountDetails.currentFragment = NAV_TO_CHARTS_SCREEN;
        }


    }


    public void refreshFragment() {

        /*if(AccountDetails.currentFragment == NAV_TO_ORDER_BOOK_SCREEN) {
            setupFragment(new OrderBookFragment());
        } else if(AccountDetails.currentFragment == NAV_TO_ORDER_BOOK_DETAILS_SCREEN) {
            setupFragment(new OrderBookFragment());

        } else if(AccountDetails.currentFragment == NAV_TO_NETPOSITION_SCREEN) {
            performMenuAction("GREEK_MENU_NETPOSITION_TXT");

        } else if(AccountDetails.currentFragment == NAV_TO_TRADE_BOOK_SCREEN) {
            setupFragment(new TradeBookFragment());
        } else if(AccountDetails.currentFragment == NAV_TO_DEMATHOLDING_SCREEN) {
            setupFragment(new DematHoldingFragmentNew());
        } else if(AccountDetails.currentFragment == NAV_TO_ALERT_SCREEN) {
            setupFragment(new AlertFragment());
        } else if(AccountDetails.currentFragment == NAV_TO_CHAT_MESSAGE_SCREEN) {
            setupFragment(new ChatMessageFragment());
        } else if(AccountDetails.currentFragment == NAV_TO_RECOMMENDATION_SCREEN) {
            setupFragment(new RecommDisplayFragment());
        } else if(AccountDetails.currentFragment == NAV_TO_NOTIFICATION_SCREEN) {
            setupFragment(new NotificationsFragment());
        }*/

        if (AccountDetails.currentFragment == NAV_TO_PENDING_TAB_SCREEN) {
            Bundle args = new Bundle();
            OrderBottomFragment orderBottomFragment = new OrderBottomFragment();
            args.putString("FromTab", "PendingTab");
            orderBottomFragment.setArguments(args);
            setupFragment(orderBottomFragment);
        } else if (AccountDetails.currentFragment == NAV_TO_EXECUTED_TAB_SCREEN) {
            Bundle args = new Bundle();
            OrderBottomFragment orderBottomFragment = new OrderBottomFragment();
            args.putString("FromTab", "ExecutedTab");
            orderBottomFragment.setArguments(args);
            setupFragment(orderBottomFragment);
        } else if (AccountDetails.currentFragment == NAV_TO_REJECTED_TAB_SCREEN) {
            Bundle args = new Bundle();
            OrderBottomFragment orderBottomFragment = new OrderBottomFragment();
            args.putString("FromTab", "RejectedTab");
            orderBottomFragment.setArguments(args);
            setupFragment(orderBottomFragment);
        } else if (AccountDetails.currentFragment == NAV_TO_CANCELLED_TAB_SCREEN) {
            Bundle args = new Bundle();
            OrderBottomFragment orderBottomFragment = new OrderBottomFragment();
            args.putString("FromTab", "CancelledTab");
            orderBottomFragment.setArguments(args);
            setupFragment(orderBottomFragment);
        } else if (AccountDetails.currentFragment == NAV_TO_NOTIFICATION_SCREEN) {
            setupFragment(new NotificationsFragment());
        }

    }


    public void onEventMainThread(OrderStreamingAuthResponse response) {
        //Log.e(this.getClass().getName(), "gcm requesttttttttttttttttttttttttttttttttttttttt");
        try {
            if (response.getError_code().equals("0")) {

                AccountDetails.setIsIrisConnected(true);
                AccountDetails.setIris_LoginCounter(1);
                AccountDetails.setHeartBeatIrisCount(0);


                AccountDetails.orderServerAuthenticated = true;
                actionBar.setSensexIcon("green");
                if (USER_TYPE == USER.OPENUSER) {
                    orderStreamingController.sendStreamingGCMInfoRequest(getApplicationContext(), AccountDetails.getToken(getApplicationContext()), AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), "true");
                } else {
                    orderStreamingController.sendStreamingGCMInfoRequest(getApplicationContext(), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), "true");
                }

            } else {
                AccountDetails.setIsIrisConnected(false);
                boolean LogoutStatus = Util.getPrefs(getApplicationContext()).getBoolean("LogoutStatus", false);

                if (!LogoutStatus) {
//                    doLogout(0);   0032960: In andriod,Retailer is getting logged out in following case
                }
                actionBar.setSensexIcon("red");
                EventBus.getDefault().post("Socket IRIS Reconnect Attempts exceeds");

            }


        } catch (Exception e) {
            Log.e("Login Failure :", e.getMessage());
        }
    }

    public void onEventMainThread(StreamingAuthResponse response) {
        //Log.e(this.getClass().getName(), "gcm requesttttttttttttttttttttttttttttttttttttttt");
        try {
            if (response.getError_code().equals("0")) {

                AccountDetails.setIsApolloConnected(true);
                AccountDetails.setApollo_LoginCounter(1);
                AccountDetails.setHeartBeatApolloCount(0);


                /*if (snackbarApollo != null && snackbarApollo.isShown()) {
                    snackbarApollo.dismiss();
                }*/


                AccountDetails.broadcastServerAuthenticated = true;
                actionBar.setNiftyIcon("green");

                if (getStreamingList().containsKey("ltpinfo")) {
                    streamingController.sendStreamingRequest(getApplication(), getStreamingList().get("ltpinfo"), "ltpinfo", null, null, false);
                }
                if (getStreamingList().containsKey("ohlc")) {
                    streamingController.sendStreamingRequest(getApplication(), getStreamingList().get("ohlc"), "ohlc", null, null, false);
                }
                if (getStreamingList().containsKey("marketpicture")) {
                    streamingController.sendStreamingRequest(getApplication(), getStreamingList().get("marketpicture"), "marketpicture", null, null, false);
                }
                if (getStreamingList().containsKey("touchline")) {
                    streamingController.sendStreamingRequest(getApplication(), getStreamingList().get("touchline"), "touchline", null, null, false);
                }
                if (getStreamingList().containsKey("index")) {
                    streamingController.sendStreamingRequest(getApplication(), getStreamingList().get("index"), "index", null, null, false);
                }
            } else {
                AccountDetails.setIsApolloConnected(false);

                boolean LogoutStatus = Util.getPrefs(getApplicationContext()).getBoolean("LogoutStatus", false);

                if (!LogoutStatus) {
                   // doLogout(0);  0032960: In andriod,Retailer is getting logged out in following case
                }

                actionBar.setNiftyIcon("red");
                EventBus.getDefault().post("Socket Apollo Reconnect Attempts exceeds");


            }

        } catch (Exception e) {
            Log.e("Login Failure :", e.getMessage());
        }
    }

    //TOhandle heart beat broadcast
    public void onEventMainThread(HeartBeatStreamingResponse response) {
        //Log.e(this.getClass().getName(), "Heart Beatttttttt Apollo");
        AccountDetails.setHeartBeatApolloCount(0);
        try {
            if (response.getError_code().equals("0")) {
            } else if (response.getError_code().equals("1")) {


                Toast.makeText(getApplicationContext(), "Apollo Invalid Session.", Toast.LENGTH_LONG).show();
                if (!AccountDetails.isIsLogout()) {
                    doLogout(0);
                }
            } else if (response.getError_code().equals("2")) {

                Toast.makeText(getApplicationContext(), "Apollo Session Expired.", Toast.LENGTH_LONG).show();
                if (!AccountDetails.isIsLogout()) {
                    doLogout(0);
                }
            }

        } catch (Exception e) {
            Log.e("Login Failure :", e.getMessage());
        }
    }

    //TODO: To handle broadcast response from apollo for guest login
    public void onEventMainThread(GuestLoginStreamingResponse guestLoginStreamingResponse) {

        try {
            if (guestLoginStreamingResponse.getError_code().equals("0")) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: iris guest login
    public void onEventMainThread(GuestLoginOrderStreamingResponse guestLoginOrderStreamingResponse) {

        try {
            if (guestLoginOrderStreamingResponse.getErrorCode().equals("0")) {
                //Log.e("Iris", "Guest Login Successfull");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEventMainThread(HeartBeatOrderResponse response) {
        Log.e("HeartBEAT", "Heart Beatttttttt Iris recieved" + AccountDetails.getHeartBeatIrisCount());
        AccountDetails.setHeartBeatIrisCount(0);

        try {
            if (response.getErrorCode().equals("0")) {

            } else if (response.getErrorCode().equals("1")) {

                Toast.makeText(getApplicationContext(), "Iris Invalid Session.", Toast.LENGTH_LONG).show();
                doLogout(0);
            } else if (response.getErrorCode().equals("2")) {
                Toast.makeText(getApplicationContext(), "Iris Session Expired.", Toast.LENGTH_LONG).show();
                doLogout(0);
            }
        } catch (Exception e) {
            Log.e("Login Failure :", e.getMessage());
        }
    }

    @SuppressLint("RestrictedApi")
    public void onEventMainThread(String error) {

        if (error.contains("SIP") || error.contains("Lumpsum") || error.contains("SWP") || error.contains("STP")) {

            String schemeName = "", alertMessage = "";

            String[] message = error.split("\\|");

            schemeName = message[0];
            alertMessage = message[1];

            GreekDialog.alertDialog(GreekBaseActivity.this, 0, schemeName, alertMessage, "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    refreshFragment();
                }
            });


        } else if (error.equalsIgnoreCase("Failed")) {
            actionBar.setNiftyIcon("red");
            AccountDetails.broadcastServerAuthenticated = false;

        } else if (error.equalsIgnoreCase("Order Failed")) {
            actionBar.setSensexIcon("red");
            AccountDetails.orderServerAuthenticated = false;

        } else if (error.equalsIgnoreCase("Force Stop")) {
            actionBar.setNiftyIcon("red");
            AccountDetails.broadcastServerAuthenticated = false;

        } else if (error.equalsIgnoreCase("Order Force Stop")) {
            actionBar.setSensexIcon("red");
            AccountDetails.orderServerAuthenticated = false;

        } else if (error.equalsIgnoreCase("SessionTimeOut")) {
            doLogout(0);
        } else if (error.equalsIgnoreCase("forcelogoff")) {

            doLogout(FORCELOGOFF);

        } else if (error.equalsIgnoreCase("orderforcelogoff")) {
            doLogout(ORDERFORCELOGOFF);

        } else if (error.equalsIgnoreCase("on")) {

            updateUI("on");
        } else if (error.equalsIgnoreCase("Weak")) {

            // snackbarWeakNet.show();

        } else if (error.equalsIgnoreCase("Strong")) {

            //snackbarWeakNet.dismiss();

        } else if (error.equalsIgnoreCase("off")) {
            updateUI("off");
        } else if (error.equalsIgnoreCase("orderconnectionfailed")) {
            Toast.makeText(getApplicationContext(), "Order server not connected", Toast.LENGTH_LONG).show();
            GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Order server not connected", "OK", false, new DialogListener() {

                @Override
                public void alertDialogAction(Action action, Object... data) {

                }
            });
        } else if (error.equalsIgnoreCase("NotificationIcon")) {
            movablefab.setVisibility(View.GONE);
        } else if (error.equalsIgnoreCase("placeorder")) {
            showdissmisssheet();
        } else if (error.equalsIgnoreCase("orderModifyCancel")) {
            showOrderPreviewSheet();
        } else if (error.equalsIgnoreCase("NetpositionOption")) {
            showNetpositionSheet();
        } else if (error.equalsIgnoreCase("HoldingDetail")) {
            showHoldingDetail();
        } else if (error.equalsIgnoreCase("Do Logout")) {
            doLogout(0);
        } else if (error.equalsIgnoreCase("Network congestion")) {

            GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "It is taking more than expected time.Please check the connection.", "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                }
            });
        } else if (error.equalsIgnoreCase("Socket Apollo Reconnect Attempts exceeds")) {

            /*if (!AccountDetails.isIsApolloConnected() && !AccountDetails.isIsIrisConnected()) {
                snackbarIrisApolloOff.show();
                return;
            }*/

            //snackbarApollo.show();

        } else if (error.equalsIgnoreCase("Socket IRIS Reconnect Attempts exceeds")) {

            /*if (!AccountDetails.isIsApolloConnected() && !AccountDetails.isIsIrisConnected()) {
                snackbarIrisApolloOff.show();
                return;
            }*/
            //snackbarIris.show();
        }

        if (error.equalsIgnoreCase("UpdateActionBarIndices")) {
            sendIndianIndicesRequestForUser();
        }


    }


    public void onEventMainThread(final StreamerOrderRejectionResponse rejectionResponse) {
        try {


            String statusCode, reJSymbol;
            String validity = "";

            if (getExchange(rejectionResponse.getGtoken()).equalsIgnoreCase("mcx")) {

//                reJSymbol = rejectionResponse.getTradeSymbol() + "-" + rejectionResponse.getInstrument();


                if (rejectionResponse.getOptionType().equalsIgnoreCase("XX")) {
                    reJSymbol = rejectionResponse.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(rejectionResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-M" + " - " + rejectionResponse.getInstrument();

                } else {

                    reJSymbol = rejectionResponse.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(rejectionResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + rejectionResponse.getStrikePrice() + "" + rejectionResponse.getOptionType() + "-M" + " - " + rejectionResponse.getInstrument();

                }

            } else {
                reJSymbol = rejectionResponse.getSymbol();
            }

            //                        EDITED BY SUSHANT
//          MANIPULATION OF QUANTITY ACCORDING TO LOT WISE FOR MCX ONLY
            if (getExchange(rejectionResponse.getGtoken()).equalsIgnoreCase("mcx") || getExchange(rejectionResponse.getGtoken()).equalsIgnoreCase("ncdex")) {

                rejectionResponse.setQty(String.valueOf((Integer.parseInt(rejectionResponse.getQty())) / (Integer.parseInt(rejectionResponse.getRegular_lot()))));
                rejectionResponse.setPending_qty(String.valueOf((Integer.parseInt(rejectionResponse.getPending_qty())) / (Integer.parseInt(rejectionResponse.getRegular_lot()))));
            }

            if (rejectionResponse.getCode().equalsIgnoreCase(String.valueOf(0)) || rejectionResponse.getCode().equalsIgnoreCase("201") || rejectionResponse.getCode().equalsIgnoreCase("202") || rejectionResponse.getCode().equalsIgnoreCase("101") || rejectionResponse.getCode().equalsIgnoreCase("102") || rejectionResponse.getCode().equalsIgnoreCase("502") || rejectionResponse.getCode().equalsIgnoreCase("1302") || rejectionResponse.getCode().equalsIgnoreCase("1301")) {
                statusCode = "Success";
            } else if (getExchange(rejectionResponse.getGtoken()).equalsIgnoreCase("ncdex")) {
                statusCode = "Error Code : " + rejectionResponse.getCode() + "\n Reason : " + rejectionResponse.getReason();
            } else {
                statusCode = getErrorMessage(getMarketId(rejectionResponse.getGtoken()), rejectionResponse.getCode());
            }

            if (getDayResult(Short.parseShort(rejectionResponse.getValidity())) == 1) {
                validity = "Day";
            } else if (getIOCResult(Short.parseShort(rejectionResponse.getValidity())) == 1) {
                validity = "IOC";
            } else if (getGTCResult(Short.parseShort(rejectionResponse.getValidity())) == 1) {
                validity = "GTC";
            } else if (getEOSResult(Short.parseShort(rejectionResponse.getValidity())) == 1) {
                validity = "EOS";
            } else if (Integer.parseInt(rejectionResponse.getGoodTillDate()) > 0) {
                validity = "GTD";
            }

            String expirydateText = "";
            if (!rejectionResponse.getExpiryDate().equalsIgnoreCase("0")) {
                expirydateText = "\n Expiry Date:" + DateTimeFormatter.getDateFromTimeStamp(rejectionResponse.getExpiryDate(), "dd MMM yyyy", "bse");
            }

            GreekDialog.dismissDialog();
            String ltQty;

//
//            if(rejectionResponse.getExchange().equalsIgnoreCase("ncdex"))
//            ltQty = rejectionResponse.getQty();
//


            if (((Integer.valueOf(rejectionResponse.getGtoken()) >= 502000000) && ((Integer.valueOf(rejectionResponse.getGtoken()) <= 502999999)) || ((Integer.valueOf(rejectionResponse.getGtoken()) >= 1302000000) && (Integer.valueOf(rejectionResponse.getGtoken()) <= 1302999999)))) {
                GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "State: " + getOrderState(rejectionResponse.getOrder_state()) + " Order Rejected" + " \n" + reJSymbol + " \n Exchange: " + getExchange(rejectionResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(rejectionResponse.getGtoken()) + "\n Ref. No : " + rejectionResponse.getGorderid() + "\n Exchange Order No: " + rejectionResponse.getEorderid() + expirydateText + "\n Product:" + getProductType(rejectionResponse.getProduct()) + "\nPrice: " + String.format("%.4f", Double.parseDouble(rejectionResponse.getPrice())) + "\nQty: " + rejectionResponse.getQty() + "\nPending Quantity: " + rejectionResponse.getPending_qty() + "\nOrder Type: " + getOrderType(rejectionResponse.getOrder_type()) + "\nSide: " + getSide(rejectionResponse.getSide()) + "\nValidity: " + validity + "\n" + statusCode, "OK", false, new DialogListener() {

                    @Override
                    public void alertDialogAction(Action action, Object... data) {
                        refreshFragment();
                        if (rejectionResponse.getOrder_state().equalsIgnoreCase("1")) {
                            setupFragment(new OrderBottomFragment());
                        }
                    }
                });
            } else {
                GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "State: " + getOrderState(rejectionResponse.getOrder_state()) + " Order Rejected" + " \n" + reJSymbol + " \n Exchange: " + getExchange(rejectionResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(rejectionResponse.getGtoken()) + "\n Ref. No : " + rejectionResponse.getGorderid() + "\n Exchange Order No: " + rejectionResponse.getEorderid() + expirydateText + "\n Product:" + getProductType(rejectionResponse.getProduct()) + "\nPrice: " + String.format("%.2f", Double.parseDouble(rejectionResponse.getPrice())) + "\nQty: " + rejectionResponse.getQty() + "\nPending Quantity: " + rejectionResponse.getPending_qty() + "\nOrder Type: " + getOrderType(rejectionResponse.getOrder_type()) + "\nSide: " + getSide(rejectionResponse.getSide()) + "\nValidity: " + validity + "\n" + statusCode, "OK", false, new DialogListener() {

                    @Override
                    public void alertDialogAction(Action action, Object... data) {
                        refreshFragment();
                        if (rejectionResponse.getOrder_state().equalsIgnoreCase("1")) {
                            setupFragment(new OrderBottomFragment());
                        }
                    }
                });
            }


            //Only Vibrate/Play Sound if Setting On
            if (Util.getPrefs(this).getBoolean("GREEK_BEEP_VIBRATE_acumengroupGLE", true)) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(GreekBaseActivity.this, notification);
                r.play();
                Vibrator v = (Vibrator) GreekBaseActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //generateNoteOnSd(e.toString() + "-" + rejectionResponse);
        }
    }

    public String getSide(String side) {
        if (side.equals("1")) {
            return "BUY";
        } else if (side.equals("2")) {
            return "SELL";
        }
        return "";
    }

    public String getOrderType(String orderType) {
        if (orderType.equals("1")) {
            return "Limit";
        }
        if (orderType.equals("2")) {
            return "Market";
        }
        if (orderType.equals("3")) {
            return "Stop Loss";
        }
        if (orderType.equals("4")) {
            return "StopLoss Market";
        }
        if (orderType.equals("5")) {
            return "Cover";
        }
        if (orderType.equals("6")) {
            return "After Market";
        }
        if (orderType.equals("7")) {
            return "Bracket";
        }
        return "";
    }

    public void onEventMainThread(final StreamerRmsRejectionResponse rmsRejectionResponse) {

        try {
            GreekDialog.dismissDialog();
            String Message, rmsJSymbol;

            String expirydateText = "";
            if (!rmsRejectionResponse.getExpiryDate().equalsIgnoreCase("") && !rmsRejectionResponse.getExpiryDate().equalsIgnoreCase("0")) {
                expirydateText = "\n Expiry Date:" + DateTimeFormatter.getDateFromTimeStamp(rmsRejectionResponse.getExpiryDate(), "dd MMM yyyy", "bse");
            }

            if (getExchange(rmsRejectionResponse.getGtoken()).equalsIgnoreCase("mcx")) {
//                rmsJSymbol = rmsRejectionResponse.getTradeSymbol() + "-" + rmsRejectionResponse.getInstrument();


                if (rmsRejectionResponse.getOptionType().equalsIgnoreCase("XX")) {
                    rmsJSymbol = rmsRejectionResponse.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(rmsRejectionResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-M" + " - " + rmsRejectionResponse.getInstrument();

                } else {

                    rmsJSymbol = rmsRejectionResponse.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(rmsRejectionResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + rmsRejectionResponse.getStrikePrice() + "" + rmsRejectionResponse.getOptionType() + "-M" + " - " + rmsRejectionResponse.getInstrument();

                }


            } else {
                rmsJSymbol = rmsRejectionResponse.getSymbol();
            }


            GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Status: " + rmsJSymbol + " is " + rmsRejectionResponse.getOrder_status() + "\n Mkt.Segment: " + getAssetType(rmsRejectionResponse.getGtoken()) + "\n Ref. No : " + rmsRejectionResponse.getGorderid() + expirydateText + "\n Reason:" + rmsRejectionResponse.getReason(), "OK", false, new DialogListener() {

                @Override
                public void alertDialogAction(Action action, Object... data) {
                    refreshFragment();
                    exchangeToken = rmsRejectionResponse.getGtoken();
                    if (AccountDetails.getIsEDISProduct().equalsIgnoreCase("true") && rmsRejectionResponse.getCode().equalsIgnoreCase("249")) {

                        showPoaPopup();

                    }
                }
            });

            //Only Vibrate/Play Sound if Setting On
            if (Util.getPrefs(this).getBoolean("GREEK_BEEP_VIBRATE_acumengroupGLE", true)) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(GreekBaseActivity.this, notification);
                r.play();
                Vibrator v = (Vibrator) GreekBaseActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //generateNoteOnSd(e.toString() + "-" + rmsRejectionResponse);
        }


    }


    public void onEventMainThread(final StreamerTriggerResponse streamerTriggerResponse) {
        try {
            GreekDialog.dismissDialog();

            String confirmSymbol;

            if (getExchange(streamerTriggerResponse.getGtoken()).equalsIgnoreCase("mcx")) {
//                confirmSymbol = streamerTriggerResponse.getTradeSymbol() + "-" + streamerTriggerResponse.getInstrument();


                if (streamerTriggerResponse.getOptionType().equalsIgnoreCase("XX")) {
                    confirmSymbol = streamerTriggerResponse.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(streamerTriggerResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-M" + " - " + streamerTriggerResponse.getInstrument();

                } else {

                    confirmSymbol = streamerTriggerResponse.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(streamerTriggerResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + streamerTriggerResponse.getStrikePrice() + "" + streamerTriggerResponse.getOptionType() + "-M" + " - " + streamerTriggerResponse.getInstrument();

                }


            } else {
                confirmSymbol = streamerTriggerResponse.getSymbol();
            }


//                        EDITED BY SUSHANT
//          MANIPULATION OF QUANTITY ACCORDING TO LOT WISE FOR MCX ONLY
            if (getExchange(streamerTriggerResponse.getGtoken()).equalsIgnoreCase("mcx") || getExchange(streamerTriggerResponse.getGtoken()).equalsIgnoreCase("ncdex")) {
                streamerTriggerResponse.setQty(String.valueOf((Integer.parseInt(streamerTriggerResponse.getQty())) / (Integer.parseInt(streamerTriggerResponse.getRegular_lot()))));
                streamerTriggerResponse.setPending_qty(String.valueOf((Integer.parseInt(streamerTriggerResponse.getPending_qty())) / (Integer.parseInt(streamerTriggerResponse.getRegular_lot()))));
                streamerTriggerResponse.setTraded_qty(String.valueOf((Integer.parseInt(streamerTriggerResponse.getTraded_qty())) / (Integer.parseInt(streamerTriggerResponse.getRegular_lot()))));
            }
            String expirydateText = "";
            if (!streamerTriggerResponse.getExpiryDate().equalsIgnoreCase("0")) {
                expirydateText = "\n Expiry Date:" + DateTimeFormatter.getDateFromTimeStamp(streamerTriggerResponse.getExpiryDate(), "dd MMM yyyy", "bse");
            }
            if (((Integer.valueOf(streamerTriggerResponse.getGtoken()) >= 502000000) && ((Integer.valueOf(streamerTriggerResponse.getGtoken()) <= 502999999)) || ((Integer.valueOf(streamerTriggerResponse.getGtoken()) >= 1302000000) && (Integer.valueOf(streamerTriggerResponse.getGtoken()) <= 1302999999)))) {
                //GreekDialog.alertDialog(GreekBaseActivity.this, ALERTS_ACTION_ID, GREEK, "Status: " + streamerTriggerResponse.getSymbol() + " is " + streamerTriggerResponse.getOrder_status() + " \n Exchange: " + getExchange(streamerTriggerResponse.getGtoken())+"\nAction : "+getSide(streamerTriggerResponse.getSide())+"\nTraded Qty: "+streamerTriggerResponse.getTraded_qty()+"\nTrigger Price : "+String.format("%.4f", Double.parseDouble(streamerTriggerResponse.getTrigger_price()))+"\nTraded Price: "+String.format("%.4f", Double.parseDouble(streamerTriggerResponse.getTraded_price()))+"\nPending Qty: "+streamerTriggerResponse.getPending_qty()+"\nTotal Order Qty: "+streamerTriggerResponse.getQty(), "Ok", false, null);
                GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Status: " + confirmSymbol + " is " + streamerTriggerResponse.getOrder_status() + " \n Exchange: " + getExchange(streamerTriggerResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(streamerTriggerResponse.getGtoken()) + "\n Ref. No : " + streamerTriggerResponse.getGorderid() + "\n Exchange Order No: " + streamerTriggerResponse.getEorderid() + expirydateText + "\n Product:" + getProductType(streamerTriggerResponse.getProduct()) + "\nAction : " + getSide(streamerTriggerResponse.getSide()) + "\nTraded Qty: " + streamerTriggerResponse.getTraded_qty() + "\nTrigger Price : " + String.format("%.4f", Double.parseDouble(streamerTriggerResponse.getTrigger_price())) + "\nTraded Price: " + String.format("%.4f", Double.parseDouble(streamerTriggerResponse.getTraded_price())) + "\nPending Qty: " + streamerTriggerResponse.getPending_qty() + "\nTotal Order Qty: " + streamerTriggerResponse.getQty() + "\nValidity: " + getValidityFromResponse(streamerTriggerResponse.getValidity(), streamerTriggerResponse.getGoodTillDate()), "OK", false, new DialogListener() {

                    @Override
                    public void alertDialogAction(Action action, Object... data) {
                        refreshFragment();
                        if (streamerTriggerResponse.getOrder_state().equalsIgnoreCase("1")) {
                            setupFragment(new OrderBottomFragment());
                        }
                    }
                });
            } else {
                //GreekDialog.alertDialog(GreekBaseActivity.this, ALERTS_ACTION_ID, GREEK, "Status: " + streamerTriggerResponse.getSymbol() + " is " + streamerTriggerResponse.getOrder_status() + " \n Exchange: " + getExchange(streamerTriggerResponse.getGtoken())+"\nAction : "+getSide(streamerTriggerResponse.getSide())+"\nTraded Qty: "+streamerTriggerResponse.getTraded_qty()+"\nTrigger Price : "+String.format("%.2f", Double.parseDouble(streamerTriggerResponse.getTrigger_price()))+"\nTraded Price: "+String.format("%.2f", Double.parseDouble(streamerTriggerResponse.getTraded_price()))+"\nPending Qty: "+streamerTriggerResponse.getPending_qty()+"\nTotal Order Qty: "+streamerTriggerResponse.getQty(), "Ok", false, null);
                GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Status: " + confirmSymbol + " is " + streamerTriggerResponse.getOrder_status() + " \n Exchange: " + getExchange(streamerTriggerResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(streamerTriggerResponse.getGtoken()) + "\n Ref. No : " + streamerTriggerResponse.getGorderid() + "\n Exchange Order No: " + streamerTriggerResponse.getEorderid() + expirydateText + "\n Product:" + getProductType(streamerTriggerResponse.getProduct()) + "\nAction : " + getSide(streamerTriggerResponse.getSide()) + "\nTraded Qty: " + streamerTriggerResponse.getTraded_qty() + "\nTrigger Price : " + String.format("%.2f", Double.parseDouble(streamerTriggerResponse.getTrigger_price())) + "\nTraded Price: " + String.format("%.2f", Double.parseDouble(streamerTriggerResponse.getTraded_price())) + "\nPending Qty: " + streamerTriggerResponse.getPending_qty() + "\nTotal Order Qty: " + streamerTriggerResponse.getQty() + "\nValidity: " + getValidityFromResponse(streamerTriggerResponse.getValidity(), streamerTriggerResponse.getGoodTillDate()), "OK", false, new DialogListener() {

                    @Override
                    public void alertDialogAction(Action action, Object... data) {
                        refreshFragment();
                        if (streamerTriggerResponse.getOrder_state().equalsIgnoreCase("1")) {
                            setupFragment(new OrderBottomFragment());
                        }
                    }
                });

            }

        } catch (Exception e) {
        }
    }


    public void onEventMainThread(final StreamerTradeResponse streamerTradeResponse) {
        try {
            GreekDialog.dismissDialog();
            String tradSymbol;

            if (getExchange(streamerTradeResponse.getGtoken()).equalsIgnoreCase("mcx")) {

//                tradSymbol = streamerTradeResponse.getTradeSymbol() + "-" + streamerTradeResponse.getInstrument();


                if (streamerTradeResponse.getOptionType().equalsIgnoreCase("XX")) {
                    tradSymbol = streamerTradeResponse.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(streamerTradeResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-M" + " - " + streamerTradeResponse.getInstrument();

                } else {

                    tradSymbol = streamerTradeResponse.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(streamerTradeResponse.getExpiryDate(), "yyMMM", "bse").toUpperCase() + streamerTradeResponse.getStrikePrice() + "" + streamerTradeResponse.getOptionType() + "-M" + " - " + streamerTradeResponse.getInstrument();

                }

            } else {

                tradSymbol = streamerTradeResponse.getSymbol();
            }

//          EDITED BY SUSHANT
//          MANIPULATION OF QUANTITY ACCORDING TO LOT WISE FOR MCX ONLY
            if (getExchange(streamerTradeResponse.getGtoken()).equalsIgnoreCase("mcx") || getExchange(streamerTradeResponse.getGtoken()).equalsIgnoreCase("ncdex")) {
                streamerTradeResponse.setQty(String.valueOf((Integer.parseInt(streamerTradeResponse.getQty())) / (Integer.parseInt(streamerTradeResponse.getRegular_lot()))));
                streamerTradeResponse.setPending_qty(String.valueOf((Integer.parseInt(streamerTradeResponse.getPending_qty())) / (Integer.parseInt(streamerTradeResponse.getRegular_lot()))));
                streamerTradeResponse.setTraded_qty(String.valueOf((Integer.parseInt(streamerTradeResponse.getTraded_qty())) / (Integer.parseInt(streamerTradeResponse.getRegular_lot()))));
            }

            String expirydateText = "";
            if (!streamerTradeResponse.getExpiryDate().equalsIgnoreCase("0")) {
                expirydateText = "\n Expiry Date:" + DateTimeFormatter.getDateFromTimeStamp(streamerTradeResponse.getExpiryDate(), "dd MMM yyyy", "bse");
            }

            if (getOrderType(streamerTradeResponse.getOrder_type()).equalsIgnoreCase("Stop Loss") || getOrderType(streamerTradeResponse.getOrder_type()).equalsIgnoreCase("StopLoss Market")) {
                if (((Integer.valueOf(streamerTradeResponse.getGtoken()) >= 502000000) && ((Integer.valueOf(streamerTradeResponse.getGtoken()) <= 502999999)) || ((Integer.valueOf(streamerTradeResponse.getGtoken()) >= 1302000000) && (Integer.valueOf(streamerTradeResponse.getGtoken()) <= 1302999999)))) {
                    GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Status: " + tradSymbol + " is " + streamerTradeResponse.getOrder_status() + " \n Exchange: " + getExchange(streamerTradeResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(streamerTradeResponse.getGtoken()) + "\n Ref. No : " + streamerTradeResponse.getGorderid() + "\n Exchange Order No: " + streamerTradeResponse.getEorderid() + "\n Trade No: " + streamerTradeResponse.getTradeid() + expirydateText + "\n Product:" + getProductType(streamerTradeResponse.getProduct()) + "\nAction : " + getSide(streamerTradeResponse.getSide()) + "\nTraded Qty: " + streamerTradeResponse.getTraded_qty() + "\nTrigger Price : " + String.format("%.4f", Double.parseDouble(streamerTradeResponse.getTrigger_price())) + "\nTraded Price: " + String.format("%.4f", Double.parseDouble(streamerTradeResponse.getTraded_price())) + "\nPending Qty: " + streamerTradeResponse.getPending_qty() + "\nTotal Order Qty: " + streamerTradeResponse.getQty(), "OK", false, new DialogListener() {

                        @Override
                        public void alertDialogAction(Action action, Object... data) {
                            refreshFragment();
                            if (streamerTradeResponse.getOrder_state().equalsIgnoreCase("1")) {
                                setupFragment(new OrderBottomFragment());
                            }
                        }
                    });
                } else {
                    GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Status: " + tradSymbol + " is " + streamerTradeResponse.getOrder_status() + " \n Exchange: " + getExchange(streamerTradeResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(streamerTradeResponse.getGtoken()) + "\n Ref. No : " + streamerTradeResponse.getGorderid() + "\n Exchange Order No: " + streamerTradeResponse.getEorderid() + "\n Trade No: " + streamerTradeResponse.getTradeid() + expirydateText + "\n Product:" + getProductType(streamerTradeResponse.getProduct()) + "\nAction : " + getSide(streamerTradeResponse.getSide()) + "\nTraded Qty: " + streamerTradeResponse.getTraded_qty() + "\nTrigger Price : " + String.format("%.2f", Double.parseDouble(streamerTradeResponse.getTrigger_price())) + "\nTraded Price: " + String.format("%.2f", Double.parseDouble(streamerTradeResponse.getTraded_price())) + "\nPending Qty: " + streamerTradeResponse.getPending_qty() + "\nTotal Order Qty: " + streamerTradeResponse.getQty(), "OK", false, new DialogListener() {

                        @Override
                        public void alertDialogAction(Action action, Object... data) {
                            refreshFragment();
                            if (streamerTradeResponse.getOrder_state().equalsIgnoreCase("1")) {
                                setupFragment(new OrderBottomFragment());
                            }
                        }
                    });
                }
            } else {
                if (((Integer.valueOf(streamerTradeResponse.getGtoken()) >= 502000000) && ((Integer.valueOf(streamerTradeResponse.getGtoken()) <= 502999999)) || ((Integer.valueOf(streamerTradeResponse.getGtoken()) >= 1302000000) && (Integer.valueOf(streamerTradeResponse.getGtoken()) <= 1302999999)))) {
                    GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Status: " + tradSymbol + " is " + streamerTradeResponse.getOrder_status() + " \n Exchange: " + getExchange(streamerTradeResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(streamerTradeResponse.getGtoken()) + "\n Ref. No : " + streamerTradeResponse.getGorderid() + "\n Exchange Order No: " + streamerTradeResponse.getEorderid() + "\n Trade No: " + streamerTradeResponse.getTradeid() + expirydateText + "\n Product:" + getProductType(streamerTradeResponse.getProduct()) + "\nAction : " + getSide(streamerTradeResponse.getSide()) + "\nTraded Qty: " + streamerTradeResponse.getTraded_qty() + "\nTraded Price: " + String.format("%.4f", Double.parseDouble(streamerTradeResponse.getTraded_price())) + "\nPending Qty: " + streamerTradeResponse.getPending_qty() + "\nTotal Order Qty: " + streamerTradeResponse.getQty(), "OK", false, new DialogListener() {

                        @Override
                        public void alertDialogAction(Action action, Object... data) {
                            refreshFragment();
                            if (streamerTradeResponse.getOrder_state().equalsIgnoreCase("1")) {
                                setupFragment(new OrderBottomFragment());
                            }
                        }
                    });
                } else {
                    GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Status: " + tradSymbol + " is " + streamerTradeResponse.getOrder_status() + " \n Exchange: " + getExchange(streamerTradeResponse.getGtoken()) + "\n Mkt.Segment: " + getAssetType(streamerTradeResponse.getGtoken()) + "\n Ref. No : " + streamerTradeResponse.getGorderid() + "\n Exchange Order No: " + streamerTradeResponse.getEorderid() + "\n Trade No: " + streamerTradeResponse.getTradeid() + expirydateText + "\n Product:" + getProductType(streamerTradeResponse.getProduct()) + "\nAction : " + getSide(streamerTradeResponse.getSide()) + "\nTraded Qty: " + streamerTradeResponse.getTraded_qty() + "\nTraded Price: " + String.format("%.2f", Double.parseDouble(streamerTradeResponse.getTraded_price())) + "\nPending Qty: " + streamerTradeResponse.getPending_qty() + "\nTotal Order Qty: " + streamerTradeResponse.getQty(), "OK", false, new DialogListener() {

                        @Override
                        public void alertDialogAction(Action action, Object... data) {
                            refreshFragment();
                            if (streamerTradeResponse.getOrder_state().equalsIgnoreCase("1")) {
                                setupFragment(new OrderBottomFragment());
                            }
                        }
                    });

                }

            }

            //Only Vibrate/Play Sound if Setting On
            if (Util.getPrefs(this).getBoolean("GREEK_BEEP_VIBRATE_acumengroupGLE", true)) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(GreekBaseActivity.this, notification);
                r.play();
                Vibrator v = (Vibrator) GreekBaseActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //generateNoteOnSd(e.toString() + "-" + streamerTradeResponse);
        }
    }

    public String getExchange(String token) {
        if ((Integer.valueOf(token) / 1000000) == 101 || (Integer.valueOf(token) / 1000000) == 102 || (Integer.valueOf(token) / 1000000) == 502) {
            return "NSE";
        } else if ((Integer.valueOf(token) / 1000000) == 201 || (Integer.valueOf(token) / 1000000) == 202 || (Integer.valueOf(token) / 1000000) == 1302) {
            return "BSE";
        } else if ((Integer.valueOf(token) / 1000000) == 403) {
            return "NCDEX";
        } else if ((Integer.valueOf(token) / 1000000) == 303) {
            return "MCX";
        }
        return "";
    }

    private String getAssetType(String token) {
        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 101999999)) || ((tokenInt >= 201000000) && (tokenInt <= 201999999))) {
            return "Equity";
        } else if (((tokenInt >= 102000000) && (tokenInt <= 102999999)) || ((tokenInt >= 202000000) && (tokenInt <= 202999999))) {
            return "FNO";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999)) || ((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "Commodity";
        } else {
            return "Currency";
        }

    }

    public String getProductType(String type) {
        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
            if (AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(type)) {
                return AccountDetails.getAllowedProduct().get(i).getcProductName();
            }
        }
        return "";
    }

    public String getOrderState(String state) {
        if (state.equals("0")) {
            return "Order Confirm";
        } else if (state.equals("1")) {
            return "Modify Confirm";
        } else if (state.equals("2")) {
            return "Order Cancelled";
        }
        return "";
    }

    public void addToStreamingList(String type, ArrayList<String> data) {
        ArrayList<String> symbolList = streamingSymbolList.get(type);
        if (symbolList == null) {
            symbolList = data;
        } else {
            for (String s : data) {
                if (symbolList.indexOf(s) == -1) { //add if does not exists
                    symbolList.add(s);
                }
            }
        }

        streamingSymbolList.put(type, symbolList);
        streamingMarketSymbolList.put(type, symbolList);
    }

    public void clearStreamingList() {
        streamingSymbolList.clear();
        streamingMarketSymbolList.clear();
    }

    public Hashtable<String, ArrayList<String>> getStreamingList() {
        return streamingSymbolList;
    }

    public Hashtable<String, ArrayList<String>> getMarketStreamingList() {
        return streamingMarketSymbolList;
    }

    private void setUpActionBarTicker() {
        sendPortfolioTrendingRequest();
    }

    private void sendPortfolioTrendingRequest() {
        WSHandler.getRequest(GreekBaseActivity.this, "getAllIndicesValue", new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject object = data.getJSONObject(i);

                        if (data.length() == 2) {
                            if (i == 0) {
                                actionBar.setNiftyValues(object.optString("last"), object.optString("change"), object.optString("p_change"), object.optString("name"));
                                GOLD_TOKEN = object.optString("token");
                                NIFTY_TOKEN = object.optString("name");
                                visibleSymbolTable.add(object.optString("token"));
                            } else if (i == 1) {
                                actionBar.setSensexValues(object.optString("last"), object.optString("change"), object.optString("p_change"), object.optString("name"));
                                CRUDE_OIL_TOKEN = object.optString("token");
                                SENSEX_TOKEN = object.optString("name");
                                visibleSymbolTable.add(object.optString("token"));
                            } else if (object.optString("name").equalsIgnoreCase("USDINR")) {
                                actionBar.setGoldValues(object.optString("last"), object.optString("change"), object.optString("p_change"));
                                GOLD_TOKEN = object.optString("token");
                                visibleSymbolTable.add(object.optString("token"));
                            }
                        } else {
                            actionBar.setNiftyValues("0.00", "0.00", "0.00", object.optString("MCXCOMDEX"));
                            CRUDE_OIL_TOKEN = object.optString("token");
                            visibleSymbolTable.add(object.optString("token"));

                            actionBar.setNiftyValues("0.00", "0.00", "0.00", object.optString("MCXMETAL"));
                            GOLD_TOKEN = object.optString("token");
                            visibleSymbolTable.add(object.optString("token"));
                        }
                    }
                    streamingController.sendStreamingRequest(getApplicationContext(), visibleSymbolTable, "ltpinfo", null, null, false); //PK*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {
                Log.e("Failure", "resp in common activity ");
            }
        });
    }

    private void sendMarketStatusRequest() {
        MarketStatusPostRequest.sendRequest(AccountDetails.getUsername(getApplicationContext()), getApplicationContext(), serviceResponseHandler);
    }

    public void setAppTitle(String key, String title) {
        if (key.equals(currentPage)) {
            globalTitles.put(key, title);
            actionBar.setTitle(title);
            actionBar.setTitleVisibility(View.VISIBLE);
        } else {
            currentPage = key;
            globalTitles.put(key, title);
            actionBar.setTitle(title);
            actionBar.setTitleVisibility(View.VISIBLE);
        }

        //On Quote Search Click
        if (key.contains("SymbolSearchFragment")) actionBar.hideSearchAction();
        else actionBar.showSearchAction();
    }

    public void hideAppTitle() {
        actionBar.setTitle("");
        actionBar.setTitleVisibility(View.GONE);
    }

    private void getLastTitle(String currentPage) {
        String title = globalTitles.get(currentPage);
        if (title != null && !title.isEmpty()) {
            actionBar.setTitle(title);
            actionBar.setTitleVisibility(View.VISIBLE);
        } else {
            hideAppTitle();
        }
        if (!currentPage.contains("SymbolSearchFragment")) actionBar.showSearchAction();
    }

    private void setupMenuDrawer() {

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {

                if (getCurrentFocus() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }


            }

            @Override
            public void onDrawerClosed(View view) {
                // your refresh code can be called from here

                if (AccountDetails.currentFragment == NAV_TO_WATCHLIST_SCREEN) {

                    //checkedBottomTAB(bottomNavigation.getMenu().findItem(R.id.my_watchlist));

                } else if (AccountDetails.currentFragment == NAV_TO_MYSCRIPTS_SCREEN) {

                    //checkedBottomTAB(bottomNavigation.getMenu().findItem(R.id.my_portfolio));


                } else if (AccountDetails.currentFragment == NAV_TO_LASTVISITED_SCREEN) {

                    //checkedBottomTAB(bottomNavigation.getMenu().findItem(R.id.order));


                } else if (AccountDetails.currentFragment == NAV_TO_PORTFOLIO_BOTTOM_SCREEN) {

                    //checkedBottomTAB(bottomNavigation.getMenu().findItem(R.id.my_portfolio));

                } else if (AccountDetails.currentFragment == NAV_TO_ORDER_BOTTOM_FRAGMENT) {

                    //checkedBottomTAB(bottomNavigation.getMenu().findItem(R.id.order));

                } else {

                    // UncheckedBottomTAB();
                }
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });


        setMenuController();
        refreshMenuDrawer();
    }

    private void setMenuController() {
        menuAdapter = new MenuAdapter(this, new ArrayList<ScreenDetails>(), new LinkedHashMap<String, Vector<SubMenu>>());
        menuList.setAdapter(menuAdapter);
        menuList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    menuList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        menuList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, final int position, final int childPosition, long id) {

                drawerLayout.closeDrawers();
                final boolean[] setMenuSelection = {false};
                String clickedPosDetails = "";

                Vector<SubMenu> sub = menuAdapter.getChildList(position);
                if (sub.size() > 0) {
                    clickedPosDetails = sub.get(childPosition).getDetails();
                }

                if (USER_TYPE == USER.OPENUSER) {

                    if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_LINKS))) {
                        handleExternalLinks(clickedPosDetails);
                    }
                } else if (USER_TYPE == USER.IBTCUSTOMER) {

                    if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_MORE_TXT))) {
                        if (childPosition == 0) {
                            Intent aboutus = new Intent(GreekBaseActivity.this, AboutActivity.class);
                            startActivity(aboutus);
                        }
                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_FUND_TRANSFER))) {
                        setMenuSelection[0] = true;
                        performFundTransferAction(clickedPosDetails);
                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_SETTINGS_TXT))) {
                        setMenuSelection[0] = true;
                        performSettingAction(clickedPosDetails);
                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_LINKS))) {
                        handleExternalLinks(clickedPosDetails);
                    } else {
                        setMenuSelection[0] = true;
                        performMenuAction(clickedPosDetails);
                    }
                    if (setMenuSelection[0]) {
                        setChildMenuSelection(position, childPosition);

                    }
                }

                if (setMenuSelection[0]) setChildMenuSelection(position, childPosition);
                return false;
            }
        });
        menuList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int position, long l) {

                boolean clickHandled = true;
                final boolean[] setMenuSelection = {false};

                PortraitMode();
                if (GreekBaseActivity.USER_TYPE == USER.OPENUSER) {


                    NeedOfDash = false;

                    if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_CHARTING))) {
                        showAppbar();
                        setMenuSelection[0] = true;
                        showChartingScreen();
                        performMenuAction(GREEK_MENU_CHARTING);
                        drawerLayout.closeDrawers();
                        clickHandled = true;

                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_LINKS))) {
                        clickHandled = false;
                        setMenuSelection[0] = true;

                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_IPO))) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.vishwasfincap.com/vishwas/ipo.html")));
                        drawerLayout.closeDrawers();
                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_EXIT))) {
                        clearWatchlistDataFromSP(USER.OPENUSER);
                        clickHandled = true;

                        Intent login = new Intent(GreekBaseActivity.this, LoginActivity.class);
                        startActivityForResult(login, CUSTOMER_LOGIN);
                        AccountDetails.setLastSelectedGroup("");
                        finish();
                    } else {
                        pageNotAccessible();
                        clickHandled = true;
                    }


                } else if (GreekBaseActivity.USER_TYPE == USER.IBTCUSTOMER) {
                    PortraitMode();
                    if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_EDIS_TXT))) {

                        showAppbar();
                        clickHandled = false;

                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_CHARTING))) {
                        showAppbar();
                        setMenuSelection[0] = true;
                        showChartingScreen();
                        performMenuAction(GREEK_MENU_CHARTING);
                        drawerLayout.closeDrawers();

                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_NOTIFICATONS))) {
                        showAppbar();
                        setMenuSelection[0] = true;
                        showNotifications();
                        performMenuAction(GREEK_MENU_NOTIFICATIONS_TXT);
                        drawerLayout.closeDrawers();

                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_RECOMMENDATION))) {
                        showAppbar();
                        setMenuSelection[0] = true;
                        showRecommInformation();
                        performMenuAction(GREEK_MENU_RECOMMENDATION_TXT);
                        drawerLayout.closeDrawers();

                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_FUND_TRANSFER))) {
                        showAppbar();
                        hideAppTitle();
                        setMenuSelection[0] = true;
                        clickHandled = false;

                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_REPORT_TXT))) {
                        showAppbar();
                        setMenuSelection[0] = true;
                        clickHandled = false;

                    } else if(groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_ALERT))) {
                        drawerLayout.closeDrawers();
                        AlertFragment alertFragment = new AlertFragment();
                        alertFragment.setArguments(AccountDetails.globalArg);
                        setupFragment(alertFragment);
                    }
                    else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_SETTINGS_TXT))) {
                        showAppbar();
                        setMenuSelection[0] = true;
                        clickHandled = false;

                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_PROFILE_TXT))) {
                        showAppbar();
                        drawerLayout.closeDrawers();
                        setMenuSelection[0] = true;
                        showProfileScreen();
                        performMenuAction(GREEK_MENU_PROFILE_TXT);
                        drawerLayout.closeDrawers();
                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_LAS_TXT))) {
                        showAppbar();
                        drawerLayout.closeDrawers();
                        setMenuSelection[0] = true;
                        showLasScreen();
                        performMenuAction(GREEK_MENU_LAS_TXT);
                        drawerLayout.closeDrawers();
                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_MORE_TXT))) {
                        showAppbar();
                        setMenuSelection[0] = true;
                        clickHandled = false;
                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_LINKS))) {
                        clickHandled = false;
                        setMenuSelection[0] = true;

                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_IPO))) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.vishwasfincap.com/vishwas/ipo.html")));
                        drawerLayout.closeDrawers();
                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_LOGOUT_TXT))) {
                        logoutCustomer();
                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_STRATEGY_BUILDER_TXT))) {
                        showStrategyBuilder();
                        drawerLayout.closeDrawers();
                    } else if (groupList.get(position).getTitle().equalsIgnoreCase(getString(R.string.GREEK_MENU_STRATEGY_TXT))) {

                        showStrategyFinder();
                        drawerLayout.closeDrawers();
                    }

                    setMenuSelection[0] = true;

                }

                if (setMenuSelection[0]) {
                    setMenuSelection(position);
                }


                return clickHandled;
            }
        });
    }


    private void PortraitMode() {
        if ((getRequestedOrientation()) == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void showAppbar() {

        marketValuesLayout.setVisibility(View.GONE);
        apptickerLayout.setVisibility(View.VISIBLE);
        screen_title.setVisibility(View.GONE);
        dashboardlayout.setVisibility(View.VISIBLE);
    }

    private void showStrategyFinder() {
        setupFragment(new SFScanFragment());
    }

    private void showStrategyBuilder() {
        setupFragment(new StrategyBuildUpFragment());
    }

    private void showChartingScreen() {
        setupFragment(new ChartingFragment());
    }

    private void showProfileScreen() {
        setupFragment(new UserProfile());
    }

    private void showLasScreen() {
        setupFragment(new LASFragment());
    }

    private void showChatMessages() {
        setupFragment(new ChatMessageFragment());
    }

    private void getKycDetails() {

        showProgress();
        KycDetailRequest.sendRequest(AccountDetails.getUserPAN(getApplication()), AccountDetails.getUsername(getApplication()), GreekBaseActivity.this, serviceResponseHandler);
    }

    private void showAlertInformation() {
        setupFragment(new AlertFragment());
    }

    private void showFundTransferFragment() {
        setupFragment(new FundDisplayFragment());
    }


    private void refreshMenuDrawer() {
        GreekTextView pageTitleView = findViewById(R.id.pageHeaderText);
        LinearLayout linearLayout = findViewById(R.id.pageHeaderLayout);
        GreekTextView userName = findViewById(R.id.user_name_txt);
        ImageView uploadImg = findViewById(R.id.user_img);


        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
            linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
            userName.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            pageTitleView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }

        uploadImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_white_24dp));

        userName.setText(AccountDetails.getCLIENTNAME(this));
        String bse_URL = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port();


        String showPDFUrl = bse_URL + "/downloadFileV2?imageName=ClientImage_" + AccountDetails.getUsername(this);
        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
            Picasso.with(this).load(showPDFUrl + ".jpeg").networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).error(R.drawable.ic_person_black_24dp).into(uploadImg);
        } else {
            Picasso.with(this).load(
                    showPDFUrl + ".jpeg")
                    .networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).error( R.drawable.ic_person_white_24dp).into(uploadImg);
        }

        linearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //Rohit
                if (GreekBaseActivity.USER_TYPE != USER.OPENUSER) {
                    showUserProfile();

                }

                drawerLayout.closeDrawers();
            }
        });
        if (USER_TYPE == USER.OPENUSER) {
            pageTitleView.setText(" Guest");
        } else {
            pageTitleView.setText(AccountDetails.getUsername(this));
        }

        groupList = DrawerMenu.createGroupList(this);
        final Map<String, Vector<SubMenu>> subMenuCollections = DrawerMenu.createCollection(this, groupList);

        if (lastExpandedPosition != -1) menuList.collapseGroup(lastExpandedPosition);
        menuAdapter.updateMenu(groupList, subMenuCollections);
    }

    private void pageNotAccessible() {
        GreekDialog.alertDialog(GreekBaseActivity.this, 0, GreekBaseActivity.GREEK, getString(R.string.open_account_msg), "Ok", false, new DialogListener() {
            @Override
            public void alertDialogAction(Action action, Object... data) {
            }
        });
    }

    private void pageComingSoon() {
        GreekDialog.alertDialog(GreekBaseActivity.this, 0, GreekBaseActivity.GREEK, getString(R.string.coming_soon_msg), "Ok", false, new DialogListener() {
            @Override
            public void alertDialogAction(Action action, Object... data) {
            }
        });
    }

    private void initiateCallToHelp() {
        GreekDialog.alertDialog(this, 0, GREEK, "Do you want to Call for Help?", "Yes", "No", true, new DialogListener() {
            @Override
            public void alertDialogAction(Action action, Object... data) {
                if (action == Action.OK) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + "02200000000"));
                    startActivity(callIntent);
                }
            }
        });
    }

    private void initiateCallToTrade() {
        GreekDialog.alertDialog(this, 0, GREEK, "Do you want to Call to Trade?", "Yes", "No", true, new DialogListener() {
            @Override
            public void alertDialogAction(Action action, Object... data) {
                if (action == Action.OK) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + Util.getPrefs(GreekBaseActivity.this).getString("GREEK_CALL_TO_TRADE_NUMBER", "02200000000")));
                    startActivity(callIntent);
                }
            }
        });
    }

    private void handleExternalLinks(String det) {
        switch (det) {
            case GREEK_NSE_LINKS_TXT:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nseindia.com/")));
                break;
            case GREEK_BSE_LINKS_TXT:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bseindia.com/")));
                break;
            case GREEK_NCDEX_LINKS_TXT:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ncdex.com/index.aspx")));
                break;
            case GREEK_SEBI_LINKS_TXT:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sebi.gov.in")));
                break;
            case GREEK_SSL_LINKS_TXT:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://seal.godaddy.com/verifySeal?sealID=a8fEnR8nEQlWgVQRrtd41Rhld681YCu0udW8daQHyjEkIozTsTA3vsPkCY8b/")));
                break;
            case GREEK_MCX_LINKS_TXT:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mcxindia.com/")));
                break;
            case GREEK_BACK_OFFICE_LINKS_TXT:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://bo.vishwasfincap.com:8031/CAP/indexapp.jsp")));
                break;
            case GREEK_OFFLINE_BANK_LINKS_TXT:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://fundtrf.vishwasfincap.com:9002/Signin.aspx")));
                break;
        }
    }

    private void logoutCustomer() {
        GreekDialog.alertDialog(GreekBaseActivity.this, 0, GreekBaseActivity.GREEK, getString(R.string.GREEK_LOGOUT_ALERT_MSG), "Yes", "No", true, new DialogListener() {
            @Override
            public void alertDialogAction(Action action, Object... data) {
                if (action == Action.OK) {
                    doLogout(ALL);
                }
            }
        });
    }

    //TODO sushant
    private void logoutCustomerExit() {
        doLogout(ALL);
    }

    public void doLogout(final int status) {


        if (orderStreamingController != null) {
            orderStreamingController.sendStreamingLogoffRequest(this,
                    AccountDetails.getUsername(this),
                    AccountDetails.getClientCode(this),
                    AccountDetails.getSessionId(getApplicationContext()),
                    AccountDetails.getToken(this));
            orderStreamingController = null;
        }

        if (streamController != null) {
            streamController.sendStreamingLogoffRequest(this,
                    AccountDetails.getUsername(getApplicationContext()),
                    AccountDetails.getClientCode(this),
                    AccountDetails.getSessionId(getApplicationContext()),
                    AccountDetails.getToken(this), null, null);
            streamController = null;
        }

        AccountDetails.isLoginAndDisconnected = false;
        AccountDetails.setHeartBeatApolloCount(0);
        AccountDetails.setHeartBeatIrisCount(0);


        //LogoutRequest.sendRequest(AccountDetails.getClientCode(GreekBaseActivity.this), GreekBaseActivity.this, serviceResponseHandler);
        new HeartBeatService(this, AccountDetails.getUsername(this), AccountDetails.getSessionId(this),
                AccountDetails.getClientCode(this)).stopHeartBeat();
        Util.getPrefs(GreekBaseActivity.this).edit().putString("GREEK_RETAINED_CUST_PASS", "").apply();
        Util.getPrefs(GreekBaseActivity.this).edit().putString("GREEK_RETAINED_CUST_TRANS_PASS", "").apply();
        Util.getPrefs(GreekBaseActivity.this).edit().putLong("LastValidatedTime", 0).apply();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onLogout(status);
            }
        }, 100);
    }

    private void showAdminInformation() {
        Toast.makeText(getApplicationContext(), "Admin Information...coming soon", Toast.LENGTH_SHORT).show();
        //   AdminInformationRequest.sendRequest(AccountDetails.getUsername(GreekBaseActivity.this), GreekBaseActivity.this, serviceResponseHandler);
        SnapshotFloatingFragment snapshotFloatingFragment = new SnapshotFloatingFragment();
        setupFragment(snapshotFloatingFragment);
    }

    private void helpInfo() {

        String bse_URL = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port();

        Intent i = new Intent(this, PdfActivity.class);
        i.putExtra("showPDFUrl", bse_URL + "/getHelpMobile");
        startActivity(i);

       /* String Atom2Request = "http://192.168.209.192:8080/getHelp";
//        String Atom2Request = xmlURL + "?ttype=" + paymodel.getXmlttype() + "&tempTxnId=" + paymodel.getXmltempTxnId() + "&token=" + paymodel.getXmltoken() + "&txnStage=" + paymodel.getXmltxnStage();


        Intent intent = new Intent(this, WebContent.class);
        intent.putExtra("AtomRequest", Atom2Request);
        //intent.putExtra("segstr", segStr);
        startActivityForResult(intent, 3);*/


    }

    private void showNewsInformation() {
        setupFragment(new NewsFragmentPager());
        //setupFragment(new NewsDisplayFragment());

        //Toast.makeText(getApplicationContext(), "News will..coming soon",Toast.LENGTH_SHORT).show();
        //   AdminInformationRequest.sendRequest(AccountDetails.getUsername(GreekBaseActivity.this), GreekBaseActivity.this, serviceResponseHandler);
    }

    private void showRecommInformation() {
        setupFragment(new RecommDisplayFragment());
        //Toast.makeText(getApplicationContext(), "Recommendation will..coming soon",Toast.LENGTH_SHORT).show();
        //   AdminInformationRequest.sendRequest(AccountDetails.getUsername(GreekBaseActivity.this), GreekBaseActivity.this, serviceResponseHandler);
    }

    private void showUserProfile() {
        setupFragment(new UserProfile());
        //Toast.makeText(getApplicationContext(), "Recommendation will..coming soon",Toast.LENGTH_SHORT).show();
        //   AdminInformationRequest.sendRequest(AccountDetails.getUsername(GreekBaseActivity.this), GreekBaseActivity.this, serviceResponseHandler);
    }

    private void showNotifications() {
        setupFragment(new NotificationsFragment());
        //Toast.makeText(getApplicationContext(), "Recommendation will..coming soon",Toast.LENGTH_SHORT).show();
        //   AdminInformationRequest.sendRequest(AccountDetails.getUsername(GreekBaseActivity.this), GreekBaseActivity.this, serviceResponseHandler);
    }

    private void performMenuAction(ScreenDetails details) {
        performMenuAction(details.getDetails());
    }

    private void performFundTransferAction(String setting) {

        switch (setting) {
            case GREEK_MENU_FUND_TRANSFER:
                setupFragment(new FundTransferFragment());
                performMenuAction(GREEK_MENU_FUND_TRANSFER_TXT);
                break;

            case GREEK_MENU_FUND_TRANSFER_TXT:
                setupFragment(new FundTransferFragment());
                performMenuAction(GREEK_MENU_FUND_TRANSFER_TXT);
                break;

            case GREEK_MENU_FUND_TRANSFER_DETAILS_TXT:
                performMenuAction(GREEK_MENU_FUND_TRANSFER_DETAILS_TXT);
                setupFragment(new FundTransferDetailsFragment());
                break;

        }
    }

    private void performSettingAction(String setting) {

        switch (setting) {

            case GREEK_MENU_MF_MYACCOUNT_TXT:
//                setupFragment(new MyAccountPersonalDetails());

                //getKycDetails();
                pageNotAccessible();
                break;

            case GREEK_MENU_APP_SETTING_TXT:
                setupFragment(new AppSettingFragment());
                performMenuAction(GREEK_MENU_SETTINGS_TXT);
                break;

            case GREEK_MENU_CHANGE_PASSWORD_TXT:
                setupFragment(new ChangePasswordFragment());
                performMenuAction(GREEK_MENU_CHANGE_PASSWORD_TXT);
                break;

            case GREEK_MENU_CHANGE_THEME_TXT:

//                pageComingSoon();

                GreekDialog.alertDialog(GreekBaseActivity.this, 0, GreekBaseActivity.GREEK, getString(R.string.GREEK_THEME_ALERT), "Yes", "No", true, new DialogListener() {
                    @Override
                    public void alertDialogAction(Action action, Object... data) {
                        if (action == Action.OK) {
                            System.out.println("Current Theme :" + AccountDetails.getThemeFlag(getApplicationContext()));


                            if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                                AccountDetails.setThemeflag("black");
                                saveThemeSettingsToServer("DarkTheme");
                                SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                                editor.putString("THEME_FLAG", "black");
                                editor.commit();
                                settingThemeAsset();

                            } else if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("black")) {
                                AccountDetails.setThemeflag("white");
                                saveThemeSettingsToServer("LightTheme");
                                SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                                editor.putString("THEME_FLAG", "white");
                                editor.commit();
                                settingThemeAsset();


                            }

                            AccountDetails.setIsThemeApplied(true);
                            SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                            editor.putString("themeApplied", "true");
                            editor.commit();
                            getCurrentFragmentNameFromId();
                            AccountDetails.setIsApolloConnected(false);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    }


                });

                break;
        }
    }

    public void saveThemeSettingsToServer(String theam) {

        String url = "saveThemeSettings?gcid=" + AccountDetails.getClientCode(getApplicationContext()) + "&gscid=" + AccountDetails.getUsername(getApplicationContext())
                + "&myTheme=" + theam;
        WSHandler.getRequest(getApplicationContext(), url, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onFailure(String message) {

            }

        });

    }

    public void settingThemeAsset() {
        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {

            menuList.setBackgroundColor(getResources().getColor(R.color.white));
//            menuList.setDivider(getResources().getDrawable(R.color.white));
//            menuList.setChildDivider(getResources().getDrawable(R.color.white));

            bottomNavigation.setItemBackgroundResource(R.drawable.navbar_backcolor_white);
            bottomNavigation.setBackgroundColor(getResources().getColor(R.color.white));

            ColorStateList iconsColorStates = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked},
                            new int[]{android.R.attr.state_checked}
                    },
                    new int[]{
                            Color.parseColor("#000000"),
                            Color.parseColor("#000000")
                    });

            bottomNavigation.setItemIconTintList(iconsColorStates);
            //bottomNavigation.getItemIconTintList(getResources().getColor(R.color.white));
            bottomNavigation.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));

            if (apptickerLayout1 != null) {
                apptickerLayout1.setBackgroundColor(getResources().getColor(R.color.login_bg));
            }
            AccountDetails.setRowSpinnerSimple(R.layout.row_spinner_simple_white);
            AccountDetails.setTextColorDropdown(R.color.textColorCustomWhite);
            AccountDetails.textColorDropdown = R.color.textColorCustomWhite;
            AccountDetails.textColorBlue = R.color.textColorBlueWhite;
            AccountDetails.textColorRed = R.color.textColorRedWhite;
            AccountDetails.marketStatusStrip = R.color.marketStatusStripWhite;
            AccountDetails.topViewBg = R.color.topViewBgWhite;
            AccountDetails.backgroundColor = R.color.backgroundColorWhite;
            AccountDetails.dividerColor = R.color.dividerColorWhite;
            AccountDetails.backgroundBgDrawable = getResources().getDrawable(R.drawable.bg_drawable);
        } else {


            AccountDetails.setRowSpinnerSimple(R.layout.row_spinner_simple);
//            AccountDetails.setTextColorDropdown(R.color.textColorCustom);
            AccountDetails.textColorDropdown = R.color.textColorCustom;
            AccountDetails.textColorBlue = R.color.textColorGreenBlack;
            AccountDetails.textColorRed = R.color.textColorRedBlack;
            AccountDetails.marketStatusStrip = R.color.marketStatusStripBlack;
            AccountDetails.topViewBg = R.color.topViewBgBlack;
            AccountDetails.backgroundColor = R.drawable.bg_drawable;
            AccountDetails.dividerColor = R.color.dividerColorBlack;
            AccountDetails.backgroundBgDrawable = getResources().getDrawable(R.drawable.bg_drawable);
        }
    }


    private void clearTitles() {

        globalTitles.clear();
        actionBar.showSearchAction();
    }

    public void performMenuAction(String details) {
        clearTitles();

        GreekBaseFragment fragment = (GreekBaseFragment) MenuGetter.getFragment(details);

        if (fragment != null) {
            setupFragment(fragment);
        } else {
            UncheckedBottomTAB();
        }
    }


    public Fragment getCurrentFragmentByName(String className) {
        FragmentManager fragmentManager = GreekBaseActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            int size = fragments.size();
            for (int i = 0; i < size; i++) {
                if (fragments.get(i) != null) {
                    if (fragments.get(i).getClass().toString().toLowerCase().contains(className)) {
                        return fragments.get(i);
                    }
                }
            }
        }
        return null;
    }

    private void openMenuDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
        refreshMenuDrawer();
    }


    public void showProgress() {
        if (progressLayout != null)
            progressLayout.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AccountDetails.setIsMainActivity(false);
        if (requestCode == CDSLREQUESTCODE) {

            if (data != null) {

                String message = data.getStringExtra("Result");
                org.jsoup.nodes.Document doc1 = Jsoup.parse(message);
                Elements type1 = doc1.select("pre");

                String response = type1.first().html();
                Gson gson = new Gson();
                CDSLReturnResponse cdslReturnResponse = gson.fromJson(String.valueOf(response), CDSLReturnResponse.class);
                ArrayList<CDSLReturnResponse.Response.Data.StockDetail> dataArrayList = cdslReturnResponse.getResponse().getData().getStockDetails();
                if (cdslReturnResponse.getResponse().getErrorCode().equalsIgnoreCase("0")) {

                    try {
                        JSONObject symbols = new JSONObject();
                        JSONArray jsonArray = new JSONArray();

                        for (int i = 0; i < dataArrayList.size(); i++) {
                            if (dataArrayList.get(i).getStatus().equalsIgnoreCase("success")) {
                                JSONObject jsonobj = new JSONObject();
                                jsonobj.put("token", dataArrayList.get(i).getToken());
                                jsonobj.put("Qty", dataArrayList.get(i).getQuantity());
                                jsonobj.put("Isin", dataArrayList.get(i).getISIN());
                                jsonobj.put("Status", dataArrayList.get(i).getStatus());
                                jsonobj.put("ReqType", dataArrayList.get(i).getReqType());
                                jsonobj.put("ReqIdentifier", dataArrayList.get(i).getReqIdentifier());
                                jsonobj.put("TxnId", dataArrayList.get(i).getTxnId());

                                jsonArray.put(jsonobj);
                            }

                        }
                        symbols.put("symbols", jsonArray);

                        Log.e("JsonRequestIRIS", symbols.toString());
                        if (jsonArray.length() > 0) {
                            orderStreamingController.sendCDSLTransferDetailsRequest(getApplicationContext(),
                                    symbols, cdslReturnResponse.getResponse().getData().getReqId(),
                                    AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()));
                        }


                    } catch (JSONException e) {
                        Log.e("Json error", e.toString());
                    }
                    if (dataArrayList.size() > 0) {

                        showAuthorizeTransectionReturnnResponse(dataArrayList);
                    } else {
                        GreekDialog.alertDialogOnly(getApplicationContext(), GreekBaseActivity.GREEK, "NO Records Found", getString(R.string.GREEK_OK));
                    }

                } else {

                    GreekDialog.alertDialogOnly(getApplicationContext(), GreekBaseActivity.GREEK, "Transaction Failed", getString(R.string.GREEK_OK));
                }
            } else {

                GreekDialog.alertDialogOnly(getApplicationContext(), GreekBaseActivity.GREEK, "Transaction cancelled", getString(R.string.GREEK_OK));

            }

        } else if (resultCode == LOGIN_SUCCESS) {
            if (requestCode == CUSTOMER_LOGIN) {

                refreshMenuDrawer();
                setupDefaultFragment(data);
            }
        } else if (resultCode == PASSWORD_CHANGE_NEEDED) {

            setupDefaultFragment(data);
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            onCapturePhotoResult(data,GreekBaseActivity.this);

//            bitmap = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//            destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
//            FileOutputStream fo;
//            try {
//                destination.createNewFile();
//                fo = new FileOutputStream(destination);
//                fo.write(bytes.toByteArray());
//                fo.close();
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            bitmap = (Bitmap) data.getExtras().get("data");


            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), bitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            destination = new File(getRealPathFromURI(tempUri));
            uploadMultipart();


        } else if (requestCode == VIDEO_CAPTURED && resultCode == Activity.RESULT_OK) {

            OnVideoCaptureResult(data, getApplicationContext());

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

                onSelectPhotoFromGalleryResult(data,getApplicationContext());

                if (data != null) {
                    destination = new File(getPath(getApplicationContext(), data.getData()));
                    bitmap = getBitmapofPhoto(data);
                }
                uploadMultipart();

            }
        }


    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    public void uploadMultipart() {
        //getting name for the image

        showProgress();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now);

        Uri filePath = Uri.fromFile(destination);

        //getting the actual path of the image
        String path = FilePath.getPath(getApplicationContext(), filePath);

        if (path == null) {

            Toast.makeText(getApplicationContext(), "Please move your video file to internal storage and retry", Toast.LENGTH_LONG).show();

        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();
                uploadReceiver.setUploadID(uploadId);
                uploadReceiver.setDelegate(this);


                String upload_URL = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP()
                        + ":" + AccountDetails.getArachne_Port() + "/upload";


                new MultipartUploadRequest(getApplicationContext(), uploadId, upload_URL)
                        .addFileToUpload(path, "imagePath") //Adding file
                        .addParameter("imgeId", "5") //Adding text parameter to the request
                        .addParameter("clientCode", AccountDetails.getUsername(getApplicationContext())) //Adding text parameter to the request
                        .addParameter("imgeType", "jpeg") //Adding text parameter to the request
                        //.addParameter("panNo",AccountDetails.getUserPAN(getMainActivity())) //Adding text parameter to the request
                        .addParameter("panNo", AccountDetails.getUserPAN(getApplicationContext())) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .setDelegate(new UploadStatusDelegate() {
                            @Override
                            public void onProgress(Context context, UploadInfo uploadInfo) {

                            }

                            @Override
                            public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

                                hideProgress();
                                GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Photo upload failed !",
                                        "Ok", false, new GreekDialog.DialogListener() {

                                            @Override
                                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                                if (action == GreekDialog.Action.OK) {


                                                }
                                            }
                                        });
                            }

                            @Override
                            public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {

                                hideProgress();

                                GreekDialog.alertDialog(GreekBaseActivity.this, 0, GREEK, "Photo uploaded successfully.",
                                        "Ok", false, new GreekDialog.DialogListener() {

                                            @Override
                                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                                if (action == GreekDialog.Action.OK) {


                                                    bitmap = null;

                                                    SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
                                                    editor.putString("uploadAadharSubmit", "yes");
                                                    editor.apply();
                                                    editor.commit();


                                                    UserProfile.setProfilePic();


                                                }
                                            }
                                        });
                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {
                                hideProgress();
                            }
                        })
//                        .setUtf8Charset("application/x-www-form-urlencoded; charset=UTF-8")
                        .startUpload();

                //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(getApplicationContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void setupDefaultFragment(Intent data) {

        int isProceed = data.getIntExtra("isProceed", AccountDetails.currentFragment);
        marketValuesLayout.setVisibility(View.GONE);
        apptickerLayout.setVisibility(View.VISIBLE);
        screen_title.setVisibility(View.GONE);
        dashboardlayout.setVisibility(View.VISIBLE);

        try {

            switch (isProceed) {

                case NAV_TO_MARKET_STARTUP_SCREEN:
                    setupFragment(new MarketsFragment());
                    break;
                case NAV_TO_WATCHLIST_SCREEN_SCREEN:
                    Bundle args = new Bundle();
                    args.putInt("Source", 1);
                    checkedBottomTAB(bottomNavigation.getMenu().findItem(R.id.my_watchlist));
                    bottomNavigation.setSelectedItemId(R.id.my_watchlist);
                    WatchListFragment sectionFragment = new WatchListFragment();
                    sectionFragment.setArguments(args);
//                watchlistFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(sectionFragment);
                    break;

                case NAV_TO_ORDER_PREVIEW_SCREEN:
                    TradePreviewFragment tradePreviewFragment = new TradePreviewFragment();
                    tradePreviewFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(tradePreviewFragment);
                    break;

                case NAV_TO_STRATEGY_DATA_SCREEN:
                    StrategyDataListFragment strategyDataListFragment = new StrategyDataListFragment();
                    strategyDataListFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(strategyDataListFragment);
                    break;

                case NAV_TO_STRATEGY_BUILDUP_SCREEN:
                    StrategyBuildUpFragment strategyBuildUpFragment = new StrategyBuildUpFragment();
                    strategyBuildUpFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(strategyBuildUpFragment);
                    break;
                case NAV_TO_LAS_STEPS_FORMS_SCREEN:
                    LasStepsFormFragment lasStepsFormFragment = new LasStepsFormFragment();
                    lasStepsFormFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(lasStepsFormFragment);
                    break;
                case NAV_TO_ORDER_BOOK_SCREEN:
                    OrderBookFragment orderBookFragment = new OrderBookFragment();
                    orderBookFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(orderBookFragment);
                    break;
                case NAV_TO_INDICES_STOCK_SCREEN:
                    IndicesStockFragment indicesStockFragment = new IndicesStockFragment();
                    indicesStockFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(indicesStockFragment);
                    break;
                case NAV_TO_TRADE_SCREEN:

                    TradeFragment tradeFragment1 = new TradeFragment();
                    tradeFragment1.setArguments(AccountDetails.globalArg);
                    setupFragment(tradeFragment1);
                    break;
                case NAV_TO_ORDER_BOOK_DETAILS_SCREEN:
                    OrderBookDetailsFragment orderBookDetailsFragment = new OrderBookDetailsFragment();
                    orderBookDetailsFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(orderBookDetailsFragment);
                    break;
                case NAV_TO_TRADE_BOOK_SCREEN:
                    TradeBookFragment tradeBookFragment = new TradeBookFragment();
                    tradeBookFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(tradeBookFragment);
                    break;
                case NAV_TO_CHAT_MESSAGE_SCREEN:
                    ChatMessageFragment chatMessageFragment = new ChatMessageFragment();
                    chatMessageFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(chatMessageFragment);
                    break;
                case NAV_TO_LAS_MESSAG_SCREEN:
                    LASFragment lasFragment = new LASFragment();
                    lasFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(lasFragment);
                    break;

                case NAV_TO_HOLDING_SCREEN:
                    HoldingReportFragment holdingReportFragment = new HoldingReportFragment();
                    holdingReportFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(holdingReportFragment);
                    break;
                case NAV_TO_NOTIFICATION_SCREEN:
                    NotificationsFragment notificationsFragment = new NotificationsFragment();
                    notificationsFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(notificationsFragment);
                    break;
                case NAV_TO_TRADE_BOOK_DETAILS_SCREEN:
                    TradeBookDetailsFragment tradeBookDetailsFragment = new TradeBookDetailsFragment();
                    tradeBookDetailsFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(tradeBookDetailsFragment);
                    break;
                case NAV_TO_NETPOSITION_SCREEN:
                    NetPositionFragment netPositionFragment = new NetPositionFragment();
                    netPositionFragment.setArguments(AccountDetails.globalArg);
                    AccountDetails.AutoRefreshForNetPosition = false;
                    setupFragment(netPositionFragment);
                    break;
                case NAV_TO_DEMATHOLDING_SCREEN:
                    DematHoldingFragmentNew dematHoldingFragmentNew = new DematHoldingFragmentNew();
                    dematHoldingFragmentNew.setArguments(AccountDetails.globalArg);
                    AccountDetails.AutoRefreshForDemat = false;
                    setupFragment(dematHoldingFragmentNew);
                    break;
                case NAV_TO_MARGIN_SUMMARY:
                    MarginFragment marginFragment = new MarginFragment();
                    marginFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(marginFragment);
                    break;
                case NAV_TO_MARKET_DEPTH_SCREEN:
                    MarketDepthFragment marketDepthFragment = new MarketDepthFragment();
                    marketDepthFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(marketDepthFragment);
                    break;
                case NAV_TO_QUOTES_SCREEN:
                    QuotesFragment quotesFragment = new QuotesFragment();
                    //Bundle args3 = new Bundle();
                    quotesFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(quotesFragment);
                    break;
                case NAV_TO_SYMBOL_SEARCH_SCREEN:
                    SymbolSearchFragment symbolSearchFragment = new SymbolSearchFragment();
                    //Bundle args3 = new Bundle();
                    symbolSearchFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(symbolSearchFragment);
                    break;
                case NAV_TO_EDIT_WATCHLIST_SCREEN:
                    EditWatchlistFragment editWatchlistFragment = new EditWatchlistFragment();
                    //Bundle args3 = new Bundle();
                    editWatchlistFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(editWatchlistFragment);
                    break;
                case NAV_TO_MYSCRIPTS_SCREEN:
                    SnapshotFloatingFragment snapshotFloatingFragment = new SnapshotFloatingFragment();
                    snapshotFloatingFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(snapshotFloatingFragment);
                    break;
                case NAV_TO_WATCHLIST_SCREEN:

                    checkedBottomTAB(bottomNavigation.getMenu().findItem(R.id.my_watchlist));
                    bottomNavigation.setSelectedItemId(R.id.my_watchlist);
                    WatchListFragment watchlistFragment = new WatchListFragment();
                    watchlistFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(watchlistFragment);

                    break;

                case NAV_TO_PORTFOLIO_BOTTOM_SCREEN:

                    checkedBottomTAB(bottomNavigation.getMenu().findItem(R.id.my_portfolio));
                    bottomNavigation.setSelectedItemId(R.id.my_portfolio);
                    PortfolioBottomFragment portfolioBottomFragment = new PortfolioBottomFragment();
                    portfolioBottomFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(portfolioBottomFragment);

                    break;
                case NAV_TO_ORDER_BOTTOM_FRAGMENT:

                    checkedBottomTAB(bottomNavigation.getMenu().findItem(R.id.order));
                    bottomNavigation.setSelectedItemId(R.id.order);
                    OrderBottomFragment orderBottomFragment = new OrderBottomFragment();
                    orderBottomFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(orderBottomFragment);

                    break;
                case NAV_TO_LASTVISITED_SCREEN:

                    LastVisitedFragment lastVisitedFragment = new LastVisitedFragment();
                    lastVisitedFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(lastVisitedFragment);
                    break;
                case NAV_TO_NEWS_DISPLAY:
                    NewsDisplayFragment newsDisplayFragment = new NewsDisplayFragment();
                    newsDisplayFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(newsDisplayFragment);
                    break;
                case NAV_TO_NEWS_PAGER:
                    NewsFragmentPager newsFragmentPager = new NewsFragmentPager();
                    newsFragmentPager.setArguments(AccountDetails.globalArg);
                    setupFragment(newsFragmentPager);
                    break;

                case NAV_TO_RECOMMENDATION_SCREEN:
                    RecommDisplayFragment recommDisplayFragment = new RecommDisplayFragment();
                    recommDisplayFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(recommDisplayFragment);
                    break;
                case NAV_TO_ALERT_SCREEN:
                    AlertFragment alertFragment = new AlertFragment();
                    alertFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(alertFragment);
                    break;
                case NAV_TO_FUNDTRANSFER_SCREEN:
                    FundTransferFragment fundTransferFragment = new FundTransferFragment();
                    fundTransferFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(fundTransferFragment);
                    break;
                case NAV_TO_EDIS_MARGIN:
                    EdisMarginPledgeReport edisMarginPledgeReport = new EdisMarginPledgeReport();
                    edisMarginPledgeReport.setArguments(AccountDetails.globalArg);
                    setupFragment(edisMarginPledgeReport);
                    break;
                case NAV_TO_TRANSFER_PAYOUT:
                    FundTransfer_payout_fragment fundTransfer_payout_fragment = new FundTransfer_payout_fragment();
                    fundTransfer_payout_fragment.setArguments(AccountDetails.globalArg);
                    setupFragment(fundTransfer_payout_fragment);
                    break;
                case NAV_TO_TRANSFER_PAYING:
                    FundTransfer_tabs_Fragment fundTransfer_tabs_fragment = new FundTransfer_tabs_Fragment();
                    fundTransfer_tabs_fragment.setArguments(AccountDetails.globalArg);
                    setupFragment(fundTransfer_tabs_fragment);
                    break;

                case NAV_TO_PENDING_TAB_SCREEN:
                    PendingTabFragment pendingTabFragment = new PendingTabFragment();
                    pendingTabFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(pendingTabFragment);
                    break;
                case NAV_TO_EXECUTED_TAB_SCREEN:
                    ExecutedTabFragment executedTabFragment = new ExecutedTabFragment();
                    executedTabFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(executedTabFragment);
                    break;
                case NAV_TO_REJECTED_TAB_SCREEN:
                    RejectedTabFragment rejectedTabFragment = new RejectedTabFragment();
                    rejectedTabFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(rejectedTabFragment);
                    break;
                case NAV_TO_CANCELLED_TAB_SCREEN:
                    CancelledTabFragment cancelledTabFragment = new CancelledTabFragment();
                    cancelledTabFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(cancelledTabFragment);
                    break;


                case NAV_TO_EDIS_DASHBOARD_REPORT:
                    EdisDashboardReport edisDashboardReport = new EdisDashboardReport();
                    edisDashboardReport.setArguments(AccountDetails.globalArg);
                    setupFragment(edisDashboardReport);
                    break;
                case NAV_TO_EDIS_TRANSACTION_DETAILS:
                    EdisTransactionDetails edisTransactionDetails = new EdisTransactionDetails();
                    edisTransactionDetails.setArguments(AccountDetails.globalArg);
                    setupFragment(edisTransactionDetails);
                    break;

                case NAV_TO_LOGINPASSWORD:
                    LoginPasswordFragment loginPasswordFragment = new LoginPasswordFragment();
                    loginPasswordFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(loginPasswordFragment);
                    break;
                case NAV_TO_TRANSPASSWORD:
                    TransPasswordFragment transPasswordFragment = new TransPasswordFragment();
                    transPasswordFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(transPasswordFragment);
                    break;
                case NAV_TO_NEWS_TAB:

                    NewsTabFragment newsTabFragment = new NewsTabFragment();
                    newsTabFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(newsTabFragment);
                    break;
                case NAV_TO_USER_SCREEN:
                    UserProfile userProfile = new UserProfile();
                    userProfile.setArguments(AccountDetails.globalArg);
                    setupFragment(userProfile);
                    break;
                case NAV_TO_MFUND_DASHBOARD_SCREEN:

                    marketValuesLayout.setVisibility(View.GONE);
                    apptickerLayout.setVisibility(View.VISIBLE);
                    screen_title.setVisibility(View.GONE);
                    dashboardlayout.setVisibility(View.VISIBLE);

                    MFundDashboard mFundFragment = new MFundDashboard();
                    mFundFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(mFundFragment);
                    break;
                case NAV_TO_CHANGE_DEFAULTSCREEN_SCREEN:
                    AppSettingFragment appSettingFragment = new AppSettingFragment();
                    appSettingFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(appSettingFragment);
                    break;

                case NAV_TO_CHANGEPASSWORD_SCREEN:
                    ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                    changePasswordFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(changePasswordFragment);
                    break;

                case NAV_TO_CONTRACTINFO_SCREEN:
                    ContractInformationFragment contractInformationFragment = new ContractInformationFragment();
                    contractInformationFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(contractInformationFragment);
                    break;
                case NAV_TO_CHARTS_SCREEN:
                    ChartsFragment chartsFragment = new ChartsFragment();
                    chartsFragment.setArguments(AccountDetails.globalArgChartFrag);
                    setupFragment(chartsFragment);

                    break;
                case GREEK_MENU_OPENPOSITION:
                    OpenPositionFragment openPositionFragment = new OpenPositionFragment();
                    openPositionFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(openPositionFragment);
                    break;
                case NAV_TO_CUMULATIVE_SCREEN:
                    CumulativePositionFragment cumulativePositionFragment = new CumulativePositionFragment();
                    cumulativePositionFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(cumulativePositionFragment);
                    break;
                case NAV_TO_FUNDTRANSFERDETAILS_SCREEN:
                    FundTransferDetailsFragment fundTransferDetailsFragment = new FundTransferDetailsFragment();
                    fundTransferDetailsFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(fundTransferDetailsFragment);
                    break;

                case NAV_TO_BANK_DETAILS_SCREEN:
                    BankDetailsScreenFragment bankDetailsScreenFragment = new BankDetailsScreenFragment();
                    bankDetailsScreenFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(bankDetailsScreenFragment);
                    break;
                case NAV_TO_MFUND_DETAILS_SCREEN:
                    MFDetailsFragment mfDetailsScreenFragment = new MFDetailsFragment();
                    mfDetailsScreenFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(mfDetailsScreenFragment);
                    break;

                case NAV_TO_MARKET_HOME_SCREEN:
                    marketValuesLayout.setVisibility(View.GONE);
                    apptickerLayout.setVisibility(View.VISIBLE);
                    screen_title.setVisibility(View.GONE);
                    dashboardlayout.setVisibility(View.VISIBLE);
                    hideAppTitle();

                    setChildMenuSelection(0, 0);
                    MarketBottomFragment marketBottomFragment = new MarketBottomFragment();
                    marketBottomFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(marketBottomFragment);
                    checkedBottomTAB(bottomNavigation.getMenu().findItem(R.id.market));
                    bottomNavigation.setSelectedItemId(R.id.market);

                    break;
                case NAV_TO_DEMATHOLDING_SINGLE_SCREEN:
                    DematHoldingFragment dematHoldingFragment = new DematHoldingFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    dematHoldingFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(dematHoldingFragment);
                    break;

                case NAV_TO_USERCREATION_PAN_DETAILS:
                    UserCreationPanDetails userCreationPanDetails = new UserCreationPanDetails();
                    AccountDetails.AutoRefreshForDemat = false;
                    userCreationPanDetails.setArguments(AccountDetails.globalArg);
                    setupFragment(userCreationPanDetails);
                    break;

                case NAV_TO_MUTUALFUND_ACTION:
                    MutualFundFragmentNew mutualFundFragmentNew = new MutualFundFragmentNew();
                    AccountDetails.AutoRefreshForDemat = false;
                    mutualFundFragmentNew.setArguments(AccountDetails.globalArg);
                    setupFragment(mutualFundFragmentNew);
                    break;

                case NAV_TO_MUTUALFUND_TRADE:
                    MutualFundTradeFragment mutualFundTradeFragment = new MutualFundTradeFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    mutualFundTradeFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(mutualFundTradeFragment);
                    break;


                case NAV_TO_PAN_DETAILS:
                    PanDetails panDetails = new PanDetails();
                    AccountDetails.AutoRefreshForDemat = false;
                    panDetails.setArguments(AccountDetails.globalArg);
                    setupFragment(panDetails);
                    break;

                case NAV_TO_MUTUALFUND_PERSONAL_DETAILS:
                    MyAccountPersonalDetails personalDetail = new MyAccountPersonalDetails();
                    AccountDetails.AutoRefreshForDemat = false;
                    personalDetail.setArguments(AccountDetails.globalArg);
                    setupFragment(personalDetail);
                    break;
                case NAV_TO_ALL_BANKDETAILS_MF:
                    AllBankDetailsFragment allBankDetailsFragment = new AllBankDetailsFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    allBankDetailsFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(allBankDetailsFragment);
                    break;

                case NAV_TO_MUTUALFUND_BANK_MANDATE:
                    BankDetailsMandateFragment bankDetailsMandateFragment = new BankDetailsMandateFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    bankDetailsMandateFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(bankDetailsMandateFragment);
                    break;
                case NAV_TO_MUTUALFUND_KYC_UPLOAD:
                    KYCUploadPhoto kycDocumentUpload = new KYCUploadPhoto();
                    AccountDetails.AutoRefreshForDemat = false;
                    kycDocumentUpload.setArguments(AccountDetails.globalArg);
                    setupFragment(kycDocumentUpload);
                    break;
                case NAV_TO_MUTUALFUND_KYC_PAN_UPLOAD:
                    UploadpanDetails uploadpanDetails = new UploadpanDetails();
                    AccountDetails.AutoRefreshForDemat = false;
                    uploadpanDetails.setArguments(AccountDetails.globalArg);
                    setupFragment(uploadpanDetails);
                    break;
                case NAV_TO_MUTUALFUND_KYC_AADHAR_UPLOAD:
                    UploadAadharDetails uploadAadharDetails = new UploadAadharDetails();
                    AccountDetails.AutoRefreshForDemat = false;
                    uploadAadharDetails.setArguments(AccountDetails.globalArg);
                    setupFragment(uploadAadharDetails);
                    break;
                case NAV_TO_MUTUALFUND_KYC_BANK_UPLOAD:
                    UploadBankDetails uploadbankDetails = new UploadBankDetails();
                    AccountDetails.AutoRefreshForDemat = false;
                    uploadbankDetails.setArguments(AccountDetails.globalArg);
                    setupFragment(uploadbankDetails);
                    break;
                case NAV_TO_MUTUALFUND_KYC_CHEQUE_UPLOAD:
                    UploadChequeDetails uploadChequeDetails = new UploadChequeDetails();
                    AccountDetails.AutoRefreshForDemat = false;
                    uploadChequeDetails.setArguments(AccountDetails.globalArg);
                    setupFragment(uploadChequeDetails);
                    break;
                case NAV_TO_MUTUALFUND_KYC_IPVP_UPLOAD:
                    UploadIpvp uploadIpvp = new UploadIpvp();
                    AccountDetails.AutoRefreshForDemat = false;
                    uploadIpvp.setArguments(AccountDetails.globalArg);
                    setupFragment(uploadIpvp);
                    break;
                case NAV_TO_MUTUALFUND_SIGNATURE_UPLOAD:
                    UploadSignature uploadSignature = new UploadSignature();
                    AccountDetails.AutoRefreshForDemat = false;
                    uploadSignature.setArguments(AccountDetails.globalArg);
                    setupFragment(uploadSignature);
                    break;
                case NAV_TO_MUTUALFUND_GET_QUOTE:
                    MutualFundGetQuoteFragment mutualFundGetQuoteFragment = new MutualFundGetQuoteFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    mutualFundGetQuoteFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(mutualFundGetQuoteFragment);
                    break;
                case NAV_TO_MUTUAL_FUND_STP:
                    MutualFundSTPFragment mutualFundSTPFragment = new MutualFundSTPFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    mutualFundSTPFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(mutualFundSTPFragment);
                    break;
                case NAV_TO_MUTUAL_FUND_SWP:
                    MutualFundSWPFragment mutualFundSWPFragment = new MutualFundSWPFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    mutualFundSWPFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(mutualFundSWPFragment);
                    break;
                case NAV_TO_MUTUAL_FUND_SIP:
                    MutualFundSipFragment mutualFundSIPFragment = new MutualFundSipFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    mutualFundSIPFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(mutualFundSIPFragment);
                    break;
                case NAV_TO_MUTUAL_FUND_SIP_SUMMARY:
                    MutualFundSipSummary mutualFundSIPSummery = new MutualFundSipSummary();
                    AccountDetails.AutoRefreshForDemat = false;
                    mutualFundSIPSummery.setArguments(AccountDetails.globalArg);
                    setupFragment(mutualFundSIPSummery);
                    break;
                case NAV_TO_MF_WATCHLIST_MF:
                    MFWatchlistFragment mfWatchlistFragment = new MFWatchlistFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    mfWatchlistFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(mfWatchlistFragment);
                    break;


                case NAV_TO_MF_ORDER_BOOK_DETAILS_SCREEN:
                    MFOrderBookDetailsFragment mfOrderBookDetailsFragment = new MFOrderBookDetailsFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    mfOrderBookDetailsFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(mfOrderBookDetailsFragment);
                    break;
                case NAV_TO_MUTUALFUND_TRADE_PREVIEW:
                    MutualFundTradePreview mutualFundTradePreview = new MutualFundTradePreview();
                    AccountDetails.AutoRefreshForDemat = false;
                    mutualFundTradePreview.setArguments(AccountDetails.globalArg);
                    setupFragment(mutualFundTradePreview);
                    break;

                case NAV_TO_MF_ORDER_BOOK_SCREEN:
                    MFOrderBookFragment mfOrderBookFragment = new MFOrderBookFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    mfOrderBookFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(mfOrderBookFragment);
                    break;
                case NAV_TO_FIIDII_SCREEN:
                    FiiDiiActivitiesTabsFragment fiiDiiActivitiesTabsFragment = new FiiDiiActivitiesTabsFragment();
                    fiiDiiActivitiesTabsFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(fiiDiiActivitiesTabsFragment);
                    break;
                case NAV_TO_CHARTING_SCREEN:
                    ChartingFragment chartingTabsFragment = new ChartingFragment();
                    chartingTabsFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(chartingTabsFragment);
                    break;
                case NAV_TO_STRATEGY_FINDER:
                    SFScanFragment strategyFinderFragment = new SFScanFragment();
                    AccountDetails.AutoRefreshForDemat = false;
                    strategyFinderFragment.setArguments(AccountDetails.globalArg);
                    setupFragment(strategyFinderFragment);
                    break;


                case NAV_TO_BOTTOM_POT_FOLIO_FRAGMENT:

                    checkedBottomTAB(bottomNavigation.getMenu().findItem(R.id.my_portfolio));
                    bottomNavigation.setSelectedItemId(R.id.my_portfolio);
                    setupFragment(new PortfolioBottomFragment());
                    performMenuAction("GREEK_MENU_BOTTOM_PORTFOLIO_TXT");


            }

        } catch (Exception e) {
            Log.e("GreekBaseActivity", "Exception");
        }
    }

    public void setupFragment(GreekBaseFragment fragment) {
        addFragment(R.id.activityFrameLayout, fragment, false);
    }

    public void addFragment(int containerViewId, Fragment fragment, boolean addStack) {

        if (fragment == null) {
            GreekLog.error("Fragment is null.");
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            // Pause the current fragment before adding new fragment
            FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
            GreekBaseFragment currentFragment = (GreekBaseFragment) fragmentManager.findFragmentByTag(backEntry.getName());
            if (currentFragment != null) currentFragment.onFragmentPause();
        } else {
            // Last fragment is not attached to back stack. So get
            // the fragment by id and pause the current fragment.
            GreekBaseFragment currentFragment = (GreekBaseFragment) fragmentManager.findFragmentById(containerViewId);
            if (currentFragment != null) currentFragment.onFragmentPause();
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String tag = fragment.getClass().toString();

        if (fragment.isAdded()) {

            fragmentTransaction.show(fragment);

        } else {
            if (tag.contains("DashBoardFragment") || tag.contains("WatchListFragment") || tag.contains("MFundDashboard") || tag.contains("MFWatchlistFragment")
                    || tag.contains("PanDetails") || tag.contains("MutualFundFragmentNew") || tag.contains("MFOrderBookFragment")
                    || tag.contains("MarketMutualFundFragment") || tag.contains("MutualFundTradeFragment") || tag.contains("MutualFundSipFragment")
                    || tag.contains("MutualFundGetQuoteFragment") || tag.contains("MutualFundSipSummary") || tag.contains("MFDetailsFragment")
                    || tag.contains("MyAccountPersonalDetails") || tag.contains("MFEditWatchlistFragment") || tag.contains("MFAddNewPortflio")
                    || tag.contains("AllBankDetailsFragment") || tag.contains("KYCUploadPhoto") || tag.contains("MFOrderBookDetailsFragment")
                    || tag.contains("UploadChequeDetails") || tag.contains("UploadBankDetails") || tag.contains("UploadAadharDetails")
                    || tag.contains("UploadSignature") || tag.contains("UploadIpvp") || tag.contains("UploadpanDetails")
                    || tag.contains("BankDetailsMandateFragment") || tag.contains("MutualFundMandateFragment") || tag.contains("MFDetailsFragment") || tag.contains("OrderBottomFragment")) {

                marketValuesLayout.setVisibility(View.GONE);
                apptickerLayout.setVisibility(View.VISIBLE);
                //  screen_title.setVisibility(View.GONE);
                NeedOfDash = true;
                dashboardlayout.setVisibility(View.VISIBLE);
                hideAppTitle();
                AccountDetails.currentFragment = NAV_TO_MARKET_HOME_SCREEN;

            } else {

                if (!tag.contains("SnapshotFloatingFragment") && !tag.contains("LastVisitedFragment") && !tag.contains("WatchListFragment") && !tag.contains("PortfolioSectionFragment")) {
//                    UncheckedBottomTAB();
                }

                if (tag.contains("IndicesStockFragment")) {

                    AccountDetails.currentFragment = NAV_TO_INDICES_STOCK_SCREEN;
                }
                if (tag.contains("MarketsFragment")) {

                    AccountDetails.currentFragment = NAV_TO_MARKET_STARTUP_SCREEN;
                }


                NeedOfDash = false;

                marketValuesLayout.setVisibility(View.GONE);
                apptickerLayout.setVisibility(View.VISIBLE);
                screen_title.setVisibility(View.GONE);
                dashboardlayout.setVisibility(View.VISIBLE);

            }

            if (addStack) {
                fragmentTransaction.add(containerViewId, fragment, tag);
                fragmentTransaction.addToBackStack(tag);
            } else {
                clearTitles();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(containerViewId, fragment, tag);
            }
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        if (!addStack) ((GreekBaseFragment) fragment).onFragmentResume();
    }

    public void onSessionOut() {

        USER_TYPE = USER.OPENUSER;
        refreshMenuDrawer();

        Util.getPrefs(GreekBaseActivity.this).edit().putString("GREEK_RETAINED_CUST_PASS", "").apply();
        Util.getPrefs(GreekBaseActivity.this).edit().putLong("LastValidatedTime", 0).apply();
        Intent args = new Intent();
        args.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);
        setupDefaultFragment(args);
    }


    public void onLogout(int status) {


        SharedPreferences.Editor editor = Util.getPrefs(getApplicationContext()).edit();
        editor.putString("transcation_pass", null);
        editor.putString("transcation_pass_Date", null);
        editor.putString("SharedPref_CLIENTCODE", null);
        editor.putString("SharedPref_USERNAME", null);
        editor.putString("SharedPref_SESSIONID", null);
        editor.putString("setArachne_Port", " ");
        editor.putString("setApollo_Port", " ");
        editor.putString("setIris_Port", " ");
        editor.putString("setArachne_IP", " ");
        editor.putString("setApollo_IP", " ");
        editor.putBoolean("LogoutStatus", true);
        editor.commit();
        editor.apply();


        if (status == FORCELOGOFF) {
            tcpConnectionHandler.stopStreaming();
        } else if (status == ORDERFORCELOGOFF) {
            tcpOrderConnectionHandler.stopStreaming();
        } else if (status == ALL) {

            tcpConnectionHandler.stopStreaming();
            tcpOrderConnectionHandler.stopStreaming();
        }

        USER_TYPE = USER.OPENUSER;
        clearWatchlistDataFromSP(USER.IBTCUSTOMER);

        //TODO:Reset AccountDetails class's all variables.
        AccountDetails.isValidSession = true;
        AccountDetails.setIsApolloConnected(false);
        AccountDetails.setIsIrisConnected(false);
        AccountDetails.setIsLogout(true);
        AccountDetails.isLoginAndDisconnected = false;
        AccountDetails.setHeartBeatApolloCount(0);
        AccountDetails.setHeartBeatIrisCount(0);

        //Apollo server related flags.
        AccountDetails.forceClose = 3;
        AccountDetails.fClose = 0;
        AccountDetails.broadcastServerAuthenticated = false;
        AccountDetails.recoonectbroadcastServerAuthenticated = false;

        //Iris server related flags.
        AccountDetails.orderforceClose = 3;
        AccountDetails.ofClose = 0;
        AccountDetails.isValidSession = true;
        AccountDetails.orderServerAuthenticated = false;


        AccountDetails.clearCache(getApplicationContext());

        refreshMenuDrawer();
        AccountDetails.setValidateTpassFlag(false);
        Intent guestLogin = new Intent(GreekBaseActivity.this, LoginActivity.class);
        guestLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(guestLogin);
        AccountDetails.setLastSelectedGroup("");
        finish();
    }

    private void clearWatchlistDataFromSP(USER usertype) {

        // WatchListFragment.hashMapWatchListData = new HashMap<String, List<SymbolList>>();


        if (hashMapWatchListData != null && hashMapWatchListData.size() > 0) {
            PortfolioGetUserWatchListResponse portfolioGetUserWatchListResponse = new PortfolioGetUserWatchListResponse();
            portfolioGetUserWatchListResponse.setErrorCode("0");
            List<GetUserwatchlist> getUserwatchlists = new ArrayList<>();
            for (int i = 0; i < hashMapWatchListData.size(); i++) {
                GetUserwatchlist getUserwatchlist = new GetUserwatchlist();
                getUserwatchlist.setWatchlistName(hashMapWatchListData.keySet().toArray()[i].toString());
                getUserwatchlist.setWatchtype("User");
                if (defaultGroupName.equalsIgnoreCase(hashMapWatchListData.keySet().toArray()[i].toString())) {
                    getUserwatchlist.setDefaultFlag("true");
                } else {
                    getUserwatchlist.setDefaultFlag("false");
                }
                List<SymbolList> symbolList = new ArrayList<>();
                symbolList = hashMapWatchListData.get(hashMapWatchListData.keySet().toArray()[i].toString());

                getUserwatchlist.setSymbolList(symbolList);

                getUserwatchlists.add(getUserwatchlist);
            }


            portfolioGetUserWatchListResponse.setGetUserwatchlist(getUserwatchlists);

            SharedPreferences.Editor editor1 = Util.getPrefs(getApplicationContext()).edit();
            Gson gson = new Gson();
            String data = gson.toJson(portfolioGetUserWatchListResponse);
            editor1.putString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), data);
            editor1.apply();
            editor1.commit();
        } else {
            PortfolioGetUserWatchListResponse portfolioGetUserWatchListResponse1 = new PortfolioGetUserWatchListResponse();
            portfolioGetUserWatchListResponse1.setErrorCode("0");
            List<GetUserwatchlist> getUserwatchlists = new ArrayList<>();
/*            for (int i = 0; i < hashMapWatchListData.size(); i++) {
                GetUserwatchlist getUserwatchlist = new GetUserwatchlist();
                getUserwatchlist.setWatchlistName(hashMapWatchListData.keySet().toArray()[i].toString());
                getUserwatchlist.setWatchtype("User");
                if(defaultGroupName.equalsIgnoreCase(hashMapWatchListData.keySet().toArray()[i].toString())){
                    getUserwatchlist.setDefaultFlag("true");
                }else{
                    getUserwatchlist.setDefaultFlag("false");
                }
                List<SymbolList> symbolList = new ArrayList<>();
                symbolList =hashMapWatchListData.get(hashMapWatchListData.keySet().toArray()[i].toString());

                getUserwatchlist.setSymbolList(symbolList);

                getUserwatchlists.add(getUserwatchlist);
            }*/


            portfolioGetUserWatchListResponse1.setGetUserwatchlist(getUserwatchlists);

            SharedPreferences.Editor editor1 = Util.getPrefs(getApplicationContext()).edit();
            Gson gson = new Gson();
            String data = gson.toJson(portfolioGetUserWatchListResponse1);
            editor1.putString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), data);
            editor1.apply();
            editor1.commit();
        }


        if (usertype == USER.OPENUSER) {
            hashMapWatchListData.clear();
            SharedPreferences.Editor editor1 = Util.getPrefs(GreekBaseActivity.this).edit();
            editor1.putString("HMapWatchlist" + AccountDetails.getUsername(getApplicationContext()), " ");
            editor1.apply();
            editor1.commit();
        } else {
            hashMapWatchListData.clear();
            SharedPreferences.Editor editor1 = Util.getPrefs(GreekBaseActivity.this).edit();
            editor1.putString("WatchlistGroupsNew", " ");
            editor1.apply();
            editor1.commit();
        }

    }

    //TODO:sushant
    public void onExitApp() {
        AccountDetails.setIsApolloConnected(false);
        AccountDetails.setIsIrisConnected(false);
        AccountDetails.clearCache(getApplicationContext());
        if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER || USER_TYPE == USER.MFCUSTOMER) {
            logoutCustomerExit();
            USER_TYPE = USER.OPENUSER;
        }
        AccountDetails.setLastSelectedGroup("");
        finish();
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {

        boolean isNiftyRemoved = false;
        boolean issensexRemoved = false;

        JSONResponse jsonResponse = (JSONResponse) response;
        if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && GENERATETPINCDSL_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {

                String message = jsonResponse.getResponseData().getString("Message");

                GreekDialog.alertDialogOnly(this, GreekBaseActivity.GREEK, message, getString(R.string.GREEK_OK));


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && SENDAUTHREQUESTCDSL_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {

                Gson gson = new Gson();
                SendAuthorizationResponse sendAuthorizationResponse = gson.fromJson(String.valueOf(response), SendAuthorizationResponse.class);

                Intent intent = new Intent(GreekBaseActivity.this, WebContentCDSL.class);
                intent.putExtra("DPId", sendAuthorizationResponse.getResponse().getData().getDPId());
                intent.putExtra("ReqId", sendAuthorizationResponse.getResponse().getData().getReqId());
                intent.putExtra("Version", sendAuthorizationResponse.getResponse().getData().getVersion());
                intent.putExtra("TransDtls", sendAuthorizationResponse.getResponse().getData().getResponse());
                intent.putExtra("Url", sendAuthorizationResponse.getResponse().getData().getUrl());
                startActivityForResult(intent, 4);
                AccountDetails.setIsMainActivity(true);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && INDIAN_INDICES_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {
                indianIndicesResponse = (MarketsIndianIndicesResponse) jsonResponse.getResponse();
                goToIndianIndicesPage();
            } catch (Exception e) {
                actionBar.setNiftyClick(true);
                actionBar.setSensexClick(true);
                e.printStackTrace();
            }
        } else if ("getMarketStatus".equals(jsonResponse.getServiceName())) {
            try {
                //uncomment by rohit for 1st order offline issue on 12/4/2020
                MarketStatusPostResponse marketStatusPostResponse = (MarketStatusPostResponse) jsonResponse.getResponse();
                List<MarketStatusResponse> statusResponse = marketStatusPostResponse.getStatusResponse();
                updateMarketStatus(statusResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("getAllowedProduct".equals(jsonResponse.getServiceName())) {
            try {
                AllowedProductResponse allowedProductResponse = (AllowedProductResponse) jsonResponse.getResponse();
                List<AllowedProduct> statusResponse = allowedProductResponse.getAllowedProduct();
                AccountDetails.setAllowedProduct(allowedProductResponse.getAllowedProduct());
                ArrayList<String> productList = new ArrayList<String>();
                for (int i = 0; i < allowedProductResponse.getAllowedProduct().size(); i++) {
                    productList.add(allowedProductResponse.getAllowedProduct().get(i).getcProductName());
                    if (allowedProductResponse.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase(AccountDetails.getDefaultProducttype_name())) {
                        AccountDetails.setProductTypeFlag(AccountDetails.getAllowedProduct().get(i).getiProductToken());
                    }
                }
                AccountDetails.setAllowedProductList(productList);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("getKYCDetailsMF".equals(jsonResponse.getServiceName())) {
            try {
                //Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_LONG).show();
                kycDetailResponse = (KycDetailResponse) jsonResponse.getResponse();
                args = new Bundle();
                args.putString("cErrorCode", kycDetailResponse.getErrorCode());


                if (kycDetailResponse.getErrorCode().equalsIgnoreCase("3")) {

                    PanDetails panDetails = new PanDetails();
                    setupFragment(panDetails);

                } else {

                    args.putString("cOccupation", kycDetailResponse.getcOccupation());
                    args.putString("cFirstApplicantName", kycDetailResponse.getcFirstApplicantName());
                    args.putString("cSecondApplicantName", kycDetailResponse.getcSecondApplicantName());
                    args.putString("cThirdApplicantName", kycDetailResponse.getcThirdApplicantName());
                    args.putString("cFirstApplicantDOB", kycDetailResponse.getcFirstApplicantDOB());
                    args.putString("cFirstAppGender", kycDetailResponse.getcFirstAppGender());
                    args.putString("cClientGuardian", kycDetailResponse.getcClientGuardian());
                    args.putString("cFirstApplicantPAN", kycDetailResponse.getcFirstApplicantPAN());
                    args.putString("cClientNominee", kycDetailResponse.getcClientNominee());
                    args.putString("cClientNomineeRelation", kycDetailResponse.getcClientNomineeRelation());
                    args.putString("cGuardianPAN", kycDetailResponse.getcGuardianPAN());
                    args.putString("income", kycDetailResponse.getIncome());
                    args.putString("cAdd1", kycDetailResponse.getcAdd1());
                    args.putString("cAdd2", kycDetailResponse.getcAdd2());
                    args.putString("cAdd3", kycDetailResponse.getcAdd3());
                    args.putString("cCity", kycDetailResponse.getcCity());
                    args.putString("cClientState", kycDetailResponse.getcClientState());
                    args.putString("cPINCode", kycDetailResponse.getcPINCode());
                    args.putString("cCountry", kycDetailResponse.getcCountry());
                    args.putString("cResiPhone", kycDetailResponse.getcResiPhone());
                    args.putString("cResiFax", kycDetailResponse.getcResiFax());
                    args.putString("cOfficePhone", kycDetailResponse.getcOfficePhone());
                    args.putString("cClientOfficeFax", kycDetailResponse.getcClientOfficeFax());
                    args.putString("cClientEmail", kycDetailResponse.getcClientEmail());
                    args.putString("cForAdd1", kycDetailResponse.getcForAdd1());
                    args.putString("cForAdd2", kycDetailResponse.getcForAdd2());
                    args.putString("cForAdd3", kycDetailResponse.getcForAdd3());
                    args.putString("cForCity", kycDetailResponse.getcForCity());
                    args.putString("cForPinCode", kycDetailResponse.getcForPinCode());
                    args.putString("cForState", kycDetailResponse.getcForState());
                    args.putString("cForCountry", kycDetailResponse.getcForCountry());
                    args.putString("cForResiPhone", kycDetailResponse.getcForResiPhone());
                    args.putString("cForResiFax", kycDetailResponse.getcForResiFax());
                    args.putString("cForOffPhone", kycDetailResponse.getcForOffPhone());
                    args.putString("cForOffFax", kycDetailResponse.getcForOffFax());
                    args.putString("cMobile", kycDetailResponse.getcMobile());

                    args.putString("cAadharNo", kycDetailResponse.getcAadharNo());
                    args.putString("cNationality", kycDetailResponse.getcNationality());
                    args.putString("cMaritalStatus", kycDetailResponse.getcMaritalStatus());


                    args.putString("cAccType1", kycDetailResponse.getcAccType1());
                    args.putString("cAccNoNo1", kycDetailResponse.getcAccNoNo1());
                    args.putString("cClientMICRNo1", kycDetailResponse.getcClientMICRNo1());
                    args.putString("cNEFTIFSCCode1", kycDetailResponse.getcNEFTIFSCCode1());
                    args.putString("cBankName1", kycDetailResponse.getcBankName1());
                    args.putString("cBankBranch1", kycDetailResponse.getcBankBranch1());
                    args.putString("cDefaultBankFlag1", kycDetailResponse.getcDefaultBankFlag1());

                    args.putString("cAccType2", kycDetailResponse.getcAccType2());
                    args.putString("cAccNoNo2", kycDetailResponse.getcAccNoNo2());
                    args.putString("cClientMICRNo2", kycDetailResponse.getcClientMICRNo2());
                    args.putString("cNEFTIFSCCode2", kycDetailResponse.getcNEFTIFSCCode2());
                    args.putString("cBankName2", kycDetailResponse.getcBankName2());
                    args.putString("cBankBranch2", kycDetailResponse.getcBankBranch2());
                    args.putString("cDefaultBankFlag2", kycDetailResponse.getcDefaultBankFlag2());

                    args.putString("cAccType3", kycDetailResponse.getcAccType3());
                    args.putString("cAccNoNo3", kycDetailResponse.getcAccNoNo3());
                    args.putString("cClientMICRNo3", kycDetailResponse.getcClientMICRNo3());
                    args.putString("cNEFTIFSCCode3", kycDetailResponse.getcNEFTIFSCCode3());
                    args.putString("cBankName3", kycDetailResponse.getcBankName3());
                    args.putString("cBankBranch3", kycDetailResponse.getcBankBranch3());
                    args.putString("cDefaultBankFlag3", kycDetailResponse.getcDefaultBankFlag3());

                    args.putString("cAccType4", kycDetailResponse.getcAccType4());
                    args.putString("cAccNoNo4", kycDetailResponse.getcAccNoNo4());
                    args.putString("cClientMICRNo4", kycDetailResponse.getcClientMICRNo4());
                    args.putString("cNEFTIFSCCode4", kycDetailResponse.getcNEFTIFSCCode4());
                    args.putString("cBankName4", kycDetailResponse.getcBankName4());
                    args.putString("cBankBranch4", kycDetailResponse.getcBankBranch4());
                    args.putString("cDefaultBankFlag4", kycDetailResponse.getcDefaultBankFlag4());

                    args.putString("cAccType5", kycDetailResponse.getcAccType5());
                    args.putString("cAccNoNo5", kycDetailResponse.getcAccNoNo5());
                    args.putString("cClientMICRNo5", kycDetailResponse.getcClientMICRNo5());
                    args.putString("cNEFTIFSCCode5", kycDetailResponse.getcNEFTIFSCCode5());
                    args.putString("cBankName5", kycDetailResponse.getcBankName5());
                    args.putString("cBankBranch5", kycDetailResponse.getcBankBranch5());
                    args.putString("cDefaultBankFlag5", kycDetailResponse.getcDefaultBankFlag5());


                    MyAccountPersonalDetails personalDetail = new MyAccountPersonalDetails();
                    personalDetail.setArguments(args);
                    setupFragment(personalDetail);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (MARKETS_SVC_GROUP.equals(jsonResponse.getServiceGroup()) &&
                "getIndianIndicesDataForUserV2".equals(jsonResponse.getServiceName())) {
            try {
                int textColorPositive;
                if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                    textColorPositive = R.color.whitetheambuyColor;
                } else {
                    textColorPositive = R.color.dark_green_positive;
                }

                int textColorNegative = R.color.dark_red_negative;

                streamingList.clear();
                indianIndicesResponse = (MarketsIndianIndicesResponse) jsonResponse.getResponse();
                SharedPreferences getsp = PreferenceManager.getDefaultSharedPreferences(this);


                MarketsIndianIndicesResponse tempResponse = (MarketsIndianIndicesResponse) jsonResponse.getResponse();
                ArrayList<Integer> indicesIndex = new ArrayList<>();
                ArrayList<IndianIndice> IndianIndiceList = new ArrayList<>();

                for (int i = 0; i < indianIndicesResponse.getIndianIndices().size(); i++) {

                    IndianIndiceList.add(indianIndicesResponse.getIndianIndices().get(i));
                    if (indianIndicesResponse.getIndianIndices().get(i).getToken().equalsIgnoreCase("Nifty 50")) {
                        indianIndicesResponse.getIndianIndices().get(i).setToken("Nifty");
                    }
                    if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("0")) {
                        niftyTxt.setText(indianIndicesResponse.getIndianIndices().get(i).getToken());

                        if (getAssetType(IndianIndiceList.get(i).getIndexCode()).equalsIgnoreCase("currency")) {
                            niftyValueTxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLtp()))));
                            niftyChngTxt.setText(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getP_change())) + "%)");
                            actionBar.setActionbarChangenifty(indianIndicesResponse.getIndianIndices().get(i).getToken(), StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLtp()))), indianIndicesResponse.getIndianIndices().get(i).getChange());
                        } else {

                            niftyValueTxt.setText(StringStuff.commaDecorator(indianIndicesResponse.getIndianIndices().get(i).getLtp()));
                            niftyChngTxt.setText(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getP_change())) + "%)");

                            actionBar.setActionbarChangenifty(indianIndicesResponse.getIndianIndices().get(i).getToken(), StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLtp()))), indianIndicesResponse.getIndianIndices().get(i).getChange());
                        }

                        if (niftyChngTxt.getText().toString().startsWith("-")) {
                            niftyChngTxt.setTextColor(getResources().getColor(R.color.bajaj_light_red));
                        } else {
                            if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                                niftyChngTxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
                            } else {
                                niftyChngTxt.setTextColor(getResources().getColor(R.color.dark_green_positive));
                            }
                        }

                        streamingList.add(indianIndicesResponse.getIndianIndices().get(i).getIndexCode());
                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("1")) {


                        if (getAssetType(IndianIndiceList.get(i).getIndexCode()).equalsIgnoreCase("currency")) {
                            sensextxt.setText(indianIndicesResponse.getIndianIndices().get(i).getToken());
                            sensexValueTxt.setText(StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLtp()))));
                            sensexChngTxt.setText(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getP_change())) + "%)");

                            actionBar.setActionbarChangeSensex(indianIndicesResponse.getIndianIndices().get(i).getToken(),
                                    StringStuff.commaINRDecorator(String.format("%.4f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLtp()))),
                                    indianIndicesResponse.getIndianIndices().get(i).getChange());

                        } else {

                            sensextxt.setText(indianIndicesResponse.getIndianIndices().get(i).getToken());
                            sensexValueTxt.setText(StringStuff.commaINRDecorator(indianIndicesResponse.getIndianIndices().get(i).getLtp()));
                            sensexChngTxt.setText(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getP_change())) + "%)");
                            actionBar.setActionbarChangeSensex(indianIndicesResponse.getIndianIndices().get(i).getToken(),
                                    StringStuff.commaINRDecorator(String.format("%.2f", Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getLtp()))),
                                    indianIndicesResponse.getIndianIndices().get(i).getChange());
                        }
                        if (sensexChngTxt.getText().toString().startsWith("-")) {
                            sensexChngTxt.setTextColor(getResources().getColor(R.color.bajaj_light_red));
                        } else {
                            if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                                sensexChngTxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
                            } else {
                                sensexChngTxt.setTextColor(getResources().getColor(R.color.dark_green_positive));
                            }
                        }

                        streamingList.add(indianIndicesResponse.getIndianIndices().get(i).getIndexCode());

                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("2")) {

                        firstsymbolTxt.setText(IndianIndiceList.get(i).getToken());

                        if (getAssetType(IndianIndiceList.get(i).getIndexCode()).equalsIgnoreCase("currency")) {
                            firstvalueTxt.setText(String.format("%.4f", Double.parseDouble((IndianIndiceList.get(i).getLtp()))));
                            firstChngTxt.setText(String.format("%.4f", Double.parseDouble(IndianIndiceList.get(i).getChange()))
                                    + "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");

                        } else {

                            firstvalueTxt.setText(String.format("%.2f", Double.parseDouble((IndianIndiceList.get(i).getLtp()))));
                            firstChngTxt.setText(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getChange()))
                                    + "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");

                        }
                        if (firstChngTxt.getText().toString().startsWith("-")) {
                            firstChngTxt.setTextColor(getResources().getColor(R.color.bajaj_light_red));
                        } else {
                            if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                                firstChngTxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
                            } else {
                                firstChngTxt.setTextColor(getResources().getColor(R.color.dark_green_positive));
                            }
                        }

                        streamingList.add(indianIndicesResponse.getIndianIndices().get(i).getIndexCode());

                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("3")) {

                        secondSymbolTxt.setText(IndianIndiceList.get(i).getToken());

                        if (getAssetType(IndianIndiceList.get(i).getIndexCode()).equalsIgnoreCase("currency")) {
                            secondvalueTxt.setText(String.format("%.4f", Double.parseDouble(IndianIndiceList.get(i).getLtp())));
                            secondChnagTxt.setText(String.format("%.4f", Double.parseDouble(IndianIndiceList.get(i).getChange()))
                                    + "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");

                        } else {

                            secondvalueTxt.setText(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getLtp())));
                            secondChnagTxt.setText(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getChange()))
                                    + "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");

                        }

                        if (secondChnagTxt.getText().toString().startsWith("-")) {
                            secondChnagTxt.setTextColor(getResources().getColor(R.color.bajaj_light_red));
                        } else {
                            if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                                secondChnagTxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
                            } else {
                                secondChnagTxt.setTextColor(getResources().getColor(R.color.dark_green_positive));
                            }
                        }

                        streamingList.add(indianIndicesResponse.getIndianIndices().get(i).getIndexCode());


                    } else if (IndianIndiceList.get(i).getSeqNo().equalsIgnoreCase("4")) {

                        thirdsymbolTxt.setText(IndianIndiceList.get(i).getToken());

                        if (getAssetType(IndianIndiceList.get(i).getIndexCode()).equalsIgnoreCase("currency")) {

                            thrdvalueTxt.setText(String.format("%.4f", Double.parseDouble(IndianIndiceList.get(i).getLtp())));
                            thrdChngTxt.setText(String.format("%.4f", Double.parseDouble(IndianIndiceList.get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");
                        } else {
                            thrdvalueTxt.setText(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getLtp())));
                            thrdChngTxt.setText(String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getChange())) +
                                    "(" + String.format("%.2f", Double.parseDouble(IndianIndiceList.get(i).getP_change())) + "%)");
                        }
                        if (thrdChngTxt.getText().toString().startsWith("-")) {
                            thrdChngTxt.setTextColor(getResources().getColor(R.color.bajaj_light_red));
                        } else {

                            if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
                                thrdChngTxt.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
                            } else {
                                thrdChngTxt.setTextColor(getResources().getColor(R.color.dark_green_positive));
                            }
                        }
                        streamingList.add(indianIndicesResponse.getIndianIndices().get(i).getIndexCode());

                    }


                }


                sendStreamingRequest("index");

                hideProgress();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void updateMarketStatus(List<MarketStatusResponse> response) {

        LinkedHashMap<String, String> hm = new LinkedHashMap<>();
        hm.put("green", "1");
        hm.put("yellow", "2");
        hm.put("blue", "3");
        hm.put("pink", "4");
        hm.put("red", "5");

        for (int i = 0; i < response.size(); i++) {
            if (AccountDetails.allowedmarket_nse) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = true;
                    nse_eq = "green";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "yellow";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = true;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "blue";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "pink";
                    AccountDetails.nse_eq_status = false;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("1")) {

                    eq = false;
                    nse_eq = "red";
                    AccountDetails.nse_eq_status = true;
                    AccountDetails.isPreOpen_nse_eq = false;
                    AccountDetails.isPostClosed_nse_eq = false;

                }
            }

            //FOR FNO
            /*  int fo = 0;*/
            if (AccountDetails.allowedmarket_nfo) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    nse_fno = "green";
                    fno = true;
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "yellow";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = true;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "blue";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "pink";
                    AccountDetails.nse_fno_status = false;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("2")) {

                    fno = false;
                    nse_fno = "red";
                    AccountDetails.nse_fno_status = true;
                    AccountDetails.isPreOpen_nse_fno = false;
                    AccountDetails.isPostClosed_nse_fno = false;

                }
            }
            //FOR CURRENCY

            /* int cd = 0;*/
            if (AccountDetails.allowedmarket_ncd) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    cd = true;
                    nse_cd = "green";
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "yellow";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = true;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "blue";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "pink";
                    cd = false;
                    AccountDetails.nse_cd_status = false;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("3")) {

                    nse_cd = "red";
                    cd = false;
                    AccountDetails.nse_cd_status = true;
                    AccountDetails.isPreOpen_nse_cd = false;
                    AccountDetails.isPostClosed_nse_cd = false;

                }
            }

            if (AccountDetails.allowedmarket_mcx) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "green";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "yellow";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = true;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "blue";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "pink";
                    AccountDetails.mcx_com_status = false;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("9")) {

                    mcx_com = "red";
                    AccountDetails.mcx_com_status = true;
                    AccountDetails.isPreOpen_mcx_com = false;
                    AccountDetails.isPostClosed_mcx_com = false;
                }
            }

            if (AccountDetails.allowedmarket_bse) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bseeq = true;
                    bse_eq = "green";
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;
                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    bseeq = false;
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "yellow";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = true;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    bseeq = false;
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "blue";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "pink";
                    bseeq = false;
                    AccountDetails.bse_eq_status = false;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("4")) {

                    bse_eq = "red";
                    AccountDetails.bse_eq_status = true;
                    AccountDetails.isPreOpen_bse_eq = false;
                    AccountDetails.isPostClosed_bse_eq = false;
                    bseeq = false;

                }

            }

            if (AccountDetails.allowedmarket_bfo) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bsefno = true;
                    bse_fno = "green";
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    bsefno = false;
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "yellow";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = true;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;
                    bsefno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "blue";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "pink";
                    bsefno = false;
                    AccountDetails.bse_fno_status = false;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("5")) {

                    bse_fno = "red";
                    AccountDetails.bse_fno_status = true;
                    AccountDetails.isPreOpen_bse_fno = false;
                    AccountDetails.isPostClosed_bse_fno = false;
                    bsefno = false;

                }

            }

            if (AccountDetails.allowedmarket_bcd) {

                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bsecd = true;
                    bse_cd = "green";
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "yellow";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = true;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "blue";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "pink";
                    bsecd = false;
                    AccountDetails.bse_cd_status = false;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("6")) {

                    bse_cd = "red";
                    bsecd = false;
                    AccountDetails.bse_cd_status = true;
                    AccountDetails.isPreOpen_bse_cd = false;
                    AccountDetails.isPostClosed_bse_cd = false;
                }
            }

            if (AccountDetails.allowedmarket_ncdex) {
                if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("1") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "green";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("-1") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("0") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "yellow";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = true;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("2") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("3") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "blue";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = true;

                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("4") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "pink";
                    AccountDetails.ncdex_com_status = false;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                } else if (response.get(i).getSession().equalsIgnoreCase("1") && response.get(i).getStatus().equalsIgnoreCase("5") && response.get(i).getMarket_id().equalsIgnoreCase("7")) {

                    ncdex_com = "red";
                    AccountDetails.ncdex_com_status = true;
                    AccountDetails.isPreOpen_ncdex_com = false;
                    AccountDetails.isPostClosed_ncdex_com = false;
                }
            }


            if (Integer.parseInt(hm.get(nse_eq)) <= Integer.parseInt(hm.get(bse_eq))) {
                //Log.d("color",hm.get(nse_eq)+"-----"+hm.get(bse_eq));
                actionBar.changeStatus("eq", nse_eq);
            } else if (Integer.parseInt(hm.get(bse_eq)) <= Integer.parseInt(hm.get(nse_eq))) {
                actionBar.changeStatus("eq", bse_eq);
            }

            if (Integer.parseInt(hm.get(nse_fno)) <= Integer.parseInt(hm.get(bse_fno))) {
                actionBar.changeStatus("fno", nse_fno);
            } else if (Integer.parseInt(hm.get(bse_fno)) <= Integer.parseInt(hm.get(nse_fno))) {
                actionBar.changeStatus("fno", bse_fno);
            }

            if (Integer.parseInt(hm.get(nse_cd)) <= Integer.parseInt(hm.get(bse_cd))) {
                actionBar.changeStatus("cd", nse_cd);
            } else if (Integer.parseInt(hm.get(bse_cd)) <= Integer.parseInt(hm.get(nse_cd))) {
                actionBar.changeStatus("cd", bse_cd);
            }

            if (Integer.parseInt(hm.get(mcx_com)) <= Integer.parseInt(hm.get(ncdex_com))) {
                actionBar.changeStatus("com", mcx_com);
            } else if (Integer.parseInt(hm.get(ncdex_com)) <= Integer.parseInt(hm.get(mcx_com))) {
                actionBar.changeStatus("com", ncdex_com);
            }
        }

    }

    private void goToIndianIndicesPage() {
        Bundle bundle = new Bundle();


        if (clickedPos == 1) {
            bundle.putString("IndicesTitleText", actionBar.getNiftyTitleText());


        } else if (clickedPos == 0) {
            bundle.putString("IndicesTitleText", actionBar.getSensexTitleText());

        }

        bundle.putInt("clickedPos", clickedPos);
        bundle.putSerializable("Response", indianIndicesResponse);
        AccountDetails.currentFragment = NAV_TO_INDICES_STOCK_SCREEN;
        actionBar.setSensexClick(true);
        actionBar.setNiftyClick(true);
        IndicesStockFragment frag = new IndicesStockFragment();
        frag.setArguments(bundle);
        setupFragment(frag);

//        navigateTo(NAV_TO_INDICES_STOCK_SCREEN, bundle, true);
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

    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();

            } else {
                openMenuDrawer();

            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void goBackOnce() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() != 0) {

            // Get the last fragment tag from FragmentManager.BackStackEntry
            FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
            // Closing fragment before calling popBackStackImmediate()
            GreekBaseFragment closingFragment = (GreekBaseFragment) fragmentManager.findFragmentByTag(backEntry.getName());
            closingFragment.onFragmentPause();
            // Close the current fragment
            fragmentManager.popBackStackImmediate();

            GreekBaseFragment currentFragment;

            if (fragmentManager.getBackStackEntryCount() != 0) {
                // Get the last fragment in back task after
                // popBackStackImmediate() to resume the current fragment
                backEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
                currentFragment = (GreekBaseFragment) fragmentManager.findFragmentByTag(backEntry.getName());

            } else {
                // Last fragment is not attached to back stack. So get
                // the fragment by id and resume the fragment.
                currentFragment = (GreekBaseFragment) fragmentManager.findFragmentById(R.id.activityFrameLayout);
            }


            getLastTitle(currentFragment.getClass().toString());
        }
    }

    public void generateNoteOnSd(String error) {
        try {
            Random random = new Random();
            int value = random.nextInt();
            String h = String.valueOf(value);
            File root = new File(Environment.getExternalStorageDirectory(), "Logs");
            if (!root.exists()) {
                root.mkdir();
            }
            File filepath = new File(root, h + ".txt");
            FileWriter writer = new FileWriter(filepath);
            writer.append(error);
            writer.flush();
            writer.close();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            //NeedOfDash = true;
            //Log.d("Called", "on Back Press Also" + keyCode);
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }

            if (progressLayout.getVisibility() == View.VISIBLE) {
                progressLayout.setVisibility(View.GONE);
                return false;
            }


            FragmentManager fragmentManager = getSupportFragmentManager();


            if (fragmentManager.getBackStackEntryCount() == 0 && !AccountDetails.getIsMainActivity()) {

                CustomLogoutFragment customLogoutFragment = new CustomLogoutFragment();
                customLogoutFragment.show(getSupportFragmentManager(), "transaction");
                customLogoutFragment.setCancelable(false);

            } else {
                goBackOnce();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void restoreValue(Bundle savedInstanceState) {
        userTypeValue = savedInstanceState.getString("VALUE_KEY");
        if (userTypeValue != null) {
            switch (userTypeValue) {
                case "CUSTOMER":
                    USER_TYPE = USER.CUSTOMER;
                    break;
                case "MFCUSTOMER":
                    USER_TYPE = USER.MFCUSTOMER;
                    break;
                case "IBTCUSTOMER":
                    USER_TYPE = USER.IBTCUSTOMER;
                    break;
                case "OPEN":
                    USER_TYPE = USER.OPENUSER;
                    break;
            }
        }
    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {
        hideProgress();
        showMessageOnScreenManually(msg);
    }

    private void showMessageOnScreenManually(String msg) {
        showMessageOnScreenManually(msg, R.id.showmsgLayout, R.id.errorHeader);
    }

    private void showMessageOnScreenManually(String msg, int layoutId, int headerTextId) {
        try {
            RelativeLayout showmsgOnScreenLayout = findViewById(layoutId);
            showmsgOnScreenLayout.setVisibility(View.VISIBLE);

            GreekTextView showmsgOnScreenTxt = findViewById(headerTextId);
            showmsgOnScreenTxt.setVisibility(View.VISIBLE);
            showmsgOnScreenTxt.setText(Html.fromHtml(msg));
        } catch (Exception e) {
            GreekDialog.alertDialogOnly(this, GreekBaseActivity.GREEK, msg, getString(R.string.GREEK_OK));
            e.printStackTrace();
        }
    }

    public void hideMessageOnScreenManually() {
        RelativeLayout showmsgOnScreenLayout = findViewById(R.id.showmsgLayout);
        if (showmsgOnScreenLayout != null) showmsgOnScreenLayout.setVisibility(View.GONE);
    }

    public void setMenuSelection(int selection) {
        menuAdapter.setMenuSelection(selection);
    }

    public void setChildMenuSelection(int grpsle, int selection) {
        if (menuAdapter != null && menuList != null) {
            menuAdapter.setChildMenuSelection(grpsle, selection);
            menuList.expandGroup(grpsle);
            // menuAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {

    }

    @Override
    public void onError(Exception exception) {

    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {

        GreekDialog.alertDialog(getApplicationContext(), 0, GREEK, "Photo uploaded successfully.",
                "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        if (action == GreekDialog.Action.OK) {

                        }
                    }
                });
    }

    @Override
    public void onCancelled() {
        hideProgress();
    }

    public enum USER {
        CUSTOMER, GUEST, OPENUSER, MFCUSTOMER, IBTCUSTOMER
    }


    public int getMarketId(String token) {
        int tokenNumber = Integer.parseInt(token);
        if (tokenNumber >= 101000000 && tokenNumber <= 101999999) {
            return 1;
        }
        if (tokenNumber >= 102000000 && tokenNumber <= 102999999) {
            return 2;
        }
        if (tokenNumber >= 502000000 && tokenNumber <= 502999999) {
            return 3;
        }
        if (tokenNumber >= 201000000 && tokenNumber <= 201999999) {
            return 4;
        }
        if (tokenNumber >= 202000000 && tokenNumber <= 202999999) {
            return 5;
        }
        if (tokenNumber >= 1302000000 && tokenNumber <= 1302999999) {
            return 6;
        }
        if (tokenNumber >= 303000000 && tokenNumber <= 303999999) {
            return 9;
        }

        return 0;

    }


    public String getErrorMessage(int marketId, String errorcode) {
        String message = "";
        if (marketId == 6 || marketId == 5 || marketId == 4) {
            message = getMarketErrorMessage6(errorcode);
        } else if (marketId == 1) {
            message = getMarketErrorMessage1(errorcode);
        } else if (marketId == 2) {
            message = getMarketErrorMessage2(errorcode);
        } else if (marketId == 3) {
            message = getMarketErrorMessage3(errorcode);
        } else if (marketId == 7) {
            message = getMarketErrorMessage7(errorcode);
        } else if (marketId == 9) {
            message = getMarketErrorMessage9(errorcode);
        } else {
            message = getMarketErrorDefault(errorcode);
        }

        return message;
    }

    @Override
    public void onBackPressed() {

        if (progressLayout.getVisibility() == View.VISIBLE) {

        } else {
            if (AccountDetails.getIsLogoutApp()) {
                AccountDetails.setIsLogoutApp(false);
                super.onBackPressed();
            } else {


            }

        }
        //NeedOfDash=true;
    }

    private String getMarketErrorDefault(String errorcode) {
        String message = "";

        switch (errorcode) {
            case "-1":
                message = "Error Code :   -1   Reason :Exchange Downloading in Progess. Order Entry Not Allowded";
                break;
            case "293":
                message = "Error Code :   293  Reason :Invalid instrument type.";
                break;
            case "509":
                message = "Error Code :   509  Reason :Order does not exist.";
                break;
            case "8049":
                message = "Error Code :  8049  Reason :Initiator is not allowed to cancel auction order.";
                break;
            case "8485":
                message = "Error Code :  8485  Reason :Auction number does not exist";
                break;
            case "16000":
                message = "Error Code : 16000  Reason :The trading system is not available for trading.";
                break;
            case "16003":
                message = "Error Code : 16003  Reason :System error was encountered. Please call the Exchange.";
                break;
            case "16004":
                message = "Error Code : 16004  Reason :The user is already signed on.";
                break;
            case "16005":
                message = "Error Code : 16005  Reason :System error while trying to sign-off. Please call the Exchange.";
                break;
            case "16006":
                message = "Error Code : 16006  Reason :Invalid sign-on. Please try again.";
                break;
            case "16011":
                message = "Error Code : 16011  Reason :This report has already been requested.";
                break;
            case "16012":
                message = "Error Code : 16012  Reason :Invalid contract descriptor";
                break;
            case "16014":
                message = "Error Code : 16014  Reason :This order is not yours.";
                break;
            case "16015":
                message = "Error Code : 16015  Reason :This trade is not yours.";
                break;
            case "16016":
                message = "Error Code : 16016  Reason :Invalid trade number.";
                break;
            case "16019":
                message = "Error Code : 16019  Reason :Stock not found.";
                break;
            case "16035":
                message = "Error Code : 16035  Reason :Security is unavailable for trading at this time. Please try later.";
                break;
            case "16041":
                message = "Error Code : 16041  Reason :Trading member does not exist in the system.";
                break;
            case "16042":
                message = "Error Code : 16042  Reason :Dealer does not exist in the system.";
                break;
            case "16043":
                message = "Error Code : 16043  Reason :This record already exist on the NEAT system.";
                break;
            case "16044":
                message = "Error Code : 16044  Reason :Order has been modified. Please try again.";
                break;
            case "16049":
                message = "Error Code : 16049  Reason :Stock is suspended.";
                break;
            case "16053":
                message = "Error Code : 16053  Reason :Your password has expired, must be changed.";
                break;
            case "16054":
                message = "Error Code : 16054  Reason :Invalid branch for trading member.";
                break;
            case "16056":
                message = "Error Code : 16056  Reason :Program error.";
                break;
            case "16086":
                message = "Error Code : 16086  Reason :Duplicate trade cancel request.";
                break;
            case "16098":
                message = "Error Code : 16098  Reason :Invalid trader ID for buyer.";
                break;
            case "16099":
                message = "Error Code : 16099  Reason :Invalid trader ID for buyer.";
                break;
            case "16104":
                message = "Error Code : 16104  Reason :System could not complete your transaction - Admin notified.";
                break;
            case "16148":
                message = "Error Code : 16148  Reason :Invalid Dealer ID entered.";
                break;
            case "16154":
                message = "Error Code : 16154  Reason :Invalid Trader ID entered.";
                break;
            case "16169":
                message = "Error Code : 16169  Reason :Order priced ATO cannot be entered when a security is open.";
                break;
            case "16198":
                message = "Error Code : 16198  Reason :Duplicate modification or cancellation request for the same trade has been encountered.";
                break;
            case "16227":
                message = "Error Code : 16227  Reason :Only market orders are allowed in postclose.";
                break;
            case "16228":
                message = "Error Code : 16228  Reason :SL, MIT or NT orders are not allowed during Post Close.";
                break;
            case "16229":
                message = "Error Code : 16229  Reason :GTC GTD or GTDays orders are Not Allowed during Post Close.";
                break;
            case "16230":
                message = "Error Code : 16230  Reason :Continuous session orders cannot be modified.";
                break;
            case "16231":
                message = "Error Code : 16231  Reason :Continuous session trades cannot be changed.";
                break;
            case "16706":
                message = "Error Code : 16706  Reason :Cancelled by the System";
                break;
            case "16708":
                message = "Error Code : 16708  Reason :System Error. Orders not completely cancelled by the system. Please request Quick CXL again";
                break;
            case "16233":
                message = "Error Code : 16233  Reason :Proprietary requests cannot be made for participant.";
                break;
            case "16251":
                message = "Error Code : 16251  Reason :Trade modification with different quantities is received.";
                break;
            case "16273":
                message = "Error Code : 16273  Reason :Record does not exist.";
                break;
            case "16278":
                message = "Error Code : 16278  Reason :The markets have not been opened for trading.";
                break;
            case "16279":
                message = "Error Code : 16279  Reason :The contract has not yet been admitted for trading.";
                break;
            case "16280":
                message = "Error Code : 16280  Reason :The Contract has matured.";
                break;
            case "16281":
                message = "Error Code : 16281  Reason :The security has been expelled.";
                break;
            case "16282":
                message = "Error Code : 16282  Reason :The order quantity is greater than the issued capital.";
                break;
            case "16283":
                message = "Error Code : 16283  Reason :The order price is not multiple of the tick size.";
                break;
            case "16284":
                message = "Error Code : 16284  Reason :The order price is out of the day's price range.";
                break;
            case "16285":
                message = "Error Code : 16285  Reason :The broker is not active.";
                break;
            case "16300":
                message = "Error Code : 16300  Reason :System is in a wrong state to make the requested change.";
                break;
            case "16303":
                message = "Error Code : 16303  Reason :The auction is pending.";
                break;
            case "16307":
                message = "Error Code : 16307  Reason :The order has been canceled due to quantity freeze.";
                break;
            case "16308":
                message = "Error Code : 16308  Reason :The order has been canceled due to price freeze.";
                break;
            case "16311":
                message = "Error Code : 16311  Reason :The Solicitor period for the Auction is over.";
                break;
            case "16312":
                message = "Error Code : 16312  Reason :The Competitor period for the Auction is over.";
                break;
            case "16313":
                message = "Error Code : 16313  Reason :The Auction period will cross market close time.";
                break;
            case "16315":
                message = "Error Code : 16315  Reason :The limit price is worse than the trigger price.";
                break;
            case "16316":
                message = "Error Code : 16316  Reason :The trigger price is not a multiple of tick size.";
                break;
            case "16317":
                message = "Error Code : 16317  Reason :AON attribute not allowed.";
                break;
            case "16318":
                message = "Error Code : 16318  Reason :MF attribute not allowed.";
                break;
            case "16319":
                message = "Error Code : 16319  Reason :AON attribute not allowed at Security level.";
                break;
            case "16320":
                message = "Error Code : 16320  Reason :MF attribute not allowed at security level.";
                break;
            case "16321":
                message = "Error Code : 16321  Reason :MF quantity is greater than disclosed quantity";
                break;
            case "16322":
                message = "Error Code : 16322  Reason :MF quantity is not a multiple of regular lot.";
                break;
            case "16323":
                message = "Error Code : 16323  Reason :MF quantity is greater than Original quantity.";
                break;
            case "16324":
                message = "Error Code : 16324  Reason :Disclosed quantity is greater than Original quantity.";
                break;
            case "16325":
                message = "Error Code : 16325  Reason :Disclosed quantity is not a multiple of regular lot.";
                break;
            case "16326":
                message = "Error Code : 16326  Reason :GTD is greater than that specified at the trading system.";
                break;
            case "16327":
                message = "Error Code : 16327  Reason :Odd lot quantity cannot be greater than or equal to regular lot size.";
                break;
            case "16328":
                message = "Error Code : 16328  Reason :Quantity is not a multiple of regular lot.";
                break;
            //	case "16329": message = "Error Code : 16329  Reason :Trading Member not permitted in the market.";break;
            case "16330":
                message = "Error Code : 16330  Reason :Security is suspended.";
                break;
            case "16333":
                message = "Error Code : 16333  Reason :Branch Order Value Limit has been exceeded.";
                break;
            case "16343":
                message = "Error Code : 16343  Reason :The order to be cancelled has changed.";
                break;
            case "16344":
                message = "Error Code : 16344  Reason :The order cannot be cancelled.";
                break;
            case "16345":
                message = "Error Code : 16345  Reason :Initiator order cannot be cancelled.";
                break;
            case "16346":
                message = "Error Code : 16346  Reason :Order cannot be modified.";
                break;
            case "16348":
                message = "Error Code : 16348  Reason :Trading is not allowed in this market.";
                break;
            case "16357":
                message = "Error Code : 16357  Reason :Control has rejected the Negotiated Trade.";
                break;
            case "16363":
                message = "Error Code : 16363  Reason :Status is in the required state.";
                break;
            case "16369":
                message = "Error Code : 16369  Reason :Contract is in preopen.";
                break;
            case "16372":
                message = "Error Code : 16372  Reason :Order entry not allowed for user as it is of inquiry type.";
                break;
            case "16392":
                message = "Error Code : 16392  Reason :Turnover limit not provided. Please contact Exchange.";
                break;
            case "16400":
                message = "Error Code : 16400  Reason :DQ is less than minimum quantity allowed.";
                break;
            case "16404":
                message = "Error Code : 16404  Reason :Order has been cancelled due to freeze admin suspension.";
                break;
            case "16405":
                message = "Error Code : 16405  Reason :BUY - SELL type entered is invalid.";
                break;
            case "16415":
                message = "Error Code : 16415  Reason :Invalid combination of book type and instructions (order_type).";
                break;
            case "16416":
                message = "Error Code : 16416  Reason :Invalid combination of mf/aon/disclosed volume.";
                break;
            case "16440":
                message = "Error Code : 16440  Reason :GTD is greater than Maturity date.";
                break;
            case "16441":
                message = "Error Code : 16441  Reason :DQ orders are not allowed in preopen.";
                break;
            case "16442":
                message = "Error Code : 16442  Reason :ST orders are not allowed in preopen.";
                break;
            case "16445":
                message = "Error Code : 16445  Reason :Stop Loss orders are not allowed.";
                break;
            case "16446":
                message = "Error Code : 16446  Reason :Market If Touched orders are not allowed.";
                break;
            case "16447":
                message = "Error Code : 16447  Reason :Order entry not allowed in Pre-open.";
                break;
            case "16500":
                message = "Error Code : 16500  Reason :Ex/Pl not allowed.";
                break;
            case "16501":
                message = "Error Code : 16501  Reason :Invalid ExPl flag value.";
                break;
            case "16513":
                message = "Error Code : 16513  Reason :Ex/Pl rejection not allowed.";
                break;
            case "16514":
                message = "Error Code : 16514  Reason :Not modifiable.";
                break;
            case "16518":
                message = "Error Code : 16518  Reason :Clearing member, Trading Member link not found.";
                break;
            case "16521":
                message = "Error Code : 16521  Reason :Not a clearing member.";
                break;
            case "16523":
                message = "Error Code : 16523  Reason :User in not a corporate manager.";
                break;
            case "16532":
                message = "Error Code : 16532  Reason :Clearing member Participant link not found.";
                break;
            case "16533":
                message = "Error Code : 16533  Reason :Enter either TM or Participant.";
                break;
            case "16550":
                message = "Error Code : 16550  Reason :Trade cannot be modified /cancelled. It has already been approved by CM.";
                break;
            case "16552":
                message = "Error Code : 16552  Reason :Stock has been suspended.";
                break;
            case "16554":
                message = "Error Code : 16554  Reason :Trading Member not permitted in Futures.";
                break;
            case "16555":
                message = "Error Code : 16555  Reason :Trading Member not permitted in Options.";
                break;
            case "16556":
                message = "Error Code : 16556  Reason :Quantity less than the minimum lot size.";
                break;
            case "16557":
                message = "Error Code : 16557  Reason :Disclose quantity less than the minimum lot size.";
                break;
            case "16558":
                message = "Error Code : 16558  Reason :Minimum fill is less than the minimum lot size.";
                break;
            case "16560":
                message = "Error Code : 16560  Reason :The give up trade has already been rejected.";
                break;
            case "16561":
                message = "Error Code : 16561  Reason :Negotiated Orders not allowed.";
                break;
            case "16562":
                message = "Error Code : 16562  Reason :Negotiated Trade not allowed.";
                break;
            case "16566":
                message = "Error Code : 16566  Reason :User does not belong to Broker or Branch.";
                break;
            case "16570":
                message = "Error Code : 16570  Reason :The market is in post-close.";
                break;
            case "16571":
                message = "Error Code : 16571  Reason :The Closing Session has ended.";
                break;
            case "16572":
                message = "Error Code : 16572  Reason :Closing Session trades have been generated.";
                break;
            case "16573":
                message = "Error Code : 16573  Reason :Message length is invalid.";
                break;
            case "16574":
                message = "Error Code : 16574  Reason :Open - Close type entered is invalid.";
                break;
            case "16576":
                message = "Error Code : 16576  Reason :No. of nnf inquiry requests exceeded.";
                break;
            case "16578":
                message = "Error Code : 16578  Reason :Cover - Uncover type entered is invalid.";
                break;
            case "16579":
                message = "Error Code : 16579  Reason :Giveup requested for wrong CM ID.";
                break;
            case "16580":
                message = "Error Code : 16580  Reason :Order does not belong to the given participant.";
                break;
            case "16581":
                message = "Error Code : 16581  Reason :Invalid trade price.";
                break;
            case "16583":
                message = "Error Code : 16583  Reason :For Pro order participant entry not allowed.";
                break;
            case "16585":
                message = "Error Code : 16585  Reason :Not a valid account number.";
                break;
            case "16586":
                message = "Error Code : 16586  Reason :Participant Order Entry Not Allowed.";
                break;
            case "16589":
                message = "Error Code : 16589  Reason :All continuous session orders are being deleted now.";
                break;
            case "16594":
                message = "Error Code : 16594  Reason :After giveup approve/reject, trade quantity cannot be modified.";
                break;
            case "16596":
                message = "Error Code : 16596  Reason :Trading member cannot put Ex/Pl request for Participant. ";
                break;
            //	case "16597": message = "Error Code : 16597  Reason :Branch limit should be greater than sum of user limits.";break;
            //	case "16598": message = "Error Code : 16598  Reason :Branch limit should be greater than used limit.";break;
            case "16602":
                message = "Error Code : 16602  Reason :Dealer value limit exceeds the set limit.";
                break;
            case "16604":
                message = "Error Code : 16604  Reason :Participant not found.";
                break;
            case "16605":
                message = "Error Code : 16605  Reason :One leg of spread/2L failed.";
                break;
            case "16606":
                message = "Error Code : 16606  Reason :Quantity greater than Freeze quantity.";
                break;
            case "16607":
                message = "Error Code : 16607  Reason :Spread not allowed.";
                break;
            case "16608":
                message = "Error Code : 16608  Reason :Spread allowed only when market is open.";
                break;
            case "16609":
                message = "Error Code : 16609  Reason :Spread allowed only when stock is open.";
                break;
            case "16610":
                message = "Error Code : 16610  Reason :Both legs should have same quantity.";
                break;
            case "16611":
                message = "Error Code : 16611  Reason :Modified order qty freeze not allowed.";
                break;
            case "16612":
                message = "Error Code : 16612  Reason :The trade record has been modified.";
                break;
            case "16615":
                message = "Error Code : 16615  Reason :Order cannot be modified.";
                break;
            case "16616":
                message = "Error Code : 16616  Reason :Order can not be cancelled.";
                break;
            case "16617":
                message = "Error Code : 16617  Reason :Trade can not be manipulated.";
                break;
            case "16619":
                message = "Error Code : 16619  Reason :PCM can not ex_pl for himself.";
                break;
            case "16620":
                message = "Error Code : 16620  Reason :Ex/Pl by clearing member for TM not allowed.";
                break;
            case "16621":
                message = "Error Code : 16621  Reason :Clearing member cannot change the Ex/Pl requests placed by Trading Member.";
                break;
            case "16625":
                message = "Error Code : 16625  Reason :Clearing member is suspended.";
                break;
            case "16626":
                message = "Error Code : 16626  Reason :Expiry date not in ascending order.";
                break;
            case "16627":
                message = "Error Code : 16627  Reason :Invalid contract combination.";
                break;
            case "16628":
                message = "Error Code : 16628  Reason :Branch manager cannot cancel corporate manager's order.";
                break;
            case "16629":
                message = "Error Code : 16629  Reason :Branch manager cannot cancel other branch manager's order.";
                break;
            case "16630":
                message = "Error Code : 16630  Reason :Corporate manager cannot cancel other corporate manager's order.";
                break;
            case "16631":
                message = "Error Code : 16631  Reason :Spread not allowed for different underlying.";
                break;
            case "16632":
                message = "Error Code : 16632  Reason :Cli A/c number cannot be modified as trading member ID.";
                break;
            case "16636":
                message = "Error Code : 16636  Reason :Futures buy branch Order Value Limit has been exceeded.";
                break;
            case "16637":
                message = "Error Code : 16637  Reason :Futures sell branch Order Value Limit has been exceeded.";
                break;
            case "16638":
                message = "Error Code : 16638  Reason :Options buy branch Order Value Limit has been exceeded.";
                break;
            case "16639":
                message = "Error Code : 16639  Reason :Options sell branch Order Value Limit has been exceeded.";
                break;
            case "16640":
                message = "Error Code : 16640  Reason :Futures buy used limit execeeded the user limit.";
                break;
            case "16641":
                message = "Error Code : 16641  Reason :Futures sell used limit execeeded the user limit.";
                break;
            case "16642":
                message = "Error Code : 16642  Reason :Options buy used limit execeeded the user limit.";
                break;
            case "16643":
                message = "Error Code : 16643  Reason :Options sell used limit execeeded the user limit.";
                break;
            case "16645":
                message = "Error Code : 16645  Reason :Cannot approve. Bhav Copy generated.";
                break;
            case "16646":
                message = "Error Code : 16646  Reason :Cannot modify.";
                break;
            case "16541":
                message = "Error Code : 16541  Reason :Participant is invalid.";
                break;
            case "16577":
                message = "Error Code : 16577  Reason :Both participant and volume changed.";
                break;
            case "16001":
                message = "Error Code : 16001  Reason :Header user ID is not equal to user ID in the order packet.";
                break;
            case "16007":
                message = "Error Code : 16007  Reason :Signing onto the trading system is restricted.  Please try later on.";
                break;
            case "16100":
                message = "Error Code : 16100  Reason :Your system version has not been updated.";
                break;
            case "16443":
                message = "Error Code : 16443  Reason :Order value exceeds the order limit value.";
                break;
            case "16656":
                message = "Error Code : 16656  Reason :No address in the database.";
                break;
            case "16667":
                message = "Error Code : 16667  Reason :GTC GTD Orders not allowed.";
                break;
            case "16662":
                message = "Error Code : 16662  Reason :Contract is opening. Please wait for the contract to open.";
                break;
            case "16666":
                message = "Error Code : 16666  Reason :Invalid NNF field.";
                break;
            //case "16388": message = "Error Code : 16388  Reason :Invalid NNF field From Manager.";break;
            ///////EXTRA FIELDS IN NCDEX////
            case "8661":
                message = "Error Code :8661  Reason: Delivery start number must less than or equal to Delivery end number";
                break;
            case "16172":
                message = "Error Code :16172 Reason: Prices freeze are not allowed";
                break;
            case "16159":
                message = "Error Code :16159 Reason: No market orders are allowed in PostClose";
                break;
            case "16668":
                message = "Error Code :16668 Reason: Orders not allowed in PostClose";
                break;
            case "16375":
                message = "Error Code :16375 Reason: Request rejected as end of day processing has started";
                break;
            case "16387":
                message = "Error Code :16387 Reason: Contract not allowed to trader in.";
                break;
            case "16437":
                message = "Error Code :16437 Reason: Warehouse deleted";
                break;
            case "16534":
                message = "Error Code :16534 Reason: Participant/CM id link not found";
                break;
            case "16542":
                message = "Error Code :16542 Reason: Invalid participant";
                break;
            case "16664":
                message = "Error Code :16664 Reason: Quantity is not a multiple of delivery lot";
                break;
            case "16672":
                message = "Error Code :16672 Reason: Both TM id and client code required";
                break;
            case "16692":
                message = "Error Code :16692 Reason: Nnf field value is <1 or >999999999999999";
                break;
            case "16684":
                message = "Error Code :16684 Reason: Password is one of last 5 passwords";
                break;
            case "16683":
                message = "Error Code :16683 Reason: User password is locked";
                break;
            case "16685":
                message = "Error Code :16685 Reason: User does not linked to you.";
                break;
            case "16145":
                message = "Error Code :16145 Reason: Security is not eligible to trade in Preopen";
                break;
            case "16115":
                message = "Error Code :16115 Reason: Order Modification/ Cancellation rejected by the system";
                break;
            case "16592":
                message = "Error Code :16592 Reason: Order Entry is not allowed";
                break;
            case "16597":
                message = "Error Code :16597 Reason: Order entry / Modification rejected by the Exchange";
                break;
            case "16598":
                message = "Error Code :16598 Reason: Order Entry is not allowed";
                break;
            case "16052":
                message = "Error Code :16052 Reason: When Preopen trade cancel request is rejected";
                break;
            case "16418":
                message = "Error Code :16418 Reason: Order entered has invalid data";
                break;
            case "16388":
                message = "Error Code :16388 Reason: When Preopen unmatched orders are cancelled by the system after preopen session ends";
                break;
            case "16601":
                message = "Error Code :16601 Reason: Request Rejected by the exchange";
                break;
            case "16329":
                message = "Error Code :16329 Reason: Broker is not eligible for trading in given market";
                break;
            case "21019":
                message = "Error Code :21019 Reason: Invalid Instrument Identifier";
                break;
            case "21025":
                message = "Error Code :21025 Reason: Desc Invalid Order Price !";
                break;
            case "507":
                message = "Error Code :507   Reason: Invalid Disclosed Qty";
                break;
            case "23333":
                message = "Error Code :23333 Reason: CTCL unique number not registered with Exchange";
                break;
            case "521":
                message = "Error Code :521   Reason: Order price below the low price not allowed";
                break;
            case "21010":
                message = "Error Code :21010 Reason: Invalid Order Time !";
                break;
            case "520":
                message = "Error Code :520   Reason: Order price beyond the high price not allowed";
                break;
            case "526":
                message = "Error Code :526   Reason: Single transaction quantity for security Product cannot exceed maximum limit.";
                break;
            case "21022":
                message = "Error Code :21022 Reason: Invalid Disclosed Quantity Remaining !";
                break;
            case "21023":
                message = "Error Code :21023 Reason: Invalid Pending Order Quantity !";
                break;
            case "505":
                message = "Error Code :505   Reason: The market is currently not open for trading.";
                break;
            case "22222":
                message = "Error Code :22222 Reason: Invalid Participant Id!";
                break;
            case "16603":
                message = "Disclosed Quantity order not allowed in closing session";
                break;
            //case "16388": message = "Error Code : 16388  Reason :Invalid NNF field";break;
            case "-5":
                message = "Error Code : -5  Reason :Invalid NNF field";
                break;// PRAVIN
            case "16414":
                message = "Error Code : 16414  Pro Cli field is invalid (something other than Pro – 2 or Cli – 1)";
                break;
            case "16419":
                message = "Error Code : 16419  This error code will be message =ed for invalid data in the order packet[Cross Check Limits(NSEFO)].";
                break; //prajakta
            case "16529":
                message = "Error Code : 16529  Not a valid derivative contract.";
                break;
            case "16686":
                message = "Error Code : 16686  This error code will be message =ed if Close out order rejected by the system.";
                break;
            case "16688":
                message = "Error Code : 16688  This error code will be message =ed if the close out order is not allowed in the system.";
                break;
            case "16690":
                message = "Error Code : 16690  This error code will be message =ed when a Trade MOD request is placed by a broker in Close-out.";
                break;
            case "16713":
                message = "Error Code : 16713  Spread order price difference is out of range.";
                break;
            case "16807":
                message = "Error Code : 16807  The account is debarred from trading.";
                break;
            case "17036":
                message = "Error Code : 17036  MBA inquiry is not allowed for this contract";
                break;
            case "17037":
                message = "Error Code : 17037  insufficient record for MBA inquiry";
                break;
            case "17038":
                message = "Error Code : 17038  Order is outstanding.";
                break;
            case "17039":
                message = "Error Code : 17039  Pro Cli Modification not allowed for the Order.";
                break;
            case "17045":
                message = "Error Code : 17045  Order Quantity Exceeds Quantity Value Limit for User.";
                break;
            case "17046":
                message = "Error Code : 17046  Trade Modification Not Allowed for User Type.";
                break;
            case "17047":
                message = "Error Code : 17047  Trade Modification not Allowed for Broker.";
                break;
            case "17048":
                message = "Error Code : 17048  Trade Modification Not Allowed for User.";
                break;
            case "17070":
                message = "Error Code : 17070  The Price is out of the current execution range.";
                break;
            case "16793":
                message = "Error Code : 16793  Order Entered has invalid data.";
                break;
            case "16794":
                message = "Error Code : 16794  Order Entered has invalid data.";
                break;
            case "16795":
                message = "Error Code : 16795  Order cancelled mdue to voluntary close out.";
                break;
            case "16796":
                message = "Error Code : 16796  Order cancelled due to OI violation.";
                break;
            case "10001":
                message = "Error Code : 10001  The Price is out of the current execution range.";
                break;

            default: {
                message = "Unknown Error Code ";
                break;
            }
        }
        return message;
    }

    private String getMarketErrorMessage7(String errorcode) {
        String message = "";

        switch (errorcode) {

            case "293":
                message = "Error Code : 293    Reason :   Invalid instrument type";
                break;
            case "509":
                message = "Error Code : 509    Reason :   Order does not exit";
                break;
            case "8049":
                message = "Error Code : 8049   Reason :   Initiator is not allowed to cancel auction order.";
                break;
            case "8485":
                message = "Error Code : 8485   Reason :   Auction number does not exit";
                break;
            case "8661":
                message = "Error Code : 8661   Reason :   Delivery start number must less than or equal to Delivery end number";
                break;
            case "16000":
                message = "Error Code : 16000  Reason :  The trading system is not available for trading.";
                break;
            case "16003":
                message = "Error Code : 16003  Reason :  System error was encountered. Please call the Exchange.";
                break;
            case "16004":
                message = "Error Code : 16004  Reason :  The user is already signed on.";
                break;
            case "16005":
                message = "Error Code : 16005  Reason :  System error while trying to sign-off. Please call the Exchange.";
                break;
            case "16006":
                message = "Error Code : 16006  Reason :  Invalid signon Please try again.";
                break;
            case "16011":
                message = "Error Code : 16011  Reason :  This report has already been requested.";
                break;
            case "16012":
                message = "Error Code : 16012  Reason :  Invalid contract descriptor";
                break;
            case "16014":
                message = "Error Code : 16014  Reason :  This order is not yours.";
                break;
            case "16015":
                message = "Error Code : 16015  Reason :  This trade is not yours.";
                break;
            case "16016":
                message = "Error Code : 16016  Reason :  Invalid trade number.";
                break;
            case "16019":
                message = "Error Code : 16019  Reason :  Stock not found.";
                break;
            case "16035":
                message = "Error Code : 16035  Reason :  Contract is unavailable for trading at this time. Please try later";
                break;
            case "16041":
                message = "Error Code : 16041  Reason :  Trading member does not exit in the system";
                break;
            case "16042":
                message = "Error Code : 16042  Reason :  Dealer does not exist in the system.";
                break;
            case "16043":
                message = "Error Code : 16043  Reason :  This record already exits on the NEAT system";
                break;
            case "16044":
                message = "Error Code : 16044  Reason :  Order has been modified. Please try again.";
                break;
            case "16049":
                message = "Error Code : 16049  Reason :  Stock is SUSPENDED ";
                break;
            case "16053":
                message = "Error Code : 16053  Reason :  Your password has expired, must be changed.";
                break;
            case "16054":
                message = "Error Code : 16054  Reason :  Invalid branch for trading member.";
                break;
            case "16056":
                message = "Error Code : 16056  Reason :  Program error.";
                break;
            case "16086":
                message = "Error Code : 16086  Reason :  Duplicate trade cancel request.";
                break;
            case "16098":
                message = "Error Code : 16098  Reason :  Invalid trader id for buyer.";
                break;
            case "16099":
                message = "Error Code : 16099  Reason :  Invalid trader id for buyer.";
                break;
            case "16104":
                message = "Error Code : 16104  Reason :  System could not complete your transaction - ADMIN notified.";
                break;
            case "16148":
                message = "Error Code : 16148  Reason :  Invalid Dealer Id entered";
                break;
            case "16154":
                message = "Error Code : 16154  Reason :  Invalid Trader id entered.";
                break;
            case "16169":
                message = "Error Code : 16169  Reason :  Order priced ATO cannot be entered when a security is open.";
                break;
            case "16172":
                message = "Error Code : 16172  Reason :  Prices freeze are not allowed";
                break;
            case "16179":
                message = "Error Code : 16179  Reason :  Order price is out of range";
                break;
            case "16198":
                message = "Error Code : 16198  Reason :  Duplicate modification or cancellation request for the same trade has been encountered.";
                break;
            case "16159":
                message = "Error Code : 16159  Reason :  No market orders are allowed in PostClose";
                break;
            case "16668":
                message = "Error Code : 16668  Reason :  Orders not allowed in PostClose";
                break;
            case "16228":
                message = "Error Code : 16228  Reason :  SL, MIT or NT Orders are Not Allowed during Post Close.";
                break;
            case "16229":
                message = "Error Code : 16229  Reason :  GTC GTD or GTDays Orders are Not Allowed during Post Close.";
                break;
            case "16230":
                message = "Error Code : 16230  Reason :  Continuous session orders cannot be modified";
                break;
            case "16231":
                message = "Error Code : 16231  Reason :  Continuous session trades cannot be changed.";
                break;
            case "16233":
                message = "Error Code : 16233  Reason :  Proprietary requests cannot be made for participant";
                break;
            case "16251":
                message = "Error Code : 16251  Reason :  Trade modification with different quantities is received.";
                break;
            case "16273":
                message = "Error Code : 16273  Reason :  Record does not exit";
                break;
            case "16278":
                message = "Error Code : 16278  Reason :  The markets have not been opened for trading.";
                break;
            case "16279":
                message = "Error Code : 16279  Reason :  The contract has not yet been admitted for trading.";
                break;
            case "16280":
                message = "Error Code : 16280  Reason :  The Contract has matured";
                break;
            case "16281":
                message = "Error Code : 16281  Reason :  The security has been expelled.";
                break;
            case "16282":
                message = "Error Code : 16282  Reason :  The order quantity is greater than the issued capital.";
                break;
            case "16283":
                message = "Error Code : 16283  Reason :  The order price is not multiple of the tick size.";
                break;
            case "16284":
                message = "Error Code : 16284  Reason :  The order price is out of the day’s price range.";
                break;
            case "16285":
                message = "Error Code : 16285  Reason :  The broker is not active.";
                break;
            case "16300":
                message = "Error Code : 16300  Reason :  System is in a wrong state to make the requested change.";
                break;
            case "16303":
                message = "Error Code : 16303  Reason :  The auction is pending.";
                break;
            case "16307":
                message = "Error Code : 16307  Reason :  The order has been canceled due to quantity freeze.";
                break;
            case "16308":
                message = "Error Code : 16308  Reason :  The order has been canceled due to price freeze.";
                break;
            case "16311":
                message = "Error Code : 16311  Reason :  The Solicitor period for the Auction is over.";
                break;
            case "16312":
                message = "Error Code : 16312  Reason :  The Competitor period for the Auction is over.";
                break;
            case "16313":
                message = "Error Code : 16313  Reason :  The Auction period will cross market close time";
                break;
            case "16315":
                message = "Error Code : 16315  Reason :  The limit price Is worse than the trigger price.";
                break;
            case "16316":
                message = "Error Code : 16316  Reason :  The trigger price is not a multiple of tick size.";
                break;
            case "16317":
                message = "Error Code : 16317  Reason :  AON attribute not allowed.";
                break;
            case "16318":
                message = "Error Code : 16318  Reason :  MF attribute not allowed.";
                break;
            case "16319":
                message = "Error Code : 16319  Reason :  AON attribute not allowed at Security level.";
                break;
            case "16320":
                message = "Error Code : 16320  Reason :  MF attribute not allowed at security level.";
                break;
            case "16321":
                message = "Error Code : 16321  Reason :  MF quantity is greater than disclosed quantity";
                break;
            case "16322":
                message = "Error Code : 16322  Reason :  MF quantity is not a multiple of regular lot";
                break;
            case "16323":
                message = "Error Code : 16323  Reason :  MF quantity is greater than Original quantity.";
                break;
            case "16324":
                message = "Error Code : 16324  Reason :  Disclosed quantity is greater than Original quantity.";
                break;
            case "16325":
                message = "Error Code : 16325  Reason :  Disclosed quantity is not a multiple of regular lot.";
                break;
            case "16326":
                message = "Error Code : 16326  Reason :  GTD is greater than that specified at System.";
                break;
            case "16327":
                message = "Error Code : 16327  Reason :  Odd lot quantity cannot be greater than or equal to regular lot size";
                break;
            case "16328":
                message = "Error Code : 16328  Reason :  Quantity is not a multiple of regular lot.";
                break;
            case "16329":
                message = "Error Code : 16329  Reason :  Trading Member not permitted in the market.";
                break;
            case "16330":
                message = "Error Code : 16330  Reason :  Security is suspended.";
                break;
            case "16333":
                message = "Error Code : 16333  Reason :  Branch Order Value Limit has been exceeded.";
                break;
            case "16343":
                message = "Error Code : 16343  Reason :  The order to be cancelled has changed.";
                break;
            case "16344":
                message = "Error Code : 16344  Reason :  The order cannot be cancelled.";
                break;
            case "16345":
                message = "Error Code : 16345  Reason :  Initiator order cannot be cancelled.";
                break;
            case "16346":
                message = "Error Code : 16346  Reason :  Order cannot be modified.";
                break;
            case "16347":
                message = "Error Code : 16347  Reason :  Request rejected as end of day processing has ended";
                break;
            case "16348":
                message = "Error Code : 16348  Reason :  Trading is not allowed in this market.";
                break;
            case "16357":
                message = "Error Code : 16357  Reason :  Control has rejected the Negotiated Trade.";
                break;
            case "16363":
                message = "Error Code : 16363  Reason :  Status is in the required state.";
                break;
            case "16369":
                message = "Error Code : 16369  Reason :  Contract is in preopen.";
                break;
            case "16372":
                message = "Error Code : 16372  Reason :  Order entry not allowed for user as it is ofinquiry type.";
                break;
            case "16375":
                message = "Error Code : 16375  Reason :  Request rejected as end of day processing has started";
                break;
            case "16387":
                message = "Error Code : 16387  Reason :  Contract not allowed to trader in.";
                break;
            case "16392":
                message = "Error Code : 16392  Reason :  Turnover limit not provided. Please contact Exchange.";
                break;
            case "16400":
                message = "Error Code : 16400  Reason :  DQ is less than minimum allowed.";
                break;
            case "16404":
                message = "Error Code : 16404  Reason :  Order has been cancelled due to freeze admin suspension.";
                break;
            case "16405":
                message = "Error Code : 16405  Reason :  BUY – SELL type entered is invalid";
                break;
            case "16415":
                message = "Error Code : 16415  Reason :  invalid combination of book type and instructions(order_type).";
                break;
            case "16416":
                message = "Error Code : 16416  Reason :  invalid combination of mf/aon/disclosed volume.";
                break;
            case "16437":
                message = "Error Code : 16437  Reason :  Warehouse deleted";
                break;
            case "16440":
                message = "Error Code : 16440  Reason :  GTD > Maturity Date";
                break;
            case "16441":
                message = "Error Code : 16441  Reason :  DQ orders are not allowed in preopen.";
                break;
            case "16442":
                message = "Error Code : 16442  Reason :  ST orders are not allowed in preopen";
                break;
            case "16443":
                message = "Error Code : 16443  Reason :  Order limit exceeds the order value limit";
                break;
            case "16445":
                message = "Error Code : 16445  Reason :  Stoploss orders are not allowed”";
                break;
            case "16446":
                message = "Error Code : 16446  Reason :  Market If Touched orders are not allowed";
                break;
            case "16447":
                message = "Error Code : 16447  Reason :  Order entry not allowed in Pre-Open";
                break;
            case "16500":
                message = "Error Code : 16500  Reason :  Ex/Pl not allowed";
                break;
            case "16501":
                message = "Error Code : 16501  Reason :  Invalid ExPl flag value";
                break;
            case "16513":
                message = "Error Code : 16513  Reason :  Ex/Pl rejection not allowed";
                break;
            case "16514":
                message = "Error Code : 16514  Reason :  Not modifiable.";
                break;
            case "16518":
                message = "Error Code : 16518  Reason :  Clearing member , Trading Member link not found";
                break;
            case "16521":
                message = "Error Code : 16521  Reason :  Not a clearing member";
                break;
            case "16523":
                message = "Error Code : 16523  Reason :  User in not a corporate manager";
                break;
            case "16531":
                message = "Error Code : 16531  Reason :  Enter either TM or participant";
                break;
            case "16532":
                message = "Error Code : 16532  Reason :  Clearing member Participant link not found.";
                break;
            case "16533":
                message = "Error Code : 16533  Reason :  enter either TM or Participant.";
                break;
            case "16534":
                message = "Error Code : 16534  Reason :  Participant/CM id link not found";
                break;
            case "16542":
                message = "Error Code : 16542  Reason :  Invalid participant";
                break;
            case "16550":
                message = "Error Code : 16550  Reason :  Trade cannot be modified/cancelled - has already been approved by CM.";
                break;
            case "16552":
                message = "Error Code : 16552  Reason :  Stock has been suspended.";
                break;
            case "16554":
                message = "Error Code : 16554  Reason :  Trading Member not permitted in Futures.";
                break;
            case "16555":
                message = "Error Code : 16555  Reason :  Trading Member not permitted in Options.";
                break;
            case "16556":
                message = "Error Code : 16556  Reason :  Quantity less than the minimum lot size.";
                break;
            case "16557":
                message = "Error Code : 16557  Reason :  Disclose quantity less than the minimum lot size.";
                break;
            case "16558":
                message = "Error Code : 16558  Reason :  Minimum fill less than the minimum lot size.";
                break;
            case "16560":
                message = "Error Code : 16560  Reason :  The giveup trade has already been rejected.";
                break;
            case "16561":
                message = "Error Code : 16561  Reason :  Negotiated Orders not allowed.";
                break;
            case "16562":
                message = "Error Code : 16562  Reason :  Negotiated Trade not allowed.";
                break;
            case "16566":
                message = "Error Code : 16566  Reason :  User does not belong to Broker or Branch.";
                break;
            case "16570":
                message = "Error Code : 16570  Reason :  The market is in postclose.";
                break;
            case "16571":
                message = "Error Code : 16571  Reason :  The Closing Session has ended.";
                break;
            case "16572":
                message = "Error Code : 16572  Reason :  Closing Session trades have been generated.";
                break;
            case "16573":
                message = "Error Code : 16573  Reason :  Message Length Is Invalid.";
                break;
            case "16574":
                message = "Error Code : 16574  Reason :  OPEN - CLOSE type entered is invalid.";
                break;
            case "16576":
                message = "Error Code : 16576  Reason :  No. of nnf inquiry requests exceeded.";
                break;
            case "16578":
                message = "Error Code : 16578  Reason :  COVER - UNCOVER type entered is invalid.";
                break;
            case "16579":
                message = "Error Code : 16579  Reason :  Giveup requested for wrong CM Id.";
                break;
            case "16580":
                message = "Error Code : 16580  Reason :  Order does not belong to the given participant.";
                break;
            case "16581":
                message = "Error Code : 16581  Reason :  Invalid trade price.";
                break;
            case "16583":
                message = "Error Code : 16583  Reason :  For Pro Order Participant Entry Not Allowed.";
                break;
            case "16585":
                message = "Error Code : 16585  Reason :  Not a valid account number.";
                break;
            case "16586":
                message = "Error Code : 16586  Reason :  Participant Order Entry Not Allowed.";
                break;
            case "16589":
                message = "Error Code : 16589  Reason :  All continuous session orders are being deleted now.";
                break;
            case "16594":
                message = "Error Code : 16594  Reason :  Etmid_cant_exercise_for_ptcpnt";
                break;
            case "16596":
                message = "Error Code : 16596  Reason :  Trading member cannot put Ex/Pl request for Participant.";
                break;
            case "16597":
                message = "Error Code : 16597  Reason :  Branch limit should be greater than sum of user limits";
                break;
            case "16598":
                message = "Error Code : 16598  Reason :  Branch limit should be greater than used limit";
                break;
            case "16602":
                message = "Error Code : 16602  Reason :  dealer_value_limit exceeds set limit";
                break;
            case "16604":
                message = "Error Code : 16604  Reason :  Participant not found";
                break;
            case "16605":
                message = "Error Code : 16605  Reason :  One leg of spread/2L failed.";
                break;
            case "16606":
                message = "Error Code : 16606  Reason :  Quantity greater than Freeze quantity.";
                break;
            case "16607":
                message = "Error Code : 16607  Reason :  Spread Not Allowed.";
                break;
            case "16608":
                message = "Error Code : 16608  Reason :  Spread Allowed Only When Mkt Is Open.";
                break;
            case "16609":
                message = "Error Code : 16609  Reason :  Spread Allowed Only When Stock Is Open.";
                break;
            case "16610":
                message = "Error Code : 16610  Reason :  both legs should have same quantity.";
                break;
            case "16611":
                message = "Error Code : 16611  Reason :  Modified order qty freeze not allowed.";
                break;
            case "16612":
                message = "Error Code : 16612  Reason :  The trade record has been modified";
                break;
            case "16615":
                message = "Error Code : 16615  Reason :  Order cannot be modified";
                break;
            case "16616":
                message = "Error Code : 16616  Reason :  Order can not be cancelled";
                break;
            case "16617":
                message = "Error Code : 16617  Reason :  Trade can not be manipulated";
                break;
            case "16619":
                message = "Error Code : 16619  Reason :  Pcm can not ex_pl for himself";
                break;
            case "16620":
                message = "Error Code : 16620  Reason :  Ex/Pl by clearing member for Tm not allowed.";
                break;
            case "16621":
                message = "Error Code : 16621  Reason :  Clearing member cannot change the Ex/Pl requests placed by Trading Member";
                break;
            case "16625":
                message = "Error Code : 16625  Reason :  Clearing member is suspended";
                break;
            case "16626":
                message = "Error Code : 16626  Reason :  expiry date not in ascending order";
                break;
            case "16627":
                message = "Error Code : 16627  Reason :  Invalid contract combination";
                break;
            case "16628":
                message = "Error Code : 16628  Reason :  Ebm_cannot_cancel_cm_orders ";
                break;
            case "16629":
                message = "Error Code : 16629  Reason :  Ebm_cannot_cancel_bm_orders ";
                break;
            case "16630":
                message = "Error Code : 16630  Reason :  Ecm_cannot_cancel_cm_orders ";
                break;
            case "16631":
                message = "Error Code : 16631  Reason :  spread not allowed for different underlying ";
                break;
            case "16632":
                message = "Error Code : 16632  Reason :  Cli A/c number cannot be modified as Trading member id.";
                break;
            case "16636":
                message = "Error Code : 16636  Reason :  Futures buy branch Order Value Limit has been exceeded";
                break;
            case "16637":
                message = "Error Code : 16637  Reason :  Futures sell branch Order Value Limit has been exceeded";
                break;
            case "16638":
                message = "Error Code : 16638  Reason :  Options buy branch Order Value Limit has been exceeded";
                break;
            case "16639":
                message = "Error Code : 16639  Reason :  Options sell branch Order Value Limit has been exceeded";
                break;
            case "16640":
                message = "Error Code : 16640  Reason :  Futures buy used limit execeeded the user limit";
                break;
            case "16641":
                message = "Error Code : 16641  Reason :  Futures sell used limit exceeded the user limit";
                break;
            case "16642":
                message = "Error Code : 16642  Reason :  Options buy used limit exceeded the user limit";
                break;
            case "16643":
                message = "Error Code : 16643  Reason :  Options sell used limit exceeded the user limit";
                break;
            case "16645":
                message = "Error Code : 16645  Reason :  Cannot approve.Bhav Copy generated.";
                break;
            case "16646":
                message = "Error Code : 16646  Reason :  Cannot modify";
                break;
            case "16664":
                message = "Error Code : 16664  Reason :  Quantity is not a multiple of delivery lot";
                break;
            case "16666":
                message = "Error Code : 16666  Reason :  Not a valid date for Delivery Request";
                break;
            case "16672":
                message = "Error Code : 16672  Reason :  Both TM id and client code required";
                break;
            case "16541":
                message = "Error Code : 16541  Reason :  Participant is invalid.";
                break;
            case "16577":
                message = "Error Code : 16577  Reason :  Both participant and volume changed";
                break;
            case "16692":
                message = "Error Code : 16692  Reason :  Nnf field value is <1 or > 999999999999999";
                break;
            case "16684":
                message = "Error Code : 16684  Reason :  Password is one of last 5 passwords.";
                break;
            case "16683":
                message = "Error Code : 16683  Reason :  User password is locked.";
                break;
            case "16685":
                message = "Error Code : 16685  Reason :  User does not linked to you.";
                break;
            case "16695":
                message = "Error Code : 16695  Reason :  Only Corporate Manager can change trade modification rights";
                break;
            case "16697":
                message = "Error Code : 16697  Reason :  CM/Admin/Inquiry user not allowed";
                break;
            case "16698":
                message = "Error Code : 16698  Reason :  Trade modification restricted by Corporate Manager";
                break;
            case "16699":
                message = "Error Code : 16699  Reason :  Trigger Price is out of the days price range";
                break;
            case "16704":
                message = "Error Code : 16704  Reason :  Counter Party delivery request not allowed for this symbol";
                break;
            case "16706":
                message = "Error Code : 16706  Reason :  Essential Client Details is not submitted to exchange.";
                break;
            case "16707":
                message = "Error Code : 16707  Reason :  Order not allowed on this contract";
                break;
            case "16708":
                message = "Error Code : 16708  Reason :  Order not allowed on specified order types";
                break;
            case "16709":
                message = "Error Code : 16709  Reason :  Order validity expired and hence cancelled by system";
                break;
            case "16710":
                message = "Error Code : 16710  Reason :  Fresh position not allowed on this contract";
                break;
            case "16670":
                message = "Error Code : 16670  Reason :  Spread IOC not allowed";
                break;
            case "16696":
                message = "Error Code : 16696  Reason :  Spread Day Order Not Allowed";
                break;
            case "16197":
                message = "Error Code : 16197  Reason :  Square-off order not allowed";
                break;
            case "16438":
                message = "Error Code : 16438  Reason :  Trade modification is not allowed";
                break;
            case "16718":
                message = "Error Code : 16718  Reason :  Position or Account no or Participant field in trade MOD are not active";
                break;
            case "16719":
                message = "Error Code : 16719  Reason :  Trading member is deactivated/Suspended";
                break;
            case "16720":
                message = "Error Code : 16720  Reason :  Cover/Uncover or Position or Participant or account no fields in trade MOD are not active";
                break;
            case "16721":
                message = "Error Code : 16721  Reason :  Participant is Suspended";
                break;
            case "16722":
                message = "Error Code : 16722  Reason :  Cli acct No. cannot be modified to broker_id";
                break;

            default: {
                message = "Unknown Error Code ";
                break;
            }
        }
        return message;
    }


    private String getMarketErrorMessage9(String errorcode) {
        String message = "";

        switch (errorcode) {

            case "504":
                message = "System currently not available for trading";
                break;
            case "505":
                message = "The market is currently not open for trading";
                break;
            case "506":
                message = "Invalid Buy Or Sell Parameter";
                break;
            case "507":
                message = "Invalid Disclosed Qty";
                break;
            case "508":
                message = "Invalid Disclosed Qty Remaining";
                break;
            case "509":
                message = "Invalid Total Qty Remaining";
            case "510":
                message = "Invalid Total Qty";
                break;
            case "511":
                message = "Invalid Qty Traded Today";
                break;
            case "512":
                message = "Invalid Price";
                break;
            case "513":
                message = "Invalid Trigger Price";
                break;
            case "514":
                message = "Invalid Good Till Days";
                break;
            case "515":
                message = "Invalid good till day validity specified";
                break;
            case "516":
                message = " Invalid Min Fill Qty";
                break;
            case "517":
                message = " Invalid PRO or CLI";
                break;
            case "518":
                message = " Invalid Order Type";
                break;
            case "519":
                message = " Price band information not available";
                break;
            case "520":
                message = " Order price beyond the high price not allowed";
                break;
            case "521":
                message = " Order price below the low price not allowed";
                break;
            case "522":
                message = " Security not found in the security master";
                break;
            case "523":
                message = " Security is not active";
                break;
            case "524":
                message = " Security is not permitted for trading";
                break;
            case "525":
                message = " Security is already matured";
                break;
            case "526":
                message = " Single transaction quantity for security cannot exceed maximum limit";
                break;
            case "527":
                message = " Single transaction value for security cannot exceed maximum limit";
                break;
            case "528":
                message = " Risk management parameters not available for the Product";
                break;
            case "529":
                message = " Order does not exists";
                break;
            case "530":
                message = " Order has changed";
                break;
            case "531":
                message = " Order is not currently in pending passive state at the exchange";
                break;
            case "532":
                message = " Order is locked for previous operation";
                break;
            case "533":
                message = " Category not defined for the user";
                break;
            case "534":
                message = " order not allowed, privilege not given for Any user, request rejected";
                break;
            case "535":
                message = " U/L Asset information not available";
                break;
            case "536":
                message = " U/L Asset not permitted for trading";
                break;
            case "537":
                message = " Not allowed for particular category";
                break;
            case "538":
                message = " Invalid Message Timestamp";
                break;
            case "539":
                message = " Invalid Message Timestamp1";
                break;
            case "540":
                message = " Invalid Alpha Character";
                break;
            case "541":
                message = " Invalid Order Message Code";
                break;
            case "542":
                message = " Invalid field value for ErrorCode";
                break;
            case "543":
                message = " Invalid message length";
                break;
            case "544":
                message = " Invalid Market Segment";
                break;
            case "545":
                message = " Invalid Participant Type";
                break;
            case "546":
                message = " Invalid field value for Reserved1";
                break;
            case "547":
                message = " Invalid field value for Competitor Period";
                break;
            case "548":
                message = " Invalid field value for Solicitor Period";
                break;
            case "549":
                message = " Invalid field value for ModCancelBy";
                break;
            case "550":
                message = " Invalid field value for Reserved2";
                break;
            case "551":
                message = " Invalid field value for ReasonCode";
                break;
            case "552":
                message = " Invalid field value for Reserved3";
                break;
            case "553":
                message = " Invalid token , value should be greater than 0";
                break;
            case "554":
                message = " Invalid Instrument Name";
                break;
            case "555":
                message = " Invalid Series";
                break;
            case "556":
                message = " Invalid Expiry Date, value should be greater than 0";
                break;
            case "557":
                message = " Invalid strike price, value should be 0 for FUTCOM";
                break;
            case "558":
                message = " Invalid option type , value should be XX for FUTCOM";
                break;
            case "559":
                message = " Invalid CALevel";
                break;
            case "560":
                message = " Invalid Session number";
                break;
            case "561":
                message = " Invalid CPBroker Code";
                break;
            case "562":
                message = " Invalid Security Suspend Indicator";
                break;
            case "563":
                message = " Invalid Reserved4";
                break;
            case "564":
                message = " Invalid order number, value should be 0";
                break;
            case "565":
                message = " Invalid account code length";
                break;
            case "566":
                message = " Invalid Order Book type";
                break;
            case "567":
                message = " Invalid Entry Time for Order Entry Request";
                break;
            case "568":
                message = " Invalid Entry Time for Order Modify Request";
                break;
            case "569":
                message = " Invalid Entry Time for Order Cancel Request";
                break;
            case "570":
                message = " Invalid Order Time for Order Entry Request";
                break;
            case "571":
                message = " Invalid Order Time for Order Modify Request";
                break;
            case "572":
                message = " Invalid Order Time for Order Cancel Request";
                break;
            case "573":
                message = " Invalid AON terms, value should be 0";
                break;
            case "574":
                message = " Invalid MIT terms, value should be 0";
                break;
            case "575":
                message = " Invalid ATO terms, value should be 0";
                break;
            case "576":
                message = " Invalid Reserved1 terms, value should be 0";
                break;
            case "577":
                message = " Invalid Frozen terms, value should be 0";
                break;
            case "578":
                message = " Terms Modified should be 0 for Order Entry Request";
                break;
            case "579":
                message = " Terms Traded should be 0 for Order Entry Request";
                break;
            case "580":
                message = " Terms Matched should be 0";
                break;
            case "581":
                message = " Terms MF should be 0";
                break;
            case "582":
                message = " Invalid Market Price order";
                break;
            case "583":
                message = " Invalid IOC combination";
                break;
            case "584":
                message = " Invalid GTC Combination";
                break;
            case "585":
                message = " Invalid Day Combination";
                break;
            case "586":
                message = " Invalid BranchId";
                break;
            case "587":
                message = " UserId should be 0 for Order Entry Request";
                break;
            case "588":
                message = " Invalid UserId";
                break;
            case "589":
                message = " Invalid BrokerId";
                break;
            case "590":
                message = " Invalid Open Or Close";
                break;
            case "591":
                message = " Invalid Participant";
                break;
            case "592":
                message = " Invalid Client Code";
                break;
            case "593":
                message = " Invalid Pro information";
                break;
            case "594":
                message = " Settlement Days should be 0";
                break;
            case "595":
                message = " Invalid Cover Or Uncover";
                break;
            case "596":
                message = " Invalid Giveup Flag";
                break;
            case "597":
                message = " Invalid Transaction Reply Text";
                break;
            case "598":
                message = " Invalid TermID";
                break;
            case "599":
                message = " Invalid Client Order Number";
                break;
            case "600":
                message = " Invalid trigger price for stop loss order";
                break;
            case "601":
                message = " Price not in multiples of tick size";
                break;
            case "602":
                message = " Trigger price not in multiples of tick size";
                break;
            case "603":
                message = " Update to intended parameter not allowed";
                break;
            case "604":
                message = " Invalid strike price, value should be 0 for SPTCOM";
                break;
            case "605":
                message = " Invalid option type , value should be XX for SPTCOM";
                break;
            case "606":
                message = " Option Type[0] should be C or P";
                break;
            case "607":
                message = " Option Type[1] should be A or E";
                break;
            case "608":
                message = " Token not found";
                break;
            case "609":
                message = " Token not a correct spread token for U/L Asset";
                break;
            case "610":
                message = " Invalid Instrument Class";
                break;
            case "611":
                message = " One Leg of Spread Product has expired";
                break;
            case "612":
                message = " Near Leg Maturity should be less than Far Leg Maturity for Token %1";
                break;
            case "613":
                message = " U/L Asset not allowed for trading in the current trading session";
                break;
            case "614":
                message = " Segment not allowed in Product";
                break;
            case "615":
                message = " Integrity check failure";
                break;
            case "616":
                message = " Invalid function parameters";
                break;
            case "617":
                message = " Invalid Trading Member";
                break;
            case "618":
                message = " Invalid Clearing Member";
                break;
            case "619":
                message = " Entity not allowed to perform the particular operation";
                break;
            case "620":
                message = " Critical operation failure";
                break;
            case "621":
                message = " Invalid Message Code";
                break;
            case "622":
                message = " Invalid Order ID";
                break;
            case "623":
                message = " Invalid Trade Number";
                break;
            case "624":
                message = " Invalid Traded Time";
                break;
            case "625":
                message = " Invalid Initiator Id";
                break;
            case "626":
                message = " Invalid Requested Time";
                break;
            case "627":
                message = " Trade modification operation not allowed currently";
                break;
            case "628":
                message = " Trade information not available";
                break;
            case "629":
                message = " The entered instrument token in the request does not match the actual information";
                break;
            case "630":
                message = " The trade buysell parameter does not match the actual information";
                break;
            case "631":
                message = " The trade client code information does not match the actual information";
                break;
            case "632":
                message = " Trade update not intended. Operation not allowed";
                break;
            case "633":
                message = " The previous client code information does not match the actual previous code";
                break;
            case "634":
                message = " Rejection initiated by the system";
                break;
            case "635":
                message = " GTC/GTD order not allowed in Square-off mode";
                break;
            case "636":
                message = " Spread order not allowed in Square-off mode";
                break;
            case "637":
                message = " Send to RMS Failed for Square-Off";
                break;
            case "638":
                message = " Not a Square Off Order. Order rejected by RMS";
                break;
            case "639":
                message = " Ex/DEx/DI request currently not allowed";
                break;
            case "640":
                message = " Invalid strike price, value should be greater than 0 for OPTCOM";
                break;
            case "641":
                message = " Invalid time for Ex/DEx/DI entry request";
                break;
            case "642":
                message = " Invalid time for Ex/DEx/DI modification request";
                break;
            case "643":
                message = " Invalid time for Ex/DEx/DI cancellation request";
                break;
            case "644":
                message = " Invalid flag for Ex/DEx/DI request";
                break;
            case "645":
                message = " Invalid Ex/DEx/DI request number";
                break;
            case "646":
                message = " Invalid market type in the request";
                break;
            case "647":
                message = " Invalid Last Updated time in the Ex/DEx/DI request";
                break;
            case "648":
                message = " Ex/DEx/DI request is not allowed for the entered instrument";
                break;
            case "649":
                message = " Invalid Ex/DEx/DI request. The Ex/DEx/DI request is not pending";
                break;
            case "650":
                message = " Entered instrument not in tender period";
                break;
            case "651":
                message = " Order value beyond maximum permissible value";
                break;
            case "652":
                message = " Trade modification of Own to Cli not allowed";
                break;
            case "653":
                message = " Invalid participant trade accept/reject request";
                break;
            case "654":
                message = " INST Trade accept/reject/resubmit request currently not allowed";
                break;
            case "655":
                message = " Delivery Instruction not allowed for Buyer";
                break;
            case "656":
                message = " Trade modification is not allowed, entity is not in active state";
                break;
            case "657":
                message = " Only EOS order is allowed for Auction";
                break;
            case "658":
                message = " Member is blocked for Product";
                break;
            case "659":
                message = " Trading is not allowed for entered instrument for Auction";
                break;
            case "660":
                message = " Modification for only betterment of quantity and price is allowed in last specified minutes of current auction session";
                break;
            case "661":
                message = " Auction buy order is not allowed for Exchange Buy In instrument";
                break;
            case "662":
                message = " Auction sell order is not allowed for Exchange Sell Out instrument";
                break;
            case "663":
                message = " Auction order cancellation is not allowed in last specified minutes of current auction session";
                break;
            case "664":
                message = " %1 not allowed";
                break;
            case "665":
                message = " Admin order is not allowed because Auction market is open";
                break;
            case "666":
                message = " Invalid password field";
                break;
            case "667":
                message = " Trade modification of Cli to Own not allowed";
                break;
            case "668":
                message = " Auction session is already closed";
                break;
            case "669":
                message = " Request Rejected: Not allowed in Special Session";
                break;
            case "670":
                message = " Trade modification of Inst trade to Cli or Own or BBCLI or OMNI not allowed";
                break;
            case "671":
                message = " Inst trade modification resubmit is allowed only in case of rejected trade";
                break;
            case "672":
                message = " Inst trade modification is allowed only for pending or rejected trades";
                break;
            case "673":
                message = " Trade Accept/Reject request is rejected because the trade is being modified";
                break;
            case "674":
                message = " Trade modification request is rejected because trade status has been changed after submission of modification request";
                break;
            case "675":
                message = " INST Acceptance Request rejected by RMS";
                break;
            case "676":
                message = " The participant id information does not match with actual information";
                break;
            case "677":
                message = " Trade resubmission is only allowed for institutional trade";
                break;
            case "678":
                message = " Invalid Instrument Type";
                break;
            case "679":
                message = " Square off order not allowed as user is in sq-off mode and Product is in T2T";
                break;
            case "680":
                message = " Invalid category for an entity";
                break;
            case "681":
                message = " Trade modification of Cli trade to Inst trade not allowed";
                break;
            case "682":
                message = " Trade modification of Pro trade to Inst trade not allowed";
                break;
            case "683":
                message = " Exercise or Do Not Exercise instruction is not valid for seller";
                break;
            case "684":
                message = " GTC/GTD order not allowed as Entity is in Sq-off / Suspended Sq-off mode";
                break;
            case "685":
                message = " GTC order request not allowed for this Market";
                break;
            case "686":
                message = " GTD order request not allowed for this Market";
                break;
            case "687":
                message = " Sell order with Client Type BBCli not permitted";
                break;
            case "688":
                message = " BuyBack facility presently not allowed for instrument";
                break;
            case "689":
                message = " BuyBack order Privilege not allowed";
                break;
            case "690":
                message = " BuyBack order presently not permitted for instrument";
                break;
            case "691":
                message = " Trade modification of BBCli trade to Pro trade not allowed";
                break;
            case "692":
                message = " Trade modification of BBCli trade to Cli trade not allowed";
                break;
            case "693":
                message = " Trade modification of BBCli trade to Inst trade not allowed";
                break;
            case "694":
                message = " Trade modification of Pro trade to BBCLI trade is not allowed";
                break;
            case "695":
                message = " Trade modification of Cli trade to BBCLI trade is not allowed";
                break;
            case "696":
                message = " Trade modification of INST trade to BBCLI trade is not allowed";
                break;
            case "697":
                message = " GTD BuyBack order Date greater than instrument's last Buyback day";
                break;
            case "698":
                message = " Stop Loss Order Type not allowed for Buyback orders";
                break;
            case "699":
                message = " Only Regular Lot order allowed in PostClose Session";
                break;
            case "700":
                message = " IOC order not allowed in PostClose Session";
                break;
            case "701":
                message = " GTD order not allowed in PostClose Session";
                break;
            case "702":
                message = " GTC order not allowed in PostClose Session";
                break;
            case "703":
                message = " EOS order not allowed in PostClose Session";
                break;
            case "704":
                message = " Spread order not allowed in PostClose Session";
                break;
            case "705":
                message = " Only Regular Lot order modification allowed in PostClose Session";
                break;
            case "706":
                message = " IOC order modification not allowed in PostClose Session";
                break;
            case "707":
                message = " GTD order modification not allowed in PostClose Session";
                break;
            case "708":
                message = " GTC order modification not allowed in PostClose Session";
                break;
            case "709":
                message = " EOS order modification not allowed in PostClose Session";
                break;
            case "710":
                message = " Spread order modification not allowed in PostClose Session";
                break;
            case "711":
                message = " Order modification of only postclose session's order allowed in PostClose Session";
                break;
            case "712":
                message = " PostClose Session message code Invalid";
                break;
            case "713":
                message = " OTR order request not allowed for this Market";
                break;
            case "714":
                message = " Invalid Market Order";
                break;
            case "715":
                message = " Spread order not allowed as Entity is in Sq-off / Suspended Sq-off mode";
                break;
            case "716":
                message = " Instrument Information is not valid";
                break;
            case "717":
                message = " Mkt orders are not allowed for Block deal orders";
                break;
            case "718":
                message = " GTD orders are not allowed for Block deal orders";
                break;
            case "719":
                message = " GTC orders are not allowed for Block deal orders";
                break;
            case "720":
                message = "Stop loss orders are not allowed for Block Deal orders";
                break;
            case "721":
                message = "Auction orders are not allowed for Block deal orders";
                break;
            case "722":
                message = "Invalid Disclose Qty for Block deal order";
                break;
            case "723":
                message = "BbCli orders are not allowed for Block deal orders";
                break;
            case "724":
                message = "Block deal orders are not allowed for Product";
                break;
            case "725":
                message = "Quantity for Block deal is not within the specified range.";
                break;
            case "726":
                message = "Value for Block deal is not within the specified range.";
                break;
            case "727":
                message = "Block deal orders presently not permitted.";
                break;
            case "728":
                message = "Security is not available for trading. ";
                break;
            case "729":
                message = " Security is not available for trade modification.";
                break;
            case "730":
                message = "Entered instrument not available for trading.";
                break;
            case "731":
                message = "Auction Trade Modification Not Allowed.";
                break;
            case "732":
                message = "Auction order not allowed for SpreadIOC/Combination Order.";
                break;
            case "733":
                message = "Invalid underlying for SpreadIOC/Combination Order.";
                break;
            case "734":
                message = "Stop loss order not allowed for SpreadIOC/Combination Order.";
                break;
            case "735":
                message = "Blockdeal order not allowed for SpreadIOC/Combination Order.";
                break;
            case "736":
                message = "Only regular lot order allowed for SpreadIOC/Combination Order.";
                break;
            case "737":
                message = "Spread order not allowed for SpreadIOC/Combination Order.";
                break;
            case "738":
                message = "Market order not allowed for Spread IOC order.";
                break;
            case "739":
                message = "Invalid reference number for SpreadIOC/Combination Order.";
                break;
            case "740":
                message = "Only IOC order allowed for SpreadIOC/Combination Order.";
                break;
            case "741":
                message = "EOS order not allowed for SpreadIOC/Combination Order.";
                break;
            case "742":
                message = "GTC order not allowed for SpreadIOC/Combination Order.";
                break;
            case "743":
                message = "GTD order not allowed for SpreadIOC/Combination Order.";
                break;
            case "744":
                message = "BBCLI order not allowed for SpreadIOC/Combination Order.";
                break;
            case "745":
                message = "Market lot mismatch for SpreadIOC/Combination Order.";
                break;
            case "746":
                message = "Quantity not multiple of market lot for SpreadIOC/Combination Order.";
                break;
            case "748":
                message = "Decimal locator mismatch for Spread IOC order.";
                break;
            case "750":
                message = "CM not mapped or Clearing Member not registered.";
                break;
            case "751":
                message = "INST Clearing Member not registered.";
                break;
            case "752":
                message = "Trading Member not registered.";
                break;
            case "753":
                message = "User not registered.";
                break;
            case "754":
                message = "INST User not registered.";
                break;
            case "755":
                message = "SpreadIOC/Combination order not allowed in special session.";
                break;
            case "756":
                message = "SpreadIOC/Combination order not allowed in PostClose session.";
                break;
            case "757":
                message = "INST Clearing Member is not in active mode.";
                break;
            case "758":
                message = "Clearing Member is not in active mode.";
                break;
            case "759":
                message = "Trading Member is not in active mode.";
                break;
            case "760":
                message = "INST User is not in active mode.";
                break;
            case "761":
                message = "User is not in active mode.";
                break;
            case "762":
                message = "Same token not allowed for SpreadIOC/Combination Order.";
                break;
            case "763":
                message = "Invalid counter party Trading member Id for Negotiated Trade orders.";
                break;
            case "764":
                message = "Invalid counter party Trading member category for Negotiated Trade orders.";
                break;
            case "765":
                message = "Order modification not allowed for Negotiated Trade orders.";
                break;
            case "766":
                message = "Spread order not allowed for Negotiated Trade orders.";
                break;
            case "767":
                message = "Mkt order are not allowed for Negotiated Trade orders.";
                break;
            case "768":
                message = "GTD order not allowed for Negotiated Trade orders.";
                break;
            case "769":
                message = "GTC order not allowed for Negotiated Trade orders.";
                break;
            case "770":
                message = "Auction order not allowed for Negotiated Trade orders.";
                break;
            case "771":
                message = "Invalid Disclosed Qty for Negotiated Trade order.";
                break;
            case "772":
                message = "BbCli order not allowed for Negotiated Trade orders.";
                break;
            case "773":
                message = "Quantity for Negotiated Trade is not within the specified range.";
                break;
            case "774":
                message = "Value for Negotiated Trade is not within the specified range.";
                break;
            case "775":
                message = "NT order request not allowed for this Market.";
                break;
            case "776":
                message = "Order price below the NT low price not allowed for Negotiated Trade orders.";
                break;
            case "777":
                message = "Order price beyond the NT high price not allowed for Negotiated Trade orders.";
                break;
            case "778":
                message = "IOC order not allowed for Negotiated Trade orders.";
                break;
            case "779":
                message = "Only Day/EOS order allowed for Negotiated Trade orders.";
                break;
            case "780":
                message = "Invalid CP BrokerId.";
                break;
            case "782":
                message = "Invalid Spread Price for SpreadIOC/Combination Order.";
                break;
            case "784":
                message = "Price not multiple of decimal locator for Combination order.";
                break;
            case "785":
                message = "Trade Modification not allowed, SpreadIOC/Combination Order transaction in process.";
                break;
            case "786":
                message = "Invalid Order Terms for SpreadIOC/Combination Order.";
                break;
            case "787":
                message = "Invalid OMNI Id ";
                break;
            case "788":
                message = "Invalid OMNI Id for member.";
                break;
            case "789":
                message = "OMNI Id has been disabled.";
                break;
            case "790":
                message = "Invalid Client for OMNI.";
                break;
            case "791":
                message = "Trade modification of OMNI trade to PRO trade is not allowed.";
                break;
            case "792":
                message = "Trade modification of OMNI trade to CLI trade is not allowed.";
                break;
            case "793":
                message = "Trade modification of OMNI trade to INST trade is not allowed.";
                break;
            case "794":
                message = "Trade modification of OMNI trade to BB CLI trade is not allowed.";
                break;
            case "795":
                message = "Trade modification of PRO trade to OMNI trade is not allowed.";
                break;
            case "796":
                message = "Trade modification of CLI trade to OMNI trade is not allowed.";
                break;
            case "797":
                message = "Trade modification of BB CLI trade to OMNI trade is not allowed.";
                break;
            case "798":
                message = "GTC order not allowed for product/ U/L Asset in Square off basket.";
                break;
            case "799":
                message = "GTD order not allowed for product/ U/L Asset in Square off basket.";
                break;
            case "800":
                message = "Spread order not allowed for product/ U/L Asset in Square off basket.";
                break;
            case "801":
                message = "SpreadIOC/Combination order not allowed for product/ U/L Asset in Square off basket.";
                break;
            case "802":
                message = "INST Order not allowed for product/ U/L Asset in Square off basket.";
                break;
            case "803":
                message = "Leg1/Leg2 trading currency mismatch for Spread Product.";
                break;
            case "804":
                message = "Invalid user remarks.";
                break;
            case "805":
                message = "Trade modification/split request is rejected because the trade is being modified. ";
                break;
            case "806":
                message = "Value and quantity for Block deal is not within the specified range.";
                break;
            case "807":
                message = "Value and quantity for Negotiated Trade is not within the specified range.";
                break;
            case "808":
                message = "Trade Split operation not allowed currently.";
                break;
            case "809":
                message = "Security is not available for trade split.";
                break;
            case "810":
                message = "Invalid trade split number.";
                break;
            case "811":
                message = "Invalid Traded quantity.";
                break;
            case "812":
                message = "Invalid Trade split total record count.";
                break;
            case "813":
                message = "Invalid Trade split key.";
                break;
            case "814":
                message = "Invalid trade split quantity.";
                break;
            case "815":
                message = "Cannot modify first trade split parameters other then quantity.";
                break;
            case "816":
                message = "Trade modification for this account code combination not allowed.";
                break;
            case "817":
                message = "The participant id information does not match with actual information.";
                break;
            case "818":
                message = "Trade split original client information mismatch.";
                break;
            case "819":
                message = "Trade split original traded quantity invalid.";
                break;
            case "820":
                message = "Trade split original traded quantity mismatch.";
                break;
            case "821":
                message = "Trade split original traded price mismatch.";
                break;
            case "822":
                message = "Trade split number mismatch.";
                break;
            case "823":
                message = "Trade split original user id mismatch.";
                break;
            case "824":
                message = "Trade split client cannot be modified for accepted trades.";
                break;
            case "825":
                message = "Trade split not allowed from INST to any, for accepted trade.";
                break;
            case "826":
                message = "Trade Already splited.";
                break;
            case "827":
                message = "Trade split is not allowed, entity is not in active state.";
                break;
            case "828":
                message = "System not available for trade related operation.";
                break;
            case "829":
                message = "Trade modification from INST trade to Non INST trade not allowed for accepted trades.";
                break;
            case "830":
                message = "Trade Split for this account code combination not allowed.";
                break;
            case "831":
                message = "Transaction cannot be processed ... Probably request already pending...";
                break;
            case "832":
                message = "Order Modification cannot be processed for order ... Probably request already pending... ";
                break;
            case "833":
                message = "Order Cancellation cannot be processed for order ... Probably request already pending... ";
                break;
            case "835":
                message = "Any user not allowed to trade in Product.";
                break;
            case "836":
                message = "Any user not allowed to trade in U/L Asset.";
                break;
            case "837":
                message = "User not allowed to trade in Product.";
                break;
            case "838":
                message = "User not allowed to trade in U/L Asset.";
                break;
            case "839":
                message = "Member not allowed to trade in Product.";
                break;
            case "840":
                message = "Member not allowed to trade in U/L Asset.";
                break;
            case "841":
                message = "Clearing Member not allowed to trade in Product.";
                break;
            case "842":
                message = "Clearing Member not allowed to trade in U/L Asset.";
                break;
            case "843":
                message = "Participant not allowed to trade in Product.";
                break;
            case "844":
                message = "Participant not allowed to trade in U/L Asset.";
                break;
            case "845":
                message = "Segment not allowed for user in product.";
                break;
            case "846":
                message = "Segment not allowed for user in product.";
                break;
            case "847":
                message = "Segment not allowed for user in product.";
                break;
            case "848":
                message = "Segment not allowed for user in product.";
                break;
            case "849":
                message = "Segment not allowed for member in product.";
                break;
            case "850":
                message = "Segment not allowed for member in product.";
                break;
            case "851":
                message = "Segment not allowed for member in product.";
                break;
            case "852":
                message = "Segment not allowed for member in product.";
                break;
            case "853":
                message = "Segment not allowed for clearing member in product.";
                break;
            case "854":
                message = "Segment not allowed for clearing member in product.";
                break;
            case "855":
                message = "Segment not allowed for clearing member in product.";
                break;
            case "856":
                message = "Segment not allowed for clearing member in product.";
                break;
            case "857":
                message = "Segment not allowed for participant in product.";
                break;
            case "858":
                message = "Segment not allowed for participant in product.";
                break;
            case "859":
                message = "Segment not allowed for participant in product.";
                break;
            case "860":
                message = "Segment not allowed for participant in product.";
                break;
            case "861":
                message = "Request rejected as user in delete mode.";
                break;
            case "862":
                message = "Request rejected as user in suspended mode.";
                break;
            case "863":
                message = "Request rejected as user in view only mode.";
                break;
            case "864":
                message = "Request rejected as user in square off mode.";
                break;
            case "865":
                message = "Request rejected as user in suspended(square-off) mode.";
                break;
            case "866":
                message = "Request rejected as user in not active mode.";
                break;
            case "867":
                message = "Request rejected as member in delete mode.";
                break;
            case "868":
                message = "Request rejected as member in suspended mode.";
                break;
            case "869":
                message = "Request rejected as member in view only mode.";
                break;
            case "870":
                message = "Request rejected as member in square off mode.";
                break;
            case "871":
                message = "Request rejected as member in suspended(square-off) mode.";
                break;
            case "872":
                message = "Request rejected as member in not active mode.";
                break;
            case "873":
                message = "Request rejected as clearing member in delete mode.";
                break;
            case "874":
                message = "Request rejected as clearing member in suspended mode.";
                break;
            case "875":
                message = "Request rejected as clearing member in view only mode.";
                break;
            case "876":
                message = "Request rejected as clearing member in square off mode.";
                break;
            case "877":
                message = "Request rejected as clearing member in suspended(square-off) mode.";
                break;
            case "878":
                message = "Request rejected as clearing member in not active mode.";
                break;
            case "879":
                message = "Request rejected as clearing member in delete mode.";
                break;
            case "880":
                message = "Request rejected as clearing member in suspended mode.";
                break;
            case "881":
                message = "Request rejected as clearing member in view only mode.";
                break;
            case "882":
                message = "Request rejected as clearing member in square off mode.";
                break;
            case "883":
                message = "Request rejected as clearing member in suspended(square-off) mode.";
                break;
            case "884":
                message = "Request rejected as clearing member in not active mode.";
                break;
            case "885":
                message = "Request rejected as participant in delete mode.";
                break;
            case "886":
                message = "Request rejected as participant in suspended mode.";
                break;
            case "887":
                message = "Request rejected as participant in view only mode.";
                break;
            case "888":
                message = "Request rejected as participant in square off mode.";
                break;
            case "889":
                message = "Request rejected as participant in suspended(square-off) mode.";
                break;
            case "890":
                message = "Request rejected as participant in not active mode.";
                break;
            case "891":
                message = "Square off order not allowed as user is in sq-off mode";
                break;
            case "892":
                message = "Square off order not allowed as member is in sq-off mode";
                break;
            case "893":
                message = "Square off order not allowed as clearing member is in sq-off mode";
                break;
            case "894":
                message = "Auction Buy In not allowed";
                break;
            case "895":
                message = "Auction Sell Out not allowed";
                break;
            case "896":
                message = "Auction Trading Buy In not allowed";
                break;
            case "897":
                message = "Auction Trading Sell Out not allowed";
                break;
            case "898":
                message = "Order Privileges Failed  REGULARLOT order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "899":
                message = "Order Privileges Failed  SPECIALTERMS order not allowed, privilege not given for Any user, request rejected";
                break;
            case "900":
                message = "Order Privileges Failed  STOPLOSS order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "901":
                message = "Order Privileges Failed  MARKETIFTOUCHED order not allowed, privilege not given for Any user, request rejected";
                break;
            case "902":
                message = "Order Privileges Failed  NEGOTIATED TRADE order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "903":
                message = "Order Privileges Failed  BLOCKDEAL order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "904":
                message = "Order Privileges Failed  SPOT order not allowed, privilege not given for Any user, request rejected";
                break;
            case "905":
                message = "Order Privileges Failed  AUCTION order not allowed, privilege not given for Any user, request rejected";
                break;
            case "906":
                message = "Order Privileges Failed  LIMIT order not allowed, privilege not given for Any user, request rejected";
                break;
            case "907":
                message = "Order Privileges Failed  MARKET order not allowed, privilege not given for Any user, request rejected";
                break;
            case "908":
                message = "Order Privileges Failed  SPREAD order not allowed, privilege not given for Any user, request rejected";
                break;
            case "909":
                message = "Order Privileges Failed  EXERCISE order not allowed, privilege not given for Any user, request rejected";
                break;
            case "910":
                message = "Order Privileges Failed  DONTEXERCISE order not allowed, privilege not given for Any user, request rejected";
                break;
            case "911":
                message = "Order Privileges Failed  POSITIONLIQUIDATION order not allowed, privilege not given for Any user, request rejected";
                break;
            case "912":
                message = "Order Privileges Failed  TRADEMODIFICATION order not allowed, privilege not given for Any user, request rejected";
                break;
            case "913":
                message = "Order Privileges Failed  TRADECANCELLATION order not allowed, privilege not given for Any user, request rejected";
                break;
            case "914":
                message = "Order Privileges Failed  SPREAD IOC order not allowed, privilege not given for Any user, request rejected";
                break;
            case "915":
                message = "Order Privileges Failed  COMBINATION order not allowed, privilege not given for Any user, request rejected";
                break;
            case "916":
                message = "Transaction Privileges Failed  BUYCALL order not allowed, privilege not given for Any user, request rejected";
                break;
            case "917":
                message = "Transaction Privileges Failed  SELLCALL order not allowed, privilege not given for Any user, request rejected";
                break;
            case "918":
                message = "Transaction Privileges Failed  BUYPUT order not allowed, privilege not given for Any user, request rejected";
                break;
            case "919":
                message = "Transaction Privileges Failed  SELLPUT order not allowed, privilege not given for Any user, request rejected";
                break;
            case "920":
                message = "Transaction Privileges Failed  BUY order not allowed, privilege not given for Any user, request rejected";
                break;
            case "921":
                message = "Transaction Privileges Failed  SELL order not allowed, privilege not given for Any user, request rejected";
                break;
            case "922":
                message = "Transaction Privileges Failed  AMERICAN  order not allowed, privilege not given for Any user, request rejected";
                break;
            case "923":
                message = "Transaction Privileges Failed  EUROPEAN order not allowed, privilege not given for Any user, request rejected";
                break;
            case "924":
                message = "Transaction Privileges Failed  COVER order not allowed, privilege not given for Any user, request rejected";
                break;
            case "925":
                message = "Transaction Privileges Failed  UNCOVER order not allowed, privilege not given for Any user, request rejected";
                break;
            case "926":
                message = "Transaction Privileges Failed  OPEN order not allowed, privilege not given for Any user, request rejected";
                break;
            case "927":
                message = "Transaction Privileges Failed  CLOSE order not allowed, privilege not given for Any user, request rejected";
                break;
            case "928":
                message = "Validity Privileges Failed  DAY order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "929":
                message = "Validity Privileges Failed  GTD order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "930":
                message = "Validity Privileges Failed  GTC order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "931":
                message = "Validity Privileges Failed  IOC order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "932":
                message = "Validity Privileges Failed  GTT order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "933":
                message = "Client Privileges Failed  HOUSE order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "934":
                message = "Client Privileges Failed  OWN order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "935":
                message = "Client Privileges Failed  CLIENT order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "936":
                message = "Client Privileges Failed  PARTICIPANT order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "937":
                message = "Client Privileges Failed  WHS order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "938":
                message = "Client Privileges Failed  BUYBACK order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "939":
                message = "Client Privileges Failed  OMNI order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "940":
                message = "Account Privileges Failed CASH order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "941":
                message = "Account Privileges Failed MARGIN order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "942":
                message = "Order Privileges Failed  REGULARLOT order not allowed, privilege not given for user, request rejected";
                break;
            case "943":
                message = "Order Privileges Failed  SPECIALTERMS order not allowed, privilege not given for user, request rejected";
                break;
            case "944":
                message = "Order Privileges Failed  STOPLOSS order not allowed, privilege not given for user, request rejected";
                break;
            case "945":
                message = "Order Privileges Failed  MARKETIFTOUCHED order not allowed, privilege not given for user, request rejected";
                break;
            case "946":
                message = "Order Privileges Failed  NEGOTIATED TRADE order not allowed, privilege not given for user, request rejected";
                break;
            case "947":
                message = "Order Privileges Failed  BLOCKDEAL order not allowed, privilege not given for user, request rejected";
                break;
            case "948":
                message = "Order Privileges Failed  SPOT order not allowed, privilege not given for user, request rejected";
                break;
            case "949":
                message = "Order Privileges Failed  AUCTION order not allowed, privilege not given for user, request rejected";
                break;
            case "950":
                message = "Order Privileges Failed  LIMIT order not allowed, privilege not given for user, request rejected";
                break;
            case "951":
                message = "Order Privileges Failed  MARKET order not allowed, privilege not given for user, request rejected";
                break;
            case "952":
                message = "Order Privileges Failed  SPREAD order not allowed, privilege not given for user, request rejected";
                break;
            case "953":
                message = "Order Privileges Failed  EXERCISE order not allowed, privilege not given for user, request rejected";
                break;
            case "954":
                message = "Order Privileges Failed  DONTEXERCISE order not allowed, privilege not given for user, request rejected";
                break;
            case "955":
                message = "Order Privileges Failed  POSITIONLIQUIDATION order not allowed, privilege not given for user, request rejected";
                break;
            case "956":
                message = "Order Privileges Failed  TRADEMODIFICATION order not allowed, privilege not given for user, request rejected";
                break;
            case "957":
                message = "Order Privileges Failed  TRADECANCELLATION order not allowed, privilege not given for user, request rejected";
                break;
            case "958":
                message = "Order Privileges Failed  SPREAD IOC order not allowed, privilege not given for user, request rejected";
                break;
            case "959":
                message = "Order Privileges Failed  COMBINATION order not allowed, privilege not given for user, request rejected";
                break;
            case "960":
                message = "Transaction Privileges Failed  BUYCALL order not allowed, privilege not given for user, request rejected ";
                break;
            case "961":
                message = "Transaction Privileges Failed  SELLCALL order not allowed, privilege not given for user, request rejected ";
                break;
            case "962":
                message = "Transaction Privileges Failed  BUYPUT order not allowed, privilege not given for user, request rejected ";
                break;
            case "963":
                message = "Transaction Privileges Failed  SELLPUT order not allowed, privilege not given for user, request rejected ";
                break;
            case "964":
                message = "Transaction Privileges Failed  BUY order not allowed, privilege not given for user, request rejected ";
                break;
            case "965":
                message = "Transaction Privileges Failed  SELL order not allowed, privilege not given for user, request rejected ";
                break;
            case "966":
                message = "Transaction Privileges Failed  AMERICAN  order not allowed, privilege not given for user, request rejected ";
                break;
            case "967":
                message = "Transaction Privileges Failed  EUROPEAN order not allowed, privilege not given for user, request rejected ";
                break;
            case "968":
                message = "Transaction Privileges Failed  COVER order not allowed, privilege not given for user, request rejected";
                break;
            case "969":
                message = "Transaction Privileges Failed  UNCOVER order not allowed, privilege not given for user, request rejected ";
                break;
            case "970":
                message = "Transaction Privileges Failed  OPEN order not allowed, privilege not given for user, request rejected ";
                break;
            case "971":
                message = "Transaction Privileges Failed  CLOSE order not allowed, privilege not given for user, request rejected ";
                break;
            case "972":
                message = "Validity Privileges Failed  DAY order not allowed, privilege not given for user, request rejected ";
                break;
            case "973":
                message = "Validity Privileges Failed  GTD order not allowed, privilege not given for user, request rejected ";
                break;
            case "974":
                message = "Validity Privileges Failed  GTC order not allowed, privilege not given for user, request rejected ";
                break;
            case "975":
                message = "Validity Privileges Failed  IOC order not allowed, privilege not given for user, request rejected ";
                break;
            case "976":
                message = "Validity Privileges Failed  GTT order not allowed, privilege not given for user, request rejected ";
                break;
            case "977":
                message = "Client Privileges Failed  HOUSE order not allowed, privilege not given for user, request rejected ";
                break;
            case "978":
                message = "Client Privileges Failed  OWN order not allowed, privilege not given for user, request rejected ";
                break;
            case "979":
                message = "Client Privileges Failed  CLIENT order not allowed, privilege not given for user, request rejected ";
                break;
            case "980":
                message = "Client Privileges Failed  PARTICIPANT order not allowed, privilege not given for user, request rejected ";
                break;
            case "981":
                message = "Client Privileges Failed  WHS order not allowed, privilege not given for user, request rejected ";
                break;
            case "982":
                message = "Client Privileges Failed  BUYBACK order not allowed, privilege not given for user, request rejected ";
                break;
            case "983":
                message = "Client Privileges Failed  OMNI order not allowed, privilege not given for user, request rejected ";
                break;
            case "984":
                message = "Account Privileges Failed CASH order not allowed, privilege not given for user, request rejected ";
                break;
            case "985":
                message = "Account Privileges Failed MARGIN order not allowed, privilege not given for user, request rejected ";
                break;
            case "986":
                message = "Order Privileges Failed  REGULARLOT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "987":
                message = "Order Privileges Failed  SPECIALTERMS order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "988":
                message = "Order Privileges Failed  STOPLOSS order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "989":
                message = "Order Privileges Failed  MARKETIFTOUCHED order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "990":
                message = "Order Privileges Failed  NEGOTIATED TRADE order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "991":
                message = "Order Privileges Failed  BLOCKDEAL order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "992":
                message = "Order Privileges Failed  SPOT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "993":
                message = "Order Privileges Failed  AUCTION order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "994":
                message = "Order Privileges Failed  LIMIT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "995":
                message = "Order Privileges Failed  MARKET order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "996":
                message = "Order Privileges Failed  SPREAD order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "997":
                message = "Order Privileges Failed  EXERCISE order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "998":
                message = "Order Privileges Failed  DONTEXERCISE order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "999":
                message = "Order Privileges Failed  POSITIONLIQUIDATION order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1000":
                message = "Order Privileges Failed  TRADEMODIFICATION order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1001":
                message = "Order Privileges Failed  TRADECANCELLATION order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1002":
                message = "Order Privileges Failed  SPREAD IOC order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1003":
                message = "Order Privileges Failed  COMBINATION order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1004":
                message = "Transaction Privileges Failed  BUYCALL order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1005":
                message = "Transaction Privileges Failed  SELLCALL order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1006":
                message = "Transaction Privileges Failed  BUYPUT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1007":
                message = "Transaction Privileges Failed  SELLPUT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1008":
                message = "Transaction Privileges Failed  BUY order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1009":
                message = "Transaction Privileges Failed  SELL order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1010":
                message = "Transaction Privileges Failed  AMERICAN  order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1011":
                message = "Transaction Privileges Failed  EUROPEAN order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1012":
                message = "Transaction Privileges Failed  COVER order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1013":
                message = "Transaction Privileges Failed  UNCOVER order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1014":
                message = "Transaction Privileges Failed  OPEN order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1015":
                message = "Transaction Privileges Failed  CLOSE order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1016":
                message = "Validity Privileges Failed  DAY order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1017":
                message = "Validity Privileges Failed  GTD order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1018":
                message = "Validity Privileges Failed  GTC order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1019":
                message = "Validity Privileges Failed  IOC order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1020":
                message = "Validity Privileges Failed  GTT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1021":
                message = "Client Privileges Failed  HOUSE order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1022":
                message = "Client Privileges Failed  OWN order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1023":
                message = "Client Privileges Failed  CLIENT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1024":
                message = "Client Privileges Failed  PARTICIPANT order not allowed, privilege not given for clearing member, request rejected";
                break;
            case "1025":
                message = "Client Privileges Failed  WHS order not allowed, privilege not given for clearing member, request rejected";
                break;
            case "1026":
                message = "Client Privileges Failed  BUYBACK order not allowed, privilege not given for clearing member, request rejected";
                break;
            case "1027":
                message = "Client Privileges Failed  OMNI order not allowed, privilege not given for clearing member, request rejected";
                break;
            case "1028":
                message = "Account Privileges Failed CASH order not allowed, privilege not given for clearing member, request rejected";
                break;
            case "1029":
                message = "Account Privileges Failed MARGIN order not allowed, privilege not given for clearing member, request rejected";
                break;
            case "1030":
                message = "Order Privileges Failed  REGULARLOT order not allowed, privilege not given for member, request rejected ";
                break;
            case "1031":
                message = "Order Privileges Failed  SPECIALTERMS order not allowed, privilege not given for member, request rejected ";
                break;
            case "1032":
                message = "Order Privileges Failed  STOPLOSS order not allowed, privilege not given for member, request rejected ";
                break;
            case "1033":
                message = "Order Privileges Failed  MARKETIFTOUCHED order not allowed, privilege not given for member, request rejected ";
                break;
            case "1034":
                message = "Order Privileges Failed  NEGOTIATED TRADE order not allowed, privilege not given for member, request rejected ";
                break;
            case "1035":
                message = "Order Privileges Failed  BLOCKDEAL order not allowed, privilege not given for member, request rejected ";
                break;
            case "1036":
                message = "Order Privileges Failed  SPOT order not allowed, privilege not given for member, request rejected ";
                break;
            case "1037":
                message = "Order Privileges Failed  AUCTION order not allowed, privilege not given for member, request rejected ";
                break;
            case "1038":
                message = "Order Privileges Failed  LIMIT order not allowed, privilege not given for member, request rejected ";
                break;
            case "1039":
                message = "Order Privileges Failed  MARKET order not allowed, privilege not given for member, request rejected ";
                break;
            case "1040":
                message = "Order Privileges Failed  SPREAD order not allowed, privilege not given for member, request rejected ";
                break;
            case "1041":
                message = "Order Privileges Failed  EXERCISE order not allowed, privilege not given for member, request rejected ";
                break;
            case "1042":
                message = "Order Privileges Failed  DONTEXERCISE order not allowed, privilege not given for member, request rejected ";
                break;
            case "1043":
                message = "Order Privileges Failed  POSITIONLIQUIDATION order not allowed, privilege not given for member, request rejected ";
                break;
            case "1044":
                message = "Order Privileges Failed  TRADEMODIFICATION order not allowed, privilege not given for member, request rejected ";
                break;
            case "1045":
                message = "Order Privileges Failed  TRADECANCELLATION order not allowed, privilege not given for member, request rejected ";
                break;
            case "1046":
                message = "Order Privileges Failed  SPREAD IOC order not allowed, privilege not given for member, request rejected ";
                break;
            case "1047":
                message = "Order Privileges Failed  COMBINATION order not allowed, privilege not given for member, request rejected ";
                break;
            case "1048":
                message = "Transaction Privileges Failed  BUYCALL order not allowed, privilege not given for member, request rejected ";
                break;
            case "1049":
                message = "Transaction Privileges Failed  SELLCALL order not allowed, privilege not given for member, request rejected ";
                break;
            case "1050":
                message = "Transaction Privileges Failed  BUYPUT order not allowed, privilege not given for member, request rejected ";
                break;
            case "1051":
                message = "Transaction Privileges Failed  SELLPUT order not allowed, privilege not given for member, request rejected ";
                break;
            case "1052":
                message = "Transaction Privileges Failed  BUY order not allowed, privilege not given for member, request rejected ";
                break;
            case "1053":
                message = "Transaction Privileges Failed  SELL order not allowed, privilege not given for member, request rejected ";
                break;
            case "1054":
                message = "Transaction Privileges Failed  AMERICAN  order not allowed, privilege not given for member, request rejected ";
                break;
            case "1055":
                message = "Transaction Privileges Failed  EUROPEAN order not allowed, privilege not given for member, request rejected ";
                break;
            case "1056":
                message = "Transaction Privileges Failed  COVER order not allowed, privilege not given for member, request rejected ";
                break;
            case "1057":
                message = "Transaction Privileges Failed  UNCOVER order not allowed, privilege not given for member, request rejected ";
                break;
            case "1058":
                message = "Transaction Privileges Failed  OPEN order not allowed, privilege not given for member, request rejected ";
                break;
            case "1059":
                message = "Transaction Privileges Failed  CLOSE order not allowed, privilege not given for member, request rejected ";
                break;
            case "1060":
                message = "Validity Privileges Failed  DAY order not allowed, privilege not given for member, request rejected ";
                break;
            case "1061":
                message = "Validity Privileges Failed  GTD order not allowed, privilege not given for member, request rejected ";
                break;
            case "1062":
                message = "Validity Privileges Failed  GTC order not allowed, privilege not given for member, request rejected ";
                break;
            case "1063":
                message = "Validity Privileges Failed  IOC order not allowed, privilege not given for member, request rejected ";
                break;
            case "1064":
                message = "Validity Privileges Failed  GTT order not allowed, privilege not given for member, request rejected ";
                break;
            case "1065":
                message = "Client Privileges Failed  HOUSE order not allowed, privilege not given for member, request rejected ";
                break;
            case "1066":
                message = "Client Privileges Failed  OWN order not allowed, privilege not given for member, request rejected ";
                break;
            case "1067":
                message = "Client Privileges Failed  CLIENT order not allowed, privilege not given for member, request rejected ";
                break;
            case "1068":
                message = "Client Privileges Failed  PARTICIPANT order not allowed, privilege not given for member, request rejected ";
                break;
            case "1069":
                message = "Client Privileges Failed  WHS order not allowed, privilege not given for member, request rejected ";
                break;
            case "1070":
                message = "Client Privileges Failed  BUYBACK order not allowed, privilege not given for member, request rejected ";
                break;
            case "1071":
                message = "Client Privileges Failed  OMNI order not allowed, privilege not given for member, request rejected ";
                break;
            case "1072":
                message = "Account Privileges Failed CASH order not allowed, privilege not given for member, request rejected ";
                break;
            case "1073":
                message = "Account Privileges Failed MARGIN order not allowed, privilege not given for member, request rejected ";
                break;
            case "1074":
                message = "Order Privileges Failed  REGULARLOT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1075":
                message = "Order Privileges Failed  SPECIALTERMS order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1076":
                message = "Order Privileges Failed  STOPLOSS order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1077":
                message = "Order Privileges Failed  MARKETIFTOUCHED order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1078":
                message = "Order Privileges Failed  NEGOTIATED TRADE order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1079":
                message = "Order Privileges Failed  BLOCKDEAL order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1080":
                message = "Order Privileges Failed  SPOT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1081":
                message = "Order Privileges Failed  AUCTION order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1082":
                message = "Order Privileges Failed  LIMIT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1083":
                message = "Order Privileges Failed  MARKET order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1084":
                message = "Order Privileges Failed  SPREAD order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1085":
                message = "Order Privileges Failed  EXERCISE order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1086":
                message = "Order Privileges Failed  DONTEXERCISE order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1087":
                message = "Order Privileges Failed  POSITIONLIQUIDATION order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1088":
                message = "Order Privileges Failed  TRADEMODIFICATION order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1089":
                message = "Order Privileges Failed  TRADECANCELLATION order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1090":
                message = "Order Privileges Failed  SPREAD IOC order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1091":
                message = "Order Privileges Failed  COMBINATION order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1092":
                message = "Transaction Privileges Failed  BUYCALL order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1093":
                message = "Transaction Privileges Failed  SELLCALL order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1094":
                message = "Transaction Privileges Failed  BUYPUT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1095":
                message = "Transaction Privileges Failed  SELLPUT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1096":
                message = "Transaction Privileges Failed  BUY order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1097":
                message = "Transaction Privileges Failed  SELL order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1098":
                message = "Transaction Privileges Failed  AMERICAN  order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1099":
                message = "Transaction Privileges Failed  EUROPEAN order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1100":
                message = "Transaction Privileges Failed  COVER order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1101":
                message = "Transaction Privileges Failed  UNCOVER order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1102":
                message = "Transaction Privileges Failed  OPEN order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1103":
                message = "Transaction Privileges Failed  CLOSE order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1104":
                message = "Validity Privileges Failed  DAY order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1105":
                message = "Validity Privileges Failed  GTD order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1106":
                message = "Validity Privileges Failed  GTC order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1107":
                message = "Validity Privileges Failed  IOC order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1108":
                message = "Validity Privileges Failed  GTT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1109":
                message = "Client Privileges Failed  HOUSE order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1110":
                message = "Client Privileges Failed  OWN order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1111":
                message = "Client Privileges Failed  CLIENT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1112":
                message = "Client Privileges Failed  PARTICIPANT order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1113":
                message = "Client Privileges Failed  WHS order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1114":
                message = "Client Privileges Failed  BUYBACK order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1115":
                message = "Client Privileges Failed  OMNI order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1116":
                message = "Account Privileges Failed CASH order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1117":
                message = "Account Privileges Failed MARGIN order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1118":
                message = "Order Privileges Failed  REGULARLOT order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1119":
                message = "Order Privileges Failed  SPECIALTERMS order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1120":
                message = "Order Privileges Failed  STOPLOSS order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1121":
                message = "Order Privileges Failed  MARKETIFTOUCHED order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1122":
                message = "Order Privileges Failed  NEGOTIATED TRADE order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1123":
                message = "Order Privileges Failed  BLOCKDEAL order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1124":
                message = "Order Privileges Failed  SPOT order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1125":
                message = "Order Privileges Failed  AUCTION order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1126":
                message = "Order Privileges Failed  LIMIT order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1127":
                message = "Order Privileges Failed  MARKET order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1128":
                message = "Order Privileges Failed  SPREAD order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1129":
                message = "Order Privileges Failed  EXERCISE order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1130":
                message = "Order Privileges Failed  DONTEXERCISE order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1131":
                message = "Order Privileges Failed  POSITIONLIQUIDATION order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1132":
                message = "Order Privileges Failed  TRADEMODIFICATION order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1133":
                message = "Order Privileges Failed  TRADECANCELLATION order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1134":
                message = "Order Privileges Failed  SPREAD IOC order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1135":
                message = "Order Privileges Failed  COMBINATION order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1136":
                message = "Transaction Privileges Failed  BUYCALL order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1137":
                message = "Transaction Privileges Failed  SELLCALL order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1138":
                message = "Transaction Privileges Failed  BUYPUT order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1139":
                message = "Transaction Privileges Failed  SELLPUT order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1140":
                message = "Transaction Privileges Failed  BUY order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1141":
                message = "Transaction Privileges Failed  SELL order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1142":
                message = "Transaction Privileges Failed  AMERICAN  order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1143":
                message = "Transaction Privileges Failed  EUROPEAN order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1144":
                message = "Transaction Privileges Failed  COVER order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1145":
                message = "Transaction Privileges Failed  UNCOVER order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1146":
                message = "Transaction Privileges Failed  OPEN order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1147":
                message = "Transaction Privileges Failed  CLOSE order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1148":
                message = "Validity Privileges Failed  DAY order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1149":
                message = "Validity Privileges Failed  GTD order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1150":
                message = "Validity Privileges Failed  GTC order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1151":
                message = "Validity Privileges Failed  IOC order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1152":
                message = "Validity Privileges Failed  GTT order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1153":
                message = "Client Privileges Failed  HOUSE order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1154":
                message = "Client Privileges Failed  OWN order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1155":
                message = "Client Privileges Failed  CLIENT order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1156":
                message = "Client Privileges Failed  PARTICIPANT order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1157":
                message = "Client Privileges Failed  WHS order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1158":
                message = "Client Privileges Failed  BUYBACK order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1159":
                message = "Client Privileges Failed  OMNI order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1160":
                message = "Account Privileges Failed CASH order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1161":
                message = "Account Privileges Failed MARGIN order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1162":
                message = "Trade Split to Cli not allowed for Any user, request rejected.";
                break;
            case "1163":
                message = "Trade Split to HOUSE not allowed for Any user, request rejected.";
                break;
            case "1164":
                message = "Trade Split to Inst not allowed for Any user, request rejected.";
                break;
            case "1165":
                message = "Trade Split to BBCli not allowed for Any user, request rejected.";
                break;
            case "1166":
                message = "Trade Split to OMNI not allowed for Any user, request rejected.";
                break;
            case "1167":
                message = "Trade Split to Cli not allowed for member, request rejected.";
                break;
            case "1168":
                message = "Trade Split to HOUSE not allowed for member, request rejected.";
                break;
            case "1169":
                message = "Trade Split to Inst not allowed for member, request rejected.";
                break;
            case "1170":
                message = "Trade Split to BBCli not allowed for member, request rejected.";
                break;
            case "1171":
                message = "Trade Split to OMNI not allowed for member, request rejected.";
                break;
            case "1172":
                message = "Trade Split to Cli not allowed for participant, request rejected.";
                break;
            case "1173":
                message = "Trade Split to HOUSE not allowed for participant, request rejected.";
                break;
            case "1174":
                message = "Trade Split to Inst not allowed for participant, request rejected.";
                break;
            case "1175":
                message = "Trade Split to BBCli not allowed for participant, request rejected.";
                break;
            case "1176":
                message = "Trade Split to OMNI not allowed for participant, request rejected.";
                break;
            case "1177":
                message = "Trade Split to Cli not allowed for member, request rejected.";
                break;
            case "1178":
                message = "Trade Split to HOUSE not allowed for member, request rejected.";
                break;
            case "1179":
                message = "Trade Split to Inst not allowed for member, request rejected.";
                break;
            case "1180":
                message = "Trade Split to BBCli not allowed for member, request rejected.";
                break;
            case "1181":
                message = "Trade Split to OMNI not allowed for member, request rejected.";
                break;
            case "1182":
                message = "Trade Split to Cli not allowed for member, request rejected.";
                break;
            case "1183":
                message = "Trade Split to HOUSE not allowed for member, request rejected.";
                break;
            case "1184":
                message = "Trade Split to Inst not allowed for member, request rejected.";
                break;
            case "1185":
                message = "Trade Split to BBCli not allowed for member, request rejected.";
                break;
            case "1186":
                message = "Trade Split to OMNI not allowed for member, request rejected.";
                break;
            case "1187":
                message = "Trade Split to Cli not allowed for user, request rejected.";
                break;
            case "1188":
                message = "Trade Split to HOUSE not allowed for user, request rejected.";
                break;
            case "1189":
                message = "Trade Split to Inst not allowed for user, request rejected.";
                break;
            case "1190":
                message = "Trade Split to BBCli not allowed for user, request rejected.";
                break;
            case "1191":
                message = "Trade Split to OMNI not allowed for user, request rejected.";
                break;
            case "1192":
                message = "Trade Split not allowed for Any user, request rejected";
                break;
            case "1193":
                message = "Trade Split not allowed for member, request rejected";
                break;
            case "1194":
                message = "Trade Split not allowed for Inst, request rejected";
                break;
            case "1195":
                message = "Trade Split not allowed for clearing member, request rejected";
                break;
            case "1196":
                message = "Trade Split not allowed for member, request rejected";
                break;
            case "1197":
                message = "Trade Split not allowed for user, request rejected";
                break;
            case "1198":
                message = "Trade Modification to Cli not allowed for Any user, request rejected.";
                break;
            case "1199":
                message = "Trade Modification to HOUSE not allowed for Any user, request rejected.";
                break;
            case "1200":
                message = "Trade Modification to Inst not allowed for Any user, request rejected.";
                break;
            case "1201":
                message = "Trade Modification to BBCLi not allowed for Any user, request rejected.";
                break;
            case "1202":
                message = "Trade Modification to OMNI not allowed for Any user, request rejected.";
                break;
            case "1203":
                message = "Trade Modification to Cli not allowed for clearing member, request rejected.";
                break;
            case "1204":
                message = "Trade Modification to HOUSE not allowed for clearing member, request rejected.";
                break;
            case "1205":
                message = "Trade Modification to Inst not allowed for clearing member, request rejected.";
                break;
            case "1206":
                message = "Trade Modification to BBCli not allowed for clearing member, request rejected.";
                break;
            case "1207":
                message = "Trade Modification to OMNI not allowed for clearing member, request rejected.";
                break;
            case "1208":
                message = "Trade Modification to Cli not allowed for member, request rejected.";
                break;
            case "1209":
                message = "Trade Modification to HOUSE not allowed for member, request rejected.";
                break;
            case "1210":
                message = "Trade Modification to Inst not allowed for member, request rejected.";
                break;
            case "1211":
                message = "Trade Modification to BBCli not allowed for member, request rejected.";
                break;
            case "1212":
                message = "Trade Modification to OMNI not allowed for member, request rejected.";
                break;
            case "1213":
                message = "Trade Modification to Cli not allowed for user, request rejected.";
                break;
            case "1214":
                message = "Trade Modification to HOUSE not allowed for user, request rejected.";
                break;
            case "1215":
                message = "Trade Modification to Inst not allowed for user, request rejected.";
                break;
            case "1216":
                message = "Trade Modification to BBCli not allowed for user, request rejected.";
                break;
            case "1217":
                message = "Trade Modification to OMNI not allowed for user, request rejected.";
                break;
            case "1218":
                message = "Trade Modification to Cli not allowed for participant, request rejected.";
                break;
            case "1219":
                message = "Trade Modification to HOUSE not allowed for participant, request rejected.";
                break;
            case "1220":
                message = "Trade Modification to Inst not allowed for participant, request rejected.";
                break;
            case "1221":
                message = "Trade Modification to BBCli not allowed for participant, request rejected.";
                break;
            case "1222":
                message = "Trade Modification to OMNI not allowed for participant, request rejected.";
                break;
            case "1223":
                message = "Trade Modification to Cli not allowed for inst clearing member, request rejected.";
                break;
            case "1224":
                message = "Trade Modification to HOUSE not allowed for inst clearing member, request rejected.";
                break;
            case "1225":
                message = "Trade Modification to Inst not allowed for inst clearing member, request rejected.";
                break;
            case "1226":
                message = "Trade Modification to BBCli not allowed for inst clearing member, request rejected.";
                break;
            case "1227":
                message = "Trade Modification to OMNI not allowed for inst clearing member, request rejected.";
                break;
            case "1228":
                message = "Trade Split Request Rejected By RMS.?????????????????????????????????????? ";
                break;
            case "1229":
                message = "Trade Modification Request Rejected By RMS.????????????????????? ";
                break;
            case "1230":
                message = "Price not in acceptable range.";
                break;
            case "1231":
                message = "User does not exist in the system.";
                break;
            case "1232":
                message = "Member does not exist in the system.";
                break;
            case "1233":
                message = "User or Member does not exist in the system.";
                break;
            case "1234":
                message = "Originating user does not exist in the system.";
                break;
            case "1235":
                message = "Client does not exist in the system.";
                break;
            case "1236":
                message = "Request rejected as member not in active mode.";
                break;
            case "1237":
                message = "Member deactivated.";
                break;
            case "1238":
                message = "User not allowed to trade in Product.";
                break;
            case "1239":
                message = "Single transaction quantity for user cannot exceed maximum limit.";
                break;
            case "1240":
                message = "Single transaction value for user cannot exceed maximum limit.";
                break;
            case "1241":
                message = "User not allowed to trade in the derivatives segment.";
                break;
            case "1242":
                message = "User not allowed to trade in the commodities spot segment.";
                break;
            case "1243":
                message = "User does not have the required surveillance parameters defined.";
                break;
            case "1244":
                message = "User does not have trading privelege.";
                break;
            case "1245":
                message = "Clearing member id invalid for the operation.";
                break;
            case "1246":
                message = "Out of memory";
                break;
            case "1247":
                message = "Trade Modification not allowed for BBCli Trade";
                break;
            case "1248":
                message = "Trade Split not allowed for BBCli Trade";
                break;
            case "1249":
                message = "Trade is locked for cancellation,can not make operation on this trade";
                break;
            case "1250":
                message = "Trade cancel Error,Contact Administrator.";
                break;
            case "1251":
                message = "Trade cancellation initiated, trade operations not allowed .";
                break;
            case "1252":
                message = "Trade cancellation request is already pending for this trade.";
                break;
            case "1253":
                message = "Trade cancellation not allowed, for institutional clearing member";
                break;
            case "1254":
                message = "Trade cancellation not allowed, for participant";
                break;
            case "1255":
                message = "Trading member cannot initiate trade cancel request on inst approved trade";
                break;
            case "1256":
                message = "Clearing member cannot initiate trade cancel request on inst approved trade.";
                break;
            case "1257":
                message = "Trade cancellation privilege not allowed for any user";
                break;
            case "1258":
                message = "Trade cancellation privilege not allowed for clearing member";
                break;
            case "1259":
                message = "Trade cancellation privilege not allowed for trading member";
                break;
            case "1260":
                message = "Trade cancellation not allowed currently";
                break;
            case "1261":
                message = "Trade cancellation privilege not allowed for participant";
                break;
            case "1262":
                message = "Trade cancellation privilege not allowed for inst clearing member ";
                break;
            case "1263":
                message = "Inst trade accepted in custodian hierarchy, cannot cancel the trade";
                break;
            case "1264":
                message = "Invalid institutional clearing member id";
                break;
            case "1265":
                message = "Trade cancellation request does not exist to for approval";
                break;
            case "1266":
                message = "Trade cancellation request exist but not confirmed,can not approve";
                break;
            case "1267":
                message = "Trade cancellation can not be approved, already approved.";
                break;
            case "1268":
                message = "Trade cancellation request already approved by exchange,can not approve";
                break;
            case "1269":
                message = "Trade cancellation request already rejected by exchange,can not approve";
                break;
            case "1270":
                message = "Trade cancellation request can not be initiated for exchange initiated auction trades";
                break;
            case "1271":
                message = "Trade information mismatch in CP approve request.";
                break;
            case "1272":
                message = "Invalid CP responded time";
                break;
            case "1273":
                message = "Invalid exchange responded time";
                break;
            case "1274":
                message = "OMNI Functionality not allowed.";
                break;
            case "1275":
                message = "OMNI Net Down not allowed currently";
                break;
            case "1276":
                message = "Rejected By RMS";
                break;
            case "1277":
                message = "Trade cancellation request rejected by RMS";
                break;
            case "1278":
                message = "Trade already cancelled";
                break;
            case "1279":
                message = "Only exchange admin can initiate trade cancellation request.";
                break;
            case "1280":
                message = "Trade cancellation is not allowed for Pre open trades.";
                break;
            case "1281":
                message = "Trade split not allowed for pre open trades.";
                break;
            case "1282":
                message = "Trade modification not allowed for pre open trades.";
                break;
            case "1283":
                message = "Pre open session order not allowed for the contract.";
                break;
            case "1284":
                message = "Only pre open orders allowed during pre open session.";
                break;
            case "1285":
                message = "Multi leg orders not allowed during pre open session.";
                break;
            case "1286":
                message = "Order modification from disclosed quantity to non disclosed quantity order, not allowed during pre open session";
                break;
            case "1287":
                message = "Only EOS or Day order allowed for pre open order";
                break;
            case "1288":
                message = "Order modification of order type for pre open order is not allowed";
                break;
            case "1289":
                message = "Pre open order entry allowed only in pre open order acceptance session";
                break;
            case "1290":
                message = "Order Privileges Failed  PREOPEN order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "1291":
                message = "Order Privileges Failed  PREOPEN order not allowed, privilege not given for user, request rejected ";
                break;
            case "1292":
                message = "Order Privileges Failed  PREOPEN order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1293":
                message = "Order Privileges Failed  PREOPEN order not allowed, privilege not given for clearing member, request rejected ";
                break;
            case "1294":
                message = "Order Privileges Failed  PREOPEN order not allowed, privilege not given for trading member, request rejected ";
                break;
            case "1295":
                message = "Order Privileges Failed  PREOPEN order not allowed, privilege not given for participant, request rejected ";
                break;
            case "1296":
                message = "Quantity decrement not allowed during pre open betterment session";
                break;
            case "1297":
                message = "Price decrement not allowed during pre open betterment session for buy order";
                break;
            case "1298":
                message = "Price increment not allowed during pre open betterment session for sell order";
                break;
            case "1299":
                message = "Buy back order not allowed for pre open order";
                break;
            case "1300":
                message = "Pre open order allowed only in pre open order acceptance session";
                break;
            case "1301":
                message = "Market order to limit order is not allowed for pre open order";
                break;
            case "1302":
                message = "Spread order not allowed for pre open order";
                break;
            case "1303":
                message = "Participant and CM are in non square off mode";
                break;
            case "1304":
                message = "CP Square off order allowed only from exchange admin";
                break;
            case "1305":
                message = "Trade modification not allowed during pre open matching time.";
                break;
            case "1306":
                message = "Trade accept reject not allowed during pre open matching time.";
                break;
            case "1307":
                message = "Trade split not allowed during pre open matching time.";
                break;
            case "1308":
                message = "Trade resubmission not allowed during pre open matching time.";
                break;
            case "1309":
                message = "Ex/DEx/DI request is not allowed during pre open matching time.";
                break;
            case "1310":
                message = "Omni net down request not allowed during pre open matching time.";
                break;
            case "1311":
                message = "Only regular lot or pre open order can be modified during pre open session.";
                break;
            case "1312":
                message = "Pre open order can not be modified to disclosed quantity order.";
                break;
            case "1313":
                message = "Invalid disclosed quantity for pre open order type";
                break;
            case "1314":
                message = "GTC/GTD order entry with Disclosed Quantity not allowed ";
                break;
            case "1315":
                message = "GTC/GTD order modification with Disclosed Quantity not allowed ";
                break;
            case "1316":
                message = "Any user not allowed to modify/split trade in Product.? ";
                break;
            case "1317":
                message = "Any user not allowed to modify/split trade in U/L Asset.";
                break;
            case "1318":
                message = "User not allowed to modify/split trade in Product.";
                break;
            case "1319":
                message = "User not allowed to modify/split trade in U/L Asset.";
                break;
            case "1320":
                message = "Member not allowed to modify/split trade in Product.";
                break;
            case "1321":
                message = "Member not allowed to modify/split trade in U/L Asset.";
                break;
            case "1322":
                message = "Clearing Member not allowed to modify/split trade in Product.";
                break;
            case "1323":
                message = "Clearing Member not allowed to modify/split trade in U/L Asset.";
                break;
            case "1324":
                message = "Participant not allowed to modify/split trade in Product.";
                break;
            case "1325":
                message = "Participant not allowed to modify/split trade in U/L Asset.";
                break;
            case "1326":
                message = "FOK order for Multi Leg not allowed.";
                break;
            case "1327":
                message = "Invalid FOK Combination.";
                break;
            case "1328":
                message = "FOK order not allowed for Negotiated Trade order.";
                break;
            case "1329":
                message = "FOK order entry not allowed in Post-Close session.";
                break;
            case "1330":
                message = "FOK orde modification not allowed in Post-Close session.";
                break;
            case "1331":
                message = "Validity Privileges Failed? FOK order not allowed, privilege not given for Any user, request rejected";
                break;
            case "1332":
                message = "Validity Privileges Failed? FOK order not allowed, privilege not given for user, request rejected";
                break;
            case "1333":
                message = "Validity Privileges Failed? FOK order not allowed, privilege not given for clearing member, request rejected";
                break;
            case "1334":
                message = "Validity Privileges Failed? FOK order not allowed, privilege not given for member, request rejected";
                break;
            case "1335":
                message = "Validity Privileges Failed? FOK order not allowed, privilege not given for participant, request rejected";
                break;
            case "1336":
                message = "Validity Privileges Failed? FOK order not allowed, privilege not given for clearing member, request rejected";
                break;
            case "1337":
                message = "Expiry position download is not available?Privilege not given/Market is open";
                break;
            case "1338":
                message = "Dealer ID Already Exists In the System";
                break;
            case "1339":
                message = "Default Clearing Code is Invalid";
                break;
            case "1340":
                message = "Privileges for Order Entry not allowed for specified user";
                break;
            case "1341":
                message = "Privileges for Order Modification not allowed for specified user";
                break;
            case "1342":
                message = "Privileges for Order Cancel not allowed for specified user";
                break;
            case "1343":
                message = "Privileges for Trade Modification not allowed for specified user";
                break;
            case "1344":
                message = "Privileges for Trade Resubmission not allowed for specified user";
                break;
            case "1345":
                message = "Privileges for Trade Accept/Reject not allowed for specified user";
                break;
            case "1346":
                message = "Privileges for Trade Split not allowed for specified user";
                break;
            case "1347":
                message = "Privileges for Trade Cancel not allowed for specified user";
                break;
            case "1348":
                message = "Privileges for Conditional Order Cancel not allowed for specified user";
                break;
            case "1349":
                message = "Privileges for ExDI not allowed for specified user";
                break;
            case "1350":
                message = "Privileges for OMNI Net Down not allowed for specified user";
                break;
            case "1351":
                message = "GTC/GTD non DQ to DQ order modification not allowed in pre open session.";
                break;
            case "1352":
                message = "GTC/GTD DQ order modification not allowed in pre open session.";
                break;
            case "1353":
                message = "Invalid IOC and FOK combination";
                break;
            case "1354":
                message = "Modification of GTC/GTD DQ order not allowed";
                break;
            case "1355":
                message = "Block deal FOK order not allowed";
                break;
            case "1356":
                message = "Trade split count mismatch";
                break;
            case "1357":
                message = "Stop Loss order modifiation not allowed in pre open session.";
                break;
            case "1358":
                message = "FOK order not allowed.";
                break;
            case "1359":
                message = "Update user settings privilege failed for user.";
                break;
            case "1360":
                message = "Update user settings not allowed for user.";
                break;
            case "1361":
                message = "GTC Order Not allowed for Spot Instrument";
                break;
            case "1362":
                message = "Invalid Good til date ";
                break;
            case "1363":
                message = "GTC order not allowed for spread Instrument";
                break;
            case "1364":
                message = "GTD order not allowed for spot Instrument";
                break;
            case "1365":
                message = "GTD order not allowed for spread Instrument";
                break;
            case "1366":
                message = "GTD order not allowed for Auction Instrument";
                break;
            case "1367":
                message = "Trade cancellation already rejected by CP";
                break;
            case "1368":
                message = "INST Square-off order not allowed";
                break;
            case "1369":
                message = "INST Auction order not allowed";
                break;
            case "1370":
                message = "Order cancellation allowed only in pre open order acceptance session";
                break;
            case "1371":
                message = "Trade modification to OWN not allowd for any user, request rejected";
                break;
            case "1372":
                message = "Trade modification to OWN not allowd for clearing member, request rejected";
                break;
            case "1373":
                message = "Trade modification to OWN not allowd for member, request rejected";
                break;
            case "1374":
                message = "Trade modification to OWN not allowd for inst clearing member, request rejected";
                break;
            case "1375":
                message = "Trade modification to OWN not allowd for participant, request rejected";
                break;
            case "1376":
                message = "Trade modification to OWN not allowd for user, request rejected";
                break;
            case "1377":
                message = "Trade split to OWN not allowd for any user, request rejected.";
                break;
            case "1378":
                message = "Trade split to OWN not allowd for member, request rejected.";
                break;
            case "1379":
                message = "Trade split to OWN not allowd for member, request rejected.";
                break;
            case "1380":
                message = "Trade split to OWN not allowd for member, request rejected.";
                break;
            case "1381":
                message = "Trade split to OWN not allowd for participant, request rejected.";
                break;
            case "1382":
                message = "Trade split to OWN not allowd for user, request rejected.";
                break;
            case "1383":
                message = "Trade modification not allowd for any user, request rejected";
                break;
            case "1384":
                message = "Trade modification not allowd for member, request rejected";
                break;
            case "1385":
                message = "Trade modification not allowd for inst, request rejected";
                break;
            case "1386":
                message = "Trade modification not allowd for clearing member, request rejected";
                break;
            case "1387":
                message = "Trade modification not allowd for member, request rejected";
                break;
            case "1388":
                message = "Trade modification not allowd for user, request rejected";
                break;
            case "8001":
                message = "Order Cancelled By The Exchange RMS.";
                break;
            case "8002":
                message = "Order Cancelled By The Exchange System.";
                break;
            case "8003":
                message = "Presently, trading is not allowed in this Product.";
                break;
            case "8004":
                message = "Market is closed !";
                break;
            case "8005":
                message = "System is not Ready !";
                break;
            case "8006":
                message = "Order Not in Pending State.";
                break;
            case "8007":
                message = "Order has been changed.";
                break;
            case "8008":
                message = "Only Market Orders are allowed in Post Close Session!";
                break;
            case "8009":
                message = "Currently System is not available for Post Close Trading";
                break;
            case "8010":
                message = "Market Order is not allowed for OrderType Block Deal!";
                break;
            case "8011":
                message = "Order Price is not within the defind Block Deal DPR range";
                break;
            case "8012":
                message = "Contract does not exists";
                break;
            case "8013":
                message = "Spread Product Trade would result in Far month Trade Price beyond the Far month Product Price Band Range.";
                break;
            case "8014":
                message = "Market Order is not allowed for OrderType NT!";
                break;
            case "8015":
                message = "Order Price is not within the defined NT DPR range";
                break;
            case "20001":
                message = "Invalid instrument Information !";
                break;
            case "20011":
                message = "Invalid Message Code !";
                break;
            case "20012":
                message = "Invalid Message Size !";
                break;
            case "20013":
                message = "Invalid Error Code !";
                break;
            case "20014":
                message = "Invalid Exchange Timestamp !";
                break;
            case "20015":
                message = "Invalid Product Id !";
                break;
            case "20016":
                message = "Invalid Instrument Code !";
                break;
            case "21001":
                message = "Invalid Order Quantity !";
                break;
            case "21002":
                message = "Invalid Disclosed Quantity !";
                break;
            case "21003":
                message = "Invalid Trigger Price !";
                break;
            case "21005":
                message = "Invalid Order Type !";
                break;
            case "21007":
                message = "Invalid Exchange Order Number !";
                break;
            case "21008":
                message = "Invalid Buy Or Sell !";
                break;
            case "21009":
                message = "Invalid Order Entry Time !";
                break;
            case "21010":
                message = "Invalid Order Time !";
                break;
            case "21011":
                message = "Invalid Order Attributes !";
                break;
            case "21012":
                message = "Invalid Logon Id !";
                break;
            case "21013":
                message = "Invalid Trading Member Id";
                break;
            case "21014":
                message = "Invalid Account !";
                break;
            case "21017":
                message = "Invalid Clearing Member Id !";
                break;
            case "21018":
                message = "Invalid Good Till Date !";
                break;
            case "21019":
                message = "Invalid Instrument Identifier !";
                break;
            case "21020":
                message = "Invalid Instrument Code !";
                break;
            case "21021":
                message = "Invalid Expiry Date !";
                break;
            case "21022":
                message = "Invalid Disclosed Quantity Remaining !";
                break;
            case "21023":
                message = "Invalid Pending Order Quantity !";
                break;
            case "21024":
                message = "Invalid Quantity Traded Today !";
                break;
            case "21025":
                message = "Invalid Order Price !";
                break;
            case "21026":
                message = "Invalid Client Id !";
                break;
            case "21027":
                message = "Invalid User Reference Text !";
                break;
            case "21029":
                message = "Invalid Time !";
                break;
            case "21030":
                message = "Invalid Last Updated Time !";
                break;
            case "21031":
                message = "Invalid Original Client Id !";
                break;
            case "21032":
                message = "Invalid Old Client Id !";
                break;
            case "21033":
                message = "Invalid New Client Id !";
                break;
            case "21034":
                message = "Invalid Last EX/DEx/DI Time !";
                break;
            case "21035":
                message = "Invalid Status !";
                break;
            case "21050":
                message = "Invalid Password !";
                break;
            case "21051":
                message = "Invalid New Password !";
                break;
            case "21052":
                message = "Invalid Major Version No !";
                break;
            case "21053":
                message = "Invalid Minor Version No !";
                break;
            case "21101":
                message = "Invalid Trade Number !";
                break;
            case "21102":
                message = "Invalid Traded Time !";
                break;
            case "21103":
                message = "Invalid Original Account !";
                break;
            case "21104":
                message = "Invalid Old Account !";
                break;
            case "21105":
                message = "Invalid New Account !";
                break;
            case "21106":
                message = "Invalid Initiated By !";
                break;
            case "21107":
                message = "Invalid Initiated Time !";
                break;
            case "21108":
                message = "Invalid Rejection Time !";
                break;
            case "21444":
                message = "Invalid Original Participant Id !";
                break;
            case "21445":
                message = "Invalid Old Participant Id !";
                break;
            case "21446":
                message = "Invalid New Participant Id !";
                break;
            case "21999":
                message = "Invalid Reserved1 !";
                break;
            case "22222":
                message = "Invalid Participant Id !";
                break;
            case "22223":
                message = "Disclosed Qty Not Allowed for this order type !";
                break;
            case "22224":
                message = "Market Order Not Allowed For Auction instrument !";
                break;
            case "22225":
                message = "Stop Loss Not Allowed For Spread instrument !";
                break;
            case "22226":
                message = "Stop Loss Not Allowed For Auction instrument !";
                break;
            case "22227":
                message = "Stop Loss Not Allowed For IOC Order !";
                break;
            case "22228":
                message = "GTC Orders Not Allowed for Auction Orders !";
                break;
            case "22229":
                message = "GTD Order Not Allowed For Spot instrument !";
                break;
            case "22230":
                message = "GTC Order Not Allowed For Spread instrument !";
                break;
            case "22231":
                message = "GTD Order Not Allowed For Auction instrument !";
                break;
            case "22232":
                message = "GTD Order Not Allowed For Spread instrument !";
                break;
            case "22233":
                message = "GTC Order Not Allowed For Spot instrument !";
                break;
            case "22234":
                message = "Trade modification of Inst trade to Cli or Own not allowed !";
                break;
            case "22235":
                message = "Invalid COC Order Attributes !";
                break;
            case "22236":
                message = "Invalid COC Instrument Definition !";
                break;
            case "22237":
                message = "Invalid Underlying Identifier !";
                break;
            case "22238":
                message = "GTC Order Not Allowed For Block Deal !";
                break;
            case "22239":
                message = "GTD Order Not Allowed For Block Deal !";
                break;
            case "22240":
                message = "Invalid Exchange Multi Leg Reference Number !";
                break;
            case "22241":
                message = "Invalid CP Trading Member Id !";
                break;
            case "22242":
                message = "GTD Order Not Allowed For Negotiated Trade Orders !";
                break;
            case "22243":
                message = "GTC Order Not Allowed For Negotiated Trade Orders !";
                break;
            case "22244":
                message = "Invalid Multi Leg Order Attribute Identifier !";
                break;
            case "22245":
                message = "Invalid Number of Legs !";
                break;
            case "22246":
                message = "Invalid Spread Price !";
                break;
            case "22247":
                message = "Invalid Omni ID !";
                break;
            case "22248":
                message = "Invalid Original Omni ID !";
                break;
            case "22249":
                message = "Invalid Old Omni ID !";
                break;
            case "22250":
                message = "Invalid New Omni ID !";
                break;
            case "22251":
                message = "Trade modification of Omni trade to Cli or Own or Inst not allowed";
                break;
            case "23333":
                message = "CTCL unique number not registered with Exchange  !";
                break;
            case "24446":
                message = "Invalid Reason Code !";
                break;
            case "24447":
                message = "Invalid Reason Code !";
                break;
            case "24448":
                message = "Invalid Confirmation Time !";
                break;
            case "24449":
                message = "Invalid User Reference Text !";
                break;
            case "24450":
                message = "Invalid Acceptance Time !";
                break;
            case "24451":
                message = "GoodTillDays Not Allowed For EOS Orders !";
                break;
            case "24452":
                message = "Invalid Vendor Code";
                break;
            case "24453":
                message = "User Id not permitted for ATF";
                break;
            case "24454":
                message = "User Id not permitted for Smart Order Routing without Program Trading ";
                break;
            case "24455":
                message = "User Id not permitted for Smart Order Routing with Program Trading";
                break;
            case "24456":
                message = "Invalid Program Trading Indicator";
                break;
            case "24457":
                message = "User Id not permitted for IBT";
                break;
            case "24458":
                message = "User Id not permitted for DMA";
                break;
            case "24459":
                message = "User Id not permitted for WT";
                break;
            case "24460":
                message = "User Id not permitted for CTCL";
                break;
            case "24461":
                message = "Invalid DX/EDx/DI Number";
                break;
            case "24462":
                message = "Invalid DX/EDx/DI Flag";
                break;
            case "7001 ":
                message = "Duplicate Auction order rejected from Query Server.";
                break;
            case "17001":
                message = "System Generated Cancellation";
                break;
            case "17002":
                message = "System Generated Cancellation";
                break;
            case "17003":
                message = "System Generated Cancellation";
                break;
            case "17004":
                message = "System Generated Cancellation";
                break;
            case "17005":
                message = "System Generated Cancellation";
                break;
            case "17006":
                message = "System Generated Cancellation";
                break;
            case "17007":
                message = "System Generated Cancellation";
                break;
            case "17008":
                message = "System Generated Cancellation";
                break;
            case "17009":
                message = "System Generated Cancellation";
                break;
            case "17010":
                message = "System Generated Cancellation";
                break;
            case "17011":
                message = "System Generated Cancellation";
                break;
            case "17012":
                message = "System Generated Cancellation";
                break;
            case "17013":
                message = "System Generated Cancellation";
                break;
            case "17014":
                message = "System Generated Cancellation";
                break;
            case "17015":
                message = "System Generated Cancellation";
                break;
            case "17016":
                message = "System Inserted Pre-Open End";
                break;
            case "17017":
                message = "System Inserted MarketEvent";
                break;
            case "17018":
                message = "System not available";
                break;
            case "8021":
                message = "Error Code : 8021 Invalid Order Quantity";
                break;
            case "1487":
                message = "Error Code : 1487 Market order not allowed for Limited Physical Market.";
                break;
            case "1489":
                message = "Error Code : 1489 Only Regulat Lot order allowed for Limited Physical Market.";
                break;
            case "1490":
                message = "Error Code : 1490 Post close order not allowed for Limited Physical Market.";
                break;
            case "1492":
                message = "Error Code : 1492 Invalid order value for Index Order Entry";
                break;
            case "1493":
                message = "Error Code : 1493 Contract is in Square off basket,? Index Order not allowed";
                break;
            case "1491":
                message = "Error Code : 1491 SpreadIOC/Combination order not allowed for Limited Physical Market.";
                break;
            case "1488":
                message = "Error Code : 1488 Disclose Qty Order not allowed for Limited Physical Market.";
                break;
            case "1494":
                message = "Error Code : 1494 Trade modification for BBCli not allowed in Token";
                break;
            case "1495":
                message = "Error Code : 1495 Trade modification for BBCli not allowed for TM";
                break;
            case "1496":
                message = "Error Code : 1496 Trade Split for BBCli not allowed in Token";
                break;
            case "1497":
                message = "Error Code : 1497 Trade Split for BBCli not allowed for TM";
                break;
            case "1498":
                message = "Error Code : 1498 Request rejected, User in Voluntary Square-Off Mode, Only IOC ORDER  allowed";
                break;
            case "1499":
                message = "Error Code : 1499 Request rejected, TM in Voluntary Square-Off Mode, Only IOC ORDER  allowed ";
                break;
            case "1500":
                message = "Error Code : 1500 Request rejected, CM in Voluntary Square-Off Mode, Only IOC ORDER  allowed ";
                break;
            case "1501":
                message = "Error Code : 1501 Request rejected, Participant in Voluntary Square-Off Mode, Only IOC ORDER  allowed";
                break;
            case "1502":
                message = "Error Code : 1502 TM Invalid";
                break;
            case "1503":
                message = "Error Code : 1503 Add UCC failed (possible: already added.) ";
                break;
            case "1504":
                message = "Error Code : 1504 Add UCC failed (possible: already removed.) ";
                break;
            case "1505":
                message = "Error Code : 1505 Invalid operation for UCC ";
                break;
            case "1506":
                message = "Error Code : 1506 Client not registered ";
                break;
            case "1507":
                message = "Error Code : 1507 Update scrip basket failed. ";
                break;
            case "8022":
                message = "Error Code : 8022 Cancelled as TM - Client is not registered. ";
                break;
            case "8019":
                message = "Error Code : 8019 Cancelled as TM - Client is debarred' ";
                break;
            case "1508":
                message = "Error Code : 1508 Spread Product order not allowed as User in Voluntary Square-Off Mode ";
                break;
            case "1509":
                message = "Error Code : 1509 Spread Product order not allowed as TM in Voluntary Square-Off Mode ";
                break;
            case "1510":
                message = "Error Code : 1510 Spread Product order not allowed as CM in Voluntary Square-Off Mode ";
                break;
            case "1511":
                message = "Error Code : 1511 Spread Product order not allowed as INST CM in Voluntary Square-Off Mode ";
                break;
            case "1512":
                message = "Error Code : 1512 Spread Product order not allowed as Participant in Voluntary Square-Off Mode ";
                break;
            case "1513":
                message = "Error Code : 1513 Stop Loss Not Allowed For Auction Instrument ";
                break;
            case "1514":
                message = "Error Code : 1514 Stop Loss Not Allowed For Spread instrument ";
                break;
            case "1515":
                message = "Error Code : 1515 Invalid previous closing price ";
                break;
            case "1516":
                message = "Error Code : 1516 Not a normal session contract. ";
                break;
            case "1517":
                message = "Error Code : 1517 Not a normal session contract. ";
                break;
            case "1518":
                message = "Error Code : 1518 Previous Close price update already in process. ";
                break;
            case "1519":
                message = "Error Code : 1519 DAY Order privilege failed, privilege not given for special pre-open product. ";
                break;
            case "8023":
                message = "Error Code : 8023 Trading Halted due to breach of Index Circuit Breaker ";
                break;
            case "8024":
                message = "Error Code : 8024 BATCH CANCELLATION Order Cancelled By the Exchange Due to ICB Halt. ";
                break;
            case "8025":
                message = "Error Code : 8025 The market is currently not open for trading.";
                break;
            case "8026":
                message = "Error Code : 8026 Product not allowed to participate in ?Post ICB Pre Open Session ";
                break;
            case "8027":
                message = "Error Code : 8027 Product not allowed to participate in ?Post ICB Pre Open Session ";
                break;
            case "1520":
                message = "Error Code : 1520 Order Type should be Pre-Open for the product during Special Pre-Open Session ";
                break;
            case "1521":
                message = "Error Code : 1521 Product not allowed to participate in ?Post ICB Pre Open Session? ";
                break;
            case "1522":
                message = "Error Code : 1522 Invalid Order Attributes ";
                break;
            case "8028":
                message = "Error Code : 8028 BATCH CANCELLATION Order Cancelled By the Exchange Due to ICB Halt. ";
                break;
            case "1523":
                message = "Error Code : 1523 Order entry privilege failed, privilege not given for Call Auction product. ";
                break;
            case "1524":
                message = "Error Code : 1524 Market order privilege failed, privilege not given for Call Auction product.";
                break;
            case "1525":
                message = "Error Code : 1525 EOS Order privilege failed, privilege not given for Call Auction product. ";
                break;
            case "1526":
                message = "Error Code : 1526 Only Call Auction order allowed during Call Auction session.";
                break;
            case "1527":
                message = "Error Code : 1527 Normal market participation not allowed for Call Auction product ";
                break;
            case "1528":
                message = "Error Code : 1528 All Call Auction sessions are not closed for Call Auction product. ";
                break;
            case "1529":
                message = "Error Code : 1529 All Call Auction sessions are closed for Call Auction profile. ";
                break;
            case "1530":
                message = "Error Code : 1530 Open Price update only allowed in Call Auction buffer time ";
                break;
            case "1531":
                message = "Error Code : 1531 Open Price update allowed only for products that are not allowed for Call Auction order entry ";
                break;
            case "1532":
                message = "Error Code : 1532 Invalid Call Auction state ";
                break;
            case "1533":
                message = "Error Code : 1533 Index Order Entry not allowed in Call Auction Session ";
                break;
            case "1534":
                message = "Error Code : 1534 Order Type should be Call Auction for the product during Call Auction Session ";
                break;
            case "1535":
                message = "Error Code : 1535 Trade Modification not allowed for Call Auction trades. ";
                break;
            case "1536":
                message = "Error Code : 1536 Call Auction Order allowed only in Call Auction Order Acceptance Session ";
                break;
            case "1537":
                message = "Error Code : 1537  Invalid Disclosed Qty for Call Auction Order ";
                break;
            case "1538":
                message = "Error Code : 1538 Spread Token not allowed for Call Auction ";
                break;
            case "1539":
                message = "Error Code : 1539 Only EOS or Day order allowed for Call Auction order ";
                break;
            case "1540":
                message = "Error Code : 1540 Call Auction order entry allowed only in Call Auction Order Acceptance Session ";
                break;
            case "1541":
                message = "Error Code : 1541 DAY Order privilege failed, privilege not given for Call Auction product ";
                break;
            case "1542":
                message = "Error Code : 1542 Contract is in Square off Basket during Call Auction Session ";
                break;
            case "1543":
                message = "Error Code : 1543 Quantity decrement is not allowed for Call Auction Order ";
                break;
            case "1544":
                message = "Error Code : 1544 Market to Limit not allowed during Call Auction Session ";
                break;
            case "1545":
                message = "Error Code : 1545 Price decrement is not allowed for Buy Order Call Auction Session";
                break;
            case "1546":
                message = "Error Code : 1546 Price increment is not allowed for Sell Order Call Auction Session ";
                break;
            case "1547":
                message = "Error Code : 1547 Omni Net Down Request not allowed during Call Auction Matching ";
                break;
            case "1548":
                message = "Error Code : 1548 Multi Leg Orders not allowed during Call Auction Session ";
                break;
            case "1549":
                message = "Error Code : 1549 Order Cancelation allowed during Call Auction Acceptance";
                break;
            case "1550":
                message = "Error Code : 1550 Call Auction operation not allowed for scrip ";
                break;
            case "1551":
                message = "Error Code : 1551 Ex Dex not allowed during Call Auction Matching ";
                break;
            case "1552":
                message = "Error Code : 1552 User is in Square off during Call Auction ";
                break;
            case "1553":
                message = "Error Code : 1553 TM is in Square off during Call Auction";
                break;
            case "1554":
                message = "Error Code : 1554 CM is in Square off during Call Auction";
                break;
            case "1555":
                message = "Error Code : 1555 Participant is in Square off during Call Auction ";
                break;
            case "1556":
                message = "Error Code : 1556 Trade Mod during Call Auction Matching time Not Allowed ";
                break;
            case "1557":
                message = "Error Code : 1557 Trade Split not allowed during Call Auction Matching ";
                break;
            case "1558":
                message = "Error Code : 1558 INST Accept Reject not allowed during Call Auction Matching";
                break;
            case "1559":
                message = "Error Code : 1559 Trade Resubmission not allowed during Call Auction Matching ";
                break;
            case "1560":
                message = "Error Code : 1560 BBCLI Order not allowed for Call Auction Order ";
                break;
            case "1561":
                message = "Error Code : 1561 Order Privilege Call Auction failed for Exch";
                break;
            case "1562":
                message = "Error Code : 1562 Order Privilege Call Auction failed for participant ";
                break;
            case "1563":
                message = "Error Code : 1563 Order Privilege Call Auction failed for CM ";
                break;
            case "1564":
                message = "Error Code : 1564 Order Privilege Call Auction failed for User ";
                break;
            case "1565":
                message = "Error Code : 1565 Order Privilege Call Auction failed for ITCM ";
                break;
            case "1566":
                message = "Error Code : 1566 Order Privilege Call Auction failed for TM ";
                break;
            case "1569":
                message = "Error Code : 1569 Invalid combination of products selected for ML order ";
                break;
            case "24465":
                message = "Error Code : 24465 Invalid combination of products selected for ML order ! ";
                break;
            case "1570":
                message = "Error Code : 1570 Invalid Message Sequence Number";
                break;
            case "8029":
                message = "Error Code : 8029 Order Cancelled by Exchange [Self Trade Prevention]";
                break;
            case "8030":
                message = "Error Code : 8030 Passive Cancellation by Exchange due to SMPF  ";
                break;
            case "24464":
                message = "Error Code : 24464 Invalid SMPF Order Identifier ! ";
                break;
            case "24466":
                message = "Error Code : 24466 Invalid Message Sequence Number ! ";
                break;
            case "24467":
                message = "Error Code : 24467 Ex/DEx/DI request is not allowed for the entered instrument !";
                break;

            case "1394":
                message = "Error Code : 1394 EOS Order privilege failed, privilege not given for special pre-open product. ";
                break;
            case "1395":
                message = "Error Code : 1395 Only pre-open order allowed during special pre-open session. ";
                break;
            case "1396":
                message = "Error Code : 1396 Normal market participation not allowed for special pre-open product. ";
                break;
            case "1397":
                message = "Error Code : 1397 All special pre-open sessions are not closed for special pre-open product. ";
                break;
            case "1398":
                message = "Error Code : 1398 All special pre-open sessions are closed for special pre-open profile. ";
                break;
            case "1399":
                message = "Error Code : 1399 Open Price update only allowed in special pre-open buffer time";
                break;
            case "1400":
                message = "Error Code : 1400 Invalid Product Profile configuration for Token in Open Price update ";
                break;
            case "1486":
                message = "Error Code : 1486 Order Privileges Failed? Index order not allowed, privilege not given for INST CM, request rejected ";
                break;
            case "1389":
                message = "Error Code : 1389 EOS order not allowed for pre open session. ";
                break;
            case "1390":
                message = "Error Code : 1390 Id Reserved for Special Entity ";
                break;
            case "8016":
                message = "Error Code : 8016 Product not allowed to participate in Normal Session. ";
                break;
            case "8017":
                message = "Error Code : 8017 Product not allowed to participate in Normal Session. ";
                break;
            case "1391":
                message = "Error Code : 1391 RMS Violation Sequence Number Mismatch! Message Will not be processed ";
                break;
            case "1392":
                message = "Error Code : 1392 Order entry privilege failed, privilege not given for special pre-open product. ";
                break;
            case "1393":
                message = "Error Code : 1393 Market order privilege failed, privilege not given for special pre-open product.";
                break;
            case "1401":
                message = "Error Code : 1401 Open Price update allowed only for products that are not allowed for special pre-open order entry ";
                break;
            case "1402":
                message = "Error Code : 1402 Invalid special pre-open state ";
                break;
            case "1403":
                message = "Error Code : 1403 Invalid debarred client request";
                break;
            case "1405":
                message = "Error Code : 1405 Invalid SLBM Buy/Sell combination. ";
                break;
            case "1407":
                message = "Error Code : 1407 Lend/Borrow order not allowed for product. ";
                break;
            case "1408":
                message = "Error Code : 1408 Repay order not allowed for product. ";
                break;
            case "1409":
                message = "Error Code : 1409 Recall order not allowed for product. ";
                break;
            case "1410":
                message = "Error Code : 1410 Invalid SLBM Client Id. ";
                break;
            case "1411":
                message = "Error Code : 1411 Invalid SLBM order identifier. ";
                break;
            case "1412":
                message = "Error Code : 1412 SLBM Client already exists. ";
                break;
            case "1413":
                message = "Error Code : 1413 Invalid Client Id for SLBM order. ";
                break;
            case "1414":
                message = "Error Code : 1414 Client inactive. ";
                break;
            case "1415":
                message = "Error Code : 1415 System not available for Recall/Repay operation. ";
                break;
            case "1416":
                message = "Error Code : 1416 Member in Square Off state. ";
                break;
            case "1417":
                message = "Error Code : 1417 Open position does not exist.";
                break;
            case "1418":
                message = "Error Code : 1418 Early Pay-In positions does not exists. ";
                break;
            case "1404":
                message = "Error Code : 1404 Rejected as TM - Client is debarred' ";
                break;
            case "1419":
                message = "Error Code : 1419 Trade Modification/Split not allowed for Repay/Recall Trades ";
                break;
            case "1406":
                message = "Error Code : 1406 Only Regular Lot/Stop loss orders allowed for SLBM product. ";
                break;
            case "1420":
                message = "Error Code : 1420 Specified User has no Privileges for Auction Enquiry ";
                break;
            case "1421":
                message = "Error Code : 1421 User is in square-off status during pre-open ";
                break;
            case "1422":
                message = "Error Code : 1422 Trading member is in square-off status during pre-open ";
                break;
            case "1423":
                message = "Error Code : 1423 Clearing member is in square-off status during pre-open";
                break;
            case "1424":
                message = "Error Code : 1424 Inst is in square-off status during pre-open ";
                break;
            case "1426":
                message = "Error Code : 1426 Margin limit exceeded,Rejected By RMS ";
                break;
            case "1430":
                message = "Error Code : 1430 Invalid Voluntary Square Off Parameter ";
                break;
            case "1431":
                message = "Error Code : 1431 Cannot save Market Wide Open Interest ";
                break;
            case "1432":
                message = "Error Code : 1432 Invalid ELM Lower Limit for Voluntary Square-Off Parameters ";
                break;
            case "1433":
                message = "Error Code : 1433 Invalid ELM Upper Limit for Voluntary Square-Off Parameters ";
                break;
            case "1434":
                message = "Error Code : 1434 Invalid Margin Lower Limit for Voluntary Square-Off Parameters";
                break;
            case "1435":
                message = "Error Code : 1435 Invalid Margin Upper Limit for Voluntary Square-Off Parameters";
                break;
            case "1436":
                message = "Error Code : 1436 Request rejected, User in Voluntary Square-Off Mode ";
                break;
            case "1437":
                message = "Error Code : 1437 Request rejected, TM in Voluntary Square-Off Mode ";
                break;
            case "1438":
                message = "Error Code : 1438 Request rejected, CM in Voluntary Square-Off Mode ";
                break;
            case "1439":
                message = "Error Code : 1439 Request rejected, INST CM in Voluntary Square-Off Mode ";
                break;
            case "1440":
                message = "Error Code : 1440 Request rejected, Participant in Voluntary Square-Off Mode ";
                break;
            case "1441":
                message = "Error Code : 1441 Trade Modification to Inst not allowed, CM in Voluntary Squareoff Mode ";
                break;
            case "1442":
                message = "Error Code : 1442 Trade Modification to Inst not allowed, TM in Voluntary Squareoff Mode ";
                break;
            case "1443":
                message = "Error Code : 1443 Trade Split to Inst not allowed, CM in Voluntary Squareoff Mode ";
                break;
            case "1444":
                message = "Error Code : 1444 Trade Split to Inst not allowed, TM in Voluntary Squareoff Mode ";
                break;
//				case "1283"  : message ="Error Code : 1283 Pre open session order not allowed for the product. " ;break;
            case "1425":
                message = "Error Code : 1425 Product is in square-off basket during pre-open ";
                break;
//				case "8012"  : message ="Error Code : 8012 Product does not exists " ;break;
            case "8018":
                message = "Error Code : 8018 Order Price out of Modified DPR ";
                break;
            case "1445":
                message = "Error Code : 1445 DQ order not allowed in PostClose Session";
                break;
            case "8020":
                message = "Error Code : 8020 Products not traded in Other sessions are not allowed to participate in Post Close session ";
                break;
//				case "1313"  : message ="Error Code : 1313 Disclosed Quantity not allowed for pre-open order" ;break;
            case "1449":
                message = "Error Code : 1449 Pending Orders value exceeded";
                break;
            case "1450":
                message = "Error Code : 1450 Invalid Pending Orders Limit  ";
                break;
            case "1451":
                message = "Error Code : 1451 Pending Orders value Per Slot exceeded ";
                break;
            case "1452":
                message = "Error Code : 1452 Invalid Pending Orders Limit Per Slot ";
                break;
            case "1453":
                message = "Error Code : 1453 Invalid Strategy Id ";
                break;
            case "1454":
                message = "Error Code : 1454 Invalid market protection percentage";
                break;
            case "1455":
                message = "Error Code : 1455 Spot buy daily limit exceeded for user ";
                break;
            case "1456":
                message = "Error Code : 1456 Spot sell daily limit exceeded for user";
                break;
            case "1457":
                message = "Error Code : 1457 Future buy daily limit exceeded for user";
                break;
            case "1458":
                message = "Error Code : 1458 Future sell daily limit exceeded for user ";
                break;
            case "1459":
                message = "Error Code : 1459 Auction buy daily limit exceeded for user ";
                break;
            case "1460":
                message = "Error Code : 1460 Auction sell daily limit exceeded for user ";
                break;
            case "1461":
                message = "Error Code : 1461 Option buy daily limit exceeded for user ";
                break;
            case "1462":
                message = "Error Code : 1462 Option sell daily limit exceeded for user ";
                break;
            case "17019":
                message = "Error Code : 17019 System not available ";
                break;
            case "1463":
                message = "Error Code : 1463 Multi-mat Privilege of Index Order Entry is not given to MAT ";
                break;
            case "1464":
                message = "Error Code : 1464 Entity is either in Suspended Sq-off or Sq-off ";
                break;
            case "1465":
                message = "Error Code : 1465 Only Reg Lot allowed for Index Order Entry ";
                break;
            case "1466":
                message = "Error Code : 1466 Invalid Order Terms Traded for Index Order Entry ";
                break;
            case "1467":
                message = "Error Code : 1467 Invalid Order Terms Modified for Index Order Entry ";
                break;
            case "1468":
                message = "Error Code : 1468 Invalid Order Terms FOK for Index Order Entry ";
                break;
            case "1469":
                message = "Error Code : 1469 Invalid Order Terms SL for Index Order Entry ";
                break;
            case "1470":
                message = "Error Code : 1470 Only Market Order Allowed for Index Order Entry ";
                break;
            case "1471":
                message = "Error Code : 1471 Invalid IOC combination for Index Order Entry ";
                break;
            case "1472":
                message = "Error Code : 1472 Invalid DAY combination for Index Order Entry ";
                break;
            case "1473":
                message = "Error Code : 1473 Invalid EOS combination for Index Order Entry";
                break;
            case "1474":
                message = "Error Code : 1474 Account code update for Index Order Entry ";
                break;
            case "1475":
                message = "Error Code : 1475 Index Order Entry not allowed in Pre-Open Session ";
                break;
            case "1476":
                message = "Error Code : 1476 Index Order Entry not allowed in Special Pre-Open Session ";
                break;
            case "1477":
                message = "Error Code : 1477 Dealer is not active for Index Order Entry ";
                break;
            case "1478":
                message = "Error Code : 1478 Index Order Entry not allowed in Post Close Session ";
                break;
            case "1479":
                message = "Error Code : 1479 Index value is below minimum allowed value for Index Scrip ";
                break;
            case "1480":
                message = "Error Code : 1480 Index value is beyond maximum allowed value for Index Scrip ";
                break;
            case "1481":
                message = "Error Code : 1481 Validity Privileges Failed? Index order not allowed, privilege not given for Any user, request rejected ";
                break;
            case "19500":
                message = "Error Code : 19500 Instrument is in halt session ";
                break;

            default: {
                message = "Unknown Error Code ";
                break;
            }
        }
        return message;
    }

    private String getMarketErrorMessage3(String errorcode) {
        String message = "";
        switch (errorcode) {
            case "   -1":
                message = "Error Code :    -1  Reason :Exchange Downloading in Progess. Order Entry Not Allowded";
                break;
            case "   -5":
                message = "Error Code :    -5  Reason :Invalid NNF field";
                break;
            case "  293":
                message = "Error Code :   293  Reason :Invalid instrument type.";
                break;
            case "  509":
                message = "Error Code :   509  Reason :Order does not exist.";
                break;
            case " 8049":
                message = "Error Code :  8049  Reason :Initiator is not allowed to cancel auction order.";
                break;
            case " 8485":
                message = "Error Code :  8485  Reason :Auction number does not exist";
                break;
            case "16000":
                message = "Error Code : 16000  Reason :The trading system is not available for trading.";
                break;
            case "16001":
                message = "Error Code : 16001  Reason :Header user ID is not equal to user ID in the order packet.";
                break;
            case "16003":
                message = "Error Code : 16003  Reason :System error was encountered. Please call the Exchange.";
                break;
            case "16004":
                message = "Error Code : 16004  Reason :The user is already signed on.";
                break;
            case "16005":
                message = "Error Code : 16005  Reason :System error while trying to sign-off. Please call the Exchange.";
                break;
            case "16006":
                message = "Error Code : 16006  Reason :Invalid sign-on. Please try again.";
                break;
            case "16007":
                message = "Error Code : 16007  Reason :Signing onto the trading system is restricted.  Please try later on.";
                break;
            case "16011":
                message = "Error Code : 16011  Reason :This report has already been requested.";
                break;
            case "16012":
                message = "Error Code : 16012  Reason :Invalid contract descriptor";
                break;
            case "16014":
                message = "Error Code : 16014  Reason :This order is not yours.";
                break;
            case "16015":
                message = "Error Code : 16015  Reason :This trade is not yours.";
                break;

            case "16016":
                message = "Error Code : 16016  Reason :Invalid trade number.";
                break;
            case "16019":
                message = "Error Code : 16019  Reason :Stock not found.";
                break;
            case "16035":
                message = "Error Code : 16035  Reason :Security is unavailable for trading at this time. Please try later.";
                break;
            case "16041":
                message = "Error Code : 16041  Reason :Trading member does not exist in the system.";
                break;
            case "16042":
                message = "Error Code : 16042  Reason :Dealer does not exist in the system.";
                break;
            case "16043":
                message = "Error Code : 16043  Reason :This record already exist on the NEAT system.";
                break;
            case "16044":
                message = "Error Code : 16044  Reason :Order has been modified. Please try again.";
                break;
            case "16049":
                message = "Error Code : 16049  Reason :Stock is suspended.";
                break;
            case "16053":
                message = "Error Code : 16053  Reason :Your password has expired, must be changed.";
                break;
            case "16054":
                message = "Error Code : 16054  Reason :Invalid branch for trading member.";
                break;
            case "16056":
                message = "Error Code : 16056  Reason :Program error.";
                break;
            case "16086":
                message = "Error Code : 16086  Reason :Duplicate trade cancel request.";
                break;
            case "16098":
                message = "Error Code : 16098  Reason :Invalid trader ID for buyer.";
                break;
            case "16099":
                message = "Error Code : 16099  Reason :Invalid trader ID for seller.";
                break;//Modified
            case "16100":
                message = "Error Code : 16100  Reason :Your system version has not been updated.";
                break;
            case "16104":
                message = "Error Code : 16104  Reason :System could not complete your transaction - Admin notified.";
                break;
            case "16148":
                message = "Error Code : 16148  Reason :Invalid Dealer ID entered.";
                break;
            case "16154":
                message = "Error Code : 16154  Reason :Invalid Trader ID entered.";
                break;
            case "16169":
                message = "Error Code : 16169  Reason :Order priced ATO cannot be entered when a security is open.";
                break;
            case "16198":
                message = "Error Code : 16198  Reason :Duplicate modification or cancellation request for the same trade has been encountered.";
                break;
            case "16227":
                message = "Error Code : 16227  Reason :Only market orders are allowed in post close.";
                break;
            case "16228":
                message = "Error Code : 16228  Reason :SL, MIT or NT orders are not allowed during Post Close.";
                break;
            case "16229":
                message = "Error Code : 16229  Reason :GTC GTD or GTDays orders are Not Allowed during Post Close.";
                break;
            case "16230":
                message = "Error Code : 16230  Reason :Continuous session orders cannot be modified.";
                break;
            case "16231":
                message = "Error Code : 16231  Reason :Continuous session trades cannot be changed.";
                break;
            case "16233":
                message = "Error Code : 16233  Reason :Proprietary requests cannot be made for participant.";
                break;
            case "16251":
                message = "Error Code : 16251  Reason :Trade modification with different quantities is received.";
                break;
            case "16273":
                message = "Error Code : 16273  Reason :Record does not exist.";
                break;
            case "16278":
                message = "Error Code : 16278  Reason :The markets have not been opened for trading.";
                break;
            case "16279":
                message = "Error Code : 16279  Reason :The contract has not yet been admitted for trading.";
                break;
            case "16280":
                message = "Error Code : 16280  Reason :The Contract has matured.";
                break;
            case "16281":
                message = "Error Code : 16281  Reason :The security has been expelled.";
                break;
            case "16282":
                message = "Error Code : 16282  Reason :The order quantity is greater than the issued capital.";
                break;
            case "16283":
                message = "Error Code : 16283  Reason :The order price is not multiple of the tick size.";
                break;
            case "16284":
                message = "Error Code : 16284  Reason :The order price is out of the day's price range.";
                break;
            case "16285":
                message = "Error Code : 16285  Reason :The broker is not active.";
                break;
            case "16300":
                message = "Error Code : 16300  Reason :System is in a wrong state to make the requested change.";
                break;
            case "16303":
                message = "Error Code : 16303  Reason :The auction is pending.";
                break;
            case "16307":
                message = "Error Code : 16307  Reason :The order has been canceled due to quantity freeze.";
                break;
            case "16308":
                message = "Error Code : 16308  Reason :The order has been canceled due to price freeze.";
                break;
            case "16311":
                message = "Error Code : 16311  Reason :The Solicitor period for the Auction is over.";
                break;
            case "16312":
                message = "Error Code : 16312  Reason :The Competitor period for the Auction is over.";
                break;
            case "16313":
                message = "Error Code : 16313  Reason :The Auction period will cross market close time.";
                break;
            case "16315":
                message = "Error Code : 16315  Reason :The limit price is worse than the trigger price.";
                break;
            case "16316":
                message = "Error Code : 16316  Reason :The trigger price is not a multiple of tick size.";
                break;
            case "16317":
                message = "Error Code : 16317  Reason :AON attribute not allowed.";
                break;
            case "16318":
                message = "Error Code : 16318  Reason :MF attribute not allowed.";
                break;
            case "16319":
                message = "Error Code : 16319  Reason :AON attribute not allowed at Security level.";
                break;
            case "16320":
                message = "Error Code : 16320  Reason :MF attribute not allowed at security level.";
                break;
            case "16321":
                message = "Error Code : 16321  Reason :MF quantity is greater than disclosed quantity";
                break;
            case "16322":
                message = "Error Code : 16322  Reason :MF quantity is not a multiple of regular lot.";
                break;
            case "16323":
                message = "Error Code : 16323  Reason :MF quantity is greater than Original quantity.";
                break;
            case "16324":
                message = "Error Code : 16324  Reason :Disclosed quantity is greater than Original quantity.";
                break;
            case "16325":
                message = "Error Code : 16325  Reason :Disclosed quantity is not a multiple of regular lot.";
                break;
            case "16326":
                message = "Error Code : 16326  Reason :GTD is greater than that specified at the trading system.";
                break;
            case "16327":
                message = "Error Code : 16327  Reason :Odd lot quantity cannot be greater than or equal to regular lot size.";
                break;
            case "16328":
                message = "Error Code : 16328  Reason :Quantity is not a multiple of regular lot.";
                break;
            case "16329":
                message = "Error Code : 16329  Reason :Trading Member not permitted in the market.";
                break;
            case "16330":
                message = "Error Code : 16330  Reason :Security is suspended.";
                break;
            case "16333":
                message = "Error Code : 16333  Reason :Branch Order Value Limit has been exceeded.";
                break;
            case "16343":
                message = "Error Code : 16343  Reason :The order to be cancelled has changed.";
                break;
            case "16344":
                message = "Error Code : 16344  Reason :The order cannot be cancelled.";
                break;
            case "16345":
                message = "Error Code : 16345  Reason :Initiator order cannot be cancelled.";
                break;
            case "16346":
                message = "Error Code : 16346  Reason :Order cannot be modified.";
                break;
            case "16348":
                message = "Error Code : 16348  Reason :Trading is not allowed in this market.";
                break;
            case "16357":
                message = "Error Code : 16357  Reason :Control has rejected the Negotiated Trade.";
                break;
            case "16363":
                message = "Error Code : 16363  Reason :Status is in the required state.";
                break;
            case "16369":
                message = "Error Code : 16369  Reason :Contract is in preopen.";
                break;
            case "16372":
                message = "Error Code : 16372  Reason :Order entry not allowed for user as it is of inquiry type.";
                break;
            case "16387":
                message = "Error Code : 16387  Reason: Contract not allowed to trader in.";
                break;
            case "16392":
                message = "Error Code : 16392  Reason :Turnover limit not provided. Please contact Exchange.";
                break;
            case "16400":
                message = "Error Code : 16400  Reason :DQ is less than minimum quantity allowed.";
                break;
            case "16404":
                message = "Error Code : 16404  Reason :Order has been cancelled due to freeze admin suspension.";
                break;
            case "16405":
                message = "Error Code : 16405  Reason :BUY - SELL type entered is invalid.";
                break;
            case "16414":
                message = "Error Code : 16414  Pro Cli field is invalid (something other than Pro - 2 or Cli - 1)";
                break;
            case "16415":
                message = "Error Code : 16415  Reason :Invalid combination of book type and instructions (order_type).";
                break;
            case "16416":
                message = "Error Code : 16416  Reason :Invalid combination of mf/aon/disclosed volume.";
                break;
            case "16419":
                message = "Error Code : 16419  This error code will be message =ed for invalid data in the order packet[Cross Check Limits].";
                break;
            case "16440":
                message = "Error Code : 16440  Reason :GTD is greater than Maturity date.";
                break;
            case "16441":
                message = "Error Code : 16441  Reason :DQ orders are not allowed in preopen.";
                break;
            case "16442":
                message = "Error Code : 16442  Reason :ST orders are not allowed in preopen.";
                break;
            case "16443":
                message = "Error Code : 16443  Reason :Order value exceeds the order limit value.";
                break;
            case "16445":
                message = "Error Code : 16445  Reason :Stop Loss orders are not allowed.";
                break;
            case "16446":
                message = "Error Code : 16446  Reason :Market If Touched orders are not allowed.";
                break;
            case "16447":
                message = "Error Code : 16447  Reason :Order entry not allowed in Pre-open.";
                break;
            case "16500":
                message = "Error Code : 16500  Reason :Ex/Pl not allowed.";
                break;
            case "16501":
                message = "Error Code : 16501  Reason :Invalid ExPl flag value.";
                break;
            case "16513":
                message = "Error Code : 16513  Reason :Ex/Pl rejection not allowed.";
                break;
            case "16514":
                message = "Error Code : 16514  Reason :Not modifiable.";
                break;
            case "16518":
                message = "Error Code : 16518  Reason :Clearing member, Trading Member link not found.";
                break;
            case "16521":
                message = "Error Code : 16521  Reason :Not a clearing member.";
                break;
            case "16523":
                message = "Error Code : 16523  Reason :User in not a corporate manager.";
                break;
            case "16529":
                message = "Error Code : 16529  Not a valid derivative contract.";
                break;
            case "16532":
                message = "Error Code : 16532  Reason :Clearing member Participant link not found.";
                break;
            case "16533":
                message = "Error Code : 16533  Reason :Enter either TM or Participant.";
                break;
            case "16541":
                message = "Error Code : 16541  Reason :Participant is invalid.";
                break;
            case "16550":
                message = "Error Code : 16550  Reason :Trade cannot be modified /cancelled. It has already been approved by CM.";
                break;
            case "16552":
                message = "Error Code : 16552  Reason :Stock has been suspended.";
                break;
            case "16554":
                message = "Error Code : 16554  Reason :Trading Member not permitted in Futures.";
                break;
            case "16555":
                message = "Error Code : 16555  Reason :Trading Member not permitted in Options.";
                break;
            case "16556":
                message = "Error Code : 16556  Reason :Quantity less than the minimum lot size.";
                break;
            case "16557":
                message = "Error Code : 16557  Reason :Disclose quantity less than the minimum lot size.";
                break;
            case "16558":
                message = "Error Code : 16558  Reason :Minimum fill is less than the minimum lot size.";
                break;
            case "16560":
                message = "Error Code : 16560  Reason :The give up trade has already been rejected.";
                break;
            case "16561":
                message = "Error Code : 16561  Reason :Negotiated Orders not allowed.";
                break;
            case "16562":
                message = "Error Code : 16562  Reason :Negotiated Trade not allowed.";
                break;
            case "16566":
                message = "Error Code : 16566  Reason :User does not belong to Broker or Branch.";
                break;
            case "16570":
                message = "Error Code : 16570  Reason :The market is in post-close.";
                break;
            case "16571":
                message = "Error Code : 16571  Reason :The Closing Session has ended.";
                break;
            case "16572":
                message = "Error Code : 16572  Reason :Closing Session trades have been generated.";
                break;
            case "16573":
                message = "Error Code : 16573  Reason :Message length is invalid.";
                break;
            case "16574":
                message = "Error Code : 16574  Reason :Open - Close type entered is invalid.";
                break;
            case "16576":
                message = "Error Code : 16576  Reason :No. of nnf inquiry requests exceeded.";
                break;
            case "16577":
                message = "Error Code : 16577  Reason :Both participant and volume changed.";
                break;
            case "16578":
                message = "Error Code : 16578  Reason :Cover - Uncover type entered is invalid.";
                break;
            case "16579":
                message = "Error Code : 16579  Reason :Giveup requested for wrong CM ID.";
                break;
            case "16580":
                message = "Error Code : 16580  Reason :Order does not belong to the given participant.";
                break;
            case "16581":
                message = "Error Code : 16581  Reason :Invalid trade price.";
                break;
            case "16583":
                message = "Error Code : 16583  Reason :For Pro order participant entry not allowed.";
                break;
            case "16585":
                message = "Error Code : 16585  Reason :Not a valid account number.";
                break;
            case "16586":
                message = "Error Code : 16586  Reason :Participant Order Entry Not Allowed.";
                break;
            case "16589":
                message = "Error Code : 16589  Reason :All continuous session orders are being deleted now.";
                break;
            case "16594":
                message = "Error Code : 16594  Reason :After giveup approve/reject, trade quantity cannot be modified.";
                break;
            case "16596":
                message = "Error Code : 16596  Reason :Trading member cannot put Ex/Pl request for Participant. ";
                break;
            case "16597":
                message = "Error Code : 16597  Reason :Branch limit should be greater than sum of user limits.";
                break;
            case "16598":
                message = "Error Code : 16598  Reason :Branch limit should be greater than used limit.";
                break;
            case "16602":
                message = "Error Code : 16602  Reason :Dealer value limit exceeds the set limit.";
                break;
            case "16604":
                message = "Error Code : 16604  Reason :Participant not found.";
                break;
            case "16605":
                message = "Error Code : 16605  Reason :One leg of spread/2L failed.";
                break;
            case "16606":
                message = "Error Code : 16606  Reason :Quantity greater than Freeze quantity.";
                break;
            case "16607":
                message = "Error Code : 16607  Reason :Spread not allowed.";
                break;
            case "16608":
                message = "Error Code : 16608  Reason :Spread allowed only when market is open.";
                break;
            case "16609":
                message = "Error Code : 16609  Reason :Spread allowed only when stock is open.";
                break;
            case "16610":
                message = "Error Code : 16610  Reason :Both legs should have same quantity.";
                break;
            case "16611":
                message = "Error Code : 16611  Reason :Modified order qty freeze not allowed.";
                break;
            case "16612":
                message = "Error Code : 16612  Reason :The trade record has been modified.";
                break;
            case "16615":
                message = "Error Code : 16615  Reason :Order cannot be modified.";
                break;
            case "16616":
                message = "Error Code : 16616  Reason :Order can not be cancelled.";
                break;
            case "16617":
                message = "Error Code : 16617  Reason :Trade can not be manipulated.";
                break;
            case "16619":
                message = "Error Code : 16619  Reason :PCM can not ex_pl for himself.";
                break;
            case "16620":
                message = "Error Code : 16620  Reason :Ex/Pl by clearing member for TM not allowed.";
                break;
            case "16621":
                message = "Error Code : 16621  Reason :Clearing member cannot change the Ex/Pl requests placed by Trading Member.";
                break;
            case "16625":
                message = "Error Code : 16625  Reason :Clearing member is suspended.";
                break;
            case "16626":
                message = "Error Code : 16626  Reason :Expiry date not in ascending order.";
                break;
            case "16627":
                message = "Error Code : 16627  Reason :Invalid contract combination.";
                break;
            case "16628":
                message = "Error Code : 16628  Reason :Branch manager cannot cancel corporate manager's order.";
                break;
            case "16629":
                message = "Error Code : 16629  Reason :Branch manager cannot cancel other branch manager's order.";
                break;
            case "16630":
                message = "Error Code : 16630  Reason :Corporate manager cannot cancel other corporate manager's order.";
                break;
            case "16631":
                message = "Error Code : 16631  Reason :Spread not allowed for different underlying.";
                break;
            case "16632":
                message = "Error Code : 16632  Reason :Cli A/c number cannot be modified as trading member ID.";
                break;
            case "16636":
                message = "Error Code : 16636  Reason :Futures buy branch Order Value Limit has been exceeded.";
                break;
            case "16637":
                message = "Error Code : 16637  Reason :Futures sell branch Order Value Limit has been exceeded.";
                break;
            case "16638":
                message = "Error Code : 16638  Reason :Options buy branch Order Value Limit has been exceeded.";
                break;
            case "16639":
                message = "Error Code : 16639  Reason :Options sell branch Order Value Limit has been exceeded.";
                break;
            case "16640":
                message = "Error Code : 16640  Reason :Futures buy used limit execeeded the user limit.";
                break;
            case "16641":
                message = "Error Code : 16641  Reason :Futures sell used limit execeeded the user limit.";
                break;
            case "16642":
                message = "Error Code : 16642  Reason :Options buy used limit execeeded the user limit.";
                break;
            case "16643":
                message = "Error Code : 16643  Reason :Options sell used limit execeeded the user limit.";
                break;
            case "16645":
                message = "Error Code : 16645  Reason :Cannot approve. Bhav Copy generated.";
                break;
            case "16646":
                message = "Error Code : 16646  Reason :Cannot modify.";
                break;
            case "16656":
                message = "Error Code : 16656  Reason :No address in the database.";
                break;
            case "16662":
                message = "Error Code : 16662  Reason :Contract is opening. Please wait for the contract to open.";
                break;
            case "16666":
                message = "Error Code : 16666  Reason :Invalid NNF field.";
                break;
            case "16667":
                message = "Error Code : 16667  Reason :GTC GTD Orders not allowed.";
                break;
            case "16686":
                message = "Error Code : 16686  Reason :This error code will be message =ed if Close out order rejected by the system.";
                break;
            case "16687":
                message = "Error Code : 16687  Reason :This error code will be message =ed if the close out order entered is going into freeze. (Since Freeze is not allowed for close out orders)";
                break;
            case "16688":
                message = "Error Code : 16688  Reason :This error code will be message =ed if the close out order is not allowed in the system.";
                break;
            case "16690":
                message = "Error Code : 16690  Reason :This error code will be message =ed when a Trade MOD request is placed by a broker in Close-out.";
                break;
            case "16706":
                message = "Error Code : 16706  Reason :Cancelled by the System";
                break;
            case "16708":
                message = "Error Code : 16708  Reason :System Error. Orders not completely cancelled by the system. Please request Quick CXL again";
                break;
            case "16713":
                message = "Error Code : 16713  Reason :Spread order price difference is out of range.";
                break;
            case "16807":
                message = "Error Code : 16807  Reason :The account is debarred from trading.";
                break;
            case "17036":
                message = "Error Code : 17036  Reason :MBA inquiry is not allowed for this contract";
                break;
            case "17037":
                message = "Error Code : 17037  Reason :insufficient record for MBA inquiry";
                break;
            case "17038":
                message = "Error Code : 17038  Reason :Order is outstanding.";
                break;
            case "17039":
                message = "Error Code : 17039  Reason :Pro Cli Modification not allowed for the Order.";
                break;
            case "17045":
                message = "Error Code : 17045  Reason :Order Quantity Exceeds Quantity Value Limit for User.";
                break;
            case "17046":
                message = "Error Code : 17046  Reason :Trade Modification Not Allowed for User Type.";
                break;
            case "17047":
                message = "Error Code : 17047  Reason :Trade Modification not Allowed for Broker.";
                break;
            case "17048":
                message = "Error Code : 17048  Reason :Trade Modification Not Allowed for User.";
                break;
            case "17070":
                message = "Error Code : 17070  The Price is out of the current execution range.";
                break;
            case "17071":
                message = "Error Code : 17071  Order cancelled by Exchange [Self Trade Prevention].";
                break;
            case "16793":
                message = "Error Code : 16793  Voluntary Close-out Order - Order Entered has invalid data.";
                break;
            case "16794":
                message = "Error Code : 16794  Security Specific disablement - Order Entered has invalid data.";
                break;
            case "16795":
                message = "Error Code : 16795  Order cancelled mdue to voluntary close out.";
                break;
            case "16796":
                message = "Error Code : 16796  Order cancelled due to OI violation.";
                break;
            case "10001":
                message = "Error Code : 10001  The Price is out of the current execution range.";
                break;

            case "16388":
                message = "Error Code :16388 Reason: When Preopen unmatched orders are cancelled by the system after preopen session ends";
                break;

            default: {
                message = "Unknown Error Code";
                break;
            }
        }

        return message;
    }

    private String getMarketErrorMessage2(String errorcode) {
        String message = "";

        switch (errorcode) {
            case "-1":
                message = "Error Code :    -1  Reason :Exchange Downloading in Progess. Order Entry Not Allowded";
                break;//Not Found in Exchange Doc.
            case "293":
                message = "Error Code :   293  Reason :Invalid instrument type.";
                break;
            case "509":
                message = "Error Code :   509  Reason :Order does not exist.";
                break;
            case "8049":
                message = "Error Code :  8049  Reason :Initiator is not allowed to cancel auction order.";
                break;
            case "8485":
                message = "Error Code :  8485  Reason :Auction number does not exist";
                break;
            case "16000":
                message = "Error Code : 16000  Reason :The trading system is not available for trading.";
                break;
            case "16001":
                message = "Error Code : 16001  Reason :Header user ID is not equal to user ID in the order packet.";
                break;
            case "16003":
                message = "Error Code : 16003  Reason :System error was encountered. Please call the Exchange.";
                break;
            case "16004":
                message = "Error Code : 16004  Reason :The user is already signed on.";
                break;
            case "16005":
                message = "Error Code : 16005  Reason :System error while trying to sign-off. Please call the Exchange.";
                break;
            case "16006":
                message = "Error Code : 16006  Reason :Invalid sign-on. Please try again.";
                break;
            case "16007":
                message = "Error Code : 16007  Reason :Signing onto the trading system is restricted.  Please try later on.";
                break;
            case "16011":
                message = "Error Code : 16011  Reason :This report has already been requested.";
                break; //Not Found in Exchange Doc.
            case "16012":
                message = "Error Code : 16012  Reason :Invalid contract descriptor";
                break;
            case "16014":
                message = "Error Code : 16014  Reason :This order is not yours.";
                break;
            case "16015":
                message = "Error Code : 16015  Reason :This trade is not yours.";
                break;
            case "16016":
                message = "Error Code : 16016  Reason :Invalid trade number.";
                break;
            case "16019":
                message = "Error Code : 16019  Reason :Stock not found.";
                break;
            case "16035":
                message = "Error Code : 16035  Reason :Security is unavailable for trading at this time. Please try later.";
                break;
            case "16041":
                message = "Error Code : 16041  Reason :Trading member does not exist in the system.";
                break;
            case "16042":
                message = "Error Code : 16042  Reason :Dealer does not exist in the system.";
                break;
            case "16043":
                message = "Error Code : 16043  Reason :This record already exist on the NEAT system.";
                break;
            case "16044":
                message = "Error Code : 16044  Reason :Order has been modified. Please try again.";
                break;
            case "16049":
                message = "Error Code : 16049  Reason :Stock is suspended.";
                break;
            case "16053":
                message = "Error Code : 16053  Reason :Your password has expired, must be changed.";
                break;
            case "16054":
                message = "Error Code : 16054  Reason :Invalid branch for trading member.";
                break;
            case "16056":
                message = "Error Code : 16056  Reason :Program error.";
                break;
            case "16086":
                message = "Error Code : 16086  Reason :Duplicate trade cancel request.";
                break;
            case "16098":
                message = "Error Code : 16098  Reason :Invalid trader ID for buyer.";
                break;
            case "16099":
                message = "Error Code : 16099  Reason :Invalid trader ID for seller.";
                break; //Corrected
            case "16100":
                message = "Error Code : 16100  Reason :Your system version has not been updated.";
                break;
            case "16104":
                message = "Error Code : 16104  Reason :System could not complete your transaction - Admin notified.";
                break;
            case "16148":
                message = "Error Code : 16148  Reason :Invalid Dealer ID entered.";
                break;
            case "16154":
                message = "Error Code : 16154  Reason :Invalid Trader ID entered.";
                break;
            case "16169":
                message = "Error Code : 16169  Reason :Order priced ATO cannot be entered when a security is open.";
                break;
            case "16198":
                message = "Error Code : 16198  Reason :Duplicate modification or cancellation request for the same trade has been encountered.";
                break;
            case "16227":
                message = "Error Code : 16227  Reason :Only market orders are allowed in postclose.";
                break;
            case "16228":
                message = "Error Code : 16228  Reason :SL, MIT or NT orders are not allowed during Post Close.";
                break;
            case "16229":
                message = "Error Code : 16229  Reason :GTC GTD or GTDays orders are Not Allowed during Post Close.";
                break;
            case "16230":
                message = "Error Code : 16230  Reason :Continuous session orders cannot be modified.";
                break;
            case "16231":
                message = "Error Code : 16231  Reason :Continuous session trades cannot be changed.";
                break;
            case "16233":
                message = "Error Code : 16233  Reason :Proprietary requests cannot be made for participant.";
                break;
            case "16247":
                message = "Error Code : 16247  Reason :Invalid Price.";
                break;
            case "16251":
                message = "Error Code : 16251  Reason :Trade modification with different quantities is received.";
                break;
            case "16273":
                message = "Error Code : 16273  Reason :Record does not exist.";
                break;
            case "16278":
                message = "Error Code : 16278  Reason :The markets have not been opened for trading.";
                break;
            case "16279":
                message = "Error Code : 16279  Reason :The contract has not yet been admitted for trading.";
                break;
            case "16280":
                message = "Error Code : 16280  Reason :The Contract has matured.";
                break;
            case "16281":
                message = "Error Code : 16281  Reason :The security has been expelled.";
                break;
            case "16282":
                message = "Error Code : 16282  Reason :The order quantity is greater than the issued capital.";
                break;
            case "16283":
                message = "Error Code : 16283  Reason :The order price is not multiple of the tick size.";
                break;
            case "16284":
                message = "Error Code : 16284  Reason :The order price is out of the day's price range.";
                break;
            case "16285":
                message = "Error Code : 16285  Reason :The broker is not active.";
                break;
            case "16300":
                message = "Error Code : 16300  Reason :System is in a wrong state to make the requested change.";
                break;
            case "16303":
                message = "Error Code : 16303  Reason :The auction is pending.";
                break;
            case "16307":
                message = "Error Code : 16307  Reason :The order has been canceled due to quantity freeze.";
                break;
            case "16308":
                message = "Error Code : 16308  Reason :The order has been canceled due to price freeze.";
                break;
            case "16311":
                message = "Error Code : 16311  Reason :The Solicitor period for the Auction is over.";
                break;
            case "16312":
                message = "Error Code : 16312  Reason :The Competitor period for the Auction is over.";
                break;
            case "16313":
                message = "Error Code : 16313  Reason :The Auction period will cross market close time.";
                break;
            case "16315":
                message = "Error Code : 16315  Reason :The limit price is worse than the trigger price.";
                break;
            case "16316":
                message = "Error Code : 16316  Reason :The trigger price is not a multiple of tick size.";
                break;
            case "16317":
                message = "Error Code : 16317  Reason :AON attribute not allowed.";
                break;
            case "16318":
                message = "Error Code : 16318  Reason :MF attribute not allowed.";
                break;
            case "16319":
                message = "Error Code : 16319  Reason :AON attribute not allowed at Security level.";
                break;
            case "16320":
                message = "Error Code : 16320  Reason :MF attribute not allowed at security level.";
                break;
            case "16321":
                message = "Error Code : 16321  Reason :MF quantity is greater than disclosed quantity";
                break;
            case "16322":
                message = "Error Code : 16322  Reason :MF quantity is not a multiple of regular lot.";
                break;
            case "16323":
                message = "Error Code : 16323  Reason :MF quantity is greater than Original quantity.";
                break;
            case "16324":
                message = "Error Code : 16324  Reason :Disclosed quantity is greater than Original quantity.";
                break;
            case "16325":
                message = "Error Code : 16325  Reason :Disclosed quantity is not a multiple of regular lot.";
                break;
            case "16326":
                message = "Error Code : 16326  Reason :GTD is greater than that specified at the trading system.";
                break;
            case "16327":
                message = "Error Code : 16327  Reason :Odd lot quantity cannot be greater than or equal to regular lot size.";
                break;
            case "16328":
                message = "Error Code : 16328  Reason :Quantity is not a multiple of regular lot.";
                break;
            case "16329":
                message = "Error Code : 16329  Reason :Trading Member not permitted in the market.";
                break;
            case "16330":
                message = "Error Code : 16330  Reason :Security is suspended.";
                break;
            case "16333":
                message = "Error Code : 16333  Reason :Branch Order Value Limit has been exceeded.";
                break;
            case "16343":
                message = "Error Code : 16343  Reason :The order to be cancelled has changed.";
                break;
            case "16344":
                message = "Error Code : 16344  Reason :The order cannot be cancelled.";
                break;
            case "16345":
                message = "Error Code : 16345  Reason :Initiator order cannot be cancelled.";
                break;
            case "16346":
                message = "Error Code : 16346  Reason :Order cannot be modified.";
                break;
            case "16348":
                message = "Error Code : 16348  Reason :Trading is not allowed in this market.";
                break;
            case "16357":
                message = "Error Code : 16357  Reason :Control has rejected the Negotiated Trade.";
                break;
            case "16363":
                message = "Error Code : 16363  Reason :Status is in the required state.";
                break;
            case "16369":
                message = "Error Code : 16369  Reason :Contract is in preopen.";
                break;
            case "16372":
                message = "Error Code : 16372  Reason :Order entry not allowed for user as it is of inquiry type.";
                break;
            case "16392":
                message = "Error Code : 16392  Reason :Turnover limit not provided. Please contact Exchange.";
                break;
            case "16400":
                message = "Error Code : 16400  Reason :DQ is less than minimum quantity allowed.";
                break;
            case "16404":
                message = "Error Code : 16404  Reason :Order has been cancelled due to freeze admin suspension.";
                break;
            case "16405":
                message = "Error Code : 16405  Reason :BUY - SELL type entered is invalid.";
                break;
            case "16406":
                message = "Error Code : 16406  Reason :BOOK type entered is invalid.";
                break;
            case "16408":
                message = "Error Code : 16408  Reason :trigger_price entered has invalid characters.";
                break;
            case "16414":
                message = "Error Code : 16414  Reason :Pro/Client should be either 1 (client) or 2 (broker).";
                break;
            case "16415":
                message = "Error Code : 16415  Reason :Invalid combination of book type and instructions (order_type).";
                break;
            case "16416":
                message = "Error Code : 16416  Reason :Invalid combination of mf/aon/disclosed volume.";
                break;
            case "16419":
                message = "Error Code : 16419  Reason :This error code will be message =ed for invalid data in the order packet[Cross Check Limits].";
                break;
            case "16440":
                message = "Error Code : 16440  Reason :GTD is greater than Maturity date.";
                break;
            case "16441":
                message = "Error Code : 16441  Reason :DQ orders are not allowed in preopen.";
                break;
            case "16442":
                message = "Error Code : 16442  Reason :ST orders are not allowed in preopen.";
                break;
            case "16443":
                message = "Error Code : 16443  Reason :Order value exceeds the order limit value.";
                break;
            case "16445":
                message = "Error Code : 16445  Reason :Stop Loss orders are not allowed.";
                break;
            case "16446":
                message = "Error Code : 16446  Reason :Market If Touched orders are not allowed.";
                break;
            case "16447":
                message = "Error Code : 16447  Reason :Order entry not allowed in Pre-open.";
                break;
            case "16500":
                message = "Error Code : 16500  Reason :Ex/Pl not allowed.";
                break;
            case "16501":
                message = "Error Code : 16501  Reason :Invalid ExPl flag value.";
                break;
            case "16513":
                message = "Error Code : 16513  Reason :Ex/Pl rejection not allowed.";
                break;
            case "16514":
                message = "Error Code : 16514  Reason :Not modifiable.";
                break;
            case "16518":
                message = "Error Code : 16518  Reason :Clearing member, Trading Member link not found.";
                break;
            case "16521":
                message = "Error Code : 16521  Reason :Not a clearing member.";
                break;
            case "16523":
                message = "Error Code : 16523  Reason :User in not a corporate manager.";
                break;
            case "16532":
                message = "Error Code : 16532  Reason :Clearing member Participant link not found.";
                break;
            case "16533":
                message = "Error Code : 16533  Reason :Enter either TM or Participant.";
                break;
            case "16541":
                message = "Error Code : 16541  Reason :Participant is invalid.";
                break;
            case "16550":
                message = "Error Code : 16550  Reason :Trade cannot be modified /cancelled. It has already been approved by CM.";
                break;
            case "16552":
                message = "Error Code : 16552  Reason :Stock has been suspended.";
                break;
            case "16554":
                message = "Error Code : 16554  Reason :Trading Member not permitted in Futures.";
                break;
            case "16555":
                message = "Error Code : 16555  Reason :Trading Member not permitted in Options.";
                break;
            case "16556":
                message = "Error Code : 16556  Reason :Quantity less than the minimum lot size.";
                break;
            case "16557":
                message = "Error Code : 16557  Reason :Disclose quantity less than the minimum lot size.";
                break;
            case "16558":
                message = "Error Code : 16558  Reason :Minimum fill is less than the minimum lot size.";
                break;
            case "16560":
                message = "Error Code : 16560  Reason :The give up trade has already been rejected.";
                break;
            case "16561":
                message = "Error Code : 16561  Reason :Negotiated Orders not allowed.";
                break;
            case "16562":
                message = "Error Code : 16562  Reason :Negotiated Trade not allowed.";
                break;
            case "16566":
                message = "Error Code : 16566  Reason :User does not belong to Broker or Branch.";
                break;
            case "16570":
                message = "Error Code : 16570  Reason :The market is in post-close.";
                break;
            case "16571":
                message = "Error Code : 16571  Reason :The Closing Session has ended.";
                break;
            case "16572":
                message = "Error Code : 16572  Reason :Closing Session trades have been generated.";
                break;
            case "16573":
                message = "Error Code : 16573  Reason :Message length is invalid.";
                break;
            case "16574":
                message = "Error Code : 16574  Reason :Open - Close type entered is invalid.";
                break;
            case "16576":
                message = "Error Code : 16576  Reason :No. of nnf inquiry requests exceeded.";
                break;
            case "16577":
                message = "Error Code : 16577  Reason :Both participant and volume changed.";
                break;
            case "16578":
                message = "Error Code : 16578  Reason :Cover - Uncover type entered is invalid.";
                break;
            case "16579":
                message = "Error Code : 16579  Reason :Giveup requested for wrong CM ID.";
                break;
            case "16580":
                message = "Error Code : 16580  Reason :Order does not belong to the given participant.";
                break;
            case "16581":
                message = "Error Code : 16581  Reason :Invalid trade price.";
                break;
            case "16583":
                message = "Error Code : 16583  Reason :For Pro order participant entry not allowed.";
                break;
            case "16585":
                message = "Error Code : 16585  Reason :Not a valid account number.";
                break;
            case "16586":
                message = "Error Code : 16586  Reason :Participant Order Entry Not Allowed.";
                break;
            case "16589":
                message = "Error Code : 16589  Reason :All continuous session orders are being deleted now.";
                break;
            case "16594":
                message = "Error Code : 16594  Reason :After giveup approve/reject, trade quantity cannot be modified.";
                break;
            case "16596":
                message = "Error Code : 16596  Reason :Trading member cannot put Ex/Pl request for Participant. ";
                break;
            case "16597":
                message = "Error Code : 16597  Reason :Branch limit should be greater than sum of user limits.";
                break;
            case "16598":
                message = "Error Code : 16598  Reason :Branch limit should be greater than used limit.";
                break;
            case "16602":
                message = "Error Code : 16602  Reason :Dealer value limit exceeds the set limit.";
                break;
            case "16604":
                message = "Error Code : 16604  Reason :Participant not found.";
                break;
            case "16605":
                message = "Error Code : 16605  Reason :One leg of spread/2L failed.";
                break;
            case "16606":
                message = "Error Code : 16606  Reason :Quantity greater than Freeze quantity.";
                break;
            case "16607":
                message = "Error Code : 16607  Reason :Spread not allowed.";
                break;
            case "16608":
                message = "Error Code : 16608  Reason :Spread allowed only when market is open.";
                break;
            case "16609":
                message = "Error Code : 16609  Reason :Spread allowed only when stock is open.";
                break;
            case "16610":
                message = "Error Code : 16610  Reason :Both legs should have same quantity.";
                break;
            case "16611":
                message = "Error Code : 16611  Reason :Modified order qty freeze not allowed.";
                break;
            case "16612":
                message = "Error Code : 16612  Reason :The trade record has been modified.";
                break;
            case "16615":
                message = "Error Code : 16615  Reason :Order cannot be modified.";
                break;
            case "16616":
                message = "Error Code : 16616  Reason :Order can not be cancelled.";
                break;
            case "16617":
                message = "Error Code : 16617  Reason :Trade can not be manipulated.";
                break;
            case "16619":
                message = "Error Code : 16619  Reason :PCM can not ex_pl for himself.";
                break;
            case "16620":
                message = "Error Code : 16620  Reason :Ex/Pl by clearing member for TM not allowed.";
                break;
            case "16621":
                message = "Error Code : 16621  Reason :Clearing member cannot change the Ex/Pl requests placed by Trading Member.";
                break;
            case "16625":
                message = "Error Code : 16625  Reason :Clearing member is suspended.";
                break;
            case "16626":
                message = "Error Code : 16626  Reason :Expiry date not in ascending order.";
                break;
            case "16627":
                message = "Error Code : 16627  Reason :Invalid contract combination.";
                break;
            case "16628":
                message = "Error Code : 16628  Reason :Branch manager cannot cancel corporate manager's order.";
                break;
            case "16629":
                message = "Error Code : 16629  Reason :Branch manager cannot cancel other branch manager's order.";
                break;
            case "16630":
                message = "Error Code : 16630  Reason :Corporate manager cannot cancel other corporate manager's order.";
                break;
            case "16631":
                message = "Error Code : 16631  Reason :Spread not allowed for different underlying.";
                break;
            case "16632":
                message = "Error Code : 16632  Reason :Cli A/c number cannot be modified as trading member ID.";
                break;

            case "16636":
                message = "Error Code : 16636  Reason :Futures buy branch Order Value Limit has been exceeded.";
                break;
            case "16637":
                message = "Error Code : 16637  Reason :Futures sell branch Order Value Limit has been exceeded.";
                break;
            case "16638":
                message = "Error Code : 16638  Reason :Options buy branch Order Value Limit has been exceeded.";
                break;
            case "16639":
                message = "Error Code : 16639  Reason :Options sell branch Order Value Limit has been exceeded.";
                break;
            case "16640":
                message = "Error Code : 16640  Reason :Futures buy used limit execeeded the user limit.";
                break;
            case "16641":
                message = "Error Code : 16641  Reason :Futures sell used limit execeeded the user limit.";
                break;
            case "16642":
                message = "Error Code : 16642  Reason :Options buy used limit execeeded the user limit.";
                break;
            case "16643":
                message = "Error Code : 16643  Reason :Options sell used limit execeeded the user limit.";
                break;

            case "16645":
                message = "Error Code : 16645  Reason :Cannot approve. Bhav Copy generated.";
                break;
            case "16646":
                message = "Error Code : 16646  Reason :Cannot modify.";
                break;
            case "16656":
                message = "Error Code : 16656  Reason :No address in the database.";
                break;
            case "16662":
                message = "Error Code : 16662  Reason :Contract is opening. Please wait for the contract to open.";
                break;
            case "16666":
                message = "Error Code : 16666  Reason :Invalid NNF field.";
                break;
            case "16667":
                message = "Error Code : 16667  Reason :GTC GTD Orders not allowed.";
                break;
            case "16686":
                message = "Error Code : 16686  Reason :This error code will be message =ed if Close out order rejected by the system.";
                break;
            case "16687":
                message = "Error Code : 16687  Reason :This error code will be message =ed if the close out order entered is going into freeze. (Since Freeze is not allowed for close out orders)";
                break;
            case "16688":
                message = "Error Code : 16689  Reason :This error code will be message =ed if the close out order is not allowed in the system.";
                break;
            case "16690":
                message = "Error Code : 16690  Reason :This error code will be message =ed when a Trade MOD request is placed by a broker in Close-out.";
                break;
            case "16706":
                message = "Error Code : 16706  Reason :Cancelled by the System";
                break;
            case "16708":
                message = "Error Code : 16708  Reason :System Error. Orders not completely cancelled by the system. Please request Quick CXL again";
                break;
            case "16713":
                message = "Error Code : 16713  Reason :Price difference is beyond Operating Range.";
                break;
            case "16793":
                message = "Error Code : 16793  Reason :Order Entered has invalid data.";
                break;
            case "16794":
                message = "Error Code : 16794  Reason :Order Entered has invalid data.";
                break;
            case "16795":
                message = "Error Code : 16795  Reason :Order cancelled due to voluntary close out.";
                break;
            case "16796":
                message = "Error Code : 16796  Reason :Order cancelled due to OI violation.";
                break;
            case "16803":
                message = "Error Code : 16803  Reason :Bulk Order rejected due to price freeze.";
                break;
            case "16804":
                message = "Error Code : 16804  Reason :Bulk Order rejected due to quantity Freeze.";
                break;
            case "16805":
                message = "Error Code : 16805  Reason :Trader not eligible for Bulk Order.";
                break;
            case "16806":
                message = "Error Code : 16806  Reason :Trader allowed to enter only Bulk Order.";
                break;
            case "16807":
                message = "Error Code : 16807  Reason :The Account is Debarred by Exchange.";
                break;

				/*
						Following error codes not found in NSE FO exchange document.
				*/
            ///////EXTRA FIELDS IN NCDEX////
            case "8661":
                message = "Error Code : 8661   Reason :Delivery start number must less than or equal to Delivery end number";
                break;
            case "16172":
                message = "Error Code : 16172  Reason :Prices freeze are not allowed";
                break;
            case "16159":
                message = "Error Code : 16159  Reason :No market orders are allowed in PostClose";
                break;
            case "16668":
                message = "Error Code : 16668  Reason :Orders not allowed in PostClose";
                break;
            case "16375":
                message = "Error Code : 16375  Reason :Request rejected as end of day processing has started";
                break;
            case "16387":
                message = "Error Code : 16387  Reason :Contract not allowed to trader in.";
                break;
            case "16437":
                message = "Error Code : 16437  Reason :Warehouse deleted";
                break;
            case "16534":
                message = "Error Code : 16534  Reason :No buyback running for that sercurity.";
                break;
            case "16542":
                message = "Error Code : 16542  Reason :Invalid participant";
                break;
            case "16664":
                message = "Error Code : 16664  Reason :Quantity is not a multiple of delivery lot";
                break;
            case "16672":
                message = "Error Code : 16672  Reason :Both TM id and client code required";
                break;
            case "16692":
                message = "Error Code : 16692  Reason :Nnf field value is <1 or >999999999999999";
                break;
            case "16684":
                message = "Error Code : 16684  Reason :Password is one of last 5 passwords";
                break;
            case "16683":
                message = "Error Code : 16683  Reason :User password is locked";
                break;
            case "16685":
                message = "Error Code : 16685  Reason :User does not linked to you.";
                break;
            case "16145":
                message = "Error Code : 16145  Reason :Security is not eligible to trade in Preopen";
                break;
            case "16115":
                message = "Error Code : 16115  Reason :Order Modification/ Cancellation rejected by the system";
                break;
            case "16592":
                message = "Error Code : 16592  Reason :Order Entry is not allowed";
                break;
            case "16052":
                message = "Error Code : 16052  Reason :When Preopen trade cancel request is rejected";
                break;
            case "16418":
                message = "Error Code : 16418  Reason :Order entered has invalid data";
                break;
            case "16388":
                message = "Error Code : 16388  Reason :When Preopen unmatched orders are cancelled by the system after preopen ession ends";
                break;
            case "16601":
                message = "Error Code : 16601  Reason :Request Rejected by the exchange";
                break;
            case "17039":
                message = "Error Code : 17039  Reason :PRO/CLI Modification not allowed for the Order";
                break;
            case "17041":
                message = "Error Code : 17041  Reason :Trade Modification Not Allowed for User Type";
                break;
            case "17042":
                message = "Error Code : 17042  Reason :Trade Modification not Allowed for Broker";
                break;
            case "17043":
                message = "Error Code : 17043  Reason :Trade Modification not Allowed for User";
                break;
            case "17038":
                message = "Error Code : 17038  Reason :Order is outstanding";
                break;
            case "16179":
                message = "Error Code : 16179  Reason :Order price out of DPR range";
                break; //Priya on 13/3/2013  for Bug ID 0006434.
            case "   -5":
                message = "Error Code : -5     Reason :Invalid NNF field";
                break;// PRAVIN
            case "  -50":
                message = "Error Code : -50    Reason :Retailer is Suspended";
                break; //PRAVIN on 01-JULY-13
            case "  -51":
                message = "Error Code : -51    Reason :Retailer is Deleted";
                break;

            case "17045":
                message = "Error Code : 17045  Reason :Order Quantity Exceeds Quantity Value Limit for User.";
                break;
            case "17048":
                message = "Error Code : 17048  Reason :Trade Modification Not Allowed for User.";
                break;
            case "17070":
                message = "Error Code : 17070  The Price is out of the current execution range.";
                break;
            case "17071":
                message = "Error Code : 17071 Order cancelled by Exchange [Self Trade Prevention].";
                break;
            case "10001":
                message = "Error Code : 10001  The Price is out of the current execution range.";
                break;

            default: {
                message = "Unknown Error Code ";
                break;
            }
        }
        return message;
    }

    private String getMarketErrorMessage1(String errorcode) {

        String message = "";
        switch (errorcode) {


            case "-1":
                message = "Error Code :    -1  Reason :Exchange Downloading in Progess. Order Entry Not Allowded";
                break;
            case "293":
                message = "Error Code :   293  Reason :Invalid instrument type.";
                break;
            case "509":
                message = "Error Code :   509  Reason :Order does not exist.";
                break;
            case "8049":
                message = "Error Code :  8049  Reason :Initiator is not allowed to cancel auction order.";
                break;
            case "8485":
                message = "Error Code :  8485  Reason :Auction number does not exist";
                break;
            case "16000":
                message = "Error Code : 16000  Reason :The trading system is not available for trading.";
                break;
            case "16003":
                message = "Error Code : 16003  Reason :Erroneous transaction code received.";
                break;
            case "16004":
                message = "Error Code : 16004  Reason :The user is already signed on.";
                break;
            case "16005":
                message = "Error Code : 16005  Reason :System error while trying to sign-off. Please call the Exchange.";
                break;
            case "16006":
                message = "Error Code : 16006  Reason :Invalid sign-on. Please try again.";
                break;
            case "16011":
                message = "Error Code : 16011  Reason :This report has already been requested.";
                break;
            case "16012":
                message = "Error Code : 16012  Reason :Invalid symbol/series.";
                break;
            case "16014":
                message = "Error Code : 16014  Reason :This order is not yours.";
                break;
            case "16015":
                message = "Error Code : 16015  Reason :Invalid trade cancel request.";
                break;
            case "16016":
                message = "Error Code : 16016  Reason :Invalid trade number.";
                break;
            case "16019":
                message = "Error Code : 16019  Reason :Stock not found.";
                break;
            case "16035":
                message = "Error Code : 16035  Reason :Security is unavailable for trading at this time. Please try later.";
                break;
            case "16041":
                message = "Error Code : 16041  Reason :Trading member does not exist in the system.";
                break;
            case "16042":
                message = "Error Code : 16042  Reason :Dealer does not exist in the system.";
                break;
            case "16043":
                message = "Error Code : 16043  Reason :This record already exist on the NEAT system.";
                break;
            case "16044":
                message = "Error Code : 16044  Reason :Order has been modified. Please try again.";
                break;
            case "16049":
                message = "Error Code : 16049  Reason :Stock is suspended.";
                break;
            case "16053":
                message = "Error Code : 16053  Reason :Your password has expired, must be changed.";
                break;
            case "16054":
                message = "Error Code : 16054  Reason :Branch does not exist in the system.";
                break;
            case "16056":
                message = "Error Code : 16056  Reason :Program error.";
                break;
            case "16086":
                message = "Error Code : 16086  Reason :Duplicate trade cancel request.";
                break;
            case "16098":
                message = "Error Code : 16098  Reason :Invalid trader ID for buyer.";
                break;
            case "16099":
                message = "Error Code : 16099  Reason :Invalid trader ID for seller.";
                break;
            case "16104":
                message = "Error Code : 16104  Reason :System could not complete your transaction - Admin notified.";
                break;
            case "16148":
                message = "Error Code : 16148  Reason :Invalid Dealer ID entered.";
                break;
            case "16154":
                message = "Error Code : 16154  Reason :Invalid Trader ID entered.";
                break;
            case "16169":
                message = "Error Code : 16169  Reason :Order priced ATO cannot be entered when a security is open.";
                break;
            case "16198":
                message = "Error Code : 16198  Reason :Duplicate modification or cancellation request for the same trade has been encountered.";
                break;
            case "16227":
                message = "Error Code : 16227  Reason :Only market orders are allowed in postclose.";
                break;
            case "16228":
                message = "Error Code : 16228  Reason :SL, MIT or NT orders are not allowed during Post Close.";
                break;
            case "16229":
                message = "Error Code : 16229  Reason :GTC GTD or GTDays orders are Not Allowed during Post Close.";
                break;
            case "16230":
                message = "Error Code : 16230  Reason :Continuous session orders cannot be modified.";
                break;
            case "16231":
                message = "Error Code : 16231  Reason :Continuous session trades cannot be changed.";
                break;
            case "16706":
                message = "Error Code : 16706  Reason :Cancelled by the System";
                break;
            case "16708":
                message = "Error Code : 16708  Reason :System Error. Orders not completely cancelled by the system. Please request Quick CXL again";
                break;
            case "16233":
                message = "Error Code : 16233  Reason :Proprietary requests cannot be made for participant.";
                break;
            case "16251":
                message = "Error Code : 16251  Reason :Trade modification with different quantities is received.";
                break;
            case "16273":
                message = "Error Code : 16273  Reason :Record does not exist.";
                break;
            case "16278":
                message = "Error Code : 16278  Reason :The markets have not been opened for trading.";
                break;
            case "16279":
                message = "Error Code : 16279  Reason :The security has not yet been admitted for trading.";
                break;
            case "16280":
                message = "Error Code : 16280  Reason :The security has matured.";
                break;
            case "16281":
                message = "Error Code : 16281  Reason :The security has been expelled.";
                break;
            case "16282":
                message = "Error Code : 16282  Reason :The order quantity is greater than the issued capital.";
                break;
            case "16283":
                message = "Error Code : 16283  Reason :The order price is not multiple of the tick size.";
                break;
            case "16284":
                message = "Error Code : 16284  Reason :The order price is out of the day's price range.";
                break;
            case "16285":
                message = "Error Code : 16285  Reason :The broker is not active.";
                break;
            case "16300":
                message = "Error Code : 16300  Reason :System is in a wrong state to make the requested change.";
                break;
            case "16303":
                message = "Error Code : 16303  Reason :Request denied. Pending auctions.";
                break;
            case "16307":
                message = "Error Code : 16307  Reason :The order is canceled due to quantity freeze.";
                break;
            case "16308":
                message = "Error Code : 16308  Reason :The order is canceled due to price freeze.";
                break;
            case "16311":
                message = "Error Code : 16311  Reason :The Solicitor period for the Auction is over.";
                break;
            case "16312":
                message = "Error Code : 16312  Reason :The Competitor period for the Auction is over.";
                break;
            case "16313":
                message = "Error Code : 16313  Reason :The Auction period will cross market close time.";
                break;
            case "16315":
                message = "Error Code : 16315  Reason :The limit price is worse than the trigger price.";
                break;
            case "16316":
                message = "Error Code : 16316  Reason :The trigger price is not a multiple of tick size.";
                break;
            case "16317":
                message = "Error Code : 16317  Reason :AON attribute not allowed.";
                break;
            case "16318":
                message = "Error Code : 16318  Reason :MF attribute not allowed.";
                break;
            case "16319":
                message = "Error Code : 16319  Reason :AON attribute not allowed at Security level.";
                break;
            case "16320":
                message = "Error Code : 16320  Reason :MF attribute not allowed at security level.";
                break;
            case "16321":
                message = "Error Code : 16321  Reason :MF quantity is greater than disclosed quantity";
                break;
            case "16322":
                message = "Error Code : 16322  Reason :MF quantity is not a multiple of regular lot.";
                break;
            case "16323":
                message = "Error Code : 16323  Reason :MF quantity is greater than Original quantity.";
                break;
            case "16324":
                message = "Error Code : 16324  Reason :Disclosed quantity is greater than Original quantity.";
                break;
            case "16325":
                message = "Error Code : 16325  Reason :Disclosed quantity is not a multiple of regular lot.";
                break;
            case "16326":
                message = "Error Code : 16326  Reason :GTD is greater than that specified at the trading system.";
                break;
            case "16327":
                message = "Error Code : 16327  Reason :Quantity is greater than Regular lot size.";
                break;
            case "16328":
                message = "Error Code : 16328  Reason :Quantity is not a multiple of regular lot.";
                break;
            case "16329":
                message = "Error Code : 16329  Reason :Trading Member not permitted in the market.";
                break;
            case "16330":
                message = "Error Code : 16330  Reason :Security is suspended.";
                break;
            case "16333":
                message = "Error Code : 16333  Reason :Branch Order Value Limit has been exceeded.";
                break;
            case "16343":
                message = "Error Code : 16343  Reason :The order to be cancelled has changed.";
                break;
            case "16344":
                message = "Error Code : 16344  Reason :The order cannot be cancelled.";
                break;
            case "16345":
                message = "Error Code : 16345  Reason :Initiator order cannot be cancelled.";
                break;
            case "16346":
                message = "Error Code : 16346  Reason :Order cannot be modified.";
                break;
            case "16348":
                message = "Error Code : 16348  Reason :Trading is not allowed in this market.";
                break;
            case "16357":
                message = "Error Code : 16357  Reason :Order entered for negotiated trade is cancelled.";
                break;
            case "16363":
                message = "Error Code : 16363  Reason :New status requested should not be same as existing one.";
                break;
            case "16369":
                message = "Error Code : 16369  Reason :The security status is preopen.";
                break;
            case "16372":
                message = "Error Code : 16372  Reason :Order entry not allowed for user as it is of inquiry type.";
                break;
            case "16392":
                message = "Error Code : 16392  Reason :Turnover limit not provided. Please contact Exchange.";
                break;
            case "16400":
                message = "Error Code : 16400  Reason :DQ is less than minimum quantity allowed.";
                break;
            case "16404":
                message = "Error Code : 16404  Reason :Order has been cancelled due to freeze admin suspension.";
                break;
            case "16405":
                message = "Error Code : 16405  Reason :BUY - SELL type entered is invalid.";
                break;
            case "16415":
                message = "Error Code : 16415  Reason :Invalid combination of MF / AON / Disclosed Volume.";
                break;
            case "16416":
                message = "Error Code : 16416  Reason :Invalid counter broker Id.";
                break;
            case "16440":
                message = "Error Code : 16440  Reason :Order Entry is not allowed in preopen for the series.";
                break;
            case "16441":
                message = "Error Code : 16441  Reason :ST Orders are not allowed in preopen.";
                break;
            case "16442":
                message = "Error Code : 16442  Reason :The current placed order's value is more than users order value limit.";
                break;
            case "16445":
                message = "Error Code : 16445  Reason :Stop Loss orders are not allowed.";
                break;
            case "16446":
                message = "Error Code : 16446  Reason :Market If Touched orders are not allowed.";
                break;
            case "16447":
                message = "Error Code : 16447  Reason :Order entry not allowed in Pre-open.";
                break;
            case "16500":
                message = "Error Code : 16500  Reason :Ex/Pl not allowed.";
                break;
            case "16501":
                message = "Error Code : 16501  Reason :Invalid ExPl flag value.";
                break;
            case "16513":
                message = "Error Code : 16513  Reason :Ex/Pl rejection not allowed.";
                break;
            case "16514":
                message = "Error Code : 16514  Reason :Not modifiable.";
                break;
            case "16518":
                message = "Error Code : 16518  Reason :Clearing member, Trading Member link not found.";
                break;
            case "16521":
                message = "Error Code : 16521  Reason :Not a clearing member.";
                break;
            case "16523":
                message = "Error Code : 16523  Reason :User in not a corporate manager.";
                break;
            case "16532":
                message = "Error Code : 16532  Reason :Clearing member Participant link not found.";
                break;
            case "16533":
                message = "Error Code : 16533  Reason :Enter either TM or Participant.";
                break;
            case "16550":
                message = "Error Code : 16550  Reason :Trade cannot be modified /cancelled. It has already been approved by CM.";
                break;
            case "16552":
                message = "Error Code : 16552  Reason :Stock has been suspended.";
                break;
            case "16554":
                message = "Error Code : 16554  Reason :Trading Member not permitted in Futures.";
                break;
            case "16555":
                message = "Error Code : 16555  Reason :Trading Member not permitted in Options.";
                break;
            case "16556":
                message = "Error Code : 16556  Reason :Quantity less than the minimum lot size.";
                break;
            case "16557":
                message = "Error Code : 16557  Reason :Disclose quantity less than the minimum lot size.";
                break;
            case "16558":
                message = "Error Code : 16558  Reason :Minimum fill is less than the minimum lot size.";
                break;
            case "16560":
                message = "Error Code : 16560  Reason :The give up trade has already been rejected.";
                break;
            case "16561":
                message = "Error Code : 16561  Reason :Negotiated Orders not allowed.";
                break;
            case "16562":
                message = "Error Code : 16562  Reason :Negotiated Trade not allowed.";
                break;
            case "16566":
                message = "Error Code : 16566  Reason :User does not belong to Broker or Branch.";
                break;
            case "16570":
                message = "Error Code : 16570  Reason :The market is in post-close.";
                break;
            case "16571":
                message = "Error Code : 16571  Reason :This error code will be message =ed when a user under a broker in 'Close out‘ state tries to modifyl Trade.";
                break;
            case "16572":
                message = "Error Code : 16572  Reason :Closing Session trades have been generated.";
                break;
            case "16573":
                message = "Error Code : 16573  Reason :Message length is invalid.";
                break;
            case "16574":
                message = "Error Code : 16574  Reason :Open - Close type entered is invalid.";
                break;
            case "16576":
                message = "Error Code : 16576  Reason :No. of nnf inquiry requests exceeded.";
                break;
            case "16578":
                message = "Error Code : 16578  Reason :Cover - Uncover type entered is invalid.";
                break;
            case "16579":
                message = "Error Code : 16579  Reason :Giveup requested for wrong CM ID.";
                break;
            case "16580":
                message = "Error Code : 16580  Reason :Order does not belong to the given participant.";
                break;
            case "16581":
                message = "Error Code : 16581  Reason :Invalid trade price.";
                break;
            case "16583":
                message = "Error Code : 16583  Reason :For Pro order participant entry not allowed.";
                break;
            case "16585":
                message = "Error Code : 16585  Reason :Not a valid account number.";
                break;
            case "16586":
                message = "Error Code : 16586  Reason :Participant Order Entry Not Allowed.";
                break;
            case "16589":
                message = "Error Code : 16589  Reason :All continuous session orders are being deleted now.";
                break;
            case "16594":
                message = "Error Code : 16594  Reason :After giveup approve/reject, trade quantity cannot be modified.";
                break;
            case "16596":
                message = "Error Code : 16596  Reason :Trading member cannot put Ex/Pl request for Participant. ";
                break;
            case "16597":
                message = "Error Code : 16597  Reason :Order entry / Modification rejected by the Exchange";
                break;
            case "16598":
                message = "Error Code : 16598  Reason :Order Entry is not allowed";
                break;
            case "16604":
                message = "Error Code : 16604  Reason :Disclosed Quantity (DQ) order not allowed in closing session";
                break;
            case "16605":
                message = "Error Code : 16605  Reason :One leg of spread/2L failed.";
                break;
            case "16606":
                message = "Error Code : 16606  Reason :Quantity greater than Freeze quantity.";
                break;
            case "16607":
                message = "Error Code : 16607  Reason :Spread not allowed.";
                break;
            case "16608":
                message = "Error Code : 16608  Reason :Spread allowed only when market is open.";
                break;
            case "16609":
                message = "Error Code : 16609  Reason :Spread allowed only when stock is open.";
                break;
            case "16610":
                message = "Error Code : 16610  Reason :Both legs should have same quantity.";
                break;
            case "16611":
                message = "Error Code : 16611  Reason :Modified order qty freeze not allowed.";
                break;
            case "16612":
                message = "Error Code : 16612  Reason :The trade record has been modified.";
                break;
            case "16615":
                message = "Error Code : 16615  Reason :Order cannot be modified.";
                break;
            case "16616":
                message = "Error Code : 16616  Reason :Order can not be cancelled.";
                break;
            case "16617":
                message = "Error Code : 16617  Reason :Trade can not be manipulated.";
                break;
            case "16619":
                message = "Error Code : 16619  Reason :PCM can not ex_pl for himself.";
                break;
            case "16620":
                message = "Error Code : 16620  Reason :Ex/Pl by clearing member for TM not allowed.";
                break;
            case "16621":
                message = "Error Code : 16621  Reason :Clearing member cannot change the Ex/Pl requests placed by Trading Member.";
                break;
            case "16625":
                message = "Error Code : 16625  Reason :Clearing member is suspended.";
                break;
            case "16626":
                message = "Error Code : 16626  Reason :Expiry date not in ascending order.";
                break;
            case "16627":
                message = "Error Code : 16627  Reason :Invalid contract combination.";
                break;
            case "16628":
                message = "Error Code : 16628  Reason :Branch manager cannot cancel corporate manager's order.";
                break;
            case "16629":
                message = "Error Code : 16629  Reason :Branch manager cannot cancel other branch manager's order.";
                break;
            case "16630":
                message = "Error Code : 16630  Reason :Corporate manager cannot cancel other corporate manager's order.";
                break;
            case "16631":
                message = "Error Code : 16631  Reason :Spread not allowed for different underlying.";
                break;
            case "16632":
                message = "Error Code : 16632  Reason :Cli A/c number cannot be modified as trading member ID.";
                break;

            case "16636":
                message = "Error Code : 16636  Reason :Futures buy branch Order Value Limit has been exceeded.";
                break;
            case "16637":
                message = "Error Code : 16637  Reason :Futures sell branch Order Value Limit has been exceeded.";
                break;
            case "16638":
                message = "Error Code : 16638  Reason :Options buy branch Order Value Limit has been exceeded.";
                break;
            case "16639":
                message = "Error Code : 16639  Reason :Options sell branch Order Value Limit has been exceeded.";
                break;
            case "16640":
                message = "Error Code : 16640  Reason :Futures buy used limit execeeded the user limit.";
                break;
            case "16641":
                message = "Error Code : 16641  Reason :Futures sell used limit execeeded the user limit.";
                break;
            case "16642":
                message = "Error Code : 16642  Reason :Options buy used limit execeeded the user limit.";
                break;
            case "16643":
                message = "Error Code : 16643  Reason :Options sell used limit execeeded the user limit.";
                break;

            case "16645":
                message = "Error Code : 16645  Reason :Cannot approve. Bhav Copy generated.";
                break;
            case "16646":
                message = "Error Code : 16646  Reason :Cannot modify.";
                break;
            case "16541":
                message = "Error Code : 16541  Reason :Participant is invalid.";
                break;
            case "16577":
                message = "Error Code : 16577  Reason :Both participant and volume changed.";
                break;
            case "16001":
                message = "Error Code : 16001  Reason :Invalid User Type";
                break;
            case "16007":
                message = "Error Code : 16007  Reason :Signing onto the trading system is restricted.  Please try later on.";
                break;
            case "16100":
                message = "Error Code : 16100  Reason :Your system version has not been updated.";
                break;
            case "16443":
                message = "Error Code : 16443  Reason :Order value exceeds the order limit value.";
                break;
            case "16656":
                message = "Error Code : 16656  Reason :No address in the database.";
                break;
            case "16667":
                message = "Error Code : 16667  Reason :GTC GTD Orders not allowed.";
                break;
            case "16662":
                message = "Error Code : 16662  Reason :Contract is opening. Please wait for the contract to open.";
                break;
            case "16666":
                message = "Error Code : 16666  Reason :Invalid NNF field.";
                break;
            case "16388":
                message = "Error Code : 16388  Reason :When Preopen unmatched orders are cancelled by the system after preopen session ends.";
                break;
            ///////EXTRA FIELDS IN NCDEX////
            case "8661":
                message = "Error Code : 8661   Reason :Delivery start number must less than or equal to Delivery end number";
                break;
            case "16172":
                message = "Error Code : 16172  Reason :Prices freeze are not allowed";
                break;
            case "16159":
                message = "Error Code : 16159  Reason :No market orders are allowed in PostClose";
                break;
            case "16668":
                message = "Error Code : 16668  Reason :Orders not allowed in PostClose";
                break;
            case "16375":
                message = "Error Code : 16375  Reason :Request rejected as end of day processing has started";
                break;
            case "16387":
                message = "Error Code : 16387  Reason :Security is not allowed to trade in this market.";
                break;
            case "16437":
                message = "Error Code : 16437  Reason :Warehouse deleted";
                break;
            case "16534":
                message = "Error Code : 16334  Reason :Participant/CM id link not found";
                break;
            case "16542":
                message = "Error Code : 16542  Reason :Invalid participant";
                break;
            case "16664":
                message = "Error Code : 16664  Reason :Quantity is not a multiple of delivery lot";
                break;
            case "16672":
                message = "Error Code : 16672  Reason :Both TM id and client code required";
                break;
            case "16692":
                message = "Error Code : 16692  Reason :Nnf field value is <1 or >999999999999999";
                break;
            case "16684":
                message = "Error Code : 16684  Reason :Password is one of last 5 passwords";
                break;
            case "16683":
                message = "Error Code : 16683  Reason :User password is locked";
                break;
            case "16685":
                message = "Error Code : 16685  Reason :User does not linked to you.";
                break;
            case "16145":
                message = "Error Code : 16145  Reason :Security is not eligible to trade in Preopen";
                break;
            case "16115":
                message = "Error Code : 16115  Reason :Order Modification/ Cancellation rejected by the system";
                break;
            case "16592":
                message = "Error Code : 16592  Reason :Order Entry is not allowed";
                break;
            //  case "16597": message = "Error Code : 16597  Reason :Order entry / Modification rejected by the Exchange";break;
            //	case "16598": message = "Error Code : 16598  Reason :Order Entry is not allowed";break;
            case "16052":
                message = "Error Code : 16052  Reason :When Preopen trade cancel request is rejected";
                break;
            case "16418":
                message = "Error Code : 16418  Reason :Order entered has invalid data";
                break;
            //	case "16388": message = "Error Code : 16388  Reason :When Preopen unmatched orders are cancelled by the system after preopen ession ends";break;
            case "16568":
                message = "Error Code : 16568  Reason :This error code is returned when a Close out order entry is not allowed.";
                break;
            case "16567":
                message = "Error Code : 16567  Reason :This error code will be returned when a Close out order goes into freeze.";
                break;
            case "16569":
                message = "Error Code : 16569  Reason :This error code is message =ed when a Close out order is rejected by the system.";
                break;
            case "16601":
                message = "Error Code : 16601  Reason : Request Rejected by the exchange";
                break;
            //	case "16329": message = "Broker is not eligible for trading in given market";break;
            //	case "16602": message = "Market order not allowed in Block Trade session";break;
            case "16603":
                message = "Error Code : 16603 Reason :Market order not allowed in Block Trade session";
                break;
            case "17017":
                message = "Error Code : 17017 Reason :Order Cancelled due to Voluntary Closeout.";
                break;
            //case "16388": message = "Error Code : 16388 Reason :Invalid NNF field";break;
            case "-5":
                message = "Error Code :    -5 Reason :Invalid NNF field";
                break;// PRAVIN
            case "16179":
                message = "Error Code : 16179 Reason :Order price out of DPR range";
                break; //Priya on 13/3/2013  for Bug ID 0006434
            case "-50":
                message = "Error Code :   -50 Reason Retailer is Suspended";
                break; //PRAVIN on 01-JULY-13
            case "-51":
                message = "Error Code :   -51 Reason Retailer is Deleted";
                break;

            //@@Farhan Shaikh added on 13/09/2013
            case "16060":
                message = "Error Code : 16060  Reason :Modified/Cancelled order not found";
                break;
            case "16123":
                message = "Error Code : 16123  Reason :System not able to complete your request. Please try again.";
                break;
            case "16134":
                message = "Error Code : 16134  Reason :This Dealer is disabled. Please call the Exchange.";
                break;
            case "16197":
                message = "Error Code : 16197  Reason :Order Entry or Modification not allowed in preopen.";
                break;
            case "16247":
                message = "Error Code : 16247  Reason :Invalid price in the price field.";
                break;
            case "16252":
                message = "Error Code : 16252  Reason :Cancelled the trade modify request.";
                break;
            case "16260":
                message = "Error Code : 16260  Reason :Record is there in master file but delete flag is set.";
                break;
            case "16310":
                message = "Error Code : 16310  Reason :AON volume not enough";
                break;
            case "16314":
                message = "Error Code : 16314  Reason :The Auction cannot be cancelled.";
                break;
            case "16332":
                message = "Error Code : 16332  Reason :Remaining passive order has to be cancelled.";
                break;
            case "16379":
                message = "Error Code : 16379  Reason :The broker is not allowed to enter soliciting orders.";
                break;
            case "16383":
                message = "Error Code : 16383  Reason :Trading in this auction is finished.";
                break;
            case "16395":
                message = "Error Code : 16395  Reason :The Negotiated trades cannot be cancelled";
                break;
            case "16403":
                message = "Error Code : 16403  Reason :You are trying to sign on from a different location. Sign on is not allowed.";
                break;
            case "16411":
                message = "Error Code : 16411  Reason :Pro-client can be either Pro or Client only.";
                break;
            case "16412":
                message = "Error Code : 16412  Reason :New volume should be less than the traded volume.";
                break;
            case "16413":
                message = "Error Code : 16413  Reason :Requested by can be BUY or SELL or BOTH.";
                break;
            case "16414":
                message = "Error Code : 16414  Reason :Invalid combination of book type and instructions (order_type).";
                break;
            case "16417":
                message = "Error Code : 16417  Reason :Number of NNF requests exceeded.";
                break;
            case "16419":
                message = "Error Code : 16419  Reason :Cancelled trade cancel request.";
                break;
            case "16420":
                message = "Error Code : 16420  Reason :Alpha char must be the same as first two chars of symbol.";
                break;
            case "16421":
                message = "Error Code : 16421  Reason :Only control can initiate auctions, not trader.";
                break;
            case "16422":
                message = "Error Code : 16422  Reason :Book type should be between 1(RL) and 7(AU).";
                break;
            case "16423":
                message = "Error Code : 16423  Reason :Invalid trigger price entered.";
                break;
            case "16424":
                message = "Error Code : 16424  Reason :Message length is invalid.";
                break;
            case "16425":
                message = "Error Code : 16425  Reason :Participant does not exist.";
                break;
            case "16426":
                message = "Error Code : 16426  Reason :Participant and volume cannot be changed simultaneously.";
                break;
            case "16430":
                message = "Error Code : 16430  Reason :Invalid auction inquiry request.";
                break;
            case "16431":
                message = "Error Code : 16431  Reason :Invalid Account in the Account field";
                break;
            case "16436":
                message = "Error Code : 16436  Reason :The order value limit has exceeded";
                break;
            case "16439":
                message = "Error Code : 16439  Reason :DQ Orders are not allowed in preopen.";
                break;
            case "16450":
                message = "Error Code : 16450  Reason :Account number is mandatory in Account field";
                break;
            case "16473":
                message = "Error Code : 16473  Reason :Only board lot market orders are allowed in Closing Session.";
                break;
            case "16482":
                message = "Error Code : 16482  Reason :The order has been cancelled as security has been suspended";
                break;
            case "16483":
                message = "Error Code : 16483  Reason :The order has been cancelled as participant has been suspended";
                break;
            case "16530":
                message = "Error Code : 16530  Reason :Users buy order value limit has exceeded.";
                break;
            case "16531":
                message = "Error Code : 16531  Reason :The order value limit for the sell quantity has exceeded its limit";
                break;
            case "16535":
                message = "Error Code : 16535  Reason :Order partially rejected. Remaining order quantity specified rejected due to system error.";
                break;
            case "16536":
                message = "Error Code : 16535  Reason :Quick Cancel request rejected due to system error. Retry Quick Cancel Request";
                break;
            case "16761":
                message = "Error Code : 16535  Reason :The account is debarred from trading (New error code defined for order entry/Modification due to debarred Client.)";
                break;
            case "17080":
                message = "Error Code : 17080  Order cancelled by Exchange [Self Trade Prevention].";
                break;
            case "10001":
                message = "Error Code : 10001 The Price is out of the current execution range.";
                break;

            case "-2":
                message = "Error Code :    -2  Reason :Trade Modifcation With Diffrent Exchange RetailerID not allowed";
                break;
            default: {

                message = "Unknown Error Code ";
                break;
            }
        }
        return message;
    }

    private String getMarketErrorMessage6(String errorcode) {

        String message = "";
        switch (errorcode) {
            case "16388":
                message = "Error Code :16388 Reason: When Preopen unmatched orders are cancelled by the system after preopen session ends";
                break;

            case "-1":
                message = "Error Code :-1    Reason :Exchange Downloading in Progress. Order Entry Not Allowed";
                break;
            case "-100":
                message = "Error Code :-100  Reason :Previous Order Request in Progress . Order Entry/Modification/Cancellation Not Allowed.";
                break;
            case "0":
                message = "Error Code : 0    Reason : Success";
                break;
            case "1":
                message = "Error Code : 1    Reason : Scrip Code is not val";
                break;
            case "2":
                message = "Error Code : 2    Reason :Scrip is Suspended";
                break;
            case "3":
                message = "Error Code : 3    Reason :Scrip is not active";
                break;
            case "4":
                message = "Error Code : 4    Reason :Lim Order Not Allowed For Scrip";
                break;
            case "5":
                message = "Error Code : 5    Reason :Mkt Order Not Allowed For Scrip";
                break;
            case "6":
                message = "Error Code : 6    Reason :Hit Order Not Allowed For Scrip";
                break;
            case "7":
                message = "Error Code : 7    Reason :Take Order Not Allowed For Scrip";
                break;
            case "8":
                message = "Error Code : 8    Reason :Quote Not Allowed For Scrip";
                break;
            case "9":
                message = "Error Code : 9    Reason :X-Deal Not Allowed For Scrip";
                break;
            case "10":
                message = "Error Code : 10   Reason :Block-Deal Not Allowed For Scrip";
                break;
            case "11":
                message = "Error Code : 11   Reason :All None Not Allowed For Scrip";
                break;
            case "12":
                message = "Error Code : 12   Reason :Stop loss not allowed for scrip";
                break;
            case "13":
                message = "Error Code : 13   Reason :No. Of ScripCodes less than 0";
                break;
            case "14":
                message = "Error Code : 14   Reason :NoOfScrips Should be equal to1.";
                break;
            case "51":
                message = "Error Code : 51   Reason :Company Code is not valid";
                break;
            case "52":
                message = "Error Code : 52   Reason :Company is Suspended";
                break;
            case "53":
                message = "Error Code : 53   Reason :Company is not active";
                break;
            case "101":
                message = "Error Code : 101  Reason :Not allowed in this session";
                break;
            case "102":
                message = "Error Code : 102  Reason :Market is Halted";
                break;
            case "151":
                message = "Error Code : 151  Reason :Member is Suspended ,Can't Trade";
                break;
            case "152":
                message = "Error Code : 152  Reason :User is Suspended ,Can't Trade";
                break;
            case "153":
                message = "Error Code : 153  Reason :Member is Expelled ,Can't Trade";
                break;
            case "154":
                message = "Error Code : 154  Reason :User is Expelled ,Can't Trade";
                break;
            case "155":
                message = "Error Code : 155  Reason :Permission Denied";
                break;
            case "156":
                message = "Error Code : 156  Reason :You Can't Update this";
                break;
            case "157":
                message = "Error Code : 157  Reason :You Can't Trade From this Terminal";
                break;
            case "158":
                message = "Error Code : 158  Reason :System does not recognise you";
                break;
            case "159":
                message = "Error Code : 159  Reason :Logon Successful";
                break;
            case "160":
                message = "Error Code : 160  Reason :Logoff Successful";
                break;
            case "161":
                message = "Error Code : 161  Reason :BOLT does not know your member";
                break;
            case "162":
                message = "Error Code : 162  Reason :BOLT does not know your Rep member";
                break;
            case "163":
                message = "Error Code : 163  Reason :Rep Member is Suspended ,Can't Trade";
                break;
            case "164":
                message = "Error Code : 164  Reason :User has logged on term";
                break;
            case "165":
                message = "Error Code : 165  Reason :User has logoff from term";
                break;
            case "166":
                message = "Error Code : 166  Reason :Unknown Workstation";
                break;
            case "167":
                message = "Error Code : 167  Reason :Rep member expelled";
                break;
            case "168":
                message = "Error Code : 168  Reason :Rate not within 1%";
                break;
            case "169":
                message = "Error Code : 169  Reason :Revl. Qty should be same as Tot Qty";
                break;
            case "171":
                message = "Error Code : 171  Reason :Error same password";
                break;
            case "172":
                message = "Error Code : 172  Reason :Update successful";
                break;
            case "173":
                message = "Error Code : 173  Reason :Operation not allowed";
                break;
            case "174":
                message = "Error Code : 174  Reason :Invalid Member";
                break;
            case "175":
                message = "Error Code : 175  Reason :Error in Report Generation";
                break;
            case "178":
                message = "Error Code : 178  Reason :Rejected Qtyis less OR Value is less";
                break;
            case "180":
                message = "Error Code : 180  Reason :Invalid Delivery Status Indicator";
                break;
            case "182":
                message = "Error Code : 182  Reason :Order Value Exceeds the Max CapitalAdequacy.";
                break;
            case "183":
                message = "Error Code : 183  Reason :Please Restart the Application.";
                break;
            case "184":
                message = "Error Code : 184  Reason :System Does not Recognize You";
                break;
            case "185":
                message = "Error Code : 185  Reason :Member Reactivated";
                break;
            case "207":
                message = "Error Code : 207  Reason :You Can't Enter AllNone Orders";
                break;
            case "208":
                message = "Error Code : 208  Reason :You Can't Enter Limit Orders";
                break;
            case "209":
                message = "Error Code : 209  Reason :You Can't Enter Market Orders";
                break;
            case "210":
                message = "Error Code : 210  Reason :You Can't Enter Take Orders";
                break;
            case "211":
                message = "Error Code : 211  Reason :You Can't Enter Hit Orders";
                break;
            case "212":
                message = "Error Code : 212  Reason :You Can't Enter Quotes";
                break;
            case "213":
                message = "Error Code : 213  Reason :You Can't Enter Cross Deals";
                break;
            case "214":
                message = "Error Code : 214  Reason :You Can't Enter Block Deals";
                break;
            case "215":
                message = "Error Code : 215  Reason :RepMember is Inactive,can't trade";
                break;
            case "216":
                message = "Error Code : 216  Reason :Member is Inactive,can't trade";
                break;
            case "217":
                message = "Error Code : 217  Reason :User is Inactive,can't trade";
                break;
            case "218":
                message = "Error Code : 218  Reason :Your Password Has Expired";
                break;
            case "219":
                message = "Error Code : 219  Reason :Stop loss not allowed for Trader";
                break;
            case "220":
                message = "Error Code : 220  Reason :User not allowed for rectification.";
                break;
            case "221":
                message = "Error Code : 221  Reason :Invalid Qty for DMA users";
                break;
            case "222":
                message = "Error Code : 222  Reason :No Future Mkt Trading Rights";
                break;
            case "226":
                message = "Error Code : 226  Reason :F&O Trading Facility Unavailable";
                break;
            case "235":
                message = "Error Code : 235  Reason :Apply Successful Except Derivatives";
                break;
            case "239":
                message = "Error Code : 239  Reason :Order Value exceeding the avaialbe capital";
                break;
            case "243":
                message = "Error Code : 243  Reason :Hidden Qty Not Allowed For Call Auction.";
                break;
            case "246":
                message = "Error Code : 246  Reason :[Self Trade Prevention].";
                break;
            case "251":
                message = "Error Code : 251  Reason :No Quote With The Id";
                break;
            case "252":
                message = "Error Code : 252  Reason :No Order With The Id";
                break;
            case "253":
                message = "Error Code : 253  Reason :No Block-Deal With The Id";
                break;
            case "254":
                message = "Error Code : 254  Reason :No X-Deal With The Id";
                break;
            case "255":
                message = "Error Code : 255  Reason :Qty Not OK. Market Lot";
                break;
            case "256":
                message = "Error Code : 256  Reason :Rate Not OK. Tick ";
                break;
            case "257":
                message = "Error Code : 257  Reason :Qty is Less Than Floor Limit";
                break;
            case "258":
                message = "Error Code : 258  Reason :Qty is Greater Than Ceiling Limit";
                break;
            case "259":
                message = "Error Code : 259  Reason :Invalid retention";
                break;
            case "260":
                message = "Error Code : 260  Reason :Negative or no spread in quote";
                break;
            case "-261":
                message = "Error Code : -261  Reason :Accepted. Warning! Rate Beyond ";
                break;
            case "262":
                message = "Error Code : 262  Reason :Ckt Lt!Rate Exceeds";
                break;
            case "263":
                message = "Error Code : 263  Reason :Circuit breaker warning";
                break;
            case "264":
                message = "Error Code : 264  Reason :Circuit breaker limit";
                break;
            case "265":
                message = "Error Code : 265  Reason :Update deleted order with qty";
                break;
            case "-265":
                message = "Error Code : -265  Reason :Update deleted order with qty";
                break;
            case "266":
                message = "Error Code : 266  Reason :Invalid selection of Buy or Sell";
                break;
            case "267":
                message = "Error Code : 267  Reason :Quote already exists";
                break;
            case "268":
                message = "Error Code : 268  Reason :Rate Not Equal To Close Rate";
                break;
            case "269":
                message = "Error Code : 269  Reason :Only EOSESS Retention Valid";
                break;
            case "270":
                message = "Error Code : 270  Reason :Spread Cannot Exceed Times Tick";
                break;
            case "272":
                message = "Error Code : 272  Reason :MinQty Not In MktLot";
                break;
            case "273":
                message = "Error Code : 273  Reason :MinQty > Total Qty";
                break;
            case "274":
                message = "Error Code : 274  Reason :MinQty Less Than 0";
                break;
            case "275":
                message = "Error Code : 275  Reason :EOSCktLt Rt. Exceeds";
                break;
            case "276":
                message = "Error Code : 276  Reason :No All None Order With The Id";
                break;
            case "278":
                message = "Error Code : 278  Reason :Trig. Rate is > Limit Rate for Buy Ord";
                break;
            case "279":
                message = "Error Code : 279  Reason :Limit Rate is > Trig. Rate for Sell Ord";
                break;
            case "280":
                message = "Error Code : 280  Reason :Rate Not Within Circuits Limits";
                break;
            case "281":
                message = "Error Code : 281  Reason :EOSCktLt Rt. Exceeds";
                break;
            case "282":
                message = "Error Code : 282  Reason :Revealed qty < % of Total qty";
                break;
            case "283":
                message = "Error Code : 283  Reason :Invalid Revealed Qty";
                break;
            case "284":
                message = "Error Code : 284  Reason :Drip not Allowed.Qty< Val<L";
                break;
            case "286":
                message = "Error Code : 286  Reason :Order is under process by the Matching Engine";
                break;   // Priya on 3July2013
            case "300":
                message = "Error Code : 300  Reason :Breakup not allowed for scripcode";
                break;
            case "301":
                message = "Error Code : 301  Reason :Trader not allowed for breakup";
                break;
            case "302":
                message = "Error Code : 302  Reason :Purchase Delivery < Previous Day's";
                break;
            case "303":
                message = "Error Code : 303  Reason :Sell Delivery < Previous Day's";
                break;
            case "304":
                message = "Error Code : 304  Reason :Share Badla > Previous Day's";
                break;
            case "305":
                message = "Error Code : 305  Reason :Net Position Mismatch";
                break;
            case "306":
                message = "Error Code : 306  Reason :Vyaj Badla cannot be changed";
                break;
            case "307":
                message = "Error Code : 307  Reason :Error in client code";
                break;
            case "308":
                message = "Error Code : 308  Reason :Share Badla not editable";
                break;
            case "309":
                message = "Error Code : 309  Reason :Carry forward not allowed";
                break;
            case "310":
                message = "Error Code : 310  Reason :Purchase Delivery < Today's";
                break;
            case "311":
                message = "Error Code : 311  Reason :Sell Delivery < Today's";
                break;
            case "312":
                message = "Error Code : 312  Reason :Carry Forward not allowed for FI";
                break;
            case "314":
                message = "Error Code : 314  Reason :ErrInNb_C";
                break;
            case "315":
                message = "Error Code : 315  Reason :Share Badla Position Mismatch";
                break;
            case "316":
                message = "Error Code : 316  Reason :Purchase Delivery < Yesterday's";
                break;
            case "317":
                message = "Error Code : 317  Reason :Sell Delivery < Yesterday's";
                break;
            case "318":
                message = "Error Code : 318  Reason :Net Position Exceeds Gross Position";
                break;
            case "319":
                message = "Error Code : 319  Reason :Phy Component Not Allowed in Fully DMAT";
                break;
            case "320":
                message = "Error Code : 320  Reason :Quantity Cannot be Equal To MktLot";
                break;
            case "321":
                message = "Error Code : 321  Reason :Quantity Exceeds Ceiling";
                break;
            case "351":
                message = "Error Code : 351  Reason :Invalid message type";
                break;
            case "352":
                message = "Error Code : 352  Reason :Unknown error";
                break;
            case "353":
                message = "Error Code : 353  Reason :Error in Acc table";
                break;
            case "354":
                message = "Error Code : 354  Reason :Query failed try again";
                break;
            case "361":
                message = "Error Code : 361  Reason :Invalid user";
                break;
            case "362":
                message = "Error Code : 362  Reason :Invalid session";
                break;
            case "363":
                message = "Error Code : 363  Reason :Market Halted";
                break;
            case "364":
                message = "Error Code : 364  Reason :Invalid Newsid";
                break;
            case "365":
                message = "Error Code : 365  Reason :Unauthorised member";
                break;
            case "366":
                message = "Error Code : 366  Reason :Scrip is present on BroadCast.";
                break;
            case "368":
                message = "Error Code : 368  Reason :Invalid News Category";
                break;
            case "371":
                message = "Error Code : 371  Reason :Invalid Request time";
                break;
            case "372":
                message = "Error Code : 372  Reason :Invalid Request Count";
                break;
            case "373":
                message = "Error Code : 373  Reason :Invalid Request";
                break;
            case "401":
                message = "Error Code : 401  Reason :opening session message = error";
                break;
            case "451":
                message = "Error Code : 451  Reason :Accepted crossed deal";
                break;
            case "453":
                message = "Error Code : 453  Reason :AllNone Order Added Successfully";
                break;
            case "454":
                message = "Error Code : 454  Reason :AllNone Order Updated Successfully";
                break;
            case "455":
                message = "Error Code : 455  Reason :AllNone Order Deleted Successfully";
                break;
            case "500":
                message = "Error Code : 500  Reason :Buyer And Defaulter Are Equal";
                break;
            case "501":
                message = "Error Code : 501  Reason :Buyer Not Valid";
                break;
            case "502":
                message = "Error Code : 502  Reason :Defaulter Invalid";
                break;
            case "503":
                message = "Error Code : 503  Reason :Scripcode is Invalid";
                break;
            case "504":
                message = "Error Code : 504  Reason :Qty. Not Multiple Of MktLot";
                break;
            case "505":
                message = "Error Code : 505  Reason :Buyer And Defaulter Are Invalid";
                break;
            case "506":
                message = "Error Code : 506  Reason :Incorrect Settlement No Specified";
                break;
            case "507":
                message = "Error Code : 507  Reason :Invalid Hawalarate Specified";
                break;
            case "508":
                message = "Error Code : 508  Reason :Group Name Mismatch";
                break;
            case "509":
                message = "Error Code : 509  Reason :Settlement No Not Valid";
                break;
            case "510":
                message = "Error Code : 510  Reason :Duplicate entry exits";
                break;
            case "511":
                message = "Error Code : 511  Reason :Inconsistent Hawalarate";
                break;
            case "512":
                message = "Error Code : 512  Reason :Buy Bankcode Invalid";
                break;
            case "513":
                message = "Error Code : 513  Reason :Sell Bankcode Invalid";
                break;
            case "514":
                message = "Error Code : 514  Reason :Group Name Not Up For Auction";
                break;
            case "515":
                message = "Error Code : 515  Reason :No match in HIGHRATE for code";
                break;
            case "516":
                message = "Error Code : 516  Reason :Invalid BDC Number for code";
                break;
            case "517":
                message = "Error Code : 517  Reason :Invalid Entitlement type";
                break;
            case "518":
                message = "Error Code : 518  Reason :Invalid Entitlement rate";
                break;
            case "523":
                message = "Error Code : 523  Reason :Error in reading Auction session";
                break;
            case "531":
                message = "Error Code : 531  Reason :Quantity Exceeds Auction Qty";
                break;
            case "532":
                message = "Error Code : 532  Reason :Rate  Exceeds Cut Off Rate";
                break;
            case "533":
                message = "Error Code : 533  Reason :You Can't Enter Offers";
                break;
            case "534":
                message = "Error Code : 534  Reason :Defaulter Can't Enter Offers";
                break;
            case "535":
                message = "Error Code : 535  Reason :No Offer With The Id";
                break;
            case "536":
                message = "Error Code : 536  Reason :Invalid Notice Number";
                break;
            case "537":
                message = "Error Code : 537  Reason :Offer Rate Not Within Permissible Limit";
                break;
            case "539":
                message = "Error Code : 539  Reason :Invalid delivery Type";
                break;
            case "540":
                message = "Error Code : 540  Reason :ClientId cannot be blank for CDS";
                break;
            case "541":
                message = "Error Code : 541  Reason :ClientId cannot Be Null/Blank";
                break;
            case "542":
                message = "Error Code : 542  Reason :Incorrect ClientID or ClientType";
                break;
            case "592":
                message = "Error Code : 592  Reason :Only Limit / Market Order Allowed for RRM";
                break;
            case "593":
                message = "Error Code : 593  Reason :RRM orders not allowed on T-T securities";
                break;
            case "594":
                message = "Error Code : 594  Reason :Not a risk reducing order";
                break;
            case "595":
                message = "Error Code : 595  Reason :Only single RRM order accepted for scrip/client";
                break;
            case "598":
                message = "Error Code : 598  Reason :Update Not allowed before confirmation";
                break;
            case "599":
                message = "Error Code : 599  Reason :Not a risk reducing order";
                break;
            case "600":
                message = "Error Code : 600  Reason :You can't enter Odd-Lot Orders";
                break;
            case "601":
                message = "Error Code : 601  Reason :Scrip not allowed for oddlot trading";
                break;
            case "602":
                message = "Error Code : 602  Reason :Quantity or Rate mismatch";
                break;
            case "603":
                message = "Error Code : 603  Reason :Invalid rate entered";
                break;
            case "604":
                message = "Error Code : 604  Reason :Invalid qty  entered";
                break;
            case "605":
                message = "Error Code : 605  Reason :Grab Unsuccessful";
                break;
            case "606":
                message = "Error Code : 606  Reason :Mktlot not allowed in Oddlot trading";
                break;
            case "607":
                message = "Error Code : 607  Reason :Trader not allowed for Sunshine";
                break;
            case "608":
                message = "Error Code : 608  Reason :Scrip not allowed for Sunshine";
                break;
            case "609":
                message = "Error Code : 609  Reason :SunShine Grab not Successful";
                break;
            case "610":
                message = "Error Code : 610  Reason :SunShine ID not Valid";
                break;
            case "611":
                message = "Error Code : 611  Reason :Scrip not allowed for sunshine tradin";
                break;
            case "612":
                message = "Error Code : 612  Reason :Min Qty cannot exceed MaxLimit";
                break;
            case "613":
                message = "Error Code : 613  Reason :MinGrbQty greater than actual Order Qty";
                break;
            case "651":
                message = "Error Code : 651  Reason :Bank code is invalid";
                break;
            case "652":
                message = "Error Code : 652  Reason :Record Already Confirmed";
                break;
            case "653":
                message = "Error Code : 653  Reason :Record is of last settlement";
                break;
            case "654":
                message = "Error Code : 654  Reason :Record with id not found";
                break;
            case "655":
                message = "Error Code : 655  Reason :Date is invalid";
                break;
            case "656":
                message = "Error Code : 656  Reason :Invalid character in Buy or Sell";
                break;
            case "657":
                message = "Error Code : 657  Reason :User Not Allowed for this transaction";
                break;
            case "658":
                message = "Error Code : 658  Reason :Trading Limit Download Failed";
                break;
            case "659":
                message = "Error Code : 659  Reason :Trading Limit Update Failed";
                break;
            case "660":
                message = "Error Code : 660  Reason :Auction Update Failed";
                break;
            case "661":
                message = "Error Code : 661  Reason :Badla Update Failed";
                break;
            case "662":
                message = "Error Code : 662  Reason :Unauthorized Trader! Trader 1 allowed";
                break;
            case "666":
                message = "Error Code : 666  Reason :Scrip not allowed for 6A/7A";
                break;
            case "667":
                message = "Error Code : 667  Reason :Duplicate scrip";
                break;
            case "668":
                message = "Error Code : 668  Reason :Scrip not present";
                break;
            case "669":
                message = "Error Code : 669  Reason :Limit of 50 scrip exceeded";
                break;
            case "670":
                message = "Error Code : 670  Reason :Transaction not allowed";
                break;
            case "671":
                message = "Error Code : 671  Reason :Not allowed for BUY";
                break;
            case "672":
                message = "Error Code : 672  Reason :Not allowed for SELL";
                break;
            case "677":
                message = "Error Code : 677  Reason :Error! Current Settlement not Scheduled";
                break;
            case "712":
                message = "Error Code : 712  Reason :Logon Successful";
                break;
            case "740":
                message = "Error Code : 740  Reason :No Basket Order with the Id.";
                break;
            case "741":
                message = "Error Code : 741  Reason :Basket Order Already Deleted.";
                break;
            case "742":
                message = "Error Code : 742  Reason :Bskt Ord was not Added Successfully.";
                break;
            case "743":
                message = "Error Code : 743  Reason :Bskt Ord Already Marked for Deletion";
                break;
            case "744":
                message = "Error Code : 744  Reason :Basket Code not found in Master.";
                break;
            case "745":
                message = "Error Code : 745  Reason :Basket Order Addition in Progress.";
                break;
            case "746":
                message = "Error Code : 746  Reason :Basket Order Deletion in Progress.";
                break;
            case "747":
                message = "Error Code : 747  Reason :Less scrip in Packet than Required.";
                break;
            case "748":
                message = "Error Code : 748  Reason :Total scrips do not match NumShares";
                break;
            case "760":
                message = "Error Code : 760  Reason :Relisted Scrip -Not Traded Today";
                break;
            case "768":
                message = "Error Code : 768  Reason :Not Allowed -Not Traded Today";
                break;
            case "769":
                message = "Error Code : 769  Reason :Exercise Qty Greater Than Position Qty7.";
                break;
            case "770":
                message = "Error Code : 770  Reason :Open Postion Does Not Exists.";
                break;
            case "771":
                message = "Error Code : 771  Reason :Invalid CaClass for BUY FI OR Exercise Allowed Only On Expiry Day";
                break;
            case "772":
                message = "Error Code : 772  Reason :Invalid CaClass for SELL FI OR Not Valid For Option Exercise.";
                break;
            case "775":
                message = "Error Code : 775  Reason :Invalid CaClass For Buy";
                break;
            case "776":
                message = "Error Code : 776  Reason :Invalid CaClass For Sell";
                break;
            //	case "777": message = "Error Code : 777  Reason :Invalid CaClass."; break;
            case "780":
                message = "Error Code : 780  Reason :Trade already converted";
                break;
            case "781":
                message = "Error Code : 781  Reason :6A/7A Entry not allowed for this Scrip";
                break;
            case "783":
                message = "Error Code : 783  Reason :Transaction Could not Completed";
                break;
            case "787":
                message = "Error Code : 783  Reason :OPTION EXERCISE NOT ALLOWED.";
                break;
            case "-784":
                message = "Error Code : -784  Reason :RRM order accepted successfully for check";
                break;
            case "791":
                message = "Error Code : 791  Reason :Client not Registered.";
                break;
            case "796":
                message = "Error Code : 796  Reason :Scrip Not Part Of Call Auction";
                break;
            case "807":
                message = "Error Code : 807  Reason :Not allowed for Oddlot";
                break;
            case "816":
                message = "Error Code : 816  Reason :Tws Locked Contact admin.";
                break;
//#ifdef   BSE_INST_CLI
            case "777":
                message = "Error Code : 777  Reason :Invalid Client Type.";
                break;
            case "818":
                message = "Error Code : 818  Reason :For OWN, Invalid Client Code/ID";
                break;
            case "840":
                message = "Error Code : 840  Reason :Disallowed within Non-INST type";
                break;
            case "845":
                message = "Error Code : 845  Reason :Non-INST to INST disallowed";
                break;
            case "842":
                message = "Error Code : 842  Reason :No OWN trading rights for the Trader";
                break;
//#else
            //case "777": message = "Error Code : 777  Reason :Invalid CaClass."; break;
            //case "818": message = "Error Code : 818  Reason :Invalid Client for OWN CaClass"; break;
            //case "840": message = "Error Code : 840  Reason :Disallowed for NonInstitutional Client"; break;
            //case "845": message = "Error Code : 845  Reason :NONFI to FI not allowed"; break;
            //case "842": message = "Error Code : 842  Reason :OWN type not allowed for the trader"; break;
//#endif	BSE_INST_CLI
            case "820":
                message = "Error Code : 820  Reason :Trader not allowed to Trade on group";
                break;
            case "826":
                message = "Error Code : 826  Reason :Not a Valid Terminal";
                break;
            case "831":
                message = "Error Code : 831  Reason :Not Allowed for Registartion";
                break;
            //	case "840": message = "Error Code : 840  Reason :Disallowed for NonInstitutional Client"; break;
            case "841":
                message = "Error Code : 841  Reason :Sell not allowed for this Institution";
                break;
            //	case "842": message = "Error Code : 842  Reason :OWN type not allowed for the trader"; break;
            case "843":
                message = "Error Code : 843  Reason :No Attributes updated";
                break;
            //	case "845": message = "Error Code : 845  Reason :NONFI to FI not allowed"; break;
            case "846":
                message = "Error Code : 846  Reason :Rectification Within NONFI disallowed";
                break;
            case "847":
                message = "Error Code : 847  Reason :Invalid Market Protection";
                break;
            case "848":
                message = "Error Code : 848  Reason :Invalid LocationId";
                break;
            case "849":
                message = "Error Code : 849  Reason :Debarred";
                break;
            case "850":
                message = "Error Code : 850  Reason : Debarred";
                break;
            case "851":
                message = "Error Code : 851  Reason :Trader & Scrip should not be blank";
                break;
            case "852":
                message = "Error Code : 852  Reason :NRI,Client,OWN Cannot Be Changed";
                break;
            case "853":
                message = "Error Code : 853  Reason :Disallowed for NRI,OWN and CLIENT";
                break;
            case "854":
                message = "Error Code : 854  Reason :Selling allowed for QFI only";
                break;
            case "855":
                message = "Error Code : 855  Reason :QFI not allowed for F&O & DEBT";
                break;
            case "856":
                message = "Error Code : 856  Reason :Client type can.t be QFI for F&O & DEBT";
                break;
            case "857":
                message = "Error Code : 857  Reason :FI to NON-FI not allowed";
                break;
            case "860":
                message = "Error Code : 860  Reason :Only FIIs allowed to Trade in I group";
                break;
            case "861":
                message = "Error Code : 861  Reason :ClientType mod. Not allowed for I group";
                break;
            case "862":
                message = "Error Code : 862  Reason :6A/7A process not allowed for I group";
                break;
            case "888":
                message = "Error Code : 888  Reason :Change disallowed for deleted terminal";
                break;
            case "889":
                message = "Error Code : 889  Reason :Invalid character in Textfiled";
                break;
            case "890":
                message = "Error Code : 890  Reason :Invalid Program Trading";
                break;
            case "902":
                message = "Error Code : 902  Reason :Scrip Does not Exist";
                break;
            case "985":
                message = "Error Code : 985  Reason :Can.t modify Client. Partially Traded";
                break;
            case "986":
                message = "Error Code : 986  Reason :Not a risk reducing order";
                break;
            case "987":
                message = "Error Code : 987  Reason :Only single RRM order accepted for scrip/client for MWPL RRM.";
                break;
            case "988":
                message = "Error Code : 988  Reason :Only Limit / Market Order Allowed for MWPL RRM";
                break;
            case "989":
                message = "Error Code : 989  Reason :Update Not allowed before confirmation for MWPL order";
                break;
            case "-5":
                message = "Error Code : -5  Reason :Invalid NNF field";
                break;
            case "-2":
                message = "Error Code : -2  Reason :Trade Modifcation With Diffrent Exchange RetailerID not allowed";
                break;
            case "10000":
                message = "Error Code : 10000 Reason :Order not found";
                break;
            case "10001":
                message = "Error Code : 10001 The Price is out of the current execution range.";
                break;
            default: {
                message = "Unknown Error Code ";
                break;
            }
        }
        return message;
    }


    public short getBitResult(short orderFlag) {

        short bitRes = (short) ((orderFlag >> 6) & 0x1);
        //Log.e("Bitwise Result : ", String.valueOf(bitRes));
      /*  if(String.valueOf(bitRes).equals("1")) {
            return bitRes;
        } else if(String.valueOf(bitRes).equals("0")) {
            return bitRes;
        }*/
        return bitRes;
    }

    public short getDayResult(short orderFlag) {

        short bitRes = (short) ((orderFlag >> 3) & 0x1);
        //Log.e("Bitwise Result : ", String.valueOf(bitRes));
      /*  if(String.valueOf(bitRes).equals("1")) {
            return bitRes;
        } else if(String.valueOf(bitRes).equals("0")) {
            return bitRes;
        }*/
        return bitRes;
    }

    public short getIOCResult(short orderFlag) {

        short bitRes = (short) ((orderFlag >> 1) & 0x1);
        //Log.e("Bitwise Result : ", String.valueOf(bitRes));
      /*  if(String.valueOf(bitRes).equals("1")) {
            return bitRes;
        } else if(String.valueOf(bitRes).equals("0")) {
            return bitRes;
        }*/
        return bitRes;
    }

    public short getGTCResult(short orderFlag) {

        short bitRes = (short) ((orderFlag >> 2) & 0x1);
        //Log.e("Bitwise Result : ", String.valueOf(bitRes));
      /*  if(String.valueOf(bitRes).equals("1")) {
            return bitRes;
        } else if(String.valueOf(bitRes).equals("0")) {
            return bitRes;
        }*/
        return bitRes;
    }

    public short getEOSResult(short orderFlag) {

        short bitRes = (short) ((orderFlag >> 0) & 0x1);
        //Log.e("Bitwise Result : ", String.valueOf(bitRes));
      /*  if(String.valueOf(bitRes).equals("1")) {
            return bitRes;
        } else if(String.valueOf(bitRes).equals("0")) {
            return bitRes;
        }*/
        return bitRes;
    }


    public void showdissmisssheet() {
        PlaceOrderBottomSheetFragment placeOrderBottomSheetFragment = new PlaceOrderBottomSheetFragment();
        placeOrderBottomSheetFragment.setArguments(AccountDetails.globalPlaceOrderBundle);
        placeOrderBottomSheetFragment.show(getSupportFragmentManager(), placeOrderBottomSheetFragment.getTag());
    }

    public void showOrderPreviewSheet() {
        OrderPreviewBottomSheetFragment orderPreviewBottomSheetFragment = new OrderPreviewBottomSheetFragment();
        orderPreviewBottomSheetFragment.setArguments(AccountDetails.globalPlaceOrderBundle);
        orderPreviewBottomSheetFragment.show(getSupportFragmentManager(), orderPreviewBottomSheetFragment.getTag());
    }

   /* private void showOrderPreview() {
        TradePreviewFragment tradePreviewFragment = new TradePreviewFragment();
        tradePreviewFragment.setArguments(AccountDetails.globalPlaceOrderBundle);
        tradePreviewFragment.show(getSupportFragmentManager(), tradePreviewFragment.getTag());
    }*/

    public void showNetpositionSheet() {
        NetPositionBottomSheetFragment netPositionBottomSheetFragment = new NetPositionBottomSheetFragment();
        netPositionBottomSheetFragment.setArguments(AccountDetails.globalPlaceOrderBundle);
        netPositionBottomSheetFragment.show(getSupportFragmentManager(), netPositionBottomSheetFragment.getTag());
    }

    public void showHoldingDetail() {
        HoldingBottomSheetFragment holdingBottomSheetFragment = new HoldingBottomSheetFragment();
        if (!AccountDetails.getIsholdingbottomsheetshow()) {
            AccountDetails.setIsholdingbottomsheetshow(true);
            holdingBottomSheetFragment.setArguments(AccountDetails.globalPlaceOrderBundle);
            holdingBottomSheetFragment.show(getSupportFragmentManager(), holdingBottomSheetFragment.getTag());
        }
    }


    private void sendStreamingRequest(String type) {

        if (streamingList != null) {
            if (streamingList.size() > 0) {
                streamController.pauseStreaming(this, type, streamingList);
            }
        }

        streamController.sendStreamingRequest(this, streamingList, type, null, null, false);
        addToStreamingList(type, streamingList);
    }


    private boolean isOutReported = false;

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();

        // Check if the image view is out of the parent view and report it if it is.
        // Only report once the image goes out and don't stack toasts.
        if (isOut(view)) {
            if (!isOutReported) {
                isOutReported = true;
                Toast.makeText(this, "OUT", Toast.LENGTH_SHORT).show();
            }
        } else {
            isOutReported = false;
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // _xDelta and _yDelta record how far inside the view we have touched. These
                // values are used to compute new margins when the view is moved.
                _xDelta = X - view.getLeft();
                _yDelta = Y - view.getTop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                // Do nothing
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                // Image is centered to start, but we need to unhitch it to move it around.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    lp.removeRule(RelativeLayout.CENTER_HORIZONTAL);
                    lp.removeRule(RelativeLayout.CENTER_VERTICAL);
                } else {
                    lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                    lp.addRule(RelativeLayout.CENTER_VERTICAL, 0);
                }
                lp.leftMargin = X - _xDelta;
                lp.topMargin = Y - _yDelta;
                // Negative margins here ensure that we can move off the screen to the right
                // and on the bottom. Comment these lines out and you will see that
                // the image will be hemmed in on the right and bottom and will actually shrink.
                lp.rightMargin = view.getWidth() - lp.leftMargin - windowwidth;
                lp.bottomMargin = view.getHeight() - lp.topMargin - windowheight;
                view.setLayoutParams(lp);
                break;
        }
        // invalidate is redundant if layout params are set or not needed if they are not set.
//        mRrootLayout.invalidate();
        return true;
    }

    private boolean isOut(View view) {
        // Check to see if the view is out of bounds by calculating how many pixels
        // of the view must be out of bounds to and checking that at least that many
        // pixels are out.
        float percentageOut = 0.50f;
        int viewPctWidth = (int) (view.getWidth() * percentageOut);
        int viewPctHeight = (int) (view.getHeight() * percentageOut);

        return ((-view.getLeft() >= viewPctWidth) ||
                (view.getRight() - windowwidth) > viewPctWidth ||
                (-view.getTop() >= viewPctHeight) ||
                (view.getBottom() - windowheight) > viewPctHeight);
    }

    public void checkClientPOSStatus() {

        String url = "getClientPOAStatus?gscid=" + AccountDetails.getUsername(getApplicationContext());
        WSHandler.getRequest(getApplicationContext(), url, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {

                try {
                    Log.e("getClientPOAStatus", "" + response);
                    String defaultpoa = "", DPId = "";
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        defaultpoa = json.getString("DefaultPOA");
                        if (json.has("DPId")) {
                            DPId = json.getString("DPId");
                            AccountDetails.setPOADPId(DPId);
                        }
                    }

                    if (defaultpoa.equalsIgnoreCase("YES")) {
                        AccountDetails.setClientPOA(true);
                    } else {
                        AccountDetails.setClientPOA(false);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String message) {

                Log.e("getClientPOAStatus", message);
            }

        });

    }

    private void showPoaPopup() {

        LayoutInflater factory = LayoutInflater.from(GreekBaseActivity.this);
        final View poaDialogView = factory.inflate(R.layout.poapopup_layout, null);
        final AlertDialog dialog = new AlertDialog.Builder(GreekBaseActivity.this).create();
        dialog.setView(poaDialogView);

        LinearLayout parent_layout = poaDialogView.findViewById(R.id.parent_layout);
        GreekTextView dmessage = poaDialogView.findViewById(R.id.dmessage);
        ImageView poalogo=poaDialogView.findViewById(R.id.poalogo);
        if (GreekBaseActivity.GREEK.equalsIgnoreCase("Vishwas")){
            poalogo.setBackground(getResources().getDrawable(R.drawable.vishwas_login_icon));
        }else{
            poalogo.setBackground(getResources().getDrawable(R.drawable.philips_login_icon));
        }
        if (AccountDetails.getThemeFlag(GreekBaseActivity.this).equalsIgnoreCase("white")) {
            parent_layout.setBackgroundColor(getResources().getColor(R.color.white));
            dmessage.setTextColor(getResources().getColor(R.color.black));
        }

        poaDialogView.findViewById(R.id.btn_proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                dialog.dismiss();
                showAuthorizePopup();
            }
        });
        poaDialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    private void showAuthorizePopup() {
        LayoutInflater factory = LayoutInflater.from(GreekBaseActivity.this);
        final View authDialogView = factory.inflate(R.layout.authorizepopup_layout, null);
        final AlertDialog dialog = new AlertDialog.Builder(GreekBaseActivity.this).create();
        dialog.setView(authDialogView);
        LinearLayout parent_layout = authDialogView.findViewById(R.id.parent_layout);
        GreekTextView username = authDialogView.findViewById(R.id.username);
        GreekTextView username_txt = authDialogView.findViewById(R.id.username_txt);
        GreekTextView headerTitle = authDialogView.findViewById(R.id.headerTitle);
        GreekTextView subtitle = authDialogView.findViewById(R.id.subtitle);
        ImageView imgProfile = authDialogView.findViewById(R.id.imgProfile);
        username.setText(AccountDetails.getUsername(GreekBaseActivity.this));
        username_txt.setText(AccountDetails.getCLIENTNAME(GreekBaseActivity.this));


        if (AccountDetails.getThemeFlag(GreekBaseActivity.this).equalsIgnoreCase("white")) {
            parent_layout.setBackgroundColor(getResources().getColor(R.color.white));
            username.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            username_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            headerTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            subtitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            imgProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black_24dp));

        }


        authDialogView.findViewById(R.id.btn_to_cdsl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                dialog.dismiss();

                String gscid = AccountDetails.getUsername(GreekBaseActivity.this);
                String PAN = AccountDetails.getUserPAN(GreekBaseActivity.this);
                String deviceId = Settings.Secure.getString(GreekBaseActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                String dPType = "CDSL";
                String ReqFlag = "Y";
                String BOID = AccountDetails.getPOADPId();

                MarketsSingleScripRequest.sendRequest(gscid, dPType, ReqFlag, BOID, PAN, deviceId, GreekBaseActivity.this, serviceResponseHandler);

            }
        });
        authDialogView.findViewById(R.id.btn_authorization).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                showAuthorizeTransectionPopup();

            }
        });
        authDialogView.findViewById(R.id.forgot_tpin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                String genratepin = "http://mockedis.cdslindia.com/home/generatepin";
                String changepin = "http://mockedis.cdslindia.com/home/changepin";

                Intent intent = new Intent(GreekBaseActivity.this, WebContent.class);
                intent.putExtra("AtomRequest", changepin);
                intent.putExtra("uniqueid", "");
                intent.putExtra("amt", "");
                startActivityForResult(intent, 2);
                AccountDetails.setIsMainActivity(true);
            }
        });

        dialog.show();

    }

    public void onEventMainThread(EDISHoldingInfoResponse streamingResponse) {

        try {
            // refreshComplete();
            ArrayList<EDISHoldingInfoResponse.Response.Data.StockDetail> stockArrayList =
                    streamingResponse.getResponse().getData().getStockDetails();

            int noofrecords = Integer.parseInt(streamingResponse.getResponse().getData().getNoofrecords());
            int islast = Integer.parseInt(streamingResponse.getResponse().getData().getIslast());

//            Log.e("Greekbase", "Greekbase noofrecords====>>" + noofrecords);
//            Log.e("Greekbase", "Greekbase islast====>>" + islast);

            if (sell_transection_list != null) {
                if (islast == 1 || stockArrayList.size() == noofrecords) {
                    // Vanish previous records and show current records ( this is the 1st package )

                    if (noofrecords > 0) {
                        edisHoldingdatalist = new ArrayList<>();

                        for (int i = 0; i < stockArrayList.size(); i++) {

                            AuthTransectionModel.AgeingTransData ageingTransData = new AuthTransectionModel.AgeingTransData();

                            ageingTransData.setHQty(stockArrayList.get(i).getHQty());
                            ageingTransData.setSymbol(stockArrayList.get(i).getSymbol());
                            ageingTransData.setInstrument(stockArrayList.get(i).getInstrument());
                            ageingTransData.setIsin(stockArrayList.get(i).getIsin());
                            ageingTransData.setToken(stockArrayList.get(i).getToken());
                            ageingTransData.setInstrumentSelection(false);
                            ageingTransData.setAuthQuantity(stockArrayList.get(i).getHQty());
                            ageingTransData.setOpenAuthQty(stockArrayList.get(i).getOpenAuthQty());
                            ageingTransData.setTodayAuthQty(stockArrayList.get(i).getTodayAuthQty());
                            ageingTransData.setTodaySoldQty(stockArrayList.get(i).getTodaySoldQty());
                            ageingTransData.setClose(stockArrayList.get(i).getClose());
                            edisHoldingdatalist.add(ageingTransData);

                        }

                        if (edisHoldingdatalist != null && edisHoldingdatalist.size() > 0) {
                            empty_view.setVisibility(View.GONE);
                            sell_transection_list.setVisibility(View.VISIBLE);

                            final AuthorizeTransAdapter adapter = new AuthorizeTransAdapter(GreekBaseActivity.this, edisHoldingdatalist);
                            ((SimpleItemAnimator) sell_transection_list.getItemAnimator()).setSupportsChangeAnimations(false);
                            sell_transection_list.setAdapter(adapter);
                            sell_transection_list.notifyAll();

                        } else {
                            empty_view.setVisibility(View.VISIBLE);
                            sell_transection_list.setVisibility(View.GONE);
                        }
                    } else {
                        empty_view.setVisibility(View.VISIBLE);
                        sell_transection_list.setVisibility(View.GONE);
                    }

                } else {

                    //Appends new records with previous already exists records.

                    if (edisHoldingdatalist != null) {

                        for (int i = 0; i < stockArrayList.size(); i++) {

                            AuthTransectionModel.AgeingTransData ageingTransData = new AuthTransectionModel.AgeingTransData();

                            ageingTransData.setHQty(stockArrayList.get(i).getHQty());
                            ageingTransData.setSymbol(stockArrayList.get(i).getSymbol());
                            ageingTransData.setInstrument(stockArrayList.get(i).getInstrument());
                            ageingTransData.setIsin(stockArrayList.get(i).getIsin());
                            ageingTransData.setToken(stockArrayList.get(i).getToken());
                            ageingTransData.setInstrumentSelection(false);
                            ageingTransData.setAuthQuantity(stockArrayList.get(i).getHQty());
                            ageingTransData.setOpenAuthQty(stockArrayList.get(i).getOpenAuthQty());
                            ageingTransData.setTodayAuthQty(stockArrayList.get(i).getTodayAuthQty());
                            ageingTransData.setTodaySoldQty(stockArrayList.get(i).getTodaySoldQty());
                            ageingTransData.setClose(stockArrayList.get(i).getClose());
                            edisHoldingdatalist.add(ageingTransData);
                        }
                    }


                    if (edisHoldingdatalist != null && edisHoldingdatalist.size() > 0) {
                        empty_view.setVisibility(View.GONE);
                        sell_transection_list.setVisibility(View.VISIBLE);

                        final AuthorizeTransAdapter adapter = new AuthorizeTransAdapter(GreekBaseActivity.this, edisHoldingdatalist);
                        ((SimpleItemAnimator) sell_transection_list.getItemAnimator()).setSupportsChangeAnimations(false);
                        sell_transection_list.setAdapter(adapter);

                    } else {
                        empty_view.setVisibility(View.VISIBLE);
                        sell_transection_list.setVisibility(View.GONE);
                    }

                }
            }


        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }


    }

    private void showAuthorizeTransectionPopup() {

        LayoutInflater factory = LayoutInflater.from(GreekBaseActivity.this);
        final View authDialogView = factory.inflate(R.layout.authorizetrancection_layout, null);
        final AlertDialog dialog = new AlertDialog.Builder(GreekBaseActivity.this).create();

        dialog.setView(authDialogView);
        prc_to_cdsl = authDialogView.findViewById(R.id.prc_to_cdsl);
        prc_to_cdsl.setEnabled(false);
        prc_to_cdsl.setBackground(getResources().getDrawable(R.drawable.single_line_border_bajaj_gray));
        LinearLayoutManager linearLayOutExeption = new LinearLayoutManager(GreekBaseActivity.this, LinearLayoutManager.VERTICAL, false);
        sell_transection_list = authDialogView.findViewById(R.id.sell_transection_list);
        empty_view = authDialogView.findViewById(R.id.empty_view);
        LinearLayout mainlayout = authDialogView.findViewById(R.id.mainlayout);
        GreekTextView instrument_txt = authDialogView.findViewById(R.id.instrument_txt);
        GreekTextView headerTitle = authDialogView.findViewById(R.id.headerTitle);
        GreekTextView qty_txt = authDialogView.findViewById(R.id.qty_txt);
        GreekTextView freeqty_txt = authDialogView.findViewById(R.id.freeqty_txt);
        GreekTextView qty_to_authorize_edittxt = authDialogView.findViewById(R.id.qty_to_authorize_edittxt);
        GreekTextView bottomTXT = authDialogView.findViewById(R.id.bottomTXT);

        CheckBox Allauthrize_checkbox = authDialogView.findViewById(R.id.Allauthrize_checkbox);
        sell_transection_list.setLayoutManager(linearLayOutExeption);
        sell_transection_list.setHasFixedSize(true);

        if (AccountDetails.getThemeFlag(GreekBaseActivity.this).equalsIgnoreCase("white")) {
            mainlayout.setBackgroundColor(getResources().getColor(R.color.white));
            headerTitle.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            instrument_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            freeqty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qty_to_authorize_edittxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            bottomTXT.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            empty_view.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        }

        // sendRequestForhHolding();
        orderStreamingController.sendEDISHoldingInfoRequest(getApplicationContext(),
                AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getUsername(getApplicationContext())); // This is for TCP Request


        Allauthrize_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                final AuthorizeTransAdapter adapter = new AuthorizeTransAdapter(GreekBaseActivity.this, edisHoldingdatalist);
                ((SimpleItemAnimator) sell_transection_list.getItemAnimator()).setSupportsChangeAnimations(false);
                sell_transection_list.setAdapter(adapter);
                adapter.checkALlStock(b);
            }
        });

//        if(edisHoldingdatalist != null && edisHoldingdatalist.size() > 0) {
//            empty_view.setVisibility(View.GONE);
//            sell_transection_list.setVisibility(View.VISIBLE);
//
//            final AuthorizeTransAdapter adapter = new AuthorizeTransAdapter(GreekBaseActivity.this, edisHoldingdatalist);
//            ((SimpleItemAnimator) sell_transection_list.getItemAnimator()).setSupportsChangeAnimations(false);
//            sell_transection_list.setAdapter(adapter);
//        } else {
//
//            empty_view.setVisibility(View.VISIBLE);
//            sell_transection_list.setVisibility(View.GONE);
//        }


        prc_to_cdsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    int i;

                    ArrayList<AuthTransectionModel.AgeingTransData> selecteddatalist = new ArrayList<>();
                    if (authorizeTransDataList != null) {
                        for (i = 0; i < authorizeTransDataList.size(); i++) {
                            if (authorizeTransDataList.get(i).getInstrumentSelection()) {

                                if (authorizeTransDataList.get(i).getAuthQuantity().isEmpty()) {
                                    cdslValidation = false;
                                    cdslValidationMassage = "Enter quantity should be between one to free available quantity for selected contract";
                                    break;

                                } else if (Integer.parseInt(authorizeTransDataList.get(i).getAuthQuantity()) <= 0) {
                                    cdslValidation = false;
                                    cdslValidationMassage = "Enter quantity should be between one to free available quantity for selected contract";
                                    break;

                                } else if (Integer.parseInt(authorizeTransDataList.get(i).getAuthQuantity()) >
                                        Integer.parseInt(authorizeTransDataList.get(i).getFreeQTY())) {

                                    cdslValidation = false;
                                    cdslValidationMassage = "Enter quantity should be between one to free available quantity for selected contract";
                                    break;

                                } else {
                                    cdslValidation = true;
                                    selecteddatalist.add(authorizeTransDataList.get(i));
                                }
                            } else {
                                Log.e("GreekBaseActivity", "EXtra Unselection");
                            }
                        }

                        if (!cdslValidation) {
                            GreekDialog.alertDialogOnly(GreekBaseActivity.this, GreekBaseActivity.GREEK, cdslValidationMassage, getString(R.string.GREEK_OK));
                            return;
                        }

                        if (selecteddatalist.size() > 0) {

                            if (selecteddatalist.size() > 50) {

                                GreekDialog.alertDialogOnly(GreekBaseActivity.this, GreekBaseActivity.GREEK, "Selection of more then 50 stock not allowed in single request", getString(R.string.GREEK_OK));


                            } else {

                                if (cdslValidation) {
                                    dialog.dismiss();
                                    sendAuthorizationRequestCDSL(selecteddatalist);
                                } else {
                                    Toast.makeText(GreekBaseActivity.this, cdslValidationMassage, Toast.LENGTH_SHORT).show();
                                }
                            }


                        } else {

                            if (cdslValidation) {
                                GreekDialog.alertDialogOnly(GreekBaseActivity.this, GreekBaseActivity.GREEK, "Please select stock", getString(R.string.GREEK_OK));

                            } else {
                                GreekDialog.alertDialogOnly(GreekBaseActivity.this, GreekBaseActivity.GREEK, cdslValidationMassage, getString(R.string.GREEK_OK));

                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void sendRequestForhHolding() {

        showProgress();
        String serviceURL = "getHoldingDataTCP?sessionId=" + AccountDetails.getSessionId(GreekBaseActivity.this) +
                "&gscid=" + AccountDetails.getUsername(GreekBaseActivity.this) + "&requestType=getEDISHoldingInfo";
        WSHandler.getRequest(GreekBaseActivity.this, serviceURL, new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    Log.e("response", response.toString());
                    try {


                        edisHoldingdatalist = new ArrayList<>();

                        if (response.has("data")) {
                            JSONArray ja1 = response.getJSONArray("data");

                            for (int i = 0; i < ja1.length(); i++) {
                                JSONObject jsonObject = ja1.getJSONObject(i);


                                AuthTransectionModel.AgeingTransData ageingTransData = new AuthTransectionModel.AgeingTransData();

                                ageingTransData.setHQty(jsonObject.getString("HQty"));
                                ageingTransData.setSymbol(jsonObject.getString("symbol"));
                                ageingTransData.setInstrument(jsonObject.getString("instrument"));
                                ageingTransData.setIsin(jsonObject.getString("isin"));
                                ageingTransData.setToken(jsonObject.getString("token"));
                                ageingTransData.setInstrumentSelection(false);
                                ageingTransData.setOpenAuthQty(jsonObject.getString("OpenAuthQty"));
                                ageingTransData.setTodayAuthQty(jsonObject.getString("TodayAuthQty"));
                                ageingTransData.setTodaySoldQty(jsonObject.getString("TodaySoldQty"));
                                ageingTransData.setClose(jsonObject.getString("close"));
                                edisHoldingdatalist.add(ageingTransData);


                            }

                        }

                        if (edisHoldingdatalist != null && edisHoldingdatalist.size() > 0) {
                            empty_view.setVisibility(View.GONE);
                            sell_transection_list.setVisibility(View.VISIBLE);

                            final AuthorizeTransAdapter adapter = new AuthorizeTransAdapter(GreekBaseActivity.this,
                                    edisHoldingdatalist);
                            ((SimpleItemAnimator) sell_transection_list.getItemAnimator()).setSupportsChangeAnimations(false);
                            sell_transection_list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            hideProgress();


                        } else {
                            empty_view.setVisibility(View.VISIBLE);
                            sell_transection_list.setVisibility(View.GONE);
                        }
                        hideProgress();

                    } catch (Exception e) {
                        Log.e("tag", e.getMessage());
                        hideProgress();

                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                    hideProgress();
                    Log.e("responseerror", response.toString());
                    empty_view.setVisibility(View.VISIBLE);
                    sell_transection_list.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(String message) {
                hideProgress();
                Log.e("responseFailure", message);
                empty_view.setVisibility(View.VISIBLE);
                sell_transection_list.setVisibility(View.GONE);
            }
        });


    }

    private void sendAuthorizationRequestCDSL(ArrayList<AuthTransectionModel.AgeingTransData> selecteddatalist) {

        String gscid = AccountDetails.getUsername(GreekBaseActivity.this);
        String dPType = "CDSL";
        String reqType = "D";
        String requestIdentifier = "S";
        String RU = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":"
                + AccountDetails.getArachne_Port() + "/getEDISAuthorizationResponseMobile";
        String BOID = AccountDetails.getPOADPId();
//        String BOID = "1209240000000063";
        String ExID = "";
        if (getExchange(exchangeToken).equalsIgnoreCase("BSE")) {
            ExID = "11";
        } else if (getExchange(exchangeToken).equalsIgnoreCase("NSE")) {
            ExID = "12";
        }
        String deviceId = Settings.Secure.getString(GreekBaseActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < selecteddatalist.size(); i++) {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("ISIN", selecteddatalist.get(i).getIsin());
                jsonobj.put("Quantity", selecteddatalist.get(i).getAuthQuantity());
                jsonobj.put("token", selecteddatalist.get(i).getToken());
                int freeQTY = (Integer.parseInt(selecteddatalist.get(i).getHQty())) -
                        (Integer.parseInt(selecteddatalist.get(i).getOpenAuthQty()) + Integer.parseInt(selecteddatalist.get(i).getTodayAuthQty()));


                jsonobj.put("FreeQty", freeQTY);
                jsonobj.put("description", selecteddatalist.get(i).getSymbol());
                jsonobj.put("instrument ", selecteddatalist.get(i).getInstrument());
                jsonArray.put(jsonobj);

            }

            MarketsSingleScripRequest.sendRequest(gscid, dPType, reqType, requestIdentifier, RU, BOID, ExID,
                    deviceId, jsonArray, GreekBaseActivity.this, serviceResponseHandler);


        } catch (JSONException e) {
            Log.e("Json error", e.toString());
        }


    }

    private void showAuthorizeTransectionReturnnResponse(ArrayList<CDSLReturnResponse.Response.Data.StockDetail> returnlist) {

        LayoutInflater factory = LayoutInflater.from(GreekBaseActivity.this);
        final View authDialogView = factory.inflate(R.layout.authorize_trancection_status, null);
        final AlertDialog dialog = new AlertDialog.Builder(GreekBaseActivity.this).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(authDialogView);

        LinearLayoutManager linearLayOutExeption = new LinearLayoutManager(GreekBaseActivity.this, LinearLayoutManager.VERTICAL, false);
        RecyclerView sell_transection_list = authDialogView.findViewById(R.id.sell_transection_Return_list);
        LinearLayout parentLayout = authDialogView.findViewById(R.id.parentLayout);
        GreekTextView txt_header = authDialogView.findViewById(R.id.txt_header);
        GreekTextView instrument_txt = authDialogView.findViewById(R.id.instrument_txt);
        GreekTextView qty_txt = authDialogView.findViewById(R.id.qty_txt);
        GreekTextView status_txt = authDialogView.findViewById(R.id.status_txt);
        GreekTextView empty_view = authDialogView.findViewById(R.id.empty_view);
        sell_transection_list.setLayoutManager(linearLayOutExeption);
        sell_transection_list.setHasFixedSize(true);
        final AuthorizeTransStatusAdapter adapter = new AuthorizeTransStatusAdapter(GreekBaseActivity.this, returnlist);
        ((SimpleItemAnimator) sell_transection_list.getItemAnimator()).setSupportsChangeAnimations(false);
        sell_transection_list.setAdapter(adapter);

        if (AccountDetails.getThemeFlag(GreekBaseActivity.this).equalsIgnoreCase("white")) {
            txt_header.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            instrument_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            status_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            empty_view.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            parentLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

        authDialogView.findViewById(R.id.prc_to_cdsl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();

    }


    public class AuthorizeTransAdapter extends RecyclerView.Adapter<AuthorizeTransAdapter.MyViewHolder> {

        Context context;
        private final int minDelta = 300;           // threshold in ms
        private long focusTime = 0;                 // time of last touch
        private View focusTarget = null;

        public AuthorizeTransAdapter(Context activity, ArrayList<AuthTransectionModel.AgeingTransData> authorizeTransModels) {
            this.context = activity;
            authorizeTransDataList = authorizeTransModels;
            checkedÌlist = new ArrayList<>();

        }

        public void checkALlStock(boolean Checkflag) {


            for (int i = 0; i < authorizeTransDataList.size(); i++) {
                authorizeTransDataList.get(i).setInstrumentSelection(Checkflag);

            }
            notifyDataSetChanged();
        }

        @Override
        public AuthorizeTransAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.authorize_transection_data, parent, false);
            return new AuthorizeTransAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AuthorizeTransAdapter.MyViewHolder holder, final int position) {
            final AuthTransectionModel.AgeingTransData optionChainData = authorizeTransDataList.get(position);
            holder.authrize_checkbox.setChecked(optionChainData.getInstrumentSelection());

            if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                holder.qty_to_authorize_edittxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.qty_to_authorize_edittxt.getBackground().setColorFilter(getResources().getColor(R.color.black),
                        PorterDuff.Mode.SRC_ATOP);
                holder.qty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.instrument_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.freeqty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.parent_layout.setBackgroundColor(getResources().getColor(R.color.white));
                holder.authrize_checkbox.setButtonDrawable(getResources().getDrawable(R.drawable.checkbox_selector_gray));
            }

            if (!optionChainData.getSymbol().isEmpty() && optionChainData.getSymbol() != null) {
                holder.instrument_txt.setText(optionChainData.getSymbol());
            } else {
                holder.instrument_txt.setText("");
            }

            int balaceQTY =
                    (Integer.parseInt(optionChainData.getOpenAuthQty()) + Integer.parseInt(optionChainData.getTodayAuthQty()))
                            - Integer.parseInt(optionChainData.getTodaySoldQty());

            int freeQTY = (Integer.parseInt(optionChainData.getHQty())) -
                    (Integer.parseInt(optionChainData.getOpenAuthQty()) + Integer.parseInt(optionChainData.getTodayAuthQty()));


            holder.qty_txt.setText(StringStuff.commaINRDecorator(balaceQTY + ""));
            holder.freeqty_txt.setText(StringStuff.commaINRDecorator(freeQTY + ""));
            final int qty_auth_total = freeQTY;
            optionChainData.setFreeQTY(String.valueOf(freeQTY));

            optionChainData.setAuthQuantity(String.valueOf(qty_auth_total));
            holder.qty_to_authorize_edittxt.setText(String.valueOf(qty_auth_total));

            View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    long t = System.currentTimeMillis();
                    long delta = t - focusTime;
                    if (hasFocus) {     // gained focus
                        if (delta > minDelta) {
                            focusTime = t;
                            focusTarget = view;
                        }
                    } else {              // lost focus
                        if (view == focusTarget) {
                            focusTarget.post(new Runnable() {   // reset focus to target
                                public void run() {
                                    focusTarget.requestFocus();
                                }
                            });
                        }
                    }
                }
            };

            holder.qty_to_authorize_edittxt.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                    if (holder.qty_to_authorize_edittxt.getText().toString().equalsIgnoreCase("")) {
                        holder.qty_to_authorize_edittxt.setSelectAllOnFocus(false);
                    }

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    holder.qty_to_authorize_edittxt.setSelectAllOnFocus(false);
                    optionChainData.setAuthQuantity(s.toString());


                }
            });
            holder.qty_to_authorize_edittxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.qty_to_authorize_edittxt.setSelectAllOnFocus(true);

                }
            });

            holder.authrize_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    optionChainData.setInstrumentSelection(isChecked);
                    Log.e("optionChainData", "" + optionChainData);
                    if (isChecked) {
                        checkedÌlist.add(String.valueOf(position));
                    } else {

                        checkedÌlist.remove(String.valueOf(position));

                    }
                    if (isChecked) {
                        holder.qty_to_authorize_edittxt.setEnabled(true);
//                        holder.dsell_market.requestFocus();
                        holder.qty_to_authorize_edittxt.setSelection(holder.qty_to_authorize_edittxt.getText().length());
                        holder.qty_to_authorize_edittxt.setSelectAllOnFocus(true);

                    } else {
                        holder.qty_to_authorize_edittxt.setEnabled(false);
                        holder.qty_to_authorize_edittxt.requestFocus();
                        holder.qty_to_authorize_edittxt.clearFocus();
                        holder.qty_to_authorize_edittxt.setSelectAllOnFocus(false);
                    }
                    if (checkedÌlist.size() > 0) {
                        prc_to_cdsl.setEnabled(true);
                        prc_to_cdsl.setBackground(GreekBaseActivity.this.getResources().getDrawable(R.drawable.single_line_border_bajaj));


                    } else {
                        prc_to_cdsl.setEnabled(false);
                        prc_to_cdsl.setBackground(GreekBaseActivity.this.getResources().getDrawable(R.drawable.single_line_border_bajaj_gray));

                    }

                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return authorizeTransDataList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public GreekTextView instrument_txt, qty_txt, freeqty_txt;
            public CheckBox authrize_checkbox;
            public GreekEditText qty_to_authorize_edittxt;
            public LinearLayout parent_layout;

            public MyViewHolder(View view) {
                super(view);

                parent_layout = view.findViewById(R.id.parent_layout);
                instrument_txt = view.findViewById(R.id.instrument_txt);
                qty_txt = view.findViewById(R.id.qty_txt);
                freeqty_txt = view.findViewById(R.id.freeqty_txt);
                authrize_checkbox = view.findViewById(R.id.authrize_checkbox);
                qty_to_authorize_edittxt = view.findViewById(R.id.qty_to_authorize_edittxt);
                qty_to_authorize_edittxt.setEnabled(false);

            }
        }


    }

    public class AuthorizeTransStatusAdapter extends RecyclerView.Adapter<AuthorizeTransStatusAdapter.MyViewHolder> {

        Context context;
        ArrayList<CDSLReturnResponse.Response.Data.StockDetail> authorizeTransDataList;

        public AuthorizeTransStatusAdapter(Context activity, ArrayList<CDSLReturnResponse.Response.Data.StockDetail> authorizeTransModels) {
            this.context = activity;
            authorizeTransDataList = authorizeTransModels;
        }

        @Override
        public AuthorizeTransStatusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.authorize_trans_status_data, parent, false);
            return new AuthorizeTransStatusAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AuthorizeTransStatusAdapter.MyViewHolder holder, final int position) {
            final CDSLReturnResponse.Response.Data.StockDetail optionChainData = authorizeTransDataList.get(position);


            if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                holder.symbole_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.qty_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.status_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
                holder.layoutParent.setBackgroundColor(getResources().getColor(R.color.white));
            }

            if (!optionChainData.getSymbol().isEmpty() && optionChainData.getSymbol() != null) {
                holder.symbole_txt.setText(optionChainData.getSymbol());
            } else {
                holder.symbole_txt.setText("");
            }

            holder.qty_txt.setText("" + optionChainData.getQuantity());

            if (!optionChainData.getStatus().isEmpty() && optionChainData.getStatus() != null) {
                holder.status_txt.setText(optionChainData.getStatus());
            } else {
                holder.status_txt.setText("");
            }


        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return authorizeTransDataList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public GreekTextView symbole_txt, qty_txt, status_txt;
            public LinearLayout layoutParent;


            public MyViewHolder(View view) {
                super(view);

                symbole_txt = view.findViewById(R.id.instrument_txt);
                qty_txt = view.findViewById(R.id.qty_txt);
                status_txt = view.findViewById(R.id.status_txt);
                layoutParent = view.findViewById(R.id.layoutParent);
            }
        }


    }

    class MyPhoneStateListener extends PhoneStateListener {
        public int signalStrengthValue;
        int dataSpeed;

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            mSignalStrength = signalStrength.getGsmSignalStrength();
            mSignalStrength = (2 * mSignalStrength) - 113; // -> dBm


            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            NetworkCapabilities nc = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
            }

      /*      if(nc != null && snackbarWeakNet != null) {
                int downSpeed = nc.getLinkDownstreamBandwidthKbps();
                int upSpeed = nc.getLinkUpstreamBandwidthKbps();

                if(upSpeed > 200) {
                    snackbarWeakNet.dismiss();
                } else {
                    snackbarWeakNet.show();
                }
            }*/


        }


    }

}

