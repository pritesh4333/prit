package in.co.vyaparienterprise.ui.adapter.invoice;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.robinhood.ticker.TickerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


import es.dmoral.toasty.Toasty;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.InvoiceService;
import in.co.vyaparienterprise.middleware.service.ProductService;
import in.co.vyaparienterprise.model.CalendarModel;
import in.co.vyaparienterprise.model.Firm;
import in.co.vyaparienterprise.model.Invoice;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.dto.CollectionTypeDTO;
import in.co.vyaparienterprise.model.response.dto.InvoiceDTO;
import in.co.vyaparienterprise.model.response.summary.InvoiceSum;
import in.co.vyaparienterprise.ui.activity.PaymentCollection.CreateCollectionActivity;
import in.co.vyaparienterprise.ui.activity.PaymentCollection.CreatePaymentActivity;
import in.co.vyaparienterprise.ui.activity.app.MainActivity;
import in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder.CreatePurchaseInvoiceActivity;
import in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder.CreatePurchaseOrderActivity;
import in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder.CreatePurchaseReceiptActivity;
import in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder.CreatePurchaseReturnActivity;
import in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder.PurchaseInvoiceDetailActivity;
import in.co.vyaparienterprise.ui.activity.invoice.InvoicePdfShowActivity;
import in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder.PurchaseOrderDetailActivity;
import in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder.PurchaseReceiptDetailActivity;
import in.co.vyaparienterprise.ui.activity.invoice.PurchaseOrder.PurchaseReturnDetailActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SalesOrder.CreateSalesDispatchActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SalesOrder.CreateSalesInvoiceActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SalesOrder.CreateSalesOrderActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SalesOrder.CreateSalesReturnActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SalesOrder.SalesDispatchDetailActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SalesOrder.SalesInvoiceDetailActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SalesOrder.SalesOrderDetailActivity;
import in.co.vyaparienterprise.ui.activity.invoice.SalesOrder.SalesReturnDetailActivity;
import in.co.vyaparienterprise.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyaparienterprise.ui.fragment.PurchaseOrder.PurchasesInvoiceOrderFragment;
import in.co.vyaparienterprise.ui.fragment.PurchaseOrder.PurchasesOrderFragment;
import in.co.vyaparienterprise.ui.fragment.PurchaseOrder.PurchasesReceiptOrderFragment;
import in.co.vyaparienterprise.ui.fragment.PurchaseOrder.PurchasesReturnOrderFragment;
import in.co.vyaparienterprise.ui.fragment.SalesOrder.SalesDispatchOrderFragment;
import in.co.vyaparienterprise.ui.fragment.SalesOrder.SalesInvoiceOrderFragment;
import in.co.vyaparienterprise.ui.fragment.SalesOrder.SalesOrderFragment;
import in.co.vyaparienterprise.ui.fragment.SalesOrder.SalesReturnOrderFragment;
import in.co.vyaparienterprise.ui.generic.pdf.DownloadFileUrlConnectionImpl;
import in.co.vyaparienterprise.ui.generic.pdf.RemotePDFViewPager;
import in.co.vyaparienterprise.util.CurrencyUtil;
import in.co.vyaparienterprise.util.DataUtil;
import in.co.vyaparienterprise.util.DateUtil;
import in.co.vyaparienterprise.util.Utils;


/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class InvoicesAdapter extends RecyclerSwipeAdapter<InvoicesAdapter.MyViewHolder> implements DownloadFile.Listener {

    private final RecyclerView salesRV;
    private Context mContext;
    private ArrayList<InvoiceSum> invoiceSumList;
    private String PaymentAmount = "";
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
    public static String CopyInvoiceDetails = "";
    int invoicestatus = 0;

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
        //        @BindView(R.id.btnpayemnt)
//        ImageView btnpayemnt;
        @BindView(R.id.btnprrove)
        ImageView btnaprrove;
        @BindView(R.id.btndispatch)
        ImageView btndispatch;
        @BindView(R.id.invoices_item_mid)
        RelativeLayout invoices_item_mid;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public InvoicesAdapter(Context context, ArrayList<InvoiceSum> invoiceSumList, String type, RecyclerView salesRVs) {
        this.mContext = context;
        if (invoiceSumList == null) {
            invoiceSumList = new ArrayList<>();
        }
        this.invoiceSumList = invoiceSumList;
        mItemMange = new SwipeItemRecyclerMangerImpl(this);
        this.type = type;
        this.salesRV = salesRVs;
    }

    @Override
    public InvoicesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InvoicesAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoices, parent, false));
    }

    @Override
    public void onBindViewHolder(final InvoicesAdapter.MyViewHolder holder, final int position) {

        if (type.equalsIgnoreCase("SalesOrder")) {
            invoiceType = 1;
        } else if (type.equalsIgnoreCase("SalesDispatch")) {
            invoiceType = 1;
        } else if (type.equalsIgnoreCase("SalesInvoice")) {
            invoiceType = 1;
        } else if (type.equalsIgnoreCase("SalesReturn")) {
            invoiceType = 4;
        } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
            invoiceType = 2;
        } else if (type.equalsIgnoreCase("PurchaseOrder")) {
            invoiceType = 2;
        } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
            invoiceType = 2;
        } else if (type.equalsIgnoreCase("PurchaseReturn")) {
            invoiceType = 5;
        } else {
            invoiceType = 2;
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


        if (invoiceSum.getInvoiceStatus() == 1) {
            text = " " + mContext.getString(R.string.approved);
            holder.bottom_wrapper_draft.setVisibility(View.GONE);
            holder.bottom_wrapper_approved.setVisibility(View.VISIBLE);
            holder.btndispatch.setVisibility(View.VISIBLE);
            holder.btnaprrove.setVisibility(View.GONE);
            if (type.equalsIgnoreCase("SalesReturn") || type.equalsIgnoreCase("PurchaseReturn")) {
                holder.swipeLayout.setRightSwipeEnabled(true);
                holder.swipeLayout.setLeftSwipeEnabled(false);

            }
        } else if (invoiceSum.getInvoiceStatus() == 0) {
            text = " " + mContext.getString(R.string.draft);
            holder.bottom_wrapper_draft.setVisibility(View.VISIBLE);
            holder.bottom_wrapper_approved.setVisibility(View.GONE);
            holder.btnaprrove.setVisibility(View.VISIBLE);
            holder.btndispatch.setVisibility(View.GONE);
        } else if (invoiceSum.getInvoiceStatus() == 3) {
            holder.swipeLayout.setRightSwipeEnabled(false);
            holder.swipeLayout.setLeftSwipeEnabled(true);
            holder.btnaprrove.setVisibility(View.VISIBLE);
            holder.btndispatch.setVisibility(View.GONE);
            text = " " + mContext.getString(R.string.Canceled);
            holder.bottom_wrapper_draft.setVisibility(View.GONE);
            holder.bottom_wrapper_approved.setVisibility(View.GONE);
            holder.btnaprrove.setVisibility(View.VISIBLE);
        } else if (invoiceSum.getInvoiceStatus() == 2) {
            holder.swipeLayout.setRightSwipeEnabled(false);
            holder.swipeLayout.setLeftSwipeEnabled(false);
            text = " " + mContext.getString(R.string.Gl_Approved);
            holder.bottom_wrapper_draft.setVisibility(View.GONE);
            holder.bottom_wrapper_approved.setVisibility(View.GONE);
        } else if (invoiceSum.getInvoiceStatus() == -1) {
            holder.swipeLayout.setRightSwipeEnabled(false);
            holder.swipeLayout.setLeftSwipeEnabled(false);
            text = " " + mContext.getString(R.string.unknown);
            holder.bottom_wrapper_draft.setVisibility(View.GONE);
            holder.bottom_wrapper_approved.setVisibility(View.GONE);
        } else {
            holder.swipeLayout.setRightSwipeEnabled(false);
            holder.swipeLayout.setLeftSwipeEnabled(false);
            text = " ";
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

//        holder.btnpayemnt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //CollectionPayment(position,holder);
//                Toasty.success(mContext, "payment").show();
//                //getSaleInvoiceDetailCall(invoiceSumList.get(position).getId(),position,holder);
//
//            }
//        });
        holder.btnaprrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //Button draft
                if (type.equalsIgnoreCase("SalesOrder")) {

                    ///Draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        changeStatusCallOrder(position, holder, 1);
                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        //changeStatusCallOrder(position,holder,100);
                        //connection to disaptch
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        changeStatusCallOrder(position, holder, 1);
                    }//
                    else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                        //  changeStatusCallOrder(position,holder,100);
                    }
                } else if (type.equalsIgnoreCase("SalesDispatch")) {
                    //draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        changeStatusCallDispatch(position, holder, 1);
                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
//                        changeStatusCallDispatch(position,holder,100);
                        //connecting to invoice
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        changeStatusCallDispatch(position, holder, 1);
                    }//


                } else if (type.equalsIgnoreCase("SalesInvoice")) {
                    //draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        changeStatusCallInvoice(position, holder, 1);
                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        //changeStatusCallInvoice(position,holder,100);
                        //connection to Collection
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        changeStatusCallInvoice(position, holder, 1);
                    }//

                } else if (type.equalsIgnoreCase("SalesReturn")) {
                    //draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        changeStatusCallInvoice(position, holder, 1);
                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        //changeStatusCallInvoice(position,holder,100);
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        changeStatusCallInvoice(position, holder, 1);
                    }//
                } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                    //draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        changeStatusCallInvoice(position, holder, 1);
                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        //changeStatusCallInvoice(position,holder,100);
                        //connection to Collection
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        changeStatusCallInvoice(position, holder, 1);
                    }//
                } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                    ///Draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        changeStatusCallOrder(position, holder, 1);
                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        //changeStatusCallOrder(position,holder,100);
                        //connection to disaptch
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        changeStatusCallOrder(position, holder, 1);
                    }//
                    else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                        //  changeStatusCallOrder(position,holder,100);
                    }
                } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                    //draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        changeStatusCallDispatch(position, holder, 1);
                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
