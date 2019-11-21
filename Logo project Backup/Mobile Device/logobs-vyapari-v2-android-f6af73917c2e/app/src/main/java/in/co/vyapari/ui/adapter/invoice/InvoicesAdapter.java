package in.co.vyapari.ui.adapter.invoice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.google.gson.Gson;
import com.robinhood.ticker.TickerView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


import es.dmoral.toasty.Toasty;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.constant.MobileConstants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.InvoiceService;
import in.co.vyapari.middleware.service.ProductService;
import in.co.vyapari.model.ArApCollection;
import in.co.vyapari.model.CalendarModel;
import in.co.vyapari.model.DefaultAccount;
import in.co.vyapari.model.GlCrossAccount;
import in.co.vyapari.model.Invoice;
import in.co.vyapari.model.Login;
import in.co.vyapari.model.Transaction;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.ArApCollectionDTO;
import in.co.vyapari.model.response.dto.CollectionTypeDTO;
import in.co.vyapari.model.response.dto.InvoiceDTO;
import in.co.vyapari.model.response.summary.InvoiceSum;
import in.co.vyapari.ui.activity.PaymentCollection.CreateCollectionActivity;
import in.co.vyapari.ui.activity.PaymentCollection.CreatePaymentActivity;
import in.co.vyapari.ui.activity.app.MainActivity;
import in.co.vyapari.ui.activity.invoice.CreateInvoiceActivity;
import in.co.vyapari.ui.activity.invoice.InvoiceDetailActivity;
import in.co.vyapari.ui.activity.invoice.InvoicePdfShowActivity;
import in.co.vyapari.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyapari.ui.fragment.PurchasesFragment;
import in.co.vyapari.ui.fragment.SalesFragment;
import in.co.vyapari.ui.generic.MyItemSelectedListener;
import in.co.vyapari.ui.generic.pdf.DownloadFileUrlConnectionImpl;
import in.co.vyapari.ui.generic.pdf.RemotePDFViewPager;
import in.co.vyapari.util.CurrencyUtil;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.DateUtil;
import in.co.vyapari.util.Utils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class InvoicesAdapter extends RecyclerSwipeAdapter<InvoicesAdapter.MyViewHolder> implements DownloadFile.Listener{

    private Context mContext;
    private ArrayList<InvoiceSum> invoiceSumList;
    private String PaymentAmount="";
    SwipeItemRecyclerMangerImpl mItemMange;
    private InvoiceDTO invoiceDTO;
    private String type;
    private int invoiceType;
    String text;
    Spinner otherspinner;
    public String arapNo;
    private static String paymentType;
    List<CollectionTypeDTO> collectioncode;
    private RemotePDFViewPager remotePDFViewPager;
    String destinationPath;
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        File file = new File(destinationPath);
        Uri uri = FileProvider.getUriForFile(mContext, "in.co.vyapari.provider", file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        mContext.startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.invoices_item_top_year_rl)
        RelativeLayout topYearAreaRL;
        @BindView(R.id.invoices_item_top_year_tv)
        TextView topYearTV;
        @BindView(R.id.invoices_item_firm_name)
        public TextView firmName;
        @BindView(R.id.invoices_item_price)
        public TickerView price;
        @BindView(R.id.invoices_item_invoice_date)
        public TextView iDate;
        @BindView(R.id.invoices_item_invoice_status)
        public TextView status;
        @BindView(R.id.invoices_item_bottom_view)
        View bottomView;
        @BindView(R.id.invoices_item_bottom_rl)
        RelativeLayout bottomAreaRL;
        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;

        @BindView(R.id.bottom_wrapper_draft)
        LinearLayout bottom_wrapper_draft;
        @BindView(R.id.bottom_wrapper_approved)
        LinearLayout bottom_wrapper_approved;
        @BindView(R.id.btndelete)
        ImageView btndelete;
        @BindView(R.id.btnshare)
        ImageView btnshare;
        @BindView(R.id.btnprint)
        ImageView btnprint;
        @BindView(R.id.btnedit)
        ImageView btnedit;
        @BindView(R.id.btnpayemnt)
        ImageView btnpayemnt;
        @BindView(R.id.btnprrove)
        ImageView btnaprrove;
        @BindView(R.id.invoices_item_mid)
        RelativeLayout invoices_item_mid;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public InvoicesAdapter(Context context, ArrayList<InvoiceSum> invoiceSumList,String type) {
        this.mContext = context;
        if (invoiceSumList == null) {
            invoiceSumList = new ArrayList<>();
        }
        this.invoiceSumList = invoiceSumList;
        mItemMange=new SwipeItemRecyclerMangerImpl(this);
        this.type=type;
    }

    @Override
    public InvoicesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InvoicesAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoices, parent, false));
    }

    @Override
    public void onBindViewHolder(final InvoicesAdapter.MyViewHolder holder, final int position) {

        if (type.equalsIgnoreCase("Sales")){
            invoiceType=1;
        }else{
            invoiceType=2;
        }


        // Drag From Left
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Drag From Right
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper));
        // inside OnBindViewHolder() method, at the very bottom. put this code

        mItemManger.bindView(holder.swipeLayout, position);

