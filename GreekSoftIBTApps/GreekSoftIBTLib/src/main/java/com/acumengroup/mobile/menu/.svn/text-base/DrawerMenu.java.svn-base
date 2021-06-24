package com.acumengroup.mobile.menu;

import android.content.Context;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.util.Util;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.init.SplashActivity;
import com.acumengroup.ui.menu.ScreenDetails;
import com.acumengroup.ui.menu.SubMenu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Arcadia
 */
public class DrawerMenu implements GreekConstants {
    private static String appname;
    public static List<ScreenDetails> createGroupList(Context context) {
        List<ScreenDetails> groupList = new ArrayList<>();
          appname = Util.getPrefs(context).getString("AppName","");

        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {

            groupList.add(new ScreenDetails(R.drawable.icon_fund, context.getString(R.string.GREEK_MENU_FUND_TRANSFER), GREEK_MENU_FUND_TRANSFER, true, false));
            if(AccountDetails.getIsStrategyProduct().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_finder, context.getString(R.string.GREEK_MENU_STRATEGY_TXT), GREEK_MENU_STRATEGY_TXT, false, false));
                groupList.add(new ScreenDetails(R.drawable.icon_finder, context.getString(R.string.GREEK_MENU_STRATEGY_BUILDER_TXT), GREEK_MENU_STRATEGY_BUILDER_TXT, false, false));
            }
            if(AccountDetails.getIsBackOffice().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_report, context.getString(R.string.GREEK_MENU_REPORT_TXT), GREEK_MENU_MYREPORT_TXT, true, true));
            }


                groupList.add(new ScreenDetails(R.drawable.ic_baseline_star_24, context.getString(R.string.GREEK_MENU_ALERT), GREEK_MENU_ALERTS, false, false));


            if(AccountDetails.getIsEDISProduct().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_report, context.getString(R.string.GREEK_MENU_EDIS_TXT), GREEK_MENU_EDIS_TXT, true, false));
            }
            groupList.add(new ScreenDetails(R.drawable.icon_chart, context.getString(R.string.GREEK_MENU_CHARTING), GREEK_MENU_CHARTING, false, true));

            groupList.add(new ScreenDetails(R.drawable.notification_bell, context.getString(R.string.GREEK_MENU_NOTIFICATONS), GREEK_MENU_NOTIFICATIONS_TXT, false, false));

                groupList.add(new ScreenDetails(R.drawable.ic_baseline_report_24, context.getString(R.string.GREEK_MENU_RECOMMENDATION), GREEK_MENU_RECOMMENDATION_TXT, false, true));

            groupList.add(new ScreenDetails(R.drawable.icon_sett, context.getString(R.string.GREEK_MENU_SETTINGS_TXT), GREEK_MENU_SETTINGS_TXT, true, false));
            groupList.add(new ScreenDetails(R.drawable.icon_profile, context.getString(R.string.GREEK_MENU_PROFILE_TXT), GREEK_MENU_PROFILE_TXT, false, false));
            if(AccountDetails.getIsLasProduct().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_las, context.getString(R.string.GREEK_MENU_LAS_TXT), GREEK_MENU_LAS_TXT, false, true));
            }
            groupList.add(new ScreenDetails(R.drawable.icon_more, context.getString(R.string.GREEK_MENU_MORE_TXT), GREEK_MENU_MORE_TXT, true, false));
            groupList.add(new ScreenDetails(R.drawable.ic_baseline_link_24, context.getString(R.string.GREEK_MENU_LINKS), GREEK_MENU_LINKS, true, true));

            if(appname.equalsIgnoreCase("Vishwas")) {
                //if (SplashActivity.clientCode==3) {
                groupList.add(new ScreenDetails(R.drawable.ipo, context.getString(R.string.GREEK_MENU_IPO), GREEK_MENU_IPO, false, true));
            }

            //}
            groupList.add(new ScreenDetails(R.drawable.logout_ic, context.getString(R.string.GREEK_MENU_EXIT), GREEK_MENU_EXIT, false, true));

        }
        else if(GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {

            groupList.add(new ScreenDetails(R.drawable.icon_fund, context.getString(R.string.GREEK_MENU_FUND_TRANSFER), GREEK_MENU_FUND_TRANSFER, true));
            if(AccountDetails.getIsStrategyProduct().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_finder, context.getString(R.string.GREEK_MENU_STRATEGY_TXT), GREEK_MENU_STRATEGY_TXT, false, true));
                groupList.add(new ScreenDetails(R.drawable.icon_finder, context.getString(R.string.GREEK_MENU_STRATEGY_BUILDER_TXT), GREEK_MENU_STRATEGY_BUILDER_TXT, false, true));
            }
            if(AccountDetails.getIsBackOffice().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_report, context.getString(R.string.GREEK_MENU_REPORT_TXT), GREEK_MENU_MYREPORT_TXT, true));
            }

                groupList.add(new ScreenDetails(R.drawable.ic_baseline_star_24, context.getString(R.string.GREEK_MENU_ALERT), GREEK_MENU_ALERTS, false, true));

            if(AccountDetails.getIsEDISProduct().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_report, context.getString(R.string.GREEK_MENU_EDIS_TXT), GREEK_MENU_EDIS_TXT, true));
            }
            groupList.add(new ScreenDetails(R.drawable.icon_chart, context.getString(R.string.GREEK_MENU_CHARTING), GREEK_MENU_CHARTING, false, true));

                groupList.add(new ScreenDetails(R.drawable.notification_bell, context.getString(R.string.GREEK_MENU_NOTIFICATONS), GREEK_MENU_NOTIFICATIONS_TXT, false, true));
                groupList.add(new ScreenDetails(R.drawable.ic_baseline_report_24, context.getString(R.string.GREEK_MENU_RECOMMENDATION), GREEK_MENU_RECOMMENDATION_TXT, false, true));

            groupList.add(new ScreenDetails(R.drawable.icon_sett, context.getString(R.string.GREEK_MENU_SETTINGS_TXT), GREEK_MENU_SETTINGS_TXT, true));
            groupList.add(new ScreenDetails(R.drawable.icon_profile, context.getString(R.string.GREEK_MENU_PROFILE_TXT), GREEK_MENU_PROFILE_TXT, false, true));
            if(AccountDetails.getIsLasProduct().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_las, context.getString(R.string.GREEK_MENU_LAS_TXT), GREEK_MENU_LAS_TXT, false, true));
            }
            groupList.add(new ScreenDetails(R.drawable.icon_more, context.getString(R.string.GREEK_MENU_MORE_TXT), GREEK_MENU_MORE_TXT, true));
            groupList.add(new ScreenDetails(R.drawable.ic_baseline_link_24, context.getString(R.string.GREEK_MENU_LINKS), GREEK_MENU_LINKS, true));
            if(appname.equalsIgnoreCase("Vishwas")) {
                // if (SplashActivity.clientCode==3) {
                groupList.add(new ScreenDetails(R.drawable.ipo, context.getString(R.string.GREEK_MENU_IPO), GREEK_MENU_IPO, false, true));
                //  }
            }
            groupList.add(new ScreenDetails(R.drawable.logout_ic, context.getString(R.string.GREEK_MENU_LOGOUT_TXT), GREEK_MENU_LOGOUT_TXT, false));


        }
        else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.CUSTOMER) {

            groupList.add(new ScreenDetails(R.drawable.icon_fund, context.getString(R.string.GREEK_MENU_FUND_TRANSFER), GREEK_MENU_FUND_TRANSFER, true));
            if(AccountDetails.getIsStrategyProduct().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_finder, context.getString(R.string.GREEK_MENU_STRATEGY_TXT), GREEK_MENU_STRATEGY_TXT, true, true));
                groupList.add(new ScreenDetails(R.drawable.icon_finder, context.getString(R.string.GREEK_MENU_STRATEGY_BUILDER_TXT), GREEK_MENU_STRATEGY_BUILDER_TXT, true, true));
            }
            if(AccountDetails.getIsBackOffice().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_report, context.getString(R.string.GREEK_MENU_REPORT_TXT), GREEK_MENU_MYREPORT_TXT, true));
            }

                groupList.add(new ScreenDetails(R.drawable.ic_baseline_star_24, context.getString(R.string.GREEK_MENU_ALERT), GREEK_MENU_ALERTS, true, true));


            if(AccountDetails.getIsEDISProduct().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_report, context.getString(R.string.GREEK_MENU_EDIS_TXT), GREEK_MENU_EDIS_TXT, true));
            }
            groupList.add(new ScreenDetails(R.drawable.icon_chart, context.getString(R.string.GREEK_MENU_CHARTING), GREEK_MENU_CHARTING, false, true));

                groupList.add(new ScreenDetails(R.drawable.notification_bell, context.getString(R.string.GREEK_MENU_NOTIFICATONS), GREEK_MENU_NOTIFICATIONS_TXT, false, true));
                groupList.add(new ScreenDetails(R.drawable.ic_baseline_report_24, context.getString(R.string.GREEK_MENU_RECOMMENDATION), GREEK_MENU_RECOMMENDATION_TXT, false, true));

            groupList.add(new ScreenDetails(R.drawable.icon_sett, context.getString(R.string.GREEK_MENU_SETTINGS_TXT), GREEK_MENU_SETTINGS_TXT, true));
            groupList.add(new ScreenDetails(R.drawable.icon_profile, context.getString(R.string.GREEK_MENU_PROFILE_TXT), GREEK_MENU_PROFILE_TXT, false, true));
            if(AccountDetails.getIsLasProduct().equalsIgnoreCase("true")) {
                groupList.add(new ScreenDetails(R.drawable.icon_las, context.getString(R.string.GREEK_MENU_LAS_TXT), GREEK_MENU_LAS_TXT, false, true));
            }
            groupList.add(new ScreenDetails(R.drawable.icon_more, context.getString(R.string.GREEK_MENU_MORE_TXT), GREEK_MENU_MORE_TXT, true));
            groupList.add(new ScreenDetails(R.drawable.ic_baseline_link_24, context.getString(R.string.GREEK_MENU_LINKS), GREEK_MENU_LINKS, true));
            if(appname.equalsIgnoreCase("Vishwas")) {
                //if (SplashActivity.clientCode==3) {
                groupList.add(new ScreenDetails(R.drawable.ipo, context.getString(R.string.GREEK_MENU_IPO), GREEK_MENU_IPO, false, true));

                // }
            }
            groupList.add(new ScreenDetails(R.drawable.logout_ic, context.getString(R.string.GREEK_MENU_LOGOUT_TXT), GREEK_MENU_LOGOUT_TXT, false));
        }


        return groupList;
    }

    public static Map<String, Vector<SubMenu>> createCollection(Context context, List<ScreenDetails> groupList) {

        Vector<SubMenu> portfolioSubMenu = new Vector<>();
        Vector<SubMenu> edisSubMenu = new Vector<>();
        Vector<SubMenu> reportSubMenu = new Vector<>();
        Vector<SubMenu> linksSubMenu = new Vector<>();
        Vector<SubMenu> settingSubMenu = new Vector<>();
        Vector<SubMenu> fundTransferSubMenu = new Vector<>();
        Vector<SubMenu> mutualFundSubMenu = new Vector<>();
        Vector<SubMenu> moreSubMenu = new Vector<>();

        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {

            if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {
                portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MYSTOCKS_TXT), GREEK_MENU_MYSTOCKS_TXT, false));
                portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_WATCHLIST_TXT), GREEK_MENU_WATCHLIST_TXT, true));
                portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_LASTVISITED_TXT), GREEK_MENU_LASTVISITED_TXT, false));
            } else {
                portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MYSTOCKS_TXT), GREEK_MENU_MYSTOCKS_TXT, true));
                portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_WATCHLIST_TXT), GREEK_MENU_WATCHLIST_TXT, true));
                portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_LASTVISITED_TXT), GREEK_MENU_LASTVISITED_TXT, true));
            }

            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_LEDGER_TXT), GREEK_MENU_LEDGER_TXT, true));//ok
            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_SAUDA_TXT), GREEK_MENU_SAUDA_TXT, true));//ok
