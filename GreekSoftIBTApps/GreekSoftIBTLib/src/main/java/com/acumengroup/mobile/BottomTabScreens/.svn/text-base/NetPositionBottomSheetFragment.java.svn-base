package com.acumengroup.mobile.BottomTabScreens;

import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.core.view.GravityCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastResponse;
import com.acumengroup.greekmain.core.network.ProductChangeResponse;
import com.acumengroup.greekmain.core.network.StreamingResponse;
import com.acumengroup.greekmain.core.services.CustomNetPositionSummary;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.reports.CustomNetpositionDialogFragment;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

import static com.acumengroup.greekmain.core.constants.GreekConstants.Catalyst_product;
import static com.acumengroup.mobile.GreekBaseActivity.GREEK;


public class NetPositionBottomSheetFragment extends BottomSheetDialogFragment {

    private View orderDetailsView;
    private LinearLayout orderDetails, net_position_bg, net_linear_bg, footer_buttons;
    private GreekBaseActivity greekBaseActivity;
    private Boolean changeColor = true;
    private GreekTextView symbolnametxt, exchangetxt, ltptxt, buyQtytxt, sellQtytxt, buyAmttxt, buyPricetxt, sellPricetxt, sellAmttxt, unrealizedgltxt, realizedgl, totalgltxt, producttypetxt, mtmtxt;
    private Button sqrOffBtn, prodChangeBtn;
    private GreekTextView buy_qty, sell_qty1, buy_amt, buy_price, sell_price, sell_amt, unrealized, realized, totalgl, prduct_type, mtm1;
    private CustomNetPositionSummary customNetPositionSummary;
    private StreamingController streamingController;
    private View view1, view2, view3;
    private ArrayList<String> sym = new ArrayList<>();


    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            dismiss();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        orderDetailsView = inflater.inflate(R.layout.fragment_netposition_bottom_layout, container, false);

      /*  if(getArguments().getString("from").equalsIgnoreCase("openposition")) {
            AccountDetails.currentFragment = GREEK_MENU_OPENPOSITION;
        } else if(getArguments().getString("from").equalsIgnoreCase("netposition")) {
            AccountDetails.currentFragment = NAV_TO_NETPOSITION_SCREEN;
        } else if(getArguments().getString("from").equalsIgnoreCase("cumposition")) {
            AccountDetails.currentFragment = NAV_TO_CUMULATIVE_SCREEN;
        }*/
        setupView();

