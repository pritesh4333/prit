package com.acumengroup.mobile.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.mobile.BottomTabScreens.CancelledTabFragment;
import com.acumengroup.mobile.BottomTabScreens.ExecutedTabFragment;
import com.acumengroup.mobile.BottomTabScreens.MarketEditFragment;
import com.acumengroup.mobile.BottomTabScreens.NewsTabFragment;
import com.acumengroup.mobile.BottomTabScreens.OrderBottomFragment;
import com.acumengroup.mobile.BottomTabScreens.PendingTabFragment;
import com.acumengroup.mobile.BottomTabScreens.PortfolioBottomFragment;
import com.acumengroup.mobile.BottomTabScreens.RejectedTabFragment;
import com.acumengroup.mobile.ChatMessage.ChatMessageFragment;
import com.acumengroup.mobile.Dashboard.DashBoardFragment;
import com.acumengroup.mobile.BottomTabScreens.MarketBottomFragment;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseActivity.USER;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.MutualFund.MFDetailsFragment;
import com.acumengroup.mobile.MutualFund.MFundDashboard;
import com.acumengroup.mobile.MutualFund.UploadAadharDetails;
import com.acumengroup.mobile.MutualFund.UploadBankDetails;
import com.acumengroup.mobile.MutualFund.UploadChequeDetails;
import com.acumengroup.mobile.SnapshotFloatingFragment;
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.view.StrategyBuildUpFragment;
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.view.StrategyDataListFragment;
import com.acumengroup.mobile.login.LASFragment;
import com.acumengroup.mobile.login.LasStepsFormFragment;
import com.acumengroup.mobile.login.LoginPasswordFragment;
import com.acumengroup.mobile.login.SetTransactionPasswordFragment;
import com.acumengroup.mobile.login.TransPasswordFragment;
import com.acumengroup.mobile.login.UserProfile;
import com.acumengroup.mobile.markets.ChartsFragment;
import com.acumengroup.mobile.markets.ContractInformationFragment;
import com.acumengroup.mobile.markets.IndicesStockFragment;
import com.acumengroup.mobile.markets.MarketDepthFragment;
import com.acumengroup.mobile.markets.MarketsFragment;
import com.acumengroup.mobile.markets.NewsDetailFragment;
import com.acumengroup.mobile.markets.NewsFragmentPager;
import com.acumengroup.mobile.markets.NewsSearchFragment;
import com.acumengroup.mobile.markets.QuotesFragment;
import com.acumengroup.mobile.portfolio.AddNewPortfolio;
import com.acumengroup.mobile.portfolio.EditWatchlistFragment;
import com.acumengroup.mobile.portfolio.LastVisitedFragment;
import com.acumengroup.mobile.portfolio.MFAddNewPortflio;
import com.acumengroup.mobile.portfolio.MFEditWatchlistFragment;
import com.acumengroup.mobile.portfolio.MFWatchlistFragment;
import com.acumengroup.mobile.portfolio.PortfolioSectionFragment;
import com.acumengroup.mobile.portfolio.WatchListFragment;
import com.acumengroup.mobile.reports.AgeingHoldingReport;
import com.acumengroup.mobile.reports.BankDetailsScreenFragment;
import com.acumengroup.mobile.reports.CombinedLedgerReport;
import com.acumengroup.mobile.reports.CostReport;
import com.acumengroup.mobile.reports.CumulativePositionFragment;
import com.acumengroup.mobile.reports.DematDetails;
import com.acumengroup.mobile.reports.DematHoldingFragment;
import com.acumengroup.mobile.reports.DematHoldingFragmentNew;
import com.acumengroup.mobile.reports.EdisDashboardReport;
import com.acumengroup.mobile.reports.EdisMarginPledgeReport;
import com.acumengroup.mobile.reports.EdisTransactionDetails;
import com.acumengroup.mobile.reports.FOProfitLossReport;
import com.acumengroup.mobile.reports.FiiDiiActivitiesTabsFragment;
import com.acumengroup.mobile.reports.FundTransferFragment;
import com.acumengroup.mobile.reports.FundTransfer_payout_fragment;
import com.acumengroup.mobile.reports.FundTransfer_tabs_Fragment;
import com.acumengroup.mobile.reports.HoldingReportFragment;
import com.acumengroup.mobile.reports.InterestReport;
import com.acumengroup.mobile.reports.LedgerDetailsFragment;
import com.acumengroup.mobile.reports.LoanAgainstReport;
import com.acumengroup.mobile.reports.MFOrderBookDetailsFragment;
import com.acumengroup.mobile.reports.MFOrderBookFragment;
import com.acumengroup.mobile.reports.MarginFragment;
import com.acumengroup.mobile.reports.MarginPledgeTransactionDetails;
import com.acumengroup.mobile.reports.NetPositionFragment;
import com.acumengroup.mobile.reports.OpenPositionFragment;
import com.acumengroup.mobile.reports.OrderBookDetailsFragment;
import com.acumengroup.mobile.reports.OrderBookFragment;
import com.acumengroup.mobile.reports.PLDetailsNetPositionsFragment;
import com.acumengroup.mobile.reports.PositionOrderDetails;
import com.acumengroup.mobile.reports.RecommDisplayFragment;
import com.acumengroup.mobile.reports.SaudaFragment;
import com.acumengroup.mobile.reports.TradeBookDetailsFragment;
import com.acumengroup.mobile.reports.TradeBookFragment;
import com.acumengroup.mobile.reports.TransactionFragment;
import com.acumengroup.mobile.bajajStrategyFinder.sfHomeScreen.view.SFScanFragment;
import com.acumengroup.mobile.settings.AppSettingFragment;
import com.acumengroup.mobile.symbolsearch.ChartingFragment;
import com.acumengroup.mobile.symbolsearch.MutualFundGetQuoteFragment;
import com.acumengroup.mobile.symbolsearch.SymbolSearchFragment;
import com.acumengroup.mobile.trade.AllBankDetailsFragment;
import com.acumengroup.mobile.trade.BankDetailsMandateFragment;
import com.acumengroup.mobile.trade.ChartsRotateFragments;
import com.acumengroup.mobile.trade.KYCUploadPhoto;
import com.acumengroup.mobile.trade.MutualFundFragmentNew;
import com.acumengroup.mobile.trade.MutualFundSTPFragment;
import com.acumengroup.mobile.trade.MutualFundSWPFragment;
import com.acumengroup.mobile.trade.MutualFundSipFragment;
import com.acumengroup.mobile.trade.MutualFundSipSummary;
import com.acumengroup.mobile.trade.MutualFundTradeFragment;
import com.acumengroup.mobile.trade.MutualFundTradePreview;
import com.acumengroup.mobile.trade.MyAccountPersonalDetails;
import com.acumengroup.mobile.trade.TradeFragment;
import com.acumengroup.mobile.trade.TradePreviewFragment;