//            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_P_L_TXT), GREEK_MENU_P_L_TXT, true));
            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_HOLDING_TXT), GREEK_MENU_HOLDING_TXT, true));//ok
//            reportSubMenu.add(new SubMenu("Combined Ledger", GREEK_MENU_COMBINED_LEDGER, true));
//            reportSubMenu.add(new SubMenu("Cost Report", GREEK_MENU_COST_REPORT, true));
//            reportSubMenu.add(new SubMenu("Interest Report", GREEK_MENU_INTEREST_REPORT, true));
//            reportSubMenu.add(new SubMenu("LAS Report", GREEK_MENU_LOAN_AGAINST_REPORT, true));
//            reportSubMenu.add(new SubMenu("F&O Profit And Loss", GREEK_MENU_PROFIT_AND_LOSS, true));
//            reportSubMenu.add(new SubMenu("Ageing Report", GREEK_MENU_CASH_PROFIT_AND_LOSS, true));

            edisSubMenu.add(new SubMenu("E-DIS Dashboard", GREEK_MENU_EDIS_DASHBOARD, true));
            edisSubMenu.add(new SubMenu("E-DIS Transaction Details", GREEK_MENU_EDIS_TRANSACTION_DETAILS, true));
            edisSubMenu.add(new SubMenu("Margin Pledge", GREEK_MENU_MARGIN_PLEDGE, true));


            moreSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_ABOUTUS_TXT), GREEK_MENU_ABOUTUS_TXT, false));
            moreSubMenu.add(new SubMenu(AccountDetails.getApr_version(), GREEK_MENU_VERSION_TXT, false));


            settingSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_APP_SETTING_TXT), GREEK_MENU_APP_SETTING_TXT, false));
            settingSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_CHANGE_PASSWORD_TXT), GREEK_MENU_CHANGE_PASSWORD_TXT, false));

            fundTransferSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_FUND_TRANSFERS_TXT), GREEK_MENU_FUND_TRANSFER_TXT, false));
            fundTransferSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_FUND_TRANSFER_DETAILS_TXT), GREEK_MENU_FUND_TRANSFER_DETAILS_TXT, false));

            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_DASHBOARD_TXT), GREEK_MENU_MF_DASHBOARD_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_WATCHLIST_TXT), GREEK_MENU_MF_WATCHLIST_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_TRANSACTION_TXT), GREEK_MENU_MF_TRANSACTION_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_REPORT_TXT), GREEK_MENU_MF_REPORT_TXT, false));


            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SEBI_LINKS_TXT), GREEK_SEBI_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_NSE_LINKS_TXT), GREEK_NSE_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_BSE_LINKS_TXT), GREEK_BSE_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MCX_LINKS_TXT), GREEK_MCX_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_NCDEX_LINKS_TXT), GREEK_NCDEX_LINKS_TXT, true));
            if(appname.equalsIgnoreCase("Vishwas")) {
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SSL_LINKS_TXT), GREEK_SSL_LINKS_TXT, true));
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_BACK_OFFICE_LINKS_TXT), GREEK_BACK_OFFICE_LINKS_TXT, true));
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_OFFLINE_BANK_LINKS_TXT), GREEK_OFFLINE_BANK_LINKS_TXT, true));
            }

            //linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SSL_LINKS_TXT), GREEK_SSL_LINKS_TXT, true));

        } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.MFCUSTOMER) {

            portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MYSTOCKS_TXT), GREEK_MENU_MYSTOCKS_TXT, false));
            portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_WATCHLIST_TXT), GREEK_MENU_WATCHLIST_TXT, true));
            portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_LASTVISITED_TXT), GREEK_MENU_LASTVISITED_TXT, false));

            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_LEDGER_TXT), GREEK_MENU_LEDGER_TXT, true));
            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_SAUDA_TXT), GREEK_MENU_SAUDA_TXT, true));