//                        changeStatusCallDispatch(position,holder,100);
                        //connecting to invoice
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        changeStatusCallDispatch(position, holder, 1);
                    }//

                } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                    //draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        changeStatusCallInvoice(position, holder, 1);
                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        //changeStatusCallInvoice(position,holder,100);
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        changeStatusCallInvoice(position, holder, 1);
                    }//
                }


            }
        });

        holder.btndispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Approve(position,holder);
                //Approve(position,holder);
                //button approve



                Toasty.success(mContext, "Approve").show();

                if (type.equalsIgnoreCase("SalesOrder")) {

                    if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        //changeStatusCallOrder(position,holder,100);
                        //connection to disaptch
                        getSaleOrderDetailCall(invoiceSumList.get(position).getId(),position,holder,"SalesOrderToDispatch");
                    }
                } else if (type.equalsIgnoreCase("SalesDispatch")) {

                    if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
//                        changeStatusCallDispatch(position,holder,100);
                        //connecting to invoice
                        getSaleDispatchDetailCall(invoiceSumList.get(position).getId(),position,holder,"SalesDispatchToInvoice");

                    }


                } else if (type.equalsIgnoreCase("SalesInvoice")) {

                    if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        //changeStatusCallInvoice(position,holder,100);
                        //connection to Collection
                    }

                }  else if (type.equalsIgnoreCase("PurchaseInvoice")) {

                    if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        //changeStatusCallInvoice(position,holder,100);
                        //connection to Collection
                    }
                } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                    if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        //changeStatusCallOrder(position,holder,100);
                        //connection to disaptch
                        getPurchaseOrderDetailCall(invoiceSumList.get(position).getId(),position,holder,"PurchaseOrderToDispatch");

                    }
                } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                    if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