// and then add swipelistener to current swipelayout
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

                mItemManger.closeAllExcept(layout);
            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });


       // mItemManger.bindView(holder.swipeLayout,position);

        InvoiceSum invoiceSum = invoiceSumList.get(position);
        holder.firmName.setText(invoiceSum.getFirmName());
        CharSequence doubleToCurrency = CurrencyUtil.doubleToCurrency(invoiceSum.getTotal(), invoiceSum.getCurrency());
        holder.price.setText(String.valueOf(doubleToCurrency), true);

        CalendarModel invoiceDate = DateUtil.dateToCM(invoiceSum.getInvoiceDate());
        CalendarModel afterInvoiceDate, beforeInvoiceDate;

        try {
            afterInvoiceDate = DateUtil.dateToCM(invoiceSumList.get(position - 1).getInvoiceDate());
        } catch (Exception ignored) {
            afterInvoiceDate = null;
        }

        try {
            beforeInvoiceDate = DateUtil.dateToCM(invoiceSumList.get(position + 1).getInvoiceDate());
        } catch (Exception ignored) {
            beforeInvoiceDate = null;
        }

        String date = invoiceDate.getDay() + " " + invoiceDate.getMonthName();
        holder.iDate.setText(date);


        if (invoiceSum.getInvoiceStatus()==1) {
            text = " " + mContext.getString(R.string.approved);
            holder.bottom_wrapper_draft.setVisibility(View.GONE);
            holder.bottom_wrapper_approved.setVisibility(View.VISIBLE);
            holder.btnpayemnt.setVisibility(View.VISIBLE);
            holder.btnaprrove.setVisibility(View.GONE);
        } else if(invoiceSum.getInvoiceStatus()==0){
            text = " " + mContext.getString(R.string.draft);
            holder.bottom_wrapper_draft.setVisibility(View.VISIBLE);
            holder.bottom_wrapper_approved.setVisibility(View.GONE);
            holder.btnaprrove.setVisibility(View.VISIBLE);
            holder.btnpayemnt.setVisibility(View.GONE);
        }else if (invoiceSum.getInvoiceStatus()==3){
            holder.swipeLayout.setRightSwipeEnabled(false);
            holder.swipeLayout.setLeftSwipeEnabled(false);
            text = " " + mContext.getString(R.string.Canceled);
            holder.bottom_wrapper_draft.setVisibility(View.GONE);
            holder.bottom_wrapper_approved.setVisibility(View.GONE);
        }else if (invoiceSum.getInvoiceStatus()==2){
            holder.swipeLayout.setRightSwipeEnabled(false);
            holder.swipeLayout.setLeftSwipeEnabled(false);
            text = " " + mContext.getString(R.string.Gl_Approved);
            holder.bottom_wrapper_draft.setVisibility(View.GONE);
            holder.bottom_wrapper_approved.setVisibility(View.GONE);
        }else if(invoiceSum.getInvoiceStatus()==-1){
            holder.swipeLayout.setRightSwipeEnabled(false);
            holder.swipeLayout.setLeftSwipeEnabled(false);
            text = " " + mContext.getString(R.string.unknown);
            holder.bottom_wrapper_draft.setVisibility(View.GONE);
            holder.bottom_wrapper_approved.setVisibility(View.GONE);
        }else{
            holder.swipeLayout.setRightSwipeEnabled(false);
            holder.swipeLayout.setLeftSwipeEnabled(false);
            text=" ";
            holder.bottom_wrapper_draft.setVisibility(View.GONE);
            holder.bottom_wrapper_approved.setVisibility(View.GONE);
        }
        holder.status.setText(text);

        if (afterInvoiceDate != null) {
            if (invoiceDate.getYear().equals(afterInvoiceDate.getYear())) {
                holder.topYearAreaRL.setVisibility(View.GONE);
            } else {
                holder.topYearTV.setText(invoiceDate.getYear());
                holder.topYearAreaRL.setVisibility(View.VISIBLE);
            }
        } else {
            holder.topYearTV.setText(invoiceDate.getYear());
            holder.topYearAreaRL.setVisibility(View.VISIBLE);
        }


        if (beforeInvoiceDate != null) {
            if (invoiceDate.getYear().equals(beforeInvoiceDate.getYear())) {
                holder.bottomView.setVisibility(View.VISIBLE);
                holder.bottomAreaRL.setVisibility(View.GONE);
            } else {
                holder.bottomView.setVisibility(View.GONE);
                holder.bottomAreaRL.setVisibility(View.VISIBLE);
            }
        } else {
            holder.bottomView.setVisibility(View.GONE);
            holder.bottomAreaRL.setVisibility(View.VISIBLE);
        }


        //Swipe

        holder.btnpayemnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionPayment(position,holder);

                //getSaleInvoiceDetailCall(invoiceSumList.get(position).getId(),position,holder);

            }
        });
        holder.btnaprrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Approve(position,holder);
            }
        });
        holder.btnprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print(position,holder);
            }
        });
        holder.btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Share(position,holder);
            }
        });
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // notifyDataSetChanged();
                //changeColor(position, true,holder);
                holder.swipeLayout.close();
                DataUtil.post(invoiceSumList.get(position).isActive());
                getEditSaleDetailCall(invoiceSumList.get(position).getId(), position,holder);
            }
        });
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.swipeLayout.close();
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Delete Invoice");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        deleteInvoiceCall(position,holder);
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();

            }
        });
        holder.invoices_item_mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //changeColor(position, true,holder);
                DataUtil.post(invoiceSumList.get(position).isActive());
                if(invoiceType==1) {

                    getSaleDetailCall(invoiceSumList.get(position).getId(), position, holder);
                }else {
                    getPurchaseDetailCall(invoiceSumList.get(position).getId(), position);
                }
            }
        });

    }

    private void getPurchaseDetailCall(String id, final int position) {
        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetail(id, Constants.PURCHASE_INVOICE, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {

                Utils.hideLoading();
                if (!response.isError()) {
                    if (response.getData() != null) {
                        InvoiceDTO invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);
                        mContext.startActivity(new Intent(mContext, InvoiceDetailActivity.class));
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

                Utils.hideLoading();

            }
        });
    }
