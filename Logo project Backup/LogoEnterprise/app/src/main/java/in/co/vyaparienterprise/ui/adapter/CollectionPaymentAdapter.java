package in.co.vyaparienterprise.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.robinhood.ticker.TickerView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;


import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.constant.MobileConstants;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.InvoiceService;
import in.co.vyaparienterprise.model.CalendarModel;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.dto.ArApCollectionDTO;
import in.co.vyaparienterprise.model.response.dto.InvoiceDTO;
import in.co.vyaparienterprise.model.response.summary.CollectionSum;
import in.co.vyaparienterprise.ui.activity.PaymentCollection.CollectionDetailActivity;
import in.co.vyaparienterprise.ui.activity.PaymentCollection.CreateCollectionActivity;
import in.co.vyaparienterprise.util.DataUtil;
import in.co.vyaparienterprise.util.DateUtil;
import in.co.vyaparienterprise.util.Utils;

import static android.view.View.GONE;
import static in.co.vyaparienterprise.util.CurrencyUtil.indianCurrency;


/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class CollectionPaymentAdapter extends RecyclerView.Adapter<CollectionPaymentAdapter.MyViewHolder>  {

    private final int type;
    private Context mContext;
    private List<CollectionSum> invoiceSumList;
    ArrayList<CollectionSum> arraylist ;
    private InvoiceDTO invoice;
     ;




//    @Override
//    public int getSwipeLayoutResourceId(int position) {
//        return R.id.swipe;
//    }

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
        @BindView(R.id.collectionItem)
        LinearLayout arap_item_mids;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public CollectionPaymentAdapter(Context context, List<CollectionSum> invoiceSumList, int types) {
        this.mContext = context;
        if (invoiceSumList == null) {
            invoiceSumList = new ArrayList<>();
        }
        this.invoiceSumList = invoiceSumList;
        this.arraylist= (ArrayList<CollectionSum>) invoiceSumList;
       // mItemMange=new SwipeItemRecyclerMangerImpl(this);
        this.type=types;
    }

    @Override
    public CollectionPaymentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectionPaymentAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_invoices, parent, false));
    }

    @Override
    public void onBindViewHolder(final CollectionPaymentAdapter.MyViewHolder holder, final int position) {



//        invoice = DataUtil.getBundleAndRemove(InvoiceDTO.class);
//        // Drag From Left
//        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));
//
//        // Drag From Right
//        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper));
//        // inside OnBindViewHolder() method, at the very bottom. put this code
//
//        mItemManger.bindView(holder.swipeLayout, position);
//
//// and then add swipelistener to current swipelayout
//        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
//            @Override
//            public void onStartOpen(SwipeLayout layout) {
//
//                mItemManger.closeAllExcept(layout);
//            }
//
//            @Override
//            public void onOpen(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onStartClose(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onClose(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
//
//            }
//
//            @Override
//            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
//
//            }
//        });


        // mItemManger.bindView(holder.swipeLayout,position);




            holder.firmName.setText(invoiceSumList.get(position).getARPName());

            holder.price.setText("₹" +  indianCurrency(invoiceSumList.get(position).getTotal()));

            SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            formatter6.setTimeZone(TimeZone.getTimeZone("IST"));
            Date date5 = null;
            try {
                  date5 = formatter6.parse(invoiceSumList.get(position).getSlipDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date befordate=null;
            Date afterdate=null;

            CalendarModel invoiceDate = DateUtil.dateToCM(date5);
            CalendarModel afterInvoiceDate, beforeInvoiceDate;

            try {
                afterdate=formatter6.parse(invoiceSumList.get(position-1).getSlipDate());
                afterInvoiceDate = DateUtil.dateToCM(afterdate);
            } catch (Exception ignored) {
                afterInvoiceDate = null;
            }

            try {
                befordate=formatter6.parse(invoiceSumList.get(position+1).getSlipDate());
                beforeInvoiceDate = DateUtil.dateToCM(befordate);
            } catch (Exception ignored) {
                beforeInvoiceDate = null;
            }

            String date = invoiceDate.getDay() + " " + invoiceDate.getMonthName();
            holder.iDate.setText(date);

            String text;
            if (invoiceSumList.get(position).getStatus() == 1) {
                text = " " + mContext.getString(R.string.approved);

            } else {
                text = " " + mContext.getString(R.string.draft);
            }
            holder.status.setText(text);

            if (afterInvoiceDate != null) {
                if (invoiceDate.getYear().equals(afterInvoiceDate.getYear())) {
                    holder.topYearAreaRL.setVisibility(GONE);
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
                    holder.bottomAreaRL.setVisibility(GONE);
                } else {
                    holder.bottomView.setVisibility(GONE);
                    holder.bottomAreaRL.setVisibility(View.VISIBLE);
                }
            } else {
                holder.bottomView.setVisibility(GONE);
                holder.bottomAreaRL.setVisibility(View.VISIBLE);
            }
        holder.arap_item_mids.setFocusable(false);
        holder.arap_item_mids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //notifyDataSetChanged();
                changeColor(position, true,holder);
                //DataUtil.post(invoiceSumList.get(position).isActive());
                getARAPDetailCall(invoiceSumList.get(position).getHeaderReference(), position,holder);
            }
        });

            //Swipe

