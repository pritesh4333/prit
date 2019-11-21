package in.co.vyaparienterprise.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.robinhood.ticker.TickerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.MobileConstants;
import in.co.vyaparienterprise.model.Contact;
import in.co.vyaparienterprise.model.response.summary.FirmSum;
import in.co.vyaparienterprise.ui.listener.FirmClickListener;
import in.co.vyaparienterprise.util.CurrencyUtil;

/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class FirmsAdapter extends RecyclerView.Adapter<FirmsAdapter.MyViewHolder> {

    private Context mContext;
    private FirmClickListener clickListener;
    private ArrayList<FirmSum> firmSumList;
    private int green;
    private int purple;
    private int priceColor;

    public class MyViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.firm_item_top_view)
//        View topArea;
        @BindView(R.id.firm_item_text_area)
        LinearLayout mainTextArea;
        @BindView(R.id.firm_item_phone_call)
        ImageView phoneCall;
        @BindView(R.id.firm_item_letter_layout)
        RelativeLayout mainLetterArea;
        @BindView(R.id.firm_item_letter)
        TextView letter;
        @BindView(R.id.firm_item_letter_point)
        RelativeLayout deactivePoint;
        @BindView(R.id.firm_item_name)
        public TextView firmName;
        @BindView(R.id.firm_item_dist_city)
        public TextView distCity;
        @BindView(R.id.firm_item_balance)
        TickerView balance;
        @BindView(R.id.firm_item_balance_status)
        TextView balanceStatus;
        @BindView(R.id.firm_item_bottom_view)
        View bottomView;
//        @BindView(R.id.firm_item_bottom_rl)
//        RelativeLayout bottomAreaRL;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public FirmsAdapter(FirmClickListener clickListener, Context mContext, ArrayList<FirmSum> firmSumList) {
        if (firmSumList == null) {
            firmSumList = new ArrayList<>();
        }
        this.clickListener = clickListener;
        this.mContext = mContext;
        this.firmSumList = firmSumList;

        purple = ContextCompat.getColor(mContext, R.color.purple);
        green = ContextCompat.getColor(mContext, R.color.green_view);
        priceColor = ContextCompat.getColor(mContext, R.color.price_color);
    }

    @Override
    public FirmsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FirmsAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_firm, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final FirmsAdapter.MyViewHolder holder, final int position) {
        FirmSum firmSum = firmSumList.get(position);
        holder.firmName.setText(firmSum.getName());

        if (firmSum.getCity() != null) {
            holder.distCity.setText(String.valueOf(firmSum.getCity() + " - " + firmSum.getState()));
        } else {
            holder.distCity.setText(firmSum.getState());
        }

        if (firmSum.isActive()) {
            holder.deactivePoint.setVisibility(View.GONE);
        } else {
            holder.deactivePoint.setVisibility(View.VISIBLE);
        }

        holder.balance.setVisibility(View.VISIBLE);
        holder.balanceStatus.setVisibility(View.VISIBLE);
        Double balance;
        balance=firmSum.getBalance();
        if (firmSum.getBalance() > 0) {
            holder.balance.setTextColor(green);
            holder.balanceStatus.setText(R.string.debit);
        } else if (firmSum.getBalance() == 0) {
            holder.balance.setTextColor(priceColor);
            holder.balanceStatus.setVisibility(View.GONE);
        } else {
              balance=firmSum.getBalance()*-1;
           // firmSum.setBalance(firmSum.getBalance() * -1);
            holder.balance.setTextColor(purple);
            holder.balanceStatus.setText(R.string.credit);
        }
        String _balance = String.valueOf(CurrencyUtil.doubleToCurrency(balance, MobileConstants.defaultCurrency));
        holder.balance.setText(_balance, true);

        boolean isHasPhoneNumber = false;

        for (Contact contact : firmSum.getContacts()) {
            if (contact.getType() == 0) {
                isHasPhoneNumber = true;
                break;
            }
        }

        if (!isHasPhoneNumber) {
            holder.phoneCall.setVisibility(View.GONE);
            holder.mainLetterArea.setVisibility(View.VISIBLE);

            Character c;
            if (firmSum.getName() != null && !firmSum.getName().equals("")) {
                c = firmSum.getName().charAt(0);
                if (Character.isDigit(c)) {
                    c = '#';
                }
            } else {
                c = '-';
            }

            //Random color = new Random();
            //int randomColor = Color.argb(255, color.nextInt(255), color.nextInt(255), color.nextInt(255));

            holder.letter.setText(c.toString());
            //holder.mainLetterArea.setBackgroundColor(randomColor);
            //GradientDrawable shape = new GradientDrawable();
            //shape.setShape(GradientDrawable.OVAL);
            //shape.setColor(randomColor);
            //holder.mainLetterArea.setBackgroundDrawable(shape);
        } else {

            holder.mainLetterArea.setVisibility(View.GONE);
            holder.phoneCall.setVisibility(View.VISIBLE);

            holder.phoneCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.doPhoneCall(position);
                }
            });
        }


        holder.mainTextArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(position);
            }
        });

        holder.mainTextArea.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickListener.onItemLongClick(position);
                return false;
            }
        });

        if (position == 0) {
           // holder.topArea.setVisibility(View.VISIBLE);
            holder.bottomView.setVisibility(View.VISIBLE);
          //  holder.bottomAreaRL.setVisibility(View.GONE);
        } else if (position == firmSumList.size() - 1) {
          //  holder.topArea.setVisibility(View.GONE);
            holder.bottomView.setVisibility(View.GONE);
           // holder.bottomAreaRL.setVisibility(View.VISIBLE);
        } else {
          //  holder.topArea.setVisibility(View.GONE);
            holder.bottomView.setVisibility(View.VISIBLE);
           // holder.bottomAreaRL.setVisibility(View.GONE);
        }

        if (firmSumList.size() == 1) {
         //   holder.topArea.setVisibility(View.VISIBLE);
            holder.bottomView.setVisibility(View.GONE);
          //  holder.bottomAreaRL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return firmSumList.size();
    }
}