//                        changeStatusCallDispatch(position,holder,100);
                        //connecting to invoice
                        getPuchaseReciptDetailCall(invoiceSumList.get(position).getId(),position,holder,"PurchaseRecepitToInvoice");
                    }

                }
            }
        });

        holder.btnprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print(position, holder);
            }
        });
        holder.btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Share(position, holder);
            }
        });
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // notifyDataSetChanged();
                //changeColor(position, true,holder);
                holder.swipeLayout.close();
                DataUtil.post(invoiceSumList.get(position).isActive());


                if (type.equalsIgnoreCase("SalesOrder")) {
                    getEditSaleOrderDetailCall(invoiceSumList.get(position).getId(), position, holder);
                } else if (type.equalsIgnoreCase("SalesDispatch")) {
                    getEditSaleDispatchDetailCall(invoiceSumList.get(position).getId(), position, holder);
                } else if (type.equalsIgnoreCase("SalesInvoice")) {
                    getEditSaleInvoiceDetailCall(invoiceSumList.get(position).getId(), position, holder);
                } else if (type.equalsIgnoreCase("SalesReturn")) {
                    getEditSaleReturnDetailCall(invoiceSumList.get(position).getId(), position, holder);
                } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                    getEditSaleInvoiceDetailCall(invoiceSumList.get(position).getId(), position, holder);
                } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                    getEditSaleOrderDetailCall(invoiceSumList.get(position).getId(), position, holder);
                } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                    getEditPuchaseReciptDetailCall(invoiceSumList.get(position).getId(), position, holder);
                } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                    getEditSaleReturnDetailCall(invoiceSumList.get(position).getId(), position, holder);
                }
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
                        if (type.equalsIgnoreCase("SalesOrder")) {
                            deleteOrderCall(position, holder);
                        } else if (type.equalsIgnoreCase("SalesDispatch")) {
                            deleteDispatchCall(position, holder);
                        } else if (type.equalsIgnoreCase("SalesInvoice")) {
                            deleteInvoiceCall(position, holder);
                        } else if (type.equalsIgnoreCase("SalesReturn")) {
                            deleteInvoiceCall(position, holder);
                        } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                            deleteInvoiceCall(position, holder);
                        } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                            deleteOrderCall(position, holder);
                        } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                            deleteDispatchCall(position, holder);
                        } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                            deleteInvoiceCall(position, holder);
                        }

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

                if (type.equalsIgnoreCase("SalesOrder")) {
                    getSaleOrderDetailCall(invoiceSumList.get(position).getId(), position, holder, "");
                } else if (type.equalsIgnoreCase("SalesDispatch")) {
                    getSaleDispatchDetailCall(invoiceSumList.get(position).getId(), position, holder, "");
                } else if (type.equalsIgnoreCase("SalesInvoice")) {
                    getSaleInvoiceDetailCall(invoiceSumList.get(position).getId(), position, holder, "");
                } else if (type.equalsIgnoreCase("SalesReturn")) {
                    getSaleReturnDetailCall(invoiceSumList.get(position).getId(), position, holder, "");
                } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                    getPurchaseInvoiceDetailCall(invoiceSumList.get(position).getId(), position, holder, "");
                } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                    getPurchaseOrderDetailCall(invoiceSumList.get(position).getId(), position, holder, "");
                } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                    getPuchaseReciptDetailCall(invoiceSumList.get(position).getId(), position, holder, "");
                } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                    getPurchaseReturnDetailCall(invoiceSumList.get(position).getId(), position, holder, "");
                }


            }
        });
        holder.invoices_item_mid.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//
                LogPrescClickDialogclik(invoiceSumList, position, holder);
                return false;
            }
        });

    }

    public void LogPrescClickDialogclik(final ArrayList<InvoiceSum> invoiceSumList, final int position, final MyViewHolder holder) {
        final AlertDialog builder = new AlertDialog.Builder(mContext).create();
        final View myView = LayoutInflater.from(mContext).inflate(R.layout.invoice_longpress, null);
        builder.setView(myView);
        builder.setCancelable(true);


        final LinearLayout invoice_press_popup = (LinearLayout) myView.findViewById(R.id.invoice_press_popup);
        final LinearLayout invoice_page_header = (LinearLayout) myView.findViewById(R.id.invoice_page_header);
        final TextView invoice_page_text = (TextView) myView.findViewById(R.id.invoice_page_text);

        final LinearLayout copy = (LinearLayout) myView.findViewById(R.id.copy);
        final LinearLayout change_status = (LinearLayout) myView.findViewById(R.id.change_status);
        final LinearLayout edit = (LinearLayout) myView.findViewById(R.id.edit);
        final LinearLayout delete = (LinearLayout) myView.findViewById(R.id.delete);
        final LinearLayout Dispatch = (LinearLayout) myView.findViewById(R.id.Dispatch);
        final LinearLayout invoice = (LinearLayout) myView.findViewById(R.id.invoice);
        final LinearLayout Share = (LinearLayout) myView.findViewById(R.id.Share);
        final LinearLayout print = (LinearLayout) myView.findViewById(R.id.print);
        final LinearLayout collection = (LinearLayout) myView.findViewById(R.id.collection);
        final LinearLayout returns = (LinearLayout) myView.findViewById(R.id.returns);

        final LinearLayout second_option_popup = (LinearLayout) myView.findViewById(R.id.second_option_popup);
        final LinearLayout changestatus_page_header = (LinearLayout) myView.findViewById(R.id.changestatus_page_header);

        final LinearLayout Approve = (LinearLayout) myView.findViewById(R.id.Approve);
        final LinearLayout Confirm = (LinearLayout) myView.findViewById(R.id.Confirm);
        final LinearLayout Cancel = (LinearLayout) myView.findViewById(R.id.Cancel);
        final LinearLayout Close = (LinearLayout) myView.findViewById(R.id.close);
        final LinearLayout Draft = (LinearLayout) myView.findViewById(R.id.Draft);

        invoice_page_text.setText(type);

        if (type.equalsIgnoreCase("SalesOrder")) {

            ///Draft
            if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            }//approved
            else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                Dispatch.setVisibility(View.VISIBLE);
                invoice.setVisibility(View.VISIBLE);
                Share.setVisibility(View.VISIBLE);
                print.setVisibility(View.VISIBLE);
            }//canceled
            else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
            }//
            else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                Share.setVisibility(View.VISIBLE);
                print.setVisibility(View.VISIBLE);
            }
        } else if (type.equalsIgnoreCase("SalesDispatch")) {
            //draft
            if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            }//approved
            else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                invoice.setVisibility(View.VISIBLE);
                Share.setVisibility(View.VISIBLE);
                print.setVisibility(View.VISIBLE);
            }//canceled
            else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
            }//


        } else if (type.equalsIgnoreCase("SalesInvoice")) {
            //draft
            if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            }//approved
            else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                collection.setVisibility(View.VISIBLE);
                returns.setVisibility(View.VISIBLE);
                Share.setVisibility(View.VISIBLE);
                print.setVisibility(View.VISIBLE);
            }//canceled
            else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
            }//

        } else if (type.equalsIgnoreCase("SalesReturn")) {
            //draft
            if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            }//approved
            else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                Share.setVisibility(View.VISIBLE);
                print.setVisibility(View.VISIBLE);
            }//canceled
            else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
            }//
        } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
            //draft
            if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            }//approved
            else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                collection.setVisibility(View.VISIBLE);
                returns.setVisibility(View.VISIBLE);
                Share.setVisibility(View.VISIBLE);
                print.setVisibility(View.VISIBLE);
            }//canceled
            else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
            }//
        } else if (type.equalsIgnoreCase("PurchaseOrder")) {
            ///Draft
            if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            }//approved
            else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                Dispatch.setVisibility(View.VISIBLE);
                invoice.setVisibility(View.VISIBLE);
                Share.setVisibility(View.VISIBLE);
                print.setVisibility(View.VISIBLE);
            }//canceled
            else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
            }//
            else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                Share.setVisibility(View.VISIBLE);
                print.setVisibility(View.VISIBLE);
            }
        } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
            //draft
            if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            }//approved
            else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                invoice.setVisibility(View.VISIBLE);
                Share.setVisibility(View.VISIBLE);
                print.setVisibility(View.VISIBLE);
            }//canceled
            else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
            }//

        } else if (type.equalsIgnoreCase("PurchaseReturn")) {
            //draft
            if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            }//approved
            else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
                Share.setVisibility(View.VISIBLE);
                print.setVisibility(View.VISIBLE);
            }//canceled
            else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                invoice_page_header.setVisibility(View.VISIBLE);
                copy.setVisibility(View.VISIBLE);
                change_status.setVisibility(View.VISIBLE);
            }//
        }


        change_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invoice_press_popup.setVisibility(View.GONE);

                if (type.equalsIgnoreCase("SalesOrder")) {
                    ///Draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Confirm.setVisibility(View.VISIBLE);
                        Cancel.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);

                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Draft.setVisibility(View.VISIBLE);
                        Cancel.setVisibility(View.VISIBLE);
                        Close.setVisibility(View.VISIBLE);
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);
                    }//
                    else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Close.setVisibility(View.VISIBLE);
                    }
                } else if (type.equalsIgnoreCase("SalesDispatch")) {
                    ///Draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);

                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Draft.setVisibility(View.VISIBLE);
                        Cancel.setVisibility(View.VISIBLE);
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);
                    }//
                    else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Close.setVisibility(View.VISIBLE);
                    }


                } else if (type.equalsIgnoreCase("SalesInvoice")) {
                    ///Draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);

                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Draft.setVisibility(View.VISIBLE);
                        Cancel.setVisibility(View.VISIBLE);
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);
                    }//
                    else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Close.setVisibility(View.VISIBLE);
                    }

                } else if (type.equalsIgnoreCase("SalesReturn")) {
                    ///Draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);

                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Draft.setVisibility(View.VISIBLE);
                        Cancel.setVisibility(View.VISIBLE);
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);
                    }//
                    else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Close.setVisibility(View.VISIBLE);
                    }
                } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                    ///Draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);

                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Draft.setVisibility(View.VISIBLE);
                        Cancel.setVisibility(View.VISIBLE);
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);
                    }//
                    else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Close.setVisibility(View.VISIBLE);
                    }
                } else if (type.equalsIgnoreCase("PurchaseOrder")) {
///Draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Confirm.setVisibility(View.VISIBLE);
                        Cancel.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);

                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Draft.setVisibility(View.VISIBLE);
                        Cancel.setVisibility(View.VISIBLE);
                        Close.setVisibility(View.VISIBLE);
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);
                    }//
                    else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Close.setVisibility(View.VISIBLE);
                    }
                } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                    ///Draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);

                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Draft.setVisibility(View.VISIBLE);
                        Cancel.setVisibility(View.VISIBLE);
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);
                    }//
                    else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Close.setVisibility(View.VISIBLE);
                    }

                } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                    ///Draft
                    if (invoiceSumList.get(position).getInvoiceStatus() == 0) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);

                    }//approved
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Draft.setVisibility(View.VISIBLE);
                        Cancel.setVisibility(View.VISIBLE);
                    }//canceled
                    else if (invoiceSumList.get(position).getInvoiceStatus() == 3) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Approve.setVisibility(View.VISIBLE);
                    }//
                    else if (invoiceSumList.get(position).getInvoiceStatus() == -1) {
                        second_option_popup.setVisibility(View.VISIBLE);
                        changestatus_page_header.setVisibility(View.VISIBLE);
                        Close.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
//        InvoiceStateEnum
//
//            Draunknownft = -1,
//            Draft =0,
//            Canceled = 3,
//            Approved = 1,
//            Gl_Approved = 2
//
//      OrderStateEnum
//
//            Draunknownft = -1,
//            Draft = 0,
//            Canceled = 3,
//            Confirm = 1,
//            Approved = 2
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                CopyCall(position, holder);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                EditCall(position, holder);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                DeleteCall(position, holder);
            }
        });

        Dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                if (type.equalsIgnoreCase("SalesOrder")) {
                    getSaleOrderDetailCall(invoiceSumList.get(position).getId(),position,holder,"SalesOrderToDispatch");
                } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                    getPurchaseOrderDetailCall(invoiceSumList.get(position).getId(),position,holder,"PurchaseOrderToDispatch");
                }

            }
        });
        invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                if (type.equalsIgnoreCase("SalesOrder")) {

                    getSaleOrderDetailCall(invoiceSumList.get(position).getId(),position,holder,"SalesOrderToInvoice");
                }
               else if (type.equalsIgnoreCase("SalesDispatch")) {
                    getSaleDispatchDetailCall(invoiceSumList.get(position).getId(),position,holder,"SalesDispatchToInvoice");
                }
                else if (type.equalsIgnoreCase("PurchaseOrder")) {
                    getPurchaseOrderDetailCall(invoiceSumList.get(position).getId(),position,holder,"PurchaseOrderToInvoice");
                }  else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                    getPuchaseReciptDetailCall(invoiceSumList.get(position).getId(),position,holder,"PurchaseRecepitToInvoice");
                }

            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
        //        CreateCollectionCall(invoiceSumList.get(position).getId(),position,holder,"OrderToInvoice");
            }
        });
        returns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                if (type.equalsIgnoreCase("SalesInvoice")) {

                    getSaleInvoiceDetailCall(invoiceSumList.get(position).getId(),position,holder,"SalesInvoiceToReturn");
                }
                else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                    getPurchaseInvoiceDetailCall(invoiceSumList.get(position).getId(),position,holder,"PurchaseInvoiceToReturn");
                }

            }
        });

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                ShareCall(position, holder);
            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                PrintCall(position, holder);
            }
        });

        Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                ApproveCall(position, holder);
            }
        });
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                ConfirmCall(position, holder);
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                CancelCall(position, holder);
            }
        });
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                CloseCall(position, holder);
            }
        });
        Draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                DraftCall(position, holder);
            }
        });
        builder.show();


    }

    private void DraftCall(int position, MyViewHolder holder) {
        Changestatuscall(position, holder, "Draft");
    }

    private void CloseCall(int position, MyViewHolder holder) {
        Changestatuscall(position, holder, "Close");
    }

    private void CancelCall(int position, MyViewHolder holder) {
        Changestatuscall(position, holder, "Cancel");
    }

    private void ConfirmCall(int position, MyViewHolder holder) {
        Changestatuscall(position, holder, "Confirm");

    }

    private void ApproveCall(int position, MyViewHolder holder) {
        Changestatuscall(position, holder, "Approve");
    }

    private void ReturnCall(int position, MyViewHolder holder) {
        Changestatuscall(position, holder, "Return");
    }

    private void CollectionCall(int position, MyViewHolder holder) {
        Changestatuscall(position, holder, "Collection");
    }

    private void PrintCall(int position, MyViewHolder holder) {
        print(position, holder);
    }


    private void ShareCall(int position, MyViewHolder holder) {


        Share(position, holder);
    }