        return orderDetailsView;
    }


    private void setupView() {

        streamingController = new StreamingController();
        orderDetails = orderDetailsView.findViewById(R.id.order_details);
        view1 = orderDetailsView.findViewById(R.id.view1);
        view2 = orderDetailsView.findViewById(R.id.view2);
        view3 = orderDetailsView.findViewById(R.id.view3);
        unrealizedgltxt = orderDetailsView.findViewById(R.id.unrealizedgltxt);
        realizedgl = orderDetailsView.findViewById(R.id.realizedgl);
        totalgltxt = orderDetailsView.findViewById(R.id.totalgltxt);
        producttypetxt = orderDetailsView.findViewById(R.id.producttypetxt);
        mtmtxt = orderDetailsView.findViewById(R.id.mtmtxt);
        buy_qty = orderDetailsView.findViewById(R.id.buy_qty);
        sell_qty1 = orderDetailsView.findViewById(R.id.sell_qty1);
        buy_amt = orderDetailsView.findViewById(R.id.buy_amt);
        buy_price = orderDetailsView.findViewById(R.id.buy_price);
        sell_price = orderDetailsView.findViewById(R.id.sell_price);
        sell_amt = orderDetailsView.findViewById(R.id.sell_amt);
        unrealized = orderDetailsView.findViewById(R.id.unrealized);
        realized = orderDetailsView.findViewById(R.id.realized);
        totalgl = orderDetailsView.findViewById(R.id.totalgl);
        prduct_type = orderDetailsView.findViewById(R.id.prduct_type);
        mtm1 = orderDetailsView.findViewById(R.id.mtm1);
        net_position_bg = orderDetailsView.findViewById(R.id.net_position_bg);
        symbolnametxt = orderDetailsView.findViewById(R.id.symbolnametxt);
        exchangetxt = orderDetailsView.findViewById(R.id.exchangetxt);
        net_linear_bg = orderDetailsView.findViewById(R.id.net_linear_bg);
        footer_buttons = orderDetailsView.findViewById(R.id.footer_buttons);
        ltptxt = orderDetailsView.findViewById(R.id.ltptxt);
        buyQtytxt = orderDetailsView.findViewById(R.id.buyQtytxt);
        sellQtytxt = orderDetailsView.findViewById(R.id.sellQtytxt);
        buyAmttxt = orderDetailsView.findViewById(R.id.buyAmttxt);
        buyPricetxt = orderDetailsView.findViewById(R.id.buyPricetxt);
        sellPricetxt = orderDetailsView.findViewById(R.id.sellPricetxt);
        sellAmttxt = orderDetailsView.findViewById(R.id.sellAmttxt);
        unrealizedgltxt = orderDetailsView.findViewById(R.id.unrealizedgltxt);
        realizedgl = orderDetailsView.findViewById(R.id.realizedgl);
        totalgltxt = orderDetailsView.findViewById(R.id.totalgltxt);
        producttypetxt = orderDetailsView.findViewById(R.id.producttypetxt);
        mtmtxt = orderDetailsView.findViewById(R.id.mtmtxt);


        sqrOffBtn = (GreekButton) orderDetailsView.findViewById(R.id.sqroff_btn);
        sqrOffBtn.setOnClickListener(sqrOffClickListener);

        prodChangeBtn = (GreekButton) orderDetailsView.findViewById(R.id.prodchange_btn);
        prodChangeBtn.setOnClickListener(prodChangeClickListener);

        customNetPositionSummary = (CustomNetPositionSummary) getArguments().getSerializable("response");
        parseReceivedArguments();
        setTheme();
    }

    private void setTheme() {

        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            view1.setBackgroundColor(getResources().getColor(R.color.black));
            view2.setBackgroundColor(getResources().getColor(R.color.black));
            view3.setBackgroundColor(getResources().getColor(R.color.black));
            net_linear_bg.setBackgroundColor(getResources().getColor(R.color.buttonColor));
            footer_buttons.setBackgroundColor(getResources().getColor(R.color.marketDepthGreyColor));
            net_position_bg.setBackgroundColor(getResources().getColor(R.color.white));
            mtmtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            symbolnametxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            producttypetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sellAmttxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            exchangetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            ltptxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sellPricetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            buyAmttxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            buyPricetxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            buyQtytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sellQtytxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sell_qty1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            buy_qty.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            buy_amt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            buy_price.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sell_price.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            sell_amt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            unrealized.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            realized.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalgl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            prduct_type.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            mtm1.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            unrealizedgltxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            realizedgl.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            totalgltxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }
    }


    private final View.OnClickListener sqrOffClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (!getProductType(customNetPositionSummary.getProductType()).toUpperCase().equalsIgnoreCase(getProductType(Catalyst_product))) {
                Bundle bundle = new Bundle();

                if (AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {
                    bundle.putString("Token", customNetPositionSummary.getSqoffToken());
                    bundle.putString("ExchangeName", getExchange(customNetPositionSummary.getSqoffToken()));
                    bundle.putString("AssetType", getAssetTypeFromToken(customNetPositionSummary.getSqoffToken()));
                } else {
                    bundle.putString("Token", customNetPositionSummary.getSqoffToken());
                    bundle.putString("ExchangeName", getExchange(customNetPositionSummary.getSqoffToken()));
                    bundle.putString("AssetType", getAssetTypeFromToken(customNetPositionSummary.getSqoffToken()));
                }

                if (Double.parseDouble(customNetPositionSummary.getPreNetQty()) + Double.parseDouble(customNetPositionSummary.getNetQty()) >= 0) {
                    bundle.putString("Action", "sell");
                } else {
                    bundle.putString("Action", "buy");
                }/*if(Integer.parseInt(customNetPositionSummary.getNetQty()) >= 0) {
                    bundle.putString("Action", "sell");
                } else {
                    bundle.putString("Action", "buy");
                }*/
                bundle.putString("TradeSymbol", customNetPositionSummary.getTradeSymbol());
                if (customNetPositionSummary.getProductType().isEmpty()) {
                    bundle.putString("Product", customNetPositionSummary.getProduct());
                } else {
                    bundle.putString("Product", customNetPositionSummary.getProductType());
                }

                int qty = (Integer.parseInt(customNetPositionSummary.getNetQty()) +
                        Integer.parseInt(customNetPositionSummary.getPreNetQty()));

                bundle.putString("Qty", qty + "");


                if (getAssetTypeFromToken(customNetPositionSummary.getSqoffToken()).equalsIgnoreCase("commodity")) {

                    qty = Integer.parseInt(customNetPositionSummary.getNetQty()) + (Integer.parseInt(customNetPositionSummary.getPreNetQty()));
                    bundle.putString("NetQty", String.valueOf(qty));

                } else if (getAssetTypeFromToken(customNetPositionSummary.getSqoffToken()).equalsIgnoreCase("currency")) {

                    qty = Integer.parseInt(customNetPositionSummary.getNetQty()) + (Integer.parseInt(customNetPositionSummary.getPreNetQty()));
                    bundle.putString("NetQty", String.valueOf(qty));

                } else {
                    int totalQty = Integer.parseInt(customNetPositionSummary.getPreNetQty()) + Integer.parseInt(customNetPositionSummary.getNetQty());
                    bundle.putString("NetQty", totalQty + "");
                }


                bundle.putString("Price", "");
                bundle.putString("SymbolName", customNetPositionSummary.getTradeSymbol());
                bundle.putString("Lots", customNetPositionSummary.getLotQty());
                bundle.putBoolean("isModifyOrder", false);
                bundle.putBoolean("isSquareOff", true);
                bundle.putString("TickSize", customNetPositionSummary.getTickSize());
                bundle.putString("Multiplier", customNetPositionSummary.getMultiplier());
                AccountDetails.globalPlaceOrderBundle = bundle;
                EventBus.getDefault().post("placeorder");
                dismiss();

            } else {

                GreekDialog.alertDialog(getActivity(), 0, GREEK, "\nCould Not SquareOff Retailer Position For " + getProductType(Catalyst_product) + " Product ", "OK", false, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        //refresh();
                        //sendPTRRequest();
                    }
                });
            }
        }
    };

    private final View.OnClickListener prodChangeClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