//    private void createInvoice(final int position, final MyViewHolder holder) {
//
//
//        LayoutInflater layout = (LayoutInflater) mContext
//                .getSystemService(LAYOUT_INFLATER_SERVICE);
//        View dialogView = layout.inflate(R.layout.collection_popup, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setView(dialogView);
//        final AlertDialog alertDialog = builder.create();
//        final LinearLayout collection_option = (LinearLayout) dialogView.findViewById(R.id.collection_option);
//        final LinearLayout approveornot = (LinearLayout) dialogView.findViewById(R.id.approveornot);
//        final LinearLayout otherlayout=(LinearLayout)dialogView.findViewById(R.id.otherlayout);
//        final LinearLayout amountlayout=(LinearLayout)dialogView.findViewById(R.id.amountlayout);
//        final EditText amount = (EditText) dialogView.findViewById(R.id.amount);
//        final TextView debit_credit=(TextView)dialogView.findViewById(R.id.debit_credit);
//        final TextView bank=(TextView)dialogView.findViewById(R.id.bank);
//        final TextView cash=(TextView)dialogView.findViewById(R.id.cash);
//        final TextView other=(TextView)dialogView.findViewById(R.id.other);
//        final ImageView close=(ImageView)dialogView.findViewById(R.id.closes);
//        final TextView otheramount = (TextView) dialogView.findViewById(R.id.otheramount);
//        otherspinner=(Spinner)dialogView.findViewById(R.id.other_spinner);
//        final Button othercancel = (Button) dialogView.findViewById(R.id.othercancel);
//        final Button otherok = (Button) dialogView.findViewById(R.id.otherok);
//        final Button skip = (Button) dialogView.findViewById(R.id.skip);
//        amount.setText("" + invoiceDTO.getTotalAmount());
//        PaymentAmount=amount.getText().toString();
//        debit_credit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PaymentAmount=amount.getText().toString();
//                alertDialog.dismiss();
//                Login login= (Login) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_INFO, Login.class);
//                DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
//                if(defaultAccount!=null) {
//                    if (defaultAccount.getUsername().equalsIgnoreCase(login.getUserName())&&defaultAccount.getUsername()!=null&& defaultAccount.getBankid()!=null) {
//                        alertDialog.dismiss();
//                        paymentType = "Card";
//                        getSlipno(position,holder);
//
//                    }else{
//                        Toasty.error(mContext,mContext.getString(R.string.UpdateDefaultAccount)).show();
//                    }
//                }else{
//                    Toasty.error(mContext, mContext.getString(R.string.UpdateDefaultAccount)).show();
//                }
//
//            }
//        });
//        bank.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PaymentAmount=amount.getText().toString();
//
//                Login login= (Login) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_INFO, Login.class);
//                DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
//                if(defaultAccount!=null) {
//                    if (defaultAccount.getUsername().equalsIgnoreCase(login.getUserName())&&defaultAccount.getUsername()!=null&& defaultAccount.getBankid()!=null) {
//                        alertDialog.dismiss();
//                        paymentType = "Bank";
//                        getSlipno(position, holder);
//
//                    }else{
//                        Toasty.error(mContext,mContext.getString(R.string.UpdateDefaultAccount)).show();
//                    }
//                }else{
//                    Toasty.error(mContext, mContext.getString(R.string.UpdateDefaultAccount)).show();
//                }
//            }
//        });
//        cash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PaymentAmount=amount.getText().toString();
//                Login login= (Login) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_INFO, Login.class);
//                DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
//                if(defaultAccount!=null) {
//                    if (defaultAccount.getUsername().equalsIgnoreCase(login.getUserName())&&defaultAccount.getUsername()!=null&& defaultAccount.getBankid()!=null) {
//                        alertDialog.dismiss();
//                        paymentType = "Cash";
//                        getSlipno(position, holder);
//                    }else{
//                        Toasty.error(mContext,mContext.getString(R.string.UpdateDefaultAccount)).show();
//                    }
//                }else{
//                    Toasty.error(mContext, mContext.getString(R.string.UpdateDefaultAccount)).show();
//                }
//            }
//        });
//        otherspinner.setOnItemSelectedListener(new MyItemSelectedListener() {
//            @Override
//            public void onItemSelected(int position) {
//                paymentType="other";
//                DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
//
//                DefaultAccount defaultAccountnew = new DefaultAccount();
//                defaultAccountnew.setCashcode(defaultAccount.getCashcode());
//                defaultAccountnew.setBankcode(defaultAccount.getBankcode());
//                defaultAccountnew.setCardcode(defaultAccount.getCardcode());
//                defaultAccountnew.setUsername(defaultAccount.getUsername());
//                defaultAccountnew.setCashid(defaultAccount.getCashid());
//                defaultAccountnew.setBankid(defaultAccount.getBankid());
//                defaultAccountnew.setCardid(defaultAccount.getCardid());
//                defaultAccountnew.setOthercode(collectioncode.get(position).getCode());
//                Utils.setObjectSharedPreferencesValue(mContext, defaultAccountnew, Constants.DEFAULT_ACCOUNT_INFO);
//
//
//            }
//        });
//        other.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PaymentAmount=amount.getText().toString();
//                Login login= (Login) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_INFO, Login.class);
//                DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
//                if(defaultAccount!=null) {
//                    if (defaultAccount.getUsername().equalsIgnoreCase(login.getUserName())&&defaultAccount.getUsername()!=null&& defaultAccount.getBankid()!=null) {
//
//
//                        collection_option.setVisibility(View.GONE);
//                        approveornot.setVisibility(View.GONE);
//                        otherlayout.setVisibility(View.VISIBLE);
//                        amountlayout.setVisibility(View.GONE);
//                        otheramount.setText("" + PaymentAmount);
//
//                        setCollectionType();
//                    }else{
//                        Toasty.error(mContext,mContext.getString(R.string.UpdateDefaultAccount)).show();
//                    }
//                }else{
//                    Toasty.error(mContext, mContext.getString(R.string.UpdateDefaultAccount)).show();
//                }
////                getSlipno();
////                collection_option.setVisibility(View.GONE);
////                approveornot.setVisibility(View.VISIBLE);
////                tryagain.setVisibility(View.VISIBLE);
////                approvenot.setVisibility(View.VISIBLE);
////                approvenot.setText("Failed");
////                approveornotimg.setImageResource(R.drawable.fail);
//
//            }
//        });
//        otherok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PaymentAmount=amount.getText().toString();
//                Login login= (Login) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_INFO, Login.class);
//                DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
//                if(defaultAccount!=null) {
//                    if (defaultAccount.getUsername().equalsIgnoreCase(login.getUserName())&&defaultAccount.getUsername()!=null&& defaultAccount.getBankid()!=null) {
//                        paymentType = "Other";
//                        alertDialog.dismiss();
//                        getSlipno(position, holder);
//                    }else{
//                        Toasty.error(mContext,mContext.getString(R.string.UpdateDefaultAccount)).show();
//                    }
//                }else{
//                    Toasty.error(mContext, mContext.getString(R.string.UpdateDefaultAccount)).show();
//                }
//            }
//        });
//        othercancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
//
//
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                alertDialog.dismiss();
//
//            }
//        });
//        skip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                alertDialog.dismiss();
//
//
//            }
//        });
//
//
//        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                // Prevent dialog close on back press button
//                return keyCode == KeyEvent.KEYCODE_BACK;
//            }
//        });
//        alertDialog.show();
//
//    }
    private void setCollectionType() {
        Utils.showLoading(mContext);
        ProductService.getCollectionType(new ServiceCall<BaseModel<List<CollectionTypeDTO>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<List<CollectionTypeDTO>> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    List<CollectionTypeDTO> data = response.getData();
                    if (data == null) {
                        data = new ArrayList<>();
                    }
                    collectioncode = data;

                    otherspinner.setAdapter(new OneLineSpinnerAdapter(mContext, collectioncode, Color.parseColor("#000000")));
                    otherspinner.setSelection(0);



                } else {
                    Utils.hideLoading();
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, mContext.getString(R.string.error)).show();
            }
        });
    }