//    private void InvoiceCall(int position, MyViewHolder holder) {
//        Changestatuscall(position, holder, "Invoice");
//    }
//
//    private void DispatchCall(final int position, final MyViewHolder holder) {
//        Changestatuscall(position, holder, "Dispatch");
//    }

    private void DeleteCall(final int position, final MyViewHolder holder) {
        holder.swipeLayout.close();
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("Delete Invoice");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                if (type.equalsIgnoreCase("SalesOrder")) {
                    deleteOrderCall(position, holder);
                } else if (type.equalsIgnoreCase("SalesDispatch")) {
                    deleteDispatchCall(position, holder);
                } else if (type.equalsIgnoreCase("SalesInvoice")) {
                    deleteInvoiceCall(position, holder);
                } else if (type.equalsIgnoreCase("SalesReturn")) {
                    deleteInvoiceCall(position, holder);
                } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                    deleteInvoiceCall(position, holder);
                } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                    deleteOrderCall(position, holder);
                } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                    deleteDispatchCall(position, holder);
                } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                    deleteInvoiceCall(position, holder);
                }

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

    private void EditCall(int position, MyViewHolder holder) {

        if (type.equalsIgnoreCase("SalesOrder")) {
            getEditSaleOrderDetailCall(invoiceSumList.get(position).getId(), position, holder);
        } else if (type.equalsIgnoreCase("SalesDispatch")) {
            getEditSaleDispatchDetailCall(invoiceSumList.get(position).getId(), position, holder);
        } else if (type.equalsIgnoreCase("SalesInvoice")) {
            getEditSaleInvoiceDetailCall(invoiceSumList.get(position).getId(), position, holder);
        } else if (type.equalsIgnoreCase("SalesReturn")) {
            getEditSaleReturnDetailCall(invoiceSumList.get(position).getId(), position, holder);
        } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
            getEditSaleInvoiceDetailCall(invoiceSumList.get(position).getId(), position, holder);
        } else if (type.equalsIgnoreCase("PurchaseOrder")) {
            getEditSaleOrderDetailCall(invoiceSumList.get(position).getId(), position, holder);
        } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
            getEditPuchaseReciptDetailCall(invoiceSumList.get(position).getId(), position, holder);
        } else if (type.equalsIgnoreCase("PurchaseReturn")) {
            getEditSaleReturnDetailCall(invoiceSumList.get(position).getId(), position, holder);
        }
    }


    public void CopyCall(int position, MyViewHolder holder) {

        if (type.equalsIgnoreCase("SalesOrder")) {
            getSaleOrderDetailCall(invoiceSumList.get(position).getId(), position, holder, "Copy");
        } else if (type.equalsIgnoreCase("SalesDispatch")) {
            getSaleDispatchDetailCall(invoiceSumList.get(position).getId(), position, holder, "Copy");
        } else if (type.equalsIgnoreCase("SalesInvoice")) {
            getSaleInvoiceDetailCall(invoiceSumList.get(position).getId(), position, holder, "Copy");
        } else if (type.equalsIgnoreCase("SalesReturn")) {
            getSaleReturnDetailCall(invoiceSumList.get(position).getId(), position, holder, "Copy");
        } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
            getPurchaseInvoiceDetailCall(invoiceSumList.get(position).getId(), position, holder, "Copy");
        } else if (type.equalsIgnoreCase("PurchaseOrder")) {
            getPurchaseOrderDetailCall(invoiceSumList.get(position).getId(), position, holder, "Copy");
        } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
            getPuchaseReciptDetailCall(invoiceSumList.get(position).getId(), position, holder, "Copy");
        } else if (type.equalsIgnoreCase("PurchaseReturn")) {
            getPurchaseReturnDetailCall(invoiceSumList.get(position).getId(), position, holder, "Copy");
        }


    }

    public void Changestatuscall(int position, MyViewHolder holder, String ChangeStatusType) {
        if (type.equalsIgnoreCase("SalesOrder")) {

            ///Draft
            if (ChangeStatusType.equalsIgnoreCase("Draft")) {
                changeStatusCallOrder(position, holder, 0);
            }//approved
            else if (ChangeStatusType.equalsIgnoreCase("Approve")) {
                changeStatusCallOrder(position, holder, 1);

            }//canceled
            else if (ChangeStatusType.equalsIgnoreCase("Cancel")) {
                changeStatusCallOrder(position, holder, 3);
            }//Confirm
            else if (ChangeStatusType.equalsIgnoreCase("Confirm")) {
                changeStatusCallOrder(position, holder, 2);
            }//Closed
            else if (ChangeStatusType.equalsIgnoreCase("Close")) {
                changeStatusCallOrder(position, holder, 6);
            }
        } else if (type.equalsIgnoreCase("SalesDispatch")) {
            //draft
            if (ChangeStatusType.equalsIgnoreCase("Draft")) {
                changeStatusCallDispatch(position, holder, 0);
            }//approved
            else if (ChangeStatusType.equalsIgnoreCase("Approve")) {
                changeStatusCallDispatch(position, holder, 1);

            }//canceled
            else if (ChangeStatusType.equalsIgnoreCase("Cancel")) {
                changeStatusCallDispatch(position, holder, 3);
            }//


        } else if (type.equalsIgnoreCase("SalesInvoice")) {
            //draft
            if (ChangeStatusType.equalsIgnoreCase("Draft")) {
                changeStatusCallInvoice(position, holder, 0);
            }//approved
            else if (ChangeStatusType.equalsIgnoreCase("Approve")) {
                changeStatusCallInvoice(position, holder, 1);

            }//canceled
            else if (ChangeStatusType.equalsIgnoreCase("Cancel")) {
                changeStatusCallInvoice(position, holder, 3);
            }//


        } else if (type.equalsIgnoreCase("SalesReturn")) {
            //draft
            if (ChangeStatusType.equalsIgnoreCase("Draft")) {
                changeStatusCallInvoice(position, holder, 0);
            }//approved
            else if (ChangeStatusType.equalsIgnoreCase("Approve")) {
                changeStatusCallInvoice(position, holder, 1);

            }//canceled
            else if (ChangeStatusType.equalsIgnoreCase("Cancel")) {
                changeStatusCallInvoice(position, holder, 3);
            }//
        } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
            //draft
            if (ChangeStatusType.equalsIgnoreCase("Draft")) {
                changeStatusCallInvoice(position, holder, 0);
            }//approved
            else if (ChangeStatusType.equalsIgnoreCase("Approve")) {
                changeStatusCallInvoice(position, holder, 1);

            }//canceled
            else if (ChangeStatusType.equalsIgnoreCase("Cancel")) {
                changeStatusCallInvoice(position, holder, 3);
            }//
        } else if (type.equalsIgnoreCase("PurchaseOrder")) {
            ///Draft
            if (ChangeStatusType.equalsIgnoreCase("Draft")) {
                changeStatusCallOrder(position, holder, 0);
            }//approved
            else if (ChangeStatusType.equalsIgnoreCase("Approve")) {
                changeStatusCallOrder(position, holder, 1);

            }//canceled
            else if (ChangeStatusType.equalsIgnoreCase("Cancel")) {
                changeStatusCallOrder(position, holder, 3);
            }//Confirm
            else if (ChangeStatusType.equalsIgnoreCase("Confirm")) {
                changeStatusCallOrder(position, holder, 2);
            }//Closed
            else if (ChangeStatusType.equalsIgnoreCase("Close")) {
                changeStatusCallOrder(position, holder, 6);
            }
        } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
            //draft
            if (ChangeStatusType.equalsIgnoreCase("Draft")) {
                changeStatusCallDispatch(position, holder, 0);
            }//approved
            else if (ChangeStatusType.equalsIgnoreCase("Approve")) {
                changeStatusCallDispatch(position, holder, 1);

            }//canceled
            else if (ChangeStatusType.equalsIgnoreCase("Cancel")) {
                changeStatusCallDispatch(position, holder, 3);
            }//

        } else if (type.equalsIgnoreCase("PurchaseReturn")) {
            //draft
            if (ChangeStatusType.equalsIgnoreCase("Draft")) {
                changeStatusCallInvoice(position, holder, 0);
            }//approved
            else if (ChangeStatusType.equalsIgnoreCase("Approve")) {
                changeStatusCallInvoice(position, holder, 1);

            }//canceled
            else if (ChangeStatusType.equalsIgnoreCase("Cancel")) {
                changeStatusCallInvoice(position, holder, 3);
            }//
        }

    }

    private void getPurchaseInvoiceDetailCall(String id, final int position, final MyViewHolder holder, final String copy) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetail(id, invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                changeColor(position, false, holder);
                Utils.hideLoading();

                if (!response.isError()) {
                    if (response.getData() != null) {
                        InvoiceDTO invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);

                        if (copy.equalsIgnoreCase("Copy")||copy.equalsIgnoreCase("PurchaseInvoiceToReturn")) {
                            CopyInvoiceDetails = "Copy";
                            Firm firm = new Firm(invoiceDTO.getFirm());
                            Invoice invoicecopydetail = new Invoice(invoiceDTO, firm);
                            DataUtil.post(invoicecopydetail);
                            if (type.equalsIgnoreCase("SalesOrder")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                            } else if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                if (copy.equalsIgnoreCase("PurchaseInvoiceToReturn")){
                                    mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                                }else {
                                    mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                                }
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            }
                        } else {
                            if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, PurchaseInvoiceDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, PurchaseOrderDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, PurchaseReceiptDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, PurchaseReturnDetailActivity.class));
                            } else {

                            }
                        }
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

    private void getPurchaseReturnDetailCall(String id, final int position, final MyViewHolder holder, final String copy) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetail(id, invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                changeColor(position, false, holder);
                Utils.hideLoading();

                if (!response.isError()) {
                    if (response.getData() != null) {
                        InvoiceDTO invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);

                        if (copy.equalsIgnoreCase("Copy")) {
                            CopyInvoiceDetails = "Copy";
                            Firm firm = new Firm(invoiceDTO.getFirm());
                            Invoice invoicecopydetail = new Invoice(invoiceDTO, firm);
                            DataUtil.post(invoicecopydetail);
                            if (type.equalsIgnoreCase("SalesOrder")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                            } else if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            }
                        } else {
                            if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, PurchaseInvoiceDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, PurchaseOrderDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, PurchaseReceiptDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, PurchaseReturnDetailActivity.class));
                            } else {

                            }
                        }
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

    private void getPurchaseOrderDetailCall(String id, final int position, final MyViewHolder holder, final String copy) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getOrderDetail(id, invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                changeColor(position, false, holder);
                Utils.hideLoading();
                if (!response.isError()) {
                    if (response.getData() != null) {
                        InvoiceDTO invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);

                        if (copy.equalsIgnoreCase("Copy")||copy.equalsIgnoreCase("PurchaseOrderToDispatch")
                        ||copy.equalsIgnoreCase("PurchaseOrderToInvoice")) {
                            CopyInvoiceDetails = "Copy";
                            Firm firm = new Firm(invoiceDTO.getFirm());
                            Invoice invoicecopydetail = new Invoice(invoiceDTO, firm);
                            DataUtil.post(invoicecopydetail);
                            if (type.equalsIgnoreCase("SalesOrder")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                            } else if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                if (copy.equalsIgnoreCase("PurchaseOrderToDispatch")){
                                    mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                                }else if(copy.equalsIgnoreCase("PurchaseOrderToInvoice")){
                                    mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                                }else {
                                    mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                                }
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            }
                        } else {
                            if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, PurchaseInvoiceDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, PurchaseOrderDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, PurchaseReceiptDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, PurchaseReturnDetailActivity.class));
                            } else {

                            }
                        }
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

    private void getPuchaseReciptDetailCall(String id, final int position, final MyViewHolder holder, final String copy) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getDispatchDetail(id, invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                changeColor(position, false, holder);
                Utils.hideLoading();

                if (!response.isError()) {
                    if (response.getData() != null) {

                        invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);
                        if (copy.equalsIgnoreCase("Copy")||copy.equalsIgnoreCase("PurchaseRecepitToInvoice")) {
                            CopyInvoiceDetails = "Copy";
                            Firm firm = new Firm(invoiceDTO.getFirm());
                            Invoice invoicecopydetail = new Invoice(invoiceDTO, firm);
                            DataUtil.post(invoicecopydetail);
                            if (type.equalsIgnoreCase("SalesOrder")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                            } else if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                if (copy.equalsIgnoreCase("PurchaseRecepitToInvoice")){
                                    mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                                }else {
                                    mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                                }
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            }
                        } else {
                            if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, SalesDispatchDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, SalesInvoiceDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesOrder")) {
                                mContext.startActivity(new Intent(mContext, SalesOrderDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, SalesReturnDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, PurchaseInvoiceDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, PurchaseOrderDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, PurchaseReceiptDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, PurchaseReturnDetailActivity.class));
                            } else {

                            }
                        }


                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                //changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
            }
        });
    }

    private void getSaleInvoiceDetailCall(String id, final int position, final MyViewHolder holder, final String copy) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetail(id, invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);

                        if (copy.equalsIgnoreCase("Copy")||copy.equalsIgnoreCase("SalesInvoiceToReturn"))  {
                            CopyInvoiceDetails = "Copy";
                            Firm firm = new Firm(invoiceDTO.getFirm());
                            Invoice invoicecopydetail = new Invoice(invoiceDTO, firm);
                            DataUtil.post(invoicecopydetail);
                            if (type.equalsIgnoreCase("SalesOrder")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                            } else if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                if (copy.equalsIgnoreCase("SalesInvoiceToReturn")){
                                    mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                                }else {
                                    mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                                }
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                    mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));

                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            }
                        } else {
                            if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, SalesDispatchDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, SalesInvoiceDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesOrder")) {
                                mContext.startActivity(new Intent(mContext, SalesOrderDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, SalesReturnDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            } else {

                            }
                        }


                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                //changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
            }
        });
    }

    private void getSaleReturnDetailCall(String id, final int position, final MyViewHolder holder, final String copy) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetail(id, invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);

                        if (copy.equalsIgnoreCase("Copy")) {
                            CopyInvoiceDetails = "Copy";
                            Firm firm = new Firm(invoiceDTO.getFirm());
                            Invoice invoicecopydetail = new Invoice(invoiceDTO, firm);
                            DataUtil.post(invoicecopydetail);
                            if (type.equalsIgnoreCase("SalesOrder")) {

                                mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                            } else if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            }
                        } else {
                            if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, SalesDispatchDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, SalesInvoiceDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesOrder")) {
                                mContext.startActivity(new Intent(mContext, SalesOrderDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, SalesReturnDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            } else {

                            }
                        }

                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                //changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
            }
        });
    }

    private void getSaleOrderDetailCall(String id, final int position, final MyViewHolder holder, final String copy) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getOrderDetail(id, invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);

                        if (copy.equalsIgnoreCase("Copy")||copy.equalsIgnoreCase("SalesOrderToDispatch")||
                        copy.equalsIgnoreCase("PurchaseOrderToDispatch")||copy.equalsIgnoreCase("SalesOrderToInvoice")) {
                            CopyInvoiceDetails = "Copy";
                            Firm firm = new Firm(invoiceDTO.getFirm());
                            Invoice invoicecopydetail = new Invoice(invoiceDTO, firm);
                            DataUtil.post(invoicecopydetail);
                            if (type.equalsIgnoreCase("SalesOrder")) {
                                if (copy.equalsIgnoreCase("SalesOrderToDispatch")) {
                                    mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                                } else if (copy.equalsIgnoreCase("SalesOrderToInvoice")){

                                    mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class).putExtra("SalesOrderToInvoice","SalesOrderToInvoice"));
                                }else {
                                    mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                                }
                            } else if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                if (copy.equalsIgnoreCase("PurchaseOrderToDispatch")){
                                    mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                                }else {
                                    mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                                }
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            }
                        } else {

                            if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, SalesDispatchDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, SalesInvoiceDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesOrder")) {
                                mContext.startActivity(new Intent(mContext, SalesOrderDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, SalesReturnDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            } else {

                            }
                        }


                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                //changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
            }
        });
    }

    private void getSaleDispatchDetailCall(String id, final int position, final MyViewHolder holder, final String copy) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getDispatchDetail(id, invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);
                        if (copy.equalsIgnoreCase("Copy")||copy.equalsIgnoreCase("SalesDispatchToInvoice")) {
                            CopyInvoiceDetails = "Copy";
                            Firm firm = new Firm(invoiceDTO.getFirm());
                            Invoice invoicecopydetail = new Invoice(invoiceDTO, firm);
                            DataUtil.post(invoicecopydetail);
                            if (type.equalsIgnoreCase("SalesOrder")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                            } else if (type.equalsIgnoreCase("SalesDispatch")) {
                                if (copy.equalsIgnoreCase("SalesDispatchToInvoice")){
                                    mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                                }else {
                                    mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                                }
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            }
                        } else {
                            if (type.equalsIgnoreCase("SalesDispatch")) {
                                mContext.startActivity(new Intent(mContext, SalesDispatchDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesInvoice")) {
                                mContext.startActivity(new Intent(mContext, SalesInvoiceDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesOrder")) {
                                mContext.startActivity(new Intent(mContext, SalesOrderDetailActivity.class));
                            } else if (type.equalsIgnoreCase("SalesReturn")) {
                                mContext.startActivity(new Intent(mContext, SalesReturnDetailActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                            } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                                mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                            } else {

                            }
                        }


                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                //changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
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

    private void getEditSaleInvoiceDetailCall(String id, final int position, final MyViewHolder holder) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetailEdit(id, invoiceType, new ServiceCall<BaseModel<Invoice>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<Invoice> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        Invoice invoice = response.getData();
                        DataUtil.post(invoice);


                        if (type.equalsIgnoreCase("SalesDispatch")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                        } else if (type.equalsIgnoreCase("SalesInvoice")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                        } else if (type.equalsIgnoreCase("SalesOrder")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                        } else if (type.equalsIgnoreCase("SalesReturn")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                        } else {

                        }
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                //changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
            }
        });

    }

    private void getEditSaleReturnDetailCall(String id, final int position, final MyViewHolder holder) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetailEdit(id, invoiceType, new ServiceCall<BaseModel<Invoice>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<Invoice> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        Invoice invoice = response.getData();
                        DataUtil.post(invoice);


                        if (type.equalsIgnoreCase("SalesDispatch")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                        } else if (type.equalsIgnoreCase("SalesInvoice")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                        } else if (type.equalsIgnoreCase("SalesOrder")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                        } else if (type.equalsIgnoreCase("SalesReturn")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                        } else {

                        }
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                //changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
            }
        });

    }

    private void getEditSaleOrderDetailCall(String id, final int position, final MyViewHolder holder) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getOrderDetailEdit(id, invoiceType, new ServiceCall<BaseModel<Invoice>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<Invoice> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        Invoice invoice = response.getData();
                        DataUtil.post(invoice);
                        if (type.equalsIgnoreCase("SalesDispatch")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                        } else if (type.equalsIgnoreCase("SalesInvoice")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                        } else if (type.equalsIgnoreCase("SalesOrder")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                        } else if (type.equalsIgnoreCase("SalesReturn")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                        } else {

                        }

                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                //changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
            }
        });

    }

    private void getEditSaleDispatchDetailCall(String id, final int position, final MyViewHolder holder) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getDispatchDetailEdit(id, invoiceType, new ServiceCall<BaseModel<Invoice>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<Invoice> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        Invoice invoice = response.getData();
                        DataUtil.post(invoice);
                        if (type.equalsIgnoreCase("SalesDispatch")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesDispatchActivity.class));
                        } else if (type.equalsIgnoreCase("SalesInvoice")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesInvoiceActivity.class));
                        } else if (type.equalsIgnoreCase("SalesOrder")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesOrderActivity.class));
                        } else if (type.equalsIgnoreCase("SalesReturn")) {
                            mContext.startActivity(new Intent(mContext, CreateSalesReturnActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                        } else {

                        }

                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                //changeColor(position, false, holder);
                Utils.hideLoading();
                notifyDataSetChanged();
            }
        });

    }

    private void getEditPuchaseReciptDetailCall(String id, final int position, final MyViewHolder holder) {
        changeColor(position, true, holder);
        Utils.showLoading(mContext);
        InvoiceService.getDispatchDetailEdit(id, Constants.PURCHASE_INVOICE, new ServiceCall<BaseModel<Invoice>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<Invoice> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        Invoice invoice = response.getData();
                        DataUtil.post(invoice);
                        if (type.equalsIgnoreCase("SalesDispatch")) {
                            mContext.startActivity(new Intent(mContext, SalesDispatchDetailActivity.class));
                        } else if (type.equalsIgnoreCase("SalesInvoice")) {
                            mContext.startActivity(new Intent(mContext, SalesInvoiceDetailActivity.class));
                        } else if (type.equalsIgnoreCase("SalesOrder")) {
                            mContext.startActivity(new Intent(mContext, SalesOrderDetailActivity.class));
                        } else if (type.equalsIgnoreCase("SalesReturn")) {
                            mContext.startActivity(new Intent(mContext, SalesReturnDetailActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseInvoiceActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseOrderActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseReceiptActivity.class));
                        } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                            mContext.startActivity(new Intent(mContext, CreatePurchaseReturnActivity.class));
                        } else {

                        }

                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                //changeColor(position, false, holder);
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
            color = ContextCompat.getColor(mContext, R.color.colorAccent);
            color2 = ContextCompat.getColor(mContext, R.color.colorAccent);
            color3 = ContextCompat.getColor(mContext, R.color.colorAccent);
        }


        holder = (InvoicesAdapter.MyViewHolder) salesRV.findViewHolderForLayoutPosition(position);
        holder.firmName.setTextColor(color);
        holder.price.setTextColor(color2);