//            if(!getProductType(customNetPositionSummary.getProductType()).toUpperCase().equalsIgnoreCase("CATALYST")) {
            Bundle args = new Bundle();

            if (AccountDetails.getNetpositionChecked().equalsIgnoreCase("false")) {
                args.putString("gtoken", customNetPositionSummary.getToken());
                args.putString("exchange", getExchange(customNetPositionSummary.getToken()));
                args.putString("assetType", getAssetTypeFromToken(customNetPositionSummary.getToken()));
            } else {
                args.putString("gtoken", customNetPositionSummary.getToken());
                args.putString("exchange", getExchange(customNetPositionSummary.getToken()));
                args.putString("assetType", getAssetTypeFromToken(customNetPositionSummary.getToken()));
            }
            int qtylot = Math.abs(Integer.parseInt(customNetPositionSummary.getNetQty()) / Integer.parseInt(customNetPositionSummary.getLotQty()));
            args.putString("qtylot", String.valueOf(qtylot));
            args.putString("lot", customNetPositionSummary.getLotQty());
            //args.putString("qty", String.valueOf(Math.abs(Integer.parseInt(rowData.getNetQty()))));
            if (customNetPositionSummary.getProductType().isEmpty()) {
                args.putString("product", customNetPositionSummary.getProduct());
            } else {
                args.putString("product", customNetPositionSummary.getProductType());
            }
            if (getExchange(customNetPositionSummary.getToken()).equalsIgnoreCase("mcx")
                    || getExchange(customNetPositionSummary.getToken()).equalsIgnoreCase("ncdex")) {

                qtylot = Math.abs(Integer.parseInt(customNetPositionSummary.getNetQty()));
                args.putString("traded_qty_abs", String.valueOf(qtylot));
                args.putString("traded_qty", String.valueOf(Integer.parseInt(customNetPositionSummary.getNetQty())));
                args.putString("qty", String.valueOf(Integer.parseInt(customNetPositionSummary.getNetQty())));
            } else {
                args.putString("traded_qty_abs", String.valueOf(Math.abs(Integer.parseInt(customNetPositionSummary.getNetQty()))));
                args.putString("traded_qty", customNetPositionSummary.getNetQty());
                args.putString("qty", customNetPositionSummary.getNetQty());


            }

            args.putString("multiplier", customNetPositionSummary.getMultiplier());
            args.putString("gorderid", "");
            args.putString("tradeid", "");
            args.putString("eorderid", "");
            args.putString("side", "");
            args.putString("from", "Netposition");
            CustomNetpositionDialogFragment customNetpositionDialogFragment = new CustomNetpositionDialogFragment();
            customNetpositionDialogFragment.setArguments(args);
            customNetpositionDialogFragment.show(getFragmentManager(), "ProductChange");

            dismiss();

          /*  }else{

                GreekDialog.alertDialog(getActivity(), 0, GREEK, "\n  Could not Change product delear " + "", "OK", false, new GreekDialog.DialogListener() {
                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {
                        //refresh();
                        //sendPTRRequest();
                    }
                });
            }*/

        }

    };


    private void parseReceivedArguments() {

        if (getExchange(customNetPositionSummary.getToken()).equalsIgnoreCase("mcx")) {
            //if(getArguments().getString("optionType").equalsIgnoreCase("XX"))
            //{
            symbolnametxt.setText(customNetPositionSummary.getDescription());
            exchangetxt.setText(getExchange(customNetPositionSummary.getToken()));
            //createPositionsRow("Symbol", ":\t" + getArguments().getString("tradesymbol") + "" + DateTimeFormatter.getDateFromTimeStamp(getArguments().getString("expiry"), "yyMMM", "bse").toUpperCase()
            //+ "-" + getArguments().getString("instName"));
            // }
            //else {

            //symbolnametxt.setText(customNetPositionSummary.getTradeSymbol());
            //exchangetxt.setText(customNetPositionSummary.getToken());
            //createPositionsRow("Symbol", ":\t" + getArguments().getString("tradesymbol") + "" + DateTimeFormatter.getDateFromTimeStamp(getArguments().getString("expiry"), "yyMMM", "bse").toUpperCase()+getArguments().getString("strikePrice")
            //       + getArguments().getString("optionType")+"-" + getArguments().getString("instName"));
            //}

        } else {
            symbolnametxt.setText(customNetPositionSummary.getDescription());
            exchangetxt.setText(getExchange(customNetPositionSummary.getToken()));
            //createPositionsRow("Symbol", ":\t" + getArguments().getString("tradesymbol"));
        }


        //createPositionsRow("Description", ":\t" + getArguments().getString("description"));
        //createPositionsRow("Exchange", ":\t" + getArguments().getString("ExchangeName"));
        //createPositionsRow("Net Quantity", ":\t" + getArguments().getString("qty"));
        //createPositionsRow("Product", ":\t" + getArguments().getString("ProductType"));

        if (!customNetPositionSummary.getProductType().equalsIgnoreCase("")) {
//            producttypetxt.setText(getProductType(customNetPositionSummary.getProductType()).toUpperCase());
            producttypetxt.setText(getprducttypename(customNetPositionSummary.getProductType()).toUpperCase());
        } else {
            producttypetxt.setText(getProductType(customNetPositionSummary.getProduct()).toUpperCase());
        }

        if (getArguments() != null && getArguments().getString("fromPage").equalsIgnoreCase("openposition")) {
            prodChangeBtn.setEnabled(false);
        }


        if (getAssetTypeFromToken(customNetPositionSummary.getToken()).equalsIgnoreCase("currency")) {
            //createPositionsRow("Net Amount", ":\t" + String.format("%.4f", Double.parseDouble(getArguments().getString("netamt"))));
            //createPositionsRow("Net Price", ":\t" + String.format("%.4f", Double.parseDouble(getArguments().getString("netprice"))));
            /*createPositionsRow("LTP", ":\t" + String.format("%.4f", Double.parseDouble(getArguments().getString("LTP"))));
            createPositionsRow("MTM", ":\t" + String.format("%.4f", Double.parseDouble(getArguments().getString("MTM"))));
            createPositionsRow("Sell Amount", ":\t" + String.format("%.4f", Double.parseDouble(getArguments().getString("sellamt"))));
            createPositionsRow("Buy Amount", ":\t" + String.format("%.4f", Double.parseDouble(getArguments().getString("buyamt"))));*/

            ltptxt.setText(getResources().getString(R.string.rupee_symbol) + "" + String.format("%.4f", Double.parseDouble(customNetPositionSummary.getLtp())));
            mtmtxt.setText(String.format("%.4f", Double.parseDouble(customNetPositionSummary.getMTM())));
            buyAmttxt.setText(String.format("%.4f", Double.parseDouble(customNetPositionSummary.getBuyAmt())));
            sellAmttxt.setText(String.format("%.4f", Double.parseDouble(customNetPositionSummary.getSellAmt())));
            buyPricetxt.setText(String.format("%.4f", Double.parseDouble(customNetPositionSummary.getBuyAvg())));
            sellPricetxt.setText(String.format("%.4f", Double.parseDouble(customNetPositionSummary.getSellAvg())));
            int nqty = 0;

            int qty = (Integer.parseInt(customNetPositionSummary.getNetQty()) +
                    Integer.parseInt(customNetPositionSummary.getPreNetQty()));

            if (getAssetTypeFromToken(customNetPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                nqty = qty;
            } else {
                int totalQty = Integer.parseInt(customNetPositionSummary.getPreNetQty()) + Integer.parseInt(customNetPositionSummary.getNetQty());
                nqty = totalQty;
            }
            if (nqty == 0) {
                realizedgl.setText(String.format("%.4f", Double.parseDouble(customNetPositionSummary.getMTM())));
            } else {
                unrealizedgltxt.setText(String.format("%.4f", Double.parseDouble(customNetPositionSummary.getMTM())));
            }
            totalgltxt.setText(String.format("%.4f", Double.parseDouble(realizedgl.getText().toString()) + Double.parseDouble(unrealizedgltxt.getText().toString())));

        } else {
            /*createPositionsRow("Net Amount", ":\t" + String.format("%.2f", Double.parseDouble(getArguments().getString("netamt"))));
            createPositionsRow("Net Price", ":\t" + String.format("%.2f", Double.parseDouble(getArguments().getString("netprice"))));
            createPositionsRow("LTP", ":\t" + String.format("%.2f", Double.parseDouble(getArguments().getString("LTP"))));
            createPositionsRow("MTM", ":\t" + String.format("%.2f", Double.parseDouble(getArguments().getString("MTM"))));
            createPositionsRow("Sell Amount", ":\t" + String.format("%.2f", Double.parseDouble(getArguments().getString("sellamt"))));
            createPositionsRow("Buy Amount", ":\t" + String.format("%.2f", Double.parseDouble(getArguments().getString("buyamt"))));*/
            ltptxt.setText(getResources().getString(R.string.rupee_symbol) + "" + String.format("%.2f", Double.parseDouble(customNetPositionSummary.getLtp())));
            mtmtxt.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getMTM())));
            buyAmttxt.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getBuyAmt())));
            sellAmttxt.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getSellAmt())));
            buyPricetxt.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getBuyAvg())));
            sellPricetxt.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getSellAvg())));
            int nqty = 0;
            int qty = (Integer.parseInt(customNetPositionSummary.getNetQty()) +
                    Integer.parseInt(customNetPositionSummary.getPreNetQty()));

            if (getAssetTypeFromToken(customNetPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                nqty = qty;
            } else {

                int totalQty = Integer.parseInt(customNetPositionSummary.getPreNetQty()) + Integer.parseInt(customNetPositionSummary.getNetQty());
                nqty = totalQty;
            }
            if (nqty == 0) {
                realizedgl.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getMTM())));
            } else {
                unrealizedgltxt.setText(String.format("%.2f", Double.parseDouble(customNetPositionSummary.getMTM())));
            }
            totalgltxt.setText(String.format("%.2f", Double.parseDouble(realizedgl.getText().toString()) + Double.parseDouble(unrealizedgltxt.getText().toString())));

        }


        //createPositionsRow("Sell Quantity", ":\t" + getArguments().getString("sellqty"));
        //createPositionsRow("Buy Quantity", ":\t" + getArguments().getString("buyqty"));


        if (getExchange(customNetPositionSummary.getToken()).equalsIgnoreCase("mcx")
                || getExchange(customNetPositionSummary.getToken()).equalsIgnoreCase("ncdex")) {

            buyQtytxt.setText(String.valueOf(Integer.parseInt(customNetPositionSummary.getBuyQty())
                    / Integer.parseInt(customNetPositionSummary.getLotQty())));

            sellQtytxt.setText(String.valueOf(Integer.parseInt(customNetPositionSummary.getSellQty())
                    / Integer.parseInt(customNetPositionSummary.getLotQty())));

        } else if (getAssetTypeFromToken(customNetPositionSummary.getToken()).equalsIgnoreCase("currency")) {

            buyQtytxt.setText(String.valueOf(Integer.parseInt(customNetPositionSummary.getBuyQty())
                    / Integer.parseInt(customNetPositionSummary.getMultiplier())));

            sellQtytxt.setText(String.valueOf(Integer.parseInt(customNetPositionSummary.getSellQty())
                    / Integer.parseInt(customNetPositionSummary.getMultiplier())));

        } else {
            buyQtytxt.setText(customNetPositionSummary.getBuyQty());
            sellQtytxt.setText(customNetPositionSummary.getSellQty());

        }

