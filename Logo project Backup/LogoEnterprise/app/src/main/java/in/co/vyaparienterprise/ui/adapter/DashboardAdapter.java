package in.co.vyaparienterprise.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.KeyValue;

import static in.co.vyaparienterprise.util.CurrencyUtil.indianCurrency;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {
    private Context mContext;
    ArrayList<KeyValue> topMaterial;
    String DasboardDataType;
    String QuentityOrVlaue;
    public DashboardAdapter(Context mContext, ArrayList<KeyValue> topMaterial,  String datatype,String QuentityOrVlaue) {
        this.mContext=mContext;
        this.topMaterial=topMaterial;
        this.DasboardDataType=datatype;
        this.QuentityOrVlaue=QuentityOrVlaue;
    }



    @Override
    public long getItemId(int i) {
        return topMaterial.size();
    }

    @Override
    public int getItemCount() {
        return topMaterial.size();
    }


    @Override
    public DashboardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DashboardAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dasboardbotomsheetadapter, parent, false));
    }

    @Override
    public void onBindViewHolder(DashboardAdapter.MyViewHolder viewHolder, int position) {
         viewHolder.items.setText(topMaterial.get(position).getKey());
         if (DasboardDataType.equalsIgnoreCase("TopCustomer")) {
             viewHolder.price.setText("₹"+ indianCurrency(Double.valueOf(topMaterial.get(position).getValue())));
         }else{
             if (QuentityOrVlaue.equalsIgnoreCase("2")){
                 viewHolder.price.setText("₹"+ indianCurrency(Double.valueOf(topMaterial.get(position).getValue())));
             }else {
                 viewHolder.price.setText( ""+ indianCurrency(Double.valueOf(topMaterial.get(position).getValue())));
             }
         }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.items)
        TextView items;
        @BindView(R.id.price)
        TextView price;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public static String format2DecAmountDouble(Double amount) {
        String formattedAmount = String.format("%.2f", amount);
        return formattedAmount;
    }
}