//    private void getSlipno(final int position, final MyViewHolder holder) {
//
//
//        Utils.showLoading(mContext);
//        ProductService.getArpSlipNumber(new ServiceCall<BaseModel<String>>() {
//            @Override
//            public void onResponse(boolean isOnline, BaseModel<String> response) {
//                Utils.hideLoading();
//                if (!response.isError()) {
//                    arapNo = response.getData();
//                    Log.e("Arapno", arapNo);
//
//                    CreateArAp(position,holder);
//                } else {
//                    Utils.hideLoading();
//                    Toasty.error(mContext, response.getErrorDescription()).show();
//                }
//            }
//
//            @Override
//            public void onFailure(boolean isOnline, Throwable throwable) {
//                Utils.hideLoading();
//                Toasty.error(mContext, mContext.getString(R.string.error)).show();
//            }
//        });
//
//
//    }
//    private void CreateArAp(int position, MyViewHolder holder) {
//        ArApCollection arap = new ArApCollection();
//        Transaction transaction = new Transaction();
//        GlCrossAccount glCrossAccount = new GlCrossAccount();
//        arap.setSlipNo(arapNo);
//        Date dates = new Date(new Date().getTime());
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");
//        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
//        arap.setSlipDate(sdf.format(dates.getTime()));
//
//        arap.setReceiptNo("");
//
//        if (invoiceDTO.getDescription().equalsIgnoreCase("")) {
//            arap.setDescription("");
//        } else {
//            arap.setDescription(invoiceDTO.getDescription());
//        }
//        glCrossAccount.setKey("0");
//        DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
//
//        if (paymentType.equalsIgnoreCase("Bank")) {
//            glCrossAccount.setValue(defaultAccount.getBankcode());
//        } else if (paymentType.equalsIgnoreCase("Cash")) {
//            glCrossAccount.setValue(defaultAccount.getCashcode());
//        } else if (paymentType.equalsIgnoreCase("Card")) {
//            glCrossAccount.setValue(defaultAccount.getCardcode());
//        } else{
//            glCrossAccount.setValue(defaultAccount.getOthercode());
//        }
//        transaction.setAmount(Double.parseDouble(PaymentAmount));
//        if (invoiceDTO.getDescription().
//
//                equalsIgnoreCase("")) {
//            transaction.setDescription("");
//        } else {
//            transaction.setDescription(invoiceDTO.getDescription());
//        }
//
//
//        transaction.setArpCode(invoiceDTO.getFirm().getCode());
//
//
//        transaction.setGlCrossAccount(glCrossAccount);
//        arap.setTransactions(Collections.singletonList(transaction));
//
//        Gson gson = new Gson();
//        String jsn = gson.toJson(arap);
//        Log.e("JSON OBJECT COLLEction-", jsn);
//        if (invoiceType==1) {
//            saveCollectionARAP(1, arap,position,holder);
//        } else {
//            saveCollectionARAP(2, arap, position, holder);
//        }
//
//    }
//    private void saveCollectionARAP(int i, ArApCollection ArapCollection, final int position, final MyViewHolder holder) {
//        Utils.showLoading(mContext);
//        ProductService.saveCollection(i, ArapCollection, new ServiceCall<BaseModel<ArApCollectionDTO>>() {
//            @Override
//            public void onResponse(boolean isOnline, BaseModel<ArApCollectionDTO> response) {
//                Utils.hideLoading();
//                if (!response.isError()) {
//                    String message = response.getMessage();
//                    Toasty.success(mContext, message).show();
//
//
//
//                } else {
//                    Toasty.error(mContext, response.getErrorDescription()).show();
//                }
//            }
//
//            @Override
//            public void onFailure(boolean isOnline, Throwable throwable) {
//                Utils.hideLoading();
//                Toasty.error(mContext, mContext.getString(R.string.error)).show();
//            }
//        });
//    }

    private void getEditSaleDetailCall(String id, final int position,final MyViewHolder holder) {
        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetailEdit(id, Constants.SALES_INVOICE, new ServiceCall<BaseModel<Invoice>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<Invoice> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        Invoice invoice = response.getData();
                        DataUtil.post(invoice);
                        mContext.startActivity(new Intent(mContext, CreateInvoiceActivity.class));

                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
            }
        });

    }


    private void changeColor(int position, boolean isClick, MyViewHolder holder) {
        int color, color2, color3;
        if (isClick) {
            color = ContextCompat.getColor(mContext, R.color.fab_color);
            color2 = ContextCompat.getColor(mContext, R.color.fab_color);
            color3 = ContextCompat.getColor(mContext, R.color.fab_color);
        } else {
            color = ContextCompat.getColor(mContext, R.color.black);
            color2 = ContextCompat.getColor(mContext, R.color.price_color);
            color3 = ContextCompat.getColor(mContext, R.color.date_color);
        }



        holder.firmName.setTextColor(color);
        holder.price.setTextColor(color2);
        holder.iDate.setTextColor(color3);
        holder.status.setTextColor(color3);
    }
    private void getSaleDetailCall(String id, final int position, final MyViewHolder holder) {

        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetail(id, Constants.SALES_INVOICE, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                          invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);
                        mContext.startActivity(new Intent(mContext, InvoiceDetailActivity.class));

                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
            }
        });
    }