//For todays quantity
        if (Integer.parseInt(customNetPositionSummary.getNetQty()) == 0) {
//            sqrOffBtn.setEnabled(false);
            prodChangeBtn.setEnabled(false);
        }


//for cummulative quantity
        int netqty = Integer.parseInt(customNetPositionSummary.getNetQty()) + Integer.parseInt(customNetPositionSummary.getPreNetQty());
        if (netqty == 0) {
            sqrOffBtn.setEnabled(false);
            prodChangeBtn.setEnabled(false);

        }


        sym.clear();
        sym.add(customNetPositionSummary.getToken());
        streamingController.sendStreamingRequest(getActivity(), sym, "ltpinfo", null, null, false);

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
    }

    private void createPositionsRow(String key, String value) {
        int color = 0;
        //int color = (changeColor) ? R.color.market_grey_light : R.color.market_grey_dark;

        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            color = (changeColor) ? R.color.floatingBgColor : R.color.backgroundStripColorWhite;
        } else {
            color = (changeColor) ? R.color.market_grey_dark : R.color.market_grey_light;
        }


        TableRow Row = new TableRow(getActivity());
        GreekTextView keyView = new GreekTextView(getActivity());
        keyView.setPadding(5, 12, 5, 12);
        keyView.setText(key);
        keyView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        keyView.setBackgroundColor(getResources().getColor(color));
        keyView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params = (TableRow.LayoutParams) keyView.getLayoutParams();
        params.weight = 1;
        params.bottomMargin = 1;
        keyView.setPadding(10, 10, 10, 10);

        GreekTextView valueView = new GreekTextView(getActivity());
        valueView.setPadding(10, 12, 5, 12);
        valueView.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        valueView.setText(value);

        if (":\tBuy".equalsIgnoreCase(value))
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            } else {
                valueView.setTextColor(getResources().getColor(R.color.buyColor));
            }
        else if (":\tSell".equalsIgnoreCase(value))
            valueView.setTextColor(getResources().getColor(R.color.red_textcolor));

        valueView.setTypeface(Typeface.DEFAULT_BOLD);
        valueView.setGravity(GravityCompat.START);
        if (":\tBuy".equalsIgnoreCase(value))
            if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                valueView.setTextColor(getResources().getColor(R.color.whitetheambuyColor));
            } else {
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

    public void onEventMainThread(ProductChangeResponse productChangeResponse) {
        try {
            GreekDialog.alertDialog(getActivity(), 0, GREEK, "\n Message : " + productChangeResponse.getMessage(), "OK", false, new GreekDialog.DialogListener() {
                @Override
                public void alertDialogAction(GreekDialog.Action action, Object... data) {
                    //refresh();
                    //sendPTRRequest();
                }
            });

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public String getProductType(String type) {
        /*if(type.equalsIgnoreCase("1"))
            return "Intraday";
        else if(type.equalsIgnoreCase("0"))
            return "Delivery";
        else if(type.equalsIgnoreCase("2"))
            return "MTF";
        else if(type.equalsIgnoreCase("5"))
            return "SSEQ";
        else if(type.equalsIgnoreCase("3"))
            return "TNC";
        else if(type.equalsIgnoreCase("4"))
            return "CATALYST";
        return "";*/
        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
            if (AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(type)) {
                return AccountDetails.getAllowedProduct().get(i).getcProductName();
            }
        }
        return "";
    }


    public void onEventMainThread(StreamingResponse streamingResponse) {
        try {
            if (streamingResponse.getStreamingType().equalsIgnoreCase("ltpinfo")) {
                StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                broadcastResponse.fromJSON(streamingResponse.getResponse());
                updateBroadcastData(broadcastResponse);
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void updateBroadcastData(StreamerBroadcastResponse response) {
        try {

            if (customNetPositionSummary.getToken().equals(response.getSymbol())) {

                if (((Integer.valueOf(response.getSymbol()) >= 502000000) && (Integer.valueOf(response.getSymbol()) <= 502999999)) || ((Integer.valueOf(response.getSymbol()) >= 1302000000) && (Integer.valueOf(response.getSymbol()) <= 1302999999))) {
                    ltptxt.setText(getResources().getString(R.string.rupee_symbol) + String.format("%.4f", Double.parseDouble(response.getLast())));
                } else {
                    ltptxt.setText(getResources().getString(R.string.rupee_symbol) + String.format("%.2f", Double.parseDouble(response.getLast())));
                }


                /*int NetQty;
                double NetPrice, todayMTM, mtm, NetAmt;

                NetAmt = Double.parseDouble(customNetPositionSummary.getDayNetAmt());
                NetQty = Integer.parseInt(customNetPositionSummary.getNetQty());

                if (NetQty != 0) {
                    if (((Integer.valueOf(customNetPositionSummary.getSqoffToken()) >= 502000000) && (Integer.valueOf(customNetPositionSummary.getSqoffToken()) <= 502999999)) || ((Integer.valueOf(customNetPositionSummary.getSqoffToken()) >= 1302000000) && (Integer.valueOf(customNetPositionSummary.getSqoffToken()) <= 1302999999))) {
                        String netavg = String.format("%.4f", Double.parseDouble(customNetPositionSummary.getNetAvg()));
                        NetPrice = Double.parseDouble(netavg);
                    } else {
                        String netavg = String.format("%.2f", Double.parseDouble(customNetPositionSummary.getNetAvg()));
                        NetPrice = Double.parseDouble(netavg);
                    }

                } else {
                    NetPrice = 0.0;
                }


                if (NetQty != 0) {
                    double ltpClose = Double.parseDouble(customNetPositionSummary.getLtp()) > 0 ? Double.parseDouble(customNetPositionSummary.getLtp()) : Double.parseDouble(customNetPositionSummary.getClose()) > 0 ? Double.parseDouble(customNetPositionSummary.getClose()) : 0.0;
                    double price = NetPrice;


                    if (price != 0) {
                        if (getExchange(customNetPositionSummary.getToken()).equalsIgnoreCase("MCX") ||
                                getExchange(customNetPositionSummary.getToken()).equalsIgnoreCase("NCDEX")) {

                            todayMTM = (ltpClose - price) * ((NetQty * Double.parseDouble(customNetPositionSummary.getPrice_multiplier()) * Integer.parseInt(customNetPositionSummary.getLotQty())));

                        } else {
                            todayMTM = (ltpClose - price) * ((NetQty * Double.parseDouble(customNetPositionSummary.getMultiplier())));
                        }

                    } else {
                        todayMTM = 0.0;
                    }


                } else {
                    todayMTM = NetAmt * (-1);
                }


                double preAmount = 0.0;
                if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                    preAmount = (Double.parseDouble(customNetPositionSummary.getpAmt()) *  Double.parseDouble(customNetPositionSummary.getPrice_multiplier())) + NetAmt;
                } else {
                    preAmount = Double.parseDouble(customNetPositionSummary.getpAmt()) + NetAmt;
                }

                int PrevNetQty = Integer.parseInt(customNetPositionSummary.getPreNetQty()) + Integer.parseInt(customNetPositionSummary.getNetQty());

                if (PrevNetQty != 0) {

                    double price = 0.0;

                    double ltpClose = Double.parseDouble(customNetPositionSummary.getLtp()) > 0 ?
                            Double.parseDouble(customNetPositionSummary.getLtp()) :
                            Double.parseDouble(customNetPositionSummary.getClose()) > 0 ?
                                    Double.parseDouble(customNetPositionSummary.getClose()) : 0.0;

                    if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                        if (PrevNetQty != 0) {
                            price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(customNetPositionSummary.getPrice_multiplier()))));
                        } else {
                            price = 0.0;
                        }
                    } else if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("currency")) {
                        if (PrevNetQty != 0) {
                            price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(customNetPositionSummary.getMultiplier()))));
                        } else {
                            price = 0.0;
                        }
                    } else {
                        price = Math.abs(preAmount / PrevNetQty);
                    }

                    if (Integer.parseInt(customNetPositionSummary.getPreNetQty()) != 0) {
                        if (price != 0) {
                            if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                                mtm = (ltpClose - price) * (PrevNetQty * Double.parseDouble(customNetPositionSummary.getPrice_multiplier()) * Integer.parseInt(customNetPositionSummary.getLotQty()));
                            } else {
                                mtm = ((ltpClose - price)) * ((PrevNetQty * Double.parseDouble(customNetPositionSummary.getMultiplier())));
                            }
                        } else {
                            mtm = 0.0;
                        }
                    } else {
                        mtm = todayMTM;
                    }
                } else {

                    mtm = preAmount * (-1);
                }

*/


                double mtm = 0.0;
                double NetAmt = Double.parseDouble(customNetPositionSummary.getDayNetAmt());
                double preAmount = 0.0;

                preAmount = Double.parseDouble(customNetPositionSummary.getpAmt()) + NetAmt;

                int PrevNetQty = Integer.parseInt(customNetPositionSummary.getPreNetQty()) + Integer.parseInt(customNetPositionSummary.getNetQty());

                if (PrevNetQty != 0) {

                    double ltpClose = Double.parseDouble(response.getLast()) > 0 ? Double.parseDouble(response.getLast()) : Double.parseDouble(response.getLast()) > 0 ? Double.parseDouble(response.getLast()) : 0.0;
                    double price = 0.0;

                    if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                        if (PrevNetQty != 0) {
                            price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(customNetPositionSummary.getPrice_multiplier())
                                    * Integer.parseInt(customNetPositionSummary.getLotQty()))));
                        } else {
                            price = 0.0;
                        }
                    } else if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("currency")) {
                        if (PrevNetQty != 0) {
                            price = Math.abs((preAmount / (PrevNetQty * Double.parseDouble(customNetPositionSummary.getMultiplier()))));
                        } else {
                            price = 0.0;
                        }
                    } else {
                        price = Math.abs(preAmount / PrevNetQty);
                    }


                    if (price != 0) {
                        if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                            mtm = ((ltpClose - price)) * ((PrevNetQty * Double.parseDouble(customNetPositionSummary.getPrice_multiplier()) * Integer.parseInt(customNetPositionSummary.getLotQty())));
                        } else {

                            mtm = ((ltpClose - price)) * ((PrevNetQty * Double.parseDouble(customNetPositionSummary.getMultiplier())));
                        }

                    } else {
                        mtm = 0.0;
                    }
                } else {

                    mtm = preAmount * (-1);
                }


                if (getAssetType(customNetPositionSummary.getToken()).equalsIgnoreCase("currency")) {
                    mtmtxt.setText(String.format("%.4f", mtm));
                    int nqty = 0;
                    int qty = (Integer.parseInt(customNetPositionSummary.getNetQty()) +
                            Integer.parseInt(customNetPositionSummary.getPreNetQty()));

                    if (getAssetTypeFromToken(customNetPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                        nqty = qty;
                    } else {
                        int totalQty = Integer.parseInt(customNetPositionSummary.getPreNetQty()) + Integer.parseInt(customNetPositionSummary.getNetQty());
                        nqty = totalQty;
                    }
                    if (nqty == 0) {
                        realizedgl.setText(String.format("%.4f", mtm));
                    } else {
                        unrealizedgltxt.setText(String.format("%.4f", mtm));
                    }
                    totalgltxt.setText(String.format("%.4f", (Double.parseDouble(realizedgl.getText().toString()) + Double.parseDouble(unrealizedgltxt.getText().toString()))));

                } else {
                    mtmtxt.setText(String.format("%.2f", mtm));
                    int nqty = 0;

                    int qty = (Integer.parseInt(customNetPositionSummary.getNetQty()) +
                            Integer.parseInt(customNetPositionSummary.getPreNetQty()));

                    if (getAssetTypeFromToken(customNetPositionSummary.getToken()).equalsIgnoreCase("commodity")) {
                        nqty = qty;
                    } else {

                        int totalQty = Integer.parseInt(customNetPositionSummary.getPreNetQty()) + Integer.parseInt(customNetPositionSummary.getNetQty());
                        nqty = totalQty;
                    }
                    if (nqty == 0) {
                        realizedgl.setText(String.format("%.2f", mtm));
                    } else {
                        unrealizedgltxt.setText(String.format("%.2f", mtm));
                    }
                    totalgltxt.setText(String.format("%.2f", (Double.parseDouble(realizedgl.getText().toString()) + Double.parseDouble(unrealizedgltxt.getText().toString()))));
                }

            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
//        if(streamingController != null && sym != null && sym.size() > 0)
//        {
//            streamingController.pauseStreaming(getActivity(),"ltpinfo", sym);
//        }
        super.onPause();
    }

    private String getAssetType(String token) {
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

    public String getprducttypename(String token) {
        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
            if (AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(token)) {
                return AccountDetails.getAllowedProduct().get(i).getcProductName();
            }
        }
        return "";
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }
}