//            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_P_L_TXT), GREEK_MENU_P_L_TXT, true));
            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_HOLDING_TXT), GREEK_MENU_HOLDING_TXT, true));
//            reportSubMenu.add(new SubMenu("Combined Ledger", GREEK_MENU_COMBINED_LEDGER, true));
//            reportSubMenu.add(new SubMenu("Cost Report", GREEK_MENU_COST_REPORT, true));
//            reportSubMenu.add(new SubMenu("Interest Report", GREEK_MENU_INTEREST_REPORT, true));
//            reportSubMenu.add(new SubMenu("LAS Report", GREEK_MENU_LOAN_AGAINST_REPORT, true));
//            reportSubMenu.add(new SubMenu("Ageing Report", GREEK_MENU_CASH_PROFIT_AND_LOSS, true));


            edisSubMenu.add(new SubMenu("E-DIS Dashboard", GREEK_MENU_EDIS_DASHBOARD, true));
            edisSubMenu.add(new SubMenu("E-DIS Transaction Details", GREEK_MENU_EDIS_TRANSACTION_DETAILS, true));
            edisSubMenu.add(new SubMenu("Margin Pledge", GREEK_MENU_MARGIN_PLEDGE, true));



            moreSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_ABOUTUS_TXT), GREEK_MENU_ABOUTUS_TXT, false));
            moreSubMenu.add(new SubMenu(AccountDetails.getApr_version(), GREEK_MENU_VERSION_TXT, false));

            settingSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_APP_SETTING_TXT), GREEK_MENU_APP_SETTING_TXT, true));
            settingSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_CHANGE_PASSWORD_TXT), GREEK_MENU_CHANGE_PASSWORD_TXT, true));

            fundTransferSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_FUND_TRANSFERS_TXT), GREEK_MENU_FUND_TRANSFER_TXT, false));
            fundTransferSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_FUND_TRANSFER_DETAILS_TXT), GREEK_MENU_FUND_TRANSFER_DETAILS_TXT, false));

            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_DASHBOARD_TXT), GREEK_MENU_MF_DASHBOARD_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_WATCHLIST_TXT), GREEK_MENU_MF_WATCHLIST_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_TRANSACTION_TXT), GREEK_MENU_MF_TRANSACTION_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_REPORT_TXT), GREEK_MENU_MF_REPORT_TXT, false));

            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SEBI_LINKS_TXT), GREEK_SEBI_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_NSE_LINKS_TXT), GREEK_NSE_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_BSE_LINKS_TXT), GREEK_BSE_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MCX_LINKS_TXT), GREEK_MCX_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_NCDEX_LINKS_TXT), GREEK_NCDEX_LINKS_TXT, true));
            if(appname.equalsIgnoreCase("Vishwas")) {
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SSL_LINKS_TXT), GREEK_SSL_LINKS_TXT, true));
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_BACK_OFFICE_LINKS_TXT), GREEK_BACK_OFFICE_LINKS_TXT, true));
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_OFFLINE_BANK_LINKS_TXT), GREEK_OFFLINE_BANK_LINKS_TXT, true));
            }

