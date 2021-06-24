package com.acumengroup.mobile.reports;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.LabelConfig;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.model.portfoliotrending.AllowedProduct;
import com.acumengroup.greekmain.core.network.ProductChangeRequest;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.menu.MenuGetter;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.acumengroup.greekmain.core.constants.GreekConstants.Delivery_product;
import static com.acumengroup.greekmain.core.constants.GreekConstants.Intraday_product;
import static com.acumengroup.greekmain.core.constants.GreekConstants.MTF_product;
import static com.acumengroup.greekmain.core.constants.GreekConstants.TNC_product;
import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

/**
 * Created by sushant.patil on 3/29/2016.
 */
public class CustomNetpositionDialogFragment extends DialogFragment implements LabelConfig {
    private final ArrayList<String> productTypeList = new ArrayList<>();
    private View customProductChangeView;
    private Button submit_btn, cancel_btn;
    private GreekEditText qtyedtxt;
    private Spinner productTypeSpinner;
    private RelativeLayout productchangelayout;
    Bundle args;
    private GreekTextView productChgId, title;
    OrderStreamingController orderStreamingController = new OrderStreamingController();

    public CustomNetpositionDialogFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        customProductChangeView = inflater.inflate(R.layout.custom_productchg_alert, container, false);
        args = new Bundle();
        args = getArguments();
        getProducttype();
        String exchange = args.getString("exchange");
        String assetType = args.getString("assetType");
        submit_btn = customProductChangeView.findViewById(R.id.submitbtn);

        productTypeList.clear();

        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {

            if ((exchange.equalsIgnoreCase("nse") || exchange.equalsIgnoreCase("bse")) && assetType.equalsIgnoreCase("equity") && (AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase(getProductbytoken(TNC_product)) || AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase(getProductbytoken(MTF_product)))) {
                productTypeList.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
            } else if (AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase(getProductbytoken(Delivery_product)) || AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase(getProductbytoken(Intraday_product))) {
                productTypeList.add(AccountDetails.getAllowedProduct().get(i).getcProductName());
            }

        }

        /*productTypeList.clear();
        productTypeList.add("Intraday");
        productTypeList.add("Delivery");
        if ((exchange.equalsIgnoreCase("nse") || exchange.equalsIgnoreCase("bse")) && assetType.equalsIgnoreCase("equity")) {
            productTypeList.add("SSEQ");
        }*/

        productTypeSpinner = customProductChangeView.findViewById(R.id.producttype);
        ArrayAdapter<String> assetTypeAdapter = new ArrayAdapter<String>(getActivity(), AccountDetails.getRowSpinnerSimple(), productTypeList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                // v.setTypeface(font);
                if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
                    v.setTextColor(getResources().getColor(R.color.black));
                } else {
                    v.setTextColor(getResources().getColor(R.color.white));
                }

                v.setPadding(15, 15, 15, 15);
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                //  v.setTypeface(font);
                v.setTextColor(Color.BLACK);
                v.setPadding(15, 15, 15, 15);
                return v;
            }
        };
        assetTypeAdapter.setDropDownViewResource(R.layout.custom_spinner);
        productTypeSpinner.setAdapter(assetTypeAdapter);
        cancel_btn = customProductChangeView.findViewById(R.id.cancelbtn);
        qtyedtxt = customProductChangeView.findViewById(R.id.qty_edittext);
        productChgId = customProductChangeView.findViewById(R.id.prodchg_id);
        title = customProductChangeView.findViewById(R.id.title_dialog);
        qtyedtxt.setImeOptions(EditorInfo.IME_ACTION_DONE);
        productchangelayout = customProductChangeView.findViewById(R.id.productchange_layout);
        productChgId.setText("Quantity");
        title.setText("Product Change");

        qtyedtxt.setText(args.getString("qty"));
        qtyedtxt.setSelection(qtyedtxt.getText().length());
        qtyedtxt.setHint("Quantity");
        qtyedtxt.setInputType(InputType.TYPE_CLASS_NUMBER);


        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("white")) {
            productchangelayout.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            productChgId.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qtyedtxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            qtyedtxt.setHintTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        }
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!qtyedtxt.getText().toString().equals("")) {

                    if ((Integer.valueOf(qtyedtxt.getText().toString()) == 0) || Math.abs(Integer.valueOf(qtyedtxt.getText().toString())) > Math.abs(Integer.valueOf(args.getString("traded_qty_abs")))) {
                        GreekDialog.alertDialog(getActivity(), 0, GreekBaseActivity.GREEK, "Invalid Quantity", "Ok", false, null);
                    } else {
                        int a = 0;
                        if (args.getString("exchange").equalsIgnoreCase("mcx") || args.getString("exchange").equalsIgnoreCase("ncdex")) {
                            a = Math.abs(Integer.valueOf(qtyedtxt.getText().toString()) * Integer.valueOf(args.getString("lot")));
                        } else {
                            a = Math.abs(Integer.valueOf(qtyedtxt.getText().toString()));
                        }

                        int b = Integer.valueOf(args.getString("lot"));
                        int rem = a % b;
                        String previousProduct = args.getString("product");
//                        String selectedProduct = (productTypeSpinner.getSelectedItem().toString());
                        String selectedProduct = getProduct(productTypeSpinner.getSelectedItem().toString());

                        if (qtyedtxt.getText().toString().length() > 0 && (rem != 0)) {

                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Entered Quantity is not in a multiple of Lot", "OK", false, null);

                        } else if (previousProduct.equalsIgnoreCase(selectedProduct)) {

                            GreekDialog.alertDialog(getActivity(), 0, GREEK, "Product change not allowed in same product", "OK", false, null);

                        } else {

                            ProductChangeRequest productChangeRequest = new ProductChangeRequest();
                            productChangeRequest.setGscid(AccountDetails.getUsername(getActivity()));
                            productChangeRequest.setGtoken(args.getString("gtoken"));
                            if (args.getString("exchange").equalsIgnoreCase("mcx") || args.getString("exchange").equalsIgnoreCase("ncdex")) {
                                int qtylot = Integer.valueOf(qtyedtxt.getText().toString()) * Integer.valueOf(args.getString("lot"));
                                int tradedqtylot = Integer.valueOf(args.getString("traded_qty")) * Integer.valueOf(args.getString("lot"));
                                productChangeRequest.setQty(String.valueOf(qtylot));
                                productChangeRequest.setTraded_qty(String.valueOf(tradedqtylot));
                            } else {
                                productChangeRequest.setQty(qtyedtxt.getText().toString());
                                productChangeRequest.setTraded_qty(args.getString("traded_qty"));
                            }
                            productChangeRequest.setProduct(selectedProduct);
                            productChangeRequest.setiGiveUpStatus(previousProduct);
                            productChangeRequest.setReason(args.getString("from"));
                            productChangeRequest.setGorderid(args.getString("gorderid"));
                            productChangeRequest.setTradeid(args.getString("tradeid"));
                            productChangeRequest.setEorderid(args.getString("eorderid"));
                            productChangeRequest.setSide(args.getString("side"));
                            orderStreamingController.sendProductChangeRequest(getActivity(), productChangeRequest);
                            dismiss();
                        }
                    }

                } else {
                    GreekDialog.alertDialog(getActivity(), 0, GreekBaseActivity.GREEK, "Enter Quantity", "Ok", false, null);
                }

            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qtyedtxt.setText("");
                dismiss();
            }
        });


        return customProductChangeView;

    }

