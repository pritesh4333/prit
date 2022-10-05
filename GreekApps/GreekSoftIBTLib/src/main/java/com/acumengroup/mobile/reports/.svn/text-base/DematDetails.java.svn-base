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
import com.acumengroup.greekmain.core.model.DematHolding;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

public class DematDetails extends GreekBaseFragment {

    private View orderDetailsView;
    private LinearLayout orderDetails;
    private GreekBaseActivity greekBaseActivity;
    private Boolean changeColor = true;
    private DematHolding dematResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        orderDetailsView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_position_order_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_position_order_details).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        setupView();

        return orderDetailsView;
    }


    private void setupView() {
        setAppTitle(getClass().toString(), "Demat Details");
        orderDetails = orderDetailsView.findViewById(R.id.order_details);

        parseReceivedArguments();
    }

    private void parseReceivedArguments() {

        dematResponse = (DematHolding) getArguments().getSerializable("response");
       /* createPositionsRow("Buy Quantity", ":\t" + getArguments().getString("buyqty"));*/

        createPositionsRow("Symbol", ":\t" + dematResponse.getScripName());
        createPositionsRow("Trade Symbol", ":\t" + dematResponse.getNSETradeSymbol());
        createPositionsRow("Actual DP Quantity", ":\t" + dematResponse.getActualDPQty());
        createPositionsRow("Actual Pool Quantity", ":\t" + dematResponse.getActualPoolQty());
        createPositionsRow("Actual DP Price", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getActualDPPrice())));
        createPositionsRow("Actual Pool Price", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getActualPoolPrice())));
        createPositionsRow("Actual Dp Amount", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getActualDPAmount())));
        createPositionsRow("Actual Pool Amount", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getActualPoolAmount())));
        createPositionsRow("Total Sell Quantity", ":\t" + dematResponse.getTSellQty());
        createPositionsRow("Total Buy Quantity", ":\t" + dematResponse.getTBuyQty());
        createPositionsRow("Total Sell Amount", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getTSellAmt())));
        createPositionsRow("Total Buy Amount", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getTBuyAmt())));
        createPositionsRow("Total Sell ATP", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getTSellATP())));
        createPositionsRow("Total Buy ATP", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getTBuyATP())));
        createPositionsRow("Total Net Quantity", ":\t" + dematResponse.getTNQ());
        createPositionsRow("DP Quantity", ":\t" + dematResponse.getDPQty());
        createPositionsRow("DP Value", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getDPValue())));
        createPositionsRow("DP Price", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getDPPrice())));
        createPositionsRow("Pool Quantity", ":\t" + dematResponse.getPoolQty());
        createPositionsRow("Pool Value", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getPoolValue())));
        createPositionsRow("Pool Price", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getPoolPrice())));
        createPositionsRow("Net Holding Quantity", ":\t" + dematResponse.getNetHQty());
        createPositionsRow("Net Holding Value", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getNetHValue())));
        createPositionsRow("Net Holding Price", ":\t" + String.format("%.2f", Double.parseDouble(dematResponse.getNetHPrice())));
        createPositionsRow("Sold Quantity", ":\t" + dematResponse.getSoldQty());
        createPositionsRow("Pending Quantity", ":\t" + dematResponse.getPendingQty());
        createPositionsRow("Risk Block Quantity", ":\t" + dematResponse.getRiskBlockQty());
        createPositionsRow("Free Holding Quantity", ":\t" + dematResponse.getFreeHoldingQty());
    }

    private String getAssetTypeFromToken(String token) {
        int tokenInt = Integer.parseInt(token);
        if (((tokenInt >= 101000000) && (tokenInt <= 101999999)) || ((tokenInt >= 201000000) && (tokenInt <= 201999999))) {
            return "equity";
        } else if (((tokenInt >= 102000000) && (tokenInt <= 102999999)) || ((tokenInt >= 202000000) && (tokenInt <= 202999999))) {
            return "fno";
        } else if (((tokenInt >= 403000000) && (tokenInt <= 403999999)) || ((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "commodity";
        } else {
            return "currency";
        }

        // return "";
    }


    private void createPositionsRow(String key, String value) {
        int color = 0;
        //int color = (changeColor) ? R.color.market_grey_light : R.color.market_grey_dark;

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
            }else {
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
            }
        else if (":\tSell".equalsIgnoreCase(value))
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));

        valueView.setTypeface(Typeface.DEFAULT_BOLD);
        valueView.setGravity(GravityCompat.START);
        if (":\tBuy".equalsIgnoreCase(value))
            if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            }else {
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


    }
}