import com.acumengroup.mobile.trade.UploadIpvp;
import com.acumengroup.mobile.trade.UploadSignature;
import com.acumengroup.mobile.trade.UploadpanDetails;


/**
 * Created by Arcadia
 */
public class MenuGetter implements GreekConstants {

    public static Fragment getFragment(String menuCode) {
        Bundle args = new Bundle();
        USER USER_TYPE = GreekBaseActivity.USER_TYPE;
        GreekBaseFragment frag = null;
        switch (menuCode) {
            case GREEK_MENU_MYSTOCKS_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER) {
                    AccountDetails.portfolio = true;
                    AccountDetails.watchlist = false;
                    AccountDetails.lastvisited = false;
                    AccountDetails.currentFragment = NAV_TO_MYSCRIPTS_SCREEN;
                    args.clear();
                    args.putInt("Source", 0);
                    frag = new PortfolioSectionFragment();
                    frag.setArguments(args);
                }
                break;
            case GREEK_MENU_WATCHLIST_TXT:

                AccountDetails.portfolio = false;
                AccountDetails.watchlist = true;
                AccountDetails.lastvisited = false;
                AccountDetails.currentFragment = NAV_TO_WATCHLIST_SCREEN;
                args.clear();
                args.putInt("Source", 1);
                frag = new PortfolioSectionFragment();
                frag.setArguments(args);

                break;
            case GREEK_MENU_LASTVISITED_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER) {
                    AccountDetails.portfolio = false;
                    AccountDetails.watchlist = false;
                    AccountDetails.lastvisited = true;
                    AccountDetails.currentFragment = NAV_TO_LASTVISITED_SCREEN;
                    args.clear();
                    args.putInt("Source", 2);
                    frag = new PortfolioSectionFragment();
                    frag.setArguments(args);
                }
                break;
            case GREEK_MENU_STRATEGY_TXT:

                frag = new SFScanFragment();
                break;
            case GREEK_MENU_STRATEGY_BUILDER_TXT:

                frag = new StrategyBuildUpFragment();
                break;
            case GREEK_MENU_ORDERFORM_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER) {
                    args.clear();
                    frag = new TradeFragment();
                    frag.setArguments(args);
                }
                break;
            case GREEK_MENU_TRADE_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER) {
                    args.clear();
                    frag = new TradeFragment();
                    frag.setArguments(args);
                }
                break;
            case GREEK_MENU_ORDERBOOK_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new OrderBookFragment();
                break;

            case GREEK_MENU_MARGIN_PLEDGE:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new EdisMarginPledgeReport();
                break;

            case GREEK_MENU_LEDGER_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new LedgerDetailsFragment();
                break;
            case GREEK_MENU_EDIS_DASHBOARD:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new EdisDashboardReport();
                break;
            case GREEK_MENU_EDIS_TRANSACTION_DETAILS:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new EdisTransactionDetails();
                break;

            case GREEK_MENU_TRANSACTION_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new TransactionFragment();
                break;
            case GREEK_MENU_HOLDING_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new HoldingReportFragment();
                break;
            case GREEK_MENU_SAUDA_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new SaudaFragment();
                break;
            case GREEK_MENU_NETPOSITION_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    args.clear();
                args.putString("from", "all");
                frag = new NetPositionFragment();
                frag.setArguments(args);
                break;
            case GREEK_MENU_TRADE_BOOK_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new TradeBookFragment();
                break;
            case GREEK_MENU_MARKET_TXT:
                AccountDetails.currentFragment = NAV_TO_MARKET_STARTUP_SCREEN;
                frag = new MarketsFragment();
                break;
            case GREEK_MENU_CHATMESSAGE:
                AccountDetails.currentFragment = NAV_TO_CHAT_MESSAGE_SCREEN;
                frag = new ChatMessageFragment();
                break;
            case GREEK_MENU_DEMAT_HOLDING_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER) {
                    frag = new DematHoldingFragmentNew();
                }
                break;
            case GREEK_MENU_DEMAT_HOLDING_SINGLE_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER) {
                    frag = new DematHoldingFragment();
                }
                break;
            case GREEK_MENU_DASHBOARD_TXT:

                frag = new DashBoardFragment();

                break;
            case GREEK_MENU_BOTTOM_MARKET_TXT:

                frag = new MarketBottomFragment();

                break;
            case GREEK_MENU_BOTTOM_ORDER_TXT:

                frag = new OrderBottomFragment();

                break;
            case GREEK_MENU_BOTTOM_PORTFOLIO_TXT:
                frag = new PortfolioBottomFragment();
                AccountDetails.currentFragment = NAV_TO_PORTFOLIO_BOTTOM_SCREEN;
                break;
            case GREEK_MENU_MARGIN_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER) {
                    frag = new MarginFragment();
                }
                break;
            case GREEK_MENU_MF_DASHBOARD_TXT:
                frag = new MFundDashboard();
                break;
            case GREEK_MENU_MF_WATCHLIST_TXT:
                frag = new MFWatchlistFragment();
                break;
            case GREEK_MENU_MF_TRANSACTION_TXT:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.MFCUSTOMER)
                    frag = new MutualFundFragmentNew();
                break;
            case GREEK_MENU_MF_MYACCOUNT_TXT:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER || USER_TYPE == USER.MFCUSTOMER)
                    break;
            case GREEK_MENU_MF_REPORT_TXT:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.MFCUSTOMER)
                    frag = new MFOrderBookFragment();
                break;



            case GREEK_MENU_MARGIN_PLEDGE_DASHBOARD:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new MarginPledgeTransactionDetails();
                break;
            case GREEK_MENU_COMBINED_LEDGER:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new CombinedLedgerReport();
                break;
            case GREEK_MENU_COST_REPORT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new CostReport();
                break;
            case GREEK_MENU_INTEREST_REPORT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new InterestReport();
                break;
            case GREEK_MENU_LOAN_AGAINST_REPORT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new LoanAgainstReport();
                break;

            case GREEK_MENU_CASH_PROFIT_AND_LOSS:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new AgeingHoldingReport();
                break;
            case GREEK_MENU_P_L_TXT:
                if (USER_TYPE == USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    frag = new FOProfitLossReport();
                break;
        }
        return frag;
    }


    public static GreekBaseFragment getFragmentById(int id) {
        GreekBaseActivity.USER USER_TYPE = GreekBaseActivity.USER_TYPE;
        GreekBaseFragment fragment = null;
        Bundle sensitive = new Bundle();
        switch (id) {
            case NAV_TO_NEWS_DETAIL:
                fragment = new NewsDetailFragment();
                break;
            case NAV_TO_NEWS_SEARCH:
                fragment = new NewsSearchFragment();
                break;
            case NAV_TO_SYMBOL_SEARCH_SCREEN:
                fragment = new SymbolSearchFragment();
                break;
            case NAV_TO_QUOTES_SCREEN:
                fragment = new QuotesFragment();
                break;
            case NAV_TO_WATCHLIST_SCREEN:
                fragment = new WatchListFragment();
                break;

            case NAV_TO_PORTFOLIO_BOTTOM_SCREEN:
                fragment = new PortfolioBottomFragment();
                break;

            case NAV_TO_TRADE_SCREEN:
                fragment = new TradeFragment();
                break;
            case NAV_TO_CONTRACT_INFO_SCREEN:
                fragment = new ContractInformationFragment();
                break;
            case NAV_TO_ORDER_PREVIEW_SCREEN:
                fragment = new TradePreviewFragment();
                break;

            case NAV_TO_STRATEGY_DATA_SCREEN:
                fragment = new StrategyDataListFragment();
                break;
            case NAV_TO_STRATEGY_BUILDUP_SCREEN:
                fragment = new StrategyBuildUpFragment();
                break;
            case NAV_TO_LAS_STEPS_FORMS_SCREEN:
                fragment = new LasStepsFormFragment();
                break;
            case NAV_TO_ORDER_BOOK_DETAILS_SCREEN:
                fragment = new OrderBookDetailsFragment();
                break;
            case NAV_TO_MF_ORDER_BOOK_DETAILS_SCREEN:
                fragment = new MFOrderBookDetailsFragment();
                break;
            case NAV_TO_BANK_DETAILS_SCREEN:
                fragment = new BankDetailsScreenFragment();
                break;
            case NAV_TO_MFUND_DETAILS_SCREEN:
                fragment = new MFDetailsFragment();
                break;

            case NAV_TO_ORDER_BOOK_SCREEN:
                fragment = new OrderBookFragment();
                break;
            case NAV_TO_MF_ORDER_BOOK_SCREEN:
                fragment = new MFOrderBookFragment();
                break;
            case NAV_TO_CHARTS_SCREEN:
                fragment = new ChartsFragment();
                break;
            case NAV_TO_ROTATE_CHARTS_SCREEN:
                fragment = new ChartsRotateFragments();
                break;
            case NAV_TO_NEWS_PAGER:
                fragment = new NewsFragmentPager();
                break;
            case NAV_TO_MARKET_DEPTH_SCREEN:
                fragment = new MarketDepthFragment();
                break;
            case NAV_TO_TRANS_PASS:
                fragment = new SetTransactionPasswordFragment();
                break;

            case NAV_TO_POSITION_ORDER_DETAIL_SCREEN:
                fragment = new PositionOrderDetails();
                break;
            case NAV_TO_DEMAT_DETAIL_SCREEN:
                fragment = new DematDetails();
                break;
            case NAV_TO_INDICES_STOCK_SCREEN:
                fragment = new IndicesStockFragment();
                break;
            case NAV_TO_PL_DETAILS_SCREEN:
                fragment = new PLDetailsNetPositionsFragment();
                break;
            case NAV_TO_EDIT_WATCHLIST_SCREEN:
                fragment = new EditWatchlistFragment();
                break;
            case NAV_TO_MYSCRIPTS_SCREEN:
                fragment = new SnapshotFloatingFragment();
                break;
            case NAV_TO_MF_EDIT_WATCHLIST_SCREEN:
                fragment = new MFEditWatchlistFragment();
                break;
            case NAV_TO_LASTVISITED_SCREEN:
                fragment = new LastVisitedFragment();
                break;
            case NAV_TO_ADD_NEW_PORTFOLIO_SCREEN:
                fragment = new AddNewPortfolio();
                break;
            case NAV_TO_MF_ADD_NEW_PORTFOLIO_SCREEN:
                fragment = new MFAddNewPortflio();
                break;
            case NAV_TO_TRADE_BOOK_SCREEN:
                fragment = new TradeBookFragment();
                break;
            case NAV_TO_TRADE_BOOK_DETAILS_SCREEN:
                fragment = new TradeBookDetailsFragment();
                break;
            case GREEK_MENU_NETPOSITION:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    fragment = new NetPositionFragment();
                break;
            case NAV_TO_CHAT_MESSAGE_SCREEN:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    fragment = new ChatMessageFragment();
                break;
            case NAV_TO_LAS_MESSAG_SCREEN:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    fragment = new LASFragment();
                break;

            case NAV_TO_HOLDING_SCREEN:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    fragment = new HoldingReportFragment();
                break;
            case GREEK_MENU_OPENPOSITION:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    fragment = new OpenPositionFragment();
                break;
            case NAV_TO_CUMULATIVE_SCREEN:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    fragment = new CumulativePositionFragment();
                break;
            case GREEK_MENU_ORDERPOSITION:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    fragment = new OrderBookFragment();
                break;
            case GREEK_MENU_DEMATHOLDING:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    fragment = new DematHoldingFragmentNew();
                break;
            case GREEK_MENU_DEMAT_HOLDING_SINGLE:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    fragment = new DematHoldingFragment();
                break;
            case NAV_TO_MUTUALFUND_GET_QUOTE:
                fragment = new MutualFundGetQuoteFragment();
                break;
            case NAV_TO_MUTUALFUND_BANK_MANDATE:
                fragment = new BankDetailsMandateFragment();
                break;
            case NAV_TO_MUTUAL_FUND_SIP:
                fragment = new MutualFundSipFragment();
                break;
            case NAV_TO_MUTUAL_FUND_SWP:
                fragment = new MutualFundSWPFragment();
                break;
            case NAV_TO_MUTUAL_FUND_STP:
                fragment = new MutualFundSTPFragment();
                break;
            case NAV_TO_MUTUAL_FUND_SIP_SUMMARY:
                fragment = new MutualFundSipSummary();
                break;
            case NAV_TO_MUTUALFUND_NO_SENSITIVE:
                fragment = new MutualFundFragmentNew();
                break;
            case NAV_TO_MUTUALFUND_ACTION:
                fragment = new MutualFundFragmentNew();
                sensitive.putBoolean("isSensitive", true);
                fragment.setArguments(sensitive);
                break;
            case NAV_TO_MUTUALFUND_TRADE:
                fragment = new MutualFundTradeFragment();
                //sensitive.putBoolean("isSensitive", true);
                // fragment.setArguments(sensitive);
                break;
            case NAV_TO_MUTUALFUND_TRADE_PREVIEW:
                fragment = new MutualFundTradePreview();
                break;

            case NAV_TO_MUTUALFUND_PERSONAL_DETAILS:
                fragment = new MyAccountPersonalDetails();

                break;
            case NAV_TO_USER_DETAILS_CREATION:
//                fragment = new UserCreationIDDetails();

                break;
            case NAV_TO_USER_SCREEN:
                fragment = new UserProfile();

                break;

            case NAV_TO_MUTUALFUND_KYC_UPLOAD:
                fragment = new KYCUploadPhoto();

                break;

            case NAV_TO_MUTUALFUND_KYC_PAN_UPLOAD:
                fragment = new UploadpanDetails();

                break;
            case NAV_TO_MUTUALFUND_KYC_AADHAR_UPLOAD:
                fragment = new UploadAadharDetails();

                break;
            case NAV_TO_MUTUALFUND_KYC_BANK_UPLOAD:
                fragment = new UploadBankDetails();

                break;
            case NAV_TO_MUTUALFUND_KYC_CHEQUE_UPLOAD:
                fragment = new UploadChequeDetails();

                break;

            case NAV_TO_MUTUALFUND_KYC_IPVP_UPLOAD:
                fragment = new UploadIpvp();

                break;

            case NAV_TO_MUTUALFUND_SIGNATURE_UPLOAD:
                fragment = new UploadSignature();

                break;
            case NAV_TO_ALL_BANKDETAILS_MF:
                fragment = new AllBankDetailsFragment();

                break;
            case NAV_TO_UPLOAD__PAN_DETAILS:
                fragment = new UploadpanDetails();

                break;
            case NAV_TO_MF_WATCHLIST_MF:

                fragment = new MFWatchlistFragment();

                break;
            case NAV_TO_FIIDII_SCREEN:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    fragment = new FiiDiiActivitiesTabsFragment();
                break;

            case NAV_TO_CHARTING_SCREEN:
                if (USER_TYPE == GreekBaseActivity.USER.CUSTOMER || USER_TYPE == USER.IBTCUSTOMER)
                    fragment = new ChartingFragment();
                break;

            case NAV_TO_STRATEGY_FINDER:
                fragment = new SFScanFragment();

                break;

            case NAV_TO_MARKET_EDIT_FRAGMENT:
                fragment = new MarketEditFragment();

                break;
            case NAV_TO_FUNDTRANSFER_SCREEN:
                fragment = new FundTransferFragment();
                break;
            case NAV_TO_EDIS_MARGIN:
                fragment = new EdisMarginPledgeReport();
                break;
            case NAV_TO_TRANSFER_PAYING:
                fragment = new FundTransfer_tabs_Fragment();
                break;
            case NAV_TO_TRANSFER_PAYOUT:
                fragment = new FundTransfer_payout_fragment();
                break;

            case NAV_TO_PENDING_TAB_SCREEN:
                fragment = new PendingTabFragment();
                break;
            case NAV_TO_EXECUTED_TAB_SCREEN:
                fragment = new ExecutedTabFragment();
                break;
            case NAV_TO_REJECTED_TAB_SCREEN:
                fragment = new RejectedTabFragment();
                break;
            case NAV_TO_CANCELLED_TAB_SCREEN:
                fragment = new CancelledTabFragment();
                break;
            case NAV_TO_ORDER_BOTTOM_FRAGMENT:
                fragment = new OrderBottomFragment();
                break;
            case NAV_TO_EDIS_DASHBOARD_REPORT:
                fragment = new EdisDashboardReport();
                break;
            case NAV_TO_EDIS_TRANSACTION_DETAILS:
                fragment = new EdisTransactionDetails();
                break;
            case NAV_TO_LOGINPASSWORD:
                fragment = new LoginPasswordFragment();
                break;
            case NAV_TO_TRANSPASSWORD:
                fragment = new TransPasswordFragment();
                break;
            case NAV_TO_NEWS_TAB:
                fragment = new NewsTabFragment();
                break;
            case NAV_TO_CHANGE_DEFAULTSCREEN_SCREEN:
                fragment = new AppSettingFragment();
                break;
            case NAV_TO_BOTTOM_PORTFOLIO_TXT:
                fragment = new PortfolioBottomFragment();
                break;

            case NAV_TO_BOTTOM_MARKET:
                fragment = new PortfolioBottomFragment();
                break;
        }

        return fragment;
    }
}