package com.acumengroup.mobile.reports;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.trade.TradeBook;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

/**
 * Created by summert on 6/16/2015.
 */

public class TradeBookDetailsFragment extends GreekBaseFragment {
    private View tradeBookDetailsView;
    LinearLayout orderDetails;
    private Boolean changeColor = true;
    GreekBaseActivity greekBaseActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tradeBookDetailsView = super.onCreateView(inflater, container, savedInstanceState);

        //attachLayout(R.layout.fragment_tradebook_details);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_tradebook_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_tradebook_details).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        AccountDetails.currentFragment = NAV_TO_TRADE_BOOK_DETAILS_SCREEN;
        setupView();

        return tradeBookDetailsView;
    }

    private void setupView() {
        setAppTitle(getClass().toString(), "Trade Details");
        orderDetails = tradeBookDetailsView.findViewById(R.id.order_details);

        parseReceivedArguments();
    }

    private void parseReceivedArguments() {

        TradeBook tradeBookData = (TradeBook) getArguments().getSerializable("Response");

        if (getExchange(tradeBookData.getToken()).equalsIgnoreCase("MCX")) {

            if (tradeBookData.getOptionType().equalsIgnoreCase("XX")) {
                createPositionsRow("Symbol", ":\t" + tradeBookData.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(tradeBookData.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + tradeBookData.getInstrument());
            } else {
                createPositionsRow("Symbol", ":\t" + tradeBookData.getTradeSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(tradeBookData.getExpiryDate(), "yyMMM", "bse").toUpperCase() + tradeBookData.getStrikePrice() + tradeBookData.getOptionType() + "-" + tradeBookData.getInstrument());
            }


        } else {

            createPositionsRow("Symbol", ":\t" + tradeBookData.getSymbol() + " - " + tradeBookData.getInstrument());
        }

//        createPositionsRow("Exchange ID", ":\t" + tradeBookData.getOrdID());
//        createPositionsRow("Order ID", ":\t" + tradeBookData.getUniqueOrderID());
//        if (tradeBookData.getProduct().equalsIgnoreCase("1")) {
//            createPositionsRow("Product Type", ":\t" + "Intraday");
//        } else if (tradeBookData.getProduct().equalsIgnoreCase("0")) {
//            createPositionsRow("Product Type", ":\t" + "Delivery");
//        } else if (tradeBookData.getProduct().equalsIgnoreCase("2")) {
//            createPositionsRow("Product Type", ":\t" + "MTF");
//        }else if (tradeBookData.getProduct().equalsIgnoreCase("3")) {
//            createPositionsRow("Product Type", ":\t" + "SSEQ");
//        }


        createPositionsRow("Exchange ID", ":\t" + tradeBookData.getOrdID());
        createPositionsRow("Order ID", ":\t" + tradeBookData.getUniqueOrderID());
        if (tradeBookData.getProduct().equalsIgnoreCase("1")) {
            createPositionsRow("Product Type", ":\t" + "Intraday");
        } else if (tradeBookData.getProduct().equalsIgnoreCase("0")) {
            createPositionsRow("Product Type", ":\t" + "Delivery");
        } else if (tradeBookData.getProduct().equalsIgnoreCase("2")) {
            createPositionsRow("Product Type", ":\t" + "MTF");
        } else if (tradeBookData.getProduct().equalsIgnoreCase("5")) {
            createPositionsRow("Product Type", ":\t" + "SSEQ");
        } else if (tradeBookData.getProduct().equalsIgnoreCase("3")) {
            createPositionsRow("Product Type", ":\t" + "TNC");
        } else if (tradeBookData.getProduct().equalsIgnoreCase("4")) {
            createPositionsRow("Product Type", ":\t" + "CATALYST");
        }

        if (tradeBookData.getAction().equalsIgnoreCase("1")) {
            createPositionsRow("Action", ":\t" + "BUY");
        } else {
            createPositionsRow("Action", ":\t" + "SELL");
        }

        if (!tradeBookData.getExpiryDate().equalsIgnoreCase("0")) {
            createPositionsRow("Expiry Date", ":\t" + DateTimeFormatter.getDateFromTimeStamp(tradeBookData.getExpiryDate(), "dd MMM yyyy", "bse"));
        }

        greekBaseActivity = new GreekBaseActivity();
        if (greekBaseActivity.getExchange(tradeBookData.getToken()).equalsIgnoreCase("mcx") || greekBaseActivity.getExchange(tradeBookData.getToken()).equalsIgnoreCase("ncdex")) {
            createPositionsRow("Total Order Quantity", ":\t" + (Integer.parseInt(tradeBookData.getQty())) / Integer.parseInt(tradeBookData.getRegular_lot()));
            createPositionsRow("Traded Quantity", ":\t" + (Integer.parseInt(tradeBookData.getTrdQty())) / Integer.parseInt(tradeBookData.getRegular_lot()));
            if (tradeBookData.getPendingQty() != null) {
                createPositionsRow("Pending Quantity", ":\t" + (Integer.parseInt(tradeBookData.getPendingQty())) / Integer.parseInt(tradeBookData.getRegular_lot()));
            } else {
                createPositionsRow("Pending Quantity", ":\t" + tradeBookData.getPendingQty());
            }
        } else {
            createPositionsRow("Total Order Quantity", ":\t" + tradeBookData.getQty());
            createPositionsRow("Traded Quantity", ":\t" + tradeBookData.getTrdQty());
            createPositionsRow("Pending Quantity", ":\t" + tradeBookData.getPendingQty());
        }

       /* if (tradeBookData.getExchange().equalsIgnoreCase("ncd") || tradeBookData.getExchange().equalsIgnoreCase("bcd")) {

        } else {
            createPositionsRow("Order Price", ":\t" + tradeBookData.getPrice());
        }*/

        if (((Integer.valueOf(tradeBookData.getToken()) >= 502000000) && (Integer.valueOf(tradeBookData.getToken()) <= 502999999)) || ((Integer.valueOf(tradeBookData.getToken()) >= 1302000000) && (Integer.valueOf(tradeBookData.getToken()) <= 1302999999))) {
            createPositionsRow("Order Price", ":\t" + String.format("%.4f", Double.parseDouble(tradeBookData.getPrice())));

        } else {
            createPositionsRow("Order Price", ":\t" + tradeBookData.getPrice());
        }
        createPositionsRow("Trade number", ":\t" + tradeBookData.getTradeNo());
        createPositionsRow("Trade Time", ":\t" + tradeBookData.getTrdTime());
        createPositionsRow("Pan Number", ":\t" + tradeBookData.getcPANNumber());
    }

    private void createPositionsRow(String key, String value) {
        int color;

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            color = (changeColor) ? R.color.floatingBgColor : R.color.backgroundStripColorWhite;
        } else {
            color = (changeColor) ? R.color.market_grey_dark : R.color.market_grey_light;
        }


        TableRow Row = new TableRow(getMainActivity());
        GreekTextView keyView = new GreekTextView(getMainActivity());
        keyView.setPadding(5, 12, 5, 12);
        keyView.setText(key);
        keyView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        keyView.setBackgroundColor(getResources().getColor(color));
        keyView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params = (TableRow.LayoutParams) keyView.getLayoutParams();
        params.weight = 1;
        params.bottomMargin = 1;
        keyView.setPadding(10, 10, 10, 10);

        GreekTextView valueView = new GreekTextView(getMainActivity());
        valueView.setPadding(10, 12, 5, 12);
        valueView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        valueView.setText(value);

        if (":\tBuy".equalsIgnoreCase(value))
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            }else{
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
            }
        else if (":\tSell".equalsIgnoreCase(value))
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));

        valueView.setTypeface(Typeface.DEFAULT_BOLD);
        valueView.setGravity(GravityCompat.START);
        if (":\tBuy".equalsIgnoreCase(value))
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            }else{
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
            }
        else if (":\tSell".equalsIgnoreCase(value))
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));
        valueView.setBackgroundColor(getResources().getColor(color));

        valueView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params1 = (TableRow.LayoutParams) valueView.getLayoutParams();
        params1.weight = 1;
        params1.bottomMargin = 1;

        Row.addView(keyView);
        Row.addView(valueView);
        orderDetails.addView(Row);

        changeColor = !changeColor;
    }

    private String getExchange(String token) {
        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 102999999)) || ((tokenInt >= 502000000) && (tokenInt <= 502999999))) {
            return "NSE";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999))) {
            return "NCDEX";
        } else if (((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "MCX";
        } else {
            return "BSE";
        }
        //  return "";
    }
}