//    public String getProductType(String type) {
//        if (type.equalsIgnoreCase("1"))
//            return "Intraday";
//        else if (type.equalsIgnoreCase("0"))
//            return "Delivery";
//        else if (type.equalsIgnoreCase("2"))
//            return "MTF";
//        else if (type.equalsIgnoreCase("3"))
//            return "SSEQ";
//        return "";
//    }

    public String getProductType(String type) {
      /*  if (type.equalsIgnoreCase("1"))
            return "Intraday";
        else if (type.equalsIgnoreCase("0"))
            return "Delivery";
        else if (type.equalsIgnoreCase("2"))
            return "MTF";
        else if (type.equalsIgnoreCase("5"))
            return "SSEQ";
        else if (type.equalsIgnoreCase("3"))
            return "TNC";
        else if (type.equalsIgnoreCase("4"))
            return "CATALYST";
        return "";*/

        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
            if (AccountDetails.getAllowedProduct().get(i).getiProductToken().equalsIgnoreCase(type)) {
                return AccountDetails.getAllowedProduct().get(i).getcProductName();
            }
        }
        return "";


    }

//    public String getProduct(String type) {
//        if (type.equalsIgnoreCase("Intraday"))
//            return "1";
//        else if (type.equalsIgnoreCase("Delivery"))
//            return "0";
//        else if (type.equalsIgnoreCase("MTF"))
//            return "2";
//        else if (type.equalsIgnoreCase("SSEQ"))
//            return "3";
//        return "";
//    }


    public String getProduct(String type) {
       /* if (type.equalsIgnoreCase("Intraday"))
            return "1";
        else if (type.equalsIgnoreCase("Delivery"))
            return "0";
        else if (type.equalsIgnoreCase("MTF"))
            return "2";
        else if (type.equalsIgnoreCase("SSEQ"))
            return "5";
        else if (type.equalsIgnoreCase("TNC"))
            return "3";
        else if (type.equalsIgnoreCase("CATALYST"))
            return "4";
        return "";*/

        for (int i = 0; i < AccountDetails.getAllowedProduct().size(); i++) {
            if (AccountDetails.getAllowedProduct().get(i).getcProductName().equalsIgnoreCase(type)) {
                return AccountDetails.getAllowedProduct().get(i).getiProductToken();
            }
        }
        return "";
    }

    public void navigateTo(int id, Bundle bundle, final boolean addStack) {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager.isAcceptingText() && getActivity().getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        GreekBaseFragment fragment = MenuGetter.getFragmentById(id);
        if (fragment != null) {
            if (fragment.getArguments() != null) {
                //isSensitive = fragment.getArguments().getBoolean("isSensitive");
            }
        }
        if (fragment != null && bundle != null) {
            fragment.setArguments(bundle);
        }
        addFragment(R.id.activityFrameLayout, fragment, addStack);
    }

    private void addFragment(int containerViewId, Fragment fragment, boolean addStack) {
        ((GreekBaseActivity) getActivity()).addFragment(containerViewId, fragment, addStack);
    }


    HashMap<String, String> product = new HashMap<>();

    private void getProducttype() {
        List<AllowedProduct> statusResponse = AccountDetails.getAllowedProduct();

        for (int i = 0; i < statusResponse.size(); i++) {
            product.put(statusResponse.get(i).getiProductToken(), statusResponse.get(i).getcProductName());
        }

    }

    private String getProductbytoken(String token) {

        return product.get(token);

    }


}