//            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SSL_LINKS_TXT), GREEK_SSL_LINKS_TXT, true));

        } else if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.IBTCUSTOMER) {


            portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MYSTOCKS_TXT), GREEK_MENU_MYSTOCKS_TXT, true));
            portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_WATCHLIST_TXT), GREEK_MENU_WATCHLIST_TXT, true));
            portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_LASTVISITED_TXT), GREEK_MENU_LASTVISITED_TXT, true));


            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_LEDGER_TXT), GREEK_MENU_LEDGER_TXT, true));
            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_SAUDA_TXT), GREEK_MENU_SAUDA_TXT, true));
//            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_P_L_TXT), GREEK_MENU_P_L_TXT, true));
            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_HOLDING_TXT), GREEK_MENU_HOLDING_TXT, true));
//            reportSubMenu.add(new SubMenu("Combined Ledger", GREEK_MENU_COMBINED_LEDGER, true));
//            reportSubMenu.add(new SubMenu("Cost Report", GREEK_MENU_COST_REPORT, true));
//            reportSubMenu.add(new SubMenu("Interest Report", GREEK_MENU_INTEREST_REPORT, true));
//            reportSubMenu.add(new SubMenu("LAS Report", GREEK_MENU_LOAN_AGAINST_REPORT, true));
//            reportSubMenu.add(new SubMenu("Ageing Report", GREEK_MENU_CASH_PROFIT_AND_LOSS, true));

            edisSubMenu.add(new SubMenu("E-DIS Dashboard", GREEK_MENU_EDIS_DASHBOARD, true));
            edisSubMenu.add(new SubMenu("E-DIS Transaction Details", GREEK_MENU_EDIS_TRANSACTION_DETAILS, true));
            edisSubMenu.add(new SubMenu("Margin Pledge", GREEK_MENU_MARGIN_PLEDGE, true));


            moreSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_ABOUTUS_TXT), GREEK_MENU_ABOUTUS_TXT, true));
            moreSubMenu.add(new SubMenu(AccountDetails.getApr_version(), GREEK_MENU_VERSION_TXT, true));

            settingSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_APP_SETTING_TXT), GREEK_MENU_APP_SETTING_TXT, true));
            settingSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_CHANGE_PASSWORD_TXT), GREEK_MENU_CHANGE_PASSWORD_TXT, true));
            settingSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_CHANGE_THEME_TXT), GREEK_MENU_CHANGE_THEME_TXT, true));

            fundTransferSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_FUND_TRANSFERS_TXT), GREEK_MENU_FUND_TRANSFER_TXT, true));
            fundTransferSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_FUND_TRANSFER_DETAILS_TXT), GREEK_MENU_FUND_TRANSFER_DETAILS_TXT, true));

            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_DASHBOARD_TXT), GREEK_MENU_MF_DASHBOARD_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_WATCHLIST_TXT), GREEK_MENU_MF_WATCHLIST_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_TRANSACTION_TXT), GREEK_MENU_MF_TRANSACTION_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_REPORT_TXT), GREEK_MENU_MF_REPORT_TXT, false));

            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SEBI_LINKS_TXT), GREEK_SEBI_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_NSE_LINKS_TXT), GREEK_NSE_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_BSE_LINKS_TXT), GREEK_BSE_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MCX_LINKS_TXT), GREEK_MCX_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_NCDEX_LINKS_TXT), GREEK_NCDEX_LINKS_TXT, true));
            if(appname.equalsIgnoreCase("Vishwas")) {
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SSL_LINKS_TXT), GREEK_SSL_LINKS_TXT, true));
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_BACK_OFFICE_LINKS_TXT), GREEK_BACK_OFFICE_LINKS_TXT, true));
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_OFFLINE_BANK_LINKS_TXT), GREEK_OFFLINE_BANK_LINKS_TXT, true));
            }