//        holder.iDate.setTextColor(color3);
//        holder.status.setTextColor(color3);
    }


    //    private void getSaleInvoiceDetailCall(String id, final int position, final MyViewHolder holder) {
//        holder.swipeLayout.close();
//        changeColor(position, false, holder);
//        Utils.showLoading(mContext);
//
//        InvoiceService.getInvoiceDetail(id, invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
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
                //changeColor(position, false,holder);
                Utils.hideLoading();
                if (!response.isError()) {
                    if (response.getData() != null) {
                        InvoiceDTO invoiceDTO = response.getData();
                        DataUtil.post(invoiceDTO);
                        Intent i = new Intent(mContext, CreatePaymentActivity.class);
                        i.putExtra("Create", "false");
                        mContext.startActivity(i);
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                //changeColor(position, false,holder);
                Utils.hideLoading();

            }
        });
    }

    private void getPaymentcollectionCall(String id, final int position, final MyViewHolder holder) {

        Toasty.success(mContext, "Collections").show();

        Utils.showLoading(mContext);
        InvoiceService.getInvoiceDetail(id, invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                changeColor(position, false, holder);
                if (!response.isError()) {
                    if (response.getData() != null) {

                        InvoiceDTO paymentinvoiceDTO = response.getData();
                        DataUtil.post(paymentinvoiceDTO);

                        Intent i = new Intent(mContext, CreateCollectionActivity.class);
                        i.putExtra("Create", "false");
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

    private void deleteInvoiceCall(final int position, MyViewHolder holder) {
        holder.swipeLayout.close();
        //changeColor(position,true,holder);

        Toasty.success(mContext, "Delete").show();
        Utils.showLoading(mContext);

        InvoiceService.deleteInvoice(invoiceSumList.get(position).getId(), invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();

                    if (type.equalsIgnoreCase("SalesOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesOrderFragment salesOrderFragment = new SalesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, salesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesDispatch")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesDispatchOrderFragment SalesDispatchOrderFragment = new SalesDispatchOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesDispatchOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesInvoiceOrderFragment SalesInvoiceOrderFragment = new SalesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesReturnOrderFragment SalesReturnOrderFragment = new SalesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesReturnOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesInvoiceOrderFragment PurchasesInvoiceOrderFragment = new PurchasesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesOrderFragment PurchasesOrderFragment = new PurchasesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReceiptOrderFragment PurchasesReceiptOrderFragment = new PurchasesReceiptOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReceiptOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReturnOrderFragment PurchasesReturnOrderFragment = new PurchasesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReturnOrderFragment);
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

    private void deleteOrderCall(final int position, MyViewHolder holder) {
        holder.swipeLayout.close();
        //changeColor(position,true,holder);

        Toasty.success(mContext, "Delete").show();
        Utils.showLoading(mContext);

        InvoiceService.deleteOrder(invoiceSumList.get(position).getId(), invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    if (type.equalsIgnoreCase("SalesOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesOrderFragment salesOrderFragment = new SalesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, salesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesDispatch")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesDispatchOrderFragment SalesDispatchOrderFragment = new SalesDispatchOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesDispatchOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesInvoiceOrderFragment SalesInvoiceOrderFragment = new SalesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesReturnOrderFragment SalesReturnOrderFragment = new SalesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesReturnOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesInvoiceOrderFragment PurchasesInvoiceOrderFragment = new PurchasesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesOrderFragment PurchasesOrderFragment = new PurchasesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReceiptOrderFragment PurchasesReceiptOrderFragment = new PurchasesReceiptOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReceiptOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReturnOrderFragment PurchasesReturnOrderFragment = new PurchasesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReturnOrderFragment);
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

    private void deleteDispatchCall(final int position, MyViewHolder holder) {
        holder.swipeLayout.close();
        //changeColor(position,true,holder);

        Toasty.success(mContext, "Delete").show();
        Utils.showLoading(mContext);

        InvoiceService.deleteDispatch(invoiceSumList.get(position).getId(), invoiceType, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    if (type.equalsIgnoreCase("SalesOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesOrderFragment salesOrderFragment = new SalesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, salesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesDispatch")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesDispatchOrderFragment SalesDispatchOrderFragment = new SalesDispatchOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesDispatchOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesInvoiceOrderFragment SalesInvoiceOrderFragment = new SalesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesReturnOrderFragment SalesReturnOrderFragment = new SalesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesReturnOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesInvoiceOrderFragment PurchasesInvoiceOrderFragment = new PurchasesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesOrderFragment PurchasesOrderFragment = new PurchasesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReceiptOrderFragment PurchasesReceiptOrderFragment = new PurchasesReceiptOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReceiptOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReturnOrderFragment PurchasesReturnOrderFragment = new PurchasesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReturnOrderFragment);
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

    private void Share(int position, MyViewHolder holder) {
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

    private void printOption(int position, MyViewHolder holder) {
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

    private void print(int position, MyViewHolder holder) {
        holder.swipeLayout.close();
        //changeColor(position,true,holder);

        Toasty.success(mContext, "Print").show();

        printOption(position, holder);
    }

    private void Approve(int position, MyViewHolder holder) {
        //notifyDataSetChanged();
        holder.swipeLayout.close();
        //changeColor(position,true,holder);

        Toasty.success(mContext, "Approve").show();
        changeStatusCallInvoice(position, holder, 1);

    }

    private void CollectionPayment(int position, MyViewHolder holder) {
        // notifyDataSetChanged();
        holder.swipeLayout.close();
        // changeColor(position,true,holder);
        if (type.equalsIgnoreCase("Purchase")) {
            getPurchasepaymentCall(invoiceSumList.get(position).getId(), position, holder);
        } else {
            getPaymentcollectionCall(invoiceSumList.get(position).getId(), position, holder);
        }
    }

    private void changeStatusCallInvoice(final int position, final MyViewHolder holder, int StatusType) {
        InvoiceService.changeStatus(invoiceSumList.get(position).getId(), invoiceType, StatusType, new ServiceCall<BaseModel<Boolean>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<Boolean> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    // MobileConstants.invoiceId = "changeStatus";
                    if (type.equalsIgnoreCase("SalesOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesOrderFragment salesOrderFragment = new SalesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, salesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesDispatch")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesDispatchOrderFragment SalesDispatchOrderFragment = new SalesDispatchOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesDispatchOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesInvoiceOrderFragment SalesInvoiceOrderFragment = new SalesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesReturnOrderFragment SalesReturnOrderFragment = new SalesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesReturnOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesInvoiceOrderFragment PurchasesInvoiceOrderFragment = new PurchasesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesOrderFragment PurchasesOrderFragment = new PurchasesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReceiptOrderFragment PurchasesReceiptOrderFragment = new PurchasesReceiptOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReceiptOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReturnOrderFragment PurchasesReturnOrderFragment = new PurchasesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReturnOrderFragment);
                        fragmentTransaction.commit();
                    }

                } else {
                    Toasty.error(mContext, response.getMessage()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, "" + R.string.error).show();
            }
        });
    }

    private void changeStatusCallOrder(final int position, final MyViewHolder holder, int StatusType) {
        InvoiceService.changeStatusOrder(invoiceSumList.get(position).getId(), invoiceType, StatusType, new ServiceCall<BaseModel<Boolean>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<Boolean> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    // MobileConstants.invoiceId = "changeStatus";
                    if (type.equalsIgnoreCase("SalesOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesOrderFragment salesOrderFragment = new SalesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, salesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesDispatch")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesDispatchOrderFragment SalesDispatchOrderFragment = new SalesDispatchOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesDispatchOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesInvoiceOrderFragment SalesInvoiceOrderFragment = new SalesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesReturnOrderFragment SalesReturnOrderFragment = new SalesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesReturnOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesInvoiceOrderFragment PurchasesInvoiceOrderFragment = new PurchasesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesOrderFragment PurchasesOrderFragment = new PurchasesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReceiptOrderFragment PurchasesReceiptOrderFragment = new PurchasesReceiptOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReceiptOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReturnOrderFragment PurchasesReturnOrderFragment = new PurchasesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReturnOrderFragment);
                        fragmentTransaction.commit();
                    }

                } else {
                    Toasty.error(mContext, response.getMessage()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, "" + R.string.error).show();
            }
        });
    }

    private void changeStatusCallDispatch(final int position, final MyViewHolder holder, int StatusType) {
        InvoiceService.changeStatusDispatch(invoiceSumList.get(position).getId(), invoiceType, StatusType, new ServiceCall<BaseModel<Boolean>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<Boolean> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    // MobileConstants.invoiceId = "changeStatus";
                    if (type.equalsIgnoreCase("SalesOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesOrderFragment salesOrderFragment = new SalesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, salesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesDispatch")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesDispatchOrderFragment SalesDispatchOrderFragment = new SalesDispatchOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesDispatchOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesInvoiceOrderFragment SalesInvoiceOrderFragment = new SalesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("SalesReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        SalesReturnOrderFragment SalesReturnOrderFragment = new SalesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, SalesReturnOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseInvoice")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesInvoiceOrderFragment PurchasesInvoiceOrderFragment = new PurchasesInvoiceOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesInvoiceOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseOrder")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesOrderFragment PurchasesOrderFragment = new PurchasesOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseRecepit")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReceiptOrderFragment PurchasesReceiptOrderFragment = new PurchasesReceiptOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReceiptOrderFragment);
                        fragmentTransaction.commit();
                    } else if (type.equalsIgnoreCase("PurchaseReturn")) {
                        Toasty.success(mContext, response.getMessage()).show();
                        FragmentTransaction fragmentTransaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
                        PurchasesReturnOrderFragment PurchasesReturnOrderFragment = new PurchasesReturnOrderFragment();
                        fragmentTransaction.replace(R.id.frame, PurchasesReturnOrderFragment);
                        fragmentTransaction.commit();
                    }

                } else {
                    Toasty.error(mContext, response.getMessage()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(mContext, "" + R.string.error).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return invoiceSumList.size();
    }


}