//    private void getSaleInvoiceDetailCall(String id, final int position, final MyViewHolder holder) {
//        holder.swipeLayout.close();
//        changeColor(position, false, holder);
//        Utils.showLoading(mContext);
//
//        InvoiceService.getInvoiceDetail(id, Constants.SALES_INVOICE, new ServiceCall<BaseModel<InvoiceDTO>>() {
//            @Override
//            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
//                Utils.hideLoading();
//
//                if (!response.isError()) {
//                    if (response.getData() != null) {
//
//                          invoiceDTO = response.getData();
//                        DataUtil.post(invoiceDTO);
//
//                        createInvoice(position,holder);
//
//                    }
//                } else {
//                    Toasty.error(mContext, response.getErrorDescription()).show();
//                }
//            }
//
//            @Override
//            public void onFailure(boolean isOnline, Throwable throwable) {
//                changeColor(position, false, holder);
//                Utils.hideLoading();
//                notifyDataSetChanged();
//            }
//        });
//    }
    private void getPurchasepaymentCall(String id, final int position, final MyViewHolder holder) {
        Toasty.success(mContext, "Payments").show();
        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetail(id, Constants.PURCHASE_INVOICE, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                changeColor(position, false,holder);
                Utils.hideLoading();
                if (!response.isError()) {
                    if (response.getData() != null) {
                        InvoiceDTO invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);
                        Intent i = new Intent(mContext, CreatePaymentActivity.class);
                        i.putExtra("Create","false");
                        mContext.startActivity(i);
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                changeColor(position, false,holder);
                Utils.hideLoading();

            }
        });
    }
    private void getPaymentcollectionCall(String id, final int position, final MyViewHolder holder) {

            Toasty.success(mContext, "Collections").show();

        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetail(id, Constants.SALES_INVOICE, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        InvoiceDTO paymentinvoiceDTO = response.getData();
                        DataUtil.post(paymentinvoiceDTO);

                            Intent i = new Intent(mContext, CreateCollectionActivity.class);
                            i.putExtra("Create","false");
                            mContext.startActivity(i);

                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
               // changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
            }
        });
    }
    private void deleteInvoiceCall(final int position,MyViewHolder holder) {
        holder.swipeLayout.close();
        //changeColor(position,true,holder);

        Toasty.success(mContext, "Delete").show();
        Utils.showLoading(mContext);

        InvoiceService.deleteInvoice(invoiceSumList.get(position).getId(),invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    if (type.equalsIgnoreCase("Sales")){
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                        SalesFragment salesFragment = new SalesFragment();
                        fragmentTransaction.replace(R.id.frame, salesFragment);
                        fragmentTransaction.commit();
                    }else{
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesFragment salesFragment = new PurchasesFragment();
                        fragmentTransaction.replace(R.id.frame, salesFragment);
                        fragmentTransaction.commit();
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
            }
        });
    }
    private void Share(int position,MyViewHolder holder) {
        Toasty.success(mContext, "Share").show();
        holder.swipeLayout.close();

            Utils.showLoading(mContext);
            InvoiceService.getPrintUrl(invoiceSumList.get(position).getId(), new ServiceCall<BaseModel<String>>() {
                @Override
                public void onResponse(boolean isOnline, BaseModel<String> response) {
                    if (!response.isError()) {
                        String url = response.getData();
                        if (url != null) {


                            remotePDFViewPager = new RemotePDFViewPager(mContext, url, InvoicesAdapter.this);
                            Utils.hideLoading();

                        } else {
                            Toasty.error(mContext, response.getErrorDescription()).show();
                        }
                    } else {
                        Utils.hideLoading();
                        Toasty.error(mContext, response.getErrorDescription()).show();
                    }
                }

                @Override
                public void onFailure(boolean isOnline, Throwable throwable) {
                    Utils.hideLoading();
                    //Toasty.error(mContext, getString(R.string.error)).show();
                }
            });

    }
    private void printOption(int position,MyViewHolder holder){
        Utils.showLoading(mContext);
        InvoiceService.getPrintUrl(invoiceSumList.get(position).getId(), new ServiceCall<BaseModel<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<String> response) {
                if (!response.isError()) {
                    String url = response.getData();
                    if (url != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Utils.hideLoading();
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(mContext, InvoicePdfShowActivity.class);
                            bundle.putString(Constants.URL, url);
                            bundle.putInt(Constants.INVOICE_TYPE, invoiceType);
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        } else {
                            downloadPdf(url);
                        }
                    } else {
                        Toasty.error(mContext, response.getErrorDescription()).show();
                    }
                } else {
                    Utils.hideLoading();
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                //Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }
    private void downloadPdf(String url) {
        String guid = UUID.randomUUID().toString();
        DownloadFile downloadFile = new DownloadFileUrlConnectionImpl(mContext, new Handler(), new DownloadFile.Listener() {
            @Override
            public void onSuccess(String url, String destinationPath) {
                Utils.hideLoading();
                launchOpenPDFIntent(destinationPath);
            }

            @Override
            public void onFailure(Exception e) {
                Utils.hideLoading();

            }

            @Override
            public void onProgressUpdate(int progress, int total) {

            }
        });
        downloadFile.download(url, new File(mContext.getExternalFilesDir("pdf"), "invoice-" + guid + ".pdf").getAbsolutePath());
    }
    private void launchOpenPDFIntent(String destinationPath) {
        File pdfFile = new File(destinationPath);
        Uri uri = FileProvider.getUriForFile(mContext, "in.co.vyapari.provider", pdfFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mContext.startActivity(intent);

    }
    private void print(int position,MyViewHolder holder) {
        holder.swipeLayout.close();
        //changeColor(position,true,holder);

        Toasty.success(mContext, "Print").show();

        printOption(position,holder);
    }
    private void Approve(int position,MyViewHolder holder) {
        //notifyDataSetChanged();
        holder.swipeLayout.close();
        //changeColor(position,true,holder);

        Toasty.success(mContext, "Approve").show();
        changeStatusCall(position,holder);

    }

    private void CollectionPayment(int position, MyViewHolder holder) {
       // notifyDataSetChanged();
        holder.swipeLayout.close();
       // changeColor(position,true,holder);
        if (type.equalsIgnoreCase("Purchase")) {
            getPurchasepaymentCall(invoiceSumList.get(position).getId(), position, holder);
        }else {
            getPaymentcollectionCall(invoiceSumList.get(position).getId(), position, holder);
        }
    }

    private void changeStatusCall(final int position, final MyViewHolder holder) {
        InvoiceService.changeStatus(invoiceSumList.get(position).getId(), invoiceType, new ServiceCall<BaseModel<Boolean>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<Boolean> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                   // MobileConstants.invoiceId = "changeStatus";
                    if (type.equalsIgnoreCase("Sales")){
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                        SalesFragment salesFragment = new SalesFragment();
                        fragmentTransaction.replace(R.id.frame, salesFragment);
                        fragmentTransaction.commit();
                    }else{
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesFragment salesFragment = new PurchasesFragment();
                        fragmentTransaction.replace(R.id.frame, salesFragment);
                        fragmentTransaction.commit();
                    }

                } else {
                    Toasty.error(mContext, response.getMessage()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, ""+R.string.error).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return invoiceSumList.size();
    }

}