//            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SSL_LINKS_TXT), GREEK_SSL_LINKS_TXT, true));

        } else {
            portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MYSTOCKS_TXT), GREEK_MENU_MYSTOCKS_TXT, true));
            portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_WATCHLIST_TXT), GREEK_MENU_WATCHLIST_TXT, true));
            portfolioSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_LASTVISITED_TXT), GREEK_MENU_LASTVISITED_TXT, true));


            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_LEDGER_TXT), GREEK_MENU_LEDGER_TXT, false));
            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_SAUDA_TXT), GREEK_MENU_SAUDA_TXT, false));
//            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_P_L_TXT), GREEK_MENU_P_L_TXT, false));
            reportSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_HOLDING_TXT), GREEK_MENU_HOLDING_TXT, false));
//            reportSubMenu.add(new SubMenu("Combined Ledger", GREEK_MENU_COMBINED_LEDGER, false));
//            reportSubMenu.add(new SubMenu("Cost Report", GREEK_MENU_COST_REPORT, false));
//            reportSubMenu.add(new SubMenu("Interest Report", GREEK_MENU_INTEREST_REPORT, false));
//            reportSubMenu.add(new SubMenu("LAS Report", GREEK_MENU_LOAN_AGAINST_REPORT, false));
//            reportSubMenu.add(new SubMenu("Ageing Report", GREEK_MENU_CASH_PROFIT_AND_LOSS, false));

            edisSubMenu.add(new SubMenu("E-DIS Dashboard", GREEK_MENU_EDIS_DASHBOARD, false));
            edisSubMenu.add(new SubMenu("E-DIS Transaction Details", GREEK_MENU_EDIS_TRANSACTION_DETAILS, false));
            edisSubMenu.add(new SubMenu("Margin Pledge", GREEK_MENU_MARGIN_PLEDGE, false));



            moreSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_ABOUTUS_TXT), GREEK_MENU_ABOUTUS_TXT, false));
            moreSubMenu.add(new SubMenu(AccountDetails.getApr_version(), GREEK_MENU_VERSION_TXT, false));


            settingSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_APP_SETTING_TXT), GREEK_MENU_APP_SETTING_TXT, true));
            settingSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_CHANGE_PASSWORD_TXT), GREEK_MENU_CHANGE_PASSWORD_TXT, true));
            settingSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_CHANGE_THEME_TXT), GREEK_MENU_CHANGE_THEME_TXT, false));

            fundTransferSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_FUND_TRANSFERS_TXT), GREEK_MENU_FUND_TRANSFER_TXT, true));
            fundTransferSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_FUND_TRANSFER_DETAILS_TXT), GREEK_MENU_FUND_TRANSFER_DETAILS_TXT, true));

            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_DASHBOARD_TXT), GREEK_MENU_MF_DASHBOARD_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_WATCHLIST_TXT), GREEK_MENU_MF_WATCHLIST_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_TRANSACTION_TXT), GREEK_MENU_MF_TRANSACTION_TXT, false));
            mutualFundSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MENU_MF_REPORT_TXT), GREEK_MENU_MF_REPORT_TXT, false));

            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SEBI_LINKS_TXT), GREEK_SEBI_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_NSE_LINKS_TXT), GREEK_NSE_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_BSE_LINKS_TXT), GREEK_BSE_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_MCX_LINKS_TXT), GREEK_MCX_LINKS_TXT, true));
            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_NCDEX_LINKS_TXT), GREEK_NCDEX_LINKS_TXT, true));
            if(appname.equalsIgnoreCase("Vishwas")) {
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SSL_LINKS_TXT), GREEK_SSL_LINKS_TXT, true));
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_BACK_OFFICE_LINKS_TXT), GREEK_BACK_OFFICE_LINKS_TXT, true));
                linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_OFFLINE_BANK_LINKS_TXT), GREEK_OFFLINE_BANK_LINKS_TXT, true));
            }