//            holder.btnpayemnt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Payment(position, holder);
//                }
//            });
        }
//        holder.btnaprrove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Approve();
//            }
//        });
//        holder.btnprint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                print();
//            }
//        });
//        holder.btnshare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Share();
//            }
//        });
//        holder.btnedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                notifyDataSetChanged();
//                changeColor(position, true,holder);
//                DataUtil.post(invoiceSumList.get(position).isActive());
//                getSaleDetailCall(invoiceSumList.get(position).getId(), position,holder);
//            }
//        });
//        holder.btndelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deleteInvoiceCall();
//            }
//        });



    public static String format2DecAmountDouble(Double amount) {
        String formattedAmount = String.format("%.2f", amount);
        return formattedAmount;
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
    private void getARAPDetailCall(final String id, final int position, final MyViewHolder holder) {
        Log.e("id",id);

        Utils.showLoading(mContext);
        InvoiceService.getARAPDetail(id, new ServiceCall<BaseModel<ArApCollectionDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArApCollectionDTO> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        ArApCollectionDTO ArApCollectionDTO = response.getData();
                        DataUtil.post(ArApCollectionDTO);
                        DataUtil.post(invoiceSumList);
                        if (type==2) {

                            Intent i = new Intent(mContext, CollectionDetailActivity.class);
                            i.putExtra("Type","2");
                            i.putExtra("Create","arapdetail");
                            i.putExtra("id",id);
                            i.putExtra("collectionSum",   arraylist);
                            mContext.startActivity(i);
                        }else{
                            Intent i = new Intent(mContext, CollectionDetailActivity.class);
                            i.putExtra("Type","3");
                            i.putExtra("id",id);
                            i.putExtra("Create","arapdetail");
                            i.putExtra("collectionSum",   arraylist);
                            mContext.startActivity(i);
                        }
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
    private void getPaymentDetailCall(String id, final int position, final MyViewHolder holder) {

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
                        Intent i = new Intent(mContext,CreateCollectionActivity.class);
                        i.putExtra("PaymentType","Payment");
                        mContext.startActivity(i);

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
    private void deleteInvoiceCall() {
        Toast.makeText(mContext,"Delete",Toast.LENGTH_SHORT).show();
//        Utils.showLoading(mContext);
//
//        InvoiceService.deleteInvoice(invoiceSumList.get(positionid).getId(), invoiceSumList.get(positionid).getInvoiceType(), new ServiceCall<BaseModel<InvoiceDTO>>() {
//            @Override
//            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
//                Utils.hideLoading();
//                if (!response.isError()) {
//                    String message = response.getMessage();
//                    Toasty.success(mContext, message).show();
//                    notifyDataSetChanged();
//                } else {
//                    Toasty.error(mContext, response.getErrorDescription()).show();
//                }
//            }
//
//            @Override
//            public void onFailure(boolean isOnline, Throwable throwable) {
//                Utils.hideLoading();
//            }
//        });
    }
    private void Share() {
        try {
            notifyDataSetChanged();
            Toast.makeText(mContext,"Share",Toast.LENGTH_LONG).show();
            String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/share.jpeg";
            File file = new File(String.valueOf(filepath));
            Uri uri = FileProvider.getUriForFile(mContext, "in.co.vyapari.provider", file);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            mContext.startActivity(Intent.createChooser(intent, "Share Image"));
        }catch (Exception e){

        }
    }
    private void print() {
        notifyDataSetChanged();
        Toast.makeText(mContext,"Print",Toast.LENGTH_LONG).show();
    }
    private void Approve() {
        notifyDataSetChanged();
        Toast.makeText(mContext,"Approve",Toast.LENGTH_LONG).show();
        //  changeStatusCall();
    }
    private void Payment(int position, MyViewHolder holder) {
        notifyDataSetChanged();
        Toast.makeText(mContext,"Payment",Toast.LENGTH_LONG).show();
        // getPaymentDetailCall(invoiceSumList.get(position).getId(), position,holder);

    }


    @Override
    public int getItemCount() {
        return invoiceSumList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return invoiceSumList.size();
    }
}
