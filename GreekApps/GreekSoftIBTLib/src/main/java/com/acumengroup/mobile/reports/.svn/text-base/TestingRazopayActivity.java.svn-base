package com.acumengroup.mobile.reports;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.model.FundTransfer.FundTransferDetailsRequest;
import com.acumengroup.greekmain.core.model.FundTransfer.RazorpayModelResponse;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.mobile.R;
import com.google.android.gms.wallet.callback.PaymentAuthorizationResult;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.io.Serializable;

public class TestingRazopayActivity extends Activity implements PaymentResultWithDataListener {
    RazorpayModelResponse razorpayModelResponse;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_razopay);

          /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());


        Gson gson = new Gson();
        razorpayModelResponse = gson.fromJson(getIntent().getStringExtra("RazorpayModelResponse"), RazorpayModelResponse.class);

         startPayment();
    }
    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;


                    String imgurl="";
                    try {
                        if (!razorpayModelResponse.getResponse().getData().getArrayData().get(0).getMobileImg().equalsIgnoreCase("")) {

                            String[] img = razorpayModelResponse.getResponse().getData().getArrayData().get(0).getMobileImg().split("/");
                            imgurl = WSHandler.CURRENT_BASE_URL + "/" + img[1] + "/" + img[2] + "/" + img[3];
                            Log.e("path", imgurl);

                        }
                    }catch (Exception e ){
                        Log.e("Fail razorpay load img", imgurl);
                    }
                final Checkout co = new Checkout();
                co.setKeyID(razorpayModelResponse.getResponse().getData().getArrayData().get(0).getKey());
                try {
                    JSONObject options = new JSONObject();
                    options.put("name", razorpayModelResponse.getResponse().getData().getArrayData().get(0).getCompany());
                    options.put("description", "Add Fund");
                    options.put("send_sms_hash",true);
                    options.put("allow_rotation", false);
                    options.put("order_id", razorpayModelResponse.getResponse().getData().getArrayData().get(0).getRespo().getId());//from response of step 3.
                    //You can omit the image option to fetch the image from dashboard
                    options.put("image", imgurl);
                    options.put("currency", razorpayModelResponse.getResponse().getData().getArrayData().get(0).getRespo().getCurrency());
                    options.put("amount", razorpayModelResponse.getResponse().getData().getArrayData().get(0).getRespo().getAmount());
                    options.put("timeout","300");
                    //options.put("note_key", AccountDetails.getClientCode(getApplicationContext()));
                    //options.put("notes", "tesing for note");
                    //options.put("callback_url", razorpayModelResponse.getResponse().getData().getArrayData().get(0).getCallback_url());
                    JSONObject notes = new JSONObject();
                    notes.put("name", AccountDetails.getCLIENTNAME(getApplicationContext()));
                    notes.put("user", AccountDetails.getUsername(getApplicationContext()));

                    JSONObject preFill = new JSONObject();
                    preFill.put("email", razorpayModelResponse.getResponse().getData().getArrayData().get(0).getEmail());
                    preFill.put("contact", razorpayModelResponse.getResponse().getData().getArrayData().get(0).getMobile());
                    preFill.put("name", AccountDetails.getCLIENTNAME(getApplicationContext()));
//                    preFill.put("note_key", AccountDetails.getClientCode(getApplicationContext()));
//                    preFill.put("notes", AccountDetails.getCLIENTNAME(getApplicationContext()));


                    options.put("prefill", preFill);
                    options.put("notes",notes);
                    JSONObject retry = new JSONObject();
                    retry.put("enabled", false);
                    options.put("retry", retry);

                    co.open(activity, options);
                } catch (Exception e) {
                    Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    e.printStackTrace();
                }
    }




    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        try {
            Toast.makeText(this, "Payment Successful: " + paymentData.getPaymentId(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent();
            Gson gson = new Gson();
            String myJson = gson.toJson(paymentData);
            intent.putExtra("MESSAGE",myJson);
            setResult(12,intent);
            finish();//finishing activity
        } catch (Exception e) {
            Log.e("TAG", "Exception in onPaymentSuccess", e);
        }
    }



    @Override
    public void onPaymentError(int i, String s,  PaymentData response) {
        Intent intent=new Intent();
        try {

            if(!s.equals("")){
                Log.e("Payment Error",s.toString());
                JSONObject jsonObject= new JSONObject(s);
                JSONObject jsonObjec=jsonObject.getJSONObject("error");
                JSONObject jsonObject1=jsonObjec.getJSONObject("metadata");
                String paymentiD=jsonObject1.getString("payment_id");
                String orderID=jsonObject1.getString("order_id");
                intent.putExtra("paymentiD",paymentiD);
                intent.putExtra("orderID",orderID);
                setResult(13,intent);
                finish();//finishing activity
            }{
                intent.putExtra("paymentiD","0");
                intent.putExtra("orderID","0");
                setResult(13,intent);
                finish();//finishing activity
            }


        } catch (Exception e) {
            Log.e("TAG", "Exception in onPaymentError", e);
            intent.putExtra("paymentiD","0");
            intent.putExtra("orderID","0");
            setResult(13,intent);
            finish();//f
        }
    }


}