//            linksSubMenu.add(new SubMenu(context.getString(R.string.GREEK_SSL_LINKS_TXT), GREEK_SSL_LINKS_TXT, true));
        }


        Map<String, Vector<SubMenu>> subMenuCollections = new LinkedHashMap<>();

        for (ScreenDetails scrdts : groupList) {

            if (scrdts.getTitle().equals(context.getString(R.string.GREEK_MENU_PORTFOLIO_TXT))) {
                subMenuCollections.put(scrdts.getTitle(), portfolioSubMenu);
            } else if (scrdts.getTitle().equals(context.getString(R.string.GREEK_MENU_REPORT_TXT))) {
                subMenuCollections.put(scrdts.getTitle(), reportSubMenu);
            } else if (scrdts.getTitle().equals(context.getString(R.string.GREEK_MENU_EDIS_TXT))) {
                subMenuCollections.put(scrdts.getTitle(), edisSubMenu);
            } else if (scrdts.getTitle().equals(context.getString(R.string.GREEK_MENU_LINKS))) {
                subMenuCollections.put(scrdts.getTitle(), linksSubMenu);
            } else if (scrdts.getTitle().equals(context.getString(R.string.GREEK_MENU_SETTINGS_TXT))) {
                subMenuCollections.put(scrdts.getTitle(), settingSubMenu);
            } else if (scrdts.getTitle().equals(context.getString(R.string.GREEK_MENU_FUND_TRANSFER))) {
                subMenuCollections.put(scrdts.getTitle(), fundTransferSubMenu);
            } else if (scrdts.getTitle().equals(context.getString(R.string.GREEK_MENU_MUTFUNDS_TXT))) {
                subMenuCollections.put(scrdts.getTitle(), mutualFundSubMenu);
            } else if (scrdts.getTitle().equals(context.getString(R.string.GREEK_MENU_MORE_TXT))) {
                subMenuCollections.put(scrdts.getTitle(), moreSubMenu);
            } else {
                subMenuCollections.put(scrdts.getTitle(), new Vector<SubMenu>());
            }
        }
        return subMenuCollections;
    }
}
